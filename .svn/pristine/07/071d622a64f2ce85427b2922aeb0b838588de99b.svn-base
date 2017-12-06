package com.tayyarah.hotel.util.api.concurrency;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tayyarah.api.hotel.rezlive.model.BookingResponse;
import com.tayyarah.api.hotel.rezlive.model.CancellationPolicyResponse;
import com.tayyarah.api.hotel.rezlive.model.HotelDetailsResponse;
import com.tayyarah.api.hotel.rezlive.model.HotelFindResponse;
import com.tayyarah.api.hotel.rezlive.model.HotelFindResponse.Hotels.Hotel;
import com.tayyarah.api.hotel.rezlive.model.PreBookingResponse;
import com.tayyarah.api.hotel.reznext.model.RoomInfoType;
import com.tayyarah.common.util.soap.HttpPostClient;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelOverview;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.HotelsInfo;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.RezLiveRequestBuilder;
import com.tayyarah.hotel.util.RezLiveResponseParser;

import javassist.bytecode.Descriptor.Iterator;

public class RezLivePullerTask implements Callable<APIHotelMap> {	
	public RezLivePullerTask(HotelObjectTransformer hotelObjectTransformer, HoteloverviewDao hoteloverviewDao,
			HotelroomdescriptionDao hotelroomdescriptionDao, HotelimagesDao hotelimagesDao, HotelFacilityDao hotelFacilityDao,
			IslhotelmappingDao islhotelmappingDao, HotelinandaroundDao hotelinandaroundDao,
			HotelsecondaryareaDao hotelsecondaryareaDao, HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();
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
		this.requestBuilder = new RezLiveRequestBuilder();
		this.responseParser = new RezLiveResponseParser();
		this.apiHotelMap = new APIHotelMap();
	}
	
