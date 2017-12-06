package com.tayyarah.hotel.util.api.concurrency;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.ProtocolException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.api.hotel.rezlive.model.HotelFindResponse;
import com.tayyarah.api.hotel.reznext.model.RoomInfoType;
import com.tayyarah.api.hotel.tbo.block.model.HotelBlockRequest;
import com.tayyarah.api.hotel.tbo.book.model.HotelBookRequest;
import com.tayyarah.api.hotel.tbo.book.model.HotelBookResponse;
import com.tayyarah.api.hotel.tbo.cancel.model.GetChangeRequestStatus;
import com.tayyarah.api.hotel.tbo.cancel.model.GetChangeRequestStatusResponse;
import com.tayyarah.api.hotel.tbo.cancel.model.SendChangeRequest;
import com.tayyarah.api.hotel.tbo.cancel.model.SendChangeResponse;
import com.tayyarah.api.hotel.tbo.model.AuthenticateRequest;
import com.tayyarah.api.hotel.tbo.model.AuthenticateResponse;
import com.tayyarah.api.hotel.tbo.model.HotelBlockResponse;
import com.tayyarah.api.hotel.tbo.model.HotelIRoomInfoResponse;
import com.tayyarah.api.hotel.tbo.model.HotelInfoRequest;
import com.tayyarah.api.hotel.tbo.model.HotelInfoResponse;
import com.tayyarah.api.hotel.tbo.model.HotelResult;
import com.tayyarah.api.hotel.tbo.model.HotelSearchRequest;
import com.tayyarah.api.hotel.tbo.model.HotelSearchResponse;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRowCancellation;
import com.tayyarah.hotel.entity.HotelOrderRowCancellationAPIResponse;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelCancelRequest;
import com.tayyarah.hotel.model.APIHotelCancelResponse;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelsInfo;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.TBORequestBuilder;
import com.tayyarah.hotel.util.TBOResponseParser;


public class TBOPullerTask implements Callable<APIHotelMap>{
	public TBOPullerTask(HotelSearchTemp hotelsearch, HotelObjectTransformer hotelObjectTransformer, HoteloverviewDao hoteloverviewDao,
			HotelroomdescriptionDao hotelroomdescriptionDao, HotelimagesDao hotelimagesDao, HotelFacilityDao hotelFacilityDao,
			IslhotelmappingDao islhotelmappingDao, HotelinandaroundDao hotelinandaroundDao,
			HotelsecondaryareaDao hotelsecondaryareaDao, HotelApiCredentials api, HotelSearchCommand hs, String name, HotelSearchCity city) {
		super();
		this.hotelsearch = hotelsearch;
		this.hotelObjectTransformer = hotelObjectTransformer;
		this.hoteloverviewDao = hoteloverviewDao;
		this.hotelroomdescriptionDao = hotelroomdescriptionDao;
		this.hotelimagesDao = hotelimagesDao;
		this.hotelFacilityDao = hotelFacilityDao;
		this.islhotelmappingDao = islhotelmappingDao;
		this.hotelinandaroundDao = hotelinandaroundDao;
		this.hotelsecondaryareaDao = hotelsecondaryareaDao;
		this.hotelApiCredentials = api;
		this.hs = hs;
		this.name = name;
		this.city = city;
		this.requestBuilder = new TBORequestBuilder(city);
		this.responseParser = new TBOResponseParser();
		this.apiHotelMap = new APIHotelMap();
		this.mapper = new ObjectMapper();
	}

	public TBOPullerTask(HotelSearchTemp hotelsearch, HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();
		this.hotelsearch = hotelsearch;
		this.hotelApiCredentials = api;
		this.hs = hs;
		this.name = name;
		this.requestBuilder = new TBORequestBuilder(hs.getSearchCity());
		this.responseParser = new TBOResponseParser();		
		this.apiHotelMap = new APIHotelMap();
		this.mapper = new ObjectMapper();
	}


	public TBOPullerTask(HotelApiCredentials api, String name) {
		super();		
		this.hotelApiCredentials = api;		
		this.name = name;
		this.requestBuilder = new TBORequestBuilder(null);
		this.responseParser = new TBOResponseParser();		
		this.apiHotelMap = new APIHotelMap();
		this.mapper = new ObjectMapper();
	}

