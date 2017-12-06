package com.tayyarah.hotel.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.api.hotel.reznext.model.FeatureDescriptionType;
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
import com.tayyarah.hotel.model.ErrorType;
import com.tayyarah.hotel.model.ErrorsType;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelBasicInformation;
import com.tayyarah.hotel.model.HotelOverview;
import com.tayyarah.hotel.model.HotelReservationIDsType;
import com.tayyarah.hotel.model.HotelReservationsType;
import com.tayyarah.hotel.model.HotelReservationsType.HotelReservation;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.PenaltyDescription;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.ResGlobalInfoType;
import com.tayyarah.hotel.model.RoomBookingKeyMap;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.RoomStayType.RatePlans;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomTypes;
import com.tayyarah.hotel.model.RoomTypeType;
import com.tayyarah.hotel.model.RoomTypeType.Occupancy;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.model.TaxSummaryType;
import com.tayyarah.hotel.model.TaxType;
import com.tayyarah.hotel.model.TaxesType;
import com.tayyarah.hotel.model.TimeUnitType;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.model.UniqueIDType;


public class RezNextResponseParser {
	static final Logger logger = Logger.getLogger(RezNextResponseParser.class);
	@Autowired
	HotelIdFactoryImpl hotelIdFactory;	

	public RezNextResponseParser() {
		// TODO Auto-generated constructor stub
	}

	public  RoomStay convertRezNexttoNative(com.tayyarah.api.hotel.reznext.model.RoomStayType rs, HotelOverview ho, List<Facility> hotelfacilitieslist, List<Facility> hotelRoomfacilitieslist, List<String> hotelimages, Map<Integer, Hotelroomdescription> hotelroomsmap) throws HibernateException, ClassNotFoundException, JAXBException 
	{	
		RoomStay troomstay = new RoomStay();
		BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
		if(rs.getBasicPropertyInfo() != null)
		{
			tbasic.setApiVendorID(String.valueOf(rs.getBasicPropertyInfo().getHotelCode()));
			tbasic.setHotelCode(String.valueOf(ho.getHoteId().intValue()));
			tbasic.setHotelName(rs.getBasicPropertyInfo().getHotelName());			

			Position hpos = new Position();
			if(rs.getBasicPropertyInfo().getPosition()!=null)
			{
				hpos.setLongitude(String.valueOf(rs.getBasicPropertyInfo().getPosition().getLongitude()));
				hpos.setLatitude(String.valueOf(rs.getBasicPropertyInfo().getPosition().getLatitude()));				
				tbasic.setPosition(hpos);
			}
			tbasic.setPosition(hpos);
			AddressType laddress = new AddressType();				
			if(rs.getBasicPropertyInfo().getAddress()!=null)
			{
				laddress.setCityName(rs.getBasicPropertyInfo().getAddress().getCityName());
				CountryNameType cn = new CountryNameType();
				cn.setCode(rs.getBasicPropertyInfo().getAddress().getCountryName());
				cn.setValue(rs.getBasicPropertyInfo().getAddress().getCountryName());
				laddress.setCountryName(cn);
				List<String> addresslines = new ArrayList<String>();
				addresslines.add(rs.getBasicPropertyInfo().getAddress().getAddressLine());
				addresslines.add(rs.getBasicPropertyInfo().getAddress().getAddressLine1());
				addresslines.add(rs.getBasicPropertyInfo().getAddress().getAddressLine2());
				laddress.setAddressLines(addresslines);
			}
			tbasic.setAddress(laddress);	
			List<ContactNumber> contactnos = new ArrayList<ContactNumber>();
			contactnos.add(new ContactNumber("80425555555"));
			contactnos.add(new ContactNumber(rs.getBasicPropertyInfo().getContactNumber()));
			tbasic.setContactNumbers(contactnos);
			if(rs.getBasicPropertyInfo().getAward()!=null)
			{
				tbasic.setReviewCount(String.valueOf(rs.getBasicPropertyInfo().getAwardRating()));
				tbasic.setReviewRating(String.valueOf(rs.getBasicPropertyInfo().getAward().getRating()));
			}				
			tbasic.setApiPrice(rs.getBasicPropertyInfo().getBaseRate());
			tbasic.setBasePrice(rs.getBasicPropertyInfo().getBaseRate());
			tbasic.setBasePriceWithoutMarkup(rs.getBasicPropertyInfo().getBaseRate());
			tbasic.setBookingPrice(rs.getBasicPropertyInfo().getBaseRate());			
			tbasic.setApiProvider(HotelApiCredentials.API_REZNEXT_IND);
			tbasic.setIsOfflineBooking(false);
		}		
		if(rs.getBasicPropertyInfo().getHotelImages() != null && rs.getBasicPropertyInfo().getHotelImages().getImagePaths() != null && rs.getBasicPropertyInfo().getHotelImages().getImagePaths().size()>0)
		{
			tbasic.setHotelimages(rs.getBasicPropertyInfo().getHotelImages().getImagePaths());
			tbasic.setImageurl(rs.getBasicPropertyInfo().getHotelImages().getImagePaths().get(0));
		}
		List<Facility> facilities = new ArrayList<Facility>();
		if(rs.getBasicPropertyInfo().getHotelFeatures() != null && rs.getBasicPropertyInfo().getHotelFeatures().getFeatureDescriptions() != null && rs.getBasicPropertyInfo().getHotelFeatures().getFeatureDescriptions().size()>0)
		{
			for (FeatureDescriptionType facility : rs.getBasicPropertyInfo().getHotelFeatures().getFeatureDescriptions()) {
				Facility f = new Facility();
				if(facility != null)
				{
					f.setDescription(facility.getDescription());
					if(facility.getCode() != null)
						f.setId((int)facility.getCode());
					f.setAmenityType(facility.getValue());
				}
				facilities.add(f);
			}	
		}			
		tbasic.setHotelAmenities(facilities);				
		if(ho != null)
		{
			tbasic.setHotelName(ho.getVendorName());
			tbasic.setHotelCode(String.valueOf(ho.getHoteId().intValue()));
		}		
		tbasic.setHotelimages(hotelimages);
		tbasic.setHotelAmenities(hotelfacilitieslist);	

		TPAExtensions roomstayextension = new TPAExtensions();			
		/*<TPA_Extensions StopSell="false" LowestRatePlanId="0000070986">
		-<HotelBasicInformation Rank="4" HotelType="TGU">
		<Reviews ReviewRating="5.0" ReviewCount="5"/>
		</HotelBasicInformation>
		<DeepLinkInformation overviewURL="http://cert1.travelguru.com/hotels/India/bangalore/adarsh-hamilton-00005472?checkInDate=29-07-2015&checkOutDate=30-07-2015&numRooms=1&rooms[0].adult=1&rooms[0].children=0&currencyCode=INR"/>
		</TPA_Extensions>*/
		HotelBasicInformation roomstayhotelbasic = new HotelBasicInformation();
		troomstay.setTPAExtensions(roomstayextension);
		troomstay.setBasicPropertyInfo(tbasic);	
		return troomstay;
	}	

