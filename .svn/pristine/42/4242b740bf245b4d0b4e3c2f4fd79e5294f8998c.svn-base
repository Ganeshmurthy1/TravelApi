package com.tayyarah.hotel.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.tayyarah.api.hotel.travelguru.model.*;
import com.tayyarah.api.hotel.travelguru.model.AvailRequestSegmentsType.AvailRequestSegment;
import com.tayyarah.api.hotel.travelguru.model.AvailRequestSegmentsType.AvailRequestSegment.HotelSearchCriteria;
import com.tayyarah.api.hotel.travelguru.model.HotelReservationsType.HotelReservation;
import com.tayyarah.api.hotel.travelguru.model.HotelSearchCriteriaType.Criterion;
import com.tayyarah.api.hotel.travelguru.model.HotelSearchCriterionType.RateRange;
import com.tayyarah.api.hotel.travelguru.model.HotelSearchCriterionType.RoomStayCandidates;
import com.tayyarah.api.hotel.travelguru.model.ItemSearchCriterionType.Address;
import com.tayyarah.api.hotel.travelguru.model.ItemSearchCriterionType.CodeRef;
import com.tayyarah.api.hotel.travelguru.model.ItemSearchCriterionType.HotelRef;
import com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRQ.AvailRequestSegments;
import com.tayyarah.api.hotel.travelguru.model.SourceType.RequestorID;
import com.tayyarah.api.hotel.travelguru.model.TPAExtensions.Promotion;
import com.tayyarah.common.util.soap.MarshalUnmarshal;
import com.tayyarah.hotel.model.APIHotelBook.RateMap;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelBookCommand.Profile;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelSearchCommand.GuestCount;


public class TGRequestBuilder {
	public static final Logger logger = Logger.getLogger(TGRequestBuilder.class);
	
	/*private static final String SOAP_HEADER = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "<soap:Header><ns3:RequestServerVersion xmlns:ns3=\"http://schemas.microsoft.com/exchange/services/2006/types\" Version=\"Exchange2010_SP2\"/></soap:Header>"
			+ "<soap:Body>";*/
	private static final String SOAP_HEADER = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body>";
	private static final String SOAP_FOOTER = "</soap:Body>"
			+ "</soap:Envelope>";
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
	public static String REZTNEXT_SOAP_CITY_SEARCH = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">"
			+ "<soapenv:Header><wsa:From><wsa:SystemId>Reznext</wsa:SystemId>"
			+ "<wsa:Credential>"
			+ "<wsa:UserName>Reznext123</wsa:UserName><wsa:Password>Reznext@123</wsa:Password>"
			+ "</wsa:Credential>"
			+ "</wsa:From>"
			+ "<wsa:TimeStamp>2015-08-24T15:10:48</wsa:TimeStamp>"
			+ "<wsa:Action>OTA_HotelAvailRQ</wsa:Action>"
			+ "</soapenv:Header>"
			+ "<soapenv:Body>"
			+ "<OTA_HotelAvailRQ Version=\"4.000\" EchoToken=\"12345\" TransactionIdentifier=\"1\" TimeStamp=\"2015-08-24T15:10:48\">"
			+ "<AvailRequestSegments>"
			+ "<AvailRequestSegment>"
			+ "<HotelSearchCriteria>"
			+ "<Criterion>"
			+ "<CityInfo CityName=\"Bengaluru\"/>"
			+ "<StayDateRange Start=\"2015-08-25\" End=\"2015-08-27\"/>"
			+ "<RoomInfo NumberOfRooms=\"1\"/>"
			+ "</Criterion>"
			+ "</HotelSearchCriteria>"
			+ "</AvailRequestSegment>"
			+ "</AvailRequestSegments>"
			+ "</OTA_HotelAvailRQ>"
			+ "</soapenv:Body>"
			+ "</soapenv:Envelope>";
	
	private HotelSearchCity city;	
	
