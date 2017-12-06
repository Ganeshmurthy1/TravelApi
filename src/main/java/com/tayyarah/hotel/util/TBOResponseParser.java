package com.tayyarah.hotel.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ProtocolException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tayyarah.api.hotel.rezlive.model.CancellationPolicyResponse;
import com.tayyarah.api.hotel.rezlive.model.HotelFindResponse.Hotels.Hotel.RoomDetails.RoomDetail;
import com.tayyarah.api.hotel.tbo.book.model.HotelBookResponse;
import com.tayyarah.api.hotel.tbo.model.BedType;
import com.tayyarah.api.hotel.tbo.model.CancellationPolicy;
import com.tayyarah.api.hotel.tbo.model.HotelBlockResponse;
import com.tayyarah.api.hotel.tbo.model.HotelIRoomInfoResponse;
import com.tayyarah.api.hotel.tbo.model.HotelInfoResponse;
import com.tayyarah.api.hotel.tbo.model.HotelResult;
import com.tayyarah.api.hotel.tbo.model.HotelRoomsDetail;
import com.tayyarah.api.hotel.tbo.model.HotelSearchResponse;
import com.tayyarah.api.hotel.tbo.model.Price;

import com.tayyarah.hotel.model.RoomCombinations;
import com.tayyarah.hotel.model.RoomTypeType.Occupancy;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.company.entity.Company;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelCancelResponse;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.AddressType;
import com.tayyarah.hotel.model.AmountDeterminationType;
import com.tayyarah.hotel.model.AmountPercentType;
import com.tayyarah.hotel.model.AmountType.Discount;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.BasicPropertyInfoType.ContactNumbers.ContactNumber;
import com.tayyarah.hotel.model.BasicPropertyInfoType.Position;
import com.tayyarah.hotel.model.CancelPenaltiesType;
import com.tayyarah.hotel.model.CancelPenaltyType;
import com.tayyarah.hotel.model.CancelPenaltyType.Deadline;
import com.tayyarah.hotel.model.CancelRuleType;
import com.tayyarah.hotel.model.CountryNameType;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelReservationIDsType;
import com.tayyarah.hotel.model.HotelReservationsType;
import com.tayyarah.hotel.model.HotelReservationsType.HotelReservation;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.OTAHotelResRS.CancellationInformations.CancellationInformation;
import com.tayyarah.hotel.model.PenaltyDescription;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.ResGlobalInfoType;
import com.tayyarah.hotel.model.RoomBookingKeyMap;
import com.tayyarah.hotel.model.RoomCombination;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.RoomStayType.RatePlans;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts.GuestCount;
import com.tayyarah.hotel.model.RoomStayType.RoomTypes;
import com.tayyarah.hotel.model.RoomTypeType;
import com.tayyarah.hotel.model.RoomTypeType.Room;
import com.tayyarah.hotel.reposit.entity.LintasConstantFactory;
import com.tayyarah.hotel.model.Status;
import com.tayyarah.hotel.model.TaxType;
import com.tayyarah.hotel.model.TaxesType;
import com.tayyarah.hotel.model.TimeUnitType;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.model.UniqueIDType;



public class TBOResponseParser {
	static final Logger logger = Logger.getLogger(TBOResponseParser.class);
	@Autowired
	HotelIdFactoryImpl hotelIdFactory;	
	public TBOResponseParser() {
		// TODO Auto-generated constructor stub
	}	
	public List<Room> getBookedRooms(RoomDetail roomDetail)
	{
		/*	
		<Type>
        <![CDATA[King Hilton, Deluxe Room, 1 King Bed - YearEnd RO|King Hilton, Deluxe Room, 1 King Bed - YearEnd RO]]>
    </Type>
    <BookingKey>BcGxDcAwCATAiV4yH-DNBhkhbbBEk_373GlOXx5FqkqjfLs8JvYWy7Px3MQClwXMQIdlmoTvBw</BookingKey>
    <Adults>1|1</Adults>
    <Children>0|0</Children>
    <ChildrenAges>0|0</ChildrenAges>
    <TotalRooms>2</TotalRooms>
    <TotalRate>6478.51174892|6478.51174892</TotalRate>
    <RoomDescription>
        <![CDATA[]]>
    </RoomDescription>
    <TermsAndConditions>
        <![CDATA[]]>
    </TermsAndConditions>*/
		List<Room> rooms = new ArrayList<Room>();	
		String delimeter = "\\|";
		Integer noofrooms = Integer.valueOf(roomDetail.getTotalRooms());		
		String[] names = roomDetail.getType().split(delimeter);
		String[] adults = roomDetail.getAdults().split(delimeter);
		String[] children = roomDetail.getChildren().split(delimeter);
		String[] childrenages = roomDetail.getChildrenAges().split(delimeter);
		String[] totalrates = roomDetail.getTotalRate().split(delimeter);
		int index = 0;
		for (String name : names) {
			Room room = new Room(); 
			room.setName(name);
			room.setAdults(Integer.valueOf(adults[index]));
			room.setChildren(Integer.valueOf(children[index]));
			room.setChildrenages(childrenages[index]);
			room.setTotal(new BigDecimal(totalrates[index]));
			room.setTotalWithoutMarkup(new BigDecimal(totalrates[index]));
			room.setRoomIndex(index);
			rooms.add(room);
			index++;
		}


		return rooms;
	}

