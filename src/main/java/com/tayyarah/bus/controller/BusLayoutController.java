package com.tayyarah.bus.controller;

import java.sql.Timestamp;
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
import com.tayyarah.bus.dao.BusCommonDao;
import com.tayyarah.bus.entity.BusSearchTemp;
import com.tayyarah.bus.entity.BusSeatAvailableTemp;
import com.tayyarah.bus.model.AvailableSeats;
import com.tayyarah.bus.model.BusLayoutRequest;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusMarkupCommissionDetails;
import com.tayyarah.bus.model.Seat;
import com.tayyarah.bus.model.TayyarahBusSearchMap;
import com.tayyarah.bus.model.TayyarahBusSeatMap;
import com.tayyarah.bus.util.BusBaseException;
import com.tayyarah.bus.util.BusCommonUtil;
import com.tayyarah.bus.util.BusErrorMessages;
import com.tayyarah.bus.util.BusException;
import com.tayyarah.bus.util.BusParamValidator;
import com.tayyarah.bus.util.BusRestError;
import com.tayyarah.bus.util.ErrorCodeCustomerEnum;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.esmart.bus.util.EsmartBusConfig;
import com.tayyarah.esmart.bus.util.EsmartServiceCall;

@RestController
@RequestMapping("/bus/layout")
public class BusLayoutController {
	static final Logger logger = Logger.getLogger(BusLayoutController.class);
	private static BusParamValidator  busParamValidator = new BusParamValidator();
	@Autowired
	CompanyDao companyDAO;
	@Autowired
	BusCommonDao busCommonDao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@Autowired
	MoneyExchangeDao moneydao;
	
	@RequestMapping(value = "", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody AvailableSeats getBusLayout(@RequestBody BusLayoutRequest busLayoutRequest,HttpServletResponse response,HttpServletRequest request){
		ResponseHeader.setResponse(response);
		// Check APP KEY
		if(busLayoutRequest.getApp_key()!=null && busLayoutRequest.getApp_key().equalsIgnoreCase(""))
		{
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NOTFOUND_APPKEY.getErrorMessage());
		}
		busParamValidator.layoutValidator(busLayoutRequest);
		AvailableSeats availableSeats = new AvailableSeats();		
		try{		
			String decryptedAppKey = busCommonDao.getDecryptedAppKey(companyDAO,busLayoutRequest.getApp_key());
			BusSearchTemp busSearchTemp = busCommonDao.getBusSearchTemp(busLayoutRequest.getSearchkey());
			TayyarahBusSearchMap  busSearchMap = (TayyarahBusSearchMap) BusCommonUtil.convertByteArrayToObject(busSearchTemp.getBusSearchData());
			EsmartBusConfig  esmartBusConfig = EsmartBusConfig.GetEsmartBusConfig(decryptedAppKey);
			Map<String,List<BusMarkUpConfig>> markupMap = busSearchMap.getBusMarkUpConfiglistMap();
			BusMarkupCommissionDetails markupCommissionDetails = busSearchMap.getMarkupCommissionDetails();
			CurrencyConversionMap currencyConversionMap = BusCommonUtil.buildCurrencyConversionMap(busSearchMap.getBusSearchRequest().getCurrency(),moneydao);
			availableSeats = EsmartServiceCall.getAvailableSeats(esmartBusConfig, busLayoutRequest, markupMap, currencyConversionMap, decryptedAppKey, companyConfigDAO,busSearchMap,companyDAO);
			TayyarahBusSeatMap tayyarahBusSeatMap = new TayyarahBusSeatMap();
			Map<String,com.tayyarah.bus.model.Seat> seatMap = new HashMap<String,com.tayyarah.bus.model.Seat>();
			Map<String,AvailableSeats> availableSeatslistMap = new HashMap<String,AvailableSeats>();
			availableSeatslistMap.put(busLayoutRequest.getSearchkey(), availableSeats);
			for (Seat seat : availableSeats.getSeats()) {
				seatMap.put(seat.getId(), seat);
			}
			tayyarahBusSeatMap.setMarkupCommissionDetails(markupCommissionDetails);
			tayyarahBusSeatMap.setAvailableSeatslistMap(availableSeatslistMap);
			tayyarahBusSeatMap.setBusMarkUpConfiglistMap(markupMap);
			tayyarahBusSeatMap.setSeatMap(seatMap);
			tayyarahBusSeatMap.setBusLayoutRequest(busLayoutRequest);
			byte[] busSeatData = BusCommonUtil.convertObjectToByteArray(tayyarahBusSeatMap);
			BusSeatAvailableTemp busSeatAvailableTemp = new BusSeatAvailableTemp();
			busSeatAvailableTemp.setBusSeatData(busSeatData);
			busSeatAvailableTemp.setSearchKey(busLayoutRequest.getSearchkey());
			busSeatAvailableTemp.setCreatedAt(new Timestamp(new Date().getTime()));
			busCommonDao.saveorupdateBusSeatAvailableTemp(busSeatAvailableTemp); 
			
		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_SEATAVAILABLE.getErrorMessage());
		}
		return availableSeats;
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