	public HotelObjectTransformer getHotelObjectTransformer() {
		return hotelObjectTransformer;
	}
	public void setHotelObjectTransformer(HotelObjectTransformer hotelObjectTransformer) {
		this.hotelObjectTransformer = hotelObjectTransformer;
	}
	public HoteloverviewDao getHoteloverviewDao() {
		return hoteloverviewDao;
	}
	public void setHoteloverviewDao(HoteloverviewDao hoteloverviewDao) {
		this.hoteloverviewDao = hoteloverviewDao;
	}
	public HotelroomdescriptionDao getHotelroomdescriptionDao() {
		return hotelroomdescriptionDao;
	}
	public void setHotelroomdescriptionDao(HotelroomdescriptionDao hotelroomdescriptionDao) {
		this.hotelroomdescriptionDao = hotelroomdescriptionDao;
	}
	public HotelimagesDao getHotelimagesDao() {
		return hotelimagesDao;
	}
	public void setHotelimagesDao(HotelimagesDao hotelimagesDao) {
		this.hotelimagesDao = hotelimagesDao;
	}
	public HotelFacilityDao getFacilityDao() {
		return hotelFacilityDao;
	}
	public void setFacilityDao(HotelFacilityDao hotelFacilityDao) {
		this.hotelFacilityDao = hotelFacilityDao;
	}
	public IslhotelmappingDao getIslhotelmappingDao() {
		return islhotelmappingDao;
	}
	public void setIslhotelmappingDao(IslhotelmappingDao islhotelmappingDao) {
		this.islhotelmappingDao = islhotelmappingDao;
	}
	public HotelinandaroundDao getHotelinandaroundDao() {
		return hotelinandaroundDao;
	}
	public void setHotelinandaroundDao(HotelinandaroundDao hotelinandaroundDao) {
		this.hotelinandaroundDao = hotelinandaroundDao;
	}
	public HotelsecondaryareaDao getHotelsecondaryareaDao() {
		return hotelsecondaryareaDao;
	}
	public void setHotelsecondaryareaDao(HotelsecondaryareaDao hotelsecondaryareaDao) {
		this.hotelsecondaryareaDao = hotelsecondaryareaDao;
	}
	public HotelApiCredentials getApi() {
		return hotelApiCredentials;
	}
	public void setApi(HotelApiCredentials api) {
		this.hotelApiCredentials = api;
	}
	public HotelSearchCommand getHs() {
		return hs;
	}
	public void setHs(HotelSearchCommand hs) {
		this.hs = hs;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HotelFindResponse getHotelFindResponse() {
		return hotelFindResponse;
	}
	public void setHotelFindResponse(HotelFindResponse hotelFindResponse) {
		this.hotelFindResponse = hotelFindResponse;
	}
	public APIHotelMap getApiHotelMap() {
		return apiHotelMap;
	}
	public void setApiHotelMap(APIHotelMap apiHotelMap) {
		this.apiHotelMap = apiHotelMap;
	}
	public HotelBookCommand getHb() {
		return hb;
	}
	public void setHb(HotelBookCommand hb) {
		this.hb = hb;
	}
	public String getHotelCode() {
		return hotelCode;
	}
	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	public RoomInfoType getRoomInfoType() {
		return roomInfoType;
	}
	public void setRoomInfoType(RoomInfoType roomInfoType) {
		this.roomInfoType = roomInfoType;
	}
	public String getActionname() {
		return actionname;
	}
	public void setActionname(String actionname) {
		this.actionname = actionname;
	}
	public static final Logger logger = Logger.getLogger(TBOPullerTask.class);


	public static Integer HOTEL_ID_MIN = 200000;
	private HotelSearchTemp hotelsearch;
	public HotelSearchTemp getHotelsearch() {
		return hotelsearch;
	}

	public void setHotelsearch(HotelSearchTemp hotelsearch) {
		this.hotelsearch = hotelsearch;
	}
	private HotelObjectTransformer hotelObjectTransformer;	
	private HoteloverviewDao hoteloverviewDao;	
	private HotelroomdescriptionDao hotelroomdescriptionDao;	
	private HotelimagesDao hotelimagesDao;	
	private HotelFacilityDao hotelFacilityDao;
	private IslhotelmappingDao islhotelmappingDao;
	private HotelinandaroundDao hotelinandaroundDao;
	private HotelsecondaryareaDao hotelsecondaryareaDao;
	private HotelApiCredentials hotelApiCredentials;	
	private HotelSearchCommand hs;
	private String name;
	private HotelFindResponse hotelFindResponse;
	private APIHotelMap apiHotelMap;
	private HotelBookCommand hb;
	private String hotelCode;
	private RoomInfoType roomInfoType;	
	private String actionname;
	private TBORequestBuilder requestBuilder = new TBORequestBuilder(null);
	private TBOResponseParser responseParser = new TBOResponseParser();
	private ObjectMapper mapper = null;
	private HotelSearchCity city;
	public HotelSearchCity getCity() {
		return city;
	}

	public void setCity(HotelSearchCity city) {
		this.city = city;
	}
	//TBO Testing
	/*public static final String URL_AUTHENTICATE = "http://api.tektravels.com/SharedServices/SharedData.svc/rest/Authenticate";	
	public static final String URL_SEARCH_HOTELS = "http://api.tektravels.com/BookingEngineService_Hotel/hotelservice.svc/rest/GetHotelResult/";
	public static final String URL_SEARCH_HOTELINFO = "http://api.tektravels.com/BookingEngineService_Hotel/hotelservice.svc/rest/GetHotelInfo/";
	public static final String URL_SEARCH_ROOMS = "http://api.tektravels.com/BookingEngineService_Hotel/hotelservice.svc/rest/GetHotelRoom/";
	public static final String URL_BLOCK_ROOMS = "http://api.tektravels.com/BookingEngineService_Hotel/hotelservice.svc/rest/BlockRoom/";	
	public static final String URL_BOOKING = "http://api.tektravels.com/BookingEngineService_Hotel/hotelservice.svc/rest/Book/";
	public static final String URL_BOOKING_SUMMARY = "http://128.199.96.87:8080/TayyarahAPI/hotel/book/summary";

	public static final String API_CURRENCY = "INR";
	public static final String AGENCY_NAME = "LintasAPIConsumer";
	 */
	//http://tboapi.travelboutiqueonline.com/SharedAPI/SharedData.svc
	//TBO live
	/*public static final String URL_AUTHENTICATE = "http://tboapi.travelboutiqueonline.com/SharedAPI/SharedData.svc/rest/Authenticate";	
	public static final String URL_SEARCH_HOTELS = "http://tboapi.travelboutiqueonline.com/HotelAPI_V10/HotelService.svc/rest/GetHotelResult/";
	public static final String URL_SEARCH_HOTELINFO = "http://tboapi.travelboutiqueonline.com/HotelAPI_V10/HotelService.svc/rest/GetHotelInfo/";
	public static final String URL_SEARCH_ROOMS = "http://tboapi.travelboutiqueonline.com/HotelAPI_V10/HotelService.svc/rest/GetHotelRoom/";
	public static final String URL_BLOCK_ROOMS = "http://tboapi.travelboutiqueonline.com/HotelAPI_V10/HotelService.svc/rest/BlockRoom/";	
	public static final String URL_BOOKING = "http://tboapi.travelboutiqueonline.com/HotelAPI_V10/HotelService.svc/rest/Book/";
	public static final String URL_BOOKING_SUMMARY = "http://tboapi.travelboutiqueonline.com/HotelAPI_V10/HotelService.svc";

	public static final String API_CURRENCY = "INR";
	public static final String AGENCY_NAME = "LintasAPIConsumer";
	 */

	/*	private  String URL_AUTHENTICATE = "SharedServices/SharedData.svc/rest/Authenticate";	
	private  String URL_SEARCH_HOTELS = "BookingEngineService_Hotel/hotelservice.svc/rest/GetHotelResult/";
	private  String URL_SEARCH_HOTELINFO = "BookingEngineService_Hotel/hotelservice.svc/rest/GetHotelInfo/";
	private  String URL_SEARCH_ROOMS = "BookingEngineService_Hotel/hotelservice.svc/rest/GetHotelRoom/";
	private  String URL_BLOCK_ROOMS = "BookingEngineService_Hotel/hotelservice.svc/rest/BlockRoom/";	
	private  String URL_BOOKING = "BookingEngineService_Hotel/hotelservice.svc/rest/Book/";
	private  String URL_CANCEL = "BookingEngineService_Hotel/hotelservice.svc/rest/SendChangeRequest/";
	private  String URL_CANCEL_STATUS = "BookingEngineService_Hotel/hotelservice.svc/rest/GetChangeRequestStatus/";
	//public static final String URL_BOOKING_SUMMARY = "http://tboapi.travelboutiqueonline.com/HotelAPI_V10/HotelService.svc";
	 */
	private boolean isTest;
	public static final String API_CURRENCY = "INR";
	public static final String AGENCY_NAME = "LintasAPIConsumer";

	public static boolean isAuthencated;

	public boolean authenticate(String endUserIp, String searchKey)
			throws Exception {		
		AuthenticateResponse authenticateResponse = new AuthenticateResponse();
		AuthenticateRequest authenticateRequest = new AuthenticateRequest();
		authenticateRequest.setClientId(String.valueOf(hotelApiCredentials.getTboHotelConfig().getClientId()));
		authenticateRequest.setEndUserIp(endUserIp);
		authenticateRequest.setUserName(hotelApiCredentials.getTboHotelConfig().getUserName());
		authenticateRequest.setPassword(hotelApiCredentials.getTboHotelConfig().getPassword());
		isTest = this.hotelApiCredentials.isTesting();
		boolean isauthenticated = false;

		try {

			RestTemplate restTemplate = new RestTemplate();	
			FileUtil.writeJson("hotel", "tbo", "auth", false, authenticateRequest, searchKey);
			String authenticateResponseInString1 = this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateRequest);

			logger.info("-------------(((((--authenticateResponseInString1  :"+authenticateResponseInString1);	
			//String requestJson = "{ \"ClientId\": \"ApiIntegration\", \"UserName\": \"intelli\", \"Password\": \"intelli@123\", \"EndUserIp\": \"192.168.11.120\" }";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(authenticateResponseInString1,headers);
			logger.info("-------------((((("+name+"--url:"+hotelApiCredentials.getTboHotelConfig().getAuthUrlHotel());			
			String answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getAuthUrlHotel(), entity, String.class);			

			authenticateResponse = this.mapper.readValue(answer, AuthenticateResponse.class);
			logger.info("-------------(((((--authenticateResponse  :"+authenticateResponse);			

			FileUtil.writeJson("hotel", "tbo", "auth", true, authenticateResponse, searchKey);


			authenticateResponseInString1 = this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateResponse);
			logger.info("-------------(((((--authenticateResponse pretty :"+authenticateResponseInString1);			
			this.hotelApiCredentials.setTokenId(authenticateResponse.getTokenId());

