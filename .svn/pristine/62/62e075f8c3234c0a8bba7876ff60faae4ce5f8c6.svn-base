package com.tayyarah.hotel.rezlive.test.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPException;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.api.hotel.rezlive.model.HotelFindResponse;
import com.tayyarah.api.hotel.rezlive.model.PreBookingResponse;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.hotel.controller.HotelSearchController;
import com.tayyarah.hotel.dao.ApiHotelMapStoreDao;
import com.tayyarah.hotel.dao.HotelCityDao;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelMarkupDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelSearchRoomDetailDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.entity.Islhotelmapping;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIRoomDetail;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.HotelMarkUpUtil;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.RezLivePullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;

@RestController
@RequestMapping("/rezlive")
public class RezLiveTestController {

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
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	HotelSearchRoomDetailDao hotelSearchRoomDetailDao;
	@Autowired
	HotelMarkupDao hotelMarkupDao;	
	@Autowired
	CompanyDao companyDao;
	@Autowired
	HotelIdFactoryImpl hotelIdFactory;		
	@Autowired
	AsyncSupport asyncSupport;		

	public static final Logger logger = Logger.getLogger(HotelSearchController.class);

	@RequestMapping(value="",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody	
	APIHotelMap getRoomStaiesReznext(@RequestParam(value="appkey") String appkey, @RequestParam(value="mode") int mode,@RequestParam(value="type") int type,@RequestParam(value="order") String order,@RequestParam(value="filter") int filter,@RequestParam(value="cachelevel") String cachelevel,@RequestParam(value="currency") String currency,@RequestParam(value="version") BigDecimal version,@RequestParam(value="lang") String lang,@RequestParam(value="city") String city, 
			@RequestParam(value="countrycode") String countrycode,@RequestParam(value="country") String country, @RequestParam(value="datestart") String datestart,@RequestParam(value="dateend") String dateend,@RequestParam(value="noofrooms") int noofrooms, @RequestParam(value="rooms") String rooms,    HttpServletResponse response) throws HibernateException, IOException, Exception {

		//Enable the below line to validat app key 		
		APIHotelMap apimap = new APIHotelMap();
		HotelSearchCity hotelSearchCity = null;
		try{
			hotelSearchCity = hotelCityDao.getHotelSearchCity(city, countrycode);
			logger.info("hotelSearchCity-----------:"+ hotelSearchCity);
			logger.info("hotelSearchCity----tg ------:"+ hotelSearchCity.getTgCity());		
			logger.info("hotelSearchCity----reznext ------:"+ hotelSearchCity.getReznextCity());		
			logger.info("hotelSearchCity----rezlive -------:"+ hotelSearchCity.getRezliveCity());			 
		}
		catch(Exception e)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " City and Country code has issues..." ));
			return apimap ;	
		}		
		if(hotelSearchCity == null)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Select some other city to search..." ));
			return apimap ;	
		}
		HotelSearchCommand hsc = new HotelSearchCommand(appkey, mode, type, order, filter, cachelevel, currency, new BigDecimal("1.0"),lang, city, country, country, datestart , dateend, noofrooms, rooms );
		hsc.setSearchCity(hotelSearchCity);

		HotelTransactionTemp ht = new HotelTransactionTemp();		
		HotelSearchTemp hs = new HotelSearchTemp();		
		logger.info("HotelSearchCommand-----------:"+ hsc.toString());	
		ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();		

		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);		
		RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");		
		Future<APIHotelMap> apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);	
		APIHotelMap apimaprezlive = new APIHotelMap();

		try {			
			TreeMap<String, RoomStay> roomStaysMapRezlive = new TreeMap<String, RoomStay>();
			apimaprezlive = apistackrezlive.get();
			roomStaysMapRezlive = apimaprezlive.getRoomStays();			
			TreeMap<String, RoomStay> roomStaysMap = roomStaysMapRezlive;
			apimap.setRoomStays(roomStaysMap);			
			List<HotelMarkup> markups = hotelMarkupDao.getAllHotelMarkups();						
			apimap.applyMarkUpInitHotelSearch(hsc, markups);				
			byte[] roomStaysMapdata = SerializationUtils.serialize(apimap.getRoomStays());
			hs.setHotelres_map(roomStaysMapdata);
			byte[] hscbytes = SerializationUtils.serialize(hsc);
			hs.setHotelsearch_cmd(hscbytes);
			hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);				

			//change the below for api key to string format..
			ht.setApi_key(Long.valueOf(1));
			ht.setSearch_key(hs.getSearch_key());
			ht = hotelTransactionDao.insertApiMapBySearchKey(ht);	
			return apimap;

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;			
		} catch (InterruptedException e)
		{			
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;		
		}
		catch (ExecutionException  e)
		{
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;		
		}		
		catch (Exception  e)
		{
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;		
		}	
	}

	@RequestMapping(value = "/roomdetail",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIRoomDetail getRoomStayDetailsReznext(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, @RequestParam(value="bookingkey") String bookingkey, HttpServletResponse response) {	

		//Enable the below line to validat app key 		
		ResponseHeader.setPostResponse(response);//Setting response header	
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}
		HotelSearchTemp hs = new HotelSearchTemp();
		HotelTransactionTemp ht = new HotelTransactionTemp();
		HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelSearchCommand hsc = null;
		try
		{			
			hs = hotelSearchDao.getHotelSearch(searchkey);
			ht = hotelTransactionDao.getHotelTransaction(searchkey);	

			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());
			apiHotelMap = (HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay>) SerializationUtils.deserialize(hs.getHotelres_map());
			OTAHotelAvailRS.RoomStays.RoomStay rs = apiHotelMap.get(hotelcode);
			List<HotelSearchCommand.RoomReqInfo> roomReqs = null;
			switch (rs.getBasicPropertyInfo().getApiProvider()) {
			case HotelApiCredentials.API_DESIA_IND:
				rs = hotelObjectTransformer.getTGRoomDetails(rs);
				break;
			case HotelApiCredentials.API_REZNEXT_IND:
				roomReqs = hsc.getRoomrequests();
				logger.info("Searhing roomReqs size : "+roomReqs.size());			

				StringBuilder roominfo = new StringBuilder("<RoomInfo NumberOfRooms=\""+roomReqs.size()+"\">");
				for (HotelSearchCommand.RoomReqInfo roomReq : roomReqs) {
					roominfo.append("<Rooms Room=\""+roomReq.getRoomindex()+"\" Adult=\""+roomReq.getNoofAdult()+"\" Child=\""+roomReq.getNoofChild()+"\" />");

				}
				roominfo.append("</RoomInfo>");		
				logger.info("Searhing roomReqs roominfo : "+roominfo);	
				HotelApiCredentials apirezt = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);					
				RezNextPullerTask reznextPullerTask = new RezNextPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apirezt, hsc, "RazNext Api");		
				reznextPullerTask.setRoomStay(rs);
				rs = reznextPullerTask.GetHotelRoomDetail(rs);		
				break;
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				roomReqs = hsc.getRoomrequests();
				logger.info("Searhing roomReqs size : "+roomReqs.size());					

				HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);		
				RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");		
				rs = rezLivePullerTask.getRoomDetail(rs);					

				break;
			default:
				break;
			}

			List<HotelMarkup> markups = hotelMarkupDao.getHotelMarkups(appKeyVo.getCompanyId());
			logger.info("HotelSearchCommand- no of markups----------:"+ markups.size()+"- for company id--"+appKeyVo.getCompanyId());				
			HotelMarkUpUtil.applyMarkUpOnHotelRoomDetail(hsc, markups, rs);
			apiHotelMap.put(rs.getBasicPropertyInfo().getHotelCode(), rs);
			byte[] roomStaysMapdata = SerializationUtils.serialize(apiHotelMap);
			hs.setHotelres_map(roomStaysMapdata);				
			hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);	
			return new APIRoomDetail(hsc, rs, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS),BigInteger.valueOf(ht.getSearch_key()), BigInteger.valueOf(ht.getId()) ) ;
		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}			
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
	}

	@RequestMapping(value = "/summary",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIRoomDetail getRoomStayDetailsSummaryReznext(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, @RequestParam(value="roomtypecodes") String roomtypecodes, @RequestParam(value="bookingkey") String bookingkey, HttpServletResponse response) {	
		//Enable the below line to validat app key 
		//String encryptedkey = AppControllerUtil.getDecryptedAppKey(CDAO, appkey);
		ResponseHeader.setPostResponse(response);//Setting response header	

		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		HotelSearchTemp hs = new HotelSearchTemp();
		HotelTransactionTemp ht = new HotelTransactionTemp();
		HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelSearchCommand hsc = null;

		try
		{			
			logger.info("Searhing roomdetails summary controller : "+searchkey);			
			hs = hotelSearchDao.getHotelSearch(searchkey);	
			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());
			ht = hotelTransactionDao.getHotelTransaction(searchkey);

			apiHotelMap = (HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay>) SerializationUtils.deserialize(hs.getHotelres_map());
			OTAHotelAvailRS.RoomStays.RoomStay rs = apiHotelMap.get(hotelcode);			
			List<String> roomTypeCodeList = new ArrayList<String>();
			if(roomTypeCodeList != null)
			{
				roomTypeCodeList = getRoomTypeCodeList(roomtypecodes);
				logger.info("Searhing roomdetails summary controller : roomtypecodes=="+roomtypecodes);			
			}
			switch (rs.getBasicPropertyInfo().getApiProvider()) {
			case HotelApiCredentials.API_DESIA_IND:
				rs = hotelObjectTransformer.getTGRoomDetailsSummary(rs,roomTypeCodeList);
				break;
			case HotelApiCredentials.API_REZNEXT_IND:
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);		
				RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");		
				break;
			default:
				break;
			}
			return new APIRoomDetail(hsc, rs, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS),BigInteger.valueOf(ht.getSearch_key()), BigInteger.valueOf(ht.getId()) ) ;
		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}			
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;
		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;
		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;
		}
	}

	@RequestMapping(value = "/city",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	String getCitySearch(@RequestParam(value="city") String city, @RequestParam(value="countrycode") String countrycode, HttpServletResponse response) throws HibernateException, IOException, Exception {	
		HotelSearchCity hotelSearchCity = hotelCityDao.getHotelSearchCity(city, countrycode);
		logger.info("hotelSearchCity-----------:"+ hotelSearchCity);
		logger.info("hotelSearchCity----tg ------:"+ hotelSearchCity.getTgCity());		
		logger.info("hotelSearchCity----reznext ------:"+ hotelSearchCity.getReznextCity());		
		logger.info("hotelSearchCity----rezlive -------:"+ hotelSearchCity.getRezliveCity());	

		return "success";
	}

	@RequestMapping(value = "/transform",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	String modify(@RequestParam(value="city") String city, @RequestParam(value="countrycode") String countrycode, HttpServletResponse response) throws HibernateException, IOException, Exception {	
		HotelSearchCity hotelSearchCity = hotelCityDao.getHotelSearchCity(city, countrycode);
		logger.info("hotelSearchCity-----------:"+ hotelSearchCity);
		logger.info("hotelSearchCity----tg ------:"+ hotelSearchCity.getTgCity());		
		logger.info("hotelSearchCity----reznext ------:"+ hotelSearchCity.getReznextCity());		
		logger.info("hotelSearchCity----rezlive -------:"+ hotelSearchCity.getRezliveCity());		
		//45832
		return "success";
	}

	@RequestMapping(value = "/hotelsrezlive",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	HotelFindResponse modify(HttpServletResponse response) throws HibernateException, IOException, Exception {			
		return searchHotels();
	}
	public static HotelFindResponse searchHotels()
	{
		HotelFindResponse response = new HotelFindResponse();
		try {
			File file = new File("C:\\ram\\rezlive\\live\\hotelresponse.xml");		
			Unmarshaller unmarshaller = JAXBContext.newInstance(HotelFindResponse.class).createUnmarshaller();
			response = (HotelFindResponse)unmarshaller.unmarshal(file);

		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}


	@RequestMapping(value = "/prebookrezlive",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	PreBookingResponse prebookhotels(HttpServletResponse response) throws HibernateException, IOException, Exception {			
		return prebookhotels();
	}

	public static HotelFindResponse searchHotelsRezlive()
	{
		HotelFindResponse response = new HotelFindResponse();
		try {
			File file = new File("C:\\ram\\rezlive\\live\\hotelresponse.xml");		
			Unmarshaller unmarshaller = JAXBContext.newInstance(HotelFindResponse.class).createUnmarshaller();
			response = (HotelFindResponse)unmarshaller.unmarshal(file);

		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public static PreBookingResponse prebookhotels()
	{
		PreBookingResponse response = new PreBookingResponse();
		try {
			File file = new File("C:\\ram\\rezlive\\live\\prebook-res.xml");		
			Unmarshaller unmarshaller = JAXBContext.newInstance(PreBookingResponse.class).createUnmarshaller();
			response = (PreBookingResponse)unmarshaller.unmarshal(file);

		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}


	public static List<String> getRoomTypeCodeList(String roomtypecodetext)
	{
		//roomtypecodetext=13456,13457,13458..
		List<String> roomTypeCodeList =new ArrayList<String>();
		if(roomtypecodetext == null || roomtypecodetext.length() == 0)
			return roomTypeCodeList;
		else
		{
			String temp = roomtypecodetext;
			while(temp.length() >= 2)
			{
				if(!temp.contains(","))
				{
					roomTypeCodeList.add(temp);
					temp = "";
					break;
				}
				else
				{
					int roomTypeCodeEndindex = temp.indexOf(",");
					String roomTypeCode = temp.substring(0,roomTypeCodeEndindex-1);	
					temp = temp.substring(roomTypeCodeEndindex+1);	
					roomTypeCodeList.add(roomTypeCode);					
				}
			}

		}
		return roomTypeCodeList;
	}
}
