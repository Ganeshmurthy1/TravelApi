package com.tayyarah.flight.controller;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.admin.analytics.lookbook.dao.LookBookDao;
import com.tayyarah.admin.analytics.lookbook.entity.FetchIpAddress;
import com.tayyarah.admin.analytics.lookbook.entity.FlightLook;
import com.tayyarah.admin.analytics.lookbook.entity.FlightLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPHistory;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPStatus;
import com.tayyarah.api.flight.tbo.model.TboFlightSearchResponse;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.exception.RestError;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.APIProviderCommonConstant;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.flight.cache.entity.FlightSearchCache;
import com.tayyarah.flight.dao.AirlineDAO;
import com.tayyarah.flight.dao.AirportDAO;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightApiSearchResponseTemp;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.BluestarSearchData;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.FlightsearchList;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.model.MulticityFlightSearch;
import com.tayyarah.flight.model.Passenger;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.model.UAPISearchFlightKeyMap;
import com.tayyarah.flight.service.db.AirlineService;
import com.tayyarah.flight.service.db.AirportService;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.FlightSearchExecutorServiceTaskHelper;
import com.tayyarah.flight.util.FlightSearchUtil;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.bluestar.BluestarGetFlightAvailabilityResponseParser;
import com.tayyarah.flight.util.api.tbo.TboResponseParser;
import com.tayyarah.flight.util.api.tbo.TboCommonUtil;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;

/*http://localhost:8080/LintasTravelAPI/flight/search?app_key=fgdf&triptype=M&origin=LAS-BNG-mUM&
 destination=LAX-lon-ker&arvlDate=20150813&depDate=20150813-20150813-20150813
 &adult=1&kid=0&infant=0&cabinClass=Economy&currency=USD&trips=3&Content-Type=application/json
 */

@RestController
@RequestMapping("/flight")
public class FlightSearchController {
	private static FlightWebServiceEndPointValidator flightWebServiceEndPointValidator = new FlightWebServiceEndPointValidator();
	static final Logger logger = Logger.getLogger(FlightSearchController.class);
	public static TypeBaseAirSegment typeBaseAirSegment;

