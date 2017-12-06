package com.tayyarah.flight.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.apiconfig.model.LintasConfig;
import com.tayyarah.apiconfig.model.TayyarahConfig;
import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.SearchFlightResponse;


public class FlightSearchExecutorServiceTaskHelper
{
	static final Logger logger = Logger.getLogger(FlightSearchExecutorServiceTaskHelper.class);

	/*Oneway Domestic and International Round,Domestic FlightService*/
	public static List<SearchFlightResponse> doSearchFlightService(Flightsearch flightsearch,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Map<String, String> airlineNameMap,
			ArrayList<Map<String, String>> airportMapList,boolean isDomestic,MoneyExchangeDao moneydao,FlightTempAirSegmentDAO flightTempAirSegmentDAO,CompanyDao companyDao,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO) {

		List<SearchFlightResponse> searchFlightResponses=new ArrayList<SearchFlightResponse>();
		ExecutorService executorService =null;
		if(flightsearch!=null && flightsearch.getAirlineList()!=null && flightsearch.getAirlineList().size()>0)
		{
			executorService=Executors.newFixedThreadPool(flightsearch.getAirlineList().size()+8);
		}else{
			executorService=Executors.newFixedThreadPool(8);
		}

		List<Future<SearchFlightResponse>> futures=new ArrayList<Future<SearchFlightResponse>>();
		try {
			// Get All Config From DB
			TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
			TravelportConfig travelportConfig = TravelportConfig.GetTravelportConfig();
			BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
			TayyarahConfig tayyarahConfig = TayyarahConfig.GetTayyarahConfig();
			LintasConfig lintasConfig = LintasConfig.GetLintasConfig();

			if(travelportConfig.isEnabled())
			{
				logger.info("TravelPort ON");
				if(flightsearch!=null && flightsearch.getAirlineList()!=null && flightsearch.getAirlineList().size()>0)
				{
					Set<String> airlineList = flightsearch.getAirlineList();
					Future<SearchFlightResponse> future = null;
					for(String airline: airlineList)
					{						
						future = executorService.submit(new TravelportFlightSearchExecutorServiceTask(flightsearch,markupMap,airlineNameMap,airportMapList,isDomestic,airline,moneydao,travelportConfig));
						futures.add(future);
					}
				}
				else{
					Future<SearchFlightResponse> future = executorService.submit(new TravelportFlightSearchExecutorServiceTask(flightsearch,markupMap,airlineNameMap,airportMapList,isDomestic,null,moneydao,travelportConfig));
					futures.add(future);
				}
			}
			if(bluestarConfig.isEnabled()&&!flightsearch.getTripType().equalsIgnoreCase("M"))
			{
				logger.info("Bluestar ON");
				Future<SearchFlightResponse> future = executorService.submit(new BlueStarFlightSearchExecutorServiceTask(flightsearch,markupMap,airlineNameMap,airportMapList,moneydao,flightTempAirSegmentDAO,bluestarConfig,companyDao));
				futures.add(future);
			}
			if(tayyarahConfig.isEnabled()&&!flightsearch.getTripType().equalsIgnoreCase("M"))
			{
				logger.info("TAYYARAH_API_ENABLE ON");
				Future<SearchFlightResponse> future = executorService.submit(new TayyarahFlightSearchExecutorServiceTask(flightsearch,markupMap,airlineNameMap,airportMapList,moneydao,tayyarahConfig));
				futures.add(future);
			}
			if(lintasConfig.isEnabled()&&!flightsearch.getTripType().equalsIgnoreCase("M"))
			{
				logger.info("LINTAS_API_ENABLE ON");
				Future<SearchFlightResponse> future = executorService.submit(new LintasFlightSearchExecutorServiceTask(flightsearch,markupMap,airlineNameMap,airportMapList,moneydao,lintasConfig));
				futures.add(future);
			}
			if(tboconfig.isEnabled() &&!flightsearch.getTripType().equalsIgnoreCase("M") )
			{
				logger.info("TBO_API_ENABLE ON");
				CurrencyConversionMap currencyConversionMap = TboFlightCommonUtil.buldCurrencyConversionMap(flightsearch,moneydao);
				Future<SearchFlightResponse> future = executorService.submit(new TboFlightSearchExecutorServiceTask(flightsearch,markupMap,airlineNameMap,airportMapList,currencyConversionMap,flightTempAirSegmentDAO,tboconfig,appKeyVo,companyConfigDAO,companyDao));
				futures.add(future);
			}

		} catch (Exception e) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		}

		for(Future<SearchFlightResponse> future:futures){
			try {
				searchFlightResponses.add(future.get());
			} catch (ExecutionException e)
			{
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
			}
			catch (InterruptedException  e) {
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
			}
		}
		return searchFlightResponses;
	}

	/*Special Domestic FlightService*/
	public static List<SearchFlightResponse> doSearchFlightService(List<Flightsearch> flightsearchs,
			Map<String, List<FlightMarkUpConfig>> markupMap, Map<String, String> airlineNameMap,
			ArrayList<Map<String, String>> airportMapList, boolean isDomestic, MoneyExchangeDao moneydao,FlightTempAirSegmentDAO flightTempAirSegmentDAO,CompanyDao companyDao,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO) {
		List<SearchFlightResponse> searchFlightResponses=new ArrayList<SearchFlightResponse>();
		ExecutorService executorService =Executors.newFixedThreadPool(8);
		List<Future<SearchFlightResponse>> futures=new ArrayList<Future<SearchFlightResponse>>();
		try {
			if(flightsearchs!=null && flightsearchs.size()>0)
			{
				String app_key = AppControllerUtil.getDecryptedAppKey(companyDao,flightsearchs.get(0).getApp_key());

				// Get All Config From DB
				TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
				BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
				CurrencyConversionMap currencyConversionMap  = new CurrencyConversionMap();
				Future<SearchFlightResponse> future = null;
				int i = 0;
				for(Flightsearch flightsearch: flightsearchs)
				{
					logger.info("TBO_API_ENABLE ON");
					if(i==0)
					{
						currencyConversionMap = TboFlightCommonUtil.buldCurrencyConversionMap(flightsearch,moneydao);
					}
					if(tboconfig.isEnabled()&&!flightsearch.getTripType().equalsIgnoreCase("M"))
					{				
						if(flightsearch!=null && flightsearch.isSpecialSearch()&& flightsearch.getTripType().equals("O"))
						{							
							future = executorService.submit(new TboFlightSearchExecutorServiceTask(flightsearch,markupMap,airlineNameMap,airportMapList,currencyConversionMap,flightTempAirSegmentDAO,tboconfig,appKeyVo,companyConfigDAO,companyDao));
							futures.add(future);
						}
					}
					if(bluestarConfig.isEnabled()&&!flightsearch.getTripType().equalsIgnoreCase("M"))
					{						
						if(flightsearch!=null && flightsearch.isSpecialSearch()&& flightsearch.getTripType().equals("O"))
						{						
							future = executorService.submit(new BlueStarFlightSearchExecutorServiceTask(flightsearch,markupMap,airlineNameMap,airportMapList,moneydao,flightTempAirSegmentDAO,bluestarConfig,companyDao));
							futures.add(future);
						}
					}
					i++;
				}
			}
			for(Future<SearchFlightResponse> future:futures){
				try {
					searchFlightResponses.add(future.get());
				} catch (ExecutionException e)
				{
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
				}
				catch (InterruptedException  e) {
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
				}
			}
		} catch (Exception e) {
			logger.error("Exception",e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		}
		return searchFlightResponses;
	}
}
