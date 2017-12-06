package com.tayyarah.hotel.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.AddressType;
import com.tayyarah.hotel.model.AmountDeterminationType;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.BasicPropertyInfoType.ContactNumbers.ContactNumber;
import com.tayyarah.hotel.model.BasicPropertyInfoType.Position;
import com.tayyarah.hotel.model.CancelPenaltiesType;
import com.tayyarah.hotel.model.CancelPenaltyType;
import com.tayyarah.hotel.model.CancelPenaltyType.Deadline;
import com.tayyarah.hotel.model.CountryNameType;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelReservationIDsType;
import com.tayyarah.hotel.model.HotelReservationsType;
import com.tayyarah.hotel.model.HotelReservationsType.HotelReservation;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.PenaltyDescription;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.ResGlobalInfoType;

import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.RoomStayType.RatePlans;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts.GuestCount;
import com.tayyarah.hotel.model.RoomStayType.RoomTypes;

import com.tayyarah.hotel.model.RoomTypeType;
import com.tayyarah.hotel.model.RoomTypeType.Occupancy;
import com.tayyarah.hotel.model.TaxType;
import com.tayyarah.hotel.model.TaxesType;
import com.tayyarah.hotel.model.TimeUnitType;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.model.UniqueIDType;
import com.tayyarah.hotel.reposit.dao.HotelRepositDAOIMP;
import com.tayyarah.hotel.reposit.entity.Hoteloverview;
import com.tayyarah.hotel.reposit.entity.Hotelroomfare;
import com.tayyarah.hotel.reposit.entity.LintasConstantFactory;

;

public class LintasResponseParser {
	static final Logger logger = Logger.getLogger(LintasResponseParser.class);

	@Autowired
	HotelIdFactoryImpl hotelIdFactory;	

