package com.tayyarah.hotel.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.dao.MoneyExchangeDaoImp;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.email.dao.EmailDaoImp;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.hotel.dao.HotelBookingDao;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelsInfo;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.util.HotelApiCredentials.ApiRateInfo;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.TayyarahPullerTask;
import com.tayyarah.user.dao.UserWalletDAO;


public class HotelAnalyzer {
	public static final Logger logger = Logger.getLogger(HotelAnalyzer.class);
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	HotelOrderDao hotelOrderDao;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	FlightBookingDao FBDAO;
	@Autowired
	CompanyDao CDAO;
	@Autowired
	UserWalletDAO AWDAO;
	@Autowired
	HotelBookingDao hotelBookingDao;
	@Autowired
	HotelIdFactoryImpl hotelIdFactory;
	@Autowired
	EmailDaoImp emaildao;
	@Autowired
	MoneyExchangeDaoImp moneydao;
	@Autowired
	CurrencyManager currencyManager;

	private Map<Integer, ApiRateInfo> apiCurrencyRateMap = null;
	public static final int HOTEL_NEW = 0;
	public static final int HOTEL_EXISTING_LOWER_PRICE = 1;
	public static final int HOTEL_EXISTING_HIGHER_PRICE = 2;

	public HotelAnalyzer() {
		// TODO Auto-generated constructor stub
	}



