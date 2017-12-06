package com.tayyarah.hotel.controller;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRQ;
import com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS;
import com.tayyarah.api.hotel.reznext.model.OTAHotelResNotifRQ;
import com.tayyarah.api.hotel.reznext.model.OTAHotelResNotifRS;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.common.util.soap.HttpPostClient;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.hotel.dao.ApiHotelMapStoreDao;
import com.tayyarah.hotel.dao.HotelCityDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.model.HotelSearchCommand;

import com.tayyarah.hotel.model.SuccessType;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelBackUpService;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.HotelSearchHandler;
import com.tayyarah.hotel.util.HotelSearchTest;
import com.tayyarah.hotel.util.TGRequestBuilder;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;
import com.tunyk.currencyconverter.BankUaCom;
import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverter;
import com.tunyk.currencyconverter.api.CurrencyConverterException;

/**
 * @author Intelli
 *
 */
@RestController

@RequestMapping("/SearchByCity")
public class HotelCityController {
	public static final Logger logger = Logger.getLogger(HotelCityController.class);

	@Autowired
	HotelCityDao hotelCityDao;
	@Autowired
	HoteloverviewDao hoteldao;
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	ApiHotelMapStoreDao apihotelstoredao;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	CompanyDao companyDao;

	@RequestMapping(value="/takebackup",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	String takeBackUp(HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		new HotelBackUpService().insertHotelOverview();
		return "success";		
	}	

	@RequestMapping(value = "/convert/{amount}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Float getByVendorId(@PathVariable("amount") Float amount,HttpServletResponse response) {
		// convert EUR to USD
		Float resamount =  amount;
		try {
			CurrencyConverter currencyConverter = new BankUaCom(Currency.USD, Currency.EUR);
			// convert USD to EUR (the first parameter is amount of money you'd like to convert)
			resamount = currencyConverter.convertCurrency(amount);

		} catch (CurrencyConverterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resamount;

	}

	@RequestMapping(value = "/pool/{amount}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Float threadtest(@PathVariable("amount") Float amount,HttpServletResponse response) {
		//ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Config.xml");
		//ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
		ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();		

		/*apiPullExecutor.execute(new DesiyaPullerTask("Thread 1"));
		apiPullExecutor.execute(new DesiyaPullerTask("Thread 2"));
		apiPullExecutor.execute(new DesiyaPullerTask("Thread 3"));
		apiPullExecutor.execute(new DesiyaPullerTask("Thread 4"));*/

		//check active thread, if zero then shut down the thread pool
		for (;;) {
			int count = apiPullExecutor.getActiveCount();
			System.out.println("Active Threads : " + count);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (count == 0) {
				apiPullExecutor.shutdown();
				break;
			}
		}
		return (float) 1;

	}

	@RequestMapping(value = "/SearchhotelRezt/{rezt}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	com.tayyarah.hotel.model.OTAHotelAvailRS getHotelsRezt(@PathVariable("rezt") String rezt,HttpServletResponse response) throws NumberFormatException, Exception {
		ResponseHeader.setResponse(response);//Setting response header
		HotelSearchCommand hs = HotelSearchTest.getHotelSearchCommand();
		// default app_key of the super user
		String appKey="zqJ3R9cGpNWgNXG55ub/WQ==";
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appKey);
		if(appKeyVo==null)
		{
			// TODO Error 
		}
		com.tayyarah.hotel.model.OTAHotelAvailRS tarres = new com.tayyarah.hotel.model.OTAHotelAvailRS();
		logger.info("Searhing hotels controller HotelSearchCommand: "+hs.toString());
		HotelApiCredentials apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);
		logger.info("Searhing hotels controller HotelApiCredentials: "+apiauth.toString());
		StringBuilder reqsoap = null;
		try {
			//reqsoap = HotelRequestBuilder.getHotelSearchBodyDesiya(apiauth, hs);
			//com.travelguru.hotel.OTAHotelAvailRS otaHotelAvailRes = HotelSearchHandler.searchHotelDesia(apiauth, reqsoap);
			HttpPostClient postclient = new HttpPostClient(apiauth);
			StringBuilder res = postclient.sendPost(new StringBuilder(TGRequestBuilder.REZTNEXT_SOAP_CITY_SEARCH), RezNextPullerTask.ACTION_GETHOTELINFOBYCITY);
			logger.info("Searhing hotels desiya result reztnext : "+res);
			SuccessType s = new SuccessType();
			s.setSuccDescription("respose from reztnext-------"+res);
			tarres.setSuccess(s);
		} 
		catch(IOException e)
		{
			SuccessType s = new SuccessType();
			s.setSuccDescription("IOException--"+e.getMessage());
			tarres.setSuccess(s);
			e.printStackTrace();
		}
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			s.setSuccDescription("HibernateException--"+e.getMessage());
			tarres.setSuccess(s);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tarres;
	}
	@RequestMapping(value="/reznexthotels",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelAvailRS getreznexthotels() {
		OTAHotelAvailRS tarres = new OTAHotelAvailRS();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.searchHotelReznextTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			
			tarres.setSuccess("success");
		}
		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			tarres.setSuccess("HibernateException--"+e.getMessage());

		}
		return tarres;

	}

	@RequestMapping(value="/reznexthotelsResponse",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelAvailRS getreznexthotelsrResponse() {
		OTAHotelAvailRS tarres = new OTAHotelAvailRS();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelSearchResponseTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			
			tarres.setSuccess("success");

		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			tarres.setSuccess("HibernateException--"+e.getMessage());

		}
		return tarres;

	}
	@RequestMapping(value="/reznexthotelCancelRequest",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelResNotifRQ getreznexthotelsCancelRequest() {
		OTAHotelResNotifRQ tarres = new OTAHotelResNotifRQ();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelSearchCancellationRequestTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			
			tarres.setResStatus("Success");

		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			tarres.setResStatus("Success");
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("HibernateException--"+e.getMessage());
			tarres.setResStatus("Success");


		}
		return tarres;

	}

	@RequestMapping(value="/reznexthotelCancelResponse",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelResNotifRS  getreznexthotelsCancelResponse() {
		OTAHotelResNotifRS  tarres = new 	OTAHotelResNotifRS ();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelSearchCancellationResponseTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			
			//tarres.setSuccess("Success");

		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			//tarres.setSuccess("Success");
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("HibernateException--"+e.getMessage());
			//tarres.setSuccess("Success");


		}
		return tarres;

	}
	@RequestMapping(value="/reznexthotelBookingResponse",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelResNotifRS  getreznexthotelsBookingResponse() {
		OTAHotelResNotifRS  tarres = new 	OTAHotelResNotifRS ();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelSearchBookingResponseTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			
			//tarres.setSuccess("Success");

		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			//tarres.setSuccess("Success");
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("HibernateException--"+e.getMessage());
			//tarres.setSuccess("Success");


		}
		return tarres;

	}
	@RequestMapping(value="/reznexthotelBookingRequest",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelResNotifRQ  getreznexthotelsBookingRequest() {
		OTAHotelResNotifRQ   tarres = new 	OTAHotelResNotifRQ  ();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelSearchBookingRequestTest();
			logger.info("Searhing hotels  result reztnext : "+tarres);			
			tarres.setResStatus("Success");
		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			tarres.setResStatus("Success");
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("HibernateException--"+e.getMessage());
			tarres.setResStatus("Success");


		}
		return tarres;

	}

	@RequestMapping(value="/reznexthotelCitySearchRequest",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelAvailRQ  getreznexthotelsSearchCityRequest() {
		OTAHotelAvailRQ   tarres = new 		OTAHotelAvailRQ ();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelCitySearchRequestTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			

		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			tarres.setSuccess("Success");
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("HibernateException--"+e.getMessage());
			tarres.setSuccess("Success");


		}
		return tarres;

	}
	@RequestMapping(value="/reznexthotelSearchRequest",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelAvailRQ getreznexthotelsSearchRequest() {
		OTAHotelAvailRQ  tarres = new 		OTAHotelAvailRQ ();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelSearchRequestTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			

		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			tarres.setSuccess("Success");
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("HibernateException--"+e.getMessage());
			tarres.setSuccess("Success");


		}
		return tarres;

	}
	@RequestMapping(value="/reznexthotelSearchResponse",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelAvailRS getreznexthotelsSearchResponse() {
		OTAHotelAvailRS  tarres = new 		OTAHotelAvailRS ();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelSearchResponseTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			

		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			tarres.setSuccess("Success");
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("HibernateException--"+e.getMessage());
			tarres.setSuccess("Success");


		}
		return tarres;

	}
	@RequestMapping(value="/reznexthotelBookingModRequest",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelResNotifRQ getreznexthotelsBookingModRequest() {
		OTAHotelResNotifRQ  tarres = new 	OTAHotelResNotifRQ ();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelBookingModRequestTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			

		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			tarres.setResStatus("Success");
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("HibernateException--"+e.getMessage());
			tarres.setResStatus("Success");


		}
		return tarres;

	}
	@RequestMapping(value="/reznexthotelBookingModResponse",method = RequestMethod.GET,headers="Accept=application/json")
	/*@ResponseStatus(HttpStatus.NO_CONTENT)	*/
	public @ResponseBody
	OTAHotelResNotifRS getreznexthotelsBookingModResponse() {
		OTAHotelResNotifRS  tarres = new 	OTAHotelResNotifRS ();
		StringBuilder reqsoap = null;
		try {
			tarres = HotelSearchHandler.HotelBookingModResponseTest();
			logger.info("Searhing hotels desiya result reztnext : "+tarres);			

		} 

		catch (UnsupportedOperationException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("UnsupportedOperationException--"+e.getMessage());
			//tarres.setSuccess("Success");
			e.printStackTrace();
		} 
		catch (HibernateException e) {
			SuccessType s = new SuccessType();
			//tarres.setSuccess("HibernateException--"+e.getMessage());
			//tarres.setSuccess("Success");


		}
		return tarres;

	}



}
