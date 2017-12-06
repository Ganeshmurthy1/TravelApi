package com.tayyarah.hotel.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.Islhotelmapping;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.AddressType;
import com.tayyarah.hotel.model.AmountPercentType;
import com.tayyarah.hotel.model.AmountType.Discount;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelBasicInformation;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelOverview;
import com.tayyarah.hotel.model.HotelReservationsType;
import com.tayyarah.hotel.model.HotelReservationsType.HotelReservation;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.HotelsInfo;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.PenaltyDescription;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.Reviews;
import com.tayyarah.hotel.model.RoomBookingKeyMap;
import com.tayyarah.hotel.model.RoomCombination;
import com.tayyarah.hotel.model.RoomCombinations;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts.GuestCount;
import com.tayyarah.hotel.model.RoomStayType.RoomTypes;
import com.tayyarah.hotel.model.RoomStaysType;
import com.tayyarah.hotel.model.RoomTypeType;
import com.tayyarah.hotel.model.RoomTypeType.Occupancy;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.model.TPAExtensions.DeepLinkInformation;
import com.tayyarah.hotel.model.TPAExtensions.DiscountCouponDisplayIndicator;
import com.tayyarah.hotel.model.TPAExtensions.Promotion;
import com.tayyarah.hotel.model.TPAExtensions.RoomType;
import com.tayyarah.hotel.model.TaxType;
import com.tayyarah.hotel.model.TaxesType;
import com.tayyarah.hotel.model.TimeUnitType;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.model.RoomStayType.RatePlans;
import com.tayyarah.hotel.model.BasicPropertyInfoType.ContactNumbers.ContactNumber;
import com.tayyarah.hotel.model.BasicPropertyInfoType.Position;
import com.tayyarah.hotel.model.CancelPenaltiesType;
import com.tayyarah.hotel.model.CancelPenaltyType;
import com.tayyarah.hotel.model.CountryNameType;
import com.tayyarah.hotel.model.CancelPenaltyType.Deadline;

public class HotelObjectTransformer {
	static final Logger logger = Logger.getLogger(HotelObjectTransformer.class);

	@Autowired
	HoteloverviewDao hoteldao;	
	@Autowired
	HotelroomdescriptionDao hotelroomdescriptionDao;	
	@Autowired
	HotelimagesDao hotelimagesDao;
	@Autowired
	HotelFacilityDao hotelFacilityDao;
	@Autowired
	IslhotelmappingDao islhotelmappingDao;
	@Autowired
	HotelinandaroundDao hotelinandaroundDao;
	@Autowired
	HotelsecondaryareaDao hotelsecondaryareaDao;
	@Autowired
	HotelIdFactoryImpl hotelIdFactory;		


	public static int trimSringtoInt(String src)
	{
		src.replaceFirst("^0+(?!$)", "");
		return Integer.valueOf(src);
	}

	public static <T> void copyFields(T target, T source) throws Exception{
		Class<?> clazz = source.getClass();
		for (Field field : clazz.getFields()) {
			Object value = field.get(source);
			field.set(target, value);
		}
	}

	public RoomStay getTGRoomDetails(RoomStay rs) throws HibernateException 
	{		
		List<String> roomTypeCodeList = new ArrayList<String>();
		List<String> rateplancodes = new ArrayList<String>();
		RoomRates troomrates = new RoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();

		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;	

			roomTypeCodeList.add(rr.getRoomID());
			rateplancodes.add(rr.getRatePlanCode());			
			lrratelist.add(lrr);
		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		RoomTypes troomtypes = rs.getRoomTypes();
		List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		for (com.tayyarah.hotel.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			com.tayyarah.hotel.model.RoomTypeType lrt = rt;
			if(roomTypeCodeList.contains(rt.getRoomTypeCode()))
			{
				Hotelroomdescription lroomdes = hotelroomdescriptionDao.getHotelByRoomType(trimSringtoInt(rt.getRoomTypeCode()));
				if(lroomdes != null)
					lrt.setRoomDescription(lroomdes);

				List<Facility> facilitiesRoom = hotelFacilityDao.getDescriptionByVendorProperty(rs.getBasicPropertyInfo().getHotelCode(), "room");
				if(facilitiesRoom != null)
					lrt.setAmenities(facilitiesRoom);
				lrtlist.add(lrt);
			}
		}
		troomtypes.setRoomTypes(lrtlist);
		rs.setRoomTypes(troomtypes);
		RatePlans trateplans = new RatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();		
		for (com.tayyarah.hotel.model.RatePlanType pt : rs.getRatePlans().getRatePlan()) {
			if(rateplancodes.contains(pt.getRatePlanCode()))
			{
				lplantlist.add(pt);
			}				
		}
		trateplans.setRatePlen(lplantlist);
		rs.setRatePlans(trateplans);
		return rs;
	}

	public boolean isSelectedRoomRate(com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate roomrate, List<HotelBookCommand.RoomRateType> roomRateTypeList)
	{
		boolean result = false;
		for (HotelBookCommand.RoomRateType roomRateType : roomRateTypeList) {		
			if(roomrate.getRatePlanCode().equalsIgnoreCase(roomRateType.getRatePlanCode()) && (roomrate.getRoomTypeCode().equalsIgnoreCase(roomRateType.getRoomTypeCode()) || roomrate.getRoomID().equalsIgnoreCase(roomRateType.getRoomTypeCode())))
			{
				result = true;
				break;
			}
		}
		return result;		
	}

