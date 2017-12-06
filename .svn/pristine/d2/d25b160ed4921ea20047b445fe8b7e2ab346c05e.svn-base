package com.tayyarah.hotel.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.ProtocolException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.api.hotel.rezlive.model.BookingResponse;
import com.tayyarah.api.hotel.rezlive.model.CancellationPolicyResponse;
import com.tayyarah.api.hotel.rezlive.model.HotelDetailsResponse;
import com.tayyarah.api.hotel.rezlive.model.HotelFindResponse.Hotels.Hotel;
import com.tayyarah.api.hotel.rezlive.model.HotelFindResponse.Hotels.Hotel.RoomDetails.RoomDetail;
import com.tayyarah.api.hotel.rezlive.model.PreBookingResponse;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.AddressType;
import com.tayyarah.hotel.model.AmountDeterminationType;
import com.tayyarah.hotel.model.AmountPercentType;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.BasicPropertyInfoType.ContactNumbers.ContactNumber;
import com.tayyarah.hotel.model.BasicPropertyInfoType.Position;
import com.tayyarah.hotel.model.CancelPenaltiesType;
import com.tayyarah.hotel.model.CancelPenaltyType;
import com.tayyarah.hotel.model.CancelPenaltyType.Deadline;
import com.tayyarah.hotel.model.CountryNameType;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelOverview;
import com.tayyarah.hotel.model.HotelReservationIDsType;
import com.tayyarah.hotel.model.HotelReservationsType;
import com.tayyarah.hotel.model.HotelReservationsType.HotelReservation;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.OTAHotelResRS.CancellationInformations;
import com.tayyarah.hotel.model.OTAHotelResRS.CancellationInformations.CancellationInformation;
import com.tayyarah.hotel.model.PenaltyDescription;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.ResGlobalInfoType;
import com.tayyarah.hotel.model.RoomBookingKeyMap;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.RoomStayType.RatePlans;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts.GuestCount;
import com.tayyarah.hotel.model.RoomStayType.RoomTypes;
import com.tayyarah.hotel.model.RoomTypeType;
import com.tayyarah.hotel.model.RoomTypeType.Occupancy;
import com.tayyarah.hotel.model.RoomTypeType.Room;
import com.tayyarah.hotel.reposit.entity.LintasConstantFactory;
import com.tayyarah.hotel.model.TaxType;
import com.tayyarah.hotel.model.TaxesType;
import com.tayyarah.hotel.model.TimeUnitType;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.model.UniqueIDType;



public class RezLiveResponseParser {
	static final Logger logger = Logger.getLogger(RezLiveResponseParser.class);

	@Autowired
	HotelIdFactoryImpl hotelIdFactory;	


