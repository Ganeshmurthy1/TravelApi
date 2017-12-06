package com.tayyarah.bus.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.admin.analytics.lookbook.dao.LookBookDao;
import com.tayyarah.admin.analytics.lookbook.entity.BusLook;
import com.tayyarah.admin.analytics.lookbook.entity.BusLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.FetchIpAddress;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPHistory;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPStatus;
import com.tayyarah.bus.dao.BusCommonDao;
import com.tayyarah.bus.entity.BusSearchTemp;
import com.tayyarah.bus.exception.BusErrorMessages;
import com.tayyarah.bus.exception.BusException;
import com.tayyarah.bus.model.AvailableBus;
import com.tayyarah.bus.model.AvailableBuses;
import com.tayyarah.bus.model.BoardingPoint;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusMarkupCommissionDetails;
import com.tayyarah.bus.model.BusSearchRequest;
import com.tayyarah.bus.model.DroppingPoint;
import com.tayyarah.bus.model.TayyarahBusSearchMap;
import com.tayyarah.bus.util.BusBaseException;
import com.tayyarah.bus.util.BusCommonUtil;
import com.tayyarah.bus.util.BusParamValidator;
import com.tayyarah.bus.util.BusRestError;
import com.tayyarah.bus.util.BusSearchExecutorServiceTaskHelper;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;

@RestController
@RequestMapping("/bus/available")
public class BusAvailabilityController {
	static final Logger logger = Logger.getLogger(BusAvailabilityController.class);
	private static BusParamValidator  busParamValidator = new BusParamValidator();

	@Autowired
	CompanyDao companyDAO;
	@Autowired
	MoneyExchangeDao moneydao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@Autowired
	BusCommonDao busCommonDao;

	@SuppressWarnings("rawtypes")
	@Autowired
	LookBookDao lookBookDao;
	
	

