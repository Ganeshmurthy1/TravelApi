package com.tayyarah.hotel.util;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import org.apache.log4j.Logger;

import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelBookCommand.Profile;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelSearchCommand.GuestCount;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.RoomStayType;


public class RezLiveRequestBuilder {
	public static final Logger logger = Logger.getLogger(RezLiveRequestBuilder.class);	

	public RezLiveRequestBuilder() {
		// TODO Auto-generated constructor stub
	}
	public String dateNative(String commonDateStr) throws NumberFormatException, Exception//"yyyy-MM-dd" to "dd/MM/yyyy"
	{
		DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date datestart = originalFormat.parse(commonDateStr);		
		String formattedDate = targetFormat.format(datestart);  // 20120821
		return formattedDate;

	}

	public StringBuilder getHotelSearchReq(HotelApiCredentials apicred, HotelSearchCommand hs) throws NumberFormatException, Exception
	{			
		List<HotelSearchCommand.RoomReqInfo> roomReqs = hs.getRoomrequests();
		StringBuilder roominfo = new StringBuilder("<Rooms>");	
		int roomindex = 1;
		for (HotelSearchCommand.RoomReqInfo roomReq : roomReqs) {			
			roominfo.append("<Room><Type>Room-"+roomindex+"</Type><NoOfAdults>"+roomReq.getNoofAdult()+"</NoOfAdults><NoOfChilds>"+roomReq.getNoofChild()+"</NoOfChilds>");
			roominfo.append("<ChildrenAges>");
			for (Integer childage : roomReq.getChildages()) {
				roominfo.append("<ChildAge>"+childage+"</ChildAge>");				
			}
			roominfo.append("</ChildrenAges>");
			roominfo.append("</Room>");		
			roomindex++;
		}
		roominfo.append("</Rooms>");	
		StringBuilder reqstr = new StringBuilder("<HotelFindRequest>"
				+ "<Authentication>"
				+ "<AgentCode>"+apicred.getPropertyId()+"</AgentCode>"
				+ "<UserName>"+apicred.getUserName()+"</UserName>"
				+ "<Password>"+apicred.getPassword()+"</Password>"
				+ "</Authentication>"
				+ "<Booking>"
				+ "<ArrivalDate>"+dateNative(hs.getDatestart())+"</ArrivalDate>"
				+ "<DepartureDate>"+dateNative(hs.getDateend())+"</DepartureDate>"
				+ "<CountryCode>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<City>"+hs.getSearchCity().getRezliveCity().getId()+"</City>"
				+ "<GuestNationality>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<HotelRatings>"
				+ "<HotelRating>1</HotelRating>"
				+ "<HotelRating>2</HotelRating>"
				+ "<HotelRating>3</HotelRating>"
				+ "<HotelRating>4</HotelRating>"
				+ "<HotelRating>5</HotelRating>"
				+ "</HotelRatings>"
				+ roominfo				
				+ "</Booking>"
				+ "</HotelFindRequest>");

		return reqstr;
	}
	public StringBuilder getHotelSearchByIdReq(HotelApiCredentials apicred, HotelSearchCommand hs, String hotelId) throws NumberFormatException, Exception
	{	
		/*<?xml version="1.0"?>
		<HotelFindRequest>
		  <Authentication>
		    <AgentCode>X29DC10</AgentCode>
		    <UserName>tayyarah</UserName>
		    <Password>*****</Password>
		  </Authentication>
		  <Booking>
		    <ArrivalDate>01/10/2014</ArrivalDate>
		    <DepartureDate>03/10/2014</DepartureDate>
		    <CountryCode>AE</CountryCode>
		    <City>GAE9</City>
		    <HotelIDs>
		      <Int>150884</Int>
		      <Int>171888</Int>
		      <Int>245325</Int>
		      <Int>151754</Int>
		      <Int>248860</Int>
		    </HotelIDs>
		    <GuestNationality>IN</GuestNationality>
		    <Rooms>
		      <Room>
		        <Type>Room-1</Type>
		        <NoOfAdults>1</NoOfAdults>
		        <NoOfChilds>1</NoOfChilds>
		        <ChildrenAges>
		          <ChildAge>4</ChildAge>
		        </ChildrenAges>
		      </Room>
		    </Rooms>
		  </Booking>
		</HotelFindRequest>*/

		List<HotelSearchCommand.RoomReqInfo> roomReqs = hs.getRoomrequests();		
		StringBuilder roominfo = new StringBuilder("<Rooms>");	
		int roomindex = 1;
		for (HotelSearchCommand.RoomReqInfo roomReq : roomReqs) {
			roominfo.append("<Room><Type>Room-"+roomindex+"</Type><NoOfAdults>"+roomReq.getNoofAdult()+"</NoOfAdults><NoOfChilds>"+roomReq.getNoofChild()+"</NoOfChilds></Room>");
			roomindex++;
		}
		roominfo.append("</Rooms>");
		StringBuilder reqstr = new StringBuilder("<HotelFindRequest>"
				+ "<Authentication>"
				+ "<AgentCode>"+apicred.getPropertyId()+"</AgentCode>"
				+ "<UserName>"+apicred.getUserName()+"</UserName>"
				+ "<Password>"+apicred.getPassword()+"</Password>"
				+ "</Authentication>"
				+ "<Booking>"
				+ "<ArrivalDate>"+dateNative(hs.getDatestart())+"</ArrivalDate>"
				+ "<DepartureDate>"+dateNative(hs.getDateend())+"</DepartureDate>"
				+ "<CountryCode>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<HotelIDs>"
				+ "<Int>"+hotelId+"</Int>"
				+ "</HotelIDs>"
				+ "<City>"+hs.getSearchCity().getRezliveCity().getId()+"</City>"
				+ "<GuestNationality>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<HotelRatings>"
				+ "<HotelRating>1</HotelRating>"
				+ "<HotelRating>2</HotelRating>"
				+ "<HotelRating>3</HotelRating>"
				+ "<HotelRating>4</HotelRating>"
				+ "<HotelRating>5</HotelRating>"
				+ "</HotelRatings>"
				+ roominfo				
				+ "</Booking>"
				+ "</HotelFindRequest>");

		return reqstr;
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
		int index = 0;	
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
				+ "<GuestNationality>"+hsc.getSearchCity().getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<CountryCode>"+hsc.getSearchCity().getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<City>"+hsc.getSearchCity().getRezliveCity().getId()+"</City>"
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

	public StringBuilder getPreBookingReq( HotelApiCredentials apicred, HotelSearchCommand hsc, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, String bookingkey, HotelIdFactoryImpl hotelIdFactory) throws NumberFormatException, Exception
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
		RoomStayType.RoomRates.RoomRate roomrate = roomStay.getRoomRates().getRoomRates().get(0);

		StringBuilder types = new StringBuilder();
		StringBuilder bookingkeys = new StringBuilder();
		StringBuilder adults = new StringBuilder();
		StringBuilder children = new StringBuilder();
		StringBuilder childrenAges = new StringBuilder();
		StringBuilder totalRooms = new StringBuilder();
		StringBuilder totalRates = new StringBuilder();
		bookingkeys.append(roomrate.getBookingCode());
		int index = 0;
		for (Rate rate : roomrate.getRates().getRates()) {
		types.append(rate.getName());			
			adults.append(rate.getAdults());
			children.append(rate.getChildren());
			//int childage = childrenage(hsc.getGuestcounts(), index);
			childrenAges.append(rate.getChildrenages());
			totalRates.append(rate.getApiPrice().getTotalAmountPayable());
			if(index < roomrate.getRates().getRates().size()-1)
			{
				types.append('|');				
				adults.append('|');
				children.append('|');
				childrenAges.append('|');
				totalRates.append('|');
			}
			index++;
		}	
		totalRooms.append(index);
		StringBuilder reqstr = new StringBuilder("<PreBookingRequest>"
				+ "<Authentication>"
				+ "<AgentCode>"+apicred.getPropertyId()+"</AgentCode>"
				+ "<UserName>"+apicred.getUserName()+"</UserName>"
				+ "<Password>"+apicred.getPassword()+"</Password>"
				+ "</Authentication>"
				+ "<PreBooking>"
				+ "<ArrivalDate>"+dateNative(hsc.getDatestart())+"</ArrivalDate>"
				+ "<DepartureDate>"+dateNative(hsc.getDateend())+"</DepartureDate>"	
				+ "<GuestNationality>"+hsc.getSearchCity().getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<CountryCode>"+hsc.getSearchCity().getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<City>"+hsc.getSearchCity().getRezliveCity().getId()+"</City>"
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


	public StringBuilder getHotelBookReq( HotelApiCredentials apicred, HotelSearchCommand hs, HotelBookCommand hbc, OTAHotelResRS totaHotelResRS, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelOrderRow hor, HotelIdFactoryImpl hotelIdFactory) throws NumberFormatException, Exception
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
		RoomStayType.RoomRates.RoomRate roomrate = roomStay.getRoomRates().getRoomRates().get(0);

		StringBuilder types = new StringBuilder();
		StringBuilder bookingkeys = new StringBuilder();
		StringBuilder adults = new StringBuilder();
		StringBuilder children = new StringBuilder();
		StringBuilder childrenAges = new StringBuilder();
		StringBuilder totalRooms = new StringBuilder();
		StringBuilder totalRates = new StringBuilder();
		bookingkeys.append(totaHotelResRS.getBookingCode());	
		int index = 0;
		for (Rate rate : roomrate.getRates().getRates()) {
			types.append(rate.getName());			
			adults.append(rate.getAdults());
			children.append(rate.getChildren());			
			childrenAges.append(rate.getChildrenages());
			totalRates.append(totaHotelResRS.getApiFinalPrice());
			if(index < roomrate.getRates().getRates().size()-1)
			{
				types.append('|');				
				adults.append('|');
				children.append('|');
				childrenAges.append('|');
				totalRates.append('|');
			}
			index++;
		}	
		totalRooms.append(index);
		StringBuilder reqGuestCount  = new  StringBuilder();
		for (GuestCount guestCount : hs.getGuestcounts()) {	
			reqGuestCount.append("<GuestCount AgeQualifyingCode=\""+guestCount.getAgequalifyingcode()+"\" Count=\""+guestCount.getCount()+"\"/>");		
		}
		StringBuilder reqGuests  = new  StringBuilder();
		for (Profile p : hbc.getProfiles()) {
			reqGuests.append("<Guest>"
					+ "<Salutation>"+p.getCustomer().getPersonName().getNamePrefix()+"</Salutation>"
					+ "<FirstName>"+p.getCustomer().getPersonName().getGivenName()+"</FirstName>"
					+ "<LastName>"+p.getCustomer().getPersonName().getSurname()+"</LastName>"
					+ "</Guest>");
		}
		StringBuilder reqstr = new StringBuilder("<BookingRequest>"
				+ "<Authentication>"
				+ "<AgentCode>"+apicred.getPropertyId()+"</AgentCode>"
				+ "<UserName>"+apicred.getUserName()+"</UserName>"
				+ "<Password>"+apicred.getPassword()+"</Password>"
				+ "</Authentication>"
				+ "<Booking>"
				+ "<ArrivalDate>"+dateNative(hs.getDatestart())+"</ArrivalDate>"
				+ "<DepartureDate>"+dateNative(hs.getDateend())+"</DepartureDate>"		
				+ "<GuestNationality>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<CountryCode>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<City>"+hs.getSearchCity().getRezliveCity().getId()+"</City>"
				+ "<HotelId>"+roomStay.getBasicPropertyInfo().getApiVendorID()+"</HotelId>"
				+ "<Name>"+roomStay.getBasicPropertyInfo().getHotelName()+"</Name>"				
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
				+ "<Guests>"
				+	reqGuests
				+ "</Guests>"
				+ "</RoomDetail>"
				+ "</RoomDetails>"
				+ "</Booking>"
				+ "</BookingRequest>");			
		return reqstr;
	}

	public StringBuilder getHotelRoomCancellationPolicy( HotelApiCredentials apicred, HotelSearchCommand hs, OTAHotelAvailRS.RoomStays.RoomStay roomStay) throws NumberFormatException, Exception
	{	
		RoomStayType.RoomRates.RoomRate roomrate = roomStay.getRoomRates().getRoomRates().get(0);
		StringBuilder types = new StringBuilder();
		StringBuilder bookingkeys = new StringBuilder();
		StringBuilder adults = new StringBuilder();
		StringBuilder children = new StringBuilder();
		StringBuilder childrenAges = new StringBuilder();
		StringBuilder totalRooms = new StringBuilder();		
		bookingkeys.append(roomrate.getBookingCode());
		int index = 0;
		for (Rate rate : roomrate.getRates().getRates()) {
			types.append(rate.getName());			
			adults.append(rate.getAdults());
			children.append(rate.getChildren());
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
				+ "<HotelId>"+roomStay.getBasicPropertyInfo().getApiVendorID()+"</HotelId>"
				+ "<ArrivalDate>"+dateNative(hs.getDatestart())+"</ArrivalDate>"
				+ "<DepartureDate>"+dateNative(hs.getDateend())+"</DepartureDate>"		
				+ "<GuestNationality>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<CountryCode>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<City>"+hs.getSearchCity().getRezliveCity().getId()+"</City>"						
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
				+ "</RoomDetail>"
				+ "</RoomDetails>"				
				+ "</CancellationPolicyRequest>");			
		return reqstr;
	}
	
	public StringBuilder getHotelRoomDetailsReq( HotelApiCredentials apicred, HotelSearchCommand hs,OTAHotelAvailRS.RoomStays.RoomStay roomStay) throws NumberFormatException, Exception
	{		
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
				+ "<HotelId>"+roomStay.getBasicPropertyInfo().getApiVendorID()+"</HotelId>"				
				+ "</Hotels>"				
				+ "</HotelDetailsRequest>");			
		return reqstr;
	}
	