			isauthenticated = true;
			isAuthencated = true;

		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" authenticate hotel : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
			isauthenticated = false;
			isAuthencated = false;
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" authenticate hotel : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
			isauthenticated = false;
			isAuthencated = false;
		} catch (IOException e) {
			logger.info("-------------((((("+name+" authenticate hotel : IOException--"+e.getMessage());
			e.printStackTrace();
			isauthenticated = false;
			isAuthencated = false;
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" authenticate hotel : Exception--"+e.getMessage());
			e.printStackTrace();
			isauthenticated = false;
			isAuthencated = false;
		}	
		finally
		{
			return isauthenticated;
		}
	}

	public HotelSearchResponse searchHotels()
			throws Exception {		
		HotelSearchResponse hotelSearchResponse = new HotelSearchResponse();
		HotelSearchRequest hotelSearchRequest = new HotelSearchRequest();
		isTest = this.hotelApiCredentials.isTesting();
		try {

			hotelSearchRequest = this.requestBuilder.getHotelSearchReq(this.hotelApiCredentials, this.getHs());
			String hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelSearchRequest);
			logger.info("-------------((((("+name+"--hotelSearchRequest:"+hotelSearchRequestInString);			


			String filePrefix = String.valueOf(hotelsearch.getSearch_key()) + ((city!=null && city.getId()!=null)?"-"+city.getId():"");
			FileUtil.writeJson("hotel", "tbo", "search", false, hotelSearchRequest, filePrefix);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(hotelSearchRequestInString,headers);
			logger.info("-------------((((("+name+"--req url:"+hotelApiCredentials.getTboHotelConfig().getHotelUrlSearchHotel());		

			String answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlSearchHotel(), entity, String.class);
			//logger.info("-------------((((("+name+"--response:"+answer);		
			//System.out.println(answer);	
			hotelSearchResponse = this.mapper.readValue(answer, HotelSearchResponse.class);
			//logger.info("-------------(((((--hotelSearchResponse  :"+hotelSearchResponse);			
			FileUtil.writeJson("hotel", "tbo", "search", true, hotelSearchResponse, filePrefix);

			hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelSearchResponse);
			//logger.info("-------------((((("+name+"hotelSearchResponse  : "+hotelSearchRequestInString);			
			//apiHotelBook = this.responseParser.convertRezLivetoNativePreBookResponse(apiHotelBook, apiHotelBookResponse);
			//FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBookResponse  native 3:"+FlightBookingResponseInString);

			return hotelSearchResponse;

		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" search hotels : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+"  search hotels  : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+"  search hotels  : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+"  search hotels  : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return null;

	}
	public RoomStay searchHotelInfo(OTAHotelAvailRS.RoomStays.RoomStay rs)
			throws Exception {
		HotelInfoRequest hotelInfoRequest = new HotelInfoRequest();
		hotelInfoRequest.setResultIndex(rs.getBasicPropertyInfo().getApiResultIndex());
		hotelInfoRequest.setHotelCode(rs.getBasicPropertyInfo().getApiVendorID());
		hotelInfoRequest.setEndUserIp(rs.getBasicPropertyInfo().getApiEndUserIp());
		hotelInfoRequest.setTokenId(rs.getBasicPropertyInfo().getApiTokenId());
		hotelInfoRequest.setTraceId(rs.getBasicPropertyInfo().getApiTraceId());		
		HotelInfoResponse hotelInfoResponse = new HotelInfoResponse();	
		isTest = this.hotelApiCredentials.isTesting();
		try {			
			String hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelInfoRequest);
			//logger.info("-------------((((("+name+"--hotelInfoRequest:"+hotelSearchRequestInString);			

			String filePrefix = String.valueOf(hotelsearch.getSearch_key()) + ((city!=null && city.getId()!=null)?"-"+city.getId():"");
			FileUtil.writeJson("hotel", "tbo", "search-hotel", false, hotelInfoRequest, filePrefix);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(hotelSearchRequestInString,headers);
			String answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlSearchHotelInfo(), entity, String.class);
			//logger.info("-------------((((("+name+"--response:"+answer);		
			//System.out.println(answer);	
			hotelInfoResponse = this.mapper.readValue(answer, HotelInfoResponse.class);
			//logger.info("-------------(((((--hotelInfoResponse  :"+hotelInfoResponse);			
			FileUtil.writeJson("hotel", "tbo", "search-hotel", true, hotelInfoResponse, filePrefix);

			hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelInfoResponse);
			//logger.info("-------------((((("+name+"hotelSearchResponse  : "+hotelSearchRequestInString);			
			//apiHotelBook = this.responseParser.convertRezLivetoNativePreBookResponse(apiHotelBook, apiHotelBookResponse);
			//FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBookResponse  native 3:"+FlightBookingResponseInString);

			rs = responseParser.convertTBOToNativeHotelInfo(this.hotelApiCredentials, this.hs, rs, hotelInfoResponse);



		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" Searhing hotel info : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" Searhing hotel info : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Searhing hotel info : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" Searhing hotel info : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return rs;
	}


	public com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay searchHotelRooms(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
			throws Exception {		
		HotelInfoRequest hotelInfoRequest = new HotelInfoRequest();
		hotelInfoRequest.setResultIndex(rs.getBasicPropertyInfo().getApiResultIndex());
		hotelInfoRequest.setHotelCode(rs.getBasicPropertyInfo().getApiVendorID());
		hotelInfoRequest.setEndUserIp(rs.getBasicPropertyInfo().getApiEndUserIp());
		hotelInfoRequest.setTokenId(rs.getBasicPropertyInfo().getApiTokenId());
		hotelInfoRequest.setTraceId(rs.getBasicPropertyInfo().getApiTraceId());		
		HotelIRoomInfoResponse hotelIRoomInfoResponse = new HotelIRoomInfoResponse();
		isTest = this.hotelApiCredentials.isTesting();
		try {

			String roomstaytext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rs);
			//logger.info("-------------((((("+name+"--roomstaytext:"+roomstaytext);			


			String hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelInfoRequest);
			//logger.info("-------------((((("+name+"--hotelRoomInfoRequest:"+hotelSearchRequestInString);			

			FileUtil.writeJson("hotel", "tbo", "search-room", false, hotelInfoRequest, String.valueOf(hotelsearch.getSearch_key()));


			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(hotelSearchRequestInString,headers);
			String answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlSearchRooms(), entity, String.class);
			//logger.info("-------------((((("+name+"--response:"+answer);		
			//System.out.println(answer);	
			hotelIRoomInfoResponse = this.mapper.readValue(answer, HotelIRoomInfoResponse.class);
			//logger.info("-------------(((((--hotelIRoomInfoResponse  :"+hotelIRoomInfoResponse);			
			FileUtil.writeJson("hotel", "tbo", "search-room", true, hotelIRoomInfoResponse, String.valueOf(hotelsearch.getSearch_key()));

			hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelIRoomInfoResponse);
			//logger.info("-------------((((("+name+"hotelIRoomInfoResponse  : "+hotelSearchRequestInString);			
			//apiHotelBook = this.responseParser.convertRezLivetoNativePreBookResponse(apiHotelBook, apiHotelBookResponse);
			//FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBookResponse  native 3:"+FlightBookingResponseInString);

			rs = responseParser.convertTBOToNativeHotelRoomInfo(this.hotelApiCredentials, this.hs, rs, hotelIRoomInfoResponse);

			rs = searchHotelInfo(rs);


		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" Searhing hotel info : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" Searhing hotel info : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Searhing hotel info : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" Searhing hotel info : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return rs;
	}



	public APIHotelBook preBook(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory,CompanyDao CompanyDao) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		APIHotelBook apiHotelBookResponse = new APIHotelBook();
		HotelBlockRequest hotelBlockRequest = requestBuilder.getHotelBlockRequest(hotelApiCredentials, hs, hbc, roomStay);			
		HotelBlockResponse hotelBlockResponse = new HotelBlockResponse();	
		isTest = this.hotelApiCredentials.isTesting();
		try {

			Company company = null;
			AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(CompanyDao, hbc.getAppkey());//added by basha

			//String companyId = hbc.getAppkey().substring(hbc.getAppkey().indexOf("-") + 1);//commented by basha
			try{
				//company = CompanyDao.getCompany(Integer.parseInt(companyId));//commented by basha
				company = CompanyDao.getCompany(appKeyVo.getCompanyId());
			}catch(Exception e){
				e.printStackTrace();
			}
			Company parentCompany = null;
			try{
				parentCompany = CompanyDao.getParentCompany(company);
			}catch(Exception e){
				e.printStackTrace();
			}

			String hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelBlockRequest);
			//logger.info("-------------((((("+name+"--hotelBlockRequest:"+hotelSearchRequestInString);			

			FileUtil.writeJson("hotel", "tbo", "prebook", false, hotelBlockRequest, String.valueOf(hbc.getSearchKey()));


			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(hotelSearchRequestInString,headers);
			String answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlBlockRooms(), entity, String.class);
			//logger.info("-------------((((("+name+"--response:"+answer);		
			System.out.println(answer);	
			hotelBlockResponse = this.mapper.readValue(answer, HotelBlockResponse.class);
			FileUtil.writeJson("hotel", "tbo", "prebook", true, hotelBlockResponse, String.valueOf(hbc.getSearchKey()));



			/*//F:\logs\tayyarah\hotel-tbo-50-prebook-response.json
			File file = new File("F:\\logs\\tayyarah\\hotel\\tbo\\878-prebook-response.json.txt");			
			byte[] jsonData = Files.readAllBytes(Paths.get("F:\\logs\\tayyarah\\hotel\\tbo\\878-prebook-response.json.txt"));
			hotelBlockResponse = this.mapper.readValue(jsonData, HotelBlockResponse.class);
			 */

			//logger.info("-------------(((((--hotelBlockResponse obj :"+hotelBlockResponse);			

			hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelBlockResponse);
			//logger.info("-------------((((("+name+"hotelBlockResponse  text: "+hotelSearchRequestInString);			
			//apiHotelBook = this.responseParser.convertRezLivetoNativePreBookResponse(apiHotelBook, apiHotelBookResponse);
			//FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBookResponse  native 3:"+FlightBookingResponseInString);

			apiHotelBook = this.responseParser.convertTbotoNativePreBookResponse(apiHotelBook, hbc, roomStay, hotelIdFactory, hotelBlockResponse,company,parentCompany);
			hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBookResponse  native 3:"+hotelSearchRequestInString);


		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" prebook hotel : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" prebook hotel : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" prebook hotel : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" prebook hotel : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return apiHotelBook;
	}

	public APIHotelBook doBook(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory,CompanyDao CompanyDao) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		APIHotelBook apiHotelBookResponse = new APIHotelBook();
		HotelBookRequest hotelBookRequest = requestBuilder.getHotelBookRequest(hotelApiCredentials, apiHotelBook.getSearch(), apiHotelBook.getBook(), roomStay);
		HotelBookResponse hotelBookResponse = new HotelBookResponse();	
		isTest = this.hotelApiCredentials.isTesting();
		try {

			Company company = null;
			AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(CompanyDao, hbc.getAppkey());//added by basha
			//String companyId = hbc.getAppkey().substring(hbc.getAppkey().indexOf("-") + 1);
			System.out.println("APIHotelBook-----doBook--"+appKeyVo.getCompanyId());
			try{
				company = CompanyDao.getCompany(appKeyVo.getCompanyId());
			}catch(Exception e){
				e.printStackTrace();
			}
			Company parentCompany = null;
			try{
				parentCompany = CompanyDao.getParentCompany(company);
			}catch(Exception e){
				e.printStackTrace();
			}

			String hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelBookRequest);
			//logger.info("-------------((((("+name+"--hotelBookRequest:"+hotelSearchRequestInString);
			FileUtil.writeJson("hotel", "tbo", "book", false, hotelBookRequest, String.valueOf(hbc.getSearchKey()));

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(hotelSearchRequestInString,headers);
			String answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlBooking(), entity, String.class);
			//logger.info("-------------((((("+name+"--response:"+answer);		
			//System.out.println(answer);	
			hotelBookResponse = this.mapper.readValue(answer, HotelBookResponse.class);
			//logger.info("-------------(((((--hotelBookResponse obj :"+hotelBookResponse);			
			FileUtil.writeJson("hotel", "tbo", "book", true, hotelBookResponse, String.valueOf(hbc.getSearchKey()));

			//File file = new File("F:\\logs\\tayyarah\\hotel-tbo-50-prebook-response.json");			
			//byte[] jsonData = Files.readAllBytes(Paths.get("F:\\logs\\lintas\\hotel-tbo-98-book-response.json"));
			//hotelBookResponse = this.mapper.readValue(jsonData, HotelBookResponse.class);


			hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelBookResponse);
			//logger.info("-------------((((("+name+"hotelBookResponse  text: "+hotelSearchRequestInString);			
			//apiHotelBook = this.responseParser.convertRezLivetoNativePreBookResponse(apiHotelBook, apiHotelBookResponse);
			//FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBookResponse  native :=="+hotelSearchRequestInString);

			apiHotelBook = this.responseParser.convertTbotoNativeBookResponse(apiHotelBook, hbc, roomStay, hotelIdFactory, hotelBookResponse,company,parentCompany);

			if(hotelBookResponse!=null && hotelBookResponse.getBookResult() != null && hotelBookResponse.getBookResult().getResponseStatus() == 1 && (hotelBookResponse.getBookResult().getIsPriceChanged() || hotelBookResponse.getBookResult().getIsCancellationPolicyChanged()))
			{
				logger.info("-------------((((("+name+" re booking call (Price/Cancellation policy has been changed) --");				
				doBook(apiHotelBook, hbc, apiHotelBook.getRoomsummary(), hotelIdFactory,CompanyDao);
			}			
			//hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBookResponse  native 3:"+hotelSearchRequestInString);

		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" book hotel : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" book hotel : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" book hotel : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" prebook hotel : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return apiHotelBook;
	}



	public APIHotelCancelResponse doCancelWorking(String endUserIp, APIHotelCancelResponse apiHotelCancelResponse, APIHotelCancelRequest apiHotelCancelRequest, HotelOrderRow hor,  HotelIdFactoryImpl hotelIdFactory) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		APIStatus apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_NOT_SET, "Cancellation to be initiated ..." );
		if(authenticate(endUserIp, String.valueOf(hor.getSearchKey())))
		{
			isTest = this.hotelApiCredentials.isTesting();
			SendChangeRequest sendChangeRequest = requestBuilder.getSendChangeRequest(hotelApiCredentials, endUserIp, apiHotelCancelRequest, hor);
			String sendChangeRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sendChangeRequest);
			FileUtil.writeJson("hotel", "tbo", "cancel", false, sendChangeRequest, String.valueOf(hor.getSearchKey()));
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(sendChangeRequestInString,headers);
			String answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlCancel(), entity, String.class);
			//logger.info("-------------((((("+name+"--response:"+answer);		
			//System.out.println(answer);	
			SendChangeResponse sendChangeResponse= this.mapper.readValue(answer, SendChangeResponse.class);
			//logger.info("-------------(((((--hotelBookResponse obj :"+hotelBookResponse);			
			FileUtil.writeJson("hotel", "tbo", "cancel", true, sendChangeResponse, String.valueOf(hor.getSearchKey()));
			if(sendChangeResponse  != null && sendChangeResponse.getHotelChangeRequestResult() != null && sendChangeResponse.getHotelChangeRequestResult().getResponseStatus()!=null)
			{
				if(sendChangeResponse.getHotelChangeRequestResult().getResponseStatus() ==1)
				{
					GetChangeRequestStatus getChangeRequestStatus = requestBuilder.getGetChangeRequestStatusRequest(hotelApiCredentials, endUserIp, apiHotelCancelRequest, sendChangeResponse, hor);
					sendChangeRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getChangeRequestStatus);
					FileUtil.writeJson("hotel", "tbo", "cancel-status", false, getChangeRequestStatus, String.valueOf(hor.getSearchKey()));
					restTemplate = new RestTemplate();
					headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					entity = new HttpEntity<String>(sendChangeRequestInString, headers);
					answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlCancelStatus(), entity, String.class);
					//logger.info("-------------((((("+name+"--response:"+answer);		
					//System.out.println(answer);	
					GetChangeRequestStatusResponse getChangeRequestStatusResponse= this.mapper.readValue(answer, GetChangeRequestStatusResponse.class);
					//logger.info("-------------(((((--hotelBookResponse obj :"+hotelBookResponse);			
					FileUtil.writeJson("hotel", "tbo", "cancel-status", true, getChangeRequestStatusResponse, String.valueOf(hor.getSearchKey()));

					if(getChangeRequestStatusResponse != null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult()!=null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus() !=null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus() ==1)
					{
						apiHotelCancelResponse = responseParser.converttbotoNativeCancelResponse(apiHotelCancelResponse, getChangeRequestStatusResponse);
					}
					else
					{
						apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API get cancel status error ..." );
						apiHotelCancelResponse.setApiStatus(apiStatus);
					}
				}
				else if(sendChangeResponse.getHotelChangeRequestResult().getError() != null && sendChangeResponse.getHotelChangeRequestResult().getError().getErrorCode() != null)
				{
					/*"Error" : {
				      "ErrorCode" : 3,
				      "ErrorMessage" : "BookingId should be a positive integer."
				    }*/
					apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, sendChangeResponse.getHotelChangeRequestResult().getError().getErrorMessage() );
					apiHotelCancelResponse.setApiStatus(apiStatus);

					if(sendChangeResponse.getHotelChangeRequestResult().getError().getErrorCode() == 10)
					{

					}

				}
			}
			else
			{
				apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API initiate cancel error ..." );
				apiHotelCancelResponse.setApiStatus(apiStatus);
			}			
		}
		else
		{
			apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API authentication failed..." );
			apiHotelCancelResponse.setApiStatus(apiStatus);
		}		
		return apiHotelCancelResponse;
	}


	public APIHotelCancelResponse doCancel(String endUserIp, APIHotelCancelResponse apiHotelCancelResponse, APIHotelCancelRequest apiHotelCancelRequest, HotelOrderRow hor,  HotelIdFactoryImpl hotelIdFactory, HotelOrderDao hotelOrderDao,HotelOrderRowCancellation orderRowCancellation) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		APIStatus apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_NOT_SET, "Cancellation to be initiated ..." );
		HotelOrderRowCancellationAPIResponse hotelOrderRowCancellationAPIResponse=new HotelOrderRowCancellationAPIResponse();

		if(authenticate(endUserIp, String.valueOf(hor.getSearchKey())))
		{
			isTest = this.hotelApiCredentials.isTesting();
			SendChangeRequest sendChangeRequest = requestBuilder.getSendChangeRequest(hotelApiCredentials, endUserIp, apiHotelCancelRequest, hor);
			String sendChangeRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sendChangeRequest);
			FileUtil.writeJson("hotel", "tbo", "cancel", false, sendChangeRequest, String.valueOf(hor.getSearchKey()));
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(sendChangeRequestInString,headers);
			String answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlCancel(), entity, String.class);
			//logger.info("-------------((((("+name+"--response:"+answer);		
			//System.out.println(answer);	
			SendChangeResponse sendChangeResponse= this.mapper.readValue(answer, SendChangeResponse.class);					
			//byte[] jsonData = Files.readAllBytes(Paths.get("C:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\tbo\\42-cancel-response.json"));
			//SendChangeResponse sendChangeResponse = this.mapper.readValue(jsonData, SendChangeResponse.class);
			System.out.println("sendChangeResponse"+sendChangeResponse);

			//logger.info("-------------(((((--hotelBookResponse obj :"+hotelBookResponse);			
			FileUtil.writeJson("hotel", "tbo", "cancel", true, sendChangeResponse, String.valueOf(hor.getSearchKey()));
			if(sendChangeResponse  != null && sendChangeResponse.getHotelChangeRequestResult() != null && sendChangeResponse.getHotelChangeRequestResult().getResponseStatus()!=null)
			{
				try{


					if(sendChangeResponse.getHotelChangeRequestResult().getTraceId()!=null){
						hotelOrderRowCancellationAPIResponse.setTraceId(sendChangeResponse.getHotelChangeRequestResult().getTraceId());
					}else{
						hotelOrderRowCancellationAPIResponse.setTraceId(null);
					}
					if(sendChangeResponse.getHotelChangeRequestResult().getChangeRequestId()!=null){
						hotelOrderRowCancellationAPIResponse.setChangeRequestId(sendChangeResponse.getHotelChangeRequestResult().getChangeRequestId());
					}else{
						hotelOrderRowCancellationAPIResponse.setChangeRequestId(null);
					}

					hotelOrderRowCancellationAPIResponse.setB2B2BStatus(sendChangeResponse.getHotelChangeRequestResult().isB2B2BStatus());

					if(sendChangeResponse.getHotelChangeRequestResult().getError().getErrorCode()!=null){
						hotelOrderRowCancellationAPIResponse.setErrorCode(sendChangeResponse.getHotelChangeRequestResult().getError().getErrorCode());
					}else{
						hotelOrderRowCancellationAPIResponse.setErrorCode(0);
					}
					if(sendChangeResponse.getHotelChangeRequestResult().getError().getErrorMessage()!=null){
						hotelOrderRowCancellationAPIResponse.setErrorMessage(sendChangeResponse.getHotelChangeRequestResult().getError().getErrorMessage());
					}else{
						hotelOrderRowCancellationAPIResponse.setErrorMessage("");
					}
					if(sendChangeResponse.getHotelChangeRequestResult().getChangeRequestStatus()!=null){
						hotelOrderRowCancellationAPIResponse.setChangeRequestStatus(sendChangeResponse.getHotelChangeRequestResult().getChangeRequestStatus());
					}else{
						hotelOrderRowCancellationAPIResponse.setChangeRequestStatus(null);
					}
					if(sendChangeResponse.getHotelChangeRequestResult().getResponseStatus()!=null){
						hotelOrderRowCancellationAPIResponse.setResponseStatus(sendChangeResponse.getHotelChangeRequestResult().getResponseStatus());
					}else{
						hotelOrderRowCancellationAPIResponse.setResponseStatus(null);
					}
					if(sendChangeResponse.getHotelChangeRequestResult().getCancellationCharge()!=null){
						hotelOrderRowCancellationAPIResponse.setCancellationCharge(new BigDecimal(sendChangeResponse.getHotelChangeRequestResult().getCancellationCharge()));
					}else{
						hotelOrderRowCancellationAPIResponse.setCancellationCharge(null);
					}
					if(sendChangeResponse.getHotelChangeRequestResult().getRefundedAmount()!=null){
						hotelOrderRowCancellationAPIResponse.setRefundedAmount(new BigDecimal(sendChangeResponse.getHotelChangeRequestResult().getRefundedAmount()));
					}else{
						hotelOrderRowCancellationAPIResponse.setRefundedAmount(null);
					}
					if(sendChangeResponse.getHotelChangeRequestResult().getTotalPrice()!=null){
						hotelOrderRowCancellationAPIResponse.setTotalPrice(new BigDecimal(sendChangeResponse.getHotelChangeRequestResult().getTotalPrice()));
					}else{
						hotelOrderRowCancellationAPIResponse.setTotalPrice(null);
					}
					if(sendChangeResponse.getHotelChangeRequestResult().getCreditNoteNo()!=null){
						hotelOrderRowCancellationAPIResponse.setCreditNoteNo(sendChangeResponse.getHotelChangeRequestResult().getCreditNoteNo());
					}else{
						hotelOrderRowCancellationAPIResponse.setCreditNoteNo(null);
					}
					if(sendChangeResponse.getHotelChangeRequestResult().getCreditNoteCreatedOn()!=null){
						hotelOrderRowCancellationAPIResponse.setCreditNoteCreatedOn(sendChangeResponse.getHotelChangeRequestResult().getCreditNoteCreatedOn());
					}else{
						hotelOrderRowCancellationAPIResponse.setCreditNoteCreatedOn(null);
					}
					if(orderRowCancellation!=null){
						hotelOrderRowCancellationAPIResponse.setHotelOrderRowCancellation(orderRowCancellation);
					}
					hotelOrderDao.insertHotelOrderRowCancellationApiResposnse(hotelOrderRowCancellationAPIResponse);
				}catch (Exception e) {
					System.out.println("error occured during the apiresponse save in table "+e);
					e.printStackTrace();
					// TODO: handle exception
				}
				if(sendChangeResponse.getHotelChangeRequestResult().getResponseStatus() ==1)
				{
					GetChangeRequestStatus getChangeRequestStatus = requestBuilder.getGetChangeRequestStatusRequest(hotelApiCredentials, endUserIp, apiHotelCancelRequest, sendChangeResponse, hor);
					sendChangeRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getChangeRequestStatus);
					FileUtil.writeJson("hotel", "tbo", "cancel-status", false, getChangeRequestStatus, String.valueOf(hor.getSearchKey()));
					restTemplate = new RestTemplate();
					headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					entity = new HttpEntity<String>(sendChangeRequestInString, headers);
					answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlCancelStatus(), entity, String.class);
					//logger.info("-------------((((("+name+"--response:"+answer);		
					//System.out.println(answer);	
					//jsonData = Files.readAllBytes(Paths.get("C:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\tbo\\42-cancel-status-response.json"));
					//GetChangeRequestStatusResponse getChangeRequestStatusResponse = this.mapper.readValue(jsonData, GetChangeRequestStatusResponse.class);

					GetChangeRequestStatusResponse getChangeRequestStatusResponse= this.mapper.readValue(answer, GetChangeRequestStatusResponse.class);
					//logger.info("-------------(((((--hotelBookResponse obj :"+hotelBookResponse);			
					FileUtil.writeJson("hotel", "tbo", "cancel-status", true, getChangeRequestStatusResponse, String.valueOf(hor.getSearchKey()));

					if(getChangeRequestStatusResponse != null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult()!=null )
					{
						if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus() !=null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus() ==1)
						{
							apiHotelCancelResponse = responseParser.converttbotoNativeCancelResponse(apiHotelCancelResponse, getChangeRequestStatusResponse);
							System.out.println("apiHotelCancelResponse"+apiHotelCancelResponse);
						}
						else if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError()!=null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError().getErrorCode() !=null)
						{
							apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError().getErrorMessage() );
							apiHotelCancelResponse.setApiStatus(apiStatus);

						}
						else
						{
							apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API get cancel status error ..." );
							apiHotelCancelResponse.setApiStatus(apiStatus);
						}
					}
					else
					{
						apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API get cancel status error :: response format wrondg..." );
						apiHotelCancelResponse.setApiStatus(apiStatus);
					}
				}
				else if(sendChangeResponse.getHotelChangeRequestResult().getError() != null && sendChangeResponse.getHotelChangeRequestResult().getError().getErrorCode() != null)
				{
					/*"Error" : {
				      "ErrorCode" : 3,
				      "ErrorMessage" : "BookingId should be a positive integer."
				    }*/
					apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, sendChangeResponse.getHotelChangeRequestResult().getError().getErrorMessage() );
					apiHotelCancelResponse.setApiStatus(apiStatus);

					if(sendChangeResponse.getHotelChangeRequestResult().getError().getErrorCode() == 10)
					{

					}

				}
			}
			else
			{
				apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API initiate cancel error ..." );
				apiHotelCancelResponse.setApiStatus(apiStatus);
			}			
		}
		else
		{
			apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API authentication failed..." );
			apiHotelCancelResponse.setApiStatus(apiStatus);
			System.out.println("apiHotelCancelResponse"+apiHotelCancelResponse);
		}		
		return apiHotelCancelResponse;

	}

	public APIHotelCancelResponse doGetStatus(String endUserIp, APIHotelCancelResponse apiHotelCancelResponse, APIHotelCancelRequest apiHotelCancelRequest, HotelOrderRow hor,  HotelIdFactoryImpl hotelIdFactory, HotelOrderRowCancellation hotelOrderRowCancellation,HotelOrderDao hotelOrderDao) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		APIStatus apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_NOT_SET, "Get cancellation requested..." );
		HotelOrderRowCancellationAPIResponse hotelOrderRowCancellationAPIResponse=new HotelOrderRowCancellationAPIResponse();
		if(authenticate(endUserIp, String.valueOf(hor.getSearchKey())))
		{
			if(hotelOrderRowCancellation != null && hotelOrderRowCancellation.getAPIStatusCode() != null && hotelOrderRowCancellation.getAPIConfirmationNumber() != null)
			{
				isTest = this.hotelApiCredentials.isTesting();
				GetChangeRequestStatus getChangeRequestStatus = requestBuilder.getGetChangeRequestStatusRequest(hotelApiCredentials, endUserIp, apiHotelCancelRequest, hotelOrderRowCancellation, hor);
				String sendChangeRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getChangeRequestStatus);
				FileUtil.writeJson("hotel", "tbo", "cancel-status", false, getChangeRequestStatus, String.valueOf(hor.getSearchKey()));
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<String>(sendChangeRequestInString, headers);
				String answer = restTemplate.postForObject(hotelApiCredentials.getTboHotelConfig().getHotelUrlCancelStatus(), entity, String.class);
				//logger.info("-------------((((("+name+"--response:"+answer);		
				//System.out.println(answer);	
				//jsonData = Files.readAllBytes(Paths.get("C:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\tbo\\42-cancel-status-response.json"));
				//GetChangeRequestStatusResponse getChangeRequestStatusResponse = this.mapper.readValue(jsonData, GetChangeRequestStatusResponse.class);

				GetChangeRequestStatusResponse getChangeRequestStatusResponse= this.mapper.readValue(answer, GetChangeRequestStatusResponse.class);
				//logger.info("-------------(((((--hotelBookResponse obj :"+hotelBookResponse);			
				FileUtil.writeJson("hotel", "tbo", "cancel-status", true, getChangeRequestStatusResponse, String.valueOf(hor.getSearchKey()));

				if(getChangeRequestStatusResponse != null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult()!=null )
				{
					Double newApiPrice = new Double(0);
					String creditNoteNo = null;
					String creditNoteCreatedOn = null;
					Map<String,Object> hotelChangeRequestStatusResultapiresMap=getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getAdditionalProperties();

					try{


						if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getTraceId()!=null){
							hotelOrderRowCancellationAPIResponse.setTraceId(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getTraceId());
						}else{
							hotelOrderRowCancellationAPIResponse.setTraceId(null);
						}
						if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestId()!=null){
							hotelOrderRowCancellationAPIResponse.setChangeRequestId(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestId());
						}else{
							hotelOrderRowCancellationAPIResponse.setChangeRequestId(null);
						}
						if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError()!=null){
							hotelOrderRowCancellationAPIResponse.setErrorCode(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError().getErrorCode());
						}else{
							hotelOrderRowCancellationAPIResponse.setErrorCode(0);
						}
						if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError()!=null){
							hotelOrderRowCancellationAPIResponse.setErrorMessage(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError().getErrorMessage());
						}else{
							hotelOrderRowCancellationAPIResponse.setErrorMessage("");
						}
						if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus()!=null){
							hotelOrderRowCancellationAPIResponse.setResponseStatus(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus());
						}else{
							hotelOrderRowCancellationAPIResponse.setResponseStatus(null);
						}
						if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult()!=null){
							hotelOrderRowCancellationAPIResponse.setCancellationCharge((getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getCancellationCharge()));
						}else{
							hotelOrderRowCancellationAPIResponse.setCancellationCharge(null);
						}
						if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult() !=null){
							hotelOrderRowCancellationAPIResponse.setRefundedAmount(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getRefundedAmount());
						}else{
							hotelOrderRowCancellationAPIResponse.setRefundedAmount(null);
						}

						if(hotelChangeRequestStatusResultapiresMap!=null && hotelChangeRequestStatusResultapiresMap.size()>0){
							newApiPrice=hotelChangeRequestStatusResultapiresMap.containsKey("TotalPrice")?(Double) hotelChangeRequestStatusResultapiresMap.get("TotalPrice"):new Double(0);
							creditNoteNo=hotelChangeRequestStatusResultapiresMap.containsKey("CreditNoteNo")?(String)hotelChangeRequestStatusResultapiresMap.get("CreditNoteNo"):null;
							creditNoteCreatedOn=hotelChangeRequestStatusResultapiresMap.containsKey("CreditNoteCreatedOn")?(String)hotelChangeRequestStatusResultapiresMap.get("CreditNoteCreatedOn"):null;
							hotelOrderRowCancellationAPIResponse.setB2B2BStatus(hotelChangeRequestStatusResultapiresMap.containsKey("B2B2BStatus"));

						}
						hotelOrderRowCancellationAPIResponse.setTotalPrice(new BigDecimal(newApiPrice));
						hotelOrderRowCancellationAPIResponse.setCreditNoteNo(creditNoteNo);
						hotelOrderRowCancellationAPIResponse.setCreditNoteCreatedOn(creditNoteCreatedOn);
						Integer i=getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestStatus();
						Long ChangeRequestStatusforconvertion=Long.valueOf(i);
						if(ChangeRequestStatusforconvertion!=null){
							hotelOrderRowCancellationAPIResponse.setChangeRequestStatus(ChangeRequestStatusforconvertion);
						}else{
							hotelOrderRowCancellationAPIResponse.setChangeRequestStatus(null);
						}
						if(hotelOrderRowCancellation!=null){
							hotelOrderRowCancellationAPIResponse.setHotelOrderRowCancellation(hotelOrderRowCancellation);
						}
						hotelOrderDao.insertHotelOrderRowCancellationApiResposnse(hotelOrderRowCancellationAPIResponse);
					}catch (Exception e) {
						System.out.println("error occured during the apiresponse save in table "+e);
						e.printStackTrace();
						// TODO: handle exception
					}

					if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus() !=null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus() ==1)
					{
						apiHotelCancelResponse = responseParser.converttbotoNativeCancelResponse(apiHotelCancelResponse, getChangeRequestStatusResponse);
					}
					else if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError()!=null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError().getErrorCode() !=null)
					{
						apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError().getErrorMessage() );
						apiHotelCancelResponse.setApiStatus(apiStatus);

					}
					else
					{
						apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API get cancel status error ..." );
						apiHotelCancelResponse.setApiStatus(apiStatus);
					}
				}
				else
				{
					apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API get cancel status error :: response format wrondg..." );
					apiHotelCancelResponse.setApiStatus(apiStatus);
				}
			}
			else
			{
				/*"Error" : {
				      "ErrorCode" : 3,
				      "ErrorMessage" : "BookingId should be a positive integer."
				    }*/
				apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Cancellation is not initiated properly.., Call support" );
				apiHotelCancelResponse.setApiStatus(apiStatus);

			}

		}
		else
		{
			apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "API authentication failed..." );
			apiHotelCancelResponse.setApiStatus(apiStatus);
		}		
		return apiHotelCancelResponse;
	}




	@Override
	public APIHotelMap call() {
		try {
			logger.info("-------------((((("+name+" Searhing hotels call--");
			//if(!isAuthencated){
			if(authenticate(hs.getEndUserIp(), String.valueOf(hotelsearch.getSearch_key())))
			{
				logger.info("-------------((((("+name+" Searhing hotels authenticate call--");

				HotelSearchResponse hotelSearchResponse = searchHotels();
				logger.info("-------------((((("+name+" Searhing hotels searchHotels call--");

				initHotelsMap(hotelSearchResponse);
			}
			logger.info("-------------((((("+name+" Searhing hotels initHotelsMap call--");
			//}
		} catch (ClassNotFoundException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: ClassNotFoundException--"+e.getMessage());
			e.printStackTrace();
		} catch (JAXBException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: JAXBException--"+e.getMessage());
			e.printStackTrace();
		}	
		catch (UnsupportedOperationException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: UnsupportedOperationException--"+e.getMessage());
			e.printStackTrace();
		} catch (SOAPException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: SOAPException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (HibernateException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: HibernateException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: Exception--"+e.getMessage());
			e.printStackTrace();
		}
		apiHotelMap.setApiId(hotelApiCredentials.getId());
		return apiHotelMap;
	}

	public void initHotelsMap(HotelSearchResponse hotelSearchResponse) throws Exception 
	{	
		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
		apiHotelMap = new APIHotelMap();
		apiHotelMap.setRoomStays(roomStaysMap);
		HotelsInfo thotelsinfo =  new HotelsInfo();
		if(hotelSearchResponse == null || hotelSearchResponse.getHotelSearchResult() == null || hotelSearchResponse.getHotelSearchResult().getResponseStatus() != 1)
			return ;		

		for (HotelResult hotel : hotelSearchResponse.getHotelSearchResult().getHotelResults()) {
			RoomStay troomstay = new RoomStay();

			OTAHotelAvailRS.RoomStays.RoomStay trs = responseParser.convertTBOToNative(hotelApiCredentials, hs, hotel, hotelSearchResponse);			
			//trs = searchHotelInfo(trs);
			trs.setCity(city);
			roomStaysMap.put(trs.getBasicPropertyInfo().getHotelCode(), trs);

		}
		apiHotelMap.setRoomStays(roomStaysMap);
		//logger.info(name+" hotels count for search--"+roomStaysMap.size());	

		//apiHotelMap.setTgRoomStays(tgRoomStaysMap);				
	}




	/*	
	public String getURL_AUTHENTICATE() {
		URL_AUTHENTICATE = "SharedServices/SharedData.svc/rest/Authenticate";	
		if(!isTest)
			URL_AUTHENTICATE = "SharedAPI/SharedData.svc/rest/Authenticate";	
		return URL_AUTHENTICATE;
	}

	public String getURL_SEARCH_HOTELS() {
		 URL_SEARCH_HOTELS = "BookingEngineService_Hotel/hotelservice.svc/rest/GetHotelResult/";
			if(!isTest)
				 URL_SEARCH_HOTELS = "HotelAPI_V10/HotelService.svc/rest/GetHotelResult/";
		return URL_SEARCH_HOTELS;
	}	

	public String getURL_SEARCH_HOTELINFO() {
		URL_SEARCH_HOTELINFO = "BookingEngineService_Hotel/hotelservice.svc/rest/GetHotelInfo/";
		if(!isTest)
			URL_SEARCH_HOTELINFO = "HotelAPI_V10/HotelService.svc/rest/GetHotelInfo/";
		return URL_SEARCH_HOTELINFO;
	}	

	public String getURL_SEARCH_ROOMS() {
		URL_SEARCH_ROOMS = "BookingEngineService_Hotel/hotelservice.svc/rest/GetHotelRoom/";
		if(!isTest)
			URL_SEARCH_ROOMS = "HotelAPI_V10/HotelService.svc/rest/GetHotelRoom/";
		return URL_SEARCH_ROOMS;
	}	

	public String getURL_BLOCK_ROOMS() {
		URL_BLOCK_ROOMS = "BookingEngineService_Hotel/hotelservice.svc/rest/BlockRoom/";	
		if(!isTest)
			URL_BLOCK_ROOMS = "HotelAPI_V10/HotelService.svc/rest/BlockRoom/";	
		return URL_BLOCK_ROOMS;
	}	

	public String getURL_BOOKING() {
		URL_BOOKING = "BookingEngineService_Hotel/hotelservice.svc/rest/Book/";
		if(!isTest)
			URL_BOOKING = "HotelAPI_V10/HotelService.svc/rest/Book/";
		return URL_BOOKING;
	}	

	public String getURL_CANCEL() {
		 URL_CANCEL = "BookingEngineService_Hotel/hotelservice.svc/rest/SendChangeRequest/";
		 if(!isTest)
			 URL_CANCEL = "HotelAPI_V10/HotelService.svc/rest/SendChangeRequest/";
		return URL_CANCEL;
	}	

	public String getURL_CANCEL_STATUS() {
		URL_CANCEL_STATUS = "BookingEngineService_Hotel/hotelservice.svc/rest/GetChangeRequestStatus/";
		 if(!isTest)
			 URL_CANCEL_STATUS = "HotelAPI_V10/HotelService.svc/rest/GetChangeRequestStatus/";
		return URL_CANCEL_STATUS;
	}	*/

	public boolean isTest() {
		return isTest;
	}

	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

}
