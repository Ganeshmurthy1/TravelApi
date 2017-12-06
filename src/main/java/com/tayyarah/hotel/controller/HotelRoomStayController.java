package com.tayyarah.hotel.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.hotel.dao.ApiHotelMapStoreDao;
import com.tayyarah.hotel.dao.HotelCityDao;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.Islhotelmapping;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.DesiyaPullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;

@RestController
@RequestMapping("/Hotels")
public class HotelRoomStayController {

	@Autowired
	HoteloverviewDao hotelDao;
	@Autowired
	HotelroomdescriptionDao hotelroomdescriptionDao;	
	@Autowired
	HotelimagesDao hotelimagesDao;
	@Autowired
	HotelFacilityDao hotelFacilityDao;
	@Autowired
	IslhotelmappingDao islhotelmappingDao;
	@Autowired
	HotelinandaroundDao hotelinandaroundDao;
	@Autowired
	HotelsecondaryareaDao hotelsecondaryareaDao;	
	@Autowired
	HotelCityDao hotelCityDao;
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	ApiHotelMapStoreDao apihotelstoredao;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	CompanyDao companyDao;

	public static final Logger logger = Logger.getLogger(HotelCityController.class);	


	@RequestMapping(value="/roomstaies",method = RequestMethod.GET,headers="Accept=application/json")public @ResponseBody	
	APIHotelMap getRoomStaies(@RequestParam(value="mode") int mode,@RequestParam(value="type") int type,@RequestParam(value="order") String order,@RequestParam(value="filter") int filter,@RequestParam(value="cachelevel") String cachelevel,@RequestParam(value="currency") String currency,@RequestParam(value="version") BigDecimal version,@RequestParam(value="lang") String lang,@RequestParam(value="city") String city, 
			@RequestParam(value="countrycode") String countrycode,@RequestParam(value="country") String country, @RequestParam(value="datestart") String datestart,@RequestParam(value="dateend") String dateend,@RequestParam(value="noofrooms") int noofrooms,     HttpServletResponse response) throws NumberFormatException, Exception {
		//Default app key
		String app_key = "1-1";
		HotelSearchCommand hs = new HotelSearchCommand(mode, type, HotelSearchCommand.SORT_ORDER_PRICE,HotelSearchCommand.FILTER_RATE, HotelSearchCommand.CACHE_LEVEL_LIVE,HotelSearchCommand.CURRENCY_INR, new BigDecimal("1.0"),"en", city, country, country, datestart , dateend,noofrooms );
		ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();	
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);
		if(appKeyVo==null)
		{
			// TODO Error 
			//			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);		
		DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(apidesiya, hs,"Desiya Api");

		HotelApiCredentials apirez = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);		
		RezNextPullerTask rezNextPullerTask = new RezNextPullerTask(apirez, hs,"Reznext Api");

		apiPullExecutor.submit(desiyaPullerTask);		
		apiPullExecutor.submit(rezNextPullerTask);

