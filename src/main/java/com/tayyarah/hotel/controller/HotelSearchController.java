package com.tayyarah.hotel.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
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
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.reposit.dao.HotelRepositDAOIMP;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelMarkUpUtil;
import com.tayyarah.hotel.util.HotelAnalyzer;
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
@RequestMapping("/hotel/search")
public class HotelSearchController {
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
	public static final Logger logger = Logger.getLogger(HotelSearchController.class);

	@Autowired
	HotelAnalyzer hotelAnalyzer;

	@Autowired
	HotelRepositDAOIMP lintashoteldaoImp;

	@Autowired
	HotelRepositService hotelTayyarahRepositService;

	@Autowired
	CompanyConfigDAO companyConfigDAO;

	private long startTime;

	@RequestMapping(value="/markups",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	HotelMarkupCommissionDetails getMarkUps(@RequestParam(value="appkey") String appkey,    HttpServletResponse response) throws HibernateException, IOException, Exception {
		HotelMarkupCommissionDetails hotelmarkupCommissionDetails = null;
		try {
			AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
			if(appKeyVo==null)
			{
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
			}
		} catch (Exception e1) {
			logger.error("Exception", e1);
		}
		return hotelmarkupCommissionDetails;

	}


	@RequestMapping(value="/new",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIHotelMap getRoomStaies(@RequestParam(value="appkey") String appkey, @RequestParam(value="mode") int mode,@RequestParam(value="type") int type,@RequestParam(value="order") String order,@RequestParam(value="filter") int filter,@RequestParam(value="cachelevel") String cachelevel,@RequestParam(value="currency") String currency,@RequestParam(value="version") BigDecimal version,@RequestParam(value="lang") String lang,@RequestParam(value="citycode") Integer citycode,
			@RequestParam(value="datestart") String datestart,@RequestParam(value="dateend") String dateend,@RequestParam(value="noofrooms") int noofrooms, @RequestParam(value="rooms") String rooms, @RequestParam(value="istesting", defaultValue="false") boolean istesting, @RequestParam(value="apiids", defaultValue="1,2,4,5") String apiids, @RequestParam(value = "isDynamicMarkup", defaultValue = "false") boolean isDynamicMarkup,
			@RequestParam(value = "markupAmount", defaultValue = "0.0") String markupAmount, @RequestParam(value = "searchkey") String searchkey,  HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		APIHotelMap apimap = new APIHotelMap();
		startTime = System.currentTimeMillis();
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
		HotelApiCredentials apirezt = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);
		HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apilintasReposit = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_LINTAS_REPOSITORY,appKeyVo);
		HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apiTbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
		HotelApiCredentials apitayyarahReposit = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL,appKeyVo);


		/*if(!apiTbo.isTesting() && CommonConfig.GetCommonConfig().isIs_dev_mode())
			throw new Exception("Live Booking is not allowed in dev mode");
		 */
		String  baseCurrency = "INR";
		try {
			baseCurrency = companyDao.getCompanyCurrencyCode(appKeyVo.getCompanyId());
		} catch (Exception e1) {
			logger.error("Exception", e1);
		}


