package com.tayyarah.hotel.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;


import com.tayyarah.api.hotel.tbo.model.HotelSearchRequest;
import com.tayyarah.api.hotel.tbo.model.Price;
import com.tayyarah.api.hotel.tbo.model.RoomCombinations;
import com.tayyarah.api.hotel.tbo.model.RoomGuest;
import com.tayyarah.api.hotel.tbo.block.model.HotelBlockRequest;
import com.tayyarah.api.hotel.tbo.block.model.HotelRoomsDetail;
import com.tayyarah.api.hotel.tbo.book.model.HotelBookRequest;
import com.tayyarah.api.hotel.tbo.book.model.HotelPassenger;
import com.tayyarah.api.hotel.tbo.cancel.model.GetChangeRequestStatus;
import com.tayyarah.api.hotel.tbo.cancel.model.SendChangeRequest;
import com.tayyarah.api.hotel.tbo.cancel.model.SendChangeResponse;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.TayyarahGSTDetails;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRowCancellation;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.model.APIHotelCancelRequest;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelBookCommand.Profile;
import com.tayyarah.hotel.model.HotelSearchCommand.GuestCount;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.model.TotalType;

public class TBORequestBuilder {
	public static final Logger logger = Logger.getLogger(TBORequestBuilder.class);	

	private HotelSearchCity city;
	public HotelSearchCity getCity() {
		return city;
	}

	public void setCity(HotelSearchCity city) {
		this.city = city;
	}
	public TBORequestBuilder(HotelSearchCity city) {
		this.city = city;
	}
	
	
	public String dateNative(String commonDateStr) throws NumberFormatException, Exception//"yyyy-MM-dd" to "dd/MM/yyyy"
	{
		DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");// 20120821
		DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");//18/10/2016
		Date datestart = originalFormat.parse(commonDateStr);		
		String formattedDate = targetFormat.format(datestart);  
		return formattedDate;

	}

	public HotelSearchRequest getHotelSearchReq(HotelApiCredentials apicred, HotelSearchCommand hs) throws NumberFormatException, Exception
	{			
		  /*		 
		   "RoomGuests": [
		       {
		           "NoOfAdults": 2,
		           "NoOfChild": 1,
		           "ChildAge": [
		               "7"
		           ]
		       },
		      {
		           "NoOfAdults": 1,
		           "NoOfChild": 1,
		           "ChildAge": [
		               "7"
		           ]
		       },
		      {
		           "NoOfAdults": 3,
		           "NoOfChild": 1,
		           "ChildAge": [
		               "7"
		           ]
		       }
		   ]
		}*/
		List<HotelSearchCommand.RoomReqInfo> roomReqs = hs.getRoomrequests();
		List<RoomGuest> RoomGuests = new ArrayList<RoomGuest>();		
		for (HotelSearchCommand.RoomReqInfo roomReq : roomReqs) {			
			
			RoomGuest RoomGuest = new RoomGuest();
			RoomGuest.setNoOfAdults(roomReq.getNoofAdult());
			RoomGuest.setNoOfChild(roomReq.getNoofChild());
			List<Integer> ChildAge = new ArrayList<Integer>();		
			for (int childageindex = 0; childageindex< roomReq.getChildages().size();  childageindex++) {
				Integer childage  = roomReq.getChildages().get(childageindex);				
				ChildAge.add(childage);				
			}
			RoomGuest.setChildAge(ChildAge);
			RoomGuests.add(RoomGuest);			
		}
		
		HotelSearchRequest hotelSearchRequest = new HotelSearchRequest();	
		hotelSearchRequest.setBookingMode(5);
		hotelSearchRequest.setCheckInDate(dateNative(hs.getDatestart()));
		hotelSearchRequest.setNoOfNights(CommonUtil.getNoofStayDays(hs));
		hotelSearchRequest.setTokenId(apicred.getTokenId());
		if(city.getTboCity()!=null){
			 hotelSearchRequest.setCountryCode(city.getTboCity().getCountrycode());
			 hotelSearchRequest.setCityId(city.getTboCity().getCityid());
		}			
		hotelSearchRequest.setResultCount(0);	
		hotelSearchRequest.setPreferredCurrency("INR");
		hotelSearchRequest.setGuestNationality("IN");
		hotelSearchRequest.setNoOfRooms(hs.getNoofrooms());
		hotelSearchRequest.setPreferredHotel("");;
		hotelSearchRequest.setMinRating(1);
		hotelSearchRequest.setMaxRating(5);
		hotelSearchRequest.setIsNearBySearchAllowed(false);
		hotelSearchRequest.setEndUserIp(hs.getEndUserIp());
		hotelSearchRequest.setTokenId(apicred.getTokenId());
		hotelSearchRequest.setRoomGuests(RoomGuests);
		return hotelSearchRequest;
	}
	
