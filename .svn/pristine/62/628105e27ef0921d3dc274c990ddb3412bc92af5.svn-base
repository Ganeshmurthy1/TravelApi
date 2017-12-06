package com.tayyarah.hotel.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.tayyarah.api.hotel.reznext.model.RoomInfoType;
import com.tayyarah.hotel.model.APIHotelBook.RateMap;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelBookCommand.Profile;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelSearchCommand.GuestCount;

import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.RoomRateType;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.TaxType;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;


public class RezNextRequestBuilder {
	public RezNextRequestBuilder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static final Logger logger = Logger.getLogger(RezNextPullerTask.class);
	
	public StringBuilder getHotelDetailSearchRequestBodyReznext(HotelApiCredentials apicred, HotelSearchCommand hs, OTAHotelAvailRS.RoomStays.RoomStay roomStay) throws NumberFormatException, Exception
	{
		java.util.Date date= new java.util.Date();
		//System.out.println(new Timestamp(date.getTime()));
		Timestamp now = new Timestamp(date.getTime());
		
		List<HotelSearchCommand.RoomReqInfo> roomReqs = hs.getRoomrequests();
		
		StringBuilder roominfo = new StringBuilder("<RoomInfo NumberOfRooms=\""+roomReqs.size()+"\">");
		for (HotelSearchCommand.RoomReqInfo roomReq : roomReqs) {
			roominfo.append("<Rooms Room=\""+roomReq.getRoomindex()+"\" Adult=\""+roomReq.getNoofAdult()+"\" Child=\""+roomReq.getNoofChild()+"\" />");
			
		}
		roominfo.append("</RoomInfo>");		
			
		StringBuilder reqstr = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">"
				+ "<soapenv:Header><wsa:From><wsa:SystemId>"+apicred.getSystemId()+"</wsa:SystemId>"
				+ "<wsa:Credential>"
				+ "<wsa:UserName>"+apicred.getUserName()+"</wsa:UserName><wsa:Password>"+apicred.getPassword()+"</wsa:Password>"
				+ "</wsa:Credential>"
				+ "</wsa:From>"
				+ "<wsa:TimeStamp>"+now.toString()+"</wsa:TimeStamp>"
				+ "<wsa:Action>OTA_HotelAvailRQ</wsa:Action>"
				+ "</soapenv:Header>"
				+ "<soapenv:Body>"
				+ "<OTA_HotelAvailRQ Version=\"4.000\" EchoToken=\"12345\" TransactionIdentifier=\"1\" TimeStamp=\""+now.toString()+"\">"
				+ "<AvailRequestSegments>"
				+ "<AvailRequestSegment>"
				+ "<HotelSearchCriteria>"				
				+ "<Criterion>"
				+ "<HotelRef HotelCode=\""+roomStay.getBasicPropertyInfo().getApiVendorID()+"\"/>"
				+ "<StayDateRange Start=\""+hs.getDatestart()+"\" End=\""+hs.getDateend()+"\"/>"
				+	roominfo
				+ "<CurrencyInfo CurrencyCode=\""+apicred.getApiCurrency()+"\" />"
				+ "</Criterion>"
				+ "</HotelSearchCriteria>"
				+ "</AvailRequestSegment>"
				+ "</AvailRequestSegments>"
				+ "</OTA_HotelAvailRQ>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>");

