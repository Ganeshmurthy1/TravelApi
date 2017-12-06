package com.tayyarah.hotel.test.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.tayyarah.api.hotel.tbo.model.AuthenticateRequest;
import com.tayyarah.api.hotel.tbo.model.AuthenticateResponse;
import com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.hotel.dao.HotelMarkupDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.entity.Islhotelmapping;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelBasicBookCommand;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelOverview;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.RoomBookingKeyMap;
import com.tayyarah.hotel.reposit.dao.BookingComDAO;
import com.tayyarah.hotel.reposit.dao.HotelRepositDAO;
import com.tayyarah.hotel.reposit.entity.HotelBookingComLink;
import com.tayyarah.hotel.reposit.entity.HotelMealFare;
import com.tayyarah.hotel.reposit.entity.Hotelimage;
import com.tayyarah.hotel.reposit.entity.Hotelinandaround;
import com.tayyarah.hotel.reposit.entity.Hotelmealtype;
import com.tayyarah.hotel.reposit.entity.Hoteloverview;
import com.tayyarah.hotel.reposit.entity.Hotelroomfare;
import com.tayyarah.hotel.reposit.entity.Hotelsegment;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelBookRequestBuilder;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.RegExUtil;
import com.tayyarah.hotel.util.TGRequestBuilder;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.DesiyaPullerTask;

import sun.net.www.protocol.http.HttpURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RestController
@RequestMapping("/hotel/test")
public class HotelTestController {
	@Autowired
	HoteloverviewDao hotelDao;
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	HotelMarkupDao hotelMarkupDao;	
	@Autowired
	CompanyDao companyDao;
	HotelIdFactoryImpl hotelIdFactory;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	HotelRepositDAO  lintashoteldaoImp;
	@Autowired
	BookingComDAO bookingComDAOImp;