	public HotelBlockRequest getHotelBlockRequest(HotelApiCredentials apicred, HotelSearchCommand hs , HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay rs) throws NumberFormatException, Exception
	{		
		BasicPropertyInfoType basic = rs.getBasicPropertyInfo();
		if(basic != null && basic.getApiProviderBasicMap()!= null && basic.getApiProviderBasicMap().containsKey(HotelApiCredentials.API_TBO_INTERNATIONAL))
			basic = basic.getApiProviderBasicMap().get(HotelApiCredentials.API_TBO_INTERNATIONAL);
		
		
		HotelBlockRequest hotelBlockRequest = new HotelBlockRequest();
		hotelBlockRequest.setResultIndex(basic.getApiResultIndex());		
		hotelBlockRequest.setHotelCode(basic.getApiVendorID());
		hotelBlockRequest.setHotelName(basic.getHotelName());
		hotelBlockRequest.setGuestNationality(hs.getCountrycode());
		hotelBlockRequest.setNoOfRooms(hs.getNoofrooms());
		hotelBlockRequest.setClientReferenceNo(0);
		hotelBlockRequest.setIsVoucherBooking(true);
		int noofdays = CommonUtil.getNoofStayDays(hs);
		logger.info("-------------((((( Hotel getHotelBlockRequest call  : rate--");

		
		int roomcount = 0;
		
		List<HotelRoomsDetail> HotelRoomsDetails = new ArrayList<HotelRoomsDetail>();
		
		int roomreqindex = 0;
		for (RoomRate roomrate : rs.getRoomRates().getRoomRates()) {
			HotelRoomsDetail hotelRoomsDetail = new HotelRoomsDetail();
			hotelRoomsDetail.setRoomIndex(roomrate.getSupplierRoomIndex());
			hotelRoomsDetail.setRoomTypeCode(roomrate.getRoomTypeCode());
			hotelRoomsDetail.setRoomTypeName(roomrate.getRoomTypeName());
			hotelRoomsDetail.setRatePlanCode(roomrate.getRatePlanCode());			 
            //hotelRoomsDetail.setHotelSupplements(null);
			hotelRoomsDetail.setBedTypeCode(null);
			hotelRoomsDetail.setSmokingPreference("0");
			hotelRoomsDetail.setSupplements(null);
			TotalType apiRate = roomrate.getRates().getRates().get(0).getApiPrice();
			
			
			Price price = new Price();
			price.setCurrencyCode(apicred.getApiCurrency());
			price.setRoomPrice(apiRate.getTotalRoomPrice());
			price.setTax(apiRate.getTotalTax());
			price.setExtraGuestCharge(apiRate.getTotalExtraGuestCharge());
			price.setChildCharge(apiRate.getTotalChildCharge());
			price.setOtherCharges(apiRate.getTotalOtherCharges());
			price.setDiscount(apiRate.getTotalDiscount());
			price.setPublishedPrice(apiRate.getApiPublishedPriceTotal());
			price.setPublishedPriceRoundedOff(apiRate.getApiPublishedPriceTotal().setScale(0, BigDecimal.ROUND_HALF_UP));
			price.setOfferedPrice(apiRate.getTotalOfferedPrice());
			price.setOfferedPriceRoundedOff(apiRate.getTotalOfferedPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			price.setAgentCommission(apiRate.getTotalAgentCommission());
			price.setAgentMarkUp(apiRate.getTotalAgentMarkUp());
			price.setServiceTax(apiRate.getTotalServiceTax());
			price.setTDS(apiRate.getTotalTDS());
			hotelRoomsDetail.setPrice(price);
			List<com.tayyarah.api.hotel.tbo.block.model.HotelPassenger> HotelPassengers = getBlockRoomHotelPassengers(hbc, roomreqindex);
			
			hotelRoomsDetail.setHotelPassenger(HotelPassengers);
			
			HotelRoomsDetails.add(hotelRoomsDetail);
			
			//HotelRoomsDetails.add(hotelRoomsDetail);
			roomreqindex ++;
			
		}
		
		hotelBlockRequest.setHotelRoomsDetails(HotelRoomsDetails);
		hotelBlockRequest.setEndUserIp(basic.getApiEndUserIp());
		hotelBlockRequest.setTokenId(basic.getApiTokenId());
		hotelBlockRequest.setTraceId(basic.getApiTraceId());		
	
		return hotelBlockRequest;
	}
	
	
	
	public static List<com.tayyarah.api.hotel.tbo.block.model.HotelPassenger> getBlockRoomHotelPassengers(HotelBookCommand hbc, Integer currentRoomIndex)
	{
		List<com.tayyarah.api.hotel.tbo.block.model.HotelPassenger> HotelPassengers = new ArrayList<com.tayyarah.api.hotel.tbo.block.model.HotelPassenger>();		
		for (Profile p : hbc.getProfiles()) {	
			Integer roomreqindexprofile = Integer.valueOf(p.getResGuestRPH());
			//roomreqindexprofile = roomreqindexprofile + 1;
			////lot of checkings to be done.....
			if(currentRoomIndex.compareTo(roomreqindexprofile) == 0)
			{
				com.tayyarah.api.hotel.tbo.block.model.HotelPassenger hotelPassenger = new com.tayyarah.api.hotel.tbo.block.model.HotelPassenger();
				hotelPassenger.setTitle(p.getCustomer().getPersonName().getNamePrefix());
				hotelPassenger.setFirstname(p.getCustomer().getPersonName().getGivenName());
				hotelPassenger.setMiddlename(p.getCustomer().getPersonName().getMiddleName());
				hotelPassenger.setLastname(p.getCustomer().getPersonName().getSurname());
				hotelPassenger.setPhoneno(p.getCustomer().getTelephone().get(0).getPhoneNumber());
				hotelPassenger.setEmail(p.getCustomer().getEmail());
				hotelPassenger.setPaxType(1);
				if(p.getProfileType().equalsIgnoreCase("1"))
					hotelPassenger.setLeadPassenger(true);
				else
					hotelPassenger.setLeadPassenger(false);
				
				if(p.getProfileType().equalsIgnoreCase("1"))
					hotelPassenger.setLeadPassenger(true);
				else
					hotelPassenger.setLeadPassenger(false);
				
				Integer age = Integer.valueOf(p.getCustomer().getAge());
				if(age <= 18)
				{
					hotelPassenger.setPaxType(2);
				}
				else if(age <= 1)
				{
					hotelPassenger.setPaxType(3);
				}
					
				hotelPassenger.setAge(age);
				
				hotelPassenger.setPassportNo(null);
				hotelPassenger.setPassportIssueDate(null);
				hotelPassenger.setPassportExpDate(null);
				HotelPassengers.add(hotelPassenger);				
			}
		}
		return HotelPassengers;
				
	}
	
	
	public static List<HotelPassenger> getRoomHotelPassengers(HotelBookCommand hbc, Integer currentRoomIndex)
	{
		List<HotelPassenger> HotelPassengers = new ArrayList<HotelPassenger>();		
		for (Profile p : hbc.getProfiles()) {	
			Integer roomreqindexprofile = Integer.valueOf(p.getResGuestRPH());
			//roomreqindexprofile = roomreqindexprofile + 1;
			////lot of checkings to be done.....
			if(currentRoomIndex.compareTo(roomreqindexprofile) == 0)
			{
				HotelPassenger hotelPassenger = new HotelPassenger();
				hotelPassenger.setTitle(p.getCustomer().getPersonName().getNamePrefix());
				hotelPassenger.setFirstName(p.getCustomer().getPersonName().getGivenName());
				hotelPassenger.setMiddleName(p.getCustomer().getPersonName().getMiddleName());
				hotelPassenger.setLastName(p.getCustomer().getPersonName().getSurname());
				hotelPassenger.setPhoneno(p.getCustomer().getTelephone().get(0).getPhoneNumber());
				hotelPassenger.setEmail(p.getCustomer().getEmail());
				hotelPassenger.setPaxType(1);
				if(p.getProfileType().equalsIgnoreCase("1"))
					hotelPassenger.setLeadPassenger(true);
				else
					hotelPassenger.setLeadPassenger(false);
				
				if(p.getProfileType().equalsIgnoreCase("1"))
					hotelPassenger.setLeadPassenger(true);
				else
					hotelPassenger.setLeadPassenger(false);
				
				Integer age = Integer.valueOf(p.getCustomer().getAge());
				if(age <= 18)
				{
					hotelPassenger.setPaxType(2);
				}
				else if(age <= 1)
				{
					hotelPassenger.setPaxType(3);
				}
					
				hotelPassenger.setAge(age);
				
				hotelPassenger.setPassportNo(null);
				hotelPassenger.setPassportIssueDate(null);
				hotelPassenger.setPassportExpDate(null);
				hotelPassenger.setgSTCompanyAddress(TayyarahGSTDetails.getGstValue("GSTCompanyAddress"));
				hotelPassenger.setgSTCompanyContactNumber(TayyarahGSTDetails.getGstValue("GSTCompanyContactNumber"));
				hotelPassenger.setgSTCompanyEmail(TayyarahGSTDetails.getGstValue("GSTCompanyEmail"));
				hotelPassenger.setgSTCompanyName(TayyarahGSTDetails.getGstValue("GSTCompanyName"));
				hotelPassenger.setgSTNumber(TayyarahGSTDetails.getGstValue("GSTNumber"));
				
				HotelPassengers.add(hotelPassenger);				
			}
		}
		return HotelPassengers;
				
	}
	public static HashMap<Integer, HotelPassenger> getRoomHotelPassengers(HotelBookCommand hbc, Integer currentRoomIndex, List<RoomRate> targetrooms, RoomCombinations roomcombinations)
	{
		HashMap<Integer, HotelPassenger> HotelPassengers = new HashMap<Integer, HotelPassenger>();		
		for (Profile p : hbc.getProfiles()) {	
			Integer roomreqindexprofile = Integer.valueOf(p.getResGuestRPH());
			roomreqindexprofile = roomreqindexprofile + 1;
			////lot of checkings to be done.....
			if(currentRoomIndex.compareTo(roomreqindexprofile) == 0)
			{
				HotelPassenger hotelPassenger = new HotelPassenger();
				hotelPassenger.setTitle(p.getCustomer().getPersonName().getNamePrefix());
				hotelPassenger.setFirstName(p.getCustomer().getPersonName().getGivenName());
				hotelPassenger.setMiddleName(p.getCustomer().getPersonName().getMiddleName());
				hotelPassenger.setLastName(p.getCustomer().getPersonName().getSurname());
				hotelPassenger.setPhoneno(p.getCustomer().getTelephone().get(0).getPhoneNumber());
				hotelPassenger.setEmail(p.getCustomer().getEmail());
				hotelPassenger.setPaxType(1);
				if(p.getProfileType().equalsIgnoreCase("1"))
					hotelPassenger.setLeadPassenger(true);
				else
					hotelPassenger.setLeadPassenger(false);
				
				Integer age = Integer.valueOf(p.getCustomer().getAge());
				if(age <= 18)
				{
					hotelPassenger.setPaxType(2);
				}
				else if(age <= 1)
				{
					hotelPassenger.setPaxType(3);
				}
					
				hotelPassenger.setAge(age);
				hotelPassenger.setPassportNo(null);
				hotelPassenger.setPassportIssueDate(null);
				hotelPassenger.setPassportExpDate(null);
				//HotelPassengers.add(hotelPassenger);				
			}
		}
		return HotelPassengers;				
	}
	
	
	public HotelBookRequest getHotelBookRequest( HotelApiCredentials apicred, HotelSearchCommand hs, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay rs) throws NumberFormatException, Exception
	{		
		BasicPropertyInfoType basic = rs.getBasicPropertyInfo();
		if(basic != null && basic.getApiProviderBasicMap()!= null && basic.getApiProviderBasicMap().containsKey(HotelApiCredentials.API_TBO_INTERNATIONAL))
			basic = basic.getApiProviderBasicMap().get(HotelApiCredentials.API_TBO_INTERNATIONAL);
		
		
		HotelBookRequest hotelBookRequest = new HotelBookRequest();
		hotelBookRequest.setResultIndex(basic.getApiResultIndex());		
		hotelBookRequest.setHotelCode(basic.getApiVendorID());
		hotelBookRequest.setHotelName(basic.getHotelName());
		hotelBookRequest.setGuestNationality(hs.getCountrycode());
		hotelBookRequest.setNoOfRooms(hs.getNoofrooms());
		hotelBookRequest.setClientReferenceNo("0");
		hotelBookRequest.setIsVoucherBooking(true);
		int noofdays = CommonUtil.getNoofStayDays(hs);
		logger.info("-------------((((( Hotel getHotelBlockRequest call  : rate--");		
		List<com.tayyarah.api.hotel.tbo.book.model.HotelRoomsDetail> HotelRoomsDetails = new ArrayList<com.tayyarah.api.hotel.tbo.book.model.HotelRoomsDetail>();
		
		//List<HotelPassenger> HotelPassengers = getRoomHotelPassengers(hbc, roomrate.getRoomReqIndex(), rs.getRoomRates().getRoomRates(), rs.getRoomCombinations());
		
		int roomreqindex = 0;
		for (RoomRate roomrate : rs.getRoomRates().getRoomRates()) {
			com.tayyarah.api.hotel.tbo.book.model.HotelRoomsDetail hotelRoomsDetail = new com.tayyarah.api.hotel.tbo.book.model.HotelRoomsDetail();
			hotelRoomsDetail.setRoomIndex(roomrate.getSupplierRoomIndex());
			hotelRoomsDetail.setRoomTypeCode(roomrate.getRoomTypeCode());
			hotelRoomsDetail.setRoomTypeName(roomrate.getRoomTypeName());
			hotelRoomsDetail.setRatePlanCode(roomrate.getRatePlanCode());			 
            //hotelRoomsDetail.setHotelSupplements(null);
			hotelRoomsDetail.setBedTypeCode(null);
			hotelRoomsDetail.setSmokingPreference("0");
			hotelRoomsDetail.setSupplements(null);
			
			
			TotalType apiRate = roomrate.getRates().getRates().get(0).getApiPrice();
			
			
			Price price = new Price();
			price.setCurrencyCode(apicred.getApiCurrency());
			price.setRoomPrice(apiRate.getTotalRoomPrice());
			price.setTax(apiRate.getTotalTax());
			price.setExtraGuestCharge(apiRate.getTotalExtraGuestCharge());
			price.setChildCharge(apiRate.getTotalChildCharge());
			price.setOtherCharges(apiRate.getTotalOtherCharges());
			price.setDiscount(apiRate.getTotalDiscount());
			price.setPublishedPrice(apiRate.getApiPublishedPriceTotal());
			price.setPublishedPriceRoundedOff(apiRate.getApiPublishedPriceTotal().setScale(0, BigDecimal.ROUND_HALF_UP));
			price.setOfferedPrice(apiRate.getTotalOfferedPrice());
			price.setOfferedPriceRoundedOff(apiRate.getTotalOfferedPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			price.setAgentCommission(apiRate.getTotalAgentCommission());
			price.setAgentMarkUp(apiRate.getTotalAgentMarkUp());
			price.setServiceTax(apiRate.getTotalServiceTax());
			price.setTDS(apiRate.getTotalTDS());
			
			hotelRoomsDetail.setPrice(price);
			List<HotelPassenger> HotelPassengers = getRoomHotelPassengers(hbc, roomreqindex);
			
			hotelRoomsDetail.setHotelPassenger(HotelPassengers);
			
			HotelRoomsDetails.add(hotelRoomsDetail);	
			roomreqindex ++ ;
		}		
		hotelBookRequest.setHotelRoomsDetails(HotelRoomsDetails);
		
		
		
		hotelBookRequest.setEndUserIp(basic.getApiEndUserIp());
		hotelBookRequest.setTokenId(basic.getApiTokenId());
		hotelBookRequest.setTraceId(basic.getApiTraceId());		
	
		return hotelBookRequest;
	}
	

	public int childrenage(List<GuestCount> roomguests, int index)
	{
		int childage = 0;
		for (GuestCount guest : roomguests) {
			String indextext = String.valueOf(index);
			if(guest.getResGuestRPH().equals(indextext) && guest.getAgequalifyingcode().equals(HotelSearchCommand.GuestCount.AGE_QUALIFYING_CODE_CHILD))
			{
				childage = Integer.valueOf(guest.getAge());
				break;
			}
		}
		return childage;
	}

	public StringBuilder getPreBookingReqOld( HotelApiCredentials apicred, HotelSearchCommand hsc, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory) throws NumberFormatException, Exception
	{	
		/*
		<Type>type1|type2</Type>
        <BookingKey>key1|key2</BookingKey>
        <Adults>2|2</Adults>
        <Children>0|1</Children>
        <ChildrenAges>0|4</ChildrenAges>
        <TotalRooms>2</TotalRooms>
        <TotalRate>1160.430948|1160.430948</TotalRate>
		*/
		StringBuilder types = new StringBuilder();
		StringBuilder bookingkeys = new StringBuilder();
		StringBuilder adults = new StringBuilder();
		StringBuilder children = new StringBuilder();
		StringBuilder childrenAges = new StringBuilder();
		StringBuilder totalRooms = new StringBuilder();
		StringBuilder totalRates = new StringBuilder();
		//bookingkeys.append(rr.getBookingCode());
		int index = 0;
		/*for (RoomRateType rr : hbc.getRoomRateTypes()) {
			com.lintas.hotel.model.RoomTypeType roomrate = new RezLiveResponseParser().getRoomRateType(roomStay, hbc.getBookingCode());
			types.append(roomrate.getRoomType());			
			com.lintas.hotel.model.HotelSearchCommand.RoomReqInfo roomreqinfo = hsc.getRoomReqs().get(index);
			adults.append(roomreqinfo.getNoofAdult());
			children.append(roomreqinfo.getNoofChild());
			int childage = childrenage(hsc.getGuestcounts(), index);
			childrenAges.append(childage);
			//totalRates.append(roomrate.getTotalWithoutMarkup());
			if(index < hbc.getRoomRateTypes().size()-1)
			{
				types.append('|');
				bookingkeys.append('|');
				adults.append('|');
				children.append('|');
				childrenAges.append('|');
				totalRates.append('|');
			}
			index++;
		}		
		totalRooms.append(index);
		 */

		//hsc.getRoomReqs()
		HotelSearchCommand.RoomReqInfo roomReqInfo;

		StringBuilder reqstr = new StringBuilder("<PreBookingRequest>"
				+ "<Authentication>"
				+ "<AgentCode>"+apicred.getPropertyId()+"</AgentCode>"
				+ "<UserName>"+apicred.getUserName()+"</UserName>"
				+ "<Password>"+apicred.getPassword()+"</Password>"
				+ "</Authentication>"
				+ "<PreBooking>"
				+ "<ArrivalDate>"+dateNative(hsc.getDatestart())+"</ArrivalDate>"
				+ "<DepartureDate>"+dateNative(hsc.getDateend())+"</DepartureDate>"	
				+ "<GuestNationality>"+city.getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<CountryCode>"+city.getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<City>"+city.getRezliveCity().getId()+"</City>"
				+ "<HotelId>"+roomStay.getBasicPropertyInfo().getApiVendorID()+"</HotelId>"
				+ "<Currency>"+roomStay.getBasicPropertyInfo().getApiCurrency()+"</Currency>"
				+ "<RoomDetails>"
				+ "<RoomDetail>"
				+ "<Type>"+types+"</Type>"
				+ "<BookingKey>"
				+	bookingkeys
				+ "</BookingKey>"
				+ "<Adults>"+adults+"</Adults>"
				+ "<Children>"+children+"</Children>"
				+ "<ChildrenAges>"+childrenAges+"</ChildrenAges>"
				+ "<TotalRooms>"+totalRooms+"</TotalRooms>"
				+ "<TotalRate>"+totalRates+"</TotalRate>"
				+ "<TermsAndConditions/>"
				+ "</RoomDetail>"
				+ "</RoomDetails>"
				+ "</PreBooking>"
				+ "</PreBookingRequest>");		
		return reqstr;
	}

	
	public StringBuilder getHotelRoomCancellationPolicy( HotelApiCredentials apicred, HotelSearchCommand hs,OTAHotelAvailRS.RoomStays.RoomStay roomStay) throws NumberFormatException, Exception
	{	
		
		BasicPropertyInfoType basic = roomStay.getBasicPropertyInfo();
		if(basic != null && basic.getApiProviderBasicMap()!= null && basic.getApiProviderBasicMap().containsKey(HotelApiCredentials.API_TBO_INTERNATIONAL))
			basic = basic.getApiProviderBasicMap().get(HotelApiCredentials.API_TBO_INTERNATIONAL);
		
		
		RoomStayType.RoomRates.RoomRate roomrate = roomStay.getRoomRates().getRoomRates().get(0);

		StringBuilder types = new StringBuilder();
		StringBuilder bookingkeys = new StringBuilder();
		StringBuilder adults = new StringBuilder();
		StringBuilder children = new StringBuilder();
		StringBuilder childrenAges = new StringBuilder();
		StringBuilder totalRooms = new StringBuilder();
		
		bookingkeys.append(roomrate.getBookingCode());
		//logger.info("-------------((((( Hotel prebook req  : rr--"+rr.toString());		

		int index = 0;
		for (Rate rate : roomrate.getRates().getRates()) {
			logger.info("-------------((((( Hotel prebook req  : rate--"+rate.toString());

			types.append(rate.getName());			
			adults.append(rate.getAdults());
			children.append(rate.getChildren());
			//int childage = childrenage(hsc.getGuestcounts(), index);
			childrenAges.append(rate.getChildrenages());
			if(index < roomrate.getRates().getRates().size()-1)
			{
				types.append('|');				
				adults.append('|');
				children.append('|');
				childrenAges.append('|');				
			}
			index++;
		}	
		totalRooms.append(index);		
		StringBuilder reqstr = new StringBuilder("<CancellationPolicyRequest>"
				+ "<Authentication>"
				+ "<AgentCode>"+apicred.getPropertyId()+"</AgentCode>"
				+ "<UserName>"+apicred.getUserName()+"</UserName>"
				+ "<Password>"+apicred.getPassword()+"</Password>"
				+ "</Authentication>"
				+ "<HotelId>"+basic.getApiVendorID()+"</HotelId>"
				+ "<ArrivalDate>"+dateNative(hs.getDatestart())+"</ArrivalDate>"
				+ "<DepartureDate>"+dateNative(hs.getDateend())+"</DepartureDate>"		
				+ "<GuestNationality>"+city.getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<CountryCode>"+city.getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<City>"+city.getRezliveCity().getId()+"</City>"						
				+ "<Currency>"+basic.getApiCurrency()+"</Currency>"
				+ "<RoomDetails>"
				+ "<RoomDetail>"
				+ "<Type>"+types+"</Type>"
				+ "<BookingKey>"
				+	bookingkeys
				+ "</BookingKey>"
				+ "<Adults>"+adults+"</Adults>"
				+ "<Children>"+children+"</Children>"
				+ "<ChildrenAges>"+childrenAges+"</ChildrenAges>"				
				+ "</RoomDetail>"
				+ "</RoomDetails>"				
				+ "</CancellationPolicyRequest>");			
		return reqstr;
	}
	public StringBuilder getHotelRoomDetailsReq( HotelApiCredentials apicred, HotelSearchCommand hs, OTAHotelAvailRS.RoomStays.RoomStay roomStay) throws NumberFormatException, Exception
	{	
		BasicPropertyInfoType basic = roomStay.getBasicPropertyInfo();
		if(basic != null && basic.getApiProviderBasicMap()!= null && basic.getApiProviderBasicMap().containsKey(HotelApiCredentials.API_TBO_INTERNATIONAL))
			basic = basic.getApiProviderBasicMap().get(HotelApiCredentials.API_TBO_INTERNATIONAL);
		
		
		/*<?xml version="1.0"?>
		<HotelDetailsRequest>
		<Authentication>
		<AgentCode>XXXXX</AgentCode>
		<UserName>XXXXX</UserName> 
		<Password>XXXXX</Password>
		</Authentication>
		<Hotels> 
		<HotelId>XHUB18</HotelId> 
		</Hotels>
		</HotelDetailsRequest>*/

	
		StringBuilder reqstr = new StringBuilder("<HotelDetailsRequest>"
				+ "<Authentication>"
				+ "<AgentCode>"+apicred.getPropertyId()+"</AgentCode>"
				+ "<UserName>"+apicred.getUserName()+"</UserName>"
				+ "<Password>"+apicred.getPassword()+"</Password>"
				+ "</Authentication>"
				+ "<Hotels> "
				+ "<HotelId>"+basic.getApiVendorID()+"</HotelId>"				
				+ "</Hotels>"				
				+ "</HotelDetailsRequest>");			
		return reqstr;
	}

	
	public StringBuilder getHotelRoomCancellationPolicyRoomRate( HotelApiCredentials apicred, HotelSearchCommand hs, OTAHotelAvailRS.RoomStays.RoomStay roomStay, RoomStayType.RoomRates.RoomRate roomrate) throws NumberFormatException, Exception
	{	
		BasicPropertyInfoType basic = roomStay.getBasicPropertyInfo();
		if(basic != null && basic.getApiProviderBasicMap()!= null && basic.getApiProviderBasicMap().containsKey(HotelApiCredentials.API_TBO_INTERNATIONAL))
			basic = basic.getApiProviderBasicMap().get(HotelApiCredentials.API_TBO_INTERNATIONAL);
		
		
		StringBuilder types = new StringBuilder();
		StringBuilder bookingkeys = new StringBuilder();
		StringBuilder adults = new StringBuilder();
		StringBuilder children = new StringBuilder();
		StringBuilder childrenAges = new StringBuilder();
		StringBuilder totalRooms = new StringBuilder();
		
		bookingkeys.append(roomrate.getBookingCode());
		//logger.info("-------------((((( Hotel prebook req  : rr--"+rr.toString());		

		int index = 0;
		for (Rate rate : roomrate.getRates().getRates()) {
			logger.info("-------------((((( Hotel prebook req  : rate--"+rate.toString());

			types.append(rate.getName());			
			adults.append(rate.getAdults());
			children.append(rate.getChildren());
			//int childage = childrenage(hsc.getGuestcounts(), index);
			childrenAges.append(rate.getChildrenages());
			if(index < roomrate.getRates().getRates().size()-1)
			{
				types.append('|');				
				adults.append('|');
				children.append('|');
				childrenAges.append('|');				
			}
			index++;
		}	
		totalRooms.append(index);		
		StringBuilder reqstr = new StringBuilder("<CancellationPolicyRequest>"
				+ "<Authentication>"
				+ "<AgentCode>"+apicred.getPropertyId()+"</AgentCode>"
				+ "<UserName>"+apicred.getUserName()+"</UserName>"
				+ "<Password>"+apicred.getPassword()+"</Password>"
				+ "</Authentication>"
				+ "<HotelId>"+basic.getApiVendorID()+"</HotelId>"
				+ "<ArrivalDate>"+dateNative(hs.getDatestart())+"</ArrivalDate>"
				+ "<DepartureDate>"+dateNative(hs.getDateend())+"</DepartureDate>"		
				+ "<GuestNationality>"+city.getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<CountryCode>"+city.getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<City>"+city.getRezliveCity().getId()+"</City>"						
				+ "<Currency>"+basic.getApiCurrency()+"</Currency>"
				+ "<RoomDetails>"
				+ "<RoomDetail>"
				+ "<Type>"+types+"</Type>"
				+ "<BookingKey>"
				+	bookingkeys
				+ "</BookingKey>"
				+ "<Adults>"+adults+"</Adults>"
				+ "<Children>"+children+"</Children>"
				+ "<ChildrenAges>"+childrenAges+"</ChildrenAges>"				
				+ "</RoomDetail>"
				+ "</RoomDetails>"				
				+ "</CancellationPolicyRequest>");			
		return reqstr;
	}

	
	public SendChangeRequest getSendChangeRequest(HotelApiCredentials apicred, String endUserIp, APIHotelCancelRequest apiHotelCancelRequest,  HotelOrderRow hotelorder) throws NumberFormatException, Exception
	{	
		SendChangeRequest sendChangeRequest = new SendChangeRequest();
		/*{
			"RequestType": 4,
			"Remarks": "sds",
			"BookingId": 112123,
			"EndUserIp": "123.00.00.00",
			"TokenId": "50e28fb0-c9d6-412e-b64c-6fcd459cde1c"
			}*/
		BigInteger bookingid = new BigInteger("12212");
		try
		{
			bookingid = new BigInteger(hotelorder.getApiBookingId());
		}
		catch(Exception e)
		{
			logger.info("-------------##### invlaid booking id  :");
			logger.error(e);
		}
		
		sendChangeRequest.setRequestType(4);
		sendChangeRequest.setRemarks(apiHotelCancelRequest.getRemarks());
		sendChangeRequest.setBookingId(bookingid);
		sendChangeRequest.setEndUserIp(endUserIp);
		sendChangeRequest.setTokenId(apicred.getTokenId());
		
		return sendChangeRequest;
		//return reqstr;
	}
	
	public GetChangeRequestStatus getGetChangeRequestStatusRequest(HotelApiCredentials apicred, String endUserIp, APIHotelCancelRequest apiHotelCancelRequest, SendChangeResponse sendChangeResponse,  HotelOrderRow hotelorder) throws NumberFormatException, Exception
	{	
		GetChangeRequestStatus getChangeRequestStatus = new GetChangeRequestStatus();
		
		/*{
		"ChangeRequestId": 5285,
		"EndUserIp": "123.1.1.1",
		"TokenId": "50e28fb0-c9d6-412e-b64c-6fcd459cde1c"
		}*/
		getChangeRequestStatus.setChangeRequestId(sendChangeResponse.getHotelChangeRequestResult().getChangeRequestId());
		getChangeRequestStatus.setEndUserIp(endUserIp);
		getChangeRequestStatus.setTokenId(apicred.getTokenId());		
		return getChangeRequestStatus;
		//return reqstr;
	}
	public GetChangeRequestStatus getGetChangeRequestStatusRequest(HotelApiCredentials apicred, String endUserIp, APIHotelCancelRequest apiHotelCancelRequest, HotelOrderRowCancellation hotelOrderRowCancellation,  HotelOrderRow hotelorder) throws NumberFormatException, Exception
	{	
		GetChangeRequestStatus getChangeRequestStatus = new GetChangeRequestStatus();	
		/*{
		"ChangeRequestId": 5285,
		"EndUserIp": "123.1.1.1",
		"TokenId": "50e28fb0-c9d6-412e-b64c-6fcd459cde1c"
		}*/
		Long changeRequestId = Long.valueOf(hotelOrderRowCancellation.getAPIConfirmationNumber());
		getChangeRequestStatus.setChangeRequestId(changeRequestId);
		getChangeRequestStatus.setEndUserIp(endUserIp);
		getChangeRequestStatus.setTokenId(apicred.getTokenId());		
		return getChangeRequestStatus;
		//return reqstr;
	}
	


}
