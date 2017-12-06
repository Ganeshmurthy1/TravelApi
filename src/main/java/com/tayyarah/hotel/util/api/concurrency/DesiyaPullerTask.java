

package com.tayyarah.hotel.util.api.concurrency;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;


import com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS;
import com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.common.util.soap.SoapClient;
import com.tayyarah.hotel.dao.HotelFacilityDao;

import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelCancelRequest;
import com.tayyarah.hotel.model.APIHotelCancelResponse;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.AddressType;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.CountryNameType;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelOverview;

import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.HotelsInfo;

import com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.SuccessType;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.TGRequestBuilder;
import com.tayyarah.hotel.util.TGResponseParser;
import com.tayyarah.hotel.model.BasicPropertyInfoType.Position;
import com.tayyarah.hotel.model.BasicPropertyInfoType.ContactNumbers.ContactNumber;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;


import sun.net.www.protocol.http.HttpURLConnection;

public class DesiyaPullerTask implements Callable<APIHotelMap> {
	public static final Logger logger = Logger.getLogger(DesiyaPullerTask.class);


	public DesiyaPullerTask(HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();
		this.api = api;
		this.hs = hs;
		this.name = name;
		this.requestBuilder = new TGRequestBuilder(hs.getSearchCity());
		this.responseParser = new TGResponseParser();
	}
	
	public DesiyaPullerTask(HotelSearchTemp hotelSearch, HotelObjectTransformer hotelObjectTransformer, HoteloverviewDao hoteloverviewDao, HotelroomdescriptionDao hotelroomdescriptionDao,
			HotelimagesDao hotelimagesDao, HotelFacilityDao hotelFacilityDao,
			IslhotelmappingDao islhotelmappingDao, HotelinandaroundDao hotelinandaroundDao,
			HotelsecondaryareaDao hotelsecondaryareaDao, HotelApiCredentials api, HotelSearchCommand hs, String name, HotelSearchCity city) {
		super();	
		this.hotelSearch = hotelSearch;
		this.hotelObjectTransformer = hotelObjectTransformer;
		this.hotelroomdescriptionDao = hotelroomdescriptionDao;
		this.hoteloverviewDao = hoteloverviewDao;
		this.hotelimagesDao = hotelimagesDao;
		this.hotelFacilityDao = hotelFacilityDao;
		this.islhotelmappingDao = islhotelmappingDao;
		this.hotelinandaroundDao = hotelinandaroundDao;
		this.hotelsecondaryareaDao = hotelsecondaryareaDao;
		this.api = api;
		this.hs = hs;
		this.name = name;	
		this.apiHotelMap = new APIHotelMap();
		this.city = city;
		this.requestBuilder = new TGRequestBuilder(city);
		this.responseParser = new TGResponseParser();
	}