	public Map<Integer, ApiRateInfo> initApiCurrencyRateMap(String bookingCurrency, String baseCurrency) {
		//if(apiCurrencyRateMap == null || apiCurrencyRateMap.size() == 0)
		//{
		apiCurrencyRateMap = new HashMap<Integer, ApiRateInfo>();
		String apiCurrency = "INR";
		Map<String,Double> apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
		Map<String,Double> baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
		BigDecimal exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
		BigDecimal exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));

		ApiRateInfo ApiRateInfo = new ApiRateInfo(-1, apiCurrency, baseCurrency, bookingCurrency, exRateApiToBase, exRateBaseToBooking);
		for (Integer apiid : CommonUtil.getAllApiList()) {

			switch (apiid) {
			case HotelApiCredentials.API_DESIA_IND:
				apiCurrency = "INR";
				apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
				baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
				exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
				exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));
				break;
			case HotelApiCredentials.API_TRAVELPORT_ALL:
				apiCurrency = "INR";
				apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
				baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
				exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
				exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));
				break;
			case HotelApiCredentials.API_REZNEXT_IND:
				apiCurrency = "INR";
				apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
				baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
				exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
				exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));
				break;
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				apiCurrency = "INR";
				apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
				baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
				exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
				exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));
				break;
			case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
				apiCurrency = TayyarahPullerTask.API_CURRENCY;
				apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
				baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
				exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
				exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));
				break;
			case HotelApiCredentials.API_LINTAS_REPOSITORY:
				HotelApiCredentials apiLintasReposit = HotelApiCredentials.getApiLintasReposit();
				apiCurrency = apiLintasReposit.getApiCurrency();
				apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
				baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
				exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
				exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));
				break;
			case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
				HotelApiCredentials apiLintas = HotelApiCredentials.getApiLintas();
				apiCurrency = apiLintas.getApiCurrency();
				apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
				baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
				exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
				exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));
				break;
			case HotelApiCredentials.API_TBO_INTERNATIONAL:
				apiCurrency = "INR";
				apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
				baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
				exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
				exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));
				break;
			default:
				apiCurrency = "INR";
				apiToBase = moneydao.getCurrencyRate(baseCurrency, apiCurrency);
				baseToBooking = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
				exRateApiToBase  = new BigDecimal(apiToBase.get("value"));
				exRateBaseToBooking = new BigDecimal(baseToBooking.get("value"));
				break;
			}		
			ApiRateInfo = new ApiRateInfo(apiid, apiCurrency, baseCurrency, bookingCurrency, exRateApiToBase, exRateBaseToBooking);
			apiCurrencyRateMap.put(apiid, ApiRateInfo);
		}		
		return apiCurrencyRateMap;
	}

	public APIHotelMap reDefineHotelResponseAndMergexxss(APIHotelMap apimap, TreeMap<Integer, TreeMap<String, RoomStay>> roomStaysMap, String bookingCurrency, String baseCurrency,  HotelSearchCommand hs) throws HibernateException, IOException, ParseException
	{
		apiCurrencyRateMap = initApiCurrencyRateMap(bookingCurrency, baseCurrency);
		TreeMap<String, RoomStay> roomstaysDesiya = roomStaysMap.get(HotelApiCredentials.API_DESIA_IND) == null?new TreeMap<String, RoomStay>():roomStaysMap.get(HotelApiCredentials.API_DESIA_IND);
		TreeMap<String, RoomStay> roomstaysTBO = roomStaysMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL) == null?new TreeMap<String, RoomStay>():roomStaysMap.get(HotelApiCredentials.API_DESIA_IND);
		TreeMap<String, RoomStay> roomstaysFinal = roomstaysDesiya;
		apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS));
		BigDecimal minprice = new BigDecimal("0");
		BigDecimal maxprice = new BigDecimal("0");
		for (Entry<String, RoomStay> entry : roomstaysFinal.entrySet()) {
			String hotelId = entry.getKey();
			RoomStay rs = entry.getValue();

			BigDecimal currentprice = rs.getBasicPropertyInfo().getBookingPrice();
			if(minprice.compareTo(new BigDecimal("0") ) == 0)
				minprice = currentprice;
			else if(currentprice.compareTo(minprice) == -1)
				minprice = currentprice;

			if(maxprice.compareTo(new BigDecimal("0") ) == 0)
				maxprice = currentprice;
			else if(currentprice.compareTo(maxprice) == 1)
				maxprice = currentprice;

		}

		HotelsInfo hotelsInfo = new HotelsInfo();
		TPAExtensions textensions = apimap.getTpaExtensions();
		HotelsInfo thotelsInfo = textensions.getHotelsInfo();
		thotelsInfo.setTotal(roomstaysFinal.size());
		thotelsInfo.setMaxPrice(maxprice);
		thotelsInfo.setMinPrice(minprice);
		thotelsInfo.setDeals(hotelsInfo.getDeals());
		thotelsInfo.setAvailable(roomstaysFinal.size());
		textensions.setHotelsInfo(thotelsInfo);
		apimap.setRoomStays(roomstaysFinal);
		apimap.setTpaExtensions(textensions);
		if(roomstaysFinal.size() == 0)
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_CODE_ERROR + " No Hotels available for particular search"));
		return apimap;
	}

	public APIHotelMap removeDuplicates(APIHotelMap apimap) throws HibernateException, IOException, ParseException
	{

		TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>  apiProviderMap = (apimap.getApiProviderMap() == null)? new TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>():apimap.getApiProviderMap();
		TreeMap<String, RoomStay> roomstays = apimap.getRoomStays() == null?new TreeMap<String, RoomStay>():apimap.getRoomStays() ;
		TreeMap<String, RoomStay> roomstaysFinal = new TreeMap<String, RoomStay>();
		for (Entry<String, HashMap<Integer, HashMap<String, Boolean>>> entry : apiProviderMap.entrySet()) {
			String hotelId = entry.getKey();
			RoomStay rs = roomstays.get(hotelId);
			if(rs != null)
			{
				roomstaysFinal.put(hotelId, rs);
			}
		}
		apimap.setRoomStays(roomstaysFinal);
		if(roomstaysFinal.size() == 0)
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_CODE_ERROR + " No Hotels available for particular search"));
		return apimap;
	}


	public APIHotelMap reDefineHotelResponse(APIHotelMap apimap, String bookingCurrency, String baseCurrency,  HotelSearchCommand hs,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao) throws HibernateException, IOException, ParseException
	{
		apiCurrencyRateMap = initApiCurrencyRateMap(bookingCurrency, baseCurrency);

		/// Set Service Tax component
		String decryptrdAppKey = AppControllerUtil.getDecryptedAppKey(CDAO, hs.getApikey());
		CompanyConfig companyConfig = null;
		String configId = decryptrdAppKey.substring(0,decryptrdAppKey.indexOf("-"));
		try {
			companyConfig = companyConfigDAO.getCompanyConfigByConfigId(Integer.parseInt(configId));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Company company = null;
		String companyId = decryptrdAppKey.substring(decryptrdAppKey.indexOf("-") + 1);
		try{
			company = companyDao.getCompany(Integer.parseInt(companyId));
		}catch(Exception e){
			e.printStackTrace();
		}
		Company parentCompany = null;
		try{
			parentCompany = companyDao.getParentCompany(company);
		}catch(Exception e){
			e.printStackTrace();
		}

		TreeMap<String, RoomStay> roomstays = apimap.getRoomStays();
		TreeMap<String, RoomStay> roomstaysFinal = new TreeMap<String, RoomStay>();

		apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS));
		BigDecimal minprice = new BigDecimal("0");
		BigDecimal maxprice = new BigDecimal("0");
		for (Entry<String, RoomStay> entry : roomstays.entrySet()) {
			String hotelId = entry.getKey();
			RoomStay rs = entry.getValue();

			currencyManager.fillCurrencyDataOnHotel(rs, apiCurrencyRateMap,  hs,companyConfig,company,parentCompany);

			BigDecimal currentprice = rs.getBasicPropertyInfo().getBookingPrice();
			if(minprice.compareTo(new BigDecimal("0") ) == 0)
				minprice = currentprice;
			else if(currentprice.compareTo(minprice) == -1)
				minprice = currentprice;

			if(maxprice.compareTo(new BigDecimal("0") ) == 0)
				maxprice = currentprice;
			else if(currentprice.compareTo(maxprice) == 1)
				maxprice = currentprice;

			apimap = addOrUpdate(apimap, roomstaysFinal, rs, hotelId);
		}
		HotelsInfo hotelsInfo = new HotelsInfo();
		TPAExtensions textensions = apimap.getTpaExtensions();
		HotelsInfo thotelsInfo = textensions.getHotelsInfo();
		thotelsInfo.setTotal(roomstaysFinal.size());
		thotelsInfo.setMaxPrice(maxprice);
		thotelsInfo.setMinPrice(minprice);
		thotelsInfo.setDeals(hotelsInfo.getDeals());
		thotelsInfo.setAvailable(roomstaysFinal.size());
		textensions.setHotelsInfo(thotelsInfo);
		apimap.setRoomStays(roomstaysFinal);
		apimap.setTpaExtensions(textensions);
		if(roomstaysFinal.size() == 0)
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_CODE_ERROR + " No Hotels available for particular search"));
		return apimap;
	}

	public static int isAddorUpdatable(TreeMap<String, RoomStay> roomstaysFinal, RoomStay roomStayTobeAdded)
	{
		int isAddable = HOTEL_NEW;
		for (Entry<String, RoomStay> entry : roomstaysFinal.entrySet()) {
			RoomStay rs = entry.getValue();
			if(!HotelApiComparator.isSameApiProvider(rs, roomStayTobeAdded))
				if(HotelApiComparator.isSameHotel(rs, roomStayTobeAdded))
				{
					if(HotelApiComparator.comparebyLessHotelPrice(rs, roomStayTobeAdded) == -1)
					{
						isAddable = HOTEL_EXISTING_HIGHER_PRICE;
					}
					else
					{
						isAddable = HOTEL_EXISTING_LOWER_PRICE;
					}
					break;
				}
		}
		return isAddable;
	}

	public static APIHotelMap merge(APIHotelMap apimap, TreeMap<Integer, TreeMap<String, RoomStay>> roomStaysMap, boolean removeDuplicate)
	{
		TPAExtensions textensions = apimap.getTpaExtensions();
		HotelsInfo thotelsInfo = textensions.getHotelsInfo();
		Integer duplicates = (thotelsInfo.getDuplicates() != null)?thotelsInfo.getDuplicates():new Integer(0) ;

		TreeMap<String, RoomStay> roomstaysDesiya = roomStaysMap.get(HotelApiCredentials.API_DESIA_IND) == null?new TreeMap<String, RoomStay>():roomStaysMap.get(HotelApiCredentials.API_DESIA_IND);
		TreeMap<String, RoomStay> roomstaysTBO = roomStaysMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL) == null?new TreeMap<String, RoomStay>():roomStaysMap.get(HotelApiCredentials.API_DESIA_IND);

		TreeMap<String, RoomStay> roomstaysFinal = roomstaysDesiya;

		for (Entry<String, RoomStay> entryTBO : roomstaysTBO.entrySet()) {
			RoomStay roomStayTBO = entryTBO.getValue();
			RoomStay roomStayTobeCompared = null;
			int isAddable = HOTEL_NEW;
			for (Entry<String, RoomStay> entryDesiya : roomstaysDesiya.entrySet()) {
				RoomStay rsDesiya = entryDesiya.getValue();
				if(HotelApiComparator.isSameHotel(rsDesiya, roomStayTBO))
				{
					if(HotelApiComparator.comparebyLessHotelPrice(rsDesiya, roomStayTBO) == -1)
					{
						isAddable = HOTEL_EXISTING_HIGHER_PRICE;
					}
					else
					{
						isAddable = HOTEL_EXISTING_LOWER_PRICE;
					}
					roomStayTobeCompared = rsDesiya;
					break;
				}
			}
			BasicPropertyInfoType tbasicNew = roomStayTBO.getBasicPropertyInfo();
			HashMap<Integer, String> apiProviderMapNew = tbasicNew.getApiProviderMap();
			switch (isAddable) {
			case HOTEL_NEW:
				logger.info(roomStayTBO.getBasicPropertyInfo().getHotelCode()+"#################################new hotel to be added..-");
				roomstaysFinal.put(roomStayTBO.getBasicPropertyInfo().getHotelCode(), roomStayTBO);
				break;
			case HOTEL_EXISTING_LOWER_PRICE:
				//remroomstaysFinalove the existing one
				logger.info(roomStayTBO.getBasicPropertyInfo().getHotelCode()+"#################################HOTEL_EXISTING_LOWER_PRICE to be replaced..-");
				BasicPropertyInfoType tbasicOld = roomStayTobeCompared.getBasicPropertyInfo();
				HashMap<Integer, String> apiProviderMapOld = tbasicOld.getApiProviderMap();
				apiProviderMapNew.putAll(apiProviderMapOld);
				tbasicNew.setApiProviderMap(apiProviderMapNew);
				roomStayTBO.setBasicPropertyInfo(tbasicNew);
				tbasicOld.setApiProviderMap(apiProviderMapNew);
				roomStayTobeCompared.setBasicPropertyInfo(tbasicOld);
				roomstaysFinal.remove(roomStayTobeCompared.getBasicPropertyInfo().getHotelCode());
				roomstaysFinal.put(roomStayTBO.getBasicPropertyInfo().getHotelCode(), roomStayTBO);
				duplicates = duplicates + 1;
				break;
			case HOTEL_EXISTING_HIGHER_PRICE:
				//Leave ...
				logger.info(roomStayTBO.getBasicPropertyInfo().getHotelCode()+"#################################HOTEL_EXISTING_HIGHER_PRICE to be left..-");
				BasicPropertyInfoType tbasicOld2 = roomStayTobeCompared.getBasicPropertyInfo();
				HashMap<Integer, String> apiProviderMapOld2 = tbasicOld2.getApiProviderMap();
				apiProviderMapNew.putAll(apiProviderMapOld2);
				tbasicNew.setApiProviderMap(apiProviderMapNew);
				roomStayTBO.setBasicPropertyInfo(tbasicNew);
				tbasicOld2.setApiProviderMap(apiProviderMapNew);
				roomStayTobeCompared.setBasicPropertyInfo(tbasicOld2);
				roomstaysFinal.put(roomStayTobeCompared.getBasicPropertyInfo().getHotelCode(), roomStayTobeCompared);
				duplicates = duplicates + 1;
				break;
			default:
				break;
			}
		}

		thotelsInfo.setDuplicates(duplicates);
		textensions.setHotelsInfo(thotelsInfo);
		apimap.setRoomStays(roomstaysFinal);
		apimap.setTpaExtensions(textensions);
		apimap.setRoomStays(roomstaysFinal);
		return apimap;
	}


	public static APIHotelMap addOrUpdate(APIHotelMap apimap, TreeMap<String, RoomStay> roomstays, RoomStay roomStayTobeAdded, String hotelKeyToBeAdded)
	{
		TPAExtensions textensions = apimap.getTpaExtensions();
		HotelsInfo thotelsInfo = textensions.getHotelsInfo();
		Integer duplicates = (thotelsInfo.getDuplicates() != null)?thotelsInfo.getDuplicates():new Integer(0) ;
		TreeMap<String, RoomStay> roomstaysFinal = roomstays;
		RoomStay roomStayTobeCompared = null;
		String hotelKeyExisting = null;
		int isAddable = HOTEL_NEW;
		for (Entry<String, RoomStay> entry : roomstaysFinal.entrySet()) {

			RoomStay rs = entry.getValue();

			// commented to allow duplication removal of same supplier
			//if(!HotelApiComparator.isSameApiProvider(rs, roomStayTobeAdded))
			if(HotelApiComparator.isSameHotel(rs, roomStayTobeAdded))
			{
				if(HotelApiComparator.comparebyLessHotelPrice(rs, roomStayTobeAdded) == -1)
				{
					isAddable = HOTEL_EXISTING_HIGHER_PRICE;
				}
				else
				{
					isAddable = HOTEL_EXISTING_LOWER_PRICE;
				}
				roomStayTobeCompared = rs;
				hotelKeyExisting = entry.getKey();
				break;
			}
		}
		roomstaysFinal.put(hotelKeyToBeAdded, roomStayTobeAdded);
		TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>  apiProviderMap = (apimap.getApiProviderMap() == null)? new TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>():apimap.getApiProviderMap();
		HashMap<Integer, HashMap<String, Boolean>> hotelApiRateMap = new HashMap<Integer, HashMap<String,Boolean>>();
		HashMap<String, Boolean> hotelRateMap = new HashMap<String, Boolean>();
		HashMap<Integer, HashMap<String, Boolean>> hotelApiRateMapTemp = new HashMap<Integer, HashMap<String,Boolean>>();
		switch (isAddable) {
		case HOTEL_NEW:
			hotelRateMap.put(hotelKeyToBeAdded, true);
			hotelApiRateMap.put(new Integer(roomStayTobeAdded.getBasicPropertyInfo().getApiProvider()), hotelRateMap);
			apiProviderMap.put(hotelKeyToBeAdded, hotelApiRateMap);
			apimap.setApiProviderMap(apiProviderMap);
			break;
		case HOTEL_EXISTING_LOWER_PRICE:
			//remroomstaysFinalove the existing one
			if(HotelApiComparator.isSameApiProvider(roomStayTobeCompared, roomStayTobeAdded))
				logger.info("#################################DUPLICATION FOUND IN SAME API");

			logger.info(roomStayTobeAdded.getBasicPropertyInfo().getHotelCode()+"#################################HOTEL_EXISTING_LOWER_PRICE to be replaced..-");
			hotelApiRateMap = (apiProviderMap.get(hotelKeyExisting) == null)? new HashMap<Integer, HashMap<String,Boolean>>():apiProviderMap.get(hotelKeyExisting);
			hotelRateMap = hotelApiRateMap.get(new Integer(roomStayTobeAdded.getBasicPropertyInfo().getApiProvider())) == null?new HashMap<String, Boolean>(): hotelApiRateMap.get(new Integer(roomStayTobeAdded.getBasicPropertyInfo().getApiProvider()));
			hotelRateMap.put(hotelKeyToBeAdded, true);

			for (Entry<Integer, HashMap<String, Boolean>> entry : hotelApiRateMap.entrySet()) {
				HashMap<String, Boolean> hotelRateMapOld = entry.getValue();
				HashMap<String, Boolean> hotelRateMapOldTemp = new HashMap<String, Boolean>();
				for (Entry<String, Boolean> entryRate : hotelRateMapOld.entrySet()) {
					hotelRateMapOldTemp.put(entryRate.getKey(), false);
				}
				hotelApiRateMapTemp.put(entry.getKey(), hotelRateMapOldTemp);
			}
			hotelApiRateMap = hotelApiRateMapTemp;
			hotelApiRateMap.put(new Integer(roomStayTobeAdded.getBasicPropertyInfo().getApiProvider()), hotelRateMap);
			apiProviderMap.remove(hotelKeyExisting);
			apiProviderMap.put(hotelKeyToBeAdded, hotelApiRateMap);
			apimap.setApiProviderMap(apiProviderMap);
			duplicates = duplicates + 1;
			break;
		case HOTEL_EXISTING_HIGHER_PRICE:
			//Leave ...
			if(HotelApiComparator.isSameApiProvider(roomStayTobeCompared, roomStayTobeAdded))
				logger.info("#################################DUPLICATION FOUND IN SAME API");

			logger.info(roomStayTobeAdded.getBasicPropertyInfo().getHotelCode()+"#################################HOTEL_EXISTING_HIGHER_PRICE to be left..-");
			hotelApiRateMap = (apiProviderMap.get(hotelKeyExisting) == null)? new HashMap<Integer, HashMap<String,Boolean>>():apiProviderMap.get(hotelKeyExisting);
			hotelRateMap = hotelApiRateMap.get(new Integer(roomStayTobeAdded.getBasicPropertyInfo().getApiProvider())) == null?new HashMap<String, Boolean>(): hotelApiRateMap.get(new Integer(roomStayTobeAdded.getBasicPropertyInfo().getApiProvider()));
			hotelRateMap.put(hotelKeyToBeAdded, false);
			hotelApiRateMap.put(new Integer(roomStayTobeAdded.getBasicPropertyInfo().getApiProvider()), hotelRateMap);
			apiProviderMap.put(hotelKeyExisting, hotelApiRateMap);
			apimap.setApiProviderMap(apiProviderMap);
			duplicates = duplicates + 1;
			break;
		default:
			break;
		}
		thotelsInfo.setDuplicates(duplicates);
		textensions.setHotelsInfo(thotelsInfo);
		apimap.setRoomStays(roomstaysFinal);
		apimap.setTpaExtensions(textensions);
		apimap.setRoomStays(roomstaysFinal);
		return apimap;
	}

	public static Map<String, RoomStay> sortBasicPriceold(Map<String, RoomStay> unsortMap) {
		List<Entry<String, RoomStay>> list = new LinkedList<Entry<String, RoomStay>>(unsortMap.entrySet());
		Collections.sort(list, new Comparator<Entry<String, RoomStay>>() {
			@Override
			public int compare(Entry<String, RoomStay> o1, Entry<String, RoomStay> o2) {
				BigDecimal o1amount = o1.getValue().getBasicPropertyInfo().getBasePriceWithoutMarkup();
				BigDecimal o2amount = o2.getValue().getBasicPropertyInfo().getBasePriceWithoutMarkup();
				return o1amount.compareTo(o2amount);
			}
		});

		Map<String, RoomStay> sortedMap = new LinkedHashMap<String, RoomStay>();
		for (Iterator<Entry<String, RoomStay>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, RoomStay> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}	

	private static HashMap<String, RoomStay> sortByPriceold(HashMap<String, RoomStay> roomstaysFinal, final boolean order)
	{
		List<Entry<String, RoomStay>> list = new LinkedList<Entry<String, RoomStay>>(roomstaysFinal.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, RoomStay>>()
		{
			@Override
			public int compare(Entry<String, RoomStay> o1, Entry<String, RoomStay> o2) {
				//logger.info("price sorting comparion.......call-hotel1..."+o1.getValue().getBasicPropertyInfo().getHotelName()+"---with hotel2.."+o2.getValue().getBasicPropertyInfo().getHotelName());
				BigDecimal o1amount = o1.getValue().getBasicPropertyInfo().getBasePriceWithoutMarkup();
				BigDecimal o2amount = o2.getValue().getBasicPropertyInfo().getBasePriceWithoutMarkup();

				if (order)
				{
					return (o1amount.compareTo(o2amount));
				}
				else
				{
					return (o2amount.compareTo(o1amount));

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		HashMap<String, RoomStay> sortedMap = new HashMap<String, RoomStay>();
		for (Entry<String, RoomStay> entry : list)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public static TreeMap<String, RoomStay> SortByValue(TreeMap<String, RoomStay> map) {
		HotelPriceComparator hpc =  new HotelPriceComparator(map);
		TreeMap<String, RoomStay> sortedMap = new TreeMap<String,RoomStay>(hpc);
		sortedMap.putAll(map);
		return sortedMap;
	}

	public OTAHotelAvailRS.RoomStays.RoomStay reDefineRoomDetailResponse(HotelSearchCommand hs, Map<String,List<HotelMarkup>> markupsmap, OTAHotelAvailRS.RoomStays.RoomStay rs)throws HibernateException, IOException, ParseException
	{
		String decryptrdAppKey = AppControllerUtil.getDecryptedAppKey(CDAO, hs.getApikey());
		Company company = null;
		String companyId = decryptrdAppKey.substring(decryptrdAppKey.indexOf("-") + 1);
		try{
			company = CDAO.getCompany(Integer.parseInt(companyId));
		}catch(Exception e){
			e.printStackTrace();
		}
		Company parentCompany = null;
		try{
			parentCompany = CDAO.getParentCompany(company);
		}catch(Exception e){
			e.printStackTrace();
		}

		rs = currencyManager.fillCurrencyDataOnRoomDetailWithMarkups(hs, markupsmap, rs,company,parentCompany);
		rs = currencyManager.fillCurrencyDataOnCancellationPloicyWithMarkups(hs, markupsmap, rs);
		return rs;
	}

}