	public RezLivePullerTask(HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();		
		this.api = api;
		this.hs = hs;
		this.name = name;
		this.requestBuilder = new RezLiveRequestBuilder();
		this.responseParser = new RezLiveResponseParser();
		this.apiHotelMap = new APIHotelMap();
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
	public static final Logger logger = Logger.getLogger(RezLivePullerTask.class);


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
	private String hotelCode;
	private RoomInfoType roomInfoType;	
	private String actionname;
	private RezLiveRequestBuilder requestBuilder = new RezLiveRequestBuilder();
	private RezLiveResponseParser responseParser = new RezLiveResponseParser();

	/*public static final String URL_FIND_HOTEL_GEO = "http://s2.xmlhub.com/findhotel";
	public static final String URL_FIND_HOTEL_ID = "http://s2.xmlhub.com/findhotelbyid";
	public static final String URL_PREBOOK = "http://s2.xmlhub.com/prebook";
	public static final String URL_BOOKING = "http://s2.xmlhub.com/bookhotel";
	public static final String URL_CANCEL_BOOKING = "http://s2.xmlhub.com/cancelhotel";
	public static final String URL_GET_CANCELLATION_POLICY = "http://s2.xmlhub.com/getcancellationpolicy";
	public static final String URL_GET_BOOKING_DETAILS = "http://s2.xmlhub.com/getbookingdetails";
	public static final String URL_GET_HOTEL_DETAILS = "http://s2.xmlhub.com/gethoteldetails";
	public static final String URL_GET_CANCELLATION_POLICY_AFTER_BOOKING = "http://s2.xmlhub.com/getCancellationPolicyAfterBooking";
	public static final String URL_GET_HOTEL_CONFIRMATION_DETAILS = "http://s2.xmlhub.com/getConfirmationDetails";
*/
	public static final String URL_FIND_HOTEL_GEO = "http://test.xmlhub.com/testpanel.php/action/findhotel";
	public static final String URL_FIND_HOTEL_ID = "http://test.xmlhub.com/testpanel.php/action/findhotelbyid";
	public static final String URL_PREBOOK = "http://test.xmlhub.com/testpanel.php/action/prebook";
	public static final String URL_BOOKING = "http://test.xmlhub.com/testpanel.php/action/bookhotel";
	public static final String URL_CANCEL_BOOKING = "http://test.xmlhub.com/testpanel.php/action/cancelhotel";
	public static final String URL_GET_CANCELLATION_POLICY = "http://test.xmlhub.com/testpanel.php/action/getcancellationpolicy";
	public static final String URL_GET_BOOKING_DETAILS = "http://test.xmlhub.com/testpanel.php/action/getbookingdetails";
	public static final String URL_GET_HOTEL_DETAILS = "http://test.xmlhub.com/testpanel.php/action/gethoteldetails";
	public static final String URL_GET_CANCELLATION_POLICY_AFTER_BOOKING = "http://test.xmlhub.com/testpanel.php/action/getCancellationPolicyAfterBooking";
	public static final String URL_GET_HOTEL_CONFIRMATION_DETAILS = "http://test.xmlhub.com/testpanel.php/action/getConfirmationDetails";

	/*	Hotel Search By ID: http://test.xmlhub.com/testpanel.php/action/findhotelbyid
		Hotel Search By Geo: http://test.xmlhub.com/testpanel.php/action/findhotel
		Hotel Pre Book : http://test.xmlhub.com/testpanel.php/action/prebook
		Hotel Booking: http://test.xmlhub.com/testpanel.php/action/bookhotel
		Hotel Booking Cancellation:
		http://test.xmlhub.com/testpanel.php/action/cancelhotel
		Get Cancellation Policy: http://test.xmlhub.com/testpanel.php/action/getcancellationpolicy
		Get Booked Hotel Details:
		http://test.xmlhub.com/testpanel.php/action/getbookingdetails
		Get Hotel Detail: http://test.xmlhub.com/testpanel.php/action/gethoteldetails
		Get Cancellation Policy After Boooking:
		http://test.xmlhub.com/testpanel.php/action/getCancellationPolicyAfterBooking
		Get Hotel Confirmation Detil
		http://test.xmlhub.com/testpanel.php/action/getConfirmationDetails
	 */
	// HTTP POST request
	private StringBuilder sendPost(String endPointUrl, StringBuilder requestxml) throws SOAPException,JAXBException
	{
		String urlParameters = "";
		StringBuilder response = new StringBuilder();
		try{			
			urlParameters =	"XML=" + URLEncoder.encode(requestxml.toString(), "UTF-8") ;
			URL obj = new URL(endPointUrl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("enctype", "application/x-www-form-urlencoded");
			// Send post request
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

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
	
	public void findHotelbyGeo() throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		StringBuilder requestxml = requestBuilder.getHotelSearchReq(this.api, this.hs);
		this.hotelFindResponse = new HotelFindResponse();		
		StringBuilder res = sendPost(URL_FIND_HOTEL_GEO, requestxml);
		int start = res.indexOf("<HotelFindResponse");
		if(start != -1 )
		{
			res.delete(0, start);
			res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			Unmarshaller unmarshaller = JAXBContext.newInstance(HotelFindResponse.class).createUnmarshaller();
			StringReader reader = new StringReader(res.toString());
			this.hotelFindResponse = (HotelFindResponse)unmarshaller.unmarshal(reader);
		}		
	}

	public OTAHotelAvailRS.RoomStays.RoomStay getRoomDetail(OTAHotelAvailRS.RoomStays.RoomStay rs) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		rs = responseParser.convertRezLivetoRoomDetail(rs);
		return rs;
	}	
	
	public APIHotelBook preBook(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		StringBuilder requestxml = requestBuilder.getPreBookingReq(this.api, this.hs, hbc, roomStay, hbc.getBookingCode(), hotelIdFactory);	
		PreBookingResponse preBookingResponse = new PreBookingResponse();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = sendPost(URL_PREBOOK,requestxml);
		int start = res.indexOf("<PreBookingResponse");
		OTAHotelResRS prebookres = new OTAHotelResRS();
		if(start != -1 )
		{
			res.delete(0, start);	
			res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			Unmarshaller unmarshaller = JAXBContext.newInstance(PreBookingResponse.class).createUnmarshaller();
			StringReader reader = new StringReader(res.toString());
			preBookingResponse = (PreBookingResponse)unmarshaller.unmarshal(reader);
			apiHotelBook = this.responseParser.convertRezLivetoNativePreBookResponse(apiHotelBook, hbc, roomStay, hotelIdFactory,preBookingResponse);
		}
		else
		{			
			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebook Error - API Format error..." ); 
			apiHotelBook.setStatus(status);
			prebookres.setStatus(status);			
			apiHotelBook.setPreBookRes(prebookres);
		}			
		return apiHotelBook;		
	}
	
	public APIHotelBook book(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelResRS totaHotelResRS, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelOrderRow hor, HotelIdFactoryImpl hotelIdFactory) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		StringBuilder requestxml = requestBuilder.getHotelBookReq(this.api, this.hs, hbc, totaHotelResRS, roomStay, hor, hotelIdFactory);		
		BookingResponse bookingResponse = new BookingResponse();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = sendPost(URL_BOOKING,requestxml);
		int start = res.indexOf("<BookingResponse");	
		OTAHotelResRS bookres = new OTAHotelResRS();
		if(start != -1 )
		{
			res.delete(0, start);			
			res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			Unmarshaller unmarshaller = JAXBContext.newInstance(BookingResponse.class).createUnmarshaller();
			StringReader reader = new StringReader(res.toString());
			bookingResponse = (BookingResponse)unmarshaller.unmarshal(reader);
			apiHotelBook = this.responseParser.convertRezLivetoNativeBookResponse(apiHotelBook, hbc, roomStay, hor, hotelIdFactory,bookingResponse);
		}
		else
		{
			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebook Error - API Format error..." ); 
			apiHotelBook.setStatus(status);
			bookres.setStatus(status);			
			apiHotelBook.setBookRes(bookres);
		}	
		return apiHotelBook;
	}
		
	public void cancel() throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		StringBuilder requestxml = requestBuilder.getHotelSearchReq(this.api, this.hs);		
		this.hotelFindResponse = new HotelFindResponse();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = sendPost(URL_FIND_HOTEL_ID,requestxml);
		int start = res.indexOf("<HotelFindResponse");
		if(start != -1 )
		{
			res.delete(0, start);			
			res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			Unmarshaller unmarshaller = JAXBContext.newInstance(HotelFindResponse.class).createUnmarshaller();
			StringReader reader = new StringReader(res.toString());
			this.hotelFindResponse = (HotelFindResponse)unmarshaller.unmarshal(reader);
		}		
	}
	