	public DesiyaPullerTask(HotelApiCredentials api, String name) {
		super();		
		this.api = api;		
		this.name = name;
		this.requestBuilder = new TGRequestBuilder(null);
		this.responseParser = new TGResponseParser();
		this.apiHotelMap = new APIHotelMap();		
		//this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		//this.mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}
	private HotelSearchTemp hotelSearch;
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
	private com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS otaHotelAvailRes;
	private APIHotelMap apiHotelMap;
	private TGRequestBuilder requestBuilder = new TGRequestBuilder(null);
	private TGResponseParser responseParser = new TGResponseParser();
	private HotelSearchCity city;
	
	public SOAPMessage sendSoapMessage(StringBuilder reqString) throws UnsupportedOperationException,
	SOAPException{
		SOAPMessage reply = null;
		try{
			SOAPMessage requestMessage = buildSoapMsgFromStr(reqString);
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();				
			reply = connection.call(requestMessage, api.getEndPointUrl()+"TGServiceEndPoint");
		}
		catch(SOAPException ex){			
			ex.printStackTrace();		
		}
		catch(Exception ex){		
			ex.printStackTrace();
		}
		return reply;

	}
	public SOAPMessage sendSoapMessage(StringBuilder reqString, String endpoint) throws UnsupportedOperationException,
	SOAPException{
		SOAPMessage reply = null;
		try{
			SOAPMessage requestMessage = buildSoapMsgFromStr(reqString);
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();		
			reply = connection.call(requestMessage, endpoint);
		}
		catch(SOAPException ex){		
			ex.printStackTrace();			
		}
		catch(Exception ex){			
			ex.printStackTrace();
		}
		return reply;
	}

	public SOAPMessage buildSoapMsgFromStr(StringBuilder requestXml) throws SOAPException{
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart soapPart =     message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody body =         envelope.getBody();
		byte[] buffer                 = requestXml.toString().getBytes();  
		ByteArrayInputStream stream   = new ByteArrayInputStream(buffer);  
		StreamSource source           = new StreamSource(stream);  
		soapPart.setContent(source);
		if(message.saveRequired())
			message.saveChanges();
		return message;
	}

	public OTAHotelAvailRS searchHotelDesia(HotelApiCredentials api, StringBuilder soapmessage)throws UnsupportedOperationException,SOAPException,JAXBException,IOException,Exception
	{
		OTAHotelAvailRS otaHotelAvailRS = null;
		FileUtil.writeSoap("hotel", "desiya", "search", false, soapmessage.toString(), String.valueOf(this.hotelSearch.getSearch_key()));		
		SOAPMessage sm = sendSoapMessage(soapmessage);	
		FileUtil.writeSoap("hotel", "desiya", "search", true, sm, String.valueOf(this.hotelSearch.getSearch_key()));
		Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();
		otaHotelAvailRS = (OTAHotelAvailRS)unmarshaller.unmarshal(sm.getSOAPBody().extractContentAsDocument());
		return otaHotelAvailRS;
	}
	
	public OTAHotelAvailRS searchHotelDetailDesia(HotelApiCredentials api, StringBuilder soapmessage)throws UnsupportedOperationException,SOAPException,JAXBException,IOException,Exception
	{
		OTAHotelAvailRS otaHotelAvailRS = null;
		FileUtil.writeSoap("hotel", "desiya", "search-hotel-detail", false, soapmessage.toString(), String.valueOf(this.hotelSearch.getSearch_key()));		
		SOAPMessage sm = sendSoapMessage(soapmessage);	
		FileUtil.writeSoap("hotel", "desiya", "search-hotel-detail", true, sm, String.valueOf(this.hotelSearch.getSearch_key()));
		Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();
		otaHotelAvailRS = (OTAHotelAvailRS)unmarshaller.unmarshal(sm.getSOAPBody().extractContentAsDocument());
		return otaHotelAvailRS;
	}
	
	public APIHotelBook provisionalBookingHotelDesia(com.tayyarah.hotel.model.TotalType revisedRate, APIHotelBook apiHotelBook, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay roomStay,String endPoint, SOAPMessage sm)throws UnsupportedOperationException,SOAPException,JAXBException,IOException,Exception
	{
		OTAHotelResRS otaHotelResRS = null;	
		FileUtil.writeSoap("hotel", "desiya", "prebook", false, sm, String.valueOf(apiHotelBook.getSearchKey()));		

		SOAPMessage ressm = sendSoapMessageTest(sm, endPoint);	
		FileUtil.writeSoap("hotel", "desiya", "prebook", true, ressm, String.valueOf(apiHotelBook.getSearchKey()));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ressm.writeTo(out);
		String strMsg = new String(out.toByteArray());		
		logger.info("provisionalBookingHotelDesia - --- :--- sources response "+ strMsg);		

		Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();
		otaHotelResRS = (OTAHotelResRS)unmarshaller.unmarshal(ressm.getSOAPBody().extractContentAsDocument());
		apiHotelBook = responseParser.convertTGtoNativePreBookResponse(revisedRate, apiHotelBook,roomStay, otaHotelResRS);
		return apiHotelBook;
	}

	public APIHotelBook finalBookingHotelDesia(APIHotelBook apiHotelBook, String endPoint, SOAPMessage sm)throws UnsupportedOperationException,SOAPException,JAXBException,IOException,Exception
	{
		OTAHotelResRS otaHotelResRS = null;	
		FileUtil.writeSoap("hotel", "desiya", "book", false, sm, String.valueOf(apiHotelBook.getSearchKey()));	
		SOAPMessage ressm = sendSoapMessageTest(sm, endPoint);	
		FileUtil.writeSoap("hotel", "desiya", "book", true, ressm, String.valueOf(apiHotelBook.getSearchKey()));
		Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();
	    otaHotelResRS = (OTAHotelResRS)unmarshaller.unmarshal(ressm.getSOAPBody().extractContentAsDocument());
		apiHotelBook = responseParser.convertTGtoNativeFinalBookResponse(apiHotelBook, otaHotelResRS);
		return apiHotelBook;
	}

	public static SOAPMessage sendSoapMessageTest(SOAPMessage sm,
			String url) throws UnsupportedOperationException,
			SOAPException, Exception{
		SOAPMessage reply = null;
		try{
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();			
			reply = connection.call(sm, url);
		}
		catch(SOAPException ex){
			throw ex;	
		}
		catch(Exception ex){
			throw ex;
		}
		return reply;
	}
	
	public static OTAHotelResRS probookpost(StringBuilder soapmessage, String url) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException
	{
		OTAHotelResRS otaHotelResRS =new OTAHotelResRS();		
		StringBuilder res = sendPost(soapmessage, url);
		int start = res.indexOf("<OTA_HotelResRS");

		if(start != -1 )
		{
			res.delete(0, start);
			int end = res.indexOf("</soapenv:Body>");
			if(end!= -1)
			{
				res.delete(end-1, res.length()-1);
				res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS.class).createUnmarshaller();
				StringReader reader = new StringReader(res.toString());
				otaHotelResRS = (OTAHotelResRS)unmarshaller.unmarshal(reader);				
			}			
		}	
		return otaHotelResRS;
	}