		if(appkey!=null && appkey.equalsIgnoreCase(""))
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + APIStatus.STATUS_MESSAGE_APPKEY_NOTFOUND));
			return apimap ;
		}
		System.out.println("searchkey" +searchkey);
		System.out.println("isDynamicMarkup" +isDynamicMarkup);
		if(!searchkey.equalsIgnoreCase("") && isDynamicMarkup ){
			HotelSearchTemp hs = hotelSearchDao.getHotelSearch(Long.parseLong(searchkey));
			System.out.println("hs" +hs);
			TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
			HotelSearchCommand hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());
			apiHotelMap =  (TreeMap<String, RoomStay>) SerializationUtils.deserialize(hs.getHotelres_map());
			Map<String,List<HotelMarkup>> markups = new HashMap<String,List<HotelMarkup>>();
			HotelMarkupCommissionDetails hotelmarkupCommissionDetails = new HotelMarkupCommissionDetails();
			hotelmarkupCommissionDetails.setMarkups(markups);
			if(isDynamicMarkup){
				//String companyId = decryptrdAppKey.substring(decryptrdAppKey.indexOf("-") + 1);
				List<HotelMarkup> markupList=new ArrayList<HotelMarkup>();
				HotelMarkUpUtil.addDynamicMarkup(appKeyVo, markupList, markupAmount);
				markups.put(String.valueOf(appKeyVo.getCompanyId()), markupList);
			}
			/////////////////*************

			hsc.setHotelMarkupCommissionDetails(hotelmarkupCommissionDetails);


			apimap = hotelAnalyzer.reDefineHotelResponse(apimap, hsc.getCurrency(), baseCurrency, hsc,companyConfigDAO,companyDao);

			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... ############  Hotel search -------total hotels count-redefined---:"+ apimap.getRoomStays().size());
			return apimap ;
		}
		else{
			List<Integer> apiIdList = CommonUtil.getSelectedApiIdList(istesting, apiids);
			HotelSearchCity hotelSearchCity = null;
			try{
				if(apidesiya.isEnabled() && apiTbo.isEnabled())
				{
					hotelSearchCity = hotelCityDao.getHotelSearchCity(citycode);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity-----------:"+ hotelSearchCity);
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
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity----tbo -------:"+ hotelSearchCity.getTboCity());
					}
					else
					{
						apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Select some other city to search...City ref is null"));
						return apimap ;
					}
				}
				if(hotelSearchCity!=null)
				{
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity----tg ------:"+ hotelSearchCity.getTgCity());
				}
				/* Only enable here for rezlive and reznxt
				 * 	logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity----reznext ------:"+ hotelSearchCity.getReznextCity());
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity----rezlive -------:"+ hotelSearchCity.getRezliveCity());	*/

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

			String country = (hotelSearchCity.getTboCity() != null && hotelSearchCity.getTboCity().getCountry() != null && hotelSearchCity.getTboCity().getCountry().trim().length()>1)?hotelSearchCity.getTboCity().getCountry():"India";


			HotelSearchCommand hsc = new HotelSearchCommand(appkey, mode, type, order, filter, cachelevel, currency, new BigDecimal("1.0"),lang, hotelSearchCity.getCity(), hotelSearchCity.getCountryCode(), country, datestart , dateend, noofrooms, rooms );
			hsc.setSearchCity(hotelSearchCity);

			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			logger.info(CommonUtil.getElapsedTime(startTime)+" #############  ip address---X-FORWARDED-FOR--------:"+ ipAddress);

			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
				logger.info(CommonUtil.getElapsedTime(startTime)+" #############  ip address---getRemoteAddr--------:"+ ipAddress);
			}
			if(ipAddress.contains("0:0:0:0:0"))
				ipAddress = "122.166.233.133";

			logger.info(CommonUtil.getElapsedTime(startTime)+"hotel search server ip address-----------:"+ ipAddress);
			hsc.setEndUserIp(ipAddress);
			hsc = CommonUtil.initSearchDestinationType(hsc, companyDao);


			HotelTransactionTemp ht = new HotelTransactionTemp();
			HotelSearchTemp hs = new HotelSearchTemp();
			hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelsearch-----------:"+ hs.toString());

			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand-----------:"+ hsc.toString());
			ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();

			Future<APIHotelMap> apistacktayyarah = null;
			Future<APIHotelMap> apistackdesiya = null;
			Future<APIHotelMap> apistackreznext = null;
			Future<APIHotelMap> apistackrezlive = null;
			Future<APIHotelMap> apistacklintasreposit = null;
			Future<APIHotelMap> apistacktbo = null;

			TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(BigInteger.valueOf(hs.getSearch_key()), hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");


			DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(hs, hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apidesiya, hsc, "Desiya Api", hotelSearchCity);

			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand-------desiya----:"+ desiyaPullerTask.getApi().getApiProviderName());


			RezNextPullerTask reznextPullerTask = new RezNextPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apirezt, hsc, "RazNext Api");

			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand-------reznext----:"+ reznextPullerTask.getApi().getApiProviderName());

			RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");

			LintasHotelRepositPullerTask lintasHotelRepositPullerTask = new LintasHotelRepositPullerTask(hotelObjectTransformer, lintashoteldaoImp, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apilintasReposit, hsc, "Lintas Reposit Api");

			TBOPullerTask tboPullerTask = new TBOPullerTask(hs, hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apiTbo, hsc, "TBO Api", hotelSearchCity);


			APIHotelMap apimaptayyarah = new APIHotelMap();
			APIHotelMap apimapdesiya = new APIHotelMap();
			APIHotelMap apimapreznext = new APIHotelMap();
			APIHotelMap apimaprezlive = new APIHotelMap();
			APIHotelMap apimaplintasreposit = new APIHotelMap();
			APIHotelMap apimaptbo = new APIHotelMap();


			TPAExtensions textensions = new TPAExtensions();
			HotelsInfo thotelsInfo = new HotelsInfo();

			HashMap<Integer, Integer> apiProviderMap = new HashMap<Integer, Integer>();


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

				if(!istesting)// live
				{
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------live search----:");
					if( apitayyarah.isEnabled() && CommonUtil.isAPIValid(hsc, apitayyarah ))
					{
						apistacktayyarah = apiPullExecutor.submit(tayyarahPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah started----:");

						//apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
						apimaptayyarah = apistacktayyarah.get();
						if(apimaptayyarah.getRoomStays()!=null && apimaptayyarah.getRoomStays().size()>0)
						{
							roomStaysMapTayyarah = apimaptayyarah.getRoomStays();
							apiProviderMap.put(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL, roomStaysMapTayyarah.size());
							roomStaysMap.putAll(roomStaysMapTayyarah);
							logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah hotels count----:"+ roomStaysMap.size());
						}
					}
					if( apidesiya.isEnabled() && CommonUtil.isAPIValid(hsc, apidesiya))
					{
						apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya started----:");

						//apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
						apimapdesiya = apistackdesiya.get();
						if( apimapdesiya.getRoomStays()!=null &&  apimapdesiya.getRoomStays().size()>0)
						{
							roomStaysMapDesiya = apimapdesiya.getRoomStays();
							apiProviderMap.put(HotelApiCredentials.API_DESIA_IND, roomStaysMapDesiya.size());
							roomStaysMap.putAll(roomStaysMapDesiya);
							logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya hotels count----:"+ roomStaysMap.size());
						}
					}
					if( apirezt.isEnabled() && CommonUtil.isAPIValid(hsc, apirezt))
					{
						apistackreznext = apiPullExecutor.submit(reznextPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... seconds ...Hotel search -------reznext started----:");

						//apistackreznext = apiPullExecutor.submit(reznextPullerTask);
						apimapreznext = apistackreznext.get();
						if(apimapreznext.getRoomStays()!=null && apimapreznext.getRoomStays().size()>0)
						{
							roomStaysMapReznext = apimapreznext.getRoomStays();
							apiProviderMap.put(HotelApiCredentials.API_REZNEXT_IND, roomStaysMapReznext.size());
							roomStaysMap.putAll(roomStaysMapReznext);
							logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------reznext hotels count----:"+ roomStaysMapReznext.size());
						}

					}
					if( apireztlive.isEnabled() && CommonUtil.isAPIValid(hsc, apireztlive))
					{
						apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive started----:");

						//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
						apimaprezlive = apistackrezlive.get();
						if(apimaprezlive.getRoomStays()!=null && apimaprezlive.getRoomStays().size()>0)
						{
							roomStaysMapRezlive = apimaprezlive.getRoomStays();
							apiProviderMap.put(HotelApiCredentials.API_REZLIVE_INTERNATIONAL, roomStaysMapRezlive.size());
							roomStaysMap.putAll(roomStaysMapRezlive);
							logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive hotels count----:"+ roomStaysMapRezlive.size());
						}
					}
					if( apilintasReposit.isEnabled() && CommonUtil.isAPIValid(hsc, apilintasReposit))
					{
						apistacklintasreposit = apiPullExecutor.submit(lintasHotelRepositPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit started----:");

						//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
						apimaplintasreposit = apistacklintasreposit.get();
						if(apimaplintasreposit.getRoomStays()!=null && apimaplintasreposit.getRoomStays().size()>0)
						{
							roomStaysMapLintasReposit = apimaplintasreposit.getRoomStays();
							apiProviderMap.put(HotelApiCredentials.API_LINTAS_REPOSITORY, roomStaysMapLintasReposit.size());
							roomStaysMap.putAll(roomStaysMapLintasReposit);
							logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());
						}

					}

					logger.info(CommonUtil.getElapsedTime(startTime)+" TBO apiTbo.isEnabled()----:"+ apiTbo.isEnabled());
					logger.info(CommonUtil.getElapsedTime(startTime)+" TBO CommonUtil.isAPIValid(hsc, apiTbo)----:"+ CommonUtil.isAPIValid(hsc, apiTbo));

					if( apiTbo.isEnabled() && CommonUtil.isAPIValid(hsc, apiTbo))
					{
						apistacktbo = apiPullExecutor.submit(tboPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------TBO started----:");

						apimaptbo = apistacktbo.get();
						roomStaysMapTbo = apimaptbo.getRoomStays();
						if(roomStaysMapTbo!=null && roomStaysMapTbo.size()>0)
						{
							apiProviderMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, roomStaysMapTbo.size());
							roomStaysMap.putAll(roomStaysMapTbo);
							logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());
						}

					}

				}
				else
				{
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------testing search----:");

					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apitayyarah) && CommonUtil.isAPIValid(hsc, apitayyarah))
					{
						apistacktayyarah = apiPullExecutor.submit(tayyarahPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah started----:");
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apidesiya) && CommonUtil.isAPIValid(hsc, apidesiya))
					{
						apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya started----:");
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apirezt) && CommonUtil.isAPIValid(hsc, apirezt))
					{
						apistackreznext = apiPullExecutor.submit(reznextPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------reznext started----:");
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apireztlive) && CommonUtil.isAPIValid(hsc, apireztlive))
					{
						apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive started----:");
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apilintasReposit) && CommonUtil.isAPIValid(hsc, apilintasReposit))
					{
						apistacklintasreposit = apiPullExecutor.submit(lintasHotelRepositPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive started----:");
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apiTbo) && CommonUtil.isAPIValid(hsc, apiTbo))
					{
						apistacktbo = apiPullExecutor.submit(tboPullerTask);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------TBO started----:");
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apitayyarah) && CommonUtil.isAPIValid(hsc, apitayyarah))
					{
						//apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
						apimaptayyarah = apistacktayyarah.get();
						roomStaysMapTayyarah = apimaptayyarah.getRoomStays();
						apiProviderMap.put(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL, roomStaysMapTayyarah.size());
						roomStaysMap.putAll(roomStaysMapTayyarah);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah hotels count----:"+ roomStaysMap.size());
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apidesiya) && CommonUtil.isAPIValid(hsc, apidesiya))
					{
						//apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
						apimapdesiya = apistackdesiya.get();
						roomStaysMapDesiya = apimapdesiya.getRoomStays();
						apiProviderMap.put(HotelApiCredentials.API_DESIA_IND, roomStaysMapDesiya.size());
						roomStaysMap.putAll(roomStaysMapDesiya);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya hotels count----:"+ roomStaysMap.size());

					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apirezt) && CommonUtil.isAPIValid(hsc, apirezt))
					{
						//apistackreznext = apiPullExecutor.submit(reznextPullerTask);
						apimapreznext = apistackreznext.get();
						roomStaysMapReznext = apimapreznext.getRoomStays();
						apiProviderMap.put(HotelApiCredentials.API_REZNEXT_IND, roomStaysMapReznext.size());
						roomStaysMap.putAll(roomStaysMapReznext);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------reznext hotels count----:"+ roomStaysMapReznext.size());
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apireztlive) && CommonUtil.isAPIValid(hsc, apireztlive))
					{
						//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
						apimaprezlive = apistackrezlive.get();
						roomStaysMapRezlive = apimaprezlive.getRoomStays();
						apiProviderMap.put(HotelApiCredentials.API_REZLIVE_INTERNATIONAL, roomStaysMapRezlive.size());
						roomStaysMap.putAll(roomStaysMapRezlive);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive hotels count----:"+ roomStaysMapRezlive.size());
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apilintasReposit) && CommonUtil.isAPIValid(hsc, apilintasReposit))
					{
						//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
						apimaplintasreposit = apistacklintasreposit.get();
						roomStaysMapLintasReposit = apimaplintasreposit.getRoomStays();
						apiProviderMap.put(HotelApiCredentials.API_LINTAS_REPOSITORY, roomStaysMapLintasReposit.size());
						roomStaysMap.putAll(roomStaysMapLintasReposit);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());
					}
					if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apiTbo) && CommonUtil.isAPIValid(hsc, apiTbo))
					{
						apimaptbo = apistacktbo.get();
						roomStaysMapTbo = apimaptbo.getRoomStays();
						apiProviderMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, roomStaysMapTbo.size());
						roomStaysMap.putAll(roomStaysMapTbo);
						logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());
					}
				}


				thotelsInfo.setApiProviderMap(apiProviderMap);
				textensions.setHotelsInfo(thotelsInfo);
				apimap.setTpaExtensions(textensions);

				/////////added by ilyas///////////



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
					if(isDynamicMarkup){
						//String companyId = decryptrdAppKey.substring(decryptrdAppKey.indexOf("-") + 1);
						List<HotelMarkup> markupList=new ArrayList<HotelMarkup>();
						HotelMarkUpUtil.addDynamicMarkup(appKeyVo, markupList, markupAmount);
						markups.put(String.valueOf(appKeyVo.getCompanyId()), markupList);
					}
					/////////////////*************
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------total hotels count----:"+ roomStaysMap.size());
					apimap.setRoomStays(roomStaysMap);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand- no of markups----------:"+ markups.size());
					hsc.setHotelMarkupCommissionDetails(hotelmarkupCommissionDetails);
					//if(hotelmarkupCommissionDetails!=null && hotelmarkupCommissionDetails.getMarkups().size() > 0)
					//	apimap.applyAllLevelMarkUpInitHotelSearch(hsc, hotelmarkupCommissionDetails);

					apimap = hotelAnalyzer.reDefineHotelResponse(apimap, hsc.getCurrency(), baseCurrency, hsc,companyConfigDAO,companyDao);

					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... ############  Hotel search -------total hotels count-redefined---:"+ apimap.getRoomStays().size());


					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel serach response.. markup allied---:");

				} catch (Exception e1) {
					logger.error("Exception", e1);
				}
				/*
			HotelMarkupCommissionDetails hotelmarkupCommissionDetails = null;
			try {
				hotelmarkupCommissionDetails = CDAO
						.getHotelMarkupCommissionDetails(decryptrdAppKey);
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


				return apimap;

			} catch (HibernateException e) {
				e.printStackTrace();
				logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
				apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
				return apimap ;
			} /*catch (IOException e) {
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
		}*/ catch (InterruptedException e)
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
			/*catch (ClassNotFoundException  e)
        {
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
        }
		catch (JAXBException  e)
        {
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
        }*/
			catch (Exception  e)
			{
				e.printStackTrace();
				logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
				apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
				return apimap ;
			}
		}
	}


	@RequestMapping(value="",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIHotelMap getRoomStaies(@RequestParam(value="appkey") String appkey, @RequestParam(value="mode") int mode,@RequestParam(value="type") int type,@RequestParam(value="order") String order,@RequestParam(value="filter") int filter,@RequestParam(value="cachelevel") String cachelevel,@RequestParam(value="currency") String currency,@RequestParam(value="version") BigDecimal version,@RequestParam(value="lang") String lang,@RequestParam(value="city") String city,
			@RequestParam(value="countrycode") String countrycode,@RequestParam(value="country") String country, @RequestParam(value="datestart") String datestart,@RequestParam(value="dateend") String dateend,@RequestParam(value="noofrooms") int noofrooms, @RequestParam(value="rooms") String rooms, @RequestParam(value="istesting", defaultValue="false") boolean istesting, @RequestParam(value="apiids", defaultValue="1,2,4,5") String apiids, @RequestParam(value = "isDynamicMarkup", defaultValue = "false") boolean isDynamicMarkup,
			@RequestParam(value = "markupAmount", defaultValue = "0.0") String markupAmount,   HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		APIHotelMap apimap = new APIHotelMap();
		startTime = System.currentTimeMillis();
		if(appkey!=null && appkey.equalsIgnoreCase(""))
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + APIStatus.STATUS_MESSAGE_APPKEY_NOTFOUND));
			return apimap ;
		}

		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		List<Integer> apiIdList = CommonUtil.getSelectedApiIdList(istesting, apiids);
		HotelSearchCity hotelSearchCity = null;
		try{
			hotelSearchCity = hotelCityDao.getHotelSearchCity(city, countrycode);
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity-----------:"+ hotelSearchCity);
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity----tg ------:"+ hotelSearchCity.getTgCity());
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity----reznext ------:"+ hotelSearchCity.getReznextCity());
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity----rezlive -------:"+ hotelSearchCity.getRezliveCity());
			logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelSearchCity----tbo -------:"+ hotelSearchCity.getTboCity());
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
		HotelSearchCommand hsc = new HotelSearchCommand(appkey, mode, type, order, filter, cachelevel, currency, new BigDecimal("1.0"),lang, city, countrycode, country, datestart , dateend, noofrooms, rooms );
		hsc.setSearchCity(hotelSearchCity);

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		if(ipAddress.contains("0:0:0:0:0"))
			ipAddress = "128.199.209.95";
		logger.info(CommonUtil.getElapsedTime(startTime)+"hotel search server ip address-----------:"+ ipAddress);
		hsc.setEndUserIp(ipAddress);
		hsc = CommonUtil.initSearchDestinationType(hsc, companyDao);


		HotelTransactionTemp ht = new HotelTransactionTemp();
		HotelSearchTemp hs = new HotelSearchTemp();
		hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);
		logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... hotelsearch-----------:"+ hs.toString());

		logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand-----------:"+ hsc.toString());
		ThreadPoolTaskExecutor apiPullExecutor = (ThreadPoolTaskExecutor) asyncSupport.getAsyncExecutor();

		HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
		TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(BigInteger.valueOf(hs.getSearch_key()), hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");


		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
		DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(hs, hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apidesiya, hsc, "Desiya Api", hotelSearchCity);

		logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand-------desiya----:"+ desiyaPullerTask.getApi().getApiProviderName());

		Future<APIHotelMap> apistacktayyarah = null;
		Future<APIHotelMap> apistackdesiya = null;
		Future<APIHotelMap> apistackreznext = null;
		Future<APIHotelMap> apistackrezlive = null;
		Future<APIHotelMap> apistacklintasreposit = null;
		Future<APIHotelMap> apistacktbo = null;
		HotelApiCredentials apirezt = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);
		RezNextPullerTask reznextPullerTask = new RezNextPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apirezt, hsc, "RazNext Api");

		logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand-------reznext----:"+ reznextPullerTask.getApi().getApiProviderName());

		HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
		RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");

		HotelApiCredentials apilintasReposit = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_LINTAS_REPOSITORY,appKeyVo);
		LintasHotelRepositPullerTask lintasHotelRepositPullerTask = new LintasHotelRepositPullerTask(hotelObjectTransformer, lintashoteldaoImp, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apilintasReposit, hsc, "Lintas Reposit Api");

		HotelApiCredentials apiTbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
		TBOPullerTask tboPullerTask = new TBOPullerTask(hs, hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apiTbo, hsc, "TBO Api", hotelSearchCity);




		/*//shut down the executor service now
           executor.shutdown();*/

		APIHotelMap apimaptayyarah = new APIHotelMap();
		APIHotelMap apimapdesiya = new APIHotelMap();
		APIHotelMap apimapreznext = new APIHotelMap();
		APIHotelMap apimaprezlive = new APIHotelMap();
		APIHotelMap apimaplintasreposit = new APIHotelMap();
		APIHotelMap apimaptbo = new APIHotelMap();

		try {
			//logger.info("HotelSearchCommand- getRoomGuests----------:"+ hsc.getRoomGuests().size());
			TreeMap<String, RoomStay> roomStaysMapTayyarah = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapDesiya = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapReznext = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapRezlive = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapLintasReposit = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMapTbo = new TreeMap<String, RoomStay>();
			TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();

			if(!istesting)// live
			{
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
					roomStaysMap.putAll(roomStaysMapTayyarah);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah hotels count----:"+ roomStaysMap.size());

				}
				if( apidesiya.isEnabled() && CommonUtil.isAPIValid(hsc, apidesiya))
				{
					//apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
					apimapdesiya = apistackdesiya.get();
					roomStaysMapDesiya = apimapdesiya.getRoomStays();
					roomStaysMap.putAll(roomStaysMapDesiya);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya hotels count----:"+ roomStaysMap.size());

				}
				if( apirezt.isEnabled() && CommonUtil.isAPIValid(hsc, apirezt))
				{
					//apistackreznext = apiPullExecutor.submit(reznextPullerTask);
					apimapreznext = apistackreznext.get();
					roomStaysMapReznext = apimapreznext.getRoomStays();
					roomStaysMap.putAll(roomStaysMapReznext);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------reznext hotels count----:"+ roomStaysMapReznext.size());
				}
				if( apireztlive.isEnabled() && CommonUtil.isAPIValid(hsc, apireztlive))
				{
					//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
					apimaprezlive = apistackrezlive.get();
					roomStaysMapRezlive = apimaprezlive.getRoomStays();
					roomStaysMap.putAll(roomStaysMapRezlive);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive hotels count----:"+ roomStaysMapRezlive.size());
				}
				if( apilintasReposit.isEnabled() && CommonUtil.isAPIValid(hsc, apilintasReposit))
				{
					//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
					apimaplintasreposit = apistacklintasreposit.get();
					roomStaysMapLintasReposit = apimaplintasreposit.getRoomStays();
					roomStaysMap.putAll(roomStaysMapLintasReposit);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());

				}
				if( apiTbo.isEnabled() && CommonUtil.isAPIValid(hsc, apiTbo))
				{
					apimaptbo = apistacktbo.get();
					roomStaysMapTbo = apimaptbo.getRoomStays();
					roomStaysMap.putAll(roomStaysMapTbo);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());
				}
			}
			else
			{
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------testing search----:");

				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apitayyarah) && CommonUtil.isAPIValid(hsc, apitayyarah))
				{
					apistacktayyarah = apiPullExecutor.submit(tayyarahPullerTask);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah started----:");
				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apidesiya) && CommonUtil.isAPIValid(hsc, apidesiya))
				{
					apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya started----:");
				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apirezt) && CommonUtil.isAPIValid(hsc, apirezt))
				{
					apistackreznext = apiPullExecutor.submit(reznextPullerTask);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------reznext started----:");
				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apireztlive) && CommonUtil.isAPIValid(hsc, apireztlive))
				{
					apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive started----:");
				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apilintasReposit) && CommonUtil.isAPIValid(hsc, apilintasReposit))
				{
					apistacklintasreposit = apiPullExecutor.submit(lintasHotelRepositPullerTask);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive started----:");
				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apiTbo) && CommonUtil.isAPIValid(hsc, apiTbo))
				{
					apistacktbo = apiPullExecutor.submit(tboPullerTask);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------TBO started----:");
				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apitayyarah) && CommonUtil.isAPIValid(hsc, apitayyarah))
				{
					//apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
					apimaptayyarah = apistacktayyarah.get();
					roomStaysMapTayyarah = apimaptayyarah.getRoomStays();
					roomStaysMap.putAll(roomStaysMapTayyarah);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------tayyarah hotels count----:"+ roomStaysMap.size());

				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apidesiya) && CommonUtil.isAPIValid(hsc, apidesiya))
				{
					//apistackdesiya = apiPullExecutor.submit(desiyaPullerTask);
					apimapdesiya = apistackdesiya.get();
					roomStaysMapDesiya = apimapdesiya.getRoomStays();
					roomStaysMap.putAll(roomStaysMapDesiya);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------desiya hotels count----:"+ roomStaysMap.size());

				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apirezt) && CommonUtil.isAPIValid(hsc, apirezt))
				{
					//apistackreznext = apiPullExecutor.submit(reznextPullerTask);
					apimapreznext = apistackreznext.get();
					roomStaysMapReznext = apimapreznext.getRoomStays();
					roomStaysMap.putAll(roomStaysMapReznext);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------reznext hotels count----:"+ roomStaysMapReznext.size());
				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apireztlive) && CommonUtil.isAPIValid(hsc, apireztlive))
				{
					//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
					apimaprezlive = apistackrezlive.get();
					roomStaysMapRezlive = apimaprezlive.getRoomStays();
					roomStaysMap.putAll(roomStaysMapRezlive);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------rezlive hotels count----:"+ roomStaysMapRezlive.size());
				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apilintasReposit) && CommonUtil.isAPIValid(hsc, apilintasReposit))
				{
					//apistackrezlive = apiPullExecutor.submit(rezLivePullerTask);
					apimaplintasreposit = apistacklintasreposit.get();
					roomStaysMapLintasReposit = apimaplintasreposit.getRoomStays();
					roomStaysMap.putAll(roomStaysMapLintasReposit);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());

				}
				if( CommonUtil.isTestingAndApiValid(istesting, apiIdList, apiTbo) && CommonUtil.isAPIValid(hsc, apiTbo))
				{
					apimaptbo = apistacktbo.get();
					roomStaysMapTbo = apimaptbo.getRoomStays();
					roomStaysMap.putAll(roomStaysMapTbo);
					logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------lintas reposit hotels count----:"+ roomStaysMapLintasReposit.size());

				}
			}

			/////////added by ilyas///////////

			String  baseCurrency = "INR";
			try {
				baseCurrency = companyDao.getCompanyCurrencyCode(appKeyVo.getCompanyId());
			} catch (Exception e1) {
				logger.error("Exception", e1);
			}

			Map<String,List<HotelMarkup>> markupMap = new HashMap<String,List<HotelMarkup>>();
			HotelMarkupCommissionDetails hotelmarkupCommissionDetails = null;
			try {
				hotelmarkupCommissionDetails = companyDao.getHotelMarkupCommissionDetails(appKeyVo);
				Map<String,List<HotelMarkup>> markups = hotelMarkupDao.getHotelMarkUpConfigMapByCompanyId(appKeyVo,  markupMap);
				//List<HotelMarkup> markups = new ArrayList<HotelMarkup>();
				hotelmarkupCommissionDetails.setMarkups(markups);
				//**** for dyanmic markup by ilyas
				if(isDynamicMarkup){
					//String companyId = decryptrdAppKey.substring(decryptrdAppKey.indexOf("-") + 1);
					List<HotelMarkup> markupList=new ArrayList<HotelMarkup>();
					HotelMarkUpUtil.addDynamicMarkup(appKeyVo, markupList, markupAmount);
					markups.put(String.valueOf(appKeyVo.getCompanyId()), markupList);
				}
				/////////////////*************
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel search -------total hotels count----:"+ roomStaysMap.size());
				apimap.setRoomStays(roomStaysMap);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... HotelSearchCommand- no of markups----------:"+ markups.size());
				hsc.setHotelMarkupCommissionDetails(hotelmarkupCommissionDetails);
				//if(hotelmarkupCommissionDetails!=null && hotelmarkupCommissionDetails.getMarkups().size() > 0)
				//	apimap.applyAllLevelMarkUpInitHotelSearch(hsc, hotelmarkupCommissionDetails);
				apimap = hotelAnalyzer.reDefineHotelResponse(apimap, hsc.getCurrency(), baseCurrency, hsc,companyConfigDAO,companyDao);
				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... ############  Hotel search -------total hotels count-redefined---:"+ apimap.getRoomStays().size());


				logger.info(CommonUtil.getElapsedTime(startTime)+"seconds ... Hotel serach response.. markup allied---:");

			} catch (Exception e1) {
				logger.error("Exception", e1);
			}
			/*
			HotelMarkupCommissionDetails hotelmarkupCommissionDetails = null;
			try {
				hotelmarkupCommissionDetails = CDAO
						.getHotelMarkupCommissionDetails(decryptrdAppKey);
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



			return apimap;

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
		} /*catch (IOException e) {
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
		}*/ catch (InterruptedException e)
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
		/*catch (ClassNotFoundException  e)
        {
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
        }
		catch (JAXBException  e)
        {
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
        }*/
		catch (Exception  e)
		{
			e.printStackTrace();
			logger.info("HotelSearch----------HibernateException-:"+ e.getMessage());
			apimap.setStatus(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() ));
			return apimap ;
		}

	}


}