		return reqstr;


	}
	
	public StringBuilder getHotelSearchBodyReznext(HotelApiCredentials apicred, HotelSearchCommand hs) throws ClassNotFoundException, JAXBException
	{
		java.util.Date date= new java.util.Date();		
		Timestamp now = new Timestamp(date.getTime());		
		StringBuilder reqstr = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">"
				+ "<soapenv:Header><wsa:From><wsa:SystemId>"+apicred.getSystemId()+"</wsa:SystemId>"
				+ "<wsa:Credential>"
				+ "<wsa:UserName>"+apicred.getUserName()+"</wsa:UserName><wsa:Password>"+apicred.getPassword()+"</wsa:Password>"
				+ "</wsa:Credential>"
				+ "</wsa:From>"
				+ "<wsa:TimeStamp>"+now.toString()+"</wsa:TimeStamp>"
				+ "<wsa:Action>OTA_HotelAvailRQ</wsa:Action>"
				+ "</soapenv:Header>"
				+ "<soapenv:Body>"
				+ "<OTA_HotelAvailRQ Version=\"4.000\" EchoToken=\"12345\" TransactionIdentifier=\"1\" TimeStamp=\""+now.toString()+"\">"
				+ "<AvailRequestSegments>"
				+ "<AvailRequestSegment>"
				+ "<HotelSearchCriteria>"
				+ "<Criterion>"
				+ "<CityInfo CityName=\""+hs.getSearchCity().getReznextCity().getCity()+"\"/>"
				+ "<StayDateRange Start=\""+hs.getDatestart()+"\" End=\""+hs.getDateend()+"\"/>"
				+ "<RoomInfo NumberOfRooms=\""+hs.getNoofrooms()+"\"/>"
				+ "</Criterion>"
				+ "</HotelSearchCriteria>"
				+ "</AvailRequestSegment>"
				+ "</AvailRequestSegments>"
				+ "</OTA_HotelAvailRQ>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>");

		return reqstr;
	}	
	
	public StringBuilder getHotelCitySearchRequestBodyReznext(HotelApiCredentials apicred, HotelSearchCommand hs,RoomInfoType roomInfoType) throws ClassNotFoundException, JAXBException
	{
		java.util.Date date= new java.util.Date();
		Timestamp now = new Timestamp(date.getTime());
		StringBuilder reqstr = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">"
				+ "<soapenv:Header><wsa:From><wsa:SystemId>"+apicred.getSystemId()+"</wsa:SystemId>"
				+ "<wsa:Credential>"
				+ "<wsa:UserName>"+apicred.getUserName()+"</wsa:UserName><wsa:Password>"+apicred.getPassword()+"</wsa:Password>"
				+ "</wsa:Credential>"
				+ "</wsa:From>"
				+ "<wsa:TimeStamp>"+now.toString()+"</wsa:TimeStamp>"
				+ "<wsa:Action>OTA_HotelAvailRQ</wsa:Action>"
				+ "</soapenv:Header>"
				+ "<soapenv:Body>"
				+ "<OTA_HotelAvailRQ Version=\"4.000\" EchoToken=\"12345\" TransactionIdentifier=\"1\" TimeStamp=\""+now.toString()+"\">"
				+ "<AvailRequestSegments>"
				+ "<AvailRequestSegment>"
				+ "<HotelSearchCriteria>"
				+ "<Criterion>"
				+ "<CityInfo CityName=\""+hs.getSearchCity().getReznextCity().getCity()+"\"/>"
				+ "<StayDateRange Start=\""+hs.getDatestart()+"\" End=\""+hs.getDateend()+"\"/>"
				+ "<RoomInfo NumberOfRooms=\""+roomInfoType.getNumberOfRooms()+"\">"
				+ "</Criterion>"
				+ "</HotelSearchCriteria>"
				+ "</AvailRequestSegment>"
				+ "</AvailRequestSegments>"
				+ "</OTA_HotelAvailRQ>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>");

		return reqstr;
	}	
	
	public RoomStayType.RoomRates.RoomRate getRoomRate(OTAHotelAvailRS.RoomStays.RoomStay rs, RoomStayType.RoomRates.RoomRate roomrate)
	{
		return rs.getRoomRates().getRoomRates().get(0);
	}
	public RoomRateType getRoomRateType(List<RoomRateType> roomRateTypes, String roomTypeCode, String ratePlanCode)
	{
		for (RoomRateType roomRateType : roomRateTypes) {
			if(roomRateType.getRatePlanCode().equals(ratePlanCode) && roomRateType.getRoomTypeCode().equals(roomTypeCode))
			{
				return roomRateType;
			}
		}
		return null;
	}
	
	
	public StringBuilder getBookingReq( HotelApiCredentials apicred, HotelTransactionTemp ht, HotelSearchCommand hsc, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay rs, RateMap ratemap, HotelOrderRow hor, HotelIdFactoryImpl hotelIdFactory) throws NumberFormatException, Exception
	{

		/*<soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope" 
		xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:htn="http://pms.ihotelier.com/HTNGService/services/HTNG2011BService">
		<soapenv:Header>
		<wsa:MessageID>713</wsa:MessageID>
		<wsa:To>http://pmcoutllm01-t5.tcprod.local:8080/HtngSimulator/PMSInterfaceSimulator</wsa:To>
		<wsa:UserName>Reznext123</wsa:UserName>
		<wsa:PassWord>Reznext@123</wsa:PassWord>
		<wsa:ReplyTo>
		<wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>
		</wsa:ReplyTo>
		<wsa:Action>http://htng.org/2011B/HTNG2011B_SubmitResult</wsa:Action>
		</soapenv:Header>
		<soapenv:Body>
		<OTA_HotelResNotifRQ xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.opentravel.org/OTA/2003/05 OTA_HotelResNotifRQ.xsd" Version="1.003" EchoToken="713" TimeStamp="2015-04-16T12:47:59" ResStatus="Book">
		<POS>
		<Source>
		<RequestorID Type="14" ID="Channelid"/>
		<BookingChannel Type="5" Primary="1">
		<CompanyName Code="WEB">WEB</CompanyName>
		</BookingChannel>
		</Source>
		</POS>
		<HotelReservations>
		<HotelReservation CreatorID="channelid" CreateDateTime="2015-10-14T15:35:00" ResStatus="Reserved">
		<UniqueID ID="133" Type="14"/>
		<RoomStays>
		<RoomStay MarketCode=" " SourceOfBusiness="WEB ">
		<RatePlans>
		<RatePlan RatePlanCode="BO1">
		<MealsIncluded MealPlanCodes="CP" MealPlanIndicator="True"/>
		</RatePlan>
		</RatePlans>
		<RoomRates>
		<RoomRate RoomTypeCode="RGL" NumberOfUnits="1" RatePlanCode="BO1" RatePlanCategory="Bar">
		<Rates>
		<Rate EffectiveDate="2015-10-15" ExpireDate="2015-10-16" RateTimeUnit="DAY" UnitMultiplier="2">
		<Base AmountBeforeTax="5100.0" AmountAfterTax="5100.0" CurrencyCode="INR">
		<Taxes Amount="0" CurrencyCode="INR"/>
		</Base>
		</Rate>
		</Rates>
		</RoomRate>
		</RoomRates>
		<GuestCounts IsPerRoom="1">
		<GuestCount AgeQualifyingCode="10" Count="3"/>
		<GuestCount AgeQualifyingCode="8" Count="0"/>
		</GuestCounts>
		<TimeSpan Start="2015-10-15" End="2015-10-16"/>
		<Total AmountBeforeTax="5100.0" CurrencyCode="INR">
		<Taxes Amount="0" CurrencyCode="INR"/>
		</Total>
		<BasicPropertyInfo HotelCode="7777"/>
		<ResGuestRPHs>
		<ResGuestRPH RPH="0"/>
		</ResGuestRPHs>
		</RoomStay>
		</RoomStays>
		<ResGuests>
		<ResGuest ResGuestRPH="0" PrimaryIndicator="1">
		<Profiles>
		<ProfileInfo>
		<UniqueID ID="0001010062" Type="1" ID_Context="Customer"/>
		<Profile ProfileType="1">
		<Customer>
		<PersonName>
		<NamePrefix>Mr</NamePrefix>
		<GivenName>K</GivenName>
		<Surname>Mohandas</Surname>
		</PersonName>
		<Telephone PhoneLocationType="6" PhoneTechType="1" PhoneNumber="9494730961" FormattedInd="0"/>
		<Email>mdkukatlapalli@gmail.com</Email>
		<Address Type="1">
		<AddressLine>NC-132, EIGHT INCLINE COLONY GODAVARI 8 INCLINE </AddressLine>
		<CityName>KARIM NAGAR</CityName>
		<PostalCode>505211</PostalCode>
		<StateProv>ANDHRA PRADESH</StateProv>
		<CountryName>India</CountryName>
		</Address>
		</Customer>
		</Profile>
		</ProfileInfo>
		</Profiles>
		</ResGuest>
		<ResGuest ResGuestRPH="0" PrimaryIndicator="0">
		<Profiles>
		<ProfileInfo>
		<UniqueID ID="0001010062" Type="1" ID_Context="Customer"/>
		<Profile ProfileType="1">
		<Customer>
		<PersonName>
		<NamePrefix>Mr</NamePrefix>
		<GivenName>K</GivenName>
		<Surname>Mohandas</Surname>
		</PersonName>
		<Telephone PhoneLocationType="6" PhoneTechType="1" PhoneNumber="9494730961" FormattedInd="0"/>
		<Email>mdkukatlapalli@gmail.com</Email>
		<Address Type="1">
		<AddressLine>NC-132, EIGHT INCLINE COLONY GODAVARI 8 INCLINE </AddressLine>
		<CityName>KARIM NAGAR</CityName>
		<PostalCode>505211</PostalCode>
		<StateProv>ANDHRA PRADESH</StateProv>
		<CountryName>India</CountryName>
		</Address>
		</Customer>
		</Profile>
		</ProfileInfo>
		</Profiles>
		</ResGuest>
		</ResGuests>
		<ResGlobalInfo>
		<HotelReservationIDs>
		<HotelReservationID ResID_Value="133" ResID_Type="14" ResID_Source="OTA Name"/>
		</HotelReservationIDs>
		</ResGlobalInfo>
		</HotelReservation>
		</HotelReservations>
		</OTA_HotelResNotifRQ>
		</soapenv:Body>
		</soapenv:Envelope>*/
		java.util.Date date= new java.util.Date();	
		Timestamp now = new Timestamp(date.getTime());		
		String echoToken = hotelIdFactory.createShortId("");		
		StringBuilder req  = new  StringBuilder();				
		StringBuilder reqRatePlan  = new  StringBuilder();
		for (RatePlanType ratePlan : rs.getRatePlans().getRatePlan()) {
			reqRatePlan.append("<RatePlan RatePlanCode=\""+ratePlan.getRatePlanCode()+"\">");
			if(ratePlan.getMealsIncluded() != null)
			{
				StringBuilder mealplancodes = new StringBuilder("");
				RatePlanType.MealsIncluded mealplan = ratePlan.getMealsIncluded();
				for(int mpc =0; mpc<mealplan.getMealPlanCodes().size();mpc ++)
				{
					if(mpc == mealplan.getMealPlanCodes().size()-1)
						mealplancodes.append(mealplan.getMealPlanCodes().get(mpc));
					else
						mealplancodes.append(mealplan.getMealPlanCodes().get(mpc)+",");
				}
				String mealplanindicator = (mealplan.isMealPlanIndicator() != null)?"MealPlanIndicator=\""+mealplan.isMealPlanIndicator()+"\"":"";			
				reqRatePlan.append("<MealsIncluded MealPlanCodes=\""+mealplancodes+"\" "+mealplanindicator+"/>");
			}				
			reqRatePlan.append("</RatePlan>");
		}
		StringBuilder reqRoomRate  = new  StringBuilder();
		for (RoomStayType.RoomRates.RoomRate roomrateDetail : rs.getRoomRates().getRoomRates()) {
			
			//com.lintas.hotel.model.RoomStayType.RoomRates.RoomRate roomrateDetail = getRoomRate(rs, roomrate) ;

			/*  <RoomRate RoomTypeCode="RGL" NumberOfUnits="1" RatePlanCode="BO1" RatePlanCategory="Bar">
              <Rates>
                  <Rate EffectiveDate="2015-10-15" ExpireDate="2015-10-16" RateTimeUnit="DAY" UnitMultiplier="2">
                      <Base AmountBeforeTax="5100.0" AmountAfterTax="5100.0" CurrencyCode="INR">
                          <Taxes Amount="0" CurrencyCode="INR"/>
                      </Base>
                  </Rate>
              </Rates>
          </RoomRate>*/				
			
			reqRoomRate.append("<RoomRate RoomTypeCode=\""+roomrateDetail.getRoomTypeCode()+"\" NumberOfUnits=\""+hbc.getNumberOfUnits()+"\" RatePlanCode=\""+roomrateDetail.getRatePlanCode()+"\" RatePlanCategory=\""+roomrateDetail.getRatePlanCategory()+"\">");
			if(roomrateDetail.getRates() != null && roomrateDetail.getRates().getRates() != null && !roomrateDetail.getRates().getRates().isEmpty())
			{
				reqRoomRate.append("<Rates>");
				for (Rate rate : roomrateDetail.getRates().getRates()) {
					
					String effectivedate = (rate.getEffectiveDate() != null)?"EffectiveDate=\""+rate.getEffectiveDate()+"\"":"";
					String expirydate = (rate.getExpireDate() != null)?"ExpireDate=\""+rate.getExpireDate()+"\"":"";
					String ratetimeout = (rate.getRateTimeUnit() != null)?"RateTimeUnit=\""+rate.getRateTimeUnit()+"\"":"";			
					
					reqRoomRate.append("<Rate "+effectivedate+" "+expirydate+" "+ratetimeout+" UnitMultiplier=\""+hbc.getNumberOfUnits()+"\">");
					if(rate.getApiPrice() != null)
					{
						String amountbeforetax = (rate.getApiPrice().getAmountBeforeTax() != null)?"AmountBeforeTax=\""+rate.getApiPrice().getAmountBeforeTax()+"\"":"";
						String amountaftertax = (rate.getApiPrice().getAmountAfterTax() != null)?"AmountAfterTax=\""+rate.getApiPrice().getAmountAfterTax()+"\"":"";
						
						reqRoomRate.append("<Base "+amountbeforetax+" "+amountaftertax+" CurrencyCode=\""+apicred.getApiCurrency()+"\">");
						if(rate.getApiPrice().getTaxes() != null && !rate.getApiPrice().getTaxes().getTaxes().isEmpty())
						{
							for (TaxType tax : rate.getApiPrice().getTaxes().getTaxes()) {
								
								String amounttax = (tax.getAmount() != null)?"Amount=\""+tax.getAmount()+"\"":"";
								
								reqRoomRate.append("<Taxes "+amounttax+" CurrencyCode=\""+apicred.getApiCurrency()+"\"/>");
							}
						}
						reqRoomRate.append("</Base>");
					}
					reqRoomRate.append("</Rate>");
				}
				reqRoomRate.append("</Rates>");
			}				
			reqRoomRate.append("</RoomRate>");			
		}		
		StringBuilder reqGuestCount  = new  StringBuilder();
		for (GuestCount guestCount : hsc.getGuestcounts()) {	
			reqGuestCount.append("<GuestCount AgeQualifyingCode=\""+guestCount.getAgequalifyingcode()+"\" Count=\""+guestCount.getCount()+"\"/>");		
		}
		
		
		StringBuilder reqGuests  = new  StringBuilder();
		for (Profile p : hbc.getProfiles()) {
			
			reqGuests.append("<ResGuest ResGuestRPH=\""+p.getResGuestRPH()+"\" PrimaryIndicator=\""+p.getProfileType()+"\">"
			+ "<Profiles>"
			+ "<ProfileInfo>"
			+ "<UniqueID ID=\""+p.getId()+"\" Type=\"1\" ID_Context=\"Customer\"/>"
			+  "<Profile ProfileType=\""+p.getProfileType()+"\">"
					+ "<Customer>"
					+ "<PersonName>"
					+ "<NamePrefix>"+p.getCustomer().getPersonName().getNamePrefix()+"</NamePrefix>"
					+ "<GivenName>"+p.getCustomer().getPersonName().getGivenName()+"</GivenName>"
					+ "<Surname>"+p.getCustomer().getPersonName().getSurname()+"</Surname>"
					+ "</PersonName>"
					+ "<Telephone PhoneLocationType=\"6\" PhoneTechType=\"1\" PhoneNumber=\"9494730961\" FormattedInd=\"0\"/>"
					+ "<Email>"+p.getCustomer().getEmail()+"</Email>"
					+ "<Address Type=\"1\">"
					+ "<AddressLine>"+p.getCustomer().getAddress().getAddressLine().get(0)+"</AddressLine>"
					+ "<CityName>"+p.getCustomer().getAddress().getCityName()+"</CityName>"
					+ "<PostalCode>"+p.getCustomer().getAddress().getPostalCode()+"</PostalCode>"
					+ "<StateProv>"+p.getCustomer().getAddress().getStateProv()+"</StateProv>"
					+ "<CountryName>"+p.getCustomer().getAddress().getCountryName()+"</CountryName>"
					+ "</Address>"
					+ "</Customer>"
					+ "</Profile>"				
			+ "</ProfileInfo>"
			+ "</Profiles>"
			+ "</ResGuest>");
					
		}
		
		StringBuilder reqstr = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\""
				+ " xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" xmlns:htn=\"http://pms.ihotelier.com/HTNGService/services/HTNG2011BService\">"
				+ "<soapenv:Header>"
				+ "<wsa:MessageID>713</wsa:MessageID>"
				+ "<wsa:To>http://pmcoutllm01-t5.tcprod.local:8080/HtngSimulator/PMSInterfaceSimulator</wsa:To>"
				+ "<wsa:UserName>Reznext123</wsa:UserName>"
				+ "<wsa:PassWord>Reznext@123</wsa:PassWord>"
				+ "<wsa:ReplyTo>"
				+ "<wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>"
				+ "</wsa:ReplyTo>"
				+ "<wsa:Action>http://htng.org/2011B/HTNG2011B_SubmitResult</wsa:Action>"
				+ "</soapenv:Header>"
				+ "<soapenv:Body>"
				+ "<OTA_HotelResNotifRQ xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelResNotifRQ.xsd\" Version=\"1.003\""
				+ " EchoToken=\""+echoToken+"\" TimeStamp=\""+now.toString()+"\" ResStatus=\"Book\">"
				+ "<POS>"
				+ "<Source>"
				+ "<RequestorID Type=\"14\" ID=\""+HotelApiCredentials.LINTAS_REQUESTERID+"\"/>"
				+ "<BookingChannel Type=\"5\" Primary=\"1\">"
				+ "<CompanyName Code=\"WEB\">WEB</CompanyName>"
				+ "</BookingChannel>"
				+ "</Source>"
				+ "</POS>"
				+ "<HotelReservations>"
				+ "<HotelReservation CreatorID=\"channelid\" CreateDateTime=\""+now.toString()+"\" ResStatus=\"Reserved\">"
				+ "<UniqueID ID=\"133\" Type=\"14\"/>"
				+ "<RoomStays>"
				+ "<RoomStay MarketCode=\" \" SourceOfBusiness=\"WEB \">"
				+ "<RatePlans>"
				+ 	reqRatePlan
				+ "</RatePlans>"
				+ "<RoomRates>"
				+	reqRoomRate
				+ "</RoomRates>"
				+ "<GuestCounts IsPerRoom=\"1\">"
				+ 	reqGuestCount
				+ "</GuestCounts>"
				+ "<TimeSpan End=\""+hsc.getDateend()+"\" Start=\""+hsc.getDatestart()+"\"/>"
				+ "<Total AmountBeforeTax=\""+ratemap.getAmountbeforeTax()+"\" CurrencyCode=\""+apicred.getApiCurrency()+"\">"
				+ "<Taxes Amount=\""+ratemap.getRoomrateTax()+"\"  CurrencyCode=\""+apicred.getApiCurrency()+"\"/>"
				+ "</Total>"
				+ "<BasicPropertyInfo HotelCode=\""+rs.getBasicPropertyInfo().getApiVendorID()+"\"/>"
				+ "<ResGuestRPHs>"
				+ "<ResGuestRPH RPH=\"0\"/>"
				+ "</ResGuestRPHs>"
				+ "</RoomStay>"
				+ "</RoomStays>"
				+ "<ResGuests>"
				+ reqGuests
				+ "</ResGuests>"
				+ "<ResGlobalInfo>"
				+ "<HotelReservationIDs>"
				+ "<HotelReservationID ResID_Value=\"133\" ResID_Type=\"14\" ResID_Source=\"OTA Name\"/>"
				+ "</HotelReservationIDs>"
				+ "</ResGlobalInfo>"
				+ "</HotelReservation>"
				+ "</HotelReservations>"
				+ "</OTA_HotelResNotifRQ>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>");	
		
		return reqstr;
	}
	
	public static StringBuilder getHotelCancellationRequestBodyReznext(HotelApiCredentials apicred, HotelSearchCommand hs, RoomInfoType roomInfoType,Long requestorID , String id,Long BookingChannel, Long Primary, String CompanyName) throws ClassNotFoundException, JAXBException
	{
		java.util.Date date= new java.util.Date();		
		Timestamp now = new Timestamp(date.getTime());

		/*<soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope" xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:htn="http://pms.ihotelier.com/HTNGService/services/HTNG2011BService">
		  <soapenv:Header>
		    <wsa:MessageID>713</wsa:MessageID>
		    <wsa:To>http://pmcoutllm01-t5.tcprod.local:8080/HtngSimulator/PMSInterfaceSimulator</wsa:To>
			<wsa:UserName>Reznext123</wsa:UserName>
			<wsa:PassWord>Reznext@123</wsa:PassWord>
		    <wsa:ReplyTo>
		      <wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>
		    </wsa:ReplyTo>
		    <wsa:Action>http://htng.org/2011B/HTNG2011B_SubmitResult</wsa:Action>
		  </soapenv:Header>
		  <soapenv:Body>
		    <OTA_HotelResNotifRQ xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.opentravel.org/OTA/2003/05 OTA_HotelResNotifRQ.xsd" Version="1.003" EchoToken="713" TimeStamp="2014-09-12T12:47:59" ResStatus="Cancel">
		      <POS>
		        <Source>
		          <RequestorID Type="14" ID="OTA Name" />
		          <BookingChannel Type="5" Primary="1">
		            <CompanyName Code="WEB">WEB</CompanyName>
		          </BookingChannel>
		        </Source>
		      </POS>
		      <HotelReservations>
		        <HotelReservation CreatorID="OTA Name" CreateDateTime="2013-03-14T12:00:00" LastModifiedDateTime="2013-03-14T12:00:00">
		          <UniqueID ID="133" Type="14" />
		          <BasicPropertyInfo HotelCode="7777" />
		          <ResGlobalInfo>
		            <HotelReservationIDs>
		              <HotelReservationID ResID_Value="133" ResID_Type="14" ResID_Source="OTA Name" />
		            </HotelReservationIDs>
		          </ResGlobalInfo>
		        </HotelReservation>
		      </HotelReservations>
		    </OTA_HotelResNotifRQ>
		  </soapenv:Body>
		</soapenv:Envelope>*/		
		
		StringBuilder reqstr = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">"
				+ "<soapenv:Header><wsa:From><wsa:SystemId>"+apicred.getSystemId()+"</wsa:SystemId>"
				+ "<wsa:Credential>"
				+ "<wsa:UserName>"+apicred.getUserName()+"</wsa:UserName><wsa:Password>"+apicred.getPassword()+"</wsa:Password>"
				+ "</wsa:Credential>"
				+ "</wsa:From>"
				+ "<wsa:TimeStamp>"+now.toString()+"</wsa:TimeStamp>"
				+ "<wsa:Action>OTA_HotelAvailRQ</wsa:Action>"
				+ "</soapenv:Header>"
				+ "<soapenv:Body>"
				+ "<OTA_HotelAvailRQ Version=\"4.000\" EchoToken=\"12345\" TransactionIdentifier=\"1\" ResStatus=\"Cancel\"   TimeStamp=\""+now.toString()+"\">"
				+ "<POS>"
				+ "<Source>"
				+ "<RequestorID Type=\"14\" ID=\"OTA Name\" />"
				+ "<BookingChannel Type=\"5\" Primary=\"1\">"
				+ "<CompanyName Code=\"WEB\">WEB</CompanyName>"
				+ "</BookingChannel>"
				+ "</Source>"
				+ "</POS>"				
				+ "<AvailRequestSegments>"
				+ "<AvailRequestSegment>"
				+ "<HotelSearchCriteria>"
				+ "<Criterion>"
				+ "<CityInfo CityName=\""+hs.getSearchCity().getReznextCity().getCity()+"\"/>"
				+ "<StayDateRange Start=\""+hs.getDatestart()+"\" End=\""+hs.getDateend()+"\"/>"
				+ "<RoomInfo NumberOfRooms=\""+roomInfoType.getNumberOfRooms()+"\">"
				+ "</Criterion>"
				+ "</HotelSearchCriteria>"
				+ "</AvailRequestSegment>"
				+ "</AvailRequestSegments>"
				+ "</OTA_HotelAvailRQ>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>");
		return reqstr;
	}
}
