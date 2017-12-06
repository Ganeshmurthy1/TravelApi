package com.tayyarah.bus.controller;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.bus.util.BusErrorMessages;
import com.tayyarah.bus.util.BusException;
import com.tayyarah.bus.util.ErrorCodeCustomerEnum;
import com.tayyarah.esmart.bus.util.EsmartBusConfig;
import com.tayyarah.esmart.bus.util.EsmartServiceCall;
import com.tayyarah.bus.model.BusStations;



@RestController
@RequestMapping("/bus")
public class BusStationsController {
	static final Logger logger = Logger.getLogger(BusStationsController.class);
	
	@RequestMapping(value = "/getStations",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody BusStations getBusStationList(){
		BusStations busStation = null;
		try{
		EsmartBusConfig esmartBusConfig = new EsmartBusConfig();
		busStation = EsmartServiceCall.getStationsList(esmartBusConfig);
		}catch(Exception e){
			logger.error("getBusStationList " +e.getMessage());
			throw new BusException(ErrorCodeCustomerEnum.Exception, BusErrorMessages.NO_STATIONAVAILABLE.getErrorMessage());
		}		
		return busStation;		
	}
}
