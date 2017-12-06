package com.tayyarah.hotel.util.api.concurrency;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.ProtocolException;
import java.nio.charset.Charset;
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
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS;
import com.tayyarah.api.hotel.reznext.model.OTAHotelResNotifRS;
import com.tayyarah.api.hotel.reznext.model.RoomInfoType;
import com.tayyarah.common.util.soap.HttpPostClient;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.AddressType;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.CountryNameType;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelBasicInformation;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelOverview;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.HotelsInfo;

import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.RezNextRequestBuilder;
import com.tayyarah.hotel.util.RezNextResponseParser;
import com.tayyarah.hotel.model.BasicPropertyInfoType.Position;
import com.tayyarah.hotel.model.BasicPropertyInfoType.ContactNumbers.ContactNumber;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;



public class RezNextPullerTask  implements Callable<APIHotelMap> {

	public RezNextPullerTask(HotelApiCredentials api, HotelSearchCommand hs, String name, String actionname) {
		super();
		this.api = api;
		this.hs = hs;
		this.name = name;
		this.actionname = actionname;
		this.apiHotelMap = new APIHotelMap();
		this.reqbuilder = new RezNextRequestBuilder();
		this.resparser = new RezNextResponseParser();
	}

	public static final Logger logger = Logger.getLogger(RezNextPullerTask.class);

	public HotelBookCommand getHb() {
		return hb;
	}

	public void setHb(HotelBookCommand hb) {
		this.hb = hb;
	}

	

	public RoomInfoType getRoomInfoType() {
		return roomInfoType;
	}

	public void setRoomInfoType(RoomInfoType roomInfoType) {
		this.roomInfoType = roomInfoType;
	}

	public APIHotelMap getApiHotelMap() {
		return apiHotelMap;
	}

	public void setApiHotelMap(APIHotelMap apiHotelMap) {
		this.apiHotelMap = apiHotelMap;
	}

	public RezNextPullerTask(HotelApiCredentials api, HotelSearchCommand hs, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay roomStay,
			RoomInfoType roomInfoType, String name,
			String actionname, int x) {
		super();
		this.api = api;
		this.hs = hs;
		this.roomStay = roomStay;
		this.roomInfoType = roomInfoType;
		this.name = name;
		this.actionname = actionname;
		this.apiHotelMap = new APIHotelMap();
		this.reqbuilder = new RezNextRequestBuilder();
		this.resparser = new RezNextResponseParser();
	}

	/**
	 * @return the otaHotelAvailRes
	 */
	public com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS getOtaHotelAvailRes() {
		return otaHotelAvailRes;
	}

	/**
	 * @param otaHotelAvailRes the otaHotelAvailRes to set
	 */
	public void setOtaHotelAvailRes(com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS otaHotelAvailRes) {
		this.otaHotelAvailRes = otaHotelAvailRes;
	}

	public RezNextPullerTask(HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();
		this.api = api;
		this.hs = hs;
		this.name = name;
		this.actionname = ACTION_GETHOTELINFOBYCITY;
		this.apiHotelMap = new APIHotelMap();
		this.reqbuilder = new RezNextRequestBuilder();
		this.resparser = new RezNextResponseParser();
	}
	public RezNextPullerTask(HotelObjectTransformer hotelObjectTransformer, HoteloverviewDao hoteloverviewDao, HotelroomdescriptionDao hotelroomdescriptionDao,
			HotelimagesDao hotelimagesDao, HotelFacilityDao hotelFacilityDao,
			IslhotelmappingDao islhotelmappingDao, HotelinandaroundDao hotelinandaroundDao,
			HotelsecondaryareaDao hotelsecondaryareaDao, HotelApiCredentials api, HotelSearchCommand hs, String name) {
		super();	
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
		this.actionname = ACTION_GETHOTELINFOBYCITY;
		this.apiHotelMap = new APIHotelMap();
		this.reqbuilder = new RezNextRequestBuilder();
		this.resparser = new RezNextResponseParser();
	}

