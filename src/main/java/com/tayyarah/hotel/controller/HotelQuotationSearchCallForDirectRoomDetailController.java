package com.tayyarah.hotel.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.common.util.ResponseHeader;
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
import com.tayyarah.hotel.entity.HotelSearchRoomDetailTemp;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.entity.Islhotelmapping;
import com.tayyarah.hotel.entity.TboCity;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIRoomDetail;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.HotelMarkupCommissionDetails;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.HotelsInfo;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.reposit.dao.HotelRepositDAOIMP;
import com.tayyarah.hotel.util.HotelAnalyzer;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelMarkUpUtil;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.RoomAnalyzer;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.DesiyaPullerTask;
import com.tayyarah.hotel.util.api.concurrency.LintasHotelRepositPullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezLivePullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TBOPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TayyarahPullerTask;
import com.tayyarah.services.HotelRepositService;


@RestController
@RequestMapping("/hotelquote/search/fast")
public class HotelQuotationSearchCallForDirectRoomDetailController {

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

	private long startTime;

	@RequestMapping(value="",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIRoomDetail getHotels(@RequestParam(value="appkey") String appkey, @RequestParam(value="mode") int mode,@RequestParam(value="type") int type,@RequestParam(value="order") String order,@RequestParam(value="filter") int filter,@RequestParam(value="cachelevel") String cachelevel,@RequestParam(value="currency") String currency,@RequestParam(value="version") BigDecimal version,@RequestParam(value="lang") String lang,@RequestParam(value="citycode") Integer citycode,
			@RequestParam(value="datestart") String datestart,@RequestParam(value="dateend") String dateend,@RequestParam(value="noofrooms") int noofrooms, @RequestParam(value="rooms") String rooms, @RequestParam(value="istesting", defaultValue="false") boolean istesting, @RequestParam(value="apiids", defaultValue="1,2,4,5") String apiids, @RequestParam(value = "isDynamicMarkup", defaultValue = "false") boolean isDynamicMarkup,
			@RequestParam(value = "markupAmount", defaultValue = "0.0") String markupAmount, @RequestParam(value = "searchkey") String searchkey, @RequestParam(value = "hotelcode") String hotelcode, @RequestParam(value = "hotelname") String hotelname, @RequestParam(value = "address") String address, @RequestParam(value = "location") String location,  HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		APIHotelMap apimap = new APIHotelMap();
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		startTime = System.currentTimeMillis();
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
		HotelApiCredentials apirezt = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);
		HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apilintasReposit = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_LINTAS_REPOSITORY,appKeyVo);
		HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apiTbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apitayyarahReposit = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL,appKeyVo);
		if(appkey!=null && appkey.equalsIgnoreCase(""))
		{
			APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_APPKEY_NOTFOUND));
			return apiRoomDetail ;
		}
		List<Integer> apiIdList = CommonUtil.getSelectedApiIdList(istesting, apiids);
		String  baseCurrency = "INR";
		try {
			baseCurrency = companyDao.getCompanyCurrencyCode(appKeyVo.getCompanyId());
		} catch (Exception e1) {
			logger.error("Exception", e1);
		}

		System.out.println("searchkey" +searchkey);
		System.out.println("isDynamicMarkup" +isDynamicMarkup);
		if(!searchkey.equalsIgnoreCase("") && isDynamicMarkup ){
			HotelSearchTemp hs = hotelSearchDao.getHotelSearch(Long.parseLong(searchkey));
			//System.out.println("hs" +hs);
			HotelSearchCommand hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());
			apimap = (APIHotelMap) SerializationUtils.deserialize(hs.getHotelres_map());
			///	Map<String,List<HotelMarkup>> markups = new HashMap<String,List<HotelMarkup>>();

			HotelMarkupCommissionDetails hotelmarkupCommissionDetails = hsc.getHotelMarkupCommissionDetails();
			//hotelmarkupCommissionDetails = CDAO.getHotelMarkupCommissionDetails(appKeyVo);
			Map<String,List<HotelMarkup>> markupMap = new HashMap<String,List<HotelMarkup>>();
			Map<String,List<HotelMarkup>> markups = hotelMarkupDao.getHotelMarkUpConfigMapByCompanyId(appKeyVo,  markupMap);
			//List<HotelMarkup> markups = new ArrayList<HotelMarkup>();
			hotelmarkupCommissionDetails.setMarkups(markups);
			if(isDynamicMarkup){
				//String companyId = appKeyVo.substring(appKeyVo.indexOf("-") + 1);
				List<HotelMarkup> markupList=new ArrayList<HotelMarkup>();
				HotelMarkUpUtil.addDynamicMarkup(appKeyVo, markupList, markupAmount);
				markups.put(String.valueOf(appKeyVo.getCompanyId()), markupList);
			}
			/////////////////*************
			System.out.println("markups" +markups.size());
			hsc.getHotelMarkupCommissionDetails().setMarkups(markups);
			//	hotelmarkupCommissionDetails.setMarkups(markups);
			//hsc.setHotelMarkupCommissionDetails(hotelmarkupCommissionDetails);


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

			//	return apimap ;
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
				else
				{
					APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR  + " Select some other city to search...City ref is null"));
					return apiRoomDetail ;
					//apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Select some other city to search...City ref is null"));
					//return apimap ;
				}
			}
		}
		catch(FlightException e)
		{
			APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR  + e.getDebugMessage()));
			return apiRoomDetail ;
			//apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getDebugMessage() ));
			//return apimap ;
		}
		catch(Exception e)
		{
			APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR  + e.getMessage()));
			return apiRoomDetail ;
			//apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " City and Country code has issues..."+e.getMessage() ));
			//return apimap ;
		}
		if(hotelSearchCity == null)
		{
			APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR  +" Select some other city to search...City ref is null"));
			return apiRoomDetail ;
			//apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Select some other city to search...City ref is null"));
			//return apimap ;
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
				//apiProviderMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, roomStaysMapTbo.size());
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
				hotelmarkupCommissionDetails = companyDao
						.getHotelMarkupCommissionDetails(appKeyVo);
				Map<String,List<HotelMarkup>> markups = hotelMarkupDao.getHotelMarkUpConfigMapByCompanyId(appKeyVo,  markupMap);
				//List<HotelMarkup> markups = new ArrayList<HotelMarkup>();
				hotelmarkupCommissionDetails.setMarkups(markups);
				//**** for dyanmic markup by ilyas
				/*if(isDynamicMarkup){
					//String companyId = appKeyVo.substring(appKeyVo.indexOf("-") + 1);
					List<HotelMarkup> markupList=new ArrayList<HotelMarkup>();
					addDynamicMarkup(appKeyVo, markupList, markupAmount);
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
				logger.error("Exception", e1);
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

			BigInteger searchKey = apimap.getSearchKey();
			String appKey = apimap.getHotelSearchCommand().getApikey();
			String hotelCode = "";
			for (Entry<String, RoomStay> entry : apimap.getRoomStays().entrySet()) {

				RoomStay rs = entry.getValue();
				if(hotelcode!=null && !hotelcode.equals("")){
					if(hotelcode.equalsIgnoreCase(rs.getBasicPropertyInfo().getHotelCode()));{
						hotelCode = rs.getBasicPropertyInfo().getHotelCode();
					}
				}
				else if(hotelname!=null && !hotelname.equals("")){
					if(hotelname.equalsIgnoreCase(rs.getBasicPropertyInfo().getHotelName()) );{
						hotelCode = rs.getBasicPropertyInfo().getHotelCode();
					}
				}
				else if(address!=null && !address.equals("")){
					for (String addressline : rs.getBasicPropertyInfo().getAddress().getAddressLines()) {
						if(address.equalsIgnoreCase(addressline ));{
							hotelCode = rs.getBasicPropertyInfo().getHotelCode();
						}
					}

				}

			}


			///// Call Room Details////////////////

			if(searchKey != null && !searchKey.equals("")){

				HotelSearchTemp hsroom = new HotelSearchTemp();
				HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchKey.longValue());
				HotelTransactionTemp htroom = new HotelTransactionTemp();
				TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay> roomStays = new TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
				Islhotelmapping islhotelmapping= null;
				HotelSearchCommand hscroom = null;

				logger.info("Searhing roomdetails searchkey : "+searchKey.longValue());
				hsroom = hotelSearchDao.getHotelSearch(searchKey.longValue());
				htroom = hotelTransactionDao.getHotelTransaction(searchKey.longValue());
				hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
				if(hotelSearchRoomDetail == null)
				{
					hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchKey.longValue());
				}
				//logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp-"+hotelSearchRoomDetail.toString());
				hscroom =  (HotelSearchCommand) SerializationUtils.deserialize(hsroom.getHotelsearch_cmd());

				APIHotelMap apiHotelMap = (APIHotelMap) SerializationUtils.deserialize(hsroom.getHotelres_map());
				if(apiHotelMap == null)
				{
					return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Search found, Try again"));
				}
				logger.info("apiHotelMap.getRoomStays() size : "+apiHotelMap.getRoomStays().size());
				TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>  apiProviderMapRoom = (apiHotelMap.getApiProviderMap() == null)? new TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>():apiHotelMap.getApiProviderMap();
				roomStays = apiHotelMap.getRoomStays() == null?new TreeMap<String, RoomStay>():apiHotelMap.getRoomStays() ;
				logger.info("roomStays size : "+roomStays.size());

				//ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> rsList = new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>();

				//:::::::::::::::for multiple hotels starts..
				//HashMap<Integer, List<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>> roomStayMap = new HashMap<Integer, List<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>>();
				//:::::::::::::::for multiple hotels ends..
				HashMap<Integer, OTAHotelAvailRS.RoomStays.RoomStay> roomStayMap = new HashMap<Integer,OTAHotelAvailRS.RoomStays.RoomStay>();



				OTAHotelAvailRS.RoomStays.RoomStay rs = null;
				List<OTAHotelAvailRS.RoomStays.RoomStay> rsApiCompleteList = new ArrayList<OTAHotelAvailRS.RoomStays.RoomStay>();
				OTAHotelAvailRS.RoomStays.RoomStay rsApiComplete = null;
				OTAHotelAvailRS.RoomStays.RoomStay rsComplete = null;
				List<HotelSearchCommand.RoomReqInfo> roomReqs = null;

				HashMap<Integer, HashMap<String, Boolean>> apiHotelIdMap = (apiProviderMapRoom.get(hotelcode)!=null)?apiProviderMapRoom.get(hotelcode):new HashMap<Integer, HashMap<String,Boolean>>();

				int roomCollectCount = 1;
				Boolean isOfflinePull = false;
				for (Entry<Integer, HashMap<String, Boolean>> entryApi : apiHotelIdMap.entrySet()) {
					HashMap<String, Boolean> apiHotelId = entryApi.getValue();
					Integer apiProvider = entryApi.getKey();
					switch (apiProvider) {
					case HotelApiCredentials.API_DESIA_IND:
						for (Entry<String, Boolean> entryApiHotelId : apiHotelId.entrySet()) {
							String key = entryApiHotelId.getKey();
							rs = roomStays.get(key);
							if(rs!=null)
							{
								//F:\logs\tayyarah\hotel-tbo-50-prebook-response.json
								//byte[] jsonData = Files.readAllBytes(Paths.get("D:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\common\\"+searchkey+"-rs-desiya-request.json"));
								//rsApiComplete = this.mapper.readValue(jsonData, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay.class);
								rsApiComplete = hotelObjectTransformer.getTGRoomDetails(rs);


								HotelApiCredentials apidesiyaRoom = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
								DesiyaPullerTask desiyaPullerTaskRoom = new DesiyaPullerTask(hs, hotelObjectTransformer, null, null, null, null, null, null, null , apidesiyaRoom, hscroom, "TBO Api", rs.getCity());
								//rsApiComplete = desiyaPullerTask.searchHotelDetail(rsApiComplete);


								FileUtil.writeJson("hotel", "common", "rs-desiya-"+roomCollectCount, false, rsApiComplete, String.valueOf(searchkey));
								logger.info("###################################");
								logger.info("############## API_DESIA_IND room collection ---: "+roomCollectCount);
								logger.info("############## room collection ---rsApiComplete : "+rsApiComplete);


								roomCollectCount += 1;
								if(rsApiComplete != null && rsApiComplete.getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates().size()>0)
								{
									//:::::::::::::::for multiple hotels starts..
									//rsApiCompleteList =  roomStayMap.get(HotelApiCredentials.API_DESIA_IND) == null ? new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_DESIA_IND);
									//rsApiCompleteList.add(rsApiComplete);
									//roomStayMap.put(HotelApiCredentials.API_DESIA_IND, rsApiCompleteList);
									//:::::::::::::::for multiple hotels ends..
									roomStayMap.put(HotelApiCredentials.API_DESIA_IND, rsApiComplete);

									//rsList.add(rsApiComplete);
								}
							}


							break;
						}

						break;
					case HotelApiCredentials.API_REZNEXT_IND:

						break;
					case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:

						break;
					case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
						/*
						HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL);
						//TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hscroom, "Tayyarah Api");
						TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(searchkey, apitayyarah, hscroom, "Tayyarah Api");
						APIRoomDetail tayyarahroomdetail = tayyarahPullerTask.searchHotelRooms(rsApiInComplete);
						rsApiInComplete = tayyarahroomdetail.getRs();
						//HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
						//RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hscroom, "RazLive Api");
						//rs = rezLivePullerTask.getRoomDetail(rs, bookingkey);
						 */
						break;

					case HotelApiCredentials.API_LINTAS_REPOSITORY:
						//Do Room availablity check of lintas Rposit hotels
						break;
					case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
						//Do Room availablity check of lintas api hotels
						break;

					case HotelApiCredentials.API_TBO_INTERNATIONAL:

						for (Entry<String, Boolean> entryApiHotelId : apiHotelId.entrySet()) {
							String key = entryApiHotelId.getKey();
							rs = roomStays.get(key);
							if(rs!=null)
							{
								roomReqs = hscroom.getRoomrequests();
								logger.info("Searhing roomReqs size : "+roomReqs.size());
								//byte[] jsonData = Files.readAllBytes(Paths.get("D:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\common\\"+searchkey+"-rs-tbo-request.json"));
								//rsApiComplete = this.mapper.readValue(jsonData, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay.class);
								HotelApiCredentials apitbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
								TBOPullerTask tboRoomPullerTask = new TBOPullerTask(hs, hotelObjectTransformer, null, null, null, null, null, null, null , apitbo, hscroom, "TBO Api", rs.getCity());
								rsApiComplete = tboRoomPullerTask.searchHotelRooms(rs);
								logger.info("Searhing after room cancellation policy: "+rsApiComplete);
								FileUtil.writeJson("hotel", "common", "rs-tbo-"+roomCollectCount, false, rsApiComplete, String.valueOf(searchkey));
								logger.info("###################################");
								logger.info("############## API_TBO_INTERNATIONAL room collection ---: "+roomCollectCount);
								logger.info("############## room collection ---rsApiComplete : "+rsApiComplete);
								roomCollectCount += 1;
								if(rsApiComplete != null && rsApiComplete.getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates().size()>0)
								{
									//roomStayMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, rsApiComplete);
									//rsList.add(rsApiComplete);
									//:::::::::::::::for multiple hotels starts..
									//rsApiCompleteList =  roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL) == null ? new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL);
									//rsApiCompleteList.add(rsApiComplete);
									//roomStayMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, rsApiCompleteList);
									//:::::::::::::::for multiple hotels ends..
									roomStayMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, rsApiComplete);


								}
							}
							break;

						}
						rsComplete = RoomAnalyzer.mergeRooms(rsApiComplete, HotelApiCredentials.API_TBO_INTERNATIONAL);
						break;
					case HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL:

						for (Entry<String, Boolean> entryApiHotelId : apiHotelId.entrySet()) {
							String key = entryApiHotelId.getKey();
							rs = roomStays.get(key);
							rsComplete = rs;

						}
						isOfflinePull = true;
						rsComplete = RoomAnalyzer.mergeRooms(rsComplete, HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL);

						break;


					default:
						break;
					}
				}
				logger.info("###################### suppliers count in room search---------:"+ roomStayMap.size());
				//rsComplete = rsApiComplete;

				//:::::::::::::::for multiple hotels starts..
				//rsComplete = RoomAnalyzer.mergeRoomsOfSuppliersHotels(roomStayMap);
				//:::::::::::::::for multiple hotels ends..
				if(!isOfflinePull)
					rsComplete = RoomAnalyzer.mergeRoomsOfSuppliersHotel(roomStayMap);
				//byte[] jsonData = Files.readAllBytes(Paths.get("D:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\common\\"+searchkey+"-rs-tbo-request.json"));
				//rsApiComplete = this.mapper.readValue(jsonData, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay.class);


				//FileUtil.writeJson("hotel", "common", "rs-combined", false, rsComplete, String.valueOf(searchkey));

				//markups = hotelMarkupDao.getHotelMarkups(companyId);

				if(rsComplete == null || rsComplete.getRoomRates() == null || rsComplete.getRoomRates().getRoomRates() == null || rsComplete.getRoomRates().getRoomRates().size()==0)
				{
					APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Room Details found, Try some other hotel"));
					apiRoomDetail.setRoomStayMap(roomStayMap);
					return apiRoomDetail;
					//return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Room Details found, Try some other hotel"));
				}
				Map<String,List<HotelMarkup>> markups = hscroom.getHotelMarkupCommissionDetails().getMarkups();
				//				logger.info("HotelSearchCommand- no of markups----------:"+ markups.size()+"- for company id--"+companyId);
				rs = hotelAnalyzer.reDefineRoomDetailResponse(hscroom, markups, rsComplete);

				//rs = HotelMarkUpUtil.applyMarkUpOnHotelRoomDetail(hscroom, markups, rs);
				//				logger.info("HotelSearchCommand- mark up applied for roomdetail....----------:"+ markups.size()+"- for company id--"+companyId);

				//apiHotelMap.put(hotelcode, rs);
				logger.info("Room details have been updated for  -- "+hotelcode+"----------:");


				byte[] roomStaysdata = SerializationUtils.serialize(rs);
				hotelSearchRoomDetail.setRoomstay(roomStaysdata);
				hotelSearchRoomDetail.setApi_provider(Integer.valueOf(rs.getBasicPropertyInfo().getApiProvider()));
				hotelSearchRoomDetail.setHotelsearch_cmd(hs.getHotelsearch_cmd());
				hotelSearchRoomDetail = hotelSearchRoomDetailDao.insertOrupdateHotelSearchRoomDetail(hotelSearchRoomDetail);
				//byte[] roomStaysMapdata = SerializationUtils.serialize(apiHotelMap);
				//hs.setHotelres_map(roomStaysMapdata);
				//hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);
				logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp updated...-");


				return new APIRoomDetail(hscroom, rs, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS), BigInteger.valueOf(ht.getSearch_key()), BigInteger.valueOf(ht.getId()) ) ;


			}
		}



		//return apimap;

		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Exception:HibernateException--"+e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "IOException--"+e.getMessage() )) ;

		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "ClassNotFoundException--"+e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "JAXBException--"+e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "UnsupportedOperationException--"+e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "SOAPException--"+e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Exception--"+e.getMessage() )) ;

		}
		return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR )) ;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/new",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIRoomDetail getRoomStayDetailsNew(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {
		logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelcode-"+hotelcode);
		ResponseHeader.setPostResponse(response);//Setting response header
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}
		ObjectMapper mapper = new ObjectMapper();
		//hbc.setAppkey();
		HotelSearchTemp hs = new HotelSearchTemp();
		HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
		HotelTransactionTemp ht = new HotelTransactionTemp();
		TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay> roomStays = new TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelSearchCommand hsc = null;
		try
		{
			logger.info("Searhing roomdetails searchkey : "+searchkey);
			hs = hotelSearchDao.getHotelSearch(searchkey);
			ht = hotelTransactionDao.getHotelTransaction(searchkey);
			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			if(hotelSearchRoomDetail == null)
			{
				hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
			}
			//logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp-"+hotelSearchRoomDetail.toString());
			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());

			APIHotelMap apiHotelMap = (APIHotelMap) SerializationUtils.deserialize(hs.getHotelres_map());
			if(apiHotelMap == null)
			{
				return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Search found, Try again"));
			}
			logger.info("apiHotelMap.getRoomStays() size : "+apiHotelMap.getRoomStays().size());
			TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>  apiProviderMap = (apiHotelMap.getApiProviderMap() == null)? new TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>():apiHotelMap.getApiProviderMap();
			roomStays = apiHotelMap.getRoomStays() == null?new TreeMap<String, RoomStay>():apiHotelMap.getRoomStays() ;
			logger.info("roomStays size : "+roomStays.size());

			//ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> rsList = new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>();

			//:::::::::::::::for multiple hotels starts..
			//HashMap<Integer, List<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>> roomStayMap = new HashMap<Integer, List<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>>();
			//:::::::::::::::for multiple hotels ends..
			HashMap<Integer, OTAHotelAvailRS.RoomStays.RoomStay> roomStayMap = new HashMap<Integer, OTAHotelAvailRS.RoomStays.RoomStay>();



			OTAHotelAvailRS.RoomStays.RoomStay rs = null;
			List<OTAHotelAvailRS.RoomStays.RoomStay> rsApiCompleteList = new ArrayList<OTAHotelAvailRS.RoomStays.RoomStay>();
			OTAHotelAvailRS.RoomStays.RoomStay rsApiComplete = null;
			OTAHotelAvailRS.RoomStays.RoomStay rsComplete = null;
			List<HotelSearchCommand.RoomReqInfo> roomReqs = null;

			HashMap<Integer, HashMap<String, Boolean>> apiHotelIdMap = (apiProviderMap.get(hotelcode)!=null)?apiProviderMap.get(hotelcode):new HashMap<Integer, HashMap<String,Boolean>>();

			int roomCollectCount = 1;
			Boolean isOfflinePull = false;
			for (Entry<Integer, HashMap<String, Boolean>> entryApi : apiHotelIdMap.entrySet()) {
				HashMap<String, Boolean> apiHotelId = entryApi.getValue();
				Integer apiProvider = entryApi.getKey();
				switch (apiProvider) {
				case HotelApiCredentials.API_DESIA_IND:
					for (Entry<String, Boolean> entryApiHotelId : apiHotelId.entrySet()) {
						String key = entryApiHotelId.getKey();
						rs = roomStays.get(key);
						if(rs!=null)
						{
							//F:\logs\tayyarah\hotel-tbo-50-prebook-response.json
							//byte[] jsonData = Files.readAllBytes(Paths.get("D:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\common\\"+searchkey+"-rs-desiya-request.json"));
							//rsApiComplete = this.mapper.readValue(jsonData, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay.class);
							rsApiComplete = hotelObjectTransformer.getTGRoomDetails(rs);


							HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
							DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(hs, hotelObjectTransformer, null, null, null, null, null, null, null , apidesiya, hsc, "TBO Api", rs.getCity());
							//rsApiComplete = desiyaPullerTask.searchHotelDetail(rsApiComplete);


							FileUtil.writeJson("hotel", "common", "rs-desiya-"+roomCollectCount, false, rsApiComplete, String.valueOf(searchkey));
							logger.info("###################################");
							logger.info("############## API_DESIA_IND room collection ---: "+roomCollectCount);
							logger.info("############## room collection ---rsApiComplete : "+rsApiComplete);


							roomCollectCount += 1;
							if(rsApiComplete != null && rsApiComplete.getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates().size()>0)
							{
								//:::::::::::::::for multiple hotels starts..
								//rsApiCompleteList =  roomStayMap.get(HotelApiCredentials.API_DESIA_IND) == null ? new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_DESIA_IND);
								//rsApiCompleteList.add(rsApiComplete);
								//roomStayMap.put(HotelApiCredentials.API_DESIA_IND, rsApiCompleteList);
								//:::::::::::::::for multiple hotels ends..
								roomStayMap.put(HotelApiCredentials.API_DESIA_IND, rsApiComplete);

								//rsList.add(rsApiComplete);
							}
						}


						break;
					}

					break;
				case HotelApiCredentials.API_REZNEXT_IND:

					break;
				case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:

					break;
				case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
					/*
					HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL);
					//TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");
					TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(searchkey, apitayyarah, hsc, "Tayyarah Api");
					APIRoomDetail tayyarahroomdetail = tayyarahPullerTask.searchHotelRooms(rsApiInComplete);
					rsApiInComplete = tayyarahroomdetail.getRs();
					//HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
					//RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");
					//rs = rezLivePullerTask.getRoomDetail(rs, bookingkey);
					 */
					break;

				case HotelApiCredentials.API_LINTAS_REPOSITORY:
					//Do Room availablity check of lintas Rposit hotels
					break;
				case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
					//Do Room availablity check of lintas api hotels
					break;

				case HotelApiCredentials.API_TBO_INTERNATIONAL:

					for (Entry<String, Boolean> entryApiHotelId : apiHotelId.entrySet()) {
						String key = entryApiHotelId.getKey();
						rs = roomStays.get(key);
						if(rs!=null)
						{
							roomReqs = hsc.getRoomrequests();
							logger.info("Searhing roomReqs size : "+roomReqs.size());
							//byte[] jsonData = Files.readAllBytes(Paths.get("D:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\common\\"+searchkey+"-rs-tbo-request.json"));
							//rsApiComplete = this.mapper.readValue(jsonData, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay.class);
							HotelApiCredentials apitbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
							TBOPullerTask tboRoomPullerTask = new TBOPullerTask(hs, hotelObjectTransformer, null, null, null, null, null, null, null , apitbo, hsc, "TBO Api", rs.getCity());
							rsApiComplete = tboRoomPullerTask.searchHotelRooms(rs);
							logger.info("Searhing after room cancellation policy: "+rsApiComplete);
							FileUtil.writeJson("hotel", "common", "rs-tbo-"+roomCollectCount, false, rsApiComplete, String.valueOf(searchkey));
							logger.info("###################################");
							logger.info("############## API_TBO_INTERNATIONAL room collection ---: "+roomCollectCount);
							logger.info("############## room collection ---rsApiComplete : "+rsApiComplete);
							roomCollectCount += 1;
							if(rsApiComplete != null && rsApiComplete.getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates().size()>0)
							{
								//roomStayMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, rsApiComplete);
								//rsList.add(rsApiComplete);
								//:::::::::::::::for multiple hotels starts..
								//rsApiCompleteList =  roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL) == null ? new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL);
								//rsApiCompleteList.add(rsApiComplete);
								//roomStayMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, rsApiCompleteList);
								//:::::::::::::::for multiple hotels ends..
								roomStayMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, rsApiComplete);


							}
						}
						break;

					}
					rsComplete = RoomAnalyzer.mergeRooms(rsApiComplete, HotelApiCredentials.API_TBO_INTERNATIONAL);
					break;
				case HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL:

					for (Entry<String, Boolean> entryApiHotelId : apiHotelId.entrySet()) {
						String key = entryApiHotelId.getKey();
						rs = roomStays.get(key);
						rsComplete = rs;

					}
					isOfflinePull = true;
					rsComplete = RoomAnalyzer.mergeRooms(rsComplete, HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL);

					break;


				default:
					break;
				}
			}
			logger.info("###################### suppliers count in room search---------:"+ roomStayMap.size());
			//rsComplete = rsApiComplete;

			//:::::::::::::::for multiple hotels starts..
			//rsComplete = RoomAnalyzer.mergeRoomsOfSuppliersHotels(roomStayMap);
			//:::::::::::::::for multiple hotels ends..
			if(!isOfflinePull)
				rsComplete = RoomAnalyzer.mergeRoomsOfSuppliersHotel(roomStayMap);
			//byte[] jsonData = Files.readAllBytes(Paths.get("D:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\common\\"+searchkey+"-rs-tbo-request.json"));
			//rsApiComplete = this.mapper.readValue(jsonData, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay.class);


			//FileUtil.writeJson("hotel", "common", "rs-combined", false, rsComplete, String.valueOf(searchkey));

			//markups = hotelMarkupDao.getHotelMarkups(companyId);

			if(rsComplete == null || rsComplete.getRoomRates() == null || rsComplete.getRoomRates().getRoomRates() == null || rsComplete.getRoomRates().getRoomRates().size()==0)
			{
				APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Room Details found, Try some other hotel"));
				apiRoomDetail.setRoomStayMap(roomStayMap);
				return apiRoomDetail;
				//return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Room Details found, Try some other hotel"));
			}
			Map<String,List<HotelMarkup>> markups = hsc.getHotelMarkupCommissionDetails().getMarkups();
			//			logger.info("HotelSearchCommand- no of markups----------:"+ markups.size()+"- for company id--"+companyId);
			rs = hotelAnalyzer.reDefineRoomDetailResponse(hsc, markups, rsComplete);
			//rs = HotelMarkUpUtil.applyMarkUpOnHotelRoomDetail(hsc, markups, rs);
			//			logger.info("HotelSearchCommand- mark up applied for roomdetail....----------:"+ markups.size()+"- for company id--"+companyId);

			//apiHotelMap.put(hotelcode, rs);
			logger.info("Room details have been updated for  -- "+hotelcode+"----------:");


			byte[] roomStaysdata = SerializationUtils.serialize(rs);
			hotelSearchRoomDetail.setRoomstay(roomStaysdata);
			hotelSearchRoomDetail.setApi_provider(Integer.valueOf(rs.getBasicPropertyInfo().getApiProvider()));
			hotelSearchRoomDetail.setHotelsearch_cmd(hs.getHotelsearch_cmd());
			hotelSearchRoomDetail = hotelSearchRoomDetailDao.insertOrupdateHotelSearchRoomDetail(hotelSearchRoomDetail);
			//byte[] roomStaysMapdata = SerializationUtils.serialize(apiHotelMap);
			//hs.setHotelres_map(roomStaysMapdata);
			//hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);
			logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp updated...-");


			return new APIRoomDetail(hsc, rs, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS), BigInteger.valueOf(ht.getSearch_key()), BigInteger.valueOf(ht.getId()) ) ;

		}
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Exception:HibernateException--"+e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "IOException--"+e.getMessage() )) ;

		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "ClassNotFoundException--"+e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "JAXBException--"+e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "UnsupportedOperationException--"+e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "SOAPException--"+e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Exception--"+e.getMessage() )) ;

		}

	}


	public void addDynamicMarkup(String app_key,List<HotelMarkup> markUplist,String markupAmount){
		String companyId = "invalid";
		String configId = "invalid";
		configId = app_key.substring(0, app_key.indexOf("-"));
		companyId = app_key.substring(app_key.indexOf("-") + 1);
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
		hotelMarkup.setCompanyId(Integer.valueOf(companyId));
		hotelMarkup.setConfigId(Integer.valueOf(configId));
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
		markUplist.add(hotelMarkup);
	}
}
