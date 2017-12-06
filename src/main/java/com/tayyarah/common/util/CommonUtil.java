package com.tayyarah.common.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.hibernate.HibernateException;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tayyarah.api.orderrow.rm.structure.HotelOrderRowRmConfigStruct;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.RmConfigModel;
import com.tayyarah.common.exception.CommonException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.gstconfig.entity.HotelGstTaxConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CommissionDetails;
import com.tayyarah.common.servicetaxconfig.entity.HotelServiceTaxConfig;
import com.tayyarah.common.util.enums.CommonBookingStatusEnum;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.entity.HotelOrderCancellationPolicy;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderHotelData;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRowCancellation;
import com.tayyarah.hotel.entity.HotelOrderRowCommission;
import com.tayyarah.hotel.entity.HotelOrderRowGstTax;
import com.tayyarah.hotel.entity.HotelOrderRowMarkup;
import com.tayyarah.hotel.entity.HotelOrderRowServiceTax;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelCancelRequest;
import com.tayyarah.hotel.model.APIHotelCancelResponse;
import com.tayyarah.hotel.model.AmountPercentType;
import com.tayyarah.hotel.model.AmountType.Discount;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelBookCommand.Profile;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelSearchCommand.GuestCount;
import com.tayyarah.hotel.model.OTAHotelResRS.CancellationInformations.CancellationInformation;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.RoomTypeType;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelMarkUpUtil;
import com.tayyarah.user.dao.FrontUserDao;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;


@SuppressWarnings("deprecation")
public class CommonUtil {
	public static final Logger logger = Logger.getLogger(CommonUtil.class);

	public static HotelSearchCommand initSearchDestinationType(HotelSearchCommand hs, CompanyDao cdao)
	{
		hs.setDestinationType("ALL");
		try{
			try {
				String biilingcountry = cdao.getBillingCoyuntry();
				if(biilingcountry.equalsIgnoreCase(hs.getCountry())){
					hs.setDestinationType("Domestic");
				}else{
					hs.setDestinationType("International");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception ",e);
			}
		}catch (Exception e) {
			logger.error("Exception ",e);		  
		}
		return hs;
	}

	public static BigDecimal getMarkUpAmount(APIHotelBook apibook)
	{
		try
		{			
			BigDecimal apiFinalAmt =  apibook.getApiRate().getPayableAmt().divide(new BigDecimal(getNoofStayDays(apibook.getSearch())));
			BigDecimal baseFinalAmt =  apibook.getBaseRate().getPayableAmt().divide(new BigDecimal(getNoofStayDays(apibook.getSearch())));
			logger.info("baseFinalAmt " + baseFinalAmt);
			logger.info("apiFinalAmt " + apiFinalAmt);
			BigDecimal markupamount = AmountRoundingModeUtil.roundingModeForHotel(baseFinalAmt).subtract(AmountRoundingModeUtil.roundingModeForHotel(apiFinalAmt));
			logger.info("getMarkUpAmount markupamount " + markupamount);
			return markupamount;
		}
		catch(NumberFormatException e)
		{			
			logger.error("getMarkUpAmount NumberFormatException= ",e);
		}
		catch(Exception e)
		{
			logger.error("getMarkUpAmount Exception= ",e);
		}
		return new BigDecimal("0");
	}

	public static int getNoofStayDays() throws java.text.ParseException
	{

		DateTimeFormatter dateStringFormat = DateTimeFormat
				.forPattern("yyyy-MM-dd");
		DateTime firstTime = dateStringFormat.parseDateTime("2016-03-31");
		DateTime secondTime = dateStringFormat.parseDateTime("2016-04-03");
		int days = Days.daysBetween(new LocalDate(firstTime),
				new LocalDate(secondTime)).getDays();		
		return days;
	}

	public static String getCancellaionDTFormat(String dtstr)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		if(dtstr != null)
		{
			try {
				DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = originalFormat.parse(dtstr);
				String formattedDate = sdf.format(date);
				return formattedDate;
			} catch (ParseException e) {
				e.printStackTrace();
				return sdf.format(cal.getTime());

			}
		}
		else
		{
			return sdf.format(cal.getTime());
		}
	}


	//created by saumya for comparing db date with today date at 06/02/2017
	public static int compareDbDateWithTodayDate(Date date)
	{

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate=format.format(date);
		String todayDate = format.format(Calendar.getInstance().getTime());
		if(todayDate.compareTo(formattedDate) == 0){
			return 0;
		}
		else if (todayDate.compareTo(formattedDate)>0) {
			return -1;
		}
		else{
			return 1;
		}
	}

	public static HashMap getDateTimeMapFromTimestamp(Timestamp timestamp)
	{
		Date date = new Date(timestamp.getTime());		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
		HashMap<String, String> datetimemap = new HashMap<String, String>();
		datetimemap.put("date", simpleDateFormat.format(date));
		datetimemap.put("time", simpleTimeFormat.format(date));
		return datetimemap;
	}

	public static int getNoofStayDays(HotelSearchCommand hsc) throws java.text.ParseException
	{
		DateTimeFormatter dateStringFormat = DateTimeFormat
				.forPattern("yyyy-MM-dd");
		DateTime firstTime = dateStringFormat.parseDateTime(hsc.getDatestart());
		DateTime secondTime = dateStringFormat.parseDateTime(hsc.getDateend());
		int days = Days.daysBetween(new LocalDate(firstTime),
				new LocalDate(secondTime)).getDays();		
		return days;	
	}

	public static int getNoofStayDaysxx(String startdate, String enddate) throws java.text.ParseException
	{
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date date = sdf.parse(startdate);
		cal1.setTime(date);
		date = sdf.parse(enddate);
		cal2.setTime(date);		
		int noofdays = daysBetween(cal1.getTime(),cal2.getTime());		
		return noofdays;
	}

	public static int getNoofStayDaysDBSQL(String startdate, String enddate) throws java.text.ParseException
	{
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		Date date = sdf.parse(startdate);
		cal1.setTime(date);
		date = sdf.parse(enddate);
		cal2.setTime(date);
		int noofdays = daysBetween(cal1.getTime(),cal2.getTime());		
		return noofdays;
	}
	public static Date getFormattedDateFromStringSQL(String dateStr) throws java.text.ParseException
	{		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		Date date = sdf.parse(dateStr);
		return date;
	}
	public static String  convertDateToStringDate(Date date){	
		String stringDate = null;
		try {
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			stringDate=formatter1.format(date);
		} catch (Exception e) {
			logger.error("Exception",e);
		}
		return stringDate;
	}

	public static int daysBetween(Date d1, Date d2){
		long diff = d1.getTime() - d2.getTime();		
		return (int) diff;
	}

