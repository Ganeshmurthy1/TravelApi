package com.tayyarah.bus.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.tayyarah.bus.model.AvailableBuses;
import com.tayyarah.bus.model.BusSearchRequest;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.esmart.bus.util.ESmartBusSearchExecutorServiceTask;
import com.tayyarah.esmart.bus.util.EsmartBusConfig;
import com.tayyarah.bus.model.BusMarkUpConfig;



public class BusSearchExecutorServiceTaskHelper {
	static final Logger logger = Logger.getLogger(BusSearchExecutorServiceTaskHelper.class);
	public static List<AvailableBuses> busSearchService(BusSearchRequest busSearchRequest,String decryptedAppKey,Map<String,List<BusMarkUpConfig>> markupMap,MoneyExchangeDao moneydao,CompanyConfigDAO companyConfigDAO,String searchKey,CompanyDao CompanyDao){
		List<AvailableBuses>  availableBuses = new ArrayList<>();
		ExecutorService executorService = Executors.newFixedThreadPool(8);		
		List<Future<AvailableBuses>> futures = new ArrayList<Future<AvailableBuses>>();
		try{
			EsmartBusConfig  esmartBusConfig = EsmartBusConfig.GetEsmartBusConfig(decryptedAppKey);
			CurrencyConversionMap currencyConversionMap = BusCommonUtil.buildCurrencyConversionMap(busSearchRequest.getCurrency(),moneydao);
			Future<AvailableBuses> future = executorService.submit(new ESmartBusSearchExecutorServiceTask(esmartBusConfig, busSearchRequest, markupMap, currencyConversionMap, decryptedAppKey, companyConfigDAO, searchKey, CompanyDao));
			futures.add(future);
		
		}catch(Exception e){
			 throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
		}
		
		for(Future<AvailableBuses> future:futures){
			try {
				availableBuses.add(future.get());
			} catch (ExecutionException e)
			{
				throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
			}
			catch (InterruptedException  e) {
				throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
			}
		}
		
		return availableBuses;
	}
}