	public static StringBuilder getHotelSearchBodyDesiya(HotelApiCredentials apicred, HotelSearchCommand hs, HotelSearchCity city) throws NumberFormatException, Exception
	{

		/*<OTA_HotelAvailRQ xmlns="http://www.opentravel.org/OTA/2003/05" 
				RequestedCurrency="INR" SortOrder="TG_RANKING"
				Version="0.0" PrimaryLangID="en"
				SearchCacheLevel="VeryRecent">*/
		OTAHotelAvailRQ rq = new OTAHotelAvailRQ();
		rq.setRequestedCurrency(apicred.getApiCurrency());
		rq.setSortOrder(hs.getOrder());
		rq.setVersion(hs.getVersion());
		rq.setPrimaryLangID(hs.getLang());
		rq.setSearchCacheLevel(hs.getCachelevel());
		/*<AvailRequestSegments>
		<AvailRequestSegment> 
		<HotelSearchCriteria> 
		<Criterion>*/
		AvailRequestSegments availsegs = new AvailRequestSegments();
		List<AvailRequestSegment> seglist = new ArrayList<AvailRequestSegment>();		
		AvailRequestSegment seg = new AvailRequestSegment();

		HotelSearchCriteria hotelsearchcriteria = new HotelSearchCriteria();
		List<Criterion> criterions = new ArrayList<Criterion>();
		Criterion criterion = new Criterion();
		Address address = new Address();

		switch (hs.getMode()) {
		case HotelSearchCommand.MODE_SEARCH_SINGLE:
			switch (hs.getType()) {
			case HotelSearchCommand.TYPE_SEARCH_CITY:

				address.setCityName(city.getTgCity().getCity());
				CountryNameType cn = new CountryNameType();
				cn.setCode(city.getTgCity().getCountryName());
				address.setCountryName(cn);
				//DateTimeSpanType staydaterange = criterion.getStayDateRange();
				DateTimeSpanType staydaterange = new DateTimeSpanType();
				staydaterange.setStart(hs.getDatestart());
				staydaterange.setEnd(hs.getDateend());
				criterion.setStayDateRange(staydaterange);
				//criterion.getStayDateRange()setStayDateRange(staydaterange);
				/*<StayDateRange End=\"2015-07-30\" Start=\"2015-07-29\"/>"*/
				
				criterion.setAddress(address);
				break;
			case HotelSearchCommand.TYPE_SEARCH_POI:
				/*	<!—For POI Search: CodeRef indicates 
				Point of Interest. 
				Pass attribute LocationCode ="<POI Internal Id>"
						+ " in CodeRef element e.g. <CodeRef LocationCode="470"/> " --> */

				address.setCityName(city.getTgCity().getCity());
				CountryNameType cnpoi = new CountryNameType();

				address.setCountryName(cnpoi);
				criterion.setAddress(address);

				CodeRef cr = new CodeRef();
				cr.setLocationCode(String.valueOf(hs.getPoi().getPoi_Id()));
				criterion.setCodeRef(cr);

				//loops for adding hotel ref of area id s
				List<HotelRef> hotelrefspoi = new ArrayList<HotelRef>();
				//HotelRef hr = criterion.getHotelRef()
				for (com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef h : hs.getHotelrefs()) {
					HotelRef hr = new HotelRef();
					hr.setAreaID(h.getAreaID());
					hotelrefspoi.add(hr);
				}
				criterion.setHotelReves(hotelrefspoi);
				break;
			case HotelSearchCommand.TYPE_SEARCH_AREA:
				
				address.setCityName(city.getTgCity().getCity());
				CountryNameType cnarea = new CountryNameType();
				address.setCountryName(cnarea);
				criterion.setAddress(address);
				
				//loops for adding hotel ref of area id s
				List<HotelRef> hotelrefs = new ArrayList<HotelRef>();
				//HotelRef hr = criterion.getHotelRef()
				for (com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef h : hs.getHotelrefs()) {
					HotelRef hr = new HotelRef();
					hr.setAreaID(h.getAreaID());
					hotelrefs.add(hr);
				}
				criterion.setHotelReves(hotelrefs);
				break;
			default:
				break;
			}
			break;
		case HotelSearchCommand.MODE_SEARCH_DETAILED:
			//loops for adding hotel ref of area id s
			List<HotelRef> hotelrefs = new ArrayList<HotelRef>();
			//HotelRef hr = criterion.getHotelRef()
			for (com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef h : hs.getHotelrefs()) {
				HotelRef hr = new HotelRef();
				hr.setHotelCode(h.getHotelCode());
				hotelrefs.add(hr);
			}
			criterion.setHotelReves(hotelrefs);
			break;			

		case HotelSearchCommand.MODE_SEARCH_MULTI:

			//loops for adding hotel ref of area id s
			List<HotelRef> hotelrefsm = new ArrayList<HotelRef>();		
			for (com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef h : hs.getHotelrefs()) {
				HotelRef hr = new HotelRef();
				hr.setHotelCode(h.getHotelCode());
				hotelrefsm.add(hr);
			}
			criterion.setHotelReves(hotelrefsm);
			break;	
		default:
			break;
		}
		
		//Filtering..
		switch (hs.getFilter()) {
		case HotelSearchCommand.FILTER_RATE :
			//loops for adding hotel ref of area id s
			List<RateRange> rateranges = new ArrayList<RateRange>();		
			for (com.tayyarah.hotel.model.HotelSearchCriterionType.RateRange r : hs.getRateranges()) {
				RateRange rr = new RateRange();
				rr.setMinRate(r.getMinRate());
				rr.setMaxRate(r.getMaxRate());
				rateranges.add(rr);
			}
			//criterion.setRateRanges(rateranges);
			break;
		default:
			break;
		}
		List<com.tayyarah.hotel.model.HotelSearchCommand.RoomReqInfo> roomReqs = hs.getRoomrequests();
		/*<RoomStayCandidates> 
		<RoomStayCandidate> 
		<GuestCounts> 
		<GuestCount AgeQualifyingCode="10"/> 
		<GuestCount Age="10" AgeQualifyingCode="8"/>
		</GuestCounts>
		</RoomStayCandidate> 
		</RoomStayCandidates>*/
		com.tayyarah.api.hotel.travelguru.model.HotelSearchCriterionType.RoomStayCandidates roomStayCandidates = new RoomStayCandidates();
		List<RoomStayCandidateType> roomStayCandidateList = new ArrayList<RoomStayCandidateType>();
		for (com.tayyarah.hotel.model.HotelSearchCommand.RoomReqInfo roomReq : roomReqs) {
			
			RoomStayCandidateType roomStayCandidateType = new RoomStayCandidateType();
			GuestCountType guestCountType = new GuestCountType();
			List<GuestCountType.GuestCount> guestCounts = new ArrayList<GuestCountType.GuestCount>();
			
			for (int adultindex = 0 ; adultindex< roomReq.getNoofAdult(); adultindex++) {
				GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
				guestCount.setAgeQualifyingCode("10");
				guestCounts.add(guestCount);
			}
			for (Integer childage : roomReq.getChildages()) {
				GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
				guestCount.setAgeQualifyingCode("8");
				guestCount.setAge(childage);
				guestCounts.add(guestCount);	
			}			
			guestCountType.setGuestCounts(guestCounts);
			roomStayCandidateType.setGuestCounts(guestCountType);	
			roomStayCandidateList.add(roomStayCandidateType);
		}		
		roomStayCandidates.setRoomStayCandidates(roomStayCandidateList);
		criterion.setRoomStayCandidates(roomStayCandidates);		
		
		//Add Pagination

		/*<TPA_Extensions> 
		<Pagination enabled="true" hotelsFrom="01" hotelsTo="05"/> <HotelBasicInformation>  
		<Reviews/>
		</HotelBasicInformation> 
		<UserAuthentication password="XXXX" propertyId="XXXXX" username="XXXX"/> 
		<Promotion Type="HOTEL" Name="StayPeriod" /> 
		</TPA_Extensions> 
		</Criterion> */
		//false on paging
		//criterion.getTPAExtensions()
		TPAExtensions ext = new TPAExtensions();
		Pagination pagination = new Pagination();
		pagination.setEnabled(hs.getPagination().isEnabled());
		pagination.setHotelsFrom(hs.getPagination().getFrom());
		pagination.setHotelsTo(hs.getPagination().getTo());
		ext.setPagination(pagination);
		//add hotel basic infor if needed
		HotelBasicInformation hbi = new HotelBasicInformation();
		ext.setHotelBasicInformation(hbi);
		//user authendication
		com.tayyarah.api.hotel.travelguru.model.TPAExtensions.UserAuthentication uauth = new com.tayyarah.api.hotel.travelguru.model.TPAExtensions.UserAuthentication();
		uauth.setPassword(apicred.getPassword());
		uauth.setPropertyId(apicred.getPropertyId());
		uauth.setUsername(apicred.getUserName());
		ext.setUserAuthentication(uauth);

		//add promotion if there..
		Promotion pro = new Promotion();
		pro.setType(hs.getPromotion().getType());
		pro.setName(hs.getPromotion().getName());
		ext.setPromotion(pro);
		criterion.setTPAExtensions(ext);
		criterions.add(criterion);
		hotelsearchcriteria.setCriterions(criterions);	

		seg.setHotelSearchCriteria(hotelsearchcriteria);
		seglist.add(seg);
		availsegs.setAvailRequestSegments(seglist);
		rq.setAvailRequestSegments(availsegs);

		MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(rq,OTAHotelAvailRQ.class);			
		requestStr = requestStr.replace(XML_HEADER, SOAP_HEADER);
		requestStr+=SOAP_FOOTER;
		StringBuilder sdb = new StringBuilder(requestStr);	
		return sdb;
	}