	public static String format(String unformattedXml) {
		try {
			final Document document = parseXmlFile(unformattedXml);
			OutputFormat format = new OutputFormat(document);
			format.setLineWidth(65);
			format.setIndenting(true);
			format.setIndent(2);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);
			return out.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private static Document parseXmlFile(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> getRoomTypeCodeList(List<HotelBookCommand.RoomRateType> roomratetypes)
	{
		//roomtypecodetext=13456,13457,13458..
		List<String> roomTypeCodeList =new ArrayList<String>();
		for (HotelBookCommand.RoomRateType roomratetype : roomratetypes) {
			roomTypeCodeList.add(roomratetype.getRoomTypeCode());
		}
		return roomTypeCodeList;
	}	
	public static List<String> getRoomTypeCodeList(String roomtypecodetext)
	{
		//roomtypecodetext=13456,13457,13458..
		List<String> roomTypeCodeList =new ArrayList<String>();
		if(roomtypecodetext == null || roomtypecodetext.length() == 0)
			return roomTypeCodeList;
		else
		{
			String temp = roomtypecodetext;
			while(temp.length() >= 2)
			{
				if(!temp.contains(","))
				{
					roomTypeCodeList.add(temp);
					temp = "";
					break;
				}
				else
				{
					int roomTypeCodeEndindex = temp.indexOf(",");
					String roomTypeCode = temp.substring(0,roomTypeCodeEndindex-1);
					temp = temp.substring(roomTypeCodeEndindex+1);
					roomTypeCodeList.add(roomTypeCode);
				}
			}

		}
		return roomTypeCodeList;
	}	

	public static List<HotelOrderRowMarkup> insertAndGetHotelOrderRowMarkups(APIHotelBook apiHotelBook, HotelOrderRow hotelOrderRow, HotelOrderDao hotelOrderDao) throws HibernateException, Exception  {
		List<HotelOrderRowMarkup> hotelOrderRowMarkups = new ArrayList<HotelOrderRowMarkup>();
		HotelOrderRow hotelOrderRowDB = hotelOrderDao.getHotelOrderRow(hotelOrderRow.getOrderReference());
		int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
		for (Entry<String, BigDecimal> entry : apiHotelBook.getBookRes().getMarkupmap().entrySet()) {
			String companyId = entry.getKey();
			// Set MarkUp (Amount * noofdays)
			BigDecimal totalMarkup = entry.getValue().multiply(new BigDecimal(noofdays));
			HotelOrderRowMarkup hotelOrderRowMarkup = new HotelOrderRowMarkup();
			hotelOrderRowMarkup.setCompanyId(companyId);
			hotelOrderRowMarkup.setMarkUp(totalMarkup);


			hotelOrderRowMarkup.setHotelOrderRow(hotelOrderRowDB);
			try {
				hotelOrderDao.insertMarkupDetails(hotelOrderRowMarkup);
				hotelOrderRowMarkups.add(hotelOrderRowMarkup);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("hotelOrderRow data hotelOrderRowMarkup save-----------Exception---:"+e.getMessage());
				//throw new HotelException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}

		}
		return hotelOrderRowMarkups;
	}
	/*public static List<HotelOrderRowMarkup> insertAndGetHotelOrderRowMarkups(APIHotelBook apiHotelBook, HotelOrderRow hotelOrderRow, HotelOrderDao hotelOrderDao) throws HibernateException, Exception  {
		List<HotelOrderRowMarkup> hotelOrderRowMarkups = new ArrayList<HotelOrderRowMarkup>();
		HotelOrderRow hotelOrderRowDB = hotelOrderDao.getHotelOrderRow(hotelOrderRow.getOrderReference());
		int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
		for (Entry<String, BigDecimal> entry : apiHotelBook.getBookRes().getMarkupmap().entrySet()) {
			String companyId = entry.getKey();
			BigDecimal totalMarkup = entry.getValue().multiply(new BigDecimal(noofdays));
			HotelOrderRowMarkup hotelOrderRowMarkup = new HotelOrderRowMarkup();
			hotelOrderRowMarkup.setCompanyId(companyId);
			hotelOrderRowMarkup.setMarkUp(totalMarkup);
			hotelOrderRowMarkup.setHotelOrderRow(hotelOrderRowDB);
			try {
				hotelOrderDao.insertMarkupDetails(hotelOrderRowMarkup);
				hotelOrderRowMarkups.add(hotelOrderRowMarkup);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("hotelOrderRow data hotelOrderRowMarkup save-----------Exception---:"+e.getMessage());
				//throw new HotelException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}

		}
		return hotelOrderRowMarkups;
	}*/

	/*public static List<HotelOrderRowCommission> insertAndGetHotelOrderRowCommissions(Rate lrate, HotelOrderRow hotelOrderRow, HotelOrderDao hotelOrderDao)  {
		List<HotelOrderRowCommission> hotelOrderRowCommissions = new ArrayList<HotelOrderRowCommission>();
		for (Entry<String, CommissionDetails> entry : lrate.getCommissionmap().entrySet()) {
			String companyId = entry.getKey();
			CommissionDetails commissionDetails = entry.getValue();
			HotelOrderRowCommission hotelOrderRowCommission = new HotelOrderRowCommission();
			hotelOrderRowCommission.setCommission(commissionDetails.getCommissionAmount());
			hotelOrderRowCommission.setCommissionType(commissionDetails.getCommissionType());
			hotelOrderRowCommission.setCompanyId(commissionDetails.getCompanyId());
			hotelOrderRowCommission.setRateType(commissionDetails.getRateType());
			hotelOrderRowCommission.setHotelOrderRow(hotelOrderRow);
			hotelOrderRowCommission.setCommissionAmountValue(commissionDetails.getCommissionCalculatedAmount());
			try {
				hotelOrderDao.insertCommission(hotelOrderRowCommission);
				hotelOrderRowCommissions.add(hotelOrderRowCommission);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("hotelOrderRow hotelOrderRowCommission data save-----------Exception---:"+e.getMessage());
				//throw new HotelException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
		}
		return hotelOrderRowCommissions;
	}*/

	public static List<HotelOrderRowCommission> insertAndGetHotelOrderRowCommissions(Rate lrate, HotelOrderRow hotelOrderRow, HotelOrderDao hotelOrderDao)  {
		List<HotelOrderRowCommission> hotelOrderRowCommissions = new ArrayList<HotelOrderRowCommission>();
		for (Entry<String, CommissionDetails> entry : lrate.getCommissionmap().entrySet()) {
			String companyId = entry.getKey();
			CommissionDetails commissionDetails = entry.getValue();
			HotelOrderRowCommission hotelOrderRowCommission = new HotelOrderRowCommission();
			hotelOrderRowCommission.setCommission(commissionDetails.getCommissionAmount());
			hotelOrderRowCommission.setCommissionType(commissionDetails.getCommissionType());
			hotelOrderRowCommission.setCompanyId(commissionDetails.getCompanyId());
			hotelOrderRowCommission.setRateType(commissionDetails.getRateType());
			hotelOrderRowCommission.setHotelOrderRow(hotelOrderRow);
			hotelOrderRowCommission.setCommissionAmountValue(commissionDetails.getCommissionCalculatedAmount());
			try {
				hotelOrderDao.insertCommission(hotelOrderRowCommission);
				hotelOrderRowCommissions.add(hotelOrderRowCommission);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("hotelOrderRow hotelOrderRowCommission data save-----------Exception---:"+e.getMessage());
				//throw new HotelException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
		}
		return hotelOrderRowCommissions;
	}
	public static List<HotelOrderRowCommission> insertAndGetHotelOrderRowCommissions(com.tayyarah.hotel.model.OTAHotelResRS bookRes, HotelOrderRow hotelOrderRow, HotelOrderDao hotelOrderDao) throws HibernateException, Exception  {
		List<HotelOrderRowCommission> hotelOrderRowCommissions = new ArrayList<HotelOrderRowCommission>();
		HotelOrderRow hotelOrderRowDB = hotelOrderDao.getHotelOrderRow(hotelOrderRow.getOrderReference());

		for (Entry<String, CommissionDetails> entry : bookRes.getCommissionmap().entrySet()) {
			String companyId = entry.getKey();
			CommissionDetails commissionDetails = entry.getValue();
			HotelOrderRowCommission hotelOrderRowCommission = new HotelOrderRowCommission();
			hotelOrderRowCommission.setCommission(commissionDetails.getCommissionAmount());
			hotelOrderRowCommission.setCommissionType(commissionDetails.getCommissionType());
			hotelOrderRowCommission.setCompanyId(commissionDetails.getCompanyId());
			hotelOrderRowCommission.setRateType(commissionDetails.getRateType());
			hotelOrderRowCommission.setHotelOrderRow(hotelOrderRowDB);
			hotelOrderRowCommission.setCommissionAmountValue(commissionDetails.getCommissionCalculatedAmount());
			try {
				hotelOrderDao.insertCommission(hotelOrderRowCommission);
				hotelOrderRowCommissions.add(hotelOrderRowCommission);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("hotelOrderRow hotelOrderRowCommission data save-----------Exception---:"+e.getMessage());
				//throw new HotelException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
		}
		return hotelOrderRowCommissions;
	}

	private static String checkGetEmulatedUserById(HotelBookCommand hotelBookCommand) {
		if(hotelBookCommand.isEmulateFlag())
		{
			return hotelBookCommand.getEmulateByUserId();
		}
		return hotelBookCommand.getUserid();

	}

	public static HotelOrderRow hotelOrderRowInsertionData(HotelOrderDao hotelOrderDao, HotelTransactionTemp hotelTransactionTemp, HotelSearchCommand hotelSearchCommand, HotelBookCommand hotelBookCommand, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs, APIHotelBook apiHotelBook,CompanyConfigDAO companyConfigDAO, AppKeyVo appKeyVo, CompanyDao companyDao,FrontUserDao frontUserDao,RmConfigDetailDAO rmConfigDetailDAO) throws NumberFormatException, Exception
	{
		HotelOrderRow  	hotelOrderRow =new HotelOrderRow();
		com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate roomrate = rs.getRoomRates().getRoomRates().get(0);
		RoomTypeType roomtype = rs.getRoomTypes().getRoomTypes().get(0);
		RatePlanType rateplan = rs.getRatePlans().getRatePlan().get(0);

		Profile leaderprofile = hotelBookCommand.getProfiles().get(0);
		for (Profile p : hotelBookCommand.getProfiles()) {
			if(p.getProfileType().equals("1"))
			{
				leaderprofile = p;
				break;
			}
		}
		//com.lintas.hotel.model.RatePlanType selectedrateplan = rs.getRatePlans().getRatePlan().get(0);

		OrderCustomer orderCustomer = new OrderCustomer();
		orderCustomer.setAddress(leaderprofile.getCustomer().getAddress().getAddressLine().get(0));
		orderCustomer.setAddress2(leaderprofile.getCustomer().getAddress().getAddressLine().get(1));
		orderCustomer.setAge(leaderprofile.getCustomer().getAge());
		orderCustomer.setBirthday(leaderprofile.getCustomer().getDob());
		orderCustomer.setCity(leaderprofile.getCustomer().getAddress().getCityName());
		orderCustomer.setCountryId(hotelSearchCommand.getCountrycode());
		orderCustomer.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		orderCustomer.setEmail(leaderprofile.getCustomer().getEmail());
		orderCustomer.setFirstName(leaderprofile.getCustomer().getPersonName().getGivenName());
		orderCustomer.setGender(leaderprofile.getCustomer().getGender());
		orderCustomer.setLastName(leaderprofile.getCustomer().getPersonName().getSurname());
		orderCustomer.setMobile(leaderprofile.getCustomer().getTelephone().get(0).getPhoneNumber());
		orderCustomer.setPhone(leaderprofile.getCustomer().getTelephone().get(1).getPhoneNumber());
		orderCustomer.setTitle(leaderprofile.getCustomer().getPersonName().getNamePrefix());
		orderCustomer.setUpdatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		orderCustomer.setVersion(5);
		orderCustomer.setZip(leaderprofile.getCustomer().getAddress().getPostalCode());
		orderCustomer.setCompanyId(hotelBookCommand.getCompanyId());
		orderCustomer.setConfigId(hotelBookCommand.getConfigId());
		orderCustomer.setBookingType("Hotel");
		orderCustomer.setOrderId(hotelBookCommand.getOrderid());
		orderCustomer.setCreatedByUserId(Integer.parseInt(checkGetEmulatedUserById(hotelBookCommand)));

		logger.info("OrderCustomer data saved--------------:");
		//....................
		HotelOrderHotelData hotelOrderHotelData=new HotelOrderHotelData();
		BasicPropertyInfoType basicproperty = rs.getBasicPropertyInfo();
		logger.info("OrderCustomer data saved--------------basicproperty:--"+basicproperty);
		logger.info("OrderCustomer data saved--------------basicproperty:--"+basicproperty.toString());
		logger.info("OrderCustomer data saved--------------(basicproperty.getAddress():--"+(basicproperty.getAddress()));
		logger.info("OrderCustomer data saved--------------(basicproperty.getAddress().getAddressLines():--"+basicproperty.getAddress().getAddressLines());
		logger.info("OrderCustomer data saved--------------(basicproperty.getAddress().getAddressLines() size:--"+basicproperty.getAddress().getAddressLines().size());

		hotelOrderHotelData.setAddress1(((basicproperty.getAddress() == null) || (basicproperty.getAddress().getAddressLines() == null) || (basicproperty.getAddress().getAddressLines().size()<1)|| (basicproperty.getAddress().getAddressLines().get(0) == null))?"":basicproperty.getAddress().getAddressLines().get(0));
		hotelOrderHotelData.setAddress2(((basicproperty.getAddress() == null) || (basicproperty.getAddress().getAddressLines() == null) || (basicproperty.getAddress().getAddressLines().size()<2)|| (basicproperty.getAddress().getAddressLines().get(1) == null))?"":basicproperty.getAddress().getAddressLines().get(1));
		hotelOrderHotelData.setAddress3(((basicproperty.getAddress() == null) || (basicproperty.getAddress().getAddressLines() == null) || (basicproperty.getAddress().getAddressLines().size()<3)|| (basicproperty.getAddress().getAddressLines().get(2) == null))?"":basicproperty.getAddress().getAddressLines().get(2));
		hotelOrderHotelData.setChain((basicproperty.getChainCode()==null)?"Not Available":basicproperty.getChainCode());
		logger.info("OrderCustomer data saved--------------setChain:--");

		//hotelOrderHotelData.setCity(((basicproperty.getAddress()==null) || (basicproperty.getAddress().getCityName()==null))?hotelSearchCommand.getCity():basicproperty.getChainCode());
		hotelOrderHotelData.setCity(basicproperty.getAddress()!=null && basicproperty.getAddress().getCityName()!=null?hotelSearchCommand.getCity():"");
		hotelOrderHotelData.setCountry(((basicproperty.getAddress()==null) || (basicproperty.getAddress().getCounty()==null))?hotelSearchCommand.getCountry():basicproperty.getAddress().getCounty());
		hotelOrderHotelData.setCreatedAt(new Timestamp(new Date().getTime()) );
		hotelOrderHotelData.setEmail(((orderCustomer.getEmail()==null))?"Not Available":orderCustomer.getEmail());
		hotelOrderHotelData.setFax("Not Available");
		logger.info("OrderCustomer data saved--------------setfax:--");

		hotelOrderHotelData.setHotelCategory(((basicproperty.getHotel_Star()==null))?"Not Available":basicproperty.getHotel_Star()+ "star");
		hotelOrderHotelData.setHotelElementID(basicproperty.getHotelCode());
		hotelOrderHotelData.setHotelInfo("Not Available");
		hotelOrderHotelData.setHotelLocation(((basicproperty.getArea()==null))?"Not Available":basicproperty.getArea());
		hotelOrderHotelData.setHotelStyle("Not Available");
		hotelOrderHotelData.setHotelTheme("Not Available");

		hotelOrderHotelData.setName(((basicproperty.getHotelName()==null))?"Not Available":basicproperty.getHotelName());


		String location = hotelOrderHotelData.getAddress1() + hotelOrderHotelData.getAddress2() + hotelOrderHotelData.getAddress3();
		hotelOrderHotelData.setHotelLocation(location);

		logger.info("OrderCustomer data saved--------------setHotelTheme:--");

		hotelOrderHotelData.setLatitude(((basicproperty.getPosition()==null) ||(basicproperty.getPosition().getLatitude()==null) )?"":basicproperty.getPosition().getLatitude());
		hotelOrderHotelData.setLongitude(((basicproperty.getPosition()==null) ||(basicproperty.getPosition().getLongitude()==null) )?"":basicproperty.getPosition().getLongitude());
		hotelOrderHotelData.setProfileImageURL(((basicproperty.getImageurl()==null))?"Not Available":basicproperty.getImageurl());
		logger.info("hotelOrderHotelData data saved--------------:setProfileImageURL");


		StringBuilder amenitiestext = new StringBuilder("");
		if(basicproperty.getHotelAmenities() != null && !basicproperty.getHotelAmenities().isEmpty())
			for (Facility facility : basicproperty.getHotelAmenities()) {
				amenitiestext.append(facility.getDescription()+"|");
			}
		logger.info("hotelOrderHotelData data saved----amenitiestext----------:"+amenitiestext);


		hotelOrderHotelData.setPropertyAmenities(amenitiestext.toString());
		hotelOrderHotelData.setRank(((basicproperty.getWeekendRank()==null))?0:basicproperty.getWeekendRank());
		hotelOrderHotelData.setRegionCityID(((hotelSearchCommand.getSearchCity()==null)||(hotelSearchCommand.getSearchCity().getId()==null))?0:hotelSearchCommand.getSearchCity().getId());
		hotelOrderHotelData.setRegionName(((hotelSearchCommand.getSearchCity()==null)||(
				hotelSearchCommand.getSearchCity().getCity()==null))?"Not Available":hotelSearchCommand.getSearchCity().getCity());

		logger.info("OrderCustomer data saved--------------setRegionName:--");

		StringBuilder roomamenitiestext = new StringBuilder("");
		if(basicproperty.getHotelAmenities() != null && !basicproperty.getHotelAmenities().isEmpty())
			for (Facility facility : basicproperty.getHotelAmenities()) {
				roomamenitiestext.append(facility.getDescription()+"|");
			}
		logger.info("OrderCustomer data saved--------------getDescription:--");

		hotelOrderHotelData.setRoomAmenities(roomamenitiestext.toString());
		hotelOrderHotelData.setStars(new Integer(3));
		hotelOrderHotelData.setState(((hotelSearchCommand.getSearchCity()==null)||(hotelSearchCommand.getSearchCity().getState()==null))?"Not Available":hotelSearchCommand.getSearchCity().getState());
		hotelOrderHotelData.setTelephone("Not Available");
		hotelOrderHotelData.setTripAdvisorLocationId("Not Available");
		hotelOrderHotelData.setType("Not Available");
		hotelOrderHotelData.setUpdatedAt(new Timestamp(new Date().getTime()));
		hotelOrderHotelData.setUrl("Not Available");
		hotelOrderHotelData.setVersion(6);

		logger.info("hotelOrderHotelData data saved--------------:setVersion");




		hotelOrderRow.setBaseCurrency(hotelSearchCommand.getCurrency());
		hotelOrderRow.setBookingStatus("pending");

		DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date datestart = originalFormat.parse(hotelSearchCommand.getDatestart());
		Date dateend = originalFormat.parse(hotelSearchCommand.getDateend());

		hotelOrderRow.setCheckInDate(datestart);
		hotelOrderRow.setCheckOutDate(dateend);
		hotelOrderRow.setCreatedAt(new Timestamp(new Date().getTime()));
		hotelOrderRow.setCreatedBy(hotelBookCommand.getUsername());
		hotelOrderRow.setUserId(checkGetEmulatedUserById(hotelBookCommand));

		logger.info("OrderCustomer data saved--------------setFinalPrice:--");

		hotelOrderRow.setHotelOrderHotelData(hotelOrderHotelData);

		hotelOrderRow.setOrderReference(hotelBookCommand.getOrderid());

		hotelOrderRow.setOrderCustomer(orderCustomer);
		hotelOrderRow.setPaymentStatus("pending");
		hotelOrderRow.setBookedCurrency(hotelSearchCommand.getCurrency());
		hotelOrderRow.setApiCurrency(basicproperty.getApiCurrency());
		hotelOrderRow.setBaseCurrency(basicproperty.getBaseCurrency());
		hotelOrderRow.setApiToBaseExchangeRate(basicproperty.getExRateApiToBase());
		hotelOrderRow.setBaseToBookingExchangeRate(basicproperty.getExRateBaseToBooking());

		logger.info("OrderCustomer data saved--------------setPriceCurrency:--");

		hotelOrderRow.setReferenceCode("");
		hotelOrderRow.setRefUniqueType("");
		logger.info("OrderCustomer data saved--------------api provider:--"+rs.getBasicPropertyInfo().getApiProvider());

		hotelOrderRow.setApiProvoder(String.valueOf(rs.getBasicPropertyInfo().getApiProvider()));
		hotelOrderRow.setStatusAction("pending");
		hotelOrderRow.setTotalGuest(getGuestTotalCount(hotelSearchCommand));
		hotelOrderRow.setNoOfRooms(hotelSearchCommand.getNoofrooms());

		hotelOrderRow.setApiPrice(apiHotelBook.getApiRate().getPayableAmt());

		hotelOrderRow.setMarkupAmount(getMarkUpAmount(apiHotelBook));
		hotelOrderRow.setDiscountAmount(apiHotelBook.getApiRate().getRoomrateDiscount());
		BigDecimal processingFees = new BigDecimal("0.0");
		if(!apiHotelBook.getBook().getPayBy().equals("cash")){
			processingFees = apiHotelBook.getBookingRate().getPayableAmt().divide(new BigDecimal("100")).multiply(new BigDecimal("2.0")) ;
			BigDecimal finalamount = apiHotelBook.getBookingRate().getPayableAmt().add(processingFees);
			finalamount = AmountRoundingModeUtil.roundingModeForHotel(finalamount);
			apiHotelBook.getBookingRate().setPayableAmt(finalamount);
			hotelOrderRow.setFinalPrice(apiHotelBook.getBookingRate().getTotalPayableAmt());
		}else{
			hotelOrderRow.setFinalPrice(apiHotelBook.getBookingRate().getPayableAmt());
		}
		hotelOrderRow.setFeeAmount(processingFees);
		hotelOrderRow.setTaxes(apiHotelBook.getApiRate().getRoomrateTax());
		hotelOrderRow.setTotInvoiceAmount(apiHotelBook.getApiRate().getTotalPayableAmt());
		hotelOrderRow.setRecievedAmount(new BigDecimal(0));
		hotelOrderRow.setBaseCurrency(apiHotelBook.getRoomsummary().getBasicPropertyInfo().getBaseCurrency());
		hotelOrderRow.setApiCurrency(apiHotelBook.getRoomsummary().getBasicPropertyInfo().getApiCurrency());
		hotelOrderRow.setBookedCurrency(apiHotelBook.getRoomsummary().getBasicPropertyInfo().getBookingCurrency());
		hotelOrderRow.setApiToBaseExchangeRate(apiHotelBook.getRoomsummary().getBasicPropertyInfo().getExRateApiToBase());
		hotelOrderRow.setBaseToBookingExchangeRate(apiHotelBook.getRoomsummary().getBasicPropertyInfo().getExRateBaseToBooking());

		hotelOrderRow.setUpdatedAt(new Timestamp(new Date().getTime()));
		hotelOrderRow.setVersion(hotelOrderHotelData.getVersion());
		hotelOrderRow.setAgencyUserName(apiHotelBook.getBook().getUsername());//get agency for the particular user who des booking
		hotelOrderRow.setBookingDate(DateConversion.convertDateToStringDate(new Date()));

//added by basha  for getHotelOrderRowRmConfigStruct dynamic model inserion 
		if(hotelOrderRow.getHotelOrderRowRmConfigStruct()==null ){
			try{
				RmConfigModel  rmConfigModel=rmConfigDetailDAO.getRmConfigModel(appKeyVo.getCompanyId());
				   if(rmConfigModel!=null){
					  HotelOrderRowRmConfigStruct hotelOrderRowRmConfigStruct=new HotelOrderRowRmConfigStruct();
					  hotelOrderRowRmConfigStruct.setRmDynamicData(rmConfigModel.getDynamicFieldsData());
				   hotelOrderRow.setHotelOrderRowRmConfigStruct(hotelOrderRowRmConfigStruct);
				   }
				} catch (Exception e) {
					logger.error("Exception", e);
					//throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
				
		}
			
			
		hotelOrderRow.setCompanyId(String.valueOf(hotelBookCommand.getCompanyId()));
		hotelOrderRow.setConfigId(String.valueOf(hotelBookCommand.getConfigId()));
		Long searchkey = hotelBookCommand.getSearchKey();
		hotelOrderRow.setSearchKey(Integer.valueOf(searchkey.intValue()));
		hotelOrderRow.setBookingMode("Online");
		int noofguests =  CommonUtil.getGuestTotalCount(hotelSearchCommand);
		logger.info("################## hotelOrderRow get config -------");
		try {
			if(appKeyVo.getConfigId()>0)
			{
				CompanyConfig companyConfig =appKeyVo.getCompanyConfig();
				Company company = appKeyVo.getCompany();
				Company parentCompany = companyDao.getParentCompany(company);
				if(companyConfig != null )
				{
					if(companyConfig.getCompanyConfigType().isB2E()){
						if(companyConfig.getTaxtype().equalsIgnoreCase("GST")){
							HotelOrderRowGstTax hotelOrderRowGstTax = new HotelOrderRowGstTax();
							HotelGstTaxConfig hotelGstTaxConfig = companyConfig.getHotelGstTaxConfig();
							hotelOrderRowGstTax = createHotelOrderRowGstTax(hotelGstTaxConfig, hotelOrderRowGstTax, hotelSearchCommand.getCountry(), company, parentCompany,noofguests);
							hotelOrderRow.setHotelOrderRowGstTax(hotelOrderRowGstTax);
							hotelOrderRow.setTotGst(hotelOrderRowGstTax.getTotalGst());

						}
						else{
							HotelOrderRowServiceTax hotelOrderRowServiceTax = new HotelOrderRowServiceTax();
							HotelServiceTaxConfig hotelServiceTaxConfig = companyConfig.getHotelServiceTaxConfig();
							hotelOrderRowServiceTax = createHotelOrderRowServiceTax(hotelServiceTaxConfig,hotelOrderRowServiceTax,hotelSearchCommand.getCountry());;
							hotelOrderRow.setHotelOrderRowServiceTax(hotelOrderRowServiceTax);
							hotelOrderRow.setServiceTax(hotelOrderRowServiceTax.getTotalTax());
						}
					}

				}
			}
		}catch(Exception e)
		{
			logger.info("hotelOrderRow config error -------------:"+e);
		}

		logger.info("hotelOrderRow data saved--------------:");		

		List<HotelOrderRoomInfo> hotelOrderRoomInfos = new ArrayList<HotelOrderRoomInfo>();
		switch (basicproperty.getApiProvider()) {
		case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
			logger.info("HotelOrderRoomInfo data save------------rrtype--:"+roomrate);
			int index = 0;
			for (Rate room : roomrate.getRates().getRates()) {
				logger.info("HotelOrderRoomInfo data save----------room "+index+"--room--:"+room);
				HotelOrderRoomInfo hor = new HotelOrderRoomInfo();
				hor.setMealType("");
				hor.setName(((room.getName()==null))?"":room.getName());
				StringBuffer inclusions = new StringBuffer();
				if(roomtype.getAmenities() != null)
					logger.info("HotelOrderRoomInfo roomtype.getAmenities()---------- "+roomtype.getAmenities().size());
				for (Facility facility : roomtype.getAmenities()) {

					if(roomtype.getAmenities().size() > 1){
						//(facility.getDescription()!=null)?inclusions.append(facility.getDescription().trim()):;
						inclusions.append((facility.getDescription()!=null)?facility.getDescription().trim()+",":"");
					}else{
						inclusions.append((facility.getDescription()!=null)?facility.getDescription().trim():"");
					}
				}


				hor.setRoomType((roomrate.getRoomTypeName()==null)?((roomtype.getRoomType()==null))?roomtype.getRoomID():roomtype.getRoomType():roomrate.getRoomTypeName());
				hor.setInclusions(inclusions.toString());

				hor.setReferenceCode(hotelBookCommand.getOrderid());
				hor.setStatus("pending");
				hor.setUpdatedAt(new Timestamp(new Date().getTime()));
				hor.setCreatedAt(new Timestamp(new Date().getTime()));
				logger.info("HotelOrderRoomInfo data save----------room "+index+"--setCreatedAt--:"+room);
				hor.setVersion(hotelOrderHotelData.getVersion());
				hor.setHotelOrderRow(hotelOrderRow);
				logger.info("HotelOrderRoomInfo data saved------------roomratetype--:"+roomrate.getRoomTypeCode());
				List<HotelOrderCancellationPolicy> roomOrderCancellationPolicies = new ArrayList<HotelOrderCancellationPolicy>();
				//Pending...
				if(rateplan.getCancelPenalties() != null)
					for (com.tayyarah.hotel.model.CancelPenaltyType cancelpenality : rateplan.getCancelPenalties().getCancelPenalties()) {
						AmountPercentType amountpercenttype = cancelpenality.getAmountPercent();
						logger.info("roomOrderCancellationPolicy data saved------------amountpercenttype--:"+amountpercenttype);
						if(amountpercenttype != null && amountpercenttype.getAmount()!= null)
						{
							try
							{
								HotelOrderCancellationPolicy roomOrderCancellationPolicy = new HotelOrderCancellationPolicy();
								roomOrderCancellationPolicy.setCancellationDay(getNoofStayDaysDBSQL(cancelpenality.getStart(), cancelpenality.getEnd()));
								roomOrderCancellationPolicy.setCreatedAt(new Timestamp(new Date().getTime()));
								roomOrderCancellationPolicy.setCurrency(amountpercenttype.getCurrencyCode());
								roomOrderCancellationPolicy.setEndDate(getFormattedDateFromStringSQL(cancelpenality.getEnd()));
								roomOrderCancellationPolicy.setFeeAmount(amountpercenttype.getAmount());
								roomOrderCancellationPolicy.setFeeType(amountpercenttype.getBasisType());
								roomOrderCancellationPolicy.setFormattedFeeAmount(amountpercenttype.getAmount().toPlainString());
								roomOrderCancellationPolicy.setFromDate(getFormattedDateFromStringSQL(cancelpenality.getStart()));
								roomOrderCancellationPolicy.setHotelOrderRow(hotelOrderRow);
								if(cancelpenality.getPenaltyDescriptions() != null && (cancelpenality.getPenaltyDescriptions().size() > 0))
									roomOrderCancellationPolicy.setRemarks(cancelpenality.getPenaltyDescriptions().get(0).getDrescription());
								//to be merged for all rooms to be booked...
								roomOrderCancellationPolicy.setRoomInfo(hor);
								roomOrderCancellationPolicy.setStartDate(new Date());
								roomOrderCancellationPolicy.setUpdatedAt(new Timestamp(new Date().getTime()));
								roomOrderCancellationPolicy.setVersion(hotelOrderHotelData.getVersion());
								roomOrderCancellationPolicies.add(roomOrderCancellationPolicy);
								hotelOrderDao.insertHotelOrderCancellationPolicyDetails(roomOrderCancellationPolicy);
								logger.info("roomOrderCancellationPolicy data saved------------roomratetype--:"+roomrate.getRoomTypeCode());
							}
							catch(Exception ex)
							{
								logger.info("roomOrderCancellationPolicy data Format error --:"+ex.getMessage());
								logger.error(ex);
							}
						}
					}
				hor.setHotelOrderCancellationPolicies(roomOrderCancellationPolicies);
				hotelOrderDao.insertHotelOrderRoomInfoDetails(hor);
				List<HotelOrderGuest> hotelOrderGuests = new ArrayList<HotelOrderGuest>();
				//for (Profile p : hbc.getProfiles()) {
				logger.info("hotelOrderGuests data save----------room "+index+"--getRoomGuestProfiles--:"+HotelBookCommand.getRoomGuestProfiles(hotelBookCommand, index));
				for (Profile p : HotelBookCommand.getRoomGuestProfiles(hotelBookCommand, index)) {
					logger.info("hotelOrderGuests data save----------room "+index+"--Profile--:"+p);
					int age = 0;
					try{
						age = ((p.getCustomer().getAge()) == null)?0:Integer.valueOf(p.getCustomer().getAge());
					}
					catch(NumberFormatException e)
					{
					}
					HotelOrderGuest hotelOrderGuest = new HotelOrderGuest();
					hotelOrderGuest.setPaxId(p.getCustomer().getPersonName().getPaxId());//added by basha on 16-10-2017
					hotelOrderGuest.setAge(age);
					hotelOrderGuest.setCreatedAt(new Timestamp(new Date().getTime()));
					hotelOrderGuest.setEmail(p.getCustomer().getEmail());
					logger.info("hotelOrderGuests data save----------room "+index+"--Profile--setEmail:"+p);
					hotelOrderGuest.setFirstName(p.getCustomer().getPersonName().getGivenName());
					hotelOrderGuest.setLastName(p.getCustomer().getPersonName().getSurname());
					hotelOrderGuest.setTitle(p.getCustomer().getPersonName().getNamePrefix());
					hotelOrderGuest.setNationality(p.getCustomer().getAddress().getCountryName());
					hotelOrderGuest.setPaxType("Not Available");
					hotelOrderGuest.setRoomInfo(hor);
					logger.info("hotelOrderGuests data save----------room "+index+"--Profile--setRoomInfo:hor--"+hor);
					hotelOrderGuest.setSessionUserId(p.getId());
					//hotelOrderGuest.setTitle(p.getCustomer().getPersonName().);
					hotelOrderGuest.setUpdatedAt(new Timestamp(new Date().getTime()));
					hotelOrderGuest.setVersion(hotelOrderHotelData.getVersion());
					logger.info("hotelOrderGuests data save----------room "+index+"--Profile--insertHotelOrderGuestDetails--hotelOrderGuest--"+hotelOrderGuest);
					if(p.getProfileType().equals("1"))
						hotelOrderGuest.setLeader(true);
					else
						hotelOrderGuest.setLeader(false);
					logger.info("hotelOrderGuests data saved------------p.getId()--:"+p.getId());
					hotelOrderGuests.add(hotelOrderGuest);
					hotelOrderDao.insertHotelOrderGuestDetails(hotelOrderGuest);


				}
				hor.setHotelOrderGuests(hotelOrderGuests);
				hotelOrderRoomInfos.add(hor);
				logger.info("hotelOrderGuests data saved--------------:");
				index++;
			}

			break;
		default:

			for(int roomindex = 0; roomindex < hotelBookCommand.getNumberOfUnits(); roomindex++){
				com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate roomrateTemp = roomrate;
				try{
					roomrateTemp = rs.getRoomRates().getRoomRates().get(roomindex);
				}
				catch(Exception e)
				{
					roomrateTemp = roomrate;
				}
				roomrate = roomrateTemp;

				HotelOrderRoomInfo hor = new HotelOrderRoomInfo();
				hor.setMealType(((roomtype.getRoomType()==null))?roomtype.getRoomID():roomtype.getRoomType());
				hor.setName((roomrate.getRoomTypeName()==null)?((roomtype.getRoomType()==null))?roomtype.getRoomID():roomtype.getRoomType():roomrate.getRoomTypeName());

				StringBuffer inclusions = new StringBuffer();
				if(roomtype.getAmenities() != null)
					for (Facility facility : roomtype.getAmenities()) {
						if(roomtype.getAmenities().size() > 1){
							//(facility.getDescription()!=null)?inclusions.append(facility.getDescription().trim()):;
							inclusions.append((facility.getDescription()!=null)?facility.getDescription().trim()+",":"");
						}else{
							inclusions.append((facility.getDescription()!=null)?facility.getDescription().trim():"");
						}
					}

				hor.setRoomType((roomrate.getRoomTypeName()==null)?((roomtype.getRoomType()==null))?roomtype.getRoomID():roomtype.getRoomType():roomrate.getRoomTypeName());
				hor.setInclusions(inclusions.toString());
				hor.setNoofguests(HotelBookCommand.getRoomGuestProfiles(hotelBookCommand, roomindex).size());
				hor.setReferenceCode(hotelBookCommand.getOrderid());
				hor.setStatus("pending");
				hor.setUpdatedAt(new Timestamp(new Date().getTime()));
				hor.setCreatedAt(new Timestamp(new Date().getTime()));
				hor.setVersion(hotelOrderHotelData.getVersion());
				hor.setHotelOrderRow(hotelOrderRow);
				hotelOrderDao.insertHotelOrderRoomInfoDetails(hor);
				logger.info("HotelOrderRoomInfo data saved------------roomratetype--:"+roomrate.getRoomTypeCode());
				List<HotelOrderCancellationPolicy> roomOrderCancellationPolicies = new ArrayList<HotelOrderCancellationPolicy>();
				//Pending...
				if(rateplan.getCancelPenalties() != null)
					for (com.tayyarah.hotel.model.CancelPenaltyType cancelpenality : rateplan.getCancelPenalties().getCancelPenalties()) {
						AmountPercentType amountpercenttype = cancelpenality.getAmountPercent();
						logger.info("roomOrderCancellationPolicy data saved------------amountpercenttype--:"+amountpercenttype);
						if(amountpercenttype != null && amountpercenttype.getAmount()!= null)
						{
							try{
								HotelOrderCancellationPolicy roomOrderCancellationPolicy = new HotelOrderCancellationPolicy();
								roomOrderCancellationPolicy.setCancellationDay(getNoofStayDaysDBSQL(cancelpenality.getStart(), cancelpenality.getEnd()));
								roomOrderCancellationPolicy.setCreatedAt(new Timestamp(new Date().getTime()));
								roomOrderCancellationPolicy.setCurrency(amountpercenttype.getCurrencyCode());
								roomOrderCancellationPolicy.setEndDate(getFormattedDateFromStringSQL(cancelpenality.getEnd()));
								roomOrderCancellationPolicy.setFeeAmount(amountpercenttype.getAmount());
								roomOrderCancellationPolicy.setFeeType(amountpercenttype.getBasisType());
								roomOrderCancellationPolicy.setFormattedFeeAmount(amountpercenttype.getAmount().toPlainString());
								roomOrderCancellationPolicy.setFromDate(getFormattedDateFromStringSQL(cancelpenality.getStart()));
								roomOrderCancellationPolicy.setHotelOrderRow(hotelOrderRow);
								if(cancelpenality.getPenaltyDescriptions() != null && (cancelpenality.getPenaltyDescriptions().size() > 0))
									roomOrderCancellationPolicy.setRemarks(cancelpenality.getPenaltyDescriptions().get(0).getDrescription());
								//to be merged for all rooms to be booked...
								roomOrderCancellationPolicy.setRoomInfo(hor);
								roomOrderCancellationPolicy.setStartDate(new Date());
								roomOrderCancellationPolicy.setUpdatedAt(new Timestamp(new Date().getTime()));
								roomOrderCancellationPolicy.setVersion(hotelOrderHotelData.getVersion());
								roomOrderCancellationPolicies.add(roomOrderCancellationPolicy);
								hotelOrderDao.insertHotelOrderCancellationPolicyDetails(roomOrderCancellationPolicy);
								logger.info("roomOrderCancellationPolicy data saved------------roomratetype--:"+roomrate.getRoomTypeCode());
							}
							catch(Exception ex)
							{
								logger.info("roomOrderCancellationPolicy data Format error --:"+ex.getMessage());
								logger.error(ex);
							}
						}

					}
				hor.setHotelOrderCancellationPolicies(roomOrderCancellationPolicies);
				List<HotelOrderGuest> hotelOrderGuests = new ArrayList<HotelOrderGuest>();
				//for (Profile p : hbc.getProfiles()) {
				for (Profile p : HotelBookCommand.getRoomGuestProfiles(hotelBookCommand, roomindex)) {

					int age = 0;
					try{
						age = ((p.getCustomer().getAge()) == null)?0:Integer.valueOf(p.getCustomer().getAge());
					}
					catch(NumberFormatException e)
					{

					}

					HotelOrderGuest hotelOrderGuest = new HotelOrderGuest();
					hotelOrderGuest.setPaxId(p.getCustomer().getPersonName().getPaxId());//added by basha on 16-10-2017
					hotelOrderGuest.setAge(age);
					hotelOrderGuest.setCreatedAt(new Timestamp(new Date().getTime()));
					hotelOrderGuest.setEmail(p.getCustomer().getEmail());
					hotelOrderGuest.setFirstName(p.getCustomer().getPersonName().getGivenName());
					hotelOrderGuest.setLastName(p.getCustomer().getPersonName().getSurname());
					hotelOrderGuest.setTitle(leaderprofile.getCustomer().getPersonName().getNamePrefix());
					hotelOrderGuest.setNationality(p.getCustomer().getAddress().getCountryName());
					hotelOrderGuest.setPaxType("Not Available");
					hotelOrderGuest.setRoomInfo(hor);
					hotelOrderGuest.setSessionUserId(p.getId());

					hotelOrderGuest.setUpdatedAt(new Timestamp(new Date().getTime()));
					hotelOrderGuest.setVersion(hotelOrderHotelData.getVersion());

					if(p.getProfileType().equals("1"))
						hotelOrderGuest.setLeader(true);
					else
						hotelOrderGuest.setLeader(false);

					hotelOrderGuests.add(hotelOrderGuest);
					hotelOrderDao.insertHotelOrderGuestDetails(hotelOrderGuest);

					logger.info("hotelOrderGuests data saved------------p.getId()--:"+p.getId());



				}

				hor.setHotelOrderGuests(hotelOrderGuests);
				hotelOrderRoomInfos.add(hor);

			}
			break;
		}


		hotelOrderRow.setHotelOrderRoomInfos(hotelOrderRoomInfos);
		logger.info("hotelOrderRow data saved--------------:");


		hotelOrderDao.insertHotelOrderRowDetails(hotelOrderRow);
		hotelOrderDao.insertOrderCustomerDetails(orderCustomer);
		hotelOrderDao.insertHotelOrderHotelDataDetails(hotelOrderHotelData);

		return hotelOrderRow;

	}

	private static HotelOrderRowServiceTax createHotelOrderRowServiceTax(
			HotelServiceTaxConfig hotelServiceTaxConfig, HotelOrderRowServiceTax hotelOrderRowServiceTax,String country) {
		hotelOrderRowServiceTax.setApplicableFare(hotelServiceTaxConfig.getApplicableFare());
		hotelOrderRowServiceTax.setBasicTax(hotelServiceTaxConfig.getBasicTax());
		hotelOrderRowServiceTax.setConvenienceFee(hotelServiceTaxConfig.getConvenienceFee());
		hotelOrderRowServiceTax.setKrishiKalyanCess(hotelServiceTaxConfig.getKrishiKalyanCess());
		if(country.equalsIgnoreCase("india"))
			hotelOrderRowServiceTax.setManagementFee(hotelServiceTaxConfig.getDomesticManagementFee());
		else
			hotelOrderRowServiceTax.setManagementFee(hotelServiceTaxConfig.getManagementFee());

		hotelOrderRowServiceTax.setSwatchBharathCess(hotelServiceTaxConfig.getSwatchBharathCess());
		hotelOrderRowServiceTax.setTotalTax(hotelServiceTaxConfig.getTotalTax());
		return hotelOrderRowServiceTax;
	}

	private static HotelOrderRowGstTax createHotelOrderRowGstTax(
			HotelGstTaxConfig hotelGstTaxConfig, HotelOrderRowGstTax hotelOrderRowGstTax,String country,Company company,Company parentCompany,int totalguests) {

		BigDecimal totalGuests = new BigDecimal(totalguests);
		BigDecimal CGST = new BigDecimal("0.0");
		BigDecimal SGST = new BigDecimal("0.0");
		BigDecimal IGST = new BigDecimal("0.0");
		BigDecimal UGST = new BigDecimal("0.0");
		BigDecimal totalGst = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");
		if(country.equalsIgnoreCase("India")){
			managementFee = hotelGstTaxConfig.getDomesticManagementFee().multiply(totalGuests);

			if(company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim())){
				CGST = hotelGstTaxConfig.getCGST();
				SGST =  hotelGstTaxConfig.getSGST();				
			}
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				IGST =  hotelGstTaxConfig.getIGST();		
			}
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				CGST = hotelGstTaxConfig.getCGST();
				UGST =  hotelGstTaxConfig.getUGST();
			}		
			totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		}
		if(!country.equalsIgnoreCase("India")){
			managementFee = hotelGstTaxConfig.getIntlManagementFee().multiply(totalGuests);

			if(company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim())){
				CGST = hotelGstTaxConfig.getCGST();
				SGST =  hotelGstTaxConfig.getSGST();				
			}
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				IGST =  hotelGstTaxConfig.getIGST();		
			}
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				CGST = hotelGstTaxConfig.getCGST();
				UGST =  hotelGstTaxConfig.getUGST();
			}		
			totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		}
		hotelOrderRowGstTax.setCGST(CGST);
		hotelOrderRowGstTax.setSGST(SGST);
		hotelOrderRowGstTax.setIGST(IGST);
		hotelOrderRowGstTax.setUGST(UGST);
		hotelOrderRowGstTax.setVersion(1);
		hotelOrderRowGstTax.setManagementFee(managementFee);
		hotelOrderRowGstTax.setConvenienceFee(hotelGstTaxConfig.getConvenienceFee());
		hotelOrderRowGstTax.setTotalGst(totalGst);
		hotelOrderRowGstTax.setApplicableFare(hotelGstTaxConfig.getApplicableFare());		
		hotelOrderRowGstTax.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));		


		return hotelOrderRowGstTax;
	}

	public static  com.tayyarah.hotel.model.UniqueIDType getApiUniqueId(com.tayyarah.hotel.model.OTAHotelResRS totaHotelResRS)
	{
		com.tayyarah.hotel.model.UniqueIDType uniqueid = null;
		if( totaHotelResRS != null && (totaHotelResRS.getHotelReservations() != null) && totaHotelResRS.getHotelReservations().getHotelReservations() != null && ! totaHotelResRS.getHotelReservations().getHotelReservations().isEmpty())
		{
			if(!totaHotelResRS.getHotelReservations().getHotelReservations().get(0).getUniqueIDs().isEmpty())
			{
				uniqueid = totaHotelResRS.getHotelReservations().getHotelReservations().get(0).getUniqueIDs().get(0);

			}
		}
		return uniqueid;
	}


	public static HotelOrderRow hotelOrderUpdationDataPreBook(HotelOrderDao hotelOrderDao, HotelOrderRow hotelOrderRow, HotelTransactionTemp ht, APIHotelBook apiHotelBook, String bookStatus, String payStatus, String actionStatus) throws NumberFormatException, Exception
	{
		logger.info("HotelPreBookController----apiHotelBook:"+apiHotelBook);
		logger.info("HotelPreBookController----apiHotelBook.getPreBookRes():"+apiHotelBook.getPreBookRes());
		com.tayyarah.hotel.model.UniqueIDType uniqueid  = getApiUniqueId(apiHotelBook.getPreBookRes());		
		BigDecimal processingFees = new BigDecimal("0.0");		
		hotelOrderRow.setBookingStatus(bookStatus);
		hotelOrderRow.setPaymentStatus(payStatus);
		hotelOrderRow.setStatusAction(actionStatus);
		hotelOrderRow.setBookingDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		hotelOrderRow.setUpdatedAt(new Timestamp(new Date().getTime()));
		if(!apiHotelBook.getBook().getPayBy().equals("cash"))
			hotelOrderRow.setFinalPrice(apiHotelBook.getBookingRate().getTotalPayableAmt());
		else
			hotelOrderRow.setFinalPrice(apiHotelBook.getBookingRate().getPayableAmt());

		hotelOrderRow.setApiPrice(apiHotelBook.getApiRate().getPayableAmt());

		List<HotelOrderRoomInfo> hotelOrderRoomInfos = new ArrayList<HotelOrderRoomInfo>();
		for (HotelOrderRoomInfo hotelOrderRoomInfo : hotelOrderRow.getHotelOrderRoomInfos()) {
			HotelOrderRoomInfo hor = hotelOrderRoomInfo;
			hor.setStatus(bookStatus);
			hotelOrderRoomInfos.add(hor);
		}
		hotelOrderRow.setHotelOrderRoomInfos(hotelOrderRoomInfos);
		hotelOrderDao.updateHotelBookStatus(hotelOrderRow.getOrderReference(), hotelOrderRow);
		hotelOrderDao.updateRoomsBookStatus(hotelOrderRow.getOrderReference(), bookStatus);		
		logger.info("hotelOrderRow data saved---prebooking-----------:");
		return hotelOrderRow;
	}

	public static void repayWallet(WalletAmountTranferHistory walletAmountTranferHistory, FlightBookingDao FBDAO) throws Exception
	{
		FBDAO.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(), walletAmountTranferHistory.getWalletId() ,walletAmountTranferHistory, 2);
	}

	public static HotelOrderRow hotelOrderUpdationDataBook(HotelOrderDao hotelOrderDao, HotelOrderRow hotelOrderRow, HotelTransactionTemp ht, APIHotelBook apiHotelBook, String bookStatusMsg, String payStatus, String actionStatus, int bookstatus,FlightBookingDao FBDAO)
	{
		logger.info("HotelBookController----apiHotelBook:"+apiHotelBook);
		logger.info("HotelBookController----apiHotelBook.getBookRes():"+apiHotelBook.getBookRes());

		try{
			com.tayyarah.hotel.model.UniqueIDType uniqueid  = getApiUniqueId(apiHotelBook.getBookRes());
			if(uniqueid != null && uniqueid.getID() != null && uniqueid.getType() != null)
			{
				logger.info("HotelPreBookController----uniqueid:"+uniqueid);
				logger.info("HotelPreBookController----uniqueid.getID():"+uniqueid.getID());
				logger.info("HotelPreBookController----uniqueid.getType():"+uniqueid.getType());
				logger.info("HotelPreBookController----uniqueid.getApiConfirmationNo():"+uniqueid.getApiConfirmationNo());
				hotelOrderRow.setReferenceCode(uniqueid.getApiBookingCode());
				hotelOrderRow.setApiConfirmationNo(uniqueid.getApiConfirmationNo());

				String confirmationNo = CommonUtil.getHotelConfirmationNumber(hotelOrderRow);
				hotelOrderRow.setConfirmationNo(confirmationNo);

				logger.info("HotelPreBookController----confirmationNo:"+ confirmationNo);
				hotelOrderRow.setApiBookingId(uniqueid.getApiBookingId());
				hotelOrderRow.setRefUniqueType(uniqueid.getType());
				BigDecimal finalprice = apiHotelBook.getBookRes().getBookingFinalPrice();
				hotelOrderRow.setFinalPrice(finalprice.setScale(2,  BigDecimal.ROUND_HALF_UP));

				BigDecimal basewithoutmarkup = apiHotelBook.getBookRes().getBaseFinalPriceWithoutMarkup();
				BigDecimal base = apiHotelBook.getBookRes().getBaseFinalPrice();

				int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
				BigDecimal totalMarkup = new BigDecimal(0);
				for (Entry<String, BigDecimal> entry : apiHotelBook.getBookRes().getMarkupmap().entrySet()) {
					BigDecimal markupvalue = new BigDecimal(0);
					markupvalue  = entry.getValue().multiply(new BigDecimal(noofdays));
					totalMarkup = totalMarkup.add(markupvalue);
				}					
				hotelOrderRow.setMarkupAmount(totalMarkup);
			}

			if(bookStatusMsg.equalsIgnoreCase("Confirmed") )
			{			
				List<HotelOrderRowMarkup> hotelOrderRowMarkups = insertAndGetHotelOrderRowMarkups(apiHotelBook, hotelOrderRow, hotelOrderDao);
				logger.info("hotelOrderRow hotelOrderRowMarkups data saved--------------:"+hotelOrderRowMarkups.size());
				hotelOrderRow.setHotelOrderRowMarkupList(hotelOrderRowMarkups);
				List<HotelOrderRowCommission> hotelOrderRowCommissions = insertAndGetHotelOrderRowCommissions(apiHotelBook.getBookRes(), hotelOrderRow, hotelOrderDao);
				hotelOrderRow.setHotelOrderRowCommissionList(hotelOrderRowCommissions);
				logger.info("hotelOrderRow hotelOrderRowCommissions data saved--------------:"+hotelOrderRowCommissions.size());
			}
			//com.lintas.hotel.model.RatePlanType selectedrateplan = rs.getRatePlans().getRatePlan().get(0);
			hotelOrderRow.setBookingStatus(bookStatusMsg);
			hotelOrderRow.setPaymentStatus(payStatus);
			hotelOrderRow.setStatusAction(actionStatus);
			hotelOrderRow.setUpdatedAt(new Timestamp(new Date().getTime()));

			List<HotelOrderRoomInfo> hotelOrderRoomInfos = new ArrayList<HotelOrderRoomInfo>();
			for (HotelOrderRoomInfo hotelOrderRoomInfo : hotelOrderRow.getHotelOrderRoomInfos()) {
				HotelOrderRoomInfo hor = hotelOrderRoomInfo;
				hor.setStatus(bookStatusMsg);
				hotelOrderRoomInfos.add(hor);
			}
			hotelOrderRow.setHotelOrderRoomInfos(hotelOrderRoomInfos);
			hotelOrderDao.updateHotelBookStatus(hotelOrderRow.getOrderReference(), hotelOrderRow);
			hotelOrderDao.updateRoomsBookStatus(hotelOrderRow.getOrderReference(), bookStatusMsg);			
			logger.info("hotelOrderRow data saved---prebooking-----------:");			
		}catch(Exception e){
			logger.info("hotelOrderRow ---prebooking-----Exception------:"+e);
		}
		return hotelOrderRow;

	}


	public static HotelOrderRowCancellation insertOrUpdateHotelCancellation(HotelOrderDao hotelOrderDao, HotelOrderRow hotelOrderRow, HotelOrderRowCancellation hotelOrderRowCancellation, APIHotelCancelResponse apiHotelCancelResponse, String methodType) throws NumberFormatException, Exception
	{
		logger.info("################  insertOrUpdateHotelCancellation call----------:");
		logger.info("################  apiHotelCancelResponse call----------:");
		if(methodType.equalsIgnoreCase(APIHotelCancelRequest.METHOD_INITIATE) )
		{
			if(apiHotelCancelResponse != null && apiHotelCancelResponse.getStatus()!=null)
			{

				hotelOrderRowCancellation.setOrderId(hotelOrderRow.getOrderReference());
				logger.info("################  insertOrUpdateHotelCancellation call-----order reference-----:"+hotelOrderRow.getOrderReference());
				hotelOrderRowCancellation.setStatusCode(apiHotelCancelResponse.getStatus().getCode());
				hotelOrderRowCancellation.setAPIStatusCode(apiHotelCancelResponse.getApiStatus().getCode());
				if(apiHotelCancelResponse.getApiStatus().getCode()=="3"){

					hotelOrderRowCancellation.setStatusMessage("Success");
				}else{
					hotelOrderRowCancellation.setStatusMessage(apiHotelCancelResponse.getStatus().getMessage());
				}
				hotelOrderRowCancellation.setAPIStatusMessage(apiHotelCancelResponse.getApiStatus().getMessage());



				if(apiHotelCancelResponse.getApiUniqueId() != null)
				{
					hotelOrderRowCancellation.setAPIConfirmationNumber(apiHotelCancelResponse.getApiUniqueId().getID());
					hotelOrderRowCancellation.setConfirmationNumber(apiHotelCancelResponse.getUniqueId().getID());
				}
				if(apiHotelCancelResponse.getApiUniqueId() != null && apiHotelCancelResponse.getApiCancelRule() != null && apiHotelCancelResponse.getCancelRule()!=null)
				{
					hotelOrderRowCancellation.setChargeType(apiHotelCancelResponse.getCancelRule().getType());
					hotelOrderRowCancellation.setPaymentType("WALLET");
					hotelOrderRowCancellation.setAPIChargeType(apiHotelCancelResponse.getApiCancelRule().getType());
					hotelOrderRowCancellation.setAPIChargeAmount(apiHotelCancelResponse.getApiCancelRule().getAmount());
					hotelOrderRowCancellation.setAPIBookingAmount(apiHotelCancelResponse.getApiCancelRule().getAPIAmount());
					hotelOrderRowCancellation.setCreditNoteNo(apiHotelCancelResponse.getApiCancelRule().getCreditNoteNo());
					hotelOrderRowCancellation.setCreditNoteCreatedOn(apiHotelCancelResponse.getApiCancelRule().getCreditNoteCreatedOn());
					hotelOrderRow.setCancelMode(hotelOrderRow.getBookingMode());
					hotelOrderRowCancellation.setHotelOrderRow(hotelOrderRow);
					if(hotelOrderRowCancellation.getAPIChargeAmount()==null){
						hotelOrderRowCancellation.setAPIChargeAmount(new BigDecimal("0.00"));
					}
					hotelOrderRowCancellation.setAPIRefundAmount(apiHotelCancelResponse.getApiCancelRule().getRefundAmount());
					if(hotelOrderRowCancellation.getAPIRefundAmount()==null){
						hotelOrderRowCancellation.setAPIRefundAmount(new BigDecimal("0.00"));
					}
					hotelOrderRowCancellation.setAPICurrency(apiHotelCancelResponse.getApiCancelRule().getCurrencyCode());
					hotelOrderRowCancellation.setAPIPaymentType("WALLET");
				}
				else
				{
					hotelOrderRowCancellation.setChargeType("PERCENTAGE");
					hotelOrderRowCancellation.setPaymentType("WALLET");
					hotelOrderRowCancellation.setAPIChargeType("PERCENTAGE");
					hotelOrderRowCancellation.setAPIChargeAmount(new BigDecimal(100));
					hotelOrderRowCancellation.setAPIRefundAmount(new BigDecimal(0));
					hotelOrderRowCancellation.setAPICurrency(hotelOrderRow.getApiCurrency());
					hotelOrderRowCancellation.setAPIPaymentType("WALLET");
				}

				hotelOrderRowCancellation = hotelOrderDao.insertOrUpdateHotelOrderRowCancellation(hotelOrderRowCancellation);
				logger.info("################  insertOrUpdateHotelCancellation call------inserted / updated----:");

			}
		}
		else
		{
			if(apiHotelCancelResponse != null && apiHotelCancelResponse.getStatus()!=null)
			{
				if(hotelOrderRowCancellation == null)
					hotelOrderRowCancellation = new HotelOrderRowCancellation();
				hotelOrderRowCancellation.setOrderId(hotelOrderRow.getOrderReference());
				logger.info("################  insertOrUpdateHotelCancellation call-----order reference-----:"+hotelOrderRow.getOrderReference());
				hotelOrderRowCancellation.setStatusCode(apiHotelCancelResponse.getStatus().getCode());
				hotelOrderRowCancellation.setStatusMessage(apiHotelCancelResponse.getStatus().getMessage());
				hotelOrderRowCancellation.setAPIStatusCode(apiHotelCancelResponse.getApiStatus().getCode());
				hotelOrderRowCancellation.setAPIStatusMessage(apiHotelCancelResponse.getApiStatus().getMessage());

				if(apiHotelCancelResponse.getApiUniqueId() != null)
				{
					hotelOrderRowCancellation.setAPIConfirmationNumber(apiHotelCancelResponse.getApiUniqueId().getID());
					hotelOrderRowCancellation.setConfirmationNumber(apiHotelCancelResponse.getUniqueId().getID());
				}
				if(apiHotelCancelResponse.getApiUniqueId() != null && apiHotelCancelResponse.getApiCancelRule() != null && apiHotelCancelResponse.getCancelRule()!=null)
				{
					hotelOrderRowCancellation.setChargeType(apiHotelCancelResponse.getCancelRule().getType());
					hotelOrderRowCancellation.setPaymentType("WALLET");
					hotelOrderRowCancellation.setAPIChargeType(apiHotelCancelResponse.getApiCancelRule().getType());
					hotelOrderRowCancellation.setAPIChargeAmount(apiHotelCancelResponse.getApiCancelRule().getAmount());
					hotelOrderRowCancellation.setAPIRefundAmount(apiHotelCancelResponse.getApiCancelRule().getRefundAmount());
					hotelOrderRowCancellation.setAPICurrency(apiHotelCancelResponse.getApiCancelRule().getCurrencyCode());
					hotelOrderRowCancellation.setAPIPaymentType("WALLET");
				}
				else
				{
					hotelOrderRowCancellation.setChargeType("PERCENTAGE");
					hotelOrderRowCancellation.setPaymentType("WALLET");
					hotelOrderRowCancellation.setAPIChargeType("PERCENTAGE");
					hotelOrderRowCancellation.setAPIChargeAmount(new BigDecimal(100));
					hotelOrderRowCancellation.setAPIRefundAmount(new BigDecimal(0));
					hotelOrderRowCancellation.setAPICurrency(hotelOrderRow.getApiCurrency());
					hotelOrderRowCancellation.setAPIPaymentType("WALLET");
				}

				hotelOrderRowCancellation = hotelOrderDao.insertOrUpdateHotelOrderRowCancellation(hotelOrderRowCancellation);
				logger.info("################  insertOrUpdateHotelCancellation call------inserted / updated----:");

			}
		}

		return hotelOrderRowCancellation;
	}

	public static HotelOrderRow updateCancellationPoliciesDB(HotelOrderDao hotelOrderDao, HotelOrderRow hotelOrderRow, HotelTransactionTemp ht, APIHotelBook apiHotelBook, String bookStatus, String payStatus, String actionStatus) throws NumberFormatException, Exception
	{
		logger.info("HotelPreBookController----apiHotelBook:"+apiHotelBook);
		logger.info("HotelPreBookController----apiHotelBook.getPreBookRes():"+apiHotelBook.getBookRes());

		if(apiHotelBook.getBookRes() != null && apiHotelBook.getBookRes().getCancellationInformations() != null && apiHotelBook.getBookRes().getCancellationInformations().getCancellationInformations() != null)
		{
			List<HotelOrderCancellationPolicy> hotelOrderCancellationPolicies = new ArrayList<HotelOrderCancellationPolicy>();
			//each hotel order may have n no of cancellation policies.....
			for (CancellationInformation cancellationInformation : apiHotelBook.getBookRes().getCancellationInformations().getCancellationInformations()) {
				try
				{
					HotelOrderCancellationPolicy hotelOrderCancellationPolicy = new HotelOrderCancellationPolicy();
					hotelOrderCancellationPolicy.setCancellationDay(-1);
					hotelOrderCancellationPolicy.setCreatedAt(new Timestamp(new Date().getTime()));
					hotelOrderCancellationPolicy.setCurrency(cancellationInformation.getCurrency());
					hotelOrderCancellationPolicy.setEndDate(getFormattedDateFromStringSQL(cancellationInformation.getEndDate()));
					hotelOrderCancellationPolicy.setFeeAmount(cancellationInformation.getChargeAmount());
					hotelOrderCancellationPolicy.setFeeType(cancellationInformation.getChargeType());
					hotelOrderCancellationPolicy.setFormattedFeeAmount(cancellationInformation.getChargeAmount().toPlainString());
					hotelOrderCancellationPolicy.setFromDate(getFormattedDateFromStringSQL(cancellationInformation.getStartDate()));
					hotelOrderCancellationPolicy.setHotelOrderRow(hotelOrderRow);
					hotelOrderCancellationPolicy.setRemarks(apiHotelBook.getBookRes().getCancellationInformations().getInfo());

					//to be merged for all rooms to be booked...
					//hotelOrderCancellationPolicy.setRoomInfo(hor);
					hotelOrderCancellationPolicy.setStartDate(getFormattedDateFromStringSQL(cancellationInformation.getStartDate()));
					hotelOrderCancellationPolicy.setUpdatedAt(new Timestamp(new Date().getTime()));
					hotelOrderCancellationPolicy.setVersion(-1);
					hotelOrderCancellationPolicies.add(hotelOrderCancellationPolicy);
					logger.info("hotelOrderCancellationPolicies data saved--------------:");
				}
				catch(Exception ex)
				{
					logger.info("roomOrderCancellationPolicy data Format error --:"+ex.getMessage());
					logger.error(ex);
				}
			}
			hotelOrderRow.setHotelOrderCancellationPolicies(hotelOrderCancellationPolicies);
			logger.info("hotelOrderCancellationPolicies data saved--------------:");
		}
		return hotelOrderRow;

	}


	public static boolean isValid(HotelSearchCity hotelSearchCity, HotelApiCredentials api)
	{
		boolean isValid = false;
		switch (api.getId()) {
		case HotelApiCredentials.API_DESIA_IND:
			logger.info("api validity check----desiya ---:");
			if(hotelSearchCity!= null && hotelSearchCity.getTgCity() != null && hotelSearchCity.getTgCity().getCity() != null && hotelSearchCity.getTgCity().getCountryName() != null)
				isValid =  true;
			else
				isValid =  false;
			break;
		case HotelApiCredentials.API_REZNEXT_IND:
			logger.info("api validity check----reztnezt ---:");
			if(hotelSearchCity != null && hotelSearchCity.getReznextCity() != null && hotelSearchCity.getReznextCity().getCity() != null && hotelSearchCity.getReznextCity().getCountryCode() != null)
				isValid =  true;
			else
				isValid =  false;
			break;
		case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
			logger.info("api validity check----rezlive ---:");
			if(hotelSearchCity != null && hotelSearchCity.getRezliveCity() != null && hotelSearchCity.getRezliveCity().getCity() != null && hotelSearchCity.getRezliveCity().getCountryCode() != null)
				isValid =  true;
			else
				isValid =  false;
			break;
		case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
			logger.info("api validity check----tayyarah ---:");
			isValid =  true;
			break;
		case HotelApiCredentials.API_LINTAS_REPOSITORY:
			logger.info("api validity check----lintas reposit ---:");
			isValid =  true;
			break;
		case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
			logger.info("api validity check----lintas international ---:");
			isValid =  true;
			break;
		case HotelApiCredentials.API_TBO_INTERNATIONAL:
			logger.info("api validity check----tbo international ---:");
			isValid =  true;
			break;
		default:
			break;
		}
		return (isValid && api.isEnabled());
	}



	public static boolean isAPIValid(HotelSearchCommand hsc, HotelApiCredentials api)
	{
		boolean isValid = false;
		switch (api.getId()) {
		case HotelApiCredentials.API_DESIA_IND:
			logger.info("api validity check----desiya ---:");
			if(hsc != null && hsc.getSearchCity() != null && hsc.getSearchCity().getTgCity() != null && hsc.getSearchCity().getTgCity().getCity() != null && hsc.getSearchCity().getTgCity().getCountryName() != null)
				isValid =  true;
			else
				isValid =  false;
			break;
		case HotelApiCredentials.API_REZNEXT_IND:
			logger.info("api validity check----reztnezt ---:");
			if(hsc != null && hsc.getSearchCity() != null && hsc.getSearchCity().getReznextCity() != null && hsc.getSearchCity().getReznextCity().getCity() != null && hsc.getSearchCity().getReznextCity().getCountryCode() != null)
				isValid =  true;
			else
				isValid =  false;
			break;
		case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
			logger.info("api validity check----rezlive ---:");
			if(hsc != null && hsc.getSearchCity() != null && hsc.getSearchCity().getRezliveCity() != null && hsc.getSearchCity().getRezliveCity().getCity() != null && hsc.getSearchCity().getRezliveCity().getCountryCode() != null)
				isValid =  true;
			else
				isValid =  false;
			break;
		case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
			logger.info("api validity check----tayyarah ---:");
			isValid =  true;
			break;
		case HotelApiCredentials.API_LINTAS_REPOSITORY:
			logger.info("api validity check----lintas reposit ---:");
			isValid =  true;
			break;
		case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
			logger.info("api validity check----lintas international ---:");
			isValid =  true;
			break;
		case HotelApiCredentials.API_TBO_INTERNATIONAL:
			logger.info("api validity check----tbo international ---:");
			isValid =  true;
			break;
		default:
			break;
		}
		return isValid;
	}



	public static boolean isTestingAndApiValid(boolean isTesting, String apiids, HotelApiCredentials api)
	{
		boolean isValid = false;
		List<Integer> apiIdList =new ArrayList<Integer>();
		if(apiids == null || apiids.length() == 0)
			return false;
		else
		{
			try{
				String delimeter = "\\,";
				String[] ids = apiids.split(delimeter);
				int index = 0;
				for (String string : ids) {
					Integer id = Integer.valueOf(index);
					index ++;
					apiIdList.add(id);
				}
			}
			catch(Exception e)
			{
				logger.info("Exception happened.."+e.getMessage());
			}

		}
		return isValid;
	}
	public static boolean isTestingAndApiValid(boolean isTesting, List<Integer> apiids, HotelApiCredentials api)
	{
		if(isTesting == false)
			return false;
		else if(apiids != null && apiids.contains(api.getId()))
			return true;
		else
			return false;

	}
	public static List<Integer> getSelectedApiIdList(boolean isTesting, String apiids)
	{
		List<Integer> apiIdList =new ArrayList<Integer>();
		if(isTesting == false || apiids == null || apiids.length() == 0)
			return apiIdList;
		else
		{
			try{
				String delimeter = "\\,";
				String[] ids = apiids.split(delimeter);
				for (String string : ids) {
					Integer id = Integer.valueOf(string);
					apiIdList.add(id);
				}
			}
			catch(Exception e)
			{
				logger.info("Exception happened.."+e.getMessage());
			}

		}
		return apiIdList;
	}

	public static List<Integer> getAllApiList()
	{
		List<Integer> apiIdList =new ArrayList<Integer>();
		apiIdList.add(HotelApiCredentials.API_DESIA_IND);
		apiIdList.add(HotelApiCredentials.API_REZNEXT_IND);
		apiIdList.add(HotelApiCredentials.API_TRAVELPORT_ALL);
		apiIdList.add(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
		apiIdList.add(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL);
		apiIdList.add(HotelApiCredentials.API_LINTAS_REPOSITORY);
		apiIdList.add(HotelApiCredentials.API_LINTAS_INTERNATIONAL);
		apiIdList.add(HotelApiCredentials.API_TBO_INTERNATIONAL);
		apiIdList.add(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL);
		return apiIdList;
	}


	//Return Elapsed time in seconds.......
	public static double getElapsedTime(long startTime) {
		long endTime = System.currentTimeMillis();
		return (double) (endTime - startTime) / (1000);
	}

	public String getAge(String DOB){
		LocalDate birthdate = new LocalDate (1970-01-20);
		LocalDate now = new LocalDate();
		Years ageIS = Years.yearsBetween(birthdate, now);
		String age = "0";
		try {
			age = String.valueOf(ageIS.getYears());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception", e);
			throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		return age;
	}

	public static String getHotelConfirmationNumber(HotelOrderRow hor) throws Exception
	{
		String hotelConfirmationNumber = (hor.getApiConfirmationNo()!=null)?hor.getApiConfirmationNo():")))0";
		Integer apiProvider = Integer.valueOf((hor.getApiProvoder()!=null)?hor.getApiProvoder():"0");
		logger.info("###########API confirmation to Tayyarah----hotelConfirmationNumber:"+hotelConfirmationNumber);
		switch (apiProvider) {
		case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
			break;
		case HotelApiCredentials.API_DESIA_IND:
			//code to remove TBO
			if(hotelConfirmationNumber != null && hotelConfirmationNumber.length()>3)
				hotelConfirmationNumber = hotelConfirmationNumber.substring(3);
			break;
		case HotelApiCredentials.API_REZNEXT_IND:

			break;

		case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
			//code to remove TBO
			if(hotelConfirmationNumber != null && hotelConfirmationNumber.length()>3)
				hotelConfirmationNumber = hotelConfirmationNumber.substring(3);
			break;
		case HotelApiCredentials.API_LINTAS_REPOSITORY:

			break;
		case HotelApiCredentials.API_LINTAS_INTERNATIONAL:

			break;
		case HotelApiCredentials.API_TBO_INTERNATIONAL:
			//code to remove TBO
			if(hotelConfirmationNumber != null && hotelConfirmationNumber.length()>3)
				hotelConfirmationNumber = hotelConfirmationNumber.substring(3);
			break;

		default:
			//code to remove for default
			if(hotelConfirmationNumber != null && hotelConfirmationNumber.length()>3)
				hotelConfirmationNumber = hotelConfirmationNumber.substring(3);
			break;

		}
		hotelConfirmationNumber = CommonConfig.GetCommonConfig().getInvoice_hotel_prefix() + hotelConfirmationNumber;
		return hotelConfirmationNumber;
	}

	public static Integer getGuestTotalCount(HotelSearchCommand hsc){
		int guestcount = 0;
		try{
			if(hsc.getGuestcounts()!=null && hsc.getGuestcounts().size() > 0){
				for (GuestCount guestCount : hsc.getGuestcounts()) {
					guestcount += guestCount.getCount();
				}
			}
		}catch(Exception e){
			logger.info("###########getGuestTotalCount Error:"+e.getMessage());

		}

		return guestcount;
	}

	public static BigDecimal getPayableAmount(HotelSearchCommand hs,CompanyConfig companyConfig,TotalType base){

		BigDecimal payableAmount = new BigDecimal(0);

		try{

			List<Entry<String, CommissionDetails>> commissionList = new LinkedList<Entry<String, CommissionDetails>>(hs.getHotelMarkupCommissionDetails().getCommissionDetailsMap().entrySet());
			// Sorting the list based on values
			Collections.sort(commissionList, new Comparator<Entry<String, CommissionDetails>>() {
				@Override
				public int compare(Entry<String, CommissionDetails> c1, Entry<String, CommissionDetails> c2) {
					Integer companyid1 = Integer.valueOf(c1.getValue().getCompanyId());
					Integer companyid2 = Integer.valueOf(c2.getValue().getCompanyId());
					return companyid1.compareTo(companyid2);
				}
			});		

			int noofdays = CommonUtil.getNoofStayDays(hs);
			int noofguests =  CommonUtil.getGuestTotalCount(hs);
			int totalmarkupmultiplier = noofdays * hs.getNoofrooms();;
			int totalmarkupdivider = 1;

			BigDecimal commissionalAmount = base.getTotalAmountPayable();
			for (Entry<String, CommissionDetails> commissionEntry : commissionList) {

				CommissionDetails agentcommission = commissionEntry.getValue();
				String companyId = agentcommission.getCompanyId();
				List<HotelMarkup> markups = hs.getHotelMarkupCommissionDetails().getMarkups().get(agentcommission.getCompanyId());

				BigDecimal companycommissionAMount = new BigDecimal("0.00");
				if(agentcommission.getRateType().equalsIgnoreCase("Commission")){
					if(agentcommission.getCommissionType().equalsIgnoreCase("Fixed")){
						companycommissionAMount = agentcommission.getCommissionAmount();
					}else{
						companycommissionAMount = commissionalAmount
								.multiply(agentcommission.getCommissionAmount()).divide(new BigDecimal("100"));
					}
				}

				for (HotelMarkup hotelMarkup : markups) {
					if(companyConfig != null){		
						if(companyConfig.getCompany_id() == hotelMarkup.getCreatedbyCompanyId()){	
							BigDecimal markUpAmount = HotelMarkUpUtil.applyMarkUp(hotelMarkup, base.getTaxes().getAmount(),totalmarkupdivider, totalmarkupmultiplier);
							BigDecimal	baseTaxB2B = AmountRoundingModeUtil.roundingModeForHotel(base.getTaxes().getAmount().subtract(markUpAmount));
							BigDecimal apiDiscount = new BigDecimal("0");
							for (Discount discount : base.getDiscounts()) {
								apiDiscount = (discount.getAmountBeforeTax() == null)?new BigDecimal("0"):discount.getAmountBeforeTax();
							}
							BigDecimal baseamountBTaxB2B = base.getAmountBeforeTax().subtract(apiDiscount);
							BigDecimal	baseamountATaxB2B = baseamountBTaxB2B.add(baseTaxB2B);
							baseamountATaxB2B = AmountRoundingModeUtil.roundingModeForHotel(baseamountATaxB2B);							
							payableAmount = AmountRoundingModeUtil.roundingModeForHotel(baseamountATaxB2B);
						}
					}
				}			

			}
		}
		catch(Exception e){

		}


		return payableAmount;
	}

	public static Map<Integer,CutandPayModel> getCutandPayModelUsersHotel(APIHotelBook apiHotelBook,CompanyConfig companyConfig,List<User> userList){
		Map<Integer,CutandPayModel> cutandpayMap = new LinkedHashMap<>();		

		try{
			int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
			int noofguests =  CommonUtil.getGuestTotalCount(apiHotelBook.getSearch());
			int totalmarkupmultiplier = noofdays * apiHotelBook.getSearch().getNoofrooms();;
			int totalmarkupdivider = 1;
			BigDecimal payableamt = apiHotelBook.getBookingRate().getTotalPayableAmt();
			Map<String, BigDecimal> markups = apiHotelBook.getPreBookRes().getMarkupmap();
			Map<String, CommissionDetails> commisions = apiHotelBook.getPreBookRes().getCommissionmap();

			for (User payableUser : userList) {
				BigDecimal hotelMarkupAmount =  new BigDecimal(0);
				BigDecimal commisionAmount = new BigDecimal(0);
				hotelMarkupAmount = markups!=null?markups.get(String.valueOf(payableUser.getCompanyid())):new BigDecimal(0);
				commisionAmount = commisions!=null && commisions.get(String.valueOf(payableUser.getCompanyid()))!=null && commisions.get(String.valueOf(payableUser.getCompanyid())).getCommissionAmount()!=null?commisions.get(String.valueOf(payableUser.getCompanyid())).getCommissionAmount():new BigDecimal(0);
				BigDecimal hotelMarkupAmountAllDays=hotelMarkupAmount.multiply(new BigDecimal(totalmarkupmultiplier));
				CutandPayModel cutandpay = new CutandPayModel();
				if(String.valueOf(payableUser.getId()).equalsIgnoreCase(apiHotelBook.getBook().getUserid())){
					BigDecimal payableamtInner = apiHotelBook.getBookingRate().getTotalPayableAmt().subtract(commisionAmount);
					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamtInner);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.HOTEL_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				else{
					payableamt = payableamt.subtract(hotelMarkupAmountAllDays);
					payableamt = payableamt.subtract(commisionAmount);
					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamt);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.HOTEL_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				cutandpayMap.put(payableUser.getId(), cutandpay);
			}
		}catch(Exception e){

		}
		return cutandpayMap;
	}

	public static Map<Integer,CutandPayModel> getCutandPayModelUsersFlight(FlightPriceResponse flightPriceResponse,CompanyConfig companyConfig,List<User> userList,FlightCustomerDetails flightCustomerDetails){
		Map<Integer,CutandPayModel> cutandpayMap = new LinkedHashMap<>();		
		try{
			BigDecimal payableamt = flightPriceResponse.getTotalPayableAmount();
			Map<String, List<FlightMarkUpConfig>> markups = flightPriceResponse.getFlightMarkUpConfiglistMap();
			List<CommissionDetails> commissions = flightPriceResponse.getMarkupCommissionDetails().getCommissionDetailslist();
			BigDecimal totalAdult = new BigDecimal(flightPriceResponse.getFlightsearch().getAdult());
			BigDecimal totalKid = new BigDecimal(flightPriceResponse.getFlightsearch().getKid());
			BigDecimal totalInfant = new BigDecimal(flightPriceResponse.getFlightsearch().getInfant());
			BigDecimal totalPassengers = totalAdult.add(totalKid).add(totalInfant);
			Map<String, BigDecimal> flightMarkups =  new LinkedHashMap<>();		
			Map<String, BigDecimal> flightCommissions =  new LinkedHashMap<>();		

			for (Entry<String, List<FlightMarkUpConfig>> entry : markups.entrySet()) {
				List<FlightMarkUpConfig> FlightMarkUpConfiglist = entry.getValue();
				String companyid = entry.getKey();
				if (FlightMarkUpConfiglist != null) {
					for (int i = 0; i < FlightMarkUpConfiglist.size(); i++) {
						FlightMarkUpConfig flightMarkUpConfig = FlightMarkUpConfiglist.get(i);
						String markup = flightMarkUpConfig.getMarkup();
						BigDecimal MarkupValue = new BigDecimal(markup);
						MarkupValue = MarkupValue.multiply(totalPassengers);
						flightMarkups.put(companyid, MarkupValue);
					}
				}
			}

			for(CommissionDetails commissionDetails: commissions){
				BigDecimal commissionAmt = commissionDetails.getCommissionAmount();
				String companyId = commissionDetails.getCompanyId();
				flightCommissions.put(companyId, commissionAmt);
			}


			for (User payableUser : userList) {
				BigDecimal markupAmount = new BigDecimal(0);
				BigDecimal commisionAmount = new BigDecimal(0);
				markupAmount = flightMarkups!=null && flightMarkups.size()>0 ?flightMarkups.get(String.valueOf(payableUser.getCompanyid())):new BigDecimal(0);
				commisionAmount = flightCommissions!=null && flightCommissions.get(String.valueOf(payableUser.getCompanyid()))!=null ?flightCommissions.get(String.valueOf(payableUser.getCompanyid())):new BigDecimal(0);
				BigDecimal flightMarksForAllPassengers = markupAmount.multiply(totalPassengers);
				CutandPayModel cutandpay = new CutandPayModel();
				if(String.valueOf(payableUser.getId()).equalsIgnoreCase(flightCustomerDetails.getUserid())){
					BigDecimal payableamtInner = flightPriceResponse.getTotalPayableAmount().subtract(commisionAmount);
					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamtInner);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				else{
					payableamt = payableamt.subtract(flightMarksForAllPassengers);
					payableamt = payableamt.subtract(commisionAmount);
					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamt);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				cutandpayMap.put(payableUser.getId(), cutandpay);
			}
		}catch(Exception e){

		}
		return cutandpayMap;
	}

	public static Map<Integer,CutandPayModel> getCutandPayModelUsersFlightSpecialRoundTrip(FlightPriceResponse flightPriceResponse,CompanyConfig companyConfig,List<User> userList,FlightCustomerDetails flightCustomerDetails,boolean isSpecial){
		Map<Integer,CutandPayModel> cutandpayMap = new LinkedHashMap<>();		
		try{
			BigDecimal payableamt = new BigDecimal(0);
			if(isSpecial)
				payableamt = flightPriceResponse.getSpecialFareFlightSegment().getPayableAmount();
			else
				payableamt = flightPriceResponse.getFareFlightSegment().getPayableAmount();

			Map<String, List<FlightMarkUpConfig>> markups = flightPriceResponse.getFlightMarkUpConfiglistMap();
			List<CommissionDetails> commissions = flightPriceResponse.getMarkupCommissionDetails().getCommissionDetailslist();
			BigDecimal totalAdult = new BigDecimal(flightPriceResponse.getFlightsearch().getAdult());
			BigDecimal totalKid = new BigDecimal(flightPriceResponse.getFlightsearch().getKid());
			BigDecimal totalInfant = new BigDecimal(flightPriceResponse.getFlightsearch().getInfant());
			BigDecimal totalPassengers = totalAdult.add(totalKid).add(totalInfant);
			Map<String, BigDecimal> flightMarkups =  new LinkedHashMap<>();		
			Map<String, BigDecimal> flightCommissions =  new LinkedHashMap<>();		

			for (Entry<String, List<FlightMarkUpConfig>> entry : markups.entrySet()) {
				List<FlightMarkUpConfig> FlightMarkUpConfiglist = entry.getValue();
				String companyid = entry.getKey();
				if (FlightMarkUpConfiglist != null) {
					for (int i = 0; i < FlightMarkUpConfiglist.size(); i++) {
						FlightMarkUpConfig flightMarkUpConfig = FlightMarkUpConfiglist.get(i);
						String markup = flightMarkUpConfig.getMarkup();
						BigDecimal MarkupValue = new BigDecimal(markup);
						MarkupValue = MarkupValue.multiply(totalPassengers);
						flightMarkups.put(companyid, MarkupValue);
					}
				}
			}

			for(CommissionDetails commissionDetails: commissions){
				BigDecimal commissionAmt = commissionDetails.getCommissionAmount();
				String companyId = commissionDetails.getCompanyId();
				flightCommissions.put(companyId, commissionAmt);
			}


			for (User payableUser : userList) {
				BigDecimal markupAmount = new BigDecimal(0);
				BigDecimal commisionAmount = new BigDecimal(0);
				markupAmount = flightMarkups!=null && flightMarkups.size() >0?flightMarkups.get(String.valueOf(payableUser.getCompanyid())):new BigDecimal(0);
				commisionAmount = flightCommissions!=null && flightCommissions.get(String.valueOf(payableUser.getCompanyid()))!=null ?flightCommissions.get(String.valueOf(payableUser.getCompanyid())):new BigDecimal(0);
				BigDecimal flightMarksForAllPassengers = markupAmount.multiply(totalPassengers);
				CutandPayModel cutandpay = new CutandPayModel();
				if(String.valueOf(payableUser.getId()).equalsIgnoreCase(flightCustomerDetails.getUserid())){
					BigDecimal payableamtInner = new BigDecimal(0);
					if(isSpecial)
						payableamtInner = flightPriceResponse.getSpecialFareFlightSegment().getPayableAmount().subtract(commisionAmount);
					else
						payableamtInner = flightPriceResponse.getFareFlightSegment().getPayableAmount().subtract(commisionAmount);

					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamtInner);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				else{
					payableamt = payableamt.subtract(flightMarksForAllPassengers);
					payableamt = payableamt.subtract(commisionAmount);
					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamt);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				cutandpayMap.put(payableUser.getId(), cutandpay);
			}
		}catch(Exception e){

		}
		return cutandpayMap;
	}

	public static LinkedList<Company> getParentCompanyBottomToTop(int companyId, CompanyDao CDAO) {
		Company company= CDAO.getCompany(companyId);
		Company companyTemp=company;
		LinkedList<Company> companies= new LinkedList<>();
		companies.add(companyTemp);
		while(companyTemp!=null && companyTemp.getCompanyRole()!=null && !companyTemp.getCompanyRole().isSuperUser())
		{
			companyTemp =CDAO.getParentCompany(companyTemp);
			companies.add(companyTemp);
		}
		return companies;

	}

	public static List<User> getUsersAllWithUserModeBottomToTop(List<Company> companyListBottomToTop, CompanyDao CDAO, User currentUser) throws Exception {
		List<User> userList= new LinkedList<>();
		if(companyListBottomToTop!=null && companyListBottomToTop.size()>0)
		{
			User userTemp = new User();
			for(Company companyInner : companyListBottomToTop)
			{
				if(currentUser.getCompanyid()==companyInner.getCompanyid())
				{
					userTemp = currentUser;
				}
				else{
					userTemp =CDAO.getUserByCompanyEmail(companyInner.getEmail());
				}
				userList.add(userTemp);

			}
		}
		return userList;
	}


}
