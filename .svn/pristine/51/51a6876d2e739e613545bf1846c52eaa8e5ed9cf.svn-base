package com.tayyarah.hotel.util.api.concurrency;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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

import com.tayyarah.common.util.FileUtil;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelCancelRequest;
import com.tayyarah.hotel.model.APIHotelCancelResponse;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIRoomDetail;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.TayyarahRequestBuilder;
import com.tayyarah.hotel.util.TayyarahResponseParser;


public class TayyarahPullerTask implements Callable<APIHotelMap> {
	public TayyarahPullerTask(BigInteger searchKey, HotelObjectTransformer hotelObjectTransformer, HoteloverviewDao hoteloverviewDao,
			HotelroomdescriptionDao hotelroomdescriptionDao, HotelimagesDao hotelimagesDao, HotelFacilityDao hotelFacilityDao,
			IslhotelmappingDao islhotelmappingDao, HotelinandaroundDao hotelinandaroundDao,
			HotelsecondaryareaDao hotelsecondaryareaDao, HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();
		this.searchKey = searchKey;
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
		this.requestBuilder = new TayyarahRequestBuilder();
		this.responseParser = new TayyarahResponseParser();
		this.apiHotelMap = new APIHotelMap();
		this.mapper = new ObjectMapper();
		this.mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		//this.mapper.setSerializationInclusion(Include.NON_NULL);
		//this.mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public TayyarahPullerTask(BigInteger searchKey, HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();
		this.searchKey = searchKey;
		this.api = api;
		this.hs = hs;
		this.name = name;
		this.requestBuilder = new TayyarahRequestBuilder();
		this.responseParser = new TayyarahResponseParser();		
		this.apiHotelMap = new APIHotelMap();
		this.mapper = new ObjectMapper();
		this.mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}
	public TayyarahPullerTask(HotelApiCredentials api, String name) {
		super();		
		this.api = api;		
		this.name = name;
		this.requestBuilder = new TayyarahRequestBuilder();
		this.responseParser = new TayyarahResponseParser();	
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
	private HotelSearchCity city;
	public HotelSearchCity getCity() {
		return city;
	}

	public void setCity(HotelSearchCity city) {
		this.city = city;
	}
	private String hotelCode;
	private RoomInfoType roomInfoType;	
	private String actionname;
	private TayyarahRequestBuilder requestBuilder = new TayyarahRequestBuilder();
	private TayyarahResponseParser responseParser = new TayyarahResponseParser();
	private ObjectMapper mapper = null;

	private BigInteger searchKey;
	
	public BigInteger getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(BigInteger searchKey) {
		this.searchKey = searchKey;
	}
	public static final String URL_SEARCH_HOTELS = "search";
	public static final String URL_SEARCH_ROOMS = "roomdetail";
	public static final String URL_SEARCH_ROOM_SUMMARY = "roomdetail/summary";
	public static final String URL_PREBOOKING = "prebook";
	public static final String URL_BOOKING = "book";
	public static final String URL_CANCEL = "cancel";
	public static final String URL_BOOKING_SUMMARY = "book/summary";

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
		HttpURLConnection urlConnection = null;
		try{				
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

	public void searchHotels()
			throws Exception {		
		try {
			//http://localhost:8282/LintasTravelAPI/hotel/search?appkey=zqJ3R9cGpNWgNXG55ub%2FWQ%3D%3D&cachelevel=Live
			//&city=Paris&country=France&countrycode=FR&currency=INR&dateend=2016-01-17&datestart=2016-01-14&filter=7
			//&lang=en&mode=0&noofrooms=1&order=PRICE&rooms=$0,1,0&type=4&version=1.0
			String city = URLEncoder.encode(hs.getCity(), "UTF8");
			String country = URLEncoder.encode(hs.getCountry(), "UTF8");			
			logger.info("-------------((((("+name+" Hotel search ..: ");			
			String searchURL = this.api.getAuthUrl()+URL_SEARCH_HOTELS+"?appkey="+this.api.getApiKey()+"&cachelevel="+hs.getCachelevel()
			+"&city="+city+"&country="+country+"&countrycode="+hs.getCountrycode()
			+"&currency="+API_CURRENCY+"&dateend="+hs.getDateend()+"&datestart="+hs.getDatestart()
			+"&filter="+hs.getFilter()+"&lang="+hs.getLang()+"&mode="+hs.getMode()
			+"&noofrooms="+hs.getNoofrooms()+"&order="+hs.getOrder()+"&rooms="+hs.getRoomstext()
			+"&type="+hs.getType()+"&version="+hs.getVersion()
			+"&istesting="+false+"&apiids=4&isDynamicMarkup="+false+"&markupAmount=0";
			logger.info("-------------((((("+name+" Hotel search ..url: "+searchURL);
			StringBuilder response = doGet(searchURL);
			logger.info("-------------((((("+name+" Hotel search ..raw response: "+response.toString());		
			//Pretty print
			
			
			apiHotelMap = mapper.readValue(response.toString(), APIHotelMap.class);
			FileUtil.writeJson("hotel", "tayyarah", "search-hotel", true, apiHotelMap, String.valueOf(searchKey));

			//apiHotelMap = mapper.readValue(new URL(searchURL), APIHotelMap.class);
			//logger.info("-------------((((("+name+" Hotel search ..apiHotelMap: "+apiHotelMap);		
			//Pretty print
			String apiHotelMapString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelMap);
			logger.info("-------------((((("+name+" Hotel search ..apiHotelMap raw response: "+apiHotelMapString);		
		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" Searhing hotels : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" Searhing hotels : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Searhing hotels : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" Searhing hotels : Exception--"+e.getMessage());
			e.printStackTrace();
		}	
	}
	public APIRoomDetail searchHotelRooms(OTAHotelAvailRS.RoomStays.RoomStay rs)
			throws Exception {		
		APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR));
		try {
			//http://localhost:8282/LintasTravelAPI/hotel/search?appkey=zqJ3R9cGpNWgNXG55ub%2FWQ%3D%3D&cachelevel=Live
			//&city=Paris&country=France&countrycode=FR&currency=INR&dateend=2016-01-17&datestart=2016-01-14&filter=7
			//&lang=en&mode=0&noofrooms=1&order=PRICE&rooms=$0,1,0&type=4&version=1.0
			logger.info("-------------((((("+name+" Hotel Rooms search ..: ");				
			String searchURL = this.api.getAuthUrl()+URL_SEARCH_ROOMS+"?appkey="+this.api.getApiKey()+"&searchkey="+rs.getBasicPropertyInfo().getSearchKey()
					+"&hotelcode="+rs.getBasicPropertyInfo().getHotelCode();
			logger.info("-------------((((("+name+" Hotel Rooms search ..url: "+searchURL);			
			apiRoomDetail = mapper.readValue(new URL(searchURL), APIRoomDetail.class);
			logger.info("-------------((((("+name+" Hotel Roomssearch ..apiRoomDetail: "+apiRoomDetail);	
			FileUtil.writeJson("hotel", "tayyarah", "search-room-api", true, apiRoomDetail, String.valueOf(searchKey));

			apiRoomDetail = covertNativeRoomDetail(apiRoomDetail, rs);
			FileUtil.writeJson("hotel", "tayyarah", "search-room-native", true, apiRoomDetail, String.valueOf(searchKey));

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
	public APIRoomDetail searchHotelRoomSummary(OTAHotelAvailRS.RoomStays.RoomStay rs, String bookingkey)
			throws Exception {		
		APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR));
		try {
			//http://localhost:8282/LintasTravelAPI/hotel/search?appkey=zqJ3R9cGpNWgNXG55ub%2FWQ%3D%3D&cachelevel=Live
			//&city=Paris&country=France&countrycode=FR&currency=INR&dateend=2016-01-17&datestart=2016-01-14&filter=7
			//&lang=en&mode=0&noofrooms=1&order=PRICE&rooms=$0,1,0&type=4&version=1.0
			logger.info("-------------((((("+name+" Hotel RoomSummary search ..: ");				
			String searchURL = this.api.getAuthUrl()+URL_SEARCH_ROOM_SUMMARY+"?appkey="+this.api.getApiKey()+"&searchkey="+rs.getBasicPropertyInfo().getSearchKey()
					+"&hotelcode="+rs.getBasicPropertyInfo().getHotelCode()+"&bookingkey="+bookingkey;
			logger.info("-------------((((("+name+" Hotel RoomSummary search ..url: "+searchURL);			
			apiRoomDetail = mapper.readValue(new URL(searchURL), APIRoomDetail.class);
			logger.info("-------------((((("+name+" Hotel RoomSummary ..apiRoomDetail: "+apiRoomDetail);
			FileUtil.writeJson("hotel", "tayyarah", "search-room-summary-api", true, apiRoomDetail, String.valueOf(searchKey));

			apiRoomDetail = covertNativeRoomDetail(apiRoomDetail, rs);
			FileUtil.writeJson("hotel", "tayyarah", "search-room-summary-native", true, apiRoomDetail, String.valueOf(searchKey));

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

	private HotelBookCommand covertApiPreBookCommand(HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay rs)
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
	private HotelBookCommand covertApiWalletBookCommand(HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay rs)
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

	private HotelBookCommand covertApiBookCommand(HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay rs)
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
		List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();
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
				logger.info("\\\\\\\\*************************roomdetail -lrate.getBase()-"+lrate.getBase());	
				logger.info("\\\\\\\\*************************roomdetail -lrate.getBaseWithoutMarkUp()-"+lrate.getBaseWithoutMarkUp());	
				logger.info("\\\\\\\\*************************roomdetail -lrate.getApiPrice()-"+lrate.getApiPrice());	
				
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
		APIHotelBook apiHotelBookResponse = new APIHotelBook();
		try {

			String FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBook before book call native 1:"+FlightBookingResponseInString);			

			//http://localhost:8282/LintasTravelAPI/hotel/search?appkey=zqJ3R9cGpNWgNXG55ub%2FWQ%3D%3D&cachelevel=Live
			//&city=Paris&country=France&countrycode=FR&currency=INR&dateend=2016-01-17&datestart=2016-01-14&filter=7
			//&lang=en&mode=0&noofrooms=1&order=PRICE&rooms=$0,1,0&type=4&version=1.0
			//logger.info("-------------((((("+name+" Hotel search ..url: "+URL_PREBOOKING);
			String hbcstring = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook.getBook());
			//logger.info("-------------((((("+name+"apiHotelBook  raw native request:"+hbcstring);	
			HotelBookCommand hbc = apiHotelBook.getBook();
			RoomStay rsNative = apiHotelBook.getRoomsummary();
			FileUtil.writeJson("hotel", "tayyarah", "prebook-native", false, hbc, String.valueOf(searchKey));

			
			hbc = covertApiPreBookCommand(hbc, rsNative);
			FileUtil.writeJson("hotel", "tayyarah", "prebook-api", false, hbc, String.valueOf(searchKey));

			hbcstring = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hbc);
			logger.info("-------------((((("+name+"apiHotelBook  raw api request:"+hbcstring);	
			FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBook before book call native 2:"+FlightBookingResponseInString);			

			
			RestTemplate restTemplate = new RestTemplate();
			apiHotelBookResponse = restTemplate.postForObject(this.api.getAuthUrl()+URL_PREBOOKING, hbc, APIHotelBook.class);
			FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBookResponse);			
			logger.info("-------------((((("+name+"apiHotelBookResponse  api: 1"+FlightBookingResponseInString);
			FileUtil.writeJson("hotel", "tayyarah", "prebook-api", true, apiHotelBookResponse, String.valueOf(searchKey));
			
			
				
			//byte[] jsonData = Files.readAllBytes(Paths.get("F:\\logs\\lintas\\hotel\\tayyarah\\152-prebook-api-response.json"));
			//apiHotelBookResponse = mapper.readValue(jsonData, APIHotelBook.class);
			
			
			
			
			apiHotelBook = this.responseParser.convertRezLivetoNativePreBookResponse(apiHotelBook, apiHotelBookResponse);
			FileUtil.writeJson("hotel", "tayyarah", "prebook-native", true, apiHotelBook, String.valueOf(searchKey));

			FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			logger.info("-------------((((("+name+"apiHotelBookResponse  native 3:"+FlightBookingResponseInString);


			logger.info("-------------((((("+name+"apiHotelBook  raw response:"+FlightBookingResponseInString);						
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

	public APIHotelBook doBook(APIHotelBook apiHotelBook)
			throws Exception {		
		APIHotelBook apiHotelBookResponse = new APIHotelBook();
		String FlightBookingResponseInString = "";
		try {
			logger.info("-------------((((("+name+" Booking call ..: ");
			/*String hbcstring = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook.getBook());
			com.lintas.hotel.model.HotelBookCommand hbc = apiHotelBook.getBook();
			RoomStay rsNative = apiHotelBook.getRoomsummary();
			hbc = covertApiWalletBookCommand(hbc, rsNative);
			hbcstring = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hbc);
			logger.info("-------------((((("+name+"apiHotelBook  raw api request:"+hbcstring);	
			String FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			//logger.info("-------------((((("+name+"apiHotelBook before book call native 2:"+FlightBookingResponseInString);			

			RestTemplate restTemplate = new RestTemplate();
			apiHotelBookResponse = restTemplate.postForObject(URL_PREBOOKING, hbc, APIHotelBook.class);
			FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBookResponse);
			logger.info("-------------((((("+name+"apiHotelBookResponse  api: 1"+FlightBookingResponseInString);			
			apiHotelBook = this.responseParser.convertRezLivetoNativeBookResponse(apiHotelBook, apiHotelBookResponse);
			FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			logger.info("-------------((((("+name+"apiHotelBookResponse  native 3:"+FlightBookingResponseInString);
			 */
			String searchURL = this.api.getAuthUrl()+URL_BOOKING+"?appkey="+this.api.getApiKey()+"&refno="+apiHotelBook.getPreBookRes().getBookingCode()
					+"&payby=cash&response_message=Success"
					+"&response_code=1&transaction_id=T11111000"
					+"&payment_status=Success&AuthCode=Auth12000121";
			/*String searchURL = URL_BOOKING+"?appkey="+this.api.getApiKey()+"&refno="+apiHotelBook.getPreBookRes().getBookingCode()
					+"&payby="+payby+"&response_message="+response_message
					+"&response_code="+response_code+"&transaction_id="+transaction_id
					+"&payment_status="+payment_status+"&AuthCode="+AuthCode;*/
			logger.info("-------------((((("+name+" Hotel Booking ..url: "+searchURL);			
			apiHotelBookResponse = mapper.readValue(new URL(searchURL), APIHotelBook.class);
			FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBookResponse);
			logger.info("-------------((((("+name+"apiHotelBookResponse  api:"+FlightBookingResponseInString);	
			FileUtil.writeJson("hotel", "tayyarah", "book-api", true, apiHotelBookResponse, String.valueOf(searchKey));

			
			//byte[] jsonData = Files.readAllBytes(Paths.get("F:\\logs\\lintas\\hotel\\tayyarah\\152-book-api-response.json"));
			//apiHotelBookResponse = mapper.readValue(jsonData, APIHotelBook.class);
			
			
			apiHotelBook = this.responseParser.convertRezLivetoNativeBookResponse(apiHotelBook, apiHotelBookResponse);
			FileUtil.writeJson("hotel", "tayyarah", "book-native", true, apiHotelBook, String.valueOf(searchKey));

			FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			logger.info("-------------((((("+name+"apiHotelBookResponse  native:"+FlightBookingResponseInString);

		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" Booking call : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" Booking call : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Booking call : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" prebook hotel : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return apiHotelBook;	
	}	
	
	
	public APIHotelCancelResponse doCancel(APIHotelCancelRequest apiHotelCancelRequest, APIHotelCancelResponse apiHotelCancelResponse, HotelOrderRow hor,  HotelIdFactoryImpl hotelIdFactory)
			throws Exception {	
		String hotelCancelResponseInString = "";
		try {
			hotelCancelResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelCancelRequest);
			//logger.info("-------------((((("+name+"apiHotelBook  raw native request:"+hbcstring);				
			FileUtil.writeJson("hotel", "tayyarah", "cancel-native", false, apiHotelCancelRequest, String.valueOf(hor.getSearchKey()));
			apiHotelCancelRequest.setAppKey(api.getAppKey());
			apiHotelCancelRequest.setOrderId(hor.getApiConfirmationNo());
			apiHotelCancelRequest.setUserId(api.getPropertyId());
			apiHotelCancelRequest.setPassword(api.getPassword());
			
			FileUtil.writeJson("hotel", "tayyarah", "cancel-api", false, apiHotelCancelRequest, String.valueOf(hor.getSearchKey()));

			hotelCancelResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelCancelRequest);
			logger.info("-------------((((("+name+"cancel  raw api request:"+hotelCancelResponseInString);	
			hotelCancelResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelCancelRequest);
			//logger.info("-------------((((("+name+"apiHotelBook before book call native 2:"+FlightBookingResponseInString);			

			
			RestTemplate restTemplate = new RestTemplate();
			APIHotelCancelResponse apiHotelCancelTayyarah = restTemplate.postForObject(this.api.getAuthUrl()+URL_CANCEL, apiHotelCancelRequest, APIHotelCancelResponse.class);
			hotelCancelResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelCancelTayyarah);			
			logger.info("-------------((((("+name+"apiHotelCancelTayyarah  api: 1"+hotelCancelResponseInString);
			FileUtil.writeJson("hotel", "tayyarah", "cancel-api", true, apiHotelCancelTayyarah, String.valueOf(hor.getSearchKey()));
				
			//byte[] jsonData = Files.readAllBytes(Paths.get("F:\\logs\\lintas\\hotel\\tayyarah\\152-prebook-api-response.json"));
			//apiHotelBookResponse = mapper.readValue(jsonData, APIHotelBook.class);
			
			apiHotelCancelResponse = this.responseParser.converttbotoNativeCancelResponse(apiHotelCancelResponse, apiHotelCancelTayyarah);
			FileUtil.writeJson("hotel", "tayyarah", "cancel-native", true, apiHotelCancelResponse, String.valueOf(hor.getSearchKey()));

			hotelCancelResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelCancelResponse);
			logger.info("-------------((((("+name+"apiHotelCancelResponse  native 3:"+hotelCancelResponseInString);

		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" Booking call : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" Booking call : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Booking call : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" prebook hotel : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return apiHotelCancelResponse;	
	}	
	
	
	public APIHotelBook getBookSummary(String appkey, String refno, String refno_old)
			throws Exception {		
		APIHotelBook apiHotelBook = new APIHotelBook();
		try {
			logger.info("-------------((((("+name+" Booking call ..: ");				
			String searchURL = this.api.getAuthUrl()+URL_BOOKING_SUMMARY+"?appkey="+appkey+"&refno="+refno
					+"&refno_old="+refno_old;
			logger.info("-------------((((("+name+" Hotel Booking ..url: "+searchURL);			
			apiHotelBook = mapper.readValue(new URL(searchURL), APIHotelBook.class);
			logger.info("-------------((((("+name+"Booking call apiHotelBook  :"+apiHotelBook);
			String FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			logger.info("-------------((((("+name+" Booking call apiHotelBook  raw response:"+FlightBookingResponseInString);						
		} catch (JsonGenerationException e) {
			logger.info("-------------((((("+name+" Booking call : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info("-------------((((("+name+" Booking call : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Booking call : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" prebook hotel : Exception--"+e.getMessage());
			e.printStackTrace();
		}
		return apiHotelBook;	
	}
	@Override
	public APIHotelMap call() throws Exception {
		try {
			searchHotels();
			initHotelsMap();
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

	public void initHotelsMap() throws HibernateException, ClassNotFoundException, JAXBException, ParseException 
	{	
		TreeMap<String, RoomStay> roomStaysMap = apiHotelMap.getRoomStays();		
		for (Entry<String, RoomStay> entry :  apiHotelMap.getRoomStays().entrySet()) {
			String hotelId = entry.getKey();
			RoomStay rs = entry.getValue();			
			BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();			
			tbasic.setApiProvider(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL);
			tbasic.setIsOfflineBooking(false);
			tbasic.setSearchKey(apiHotelMap.getSearchKey());	
			tbasic.setTransactionKey(apiHotelMap.getTransactionKey());	

			tbasic.setApiPrice(tbasic.getBookingPrice());
			tbasic.setBasePrice(tbasic.getBookingPrice());
			tbasic.setBasePriceWithoutMarkup(tbasic.getBookingPrice());
			tbasic.setBookingPrice(tbasic.getBookingPrice());



			rs.setBasicPropertyInfo(tbasic);	
			roomStaysMap.put(hotelId, rs);
		}
		apiHotelMap.setRoomStays(roomStaysMap);		
	}

}