	@RequestMapping(value = "/search", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody AvailableBuses getAvailableBuses(@RequestBody BusSearchRequest busSearchRequest,HttpServletResponse response,HttpServletRequest request){

		BusLook busLook=new BusLook();
		ResponseHeader.setResponse(response);
		// Check APP KEY
		if(busSearchRequest.getApp_key()!=null && busSearchRequest.getApp_key().equalsIgnoreCase(""))
		{
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NOTFOUND_APPKEY.getErrorMessage());
		}
		String decryptedAppKey;
		try {
			decryptedAppKey = busCommonDao.getDecryptedAppKey(companyDAO,busSearchRequest.getApp_key());
		} catch (SQLException e3) {
			throw new BusException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}
		String companyId = "-1";
		String configId = "-1";
		configId = decryptedAppKey.substring(0, decryptedAppKey.indexOf("-"));
		companyId = decryptedAppKey.substring(decryptedAppKey.indexOf("-") + 1);
		CompanyConfig companyConfig = new CompanyConfig();
		try {
			companyConfig = companyConfigDAO.getCompanyConfigByConfigId(Integer.parseInt(configId));
		} catch (NumberFormatException e2) {
			throw new BusException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		} catch (Exception e2) {
			throw new BusException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}

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
			if( ipStatus.isBlockStatus()){
				throw new BusException(ErrorCodeCustomerEnum.LimitExceedException,ErrorMessages.USEREXCEEDSSEARCHLIMIT); 
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
			if(companyConfig!=null && companyConfig.getCompanyConfigType()!=null ){
				if(companyConfig.getCompanyConfigType().isB2C() || companyConfig.getCompanyConfigType().isWhitelable()){
					ipStatus.setB2cFlag(true);
					ipStatus.setConfigType("B2C");
				}
				else if(companyConfig.getCompanyConfigType().isB2B()){
					ipStatus.setConfigType("B2B");
				}
				else if(companyConfig.getCompanyConfigType().isB2E()){
					ipStatus.setConfigType("B2E");
				}
				
				ipStatus.setCompanyName(companyConfig.getCompanyName());
				ipStatus.setConfigName(companyConfig.getConfigname());
			} 
			ipStatus.setBlockStatus(false);
			ipStatus.setIp(ip);
			ipStatus.setTotalBookedCount(0);
			ipStatus.setTotalSearchCount(1);
			ipStatus.setCompanyId(Integer.valueOf(companyId));
			ipStatus.setConfigId(Integer.valueOf(configId));
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
			if(companyConfig!=null && companyConfig.getCompanyConfigType()!=null ){
				if(companyConfig.getCompanyConfigType().isB2C() || companyConfig.getCompanyConfigType().isWhitelable()){
					ipStatusHistory.setB2cFlag(true);
					ipStatusHistory.setConfigType("B2C");
				}
				else if(companyConfig.getCompanyConfigType().isB2B()){
					ipStatusHistory.setConfigType("B2B");
				}
				else if(companyConfig.getCompanyConfigType().isB2E()){
					ipStatusHistory.setConfigType("B2E");
				}
				
				ipStatusHistory.setCompanyName(companyConfig.getCompanyName());
				ipStatusHistory.setConfigName(companyConfig.getConfigname());
			} 
			ipStatusHistory.setIp(ip);
			ipStatusHistory.setTotalBookedCount(0);
			ipStatusHistory.setTotalSearchCount(1);
			ipStatusHistory.setCompanyId(Integer.valueOf(companyId));
			ipStatusHistory.setConfigId(Integer.valueOf(configId));
			try{
				lookBookDao.insertIntoTable(ipStatusHistory);
			}
			catch (Exception e) {
			}
		}

		busParamValidator.searchValidator(busSearchRequest);

		AvailableBuses availableBuses = new AvailableBuses();		
		try{	
			Company company = companyDAO.getCompany(Integer.valueOf(companyId));
			TayyarahBusSearchMap tayyarahBusSearchMap = new TayyarahBusSearchMap();
			Map<String, AvailableBuses> availableBusesMap = new HashMap<String, AvailableBuses>();
			Map<String, List<BusMarkUpConfig>> markupMap = new HashMap<String,List<BusMarkUpConfig>>();
			Map<String, List<BoardingPoint>> boardingPointsMap = new HashMap<String, List<BoardingPoint>>();
			Map<String, List<DroppingPoint>> droppingPointMap = new HashMap<String, List<DroppingPoint>>();
			try {
				markupMap = companyDAO.getBusMarkUpMapByCompanyId(companyId, configId,  markupMap);
			} catch (Exception e1) {
				logger.error("getBusMarkUpMapByCompanyId Exception", e1);
			}
			BusMarkupCommissionDetails markupCommissionDetails = new BusMarkupCommissionDetails();
			try {
				markupCommissionDetails = companyDAO.getBusMarkupCommissionDetailsByCompanyId(companyId, configId, markupCommissionDetails);
			} catch (Exception e1) {
				logger.error("getFlightMarkupCommissionDetailsByCompanyId Exception", e1);
			}	

			ObjectMapper mapper = new ObjectMapper();
			String markuptext;
			String commissiontext;
			try {
				markuptext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(markupMap);
				commissiontext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(markupCommissionDetails);

			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(busSearchRequest.getIsDynamicMarkup() && !busSearchRequest.getSearchkey().equalsIgnoreCase("")){
				Map<String,List<BusMarkUpConfig>> dynamicmarkupMap = new HashMap<String,List<BusMarkUpConfig>>();				
				List<BusMarkUpConfig> markupList= new ArrayList<BusMarkUpConfig>();
				BusCommonUtil.addDynamicMarkup(decryptedAppKey, markupList, busSearchRequest.getMarkupAmount());
				dynamicmarkupMap.put(companyId, markupList);
				try{
					BusSearchTemp busSearchTemp = new BusSearchTemp();
					busSearchTemp = busCommonDao.getBusSearchTemp(busSearchRequest.getSearchkey());
					TayyarahBusSearchMap  busSearchMap = (TayyarahBusSearchMap) BusCommonUtil.convertByteArrayToObject(busSearchTemp.getBusSearchData());
					AvailableBuses availableBusesTemp = busSearchMap.getAvailableBusesMap().get(busSearchTemp.getSearchKey());
					CurrencyConversionMap currencyConversionMap = BusCommonUtil.buildCurrencyConversionMap(busSearchRequest.getCurrency(),moneydao);					
					availableBuses = BusCommonUtil.busAvailableWithDynamicMarkupParser(availableBusesTemp, dynamicmarkupMap, busSearchRequest, currencyConversionMap, decryptedAppKey, companyConfigDAO,companyDAO);
					availableBuses.setSearchKey(busSearchRequest.getSearchkey());
					availableBuses.setTransactionkey(busSearchTemp.getTransactionKey());
					availableBusesMap.put(busSearchRequest.getSearchkey(), availableBuses);					
					tayyarahBusSearchMap.setAvailableBusesMap(availableBusesMap);
					tayyarahBusSearchMap.setMarkupCommissionDetails(markupCommissionDetails);
					tayyarahBusSearchMap.setBusMarkUpConfiglistMap(dynamicmarkupMap);
					tayyarahBusSearchMap.setBoardingPointsMap(busSearchMap.getBoardingPointsMap());
					tayyarahBusSearchMap.setDroppingPointMap(busSearchMap.getDroppingPointMap());
					tayyarahBusSearchMap.setBusSearchRequest(busSearchRequest);
					byte[] busSearchData = BusCommonUtil.convertObjectToByteArray(tayyarahBusSearchMap);
					busSearchTemp.setBusSearchData(busSearchData);
					busSearchTemp.setSearchKey(busSearchTemp.getSearchKey());
					busSearchTemp.setTransactionKey(busSearchTemp.getTransactionKey());
					busCommonDao.saveorupdateBusSearchTemp(busSearchTemp);
				}catch(Exception e){
					logger.error("isDynamicMarkup Exception", e);
				}

			}else{

				BusSearchTemp busSearchTemp = new BusSearchTemp();
				long lastrowid =  busCommonDao.getLastBusSearchTempId();
				lastrowid = lastrowid + 1;
				String searchKey = "BSK"+lastrowid;
				String transationKey = "BTK"+lastrowid;

				//List<AvailableBuses> AvailableBusesList = BusSearchExecutorServiceTaskHelper.busSearchService(busSearchRequest, decryptedAppKey, markupMap, moneydao, companyConfigDAO, searchKey, companyDAO);
				List<AvailableBuses> AvailableBusesList=BusSearchExecutorServiceTaskHelper.busSearchService(busSearchRequest, decryptedAppKey, markupMap, moneydao, companyConfigDAO, searchKey, companyDAO);
				for (AvailableBuses availableBusesArr : AvailableBusesList) {
					for (AvailableBus availableBus : availableBusesArr.getAvailableBus()) {
						boardingPointsMap.put(availableBus.getRouteScheduleId(), availableBus.getBoardingPoints()); 
						droppingPointMap.put(availableBus.getRouteScheduleId(), availableBus.getDroppingPoints());
					}
					availableBuses = availableBusesArr;
				}

				availableBuses.setSearchKey(searchKey);
				availableBuses.setTransactionkey(transationKey);			
				availableBusesMap.put(searchKey, availableBuses);
				tayyarahBusSearchMap.setAvailableBusesMap(availableBusesMap);
				tayyarahBusSearchMap.setMarkupCommissionDetails(markupCommissionDetails);
				tayyarahBusSearchMap.setBusMarkUpConfiglistMap(markupMap);
				tayyarahBusSearchMap.setBoardingPointsMap(boardingPointsMap);
				tayyarahBusSearchMap.setDroppingPointMap(droppingPointMap);
				tayyarahBusSearchMap.setBusSearchRequest(busSearchRequest);
				byte[] busSearchData = BusCommonUtil.convertObjectToByteArray(tayyarahBusSearchMap);
				busSearchTemp.setBusSearchData(busSearchData);
				busSearchTemp.setSearchKey(searchKey);
				busSearchTemp.setTransactionKey(transationKey);
				busSearchTemp.setCreatedAt(new Timestamp(new Date().getTime()));				
				busCommonDao.saveorupdateBusSearchTemp(busSearchTemp);
			}

			try {
				String queryString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(busSearchRequest);	
				busLook.setAppkey(busSearchRequest.getApp_key());
				busLook.setIP(ip);
				busLook.setTransactionId(availableBuses.getTransactionkey()!=null?String.valueOf(availableBuses.getTransactionkey()):"not avail");
				busLook.setSearchKey(availableBuses.getSearchKey()!=null?String.valueOf(availableBuses.getSearchKey()):"search failed");
				busLook.setSearchOnDateTime(new Timestamp(new Date().getTime()));
				busLook.setSearchQueryString(queryString);
				busLook.setCompanyId(Integer.valueOf(companyId));
				busLook.setConfigId(Integer.valueOf(configId));
				busLook.setCompanyName(company.getCompanyname());
				lookBookDao.insertIntoTable(busLook);

				BusLookBook lookBook=new BusLookBook(); 
				lookBook.setAppkey(busSearchRequest.getApp_key());
				lookBook=lookBookDao.CheckAndFetchBusLookBookByAppKey(lookBook);
				if(lookBook!=null && lookBook.getId()>0){
					lookBookDao.updateIntoBusTable(lookBook, "search");
				}
				else{
					lookBook=new BusLookBook(); 
					lookBook.setAppkey(busSearchRequest.getApp_key());
					lookBook.setTotalBookedCount(0);
					lookBook.setTotalSearchCount(1);
					lookBook.setCompanyId(Integer.valueOf(companyId));
					lookBook.setConfigId(Integer.valueOf(configId));
					lookBook.setCompanyName(company.getCompanyname());
					lookBookDao.insertIntoTable(lookBook);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}catch(BusException e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
		}catch(Exception e1){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
		}
		return availableBuses;
	}
	@ExceptionHandler(BusBaseException.class)
	public @ResponseBody
	BusRestError handleCustomException(BusBaseException ex,
			HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