	public com.tayyarah.api.hotel.reznext.model.RoomRateType getReznextRoomRate(com.tayyarah.api.hotel.reznext.model.RoomStayType rs,com.tayyarah.api.hotel.reznext.model.RatePlanType pt)
	{		
		com.tayyarah.api.hotel.reznext.model.RoomRateType troomrate = null;	
		if(rs.getRoomRates() != null && rs.getRoomRates().getRoomRates()!=null && !rs.getRoomRates().getRoomRates().isEmpty())
			for (com.tayyarah.api.hotel.reznext.model.RoomRateType roomrate : rs.getRoomRates().getRoomRates()) {
				if(roomrate.getRatePlanCode().equals(pt.getRatePlanCode()))
				{
					troomrate = roomrate;
					break;
				}

			}
		return troomrate;		

	}

	public RoomStay convertReznettoNativeRoomDetail(HotelSearchCommand hsc, OTAHotelAvailRS.RoomStays.RoomStay troomstay, com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS otaHotelAvailResRezt) throws HibernateException, ClassNotFoundException, JAXBException, ParseException 
	{	
		int noofdays = CommonUtil.getNoofStayDays(hsc);

		if(otaHotelAvailResRezt == null || otaHotelAvailResRezt.getRoomStays() == null || otaHotelAvailResRezt.getRoomStays().getRoomStaies().isEmpty())
			return troomstay;		
		com.tayyarah.api.hotel.reznext.model.RoomStayType rs = otaHotelAvailResRezt.getRoomStays().getRoomStaies().get(0);		
		RoomTypes troomtypes = new RoomTypes();
		List<RoomTypeType> lrtlist = new ArrayList<RoomTypeType>();
		for (com.tayyarah.api.hotel.reznext.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			RoomTypeType lrt = new RoomTypeType();
			com.tayyarah.api.hotel.reznext.model.RoomDescriptionType roomdes = rt.getRoomDescription();
			Hotelroomdescription lroomdes = new Hotelroomdescription();
			lrt.setRoomType(roomdes.getText());
			lrt.setRoomTypeCode(rt.getRoomTypeCode());
			lroomdes.setId(1);
			lroomdes.setImagePath(roomdes.getImagePath());			
			lrt.setMaxChildAge(rt.getMaxChildAge());
			lrt.setMinChildAge(rt.getMinChildAge());
			lrt.setMaxPersons(rt.getMaxPersons());			
			lrt.setRoomType(roomdes.getText());
			lrt.setRoomDescription(lroomdes);
			List<Facility> facilitiesRoom = new ArrayList<Facility>();
			if(rt.getRoomAmenities() !=null && rt.getRoomAmenities().getAmenityDescriptions()!= null && !rt.getRoomAmenities().getAmenityDescriptions().isEmpty())
				for (com.tayyarah.api.hotel.reznext.model.AmenityDescriptionType amenity : rt.getRoomAmenities().getAmenityDescriptions()) {
					if(amenity != null && amenity.getCode() != null)
					{
						Facility f = new Facility();
						f.setId(amenity.getCode().intValue());
						f.setAmenityType(amenity.getValue());
						f.setDescription(amenity.getDescription());
						facilitiesRoom.add(f);
					}
				}		
			lrt.setAmenities(facilitiesRoom);
			List<Occupancy> locclist = new ArrayList<RoomTypeType.Occupancy>();
			Occupancy locc = new Occupancy();
			locc.setAgeQualifyingCode("");
			locc.setMaxOccupancy(rt.getMaxPersons());
			locc.setAgeBucket("");			
			locc.setMaxAge(rt.getMaxChildAge());
			locc.setMinAge(rt.getMinChildAge());
			locc.setMinOccupancy(1);
			locclist.add(locc);
			lrt.setOccupancies(locclist);	
			lrtlist.add(lrt);
		}
		troomtypes.setRoomTypes(lrtlist);		
		troomstay.setRoomTypes(troomtypes);

		/*<RatePlans>
        <RatePlan RatePlanCode="BO1" RatePlanName="BAR" RatePlanType="BAR">
            <RatePlanDescription Text="BAR"/>
        </RatePlan>
    </RatePlans>*/

		RatePlans trateplans = new RatePlans();
		List<RatePlanType> lplantlist = new ArrayList<RatePlanType>();
		if(rs.getRatePlans() != null && rs.getRatePlans().getRatePlen()!=null && !rs.getRatePlans().getRatePlen().isEmpty())
			for (com.tayyarah.api.hotel.reznext.model.RatePlanType rp : rs.getRatePlans().getRatePlen()) {
				/* <RatePlan RatePlanCode="BO1" RatePlanName="BAR" RatePlanType="BAR">
            <RatePlanDescription Text="BAR"/>
        </RatePlan>*/
				RatePlanType lrt = new RatePlanType();
				lrt.setRatePlanType(rp.getRatePlanType());
				lrt.setRatePlanCode(rp.getRatePlanCode());
				lrt.setRatePlanName(rp.getRatePlanName());
				CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();
				com.tayyarah.api.hotel.reznext.model.RoomRateType roomrate = getReznextRoomRate(rs, rp);
				List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
				if(roomrate != null)
				{
					for (com.tayyarah.api.hotel.reznext.model.RatesType ratestype : roomrate.getRates()) {

						if(ratestype.getInclusion() != null && ratestype.getInclusion().getTPAExtensions() != null && ratestype.getInclusion().getTPAExtensions().getCancelPenalties() != null)
						{
							com.tayyarah.api.hotel.reznext.model.CancelPenaltiesType cancellationpenalities = ratestype.getInclusion().getTPAExtensions().getCancelPenalties();
							for (com.tayyarah.api.hotel.reznext.model.CancelPenaltyType cpt : cancellationpenalities.getCancelPenalties()) {
								CancelPenaltyType lcpt = new CancelPenaltyType();
								Deadline ldeadline = new Deadline();	
								if(cpt.getDeadline() != null)
								{
									ldeadline.setOffsetTimeUnit(TimeUnitType.fromValue(cpt.getDeadline().getOffsetTimeUnit()));
									ldeadline.setOffsetDropTime(cpt.getDeadline().getOffsetDropTime());
									int unitmultiplier = cpt.getDeadline().getOffsetUnitMultiplier().intValue();
									ldeadline.setOffsetUnitMultiplier(unitmultiplier);
									lcpt.setDeadline(ldeadline);
								}
								List<PenaltyDescription> lpenlist = new ArrayList<PenaltyDescription>();
								PenaltyDescription lpdes = new PenaltyDescription();
								lpdes.setDrescription(cpt.getCancelDescription());
								lpenlist.add(lpdes);
								cancellist.add(lcpt);
							}
						}
					}
				}
				cancelpenalities.setCancelPenalties(cancellist);
				lrt.setCancelPenalties(cancelpenalities);			
				lplantlist.add(lrt);	 
			}
		trateplans.setRatePlen(lplantlist);
		troomstay.setRatePlans(trateplans);
		RoomRates troomrates = new RoomRates();
		List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();
		for (com.tayyarah.api.hotel.reznext.model.RoomRateType rr : rs.getRoomRates().getRoomRates()) {
			for (com.tayyarah.api.hotel.reznext.model.RatesType ratestype : rr.getRates()) {
				if(ratestype.getInclusion() != null )
				{
					if(ratestype.getInclusion().getRate() != null)
					{
						com.tayyarah.api.hotel.reznext.model.RateType rtype =  ratestype.getInclusion().getRate();
						RoomStayType.RoomRates.RoomRate lrr = new RoomStayType.RoomRates.RoomRate();
						RoomBookingKeyMap roomBookingKeyMap = HotelIdFactoryImpl.getInstance().createRoomRateBookingKey(ratestype.getRoomTypeCode(), rr.getRatePlanCode());			
						lrr.setBookingCode(roomBookingKeyMap.getBookingKey());
						lrr.setRatePlanCode(rr.getRatePlanCode());
						lrr.setRoomID(ratestype.getRoomTypeCode());
						lrr.setRoomTypeCode(ratestype.getRoomTypeCode());
						RateType lrates = new RateType();
						List<Rate> lratelist = new ArrayList<Rate>();
						Rate lrate = new Rate();
						TotalType ltotsingle = new TotalType();
						BigDecimal amountBeforeTax = rtype.getBase().getSingleRate();
						BigDecimal amountAfterTax = rtype.getBase().getSingleRate();
						ltotsingle.setAmountBeforeTax(rtype.getBase().getSingleRate());
						BigDecimal taxAmount = new BigDecimal(0);
						if(ratestype.getInclusion().getTaxes() != null && ratestype.getInclusion().getTaxes().getTax() != null)
						{
							TaxesType ltaxsingle = new TaxesType();
							com.tayyarah.api.hotel.reznext.model.TaxType taxsingle = ratestype.getInclusion().getTaxes().getTax();
							List<TaxType> ltaxlistsingle  = new ArrayList<TaxType>();
							TaxType lrttsingle = new TaxType();					
							taxAmount = (taxsingle.getTaxAmount()==null)?new BigDecimal(0):taxsingle.getTaxAmount();
							lrttsingle.setAmount(taxAmount);
							lrttsingle.setCode(taxsingle.getTaxCode());
							lrttsingle.setType(AmountDeterminationType.EXCLUSIVE);
							ltaxlistsingle.add(lrttsingle);
							ltaxsingle.setAmount(taxsingle.getTaxAmount());
							ltaxsingle.setTaxes(ltaxlistsingle);
							TaxesType ltaxmarkup = ltaxsingle;
							ltotsingle.setTaxes(ltaxsingle);						
						}
						amountAfterTax = amountBeforeTax.add(taxAmount);
						ltotsingle.setAmountAfterTax(amountAfterTax);
						BigDecimal totalapipayable = new BigDecimal("0");
						try{
							totalapipayable = amountAfterTax.multiply(new BigDecimal(noofdays));								
							logger.info("object transformation---: rezlive to common. -----total payable day price "+totalapipayable);	
						}
						catch(ArithmeticException ex)
						{
							totalapipayable = amountAfterTax.multiply(new BigDecimal(noofdays));
							logger.info("object transformation---: rezlive to common. -----total payable day price "+totalapipayable);				
						}
						ltotsingle.setTotalAmountPayable(totalapipayable);
						TotalType ltotsinglebase = new TotalType();
						TotalType ltotsinglebasewithoutmarkup = new TotalType();
						TotalType ltotsinglebooking = new TotalType();				
						BeanUtils.copyProperties(ltotsingle, ltotsinglebase);
						BeanUtils.copyProperties(ltotsingle, ltotsinglebasewithoutmarkup);
						BeanUtils.copyProperties(ltotsingle, ltotsinglebooking);				
						lrate.setApiPrice(ltotsingle);
						lrate.setBase(ltotsinglebase);			
						lrate.setBaseWithoutMarkUp(ltotsinglebasewithoutmarkup);
						lrate.setBookingPrice(ltotsinglebooking);
						if(ratestype.getInclusion() != null && ratestype.getInclusion().getTPAExtensions() != null && ratestype.getInclusion().getTPAExtensions().getTotalRates() != null)
						{
							com.tayyarah.api.hotel.reznext.model.TotalRatesType totalrate = ratestype.getInclusion().getTPAExtensions().getTotalRates();
							/*<TotalRates SingleRate="3960.00*1" DoubleRate="3960.00*0" ExtraBed="800.00*0" ExtraChild="0.00*0" 
							 * NoOfNights="1" BasicAmount="3960" NetAmount="4952"/>
							 */ 
							TPAExtensions rateplanext = new TPAExtensions();
							TPAExtensions.Rate lrateext = new TPAExtensions.Rate();
							TotalType ltotext = new TotalType(); 							
							lrateext.setBase(ltotext);
							rateplanext.setRate(lrateext);
							lrate.setTPAExtensions(rateplanext);
						}	
						lratelist.add(lrate);
						lrates.setRates(lratelist);
						lrr.setRates(lrates);
						lrratelist.add(lrr);
					}
				}
			}
		}
		troomrates.setRoomRates(lrratelist);
		troomstay.setRoomRates(troomrates);
		BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
		com.tayyarah.api.hotel.reznext.model.BasicPropertyInfoType ho = rs.getBasicPropertyInfo();
		tbasic.setHotelCode(String.valueOf(ho.getHotelCode()));
		if(ho != null)
		{
			String hotelimageurl = "";
			List<String> hotelimages = new ArrayList<String>();
			if(ho.getHotelImages() != null && !ho.getHotelImages().getImagePaths().isEmpty())
				for (String imageurl : ho.getHotelImages().getImagePaths()) {
					hotelimages.add(imageurl);
					hotelimageurl = imageurl;
				}
			tbasic.setHotelName(ho.getHotelName());			
			tbasic.setImageurl(hotelimageurl);
			Position hpos = new Position();
			if(ho.getPosition() != null)
			{
				hpos.setLongitude(String.valueOf(ho.getPosition().getLongitude()));
				hpos.setLatitude(String.valueOf(ho.getPosition().getLatitude()));	
			}
			tbasic.setPosition(hpos);
			AddressType laddress = new AddressType();
			if(ho.getAddress() != null)
			{					
				laddress.setCityName(ho.getAddress().getCityName());
				CountryNameType cn = new CountryNameType();
				cn.setCode(ho.getAddress().getCountryName());
				cn.setValue(ho.getAddress().getCountryName());
				laddress.setCountryName(cn);
				List<String> addresslines = new ArrayList<String>();
				addresslines.add(ho.getAddress().getAddressLine());
				addresslines.add(ho.getAddress().getAddressLine1());
				addresslines.add(ho.getAddress().getAddressLine2());
				laddress.setAddressLines(addresslines);
				tbasic.setArea(ho.getAddress().getArea());
				tbasic.setArea_Seo_Id("Not Available");
			}
			List<ContactNumber> contactnos = new ArrayList<ContactNumber>();
			contactnos.add(new ContactNumber("80425555555"));
			tbasic.setAddress(laddress);
			tbasic.setContactNumbers(contactnos);
			tbasic.setReviewCount("Not Available");
			tbasic.setReviewRating(String.valueOf(ho.getAwardRating()));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat ("HH:mm");			
			java.sql.Time checkin = java.sql.Time.valueOf("00:00:00");
			java.sql.Time checkout = java.sql.Time.valueOf("00:00:00");
			try
			{
				checkin = java.sql.Time.valueOf(ho.getCheckIn());
				checkout = java.sql.Time.valueOf(ho.getCheckOut());
			}
			catch(Exception e)
			{
			}		
			tbasic.setDefaultCheckInTime(checkin);
			tbasic.setDefaultCheckOutTime(checkout);
			tbasic.setHotel_Star(1);//reznext is not providing
			tbasic.setHotelClass("");//reznext is not providing
			tbasic.setWeekdayRank(4);//reznext is not providing
			tbasic.setWeekendRank(4);//reznext is not providing
			tbasic.setApiPrice(ho.getBaseRate());
			tbasic.setBasePrice(ho.getBaseRate());
			tbasic.setBasePriceWithoutMarkup(ho.getBaseRate());
			tbasic.setBookingPrice(ho.getBaseRate());	


			tbasic.setApiProvider(HotelApiCredentials.API_REZNEXT_IND);	
			tbasic.setIsOfflineBooking(false);
			TaxSummaryType taxsummary  = new TaxSummaryType();			
			List<TaxType> taxes = new ArrayList<TaxType>();
			if(ho.getTaxSummary() !=null && ho.getTaxSummary().getTaxes()!= null && !ho.getTaxSummary().getTaxes().isEmpty())
				for (com.tayyarah.api.hotel.reznext.model.TaxType tax : ho.getTaxSummary().getTaxes()) {
					if(tax != null && tax.getTaxCode() != null)
					{
						/*<Tax TaxCode="LTX" TaxDescription="Luxury Tax" TaxType=" Tax on Charge Rate" TaxPercent="12.00" />*/
						TaxType t = new TaxType();
						t.setAmount(tax.getTaxAmount());
						t.setPercent(tax.getTaxPercent());
						t.setCode(tax.getTaxCode());						
						t.setTaxDescription(tax.getTaxDescription());
						t.setType(AmountDeterminationType.TAX_ON_CHARGE);						
						taxes.add(t);
					}
				}
			taxsummary.setTaxes(taxes);
			tbasic.setTaxSummary(taxsummary);
			List<Facility> facilitiesRoom = new ArrayList<Facility>();
			if(ho.getHotelFeatures() !=null && ho.getHotelFeatures().getFeatureDescriptions()!= null && !ho.getHotelFeatures().getFeatureDescriptions().isEmpty())
				for (FeatureDescriptionType amenity : ho.getHotelFeatures().getFeatureDescriptions()) {
					if(amenity != null && amenity.getCode() != null)
					{
						Facility f = new Facility();
						f.setId(amenity.getCode().intValue());
						f.setAmenityType(amenity.getValue());
						f.setDescription(amenity.getDescription());
						facilitiesRoom.add(f);
					}
				}		
			tbasic.setHotelAmenities(facilitiesRoom);
		}
		return troomstay;
	}