	public List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> getRoomRatesBooking(RoomStay rs, List<HotelBookCommand.RoomRateType> roomRateTypeList) throws HibernateException 
	{	
		List<String> roomTypeCodeList = new ArrayList<String>();
		List<String> rateplancodes = new ArrayList<String>();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			if(isSelectedRoomRate(rr, roomRateTypeList))
			{
				if(lrratelist.size() < roomRateTypeList.size())
					lrratelist.add(rr);
				else if(lrratelist.size() == roomRateTypeList.size())
					break;
			}
		}
		return lrratelist;
	}

	public RoomStay getRoomStayBooking(RoomStay rs, List<HotelBookCommand.RoomRateType> roomRateTypeList) throws HibernateException 
	{		
		List<String> roomTypeCodeList = new ArrayList<String>();
		List<String> rateplancodes = new ArrayList<String>();
		RoomRates troomrates = new RoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			if(isSelectedRoomRate(rr, roomRateTypeList))
			{
				roomTypeCodeList.add(rr.getRoomID());
				rateplancodes.add(rr.getRatePlanCode());			
				lrratelist.add(lrr);
			}
		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		RoomTypes troomtypes = rs.getRoomTypes();
		List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		for (com.tayyarah.hotel.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			com.tayyarah.hotel.model.RoomTypeType lrt = rt;
			if(roomTypeCodeList.contains(rt.getRoomTypeCode()))
			{				
				lrtlist.add(lrt);
			}
		}
		troomtypes.setRoomTypes(lrtlist);
		rs.setRoomTypes(troomtypes);
		RatePlans trateplans = new RatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();
		for (com.tayyarah.hotel.model.RatePlanType pt : rs.getRatePlans().getRatePlan()) {
			if(rateplancodes.contains(pt.getRatePlanCode()))
			{
				lplantlist.add(pt);
			}				
		}
		trateplans.setRatePlen(lplantlist);
		rs.setRatePlans(trateplans);
		return rs;
	}

	public RoomStay getTGRoomDetailsSummary(RoomStay rs, List<String> roomTypeCodeList) throws HibernateException 
	{	
		RoomTypes troomtypes = rs.getRoomTypes();
		List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		for (com.tayyarah.hotel.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			com.tayyarah.hotel.model.RoomTypeType lrt = rt;
			if(roomTypeCodeList.contains(rt.getRoomTypeCode()))
			{
				Hotelroomdescription lroomdes = hotelroomdescriptionDao.getHotelByRoomType(trimSringtoInt(rt.getRoomTypeCode()));
				if(lroomdes != null)
					lrt.setRoomDescription(lroomdes);

				List<Facility> facilitiesRoom = hotelFacilityDao.getDescriptionByVendorProperty(rs.getBasicPropertyInfo().getHotelCode(), "room");
				if(facilitiesRoom != null)
					lrt.setAmenities(facilitiesRoom);
				lrtlist.add(lrt);
			}
		}
		troomtypes.setRoomTypes(lrtlist);
		rs.setRoomTypes(troomtypes);
		RoomRates troomrates = new RoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		List<String> rateplancodes = new ArrayList<String>();
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			if(roomTypeCodeList.contains(rr.getRoomID()))
			{
				rateplancodes.add(rr.getRatePlanCode());			
				lrratelist.add(lrr);
			}			
		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		RatePlans trateplans = new RatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();
		for (com.tayyarah.hotel.model.RatePlanType pt : rs.getRatePlans().getRatePlan()) {
			if(rateplancodes.contains(pt.getRatePlanCode()))
			{
				lplantlist.add(pt);
			}				
		}
		trateplans.setRatePlen(lplantlist);
		rs.setRatePlans(trateplans);
		return rs;
	}

	public RoomStay getRoomDetailsSummary(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs, String bookingKey) throws HibernateException, ClassNotFoundException, JAXBException 
	{		
		String roomTypeCode = "";
		String rateplancode = "";
		RoomRates troomrates = new RoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			if(lrr.getBookingCode().equalsIgnoreCase(bookingKey))
			{
				roomTypeCode = rr.getRoomID();
				rateplancode = rr.getRatePlanCode();			
				lrratelist.add(lrr);
				break;
			}
		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		RoomTypes troomtypes = rs.getRoomTypes();
		List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		for (com.tayyarah.hotel.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			com.tayyarah.hotel.model.RoomTypeType lrt = rt;

			if(roomTypeCode.equalsIgnoreCase(rt.getRoomTypeCode()))
			{				
				lrtlist.add(lrt);
				break;
			}
		}
		troomtypes.setRoomTypes(lrtlist);
		rs.setRoomTypes(troomtypes);
		RatePlans trateplans = new RatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();
		for (com.tayyarah.hotel.model.RatePlanType pt : rs.getRatePlans().getRatePlan()) {
			if(rateplancode.equalsIgnoreCase(pt.getRatePlanCode()))
			{
				lplantlist.add(pt);
				break;
			}				
		}
		trateplans.setRatePlen(lplantlist);
		rs.setRatePlans(trateplans);
		return rs;
	}

	public RoomStay getRoomDetailsSummary(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs, String[] bookingKeys) throws HibernateException, ClassNotFoundException, JAXBException 
	{		
		List<String> roomTypeCodes = new ArrayList<String>();
		List<String> rateplancodes = new ArrayList<String>();
		BasicPropertyInfoType basicPropertyInfoType = rs.getBasicPropertyInfo();
		RoomRates troomrates = new RoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		for (String bookingKey : bookingKeys) {
			for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
				RoomRate lrr = rr;
				if(lrr.getBookingCode().equalsIgnoreCase(bookingKey))
				{
					logger.info("##########room booked found bookingKey--"+bookingKey+ "-----rr.getRoomID()"+rr.getRoomID());	
					roomTypeCodes.add(rr.getRoomID());
					rateplancodes.add(rr.getRatePlanCode());			
					lrratelist.add(lrr);					
					basicPropertyInfoType.setApiProvider((lrr.getApiProvider() == null)?basicPropertyInfoType.getApiProvider():lrr.getApiProvider());
					break;
				}
			}

		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		RoomTypes troomtypes = rs.getRoomTypes();
		List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		HashMap<String, com.tayyarah.hotel.model.RoomTypeType> roomTypeMap = new HashMap<String, com.tayyarah.hotel.model.RoomTypeType>();
		for (com.tayyarah.hotel.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			com.tayyarah.hotel.model.RoomTypeType lrt = rt;
			if(roomTypeCodes.contains(rt.getRoomTypeCode()))
			{		
				roomTypeMap.put(rt.getRoomTypeCode(), lrt);
			}
		}
		for(int i = 0; i < roomTypeCodes.size(); i++){
			com.tayyarah.hotel.model.RoomTypeType roomType = roomTypeMap.get(roomTypeCodes.get(i));
			lrtlist.add(roomType);
		}
		troomtypes.setRoomTypes(lrtlist);
		rs.setRoomTypes(troomtypes);
		RatePlans trateplans = new RatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();
		switch (rs.getBasicPropertyInfo().getApiProvider()) {
		case HotelApiCredentials.API_TBO_INTERNATIONAL:	
			for (String bookingKey : bookingKeys) {
				for (com.tayyarah.hotel.model.RatePlanType pt : rs.getRatePlans().getRatePlan()) {
					if(bookingKey.equalsIgnoreCase(pt.getBookingCode()))
					{
						lplantlist.add(pt);	
						break;
					}				
				}
			}
			break;
		default:					
			for (com.tayyarah.hotel.model.RatePlanType pt : rs.getRatePlans().getRatePlan()) {
				if(rateplancodes.contains(pt.getRatePlanCode()))
				{
					lplantlist.add(pt);					
				}				
			}
			break;
		}
		trateplans.setRatePlen(lplantlist);
		rs.setRatePlans(trateplans);
		rs.setBasicPropertyInfo(basicPropertyInfoType);
		return rs;
	}

	public RoomStay getReztRoomDetailsSummaryOLD(RoomStay rs, List<String> roomTypeCodeList) throws HibernateException 
	{		
		RoomTypes troomtypes = rs.getRoomTypes();
		List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		for (com.tayyarah.hotel.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			com.tayyarah.hotel.model.RoomTypeType lrt = rt;
			if(roomTypeCodeList.contains(rt.getRoomTypeCode()))
			{
				lrtlist.add(lrt);
			}
		}
		troomtypes.setRoomTypes(lrtlist);
		rs.setRoomTypes(troomtypes);
		RoomRates troomrates = new RoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		List<String> rateplancodes = new ArrayList<String>();
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			if(roomTypeCodeList.contains(rr.getRoomTypeCode()))
			{
				rateplancodes.add(rr.getRatePlanCode());		
				lrratelist.add(lrr);
			}			
		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		RatePlans trateplans = new RatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();
		for (com.tayyarah.hotel.model.RatePlanType pt : rs.getRatePlans().getRatePlan()) {
			if(rateplancodes.contains(pt.getRatePlanCode()))
			{
				lplantlist.add(pt);
			}				
		}
		trateplans.setRatePlen(lplantlist);
		rs.setRatePlans(trateplans);
		return rs;
	}

	public static RoomStay getRoomDetailsSummarySpecific(RoomStay rs, com.tayyarah.hotel.model.HotelBookCommand.RoomRateType roomratetype) throws HibernateException 
	{		
		RoomTypes troomtypes = rs.getRoomTypes();
		List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		for (com.tayyarah.hotel.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			com.tayyarah.hotel.model.RoomTypeType lrt = rt;
			if(roomratetype.getRoomTypeCode().equals(rt.getRoomTypeCode()))
			{				
				lrtlist.add(lrt);
			}
		}
		troomtypes.setRoomTypes(lrtlist);
		rs.setRoomTypes(troomtypes);
		RoomRates troomrates = new RoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		List<String> rateplancodes = new ArrayList<String>();
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			if(roomratetype.getRoomTypeCode().equals(rr.getRoomTypeCode()))
			{				
				lrratelist.add(lrr);
			}

		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		RatePlans trateplans = new RatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();
		for (com.tayyarah.hotel.model.RatePlanType pt : rs.getRatePlans().getRatePlan()) {
			if(roomratetype.getRatePlanCode().equals(pt.getRatePlanCode()))
			{
				lplantlist.add(pt);
			}				
		}
		trateplans.setRatePlen(lplantlist);
		rs.setRatePlans(trateplans);
		return rs;
	}

	public boolean isAvailableRoomTypeCode(List<com.tayyarah.hotel.model.HotelBookCommand.RoomRateType> roomRateTypeList, String roomTypeCode)
	{
		boolean isavailable = false;
		for (com.tayyarah.hotel.model.HotelBookCommand.RoomRateType roomRateType : roomRateTypeList) {
			if(roomRateType.getRoomTypeCode().equalsIgnoreCase(roomTypeCode))
			{
				isavailable = true;
				break;
			}
		}
		return isavailable;
	}

	public com.tayyarah.hotel.model.TotalType getReviseRateTotal(com.tayyarah.hotel.model.OTAHotelResRS rs) throws HibernateException 
	{		
		com.tayyarah.hotel.model.TotalType total = new com.tayyarah.hotel.model.TotalType();	
		if(rs == null)
		{	
			logger.info("prebook object transformation---: rs.getSuccess() != null-- "+rs.getSuccess());			
			return total;
		}
		else
		{			
			logger.info("object transformation---: OTAHotelResRS has errors ");

			HotelReservationsType hotelReservationsType = new HotelReservationsType();
			List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
			if(rs.getHotelReservations() != null && rs.getHotelReservations().getHotelReservations() != null && !rs.getHotelReservations().getHotelReservations().isEmpty())					
			{				
				for (HotelReservation hotelReservation : rs.getHotelReservations().getHotelReservations()) {
					logger.info("object transformation---: OTAHotelResRS has HotelReservations ");

					if(hotelReservation.getRoomStays() != null && hotelReservation.getRoomStays().getRoomStaies() != null)
					{
						logger.info("object transformation---: OTAHotelResRS has RoomStays ");

						com.tayyarah.hotel.model.RoomStaysType troomstayes = new RoomStaysType();
						List<com.tayyarah.hotel.model.RoomStaysType.RoomStay> troomstaylist = new ArrayList<com.tayyarah.hotel.model.RoomStaysType.RoomStay>();
						for (com.tayyarah.hotel.model.RoomStaysType.RoomStay roomStay : hotelReservation.getRoomStays().getRoomStaies()) {
							com.tayyarah.hotel.model.TotalType ttotal = new com.tayyarah.hotel.model.TotalType();
							if(roomStay.getTotal() != null)
							{
								logger.info("object transformation---: OTAHotelResRS has Total ");

								ttotal.setAmountAfterTax(roomStay.getTotal().getAmountAfterTax());
								ttotal.setAmountBeforeTax(roomStay.getTotal().getAmountBeforeTax());
								ttotal.setAmountIncludingMarkup(roomStay.getTotal().getAmountIncludingMarkup());
								ttotal.setEditCommission(roomStay.getTotal().getEditCommission());
								ttotal.setEditNetRate(roomStay.getTotal().getEditNetRate());
								ttotal.setEditSellRate(roomStay.getTotal().getEditSellRate());
								ttotal.setEditServiceCharge(roomStay.getTotal().getEditServiceCharge());
								ttotal.setEditServiceTax(roomStay.getTotal().getEditServiceTax());
								ttotal.setEditTotalTax(roomStay.getTotal().getEditTotalTax());
								ttotal.setCurrencyCode(roomStay.getTotal().getCurrencyCode());
								ttotal.setAdditionalFeesExcludedIndicator(roomStay.getTotal().isAdditionalFeesExcludedIndicator());
								ttotal.setType(roomStay.getTotal().getType());
								return ttotal;
							}
						}			
					}		
				}
			}
			if(rs.getSuccess() != null)
			{
				return null;
			}
		}
		return null;
	}

	public com.tayyarah.hotel.model.TotalType getNewRateTotal(com.tayyarah.hotel.model.OTAHotelResRS rs) throws HibernateException 
	{		
		com.tayyarah.hotel.model.TotalType total = new com.tayyarah.hotel.model.TotalType();	
		if(rs == null)
		{	
			return total;
		}
		else
		{			
			if(rs.getErrors() != null && rs.getErrors().getErrors() != null && !rs.getErrors().getErrors().isEmpty())
			{
				HotelReservationsType hotelReservationsType = new HotelReservationsType();
				List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
				if(rs.getHotelReservations() != null && rs.getHotelReservations().getHotelReservations() != null && !rs.getHotelReservations().getHotelReservations().isEmpty())					
				{				
					for (HotelReservation hotelReservation : rs.getHotelReservations().getHotelReservations()) {

						if(hotelReservation.getRoomStays() != null && hotelReservation.getRoomStays().getRoomStaies() != null)
						{
							com.tayyarah.hotel.model.RoomStaysType troomstayes = new RoomStaysType();
							List<com.tayyarah.hotel.model.RoomStaysType.RoomStay> troomstaylist = new ArrayList<com.tayyarah.hotel.model.RoomStaysType.RoomStay>();
							for (com.tayyarah.hotel.model.RoomStaysType.RoomStay roomStay : hotelReservation.getRoomStays().getRoomStaies()) {
								com.tayyarah.hotel.model.TotalType ttotal = new com.tayyarah.hotel.model.TotalType();
								if(roomStay.getTotal() != null)
								{
									ttotal.setAmountAfterTax(roomStay.getTotal().getAmountAfterTax());
									ttotal.setAmountBeforeTax(roomStay.getTotal().getAmountBeforeTax());
									ttotal.setAmountIncludingMarkup(roomStay.getTotal().getAmountIncludingMarkup());
									ttotal.setEditCommission(roomStay.getTotal().getEditCommission());
									ttotal.setEditNetRate(roomStay.getTotal().getEditNetRate());
									ttotal.setEditSellRate(roomStay.getTotal().getEditSellRate());
									ttotal.setEditServiceCharge(roomStay.getTotal().getEditServiceCharge());
									ttotal.setEditServiceTax(roomStay.getTotal().getEditServiceTax());
									ttotal.setEditTotalTax(roomStay.getTotal().getEditTotalTax());
									ttotal.setCurrencyCode(roomStay.getTotal().getCurrencyCode());
									ttotal.setAdditionalFeesExcludedIndicator(roomStay.getTotal().isAdditionalFeesExcludedIndicator());
									ttotal.setType(roomStay.getTotal().getType());
									return ttotal;
								}
							}			
						}		
					}
				}
			}
			if(rs.getSuccess() != null)
			{
				return null;
			}
		}
		return null;
	}

	public com.tayyarah.hotel.model.TotalType getNewRateTotal(com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS rs) throws HibernateException 
	{		
		com.tayyarah.hotel.model.TotalType total = new com.tayyarah.hotel.model.TotalType();	
		if(rs == null)
		{	
			return total;
		}
		else
		{			
			if(rs.getErrors() != null && rs.getErrors().getErrors() != null && !rs.getErrors().getErrors().isEmpty())
			{
				HotelReservationsType hotelReservationsType = new HotelReservationsType();
				List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
				if(rs.getHotelReservations() != null && rs.getHotelReservations().getHotelReservations() != null && !rs.getHotelReservations().getHotelReservations().isEmpty())					
				{				
					for (com.tayyarah.api.hotel.travelguru.model.HotelReservationType hotelReservation : rs.getHotelReservations().getHotelReservations()) {
						if(hotelReservation.getRoomStays() != null && hotelReservation.getRoomStays().getRoomStaies() != null)
						{
							com.tayyarah.hotel.model.RoomStaysType troomstayes = new RoomStaysType();
							List<com.tayyarah.hotel.model.RoomStaysType.RoomStay> troomstaylist = new ArrayList<com.tayyarah.hotel.model.RoomStaysType.RoomStay>();
							for (com.tayyarah.api.hotel.travelguru.model.RoomStaysType.RoomStay roomStay : hotelReservation.getRoomStays().getRoomStaies()) {
								com.tayyarah.hotel.model.TotalType ttotal = new com.tayyarah.hotel.model.TotalType();
								if(roomStay.getTotal() != null)
								{
									ttotal.setAmountAfterTax(roomStay.getTotal().getAmountAfterTax());
									ttotal.setAmountBeforeTax(roomStay.getTotal().getAmountBeforeTax());
									ttotal.setAmountIncludingMarkup(roomStay.getTotal().getAmountIncludingMarkup());
									ttotal.setEditCommission(roomStay.getTotal().getEditCommission());
									ttotal.setEditNetRate(roomStay.getTotal().getEditNetRate());
									ttotal.setEditSellRate(roomStay.getTotal().getEditSellRate());
									ttotal.setEditServiceCharge(roomStay.getTotal().getEditServiceCharge());
									ttotal.setEditServiceTax(roomStay.getTotal().getEditServiceTax());
									ttotal.setEditTotalTax(roomStay.getTotal().getEditTotalTax());
									ttotal.setCurrencyCode(roomStay.getTotal().getCurrencyCode());
									ttotal.setAdditionalFeesExcludedIndicator(roomStay.getTotal().isAdditionalFeesExcludedIndicator());
									ttotal.setType(roomStay.getTotal().getType());
									return ttotal;
								}
							}			
						}		
					}
				}
			}
			if(rs.getSuccess() != null)
			{
				return null;
			}
		}
		return null;
	}

	public RoomStay convertTGtoNative(HotelApiCredentials api, HotelSearchCommand hsc, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws HibernateException, ClassNotFoundException, JAXBException, ParseException 
	{	
		BigDecimal minbase = new BigDecimal(0);
		int noOfRooms = hsc.getNoofrooms();
		RoomStay troomstay = new RoomStay();
		RoomTypes troomtypes = new RoomTypes();
		List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		for (com.tayyarah.api.hotel.travelguru.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			com.tayyarah.hotel.model.RoomTypeType lrt = new com.tayyarah.hotel.model.RoomTypeType();
			lrt.setFloor(rt.getFloor());
			lrt.setNonSmoking(rt.isNonSmoking());
			lrt.setRoomType(rt.getRoomType());
			lrt.setRoomTypeCode(rt.getRoomTypeCode());		
			List<Occupancy> locclist = new ArrayList<RoomTypeType.Occupancy>();
			for (com.tayyarah.api.hotel.travelguru.model.RoomTypeType.Occupancy occ : rt.getOccupancies()) {
				Occupancy locc = new Occupancy();
				locc.setAgeQualifyingCode(occ.getAgeQualifyingCode());
				locc.setMaxOccupancy(occ.getMaxOccupancy());
				locc.setAgeBucket(occ.getAgeBucket());
				locc.setMaxAge(occ.getMaxAge());
				locc.setMinAge(occ.getMinAge());
				locc.setMinOccupancy(occ.getMinOccupancy());
				locclist.add(locc);
			}
			lrt.setOccupancies(locclist);
			TPAExtensions lext = new TPAExtensions();
			RoomType lextroomtype = new RoomType();
			if(rt.getTPAExtensions().getRoomType() != null)
			{
				lextroomtype.setMaxAdult(rt.getTPAExtensions().getRoomType().getMaxAdult());
				lextroomtype.setMaxChild(rt.getTPAExtensions().getRoomType().getMaxChild());
				lextroomtype.setMaxChildAge(rt.getTPAExtensions().getRoomType().getMaxChildAge());
				lextroomtype.setMaxGuest(rt.getTPAExtensions().getRoomType().getMaxGuest());
				lextroomtype.setMaxInfant(rt.getTPAExtensions().getRoomType().getMaxInfant());
				lextroomtype.setMinChildAge(rt.getTPAExtensions().getRoomType().getMinChildAge());
				lextroomtype.setSmoking(rt.getTPAExtensions().getRoomType().getSmoking());
			}
			lext.setRoomType(lextroomtype);
			lrt.setTPAExtensions(lext);	
			lrtlist.add(lrt);
		}
		troomtypes.setRoomTypes(lrtlist);
		troomstay.setRoomTypes(troomtypes);

		RatePlans trateplans = new RatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();

		for (com.tayyarah.api.hotel.travelguru.model.RatePlanType pt : rs.getRatePlans().getRatePlen()) {
			com.tayyarah.hotel.model.RatePlanType lrt = new com.tayyarah.hotel.model.RatePlanType();
			lrt.setRatePlanType(pt.getRatePlanType());
			lrt.setRatePlanCode(pt.getRatePlanCode());
			lrt.setRatePlanName(pt.getRatePlanName());
			lrt.setAvailableQuantity(pt.getAvailableQuantity());
			CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();
			List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
			for (com.tayyarah.api.hotel.travelguru.model.CancelPenaltyType cpt : pt.getCancelPenalties().getCancelPenalties()) {
				CancelPenaltyType lcpt = new CancelPenaltyType();
				lcpt.setNonRefundable(cpt.isNonRefundable());
				lcpt.setDuration(cpt.getDuration());
				lcpt.setEnd(cpt.getEnd());
				lcpt.setFri(cpt.isFri());
				lcpt.setMon(cpt.isMon());
				lcpt.setPolicyCode(cpt.getPolicyCode());
				lcpt.setRoomTypeCode(cpt.getRoomTypeCode());
				lcpt.setSat(cpt.isSat());
				lcpt.setStart(cpt.getStart());
				lcpt.setSun(cpt.isSun());
				lcpt.setThur(cpt.isThur());
				lcpt.setTue(cpt.isTue());
				lcpt.setWeds(cpt.isWeds());

				Deadline ldeadline = new Deadline();
				BeanUtils.copyProperties(cpt.getDeadline(), ldeadline);				
				if(cpt.getStart() == null || cpt.getEnd() == null || cpt.getStart().isEmpty() || cpt.getEnd().isEmpty())				
				{
					String date = CommonUtil.getCancellaionDTFormat(null);
					lcpt.setStart(date);
					ldeadline.setFromDate(date);
					String date2 = CommonUtil.getCancellaionDTFormat(hsc.getDatestart());
					lcpt.setEnd(date2);
					ldeadline.setToDate(date2);
					ldeadline.setOffsetTimeUnit(TimeUnitType.DAY);
					ldeadline.setOffsetDropTime("1");			
					ldeadline.setOffsetUnitMultiplier(1);
				}
				lcpt.setDeadline(ldeadline);				
				AmountPercentType amountPercentType = new AmountPercentType();
				if(cpt.isNonRefundable())
				{
					amountPercentType.setAmount(new BigDecimal(100));
					amountPercentType.setBasisType("Percentage");
				}
				else
				{
					amountPercentType.setAmount(new BigDecimal(0));
					amountPercentType.setBasisType("Percentage");
				}

				amountPercentType.setCurrencyCode(api.getApiCurrency());

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
				for (com.tayyarah.api.hotel.travelguru.model.ParagraphType pdes : cpt.getPenaltyDescriptions()) {
					PenaltyDescription lpdes = new PenaltyDescription();
					lpdes.setDrescription(pdes.getName());										
					lpenlist.add(lpdes);
				}
				lcpt.setPenaltyDescriptions(lpenlist);
				cancellist.add(lcpt);
			}
			cancelpenalities.setCancelPenalties(cancellist);
			lrt.setCancelPenalties(cancelpenalities);
			TPAExtensions lrpext = new TPAExtensions();
			Promotion rppro = new Promotion();
			if(pt.getTPAExtensions()!=null)
			{
				if(pt.getTPAExtensions()!=null && pt.getTPAExtensions().getPromotion() !=null)
				{
					rppro.setType(pt.getTPAExtensions().getPromotion().getType());
					rppro.setId(pt.getTPAExtensions().getPromotion().getId());
					rppro.setName(pt.getTPAExtensions().getPromotion().getName());
				}
				lrpext.setPromotion(rppro);
				DiscountCouponDisplayIndicator rpdiscount = new DiscountCouponDisplayIndicator(); 

				if(pt.getTPAExtensions()!=null && pt.getTPAExtensions().getDiscountCouponDisplayIndicator() !=null)
				{
					rpdiscount.setEnabled(pt.getTPAExtensions().getDiscountCouponDisplayIndicator().isEnabled());
				}
				lrpext.setDiscountCouponDisplayIndicator(rpdiscount);
			}
			lrt.setTPAExtensions(lrpext);	
			lplantlist.add(lrt);	 
		}
		trateplans.setRatePlen(lplantlist);
		troomstay.setRatePlans(trateplans);
		RoomRates troomrates = new RoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		for (com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomRates.RoomRate rr : rs.getRoomRates().getRoomRates()) {
			com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate lrr = new com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate();
			lrr.setRatePlanCode(rr.getRatePlanCode());
			lrr.setBookingCode(rr.getBookingCode());
			lrr.setEffectiveDate(rr.getEffectiveDate());
			lrr.setExpireDate(rr.getExpireDate());
			lrr.setInvBlockCode(rr.getInvBlockCode());
			lrr.setNumberOfUnits(rr.getNumberOfUnits());
			lrr.setRoomID(rr.getRoomID());
			lrr.setRoomTypeCode(rr.getRoomID());

			//Rates
			RateType lrates = new RateType();
			List<Rate> lratelist = new ArrayList<Rate>();
			for (com.tayyarah.api.hotel.travelguru.model.RateType.Rate rtype : rr.getRates().getRates()) {
				Rate lrate = new Rate();
				lrate.setExpireDate(rtype.getExpireDate());
				lrate.setEffectiveDate(rtype.getEffectiveDate());

				BigDecimal currentbase = rtype.getBase().getAmountBeforeTax();				
				if(minbase.compareTo(new BigDecimal("0") ) == 0)
					minbase = currentbase;
				else if(currentbase.compareTo(minbase) == -1)
					minbase = currentbase;

				TotalType ltotsingle = new TotalType();
				ltotsingle.setAmountBeforeTax(rtype.getBase().getAmountBeforeTax());
				ltotsingle.setAmountAfterTax(rtype.getBase().getAmountAfterTax());	

				TaxesType ltaxsingle = new TaxesType();
				List<TaxType> ltaxlistsingle  = new ArrayList<TaxType>();
				for (com.tayyarah.api.hotel.travelguru.model.TaxType rtt : rtype.getBase().getTaxes().getTaxes()) {
					TaxType lrttsingle = new TaxType();
					lrttsingle.setAmount(rtt.getAmount());
					lrttsingle.setCode(rtt.getCode());
					ltaxlistsingle.add(lrttsingle);
				}
				ltaxsingle.setAmount(rtype.getBase().getTaxes().getAmount());
				ltaxsingle.setTaxes(ltaxlistsingle);
				ltotsingle.setTaxes(ltaxsingle);
				List<Discount> ldiscountlist  = new ArrayList<Discount>();
				for (com.tayyarah.api.hotel.travelguru.model.AmountType.Discount taxType : rtype.getDiscounts()) {
					Discount d = new Discount();
					d.setAmountAfterTax(taxType.getAmountAfterTax());
					d.setAmountBeforeTax(taxType.getAmountBeforeTax());
					d.setAppliesTo(taxType.getAppliesTo());
					ldiscountlist.add(d);
				}
				ltotsingle.setDiscounts(ldiscountlist);
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
				TPAExtensions rateplanext = new TPAExtensions();
				com.tayyarah.api.hotel.travelguru.model.TPAExtensions.Rate rateext = rtype.getTPAExtensions().getRate();
				com.tayyarah.hotel.model.TPAExtensions.Rate lrateext = new com.tayyarah.hotel.model.TPAExtensions.Rate();
				lrateext.setRoomTypeCode(rateext.getRoomTypeCode());
				lrateext.setRatePlanCode(rateext.getRatePlanCode());
				lrateext.setType(rateext.getType());
				lrateext.setBookable(rateext.isBookable());
				lrateext.setBaseChildOccupancy(rateext.getBaseChildOccupancy());
				lrateext.setBaseAdultOccupancy(rateext.getBaseAdultOccupancy());

				TotalType ltotext = new TotalType(); 
				ltotext.setAmountBeforeTax(rateext.getBase().getAmountBeforeTax());
				ltotext.setAmountAfterTax(rateext.getBase().getAmountAfterTax());

				lrateext.setBase(ltotext);
				lrateext.setBaseWithoutMarkUp(ltotext);

				rateplanext.setRate(lrateext);
				lrate.setTPAExtensions(rateplanext);					

				lratelist.add(lrate);			

			}
			lrates.setRates(lratelist);
			lrr.setRates(lrates);

			//add guest information..
			lrr.getGuestCounts();
			GuestCounts gustcounts = new GuestCounts();
			List<GuestCount> gustcountslist = new ArrayList<GuestCount>();					
			for (com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomRates.RoomRate.GuestCounts.GuestCount guestCount : rr.getGuestCounts().getGuestCounts()) {
				GuestCount lgc = new GuestCount();
				lgc.setAge(guestCount.getAge());
				lgc.setAgeBucket(guestCount.getAgeBucket());
				lgc.setAgeQualifyingCode(guestCount.getAgeQualifyingCode());
				lgc.setCount(guestCount.getCount());			
				gustcountslist.add(lgc);
			}
			gustcounts.setGuestCounts(gustcountslist);
			lrr.setGuestCounts(gustcounts);			
			lrratelist.add(lrr);
		}
		troomrates.setRoomRates(lrratelist);
		troomstay.setRoomRates(troomrates);		

		BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
		tbasic.setHotelCode(rs.getBasicPropertyInfo().getHotelCode());

		troomstay.setBasicPropertyInfo(tbasic);	

		TPAExtensions roomstayextension = new TPAExtensions();		
		HotelBasicInformation roomstayhotelbasic = new HotelBasicInformation();

		if(rs.getTPAExtensions()!=null)
		{
			if(rs.getTPAExtensions().getHotelBasicInformation()!=null)
			{
				roomstayhotelbasic.setRank(rs.getTPAExtensions().getHotelBasicInformation().getRank());
				roomstayhotelbasic.setHotelType(rs.getTPAExtensions().getHotelBasicInformation().getHotelType());

			}
			Reviews roomstayreviews = new Reviews();
			if(rs.getTPAExtensions().getHotelBasicInformation().getReviews()!=null)
			{
				roomstayreviews.setReviewCount(rs.getTPAExtensions().getHotelBasicInformation().getReviews().getReviewCount());
				roomstayreviews.setReviewRating(rs.getTPAExtensions().getHotelBasicInformation().getReviews().getReviewRating());

			}
			roomstayhotelbasic.setReviews(roomstayreviews);			

			DeepLinkInformation roomstaydeeplink = new DeepLinkInformation();
			if(rs.getTPAExtensions().getDeepLinkInformation()!=null)
			{
				roomstaydeeplink.setOverviewURL(rs.getTPAExtensions().getDeepLinkInformation().getOverviewURL());
				roomstaydeeplink.setOverviewURL(rs.getTPAExtensions().getDeepLinkInformation().getOverviewURL());

			}
			roomstayextension.setStopSell(rs.getTPAExtensions().isStopSell());
			roomstayextension.setDeepLinkInformation(roomstaydeeplink);
		}
		troomstay.setTPAExtensions(roomstayextension);
		return troomstay;
	}

	public RoomStay convertTGtoNative(HotelApiCredentials api, HotelSearchCommand hsc, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay rs, HotelOverview ho, List<Facility> hotelfacilitieslist, List<Facility> hotelRoomfacilitieslist, List<String> hotelimages, Map<Integer, Hotelroomdescription> hotelroomsmap) throws HibernateException, ClassNotFoundException, JAXBException, ParseException 
	{	
		BigDecimal minbase = new BigDecimal(0);
		int noofdays = CommonUtil.getNoofStayDays(hsc);
		RoomStay troomstay = new RoomStay();
		int noofrooms = hsc.getNoofrooms();
		RoomTypes troomtypes = new RoomTypes();		
		List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		for (com.tayyarah.api.hotel.travelguru.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
			for(int i = 0; i<noofrooms ; i++)
			{
				com.tayyarah.hotel.model.RoomTypeType lrt = new com.tayyarah.hotel.model.RoomTypeType();
				lrt.setFloor(rt.getFloor());
				lrt.setNonSmoking(rt.isNonSmoking());
				lrt.setRoomType(rt.getRoomType());
				lrt.setRoomTypeCode(rt.getRoomTypeCode());
				Hotelroomdescription lroomdes = hotelroomsmap.get(trimSringtoInt(rt.getRoomTypeCode()));
				if(lroomdes != null)
					lrt.setRoomDescription(lroomdes);
				List<Facility> facilitiesRoom = hotelRoomfacilitieslist;
				lrt.setAmenities(facilitiesRoom);
				List<Occupancy> locclist = new ArrayList<RoomTypeType.Occupancy>();
				for (com.tayyarah.api.hotel.travelguru.model.RoomTypeType.Occupancy occ : rt.getOccupancies()) {
					Occupancy locc = new Occupancy();
					locc.setAgeQualifyingCode(occ.getAgeQualifyingCode());
					locc.setMaxOccupancy(occ.getMaxOccupancy());
					locc.setAgeBucket(occ.getAgeBucket());
					locc.setMaxAge(occ.getMaxAge());
					locc.setMinAge(occ.getMinAge());
					locc.setMinOccupancy(occ.getMinOccupancy());
					locclist.add(locc);
				}
				lrt.setOccupancies(locclist);
				TPAExtensions lext = new TPAExtensions();
				RoomType lextroomtype = new RoomType();
				if(rt.getTPAExtensions().getRoomType() != null)
				{
					lextroomtype.setMaxAdult(rt.getTPAExtensions().getRoomType().getMaxAdult());
					lextroomtype.setMaxChild(rt.getTPAExtensions().getRoomType().getMaxChild());
					lextroomtype.setMaxChildAge(rt.getTPAExtensions().getRoomType().getMaxChildAge());
					lextroomtype.setMaxGuest(rt.getTPAExtensions().getRoomType().getMaxGuest());
					lextroomtype.setMaxInfant(rt.getTPAExtensions().getRoomType().getMaxInfant());
					lextroomtype.setMinChildAge(rt.getTPAExtensions().getRoomType().getMinChildAge());
					lextroomtype.setSmoking(rt.getTPAExtensions().getRoomType().getSmoking());

				}
				lext.setRoomType(lextroomtype);
				lrt.setTPAExtensions(lext);	
				lrtlist.add(lrt);
			}
		}
		troomtypes.setRoomTypes(lrtlist);
		troomstay.setRoomTypes(troomtypes);

		RatePlans trateplans = new RatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();
		for (com.tayyarah.api.hotel.travelguru.model.RatePlanType pt : rs.getRatePlans().getRatePlen()) {

			for(int i = 0; i<noofrooms ; i++)
			{
				com.tayyarah.hotel.model.RatePlanType lrt = new com.tayyarah.hotel.model.RatePlanType();
				lrt.setRatePlanType(pt.getRatePlanType());
				lrt.setRatePlanCode(pt.getRatePlanCode());
				lrt.setRatePlanName(pt.getRatePlanName());
				lrt.setAvailableQuantity(pt.getAvailableQuantity());			
				CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();
				List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
				for (com.tayyarah.api.hotel.travelguru.model.CancelPenaltyType cpt : pt.getCancelPenalties().getCancelPenalties()) {
					CancelPenaltyType lcpt = new CancelPenaltyType();				
					lcpt.setNonRefundable(cpt.isNonRefundable());
					lcpt.setDuration(cpt.getDuration());
					lcpt.setEnd(cpt.getEnd());
					lcpt.setFri(cpt.isFri());
					lcpt.setMon(cpt.isMon());
					lcpt.setPolicyCode(cpt.getPolicyCode());
					lcpt.setRoomTypeCode(cpt.getRoomTypeCode());
					lcpt.setSat(cpt.isSat());
					lcpt.setStart(cpt.getStart());
					lcpt.setSun(cpt.isSun());
					lcpt.setThur(cpt.isThur());
					lcpt.setTue(cpt.isTue());
					lcpt.setWeds(cpt.isWeds());
					Deadline ldeadline = new Deadline();
					BeanUtils.copyProperties(cpt.getDeadline(), ldeadline);				
					if(cpt.getStart() == null || cpt.getEnd() == null || cpt.getStart().isEmpty() || cpt.getEnd().isEmpty())
					{
						String date = CommonUtil.getCancellaionDTFormat(null);
						lcpt.setStart(date);
						ldeadline.setFromDate(date);

						String date2 = CommonUtil.getCancellaionDTFormat(hsc.getDatestart());
						lcpt.setEnd(date2);
						ldeadline.setToDate(date2);
						ldeadline.setOffsetTimeUnit(TimeUnitType.DAY);
						ldeadline.setOffsetDropTime("1");			
						ldeadline.setOffsetUnitMultiplier(1);
					}
					lcpt.setDeadline(ldeadline);				
					AmountPercentType amountPercentType = new AmountPercentType();				
					if(cpt.isNonRefundable())
					{
						amountPercentType.setAmount(new BigDecimal(100));
						amountPercentType.setBasisType("Percentage");
					}
					else
					{
						amountPercentType.setAmount(new BigDecimal(0));
						amountPercentType.setBasisType("Percentage");
					}				
					amountPercentType.setCurrencyCode(api.getApiCurrency());
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
					for (com.tayyarah.api.hotel.travelguru.model.ParagraphType pdes : cpt.getPenaltyDescriptions()) {
						PenaltyDescription lpdes = new PenaltyDescription();
						lpdes.setDrescription(pdes.getName());						
						lpenlist.add(lpdes);
					}
					lcpt.setPenaltyDescriptions(lpenlist);
					cancellist.add(lcpt);
				}
				cancelpenalities.setCancelPenalties(cancellist);
				lrt.setCancelPenalties(cancelpenalities);
				TPAExtensions lrpext = new TPAExtensions();
				Promotion rppro = new Promotion();
				if(pt.getTPAExtensions()!=null)
				{
					if(pt.getTPAExtensions()!=null && pt.getTPAExtensions().getPromotion() !=null)
					{
						rppro.setType(pt.getTPAExtensions().getPromotion().getType());
						rppro.setId(pt.getTPAExtensions().getPromotion().getId());
						rppro.setName(pt.getTPAExtensions().getPromotion().getName());
					}
					lrpext.setPromotion(rppro);
					DiscountCouponDisplayIndicator rpdiscount = new DiscountCouponDisplayIndicator(); 

					if(pt.getTPAExtensions()!=null && pt.getTPAExtensions().getDiscountCouponDisplayIndicator() !=null)
					{
						rpdiscount.setEnabled(pt.getTPAExtensions().getDiscountCouponDisplayIndicator().isEnabled());
					}
					lrpext.setDiscountCouponDisplayIndicator(rpdiscount);
				}
				lrt.setTPAExtensions(lrpext);	
				lplantlist.add(lrt);
			}
		}
		trateplans.setRatePlen(lplantlist);
		troomstay.setRatePlans(trateplans);
		RoomRates troomrates = new RoomRates();			
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		int roomreqindexmax  = 0; 
		Integer roomindex = 0;		
		List<RoomCombination> roomCombinationList = new ArrayList<RoomCombination>();			
		for (com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomRates.RoomRate rr : rs.getRoomRates().getRoomRates()) {
			roomindex++;
			List<Integer> roomindexlist = new ArrayList<Integer>();		
			for(int i = 0; i<noofrooms ; i++)
			{
				com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate lrr = new com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate();
				RoomBookingKeyMap roomBookingKeyMap = hotelIdFactory.createRoomRateBookingKey(rr.getRoomID(), rr.getRatePlanCode());				
				roomindexlist.add(roomindex);
				lrr.setSupplierRoomIndex(roomindex);
				lrr.setSupplierHotelCode(rs.getBasicPropertyInfo().getHotelCode());
				lrr.setRoomReqIndex(roomreqindexmax);
				lrr.setRatePlanCode(rr.getRatePlanCode());
				lrr.setBookingCode(roomBookingKeyMap.getBookingKey());
				lrr.setEffectiveDate(rr.getEffectiveDate());
				lrr.setExpireDate(rr.getExpireDate());
				lrr.setInvBlockCode(rr.getInvBlockCode());
				lrr.setNumberOfUnits(rr.getNumberOfUnits());
				lrr.setRoomID(rr.getRoomID());
				lrr.setRoomTypeCode(rr.getRoomID());
				for (RatePlanType rpt : lplantlist) {
					if(rpt.getRatePlanCode().equalsIgnoreCase(rr.getRatePlanCode()))
					{
						lrr.setRoomTypeName(rpt.getRatePlanName());
					}
				}

				//Rates
				RateType lrates = new RateType();
				List<Rate> lratelist = new ArrayList<Rate>();
				for (com.tayyarah.api.hotel.travelguru.model.RateType.Rate rtype : rr.getRates().getRates()) {
					Rate lrate = new Rate();
					lrate.setExpireDate(rtype.getExpireDate());
					lrate.setEffectiveDate(rtype.getEffectiveDate());
					BigDecimal currentbase = rtype.getBase().getAmountBeforeTax();
					currentbase = AmountRoundingModeUtil.roundingModeForHotel(currentbase.divide(new BigDecimal(noofrooms),1,RoundingMode.UP));

					if(minbase.compareTo(new BigDecimal("0") ) == 0)
						minbase = currentbase;
					else if(currentbase.compareTo(minbase) == -1)
						minbase = currentbase;

					TotalType ltotsingle = new TotalType();
					BigDecimal amountBeforeTax = rtype.getBase().getAmountBeforeTax()==null?new BigDecimal(0):rtype.getBase().getAmountBeforeTax();
					amountBeforeTax = AmountRoundingModeUtil.roundingModeForHotel(amountBeforeTax.divide(new BigDecimal(noofrooms),1,RoundingMode.UP));
					ltotsingle.setAmountBeforeTax(amountBeforeTax);
					BigDecimal taxAmountTotal = (rtype.getBase().getTaxes()) == null ||rtype.getBase().getTaxes().getAmount()==null ? new BigDecimal(0): rtype.getBase().getTaxes().getAmount();
					taxAmountTotal =  AmountRoundingModeUtil.roundingModeForHotel(taxAmountTotal.divide(new BigDecimal(noofrooms),1,RoundingMode.UP));
					BigDecimal amountAfterTax = rtype.getBase().getAmountAfterTax()==null?amountBeforeTax.add(taxAmountTotal):rtype.getBase().getAmountAfterTax();
					amountAfterTax = AmountRoundingModeUtil.roundingModeForHotel(amountAfterTax);
					TaxesType ltaxsingle = new TaxesType();
					List<TaxType> ltaxlistsingle  = new ArrayList<TaxType>();
					for (com.tayyarah.api.hotel.travelguru.model.TaxType rtt : rtype.getBase().getTaxes().getTaxes()) {
						TaxType lrttsingle = new TaxType();
						BigDecimal taxAmount = rtt.getAmount() == null ? new BigDecimal(0): rtt.getAmount();
						taxAmount = AmountRoundingModeUtil.roundingModeForHotel(taxAmount.divide(new BigDecimal(noofrooms),1,RoundingMode.UP));
						lrttsingle.setAmount(taxAmount);
						lrttsingle.setCode(rtt.getCode());
						ltaxlistsingle.add(lrttsingle);
					}
					ltaxsingle.setAmount(taxAmountTotal);
					ltaxsingle.setTaxes(ltaxlistsingle);
					ltotsingle.setTaxes(ltaxsingle);	
					if(amountAfterTax == null || amountAfterTax.equals(0))	
					{
						amountAfterTax = amountBeforeTax.add(taxAmountTotal);					
					}
					ltotsingle.setAmountAfterTax(amountAfterTax);
					BigDecimal discount = new BigDecimal(0);
					List<Discount> ldiscountlist  = new ArrayList<Discount>();
					for (com.tayyarah.api.hotel.travelguru.model.AmountType.Discount disType : rtype.getDiscounts()) {
						Discount d = new Discount();
						BigDecimal disAmountAfterTax = disType.getAmountAfterTax() == null ? new BigDecimal(0): disType.getAmountAfterTax();
						disAmountAfterTax =  AmountRoundingModeUtil.roundingModeForHotel(disAmountAfterTax.divide(new BigDecimal(noofrooms),1,RoundingMode.UP));
						BigDecimal disAmountBeforeTax = disType.getAmountBeforeTax() == null ? new BigDecimal(0): disType.getAmountBeforeTax();
						disAmountBeforeTax = AmountRoundingModeUtil.roundingModeForHotel(disAmountBeforeTax.divide(new BigDecimal(noofrooms),1,RoundingMode.UP));
						d.setAmountAfterTax(disAmountAfterTax);
						d.setAmountBeforeTax(disAmountBeforeTax);
						d.setAppliesTo(disType.getAppliesTo());
						discount =  discount.add((disAmountBeforeTax==null)?new BigDecimal(0):disAmountBeforeTax);
						ldiscountlist.add(d);
					}
					ltotsingle.setDiscounts(ldiscountlist);
					BigDecimal totalPayable = amountBeforeTax.subtract(discount);
					totalPayable = totalPayable.add(taxAmountTotal);
					totalPayable =  totalPayable.multiply(new BigDecimal(noofdays));
					ltotsingle.setTotalAmountPayable(totalPayable);		
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
					TPAExtensions rateplanext = new TPAExtensions();
					com.tayyarah.api.hotel.travelguru.model.TPAExtensions.Rate rateext = rtype.getTPAExtensions().getRate();
					com.tayyarah.hotel.model.TPAExtensions.Rate lrateext = new com.tayyarah.hotel.model.TPAExtensions.Rate();
					lrateext.setRoomTypeCode(rateext.getRoomTypeCode());
					lrateext.setRatePlanCode(rateext.getRatePlanCode());
					lrateext.setType(rateext.getType());
					lrateext.setBookable(rateext.isBookable());
					lrateext.setBaseChildOccupancy(rateext.getBaseChildOccupancy());
					lrateext.setBaseAdultOccupancy(rateext.getBaseAdultOccupancy());
					TotalType ltotext = new TotalType(); 
					if(rateext.getBase()!=null)
						ltotext.setAmountBeforeTax(rateext.getBase().getAmountBeforeTax());
					if(rateext.getBase()!=null)
						ltotext.setAmountAfterTax(rateext.getBase().getAmountAfterTax());

					lrateext.setBase(ltotext);
					rateplanext.setRate(lrateext);
					lrate.setTPAExtensions(rateplanext);
					lratelist.add(lrate);	
				}
				lrates.setRates(lratelist);
				lrr.setRates(lrates);
				//add guest information..
				lrr.getGuestCounts();
				GuestCounts gustcounts = new GuestCounts();
				List<GuestCount> gustcountslist = new ArrayList<GuestCount>();					
				for (com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomRates.RoomRate.GuestCounts.GuestCount guestCount : rr.getGuestCounts().getGuestCounts()) {
					GuestCount lgc = new GuestCount();
					lgc.setAge(guestCount.getAge());
					lgc.setAgeBucket(guestCount.getAgeBucket());
					lgc.setAgeQualifyingCode(guestCount.getAgeQualifyingCode());
					lgc.setCount(guestCount.getCount());					
					gustcountslist.add(lgc);
				}
				gustcounts.setGuestCounts(gustcountslist);
				lrr.setGuestCounts(gustcounts);				
				lrratelist.add(lrr);
			}
			RoomCombination roomCombination = new RoomCombination();
			roomCombination.setRoomIndex(roomindexlist);
			roomCombinationList.add(roomCombination);
		}
		RoomCombinations roomCombinations = new RoomCombinations(); 
		roomCombinations.setInfoSource("FixedCombination");
		roomCombinations.setRoomCombination(roomCombinationList);
		roomCombinations.setApiProvider(HotelApiCredentials.API_DESIA_IND);
		List<RoomCombinations> supplierRoomGroups = new ArrayList<RoomCombinations>();
		supplierRoomGroups.add(roomCombinations);
		troomstay.setSupplierRoomGroups(supplierRoomGroups);
		troomrates.setRoomRates(lrratelist);
		troomstay.setRoomRates(troomrates);
		BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
		if(ho != null)
		{
			tbasic.setApiVendorID(rs.getBasicPropertyInfo().getHotelCode());
			tbasic.setHotelCode(api.getId()+"-"+String.valueOf(ho.getHoteId().intValue()));
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

			HashMap<Integer, String> apiProviderMap = new HashMap<Integer, String>();
			apiProviderMap.put(HotelApiCredentials.API_DESIA_IND, rs.getBasicPropertyInfo().getHotelCode());
			tbasic.setApiProviderMap(apiProviderMap);
		}
		tbasic.setApiPrice(minbase);
		tbasic.setBasePrice(minbase);
		tbasic.setBasePriceWithoutMarkup(minbase);
		tbasic.setBookingPrice(minbase);
		tbasic.setApiProvider(HotelApiCredentials.API_DESIA_IND);
		tbasic.setIsOfflineBooking(false);	
		tbasic.setHotelimages(hotelimages);
		tbasic.setHotelAmenities(hotelfacilitieslist);
		troomstay.setBasicPropertyInfo(tbasic);

		TPAExtensions roomstayextension = new TPAExtensions();			
		HotelBasicInformation roomstayhotelbasic = new HotelBasicInformation();
		if(rs.getTPAExtensions()!=null)
		{
			if(rs.getTPAExtensions().getHotelBasicInformation()!=null)
			{
				roomstayhotelbasic.setRank(rs.getTPAExtensions().getHotelBasicInformation().getRank());
				roomstayhotelbasic.setHotelType(rs.getTPAExtensions().getHotelBasicInformation().getHotelType());

			}
			Reviews roomstayreviews = new Reviews();
			if(rs.getTPAExtensions().getHotelBasicInformation().getReviews()!=null)
			{
				roomstayreviews.setReviewCount(rs.getTPAExtensions().getHotelBasicInformation().getReviews().getReviewCount());
				roomstayreviews.setReviewRating(rs.getTPAExtensions().getHotelBasicInformation().getReviews().getReviewRating());

			}
			roomstayhotelbasic.setReviews(roomstayreviews);			

			DeepLinkInformation roomstaydeeplink = new DeepLinkInformation();
			if(rs.getTPAExtensions().getDeepLinkInformation()!=null)
			{
				roomstaydeeplink.setOverviewURL(rs.getTPAExtensions().getDeepLinkInformation().getOverviewURL());
				roomstaydeeplink.setOverviewURL(rs.getTPAExtensions().getDeepLinkInformation().getOverviewURL());

			}
			roomstayextension.setStopSell(rs.getTPAExtensions().isStopSell());
			roomstayextension.setDeepLinkInformation(roomstaydeeplink);
		}	
		return troomstay;
	}

	public	Map<String, HotelOverview> getHotelOverview(List<String> list) {		
		return hoteldao.getHotelOverviewByVendorID(list);
	}

	public	Map<String, List<Facility>> getFacilities(List<String> list, String amenitytype) {	
		return hotelFacilityDao.getFacilityByVendorID(list, amenitytype);		
	}
	public	Map<String, List<Facility>> getFacilities(Map<String, Integer> hotelidmap, String amenitytype) {	
		return hotelFacilityDao.getFacilityApiMap(hotelidmap, amenitytype);		
	}
	public	Map<String, List<String>> getHotelImages(List<String> list) {	
		return hotelimagesDao.getImagesByVendorID(list);		
	}
	public	Map<String, List<String>> getHotelImages(Map<String, Integer> hotelidmap) {	
		return hotelimagesDao.getImagesApiMap(hotelidmap);		
	}	
	public	Map<String, Map<Integer, Hotelroomdescription>> getHotelRooms(List<String> list) {	
		return hotelroomdescriptionDao.getHotelroomdescriptionByVendorID(list);
	}
	public	Map<String, Map<Integer, Hotelroomdescription>> getHotelRooms(Map<String, Integer> hotelidmap) {	
		return hotelroomdescriptionDao.getHotelroomdescriptionApiMap(hotelidmap);
	}
	public	Map<String, HotelOverview> getHotelOverviewCommon(List<String> list, String apiProviderId) {		
		return hoteldao.getHotelOverviewApiMapByVendorIDs(list, apiProviderId);
	}
	public	Map<String, HotelOverview> getHotelOverviewNative(List<String> list, String apiProviderId) {		
		return hoteldao.getHotelOverviewNativeMapByVendorIDs(list, apiProviderId);
	}

	public	Map<String, List<Facility>> getFacilitiesNative(List<String> list, String amenitytype, String apiProviderId) {	
		return hotelFacilityDao.getFacilityNativeMapByVendorIDs(list, amenitytype, apiProviderId);		
	}

	public OTAHotelAvailRS transformHotelSearchResult(HotelApiCredentials api, HotelSearchCommand hsc, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS desiares) throws HibernateException 
	{		
		OTAHotelAvailRS targetRes = new OTAHotelAvailRS();
		HotelsInfo thotelsinfo =  new HotelsInfo();
		if(desiares == null)
			return targetRes;

		if(desiares.getTPAExtensions()!=null && desiares.getTPAExtensions().getHotelsInfo() !=null)
		{
			thotelsinfo.setAvailable(desiares.getTPAExtensions().getHotelsInfo().getAvailable());
			thotelsinfo.setTotal(desiares.getTPAExtensions().getHotelsInfo().getTotal());
			thotelsinfo.setMinPrice(desiares.getTPAExtensions().getHotelsInfo().getMinPrice());
			thotelsinfo.setMaxPrice(desiares.getTPAExtensions().getHotelsInfo().getMaxPrice());
			thotelsinfo.setDeals(desiares.getTPAExtensions().getHotelsInfo().getDeals());
		}
		TPAExtensions targettpa = new TPAExtensions();
		targettpa.setHotelsInfo(thotelsinfo);
		targetRes.setTPAExtensions(targettpa);
		RoomStays troomstays = new RoomStays();

		List<RoomStay> roomstaies = new ArrayList<RoomStay>();	
		for(com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay rs:desiares.getRoomStays().getRoomStaies()){ 
			RoomStay troomstay = new RoomStay();
			RoomTypes troomtypes = new RoomTypes();
			List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();		

			for (com.tayyarah.api.hotel.travelguru.model.RoomTypeType rt : rs.getRoomTypes().getRoomTypes()) {
				com.tayyarah.hotel.model.RoomTypeType lrt = new com.tayyarah.hotel.model.RoomTypeType();
				lrt.setFloor(rt.getFloor());
				lrt.setNonSmoking(rt.isNonSmoking());
				lrt.setRoomType(rt.getRoomType());
				lrt.setRoomTypeCode(rt.getRoomTypeCode());
				Hotelroomdescription lroomdes = hotelroomdescriptionDao.getHotelByRoomType(trimSringtoInt(rt.getRoomTypeCode()));
				if(lroomdes != null)
					lrt.setRoomDescription(lroomdes);

				List<Occupancy> locclist = new ArrayList<RoomTypeType.Occupancy>();
				for (com.tayyarah.api.hotel.travelguru.model.RoomTypeType.Occupancy occ : rt.getOccupancies()) {
					Occupancy locc = new Occupancy();
					locc.setAgeQualifyingCode(occ.getAgeQualifyingCode());
					locc.setMaxOccupancy(occ.getMaxOccupancy());
					locc.setAgeBucket(occ.getAgeBucket());
					locc.setMaxAge(occ.getMaxAge());
					locc.setMinAge(occ.getMinAge());
					locc.setMinOccupancy(occ.getMinOccupancy());
					locclist.add(locc);
				}
				lrt.setOccupancies(locclist);
				TPAExtensions lext = new TPAExtensions();
				RoomType lextroomtype = new RoomType();
				if(rt.getTPAExtensions().getRoomType() != null)
				{
					lextroomtype.setMaxAdult(rt.getTPAExtensions().getRoomType().getMaxAdult());
					lextroomtype.setMaxChild(rt.getTPAExtensions().getRoomType().getMaxChild());
					lextroomtype.setMaxChildAge(rt.getTPAExtensions().getRoomType().getMaxChildAge());
					lextroomtype.setMaxGuest(rt.getTPAExtensions().getRoomType().getMaxGuest());
					lextroomtype.setMaxInfant(rt.getTPAExtensions().getRoomType().getMaxInfant());
					lextroomtype.setMinChildAge(rt.getTPAExtensions().getRoomType().getMinChildAge());
					lextroomtype.setSmoking(rt.getTPAExtensions().getRoomType().getSmoking());
				}
				lext.setRoomType(lextroomtype);
				lrt.setTPAExtensions(lext);	
				lrtlist.add(lrt);
			}
			troomtypes.setRoomTypes(lrtlist);
			troomstay.setRoomTypes(troomtypes);

			RatePlans trateplans = new RatePlans();
			List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();
			for (com.tayyarah.api.hotel.travelguru.model.RatePlanType pt : rs.getRatePlans().getRatePlen()) {
				com.tayyarah.hotel.model.RatePlanType lrt = new com.tayyarah.hotel.model.RatePlanType();
				lrt.setRatePlanType(pt.getRatePlanType());
				lrt.setRatePlanCode(pt.getRatePlanCode());
				lrt.setRatePlanName(pt.getRatePlanName());
				lrt.setAvailableQuantity(pt.getAvailableQuantity());
				CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();
				List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
				for (com.tayyarah.api.hotel.travelguru.model.CancelPenaltyType cpt : pt.getCancelPenalties().getCancelPenalties()) {
					CancelPenaltyType lcpt = new CancelPenaltyType();
					lcpt.setNonRefundable(cpt.isNonRefundable());
					lcpt.setDuration(cpt.getDuration());
					lcpt.setEnd(cpt.getEnd());
					lcpt.setFri(cpt.isFri());
					lcpt.setMon(cpt.isMon());
					lcpt.setPolicyCode(cpt.getPolicyCode());
					lcpt.setRoomTypeCode(cpt.getRoomTypeCode());
					lcpt.setSat(cpt.isSat());
					lcpt.setStart(cpt.getStart());
					lcpt.setSun(cpt.isSun());
					lcpt.setThur(cpt.isThur());
					lcpt.setTue(cpt.isTue());
					lcpt.setWeds(cpt.isWeds());

					Deadline ldeadline = new Deadline();
					BeanUtils.copyProperties(cpt.getDeadline(), ldeadline);				
					if(cpt.getStart() == null || cpt.getEnd() == null || cpt.getStart().isEmpty() || cpt.getEnd().isEmpty())
					{
						String date = CommonUtil.getCancellaionDTFormat(null);
						lcpt.setStart(date);
						ldeadline.setFromDate(date);

						String date2 = CommonUtil.getCancellaionDTFormat(hsc.getDatestart());
						lcpt.setEnd(date2);
						ldeadline.setToDate(date2);
						ldeadline.setOffsetTimeUnit(TimeUnitType.DAY);
						ldeadline.setOffsetDropTime("1");			
						ldeadline.setOffsetUnitMultiplier(1);
					}
					lcpt.setDeadline(ldeadline);				
					AmountPercentType amountPercentType = new AmountPercentType();

					if(cpt.isNonRefundable())
					{
						amountPercentType.setAmount(new BigDecimal(100));
						amountPercentType.setBasisType("Percentage");
					}
					else
					{
						amountPercentType.setAmount(new BigDecimal(0));
						amountPercentType.setBasisType("Percentage");
					}
					amountPercentType.setCurrencyCode(api.getApiCurrency());

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
					for (com.tayyarah.api.hotel.travelguru.model.ParagraphType pdes : cpt.getPenaltyDescriptions()) {
						PenaltyDescription lpdes = new PenaltyDescription();
						lpdes.setDrescription(pdes.getName());											
						lpenlist.add(lpdes);
					}
					lcpt.setPenaltyDescriptions(lpenlist);
					BeanUtils.copyProperties(ldeadline, cpt.getDeadline());
					cancellist.add(lcpt);
				}
				cancelpenalities.setCancelPenalties(cancellist);
				lrt.setCancelPenalties(cancelpenalities);
				TPAExtensions lrpext = new TPAExtensions();
				Promotion rppro = new Promotion();
				if(pt.getTPAExtensions()!=null)
				{
					if(pt.getTPAExtensions()!=null && pt.getTPAExtensions().getPromotion() !=null)
					{
						rppro.setType(pt.getTPAExtensions().getPromotion().getType());
						rppro.setId(pt.getTPAExtensions().getPromotion().getId());
						rppro.setName(pt.getTPAExtensions().getPromotion().getName());
					}
					lrpext.setPromotion(rppro);
					DiscountCouponDisplayIndicator rpdiscount = new DiscountCouponDisplayIndicator(); 

					if(pt.getTPAExtensions()!=null && pt.getTPAExtensions().getDiscountCouponDisplayIndicator() !=null)
					{
						rpdiscount.setEnabled(pt.getTPAExtensions().getDiscountCouponDisplayIndicator().isEnabled());
					}
					lrpext.setDiscountCouponDisplayIndicator(rpdiscount);
				}
				lrt.setTPAExtensions(lrpext);	
				lplantlist.add(lrt);	 
			}
			trateplans.setRatePlen(lplantlist);
			troomstay.setRatePlans(trateplans);
			RoomRates troomrates = new RoomRates();
			List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
			for (com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomRates.RoomRate rr : rs.getRoomRates().getRoomRates()) {
				com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate lrr = new com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate();
				lrr.setRatePlanCode(rr.getRatePlanCode());
				lrr.setBookingCode(rr.getBookingCode());
				lrr.setEffectiveDate(rr.getEffectiveDate());
				lrr.setExpireDate(rr.getExpireDate());
				lrr.setInvBlockCode(rr.getInvBlockCode());
				lrr.setNumberOfUnits(rr.getNumberOfUnits());
				lrr.setRoomID(rr.getRoomID());
				lrr.setRoomTypeCode(rr.getRoomTypeCode());

				//Rates
				RateType lrates = new RateType();
				List<Rate> lratelist = new ArrayList<Rate>();							
				for (com.tayyarah.api.hotel.travelguru.model.RateType.Rate rtype : rr.getRates().getRates()) {
					Rate lrate = new Rate();
					lrate.setExpireDate(rtype.getExpireDate());
					lrate.setEffectiveDate(rtype.getEffectiveDate());

					TotalType ltot = new TotalType();
					ltot.setAmountBeforeTax(rtype.getBase().getAmountBeforeTax());
					ltot.setAmountAfterTax(rtype.getBase().getAmountAfterTax());

					TaxesType ltax = new TaxesType();
					List<TaxType> ltaxlist  = new ArrayList<TaxType>();					
					for (com.tayyarah.api.hotel.travelguru.model.TaxType rtt : rtype.getBase().getTaxes().getTaxes()) {
						TaxType lrtt = new TaxType();
						lrtt.setAmount(rtt.getAmount());
						lrtt.setCode(rtt.getCode());
						ltaxlist.add(lrtt);
					}
					ltax.setAmount(rtype.getBase().getTaxes().getAmount());
					ltax.setTaxes(ltaxlist);
					ltot.setTaxes(ltax);			
					List<Discount> ldiscountlist  = new ArrayList<Discount>();
					for (com.tayyarah.api.hotel.travelguru.model.AmountType.Discount taxType : rtype.getDiscounts()) {
						Discount d = new Discount();
						d.setAmountAfterTax(taxType.getAmountAfterTax());
						d.setAmountBeforeTax(taxType.getAmountBeforeTax());
						d.setAppliesTo(taxType.getAppliesTo());
					}
					ltot.setDiscounts(ldiscountlist);

					TotalType ltotsinglebase = new TotalType();
					TotalType ltotsinglebasewithoutmarkup = new TotalType();
					TotalType ltotsinglebooking = new TotalType();				
					BeanUtils.copyProperties(ltot, ltotsinglebase);
					BeanUtils.copyProperties(ltot, ltotsinglebasewithoutmarkup);
					BeanUtils.copyProperties(ltot, ltotsinglebooking);				
					lrate.setApiPrice(ltot);
					lrate.setBase(ltotsinglebase);			
					lrate.setBaseWithoutMarkUp(ltotsinglebasewithoutmarkup);
					lrate.setBookingPrice(ltotsinglebooking);

					TPAExtensions rateplanext = new TPAExtensions();
					com.tayyarah.api.hotel.travelguru.model.TPAExtensions.Rate rateext = rtype.getTPAExtensions().getRate();
					com.tayyarah.hotel.model.TPAExtensions.Rate lrateext = new com.tayyarah.hotel.model.TPAExtensions.Rate();

					lrateext.setRoomTypeCode(rateext.getRoomTypeCode());
					lrateext.setRatePlanCode(rateext.getRatePlanCode());
					lrateext.setType(rateext.getType());
					lrateext.setBookable(rateext.isBookable());
					lrateext.setBaseChildOccupancy(rateext.getBaseChildOccupancy());
					lrateext.setBaseAdultOccupancy(rateext.getBaseAdultOccupancy());

					TotalType ltotext = new TotalType(); 
					ltotext.setAmountBeforeTax(rateext.getBase().getAmountBeforeTax());
					ltotext.setAmountAfterTax(rateext.getBase().getAmountAfterTax());

					lrateext.setBase(ltotext);
					rateplanext.setRate(lrateext);
					lrate.setTPAExtensions(rateplanext);
					lratelist.add(lrate);
				}
				lrates.setRates(lratelist);
				lrr.setRates(lrates);

				//add guest information..
				lrr.getGuestCounts();
				GuestCounts gustcounts = new GuestCounts();
				List<GuestCount> gustcountslist = new ArrayList<GuestCount>();					
				for (com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomRates.RoomRate.GuestCounts.GuestCount guestCount : rr.getGuestCounts().getGuestCounts()) {
					GuestCount lgc = new GuestCount();
					lgc.setAge(guestCount.getAge());
					lgc.setAgeBucket(guestCount.getAgeBucket());
					lgc.setAgeQualifyingCode(guestCount.getAgeQualifyingCode());
					lgc.setCount(guestCount.getCount());					
					gustcountslist.add(lgc);
				}
				gustcounts.setGuestCounts(gustcountslist);
				lrr.setGuestCounts(gustcounts);
				lrratelist.add(lrr);
			}
			troomrates.setRoomRates(lrratelist);
			troomstay.setRoomRates(troomrates);		

			BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
			tbasic.setHotelCode(rs.getBasicPropertyInfo().getHotelCode());

			HotelOverview ho = hoteldao.getHotelOverviewByVendorID(rs.getBasicPropertyInfo().getHotelCode());
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
			}
			List<String> images = hotelimagesDao.getImagesByVendorID(rs.getBasicPropertyInfo().getHotelCode());//get image url .. as string list..
			tbasic.setHotelimages(images);

			List<Facility> facilities=hotelFacilityDao.getDescriptionByVendorProperty(rs.getBasicPropertyInfo().getHotelCode(), "property");
			tbasic.setHotelAmenities(facilities);

			troomstay.setBasicPropertyInfo(tbasic);		
			TPAExtensions roomstayextension = new TPAExtensions();			
			HotelBasicInformation roomstayhotelbasic = new HotelBasicInformation();
			if(rs.getTPAExtensions()!=null)
			{
				if(rs.getTPAExtensions().getHotelBasicInformation()!=null)
				{
					roomstayhotelbasic.setRank(rs.getTPAExtensions().getHotelBasicInformation().getRank());
					roomstayhotelbasic.setHotelType(rs.getTPAExtensions().getHotelBasicInformation().getHotelType());
				}
				Reviews roomstayreviews = new Reviews();
				if(rs.getTPAExtensions().getHotelBasicInformation().getReviews()!=null)
				{
					roomstayreviews.setReviewCount(rs.getTPAExtensions().getHotelBasicInformation().getReviews().getReviewCount());
					roomstayreviews.setReviewRating(rs.getTPAExtensions().getHotelBasicInformation().getReviews().getReviewRating());
				}
				roomstayhotelbasic.setReviews(roomstayreviews);			

				DeepLinkInformation roomstaydeeplink = new DeepLinkInformation();
				if(rs.getTPAExtensions().getDeepLinkInformation()!=null)
				{
					roomstaydeeplink.setOverviewURL(rs.getTPAExtensions().getDeepLinkInformation().getOverviewURL());
					roomstaydeeplink.setOverviewURL(rs.getTPAExtensions().getDeepLinkInformation().getOverviewURL());
				}
				roomstayextension.setStopSell(rs.getTPAExtensions().isStopSell());
				roomstayextension.setDeepLinkInformation(roomstaydeeplink);
			}
			troomstay.setTPAExtensions(roomstayextension);
			roomstaies.add(troomstay);
		} 
		troomstays.setRoomStaies(roomstaies);
		targetRes.setRoomStays(troomstays);
		HotelsInfo hi = new HotelsInfo();
		if(desiares.getTPAExtensions()!=null && desiares.getTPAExtensions().getHotelsInfo()!= null)
		{
			hi.setTotal(desiares.getTPAExtensions().getHotelsInfo().getTotal());
			hi.setMinPrice(desiares.getTPAExtensions().getHotelsInfo().getMinPrice());		
			hi.setMaxPrice(desiares.getTPAExtensions().getHotelsInfo().getMaxPrice());
			hi.setDeals(desiares.getTPAExtensions().getHotelsInfo().getDeals());
			hi.setAvailable(desiares.getTPAExtensions().getHotelsInfo().getAvailable());
		}
		TPAExtensions resext = new TPAExtensions();
		resext.setHotelsInfo(hi);
		targetRes.setTPAExtensions(resext);
		return targetRes;
	}

	public APIHotelMap initHotelsMap(com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS desiares) throws HibernateException 
	{	
		long addionalhotelislindex = 1000000;
		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();
		TreeMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> tgRoomStaysMap = new TreeMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay>();

		APIHotelMap apihotelmap = new APIHotelMap();
		HotelsInfo thotelsinfo =  new HotelsInfo();
		if(desiares == null)
			return apihotelmap;

		for(com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay rs:desiares.getRoomStays().getRoomStaies()){ 
			tgRoomStaysMap.put(rs.getBasicPropertyInfo().getHotelCode(), rs);	
			RoomStay troomstay = new RoomStay();
			RoomRates troomrates = new RoomRates();
			List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
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
			HotelOverview ho = hoteldao.getHotelOverviewByVendorID(rs.getBasicPropertyInfo().getHotelCode());
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
				tbasic.setBasePrice(base);
				tbasic.setBasePriceWithoutMarkup(base);
				tbasic.setBookingPrice(base);
			}
			troomstay.setBasicPropertyInfo(tbasic);
			List<String> images = hotelimagesDao.getImagesByVendorID(rs.getBasicPropertyInfo().getHotelCode());//get image url .. as string list..
			tbasic.setHotelimages(images);

			List<Facility> facilities=hotelFacilityDao.getDescriptionByVendorProperty(rs.getBasicPropertyInfo().getHotelCode(), "property");
			tbasic.setHotelAmenities(facilities);

			troomstay.setBasicPropertyInfo(tbasic);
			Islhotelmapping islhotelmapping = null;
			try {			

				islhotelmapping = islhotelmappingDao.getHotelByTGVendorID(rs.getBasicPropertyInfo().getHotelCode());
				//compare rate and replace the roomstay item	
				if(islhotelmapping != null && islhotelmapping.getISLVendorID() != null)
				{					
					roomStaysMap.put(islhotelmapping.getISLVendorID(), troomstay);
				}
				else
				{
					addionalhotelislindex++;								
					roomStaysMap.put(String.valueOf(addionalhotelislindex), troomstay);
				}

			} catch (HibernateException e) {
				addionalhotelislindex++;				
				roomStaysMap.put(String.valueOf(addionalhotelislindex), troomstay);

			}
		} 
		apihotelmap.setRoomStays(roomStaysMap);	
		return apihotelmap;
	}

	public APIHotelMap compareandAddHotelsMap(APIHotelMap apihotelmap, com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS desiares) throws HibernateException 
	{	
		TreeMap<String, RoomStay> roomStaysMap = apihotelmap.getRoomStays();
		long addionalhotelislindex = 2000000;		
		HotelsInfo thotelsinfo =  new HotelsInfo();
		if(desiares == null || desiares.getRoomStays()== null)
			return apihotelmap;		

		for(com.tayyarah.api.hotel.reznext.model.RoomStayType rs:desiares.getRoomStays().getRoomStaies()){ 
			RoomStay troomstay = new RoomStay();
			BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
			if(rs.getBasicPropertyInfo() != null)
			{
				tbasic.setHotelCode(String.valueOf(rs.getBasicPropertyInfo().getHotelCode()));
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
			}			
			if(rs.getBasicPropertyInfo().getHotelImages() != null && rs.getBasicPropertyInfo().getHotelImages().getImagePaths() != null && rs.getBasicPropertyInfo().getHotelImages().getImagePaths().size()>0)
			{
				tbasic.setHotelimages(rs.getBasicPropertyInfo().getHotelImages().getImagePaths());
				tbasic.setImageurl(rs.getBasicPropertyInfo().getHotelImages().getImagePaths().get(0));
			}
			List<Facility> facilities = new ArrayList<Facility>();
			if(rs.getBasicPropertyInfo().getHotelFeatures() != null && rs.getBasicPropertyInfo().getHotelFeatures().getFeatureDescriptions() != null && rs.getBasicPropertyInfo().getHotelFeatures().getFeatureDescriptions().size()>0)
			{

			}			
			tbasic.setHotelAmenities(facilities);
			troomstay.setBasicPropertyInfo(tbasic);

			TPAExtensions roomstayextension = new TPAExtensions();			
			HotelBasicInformation roomstayhotelbasic = new HotelBasicInformation();
			troomstay.setTPAExtensions(roomstayextension);
			Islhotelmapping islhotelmapping= null;
			try {			
				//compare rate and replace the roomstay item	
				if(islhotelmapping != null && islhotelmapping.getISLVendorID() != null)					
				{
					//comparable element..
					RoomStay availableroom = roomStaysMap.get(islhotelmapping.getISLVendorID());
					//compare available room with room to be added
					if(troomstay.getBasicPropertyInfo().getBasePrice().compareTo(availableroom.getBasicPropertyInfo().getBasePrice()) == -1)
						roomStaysMap.put(islhotelmapping.getISLVendorID(), troomstay);
				}
				else
				{
					addionalhotelislindex++;								
					roomStaysMap.put(String.valueOf(addionalhotelislindex), troomstay);
				}

			} catch (HibernateException e) {
				addionalhotelislindex++;				
				roomStaysMap.put(String.valueOf(addionalhotelislindex), troomstay);

			}
		} 
		apihotelmap.setRoomStays(roomStaysMap);		
		return apihotelmap;

	}	
}