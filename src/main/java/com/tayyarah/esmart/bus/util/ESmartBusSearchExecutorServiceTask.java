package com.tayyarah.esmart.bus.util;



import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.json.JsonException;

import org.apache.log4j.Logger;

import com.tayyarah.bus.model.AvailableBuses;
import com.tayyarah.bus.model.BusSearchRequest;
import com.tayyarah.bus.util.BusErrorMessages;
import com.tayyarah.bus.util.BusException;
import com.tayyarah.bus.util.ErrorCodeCustomerEnum;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.bus.model.BusMarkUpConfig;

public class ESmartBusSearchExecutorServiceTask implements Callable<AvailableBuses>{
	static final Logger logger = Logger.getLogger(ESmartBusSearchExecutorServiceTask.class);
    private BusSearchRequest busSearchRequest;
    private Map<String,List<BusMarkUpConfig>> markupMap;
    private CurrencyConversionMap currencyConversionMap = null;
    private EsmartBusConfig esmartBusConfig;
    private String decAppKey ;
    private CompanyConfigDAO companyConfigDAO;
    private String searchKey;
    private CompanyDao companyDAO;
    public ESmartBusSearchExecutorServiceTask(EsmartBusConfig esmartBusConfig,BusSearchRequest busSearchRequest,Map<String,List<BusMarkUpConfig>> markupMap,CurrencyConversionMap currencyConversionMap,String decAppKey,CompanyConfigDAO companyConfigDAO,String searchKey,CompanyDao companyDAO){
    	this.esmartBusConfig = esmartBusConfig;
    	this.busSearchRequest = busSearchRequest;
    	this.companyConfigDAO = companyConfigDAO;
    	this.currencyConversionMap = currencyConversionMap;
    	this.decAppKey = decAppKey;
    	this.esmartBusConfig = esmartBusConfig;
    	this.markupMap = markupMap;
    	this.searchKey = searchKey;
    	this.companyDAO = companyDAO;
    }
	@Override
	public AvailableBuses call() throws Exception {
		AvailableBuses availableBuses = null;
		try{
			availableBuses = EsmartServiceCall.getAvailableBusList(esmartBusConfig,busSearchRequest,markupMap,currencyConversionMap,decAppKey,companyConfigDAO,searchKey,companyDAO);
		}catch(JsonException e){
			 logger.error("ClassNotFoundException",e);
			 throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
		}catch(Exception e){
			 logger.error("ClassNotFoundException",e);
			 throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
		}
		return availableBuses;
	}
}