	public RoomStay convertTBOToNative(HotelApiCredentials api, HotelSearchCommand hsc, HotelResult hotel, HotelSearchResponse hotelSearchResponse) throws HibernateException, ClassNotFoundException, JAXBException, ParseException, CloneNotSupportedException 
	{	
		BigDecimal minbase = new BigDecimal(0);	
		RoomStay troomstay = new RoomStay();
		BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
		int noofdays = CommonUtil.getNoofStayDays(hsc);     
		if(hotel != null)
		{
			tbasic.setHotelName(hotel.getHotelName());
			tbasic.setApiVendorID(hotel.getHotelCode());
			tbasic.setHotelCode(api.getId()+"-"+hotel.getHotelCode());
			tbasic.setImageurl(hotel.getHotelPicture());
			Position hpos = new Position();
			hpos.setLongitude(hotel.getLongitude());
			hpos.setLatitude(hotel.getLatitude());		
			tbasic.setPosition(hpos);
			AddressType laddress = new AddressType();
			laddress.setCityName(hsc.getCity());
			CountryNameType cn = new CountryNameType();
			cn.setCode(hsc.getCountrycode());
			cn.setValue(hsc.getCountry());
			laddress.setCountryName(cn);
			List<String> addresslines = new ArrayList<String>();
			addresslines.add(hotel.getHotelAddress());
			laddress.setAddressLines(addresslines);
			List<ContactNumber> contactnos = new ArrayList<ContactNumber>();
			contactnos.add(new ContactNumber(hotel.getHotelContactNo()));
			tbasic.setAddress(laddress);
			tbasic.setContactNumbers(contactnos);
			tbasic.setReviewCount("");
			tbasic.setReviewRating("");
			tbasic.setArea(hotel.getHotelLocation());
			tbasic.setArea_Seo_Id("");		
			tbasic.setHotel_Star(hotel.getStarRating());
			tbasic.setHotelClass("");
			tbasic.setWeekdayRank(1);
			tbasic.setWeekendRank(1);
			tbasic.setChainCode("");				
			Price p = hotel.getPrice();
			BigDecimal totaltax = p.getTax();
			BigDecimal taxperday = new BigDecimal(0);
			BigDecimal roomPricePerDay = ((p.getRoomPrice() == null)?new BigDecimal("0"):p.getRoomPrice())
					.add((p.getExtraGuestCharge() == null)?new BigDecimal("0"):p.getExtraGuestCharge())
					.add((p.getChildCharge() == null)?new BigDecimal("0"):p.getChildCharge())
					.add((p.getOtherCharges() == null)?new BigDecimal("0"):p.getOtherCharges());


			BigDecimal discountperday = new BigDecimal(0);
			try{				
				minbase = p.getOfferedPrice().divide(new BigDecimal(noofdays));
				tbasic.setApiPrice(minbase);
				tbasic.setBasePrice(minbase);
				tbasic.setBasePriceWithoutMarkup(minbase);
				tbasic.setBookingPrice(minbase);
			}
			catch(ArithmeticException ex)
			{				
				minbase = p.getOfferedPrice().divide(new BigDecimal(noofdays), 7, RoundingMode.HALF_UP);
				tbasic.setApiPrice(minbase);
				tbasic.setBasePrice(minbase);
				tbasic.setBasePriceWithoutMarkup(minbase);
				tbasic.setBookingPrice(minbase);
			}
			HashMap<Integer, String> apiProviderMap = new HashMap<Integer, String>();
			apiProviderMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, hotel.getHotelCode());
			tbasic.setApiProviderMap(apiProviderMap);
			tbasic.setApiProvider(HotelApiCredentials.API_TBO_INTERNATIONAL);
			tbasic.setIsOfflineBooking(false);
			tbasic.setApiEndUserIp(hsc.getEndUserIp());
			tbasic.setApiTokenId(api.getTokenId());
			tbasic.setApiTraceId(hotelSearchResponse.getHotelSearchResult().getTraceId());
			tbasic.setApiResultIndex(hotel.getResultIndex());
			troomstay.setBasicPropertyInfo(tbasic);	
		}		
		return troomstay;
	}

	public RoomStay convertTBOToNativeHotelInfo(HotelApiCredentials api, HotelSearchCommand hsc, OTAHotelAvailRS.RoomStays.RoomStay rs, HotelInfoResponse hotelInfoResponse) throws HibernateException, ClassNotFoundException, JAXBException, ParseException, CloneNotSupportedException 
	{	
		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();
		if(hotelInfoResponse != null && hotelInfoResponse.getHotelInfoResult() != null && hotelInfoResponse.getHotelInfoResult().getHotelDetails() != null)
		{
			tbasic.setHotelimages(hotelInfoResponse.getHotelInfoResult().getHotelDetails().getImages());
			List<Facility> hotelAmenities = new ArrayList<Facility>();
			for (String facility : hotelInfoResponse.getHotelInfoResult().getHotelDetails().getHotelFacilities()) {
				Facility f = new Facility();
				f.setDescription(facility);
				f.setAmenityType(LintasConstantFactory.FACILITY_TYPE);
				hotelAmenities.add(f);
			}
			tbasic.setHotelAmenities(hotelAmenities);
			Position hpos = new Position();
			hpos.setLongitude(hotelInfoResponse.getHotelInfoResult().getHotelDetails().getLongitude());
			hpos.setLatitude(hotelInfoResponse.getHotelInfoResult().getHotelDetails().getLatitude());		
			tbasic.setPosition(hpos);
			AddressType laddress = new AddressType();
			laddress.setCityName(hsc.getCity());
			CountryNameType cn = new CountryNameType();
			cn.setCode(hsc.getCountrycode());
			cn.setValue(hsc.getCountry());
			laddress.setCountryName(cn);
			List<String> addresslines = new ArrayList<String>();
			addresslines.add(hotelInfoResponse.getHotelInfoResult().getHotelDetails().getAddress());
			laddress.setAddressLines(addresslines);
			List<ContactNumber> contactnos = new ArrayList<ContactNumber>();
			contactnos.add(new ContactNumber(hotelInfoResponse.getHotelInfoResult().getHotelDetails().getHotelContactNo()));
			tbasic.setAddress(laddress);
			tbasic.setContactNumbers(contactnos);
			tbasic.setReviewCount("");
			tbasic.setReviewRating("");
			tbasic.setArea_Seo_Id("");
			tbasic.setHotel_Star(hotelInfoResponse.getHotelInfoResult().getHotelDetails().getStarRating());
			tbasic.setHotelClass("");
			tbasic.setWeekdayRank(1);
			tbasic.setWeekendRank(1);
			tbasic.setChainCode("");
			rs.setBasicPropertyInfo(tbasic);	
		}		
		return rs;
	}

	public static boolean isDatesMatchs(String fromdate, String todate, String cancelpenaltytext) throws java.text.ParseException
	{
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date1 = sdf.parse(fromdate);
		cal1.setTime(date1);
		Date date2 = sdf.parse(todate);
		cal2.setTime(date2);
		DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String datefromtext = targetFormat.format(date1); 
		String datetotext = targetFormat.format(date2); 
		if(cancelpenaltytext.contains(datefromtext) && cancelpenaltytext.contains(datetotext))
			return true;
		else 
			return false;
	}

	public static String getCommonPojoDate(String fromdate) throws java.text.ParseException
	{
		Calendar cal2 = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		Date date1 = sdf.parse(fromdate);
		String datefromtext = targetFormat.format(date1); 
		return datefromtext;
	}

	public static String getCancellationPenalty(String cancelpenaltytext, CancellationPolicy cancellationPolicy)
	{	
		String cancelPenalty = "";
		if(cancelpenaltytext == null || cancelpenaltytext.length() == 0)
			return cancelPenalty;
		else
		{
			try{
				String delimeter = "\\|";				
				String[] ids = cancelpenaltytext.split(delimeter);				
				for (String string : ids) {						
					if(string.length()> 5 && isDatesMatchs(cancellationPolicy.getFromDate(), cancellationPolicy.getToDate(), string))
					{
						return string;
					}
				}
			}
			catch(Exception e)
			{
				logger.info("Exception happened.."+e.getMessage());
			}

		}
		return cancelPenalty;
	}

	public static Integer getRoomRegIndex(RoomCombinations roomCombinations, Integer roomindex)
	{	
		Integer roomregindex = 1;		
		for (RoomCombination roomCombination: roomCombinations.getRoomCombination()) {
			if(roomCombination.getRoomIndex().contains(roomindex))
			{				
				return roomregindex;
			}
			roomregindex ++;
		}		
		return -1;
	}

	public static String getCancellationBasisType(Integer chargeType)
	{	
		//   Amount = 1,            Percentage = 2,            Nights = 3 

		String basisType = "Amount";
		switch (chargeType) {
		case 1:
			basisType = "Amount";
			break;
		case 2:
			basisType = "Percentage";
			break;
		case 3:
			basisType = "Nights";
			break;

		default:
			basisType = "Amount";
			break;
		}
		return basisType;
	}



	public RoomStay convertTBOToNativeHotelRoomInfo(HotelApiCredentials api, HotelSearchCommand hsc, OTAHotelAvailRS.RoomStays.RoomStay rs, HotelIRoomInfoResponse hotelIRoomInfoResponse) throws HibernateException, ClassNotFoundException, JAXBException, ParseException, CloneNotSupportedException 
	{	
		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();
		//tbasic.setHotelCode(rs.getId());	
		int noofdays = CommonUtil.getNoofStayDays(hsc);
		if(hotelIRoomInfoResponse != null && hotelIRoomInfoResponse.getGetHotelRoomResult() != null && hotelIRoomInfoResponse.getGetHotelRoomResult().getHotelRoomsDetails() != null && hotelIRoomInfoResponse.getGetHotelRoomResult() .getRoomCombinations() != null)
		{
			RoomCombinations roomCombinations = new RoomCombinations();
			String infoSource = (hotelIRoomInfoResponse.getGetHotelRoomResult() .getRoomCombinations() != null && hotelIRoomInfoResponse.getGetHotelRoomResult() .getRoomCombinations().getInfoSource() != null)?hotelIRoomInfoResponse.getGetHotelRoomResult() .getRoomCombinations().getInfoSource():"";
			roomCombinations.setInfoSource(infoSource);
			List<RoomCombination> roomCombinationList = new ArrayList<RoomCombination>();
			for (com.tayyarah.api.hotel.tbo.model.RoomCombination roomCombinationtbo : hotelIRoomInfoResponse.getGetHotelRoomResult() .getRoomCombinations().getRoomCombination()) {
				RoomCombination roomCombination = new RoomCombination();
				roomCombination.setRoomIndex(roomCombinationtbo.getRoomIndex());
				roomCombinationList.add(roomCombination);				
			}
			roomCombinations.setRoomCombination(roomCombinationList);
			roomCombinations.setApiProvider(HotelApiCredentials.API_TBO_INTERNATIONAL);
			List<RoomCombinations> supplierRoomGroups = new ArrayList<RoomCombinations>();
			supplierRoomGroups.add(roomCombinations);
			rs.setSupplierRoomGroups(supplierRoomGroups);

			RoomTypes troomtypes = new RoomTypes();
			List<RoomTypeType> lrtlist = new ArrayList<RoomTypeType>();

			RatePlans trateplans = new RatePlans();
			List<RatePlanType> lplantlist = new ArrayList<RatePlanType>();

			RoomRates troomrates = new RoomRates();
			List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();

			for (HotelRoomsDetail hotelRoomsDetail : hotelIRoomInfoResponse.getGetHotelRoomResult().getHotelRoomsDetails()) {			


				/* {
		                "ChildCount": 0,
		                "RequireAllPaxDetails": true,
		                "RoomIndex": 1,
		                "RoomTypeCode": "Jd/MgHw6DOYoRT3QhX35XA==|EJ|GR-ALL-BB|270###",
		                "RoomTypeName": "Double or Twin EXECUTIVE",
		                "RatePlanCode": "BB-E10",
		                "RatePlanName": "BED AND BREAKFAST",
		                "InfoSource": "OpenCombination",
		                "SequenceNo": "1",
		                "DayRates": [
		                    {
		                        "Amount": 1500.26,
		                        "Date": "2016-10-18T00:00:00"
		                    },
		                    {
		                        "Amount": 1500.26,
		                        "Date": "2016-10-19T00:00:00"
		                    }
		                ],
		                "SupplierPrice": null,
		                "Price": {
		                    "CurrencyCode": "INR",
		                    "RoomPrice": 3000.53,
		                    "Tax": 0,
		                    "ExtraGuestCharge": 0,
		                    "ChildCharge": 0,
		                    "OtherCharges": 0,
		                    "Discount": 0,
		                    "PublishedPrice": 3000.53,
		                    "PublishedPriceRoundedOff": 3001,
		                    "OfferedPrice": 3000.53,
		                    "OfferedPriceRoundedOff": 3001,
		                    "AgentCommission": 0,
		                    "AgentMarkUp": 0,
		                    "ServiceTax": 0,
		                    "TDS": 0
		                },
		                "RoomPromotion": "",
		                "Amenities": [
		                    "BED AND BREAKFAST"
		                ],
		                "SmokingPreference": "NoPreference",
		                "BedTypes": [],
		                "HotelSupplements": [],
		                "LastCancellationDate": "2016-10-12T00:00:00",
		                "CancellationPolicies": [
		                    {
		                        "Charge": 1500,
		                        "ChargeType": 1,
		                        "Currency": "INR",
		                        "FromDate": "2016-10-13T00:00:00",
		                        "ToDate": "2016-10-16T00:00:00"
		                    },
		                    {
		                        "Charge": 100,
		                        "ChargeType": 2,
		                        "Currency": "INR",
		                        "FromDate": "2016-10-16T00:00:01",
		                        "ToDate": "2016-10-20T23:59:59"
		                    }
		                ],
		                "CancellationPolicy": "Double or Twin EXECUTIVE#^#INR 0.00 will be charged, If cancelled between 13-Oct-2016 00:00:00 and 16-Oct-2016 00:00:00.|0.00% of total amount will be charged, If cancelled between 16-Oct-2016 00:00:01 and 20-Oct-2016 23:59:59.|#!#"
		            }

				 */


				//logger.info("object transformation---: tbo to common. -----getRoomTypeName- "+hotelRoomsDetail.getRoomTypeName());

				RoomBookingKeyMap roomBookingKeyMap = HotelIdFactoryImpl.getInstance().createRoomRateBookingKeySimple(hotelRoomsDetail.getRoomTypeCode(), hotelRoomsDetail.getRatePlanCode());
				RoomTypeType lrt = new RoomTypeType();		
				Hotelroomdescription lroomdes = new Hotelroomdescription();					
				lrt.setRoomType(hotelRoomsDetail.getRoomTypeName());
				lrt.setRoomTypeCode(roomBookingKeyMap.getRoomTypeCode());	
				Integer noofunits = Integer.valueOf(1);
				lrt.setNumberOfUnits(noofunits);					
				lroomdes.setId(1);
				lroomdes.setImagePath("");	
				lroomdes.setDescription(hotelRoomsDetail.getRoomTypeName());						
				lrt.setRoomDescription(lroomdes);
				lrt.setTermsAndConditions("No Information available..");
				List<Facility> facilitiesRoom = new ArrayList<Facility>();	
				for (String facility : hotelRoomsDetail.getAmenities()) {
					Facility f = new Facility();
					f.setDescription(facility);
					f.setAmenityType(LintasConstantFactory.FACILITY_TYPE_ROOM);
					facilitiesRoom.add(f);
				}				
				lrt.setAmenities(facilitiesRoom);
				List<Occupancy> locclist = new ArrayList<RoomTypeType.Occupancy>();
				Occupancy locc = new Occupancy();
				locc.setAgeQualifyingCode("");					
				locc.setAgeBucket("");
				locc.setMinOccupancy(1);
				locclist.add(locc);
				lrt.setOccupancies(locclist);		
				//Constructing Rateplans...
				RatePlanType lrp = new RatePlanType();
				lrp.setRatePlanType(hotelRoomsDetail.getRatePlanName());
				lrp.setRatePlanCode(hotelRoomsDetail.getRatePlanCode());
				lrp.setRatePlanName(hotelRoomsDetail.getRatePlanName());
				lrp.setBookingCode(roomBookingKeyMap.getBookingKey());

				//logger.info("object transformation---:  parsing------ rp.getRatePlanCode()--"+roomBookingKeyMap.getRatePlanCode());
				CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();			
				//logger.info("object transformation---:  parsing------ rp.getRatePlanCode() -"+roomBookingKeyMap.getRatePlanCode());
				List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();

				for (CancellationPolicy cancellationPolicy : hotelRoomsDetail.getCancellationPolicies()) {

					/* 
							"Charge" : 10621,
						     "ChargeType" : 1,
						        "Currency" : "INR",
						        "FromDate" : "2016-05-04T00:00:00",
						        "ToDate" : "2016-05-08T00:00:00"
					 */

					CancelPenaltyType lcpt = new CancelPenaltyType();
					lcpt.setStart(getCommonPojoDate(cancellationPolicy.getFromDate()));
					lcpt.setEnd(getCommonPojoDate(cancellationPolicy.getToDate()));
					Deadline ldeadline = new Deadline();	
					ldeadline.setOffsetTimeUnit(TimeUnitType.DAY);
					ldeadline.setOffsetDropTime("1");			
					ldeadline.setOffsetUnitMultiplier(1);



					ldeadline.setFromDate(getCommonPojoDate(cancellationPolicy.getFromDate()));
					ldeadline.setToDate(getCommonPojoDate(cancellationPolicy.getToDate()));
					lcpt.setDeadline(ldeadline);
					AmountPercentType amountPercentType = new AmountPercentType();
					amountPercentType.setBasisType(getCancellationBasisType(cancellationPolicy.getChargeType()));
					amountPercentType.setAmount(cancellationPolicy.getCharge());
					amountPercentType.setCurrencyCode(cancellationPolicy.getCurrency());


					AmountPercentType apiAmountPercent = new AmountPercentType();
					AmountPercentType baseAmountPercent = new AmountPercentType();  
					AmountPercentType baseWithoutMarkupAmountPercent = new AmountPercentType();


					BeanUtils.copyProperties(amountPercentType, apiAmountPercent);
					BeanUtils.copyProperties(amountPercentType, baseAmountPercent);
					BeanUtils.copyProperties(amountPercentType, baseWithoutMarkupAmountPercent);	

					lcpt.setAmountPercent(amountPercentType);
					lcpt.setApiAmountPercent(apiAmountPercent);
					lcpt.setBaseAmountPercent(baseAmountPercent);
					lcpt.setBaseWithoutMarkupAmountPercent(baseWithoutMarkupAmountPercent);

					List<PenaltyDescription> lpenlist = new ArrayList<PenaltyDescription>();
					PenaltyDescription lpdes = new PenaltyDescription();
					lpdes.setDrescription(getCancellationPenalty(hotelRoomsDetail.getCancellationPolicy(), cancellationPolicy));
					lpenlist.add(lpdes);
					cancellist.add(lcpt);
				}

				cancelpenalities.setCancelPenalties(cancellist);
				lrp.setCancelPenalties(cancelpenalities);	

				//RoomRate Construction..
				RoomStayType.RoomRates.RoomRate lrr = new RoomStayType.RoomRates.RoomRate();
				lrr.setRatePlanCode(roomBookingKeyMap.getRatePlanCode());	
				lrr.setBookingCode(roomBookingKeyMap.getBookingKey());
				lrr.setRoomID(roomBookingKeyMap.getRoomTypeCode());
				lrr.setRoomTypeCode(roomBookingKeyMap.getRoomTypeCode());
				lrr.setRequireAllPaxDetails(hotelRoomsDetail.getRequireAllPaxDetails());
				lrr.setSupplierRoomIndex(hotelRoomsDetail.getRoomIndex());
				lrr.setSupplierHotelCode(tbasic.getApiVendorID());
				lrr.setRoomTypeName(hotelRoomsDetail.getRoomTypeName());
				lrr.setRatePlanName(hotelRoomsDetail.getRatePlanName());
				lrr.setInfoSource(hotelRoomsDetail.getInfoSource());
				lrr.setSequenceNo(hotelRoomsDetail.getSequenceNo());
				lrr.setSmokingPreference(hotelRoomsDetail.getSmokingPreference());

				List<com.tayyarah.hotel.model.BedType> bedTypes = new  ArrayList<com.tayyarah.hotel.model.BedType>();
				for (BedType bedType : hotelRoomsDetail.getBedTypes()) {
					com.tayyarah.hotel.model.BedType bedTypeCommon = new com.tayyarah.hotel.model.BedType();
					bedTypeCommon.setBedTypeCode(bedType.getBedTypeCode());
					bedTypeCommon.setBedTypeDescription(bedType.getBedTypeDescription());
					bedTypes.add(bedTypeCommon);
				}				
				lrr.setBedTypes(bedTypes);

				lrr.setHotelSupplements(hotelRoomsDetail.getHotelSupplements());
				lrr.setLastCancellationDate(hotelRoomsDetail.getLastCancellationDate());

				RateType lrates = new RateType();
				List<Rate> lratelist = new ArrayList<Rate>();


				Rate lrate = new Rate();
				lrate.setRoomIndex(1);	
				lrate.setName(hotelRoomsDetail.getRoomTypeName());
				lrate.setAdults(1);
				lrate.setChildrenages("1|2");
				lrate.setChildren(2);		

				//lrate.setExpireDate(rtype.getExpireDate());
				//lrate.setEffectiveDate(rtype.getEffectiveDate());

				//Total base
				/*"Price" : {
					>         "CurrencyCode" : "INR",
					>         "RoomPrice" : 1170.33,
					>         "Tax" : 241,
					>         "ExtraGuestCharge" : 203.11,
					>         "ChildCharge" : 0,
					>         "OtherCharges" : 0,
					>         "Discount" : 580,
					>         "PublishedPrice" : 1614.44,
					>         "PublishedPriceRoundedOff" : 1614,
					>         "OfferedPrice" : 1614.44,
					>         "OfferedPriceRoundedOff" : 1614,
					>         "AgentCommission" : 0,
					>         "AgentMarkUp" : 0,
					>         "ServiceTax" : 0.00,
					>         "TDS" : 0
					>       },*/

				Price p = hotelRoomsDetail.getPrice();
				BigDecimal totaltax = p.getTax();
				BigDecimal taxperday = new BigDecimal(0);
				BigDecimal roomPricePerDay = ((p.getRoomPrice() == null)?new BigDecimal("0"):p.getRoomPrice())
						.add((p.getExtraGuestCharge() == null)?new BigDecimal("0"):p.getExtraGuestCharge())
						.add((p.getChildCharge() == null)?new BigDecimal("0"):p.getChildCharge())
						.add((p.getOtherCharges() == null)?new BigDecimal("0"):p.getOtherCharges());

				//BigDecimal totaldiscount = p.getDiscount();
				BigDecimal discountperday = new BigDecimal(0);

				//Single day base
				TotalType ltotperday = new TotalType();
				//logger.info("object transformation---: tbo to common. -----tbasic-rs.getPrice() published price  "+p.getPublishedPrice());	
				//logger.info("object transformation---: tbo to common. -----tbasic-rs.getPrice() offered price  "+p.getOfferedPriceRoundedOff());		




				try{
					ltotperday.setAmountBeforeTax(AmountRoundingModeUtil.roundingModeForHotel(roomPricePerDay.divide(new BigDecimal(noofdays),2, RoundingMode.UP)));
					//ltotperday.setAmountAfterTax(p.getOfferedPriceRoundedOff().divide(new BigDecimal(noofdays)));
					ltotperday.setAmountAfterTax(AmountRoundingModeUtil.roundingModeForHotel(p.getOfferedPrice().divide(new BigDecimal(noofdays),2, RoundingMode.UP)));
					logger.info("totaltax before round "+totaltax.divide(new BigDecimal(noofdays),1, RoundingMode.HALF_UP));	
					taxperday = AmountRoundingModeUtil.roundingModeForHotel(totaltax.divide(new BigDecimal(noofdays),1, RoundingMode.HALF_UP));
					//discountperday = totaldiscount.divide(new BigDecimal(noofdays));
					logger.info("totaltax after round "+ taxperday);	
				}
				catch(ArithmeticException ex)
				{
					logger.info("object transformation---: tbo to common. -----tbasic-rs.getPrice() divide ArithmeticException "+ex.getMessage());			
					ltotperday.setAmountBeforeTax(AmountRoundingModeUtil.roundingModeForHotel( roomPricePerDay.divide(new BigDecimal(noofdays),2, RoundingMode.UP)));
					ltotperday.setAmountAfterTax(AmountRoundingModeUtil.roundingModeForHotel(p.getOfferedPrice().divide(new BigDecimal(noofdays),2, RoundingMode.UP)));
					//ltotperday.setAmountAfterTax(p.getOfferedPriceRoundedOff().divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP));
					taxperday = AmountRoundingModeUtil.roundingModeForHotel(totaltax.divide(new BigDecimal(noofdays),1, RoundingMode.HALF_UP));
					//discountperday = totaldiscount.divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP);
					//logger.info("object transformation---: tbo to common. -----rateplan single day price "+ltotperday.getAmountBeforeTax());			

				}
				////logger.info("object transformation---: tax parsing------ ");
				TaxesType ltaxperday = new TaxesType();			
				List<TaxType> ltaxlistperday  = new ArrayList<TaxType>();
				TaxType lrttperday = new TaxType();
				lrttperday.setAmount(taxperday);
				lrttperday.setCode("");
				lrttperday.setType(AmountDeterminationType.EXCLUSIVE);
				ltaxlistperday.add(lrttperday);
				ltaxperday.setAmount(taxperday);
				ltaxperday.setTaxes(ltaxlistperday);				
				ltotperday.setTaxes(ltaxperday);

				List<Discount> ldiscountlist  = new ArrayList<Discount>();
				Discount d = new Discount();
				d.setAmountAfterTax(discountperday);
				d.setAmountBeforeTax(discountperday);
				d.setAppliesTo("Base");					
				ldiscountlist.add(d);				
				ltotperday.setDiscounts(ldiscountlist);


				ltotperday.setTotalRoomPrice(p.getRoomPrice());
				ltotperday.setTotalExtraGuestCharge(p.getExtraGuestCharge());
				ltotperday.setTotalChildCharge(p.getChildCharge());
				ltotperday.setTotalOtherCharges(p.getOtherCharges());
				ltotperday.setTotalAmountPayable(p.getOfferedPrice());
				ltotperday.setApiPublishedPriceTotal(p.getPublishedPrice());
				ltotperday.setTotalOfferedPrice(p.getOfferedPrice());
				ltotperday.setTotalAgentCommission(p.getAgentCommission());
				ltotperday.setTotalAgentMarkUp(p.getAgentMarkUp());
				ltotperday.setTotalServiceTax(p.getServiceTax());
				ltotperday.setTotalTDS(p.getTDS());
				ltotperday.setTotalTax(p.getTax());
				ltotperday.setTotalDiscount(p.getDiscount());


				TotalType ltotsinglebase = new TotalType();
				TotalType ltotsinglebasewithoutmarkup = new TotalType();
				TotalType ltotsinglebooking = new TotalType();				
				BeanUtils.copyProperties(ltotperday, ltotsinglebase);
				BeanUtils.copyProperties(ltotperday, ltotsinglebasewithoutmarkup);
				BeanUtils.copyProperties(ltotperday, ltotsinglebooking);				
				lrate.setApiPrice(ltotperday);
				lrate.setBase(ltotsinglebase);			
				lrate.setBaseWithoutMarkUp(ltotsinglebasewithoutmarkup);
				lrate.setBookingPrice(ltotsinglebooking);




				//logger.info("\\\\\\\\*************************roomdetail -lrate.getBase()-"+lrate.getBase());	
				//logger.info("\\\\\\\\*************************roomdetail -lrate.getBaseWithoutMarkUp()-"+lrate.getBaseWithoutMarkUp());	
				//logger.info("\\\\\\\\*************************roomdetail -lrate.getApiPrice()-"+lrate.getApiPrice());	
				lratelist.add(lrate);	


				lrates.setRates(lratelist);
				GuestCounts gustcounts = new GuestCounts();
				List<GuestCount> gustcountslist = new ArrayList<GuestCount>();				
				gustcounts.setGuestCounts(gustcountslist);
				lrr.setGuestCounts(gustcounts);

				lrr.setRates(lrates);
				Integer roomregindex = getRoomRegIndex(roomCombinations, hotelRoomsDetail.getRoomIndex());//getRoomReqCombinationIndex
				lrr.setRoomReqIndex(roomregindex);

				if(roomregindex != -1)
				{
					lrratelist.add(lrr);
					lplantlist.add(lrp);
					lrtlist.add(lrt);
				}	

			}			

			troomtypes.setRoomTypes(lrtlist);	
			//logger.info("object transformation---: Roomtypes "+lrtlist.size());

			rs.setRoomTypes(troomtypes);

			trateplans.setRatePlen(lplantlist);	
			//logger.info("object transformation---: RatePlen "+lplantlist.size());

			rs.setRatePlans(trateplans);


			troomrates.setRoomRates(lrratelist);
			//logger.info("object transformation---: RoomRates "+lrratelist.size());

			rs.setRoomRates(troomrates);









		}


		return rs;
	}


	public RoomStay updateRoomStayOnBlock( HotelSearchCommand hsc, OTAHotelAvailRS.RoomStays.RoomStay rs, HotelBlockResponse hotelBlockResponse,Company company,Company parentCompany) throws ParseException,Exception
	{
		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();

		if(tbasic != null && tbasic.getApiProviderBasicMap()!= null && tbasic.getApiProviderBasicMap().containsKey(HotelApiCredentials.API_TBO_INTERNATIONAL))
			tbasic = tbasic.getApiProviderBasicMap().get(HotelApiCredentials.API_TBO_INTERNATIONAL);


		//tbasic.setHotelCode(rs.getId());		
		int noofdays = CommonUtil.getNoofStayDays(hsc);
		//"IsPriceChanged" : false,
		//"IsCancellationPolicyChanged" : false,		

		if(hotelBlockResponse != null && hotelBlockResponse.getBlockRoomResult() != null && hotelBlockResponse.getBlockRoomResult() .getHotelRoomsDetails() != null)
		{	

			if((hotelBlockResponse.getBlockRoomResult().getIsPriceChanged() || hotelBlockResponse.getBlockRoomResult().getIsCancellationPolicyChanged()) && hotelBlockResponse.getBlockRoomResult().getHotelRoomsDetails().size()>0)
			{
				RatePlans trateplans = new RatePlans();
				List<RatePlanType> lplantlist = new ArrayList<RatePlanType>();

				RoomRates troomrates = new RoomRates();
				List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();

				//logger.info("object transformation---: updateRoomStayOnBlock roomdetails size---"+hotelBlockResponse.getBlockRoomResult().getHotelRoomsDetails().size());


				for (HotelRoomsDetail hotelRoomsDetail : hotelBlockResponse.getBlockRoomResult().getHotelRoomsDetails()) {	
					//Constructing Rateplans...
					//logger.info("object transformation---: updateRoomStayOnBlock roomsummary available rateplen size---"+rs.getRatePlans().getRatePlan().size());

					for (RatePlanType rateplan : rs.getRatePlans().getRatePlan()) {
						RatePlanType lrp = rateplan;	
						if(hotelRoomsDetail.getRatePlanCode().equalsIgnoreCase(lrp.getRatePlanCode()))
						{
							//logger.info("object transformation---: Rateplan match found --update-");


							CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();			
							List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
							for (CancellationPolicy cancellationPolicy : hotelRoomsDetail.getCancellationPolicies()) {

								CancelPenaltyType lcpt = new CancelPenaltyType();
								lcpt.setStart(getCommonPojoDate(cancellationPolicy.getFromDate()));
								lcpt.setEnd(getCommonPojoDate(cancellationPolicy.getToDate()));
								//lcpt.setStart(getCommonPojoDate(cancellationPolicy.getFromDate()));
								lcpt.setEnd(getCommonPojoDate(cancellationPolicy.getToDate()));
								Deadline ldeadline = new Deadline();	
								//ldeadline.setOffsetTimeUnit(TimeUnitType.fromValue(cpt.getDeadline().getOffsetTimeUnit()));
								ldeadline.setOffsetTimeUnit(TimeUnitType.DAY);
								ldeadline.setOffsetDropTime("1");			
								ldeadline.setOffsetUnitMultiplier(1);


								//"2016-05-04T00:00:00" to //22 Dec 2015 00:00:00		
								ldeadline.setFromDate(getCommonPojoDate(cancellationPolicy.getFromDate()));
								ldeadline.setToDate(getCommonPojoDate(cancellationPolicy.getToDate()));
								lcpt.setDeadline(ldeadline);
								AmountPercentType amountPercentType = new AmountPercentType();
								amountPercentType.setBasisType(getCancellationBasisType(cancellationPolicy.getChargeType()));
								amountPercentType.setAmount(cancellationPolicy.getCharge());
								amountPercentType.setCurrencyCode(cancellationPolicy.getCurrency());



								AmountPercentType apiAmountPercent = new AmountPercentType();
								AmountPercentType baseAmountPercent = new AmountPercentType();  
								AmountPercentType baseWithoutMarkupAmountPercent = new AmountPercentType();


								BeanUtils.copyProperties(amountPercentType, apiAmountPercent);
								BeanUtils.copyProperties(amountPercentType, baseAmountPercent);
								BeanUtils.copyProperties(amountPercentType, baseWithoutMarkupAmountPercent);	

								lcpt.setAmountPercent(amountPercentType);
								lcpt.setApiAmountPercent(apiAmountPercent);
								lcpt.setBaseAmountPercent(baseAmountPercent);
								lcpt.setBaseWithoutMarkupAmountPercent(baseWithoutMarkupAmountPercent);

								//logger.info("object transformation---: updateRoomStayOnBlock getDeadline parsing------ rp.getRatePlanCode() -"+hotelRoomsDetail.getRatePlanCode());

								List<PenaltyDescription> lpenlist = new ArrayList<PenaltyDescription>();
								PenaltyDescription lpdes = new PenaltyDescription();
								lpdes.setDrescription(getCancellationPenalty(hotelRoomsDetail.getCancellationPolicy(), cancellationPolicy));
								lpenlist.add(lpdes);
								//lcpt.setPenaltyDescriptions(lpenlist);
								cancellist.add(lcpt);

							}
							cancelpenalities.setCancelPenalties(cancellist);
							lrp.setCancelPenalties(cancelpenalities);	
							lplantlist.add(lrp);
							break;
						}					
					}
					//RoomRate Construction..
					//logger.info("object transformation---: updateRoomStayOnBlock roomsummary available roomrate size---"+rs.getRoomRates().getRoomRates().size());

					for (RoomRate roomRate : rs.getRoomRates().getRoomRates()) {
						if(hotelRoomsDetail.getRoomTypeCode().equalsIgnoreCase(roomRate.getRoomTypeCode()) && (hotelRoomsDetail.getRoomIndex().compareTo(roomRate.getSupplierRoomIndex())==0))
						{
							//logger.info("object transformation---: roomrate match found --update-");
							RoomStayType.RoomRates.RoomRate lrr = roomRate;
							lrr.setSupplierRoomIndex(hotelRoomsDetail.getRoomIndex());
							//lrr.setSupplierHotelCode(tbasic.getApiVendorID());
							lrr.setRoomTypeName(hotelRoomsDetail.getRoomTypeName());
							lrr.setRatePlanName(hotelRoomsDetail.getRatePlanName());
							lrr.setInfoSource(hotelRoomsDetail.getInfoSource());
							lrr.setSequenceNo(hotelRoomsDetail.getSequenceNo());
							lrr.setSmokingPreference(hotelRoomsDetail.getSmokingPreference());



							List<com.tayyarah.hotel.model.BedType> bedTypes = new  ArrayList<com.tayyarah.hotel.model.BedType>();
							for (BedType bedType : hotelRoomsDetail.getBedTypes()) {
								com.tayyarah.hotel.model.BedType bedTypeCommon = new com.tayyarah.hotel.model.BedType();
								bedTypeCommon.setBedTypeCode(bedType.getBedTypeCode());
								bedTypeCommon.setBedTypeDescription(bedType.getBedTypeDescription());
								bedTypes.add(bedTypeCommon);
							}				
							lrr.setBedTypes(bedTypes);
							//lrr.setBedTypes(hotelRoomsDetail.getBedTypes());
							lrr.setHotelSupplements(hotelRoomsDetail.getHotelSupplements());
							lrr.setLastCancellationDate(hotelRoomsDetail.getLastCancellationDate());
							RateType lrates = new RateType();
							List<Rate> lratelist = new ArrayList<Rate>();
							Rate lrate = new Rate();
							lrate.setRoomIndex(1);	
							lrate.setName(hotelRoomsDetail.getRoomTypeName());
							lrate.setAdults(1);
							lrate.setChildrenages("1|2");
							lrate.setChildren(2);
							//lrate.setExpireDate(rtype.getExpireDate());
							//lrate.setEffectiveDate(rtype.getEffectiveDate());

							//Total base

							Price p = hotelRoomsDetail.getPrice();
							BigDecimal totaltax =  AmountRoundingModeUtil.roundingModeForHotel(p.getTax());
							BigDecimal taxperday = new BigDecimal(0);
							//BigDecimal totaldiscount = p.getDiscount();
							BigDecimal roomPricePerDay = ((p.getRoomPrice() == null)?new BigDecimal("0"):p.getRoomPrice())
									.add((p.getExtraGuestCharge() == null)?new BigDecimal("0"):p.getExtraGuestCharge())
									.add((p.getChildCharge() == null)?new BigDecimal("0"):p.getChildCharge())
									.add((p.getOtherCharges() == null)?new BigDecimal("0"):p.getOtherCharges());
							BigDecimal discountperday = new BigDecimal(0);

							//Single day base
							TotalType ltotperday = new TotalType();
							////logger.info("object transformation---: tbo to common. -----tbasic-rs.getPrice() "+p.getPublishedPrice());		
							try{
								roomPricePerDay = AmountRoundingModeUtil.roundingModeForHotel(roomPricePerDay);
								BigDecimal offeredPrice = AmountRoundingModeUtil.roundingModeForHotel(p.getOfferedPrice());
								ltotperday.setAmountBeforeTax(roomPricePerDay.divide(new BigDecimal(noofdays)));
								ltotperday.setAmountAfterTax(offeredPrice.divide(new BigDecimal(noofdays)));
								taxperday = totaltax.divide(new BigDecimal(noofdays));
								//discountperday = totaldiscount.divide(new BigDecimal(noofdays));
								//logger.info("object transformation---: tbo to common. -----single day price "+ltotperday.getAmountBeforeTax());	
							}
							catch(ArithmeticException ex)
							{
								logger.info("object transformation---: tbo to common. -----tbasic-rs.getPrice() divide ArithmeticException "+ex.getMessage());			
								roomPricePerDay = AmountRoundingModeUtil.roundingModeForHotel(roomPricePerDay);
								BigDecimal offeredPrice = AmountRoundingModeUtil.roundingModeForHotel(p.getOfferedPrice());
								ltotperday.setAmountBeforeTax(roomPricePerDay.divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP));
								ltotperday.setAmountAfterTax(offeredPrice.divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP));
								taxperday = totaltax.divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP);
								//discountperday = totaldiscount.divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP);
								//logger.info("object transformation---: tbo to common. -----rateplan single day price "+ltotperday.getAmountBeforeTax());			

							}
							//logger.info("object transformation---: tax parsing------ ");
							TaxesType ltaxperday = new TaxesType();			
							List<TaxType> ltaxlistperday  = new ArrayList<TaxType>();
							TaxType lrttperday = new TaxType();
							lrttperday.setAmount(taxperday);
							lrttperday.setCode("");
							lrttperday.setType(AmountDeterminationType.EXCLUSIVE);
							ltaxlistperday.add(lrttperday);
							ltaxperday.setAmount(taxperday);
							ltaxperday.setTaxes(ltaxlistperday);				
							ltotperday.setTaxes(ltaxperday);
							List<Discount> ldiscountlist  = new ArrayList<Discount>();
							Discount d = new Discount();
							d.setAmountAfterTax(discountperday);
							d.setAmountBeforeTax(discountperday);
							d.setAppliesTo("Base");					
							ldiscountlist.add(d);

							ltotperday.setDiscounts(ldiscountlist);
							ltotperday.setTotalRoomPrice(p.getRoomPrice());
							ltotperday.setTotalExtraGuestCharge(p.getExtraGuestCharge());
							ltotperday.setTotalChildCharge(p.getChildCharge());
							ltotperday.setTotalOtherCharges(p.getOtherCharges());
							ltotperday.setTotalAmountPayable(p.getOfferedPrice());
							ltotperday.setApiPublishedPriceTotal(p.getPublishedPrice());
							ltotperday.setTotalOfferedPrice(p.getOfferedPrice());
							ltotperday.setTotalAgentCommission(p.getAgentCommission());
							ltotperday.setTotalAgentMarkUp(p.getAgentMarkUp());
							ltotperday.setTotalServiceTax(p.getServiceTax());
							ltotperday.setTotalTDS(p.getTDS());
							ltotperday.setTotalTax(p.getTax());
							ltotperday.setTotalDiscount(p.getDiscount());

							TotalType ltotsinglebase = new TotalType();
							TotalType ltotsinglebasewithoutmarkup = new TotalType();
							TotalType ltotsinglebooking = new TotalType();				
							BeanUtils.copyProperties(ltotperday, ltotsinglebase);
							BeanUtils.copyProperties(ltotperday, ltotsinglebasewithoutmarkup);
							BeanUtils.copyProperties(ltotperday, ltotsinglebooking);				
							lrate.setApiPrice(ltotperday);
							lrate.setBase(ltotsinglebase);			
							lrate.setBaseWithoutMarkUp(ltotsinglebasewithoutmarkup);
							lrate.setBookingPrice(ltotsinglebooking);
							//logger.info("\\\\\\\\*************************roomdetail -lrate.getBase()-"+lrate.getBase());	
							//logger.info("\\\\\\\\*************************roomdetail -lrate.getBaseWithoutMarkUp()-"+lrate.getBaseWithoutMarkUp());	
							//logger.info("\\\\\\\\*************************roomdetail -lrate.getApiPrice()-"+lrate.getApiPrice());	
							lratelist.add(lrate);	

							lrates.setRates(lratelist);
							GuestCounts gustcounts = new GuestCounts();
							List<GuestCount> gustcountslist = new ArrayList<GuestCount>();				
							gustcounts.setGuestCounts(gustcountslist);
							lrr.setGuestCounts(gustcounts);

							lrr.setRates(lrates);
							lrratelist.add(lrr);
							break;
						}
					}
				}
				trateplans.setRatePlen(lplantlist);	
				//logger.info("object transformation---: final RatePlen size"+lplantlist.size());
				rs.setRatePlans(trateplans);

				troomrates.setRoomRates(lrratelist);
				//logger.info("object transformation---: final RoomRates size"+lrratelist.size());
				rs.setRoomRates(troomrates);
				Map<String,List<HotelMarkup>> markupsmap = hsc.getHotelMarkupCommissionDetails().getMarkups();
				CurrencyManager currencyManager = new CurrencyManager();				
				rs = currencyManager.fillCurrencyDataOnRoomDetailWithMarkups(hsc, markupsmap, rs,company,parentCompany);
			}

		}


		return rs;
	}
	public RoomStay updateRoomStayOnBook( HotelSearchCommand hsc, OTAHotelAvailRS.RoomStays.RoomStay rs, HotelBookResponse hotelBookResponse,Company company,Company parentCompany) throws ParseException,Exception
	{
		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();
		if(tbasic != null && tbasic.getApiProviderBasicMap()!= null && tbasic.getApiProviderBasicMap().containsKey(HotelApiCredentials.API_TBO_INTERNATIONAL))
			tbasic = tbasic.getApiProviderBasicMap().get(HotelApiCredentials.API_TBO_INTERNATIONAL);

		//tbasic.setHotelCode(rs.getId());		
		int noofdays = CommonUtil.getNoofStayDays(hsc);
		if(hotelBookResponse != null && hotelBookResponse.getBookResult() != null && hotelBookResponse.getBookResult().getHotelRoomsDetails() != null)
		{			
			if((hotelBookResponse.getBookResult().getIsPriceChanged() || hotelBookResponse.getBookResult().getIsCancellationPolicyChanged()) && hotelBookResponse.getBookResult().getHotelRoomsDetails().size()>0)
			{

				RatePlans trateplans = new RatePlans();
				List<RatePlanType> lplantlist = new ArrayList<RatePlanType>();

				RoomRates troomrates = new RoomRates();
				List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();

				for (HotelRoomsDetail hotelRoomsDetail : hotelBookResponse.getBookResult().getHotelRoomsDetails()) {	

					//Constructing Rateplans...

					for (RatePlanType rateplan : rs.getRatePlans().getRatePlan()) {
						RatePlanType lrp = rateplan;	
						if(hotelRoomsDetail.getRatePlanCode().equalsIgnoreCase(lrp.getRatePlanCode()))
						{
							CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();			
							List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
							for (CancellationPolicy cancellationPolicy : hotelRoomsDetail.getCancellationPolicies()) {

								CancelPenaltyType lcpt = new CancelPenaltyType();
								lcpt.setStart(getCommonPojoDate(cancellationPolicy.getFromDate()));
								lcpt.setEnd(getCommonPojoDate(cancellationPolicy.getToDate()));
								Deadline ldeadline = new Deadline();	
								//ldeadline.setOffsetTimeUnit(TimeUnitType.fromValue(cpt.getDeadline().getOffsetTimeUnit()));
								ldeadline.setOffsetTimeUnit(TimeUnitType.DAY);
								ldeadline.setOffsetDropTime("1");			
								ldeadline.setOffsetUnitMultiplier(1);


								//"2016-05-04T00:00:00" to //22 Dec 2015 00:00:00		
								ldeadline.setFromDate(getCommonPojoDate(cancellationPolicy.getFromDate()));
								ldeadline.setToDate(getCommonPojoDate(cancellationPolicy.getToDate()));
								lcpt.setDeadline(ldeadline);
								AmountPercentType amountPercentType = new AmountPercentType();
								amountPercentType.setBasisType(getCancellationBasisType(cancellationPolicy.getChargeType()));
								amountPercentType.setAmount(cancellationPolicy.getCharge());
								amountPercentType.setCurrencyCode(cancellationPolicy.getCurrency());
								//lcpt.setAmountPercent(amountPercentType);


								AmountPercentType apiAmountPercent = new AmountPercentType();
								AmountPercentType baseAmountPercent = new AmountPercentType();  
								AmountPercentType baseWithoutMarkupAmountPercent = new AmountPercentType();


								BeanUtils.copyProperties(amountPercentType, apiAmountPercent);
								BeanUtils.copyProperties(amountPercentType, baseAmountPercent);
								BeanUtils.copyProperties(amountPercentType, baseWithoutMarkupAmountPercent);	

								lcpt.setAmountPercent(amountPercentType);
								lcpt.setApiAmountPercent(apiAmountPercent);
								lcpt.setBaseAmountPercent(baseAmountPercent);
								lcpt.setBaseWithoutMarkupAmountPercent(baseWithoutMarkupAmountPercent);
								List<PenaltyDescription> lpenlist = new ArrayList<PenaltyDescription>();
								PenaltyDescription lpdes = new PenaltyDescription();
								lpdes.setDrescription(getCancellationPenalty(hotelRoomsDetail.getCancellationPolicy(), cancellationPolicy));
								lpenlist.add(lpdes);
								lcpt.setPenaltyDescriptions(lpenlist);
								cancellist.add(lcpt);

							}
							cancelpenalities.setCancelPenalties(cancellist);
							lrp.setCancelPenalties(cancelpenalities);	
							lplantlist.add(lrp);
						}					
					}
					//RoomRate Construction..
					for (RoomRate roomRate : rs.getRoomRates().getRoomRates()) {
						//if(hotelRoomsDetail.getRatePlanCode().equalsIgnoreCase(roomRate.getRatePlanCode()))
						if(hotelRoomsDetail.getRoomTypeCode().equalsIgnoreCase(roomRate.getRoomTypeCode()) && (hotelRoomsDetail.getRoomIndex().compareTo(roomRate.getSupplierRoomIndex())==0))
						{
							RoomStayType.RoomRates.RoomRate lrr = roomRate;

							lrr.setSupplierRoomIndex(hotelRoomsDetail.getRoomIndex());
							lrr.setRoomTypeName(hotelRoomsDetail.getRoomTypeName());
							lrr.setRatePlanName(hotelRoomsDetail.getRatePlanName());
							lrr.setInfoSource(hotelRoomsDetail.getInfoSource());
							lrr.setSequenceNo(hotelRoomsDetail.getSequenceNo());
							lrr.setSmokingPreference(hotelRoomsDetail.getSmokingPreference());

							List<com.tayyarah.hotel.model.BedType> bedTypes = new  ArrayList<com.tayyarah.hotel.model.BedType>();
							for (BedType bedType : hotelRoomsDetail.getBedTypes()) {
								com.tayyarah.hotel.model.BedType bedTypeCommon = new com.tayyarah.hotel.model.BedType();
								bedTypeCommon.setBedTypeCode(bedType.getBedTypeCode());
								bedTypeCommon.setBedTypeDescription(bedType.getBedTypeDescription());
								bedTypes.add(bedTypeCommon);
							}				
							lrr.setBedTypes(bedTypes);

							//lrr.setBedTypes(hotelRoomsDetail.getBedTypes());
							lrr.setHotelSupplements(hotelRoomsDetail.getHotelSupplements());
							lrr.setLastCancellationDate(hotelRoomsDetail.getLastCancellationDate());

							RateType lrates = new RateType();
							List<Rate> lratelist = new ArrayList<Rate>();


							Rate lrate = new Rate();
							lrate.setRoomIndex(1);	
							lrate.setName(hotelRoomsDetail.getRoomTypeName());
							lrate.setAdults(1);
							lrate.setChildrenages("1|2");
							lrate.setChildren(2);		

							//lrate.setExpireDate(rtype.getExpireDate());
							//lrate.setEffectiveDate(rtype.getEffectiveDate());

							//Total base


							Price p = hotelRoomsDetail.getPrice();
							BigDecimal totaltax = p.getTax();
							BigDecimal taxperday = new BigDecimal(0);
							BigDecimal roomPricePerDay = ((p.getRoomPrice() == null)?new BigDecimal("0"):p.getRoomPrice())
									.add((p.getExtraGuestCharge() == null)?new BigDecimal("0"):p.getExtraGuestCharge())
									.add((p.getChildCharge() == null)?new BigDecimal("0"):p.getChildCharge())
									.add((p.getOtherCharges() == null)?new BigDecimal("0"):p.getOtherCharges());
							//BigDecimal totaldiscount = p.getDiscount();
							BigDecimal discountperday = new BigDecimal(0);

							//Single day base
							TotalType ltotperday = new TotalType();
							//logger.info("object transformation---: tbo to common. -----tbasic-rs.getPrice() "+p.getOfferedPriceRoundedOff());		
							try{
								ltotperday.setAmountBeforeTax(roomPricePerDay.divide(new BigDecimal(noofdays)));
								ltotperday.setAmountAfterTax(p.getOfferedPrice().divide(new BigDecimal(noofdays)));
								taxperday = totaltax.divide(new BigDecimal(noofdays));
								//discountperday = totaldiscount.divide(new BigDecimal(noofdays));
								//logger.info("object transformation---: tbo to common. -----single day price "+ltotperday.getAmountBeforeTax());	
							}
							catch(ArithmeticException ex)
							{
								logger.info("object transformation---: tbo to common. -----tbasic-rs.getPrice() divide ArithmeticException "+ex.getMessage());			
								ltotperday.setAmountBeforeTax(roomPricePerDay.divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP));
								ltotperday.setAmountAfterTax(p.getOfferedPrice().divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP));
								taxperday = totaltax.divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP);
								//discountperday = totaldiscount.divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP);
								logger.info("object transformation---: tbo to common. -----rateplan single day price "+ltotperday.getAmountBeforeTax());			

							}
							//logger.info("object transformation---: tax parsing------ ");
							TaxesType ltaxperday = new TaxesType();			
							List<TaxType> ltaxlistperday  = new ArrayList<TaxType>();
							TaxType lrttperday = new TaxType();
							lrttperday.setAmount(taxperday);
							lrttperday.setCode("");
							lrttperday.setType(AmountDeterminationType.EXCLUSIVE);
							ltaxlistperday.add(lrttperday);
							ltaxperday.setAmount(taxperday);
							ltaxperday.setTaxes(ltaxlistperday);				
							ltotperday.setTaxes(ltaxperday);

							List<Discount> ldiscountlist  = new ArrayList<Discount>();
							Discount d = new Discount();
							d.setAmountAfterTax(discountperday);
							d.setAmountBeforeTax(discountperday);
							d.setAppliesTo("Base");					
							ldiscountlist.add(d);	


							ltotperday.setDiscounts(ldiscountlist);

							ltotperday.setTotalRoomPrice(p.getRoomPrice());
							ltotperday.setTotalExtraGuestCharge(p.getExtraGuestCharge());
							ltotperday.setTotalChildCharge(p.getChildCharge());
							ltotperday.setTotalOtherCharges(p.getOtherCharges());
							ltotperday.setTotalAmountPayable(p.getOfferedPrice());
							ltotperday.setApiPublishedPriceTotal(p.getPublishedPrice());
							ltotperday.setTotalOfferedPrice(p.getOfferedPrice());
							ltotperday.setTotalAgentCommission(p.getAgentCommission());
							ltotperday.setTotalAgentMarkUp(p.getAgentMarkUp());
							ltotperday.setTotalServiceTax(p.getServiceTax());
							ltotperday.setTotalTDS(p.getTDS());
							ltotperday.setTotalTax(p.getTax());
							ltotperday.setTotalDiscount(p.getDiscount());

							TotalType ltotsinglebase = new TotalType();							
							TotalType ltotsinglebasewithoutmarkup = new TotalType();
							TotalType ltotsinglebooking = new TotalType();				
							BeanUtils.copyProperties(ltotperday, ltotsinglebase);
							BeanUtils.copyProperties(ltotperday, ltotsinglebasewithoutmarkup);
							BeanUtils.copyProperties(ltotperday, ltotsinglebooking);				
							lrate.setApiPrice(ltotperday);
							lrate.setBase(ltotsinglebase);			
							lrate.setBaseWithoutMarkUp(ltotsinglebasewithoutmarkup);
							lrate.setBookingPrice(ltotsinglebooking);
							//logger.info("\\\\\\\\*************************roomdetail -lrate.getBase()-"+lrate.getBase());	
							//logger.info("\\\\\\\\*************************roomdetail -lrate.getBaseWithoutMarkUp()-"+lrate.getBaseWithoutMarkUp());	
							//logger.info("\\\\\\\\*************************roomdetail -lrate.getApiPrice()-"+lrate.getApiPrice());	
							lratelist.add(lrate);
							lrates.setRates(lratelist);
							GuestCounts gustcounts = new GuestCounts();
							List<GuestCount> gustcountslist = new ArrayList<GuestCount>();				
							gustcounts.setGuestCounts(gustcountslist);
							lrr.setGuestCounts(gustcounts);
							lrr.setRates(lrates);
							lrratelist.add(lrr);
						}
					}
				}
				trateplans.setRatePlen(lplantlist);	
				//logger.info("object transformation---: RatePlen "+lplantlist.size());
				rs.setRatePlans(trateplans);
				troomrates.setRoomRates(lrratelist);
				//logger.info("object transformation---: RoomRates "+lrratelist.size());
				rs.setRoomRates(troomrates);
				Map<String,List<HotelMarkup>> markupsmap = hsc.getHotelMarkupCommissionDetails().getMarkups();
				CurrencyManager currencyManager = new CurrencyManager();				
				rs = currencyManager.fillCurrencyDataOnRoomDetailWithMarkups(hsc, markupsmap, rs,company,parentCompany);

			}

		}


		return rs;
	}

	public APIHotelBook convertTbotoNativePreBookResponse(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory, HotelBlockResponse hotelBlockResponse,Company company,Company parentCompany) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{

		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebooking ..." );
		//logger.info("pre parsing--1--apiHotelBook:"+apiHotelBook);
		//logger.info("pre parsing--1--totaHotelResRS:"+totaHotelResRS);
		//logger.info("pre parsing--1--status:"+status);
		//logger.info("pre parsing--1--status msg:"+status.getMessage());		
		//logger.info("pre parsing--1--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
		//logger.info("pre parsing--1--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());


		if(apiHotelBook.getPreBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getPreBookRes();
			status = totaHotelResRS.getStatus();
		}
		//logger.info("pre parsing--2--apiHotelBook:"+apiHotelBook);
		//logger.info("pre parsing--2--totaHotelResRS:"+totaHotelResRS);
		//logger.info("pre parsing--2--status:"+status);
		//logger.info("pre parsing--2--status msg:"+status.getMessage());		
		//logger.info("pre parsing--2--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
		//logger.info("pre parsing--2--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());
		//bookingDetails
		//
		BigDecimal totalAmountPayableBeforePriceChange = new BigDecimal("0.0");
		for (RoomRate roomratebefore : roomStay.getRoomRates().getRoomRates()) {			
			if(roomratebefore.getRates().getRates().size()>0)
			{
				for (Rate rate : roomratebefore.getRates().getRates()) {				
					totalAmountPayableBeforePriceChange = totalAmountPayableBeforePriceChange.add(rate.getBookingPrice().getTotalAmountPayable());
				}
			}
		}





		if(hotelBlockResponse!=null && hotelBlockResponse.getBlockRoomResult() != null && hotelBlockResponse.getBlockRoomResult().getResponseStatus() == 1)
		{
			roomStay = updateRoomStayOnBlock(apiHotelBook.getSearch(), roomStay, hotelBlockResponse,company,parentCompany);	
			apiHotelBook.setRoomsummary(roomStay);
			apiHotelBook.initRate(roomStay, CommonUtil.getNoofStayDays(apiHotelBook.getSearch()),apiHotelBook.getSearch().getNoofrooms());
			status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, "Rooms has been blocked.." );

			totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
			totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
			HotelReservationsType hotelReservationsType = new HotelReservationsType();
			List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
			HotelReservation thotelReservation = new HotelReservation();


			List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
			UniqueIDType uniqueIDType = new UniqueIDType();
			uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
			String blockid = HotelIdFactoryImpl.getInstance().createShortId("bl");
			String bookingcode = HotelIdFactoryImpl.getInstance().createShortId("b");

			uniqueIDType.setID(blockid);
			totaHotelResRS.setBookingCode(bookingcode);
			//uniqueIDType.setCompanyName(uniqueid.getCompanyName());
			uniqueIDTypeList.add(uniqueIDType);
			thotelReservation.setUniqueIDs(uniqueIDTypeList);			

			ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
			HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
			List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();


			RoomStayType.RoomRates.RoomRate roomrate = roomStay.getRoomRates().getRoomRates().get(0);
			if(roomrate.getRates().getRates().size()>0)
			{
				for (Rate rate : roomrate.getRates().getRates()) {
					HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
					thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
					thotelresid.setResIDValue(bookingcode);//XHUB891565bookingId
					thotelresid.setResIDSourceContext("");
					thotelresid.setForGuest(true);
					thotelresidlist.add(thotelresid);

				}
			}
			else
			{
				//Add atleast one unique book id
				HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
				thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
				thotelresid.setResIDValue(bookingcode);//XHUB891565bookingId
				thotelresid.setResIDSourceContext("");
				thotelresid.setForGuest(true);
				thotelresidlist.add(thotelresid);
			}



			thotelresIds.setHotelReservationIDs(thotelresidlist);
			tresGlobalinfo.setHotelReservationIDs(thotelresIds);
			thotelReservation.setResGlobalInfo(tresGlobalinfo);
			hotelReservationslist.add(thotelReservation);	
			hotelReservationsType.setHotelReservations(hotelReservationslist);
			totaHotelResRS.setHotelReservations(hotelReservationsType);	

			totaHotelResRS.setPriceChanged(hotelBlockResponse.getBlockRoomResult().getIsPriceChanged());


			BigDecimal newtotalrate = apiHotelBook.getApiRate().getPayableAmt();
			BigDecimal newbaserate = apiHotelBook.getBaseRate().getPayableAmt();
			BigDecimal newbasewithoutmarkuprate = apiHotelBook.getBaseRateWithoutMarkUp().getPayableAmt();
			BigDecimal newbookignrate = apiHotelBook.getBookingRate().getPayableAmt();
			logger.info("tbo blocking----"+newtotalrate);
			logger.info("tbo blocking newbaserate ----"+newbaserate);
			totaHotelResRS.setApiFinalPrice(newtotalrate);
			totaHotelResRS.setBaseFinalPrice(newbaserate);
			totaHotelResRS.setBaseFinalPriceWithoutMarkup(newbasewithoutmarkuprate);
			totaHotelResRS.setBookingFinalPrice(newbookignrate);
			totaHotelResRS.setBookingPayablePrice(apiHotelBook.getBookingRate().getTotalPayableAmt());


			/*BigDecimal newtotalrate = apiHotelBook.getApiRate().getPayableAmt();
			BigDecimal newbaserate = apiHotelBook.getBaseRate().getPayableAmt();
			BigDecimal newbasewithoutmarkuprate = apiHotelBook.getBaseRateWithoutMarkUp().getPayableAmt();
			BigDecimal newbookignrate = apiHotelBook.getBookingRate().getPayableAmt();
			logger.info("tbo blocking----"+newtotalrate);
			totaHotelResRS.setApiFinalPrice(newtotalrate);
			totaHotelResRS.setBaseFinalPrice(newbaserate);
			totaHotelResRS.setBaseFinalPriceWithoutMarkup(newbasewithoutmarkuprate);
			totaHotelResRS.setBookingFinalPrice(newbookignrate);*/



		}
		else if(hotelBlockResponse.getBlockRoomResult().getError().getErrorCode() != 0)
		{
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebook Error -"+hotelBlockResponse.getBlockRoomResult().getError().getErrorMessage() ); 
		}
		else
		{
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Book Error - API" ); 
		}

		if(totaHotelResRS.isPriceChanged()){
			totaHotelResRS.setOldBookingFinalPrice(totalAmountPayableBeforePriceChange);
		}

		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setPreBookRes(totaHotelResRS);
		//logger.info("pre parsing--3--apiHotelBook:"+apiHotelBook);
		//logger.info("pre parsing--3--totaHotelResRS:"+totaHotelResRS);
		//logger.info("pre parsing--3--status:"+status);
		//logger.info("pre parsing--3--status msg:"+status.getMessage());		
		//logger.info("pre parsing--3--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
		//logger.info("pre parsing--3--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());


		return apiHotelBook;


	}
	public APIHotelBook convertTbotoNativeBookResponse(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory, HotelBookResponse hotelBookResponse,Company company,Company parentCompany) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Booking ..." );

		if(apiHotelBook.getBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getBookRes();
			status = totaHotelResRS.getStatus();
		}
		//logger.info(" parsing--2--apiHotelBook:"+apiHotelBook);
		//logger.info(" parsing--2--totaHotelResRS:"+totaHotelResRS);
		//logger.info(" parsing--2--status:"+status);
		//logger.info(" parsing--2--status msg:"+status.getMessage());		
		//logger.info(" parsing--2--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
		//logger.info(" parsing--2--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());


		if(hotelBookResponse!=null && hotelBookResponse.getBookResult() != null && hotelBookResponse.getBookResult().getResponseStatus() == 1)
		{
			roomStay = updateRoomStayOnBook(apiHotelBook.getSearch(), roomStay, hotelBookResponse,company,parentCompany);	
			apiHotelBook.setRoomsummary(roomStay);
			apiHotelBook.initRate(roomStay, CommonUtil.getNoofStayDays(apiHotelBook.getSearch()),apiHotelBook.getSearch().getNoofrooms());
			status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, hotelBookResponse.getBookResult().getHotelBookingStatus() );

			totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
			totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
			HotelReservationsType hotelReservationsType = new HotelReservationsType();
			List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
			HotelReservation thotelReservation = new HotelReservation();
			totaHotelResRS.setPriceChanged(hotelBookResponse.getBookResult().getIsPriceChanged()); 

			List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
			UniqueIDType uniqueIDType = new UniqueIDType();
			uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
			uniqueIDType.setApiBookingId(hotelBookResponse.getBookResult().getBookingId());
			uniqueIDType.setApiBookingCode(hotelBookResponse.getBookResult().getBookingRefNo());
			uniqueIDType.setApiConfirmationNo(hotelBookResponse.getBookResult().getConfirmationNo());
			uniqueIDType.setID(hotelBookResponse.getBookResult().getBookingId());



			/*"ConfirmationNo": "LL8F392693 - 010/502365",
			"BookingRefNo": "502365",
			"BookingId": 1302607, 
			 */
			totaHotelResRS.setBookingCode(hotelBookResponse.getBookResult().getConfirmationNo());
			//uniqueIDType.setCompanyName(uniqueid.getCompanyName());
			uniqueIDTypeList.add(uniqueIDType);
			thotelReservation.setUniqueIDs(uniqueIDTypeList);			

			ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
			HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
			List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();

			/*com.lintas.hotel.model.RoomStayType.RoomRates.RoomRate roomrate = roomStay.getRoomRates().getRoomRates().get(0);
			for (Rate rate : roomrate.getRates().getRates()) {
				com.lintas.hotel.model.HotelReservationIDsType.HotelReservationID thotelresid = new com.lintas.hotel.model.HotelReservationIDsType.HotelReservationID();
				thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
				thotelresid.setResIDValue(hotelBookResponse.getBookResult().getConfirmationNo());//XHUB891565bookingId
				thotelresid.setResIDSourceContext(hotelBookResponse.getBookResult().getBookingRefNo());
				thotelresid.setForGuest(true);
				thotelresidlist.add(thotelresid);
			}
			 */

			//Add atleast one unique book id
			HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
			thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
			thotelresid.setResIDValue(hotelBookResponse.getBookResult().getConfirmationNo());//XHUB891565bookingId
			thotelresid.setResIDSourceContext(hotelBookResponse.getBookResult().getBookingRefNo());
			thotelresid.setForGuest(true);
			thotelresidlist.add(thotelresid);



			thotelresIds.setHotelReservationIDs(thotelresidlist);
			tresGlobalinfo.setHotelReservationIDs(thotelresIds);
			thotelReservation.setResGlobalInfo(tresGlobalinfo);
			hotelReservationslist.add(thotelReservation);	
			hotelReservationsType.setHotelReservations(hotelReservationslist);
			totaHotelResRS.setHotelReservations(hotelReservationsType);	


			BigDecimal newtotalrate = apiHotelBook.getApiRate().getPayableAmt();
			BigDecimal newbaserate = apiHotelBook.getBaseRate().getPayableAmt();
			BigDecimal newbasewithoutmarkuprate = apiHotelBook.getBaseRateWithoutMarkUp().getPayableAmt();
			BigDecimal newbookignrate = apiHotelBook.getBookingRate().getPayableAmt();
			logger.info("tbo blocking----"+newtotalrate);
			logger.info("tbo blocking newbaserate ----"+newbaserate);
			totaHotelResRS.setApiFinalPrice(newtotalrate);
			totaHotelResRS.setBaseFinalPrice(newbaserate);
			totaHotelResRS.setBaseFinalPriceWithoutMarkup(newbasewithoutmarkuprate);
			totaHotelResRS.setBookingFinalPrice(newbookignrate);

			/*BigDecimal newtotalrate = apiHotelBook.getApiRate().getPayableAmt();
			logger.info("tbo blocking----"+newtotalrate);
			totaHotelResRS.setApiFinalPrice(newtotalrate);
			totaHotelResRS.setBaseFinalPrice(newtotalrate);
			totaHotelResRS.setBaseFinalPriceWithoutMarkup(newtotalrate);
			totaHotelResRS.setBookingFinalPrice(newtotalrate);*/

		}		
		else if(hotelBookResponse.getBookResult().getError().getErrorCode() != 0)
		{
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebook Error -"+hotelBookResponse.getBookResult().getError().getErrorMessage() ); 
		}
		else
		{
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Book Error - API" ); 
		}
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setBookRes(totaHotelResRS);
		//logger.info(" parsing--3--apiHotelBook:"+apiHotelBook);
		//logger.info(" parsing--3--totaHotelResRS:"+totaHotelResRS);
		//logger.info(" parsing--3--status:"+status);
		//logger.info(" parsing--3--status msg:"+status.getMessage());		
		//logger.info(" parsing--3--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
		//logger.info(" parsing--3--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());

		return apiHotelBook;

	}


	public APIHotelBook converttbotoNativeCancellationPolicy(APIHotelBook apiHotelBook, CancellationPolicyResponse cancellationPolicyResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();			
		if(apiHotelBook.getBookRes() != null && cancellationPolicyResponse != null && cancellationPolicyResponse.getCancellationInformations() != null && cancellationPolicyResponse.getCancellationInformations().getCancellationInformations() != null)
		{
			totaHotelResRS = apiHotelBook.getBookRes();	
			OTAHotelResRS.CancellationInformations tcancellationInformations = new OTAHotelResRS.CancellationInformations();
			List<OTAHotelResRS.CancellationInformations.CancellationInformation> tCancellationInformationlist = new ArrayList<OTAHotelResRS.CancellationInformations.CancellationInformation>();
			for (com.tayyarah.api.hotel.rezlive.model.CancellationPolicyResponse.CancellationInformations.CancellationInformation CancellationInformation : cancellationPolicyResponse.getCancellationInformations().getCancellationInformations()) {
				OTAHotelResRS.CancellationInformations.CancellationInformation tcancellationInformation = new OTAHotelResRS.CancellationInformations.CancellationInformation();
				tcancellationInformation.setChargeAmount(CancellationInformation.getChargeAmount());
				tcancellationInformation.setChargeType(CancellationInformation.getChargeType());
				tcancellationInformation.setCurrency(CancellationInformation.getCurrency());
				tcancellationInformation.setStartDate(CancellationInformation.getStartDate());
				tcancellationInformation.setEndDate(CancellationInformation.getEndDate());	
				tCancellationInformationlist.add(tcancellationInformation);
			}
			tcancellationInformations.setCancellationInformations(tCancellationInformationlist);
			tcancellationInformations.setInfo(cancellationPolicyResponse.getCancellationInformations().getInfo());
			totaHotelResRS.setCancellationInformations(tcancellationInformations);
			apiHotelBook.setBookRes(totaHotelResRS);
		}

		return apiHotelBook;

	}
	public OTAHotelAvailRS.RoomStays.RoomStay converttbotoNativeCancellationPolicyRoomDetail(OTAHotelAvailRS.RoomStays.RoomStay roomStay, CancellationPolicyResponse cancellationPolicyResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		RatePlans trateplans = roomStay.getRatePlans();
		List<RatePlanType> lplantlist = trateplans.getRatePlan();	
		RatePlanType lrt = lplantlist.get(0);
		if(cancellationPolicyResponse != null && cancellationPolicyResponse.getCancellationInformations() != null && cancellationPolicyResponse.getCancellationInformations().getCancellationInformations() != null)
		{
			CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();
			List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();

			for (com.tayyarah.api.hotel.rezlive.model.CancellationPolicyResponse.CancellationInformations.CancellationInformation cancellationInformation : cancellationPolicyResponse.getCancellationInformations().getCancellationInformations()) {

				CancelPenaltyType lcpt = new CancelPenaltyType();			
				lcpt.setStart(cancellationInformation.getStartDate());
				lcpt.setEnd(cancellationInformation.getEndDate());
				AmountPercentType amountPercentType = new AmountPercentType();
				amountPercentType.setAmount(cancellationInformation.getChargeAmount());
				amountPercentType.setBasisType(cancellationInformation.getChargeType());
				amountPercentType.setCurrencyCode(cancellationInformation.getCurrency());
				AmountPercentType apiAmountPercent = new AmountPercentType();
				AmountPercentType baseAmountPercent = new AmountPercentType();  
				AmountPercentType baseWithoutMarkupAmountPercent = new AmountPercentType();
				BeanUtils.copyProperties(amountPercentType, apiAmountPercent);
				BeanUtils.copyProperties(amountPercentType, baseAmountPercent);
				BeanUtils.copyProperties(amountPercentType, baseWithoutMarkupAmountPercent);				lcpt.setAmountPercent(amountPercentType);
				lcpt.setApiAmountPercent(apiAmountPercent);
				lcpt.setBaseAmountPercent(baseAmountPercent);
				lcpt.setBaseWithoutMarkupAmountPercent(baseWithoutMarkupAmountPercent);
				Deadline ldeadline = new Deadline();				
				ldeadline.setOffsetTimeUnit(TimeUnitType.FULL_DURATION);
				int unitmultiplier = 1;
				ldeadline.setOffsetUnitMultiplier(unitmultiplier);
				lcpt.setDeadline(ldeadline);
				List<PenaltyDescription> lpenlist = new ArrayList<PenaltyDescription>();
				PenaltyDescription lpdes = new PenaltyDescription();
				lpdes.setDrescription(cancellationPolicyResponse.getCancellationInformations().getInfo());
				lpenlist.add(lpdes);
				lcpt.setPenaltyDescriptions(lpenlist);
				cancellist.add(lcpt);				
				cancelpenalities.setCancelPenalties(cancellist);
				lrt.setCancelPenalties(cancelpenalities);
				lplantlist = new ArrayList<RatePlanType>();					
				lplantlist.add(lrt);	
				trateplans.setRatePlen(lplantlist);
				roomStay.setRatePlans(trateplans);
			}
		}
		return roomStay;
	}
	public RatePlanType converttbotoNativeCancellationPolicyRoomDetailRatePlan(RatePlanType lrt, CancellationPolicyResponse cancellationPolicyResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		if(cancellationPolicyResponse != null && cancellationPolicyResponse.getCancellationInformations() != null && cancellationPolicyResponse.getCancellationInformations().getCancellationInformations() != null)
		{
			CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();
			List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();

			for (com.tayyarah.api.hotel.rezlive.model.CancellationPolicyResponse.CancellationInformations.CancellationInformation cancellationInformation : cancellationPolicyResponse.getCancellationInformations().getCancellationInformations()) {
				/*com.lintas.hotel.model.OTAHotelResRS.CancellationInformations.CancellationInformation tcancellationInformation = new com.lintas.hotel.model.OTAHotelResRS.CancellationInformations.CancellationInformation();
				tcancellationInformation.setChargeAmount(CancellationInformation.getChargeAmount());
				tcancellationInformation.setChargeType(CancellationInformation.getChargeType());
				tcancellationInformation.setCurrency(CancellationInformation.getCurrency());
				tcancellationInformation.setStartDate(CancellationInformation.getStartDate());
				tcancellationInformation.setEndDate(CancellationInformation.getEndDate());	
				tCancellationInformationlist.add(tcancellationInformation);*/
				CancelPenaltyType lcpt = new CancelPenaltyType();			
				lcpt.setStart(cancellationInformation.getStartDate());
				lcpt.setEnd(cancellationInformation.getEndDate());
				AmountPercentType amountPercentType = new AmountPercentType();
				amountPercentType.setAmount(cancellationInformation.getChargeAmount());
				amountPercentType.setBasisType(cancellationInformation.getChargeType());
				amountPercentType.setCurrencyCode(cancellationInformation.getCurrency());
				//lcpt.setAmountPercent(amountpercenttype);


				AmountPercentType apiAmountPercent = new AmountPercentType();
				AmountPercentType baseAmountPercent = new AmountPercentType();  
				AmountPercentType baseWithoutMarkupAmountPercent = new AmountPercentType();


				BeanUtils.copyProperties(amountPercentType, apiAmountPercent);
				BeanUtils.copyProperties(amountPercentType, baseAmountPercent);
				BeanUtils.copyProperties(amountPercentType, baseWithoutMarkupAmountPercent);	

				lcpt.setAmountPercent(amountPercentType);
				lcpt.setApiAmountPercent(apiAmountPercent);
				lcpt.setBaseAmountPercent(baseAmountPercent);
				lcpt.setBaseWithoutMarkupAmountPercent(baseWithoutMarkupAmountPercent);





				Deadline ldeadline = new Deadline();				
				//ldeadline.setOffsetTimeUnit(TimeUnitType.fromValue(cpt.getDeadline().getOffsetTimeUnit()));
				ldeadline.setOffsetTimeUnit(TimeUnitType.FULL_DURATION);
				//ldeadline.setOffsetDropTime(cpt.getDeadline().getOffsetDropTime());
				int unitmultiplier = 1;
				ldeadline.setOffsetUnitMultiplier(unitmultiplier);
				lcpt.setDeadline(ldeadline);

				List<PenaltyDescription> lpenlist = new ArrayList<PenaltyDescription>();
				PenaltyDescription lpdes = new PenaltyDescription();
				lpdes.setDrescription(cancellationPolicyResponse.getCancellationInformations().getInfo());
				lpenlist.add(lpdes);
				lcpt.setPenaltyDescriptions(lpenlist);
				cancellist.add(lcpt);				

			}
			cancelpenalities.setCancelPenalties(cancellist);
			lrt.setCancelPenalties(cancelpenalities);		
		}
		return lrt;
	}


	public APIHotelCancelResponse converttbotoNativeCancelResponse(APIHotelCancelResponse apiHotelCancel, com.tayyarah.api.hotel.tbo.cancel.model.GetChangeRequestStatusResponse getChangeRequestStatusResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		APIStatus apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Cancellation failed ..." );
		Status status = new Status(Status.STATUS_CODE_ERROR, "Cancellation failed ..." );
		if(getChangeRequestStatusResponse != null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult() != null )
		{			
			/*	{
				"HotelChangeRequestStatusResult": {
				"ResponseStatus": 1,
				"Error": {
				"ErrorCode": 0,
				"ErrorMessage": ""
				}, 
				"TraceId": "51f76eaf-c4ec-43f7-8d96-6288fcba7da1",
				"ChangeRequestId": 199925,
				"RefundedAmount": 4262.50,
				"CancellationCharge": 450,
				"ChangeRequestStatus": 3
				}
				}*/
			if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus() !=null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus() == 1)
			{				
				UniqueIDType apicanceluniqueid = new UniqueIDType();				
				apicanceluniqueid.setApiConfirmationNo(String.valueOf((getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestId()!=null?getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestId():0)));
				apicanceluniqueid.setApiBookingCode(String.valueOf(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestId()!=null?getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestId():0));
				apicanceluniqueid.setApiBookingId(String.valueOf(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestId()!=null?getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestId():0));
				apicanceluniqueid.setID(String.valueOf(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestId()));				
				apiHotelCancel.setApiUniqueId(apicanceluniqueid);				
				UniqueIDType canceluniqueid = new UniqueIDType();
				BeanUtils.copyProperties(apicanceluniqueid, canceluniqueid);
				apiHotelCancel.setUniqueId(canceluniqueid);					
				CancelRuleType apiCancelRule = new CancelRuleType();
				apiCancelRule.setAmount(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getCancellationCharge());
				apiCancelRule.setRefundAmount(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getRefundedAmount());
				Double newApiPrice = new Double(0);
				String creditNoteNo = null;
				String creditNoteCreatedOn = null;
				Map<String,Object> hotelChangeRequestStatusResultMap=getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getAdditionalProperties();
				if(hotelChangeRequestStatusResultMap!=null && hotelChangeRequestStatusResultMap.size()>0){
					newApiPrice=hotelChangeRequestStatusResultMap.containsKey("TotalPrice")?(Double) hotelChangeRequestStatusResultMap.get("TotalPrice"):new Double(0);
					creditNoteNo=hotelChangeRequestStatusResultMap.containsKey("CreditNoteNo")?(String)hotelChangeRequestStatusResultMap.get("CreditNoteNo"):null;
					creditNoteCreatedOn=hotelChangeRequestStatusResultMap.containsKey("CreditNoteCreatedOn")?(String)hotelChangeRequestStatusResultMap.get("CreditNoteCreatedOn"):null;
				}

				//Set<String> strings=getChangeRequestStatusResponse.getAdditionalProperties().keySet();
				apiCancelRule.setAPIAmount(new BigDecimal(newApiPrice));
				apiCancelRule.setCreditNoteNo(creditNoteNo);
				apiCancelRule.setCreditNoteCreatedOn(creditNoteCreatedOn);

				//apiCancelRule.setAPIAmount(getChangeRequestStatusResponse.getAdditionalProperties().get(key));
				CancelRuleType cancelRule = new CancelRuleType();
				BeanUtils.copyProperties(apiCancelRule, cancelRule);
				apiHotelCancel.setApiCancelRule(apiCancelRule);
				apiHotelCancel.setCancelRule(cancelRule);				
				switch (getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestStatus()!=null?getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getChangeRequestStatus():0) {
				case 0:
					apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_NOT_SET, APIStatus.STATUS_MESSAGE_CANCEL_NOT_SET );
					break;
				case 1:
					apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_PENDING, APIStatus.STATUS_MESSAGE_CANCEL_PENDING );
					break;
				case 2:
					apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_IN_PROCESS, APIStatus.STATUS_MESSAGE_CANCEL_IN_PROGRESS );
					break;
				case 3:
					apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_PROCESSED, APIStatus.STATUS_MESSAGE_CANCEL_PROCESSED );
					break;
				case 4:
					apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_REJECTED, APIStatus.STATUS_MESSAGE_CANCEL_REJECTED );
					break;

				default:
					apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Cancellation failed ..." );
					break;
				}	

				long i =(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus());
				int longTointValue =(int) i;

				switch (getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getResponseStatus()!=null?longTointValue:0) {
				case 0:
					status = new Status(Status.STATUS_CODE_CANCEL_NOT_SET, Status.STATUS_MESSAGE_CANCEL_NOT_SET );
					break;
				case 1:
					status = new Status(Status.STATUS_CODE_CANCEL_SUCCESSFULL, Status.STATUS_MESSAGE_CANCEL_SUCCESSFULL );
					break;
				case 2:
					status = new Status(Status.STATUS_CODE_CANCEL_FAILED, Status.STATUS_MESSAGE_CANCEL_FAILED );
					break;
				case 3:
					status = new Status(Status.STATUS_CODE_CANCEL_INVALID_REQUEST, Status.STATUS_MESSAGE_CANCEL_INVALID_REQUEST );
					break;
				case 4:
					status = new Status(Status.STATUS_CODE_CANCEL_INVALID_SESSION, Status.STATUS_MESSAGE_CANCEL_INVALID_SESSION );
					break;
				case 5:
					status = new Status(Status.STATUS_CODE_CANCEL_INVALID_CREDENTIALS, Status.STATUS_MESSAGE_CANCEL_INVALID_CREDENTIALS );
					break;

				default:
					status = new Status(Status.STATUS_CODE_ERROR, "Cancellation failed ..." );
					break;
				}		

			}
			else if(getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError() != null && getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError().getErrorCode() != null)
			{
				/*"Error" : {
			      "ErrorCode" : 3,
			      "ErrorMessage" : "BookingId should be a positive integer."
			    }*/
				apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, getChangeRequestStatusResponse.getHotelChangeRequestStatusResult().getError().getErrorMessage() );

			}

		}
		apiHotelCancel.setApiStatus(apiStatus);
		apiHotelCancel.setStatus(status);
		return apiHotelCancel;
	}



}
