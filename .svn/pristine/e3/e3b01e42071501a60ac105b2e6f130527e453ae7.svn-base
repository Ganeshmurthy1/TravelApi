package com.tayyarah.hotel.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.admin.analytics.lookbook.dao.LookBookDao;
import com.tayyarah.admin.analytics.lookbook.entity.FetchIpAddress;
import com.tayyarah.admin.analytics.lookbook.entity.HotelLook;
import com.tayyarah.admin.analytics.lookbook.entity.HotelLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPHistory;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPStatus;
import com.tayyarah.common.exception.CommonException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.hotel.dao.ApiHotelMapStoreDao;
import com.tayyarah.hotel.dao.HotelCityDao;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelMarkupDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelSearchRoomDetailDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.entity.TboCity;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.HotelMarkupCommissionDetails;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelsInfo;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.reposit.dao.HotelRepositDAOIMP;
import com.tayyarah.hotel.util.HotelAnalyzer;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelMarkUpUtil;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.DesiyaPullerTask;
import com.tayyarah.hotel.util.api.concurrency.LintasHotelRepositPullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezLivePullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TBOPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TayyarahPullerTask;
import com.tayyarah.services.HotelRepositService;

@RestController
@RequestMapping("/hotelnew/search/fast")
public class HotelSearchSimpleController {
	@Autowired
	HoteloverviewDao hotelDao;
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
	HotelCityDao hotelCityDao;
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	ApiHotelMapStoreDao apihotelstoredao;
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	HotelSearchRoomDetailDao hotelSearchRoomDetailDao;
	@Autowired
	HotelMarkupDao hotelMarkupDao;
	@Autowired
	CompanyDao companyDao;
	@Autowired
	AsyncSupport asyncSupport;
	public static final Logger logger = Logger.getLogger(HotelSearchSimpleController.class);
	@Autowired
	HotelAnalyzer hotelAnalyzer;
	@Autowired
	HotelRepositDAOIMP lintashoteldaoImp;
	@Autowired
	HotelRepositService hotelTayyarahRepositService;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@SuppressWarnings("rawtypes")
	@Autowired
	LookBookDao lookBookDao;

	@RequestMapping(value="/markups",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	HotelMarkupCommissionDetails getMarkUps(@RequestParam(value="appkey") String appkey,    HttpServletResponse response) throws HibernateException, IOException, Exception {
		HotelMarkupCommissionDetails hotelmarkupCommissionDetails = null;
		try {
			AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		} catch (Exception e1) {
			logger.error("Exception", e1);
		}
		return hotelmarkupCommissionDetails;
	}

	@RequestMapping(value="",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIHotelMap getRoomStaies2(@RequestParam(value="appkey") String appkey, @RequestParam(value="mode") int mode,@RequestParam(value="type") int type,@RequestParam(value="order") String order,@RequestParam(value="filter") int filter,@RequestParam(value="cachelevel") String cachelevel,@RequestParam(value="currency") String currency,@RequestParam(value="version") BigDecimal version,@RequestParam(value="lang") String lang,@RequestParam(value="citycode") Integer citycode,
			@RequestParam(value="datestart") String datestart,@RequestParam(value="dateend") String dateend,@RequestParam(value="noofrooms") int noofrooms, @RequestParam(value="rooms") String rooms, @RequestParam(value="istesting", defaultValue="false") boolean istesting, @RequestParam(value="apiids", defaultValue="1,2,4,5") String apiids, @RequestParam(value = "isDynamicMarkup", defaultValue = "false") boolean isDynamicMarkup,
			@RequestParam(value = "markupAmount", defaultValue = "0.0") String markupAmount, @RequestParam(value = "searchkey") String searchkey,   HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		HotelLook hotelLook=new HotelLook();
		APIHotelMap apimap = new APIHotelMap();
		long startTime = System.currentTimeMillis();
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
		HotelApiCredentials apirezt = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);
		HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apilintasReposit = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_LINTAS_REPOSITORY,appKeyVo);
		HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apiTbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apitayyarahReposit = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL,appKeyVo);