	@Autowired
	MoneyExchangeDao moneydao;
	@Autowired
	FlightTempAirSegmentDAO flightTempAirSegmentDAO;
	@Autowired
	CompanyDao companyDao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@SuppressWarnings("rawtypes")
	@Autowired
	LookBookDao lookBookDao;
	@Autowired
	AirportService airportService;
	@Autowired
	AirlineService airlineService;
	private long startTime;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search", method = RequestMethod.GET, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody
	SearchFlightResponse search(
			@RequestParam(value = "airline", defaultValue = "All") String airline,
			@RequestParam(value = "trips", defaultValue = "1") String trips,
			@RequestParam(value = "triptype") String triptype,
			@RequestParam(value = "origin") String origin,
			@RequestParam(value = "destination") String destination,
			@RequestParam(value = "depDate") String depDate,
			@RequestParam(value = "arvlDate", defaultValue = "20150731") String arvlDate,
			@RequestParam(value = "adult") String adult,
			@RequestParam(value = "kid", defaultValue = "0") String kid,
			@RequestParam(value = "infant", defaultValue = "0") String infant,
			@RequestParam(value = "cabinClass") String cabinClass,
			@RequestParam(value = "currency") String currency,
			@RequestParam(value = "app_key") String app_key,
			@RequestParam(value = "isDynamicMarkup", defaultValue = "false") boolean isDynamicMarkup,			
			@RequestParam(value = "isCacheData", defaultValue = "false") boolean isCacheData,
			@RequestParam(value = "isDomestic", defaultValue = "false") boolean isDomestic,
			@RequestParam(value = "isSpecialSearch", defaultValue = "false") boolean isSpecialSearch,
			@RequestParam(value = "markupAmount", defaultValue = "0.0") String markupAmount,
			@RequestParam(value = "searchkey") String searchkey,
			@RequestParam(value = "isCacheDataInDb", defaultValue = "false") boolean isCacheDataInDb,
			HttpServletRequest request,
			HttpServletResponse response) {

		startTime = System.currentTimeMillis();
		ResponseHeader.setResponse(response);// Setting response header
		Timestamp currentDate = new Timestamp(new Date().getTime());

		logger.info("triptype :" + triptype + "  ,origin :" + origin
				+ "  ,destination:" + destination + "  ,depDate:" + depDate
				+ "  ,adult:" + adult + "  ,kid:" + kid + "  ,infant:" + infant
				+ "  ,cabinClass:" + cabinClass + "  ,currency:" + currency);

		if(app_key!=null && app_key.equalsIgnoreCase(""))
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.NOTFOUND_APPKEY);
		}
		flightWebServiceEndPointValidator.searchValidator(triptype, origin,
				destination, depDate, adult, kid, infant, trips, cabinClass,
				currency, app_key,markupAmount);
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);

		/*String NewAPP_Key = AppControllerUtil.getDecryptedAppKey(companyDao,app_key);
		String companyId = "-1";
		String configId = "-1";
		configId = NewAPP_Key.substring(0, NewAPP_Key.indexOf("-"));
		companyId = NewAPP_Key.substring(NewAPP_Key.indexOf("-") + 1);
		CompanyConfig appKeyVo.getCompanyConfig() = new CompanyConfig();
		try {
			appKeyVo.getCompanyConfig() = companyConfigDAO.getCompanyConfigByConfigId(Integer.parseInt(configId));
		} catch (NumberFormatException e2) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		} catch (Exception e2) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}*/
		String  baseCurrency = "invalid";
		String  bookingCurrency = currency;
		Map<String, Double> currencyrate = null;
		try {
			baseCurrency = companyDao.getCompanyCurrencyCode(appKeyVo.getCompanyId());
			currencyrate = moneydao.getCurrencyRate(bookingCurrency, baseCurrency);
		} catch (Exception e1) {			
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALIDCOMPANY_CURRENCY);
		}	
		Double currencyValue;
		if(currencyrate!=null)
			currencyValue = currencyrate.get("value");
		else
			currencyValue = 0.0;

		BigDecimal baseToBookingExchangeRate = new BigDecimal(currencyValue);
		FlightsearchList oneways = new FlightsearchList();
		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();
		Flightsearch flightsearch = new Flightsearch();
		MulticityFlightSearch MFS = new MulticityFlightSearch();
		String multiOrigin = "";
		String multiDest = "";
		String multiDepDate = "";
		String multiArvDate = "";
		if (triptype != null && triptype.equalsIgnoreCase("O")) {
			flightsearch = new Flightsearch(trips, origin, destination,
					FlightWebServiceEndPointValidator.getDateformat(depDate),
					Integer.parseInt(adult), Integer.parseInt(kid),
					Integer.parseInt(infant), cabinClass, currency, triptype,
					"", app_key, MFS, false, airline, false,baseCurrency,bookingCurrency,baseToBookingExchangeRate,isSpecialSearch,isDomestic,isDynamicMarkup,isCacheData,isCacheDataInDb);

			oneways.getFlightsearch().add(flightsearch);
		} else if (triptype != null && triptype.equalsIgnoreCase("R")) {
			flightWebServiceEndPointValidator
			.validateDate(arvlDate, "arvlDate");
			flightsearch = new Flightsearch(trips, origin, destination,
					FlightWebServiceEndPointValidator.getDateformat(depDate),
					Integer.parseInt(adult), Integer.parseInt(kid),
					Integer.parseInt(infant), cabinClass, currency, triptype,
					FlightWebServiceEndPointValidator.getDateformat(arvlDate),
					app_key, MFS, false, airline, false,baseCurrency,bookingCurrency,baseToBookingExchangeRate,isSpecialSearch,isDomestic,isDynamicMarkup,isCacheData,isCacheDataInDb);

			oneways.getFlightsearch().add(flightsearch);
		}
		else if (triptype != null && triptype.equalsIgnoreCase("SR")) {

			flightWebServiceEndPointValidator
			.validateDate(arvlDate, "arvlDate");
			flightsearch = new Flightsearch(trips, origin, destination,
					FlightWebServiceEndPointValidator.getDateformat(depDate),
					Integer.parseInt(adult), Integer.parseInt(kid),
					Integer.parseInt(infant), cabinClass, currency, triptype,
					FlightWebServiceEndPointValidator.getDateformat(arvlDate),
					app_key, MFS, false, airline, false,baseCurrency,bookingCurrency,baseToBookingExchangeRate,isSpecialSearch,isDomestic,isDynamicMarkup,isCacheData,isCacheDataInDb);

			oneways.getFlightsearch().add(flightsearch);
		}
		else if (triptype != null && triptype.equalsIgnoreCase("AD")) {
			flightsearch = new Flightsearch(trips, origin, destination,
					FlightWebServiceEndPointValidator.getDateformat(depDate),
					Integer.parseInt(adult), Integer.parseInt(kid),
					Integer.parseInt(infant), cabinClass, currency, triptype,
					"", app_key, MFS, false, airline, false,baseCurrency,bookingCurrency,baseToBookingExchangeRate,isSpecialSearch,isDomestic,isDynamicMarkup,isCacheData,isCacheDataInDb);

			oneways.getFlightsearch().add(flightsearch);
		}
		else if (triptype != null && triptype.equalsIgnoreCase("M")) {
			String Origintemp = origin;
			String Desttemp = destination;
			String depDatetemp = depDate;
			if (Origintemp.length() != Desttemp.length()) {
				throw new FlightException(ErrorCodeCustomerEnum.Exception,
						FlightErrorMessages.EQUAL_S_D);
			} else if (Integer.parseInt(trips) > 1) {
				if (Integer.parseInt(trips) < 5) {
					int i = 1;
					while (Origintemp.length() > 3) {
						String tempOrigin;
						String tempDest;
						String tempdepDate;
						try {
							tempOrigin = Origintemp.substring(0,
									Origintemp.indexOf("-"));
							tempDest = Desttemp.substring(0,
									Desttemp.indexOf("-"));
							tempdepDate = depDatetemp.substring(0,
									depDatetemp.indexOf("-"));
							Origintemp = Origintemp.substring(tempOrigin
									.length() + 1);
							Desttemp = Desttemp
									.substring(tempDest.length() + 1);
							depDatetemp = depDatetemp.substring(tempdepDate
									.length() + 1);
							flightWebServiceEndPointValidator.validateDate(
									tempdepDate, "depDate");
						} catch (Exception e) {
							logger.error("Exception", e);
							throw new FlightException(
									ErrorCodeCustomerEnum.Exception,
									"Invalid input parameters for multitrip");
						}
						if (i == 1) {
							MFS.setOrigin1(tempOrigin);
							MFS.setDest1(tempDest);
							MFS.setDepartDate1(FlightWebServiceEndPointValidator
									.getDateformat(tempdepDate));
							multiOrigin = tempOrigin;
							multiDest = tempDest;
							multiDepDate = tempdepDate;
						} else if (i == 2) {
							MFS.setOrigin2(tempOrigin);
							MFS.setDest2(tempDest);
							MFS.setDepartDate2(FlightWebServiceEndPointValidator
									.getDateformat(tempdepDate));// multiDest=tempDest;multiArvDate=tempdepDate;
						} else if (i == 3) {
							MFS.setOrigin3(tempOrigin);
							MFS.setDest3(tempDest);
							MFS.setDepartDate3(FlightWebServiceEndPointValidator
									.getDateformat(tempdepDate)); // multiDest=tempDest;multiArvDate=tempdepDate;
						} else if (i == 4) {
							MFS.setOrigin4(tempOrigin);
							MFS.setDest4(tempDest);
							MFS.setDepartDate4(FlightWebServiceEndPointValidator
									.getDateformat(tempdepDate)); // multiDest=tempDest;multiArvDate=tempdepDate;
						}
						if (Origintemp.length() == 3
								|| (Origintemp.indexOf("-") == -1)) {
							flightWebServiceEndPointValidator.validateDate(
									depDatetemp, "depDate");
							if (i == 1) {
								MFS.setOrigin2(Origintemp);
								MFS.setDest2(Desttemp);
								MFS.setDepartDate2(FlightWebServiceEndPointValidator
										.getDateformat(depDatetemp));
								multiDest = Desttemp;
								multiArvDate = depDatetemp;
							} else if (i == 2) {
								MFS.setOrigin3(Origintemp);
								MFS.setDest3(Desttemp);
								MFS.setDepartDate3(FlightWebServiceEndPointValidator
										.getDateformat(depDatetemp));
								multiDest = Desttemp;
								multiArvDate = depDatetemp;
							} else if (i == 3) {
								MFS.setOrigin4(Origintemp);
								MFS.setDest4(Desttemp);
								MFS.setDepartDate4(FlightWebServiceEndPointValidator
										.getDateformat(depDatetemp));
								multiDest = Desttemp;
								multiArvDate = depDatetemp;
							}
						}
						i++;
					}
				} else {
					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							FlightErrorMessages.MAX_TRIPS);
				}
				flightsearch = new Flightsearch(trips, multiOrigin, multiDest,
						FlightWebServiceEndPointValidator
						.getDateformat(multiDepDate),
						Integer.parseInt(adult), Integer.parseInt(kid),
						Integer.parseInt(infant), cabinClass, currency,
						triptype,
						FlightWebServiceEndPointValidator
						.getDateformat(multiArvDate), app_key, MFS,
						false, airline, false,baseCurrency,bookingCurrency,baseToBookingExchangeRate,isSpecialSearch,isDomestic,isDynamicMarkup,isCacheData,isCacheDataInDb);
			} else {
				throw new FlightException(ErrorCodeCustomerEnum.Exception,
						FlightErrorMessages.MULTI_TRIPS);
			}
		} else {
			throw new FlightException(ErrorCodeCustomerEnum.Exception,
					FlightErrorMessages.INVALID_TRIPTYPE);
		}

		FlightLook flightLook = new FlightLook();
		String ip=null;
		if(!flightsearch.isCacheDataInDb())
		{
			// below start code for look book
			LookBookCustomerIPStatus ipStatus = new LookBookCustomerIPStatus();
			LookBookCustomerIPHistory ipStatusHistory = new LookBookCustomerIPHistory();
			flightLook.setAppkey(app_key);
			flightLook.setSearchQueryString(request.getQueryString());

			try{
				ip = FetchIpAddress.getClientIpAddress(request);
				ipStatus = lookBookDao.CheckAndFetchIpStatus(ip);
				ipStatusHistory = lookBookDao.CheckAndfetchIpHistory(ip);
			}
			catch (Exception e) {
				if(ip!=null)
					ip=new String("");
			}
			if(ipStatus!=null && ipStatus.getId()>0){
				if( ipStatus.isBlockStatus() ){
					throw new FlightException(ErrorCodeCustomerEnum.LimitExceedException,ErrorMessages.USEREXCEEDSSEARCHLIMIT); 
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
		}

		Map<String, String> airlineNameMap;
		ArrayList<Map<String, String>> airportMapList = new ArrayList<Map<String, String>>();
		try {
			airportMapList = airportService.getAirportMap();
			airportMapList = airportService.getAirportMap();
			airlineNameMap = airlineService.getAirlineNameMap();
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,
					FlightErrorMessages.NO_FLIGHT);
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,
					FlightErrorMessages.NO_FLIGHT);
		}


		FlightDataBaseServices flightDataBaseServices = new FlightDataBaseServices();		
		try {
			flightDataBaseServices.isInternational(flightsearch, airportService.getAllAirportMap(),companyDao);
		} catch (Exception e2) {
			logger.error("Error setting isInternational");
			e2.printStackTrace();
		}		
		Map<String,List<FlightMarkUpConfig>> markupMap = new HashMap<String,List<FlightMarkUpConfig>>();
		try {
			markupMap = companyDao.getFlightMarkUpConfigMapByCompanyId(appKeyVo,  markupMap);
		} catch (Exception e1) {
			logger.error("getFlightMarkUpConfigMapByCompanyId Exception", e1);
		}
		MarkupCommissionDetails markupCommissionDetails = new MarkupCommissionDetails();
		try {
			markupCommissionDetails = companyDao.getFlightMarkupCommissionDetailsByCompanyId(appKeyVo, markupCommissionDetails);
		} catch (Exception e1) {
			logger.error("getFlightMarkupCommissionDetailsByCompanyId Exception", e1);
		}	

		/*ObjectMapper mapper = new ObjectMapper();
		String markuptext;
		String commissiontext;
		try {
			markuptext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(markupMap);
			commissiontext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(markupCommissionDetails);

		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		boolean  continueNormalSearch=true;
		if(isCacheData){
			try{
				TboFlightSearchResponse tboFlightSearchResponse = null;
				CurrencyConversionMap currencyConversionMap = TboCommonUtil.buldCurrencyConversionMap(flightsearch,moneydao);
				List<SearchFlightResponse> flightResponses = new ArrayList<SearchFlightResponse>();
				SearchFlightResponse savedsearchFlightResponse = new SearchFlightResponse();
				FlightSearchCache flightSearchCache = null;
				if(flightsearch.getTripType().equalsIgnoreCase("O"))
					flightSearchCache = flightTempAirSegmentDAO.getApiSearchCacheResponse(flightsearch);

				if(flightSearchCache != null){
					byte[] flightSearchByte = flightSearchCache.getSearchData();
					tboFlightSearchResponse =  (TboFlightSearchResponse) FlightDataBaseServices.convertByteArrayToObject(flightSearchByte);
					if(tboFlightSearchResponse!=null && tboFlightSearchResponse.getResponse()!=null && tboFlightSearchResponse.getResponse().getError()==null)
					{

						// TBO Oneway Parser
						if(flightsearch.getTripType().equalsIgnoreCase("O") && !flightsearch.isDomestic())
							savedsearchFlightResponse = TboResponseParser.parseResponseOneway(tboFlightSearchResponse,markupMap,flightsearch,airlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

						// TBO Round Trip Parser
						if(flightsearch.getTripType().equalsIgnoreCase("R"))
							savedsearchFlightResponse= TboResponseParser.parseResponseRoundTrip(tboFlightSearchResponse,markupMap,flightsearch,airlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

						// TBO Oneway Parser for Domestic
						if((flightsearch.getTripType().equalsIgnoreCase("O") && flightsearch.isDomestic()) || flightsearch.isSpecialSearch())
							savedsearchFlightResponse= TboResponseParser.parseResponseOnewayDomestic(tboFlightSearchResponse,markupMap,flightsearch,airlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

						flightResponses.add(savedsearchFlightResponse);				

						UAPISearchFlightKeyMap uapiSearchFlightKeyMap1 = new UAPISearchFlightKeyMap();

						if (flightResponses != null && flightResponses.size() > 0) {
							for(SearchFlightResponse searchFlightResponse2: flightResponses)
							{
								if(searchFlightResponse2!=null && searchFlightResponse2.getFareFlightSegment()!=null)
								{
									searchFlightResponse.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
									UAPISearchFlightKeyMap uapiSearchFlightKeyMap2 = searchFlightResponse2.getUapiSearchFlightKeyMap();

									if(uapiSearchFlightKeyMap2.getFareRulesMap()!=null)
										uapiSearchFlightKeyMap1.getFareRulesMap().putAll(uapiSearchFlightKeyMap2.getFareRulesMap());
									if(uapiSearchFlightKeyMap2.getFareFlightSegmentMap()!=null)
										uapiSearchFlightKeyMap1.getFareFlightSegmentMap().putAll(uapiSearchFlightKeyMap2.getFareFlightSegmentMap());
									if(uapiSearchFlightKeyMap2.getFlightSegmentstMap()!=null)
										uapiSearchFlightKeyMap1.getFlightSegmentstMap().putAll(uapiSearchFlightKeyMap2.getFlightSegmentstMap());
									if(uapiSearchFlightKeyMap2.getFaredetailMap()!=null)
										uapiSearchFlightKeyMap1.getFaredetailMap().putAll(uapiSearchFlightKeyMap2.getFaredetailMap());
									if(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap()!=null)
										uapiSearchFlightKeyMap1.getUapiAirSegmentListMap().putAll(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap());
									if(uapiSearchFlightKeyMap2.getExchangeRate()!=null)
										uapiSearchFlightKeyMap1.setExchangeRate(uapiSearchFlightKeyMap2.getExchangeRate());
								}
							}

						}else {
							throw new FlightException(ErrorCodeCustomerEnum.Exception,
									FlightErrorMessages.NO_FLIGHT);
						}

						uapiSearchFlightKeyMap1.setFlightsearch(flightsearch);
						uapiSearchFlightKeyMap1.setFlightMarkUpConfiglistMap(markupMap);
						uapiSearchFlightKeyMap1.setMarkupCommissionDetails(markupCommissionDetails);
						List<Passenger> passengers = new ArrayList<Passenger>();
						FlightSearchUtil.buildPassengerList(flightsearch, passengers);                // buildPassengerList
						searchFlightResponse.setPassenger(passengers);
						searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap1);
						searchFlightResponse.setTransactionKey(flightsearch.getTransactionKey());
						searchFlightResponse.setDeptDate(flightsearch.getDepDate());
						searchFlightResponse.setArvlDate(flightsearch.getArvlDate());
						searchFlightResponse.setDest(flightsearch.getDestination());
						searchFlightResponse.setOri(flightsearch.getOrigin());
						searchFlightResponse.setType(flightsearch.getTripType());
						searchFlightResponse.setCountry(APIProviderCommonConstant.NO_DATA);
						searchFlightResponse.setCabin(flightsearch.getCabinClass());
						searchFlightResponse.setCache(false);
						searchFlightResponse.setBaseCurrency(baseCurrency);
						searchFlightResponse.setBookedCurrency(bookingCurrency);
						searchFlightResponse.setBaseToBookingExchangeRate(baseToBookingExchangeRate);
						searchFlightResponse.setSearchKey(flightsearch.getSearchKey());
						flightDataBaseServices.UpdateSearchFlightKeyMap(
								searchFlightResponse.getUapiSearchFlightKeyMap(),
								flightsearch.getTransactionKey(), flightsearch.getSearchKey(), flightTempAirSegmentDAO);
						flightDataBaseServices.updateKeys(flightsearch.getTransactionKey(), flightsearch.getSearchKey(),
								flightTempAirSegmentDAO);
						continueNormalSearch=false;
					}

				}
				else{
					continueNormalSearch=true;
				}
			}catch(Exception e){
				logger.error("Exception from cache search data fetch ", e);
			}
		}		
		if(continueNormalSearch && isDynamicMarkup && !searchkey.equalsIgnoreCase("")){
			Map<String,List<FlightMarkUpConfig>> dynamicmarkupMap = new HashMap<String,List<FlightMarkUpConfig>>();
			if(isDynamicMarkup){
				List<FlightMarkUpConfig> markupList= new ArrayList<FlightMarkUpConfig>();
				FlightSearchUtil.addDynamicMarkup(appKeyVo, markupList, markupAmount);
				dynamicmarkupMap.put(String.valueOf(appKeyVo.getCompanyId()), markupList);
			}
			try{
				List<FlightApiSearchResponseTemp>  flightApiSearchResponseTemplist = flightTempAirSegmentDAO.GetApiSearchResponse(searchkey);
				if(flightApiSearchResponseTemplist.size() > 0){
					if(isSpecialSearch&&(triptype.equals("R"))){
						List<Flightsearch> flightsearchs = new ArrayList<>();
						List<SearchFlightResponse> flightResponses = new ArrayList<SearchFlightResponse>();
						TboFlightSearchResponse tboFlightSearchResponse = null;
						BluestarSearchData bluestarSearchData = null;
						SearchFlightResponse savedsearchFlightResponse = new SearchFlightResponse();
						CurrencyConversionMap currencyConversionMap = TboCommonUtil.buldCurrencyConversionMap(flightsearch,moneydao);
						for (FlightApiSearchResponseTemp apiSearchResponseTemp : flightApiSearchResponseTemplist) {
							byte[] flightSearchrequestByte = apiSearchResponseTemp.getSearchRequest();
							Flightsearch flightSearch =  (Flightsearch) FlightDataBaseServices.convertByteArrayToObject(flightSearchrequestByte);
							flightsearchs.add(flightSearch);
							byte[] flightSearchResponeInner = apiSearchResponseTemp.getSearchResponse();
							if(flightSearchResponeInner!=null)
							{
								if(apiSearchResponseTemp.getApiprovider().equalsIgnoreCase("TBO")){
									tboFlightSearchResponse = (TboFlightSearchResponse) FlightDataBaseServices.convertByteArrayToObject(flightSearchResponeInner);								

									// TBO Oneway Parser for Domestic
									if((flightSearch.getTripType().equalsIgnoreCase("O") && flightSearch.isDomestic()) || flightSearch.isSpecialSearch())
										savedsearchFlightResponse = TboResponseParser.parseResponseOnewayDomestic(tboFlightSearchResponse,dynamicmarkupMap,flightSearch,airlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

								}else if(apiSearchResponseTemp.getApiprovider().equalsIgnoreCase("BlueStar")){
									bluestarSearchData = (BluestarSearchData) FlightDataBaseServices.convertByteArrayToObject(flightSearchResponeInner);
									if (flightsearch.getTripType().equalsIgnoreCase("O")|| flightSearch.isSpecialSearch()) {
										savedsearchFlightResponse = BluestarGetFlightAvailabilityResponseParser
												.parseResponseOneway(bluestarSearchData, dynamicmarkupMap,
														flightsearch, airlineNameMap, airportMapList,
														moneydao);
									} else {
										savedsearchFlightResponse = BluestarGetFlightAvailabilityResponseParser
												.parseResponseRoundTrip(bluestarSearchData, dynamicmarkupMap,
														flightsearch, airlineNameMap, airportMapList,
														moneydao);
									}

								}
								else{
									tboFlightSearchResponse = (TboFlightSearchResponse) FlightDataBaseServices.convertByteArrayToObject(flightSearchResponeInner);
									// TBO Oneway Parser for Domestic
									if(tboFlightSearchResponse!=null && (flightSearch.getTripType().equalsIgnoreCase("O") && flightSearch.isDomestic()) || flightSearch.isSpecialSearch())
										savedsearchFlightResponse = TboResponseParser.parseResponseOnewayDomestic(tboFlightSearchResponse,dynamicmarkupMap,flightSearch,airlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

								}
								flightResponses.add(savedsearchFlightResponse);							
							}
						}

						UAPISearchFlightKeyMap uapiSearchFlightKeyMap1 = new UAPISearchFlightKeyMap();

						if (flightResponses != null && flightResponses.size() > 0) {
							for(SearchFlightResponse searchFlightResponse2: flightResponses)
							{
								if(searchFlightResponse2!=null && searchFlightResponse2.getFareFlightSegment()!=null)
								{
									searchFlightResponse.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
									UAPISearchFlightKeyMap uapiSearchFlightKeyMap2 = searchFlightResponse2.getUapiSearchFlightKeyMap();

									if(uapiSearchFlightKeyMap2.getFareRulesMap()!=null)
										uapiSearchFlightKeyMap1.getFareRulesMap().putAll(uapiSearchFlightKeyMap2.getFareRulesMap());
									if(uapiSearchFlightKeyMap2.getFareFlightSegmentMap()!=null)
										uapiSearchFlightKeyMap1.getFareFlightSegmentMap().putAll(uapiSearchFlightKeyMap2.getFareFlightSegmentMap());
									if(uapiSearchFlightKeyMap2.getFlightSegmentstMap()!=null)
										uapiSearchFlightKeyMap1.getFlightSegmentstMap().putAll(uapiSearchFlightKeyMap2.getFlightSegmentstMap());
									if(uapiSearchFlightKeyMap2.getFaredetailMap()!=null)
										uapiSearchFlightKeyMap1.getFaredetailMap().putAll(uapiSearchFlightKeyMap2.getFaredetailMap());
									if(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap()!=null)
										uapiSearchFlightKeyMap1.getUapiAirSegmentListMap().putAll(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap());
									if(uapiSearchFlightKeyMap2.getExchangeRate()!=null)
										uapiSearchFlightKeyMap1.setExchangeRate(uapiSearchFlightKeyMap2.getExchangeRate());
								}
							}

						}else {
							throw new FlightException(ErrorCodeCustomerEnum.Exception,
									FlightErrorMessages.NO_FLIGHT);
						}
						Flightsearch flightSearch = flightsearchs.get(0);
						uapiSearchFlightKeyMap1.setFlightsearch(flightsearch);
						uapiSearchFlightKeyMap1.setFlightMarkUpConfiglistMap(dynamicmarkupMap);
						uapiSearchFlightKeyMap1.setMarkupCommissionDetails(markupCommissionDetails);
						List<Passenger> passengers = new ArrayList<Passenger>();
						FlightSearchUtil.buildPassengerList(flightsearch, passengers);                // buildPassengerList
						searchFlightResponse.setPassenger(passengers);
						searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap1);
						searchFlightResponse.setTransactionKey(flightSearch.getTransactionKey());
						searchFlightResponse.setDeptDate(flightsearch.getDepDate());
						searchFlightResponse.setArvlDate(flightsearch.getArvlDate());
						searchFlightResponse.setDest(flightsearch.getDestination());
						searchFlightResponse.setOri(flightsearch.getOrigin());
						searchFlightResponse.setType(flightsearch.getTripType());
						searchFlightResponse.setCountry(APIProviderCommonConstant.NO_DATA);
						searchFlightResponse.setCabin(flightsearch.getCabinClass());
						searchFlightResponse.setCache(false);
						searchFlightResponse.setBaseCurrency(baseCurrency);
						searchFlightResponse.setBookedCurrency(bookingCurrency);
						searchFlightResponse.setBaseToBookingExchangeRate(baseToBookingExchangeRate);
						searchFlightResponse.setSearchKey(flightSearch.getSearchKey());
						flightDataBaseServices.UpdateSearchFlightKeyMap(
								searchFlightResponse.getUapiSearchFlightKeyMap(),
								flightSearch.getTransactionKey(), flightSearch.getSearchKey(), flightTempAirSegmentDAO);
						flightDataBaseServices.updateKeys(flightSearch.getTransactionKey(), flightSearch.getSearchKey(),
								flightTempAirSegmentDAO);

					}

					else {
						List<Flightsearch> flightsearchs = new ArrayList<>();
						SearchFlightResponse savedsearchFlightResponse = new SearchFlightResponse();
						BluestarSearchData bluestarSearchData=null;
						List<SearchFlightResponse> flightResponses = new ArrayList<SearchFlightResponse>();
						TboFlightSearchResponse tboFlightSearchResponse = null;
						CurrencyConversionMap currencyConversionMap = TboCommonUtil.buldCurrencyConversionMap(flightsearch,moneydao);
						for (FlightApiSearchResponseTemp apiSearchResponseTemp : flightApiSearchResponseTemplist) {

							/*---- Created by Manish ----*/
							byte[] flightSearchrequestByte = apiSearchResponseTemp.getSearchRequest();
							Flightsearch flightSearch =  (Flightsearch) FlightDataBaseServices.convertByteArrayToObject(flightSearchrequestByte);
							flightsearchs.add(flightSearch);
							byte[] fsr = apiSearchResponseTemp.getSearchResponse();

							if(apiSearchResponseTemp.getApiprovider().equalsIgnoreCase("TBO")){
								tboFlightSearchResponse = (TboFlightSearchResponse) FlightDataBaseServices.convertByteArrayToObject(fsr);

								// TBO Oneway Parser
								if(flightSearch.getTripType().equalsIgnoreCase("O") && !flightSearch.isDomestic())
									savedsearchFlightResponse = TboResponseParser.parseResponseOneway(tboFlightSearchResponse,dynamicmarkupMap,flightSearch,airlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

								// TBO Round Trip Parser
								if(flightSearch.getTripType().equalsIgnoreCase("R"))
									savedsearchFlightResponse= TboResponseParser.parseResponseRoundTrip(tboFlightSearchResponse,dynamicmarkupMap,flightSearch,airlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

								// TBO Oneway Parser for Domestic
								if((flightSearch.getTripType().equalsIgnoreCase("O") && flightSearch.isDomestic()) || flightSearch.isSpecialSearch())
									savedsearchFlightResponse= TboResponseParser.parseResponseOnewayDomestic(tboFlightSearchResponse,dynamicmarkupMap,flightSearch,airlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);
							}
							else if(apiSearchResponseTemp.getApiprovider().equalsIgnoreCase("BlueStar")){
								bluestarSearchData = (BluestarSearchData) FlightDataBaseServices.convertByteArrayToObject(fsr);
								if (flightsearch.getTripType().equalsIgnoreCase("O")) {
									savedsearchFlightResponse = BluestarGetFlightAvailabilityResponseParser
											.parseResponseOneway(bluestarSearchData, dynamicmarkupMap,
													flightsearch, airlineNameMap, airportMapList,
													moneydao);
								} else {
									savedsearchFlightResponse = BluestarGetFlightAvailabilityResponseParser
											.parseResponseRoundTrip(bluestarSearchData, dynamicmarkupMap,
													flightsearch, airlineNameMap, airportMapList,
													moneydao);
								}

							}
							flightResponses.add(savedsearchFlightResponse);						
						}

						if (flightResponses != null && flightResponses.size() > 0) {
							UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
							for(SearchFlightResponse searchFlightResponse2: flightResponses)
							{
								if(searchFlightResponse2!=null && searchFlightResponse2.getFareFlightSegment()!=null)
								{								
									if((triptype.equals("M"))){
										searchFlightResponse.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
									}
									UAPISearchFlightKeyMap uapiSearchFlightKeyMap2 = searchFlightResponse2.getUapiSearchFlightKeyMap();
									if(uapiSearchFlightKeyMap2.getFareRulesMap()!=null)
										uapiSearchFlightKeyMap.getFareRulesMap().putAll(uapiSearchFlightKeyMap2.getFareRulesMap());
									if(uapiSearchFlightKeyMap2.getFareFlightSegmentMap()!=null)
										uapiSearchFlightKeyMap.getFareFlightSegmentMap().putAll(uapiSearchFlightKeyMap2.getFareFlightSegmentMap());
									if(uapiSearchFlightKeyMap2.getFlightSegmentstMap()!=null)
										uapiSearchFlightKeyMap.getFlightSegmentstMap().putAll(uapiSearchFlightKeyMap2.getFlightSegmentstMap());
									if(uapiSearchFlightKeyMap2.getFaredetailMap()!=null)
										uapiSearchFlightKeyMap.getFaredetailMap().putAll(uapiSearchFlightKeyMap2.getFaredetailMap());
									if(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap()!=null)
										uapiSearchFlightKeyMap.getUapiAirSegmentListMap().putAll(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap());
									if(uapiSearchFlightKeyMap2.getExchangeRate()!=null)
										uapiSearchFlightKeyMap.setExchangeRate(uapiSearchFlightKeyMap2.getExchangeRate());
									if(uapiSearchFlightKeyMap2.getAirlineList()!=null)
										uapiSearchFlightKeyMap.getAirlineList().addAll(uapiSearchFlightKeyMap2.getAirlineList());
								}
							}
							if(triptype.equals("O") || triptype.equals("R") || triptype.equals("SR")){
								for(SearchFlightResponse searchFlightResponse2: flightResponses)
								{
									searchFlightResponse.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
								}
							}

							Flightsearch flightSearch = flightsearchs.get(0);
							uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
							uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(dynamicmarkupMap);
							uapiSearchFlightKeyMap.setMarkupCommissionDetails(markupCommissionDetails);
							List<Passenger> passengers = new ArrayList<Passenger>();
							FlightSearchUtil.buildPassengerList(flightsearch, passengers);
							searchFlightResponse.setPassenger(passengers);
							searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
							searchFlightResponse.setTransactionKey(flightSearch.getTransactionKey());
							searchFlightResponse.setDeptDate(flightsearch.getDepDate());
							searchFlightResponse.setArvlDate(flightsearch.getArvlDate());
							searchFlightResponse.setDest(flightsearch.getDestination());
							searchFlightResponse.setOri(flightsearch.getOrigin());
							searchFlightResponse.setType(flightsearch.getTripType());
							searchFlightResponse.setCountry(APIProviderCommonConstant.NO_DATA);
							searchFlightResponse.setCabin(flightsearch.getCabinClass());
							searchFlightResponse.setCache(false);
							searchFlightResponse.setBaseCurrency(baseCurrency);
							searchFlightResponse.setBookedCurrency(bookingCurrency);
							searchFlightResponse.setBaseToBookingExchangeRate(baseToBookingExchangeRate);
							searchFlightResponse.setSearchKey(flightSearch.getSearchKey());
							flightDataBaseServices.UpdateSearchFlightKeyMap(
									searchFlightResponse.getUapiSearchFlightKeyMap(),
									flightSearch.getTransactionKey(), flightSearch.getSearchKey(), flightTempAirSegmentDAO);
							flightDataBaseServices.updateKeys(flightSearch.getTransactionKey(), flightSearch.getSearchKey(),
									flightTempAirSegmentDAO);
						} else {
							throw new FlightException(ErrorCodeCustomerEnum.Exception,
									FlightErrorMessages.NO_FLIGHT);
						}
					}
				}
				else{
					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							FlightErrorMessages.NO_FLIGHT);
				}
			}catch(Exception e){
				throw new FlightException(ErrorCodeCustomerEnum.Exception,
						ErrorMessages.INVALID_SEARCHKEY);
			}
		}else if(continueNormalSearch){
			String transactionKey = "TK" + new UID().toString();
			flightsearch.setTransactionKey(transactionKey);
			if(isSpecialSearch&&(triptype.equals("R"))){//for special search, only for roundtrip
				try {
					String searchKey = (new UID()).toString();				
					flightsearch.setSearchKey(searchKey);				

					//for Ongoing
					List<Flightsearch> flightsearchs = new ArrayList<Flightsearch>();
					Flightsearch flightsearchOnGOing = new Flightsearch("1", origin, destination,
							FlightWebServiceEndPointValidator.getDateformat(depDate),
							Integer.parseInt(adult), Integer.parseInt(kid),
							Integer.parseInt(infant), cabinClass, currency, "O",
							FlightWebServiceEndPointValidator.getDateformat(arvlDate),
							app_key, MFS, false, airline, false,baseCurrency,bookingCurrency,baseToBookingExchangeRate,isSpecialSearch,isDomestic,isDynamicMarkup,isCacheData,isCacheDataInDb);
					flightsearchOnGOing.setSearchKey(searchKey);
					flightsearchOnGOing.setTransactionKey(transactionKey);
					flightsearchOnGOing.setFlightType(flightsearch.getFlightType());
					flightsearchOnGOing.setIsInternational(flightsearch.isIsInternational());
					flightsearchOnGOing.setDomestic(flightsearch.isDomestic());
					flightsearchOnGOing.setCountry(flightsearch.getCountry());
					flightsearchs.add(flightsearchOnGOing);
					Flightsearch flightsearchReturns = new Flightsearch("1", destination,origin,
							FlightWebServiceEndPointValidator.getDateformat(arvlDate),
							Integer.parseInt(adult), Integer.parseInt(kid),
							Integer.parseInt(infant), cabinClass, currency, "O",
							FlightWebServiceEndPointValidator.getDateformat(depDate),
							app_key, MFS, false, airline, false,baseCurrency,bookingCurrency,baseToBookingExchangeRate,isSpecialSearch,isDomestic,isDynamicMarkup,isCacheData,isCacheDataInDb);
					flightsearchReturns.setSearchKey(searchKey);
					flightsearchReturns.setTransactionKey(transactionKey);
					flightsearchReturns.setFlightType(flightsearch.getFlightType());
					flightsearchReturns.setCountry(flightsearch.getCountry());
					flightsearchReturns.setIsInternational(flightsearch.isIsInternational());
					flightsearchReturns.setDomestic(flightsearch.isDomestic());
					flightsearchs.add(flightsearchReturns);

					List<SearchFlightResponse> flightResponsesonward = FlightSearchExecutorServiceTaskHelper
							.doSearchFlightService(flightsearch,
									markupMap, airlineNameMap,
									airportMapList,isDomestic,moneydao,flightTempAirSegmentDAO,companyDao,appKeyVo,companyConfigDAO);
					UAPISearchFlightKeyMap uapiSearchFlightKeyMap1 = new UAPISearchFlightKeyMap();
					if (flightResponsesonward != null && flightResponsesonward.size() > 0) {
						for(SearchFlightResponse searchFlightResponse2: flightResponsesonward)
						{
							if(searchFlightResponse2!=null && searchFlightResponse2.getFareFlightSegment()!=null)
							{
								searchFlightResponse.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
								UAPISearchFlightKeyMap uapiSearchFlightKeyMap2 = searchFlightResponse2.getUapiSearchFlightKeyMap();
								if(uapiSearchFlightKeyMap2.getFareRulesMap()!=null)
									uapiSearchFlightKeyMap1.getFareRulesMap().putAll(uapiSearchFlightKeyMap2.getFareRulesMap());
								if(uapiSearchFlightKeyMap2.getFareFlightSegmentMap()!=null)
									uapiSearchFlightKeyMap1.getFareFlightSegmentMap().putAll(uapiSearchFlightKeyMap2.getFareFlightSegmentMap());
								if(uapiSearchFlightKeyMap2.getFlightSegmentstMap()!=null)
									uapiSearchFlightKeyMap1.getFlightSegmentstMap().putAll(uapiSearchFlightKeyMap2.getFlightSegmentstMap());
								if(uapiSearchFlightKeyMap2.getFaredetailMap()!=null)
									uapiSearchFlightKeyMap1.getFaredetailMap().putAll(uapiSearchFlightKeyMap2.getFaredetailMap());
								if(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap()!=null)
									uapiSearchFlightKeyMap1.getUapiAirSegmentListMap().putAll(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap());
								if(uapiSearchFlightKeyMap2.getExchangeRate()!=null)
									uapiSearchFlightKeyMap1.setExchangeRate(uapiSearchFlightKeyMap2.getExchangeRate());
							}
						}

					}else {
						throw new FlightException(ErrorCodeCustomerEnum.Exception,
								FlightErrorMessages.NO_FLIGHT);
					}

					uapiSearchFlightKeyMap1.setFlightsearch(flightsearch);
					uapiSearchFlightKeyMap1.setFlightMarkUpConfiglistMap(markupMap);
					uapiSearchFlightKeyMap1.setMarkupCommissionDetails(markupCommissionDetails);
					List<Passenger> passengers = new ArrayList<Passenger>();
					FlightSearchUtil.buildPassengerList(flightsearch, passengers);
					searchFlightResponse.setPassenger(passengers);
					searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap1);
					searchFlightResponse.setTransactionKey(flightsearch.getTransactionKey());
					searchFlightResponse.setDeptDate(flightsearch.getDepDate());
					searchFlightResponse.setArvlDate(flightsearch.getArvlDate());
					searchFlightResponse.setDest(flightsearch.getDestination());
					searchFlightResponse.setOri(flightsearch.getOrigin());
					searchFlightResponse.setType(flightsearch.getTripType());
					searchFlightResponse.setCountry(APIProviderCommonConstant.NO_DATA);
					searchFlightResponse.setCabin(flightsearch.getCabinClass());
					searchFlightResponse.setCache(false);
					searchFlightResponse.setBaseCurrency(baseCurrency);
					searchFlightResponse.setBookedCurrency(bookingCurrency);
					searchFlightResponse.setBaseToBookingExchangeRate(baseToBookingExchangeRate);
					searchKey = flightsearch.getSearchKey();//taking the the key's of API
					transactionKey = flightsearch.getTransactionKey();//taking the the key's of API
					searchFlightResponse.setSearchKey(searchKey);
					flightDataBaseServices.storeSearchFlightKeyMap(
							searchFlightResponse.getUapiSearchFlightKeyMap(),
							transactionKey, searchKey, flightTempAirSegmentDAO);
					flightDataBaseServices.insertKeys(transactionKey, searchKey,
							flightTempAirSegmentDAO);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
				}

			}
			////////////////////////////end of special search
			else if(isSpecialSearch&&(triptype.equals("SR"))){

				try {
					String searchKey = (new UID()).toString();
					flightsearch.setSearchKey(searchKey);
					List<SearchFlightResponse> flightResponses = FlightSearchExecutorServiceTaskHelper
							.doSearchFlightService(flightsearch,
									markupMap, airlineNameMap,
									airportMapList,isDomestic,moneydao,flightTempAirSegmentDAO,companyDao,appKeyVo,companyConfigDAO);
					if (flightResponses != null && flightResponses.size() > 0) {
						UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
						for(SearchFlightResponse searchFlightResponse2: flightResponses)
						{
							if(searchFlightResponse2!=null && searchFlightResponse2.getFareFlightSegment()!=null)
							{							
								if((triptype.equals("M"))){
									searchFlightResponse.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
								}
								UAPISearchFlightKeyMap uapiSearchFlightKeyMap2 = searchFlightResponse2.getUapiSearchFlightKeyMap();
								if(uapiSearchFlightKeyMap2.getFareRulesMap()!=null)
									uapiSearchFlightKeyMap.getFareRulesMap().putAll(uapiSearchFlightKeyMap2.getFareRulesMap());
								if(uapiSearchFlightKeyMap2.getFareFlightSegmentMap()!=null)
									uapiSearchFlightKeyMap.getFareFlightSegmentMap().putAll(uapiSearchFlightKeyMap2.getFareFlightSegmentMap());
								if(uapiSearchFlightKeyMap2.getFlightSegmentstMap()!=null)
									uapiSearchFlightKeyMap.getFlightSegmentstMap().putAll(uapiSearchFlightKeyMap2.getFlightSegmentstMap());
								if(uapiSearchFlightKeyMap2.getFaredetailMap()!=null)
									uapiSearchFlightKeyMap.getFaredetailMap().putAll(uapiSearchFlightKeyMap2.getFaredetailMap());
								if(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap()!=null)
									uapiSearchFlightKeyMap.getUapiAirSegmentListMap().putAll(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap());
								if(uapiSearchFlightKeyMap2.getExchangeRate()!=null)
									uapiSearchFlightKeyMap.setExchangeRate(uapiSearchFlightKeyMap2.getExchangeRate());
								if(uapiSearchFlightKeyMap2.getAirlineList()!=null)
									uapiSearchFlightKeyMap.getAirlineList().addAll(uapiSearchFlightKeyMap2.getAirlineList());
							}
						}
						if(triptype.equals("O") || triptype.equals("R") || triptype.equals("SR")){
							for(SearchFlightResponse searchFlightResponse2: flightResponses)
							{
								searchFlightResponse.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
							}
						}
						uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
						uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
						uapiSearchFlightKeyMap.setMarkupCommissionDetails(markupCommissionDetails);
						List<Passenger> passengers = new ArrayList<Passenger>();
						FlightSearchUtil.buildPassengerList(flightsearch, passengers);
						searchFlightResponse.setPassenger(passengers);
						searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
						searchFlightResponse.setTransactionKey(flightsearch.getTransactionKey());
						searchFlightResponse.setDeptDate(flightsearch.getDepDate());
						searchFlightResponse.setArvlDate(flightsearch.getArvlDate());
						searchFlightResponse.setDest(flightsearch.getDestination());
						searchFlightResponse.setOri(flightsearch.getOrigin());
						searchFlightResponse.setType(flightsearch.getTripType());
						searchFlightResponse.setCountry(APIProviderCommonConstant.NO_DATA);
						searchFlightResponse.setCabin(flightsearch.getCabinClass());
						searchFlightResponse.setCache(false);
						searchFlightResponse.setBaseCurrency(baseCurrency);
						searchFlightResponse.setBookedCurrency(bookingCurrency);
						searchFlightResponse.setBaseToBookingExchangeRate(baseToBookingExchangeRate);
						searchKey = flightsearch.getSearchKey();//taking the the key's of API
						transactionKey = flightsearch.getTransactionKey();//taking the the key's of API
						searchFlightResponse.setSearchKey(searchKey);
						flightDataBaseServices.storeSearchFlightKeyMap(
								searchFlightResponse.getUapiSearchFlightKeyMap(),
								transactionKey, searchKey, flightTempAirSegmentDAO);
						flightDataBaseServices.insertKeys(transactionKey, searchKey,
								flightTempAirSegmentDAO);
					} else {
						throw new FlightException(ErrorCodeCustomerEnum.Exception,
								FlightErrorMessages.NO_FLIGHT);
					}
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							FlightErrorMessages.NO_FLIGHT);
				}

			}			
			else
			{
				try {
					String searchKey = (new UID()).toString();
					flightsearch.setSearchKey(searchKey);
					List<SearchFlightResponse> flightResponses = FlightSearchExecutorServiceTaskHelper
							.doSearchFlightService(flightsearch,
									markupMap, airlineNameMap,
									airportMapList,isDomestic,moneydao,flightTempAirSegmentDAO,companyDao,appKeyVo,companyConfigDAO);
					if(!flightsearch.isCacheDataInDb())
					{
						if (flightResponses != null && flightResponses.size() > 0) {
							UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
							for(SearchFlightResponse searchFlightResponse2: flightResponses)
							{
								if(searchFlightResponse2!=null && searchFlightResponse2.getFareFlightSegment()!=null)
								{							
									if((triptype.equals("M"))){
										searchFlightResponse.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
									}
									UAPISearchFlightKeyMap uapiSearchFlightKeyMap2 = searchFlightResponse2.getUapiSearchFlightKeyMap();
									if(uapiSearchFlightKeyMap2.getFareRulesMap()!=null)
										uapiSearchFlightKeyMap.getFareRulesMap().putAll(uapiSearchFlightKeyMap2.getFareRulesMap());
									if(uapiSearchFlightKeyMap2.getFareFlightSegmentMap()!=null)
										uapiSearchFlightKeyMap.getFareFlightSegmentMap().putAll(uapiSearchFlightKeyMap2.getFareFlightSegmentMap());
									if(uapiSearchFlightKeyMap2.getFlightSegmentstMap()!=null)
										uapiSearchFlightKeyMap.getFlightSegmentstMap().putAll(uapiSearchFlightKeyMap2.getFlightSegmentstMap());
									if(uapiSearchFlightKeyMap2.getFaredetailMap()!=null)
										uapiSearchFlightKeyMap.getFaredetailMap().putAll(uapiSearchFlightKeyMap2.getFaredetailMap());
									if(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap()!=null)
										uapiSearchFlightKeyMap.getUapiAirSegmentListMap().putAll(uapiSearchFlightKeyMap2.getUapiAirSegmentListMap());
									if(uapiSearchFlightKeyMap2.getExchangeRate()!=null)
										uapiSearchFlightKeyMap.setExchangeRate(uapiSearchFlightKeyMap2.getExchangeRate());
									if(uapiSearchFlightKeyMap2.getAirlineList()!=null)
										uapiSearchFlightKeyMap.getAirlineList().addAll(uapiSearchFlightKeyMap2.getAirlineList());
								}
							}
							if(triptype.equals("O") || triptype.equals("R") || triptype.equals("SR")){
								for(SearchFlightResponse searchFlightResponse2: flightResponses)
								{
									if(searchFlightResponse2!=null && searchFlightResponse2.getFareFlightSegment()!=null)
									{
										searchFlightResponse.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
									}
								}
							}
							uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
							uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
							uapiSearchFlightKeyMap.setMarkupCommissionDetails(markupCommissionDetails);
							List<Passenger> passengers = new ArrayList<Passenger>();
							FlightSearchUtil.buildPassengerList(flightsearch, passengers);
							searchFlightResponse.setPassenger(passengers);
							searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
							searchFlightResponse.setTransactionKey(flightsearch.getTransactionKey());
							searchFlightResponse.setDeptDate(flightsearch.getDepDate());
							searchFlightResponse.setArvlDate(flightsearch.getArvlDate());
							searchFlightResponse.setDest(flightsearch.getDestination());
							searchFlightResponse.setOri(flightsearch.getOrigin());
							searchFlightResponse.setType(flightsearch.getTripType());
							searchFlightResponse.setCountry(APIProviderCommonConstant.NO_DATA);
							searchFlightResponse.setCabin(flightsearch.getCabinClass());
							searchFlightResponse.setCache(false);
							searchFlightResponse.setBaseCurrency(baseCurrency);
							searchFlightResponse.setBookedCurrency(bookingCurrency);
							searchFlightResponse.setBaseToBookingExchangeRate(baseToBookingExchangeRate);
							searchKey = flightsearch.getSearchKey();//taking the the key's of API
							transactionKey = flightsearch.getTransactionKey();//taking the the key's of API
							searchFlightResponse.setSearchKey(searchKey);
							flightDataBaseServices.storeSearchFlightKeyMap(
									searchFlightResponse.getUapiSearchFlightKeyMap(),
									transactionKey, searchKey, flightTempAirSegmentDAO);
							flightDataBaseServices.insertKeys(transactionKey, searchKey,
									flightTempAirSegmentDAO);

						} else {
							throw new FlightException(ErrorCodeCustomerEnum.Exception,
									FlightErrorMessages.NO_FLIGHT);
						}
					}
					else {
						logger.error("Continue To fill Flight cache");
						return searchFlightResponse;
					}
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							FlightErrorMessages.NO_FLIGHT);
				}
			}
		}

		if(!flightsearch.isCacheDataInDb())
		{
			try {
				Company company = appKeyVo.getCompany();
				flightLook.setAppkey(app_key);
				flightLook.setIP(ip);
				flightLook.setTransactionId(flightsearch.getTransactionKey()!=null?flightsearch.getTransactionKey():"not avail");
				flightLook.setSearchKey(flightsearch.getSearchKey()!=null?flightsearch.getSearchKey():"search failed");
				flightLook.setSearchOnDateTime(new Timestamp(new Date().getTime()));
				flightLook.setSearchQueryString(request.getQueryString());
				//			new AppControllerUtil().getDecryptedAppKey(CDAO, NewAPP_Key);
				flightLook.setCompanyId(appKeyVo.getCompanyId());
				flightLook.setCompanyName(company.getCompanyname());
				flightLook.setConfigId(appKeyVo.getConfigId());

				lookBookDao.insertIntoTable(flightLook);

				FlightLookBook lookBook = new FlightLookBook(); 
				lookBook.setAppkey(app_key);
				lookBook=lookBookDao.CheckAndFetchFlightLookBookByAppKey(lookBook);
				if(lookBook!=null && lookBook.getId()>0){
					lookBookDao.updateIntoTable(lookBook, "search");
				}
				else{
					lookBook = new FlightLookBook(); 
					lookBook.setAppkey(app_key);
					lookBook.setCompanyId(appKeyVo.getCompanyId());
					lookBook.setConfigId(appKeyVo.getConfigId());
					lookBook.setCompanyName(company.getCompanyname());
					lookBook.setTotalBookedCount(0);
					lookBook.setTotalSearchCount(1);
					lookBookDao.insertIntoTable(lookBook);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		logger.info("Flight search took --- "+CommonUtil.getElapsedTime(startTime)+" seconds ... ############ ");
		return searchFlightResponse;
	}

	@ExceptionHandler(BaseException.class)
	public @ResponseBody
	RestError handleCustomException(BaseException ex,
			HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex
				.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}



}