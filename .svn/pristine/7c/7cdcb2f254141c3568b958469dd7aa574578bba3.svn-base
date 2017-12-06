package com.tayyarah.flight.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.util.api.travelport.UapiServiceCall;


public class TravelportFlightSearchExecutorServiceTask implements Callable<SearchFlightResponse>{
	private Flightsearch flightsearch;
	private Map<String,List<FlightMarkUpConfig>> markupMap;
	private Map<String, String> airlineNameMap;
	private ArrayList<Map<String, String>> airportMapList;
	private boolean isDomestic;
	private String airline =null;
	private MoneyExchangeDao moneydao=null;
	private TravelportConfig travelportConfig;
	static final Logger logger = Logger.getLogger(TravelportFlightSearchExecutorServiceTask.class);
	
	public TravelportFlightSearchExecutorServiceTask(Flightsearch flightsearch,
			 Map<String,List<FlightMarkUpConfig>> markupMap,
			Map<String, String> airlineNameMap,
			ArrayList<Map<String, String>> airportMapList,boolean isDomestic, String airline,MoneyExchangeDao moneydao,TravelportConfig travelportConfig){
		this.flightsearch = flightsearch;
		this.markupMap = markupMap;
		this.airlineNameMap = airlineNameMap;
		this.airportMapList = airportMapList;
		this.isDomestic=isDomestic;
		this.airline = airline;
		this.moneydao = moneydao;
		this.travelportConfig = travelportConfig;
	}

	@Override
	public SearchFlightResponse call() throws Exception {
		SearchFlightResponse searchFlightResponse = null;
		try{
			logger.info("Searching TravelPort Flights");
			searchFlightResponse = UapiServiceCall.callSearchService(flightsearch,markupMap,airlineNameMap,airportMapList,isDomestic,airline,moneydao,travelportConfig);
		}
		 catch (ClassNotFoundException  e) {logger.error("ClassNotFoundException",e);
			throw new FlightException(ErrorCodeCustomerEnum.ClassNotFoundException,FlightErrorMessages.NO_FLIGHT);
		}
		catch(SOAPException e){logger.error("SOAPException",e);
			throw new FlightException(ErrorCodeCustomerEnum.SOAPException,FlightErrorMessages.NO_FLIGHT);
		}catch(JAXBException  e){logger.error("JAXBException",e);
			throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.NO_FLIGHT);
		}
		catch(Exception  e){logger.error("Exception",e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		}
		return searchFlightResponse;
	}

}