	public RoomStay convertLintastoNativeRoomDetail(HotelSearchCommand hsc, Hoteloverview ho, HotelRepositDAOIMP hotelRepositDAOIMP) throws HibernateException, ClassNotFoundException, JAXBException, ParseException, CloneNotSupportedException 
	{	
		BigDecimal minbase = new BigDecimal(0);	
		RoomStay troomstay = new RoomStay();
		BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
		int noofdays = CommonUtil.getNoofStayDays(hsc);	
		logger.info("object transformation---: lintas to common. -----rs.getId()- "+String.valueOf(ho.getId().getVendorID()));
		if(ho != null)
		{
			tbasic.setHotelName(ho.getVendorName());
			tbasic.setApiVendorID(String.valueOf(ho.getId().getVendorID()));
			tbasic.setHotelCode(String.valueOf(ho.getId().getVendorID()));
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
			tbasic.setArea_Seo_Id("");
			tbasic.setDefaultCheckInTime(ho.getDefaultCheckInTime());
			tbasic.setDefaultCheckOutTime(ho.getDefaultCheckOutTime());
			tbasic.setHotel_Star(3);
			tbasic.setHotelClass("Deluxe");
			tbasic.setWeekdayRank(3);
			tbasic.setWeekendRank(3);
			tbasic.setChainCode("");	
			
			HashMap<Integer, String> apiProviderMap = new HashMap<Integer, String>();
			apiProviderMap.put(HotelApiCredentials.API_LINTAS_REPOSITORY, String.valueOf(ho.getId().getVendorID()));
			tbasic.setApiProviderMap(apiProviderMap);

		}
		minbase = new BigDecimal(1000);
		tbasic.setApiPrice(minbase);
		tbasic.setBasePrice(minbase);
		tbasic.setBasePriceWithoutMarkup(minbase);
		tbasic.setBookingPrice(minbase);			
		tbasic.setApiProvider(HotelApiCredentials.API_LINTAS_REPOSITORY);
		tbasic.setIsOfflineBooking(false);
		
		List<Facility> hotelAmenities = new ArrayList<Facility>();			
		List<com.tayyarah.hotel.reposit.entity.Facility> lintashotelAmenities = hotelRepositDAOIMP.getFacilityById(ho.getId().getVendorID());
		for (com.tayyarah.hotel.reposit.entity.Facility lintasfacility : lintashotelAmenities) {
			Facility facility = new Facility();
			facility.setDescription(lintasfacility.getDescription());
			facility.setAmenityType(LintasConstantFactory.FACILITY_TYPE);
			hotelAmenities.add(facility);
		}
		tbasic.setHotelAmenities(hotelAmenities);
		List<String> hotelimages = new ArrayList<String>();
		hotelimages.add(LintasConstantFactory.HOTEL_IMAGE_PATH);
		tbasic.setHotelimages(hotelimages);		
		troomstay.setBasicPropertyInfo(tbasic);	
		RoomTypes troomtypes = new RoomTypes();
		List<RoomTypeType> lrtlist = new ArrayList<RoomTypeType>();
		RatePlans trateplans = new RatePlans();
		List<RatePlanType> lplantlist = new ArrayList<RatePlanType>();
		RoomRates troomrates = new RoomRates();
		List<RoomRate> lrratelist = new ArrayList<RoomRate>();
		List<com.tayyarah.hotel.reposit.entity.Hotelroomdescription> roomdescriptions = hotelRepositDAOIMP.getHotelroomdescriptionById(ho.getId().getVendorID());
		Map<Integer, Hotelroomfare> roomfaremap = hotelRepositDAOIMP.getHotelroomfareMap(ho.getId().getVendorID());
		for (com.tayyarah.hotel.reposit.entity.Hotelroomdescription hotelroomdescription : roomdescriptions) {
			String bookingkey = HotelIdFactoryImpl.getInstance().createLongId("LINBOOK");
			Hotelroomfare roomrate = roomfaremap.get(hotelroomdescription.getRoomno());
			if(roomrate != null)
			{
				RoomTypeType lrt = new RoomTypeType();		
				Hotelroomdescription lroomdes = new Hotelroomdescription();									
				lrt.setRoomType(hotelroomdescription.getRoomType());
				lrt.setRoomTypeCode(String.valueOf(hotelroomdescription.getRoomno()));										
				Integer noofunits = Integer.valueOf(hotelroomdescription.getAvailable_max());
				lrt.setNumberOfUnits(noofunits);							
				lroomdes.setId(1);
				lroomdes.setImagePath(LintasConstantFactory.ROOM_IMAGE_PATH);	
				lroomdes.setDescription(hotelroomdescription.getRoomType());						
				lrt.setRoomDescription(lroomdes);
				lrt.setTermsAndConditions(LintasConstantFactory.TERMSANDCONDITIONS);
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
				lrp.setRatePlanType(LintasConstantFactory.RATEPLAN_TYPE);
				lrp.setRatePlanCode(roomrate.getFareid());
				lrp.setRatePlanName(LintasConstantFactory.RATEPLAN_NAME);
				logger.info("object transformation---:  parsing------ rp.getRatePlanCode()--"+roomrate.getFareid());
				CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();			
				List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
				CancelPenaltyType lcpt = new CancelPenaltyType();

				Deadline ldeadline = new Deadline();	
				ldeadline.setOffsetTimeUnit(TimeUnitType.DAY);
				ldeadline.setOffsetDropTime("1");			
				ldeadline.setOffsetUnitMultiplier(1);
				lcpt.setDeadline(ldeadline);
	
				List<PenaltyDescription> lpenlist = new ArrayList<PenaltyDescription>();
				PenaltyDescription lpdes = new PenaltyDescription();
				lpdes.setDrescription(LintasConstantFactory.CANCELLATION_POLICY_DESCRIPTION);
				lpenlist.add(lpdes);
				cancellist.add(lcpt);
				cancelpenalities.setCancelPenalties(cancellist);
				lrp.setCancelPenalties(cancelpenalities);			
				lplantlist.add(lrp);

				//RoomRate Construction..
				RoomStayType.RoomRates.RoomRate lrr = new RoomStayType.RoomRates.RoomRate();
				lrr.setRatePlanCode(roomrate.getFareid());	
				lrr.setBookingCode(bookingkey);
				lrr.setRoomID(String.valueOf(hotelroomdescription.getRoomno()));
				lrr.setRoomTypeCode(String.valueOf(hotelroomdescription.getRoomno()));

				RateType lrates = new RateType();
				List<Rate> lratelist = new ArrayList<Rate>();


				Rate lrate = new Rate();
				lrate.setRoomIndex(hotelroomdescription.getRoomno());	
				lrate.setName(LintasConstantFactory.ROOMRATE_NAME);
				lrate.setAdults(LintasConstantFactory.NO_OF_ADULTS);
				lrate.setChildrenages("");
				lrate.setChildren(LintasConstantFactory.NO_OF_CHILDREN);
				//Total base
				TotalType ltot = new TotalType();
				ltot.setAmountBeforeTax(roomrate.getFitNormalWd());

				BigDecimal amountaftertax = roomrate.getFitNormalWd().add(LintasConstantFactory.TAX_AMOUNT);				
				ltot.setAmountAfterTax(amountaftertax);			
				TaxesType ltax = new TaxesType();			
				List<TaxType> ltaxlist  = new ArrayList<TaxType>();
				TaxType lrtt = new TaxType();
				lrtt.setAmount(LintasConstantFactory.TAX_AMOUNT);
				lrtt.setCode("");
				lrtt.setType(AmountDeterminationType.EXCLUSIVE);
				ltaxlist.add(lrtt);
				ltax.setAmount(LintasConstantFactory.TAX_AMOUNT);
				ltax.setTaxes(ltaxlist);
				TaxesType ltaxmarkup = ltax;
				ltot.setTaxes(ltax);	

				//Single day base
				TotalType ltotperday = new TotalType();
				ltotperday.setAmountBeforeTax(roomrate.getFitNormalWd());
				ltotperday.setAmountAfterTax(amountaftertax);
				TaxesType ltaxperday = new TaxesType();			
				List<TaxType> ltaxlistperday  = new ArrayList<TaxType>();
				TaxType lrttperday = new TaxType();
				lrttperday.setAmount(LintasConstantFactory.TAX_AMOUNT);
				lrttperday.setCode("");
				lrttperday.setType(AmountDeterminationType.EXCLUSIVE);
				ltaxlistperday.add(lrttperday);
				ltaxperday.setAmount(LintasConstantFactory.TAX_AMOUNT);
				ltaxperday.setTaxes(ltaxlistperday);				
				ltotperday.setTaxes(ltaxperday);

				ltotperday.setTotalAmountPayable(amountaftertax.multiply(new BigDecimal(noofdays)));				

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

				logger.info("\\\\\\\\*************************roomdetail -lrate.getBase()-"+lrate.getBase());	
				logger.info("\\\\\\\\*************************roomdetail -lrate.getBaseWithoutMarkUp()-"+lrate.getBaseWithoutMarkUp());	
				logger.info("\\\\\\\\*************************roomdetail -lrate.getApiPrice()-"+lrate.getApiPrice());	

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
		troomtypes.setRoomTypes(lrtlist);	
		troomstay.setRoomTypes(troomtypes);
		trateplans.setRatePlen(lplantlist);	
		troomstay.setRatePlans(trateplans);
		troomrates.setRoomRates(lrratelist);	
		troomstay.setRoomRates(troomrates);

		troomtypes.setRoomTypes(lrtlist);	
		troomstay.setRoomTypes(troomtypes);
		trateplans.setRatePlen(lplantlist);	
		troomstay.setRatePlans(trateplans);
		troomrates.setRoomRates(lrratelist);
		troomstay.setRoomRates(troomrates);
		return troomstay;
	}

	public APIHotelBook convertLintastoNativePreBookResponse(APIHotelBook apiHotelBook) throws HibernateException 
	{	
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebooking ..." );
		if(apiHotelBook.getPreBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getPreBookRes();
			status = totaHotelResRS.getStatus();
		}		
		totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
		totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
		HotelReservationsType hotelReservationsType = new HotelReservationsType();
		List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
		HotelReservation thotelReservation = new HotelReservation();
		List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
		UniqueIDType uniqueIDType = new UniqueIDType();
		uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
		uniqueIDType.setID("");
		uniqueIDType.setApiBookingId("");
		uniqueIDType.setApiBookingCode("");
		uniqueIDType.setApiConfirmationNo("");
		
		
		//uniqueIDType.setCompanyName(uniqueid.getCompanyName());
		uniqueIDTypeList.add(uniqueIDType);
		thotelReservation.setUniqueIDs(uniqueIDTypeList);
		ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
		HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
		List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();

		RoomStayType.RoomRates.RoomRate roomrate = apiHotelBook.getRoomsummary().getRoomRates().getRoomRates().get(0);
		for (Rate rate : roomrate.getRates().getRates()) {
			HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
			thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
			thotelresid.setResIDValue("");//XHUB891565bookingId
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

		status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, "Success" );			
		totaHotelResRS.setApiFinalPrice(apiHotelBook.getApiRate().getPayableAmt());
		totaHotelResRS.setBaseFinalPrice(apiHotelBook.getBaseRate().getPayableAmt());
		totaHotelResRS.setBaseFinalPriceWithoutMarkup(apiHotelBook.getBaseRateWithoutMarkUp().getPayableAmt());
		totaHotelResRS.setBookingFinalPrice(apiHotelBook.getBookingRate().getPayableAmt());		
		
		
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setPreBookRes(totaHotelResRS);
		return apiHotelBook;
	}
	

	public APIHotelBook convertLintastoNativeFinalBookResponse(APIHotelBook apiHotelBook) throws HibernateException 
	{		
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Booking ..." );
		String bookingId = HotelIdFactoryImpl.getInstance().createLongId(LintasConstantFactory.BOOKING_ID_PREFIX);
		if(apiHotelBook.getBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getBookRes();
			status = totaHotelResRS.getStatus();
		}				
		totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
		totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
		HotelReservationsType hotelReservationsType = new HotelReservationsType();
		List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
		HotelReservation thotelReservation = new HotelReservation();
		List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
		UniqueIDType uniqueIDType = new UniqueIDType();
		uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
		uniqueIDType.setID(bookingId);
		uniqueIDType.setApiBookingId(bookingId);
		uniqueIDType.setApiBookingCode(bookingId);
		uniqueIDType.setApiConfirmationNo(bookingId);	
		uniqueIDTypeList.add(uniqueIDType);
		thotelReservation.setUniqueIDs(uniqueIDTypeList);
		ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
		HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
		List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();

		RoomStayType.RoomRates.RoomRate roomrate = apiHotelBook.getRoomsummary().getRoomRates().getRoomRates().get(0);
		for (Rate rate : roomrate.getRates().getRates()) {
			HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
			thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
			thotelresid.setResIDValue(bookingId);//XHUB891565bookingId
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

		status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, "Success" );			
		totaHotelResRS.setApiFinalPrice(apiHotelBook.getApiRate().getPayableAmt());
		totaHotelResRS.setBaseFinalPrice(apiHotelBook.getBaseRate().getPayableAmt());
		totaHotelResRS.setBaseFinalPriceWithoutMarkup(apiHotelBook.getBaseRateWithoutMarkUp().getPayableAmt());
		totaHotelResRS.setBookingFinalPrice(apiHotelBook.getBookingRate().getPayableAmt());		
		
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setBookRes(totaHotelResRS);
		return apiHotelBook;
	}	
}