	public APIHotelBook convertReztoNativePreBookResponse(APIHotelBook apiHotelBook) throws HibernateException 
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

	public APIHotelBook convertReztoNativeFinalBookResponse(APIHotelBook apiHotelBook, com.tayyarah.api.hotel.reznext.model.OTAHotelResNotifRS rs) throws HibernateException 
	{		
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Booking ..." );
		if(apiHotelBook.getBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getBookRes();
			status = totaHotelResRS.getStatus();
		}				
		if(rs == null)
		{
			ErrorsType errors = new ErrorsType();
			List<ErrorType> errorlist = new ArrayList<ErrorType>();
			ErrorType error = new ErrorType();
			error.setCode(ErrorType.CODE_API_ISSUE);
			error.setStatus(ErrorType.STATUS_API_ISSUE);
			errorlist.add(error);
			errors.setErrors(errorlist);
			totaHotelResRS.setErrors(errors);
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "No reservation response from server" );			
		}
		else
		{
			totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
			totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
			//check for error..
			if(rs.getErrors() != null && rs.getErrors().getErrors() != null && !rs.getErrors().getErrors().isEmpty())
			{
				ErrorsType errors = new ErrorsType();
				List<ErrorType> errorlist = new ArrayList<ErrorType>();
				for (com.tayyarah.api.hotel.reznext.model.ErrorType error : rs.getErrors().getErrors()) {
					ErrorType terror = new ErrorType();
					terror.setCode(error.getCode());
					terror.setStatus(error.getStatus());
					terror.setShortText(error.getShortText());
					terror.setDocURL(error.getDocURL());					
					terror.setNodeList(error.getNodeList());
					terror.setTag(error.getTag());
					terror.setType(error.getType());					
					errorlist.add(terror);
				}
				errors.setErrors(errorlist);
				totaHotelResRS.setErrors(errors);
				status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "No reservation response from server, check error messages" );
				totaHotelResRS.setStatus(status);				
			}
			HotelReservationsType hotelReservationsType = new HotelReservationsType();
			List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
			boolean isUniqueIdFound = false;
			if(rs.getHotelReservations() != null && rs.getHotelReservations().getHotelReservation() != null)					
			{
				com.tayyarah.api.hotel.reznext.model.HotelReservationType hotelReservation = rs.getHotelReservations().getHotelReservation();
				HotelReservation thotelReservation = new HotelReservation();
				List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
				if(hotelReservation.getUniqueID() != null)
				{
					com.tayyarah.api.hotel.reznext.model.UniqueIDType uniqueid = hotelReservation.getUniqueID();
					UniqueIDType uniqueIDType = new UniqueIDType();
					uniqueIDType.setType(uniqueid.getType());
					uniqueIDType.setID(uniqueid.getID());
					uniqueIDType.setApiBookingId(uniqueid.getID());
					uniqueIDType.setApiBookingCode(uniqueid.getID());
					uniqueIDType.setApiConfirmationNo(uniqueid.getID());					
					uniqueIDTypeList.add(uniqueIDType);
					thotelReservation.setUniqueIDs(uniqueIDTypeList);
					isUniqueIdFound = true;
				}
				/*	<ResGlobalInfo>
                <HotelReservationIDs>
                    <HotelReservationID ResID_Type="14" ResID_Value="133" ResID_Source="Prakriti" ForGuest="true"/>
                    <HotelReservationID ResID_Type="10" ResID_Value="2" ResID_Source="PMS" ForGuest="true"/>
                </HotelReservationIDs>
                <RefundAmount>0</RefundAmount>
            </ResGlobalInfo>*/

				if(hotelReservation.getResGlobalInfo() != null && hotelReservation.getResGlobalInfo().getHotelReservationIDs() != null && !hotelReservation.getResGlobalInfo().getHotelReservationIDs().getHotelReservationIDs().isEmpty())
				{
					ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
					HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
					List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();
					for (com.tayyarah.api.hotel.reznext.model.HotelReservationIDType uniqueIDType : hotelReservation.getResGlobalInfo().getHotelReservationIDs().getHotelReservationIDs()) {
						HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
						thotelresid.setResIDType(uniqueIDType.getResIDType());
						thotelresid.setResIDValue(uniqueIDType.getResIDValue());
						thotelresid.setResIDSourceContext(uniqueIDType.getResIDSource());
						thotelresid.setForGuest(uniqueIDType.getForGuest());
						thotelresidlist.add(thotelresid);
					}
					thotelresIds.setHotelReservationIDs(thotelresidlist);
					tresGlobalinfo.setHotelReservationIDs(thotelresIds);
					thotelReservation.setResGlobalInfo(tresGlobalinfo);
				}	
				hotelReservationslist.add(thotelReservation);
				hotelReservationsType.setHotelReservations(hotelReservationslist);
				totaHotelResRS.setHotelReservations(hotelReservationsType);				
				totaHotelResRS.setApiFinalPrice(apiHotelBook.getApiRate().getPayableAmt());
				totaHotelResRS.setBaseFinalPrice(apiHotelBook.getBaseRate().getPayableAmt());
				totaHotelResRS.setBaseFinalPriceWithoutMarkup(apiHotelBook.getBaseRateWithoutMarkUp().getPayableAmt());
				totaHotelResRS.setBookingFinalPrice(apiHotelBook.getBookingRate().getPayableAmt());	
			}
			if(isUniqueIdFound)
			{
				status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, "Success" );
			}
		}
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setBookRes(totaHotelResRS);
		return apiHotelBook;
	}
}