		LookBookCustomerIPStatus ipStatus=new LookBookCustomerIPStatus();
		LookBookCustomerIPHistory ipStatusHistory=new LookBookCustomerIPHistory();
		Timestamp currentDate=new Timestamp(new Date().getTime());
		String ip=null;
		try{
			ip=FetchIpAddress.getClientIpAddress(request);
			ipStatus=lookBookDao.CheckAndFetchIpStatus(ip);
			ipStatusHistory=lookBookDao.CheckAndfetchIpHistory(ip);
		}
		catch (Exception e) {
		}
		if(ipStatus!=null && ipStatus.getId()>0){
			if( ipStatus.isBlockStatus() ){
				throw new CommonException(ErrorCodeCustomerEnum.LimitExceedException,ErrorMessages.USEREXCEEDSSEARCHLIMIT); 
			} 
			else{
				ipStatus.setLastDate(currentDate);
				ipStatus.setTotalSearchCount(ipStatus.getTotalSearchCount()+1);
				if(ipStatus.getTotalSearchCount()>=100 && ipStatus.isB2cFlag())
					ipStatus.setBlockStatus(true);
				try{
					lookBookDao.updateIpStatus(ipStatus);
				}
				catch (Exception e) {
				}

			}
		}
		else{
			ipStatus=new LookBookCustomerIPStatus();
			ipStatus.setStartDate(currentDate);
			ipStatus.setLastDate(currentDate);
			if(appKeyVo.getCompanyConfig()!=null && appKeyVo.getCompanyConfig().getCompanyConfigType()!=null ){
				if(appKeyVo.getCompanyConfig().getCompanyConfigType().isB2C() || appKeyVo.getCompanyConfig().getCompanyConfigType().isWhitelable()){
					ipStatus.setB2cFlag(true);
					ipStatus.setConfigType("B2C");
				}
				else if(appKeyVo.getCompanyConfig().getCompanyConfigType().isB2B()){
					ipStatus.setConfigType("B2B");
				}
				else if(appKeyVo.getCompanyConfig().getCompanyConfigType().isB2E()){
					ipStatus.setConfigType("B2E");
				}

				ipStatus.setCompanyName(appKeyVo.getCompanyConfig().getCompanyName());
				ipStatus.setConfigName(appKeyVo.getCompanyConfig().getConfigname());
			} 
			ipStatus.setBlockStatus(false);
			ipStatus.setIp(ip);
			ipStatus.setTotalBookedCount(0);
			ipStatus.setTotalSearchCount(1);
			ipStatus.setCompanyId(appKeyVo.getCompanyId());
			ipStatus.setConfigId(appKeyVo.getConfigId());
			try{
				lookBookDao.insertIntoTable(ipStatus);
			}
			catch (Exception e) {
			}
		}
		if(ipStatusHistory!=null && ipStatusHistory.getId()>0){
			ipStatusHistory.setLastDate(currentDate);
			ipStatusHistory.setTotalSearchCount(ipStatusHistory.getTotalSearchCount()+1);
			try{
				lookBookDao.updateIpHistory(ipStatusHistory);
			}
			catch (Exception e) {
			}
		}
		else{
			ipStatusHistory=new LookBookCustomerIPHistory();

			ipStatusHistory.setStartDate(currentDate);
			ipStatusHistory.setLastDate(currentDate);
			if(appKeyVo.getCompanyConfig()!=null && appKeyVo.getCompanyConfig().getCompanyConfigType()!=null ){
				if(appKeyVo.getCompanyConfig().getCompanyConfigType().isB2C() || appKeyVo.getCompanyConfig().getCompanyConfigType().isWhitelable()){
					ipStatusHistory.setB2cFlag(true);
					ipStatusHistory.setConfigType("B2C");
				}
				else if(appKeyVo.getCompanyConfig().getCompanyConfigType().isB2B()){
					ipStatusHistory.setConfigType("B2B");
				}
				else if(appKeyVo.getCompanyConfig().getCompanyConfigType().isB2E()){
					ipStatusHistory.setConfigType("B2E");
				}

				ipStatusHistory.setCompanyName(appKeyVo.getCompanyConfig().getCompanyName());
				ipStatusHistory.setConfigName(appKeyVo.getCompanyConfig().getConfigname());
			} 
			ipStatusHistory.setIp(ip);
			ipStatusHistory.setTotalBookedCount(0);
			ipStatusHistory.setTotalSearchCount(1);
			ipStatusHistory.setCompanyId(appKeyVo.getCompanyId());
			ipStatusHistory.setConfigId(appKeyVo.getConfigId());
			try{
				lookBookDao.insertIntoTable(ipStatusHistory);
			}
			catch (Exception e) {
			}
		}

