package com.tayyarah.hotel.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.hotel.dao.HotelMarkupDao;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.model.HotelMarkupCommissionDetails;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;


public class HotelMarkUpUtil {
	public static final Logger logger = Logger.getLogger(HotelMarkUpUtil.class);

	public static TreeMap<String, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> applyMarkUpOnHotelResult(HotelSearchCommand hs, List<HotelMarkup> markups, 
			TreeMap<String, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> rsmap) throws HibernateException, IOException, ParseException
	{
		TreeMap<String, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> tsmap = new TreeMap<String, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>();
		for (Map.Entry<String, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> entry : rsmap.entrySet()) {
			String key = entry.getKey();
			com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = entry.getValue();

			rs = applyMarkUpOnHotelRoomDetail(hs, markups,rs);
			tsmap.put(key, rs);
		}
		return tsmap;
	}


	public static com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay applyMarkUpOnHotel(HotelSearchCommand hs, List<HotelMarkup> markups, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws HibernateException, IOException
	{		
		BigDecimal baseAmount = (rs.getBasicPropertyInfo().getBasePrice() != null) ? rs.getBasicPropertyInfo().getBasePrice() : BigDecimal.valueOf(0);
		BigDecimal totalmarkUpAmount = BigDecimal.valueOf(0);
		for (HotelMarkup hotelMarkup : markups) {
			boolean isappliable = isMarkUpApplicable(hs, hotelMarkup, rs);
			BigDecimal markUpAmount = BigDecimal.valueOf(0);
			if(isappliable)
			{
				switch (hotelMarkup.getIsaccumulative()) {
				case (byte)0:					
					break;
				case (byte)1:
					switch (hotelMarkup.getIsfixedAmount()) {
					case (byte)0:
						BigDecimal percentagemarkup = hotelMarkup.getMarkupAmount();
					BigDecimal amountcalculated = percentagemarkup.multiply(baseAmount).divide(BigDecimal.valueOf(100));
					markUpAmount = amountcalculated;
					break;
					case (byte)1:
						markUpAmount = hotelMarkup.getMarkupAmount();
					break;
					default:
						break;
					}
				break;
				default:
					break;
				}
				//Adding markup to total markup
				totalmarkUpAmount = totalmarkUpAmount.add(markUpAmount);
			}
		}		
		baseAmount = baseAmount.add(totalmarkUpAmount);
		rs.getBasicPropertyInfo().setBasePrice(baseAmount);
		return rs;
	}

	public static com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay applyMarkUpOnHotel(HotelSearchCommand hs, HotelMarkupCommissionDetails hotelmarkupCommissionDetails, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws HibernateException, IOException
	{		
		BigDecimal baseAmount = (rs.getBasicPropertyInfo().getBasePrice() != null) ? rs.getBasicPropertyInfo().getBasePrice() : BigDecimal.valueOf(0);
		BigDecimal totalmarkUpAmount = BigDecimal.valueOf(0);	
		logger.info("Applying markup-------hotel name----:"+  rs.getBasicPropertyInfo().getHotelName());		
		logger.info("Applying markup-------basic amount----:"+ baseAmount);

		for (Entry<String, List<HotelMarkup>> entry : hotelmarkupCommissionDetails.getMarkups().entrySet()) {
			String companyId = entry.getKey();
			List<HotelMarkup> markups = (List<HotelMarkup>) entry.getValue();
			for (HotelMarkup hotelMarkup : markups) {
				boolean isappliable = isMarkUpApplicable(hs, hotelMarkup, rs);
				logger.info(hotelMarkup.getName()+"markup--isappliable-----"+isappliable+"----for ----:"+ rs.getBasicPropertyInfo().getHotelName());			
				logger.info(hotelMarkup.getName()+"markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");			
				logger.info(hotelMarkup.getName()+"markup--getIsfixedAmount----"+hotelMarkup.getIsfixedAmount()+"----");			

				BigDecimal markUpAmount = BigDecimal.valueOf(0);
				if(isappliable)
				{
					switch (hotelMarkup.getIsaccumulative()) {
					case (byte)0:

						switch (hotelMarkup.getIsfixedAmount()) {
						case (byte)0:
							BigDecimal percentagemarkup = hotelMarkup.getMarkupAmount();
						BigDecimal amountcalculated = percentagemarkup.multiply(baseAmount).divide(BigDecimal.valueOf(100));
						markUpAmount = amountcalculated;
						break;
						case (byte)1:
							markUpAmount = hotelMarkup.getMarkupAmount();
						break;
						default:
							break;
						}
					break;
					case (byte)1:
						switch (hotelMarkup.getIsfixedAmount()) {
						case (byte)0:
							BigDecimal percentagemarkup = hotelMarkup.getMarkupAmount();
						BigDecimal amountcalculated = percentagemarkup.multiply(baseAmount).divide(BigDecimal.valueOf(100));
						markUpAmount = amountcalculated;
						break;
						case (byte)1:
							markUpAmount = hotelMarkup.getMarkupAmount();
						break;
						default:
							break;
						}
					break;
					default:
						break;
					}
					//Adding markup to total markup
					totalmarkUpAmount = totalmarkUpAmount.add(markUpAmount);
				}
			}
		}

		logger.info("Applying markup-------total markUpAmount----:"+ totalmarkUpAmount);
		baseAmount = baseAmount.add(totalmarkUpAmount);
		logger.info("Applying markup-------basic amount-after markup---:"+ baseAmount);
		rs.getBasicPropertyInfo().setBasePrice(baseAmount);
		return rs;
	}

	public static BigDecimal applyMarkUp(HotelMarkup hotelMarkup, BigDecimal baseAmount, int divider) throws HibernateException, IOException
	{
		return applyMarkUp(hotelMarkup, baseAmount, divider, 1);	
	}

	public static BigDecimal applyMarkUp(HotelMarkup hotelMarkup, BigDecimal baseAmount, int divider, int multiplier) throws HibernateException, IOException
	{
		BigDecimal markUpAmount = BigDecimal.valueOf(0);
		switch (hotelMarkup.getIsfixedAmount()) {
		case (byte)0:
			BigDecimal percentagemarkup = hotelMarkup.getMarkupAmount();
		BigDecimal amountcalculated = percentagemarkup.multiply(baseAmount).divide(BigDecimal.valueOf(100));
		markUpAmount = amountcalculated;
		break;
		case (byte)1:		
			try{
				markUpAmount = hotelMarkup.getMarkupAmount();				
				logger.info("fixed markup on amount "+markUpAmount+"----divider"+divider);	
			}
		catch(ArithmeticException ex)
		{
			markUpAmount = hotelMarkup.getMarkupAmount();
			logger.info("fixed markup on amount "+markUpAmount+"----divider"+divider);
		}
		break;
		default:
			break;
		}
		return markUpAmount;
	}
	public static com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay applyMarkUpOnHotelRoomDetail(HotelSearchCommand hs, List<HotelMarkup> markups, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws HibernateException, IOException, ParseException
	{

		RoomRates troomrates = new RoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();
		int noofdays = CommonUtil.getNoofStayDays(hs);
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			//Rates
			RateType lrates = new RateType();
			List<Rate> lratelist = new ArrayList<Rate>();					
			for (Rate rtype : rr.getRates().getRates()) {
				Rate lrate = rtype;				
				TotalType ratebase = rtype.getBase();	
				BigDecimal roomratebaseAmount = ratebase.getAmountBeforeTax();				
				BigDecimal roomratetotalmarkUpAmount = BigDecimal.valueOf(0);					
				for (HotelMarkup hotelMarkup : markups) {
					boolean isappliable = isMarkUpApplicable(hs, hotelMarkup, rs);
					logger.info(hotelMarkup.getName()+"\\\\\\\\markup--isappliable-----"+isappliable+"----for ----:"+ rs.getBasicPropertyInfo().getHotelName());			
					logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");			
					logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsfixedAmount----"+hotelMarkup.getIsfixedAmount()+"----");			
					logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");

					BigDecimal markUpAmount = BigDecimal.valueOf(0);
					if(isappliable)
					{
						markUpAmount = applyMarkUp(hotelMarkup, roomratebaseAmount, noofdays);
						//Adding markup to total markup
						roomratetotalmarkUpAmount = roomratetotalmarkUpAmount.add(markUpAmount);
					}
				}
				roomratebaseAmount = roomratebaseAmount.add(roomratetotalmarkUpAmount);
				ratebase.setAmountBeforeTax(roomratebaseAmount);
				lrate.setBase(ratebase);
				TPAExtensions rateplanext = rtype.getTPAExtensions();
				if(rateplanext != null && rtype.getTPAExtensions().getRate() != null && rtype.getTPAExtensions().getRate().getBase() != null && rtype.getTPAExtensions().getRate().getBase().getAmountBeforeTax() != null)
				{
					com.tayyarah.hotel.model.TPAExtensions.Rate rateext = rtype.getTPAExtensions().getRate();
					TotalType ltotext = rateext.getBase(); 
					BigDecimal roomrateextbaseAmount = rateext.getBase().getAmountBeforeTax();				
					BigDecimal roomrateexttotalmarkUpAmount = BigDecimal.valueOf(0);
					for (HotelMarkup hotelMarkup : markups) {
						boolean isappliable = isMarkUpApplicable(hs, hotelMarkup, rs);
						BigDecimal markUpAmount = BigDecimal.valueOf(0);
						if(isappliable)
						{
							markUpAmount = applyMarkUp(hotelMarkup, roomrateextbaseAmount, noofdays);
							//Adding markup to total markup
							roomrateexttotalmarkUpAmount = roomrateexttotalmarkUpAmount.add(markUpAmount);
						}
					}
					roomrateextbaseAmount = roomrateextbaseAmount.add(roomrateexttotalmarkUpAmount);
					ltotext.setAmountBeforeTax(roomrateextbaseAmount);				
					rateext.setBase(ltotext);
					rateplanext.setRate(rateext);
					lrate.setTPAExtensions(rateplanext);	
				}
				lratelist.add(lrate);	
			}
			lrates.setRates(lratelist);
			lrr.setRates(lrates);			
			lrratelist.add(lrr);
		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		return rs;
	}

	public static com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay applyMarkUpOnHotelRoomDetail(HotelSearchCommand hs, Map<String,List<HotelMarkup>> markupsmap, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws HibernateException, IOException, ParseException
	{
		int noofdays = CommonUtil.getNoofStayDays(hs);
		RoomRates troomrates = rs.getRoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			//Rates
			RateType lrates = new RateType();
			List<Rate> lratelist = new ArrayList<Rate>();		
			for (Rate rtype : rr.getRates().getRates()) {
				Rate lrate = rtype;				
				TotalType ratebase = rtype.getBase();	

				BigDecimal roomratebaseAmount = ratebase.getAmountBeforeTax();				
				BigDecimal roomratetotalmarkUpAmount = BigDecimal.valueOf(0);			
				logger.info("\\\\\\\\************************* applying markup--rateplan code----"+rr.getRatePlanCode()+"----");			
				logger.info("\\\\\\\\************************* applying markup--rateplan code----amtbefore tax"+roomratebaseAmount+"----");

				for (Entry<String, List<HotelMarkup>> entry : markupsmap.entrySet()) {
					String companyId = entry.getKey();
					List<HotelMarkup> markups = (List<HotelMarkup>) entry.getValue();	
					for (HotelMarkup hotelMarkup : markups) {
						boolean isappliable = isMarkUpApplicable(hs, hotelMarkup, rs);
						logger.info(hotelMarkup.getName()+"\\\\\\\\markup--isappliable-----"+isappliable+"----for ----:"+ rs.getBasicPropertyInfo().getHotelName());			
						logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");			
						logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsfixedAmount----"+hotelMarkup.getIsfixedAmount()+"----");			
						logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");

						BigDecimal markUpAmount = BigDecimal.valueOf(0);
						if(isappliable)
						{
							markUpAmount = applyMarkUp(hotelMarkup, roomratebaseAmount, noofdays);
							//Adding markup to total markup
							roomratetotalmarkUpAmount = roomratetotalmarkUpAmount.add(markUpAmount);
						}
					}
				}
				roomratebaseAmount = roomratebaseAmount.add(roomratetotalmarkUpAmount);
				ratebase.setAmountBeforeTax(roomratebaseAmount);
				logger.info("\\\\\\\\************************* applying markup----total markupamount--"+roomratetotalmarkUpAmount+"----");			
				logger.info("\\\\\\\\************************* applying markup--rateplan code----amtbefore tax markup amt---"+roomratebaseAmount+"----");			
				lrate.setBase(ratebase);
				TPAExtensions rateplanext = rtype.getTPAExtensions();
				if(rateplanext != null && rtype.getTPAExtensions().getRate() != null && rtype.getTPAExtensions().getRate().getBase() != null && rtype.getTPAExtensions().getRate().getBase().getAmountBeforeTax() != null)
				{
					com.tayyarah.hotel.model.TPAExtensions.Rate rateext = rtype.getTPAExtensions().getRate();
					TotalType ltotext = rateext.getBase(); 
					BigDecimal roomrateextbaseAmount = rateext.getBase().getAmountBeforeTax();				
					BigDecimal roomrateexttotalmarkUpAmount = BigDecimal.valueOf(0);
					for (Entry<String, List<HotelMarkup>> entry : markupsmap.entrySet()) {
						String companyId = entry.getKey();
						List<HotelMarkup> markups = (List<HotelMarkup>) entry.getValue();
						for (HotelMarkup hotelMarkup : markups) {
							boolean isappliable = isMarkUpApplicable(hs, hotelMarkup, rs);
							BigDecimal markUpAmount = BigDecimal.valueOf(0);
							if(isappliable)
							{
								markUpAmount = applyMarkUp(hotelMarkup, roomrateextbaseAmount, noofdays);
								//Adding markup to total markup
								roomrateexttotalmarkUpAmount = roomrateexttotalmarkUpAmount.add(markUpAmount);
							}
						}
					}
					roomrateextbaseAmount = roomrateextbaseAmount.add(roomrateexttotalmarkUpAmount);
					ltotext.setAmountBeforeTax(roomrateextbaseAmount);				
					rateext.setBase(ltotext);
					rateplanext.setRate(rateext);
					lrate.setTPAExtensions(rateplanext);	
				}
				lratelist.add(lrate);	
			}
			lrates.setRates(lratelist);
			lrr.setRates(lrates);			
			lrratelist.add(lrr);
		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		return rs;
	}

	public static boolean isMarkUpApplicableNormal(HotelSearchCommand hs, HotelMarkup hotelMarkup, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		String checkindatetext = hotelMarkup.getHotelCheckinDate().toString();
		String checkoutdatetext = hotelMarkup.getHotelCheckoutDate().toString();
		if(hotelMarkup.getHotelCity() != null && hotelMarkup.getHotelCountry() != null && hotelMarkup.getHotelName() != null && hotelMarkup.getHotelChain() != null)
		{
			if(rs.getBasicPropertyInfo() != null && rs.getBasicPropertyInfo().getAddress() != null)
			{
				if(rs.getBasicPropertyInfo().getAddress() != null)
				{
					if(hotelMarkup.getHotelCity().equals("ALL")||hotelMarkup.getHotelCity().equals(rs.getBasicPropertyInfo().getAddress().getCityName()))
						return true;
					if(hotelMarkup.getHotelCountry().equals("ALL")||hotelMarkup.getHotelCountry().equals(rs.getBasicPropertyInfo().getAddress().getCountryName()))
						return true;
				}
				if(hotelMarkup.getHotelName().equals("ALL")||hotelMarkup.getHotelName().equals(rs.getBasicPropertyInfo().getHotelName()))
					return true;
				if(hotelMarkup.getHotelChain().equals("ALL")||hotelMarkup.getHotelChain().equals(rs.getBasicPropertyInfo().getChainName()))
					return true;

			}
			if(checkindatetext.equals("0001-01-01 00:00:00")||checkindatetext.contains(hs.getDatestart()))
				return true;
			if(checkoutdatetext.equals("0001-01-01 00:00:00")||checkoutdatetext.contains(hs.getDateend()))
				return true;
		}
		return false;

	}
	
	public static boolean checkHotelChain(HotelMarkup hotelMarkup, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		if(hotelMarkup.getHotelChain() == null)
			return true;
		else if(hotelMarkup.getHotelChain().equalsIgnoreCase("ALL"))
			return true;
		else		
			return false;

	}
	public static boolean checkHotelCity(HotelMarkup hotelMarkup, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		if(hotelMarkup.getHotelCity() == null)
			return true;
		else if(hotelMarkup.getHotelCity().equalsIgnoreCase("ALL"))
			return true;
		else if(rs.getBasicPropertyInfo().getAddress() != null && rs.getBasicPropertyInfo().getAddress().getCityName() != null && (hotelMarkup.getHotelCity().equalsIgnoreCase(rs.getBasicPropertyInfo().getAddress().getCityName())))		
			return true;			
		else
			return false;
	}
	public static boolean checkHotelCountry(HotelMarkup hotelMarkup, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		if(hotelMarkup.getHotelCountry() == null)
			return true;
		else if(hotelMarkup.getHotelCountry().equalsIgnoreCase("ALL"))
			return true;
		else if(rs.getBasicPropertyInfo().getAddress() != null && rs.getBasicPropertyInfo().getAddress().getCountryName() != null && (hotelMarkup.getHotelCountry().equalsIgnoreCase(rs.getBasicPropertyInfo().getAddress().getCountryName().getValue())))		
			return true;			
		else
			return false;
	}
	public static boolean checkHotelName(HotelMarkup hotelMarkup, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		if(hotelMarkup.getHotelName() == null)
			return true;
		else if(hotelMarkup.getHotelName().equalsIgnoreCase("ALL"))
			return true;
		else if(rs.getBasicPropertyInfo().getHotelName() != null && (hotelMarkup.getHotelName().equalsIgnoreCase(rs.getBasicPropertyInfo().getHotelName())))		
			return true;			
		else
			return false;
	}
	public static boolean checkDestinationType(HotelMarkup hotelMarkup, HotelSearchCommand hs, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		if(hotelMarkup.getDestinationType() == null)
			return true;
		else if(hotelMarkup.getDestinationType().equalsIgnoreCase("ALL"))
			return true;		
		else if(hotelMarkup.getDestinationType().equalsIgnoreCase(hs.getDestinationType()))		
			return true;		
		else
			return false;
	}
	public static String getSystemContryCode()
	{
		return "IN";
	}
	public static boolean checkCheckinDate(HotelMarkup hotelMarkup, HotelSearchCommand hs, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{	
		logger.info("#################### isMarkUpApplicable---- checkCheckinDate -markupcheckin date "+hotelMarkup.getHotelCheckinDate());
		logger.info("#################### isMarkUpApplicable---- checkCheckinDate -markupcheckin date string"+hotelMarkup.getHotelCheckinDate().toString());
		logger.info("#################### isMarkUpApplicable---- checkCheckinDate -search date "+hs.getDatestart());

		if(hotelMarkup.getHotelCheckinDate() == null)
			return true;
		else if(hotelMarkup.getHotelCheckinDate().toString().contains("0002-12-31"))
			return true;
		else if(hotelMarkup.getHotelCheckinDate().toString().contains(hs.getDatestart()))		
			return true;			
		else
			return false;
	}
	public static boolean checkCheckoutDate(HotelMarkup hotelMarkup, HotelSearchCommand hs, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		logger.info("#################### isMarkUpApplicable---- checkCheckoutDate -markupcheckin date "+hotelMarkup.getHotelCheckinDate());
		logger.info("#################### isMarkUpApplicable---- checkCheckoutDate -markupcheckin date string"+hotelMarkup.getHotelCheckinDate().toString());

		logger.info("#################### isMarkUpApplicable---- checkCheckoutDate -search date "+hs.getDatestart());

		if(hotelMarkup.getHotelCheckoutDate() == null)
			return true;
		else if(hotelMarkup.getHotelCheckoutDate().toString().contains("0002-12-31"))
			return true;
		else if(hotelMarkup.getHotelCheckoutDate().toString().contains(hs.getDateend()))		
			return true;			
		else
			return false;
	}
	public static boolean checkPromoFareRange(HotelMarkup hotelMarkup, HotelSearchCommand hs, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		if(hotelMarkup.getPromofareStartDate() == null || hotelMarkup.getPromofareEndDate() == null)
			return true;
		if(hotelMarkup.getPromofareStartDate().equalsIgnoreCase("ALL") && hotelMarkup.getPromofareEndDate().equalsIgnoreCase("ALL"))
			return true;
		else if(!hotelMarkup.getPromofareStartDate().equalsIgnoreCase("ALL") && !hotelMarkup.getPromofareEndDate().equalsIgnoreCase("ALL"))
		{
			long psd=getTimeStamp(hotelMarkup.getPromofareStartDate());
			long ped=getTimeStamp(hotelMarkup.getPromofareEndDate());
			long adt=getTimeStamp(hs.getDatestart());
			if (psd<=adt&&adt<=ped) {
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}		
	}

	public static long getTimeStamp(String inputDate){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); //  dd-MM-yyyy
		long timestamp1 =1111111111;
		try {
			Date d = formatter.parse(inputDate);
			timestamp1 = d.getTime();
			System.out.println("timestamp1 :"+timestamp1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}finally{
			return timestamp1;
		}

	}
	public static boolean isMarkUpApplicable(HotelSearchCommand hs, HotelMarkup hotelMarkup, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
	{
		try
		{
			boolean isapplicable = true;
			if(hotelMarkup.isDynamicMarkup() != null && hotelMarkup.isDynamicMarkup())				
				return true;


			isapplicable = (isapplicable && checkHotelChain(hotelMarkup, rs));
			logger.info("#################### isMarkUpApplicable---- checkHotelChain -"+isapplicable);

			isapplicable = (isapplicable && checkHotelCity(hotelMarkup, rs));
			logger.info("#################### isMarkUpApplicable---- checkHotelCity -"+isapplicable);

			isapplicable = (isapplicable && checkHotelCountry(hotelMarkup, rs));
			logger.info("#################### isMarkUpApplicable---- checkHotelCountry -"+isapplicable);

			isapplicable = (isapplicable && checkHotelName(hotelMarkup, rs));
			logger.info("#################### isMarkUpApplicable---- checkHotelName -"+isapplicable);

			isapplicable = (isapplicable && checkCheckinDate(hotelMarkup, hs, rs));
			logger.info("#################### isMarkUpApplicable---- checkCheckinDate -"+isapplicable);

			isapplicable = (isapplicable && checkCheckoutDate(hotelMarkup, hs, rs));
			logger.info("#################### isMarkUpApplicable---- checkCheckoutDate -"+isapplicable);

			isapplicable = (isapplicable && checkDestinationType(hotelMarkup, hs, rs));
			logger.info("#################### isMarkUpApplicable---- checkDestinationType -"+isapplicable);

			isapplicable = (isapplicable && checkPromoFareRange(hotelMarkup, hs, rs));
			logger.info("#################### isMarkUpApplicable---- checkPromoFareRange -"+isapplicable);

			return isapplicable;
		}
		catch(Exception e)
		{
			return false;			
		}

	}

	public static com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay applyMarkUpOnRoomDetail(HotelMarkupDao hotelMarkupDao, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws HibernateException, IOException
	{		
		return rs;
	}
	
	public static void addDynamicMarkup(AppKeyVo appKeyVo,List<HotelMarkup> MArkUplist,String markupAmount){
		//set to this hotelMarkup object as i set for flights below
		HotelMarkup hotelMarkup = new HotelMarkup();//set to this object as i set for flights below
		hotelMarkup.setName("Dynamic markup");
		hotelMarkup.setIsaccumulative((byte)0);
		hotelMarkup.setConfigname("Dynamic markup");
		hotelMarkup.setIsfixedAmount((byte)1);

		hotelMarkup.setHotelName("ALL");
		hotelMarkup.setHotelCountry("ALL");
		hotelMarkup.setHotelCity("ALL");
		hotelMarkup.setHotelChain("ALL");
		hotelMarkup.setCompanyId(appKeyVo.getCompanyId());
		hotelMarkup.setConfigId(appKeyVo.getConfigId());
		hotelMarkup.setMarkupAmount(new BigDecimal(markupAmount));
		hotelMarkup.setId(Integer.valueOf("0"));
		hotelMarkup.setPositionMarkup(Integer.valueOf("1"));


		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		//0001-01-01 00:00:00

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

		Date date1 = new Timestamp(new Date().getTime());
		Date date2 = new Timestamp(new Date().getTime());
		try {
			date1 = sdf.parse("0001-01-01 00:00:00");
			date2 = sdf.parse("0001-01-01 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		hotelMarkup.setDynamicMarkup(true);
		hotelMarkup.setHotelCheckinDate(date1);
		hotelMarkup.setHotelCheckoutDate(date2);
		MArkUplist.add(hotelMarkup);
	}
	
}