		//check active thread, if zero then shut down the thread pool
		for (;;) {
			int count = apiPullExecutor.getActiveCount();
			System.out.println("Active pullings : " + count);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (count == 0) {
				//apiPullExecutor.shutdown();
				break;
			}
		}
		APIHotelMap apistack = new APIHotelMap();
		apistack = hotelObjectTransformer.initHotelsMap(desiyaPullerTask.getOtaHotelAvailRes());
		apistack = hotelObjectTransformer.compareandAddHotelsMap(apistack, rezNextPullerTask.getOtaHotelAvailRes());
		return apistack;
	}

	@RequestMapping(value="/tgroomstaies",method = RequestMethod.GET,headers="Accept=application/json")public @ResponseBody	
	APIHotelMap getRoomStaiesDesiya(@RequestParam(value="mode") int mode,@RequestParam(value="type") int type,@RequestParam(value="order") String order,@RequestParam(value="filter") int filter,@RequestParam(value="cachelevel") String cachelevel,@RequestParam(value="currency") String currency,@RequestParam(value="version") BigDecimal version,@RequestParam(value="lang") String lang,@RequestParam(value="city") String city, 
			@RequestParam(value="countrycode") String countrycode,@RequestParam(value="country") String country, @RequestParam(value="datestart") String datestart,@RequestParam(value="dateend") String dateend,@RequestParam(value="noofrooms") int noofrooms,     HttpServletResponse response) throws NumberFormatException, Exception {
		//Default app key
		String appkey = "1-1";
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			// TODO Error 
			//			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}	
		HotelSearchCommand hs = new HotelSearchCommand(mode, type, HotelSearchCommand.SORT_ORDER_PRICE,HotelSearchCommand.FILTER_RATE, HotelSearchCommand.CACHE_LEVEL_LIVE,HotelSearchCommand.CURRENCY_INR, new BigDecimal("1.0"),"en", city, country, country, datestart , dateend,noofrooms );
		ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();		
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);		
		DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(null, hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apidesiya, hs, "Desiya Api", hs.getSearchCity());		
		Future<APIHotelMap> apistack = apiPullExecutor.submit(desiyaPullerTask);	

		try {
			APIHotelMap apimap = apistack.get(); 			
			return apimap;

		} catch (HibernateException e) {			
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException  e)
		{
			e.printStackTrace();
		}
		return new APIHotelMap();
	}	

	@RequestMapping(value = "/tgroomdetails",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	OTAHotelAvailRS.RoomStays.RoomStay getTGRoomStayDetails(@RequestParam(value="searchkey") int searchkey,@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	

		ResponseHeader.setResponse(response);//Setting response header		
		HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		try
		{				
			apiHotelMap = apihotelstoredao.getTGRoomStaysMap(searchkey);	
			OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(null, null, apiHotelMap.get(hotelcode));
			return rs;

		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
		}
		catch (JAXBException  e)
		{
			logger.info("query Exception:IOException:"+ e.getMessage());
		}
		catch (Exception  e)
		{
			logger.info("query Exception:IOException:"+ e.getMessage());	
		}	
		return null;
	}	


	@RequestMapping(value="/rzroomstaies",method = RequestMethod.GET,headers="Accept=application/json")public @ResponseBody	
	APIHotelMap getRoomStaiesReznext(@RequestParam(value="mode") int mode,@RequestParam(value="type") int type,@RequestParam(value="order") String order,@RequestParam(value="filter") int filter,@RequestParam(value="cachelevel") String cachelevel,@RequestParam(value="currency") String currency,@RequestParam(value="version") BigDecimal version,@RequestParam(value="lang") String lang,@RequestParam(value="city") String city, 
			@RequestParam(value="countrycode") String countrycode,@RequestParam(value="country") String country, @RequestParam(value="datestart") String datestart,@RequestParam(value="dateend") String dateend,@RequestParam(value="noofrooms") int noofrooms,     HttpServletResponse response) throws NumberFormatException, Exception {
		//Default app key
		String app_key = "zqJ3R9cGpNWgNXG55ub/WQ==";
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);
		if(appKeyVo==null)
		{
			// TODO Error 
			//			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		HotelSearchCommand hs = new HotelSearchCommand(mode, type, HotelSearchCommand.SORT_ORDER_PRICE,HotelSearchCommand.FILTER_RATE, HotelSearchCommand.CACHE_LEVEL_LIVE,HotelSearchCommand.CURRENCY_INR, new BigDecimal("1.0"),"en", city, country, country, datestart , dateend,noofrooms );
		ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();		
		HotelApiCredentials apirez = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);		
		RezNextPullerTask rezNextPullerTask = new RezNextPullerTask(apirez, hs,"Reznext Api", RezNextPullerTask.ACTION_GETHOTELINFOBYCITY);
		Future<APIHotelMap> apistack = apiPullExecutor.submit(rezNextPullerTask);	

		try {
			APIHotelMap apimap = apistack.get(); 
			return apimap;			
		} catch (HibernateException e) {			
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException  e)
		{
			e.printStackTrace();
		}
		return new APIHotelMap();	

	}

	@RequestMapping(value = "/rzroomdetails",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	OTAHotelAvailRS.RoomStays.RoomStay getRZRoomStayDetails(@RequestParam(value="searchkey") int searchkey,@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	

		ResponseHeader.setResponse(response);//Setting response header		
		HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		try
		{					
			apiHotelMap =apihotelstoredao.getTGRoomStaysMap(searchkey);	
			OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(null, null, apiHotelMap.get(hotelcode));
			return rs;

		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
		}
		catch (ClassNotFoundException  e)
		{
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
		}
		catch (JAXBException  e)
		{
			logger.info("query Exception:JAXBException:"+ e.getMessage());		
		}
		catch (Exception  e)
		{
			logger.info("query Exception:JAXBException:"+ e.getMessage());		
		}
		return null;
	}

	@RequestMapping(value = "/roomdetails/{searchKey}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	HashMap<String, RoomStay> getRoomStaysMap(@PathVariable("searchKey") int searchKey,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header		
		HashMap<String, RoomStay> apiHotelMap = new HashMap<String, RoomStay>();
		try {						
			apiHotelMap =apihotelstoredao.getRoomStaysMap(searchKey);	
		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
		}	
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
		}
		return apiHotelMap;
	}
}