		if(appkey!=null && appkey.equalsIgnoreCase(""))
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + APIStatus.STATUS_MESSAGE_APPKEY_NOTFOUND));
			return apimap ;
		}
		List<Integer> apiIdList = CommonUtil.getSelectedApiIdList(istesting, apiids);

		String  baseCurrency = "INR";
		try {
			baseCurrency = companyDao.getCompanyCurrencyCode(appKeyVo.getCompanyId());
		} catch (Exception e1) {
			logger.error("Exception", e1);
		}


		if(!searchkey.equalsIgnoreCase("") && isDynamicMarkup ){
			HotelSearchTemp hs = hotelSearchDao.getHotelSearch(Long.parseLong(searchkey));
			HotelSearchCommand hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());
			apimap = (APIHotelMap) SerializationUtils.deserialize(hs.getHotelres_map());
			HotelMarkupCommissionDetails hotelmarkupCommissionDetails = hsc.getHotelMarkupCommissionDetails();
			Map<String,List<HotelMarkup>> markupMap = new HashMap<String,List<HotelMarkup>>();
			Map<String,List<HotelMarkup>> markups = hotelMarkupDao.getHotelMarkUpConfigMapByCompanyId(appKeyVo,  markupMap);
			hotelmarkupCommissionDetails.setMarkups(markups);
			if(isDynamicMarkup){
				List<HotelMarkup> markupList=new ArrayList<HotelMarkup>();
				HotelMarkUpUtil.addDynamicMarkup(appKeyVo, markupList, markupAmount);
				markups.put(String.valueOf(appKeyVo.getCompanyId()), markupList);
			}
			hsc.getHotelMarkupCommissionDetails().setMarkups(markups);

			apimap = hotelAnalyzer.reDefineHotelResponse(apimap, hsc.getCurrency(), baseCurrency, hsc,companyConfigDAO,companyDao);

			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... ############  Hotel search -------total hotels count-redefined---:"+ apimap.getRoomStays().size());
			HotelTransactionTemp ht = new HotelTransactionTemp();
			byte[] roomStaysMapdata = SerializationUtils.serialize(apimap);
			hs.setHotelres_map(roomStaysMapdata);
			byte[] hscbytes = SerializationUtils.serialize(hsc);
			hs.setHotelsearch_cmd(hscbytes);
			hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);
			ht = hotelTransactionDao.getHotelTransaction(Long.parseLong(searchkey));
			ht.setApi_key(ht.getApi_key());
			ht.setSearch_key(ht.getSearch_key());
			apimap.setTransactionKey(BigInteger.valueOf(ht.getId()));
			apimap.setSearchKey(BigInteger.valueOf(ht.getSearch_key()));
			apimap.setHotelSearchCommand(hsc);
			apimap.clearUnDeliverables();
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS));
			apimap = hotelAnalyzer.removeDuplicates(apimap);

			return apimap ;
		}

		HotelSearchCity hotelSearchCity = null;
		List<HotelSearchCity> hotelSearchCityDuplicates = new ArrayList<HotelSearchCity>();
		try{

			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... appKeyVo-----------:"+ appKeyVo);
			if(apidesiya.isEnabled())
			{
				logger.info("apidesiya enabled");
				hotelSearchCity = hotelCityDao.getHotelSearchCity(citycode);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... appKeyVo-----------hotelSearchCity:"+ hotelSearchCity);
				hotelSearchCityDuplicates = hotelCityDao.getHotelSearchCityDuplicates(citycode);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... appKeyVo-----------hotelSearchCityDuplicates:"+ hotelSearchCityDuplicates);
			}
			if(apidesiya.isEnabled() && apiTbo.isEnabled())
			{
				hotelSearchCity = hotelCityDao.getHotelSearchCity(citycode);
				TboCity tboCity = hotelCityDao.getTboCity(citycode);
				if(tboCity != null)
				{
					hotelSearchCity.setCity((tboCity.getDestination()==null)?"":tboCity.getDestination());
					hotelSearchCity.setCountryCode((tboCity.getCountrycode()==null)?"":tboCity.getCountrycode());
					hotelSearchCity.setTboCity(tboCity);

				}
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... appKeyVo-----------hotelSearchCity:"+ hotelSearchCity);
				hotelSearchCityDuplicates = hotelCityDao.getHotelSearchCityDuplicates(citycode);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... appKeyVo-----------hotelSearchCityDuplicates:"+ hotelSearchCityDuplicates);
			}
			else if(apiTbo.isEnabled())
			{
				hotelSearchCity = new HotelSearchCity();
				TboCity tboCity = hotelCityDao.getTboCity(citycode);

				if(tboCity != null)
				{
					hotelSearchCity.setCity((tboCity.getDestination()==null)?"":tboCity.getDestination());
					hotelSearchCity.setCountryCode((tboCity.getCountrycode()==null)?"":tboCity.getCountrycode());
					hotelSearchCity.setTboCity(tboCity);
					//hotelSearchCityList.add(hotelSearchCity);
				}
				else if(tboCity == null){
					hotelSearchCity = hotelCityDao.getHotelSearchCity(citycode);						
				}
				else
				{
					apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Select some other city to search...City ref is null"));
					return apimap ;
				}
			}
		}
		catch(FlightException e)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getDebugMessage() ));
			return apimap ;
		}
		catch(Exception e)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " City and Country code has issues..."+e.getMessage() ));
			return apimap ;
		}
		if(hotelSearchCity == null)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Select some other city to search...City ref is null"));
			return apimap ;
		}
		logger.info(CommonUtil.getElapsedTime(startTime)+" #############  hotelSearchCity-:"+ hotelSearchCity);
		logger.info(CommonUtil.getElapsedTime(startTime)+" #############  hotelSearchCity tg city-:"+ hotelSearchCity.getTgCity());
		logger.info(CommonUtil.getElapsedTime(startTime)+" #############  hotelSearchCity tbo city-:"+ hotelSearchCity.getTboCity());


		String country = (hotelSearchCity.getTboCity() != null && hotelSearchCity.getTboCity().getCountry() != null && hotelSearchCity.getTboCity().getCountry().trim().length()>1)?hotelSearchCity.getTboCity().getCountry():"India";
		HotelSearchCommand hsc = new HotelSearchCommand(appkey, mode, type, order, filter, cachelevel, currency, new BigDecimal("1.0"),lang, hotelSearchCity.getCity(), hotelSearchCity.getCountryCode(), country, datestart , dateend, noofrooms, rooms );
		hsc.setSearchCity(hotelSearchCity);
		//hsc.setSearchCityDuplicates(hotelSearchCityDuplicates);
		hsc.setIndex(1);
		int maxCount = (hotelSearchCityDuplicates != null)?hotelSearchCityDuplicates.size():0;
		hsc.setCallableAgain((hotelSearchCityDuplicates != null && hotelSearchCityDuplicates.size()>0)?true:false);

		hsc.setMaxIndex(maxCount);

		logger.info(CommonUtil.getElapsedTime(startTime)+" #############  maxCount-:"+ maxCount);

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		logger.info(CommonUtil.getElapsedTime(startTime)+" #############  ip address---X-FORWARDED-FOR--------:"+ ipAddress);
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
			logger.info(CommonUtil.getElapsedTime(startTime)+" #############  ip address---getRemoteAddr--------:"+ ipAddress);
		}
		if(ipAddress.contains("0:0:0:0:0"))
		{
			/*URL url = new URL("http://checkip.amazonaws.com/");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			ipAddress = br.readLine();	*/
			//System.out.println();
			ipAddress = CommonConfig.GetCommonConfig().getServer_ip_address();
			//ipAddress = "122.166.233.133";
		}
		logger.info(CommonUtil.getElapsedTime(startTime)+"hotel search server ip address-----------:"+ ipAddress);
		hsc.setEndUserIp(ipAddress);
		hsc = CommonUtil.initSearchDestinationType(hsc, companyDao);
		HotelTransactionTemp ht = new HotelTransactionTemp();
		HotelSearchTemp hs = new HotelSearchTemp();
		hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);
		logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelsearch-----------:"+ hs.toString());
		logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand-----------:"+ hsc.toString());



		ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();
		TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(BigInteger.valueOf(hs.getSearch_key()), hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");
		DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(hs, hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apidesiya, hsc, "Desiya Api", hotelSearchCity);
		logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand-------desiya----:"+ desiyaPullerTask.getApi().getApiProviderName());
		Future<APIHotelMap> apistacktayyarah = null;
		Future<APIHotelMap> apistackdesiya = null;
		Future<APIHotelMap> apistackreznext = null;
		Future<APIHotelMap> apistackrezlive = null;
		Future<APIHotelMap> apistacklintasreposit = null;
		Future<APIHotelMap> apistacktbo = null;
		RezNextPullerTask reznextPullerTask = new RezNextPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apirezt, hsc, "RazNext Api");
		logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand-------reznext----:"+ reznextPullerTask.getApi().getApiProviderName());
		RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");
		LintasHotelRepositPullerTask lintasHotelRepositPullerTask = new LintasHotelRepositPullerTask(hotelObjectTransformer, lintashoteldaoImp, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apilintasReposit, hsc, "Lintas Reposit Api");
		TBOPullerTask tboPullerTask = new TBOPullerTask(hs, hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apiTbo, hsc, "TBO Api", hotelSearchCity);
		/*//shut down the executor service now
           executor.shutdown();*/
		APIHotelMap apimaptayyarah = new APIHotelMap();
		APIHotelMap apimapdesiya = new APIHotelMap();
		APIHotelMap apimapreznext = new APIHotelMap();
		APIHotelMap apimaprezlive = new APIHotelMap();
		APIHotelMap apimaplintasreposit = new APIHotelMap();
		APIHotelMap apimaptbo = new APIHotelMap();
		TPAExtensions textensions = new TPAExtensions();
		HotelsInfo thotelsInfo = new HotelsInfo();
		HashMap<Integer, HashMap<Integer, Integer>> apiProviderMap = new HashMap<Integer, HashMap<Integer, Integer>>();
		try {
			//logger.info("HotelSearchCommand- getRoomGuests----------:"+ hsc.getRoomGuests().size());
			TreeMap<String, RoomStay> roomStaysMapTayyarah = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapDesiya = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapReznext = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapRezlive = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapLintasReposit = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapTbo = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapTayyarahReposit = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();

			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------live search----:");
			if( apitayyarah.isEnabled() && CommonUtil.isAPIValid(hsc, apitayyarah ))
			{
				apistacktayyarah = apiPullExecutor.submit(tayyarahPullerTask);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah started----:");
			}
			if( apidesiya.isEnabled() && CommonUtil.isAPIValid(hsc, apidesiya))
			{
				apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya started----:");
			}
			if( apirezt.isEnabled() && CommonUtil.isAPIValid(hsc, apirezt))
			{
				apistackreznext = apiPullExecutor.submit(reznextPullerTask);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... seconds ...Hotel search -------reznext started----:");
			}
			if( apireztlive.isEnabled() && CommonUtil.isAPIValid(hsc, apireztlive))
			{
				apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive started----:");
			}
			if( apilintasReposit.isEnabled() && CommonUtil.isAPIValid(hsc, apilintasReposit))
			{
				apistacklintasreposit = apiPullExecutor.submit(lintasHotelRepositPullerTask);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit started----:");
			}
			logger.info(CommonUtil.getElapsedTime(startTime)+" TBO apiTbo.isEnabled()----:"+ apiTbo.isEnabled());
			logger.info(CommonUtil.getElapsedTime(startTime)+" TBO CommonUtil.isAPIValid(hsc, apiTbo)----:"+ CommonUtil.isAPIValid(hsc, apiTbo));
			if( apiTbo.isEnabled() && CommonUtil.isAPIValid(hsc, apiTbo))
			{
				apistacktbo = apiPullExecutor.submit(tboPullerTask);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------TBO started----:");
			}
			if( apitayyarah.isEnabled() && CommonUtil.isAPIValid(hsc, apitayyarah ))
			{
				//apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
				apimaptayyarah = apistacktayyarah.get();
				roomStaysMapTayyarah = apimaptayyarah.getRoomStays();

				HashMap<Integer, Integer> apiCityMap = (apiProviderMap.get(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL)==null)?
						new HashMap<Integer, Integer>():apiProviderMap.get(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL);
						apiCityMap.put(hsc.getSearchCity().getId(), roomStaysMapTayyarah.size());
						apiProviderMap.put(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL, apiCityMap);


						roomStaysMap.putAll(roomStaysMapTayyarah);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah hotels count----:"+ roomStaysMap.size());
			}
			if( apidesiya.isEnabled() && CommonUtil.isAPIValid(hsc, apidesiya))
			{
				//apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
				apimapdesiya = apistackdesiya.get();
				roomStaysMapDesiya = apimapdesiya.getRoomStays();

				HashMap<Integer, Integer> apiCityMap = (apiProviderMap.get(HotelApiCredentials.API_DESIA_IND)==null)?
						new HashMap<Integer, Integer>():apiProviderMap.get(HotelApiCredentials.API_DESIA_IND);
						apiCityMap.put(hsc.getSearchCity().getId(), roomStaysMapDesiya.size());
						apiProviderMap.put(HotelApiCredentials.API_DESIA_IND, apiCityMap);

						roomStaysMap.putAll(roomStaysMapDesiya);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya hotels count----:"+ roomStaysMap.size());
			}
			if( apirezt.isEnabled() && CommonUtil.isAPIValid(hsc, apirezt))
			{
				//apistackreznext = apiPullExecutor.submit(reznextPullerTask);
				apimapreznext = apistackreznext.get();
				roomStaysMapReznext = apimapreznext.getRoomStays();


				HashMap<Integer, Integer> apiCityMap = (apiProviderMap.get(HotelApiCredentials.API_REZNEXT_IND)==null)?
						new HashMap<Integer, Integer>():apiProviderMap.get(HotelApiCredentials.API_REZNEXT_IND);
						apiCityMap.put(hsc.getSearchCity().getId(), roomStaysMapReznext.size());
						apiProviderMap.put(HotelApiCredentials.API_REZNEXT_IND, apiCityMap);


						roomStaysMap.putAll(roomStaysMapReznext);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------reznext hotels count----:"+ roomStaysMapReznext.size());
			}
			if( apireztlive.isEnabled() && CommonUtil.isAPIValid(hsc, apireztlive))
			{
				//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
				apimaprezlive = apistackrezlive.get();
				roomStaysMapRezlive = apimaprezlive.getRoomStays();

				HashMap<Integer, Integer> apiCityMap = (apiProviderMap.get(HotelApiCredentials.API_REZLIVE_INTERNATIONAL)==null)?
						new HashMap<Integer, Integer>():apiProviderMap.get(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
						apiCityMap.put(hsc.getSearchCity().getId(), roomStaysMapRezlive.size());
						apiProviderMap.put(HotelApiCredentials.API_REZLIVE_INTERNATIONAL, apiCityMap);


						roomStaysMap.putAll(roomStaysMapRezlive);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive hotels count----:"+ roomStaysMapRezlive.size());
			}
			if( apilintasReposit.isEnabled() && CommonUtil.isAPIValid(hsc, apilintasReposit))
			{
				//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
				apimaplintasreposit = apistacklintasreposit.get();
				roomStaysMapLintasReposit = apimaplintasreposit.getRoomStays();

				HashMap<Integer, Integer> apiCityMap = (apiProviderMap.get(HotelApiCredentials.API_LINTAS_REPOSITORY)==null)?
						new HashMap<Integer, Integer>():apiProviderMap.get(HotelApiCredentials.API_LINTAS_REPOSITORY);
						apiCityMap.put(hsc.getSearchCity().getId(), roomStaysMapLintasReposit.size());
						apiProviderMap.put(HotelApiCredentials.API_LINTAS_REPOSITORY, apiCityMap);


						roomStaysMap.putAll(roomStaysMapLintasReposit);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());
			}

			if( apiTbo.isEnabled() && CommonUtil.isAPIValid(hsc, apiTbo))
			{
				apimaptbo = apistacktbo.get();
				roomStaysMapTbo = apimaptbo.getRoomStays();

				HashMap<Integer, Integer> apiCityMap = (apiProviderMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL)==null)?
						new HashMap<Integer, Integer>():apiProviderMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL);
						apiCityMap.put(hsc.getSearchCity().getTboCity().getCityid(), roomStaysMapTbo.size());
						apiProviderMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, apiCityMap);
						roomStaysMap.putAll(roomStaysMapTbo);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());

			}

			thotelsInfo.setApiProviderCityMap(apiProviderMap);
			textensions.setHotelsInfo(thotelsInfo);
			apimap.setTpaExtensions(textensions);
			/////////added by ilyas///////////

			hsc.setBaseCurrency(baseCurrency);


			Map<String,List<HotelMarkup>> markupMap = new HashMap<String,List<HotelMarkup>>();
			HotelMarkupCommissionDetails hotelmarkupCommissionDetails = null;
			try {
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah reposit pull starts----:");
				APIHotelMap apiHotelMapTayyarahReposit = hotelTayyarahRepositService.searchHotels(String.valueOf(citycode), appKeyVo.getCompany().getCompany_userid(), hsc, apitayyarahReposit);
				roomStaysMapTayyarahReposit = apiHotelMapTayyarahReposit.getRoomStays();
				roomStaysMap.putAll(roomStaysMapTayyarahReposit);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah reposit pull ends----:hotels count --"+roomStaysMapTayyarahReposit.size());
				hotelmarkupCommissionDetails = companyDao.getHotelMarkupCommissionDetails(appKeyVo);
				Map<String,List<HotelMarkup>> markups = hotelMarkupDao.getHotelMarkUpConfigMapByCompanyId(appKeyVo,  markupMap);
				//List<HotelMarkup> markups = new ArrayList<HotelMarkup>();
				hotelmarkupCommissionDetails.setMarkups(markups);
				//**** for dyanmic markup by ilyas
				/*if(isDynamicMarkup){
					//String companyId = appKeyVo.substring(appKeyVo.indexOf("-") + 1);
					List<HotelMarkup> markupList=new ArrayList<HotelMarkup>();
					HotelMarkUpUtil.addDynamicMarkup(appKeyVo, markupList, markupAmount);
					markups.put(companyId, markupList);
				}*/
				/////////////////*************
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------total hotels count----:"+ roomStaysMap.size());
				apimap.setRoomStays(roomStaysMap);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand- no of markups----------:"+ markups.size());
				hsc.setHotelMarkupCommissionDetails(hotelmarkupCommissionDetails);
				//if(hotelmarkupCommissionDetails!=null && hotelmarkupCommissionDetails.getMarkups().size() > 0)
				//	apimap.applyAllLevelMarkUpInitHotelSearch(hsc, hotelmarkupCommissionDetails);

				apimap = hotelAnalyzer.reDefineHotelResponse(apimap, hsc.getCurrency(), baseCurrency, hsc,companyConfigDAO,companyDao);


				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... ############  Hotel search -------total hotels count-redefined---:"+ apimap.getRoomStays().size());
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel serach response.. markup applied---:");
			} catch (Exception e1) {
				apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e1.getMessage() ));
			}
			/*
			HotelMarkupCommissionDetails hotelmarkupCommissionDetails = null;
			try {
				hotelmarkupCommissionDetails = CDAO
						.getHotelMarkupCommissionDetails(appKeyVo);
			} catch (Exception e1) {
				logger.error("Exception", e1);
			}
			 */		


			byte[] roomStaysMapdata = SerializationUtils.serialize(apimap);
			hs.setHotelres_map(roomStaysMapdata);
			byte[] hscbytes = SerializationUtils.serialize(hsc);
			hs.setHotelsearch_cmd(hscbytes);
			hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);
			//change the below for api key to string format..
			ht.setApi_key(Long.valueOf(1));
			ht.setSearch_key(hs.getSearch_key());
			ht = hotelTransactionDao.insertApiMapBySearchKey(ht);
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel serach response..stored into DB for further reference---:");
			//apimap.setTransactionKey(ht.getId());
			apimap.setTransactionKey(BigInteger.valueOf(ht.getId()));
			apimap.setSearchKey(BigInteger.valueOf(ht.getSearch_key()));
			apimap.setHotelSearchCommand(hsc);
			apimap.clearUnDeliverables();
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS));
			apimap = hotelAnalyzer.removeDuplicates(apimap);		


			try {
				hotelLook.setAppkey(appkey);
				hotelLook.setIP(ip);
				hotelLook.setTransactionId(apimap.getTransactionKey()!=null?String.valueOf(apimap.getTransactionKey()):"not avail");
				hotelLook.setSearchKey(apimap.getSearchKey()!=null?String.valueOf(apimap.getSearchKey()):"search failed");
				hotelLook.setSearchOnDateTime(new Timestamp(new Date().getTime()));
				hotelLook.setSearchQueryString(request.getQueryString());
				hotelLook.setCompanyId(appKeyVo.getCompanyId());
				hotelLook.setConfigId(appKeyVo.getConfigId());
				hotelLook.setCompanyName(appKeyVo.getCompany().getCompanyname());
				lookBookDao.insertIntoTable(hotelLook);

				HotelLookBook lookBook=new HotelLookBook(); 
				lookBook.setAppkey(appkey);
				lookBook=lookBookDao.CheckAndFetchHotelLookBookByAppKey(lookBook);
				if(lookBook!=null && lookBook.getId()>0){
					lookBookDao.updateIntoHotelTable(lookBook, "search");
				}
				else{
					lookBook=new HotelLookBook(); 
					lookBook.setAppkey(appkey);
					lookBook.setCompanyId(appKeyVo.getCompanyId());
					lookBook.setConfigId(appKeyVo.getConfigId());
					lookBook.setCompanyName(appKeyVo.getCompany().getCompanyname());
					lookBook.setTotalBookedCount(0);
					lookBook.setTotalSearchCount(1);
					lookBookDao.insertIntoTable(lookBook);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}


			return apimap;

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
		}
		catch (ExecutionException  e)
		{
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
		}
		catch (Exception  e)
		{
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
		}
	}


	@RequestMapping(value="/multicall",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIHotelMap getRoomStaiesLoop(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,   HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		APIHotelMap apimap = new APIHotelMap();
		long startTime = System.currentTimeMillis();
		HotelSearchTemp hs = new HotelSearchTemp();
		HotelTransactionTemp ht = new HotelTransactionTemp();

		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
		HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apiTbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
		if(appkey!=null && appkey.equalsIgnoreCase(""))
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + APIStatus.STATUS_MESSAGE_APPKEY_NOTFOUND));
			return apimap ;
		}

		logger.info("Searhing roomdetails searchkey : "+searchkey);
		hs = hotelSearchDao.getHotelSearch(searchkey);
		ht = hotelTransactionDao.getHotelTransaction(searchkey);
		HotelSearchCommand hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());
		APIHotelMap apiHotelMapStore = (APIHotelMap) SerializationUtils.deserialize(hs.getHotelres_map());
		if(apiHotelMapStore == null)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Search again to access loop" ));
			return apimap ;
		}
		if(hsc != null && hsc.getSearchCity() != null && hsc.getSearchCity().getDuplicateIdData() != null && hsc.getSearchCity()!=null)
		{
			logger.info("Searhing roomdetails hotels history temp map : "+apiHotelMapStore);

			if(hsc.getIndex() == hsc.getMaxIndex())
			{
				apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, "All hotels have been loaded.. No change" ));
				return apimap;
			}

			TPAExtensions textensionsDB = (apiHotelMapStore.getTpaExtensions() != null)?apiHotelMapStore.getTpaExtensions():new TPAExtensions();
			HotelsInfo thotelsInfoDB = (textensionsDB.getHotelsInfo()!=null)? textensionsDB.getHotelsInfo():new HotelsInfo();
			HashMap<Integer, HashMap<Integer, Integer>> apiProviderCityMapDB = (thotelsInfoDB.getApiProviderCityMap() != null)?thotelsInfoDB.getApiProviderCityMap() : new HashMap<Integer, HashMap<Integer, Integer>>();

			TreeMap<String, RoomStay> roomStaysMapDB = (apiHotelMapStore.getRoomStays() != null)?apiHotelMapStore.getRoomStays() : new TreeMap<String, RoomStay>();
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... ############ roomstay size before looping---:"+ roomStaysMapDB.size());

			String[] duplicates = hsc.getSearchCity().getDuplicateIdData().split(",");
			List<HotelSearchCity> hotelSearchCityDuplicates = new ArrayList<HotelSearchCity>();
			hotelSearchCityDuplicates = hotelCityDao.getHotelSearchCityDuplicates(hsc.getSearchCity().getId());
			ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();
			//TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();
			boolean isLoopFound = false;

			for (HotelSearchCity hotelSearchCity : hotelSearchCityDuplicates) {
				if(hotelSearchCity != null)
				{
					logger.info(CommonUtil.getElapsedTime(startTime)+" duplicat search for :--"+hotelSearchCity.toString());
					TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(BigInteger.valueOf(hs.getSearch_key()), hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");
					DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(hs, hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apidesiya, hsc, "Desiya Api", hotelSearchCity);
					TBOPullerTask tboPullerTask = new TBOPullerTask(hs, hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apiTbo, hsc, "TBO Api", hotelSearchCity);

					List<Future<APIHotelMap>> futureMapList = new ArrayList<Future<APIHotelMap>>();
					if( CommonUtil.isValid(hotelSearchCity, apidesiya))
					{
						futureMapList.add(apiPullExecutor.submit(desiyaPullerTask));
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya started----:");
					}
					if( CommonUtil.isValid(hotelSearchCity, apiTbo))
					{
						futureMapList.add(apiPullExecutor.submit(tboPullerTask));;
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tbo started----:");
					}
					for (Future<APIHotelMap> future : futureMapList) {
						APIHotelMap apiHotelMap = future.get();
						if(apiHotelMap != null && apiHotelMap.getRoomStays() != null && !apiHotelMap.getRoomStays().isEmpty())
						{
							isLoopFound = true;
							roomStaysMapDB.putAll(apiHotelMap.getRoomStays());

							HashMap<Integer, Integer> apiCityMap = (apiProviderCityMapDB.get(apiHotelMap.getApiId())==null)?
									new HashMap<Integer, Integer>():apiProviderCityMapDB.get(apiHotelMap.getApiId());
									apiCityMap.put(hotelSearchCity.getId(), apiHotelMap.getRoomStays().size());
									apiProviderCityMapDB.put(apiHotelMap.getApiId(), apiCityMap);
						}
					}
				}
			}
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... ############ roomstay size after looping---:"+ roomStaysMapDB.size());

			thotelsInfoDB.setApiProviderCityMap(apiProviderCityMapDB);
			textensionsDB.setHotelsInfo(thotelsInfoDB);
			apimap.setTpaExtensions(textensionsDB);


			hsc.setIndex((hotelSearchCityDuplicates != null)?hotelSearchCityDuplicates.size():0);
			hsc.setMaxIndex((hotelSearchCityDuplicates != null)?hotelSearchCityDuplicates.size():0);
			hsc.setCallableAgain(false);
			apimap.setRoomStays(roomStaysMapDB);
			apimap = hotelAnalyzer.reDefineHotelResponse(apimap, hsc.getCurrency(), hsc.getBaseCurrency(), hsc,companyConfigDAO,companyDao);
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... ############  Hotel search -------total hotels count-redefined---:"+ apimap.getRoomStays().size());
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel serach response.. markup applied---:");

		}




		byte[] roomStaysMapdata = SerializationUtils.serialize(apimap);
		hs.setHotelres_map(roomStaysMapdata);
		byte[] hscbytes = SerializationUtils.serialize(hsc);
		hs.setHotelsearch_cmd(hscbytes);
		hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);
		//change the below for api key to string format..
		ht.setApi_key(Long.valueOf(1));
		ht.setSearch_key(hs.getSearch_key());
		ht = hotelTransactionDao.insertApiMapBySearchKey(ht);
		logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel serach response..stored into DB for further reference---:");
		//apimap.setTransactionKey(ht.getId());
		apimap.setTransactionKey(BigInteger.valueOf(ht.getId()));
		apimap.setSearchKey(BigInteger.valueOf(ht.getSearch_key()));
		apimap.setHotelSearchCommand(hsc);
		apimap.clearUnDeliverables();
		apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS));
		apimap = hotelAnalyzer.removeDuplicates(apimap);

		return apimap;

	}

}