	public StringBuilder getHotelRoomCancellationPolicyRoomRate( HotelApiCredentials apicred, HotelSearchCommand hs, OTAHotelAvailRS.RoomStays.RoomStay roomStay, RoomStayType.RoomRates.RoomRate roomrate) throws NumberFormatException, Exception
	{		
		StringBuilder types = new StringBuilder();
		StringBuilder bookingkeys = new StringBuilder();
		StringBuilder adults = new StringBuilder();
		StringBuilder children = new StringBuilder();
		StringBuilder childrenAges = new StringBuilder();
		StringBuilder totalRooms = new StringBuilder();
		bookingkeys.append(roomrate.getBookingCode());
		int index = 0;
		for (Rate rate : roomrate.getRates().getRates()) {
			types.append(rate.getName());			
			adults.append(rate.getAdults());
			children.append(rate.getChildren());
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
				+ "<HotelId>"+roomStay.getBasicPropertyInfo().getApiVendorID()+"</HotelId>"
				+ "<ArrivalDate>"+dateNative(hs.getDatestart())+"</ArrivalDate>"
				+ "<DepartureDate>"+dateNative(hs.getDateend())+"</DepartureDate>"		
				+ "<GuestNationality>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</GuestNationality>"
				+ "<CountryCode>"+hs.getSearchCity().getRezliveCity().getCountryCode()+"</CountryCode>"
				+ "<City>"+hs.getSearchCity().getRezliveCity().getId()+"</City>"						
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
				+ "</RoomDetail>"
				+ "</RoomDetails>"				
				+ "</CancellationPolicyRequest>");			
		return reqstr;
	}
	
	public StringBuilder getHotelCancelReq(HotelApiCredentials apicred, HotelSearchCommand hs) throws NumberFormatException, Exception
	{	
		StringBuilder reqstr = new StringBuilder("<CancellationRequest>"
				+ "<Authentication>"
				+ "<AgentCode>"+apicred.getPropertyId()+"</AgentCode>"
				+ "<UserName>"+apicred.getUserName()+"</UserName>"
				+ "<Password>"+apicred.getApiPassword()+"</Password>"
				+ "</Authentication>"
				+ "<Cancellation>"
				+ "<BookingId>XHUB985633</BookingId>"
				+ "<BookingCode>XHUB6B4A62</BookingCode>"
				+ "</Cancellation>"
				+ "</CancellationRequest>");	
		return reqstr;
	}
}