	public static OTAHotelResRS probookpost2(StringBuilder soapmessage, String url) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException
	{
		OTAHotelResRS otaHotelResRS =new OTAHotelResRS();		
		String restext = callWebService(soapmessage.toString(), url);
		StringBuilder res = new StringBuilder(restext);
		int start = res.indexOf("<OTA_HotelResRS");

		if(start != -1 )
		{
			res.delete(0, start);
			int end = res.indexOf("</soapenv:Body>");
			if(end!= -1)
			{
				res.delete(end-1, res.length()-1);
				res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS.class).createUnmarshaller();
				StringReader reader = new StringReader(res.toString());
				otaHotelResRS = (OTAHotelResRS)unmarshaller.unmarshal(reader);				
			}			
		}	
		return otaHotelResRS;
	}

	public static StringBuilder sendPost(StringBuilder req, String url) throws IOException, ProtocolException {			
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("POST");
		//"Content-Type","application/soap+xml"
		//con.setRequestProperty("content-type", "application/x-www-form-urlencoded");	
		con.setRequestProperty("Content-Type","application/soap+xml");
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(req.toString());
		wr.flush();
		wr.close();
		@SuppressWarnings("unused")
		int responseCode = con.getResponseCode();
		/*logger.info("\nSending 'POST' request to URL : " + api.getEndPointUrl()+actionName);
		logger.info("Post parameters : " + req);
		logger.info("Response Code : " + responseCode);*/

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();			
		//print result
		logger.info(response.toString());
		return response;
	}
	
	public static String callWebService(String body, String url) throws ParseException, IOException {
		//String body ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://example.com/v1.0/Records\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><SOAP-ENV:Body>"+soapenvbody+"</SOAP-ENV:Body></SOAP-ENV:Envelope>";

		HttpPost httppost = new HttpPost(url);
		// Request parameters and other properties.
		StringEntity stringentity=new StringEntity(body,"UTF-8");
		stringentity.setChunked(true);
		httppost.setEntity(stringentity);
		httppost.addHeader("Accept" , "text/xml");
		//httppost.addHeader("SOAPAction",soapaction);
		//Execute and get the response.
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		String strresponse = null;
		if(entity!=null) {
			strresponse = EntityUtils.toString(entity);
		}
		logger.info(response.toString());
		return strresponse;
	}
	
	public static OTAHotelResRS provisionalBookingHotelDesia(String endPoint, StringBuilder soapmessage)throws UnsupportedOperationException,SOAPException,JAXBException,IOException,Exception
	{
		OTAHotelResRS otaHotelResRS = null;			
		SOAPMessage sm = SoapClient.sendSoapMessageTest(soapmessage, endPoint);	
		Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.hotel.model.OTAHotelResRS.class).createUnmarshaller();
		otaHotelResRS = (OTAHotelResRS)unmarshaller.unmarshal(sm.getSOAPBody().extractContentAsDocument());
		return otaHotelResRS;
	}
	
	public static OTAHotelResRS finalBookingHotelDesia(String endPoint, StringBuilder soapmessage)throws UnsupportedOperationException,SOAPException,JAXBException,IOException,Exception
	{
		OTAHotelResRS otaHotelResRS = null;			
		SOAPMessage sm = SoapClient.sendSoapMessageTest(soapmessage, endPoint);	
		Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.hotel.model.OTAHotelResRS.class).createUnmarshaller();
		otaHotelResRS = (OTAHotelResRS)unmarshaller.unmarshal(sm.getSOAPBody().extractContentAsDocument());
		return otaHotelResRS;
	}

	public static OTAHotelResRS initiatecancelBookingHotelDesiaxx(String endPoint, SOAPMessage sm)throws UnsupportedOperationException,SOAPException,JAXBException,IOException,Exception
	{
		OTAHotelResRS otaHotelResRS = null;			
		SOAPMessage ressm = sendSoapMessageTest(sm, endPoint);	
		Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();
		otaHotelResRS = (OTAHotelResRS)unmarshaller.unmarshal(ressm.getSOAPBody().extractContentAsDocument());
		return otaHotelResRS;
	}
	public static OTAHotelResRS confirmcancelBookingHotelDesiaxx(String endPoint, SOAPMessage sm)throws UnsupportedOperationException,SOAPException,JAXBException,IOException,Exception
	{
		OTAHotelResRS otaHotelResRS = null;			
		SOAPMessage ressm = sendSoapMessageTest(sm, endPoint);		
		Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();
		otaHotelResRS = (OTAHotelResRS)unmarshaller.unmarshal(ressm.getSOAPBody().extractContentAsDocument());
		return otaHotelResRS;
	}

	public APIHotelCancelResponse doCancel(String endUserIp, APIHotelCancelResponse apiHotelCancelResponse, APIHotelCancelRequest apiHotelCancelRequest, HotelOrderRow hor,  HotelIdFactoryImpl hotelIdFactory) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		APIStatus apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_NOT_SET, "Cancellation to be initiated ..." );
		return apiHotelCancelResponse;
	}
	public com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay searchHotelDetail(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		logger.info(name + " is pulling started------------");
		StringBuilder reqsoap = new StringBuilder();
		otaHotelAvailRes = new OTAHotelAvailRS();
		com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rsFinal = rs;;		
		try {
			hs.setMode(HotelSearchCommand.MODE_SEARCH_DETAILED);
			List<com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef> hotelrefs = new ArrayList<com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef>();
			com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef h = new com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef();
			h.setHotelCode(rs.getBasicPropertyInfo().getApiVendorID());
			hotelrefs.add(h);
			hs.setHotelrefs(hotelrefs);
			
			reqsoap = TGRequestBuilder.getHotelSearchBodyDesiya(api, hs, city);
			logger.info("-------------((((("+name+" Searhing hotel detail reqsoap: "+reqsoap);

			otaHotelAvailRes = searchHotelDesia(api, reqsoap);
			logger.info("-------------((((("+name+"a Searhing hotels otaHotelAvailRes: "+otaHotelAvailRes);
			if(otaHotelAvailRes == null || otaHotelAvailRes.getRoomStays()== null || otaHotelAvailRes.getRoomStays().getRoomStaies() == null || otaHotelAvailRes.getRoomStays().getRoomStaies().isEmpty())
				return rs;
			for(com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay trs:otaHotelAvailRes.getRoomStays().getRoomStaies()){ 
				rsFinal = hotelObjectTransformer.convertTGtoNative(api, hs, trs);		
				break;
			}
			return rsFinal;		

		} catch (ClassNotFoundException e) {
			logger.info("-------------((((("+name+" Searhing  hotel detail reqsoap: ClassNotFoundException--"+e.getMessage());			
			e.printStackTrace();
		} catch (JAXBException e) {
			logger.info("-------------((((("+name+" Searhing  hotel detail reqsoap: JAXBException--"+e.getMessage());
			e.printStackTrace();
		}	
		catch (UnsupportedOperationException e) {
			logger.info("-------------((((("+name+" Searhing  hotel detail reqsoap: UnsupportedOperationException--"+e.getMessage());			
			e.printStackTrace();
		} catch (SOAPException e) {
			logger.info("-------------((((("+name+" Searhing  hotel detail reqsoap: SOAPException--"+e.getMessage());			
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Searhing  hotel detail reqsoap: IOException--"+e.getMessage());			
			e.printStackTrace();
		}
		catch (HibernateException e) {
			logger.info("-------------((((("+name+" Searhing  hotel detail reqsoap: HibernateException--"+e.getMessage());			
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: Exception--"+e.getMessage());			
			e.printStackTrace();
		}
		logger.info(name + " is pulling over------------");		
		return rsFinal;			
	}

	@Override
	public APIHotelMap call() throws Exception {
		logger.info(name + " is pulling started------------");
		StringBuilder reqsoap = new StringBuilder();
		otaHotelAvailRes = new OTAHotelAvailRS();
		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();
		apiHotelMap = new APIHotelMap();	
		apiHotelMap.setRoomStays(roomStaysMap);
		try {
			reqsoap = TGRequestBuilder.getHotelSearchBodyDesiya(api, hs, city);
			otaHotelAvailRes = searchHotelDesia(api, reqsoap);
			initHotelsMap();

		} catch (ClassNotFoundException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: ClassNotFoundException--"+e.getMessage());
			apiHotelMap = new APIHotelMap();	
			apiHotelMap.setRoomStays(roomStaysMap);
			e.printStackTrace();
		} catch (JAXBException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: JAXBException--"+e.getMessage());
			apiHotelMap = new APIHotelMap();	
			apiHotelMap.setRoomStays(roomStaysMap);
			e.printStackTrace();
		}	
		catch (UnsupportedOperationException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: UnsupportedOperationException--"+e.getMessage());
			apiHotelMap = new APIHotelMap();	
			apiHotelMap.setRoomStays(roomStaysMap);
			e.printStackTrace();
		} catch (SOAPException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: SOAPException--"+e.getMessage());
			apiHotelMap = new APIHotelMap();	
			apiHotelMap.setRoomStays(roomStaysMap);
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: IOException--"+e.getMessage());
			apiHotelMap = new APIHotelMap();	
			apiHotelMap.setRoomStays(roomStaysMap);
			e.printStackTrace();
		}
		catch (HibernateException e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: HibernateException--"+e.getMessage());
			apiHotelMap = new APIHotelMap();	
			apiHotelMap.setRoomStays(roomStaysMap);

			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info("-------------((((("+name+" Searhing hotels reqsoap: Exception--"+e.getMessage());
			apiHotelMap = new APIHotelMap();	
			apiHotelMap.setRoomStays(roomStaysMap);

			e.printStackTrace();
		}
		logger.info(name + " is pulling over------------");			

		apiHotelMap.setApiId(api.getId());
		return apiHotelMap;
	}
	
	public void initHotelsMapOld() throws HibernateException, IOException 
	{		
		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();
		TreeMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> tgRoomStaysMap = new TreeMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay>();
		apiHotelMap = new APIHotelMap();
		HotelsInfo thotelsinfo =  new HotelsInfo();
		if(otaHotelAvailRes == null)
			return ;		

		for(com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay rs:otaHotelAvailRes.getRoomStays().getRoomStaies()){ 
			tgRoomStaysMap.put(rs.getBasicPropertyInfo().getHotelCode(), rs);	
			RoomStay troomstay = new RoomStay();
			RoomRates troomrates = new RoomRates();
			List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();
			BigDecimal base = new BigDecimal(0);
			for (com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomRates.RoomRate rr : rs.getRoomRates().getRoomRates()) {
				com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate lrr = new com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate();
				for (com.tayyarah.api.hotel.travelguru.model.RateType.Rate rtype : rr.getRates().getRates()) {
					base = rtype.getBase().getAmountBeforeTax();
					break;
				}
				break;
			}
			BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
			tbasic.setHotelCode(rs.getBasicPropertyInfo().getHotelCode());

			HotelOverview ho = hoteloverviewDao.getHotelOverviewByVendorID(rs.getBasicPropertyInfo().getHotelCode());
			if(ho != null)
			{
				tbasic.setHotelName(ho.getVendorName());
				tbasic.setImageurl(ho.getImagePath());
				Position hpos = new Position();
				hpos.setLongitude(String.valueOf(ho.getLongitude()));
				hpos.setLatitude(String.valueOf(ho.getLatitude()));				
				tbasic.setPosition(hpos);
				AddressType laddress = new AddressType();
				laddress.setCityName(ho.getCity());
				CountryNameType cn = new CountryNameType();
				cn.setCode(ho.getCountry());
				cn.setValue(ho.getCountry());
				laddress.setCountryName(cn);
				List<String> addresslines = new ArrayList<String>();
				addresslines.add(ho.getAddress());
				addresslines.add(ho.getAddress1());
				addresslines.add(ho.getAddress2());
				laddress.setAddressLines(addresslines);
				List<ContactNumber> contactnos = new ArrayList<ContactNumber>();
				contactnos.add(new ContactNumber("80425555555"));
				tbasic.setAddress(laddress);
				tbasic.setContactNumbers(contactnos);
				tbasic.setReviewCount(ho.getReviewCount());
				tbasic.setReviewRating(ho.getReviewRating());
				tbasic.setArea(ho.getArea());
				tbasic.setArea_Seo_Id(ho.getArea_Seo_Id());
				tbasic.setDefaultCheckInTime(ho.getDefaultCheckInTime());
				tbasic.setDefaultCheckOutTime(ho.getDefaultCheckOutTime());
				tbasic.setHotel_Star(ho.getHotel_Star());
				tbasic.setHotelClass(ho.getHotelClass());
				tbasic.setWeekdayRank(ho.getWeekdayRank());
				tbasic.setWeekendRank(ho.getWeekendRank());
				tbasic.setApiPrice(base);
			}
			List<String> images = hotelimagesDao.getImagesByVendorID(rs.getBasicPropertyInfo().getHotelCode());//get image url .. as string list..
			tbasic.setHotelimages(images);
			
			List<Facility> facilities=hotelFacilityDao.getDescriptionByVendorProperty(rs.getBasicPropertyInfo().getHotelCode(), "property");
			tbasic.setHotelAmenities(facilities);
			troomstay.setBasicPropertyInfo(tbasic);			

			tgRoomStaysMap.put(rs.getBasicPropertyInfo().getHotelCode(), rs);	
			roomStaysMap.put(rs.getBasicPropertyInfo().getHotelCode(), troomstay);				
		} 
		//otaHotelAvailRes
		TPAExtensions textensions = new TPAExtensions();
		HotelsInfo thotelsInfo = new HotelsInfo();
		com.tayyarah.api.hotel.travelguru.model.TPAExtensions extensions  = otaHotelAvailRes.getTPAExtensions();
		if(extensions != null && extensions.getHotelsInfo() != null)
		{
			com.tayyarah.api.hotel.travelguru.model.HotelsInfo hotelsInfo = extensions.getHotelsInfo();
			/*<HotelsInfo total="0" minPrice="294.0" maxPrice="37500.0" deals="57" available="55"/>*/
			thotelsInfo.setTotal(hotelsInfo.getTotal());
			thotelsInfo.setMaxPrice(hotelsInfo.getMaxPrice());
			thotelsInfo.setMinPrice(hotelsInfo.getMinPrice());
			thotelsInfo.setDeals(hotelsInfo.getDeals());
			thotelsInfo.setAvailable(hotelsInfo.getAvailable());
			textensions.setHotelsInfo(thotelsInfo);
		}
		apiHotelMap.setTpaExtensions(textensions);
		apiHotelMap.setRoomStays(roomStaysMap);				
	}
	
	public List<String> getApiVendorIdList()
	{
		List<String> hotelidlist = new ArrayList<String>();
		for(com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay rs:otaHotelAvailRes.getRoomStays().getRoomStaies()){ 
			hotelidlist.add(rs.getBasicPropertyInfo().getHotelCode());
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

	public void initHotelsMap() throws HibernateException, IOException, ClassNotFoundException, JAXBException, java.text.ParseException 
	{			
		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();
		TreeMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> tgRoomStaysMap = new TreeMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay>();

		apiHotelMap = new APIHotelMap();	
		apiHotelMap.setRoomStays(roomStaysMap);
		HotelsInfo thotelsinfo =  new HotelsInfo();
		if(otaHotelAvailRes == null || otaHotelAvailRes.getRoomStays()== null || otaHotelAvailRes.getRoomStays().getRoomStaies() == null || otaHotelAvailRes.getRoomStays().getRoomStaies().isEmpty())
			return ;
		List<String> hotelcodelist = new ArrayList<String>();
		hotelcodelist = getApiVendorIdList();
		Map<String, HotelOverview> map = this.hoteloverviewDao.getHotelOverviewNativeMapByVendorIDs(hotelcodelist, "0H");
		Map<String, Integer> hotelidmap = getHotelIdMap(map);
		//Map<Integer, HotelOverview> map = hotelObjectTransformer.getHotelOverviewCommon(hotelcodelist , "2H");
		logger.info(name+" object transformation---: hoteloverview loaded--- "+map.size());		

		Map<String, List<Facility>> hotelfacilitymap = hotelObjectTransformer.getFacilities(hotelidmap, "property");
		logger.info(name+" object transformation---: hotelfacilitymap loaded--- "+hotelfacilitymap.size());

		Map<String, List<Facility>> hotelroomfacilitymap = hotelObjectTransformer.getFacilities(hotelidmap, "room");
		logger.info(name+" object transformation---: hotelroomfacilitymap loaded--- "+hotelroomfacilitymap.size());

		Map<String, List<String>> hotelimagesmap = hotelObjectTransformer.getHotelImages(hotelidmap);
		logger.info(name+" object transformation---: hotelroomfacilitymap loaded--- "+hotelroomfacilitymap.size());		

		Map<String, Map<Integer, Hotelroomdescription>> hotelroommap = hotelObjectTransformer.getHotelRooms(hotelidmap);
		logger.info(name+" object transformation---: hotelroommap loaded--- "+hotelroommap.size());		

		for(com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay rs:otaHotelAvailRes.getRoomStays().getRoomStaies()){ 
			HotelOverview hoteloverview =  map.get(rs.getBasicPropertyInfo().getHotelCode());
			List<Facility> hotelfacilitieslist = hotelfacilitymap.get(rs.getBasicPropertyInfo().getHotelCode());
			List<Facility> hotelRoomfacilitieslist = hotelroomfacilitymap.get(rs.getBasicPropertyInfo().getHotelCode());
			List<String> hotelimages = hotelimagesmap.get(rs.getBasicPropertyInfo().getHotelCode());
			Map<Integer, Hotelroomdescription> hotelroomsmap = hotelroommap.get(rs.getBasicPropertyInfo().getHotelCode());
			if(hoteloverview != null)
			{
				com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay trs = hotelObjectTransformer.convertTGtoNative(api, hs, rs, hoteloverview, hotelfacilitieslist, hotelRoomfacilitieslist, hotelimages, hotelroomsmap);			
				trs.setCity(city);
				roomStaysMap.put(trs.getBasicPropertyInfo().getHotelCode(), trs);
			}					
		} 		
		TPAExtensions textensions = new TPAExtensions();
		HotelsInfo thotelsInfo = new HotelsInfo();
		com.tayyarah.api.hotel.travelguru.model.TPAExtensions extensions  = otaHotelAvailRes.getTPAExtensions();
		if(extensions != null && extensions.getHotelsInfo() != null)
		{
			com.tayyarah.api.hotel.travelguru.model.HotelsInfo hotelsInfo = extensions.getHotelsInfo();
			/*<HotelsInfo total="0" minPrice="294.0" maxPrice="37500.0" deals="57" available="55"/>*/
			thotelsInfo.setTotal(hotelsInfo.getTotal());
			thotelsInfo.setMaxPrice(hotelsInfo.getMaxPrice());
			thotelsInfo.setMinPrice(hotelsInfo.getMinPrice());
			thotelsInfo.setDeals(hotelsInfo.getDeals());
			thotelsInfo.setAvailable(hotelsInfo.getAvailable());
			textensions.setHotelsInfo(thotelsInfo);
		}
		apiHotelMap.setTpaExtensions(textensions);
		apiHotelMap.setRoomStays(roomStaysMap);
	}
	
	public HotelSearchCity getCity() {
		return city;
	}

	public void setCity(HotelSearchCity city) {
		this.city = city;
	}

	/**
	 * @return the api
	 */
	public HotelApiCredentials getApi() {
		return api;
	}

	/**
	 * @param api the api to set
	 */
	public void setApi(HotelApiCredentials api) {
		this.api = api;
	}

	/**
	 * @return the hs
	 */
	public HotelSearchCommand getHs() {
		return hs;
	}

	/**
	 * @param hs the hs to set
	 */
	public void setHs(HotelSearchCommand hs) {
		this.hs = hs;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	public APIHotelMap getApiHotelMap() {
		return apiHotelMap;
	}

	public void setApiHotelMap(APIHotelMap apiHotelMap) {
		this.apiHotelMap = apiHotelMap;
	}

	/**
	 * @return the otaHotelAvailRes
	 */
	public com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS getOtaHotelAvailRes() {
		return otaHotelAvailRes;
	}

	/**
	 * @param otaHotelAvailRes the otaHotelAvailRes to set
	 */
	public void setOtaHotelAvailRes(com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS otaHotelAvailRes) {
		this.otaHotelAvailRes = otaHotelAvailRes;
	}

	public DesiyaPullerTask(String name){
		this.name = name;
	}

}