	public static final Logger logger = Logger.getLogger(HotelTestController.class);
	@RequestMapping(value = "/prebook",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS getTGRoomReservation(@RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	

		com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS otaHotelResRS = new com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS();
		ResponseHeader.setResponse(response);//Setting response header		
		HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelSearchTemp hs = new HotelSearchTemp();
		HotelTransactionTemp ht = new HotelTransactionTemp();
		HotelSearchCommand hsc = null;
		com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = new RoomStay();
		String app_key = "zqJ3R9cGpNWgNXG55ub/WQ==";
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);
		if(appKeyVo==null)
		{
			// TODO Error 
			//			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		try
		{
			logger.info("HotelPreBookController--- : "+searchkey);			
			hs = hotelSearchDao.getHotelSearch(searchkey);	

			ht = hotelTransactionDao.getHotelTransaction(searchkey);	
			hsc = (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());

			apiHotelMap = (HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay>) SerializationUtils.deserialize(hs.getHotelres_map());

			rs = hotelObjectTransformer.convertTGtoNative(null, hsc, apiHotelMap.get(hotelcode));
			logger.info("HotelPreBookController---- HotelSearchCommand: "+hs.toString());
			HotelApiCredentials apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
			HotelBookCommand hbc = new HotelBookCommand(1);
			StringBuilder reqbook = new StringBuilder("");//HotelBookRequestBuilder.getProvisionalBookingReqPojo(apiauth, ht, hs, hsc, hbc, rs);
			logger.info("HotelPreBookController---- :"+ reqbook);
			otaHotelResRS = DesiyaPullerTask.provisionalBookingHotelDesia("http://stage-api.travelguru.com/services-2.0/tg-services/TGBookingServiceEndPoint", reqbook);
			return otaHotelResRS;
		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			e.printStackTrace();
		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			e.printStackTrace();
		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			e.printStackTrace();
		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS update(@RequestBody HotelBookCommand hbc) {
		String app_key = "zqJ3R9cGpNWgNXG55ub/WQ==";
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);
		if(appKeyVo==null)
		{
			// TODO Error 
			//			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS otaHotelResRS = new com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS();			
		HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelSearchTemp hs = new HotelSearchTemp();
		HotelTransactionTemp ht = new HotelTransactionTemp();
		HotelSearchCommand hsc = null;
		com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = new RoomStay();
		try
		{
			logger.info("HotelPreBookController--- : "+hbc.getSearchKey());			
			hs = hotelSearchDao.getHotelSearch(hbc.getSearchKey());	

			ht = hotelTransactionDao.getHotelTransaction(hbc.getSearchKey());	
			hsc = (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());

			apiHotelMap = (HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay>) SerializationUtils.deserialize(hs.getHotelres_map());

			rs = hotelObjectTransformer.convertTGtoNative(null, hsc, apiHotelMap.get(hbc.getHotelCode()));
			logger.info("HotelPreBookController---- HotelSearchCommand: "+hs.toString());
			HotelApiCredentials apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);

			StringBuilder reqbook = TGRequestBuilder.getProvisionalBookingReq(apiauth, ht, hs, hsc, hbc, rs);
			logger.info("HotelPreBookController---- :"+ reqbook);

			otaHotelResRS = DesiyaPullerTask.provisionalBookingHotelDesia("http://stage-api.travelguru.com/services-2.0/tg-services/TGBookingServiceEndPoint", reqbook);

			return otaHotelResRS;

		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			e.printStackTrace();
		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			e.printStackTrace();
		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			e.printStackTrace();
		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/uidtest",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	HotelBookCommand uidtest(@RequestParam(value="appkey") String appkey,  HttpServletResponse response) throws UnknownHostException {	    	
		for (int i=0; i<100; ++i) {
			//System.err.println(hotelIdFactory.createId());
			logger.info("unique id to be given for hotel orders:----"+ hotelIdFactory.createShortId("h"));
		}

		HotelBookCommand hbc = new HotelBookCommand(1);
		return hbc;
	}


	@RequestMapping(value = "/hbc",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	HotelBookCommand preBookCommand(@RequestParam(value="appkey") String appkey,  HttpServletResponse response) {	
		HotelBookCommand hbc = new HotelBookCommand(1);
		return hbc;
	}
	@RequestMapping(value = "/hbbc",method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody
	HotelBasicBookCommand preBookCommandGenerate(@RequestBody HotelBasicBookCommand hbbc, HttpServletResponse response) {			
		return hbbc;
	}


	@RequestMapping(value = "/hbcpost",method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody
	HotelBasicBookCommand preBookCommandTest(@RequestBody HotelBasicBookCommand hbbc, HttpServletResponse response) {			
		return hbbc;
	}
	@RequestMapping(value = "/initiatecancel",method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody
	StringBuilder initiatecancelTest(@RequestParam(value="appkey") String appkey, HttpServletResponse response) {	
		String app_key = "zqJ3R9cGpNWgNXG55ub/WQ==";
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);
		if(appKeyVo==null)
		{
			// TODO Error 
			//			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);		

		StringBuilder reqbook = new StringBuilder();
		try {
			reqbook = HotelBookRequestBuilder.getInitiateCancelReqBookPojo(apidesiya);
		} catch (NumberFormatException e) {
			reqbook.append("error :"+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			reqbook.append("error :"+e.getMessage());
			e.printStackTrace();
		}
		logger.info("initiatecancel---- :"+ reqbook);
		return reqbook;
	}
	@RequestMapping(value = "/confirmcancel",method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody
	StringBuilder confirmcancelTest(@RequestParam(value="appkey") String appkey, HttpServletResponse response) {
		String app_key = "zqJ3R9cGpNWgNXG55ub/WQ==";
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);
		if(appKeyVo==null)
		{
			// TODO Error 
			//			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);		
		StringBuilder reqbook = new StringBuilder();
		try {
			reqbook = HotelBookRequestBuilder.getConfirmCancelReqBookPojo(apidesiya);
		} catch (NumberFormatException e) {
			reqbook.append("error :"+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			reqbook.append("error :"+e.getMessage());
			e.printStackTrace();
		}
		logger.info("initiatecancel---- :"+ reqbook);
		return reqbook;
	}

	@RequestMapping(value = "/test/{city}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	String getByCity(@PathVariable("city") String searchCityKey,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		logger.info("HotelSearch----------Exception-:");

		return "success";
	}
	@RequestMapping(value = "/markups/{city}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	String markuptest(@PathVariable("city") String searchCityKey,HttpServletResponse response) throws HibernateException, IOException {
		ResponseHeader.setResponse(response);//Setting response header
		logger.info("HotelSearch----------Exception-:");
		List<HotelMarkup> markups = hotelMarkupDao.getAllHotelMarkups();
		logger.info("HotelSearchCommand- no of markups----------:"+ markups.size());

		return "success";
	}

	@RequestMapping(value = "/hotel",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Map<String, HotelOverview> getHotelOverview(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	
		List<String> list = new ArrayList<String>();
		list.add("00028177");
		list.add("00004597");
		list.add("00020813");
		list.add("00003678");
		list.add("00002636");
		list.add("00004114");
		list.add("00003395");
		list.add("00005135");
		Map<String, HotelOverview> map = hotelDao.getHotelOverviewByVendorID(list);
		return map;
	}

	@RequestMapping(value = "/images",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Map<String, List<String>> getHotelimages(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	
		List<String> list = new ArrayList<String>();
		list.add("00028177");
		list.add("00004597");
		list.add("00020813");
		list.add("00003678");
		list.add("00002636");
		list.add("00004114");
		list.add("00003395");
		list.add("00005135");		
		return hotelObjectTransformer.getHotelImages(list);
	}
	@RequestMapping(value = "/hfc",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Map<String, List<Facility>> getHotelfacilities(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	
		List<String> list = new ArrayList<String>();
		list.add("00028177");
		list.add("00004597");
		list.add("00020813");
		list.add("00003678");
		list.add("00002636");
		list.add("00004114");
		list.add("00003395");
		list.add("00005135");		
		return hotelObjectTransformer.getFacilities(list, "property");
	}
	@RequestMapping(value = "/rfc",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Map<String, List<Facility>> getRoomfacilities(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	
		List<String> list = new ArrayList<String>();
		list.add("00028177");
		list.add("00004597");
		list.add("00020813");
		list.add("00003678");
		list.add("00002636");
		list.add("00004114");
		list.add("00003395");
		list.add("00005135");		
		return hotelObjectTransformer.getFacilities(list, "room");
	}
	@RequestMapping(value = "/rooms",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Map<String, Map<Integer, Hotelroomdescription>> getHotelRooms(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	
		List<String> list = new ArrayList<String>();
		list.add("00028177");
		list.add("00004597");
		list.add("00020813");
		list.add("00003678");
		list.add("00002636");
		list.add("00004114");
		list.add("00003395");
		list.add("00005135");		
		return hotelObjectTransformer.getHotelRooms(list);
	}
	@RequestMapping(value = "/samehotels",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	boolean sameHotels(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	
		String hotelname1 = "sofia, malgré tout aimait : la laitue et le choux !";
		String hotelname2 = "malgré tout aimait : la  et le choux !, sofia, laitue";
		boolean isSameName = RegExUtil.isSimilarLines(hotelname1, hotelname2);
		String long1 = "77.598208487";
		String lat1 = "12.9642765088";
		String long2 = "77.59824604";
		String lat2 = "12.96444641";		
		boolean isSamePosition = RegExUtil.isSamePosition(lat1, long1, lat2, long2);
		return (isSameName && isSamePosition);
	}

	@RequestMapping(value = "/testbookingkeymap",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	boolean bookingkeymap(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {

		for(int i= 0; i<10 ; i++)
		{
			RoomBookingKeyMap roomBookingKeyMap1 = HotelIdFactoryImpl.getInstance().createRoomRatePlanCodes("dhfhdfhdsfhdsf-fdsfdsf");			
			RoomBookingKeyMap roomBookingKeyMap2 = HotelIdFactoryImpl.getInstance().createRoomRateBookingKey("RT"+1, "RP"+1);			
		}
		return (true);
	}

	@RequestMapping(value = "/hotelorderid",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	boolean hotelorderid(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {

		for(int i= 0; i<100 ; i++)
		{
			String orderId = HotelIdFactoryImpl.getInstance().createLongId("HO");			
			logger.info(i+"  orderId- "+orderId);		
		}
		return (true);
	}

	@RequestMapping(value = "/facByid/{byId}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<com.tayyarah.hotel.reposit.entity.Facility> getFacilityById(@PathVariable("byId") int id,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<com.tayyarah.hotel.reposit.entity.Facility> facList = null;

		try {			
			facList=lintashoteldaoImp.getFacilityById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return facList;
	}

	@RequestMapping(value = "/imageByid/{byId}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Hotelimage> getHotelimageById(@PathVariable("byId") int id,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<Hotelimage> imageList = null;
		try {			
			imageList=lintashoteldaoImp.getHotelImageById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageList;
	}
	@RequestMapping(value = "/inandaroundByid/{byId}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Hotelinandaround> getHotelinandaroundById(@PathVariable("byId") int id,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<Hotelinandaround> inandaround = null;
		try {			
			inandaround=lintashoteldaoImp.getHotelinandaroundById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inandaround;
	}
	@RequestMapping(value = "/MealFareByid/{byId}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<HotelMealFare> getHotelHotelMealFareById(@PathVariable("byId") int vendorid,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<HotelMealFare> MealFare = null;
		try {			
			MealFare=lintashoteldaoImp.getHotelMealFareByVendorId(vendorid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MealFare;
	}

	@RequestMapping(value = "/mealtypeByid/{byId}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Hotelmealtype> getHotelHotelmealtypeById(@PathVariable("byId") byte mealtypeid,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<Hotelmealtype> mealtype = null;
		try {			
			mealtype=lintashoteldaoImp.getHotelmealtypeByMealId(mealtypeid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mealtype;
	}

	@RequestMapping(value = "/overviewByid/{byId}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Hoteloverview> getHotelHoteloverviewById(@PathVariable("byId") int id,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<Hoteloverview> mealtype = null;
		try {			
			mealtype=lintashoteldaoImp.getHoteloverviewById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mealtype;
	}

	@RequestMapping(value = "/roomdescriptionByid/{byId}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<com.tayyarah.hotel.reposit.entity.Hotelroomdescription> getHotelroomdescriptionById(@PathVariable("byId") int id,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<com.tayyarah.hotel.reposit.entity.Hotelroomdescription> mealtype = null;
		try {			
			mealtype=lintashoteldaoImp.getHotelroomdescriptionById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mealtype;
	}

	@RequestMapping(value = "/segmentByid/{byId}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Hotelsegment> getHotelsegmentById(@PathVariable("byId") int segmentId,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<Hotelsegment> mealtype = null;
		try {			
			mealtype=lintashoteldaoImp.getHotelsegmentById(segmentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mealtype;
	}

	@RequestMapping(value = "/roomfareByid/{byId}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Hotelroomfare> getHotelroomfareById(@PathVariable("byId") int fareid,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<Hotelroomfare> mealtype = null;
		try {			
			mealtype=lintashoteldaoImp.getHotelroomfare(fareid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mealtype;
	}

	@RequestMapping(value = "/booking_com",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Boolean getRoomAvailablity(@RequestParam(value="vendorName") String vendorName, @RequestParam(value="room") String room, HttpServletResponse response) {	

		String title ="";
		Document doc;
		HotelBookingComLink hotelBookingComLink = null;
		try
		{
			//doc = Jsoup.connect("http://www.booking.com/hotel/me/apart-kalamper.en-gb.html?label=gen173nr-15CAsojwFCFHJlc29ydC1ydXphLXZqZXRyb3ZhSCRiBW5vcmVmaIMCiAEBmAEkuAEEyAEE2AED6AEB-AEC;sid=d8d0d6e85b0d25cb2029d96cee86b978;dcid=4;checkin=2016-12-15;checkout=2016-12-22;dist=0;group_adults=2;room1=A%2CA;sb_price_type=total;srfid=e64cbfe12dbe4abf214656107ca0024994793b72X3;type=total;ucfs=1&#rt-lightbox-open").get();

			hotelBookingComLink = bookingComDAOImp.getHotelBookingComLink(vendorName);
			logger.info("hotelBookingComLink--- : "+hotelBookingComLink);

			if(hotelBookingComLink == null || hotelBookingComLink.getBookingLink() != null || !(hotelBookingComLink.getBookingLink().contains("http")))
				return false;
			logger.info("hotelBookingComLink.getBookingLink()--- : "+hotelBookingComLink.getBookingLink());	        	 
			doc = Jsoup.connect(hotelBookingComLink.getBookingLink()).get();
			title = doc.title();
			Elements divs = doc.getElementsByClass("rtshown");
			Element div = divs.get(0);
			Element table = doc.getElementById("maxotel_rooms");	       

			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				for (Element column : tds) {					
					Elements links = column.select("a[href]"); 
					if(links != null && links.size()>=1)                        	 
					{
						if(RegExUtil.isSimilarLines(links.get(0).text(), room))
						{
							logger.info("given room is available is booking.com: "+room);	
							return true;
						}

					}
				}
			}			
		}
		catch(Exception e)
		{

			logger.info("Exception: "+e.getMessage());	
			return false;
		}
		return false;
	}


	@RequestMapping(value = "/tbo",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	StringBuilder tbotest(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) throws IOException, ProtocolException {

		/*{ "ClientId": "ApiIntegration", "UserName": "intelli", "Password": "intelli@123", "EndUserIp": "192.168.11.120" } */
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		AuthenticateResponse authenticateResponse = new AuthenticateResponse();
		AuthenticateRequest authenticateRequest = new AuthenticateRequest();
		authenticateRequest.setClientId("ApiIntegration");
		authenticateRequest.setEndUserIp("192.168.11.120");
		authenticateRequest.setUserName("intelli");
		authenticateRequest.setPassword("intelli@123");
		String authenticateResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateRequest);
		logger.info("-------------(((((--authenticateRequest:"+authenticateResponseInString);	

		byte[] b = authenticateResponseInString.getBytes("UTF-8");

		URL obj = new URL("http://api.tektravels.com/SharedServices/SharedData.svc/rest/Authenticate");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Encoding", "gzip");
		con.setRequestProperty("content-type", "application/json");			
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(b);//writeBytes(authenticateResponseInString);
		wr.flush();
		wr.close();
		@SuppressWarnings("unused")
		int responseCode = con.getResponseCode();
		logger.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder responsetext = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			responsetext.append(inputLine);
		}
		in.close();		
		logger.info(responsetext.toString());
		return responsetext;
	}

	@RequestMapping(value = "/tborest",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Boolean tbotestrest(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) throws IOException, ProtocolException {
		AuthenticateResponse authenticateResponse = new AuthenticateResponse();
		AuthenticateRequest authenticateRequest = new AuthenticateRequest();
		authenticateRequest.setClientId("ApiIntegration");
		authenticateRequest.setEndUserIp("192.168.11.120");
		authenticateRequest.setUserName("intelli");
		authenticateRequest.setPassword("intelli@123");
		ObjectMapper mapper = new ObjectMapper();
		try {

			String authenticateResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateRequest);
			logger.info("-------------(((((--authenticateRequest:"+authenticateResponseInString);			

			RestTemplate restTemplate = new RestTemplate();
			// Set the request factory. 
			// IMPORTANT: This section I had to add for POST request. Not needed for GET
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

			// Add converters
			// Note I use the Jackson Converter, I removed the http form converter 
			// because it is not needed when posting String, used for multipart forms.
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


			MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
			headers.add("Accept", "application/json");
			headers.add("Content-Type", "application/json");
			headers.add("Accept-Encoding", "gzip");

			HttpEntity request = new HttpEntity(authenticateResponseInString, headers);

			//RestTemplate restTemplate = new RestTemplate();
			authenticateResponse = restTemplate.postForObject("http://api.tektravels.com/SharedServices/SharedData.svc/rest/Authenticate", request, AuthenticateResponse.class);
			authenticateResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateResponse);
			logger.info("-------------(((((authenticateResponse  : "+authenticateResponseInString);			


		} catch (JsonGenerationException e) {
			logger.info("-------------(((((  hotel : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------(((((  hotel : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------(((((  hotel : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------(((((  hotel : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(value = "/tborest2",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Boolean tbotestrest2(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) throws IOException, ProtocolException {

		AuthenticateResponse authenticateResponse = new AuthenticateResponse();
		AuthenticateRequest authenticateRequest = new AuthenticateRequest();
		authenticateRequest.setClientId("ApiIntegration");
		authenticateRequest.setEndUserIp("192.168.11.120");
		authenticateRequest.setUserName("intelli");
		authenticateRequest.setPassword("intelli@123");
		ObjectMapper mapper = new ObjectMapper();		
		try {

			RestTemplate restTemplate = new RestTemplate();		
			String authenticateResponseInString1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateRequest);
			logger.info("-------------(((((--authenticateRequest pretty :"+authenticateResponseInString1);			

			String url = "http://api.tektravels.com/SharedServices/SharedData.svc/rest/Authenticate";
			//String requestJson = "{ \"ClientId\": \"ApiIntegration\", \"UserName\": \"intelli\", \"Password\": \"intelli@123\", \"EndUserIp\": \"192.168.11.120\" }";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(authenticateResponseInString1,headers);
			String answer = restTemplate.postForObject(url, entity, String.class);
			authenticateResponse = mapper.readValue(answer, AuthenticateResponse.class);
			logger.info("-------------(((((--authenticateResponse  :"+authenticateResponse);			

			authenticateResponseInString1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateResponse);
			logger.info("-------------(((((--authenticateResponse pretty :"+authenticateResponseInString1);			

		}
		catch (Exception e) {
			logger.info("-------------(((((  hotel : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(value = "/daystest",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	int daystest(@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) throws IOException, ProtocolException, ParseException {
		return CommonUtil.getNoofStayDays();
	}
}