	public RezNextPullerTask(HotelObjectTransformer hotelObjectTransformer, HoteloverviewDao hoteloverviewDao, HotelroomdescriptionDao hotelroomdescriptionDao,
			HotelimagesDao hotelimagesDao, HotelFacilityDao hotelFacilityDao,
			IslhotelmappingDao islhotelmappingDao, HotelinandaroundDao hotelinandaroundDao,
			HotelsecondaryareaDao hotelsecondaryareaDao, HotelApiCredentials api, HotelSearchCommand hs, String name, String action) {
		super();	
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
		this.actionname = action;
		this.apiHotelMap = new APIHotelMap();
		this.reqbuilder = new RezNextRequestBuilder();
		this.resparser = new RezNextResponseParser();
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
	private com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS otaHotelAvailRes;
	private RezNextRequestBuilder reqbuilder;
	private RezNextResponseParser resparser;
	private APIHotelMap apiHotelMap;


	private HotelBookCommand hb;
	private com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay roomStay;
	public com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay getRoomStay() {
		return roomStay;
	}

	public void setRoomStay(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay roomStay) {
		this.roomStay = roomStay;
	}

	//private String hotelCode;
	private RoomInfoType roomInfoType;	
	private String actionname;
	public static final String ACTION_GETHOTELINFOBYCITY = "GetHotelInfoByCity";
	public static final String ACTION_GETHOTELINFOBYHOTELID  = "GetHotelInfoByHotelId";
	public static final String ACTION_GETBOOKING  = "GetBooking";
	
	public void citySearchReztnext(StringBuilder soapmessage) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException
	{
		this.otaHotelAvailRes = new OTAHotelAvailRS();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = postclient.sendPost(soapmessage, ACTION_GETHOTELINFOBYCITY);
		int start = res.indexOf("<OTA_HotelAvailRS");
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
				this.otaHotelAvailRes = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS)unmarshaller.unmarshal(reader);				
			}			
		}		
	}
	public void hotelSearchReztnext(StringBuilder soapmessage) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException
	{
		this.otaHotelAvailRes = new OTAHotelAvailRS();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = postclient.sendPost(soapmessage, ACTION_GETHOTELINFOBYHOTELID);
		int start = res.indexOf("<OTA_HotelAvailRS");
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
				this.otaHotelAvailRes = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS)unmarshaller.unmarshal(reader);				
			}			
		}		
	}

	public void BookingReztnext(StringBuilder soapmessage) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException
	{
		this.otaHotelAvailRes = new OTAHotelAvailRS();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = postclient.sendPost(soapmessage, ACTION_GETBOOKING);
		int start = res.indexOf("<OTA_HotelAvailRS");
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
				this.otaHotelAvailRes = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS)unmarshaller.unmarshal(reader);				
			}			
		}		
	}
	public com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay GetHotelRoomDetail(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws NumberFormatException, Exception
	{		
		com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay trs = rs;
		StringBuilder reqsoap = new StringBuilder();
		reqsoap = reqbuilder.getHotelDetailSearchRequestBodyReznext(api, hs, rs);
		this.otaHotelAvailRes = new OTAHotelAvailRS();
		HttpPostClient postclient = new HttpPostClient(this.api);
		StringBuilder res = postclient.sendPost(reqsoap, ACTION_GETHOTELINFOBYHOTELID);
		int start = res.indexOf("<OTA_HotelAvailRS");
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
				this.otaHotelAvailRes = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS)unmarshaller.unmarshal(reader);				
				trs = resparser.convertReznettoNativeRoomDetail(hs, rs, this.otaHotelAvailRes);
			}			
		}
		return trs;		
	}	

	public APIHotelBook getModificationBookingRes(APIHotelBook apiHotelBook, HotelApiCredentials apirezt, HotelObjectTransformer hotelObjectTransformer, StringBuilder reqsoap) throws ClassNotFoundException, JAXBException, IOException, UnsupportedOperationException,SOAPException
	{		
		HttpPostClient postclient = new HttpPostClient(apirezt);
		StringBuilder res = postclient.sendPost(reqsoap, ACTION_GETBOOKING);
		int start = res.indexOf("<OTA_HotelResNotifRS");
		com.tayyarah.api.hotel.reznext.model.OTAHotelResNotifRS otaHotelResNotifRS = new OTAHotelResNotifRS();
		com.tayyarah.hotel.model.OTAHotelResRS totaHotelResRS = new OTAHotelResRS();
		if(start != -1 )
		{
			res.delete(0, start);
			int end = res.indexOf("</soapenv:Body>");
			if(end!= -1)
			{
				res.delete(end-1, res.length()-1);
				res.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				//logger.info("-------------((((("+apirezt.getApiProviderName()+" Booking response trimmed raw data : "+res);				
				Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.reznext.model.OTAHotelResNotifRS.class).createUnmarshaller();
				StringReader reader = new StringReader(res.toString());
				otaHotelResNotifRS = (com.tayyarah.api.hotel.reznext.model.OTAHotelResNotifRS)unmarshaller.unmarshal(reader);				
				//logger.info("-------------((((("+apirezt.getApiProviderName()+" Hotel response unmarshalled otaHotelAvailRS data : "+otaHotelResNotifRS);
				apiHotelBook = resparser.convertReztoNativeFinalBookResponse(apiHotelBook, otaHotelResNotifRS);
				
			}			
		}
		return apiHotelBook;	
	}
	
	public APIHotelBook getMockPreBookingRes(HotelApiCredentials apirezt, HotelObjectTransformer hotelObjectTransformer, APIHotelBook apiHotelBook) throws ClassNotFoundException, JAXBException, IOException, UnsupportedOperationException,SOAPException
	{		
		com.tayyarah.hotel.model.OTAHotelResRS totaHotelResRS = new OTAHotelResRS();
		apiHotelBook = resparser.convertReztoNativePreBookResponse( apiHotelBook);		
		return apiHotelBook;	
	}
	
	@Override
	public APIHotelMap call() throws Exception {

		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
		apiHotelMap = new APIHotelMap();		
		HotelsInfo thotelsinfo =  new HotelsInfo();
		apiHotelMap.setRoomStays(roomStaysMap);
		
		
		//logger.info(name + " is pulling started------------");
		if(actionname.equalsIgnoreCase(ACTION_GETHOTELINFOBYCITY))
		{
			StringBuilder reqsoap = new StringBuilder();

			try {
				reqsoap = reqbuilder.getHotelSearchBodyReznext(this.api, hs);
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: "+reqsoap);
				citySearchReztnext(reqsoap);
				initHotelsMap();
				//logger.info("-------------((((("+name+" Searhing hotels otaHotelAvailRes: "+otaHotelAvailRes);
			} catch (ClassNotFoundException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: ClassNotFoundException--"+e.getMessage());
				e.printStackTrace();
			} catch (JAXBException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: JAXBException--"+e.getMessage());
				e.printStackTrace();
			}	
			catch (IOException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: IOException--"+e.getMessage());
				e.printStackTrace();
			}
			catch (UnsupportedOperationException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: UnsupportedOperationException--"+e.getMessage());
				e.printStackTrace();
			} catch (SOAPException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: SOAPException--"+e.getMessage());
				e.printStackTrace();
			}
			//logger.info(name + " is pulling over------------");			
		}
		if(actionname.equalsIgnoreCase(ACTION_GETHOTELINFOBYHOTELID))
		{
			StringBuilder reqsoap = new StringBuilder();

			try {
				//reqsoap = HotelRequestBuilder.getHotelSearchBodyReznext(api, hs);
				reqsoap = reqbuilder.getHotelDetailSearchRequestBodyReznext(api, hs, this.roomStay);

				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: "+reqsoap);
				hotelSearchReztnext(reqsoap);
				initHotelsMap();
				//logger.info("-------------((((("+name+" Searhing hotels otaHotelAvailRes: "+otaHotelAvailRes);
			} catch (ClassNotFoundException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: ClassNotFoundException--"+e.getMessage());
				e.printStackTrace();
			} catch (JAXBException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: JAXBException--"+e.getMessage());
				e.printStackTrace();
			}	
			catch (IOException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: IOException--"+e.getMessage());
				e.printStackTrace();
			}
			catch (UnsupportedOperationException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: UnsupportedOperationException--"+e.getMessage());
				e.printStackTrace();
			} catch (SOAPException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: SOAPException--"+e.getMessage());
				e.printStackTrace();
			}
			//logger.info(name + " is pulling over------------");			
		}
		/*if(actionname.equalsIgnoreCase(ACTION_GETBOOKING))
		{
			StringBuilder reqsoap = new StringBuilder();			
			try {
				//reqsoap = HotelRequestBuilder.getHotelSearchBodyReznext(api, hs);
				reqsoap = RezNextRequestBuilder.getBookingReq(api, hs,  this.hb, this.otaHotelAvailRes);				
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: "+reqsoap);
				hotelSearchReztnext(reqsoap);
				initHotelsMap();
				//logger.info("-------------((((("+name+" Searhing hotels otaHotelAvailRes: "+otaHotelAvailRes);
			} catch (ClassNotFoundException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: ClassNotFoundException--"+e.getMessage());
				e.printStackTrace();
			} catch (JAXBException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: JAXBException--"+e.getMessage());
				e.printStackTrace();
			}	
			catch (IOException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: IOException--"+e.getMessage());
				e.printStackTrace();
			}
			catch (UnsupportedOperationException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: UnsupportedOperationException--"+e.getMessage());
				e.printStackTrace();
			} catch (SOAPException e) {
				//logger.info("-------------((((("+name+" Searhing hotels reqsoap: SOAPException--"+e.getMessage());
				e.printStackTrace();
			}
			//logger.info(name + " is pulling over------------");			
		}
		 */
		return apiHotelMap;
	}


	public void initHotelsMap() throws HibernateException, ClassNotFoundException, JAXBException 
	{	
		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
		apiHotelMap = new APIHotelMap();		
		HotelsInfo thotelsinfo =  new HotelsInfo();
		apiHotelMap.setRoomStays(roomStaysMap);
		if(otaHotelAvailRes == null || otaHotelAvailRes.getRoomStays()== null || otaHotelAvailRes.getRoomStays().getRoomStaies() == null || otaHotelAvailRes.getRoomStays().getRoomStaies().isEmpty())
			return ;		

		

		List<String> hotelcodelist = new ArrayList<String>();
		hotelcodelist = getApiVendorIdList();
		Map<String, HotelOverview> map = this.hoteloverviewDao.getHotelOverviewNativeMapByVendorIDs(hotelcodelist, "1H");
		Map<String, Integer> hotelidmap = getHotelIdMap(map);
		//Map<Integer, HotelOverview> map = hotelObjectTransformer.getHotelOverviewCommon(hotelcodelist , "2H");
		//logger.info(name+" object transformation---: hoteloverview loaded--- "+map.size());		

		Map<String, List<Facility>> hotelfacilitymap = hotelObjectTransformer.getFacilities(hotelidmap, "property");
		//logger.info(name+" object transformation---: hotelfacilitymap loaded--- "+hotelfacilitymap.size());

		Map<String, List<Facility>> hotelroomfacilitymap = hotelObjectTransformer.getFacilities(hotelidmap, "room");
		//logger.info(name+" object transformation---: hotelroomfacilitymap loaded--- "+hotelroomfacilitymap.size());

		Map<String, List<String>> hotelimagesmap = hotelObjectTransformer.getHotelImages(hotelidmap);
		//logger.info(name+" object transformation---: hotelroomfacilitymap loaded--- "+hotelroomfacilitymap.size());		

		Map<String, Map<Integer, Hotelroomdescription>> hotelroommap = hotelObjectTransformer.getHotelRooms(hotelidmap);
		//logger.info(name+" object transformation---: hotelroommap loaded--- "+hotelroommap.size());		
		
		
		//logger.info(name+"  object transformation---:RoomStays size "+otaHotelAvailRes.getRoomStays().getRoomStaies().size());

		for(com.tayyarah.api.hotel.reznext.model.RoomStayType rs:otaHotelAvailRes.getRoomStays().getRoomStaies()){ 			
			HotelOverview hoteloverview =  map.get(getApiVendorIdDummy(rs.getBasicPropertyInfo().getHotelCode()));
			List<Facility> hotelfacilitieslist = hotelfacilitymap.get(getApiVendorIdDummy(rs.getBasicPropertyInfo().getHotelCode()));
			List<Facility> hotelRoomfacilitieslist = hotelroomfacilitymap.get(getApiVendorIdDummy(rs.getBasicPropertyInfo().getHotelCode()));
			List<String> hotelimages = hotelimagesmap.get(getApiVendorIdDummy(rs.getBasicPropertyInfo().getHotelCode()));
			Map<Integer, Hotelroomdescription> hotelroomsmap = hotelroommap.get(getApiVendorIdDummy(rs.getBasicPropertyInfo().getHotelCode()));
			//logger.info(name+" puller task initmap call:loading static data --vendor hotelcode dummy--"+getApiVendorIdDummy(rs.getBasicPropertyInfo().getHotelCode()));			
			//logger.info(name+" puller task initmap call:loading static data --vendor hotelcode original--"+rs.getBasicPropertyInfo().getHotelCode());	
			//logger.info(name+" puller task initmap call:loading static data --hotelcode--"+rs.getBasicPropertyInfo().getHotelCode()+"----hoteloverview="+hoteloverview);	
			if(hoteloverview != null)
			{
				com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay trs = resparser.convertRezNexttoNative(rs, hoteloverview, hotelfacilitieslist, hotelRoomfacilitieslist, hotelimages, hotelroomsmap);			
				
				roomStaysMap.put(String.valueOf(hotelidmap.get(getApiVendorIdDummy(rs.getBasicPropertyInfo().getHotelCode()))), trs);
				//logger.info(name+" is being mapped to--"+String.valueOf(hotelidmap.get(getApiVendorIdDummy(rs.getBasicPropertyInfo().getHotelCode())))+"----roomstay="+trs.getBasicPropertyInfo().getHotelName());	
			}				

		} 
		apiHotelMap.setRoomStays(roomStaysMap);
		//apiHotelMap.setTgRoomStays(tgRoomStaysMap);			

	}
	
	
	public List<String> getApiVendorIdList()
	{
		Integer DBdummyvalue = 45832;
		List<String> hotelidlist = new ArrayList<String>();
		for(com.tayyarah.api.hotel.reznext.model.RoomStayType rs:otaHotelAvailRes.getRoomStays().getRoomStaies()){ 
			RoomStay troomstay = new RoomStay();
			////logger.info("object transformation---:ts name  "+rs.getBasicPropertyInfo().getHotelName());

			BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
			if(rs.getBasicPropertyInfo() != null)
			{	
				Integer apiVendorId = Integer.valueOf(rs.getBasicPropertyInfo().getHotelCode());
				Integer apiVendorIdFinal = DBdummyvalue + apiVendorId;
				hotelidlist.add(String.valueOf((apiVendorIdFinal)));
			}
		}
		return hotelidlist;				
	}

	public static String getApiVendorIdDummy(String original)
	{
		Integer DBdummyvalue = 45832;
		Integer apiVendorId = Integer.valueOf(original);
		Integer apiVendorIdFinal = DBdummyvalue + apiVendorId;
		return String.valueOf((apiVendorIdFinal));				
	}
	public static String getApiVendorIdOriginal(String dummy)
	{
		Integer DBdummyvalue = 45832;
		Integer apiVendorIdDummy = Integer.valueOf(dummy);
		Integer apiVendorIdFinal = DBdummyvalue - apiVendorIdDummy - DBdummyvalue;
		return String.valueOf((apiVendorIdFinal));				
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
