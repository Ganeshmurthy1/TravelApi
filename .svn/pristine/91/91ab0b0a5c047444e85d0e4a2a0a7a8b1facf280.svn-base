package com.tayyarah.hotel.reznext.test.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

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

import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.hotel.dao.HotelMarkupDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;


@RestController
@RequestMapping("/Reznexthotel/search")
public class ReznextTestSearchController {
	public static final Logger logger = Logger.getLogger(ReznextTestSearchController.class);
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	HotelMarkupDao hotelMarkupDao;	
	@Autowired
	CompanyDao companyDao;	
	@Autowired
	AsyncSupport asyncSupport;	

	@RequestMapping(value="",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody	
	APIHotelMap getRoomStaiesReznext(@RequestParam(value="appkey") String appkey, @RequestParam(value="mode") int mode,@RequestParam(value="type") int type,@RequestParam(value="order") String order,@RequestParam(value="filter") int filter,@RequestParam(value="cachelevel") String cachelevel,@RequestParam(value="currency") String currency,@RequestParam(value="version") BigDecimal version,@RequestParam(value="lang") String lang,@RequestParam(value="city") String city, 
			@RequestParam(value="countrycode") String countrycode,@RequestParam(value="country") String country, @RequestParam(value="datestart") String datestart,@RequestParam(value="dateend") String dateend,@RequestParam(value="noofrooms") int noofrooms, @RequestParam(value="rooms") String rooms,    HttpServletResponse response) throws NumberFormatException, Exception {

		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			// TODO error 
			/*new APIHotelMap(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));*/
		}

		HotelSearchCommand hsc = new HotelSearchCommand(appkey,mode,type,HotelSearchCommand.SORT_ORDER_PRICE ,HotelSearchCommand.FILTER_RATE,HotelSearchCommand.CACHE_LEVEL_LIVE,HotelSearchCommand.CURRENCY_INR,new BigDecimal("1.0"),"en", city, country, country, datestart , dateend, noofrooms, rooms);
		HotelTransactionTemp ht = new HotelTransactionTemp();		
		HotelSearchTemp rezhs = new HotelSearchTemp();

		ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();		
		HotelApiCredentials apirez = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);		
		RezNextPullerTask rezNextPullerTask = new RezNextPullerTask(apirez, hsc, "Reznext Api", RezNextPullerTask.ACTION_GETHOTELINFOBYCITY );
		Future<APIHotelMap> apistack = apiPullExecutor.submit(rezNextPullerTask);	
		try {
			APIHotelMap apimap =apistack.get(); 			
			List<HotelMarkup> markups = hotelMarkupDao.getAllHotelMarkups();						
			apimap.applyMarkUpInitHotelSearch(hsc, markups);			
			byte[] roomStaysMapdata = SerializationUtils.serialize(apimap.getRoomStays());
			rezhs.setHotelres_map(roomStaysMapdata);
			byte[] hscbytes = SerializationUtils.serialize(hsc);
			rezhs.setHotelsearch_cmd(hscbytes);
			rezhs = hotelSearchDao.insertOrUpdateHotelSearch(rezhs);			

			//change the below for api key to string format..
			ht.setApi_key(Long.valueOf(2));
			ht.setSearch_key(rezhs.getSearch_key());			
			ht = hotelTransactionDao.insertApiMapBySearchKey(ht);	

			//apimap.setTransactionKey(ht.getId());
			apimap.setTransactionKey(BigInteger.valueOf(ht.getId()));
			apimap.setSearchKey(BigInteger.valueOf(ht.getSearch_key()));
			apimap.setHotelSearchCommand(hsc);
			apimap.clearUnDeliverables();

			return apimap;
		} catch (HibernateException e) {
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());			
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("HotelSearch----------IOException-:"+ e.getMessage());		
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			logger.info("HotelSearch----------InterruptedException-:"+ e.getMessage());			
			e.printStackTrace();
		}
		catch (ExecutionException  e)
		{
			logger.info("HotelSearch----------ExecutionException-:"+ e.getMessage());			
			e.printStackTrace();
		}
		catch (Exception  e)
		{
			logger.info("HotelSearch----------Exception-:"+ e.getMessage());			
			e.printStackTrace();
		}
		return new APIHotelMap();		
	}
}