	public RezLiveResponseParser() {
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

	public RoomStay convertRezLivetoNativeRoomDetail(HotelSearchCommand hsc, Hotel rs, HotelOverview ho, List<Facility> hotelfacilitieslist, List<Facility> hotelRoomfacilitieslist, List<String> hotelimages, Map<Integer, Hotelroomdescription> hotelroomsmap) throws HibernateException, ClassNotFoundException, JAXBException, ParseException, CloneNotSupportedException 
	{	
		//-HotelOverview- HotelOverview [hoteId=99751, 
		///apiVendorId=215370, apiProviderId=2H, address=23 Airport RoadKodihalliBangalore 560 008,
		//		address1=null, address2=null, area=null, area_Seo_Id=null, city=Bengaluru, city_Zone=null,
		//		country=India, defaultCheckInTime=null, defaultCheckOutTime=null, hotel_Star=null, hotelClass=null, 
		//hotelGroupID=null, hotelGroupName=null, hotelOverview=null, hotelSearchKey=null, id=null, imagePath=null, 
		//latitude=12.960763000000000, location=560 008, longitude=77.64837300000000, reviewCount=null,
		//		reviewRating=5, vendorName=The Leela Palace, weekdayRank=null, weekendRank=null]

		BigDecimal minbase = new BigDecimal(0);	
		RoomStay troomstay = new RoomStay();
		BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
		int noofdays = CommonUtil.getNoofStayDays(hsc);
		//tbasic.setHotelCode(rs.getId());	
	
		if(ho != null)
		{
			tbasic.setHotelName(ho.getVendorName());
			tbasic.setApiVendorID(rs.getId());
			tbasic.setHotelCode(String.valueOf(ho.getHoteId().intValue()));
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
			addresslines.add("");
			addresslines.add("");
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
			tbasic.setChainCode("");
		}
		try{
			minbase = rs.getPrice().divide(new BigDecimal(noofdays));
			tbasic.setApiPrice(minbase);
			tbasic.setBasePrice(minbase);
			tbasic.setBasePriceWithoutMarkup(minbase);
			tbasic.setBookingPrice(minbase);			
			logger.info("object transformation---: rezlive to common. -----single day price "+tbasic.getBasePrice());	
		}
		catch(ArithmeticException ex)
		{
			minbase = rs.getPrice().divide(new BigDecimal(noofdays), 7, RoundingMode.HALF_UP);
			logger.info("object transformation---: rezlive to common. -----tbasic-rs.getPrice() divide ArithmeticException "+ex.getMessage());			
			tbasic.setApiPrice(minbase);
			tbasic.setBasePrice(minbase);
			tbasic.setBasePriceWithoutMarkup(minbase);
			tbasic.setBookingPrice(minbase);			
			logger.info("object transformation---: rezlive to common. -----single day price "+tbasic.getBasePrice());				
			
		}
		tbasic.setApiProvider(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
		tbasic.setIsOfflineBooking(false);
		tbasic.setHotelimages(hotelimages);
		tbasic.setHotelAmenities(hotelfacilitieslist);		
		troomstay.setBasicPropertyInfo(tbasic);	
		RoomTypes troomtypes = new RoomTypes();
		List<RoomTypeType> lrtlist = new ArrayList<RoomTypeType>();

		RatePlans trateplans = new RatePlans();
		List<RatePlanType> lplantlist = new ArrayList<RatePlanType>();

		RoomRates troomrates = new RoomRates();
		List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();


		for (RoomDetail roomDetail : rs.getRoomDetails().getRoomDetails()) {	

			logger.info("object transformation---: rezlive to common. -----hotelIdFactory- "+HotelIdFactoryImpl.getInstance());

			RoomBookingKeyMap roomBookingKeyMap = HotelIdFactoryImpl.getInstance().createRoomRatePlanCodes(roomDetail.getBookingKey());			
			logger.info("object transformation---: rezlive to common. -----roomDetail- "+roomDetail.toString());			
			RoomTypeType lrt = new RoomTypeType();		
			Hotelroomdescription lroomdes = new Hotelroomdescription();					
			lrt.setRoomType(roomDetail.getType());
			lrt.setRoomTypeCode(roomBookingKeyMap.getRoomTypeCode());				
			Integer noofunits = Integer.valueOf(roomDetail.getTotalRooms());
			lrt.setNumberOfUnits(noofunits);					
			lroomdes.setId(1);
			lroomdes.setImagePath("");	
			lroomdes.setDescription(roomDetail.getRoomDescription());						
			lrt.setRoomDescription(lroomdes);
			lrt.setTermsAndConditions(roomDetail.getTermsAndConditions());
			List<Facility> facilitiesRoom = new ArrayList<Facility>();						
			lrt.setAmenities(facilitiesRoom);
			List<Occupancy> locclist = new ArrayList<RoomTypeType.Occupancy>();
			Occupancy locc = new Occupancy();
			locc.setAgeQualifyingCode("");					
			locc.setAgeBucket("");
			locc.setMinOccupancy(1);
			locclist.add(locc);
			lrt.setOccupancies(locclist);	
		    lrtlist.add(lrt);

			//Constructing Rateplans...
			RatePlanType lrp = new RatePlanType();
			lrp.setRatePlanType(RoomDetailConstantFactory.RATEPLAN_TYPE);
			lrp.setRatePlanCode(roomBookingKeyMap.getRatePlanCode());
			lrp.setRatePlanName(RoomDetailConstantFactory.RATEPLAN_NAME);
			logger.info("object transformation---:  parsing------ rp.getRatePlanCode()--"+roomBookingKeyMap.getRatePlanCode());
			CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();			
			logger.info("object transformation---:  parsing------ rp.getRatePlanCode() -"+roomBookingKeyMap.getRatePlanCode());
			List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
			CancelPenaltyType lcpt = new CancelPenaltyType();

			Deadline ldeadline = new Deadline();	
			ldeadline.setOffsetTimeUnit(TimeUnitType.DAY);
			ldeadline.setOffsetDropTime("1");			
			ldeadline.setOffsetUnitMultiplier(1);
			lcpt.setDeadline(ldeadline);
			logger.info("object transformation---: getDeadline parsing------ rp.getRatePlanCode() -"+roomBookingKeyMap.getRatePlanCode());

			List<PenaltyDescription> lpenlist = new ArrayList<PenaltyDescription>();
			PenaltyDescription lpdes = new PenaltyDescription();
			lpdes.setDrescription(RoomDetailConstantFactory.CANCELLATION_POLICY_DESCRIPTION);
			lpenlist.add(lpdes);
			cancellist.add(lcpt);
			cancelpenalities.setCancelPenalties(cancellist);
			lrp.setCancelPenalties(cancelpenalities);			
			lplantlist.add(lrp);

			//RoomRate Construction..
			RoomStayType.RoomRates.RoomRate lrr = new RoomStayType.RoomRates.RoomRate();
			lrr.setRatePlanCode(roomBookingKeyMap.getRatePlanCode());	
			lrr.setBookingCode(roomBookingKeyMap.getBookingKey());
			lrr.setRoomID(roomBookingKeyMap.getRoomTypeCode());
			lrr.setRoomTypeCode(roomBookingKeyMap.getRoomTypeCode());

			RateType lrates = new RateType();
			List<Rate> lratelist = new ArrayList<Rate>();
			List<Room> rooms = getBookedRooms(roomDetail);
			for (Room room : rooms) {				

				Rate lrate = new Rate();
				lrate.setRoomIndex(room.getRoomIndex());	
				lrate.setName(room.getName());
				lrate.setAdults(room.getAdults());
				lrate.setChildrenages(room.getChildrenages());
				lrate.setChildren(room.getChildren());		

				//Total base
				TotalType ltot = new TotalType();
				ltot.setAmountBeforeTax(room.getTotal());
				ltot.setAmountAfterTax(room.getTotal());
				TaxesType ltax = new TaxesType();			
				List<TaxType> ltaxlist  = new ArrayList<TaxType>();
				TaxType lrtt = new TaxType();
				lrtt.setAmount(new BigDecimal("0"));
				lrtt.setCode("");
				lrtt.setType(AmountDeterminationType.EXCLUSIVE);
				ltaxlist.add(lrtt);
				ltax.setAmount(new BigDecimal("0"));
				ltax.setTaxes(ltaxlist);
				TaxesType ltaxmarkup = ltax;
				ltot.setTaxes(ltax);				
				
				//Single day base
				TotalType ltotperday = new TotalType();
				try{
					ltotperday.setAmountBeforeTax(room.getTotal().divide(new BigDecimal(noofdays)));
					ltotperday.setAmountAfterTax(room.getTotal().divide(new BigDecimal(noofdays)));
					logger.info("object transformation---: rezlive to common. -----single day price "+ltotperday.getAmountBeforeTax());	
				}
				catch(ArithmeticException ex)
				{
					logger.info("object transformation---: rezlive to common. -----tbasic-rs.getPrice() divide ArithmeticException "+ex.getMessage());			
					ltotperday.setAmountBeforeTax(room.getTotal().divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP));
					ltotperday.setAmountAfterTax(room.getTotal().divide(new BigDecimal(noofdays),7, RoundingMode.HALF_UP));
					logger.info("object transformation---: rezlive to common. -----rateplan single day price "+ltotperday.getAmountBeforeTax());			
					
				}
				TaxesType ltaxperday = new TaxesType();			
				List<TaxType> ltaxlistperday  = new ArrayList<TaxType>();
				TaxType lrttperday = new TaxType();
				lrttperday.setAmount(new BigDecimal("0"));
				lrttperday.setCode("");
				lrttperday.setType(AmountDeterminationType.EXCLUSIVE);
				ltaxlistperday.add(lrttperday);
				ltaxperday.setAmount(new BigDecimal("0"));
				ltaxperday.setTaxes(ltaxlistperday);				
				ltotperday.setTaxes(ltaxperday);
				ltotperday.setTotalAmountPayable(room.getTotal());				

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

				lratelist.add(lrate);			
			}			
			lrates.setRates(lratelist);
			GuestCounts gustcounts = new GuestCounts();
			List<GuestCount> gustcountslist = new ArrayList<GuestCount>();				
			gustcounts.setGuestCounts(gustcountslist);
			lrr.setGuestCounts(gustcounts);

			lrr.setRates(lrates);
			lrratelist.add(lrr);
		}
		troomtypes.setRoomTypes(lrtlist);	
		troomstay.setRoomTypes(troomtypes);
		trateplans.setRatePlen(lplantlist);	
		troomstay.setRatePlans(trateplans);
		troomrates.setRoomRates(lrratelist);
		troomstay.setRoomRates(troomrates);
		return troomstay;
	}

	public RoomStay convertRezLivetoNativeRoomDetail(OTAHotelAvailRS.RoomStays.RoomStay rs, HotelDetailsResponse hotelDetailsResponse) throws HibernateException, ClassNotFoundException, JAXBException, ParseException, CloneNotSupportedException 
	{		
		BigDecimal minbase = new BigDecimal(0);	
		RoomStay troomstay = new RoomStay();
		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();		
		if(hotelDetailsResponse != null && hotelDetailsResponse.getHotels() != null)
		{			
			Position hpos = new Position();
			hpos.setLongitude(hotelDetailsResponse.getHotels().getLongitude());
			hpos.setLatitude(hotelDetailsResponse.getHotels().getLatitude());				
			tbasic.setPosition(hpos);
			AddressType laddress = new AddressType();
			laddress.setCityName(hotelDetailsResponse.getHotels().getCity());
			CountryNameType cn = new CountryNameType();
			cn.setValue(hotelDetailsResponse.getHotels().getCountry());
			laddress.setCountryName(cn);
			laddress.setPostalCode(hotelDetailsResponse.getHotels().getHotelPostalCode());
			List<String> addresslines = new ArrayList<String>();
			addresslines.add(hotelDetailsResponse.getHotels().getHotelAddress());
			laddress.setAddressLines(addresslines);
			List<ContactNumber> contactnos = new ArrayList<ContactNumber>();
			contactnos.add(new ContactNumber(hotelDetailsResponse.getHotels().getPhone()));
			contactnos.add(new ContactNumber(hotelDetailsResponse.getHotels().getTelephone()));
			tbasic.setAddress(laddress);
			tbasic.setContactNumbers(contactnos);			
			tbasic.setReviewRating(hotelDetailsResponse.getHotels().getRating());
			tbasic.setArea(hotelDetailsResponse.getHotels().getLocation());
			String amenitiestext = (hotelDetailsResponse.getHotels().getHotelAmenities() == null)?"No information available":hotelDetailsResponse.getHotels().getHotelAmenities();
			List<Facility> hotelAmenities = new ArrayList<Facility>();			
			try{
				String delimeter = "\\,";				
				String[] amenities = amenitiestext.split(delimeter);					
				for (String amenity : amenities) {						
					Facility facility = new Facility();
					facility.setDescription(amenity);
					facility.setAmenityType(LintasConstantFactory.FACILITY_TYPE);
					hotelAmenities.add(facility);
				}
			}
			catch(Exception e)
			{
				logger.info("Exception happened.."+e.getMessage());
			}			
			tbasic.setHotelAmenities(hotelAmenities);

			List<String> hotelimages = new ArrayList<String>();
			if(hotelDetailsResponse.getHotels().getImages() != null)
				for (String imageurl : hotelDetailsResponse.getHotels().getImages().getImages()) {
					hotelimages.add(imageurl);
				}	
			tbasic.setHotelimages(hotelimages);
		}		
		troomstay.setBasicPropertyInfo(tbasic);				
		return troomstay;
	}	

	public  RoomStay convertRezLivetoNative(Hotel rs, HotelOverview ho, List<Facility> hotelfacilitieslist, List<Facility> hotelRoomfacilitieslist, List<String> hotelimages, Map<Integer, Hotelroomdescription> hotelroomsmap) throws HibernateException, ClassNotFoundException, JAXBException 
	{	
		//-HotelOverview- HotelOverview [hoteId=99751, 
		///apiVendorId=215370, apiProviderId=2H, address=23 Airport RoadKodihalliBangalore 560 008,
		//		address1=null, address2=null, area=null, area_Seo_Id=null, city=Bengaluru, city_Zone=null,
		//		country=India, defaultCheckInTime=null, defaultCheckOutTime=null, hotel_Star=null, hotelClass=null, 
		//hotelGroupID=null, hotelGroupName=null, hotelOverview=null, hotelSearchKey=null, id=null, imagePath=null, 
		//latitude=12.960763000000000, location=560 008, longitude=77.64837300000000, reviewCount=null,
		//		reviewRating=5, vendorName=The Leela Palace, weekdayRank=null, weekendRank=null]

		BigDecimal minbase = new BigDecimal(0);	
		RoomStay troomstay = new RoomStay();
		BasicPropertyInfoType tbasic = new BasicPropertyInfoType();	
		if(ho != null)
		{
			tbasic.setHotelName(ho.getVendorName());
			tbasic.setApiVendorID(rs.getId());
			tbasic.setHotelCode(String.valueOf(ho.getHoteId().intValue()));
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
			addresslines.add("");
			addresslines.add("");
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
			tbasic.setChainCode("");

			//tbasic.setHoteloverview(ho);

		}
		
		tbasic.setApiPrice(rs.getPrice());
		tbasic.setBasePrice(rs.getPrice());
		tbasic.setBasePriceWithoutMarkup(rs.getPrice());
		tbasic.setBookingPrice(rs.getPrice());	
		
		tbasic.setApiProvider(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
		tbasic.setIsOfflineBooking(false);
		tbasic.setHotelimages(hotelimages);
		tbasic.setHotelAmenities(hotelfacilitieslist);		
		troomstay.setBasicPropertyInfo(tbasic);	
		RoomTypes troomtypes = new RoomTypes();
		List<RoomTypeType> lrtlist = new ArrayList<RoomTypeType>();
		for (RoomDetail roomDetail : rs.getRoomDetails().getRoomDetails()) {			
			RoomTypeType lrt = new RoomTypeType();		
			Hotelroomdescription lroomdes = new Hotelroomdescription();

			// to be commented/////

			/*<Type><![CDATA[Deluxe Room]]></Type>
		    <BookingKey>BcGxDcAwCATAiV56CBi8QUZIC8hpsn-fuxgTarGtkjOHpX2Zz7t6R53AcysIpThEIBvizDR8Pw</BookingKey>
		    <Adults>1</Adults>
		    <Children>1</Children>
		    <ChildrenAges>4</ChildrenAges>
		    <TotalRooms>1</TotalRooms>
		    <TotalRate>38932.3981236</TotalRate>
		    <RoomDescription><![CDATA[]]></RoomDescription>
		    <TermsAndConditions><![CDATA[]]></TermsAndConditions>
			 */		
			lrt.setRoomType(roomDetail.getType());			
			lrt.setRoomTypeCode("");					
			Integer noofunits = Integer.valueOf(roomDetail.getTotalRooms());
			lrt.setNumberOfUnits(noofunits);
    		lroomdes.setId(1);
			lroomdes.setImagePath("");	
			lroomdes.setDescription(roomDetail.getRoomDescription());	
			List<Facility> facilitiesRoom = new ArrayList<Facility>();
			lrt.setAmenities(facilitiesRoom);			
			List<Occupancy> locclist = new ArrayList<RoomTypeType.Occupancy>();
			Occupancy locc = new Occupancy();
			locc.setAgeQualifyingCode("");
			locc.setAgeBucket("");
			locc.setMinOccupancy(1);
			locclist.add(locc);
			lrt.setOccupancies(locclist);
			lrtlist.add(lrt);
		}
		troomtypes.setRoomTypes(lrtlist);
		troomstay.setRoomTypes(troomtypes);
		return troomstay;
	}
	public RoomStay convertRezLivetoRoomDetail(OTAHotelAvailRS.RoomStays.RoomStay trs) throws HibernateException, ClassNotFoundException, JAXBException 
	{	
		OTAHotelAvailRS.RoomStays.RoomStay troomstay = trs;		
		RoomTypes troomtypes = new RoomTypes();
		List<RoomTypeType> lrtlist = new ArrayList<RoomTypeType>();
		if(trs.getRoomTypes() == null || trs.getRoomTypes().getRoomTypes() == null || trs.getRoomTypes().getRoomTypes().isEmpty())
			for (RoomTypeType rt : trs.getRoomTypes().getRoomTypes()) {
				RoomTypeType lrt = rt;
				lrtlist.add(lrt);
			}
		troomtypes.setRoomTypes(lrtlist);		
		troomstay.setRoomTypes(troomtypes);	
		return troomstay;
	}

	public APIHotelBook convertRezLivetoNativePreBookResponse(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory, PreBookingResponse preBookingResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebooking ..." );
		if(apiHotelBook.getPreBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getPreBookRes();
			status = totaHotelResRS.getStatus();
		}
		//bookingDetails		
		if(preBookingResponse!=null && preBookingResponse.getPreBookingDetails() != null && preBookingResponse.getPreBookingDetails().getStatus()!=null)
		{
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, preBookingResponse.getPreBookingDetails().getStatus() ); 
			if(preBookingResponse.getPreBookingDetails().getStatus().equalsIgnoreCase("True"))
			{
				status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, preBookingResponse.getPreBookingDetails().getStatus() );
				com.tayyarah.api.hotel.rezlive.model.PreBookingResponse.PreBookingRequest.PreBooking.RoomDetails.RoomDetail roomdetail = preBookingResponse.getPreBookingRequest().getPreBooking().getRoomDetails().getRoomDetail();
				PreBookingResponse.PreBookingRequest.PreBooking.CancellationInformations cancellationInformations = preBookingResponse.getPreBookingRequest().getPreBooking().getCancellationInformations();
				CancellationInformations tcancellationInformations = new CancellationInformations();
				BeanUtils.copyProperties(tcancellationInformations, cancellationInformations);
				totaHotelResRS.setCancellationInformations(tcancellationInformations);
				totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
				totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
				HotelReservationsType hotelReservationsType = new HotelReservationsType();
				List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
				HotelReservation thotelReservation = new HotelReservation();
				List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
				UniqueIDType uniqueIDType = new UniqueIDType();
				uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
				uniqueIDType.setID(roomdetail.getBookingKey());
				uniqueIDType.setApiBookingId(roomdetail.getBookingKey());
				uniqueIDType.setApiBookingCode(roomdetail.getBookingKey());
				uniqueIDType.setApiConfirmationNo(roomdetail.getBookingKey());
				totaHotelResRS.setBookingCode(roomdetail.getBookingKey());
				uniqueIDTypeList.add(uniqueIDType);
				thotelReservation.setUniqueIDs(uniqueIDTypeList);			
				ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
				HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
				List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();

				RoomStayType.RoomRates.RoomRate roomrate = roomStay.getRoomRates().getRoomRates().get(0);
				for (Rate rate : roomrate.getRates().getRates()) {
					HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
					thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
					thotelresid.setResIDValue(roomdetail.getBookingKey());//XHUB891565bookingId
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

				BigDecimal newtotalrate = preBookingResponse.getPreBookingDetails().getBookingAfterPrice();
				logger.info("prebooking revised rate----"+preBookingResponse.getPreBookingDetails().getBookingAfterPrice());
				totaHotelResRS.setApiFinalPrice(newtotalrate);
				totaHotelResRS.setBaseFinalPrice(newtotalrate);
				totaHotelResRS.setBaseFinalPriceWithoutMarkup(newtotalrate);
				totaHotelResRS.setBookingFinalPrice(newtotalrate);			
			}			
		}
		else if(preBookingResponse!=null && preBookingResponse.getError() != null)
		{
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebook Error -"+preBookingResponse.getError() ); 
		}
		else
		{
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Book Error - API" ); 
		}
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setPreBookRes(totaHotelResRS);
		return apiHotelBook;
	}
	
	public APIHotelBook convertRezLivetoNativeBookResponse(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelOrderRow hor, HotelIdFactoryImpl hotelIdFactory, BookingResponse bookingResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Booking ..." );
		if(apiHotelBook.getBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getBookRes();
			status = totaHotelResRS.getStatus();
		}
		if(bookingResponse != null&& bookingResponse.getBookingDetails() != null && bookingResponse.getBookingDetails().getBookingStatus()!=null)
		{
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, bookingResponse.getBookingDetails().getBookingStatus() ); 
			if(bookingResponse.getBookingDetails().getBookingStatus().equalsIgnoreCase("Confirmed"))
			{
				status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, bookingResponse.getBookingDetails().getBookingStatus() );  
				totaHotelResRS.setBookingResponse(bookingResponse);
				totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
				totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
				HotelReservationsType hotelReservationsType = new HotelReservationsType();
				List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
				HotelReservation thotelReservation = new HotelReservation();
				List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
				UniqueIDType uniqueIDType = new UniqueIDType();
				uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
				uniqueIDType.setID(bookingResponse.getBookingDetails().getBookingId());
				uniqueIDType.setApiBookingId(bookingResponse.getBookingDetails().getBookingId());
				uniqueIDType.setApiBookingCode(bookingResponse.getBookingDetails().getBookingId());
				uniqueIDType.setApiConfirmationNo(bookingResponse.getBookingDetails().getBookingId());
				uniqueIDTypeList.add(uniqueIDType);
				thotelReservation.setUniqueIDs(uniqueIDTypeList);
				ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
				HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
				List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();

				RoomStayType.RoomRates.RoomRate roomrate = roomStay.getRoomRates().getRoomRates().get(0);
				for (Rate rate : roomrate.getRates().getRates()) {
					HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
					thotelresid.setResIDType(bookingResponse.getBookingDetails().getBookingCode());
					thotelresid.setResIDValue(bookingResponse.getBookingDetails().getBookingId());//XHUB891565bookingId
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
				BigDecimal newtotalrate = bookingResponse.getBookingDetails().getBookingPrice();
				totaHotelResRS.setApiFinalPrice(newtotalrate);
				totaHotelResRS.setBaseFinalPrice(newtotalrate);
				totaHotelResRS.setBaseFinalPriceWithoutMarkup(newtotalrate);
				totaHotelResRS.setBookingFinalPrice(newtotalrate);
			}
		}		
		else
		{
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Book Error - API" ); 
		}
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setBookRes(totaHotelResRS);
		return apiHotelBook;
	}


