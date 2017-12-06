package com.tayyarah.hotel.util.api.concurrency;

import java.io.IOException;
import java.net.ProtocolException;
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
import com.tayyarah.api.hotel.tbo.model.AuthenticateRequest;
import com.tayyarah.api.hotel.tbo.model.AuthenticateResponse;
import com.tayyarah.api.hotel.tbo.model.HotelBlockResponse;
import com.tayyarah.api.hotel.tbo.model.HotelIRoomInfoResponse;
import com.tayyarah.api.hotel.tbo.model.HotelInfoRequest;
import com.tayyarah.api.hotel.tbo.model.HotelInfoResponse;
import com.tayyarah.api.hotel.tbo.model.HotelResult;
import com.tayyarah.api.hotel.tbo.model.HotelSearchRequest;
import com.tayyarah.api.hotel.tbo.model.HotelSearchResponse;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.TBORequestBuilder;
import com.tayyarah.hotel.util.TBOResponseParser;


public class TBORoomPullerTaskNotInUse implements Callable<OTAHotelAvailRS.RoomStays.RoomStay>{
	public TBORoomPullerTaskNotInUse(HotelSearchTemp hotelsearch, HotelObjectTransformer hotelObjectTransformer, HoteloverviewDao hoteloverviewDao,
			HotelroomdescriptionDao hotelroomdescriptionDao, HotelimagesDao hotelimagesDao, HotelFacilityDao hotelFacilityDao,
			IslhotelmappingDao islhotelmappingDao, HotelinandaroundDao hotelinandaroundDao,
			HotelsecondaryareaDao hotelsecondaryareaDao, HotelApiCredentials api, HotelSearchCommand hs, String name, OTAHotelAvailRS.RoomStays.RoomStay rs) {
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
		this.api = api;
		this.hs = hs;
		this.name = name;
		this.requestBuilder = new TBORequestBuilder(null);
		this.responseParser = new TBOResponseParser();
		this.apiRoomDetail = rs;
		this.mapper = new ObjectMapper();
		//this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		//this.mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		//this.mapper.setSerializationInclusion(Include.NON_NULL);
		//this.mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public TBORoomPullerTaskNotInUse(HotelSearchTemp hotelsearch, HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();
		this.hotelsearch = hotelsearch;
		this.api = api;
		this.hs = hs;
		this.name = name;
		this.requestBuilder = new TBORequestBuilder(null);
		this.responseParser = new TBOResponseParser();		
		this.apiRoomDetail = new OTAHotelAvailRS.RoomStays.RoomStay();
		this.mapper = new ObjectMapper();
		//this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		//this.mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}


	public TBORoomPullerTaskNotInUse(HotelApiCredentials api, String name) {
		super();		
		this.api = api;		
		this.name = name;
		this.requestBuilder = new TBORequestBuilder(null);
		this.responseParser = new TBOResponseParser();		
		this.apiRoomDetail = new OTAHotelAvailRS.RoomStays.RoomStay();
		this.mapper = new ObjectMapper();
		//this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		//this.mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
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
		return api;
	}
	public void setApi(HotelApiCredentials api) {
		this.api = api;
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
	private HotelApiCredentials api;	
	private HotelSearchCommand hs;
	private String name;
	private HotelFindResponse hotelFindResponse;
	private OTAHotelAvailRS.RoomStays.RoomStay apiRoomDetail;
	private HotelBookCommand hb;
	private String hotelCode;
	private RoomInfoType roomInfoType;	
	private String actionname;
	private TBORequestBuilder requestBuilder = new TBORequestBuilder(null);
	private TBOResponseParser responseParser = new TBOResponseParser();
	private ObjectMapper mapper = null;

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

	public static final String URL_AUTHENTICATE = "/rest/Authenticate";	
	public static final String URL_SEARCH_HOTELS = "/rest/GetHotelResult/";
	public static final String URL_SEARCH_HOTELINFO = "/rest/GetHotelInfo/";
	public static final String URL_SEARCH_ROOMS = "/rest/GetHotelRoom/";
	public static final String URL_BLOCK_ROOMS = "/rest/BlockRoom/";	
	public static final String URL_BOOKING = "/rest/Book/";
	public static final String URL_CANCEL = "/rest/SendChangeRequest/";
	public static final String URL_CANCEL_STATUS = "/rest/GetChangeRequestStatus/";
	public static final String URL_BOOKING_SUMMARY = "http://tboapi.travelboutiqueonline.com/HotelAPI_V10/HotelService.svc";

	public static final String API_CURRENCY = "INR";
	public static final String AGENCY_NAME = "LintasAPIConsumer";

	public boolean authenticate(String endUserIp, String searchKey)
			throws Exception {		
		AuthenticateResponse authenticateResponse = new AuthenticateResponse();
		AuthenticateRequest authenticateRequest = new AuthenticateRequest();
		authenticateRequest.setClientId(this.api.getPropertyId());
		authenticateRequest.setEndUserIp(endUserIp);
		authenticateRequest.setUserName(this.api.getUserName());
		authenticateRequest.setPassword(this.api.getPassword());

		boolean isauthenticated = false;
		//ObjectMapper resmapper = new ObjectMapper();
		//mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		try {

			RestTemplate restTemplate = new RestTemplate();			

			FileUtil.writeJson("hotel", "tbo", "auth", false, authenticateRequest, searchKey);



			String authenticateResponseInString1 = this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateRequest);
			//logger.info("-------------(((((--authenticateRequest pretty :"+authenticateResponseInString1);		

			//String requestJson = "{ \"ClientId\": \"ApiIntegration\", \"UserName\": \"intelli\", \"Password\": \"intelli@123\", \"EndUserIp\": \"192.168.11.120\" }";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(authenticateResponseInString1,headers);
			String answer = restTemplate.postForObject(this.api.getAuthUrl()+URL_AUTHENTICATE, entity, String.class);
			//logger.info("-------------((((("+name+"--response:"+answer);				
			//System.out.println(answer);

			authenticateResponse = this.mapper.readValue(answer, AuthenticateResponse.class);
			//logger.info("-------------(((((--authenticateResponse  :"+authenticateResponse);			

			FileUtil.writeJson("hotel", "tbo", "auth", true, authenticateResponse, searchKey);


			authenticateResponseInString1 = this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateResponse);
			//logger.info("-------------(((((--authenticateResponse pretty :"+authenticateResponseInString1);			
			this.api.setTokenId(authenticateResponse.getTokenId());
			isauthenticated = true;

		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" authenticate hotel : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
			isauthenticated = false;
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" authenticate hotel : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
			isauthenticated = false;
		} catch (IOException e) {
			logger.info("-------------((((("+name+" authenticate hotel : IOException--"+e.getMessage());
			e.printStackTrace();
			isauthenticated = false;
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" authenticate hotel : Exception--"+e.getMessage());
			e.printStackTrace();
			isauthenticated = false;
		}	
		finally
		{
			return isauthenticated;
		}
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
		try {			
			String hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelInfoRequest);
			//logger.info("-------------((((("+name+"--hotelInfoRequest:"+hotelSearchRequestInString);			

			FileUtil.writeJson("hotel", "tbo", "search-hotel", false, hotelInfoRequest, String.valueOf(hotelsearch.getSearch_key()));

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(hotelSearchRequestInString,headers);
			String answer = restTemplate.postForObject(this.api.getEndPointUrl()+URL_SEARCH_HOTELINFO, entity, String.class);
			//logger.info("-------------((((("+name+"--response:"+answer);		
			//System.out.println(answer);	
			hotelInfoResponse = this.mapper.readValue(answer, HotelInfoResponse.class);
			//logger.info("-------------(((((--hotelInfoResponse  :"+hotelInfoResponse);			
			FileUtil.writeJson("hotel", "tbo", "search-hotel", true, hotelInfoResponse, String.valueOf(hotelsearch.getSearch_key()));

			hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelInfoResponse);
			//logger.info("-------------((((("+name+"hotelSearchResponse  : "+hotelSearchRequestInString);			
			//apiHotelBook = this.responseParser.convertRezLivetoNativePreBookResponse(apiHotelBook, apiHotelBookResponse);
			//FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBookResponse  native 3:"+FlightBookingResponseInString);

			rs = responseParser.convertTBOToNativeHotelInfo(this.api, this.hs, rs, hotelInfoResponse);



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


	public OTAHotelAvailRS.RoomStays.RoomStay searchHotelRooms()
			throws Exception {		
		HotelInfoRequest hotelInfoRequest = new HotelInfoRequest();
		hotelInfoRequest.setResultIndex(apiRoomDetail.getBasicPropertyInfo().getApiResultIndex());
		hotelInfoRequest.setHotelCode(apiRoomDetail.getBasicPropertyInfo().getApiVendorID());
		hotelInfoRequest.setEndUserIp(apiRoomDetail.getBasicPropertyInfo().getApiEndUserIp());
		hotelInfoRequest.setTokenId(apiRoomDetail.getBasicPropertyInfo().getApiTokenId());
		hotelInfoRequest.setTraceId(apiRoomDetail.getBasicPropertyInfo().getApiTraceId());		
		HotelIRoomInfoResponse hotelIRoomInfoResponse = new HotelIRoomInfoResponse();		
		try {

			String roomstaytext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiRoomDetail);
			//logger.info("-------------((((("+name+"--roomstaytext:"+roomstaytext);			


			String hotelSearchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hotelInfoRequest);
			//logger.info("-------------((((("+name+"--hotelRoomInfoRequest:"+hotelSearchRequestInString);			

			FileUtil.writeJson("hotel", "tbo", "search-room", false, hotelInfoRequest, String.valueOf(hotelsearch.getSearch_key()));


			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(hotelSearchRequestInString,headers);
			String answer = restTemplate.postForObject(this.api.getEndPointUrl()+URL_SEARCH_ROOMS, entity, String.class);
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

			apiRoomDetail = responseParser.convertTBOToNativeHotelRoomInfo(this.api, this.hs, apiRoomDetail, hotelIRoomInfoResponse);

			apiRoomDetail = searchHotelInfo(apiRoomDetail);


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
		return apiRoomDetail;
	}



	
	@Override
	public OTAHotelAvailRS.RoomStays.RoomStay call() {
		try {
			logger.info("-------------((((("+name+" Searhing rooms call--");
			if(authenticate(hs.getEndUserIp(), String.valueOf(hotelsearch.getSearch_key())))
			{
				logger.info("-------------((((("+name+"Before Searhing rooms call--");

				apiRoomDetail = searchHotelRooms();
				logger.info("-------------((((("+name+"After Searhing rooms  call--");

			}
			

		} catch (ClassNotFoundException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();				
			logger.info("-------------((((("+name+" Searhing rooms reqsoap: ClassNotFoundException--"+e.getMessage());
			e.printStackTrace();
		} catch (JAXBException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			
			logger.info("-------------((((("+name+" Searhing rooms reqsoap: JAXBException--"+e.getMessage());
			e.printStackTrace();
		}	
		catch (UnsupportedOperationException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			
			logger.info("-------------((((("+name+" Searhing rooms reqsoap: UnsupportedOperationException--"+e.getMessage());
			e.printStackTrace();
		} catch (SOAPException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			
			logger.info("-------------((((("+name+" Searhing rooms reqsoap: SOAPException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			
			logger.info("-------------((((("+name+" Searhing rooms reqsoap: IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (HibernateException e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			
			logger.info("-------------((((("+name+" Searhing rooms reqsoap: HibernateException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			
			logger.info("-------------((((("+name+" Searhing rooms reqsoap: Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return apiRoomDetail;
	}

}
