package com.tayyarah.hotel.util.api.concurrency;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tayyarah.api.hotel.reznext.model.RoomInfoType;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIRoomDetail;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelsInfo;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;

import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.reposit.dao.HotelRepositDAOIMP;
import com.tayyarah.hotel.reposit.entity.Hoteloverview;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.LintasRequestBuilder;
import com.tayyarah.hotel.util.LintasResponseParser;


public class LintasHotelRepositPullerTask implements Callable<APIHotelMap> {

	public HotelRepositDAOIMP getHotelRepositDAOIMP() {
		return hotelRepositDAOIMP;
	}

	public void setHotelRepositDAOIMP(HotelRepositDAOIMP hotelRepositDAOIMP) {
		this.hotelRepositDAOIMP = hotelRepositDAOIMP;
	}

	public LintasRequestBuilder getRequestBuilder() {
		return requestBuilder;
	}

	public void setRequestBuilder(LintasRequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}

	public LintasResponseParser getResponseParser() {
		return responseParser;
	}

	public void setResponseParser(LintasResponseParser responseParser) {
		this.responseParser = responseParser;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public LintasHotelRepositPullerTask(HotelObjectTransformer hotelObjectTransformer, HotelRepositDAOIMP hotelRepositDAOIMP, HoteloverviewDao hoteloverviewDao,
			HotelroomdescriptionDao hotelroomdescriptionDao, HotelimagesDao hotelimagesDao, HotelFacilityDao hotelFacilityDao,
			IslhotelmappingDao islhotelmappingDao, HotelinandaroundDao hotelinandaroundDao,
			HotelsecondaryareaDao hotelsecondaryareaDao, HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();
		this.hotelObjectTransformer = hotelObjectTransformer;
		this.hoteloverviewDao = hoteloverviewDao;
		this.hotelRepositDAOIMP = hotelRepositDAOIMP;
		this.hotelroomdescriptionDao = hotelroomdescriptionDao;
		this.hotelimagesDao = hotelimagesDao;
		this.hotelFacilityDao = hotelFacilityDao;
		this.islhotelmappingDao = islhotelmappingDao;
		this.hotelinandaroundDao = hotelinandaroundDao;
		this.hotelsecondaryareaDao = hotelsecondaryareaDao;
		this.api = api;
		this.hs = hs;
		this.name = name;
		this.requestBuilder = new LintasRequestBuilder();
		this.responseParser = new LintasResponseParser();
		this.apiHotelMap = new APIHotelMap();
		this.mapper = new ObjectMapper();
		this.mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		}

	public LintasHotelRepositPullerTask(HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();		
		this.api = api;
		this.hs = hs;
		this.name = name;
		this.requestBuilder = new LintasRequestBuilder();
		this.responseParser = new LintasResponseParser();		
		this.apiHotelMap = new APIHotelMap();
		this.mapper = new ObjectMapper();
		this.mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
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
	public com.tayyarah.api.hotel.rezlive.model.HotelFindResponse getHotelFindResponse() {
		return hotelFindResponse;
	}
	public void setHotelFindResponse(com.tayyarah.api.hotel.rezlive.model.HotelFindResponse hotelFindResponse) {
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
	public static final Logger logger = Logger.getLogger(TayyarahPullerTask.class);


	private HotelObjectTransformer hotelObjectTransformer;


	private HotelRepositDAOIMP hotelRepositDAOIMP;

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
	private com.tayyarah.api.hotel.rezlive.model.HotelFindResponse hotelFindResponse;
	private APIHotelMap apiHotelMap;
	private HotelBookCommand hb;
	private String hotelCode;
	private RoomInfoType roomInfoType;	
	private String actionname;
	private LintasRequestBuilder requestBuilder = new LintasRequestBuilder();
	private LintasResponseParser responseParser = new LintasResponseParser();
	private ObjectMapper mapper = null;

	public static final String URL_SEARCH_HOTELS = "http://128.199.96.87:8080/TayyarahAPI/hotel/search";
	public static final String URL_SEARCH_ROOMS = "http://128.199.96.87:8080/TayyarahAPI/hotel/roomdetail";
	public static final String URL_SEARCH_ROOM_SUMMARY = "http://128.199.96.87:8080/TayyarahAPI/hotel/roomdetail/summary";
	public static final String URL_PREBOOKING = "http://128.199.96.87:8080/TayyarahAPI/hotel/prebook";
	public static final String URL_BOOKING = "http://128.199.96.87:8080/TayyarahAPI/hotel/book";
	public static final String URL_BOOKING_SUMMARY = "http://128.199.96.87:8080/TayyarahAPI/hotel/book/summary";

	public static final String API_CURRENCY = "INR";
	public static final String AGENCY_NAME = "LintasAPIConsumer";


	/*public static final String URL_SEARCH_HOTELS = "http://localhost:8282/LintasTravelAPI/hotel/search";
	public static final String URL_SEARCH_ROOMS = "http://localhost:8282/LintasTravelAPI/hotel/roomdetail";
	public static final String URL_SEARCH_ROOM_SUMMARY = "http://localhost:8282/LintasTravelAPI/hotel/roomdetail/summary";
	public static final String URL_PREBOOKING = "http://localhost:8282/LintasTravelAPI/hotel/prebook";
	public static final String URL_BOOKING = "http://localhost:8282/LintasTravelAPI/hotel/book";
	public static final String URL_BOOKING_SUMMARY = "http://localhost:8282/LintasTravelAPI/hotel/book/summary";
	 */	
	private StringBuilder doGet(String endPointUrl) throws SOAPException,JAXBException
	{
		String urlParameters = "";
		//String url = "http://test.xmlhub.com/testpanel.php/action/findhotel";
		StringBuilder response = new StringBuilder();		
		logger.info("-------------((((("+name+"---------endPointUrl : "+endPointUrl);
		HttpURLConnection urlConnection = null;
		try{

			//String encodedURL = java.net.URLEncoder.encode(endPointUrl,"UTF-8");			
			URL url = new URL(endPointUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

		}		
		catch(UnsupportedEncodingException e)
		{
			logger.info("-------------((((("+name+"---------UnsupportedEncodingException : "+e.getMessage());
			e.printStackTrace();

		}
		catch(ProtocolException e)
		{
			logger.info("-------------((((("+name+"---------ProtocolException : "+e.getMessage());
			e.printStackTrace();

		}
		catch(MalformedURLException e)
		{
			logger.info("-------------((((("+name+"---------MalformedURLException : "+e.getMessage());
			e.printStackTrace();
		}
		catch(IOException e)
		{
			logger.info("-------------((((("+name+"---------IOException : "+e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	public List<Hoteloverview> searchHotels()
			throws Exception {	
		List<Hoteloverview> hotels = new ArrayList<Hoteloverview>();
		try {
			hotels = hotelRepositDAOIMP.getHoteloverviewByCity(hs.getCity());
			logger.info("-------------((((("+name+" Hotel search ..apiHotelMap raw response: "+hotels.size());		
		} 
		catch (Exception e) {
			logger.info("-------------((((("+name+" Searhing hotels : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return hotels;
	}
	public APIRoomDetail searchHotelRooms(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
			throws Exception {		
		APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR));
		try {
			//http://localhost:8282/LintasTravelAPI/hotel/search?appkey=zqJ3R9cGpNWgNXG55ub%2FWQ%3D%3D&cachelevel=Live
			//&city=Paris&country=France&countrycode=FR&currency=INR&dateend=2016-01-17&datestart=2016-01-14&filter=7
			//&lang=en&mode=0&noofrooms=1&order=PRICE&rooms=$0,1,0&type=4&version=1.0
			logger.info("-------------((((("+name+" Hotel Rooms search ..: ");				
			String searchURL = URL_SEARCH_ROOMS+"?appkey="+this.api.getApiKey()+"&searchkey="+rs.getBasicPropertyInfo().getSearchKey()
					+"&hotelcode="+rs.getBasicPropertyInfo().getHotelCode();
			logger.info("-------------((((("+name+" Hotel Rooms search ..url: "+searchURL);			
			apiRoomDetail = mapper.readValue(new URL(searchURL), APIRoomDetail.class);
			logger.info("-------------((((("+name+" Hotel Roomssearch ..apiRoomDetail: "+apiRoomDetail);	
			apiRoomDetail = covertNativeRoomDetail(apiRoomDetail, rs);
			//Pretty print
			String apiHotelMapString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiRoomDetail);
			logger.info("-------------((((("+name+" Hotel Rooms search ..apiRoomDetail raw response: "+apiHotelMapString);		
		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" Searhing hotel Rooms : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" Searhing hotel Rooms : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Searhing hotel Rooms : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" Searhing hotel Rooms : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return apiRoomDetail;
	}
	public APIRoomDetail searchHotelRoomSummary(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs, String bookingkey)
			throws Exception {		
		APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR));
		try {
			//http://localhost:8282/LintasTravelAPI/hotel/search?appkey=zqJ3R9cGpNWgNXG55ub%2FWQ%3D%3D&cachelevel=Live
			//&city=Paris&country=France&countrycode=FR&currency=INR&dateend=2016-01-17&datestart=2016-01-14&filter=7
			//&lang=en&mode=0&noofrooms=1&order=PRICE&rooms=$0,1,0&type=4&version=1.0
			logger.info("-------------((((("+name+" Hotel RoomSummary search ..: ");				
			String searchURL = URL_SEARCH_ROOM_SUMMARY+"?appkey="+this.api.getApiKey()+"&searchkey="+rs.getBasicPropertyInfo().getSearchKey()
					+"&hotelcode="+rs.getBasicPropertyInfo().getHotelCode()+"&bookingkey="+bookingkey;
			logger.info("-------------((((("+name+" Hotel RoomSummary search ..url: "+searchURL);			
			apiRoomDetail = mapper.readValue(new URL(searchURL), APIRoomDetail.class);
			logger.info("-------------((((("+name+" Hotel RoomSummary ..apiRoomDetail: "+apiRoomDetail);
			apiRoomDetail = covertNativeRoomDetail(apiRoomDetail, rs);
			//Pretty print
			String apiHotelMapString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiRoomDetail);
			logger.info("-------------((((("+name+" Hotel RoomSummary search ..apiRoomDetail raw response: "+apiHotelMapString);		
		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" Searhing RoomSummary : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" Searhing RoomSummary : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Searhing RoomSummary : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" Searhing RoomSummary : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return apiRoomDetail;
	}

	private HotelBookCommand covertApiPreBookCommand(HotelBookCommand hbc, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		/*{ 	   "orderid":"",
			   "correlationid":"",
			   "userid":"2",
			   "password":"DirectUser",
			   "username":"DirectUser",
			   "payBy":"card",
			   "appkey":"zqJ3R9cGpNWgNXG55ub/WQ==",
			   "bookingCode":"BcHBDYAwDAPAiSw5jhLJGzACX5qWD_v_uZu1yjpt507G9lOt8bwq-li4L4EQoxCCElFkE98P",
			   "searchKey":241,
			   "hotelCode":38749,
			   "numberOfUnits":1,
			   "apiProvider":1			  
		}*/
		HotelBookCommand hbcnew  = new HotelBookCommand(hbc);		
		hbcnew.setSearchKey(rs.getBasicPropertyInfo().getSearchKey().longValue());
		hbcnew.setAppkey(this.api.getApiKey());
		hbcnew.setUserid(this.api.getPropertyId());
		hbcnew.setUsername(this.api.getUserName());
		hbcnew.setPassword(this.api.getPassword());
		hbcnew.setBookingSystemType("api");
		hbcnew.setPayBy("cash");
		return hbcnew;
	}
	private HotelBookCommand covertApiWalletBookCommand(HotelBookCommand hbc, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		/*{ 	   "orderid":"",
			   "correlationid":"",
			   "userid":"2",
			   "password":"DirectUser",
			   "username":"DirectUser",
			   "payBy":"card",
			   "appkey":"zqJ3R9cGpNWgNXG55ub/WQ==",
			   "bookingCode":"BcHBDYAwDAPAiSw5jhLJGzACX5qWD_v_uZu1yjpt507G9lOt8bwq-li4L4EQoxCCElFkE98P",
			   "searchKey":241,
			   "hotelCode":38749,
			   "numberOfUnits":1,
			   "apiProvider":1			  
		}*/
		HotelBookCommand hbcnew  = new HotelBookCommand(hbc);		
		hbcnew.setSearchKey(rs.getBasicPropertyInfo().getSearchKey().longValue());
		hbcnew.setAppkey(this.api.getApiKey());
		hbcnew.setUsername(this.api.getUserName());
		hbcnew.setPassword(this.api.getPassword());
		hbcnew.setBookingSystemType("api");
		hbcnew.setPayBy("cash");
		return hbcnew;
	}

	private HotelBookCommand covertApiBookCommand(HotelBookCommand hbc, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{		
		HotelBookCommand hbcnew  = new HotelBookCommand(hbc);		
		hbcnew.setSearchKey(rs.getBasicPropertyInfo().getSearchKey().longValue());
		hbcnew.setAppkey(this.api.getApiKey());
		hbcnew.setAppkey(this.api.getApiKey());
		hbcnew.setAppkey(this.api.getApiKey());
		hbcnew.setAppkey(this.api.getApiKey());
		hbcnew.setAppkey(this.api.getApiKey());
		hbcnew.setAppkey(this.api.getApiKey());
		//hbc.setPayBy("card");
		return hbcnew;
	}

	private APIRoomDetail covertNativeRoomDetail(APIRoomDetail apiRoomDetail, RoomStay rsNative)
	{
		RoomStay rs = apiRoomDetail.getRs();

		BasicPropertyInfoType tbasicNative = rsNative.getBasicPropertyInfo();

		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();			
		tbasic.setApiProvider(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL);
		tbasic.setIsOfflineBooking(false);
		tbasic.setSearchKey(apiRoomDetail.getSearchKey());	
		tbasic.setTransactionKey(apiRoomDetail.getTransactionKey());	


		tbasic.setApiPrice(tbasicNative.getApiPrice());
		tbasic.setBasePrice(tbasicNative.getBasePrice());
		tbasic.setBasePriceWithoutMarkup(tbasicNative.getBasePriceWithoutMarkup());
		tbasic.setBookingPrice(tbasicNative.getBookingPrice());

		tbasic.setExRateApiToBase(tbasicNative.getExRateApiToBase());
		tbasic.setExRateBaseToBooking(tbasicNative.getExRateBaseToBooking());

		tbasic.setApiCurrency(tbasicNative.getApiCurrency());
		tbasic.setBaseCurrency(tbasicNative.getBaseCurrency());
		tbasic.setBookingCurrency(tbasicNative.getBookingCurrency());	

		rs.setBasicPropertyInfo(tbasic);
		RoomRates troomrates = rs.getRoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			//Rates
			RateType lrates = new RateType();
			List<Rate> lratelist = new ArrayList<Rate>();
			logger.info("object transformation---: RateType. size "+rr.getRates().getRates().size());			
			for (Rate rtype : rr.getRates().getRates()) {
				Rate lrate = rtype;				
				TotalType apiPrice = rtype.getBookingPrice();			

				TotalType ltotsinglebase = new TotalType();
				TotalType ltotsinglebasewithoutmarkup = new TotalType();
				TotalType ltotsinglebooking = new TotalType();				
				BeanUtils.copyProperties(apiPrice, ltotsinglebase);
				BeanUtils.copyProperties(apiPrice, ltotsinglebasewithoutmarkup);
				BeanUtils.copyProperties(apiPrice, ltotsinglebooking);				
				lrate.setApiPrice(apiPrice);
				lrate.setBase(ltotsinglebase);			
				lrate.setBaseWithoutMarkUp(ltotsinglebasewithoutmarkup);
				lrate.setBookingPrice(ltotsinglebooking);
				lratelist.add(lrate);
			}
			lrates.setRates(lratelist);
			lrr.setRates(lrates);			
			lrratelist.add(lrr);

		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		rs.setBasicPropertyInfo(tbasic);		
		apiRoomDetail.setRs(rs);
		return apiRoomDetail;
	}


	public APIHotelBook doPrebook(APIHotelBook apiHotelBook)
			throws Exception {			
		apiHotelBook = responseParser.convertLintastoNativePreBookResponse(apiHotelBook);	
		return apiHotelBook;
	}

	public APIHotelBook doBook(APIHotelBook apiHotelBook)
			throws Exception {		
		//APIHotelBook apiHotelBookResponse = new APIHotelBook();
		apiHotelBook = responseParser.convertLintastoNativeFinalBookResponse(apiHotelBook);	
		return apiHotelBook;	
	}	
	public APIHotelBook getBookSummary(String appkey, String refno, String refno_old)
			throws Exception {		
		APIHotelBook apiHotelBook = new APIHotelBook();

		return apiHotelBook;	
	}
	@Override
	public APIHotelMap call() throws Exception {
		try {
			List<Hoteloverview> hotels = searchHotels();
			initHotelsMap(hotels);
		} catch (ClassNotFoundException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: ClassNotFoundException--"+e.getMessage());
			e.printStackTrace();
		} catch (JAXBException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: JAXBException--"+e.getMessage());
			e.printStackTrace();
		}	
		catch (UnsupportedOperationException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: UnsupportedOperationException--"+e.getMessage());
			e.printStackTrace();
		} catch (SOAPException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: SOAPException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (HibernateException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: HibernateException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return apiHotelMap;
	}

	public void initHotelsMap(List<Hoteloverview> hotels) throws HibernateException, ClassNotFoundException, JAXBException, ParseException, CloneNotSupportedException 
	{	
		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
		apiHotelMap = new APIHotelMap();
		apiHotelMap.setRoomStays(roomStaysMap);
		HotelsInfo thotelsinfo =  new HotelsInfo();
		if(hotels == null || hotels.isEmpty())
			return ;	
		
		for (Hoteloverview hoteloverview : hotels) {
			com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay trs = responseParser.convertLintastoNativeRoomDetail(hs, hoteloverview, hotelRepositDAOIMP);			
			roomStaysMap.put("LI"+hoteloverview.getId().getVendorID(), trs);
		}
		apiHotelMap.setRoomStays(roomStaysMap);
	}
}