	public APIHotelBook convertRezLivetoNativeCancellationPolicy(APIHotelBook apiHotelBook, CancellationPolicyResponse cancellationPolicyResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
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
	
	public OTAHotelAvailRS.RoomStays.RoomStay convertRezLivetoNativeCancellationPolicyRoomDetail(OTAHotelAvailRS.RoomStays.RoomStay roomStay, CancellationPolicyResponse cancellationPolicyResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
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
				AmountPercentType amountpercenttype = new AmountPercentType();
				amountpercenttype.setAmount(cancellationInformation.getChargeAmount());
				amountpercenttype.setBasisType(cancellationInformation.getChargeType());
				amountpercenttype.setCurrencyCode(cancellationInformation.getCurrency());
				lcpt.setAmountPercent(amountpercenttype);
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
	
	public RatePlanType convertRezLivetoNativeCancellationPolicyRoomDetailRatePlan(RatePlanType lrt, CancellationPolicyResponse cancellationPolicyResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		if(cancellationPolicyResponse != null && cancellationPolicyResponse.getCancellationInformations() != null && cancellationPolicyResponse.getCancellationInformations().getCancellationInformations() != null)
		{
			CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();
			List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();

			for (com.tayyarah.api.hotel.rezlive.model.CancellationPolicyResponse.CancellationInformations.CancellationInformation cancellationInformation : cancellationPolicyResponse.getCancellationInformations().getCancellationInformations()) {
				CancelPenaltyType lcpt = new CancelPenaltyType();			
				lcpt.setStart(cancellationInformation.getStartDate());
				lcpt.setEnd(cancellationInformation.getEndDate());
				AmountPercentType amountpercenttype = new AmountPercentType();
				amountpercenttype.setAmount(cancellationInformation.getChargeAmount());
				amountpercenttype.setBasisType(cancellationInformation.getChargeType());
				amountpercenttype.setCurrencyCode(cancellationInformation.getCurrency());
				lcpt.setAmountPercent(amountpercenttype);
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
				
			}
			cancelpenalities.setCancelPenalties(cancellist);
			lrt.setCancelPenalties(cancelpenalities);		
		}
		return lrt;
	}
	
}