	public OTAHotelAvailRS.RoomStays.RoomStay getCancelPolicyRoomDetail(OTAHotelAvailRS.RoomStays.RoomStay roomStay) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		StringBuilder requestxml = requestBuilder.getHotelRoomCancellationPolicy(this.api, this.hs, roomStay);	
		CancellationPolicyResponse cancellationPolicyResponse = new CancellationPolicyResponse();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = sendPost(URL_GET_CANCELLATION_POLICY,requestxml);
		int start = res.indexOf("<CancellationPolicyResponse");
		if(start != -1 )
		{
			res.delete(0, start);
			res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			Unmarshaller unmarshaller = JAXBContext.newInstance(CancellationPolicyResponse.class).createUnmarshaller();
			StringReader reader = new StringReader(res.toString());
			cancellationPolicyResponse = (CancellationPolicyResponse)unmarshaller.unmarshal(reader);
			roomStay = this.responseParser.convertRezLivetoNativeCancellationPolicyRoomDetail(roomStay, cancellationPolicyResponse);
		}
		return roomStay;
	}
	
	public RoomRate getRoomRate(OTAHotelAvailRS.RoomStays.RoomStay rs, RatePlanType rateplan) throws HibernateException, ClassNotFoundException, JAXBException 
	{	
		RatePlanType trateplan = null;
		String rateplancode = "";
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			if(lrr.getRatePlanCode().equalsIgnoreCase(rateplan.getRatePlanCode()))
			{				
				return rr;
			}
		}		
		return null;
	}
	
	public OTAHotelAvailRS.RoomStays.RoomStay getHotelRoomDetails(OTAHotelAvailRS.RoomStays.RoomStay roomStay) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		StringBuilder requestxml = requestBuilder.getHotelRoomDetailsReq(this.api, this.hs, roomStay);			
		HotelDetailsResponse hotelDetailsResponse = new HotelDetailsResponse();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = sendPost(URL_GET_HOTEL_DETAILS,requestxml);
		int start = res.indexOf("<HotelDetailsResponse");

		if(start != -1 )
		{
			res.delete(0, start);
			res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			logger.info("-------------((((("+name+" Hotel response trimmed raw data : "+res);				
			Unmarshaller unmarshaller = JAXBContext.newInstance(HotelDetailsResponse.class).createUnmarshaller();
			StringReader reader = new StringReader(res.toString());
			hotelDetailsResponse = (HotelDetailsResponse)unmarshaller.unmarshal(reader);
			logger.info("-------------((((("+name+" Hotel Detail response unmarshalled  HotelDetailsResponse obj : "+hotelDetailsResponse);				
			roomStay = this.responseParser.convertRezLivetoNativeRoomDetail(roomStay, hotelDetailsResponse);
		
		}
		else
		{
			logger.info("-------------((((("+name+" Hotel Detail response is not formatted : "+res);	
		}	
		return roomStay;		
	}
	
	
	public OTAHotelAvailRS.RoomStays.RoomStay getCancelPolicyHotel(OTAHotelAvailRS.RoomStays.RoomStay roomStay) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		RoomStayType.RatePlans trateplans = roomStay.getRatePlans();
		List<RatePlanType> lplantlist = new ArrayList<RatePlanType>();
		for (RatePlanType pt : roomStay.getRatePlans().getRatePlan()) {	
			RoomRate rr = getRoomRate(roomStay, pt);
			StringBuilder requestxml = requestBuilder.getHotelRoomCancellationPolicyRoomRate(this.api, this.hs, roomStay, rr);			
			CancellationPolicyResponse cancellationPolicyResponse = new CancellationPolicyResponse();
			HttpPostClient postclient = new HttpPostClient(this.api);
			StringBuilder res = sendPost(URL_GET_CANCELLATION_POLICY,requestxml);
			int start = res.indexOf("<CancellationPolicyResponse");
			if(start != -1 )
			{
				res.delete(0, start);
				res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				logger.info("-------------((((("+name+" Hotel response trimmed raw data : "+res);				
				Unmarshaller unmarshaller = JAXBContext.newInstance(CancellationPolicyResponse.class).createUnmarshaller();
				StringReader reader = new StringReader(res.toString());
				cancellationPolicyResponse = (CancellationPolicyResponse)unmarshaller.unmarshal(reader);
				logger.info("-------------((((("+name+" Hotel response unmarshalled  cancellationPolicyResponse obj : "+cancellationPolicyResponse);				
				RatePlanType ptupdated = this.responseParser.convertRezLivetoNativeCancellationPolicyRoomDetailRatePlan(pt, cancellationPolicyResponse);
				lplantlist.add(ptupdated);	
			}
			else
			{
				lplantlist.add(pt);	
			}			
		}
		trateplans.setRatePlen(lplantlist);
		roomStay.setRatePlans(trateplans);
		return roomStay;		
	}	
	
	public APIHotelBook getCancelPolicyBooking(APIHotelBook apiHotelBook, OTAHotelAvailRS.RoomStays.RoomStay roomStay) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		StringBuilder requestxml = requestBuilder.getHotelRoomCancellationPolicy(this.api, this.hs, roomStay);	
		CancellationPolicyResponse cancellationPolicyResponse = new CancellationPolicyResponse();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = sendPost(URL_GET_CANCELLATION_POLICY,requestxml);
		int start = res.indexOf("<CancellationPolicyResponse");
		if(start != -1 )
		{
			res.delete(0, start);	
			res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			logger.info("-------------((((("+name+" Hotel response trimmed raw data : "+res);				
			Unmarshaller unmarshaller = JAXBContext.newInstance(CancellationPolicyResponse.class).createUnmarshaller();
			StringReader reader = new StringReader(res.toString());
			cancellationPolicyResponse = (CancellationPolicyResponse)unmarshaller.unmarshal(reader);
			apiHotelBook = this.responseParser.convertRezLivetoNativeCancellationPolicy(apiHotelBook, cancellationPolicyResponse);
		}
		return apiHotelBook;
	}
	@Override
	public APIHotelMap call() throws Exception {
		try {
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();					
			apiHotelMap.setRoomStays(roomStaysMap);
			findHotelbyGeo();
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

	public void initHotelsMap() throws HibernateException, ClassNotFoundException, JAXBException, ParseException, CloneNotSupportedException 
	{	
		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
		apiHotelMap = new APIHotelMap();
		apiHotelMap.setRoomStays(roomStaysMap);
		HotelsInfo thotelsinfo =  new HotelsInfo();
		if(this.hotelFindResponse == null || this.hotelFindResponse.getHotels()== null || this.hotelFindResponse.getHotels().getHotels() == null ||this.hotelFindResponse.getHotels().getHotels().isEmpty())
			return ;		

		List<String> hotelcodelist = new ArrayList<String>();
		hotelcodelist = getApiVendorIdList();
		Map<String, HotelOverview> map = this.hoteloverviewDao.getHotelOverviewNativeMapByVendorIDs(hotelcodelist, "2H");
		Map<String, Integer> hotelidmap = getHotelIdMap(map);
		Map<String, List<Facility>> hotelfacilitymap = hotelObjectTransformer.getFacilities(hotelidmap, "property");
		Map<String, List<Facility>> hotelroomfacilitymap = hotelObjectTransformer.getFacilities(hotelidmap, "room");
		Map<String, List<String>> hotelimagesmap = hotelObjectTransformer.getHotelImages(hotelidmap);
		Map<String, Map<Integer, Hotelroomdescription>> hotelroommap = hotelObjectTransformer.getHotelRooms(hotelidmap);
		for (Hotel hotel : this.hotelFindResponse.getHotels().getHotels()) {
			RoomStay troomstay = new RoomStay();
			HotelOverview hoteloverview =  map.get(hotel.getId());
			List<Facility> hotelfacilitieslist = hotelfacilitymap.get(hotel.getId());
			List<Facility> hotelRoomfacilitieslist = hotelroomfacilitymap.get(hotel.getId());
			List<String> hotelimages = hotelimagesmap.get(hotel.getId());
			Map<Integer, Hotelroomdescription> hotelroomsmap = hotelroommap.get(hotel.getId());
			if(hoteloverview != null)
			{
				OTAHotelAvailRS.RoomStays.RoomStay trs = responseParser.convertRezLivetoNativeRoomDetail(hs, hotel, hoteloverview, hotelfacilitieslist, hotelRoomfacilitieslist, hotelimages, hotelroomsmap);			
				roomStaysMap.put(String.valueOf(hotelidmap.get(hotel.getId())), trs);
			}			
		}
		apiHotelMap.setRoomStays(roomStaysMap);
		}

	public List<String> getApiVendorIdList()
	{
		List<String> hotelidlist = new ArrayList<String>();
		for (Hotel hotel : this.hotelFindResponse.getHotels().getHotels()) {
			hotelidlist.add(hotel.getId());
		}
		return hotelidlist;				
	}
	public Map<String, Integer> getHotelIdMap(Map<String, HotelOverview> nativeMap)
	{
		Map<String, Integer> hotelidmap = new HashMap<String, Integer>();
		for (Entry<String, HotelOverview> entry : nativeMap.entrySet()) {
		    String apivendorid = entry.getKey();
		    HotelOverview ho = entry.getValue();
		    hotelidmap.put(apivendorid, ho.getHoteId());		  
		}
		return hotelidmap;				
	}
}