	public static StringBuilder getHotelDetailSearchBodyDesiya(HotelApiCredentials apicred, HotelSearchCommand hs, HotelSearchCity city) throws NumberFormatException, Exception
	{
		/*<OTA_HotelAvailRQ xmlns="http://www.opentravel.org/OTA/2003/05" 
				RequestedCurrency="INR" SortOrder="TG_RANKING"
				Version="0.0" PrimaryLangID="en"
				SearchCacheLevel="VeryRecent">*/
		OTAHotelAvailRQ rq = new OTAHotelAvailRQ();
	
		rq.setRequestedCurrency(apicred.getApiCurrency());
		rq.setSortOrder(hs.getOrder());
		rq.setVersion(hs.getVersion());
		rq.setPrimaryLangID(hs.getLang());
		rq.setSearchCacheLevel(hs.getCachelevel());
		/*<AvailRequestSegments>
		<AvailRequestSegment> 
		<HotelSearchCriteria> 
		<Criterion>*/
		AvailRequestSegments availsegs = new AvailRequestSegments();
		List<AvailRequestSegment> seglist = new ArrayList<AvailRequestSegment>();		
		AvailRequestSegment seg = new AvailRequestSegment();

		HotelSearchCriteria hotelsearchcriteria = new HotelSearchCriteria();
		List<Criterion> criterions = new ArrayList<Criterion>();
		Criterion criterion = new Criterion();
		Address address = new Address();

		switch (hs.getMode()) {
		case HotelSearchCommand.MODE_SEARCH_SINGLE:
			switch (hs.getType()) {
			case HotelSearchCommand.TYPE_SEARCH_CITY:

				address.setCityName(city.getTgCity().getCity());
				CountryNameType cn = new CountryNameType();
				cn.setCode(city.getTgCity().getCountryName());
				address.setCountryName(cn);				
				DateTimeSpanType staydaterange = new DateTimeSpanType();
				staydaterange.setStart(hs.getDatestart());
				staydaterange.setEnd(hs.getDateend());
				criterion.setStayDateRange(staydaterange);				
				criterion.setAddress(address);
				break;
			case HotelSearchCommand.TYPE_SEARCH_POI:
				/*	<!—For POI Search: CodeRef indicates 
				Point of Interest. 
				Pass attribute LocationCode ="<POI Internal Id>"
						+ " in CodeRef element e.g. <CodeRef LocationCode="470"/> " --> */

				address.setCityName(city.getTgCity().getCity());
				CountryNameType cnpoi = new CountryNameType();

				address.setCountryName(cnpoi);
				criterion.setAddress(address);

				CodeRef cr = new CodeRef();
				cr.setLocationCode(String.valueOf(hs.getPoi().getPoi_Id()));
				criterion.setCodeRef(cr);

				//loops for adding hotel ref of area id s
				List<HotelRef> hotelrefspoi = new ArrayList<HotelRef>();			
				for (com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef h : hs.getHotelrefs()) {
					HotelRef hr = new HotelRef();
					hr.setAreaID(h.getAreaID());
					hotelrefspoi.add(hr);
				}
				criterion.setHotelReves(hotelrefspoi);
				break;
			case HotelSearchCommand.TYPE_SEARCH_AREA:

				address.setCityName(city.getTgCity().getCity());
				CountryNameType cnarea = new CountryNameType();
				
				address.setCountryName(cnarea);
				criterion.setAddress(address);

				//loops for adding hotel ref of area id s
				List<HotelRef> hotelrefs = new ArrayList<HotelRef>();
				//HotelRef hr = criterion.getHotelRef()
				for (com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef h : hs.getHotelrefs()) {
					HotelRef hr = new HotelRef();
					hr.setAreaID(h.getAreaID());
					hotelrefs.add(hr);
				}
				criterion.setHotelReves(hotelrefs);
				break;
			default:
				break;
			}
			break;
		case HotelSearchCommand.MODE_SEARCH_DETAILED:

			//loops for adding hotel ref of area id s
			List<HotelRef> hotelrefs = new ArrayList<HotelRef>();
			//HotelRef hr = criterion.getHotelRef()
			for (com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef h : hs.getHotelrefs()) {
				HotelRef hr = new HotelRef();
				hr.setHotelCode(h.getHotelCode());
				hotelrefs.add(hr);
			}
			criterion.setHotelReves(hotelrefs);
			break;
		case HotelSearchCommand.MODE_SEARCH_MULTI:
			
			//loops for adding hotel ref of area id s
			List<HotelRef> hotelrefsm = new ArrayList<HotelRef>();
			//HotelRef hr = criterion.getHotelRef()
			for (com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef h : hs.getHotelrefs()) {
				HotelRef hr = new HotelRef();
				hr.setHotelCode(h.getHotelCode());
				hotelrefsm.add(hr);
			}
			criterion.setHotelReves(hotelrefsm);
			break;	
		default:
			break;
		}
		//Filtering..
		switch (hs.getFilter()) {
		case HotelSearchCommand.FILTER_RATE :
			//loops for adding hotel ref of area id s
			List<RateRange> rateranges = new ArrayList<RateRange>();			
			for (com.tayyarah.hotel.model.HotelSearchCriterionType.RateRange r : hs.getRateranges()) {
				RateRange rr = new RateRange();
				rr.setMinRate(r.getMinRate());
				rr.setMaxRate(r.getMaxRate());
				rateranges.add(rr);
			}
			//criterion.setRateRanges(rateranges);
			break;
		default:
			break;
		}
		List<com.tayyarah.hotel.model.HotelSearchCommand.RoomReqInfo> roomReqs = hs.getRoomrequests();
		/*<RoomStayCandidates> 
		<RoomStayCandidate> 
		<GuestCounts> 
		<GuestCount AgeQualifyingCode="10"/> 
		<GuestCount Age="10" AgeQualifyingCode="8"/>
		</GuestCounts>
		</RoomStayCandidate> 
		</RoomStayCandidates>*/
		com.tayyarah.api.hotel.travelguru.model.HotelSearchCriterionType.RoomStayCandidates roomStayCandidates = new RoomStayCandidates();
		List<RoomStayCandidateType> roomStayCandidateList = new ArrayList<RoomStayCandidateType>();
		for (com.tayyarah.hotel.model.HotelSearchCommand.RoomReqInfo roomReq : roomReqs) {
			
			RoomStayCandidateType roomStayCandidateType = new RoomStayCandidateType();
			GuestCountType guestCountType = new GuestCountType();
			List<GuestCountType.GuestCount> guestCounts = new ArrayList<GuestCountType.GuestCount>();
			
			for (int adultindex = 0 ; adultindex< roomReq.getNoofAdult(); adultindex++) {
				GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
				guestCount.setAgeQualifyingCode("10");
				guestCounts.add(guestCount);
			}
			for (Integer childage : roomReq.getChildages()) {
				GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
				guestCount.setAgeQualifyingCode("8");
				guestCount.setAge(childage);
				guestCounts.add(guestCount);	
			}			
			guestCountType.setGuestCounts(guestCounts);
			roomStayCandidateType.setGuestCounts(guestCountType);	
			roomStayCandidateList.add(roomStayCandidateType);
		}		
		roomStayCandidates.setRoomStayCandidates(roomStayCandidateList);
		criterion.setRoomStayCandidates(roomStayCandidates);		
		
		//Add Pagination

		/*<TPA_Extensions> 
		<Pagination enabled="true" hotelsFrom="01" hotelsTo="05"/> <HotelBasicInformation>  
		<Reviews/>
		</HotelBasicInformation> 
		<UserAuthentication password="XXXX" propertyId="XXXXX" username="XXXX"/> 
		<Promotion Type="HOTEL" Name="StayPeriod" /> 
		</TPA_Extensions> 
		</Criterion> */
		//false on paging
		//criterion.getTPAExtensions()
		TPAExtensions ext = new TPAExtensions();
		Pagination pagination = new Pagination();
		pagination.setEnabled(hs.getPagination().isEnabled());
		pagination.setHotelsFrom(hs.getPagination().getFrom());
		pagination.setHotelsTo(hs.getPagination().getTo());
		ext.setPagination(pagination);
		//add hotel basic infor if needed
		HotelBasicInformation hbi = new HotelBasicInformation();
		ext.setHotelBasicInformation(hbi);
		//user authendication
		com.tayyarah.api.hotel.travelguru.model.TPAExtensions.UserAuthentication uauth = new com.tayyarah.api.hotel.travelguru.model.TPAExtensions.UserAuthentication();
		uauth.setPassword(apicred.getPassword());
		uauth.setPropertyId(apicred.getPropertyId());
		uauth.setUsername(apicred.getUserName());
		ext.setUserAuthentication(uauth);

		//add promotion if there..
		Promotion pro = new Promotion();
		pro.setType(hs.getPromotion().getType());
		pro.setName(hs.getPromotion().getName());
		ext.setPromotion(pro);
		criterion.setTPAExtensions(ext);
		criterions.add(criterion);
		hotelsearchcriteria.setCriterions(criterions);	

		seg.setHotelSearchCriteria(hotelsearchcriteria);
		seglist.add(seg);
		availsegs.setAvailRequestSegments(seglist);
		rq.setAvailRequestSegments(availsegs);

		MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(rq,OTAHotelAvailRQ.class);			
		requestStr = requestStr.replace(XML_HEADER, SOAP_HEADER);
		requestStr+=SOAP_FOOTER;
		StringBuilder sdb = new StringBuilder(requestStr);		
		return sdb;
	}

	
	public static StringBuilder getProvisionalBookingReqSample(HotelApiCredentials apicred, HotelSearchCommand hs, HotelBookCommand hbc, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws ClassNotFoundException, JAXBException
	{
		/*<?xml version="1.0" encoding="UTF-8"?>
		<OTA_HotelResRQ xmlns="http://www.opentravel.org/OTA/2003/05" 
		CorrelationID="1234" TransactionIdentifier="122121"
		Version="1.003">*/
		OTAHotelResRQ rq = new OTAHotelResRQ();
		rq.setCorrelationID("1234");
		rq.setTransactionIdentifier("122121");
		rq.setVersion(new BigDecimal("1.003"));		
		logger.info("OTAHotelResRQ - ---  reservation booking:"+ rq);
		/*<POS> 
		<Source ISOCurrency="INR">
		<RequestorID MessagePassword="xxxxxxxx" ID="xxxxxxxx">
		<CompanyName Code="xxxxxxxx"></CompanyName> 
		</RequestorID> 
		</Source> 
		</POS>*/		
		
		com.tayyarah.api.hotel.travelguru.model.POSType pos = new POSType();
		List<SourceType> sources = new ArrayList<SourceType>();
		for(int s=0; s<1; s++)
		{
			SourceType st = new SourceType();
			RequestorID reqid = new RequestorID();
			reqid.setMessagePassword(apicred.getPassword());
			reqid.setId(apicred.getPropertyId());	
			CompanyNameType company = new CompanyNameType();
			company.setCode(apicred.getUserName());
			reqid.setCompanyName(company);
			st.setRequestorID(reqid);
			sources.add(st);
		}
		pos.setSources(sources);		
		rq.setPos(pos);
		logger.info("OTAHotelResRQ - ---  reservation booking:--- sources added"+ rq);
		
		/*<UniqueID Type="0" ID=""/>*/
		
		List<UniqueIDType> uniqueids = new ArrayList<UniqueIDType>();
		for(int u=0; u<1; u++)
		{
			UniqueIDType uniqueid = new UniqueIDType();
			uniqueid.setId("");
			uniqueid.setType("");
			uniqueids.add(uniqueid);
		}
		
		rq.setUniqueIDs(uniqueids);
		
		HotelReservationsType hotelReservationsType = new HotelReservationsType();
		List<HotelReservation> hotelReservations = new ArrayList<HotelReservation>();
		for(int hr=0; hr<1; hr++)
		{
			HotelReservation hotelReservation = new HotelReservation();
			//Add Roomstays			
			RoomStaysType roomStaysType = new RoomStaysType();				
			List<com.tayyarah.api.hotel.travelguru.model.RoomStaysType.RoomStay> roomstays = new ArrayList<com.tayyarah.api.hotel.travelguru.model.RoomStaysType.RoomStay>();
			for(int r=0; r<1; r++)
			{
				com.tayyarah.api.hotel.travelguru.model.RoomStaysType.RoomStay roomstay = new com.tayyarah.api.hotel.travelguru.model.RoomStaysType.RoomStay();
				/*<RoomTypes> 
				<RoomType NumberOfUnits="2" RoomTypeCode="0000036834"/> 
				</RoomTypes> 
				<RatePlans> 
				<RatePlan RatePlanCode="0000142934"/>
				</RatePlans> 
				<GuestCounts IsPerRoom="false"> 
				                         <GuestCount ResGuestRPH="0" AgeQualifyingCode="10" Count="2"/> 
				<GuestCount ResGuestRPH="0" AgeQualifyingCode="8" Age="9" Count="1"/> 
				<GuestCount ResGuestRPH="0" AgeQualifyingCode="8" Age="10" Count="1"/>
				<GuestCount ResGuestRPH="1" AgeQualifyingCode="10" Count="2" /> 
				<GuestCount ResGuestRPH="1" AgeQualifyingCode="8" Age="9" Count="1" /> 
				<GuestCount ResGuestRPH="1" AgeQualifyingCode="8" Age="10" Count="1" /> 
				</GuestCounts>*/
				com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomTypes roomTypes = new com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomTypes();				
				List<com.tayyarah.api.hotel.travelguru.model.RoomTypeType> roomTypeTypes = new ArrayList<com.tayyarah.api.hotel.travelguru.model.RoomTypeType>();
				for(int rt=0; rt<1; rt++)
				{
					com.tayyarah.api.hotel.travelguru.model.RoomTypeType roomtype = new com.tayyarah.api.hotel.travelguru.model.RoomTypeType();
					roomtype.setNumberOfUnits(new BigInteger("2"));
					roomtype.setRoomTypeCode("0000036834");
					roomTypeTypes.add(roomtype);
				}
				roomTypes.setRoomTypes(roomTypeTypes);
				
				com.tayyarah.api.hotel.travelguru.model.RoomStayType.RatePlans ratePlans = new com.tayyarah.api.hotel.travelguru.model.RoomStayType.RatePlans();				
				List<com.tayyarah.api.hotel.travelguru.model.RatePlanType> ratePlanTypes = new ArrayList<com.tayyarah.api.hotel.travelguru.model.RatePlanType>();
				for(int rpt=0; rpt<1; rpt++)
				{
					com.tayyarah.api.hotel.travelguru.model.RatePlanType ratePlanType = new com.tayyarah.api.hotel.travelguru.model.RatePlanType();
					ratePlanType.setRatePlanCode("0000142934");					
					ratePlanTypes.add(ratePlanType);
				}
				ratePlans.setRatePlen(ratePlanTypes);
				com.tayyarah.api.hotel.travelguru.model.GuestCountType guestCountType = new com.tayyarah.api.hotel.travelguru.model.GuestCountType();	
				guestCountType.setIsPerRoom(false);
				List<com.tayyarah.api.hotel.travelguru.model.GuestCountType.GuestCount> guestCounts = new ArrayList<com.tayyarah.api.hotel.travelguru.model.GuestCountType.GuestCount>();
				for(int gc=0; gc<1; gc++)
				{
					/*<GuestCount ResGuestRPH="0" AgeQualifyingCode="8" Age="10" Count="1"/>*/
					com.tayyarah.api.hotel.travelguru.model.GuestCountType.GuestCount guestCount = new com.tayyarah.api.hotel.travelguru.model.GuestCountType.GuestCount();
					guestCount.setAgeQualifyingCode("8");
					guestCount.setAge(10);
					guestCount.setResGuestRPH("0");
					guestCount.setCount(1);
					guestCounts.add(guestCount);
				}
				guestCountType.setGuestCounts(guestCounts);
				
				roomstay.setRoomTypes(roomTypes);
				roomstay.setRatePlans(ratePlans);
				roomstay.setGuestCounts(guestCountType);
				
				/*<TimeSpan End="2013-05-17" Start="2013-05-11" />
				<Total AmountBeforeTax="194800" CurrencyCode="INR"> 
				<Taxes Amount="40800"/>
				</Total> 
				<BasicPropertyInfo HotelCode="00009581"/>
				<Comments> 
				<Comment> 
				<Text>non-smoking room requested;kingbed</Text>
				</Comment> 
				</Comments> */
				com.tayyarah.api.hotel.travelguru.model.DateTimeSpanType timespan = new DateTimeSpanType();
				timespan.setStart(hs.getDatestart());
				timespan.setEnd(hs.getDateend());
				roomstay.setTimeSpan(timespan);
				com.tayyarah.api.hotel.travelguru.model.TotalType total = new TotalType();
				total.setAmountBeforeTax(new BigDecimal("194800"));
				total.setCurrencyCode(apicred.getApiCurrency());
				com.tayyarah.api.hotel.travelguru.model.TaxesType taxes = new TaxesType();
				taxes.setAmount(new BigDecimal("40800"));
				total.setTaxes(taxes);
				roomstay.setTotal(total);
				//com.tayyarah.api.hotel.travelguru.model.BasicPropertyInfoType property = new BasicPropertyInfoType();
				//property.setHotelCode("00007068");
				//roomstay.setBasicPropertyInfo(property);
				com.tayyarah.api.hotel.travelguru.model.CommentType commentType = new CommentType();				
				List<com.tayyarah.api.hotel.travelguru.model.CommentType.Comment> comments = new ArrayList<com.tayyarah.api.hotel.travelguru.model.CommentType.Comment>();
				for(int c=0; c<1; c++)
				{
					/*<GuestCount ResGuestRPH="0" AgeQualifyingCode="8" Age="10" Count="1"/>*/
					com.tayyarah.api.hotel.travelguru.model.CommentType.Comment comment = new com.tayyarah.api.hotel.travelguru.model.CommentType.Comment();
					comment.setName("non-smoking room requested;kingbed");
					comments.add(comment);
				}
				commentType.setComments(comments);
				roomstay.setComments(commentType);		
				roomstays.add(roomstay);
			}
			roomStaysType.setRoomStaies(roomstays);	
			hotelReservation.setRoomStays(roomStaysType);
			
			com.tayyarah.api.hotel.travelguru.model.ResGuestsType resGuestsType = new com.tayyarah.api.hotel.travelguru.model.ResGuestsType();			
			List<com.tayyarah.api.hotel.travelguru.model.ResGuestType> resGuestTypes = new ArrayList<com.tayyarah.api.hotel.travelguru.model.ResGuestType>();
			for(int rg=0; rg<1; rg++)
			{
				com.tayyarah.api.hotel.travelguru.model.ResGuestType resGuestType = new com.tayyarah.api.hotel.travelguru.model.ResGuestType();				
				com.tayyarah.api.hotel.travelguru.model.ProfilesType profilesType = new com.tayyarah.api.hotel.travelguru.model.ProfilesType();				
				List<com.tayyarah.api.hotel.travelguru.model.ProfilesType.ProfileInfo> profileInfos = new ArrayList<com.tayyarah.api.hotel.travelguru.model.ProfilesType.ProfileInfo>();
				for(int pr=0; pr<1; pr++)
				{
					com.tayyarah.api.hotel.travelguru.model.ProfilesType.ProfileInfo profileInfo = new com.tayyarah.api.hotel.travelguru.model.ProfilesType.ProfileInfo();
					com.tayyarah.api.hotel.travelguru.model.ProfileType profileType = new ProfileType();
					profileType.setProfileType("1");
					com.tayyarah.api.hotel.travelguru.model.CustomerType customer = new CustomerType();					
					List<com.tayyarah.api.hotel.travelguru.model.PersonNameType> personNames = new ArrayList<com.tayyarah.api.hotel.travelguru.model.PersonNameType>();
					for(int pn=0; pn<1; pn++)
					{
						com.tayyarah.api.hotel.travelguru.model.PersonNameType personName = new PersonNameType();
						/*<NamePrefix>Mr.</NamePrefix> 
						<GivenName>Rakesh</GivenName>
						<MiddleName></MiddleName> 
						<Surname>Kumar</Surname>*/
						personName.setSurnamePrefix("Mr.");
						List<String> gnames = new ArrayList<String>();
						gnames.add("Rakesh");
						personName.setGivenNames(gnames);
						List<String> mnames = new ArrayList<String>();
						mnames.add("");
						personName.setMiddleNames(mnames);						
						personName.setSurname("Kumar");		
						personNames.add(personName);					
					}
					customer.setPersonNames(personNames);
					/*<Telephone AreaCityCode="80"
							CountryAccessCode="91" Extension="0" PhoneNumber="8040301003" PhoneTechType="1"/> 
					<Email>abc@gmail.com</Email>
					<Address> 
					<AddressLine>No.13</AddressLine>
					<AddressLine>Grape Garden</AddressLine> 
					<CityName>Bangalore</CityName>
					<PostalCode>560095</PostalCode>
					<StateProv>KA</StateProv>
					<CountryName>India</CountryName> 
					</Address> 
					*/					
					List<com.tayyarah.api.hotel.travelguru.model.CustomerType.Telephone> telephones = new ArrayList<com.tayyarah.api.hotel.travelguru.model.CustomerType.Telephone>();
					for(int t=0; t<1; t++)
					{
						com.tayyarah.api.hotel.travelguru.model.CustomerType.Telephone telephone = new com.tayyarah.api.hotel.travelguru.model.CustomerType.Telephone();
						telephone.setAreaCityCode("80");
						telephone.setCountryAccessCode("91");
						telephone.setExtension("0");
						telephone.setPhoneNumber("8050459818");
						telephone.setPhoneTechType("1");
						telephones.add(telephone);
					}
					customer.setTelephones(telephones);
					List<com.tayyarah.api.hotel.travelguru.model.CustomerType.Email> emails = new ArrayList<com.tayyarah.api.hotel.travelguru.model.CustomerType.Email>();
					for(int e=0; e<1; e++)
					{
						com.tayyarah.api.hotel.travelguru.model.CustomerType.Email email = new com.tayyarah.api.hotel.travelguru.model.CustomerType.Email();
						email.setValue("abc@gmail.com");
						emails.add(email);
					}
					customer.setEmails(emails);
					List<com.tayyarah.api.hotel.travelguru.model.CustomerType.Address> addresses = new ArrayList<com.tayyarah.api.hotel.travelguru.model.CustomerType.Address>();
					for(int e=0; e<1; e++)
					{
						com.tayyarah.api.hotel.travelguru.model.CustomerType.Address address = new com.tayyarah.api.hotel.travelguru.model.CustomerType.Address();
						List<String> addresslines = new ArrayList<String>();
						for(int al=0; al<1; al++)
						{
							addresslines.add("ererewrewrwer ");
						}
						address.setAddressLines(addresslines);
						address.setCityName(hs.getSearchCity().getTgCity().getCity());
						address.setPostalCode("560001");
						StateProvType state= new StateProvType();
						state.setStateCode("KA");
						address.setStateProv(state);						
						addresses.add(address);
					}
					customer.setAddresses(addresses);	
					profileType.setCustomer(customer);
					
					profileInfo.setProfile(profileType);
					profileInfos.add(profileInfo);
				}
				profilesType.setProfileInfos(profileInfos);				
				resGuestType.setProfiles(profilesType);
				resGuestTypes.add(resGuestType);
			}			
			resGuestsType.setResGuests(resGuestTypes);			
			hotelReservation.setResGuests(resGuestsType);
			com.tayyarah.api.hotel.travelguru.model.ResGlobalInfoType resGlobalInfoType = new ResGlobalInfoType();
			com.tayyarah.api.hotel.travelguru.model.GuaranteeType guaranteeType = new GuaranteeType();
			guaranteeType.setGuaranteeType("PrePay");
			resGlobalInfoType.setGuarantee(guaranteeType);
			hotelReservation.setResGlobalInfo(resGlobalInfoType);
			hotelReservations.add(hotelReservation);
		}
		hotelReservationsType.setHotelReservations(hotelReservations);
		rq.setHotelReservations(hotelReservationsType);		
		
		MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(rq,OTAHotelAvailRQ.class);			
		requestStr = requestStr.replace(XML_HEADER, SOAP_HEADER);
		requestStr+=SOAP_FOOTER;
		StringBuilder sdb = new StringBuilder(requestStr);	
		logger.info("OTAHotelResRQ - ---  reservation booking: over---"+ rq.toString());
		return sdb;

	}

	
	public static StringBuilder getProvisionalBookingReq( HotelApiCredentials apicred, HotelTransactionTemp ht, HotelSearchTemp hs, HotelSearchCommand hsc, HotelBookCommand hbc, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws NumberFormatException, Exception
	{
		com.tayyarah.hotel.model.BasicPropertyInfoType basic = rs.getBasicPropertyInfo();
		if(basic != null && basic.getApiProviderBasicMap()!= null && basic.getApiProviderBasicMap().containsKey(HotelApiCredentials.API_DESIA_IND))
			basic = basic.getApiProviderBasicMap().get(HotelApiCredentials.API_DESIA_IND);		
		
		StringBuilder req  = new  StringBuilder();
		StringBuilder reqRoomType  = new  StringBuilder();
		StringBuilder reqRatePlan  = new  StringBuilder();
		
		com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate roomrate = rs.getRoomRates().getRoomRates().get(0);
		reqRoomType.append("<RoomType NumberOfUnits=\""+hbc.getNumberOfUnits()+"\" RoomTypeCode=\""+roomrate.getRoomTypeCode()+"\"/>");
		reqRatePlan.append("<RatePlan RatePlanCode=\""+roomrate.getRatePlanCode()+"\"/>");
			
		StringBuilder reqGuestCount  = new  StringBuilder();
		for (GuestCount guestCount : hsc.getGuestcounts()) {	
			if(guestCount.getAgequalifyingcode().equalsIgnoreCase("10"))				
				reqGuestCount.append("<GuestCount ResGuestRPH=\""+guestCount.getResGuestRPH()+"\" AgeQualifyingCode=\""+guestCount.getAgequalifyingcode()+"\" Count=\""+guestCount.getCount()+"\"/>");
			else
				reqGuestCount.append("<GuestCount ResGuestRPH=\""+guestCount.getResGuestRPH()+"\" AgeQualifyingCode=\""+guestCount.getAgequalifyingcode()+"\" Count=\""+guestCount.getCount()+"\" Age=\""+guestCount.getAge()+"\"/>");
		}
		StringBuilder amountbeforetax  = new  StringBuilder("1970.00");
		StringBuilder amounttax  = new  StringBuilder("1970.00");
		StringBuilder reqProfile  = new  StringBuilder();
		for (Profile p : hbc.getProfiles()) {	
			reqProfile.append("<Profile ProfileType=\"1\">"
					+ "<Customer>"
					+ "<PersonName>"
					+ "<NamePrefix>"+p.getCustomer().getPersonName().getNamePrefix()+"</NamePrefix>"
					+ "<GivenName>"+p.getCustomer().getPersonName().getGivenName()+"</GivenName>"
					+ "<Surname>"+p.getCustomer().getPersonName().getSurname()+"</Surname>"
					+ "</PersonName>"
					+ "<Email>"+p.getCustomer().getEmail()+"</Email>"
					+ "<Address>"
					+ "<AddressLine>"+p.getCustomer().getAddress().getAddressLine().get(0)+"</AddressLine>"
					+ "<CityName>"+p.getCustomer().getAddress().getCityName()+"</CityName>"
					+ "<PostalCode>"+p.getCustomer().getAddress().getPostalCode()+"</PostalCode>"
					+ "<StateProv>"+p.getCustomer().getAddress().getStateProv()+"</StateProv>"
					+ "<CountryName>"+p.getCustomer().getAddress().getCountryName()+"</CountryName>"
					+ "</Address>"
					+ "</Customer>"
					+ "</Profile>");			
		}		
		req.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>"
				+ "<OTA_HotelResRQ CorrelationID=\""+ht.getId()+"\" TransactionIdentifier=\""+ht.getId()+"\" Version=\"1.003\""
				+ "xmlns=\"http://www.opentravel.org/OTA/2003/05\">"
				+ "<POS>"
				+ "<Source ISOCurrency=\""+apicred.getApiCurrency()+"\">"
				+ "<RequestorID MessagePassword=\""+apicred.getPassword()+"\" ID=\""+apicred.getPropertyId()+"\">"
				+ "<CompanyName Code=\""+apicred.getUserName()+"\" />"
				+ "</RequestorID>"
				+ "</Source>"
				+ "</POS>"
				+ "<UniqueID Type=\"\" ID=\"\"/>"
				+ "<HotelReservations>"
				+ "<HotelReservation>"
				+ "<RoomStays>"
				+ "<RoomStay>"
				+ "<RoomTypes>"
				+ 	reqRoomType
				+ "</RoomTypes>"
				+ "<RatePlans>"
				+ 	reqRatePlan
				+ "</RatePlans>"				
				+ "<GuestCounts IsPerRoom=\"false\">"
				+	reqGuestCount
				+ "</GuestCounts>"
				+ "<TimeSpan End=\""+hsc.getDateend()+"\" Start=\""+hsc.getDatestart()+"\"/>"
				+ "<Total AmountBeforeTax=\""+amountbeforetax+"\" CurrencyCode=\""+apicred.getApiCurrency()+"\">"
				+ "<Taxes Amount=\""+amounttax+"\" />"
				+ "</Total>"
				+ "<BasicPropertyInfo HotelCode=\""+basic.getApiVendorID()+"\"/>"
				+ "</RoomStay>"
				+ "</RoomStays>"
				+ "<ResGuests>"
				+ "<ResGuest>"
				+ "<Profiles>"
				+ "<ProfileInfo>"
				+  reqProfile
				+ "</ProfileInfo>"
				+ "</Profiles>"
				+ "</ResGuest>"
				+ "</ResGuests>"
				+ "<ResGlobalInfo>"
				+ "<Guarantee GuaranteeType=\"PrePay\"/>"
				+ "</ResGlobalInfo>"
				+ "</HotelReservation>"
				+ "</HotelReservations>"
				+ "</OTA_HotelResRQ>"
				+ "</soap:Body>"
				+ "</soap:Envelope>");			
		
		logger.info("OTAHotelResRQ - ---  reservation booking:--- sources added"+ req);		
		return req;
	}

	public static StringBuilder getProvisionalBookingReq( HotelApiCredentials apicred, HotelTransactionTemp ht, HotelSearchCommand hsc, HotelBookCommand hbc, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs, RateMap ratemap, HotelOrderRow hor, com.tayyarah.hotel.model.TotalType revisedRate) throws NumberFormatException, Exception
	{		
		com.tayyarah.hotel.model.BasicPropertyInfoType basic = rs.getBasicPropertyInfo();
		if(basic != null && basic.getApiProviderBasicMap()!= null && basic.getApiProviderBasicMap().containsKey(HotelApiCredentials.API_DESIA_IND))
			basic = basic.getApiProviderBasicMap().get(HotelApiCredentials.API_DESIA_IND);

		StringBuilder req  = new  StringBuilder();
		StringBuilder reqRatePlan  = new  StringBuilder();
		StringBuilder reqRoomType  = new  StringBuilder();
		
		com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate roomrate = rs.getRoomRates().getRoomRates().get(0);
		reqRoomType.append("<RoomType NumberOfUnits=\""+hbc.getNumberOfUnits()+"\" RoomTypeCode=\""+roomrate.getRoomTypeCode()+"\"/>");
		reqRatePlan.append("<RatePlan RatePlanCode=\""+roomrate.getRatePlanCode()+"\"/>");
		
		StringBuilder reqGuestCount  = new  StringBuilder();
		for (GuestCount guestCount : hsc.getGuestcounts()) {	
			if(guestCount.getAgequalifyingcode().equalsIgnoreCase("10"))				
				reqGuestCount.append("<GuestCount ResGuestRPH=\""+guestCount.getResGuestRPH()+"\" AgeQualifyingCode=\""+guestCount.getAgequalifyingcode()+"\" Count=\""+guestCount.getCount()+"\"/>");
			else
				reqGuestCount.append("<GuestCount ResGuestRPH=\""+guestCount.getResGuestRPH()+"\" AgeQualifyingCode=\""+guestCount.getAgequalifyingcode()+"\" Count=\""+guestCount.getCount()+"\" Age=\""+guestCount.getAge()+"\"/>");
		}
		StringBuilder amountbeforetax  = new  StringBuilder("1970.00");
		StringBuilder amounttax  = new  StringBuilder("1970.00");
		StringBuilder reqProfile  = new  StringBuilder();
		for (Profile p : hbc.getProfiles()) {	
			reqProfile.append("<Profile ProfileType=\"1\">"
					+ "<Customer>"
					+ "<PersonName>"
					+ "<NamePrefix>"+p.getCustomer().getPersonName().getNamePrefix()+"</NamePrefix>"
					+ "<GivenName>"+p.getCustomer().getPersonName().getGivenName()+"</GivenName>"
					+ "<MiddleName>"+p.getCustomer().getPersonName().getMiddleName()+"</MiddleName>"					
					+ "<Surname>"+p.getCustomer().getPersonName().getSurname()+"</Surname>"
					+ "</PersonName>"
					+ "<Email>"+p.getCustomer().getEmail()+"</Email>"
					+ "<Address>"
					+ "<AddressLine>"+p.getCustomer().getAddress().getAddressLine().get(0)+"</AddressLine>"
					+ "<CityName>"+p.getCustomer().getAddress().getCityName()+"</CityName>"
					+ "<PostalCode>"+p.getCustomer().getAddress().getPostalCode()+"</PostalCode>"
					+ "<StateProv>"+p.getCustomer().getAddress().getStateProv()+"</StateProv>"
					+ "<CountryName>"+p.getCustomer().getAddress().getCountryName()+"</CountryName>"
					+ "</Address>"
					+ "</Customer>"
					+ "</Profile>");			
		}		
		logger.info("OTAHotelResRQ - ---  reservation booking:--- ratemap "+ ratemap.toString());	
		logger.info("OTAHotelResRQ - ---  reservation booking:--- ratemap getAmountbeforeTax "+ ratemap.getAmountbeforeTax());
		if(revisedRate != null){
		logger.info("OTAHotelResRQ - ---  reservation booking:--- revisedRate "+ revisedRate.toString());	
		logger.info("OTAHotelResRQ - ---  reservation booking:--- revisedRate "+ revisedRate.getAmountAfterTax());	
     	}
		StringBuilder reqTotal  = new  StringBuilder();
		if(revisedRate == null)
		{
			reqTotal.append("<Total AmountBeforeTax=\""+ratemap.getAmountbeforeTax()+"\" CurrencyCode=\""+apicred.getApiCurrency()+"\">"
					+ "<Taxes Amount=\""+ratemap.getRoomrateTax()+"\" />"
					+ "</Total>");
		}
		else
		{
			reqTotal.append("<Total AmountBeforeTax=\""+revisedRate.getAmountAfterTax()+"\" CurrencyCode=\""+apicred.getApiCurrency()+"\">"
					+ "<Taxes Amount=\"0\" />"
					+ "</Total>");
		}
		req.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"				
				+ "<OTA_HotelResRQ CorrelationID=\""+hbc.getCorrelationid()+"\" TransactionIdentifier=\""+hbc.getTransactionid()+"\"  Version=\"1.003\""
				+ " xmlns=\"http://www.opentravel.org/OTA/2003/05\">"
				+ "<POS>"
				+ "<Source ISOCurrency=\""+apicred.getApiCurrency()+"\">"
				+ "<RequestorID MessagePassword=\""+apicred.getPassword()+"\" ID=\""+apicred.getPropertyId()+"\">"
				+ "<CompanyName Code=\""+apicred.getUserName()+"\" />"
				+ "</RequestorID>"
				+ "</Source>"
				+ "</POS>"
				+ "<UniqueID Type=\"\" ID=\"\"/>"
				+ "<HotelReservations>"
				+ "<HotelReservation>"
				+ "<RoomStays>"
				+ "<RoomStay>"
				+ "<RoomTypes>"
				+ 	reqRoomType
				+ "</RoomTypes>"
				+ "<RatePlans>"
				+ 	reqRatePlan
				+ "</RatePlans>"				
				+ "<GuestCounts IsPerRoom=\"false\">"
				+	reqGuestCount
				+ "</GuestCounts>"
				+ "<TimeSpan End=\""+hsc.getDateend()+"\" Start=\""+hsc.getDatestart()+"\"/>"
				+	reqTotal
				+ "<BasicPropertyInfo HotelCode=\""+basic.getApiVendorID()+"\"/>"
				+ " <Comments>"
				+ "<Comment>"
				+ "<Text/>"
				+ "</Comment>"
				+ "</Comments>"
				+ "</RoomStay>"
				+ "</RoomStays>"
				+ "<ResGuests>"
				+ "<ResGuest>"
				+ "<Profiles>"
				+ "<ProfileInfo>"
				+  reqProfile
				+ "</ProfileInfo>"
				+ "</Profiles>"
				+ "</ResGuest>"
				+ "</ResGuests>"
				+ "<ResGlobalInfo>"
				+ "<Guarantee GuaranteeType=\"PrePay\"/>"
				+ "</ResGlobalInfo>"
				+ "</HotelReservation>"
				+ "</HotelReservations>"
				+ "</OTA_HotelResRQ>");
		logger.info("OTAHotelResRQ - ---  reservation booking:--- sources added"+ req);		
		return req;
	}

	
	public static StringBuilder getFinalBookingReq( HotelApiCredentials apicred, HotelTransactionTemp ht, HotelSearchTemp hs, HotelSearchCommand hsc, HotelBookCommand hbc, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs, String uniqueType, String uniqueId) throws NumberFormatException, Exception
	{
		StringBuilder req  = new  StringBuilder();		
		req.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>"
				+ "<OTA_HotelResRQ CorrelationID=\""+ht.getId()+"\" TransactionIdentifier=\""+ht.getId()+"\" Version=\"1.003\""
				+ "xmlns=\"http://www.opentravel.org/OTA/2003/05\">"
				+ "<POS>"
				+ "<Source ISOCurrency=\""+apicred.getApiCurrency()+"\">"
				+ "<RequestorID MessagePassword=\""+apicred.getPassword()+"\" ID=\""+apicred.getPropertyId()+"\">"
				+ "<CompanyName Code=\""+apicred.getUserName()+"\" />"
				+ "</RequestorID>"
				+ "</Source>"
				+ "</POS>"
				+ "<UniqueID Type=\""+uniqueType+"\" ID=\""+uniqueId+"\"/>"
				+ "<HotelReservations>"
				+ "<HotelReservation>"			
				+ "<ResGlobalInfo>"
				+ "<Guarantee GuaranteeType=\"PrePay\"/>"
				+ "</ResGlobalInfo>"
				+ "</HotelReservation>"
				+ "</HotelReservations>"
				+ "</OTA_HotelResRQ>"
				+ "</soap:Body>"
				+ "</soap:Envelope>");		
		
		logger.info("OTAHotelResRQ - ---  reservation booking:--- sources added"+ req);		
		return req;
	}
	public HotelSearchCity getCity() {
		return city;
	}
	public void setCity(HotelSearchCity city) {
		this.city = city;
	}
	public TGRequestBuilder(HotelSearchCity city) {
		this.city = city;
	}	
}