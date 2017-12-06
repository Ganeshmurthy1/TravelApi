package com.tayyarah.flight.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.util.api.tbo.TboServiceCall;


public class TboFlightSearchExecutorServiceTask implements Callable<SearchFlightResponse>{
	private Flightsearch flightsearch;
	private Map<String,List<FlightMarkUpConfig>> markupMap;
	private Map<String, String> airlineNameMap;
	private ArrayList<Map<String, String>> airportMapList;
	private CurrencyConversionMap currencyConversionMap=null;
	static final Logger logger = Logger.getLogger(TayyarahFlightSearchExecutorServiceTask.class);
	private FlightTempAirSegmentDAO flightTempAirSegmentDAO;
	private TboFlightConfig tboFlightConfig;
	private AppKeyVo appKeyVo ;
	private CompanyConfigDAO companyConfigDAO;
	private CompanyDao companyDao;

	public TboFlightSearchExecutorServiceTask(Flightsearch flightsearch,
			 Map<String,List<FlightMarkUpConfig>> markupMap,
			Map<String, String> airlineNameMap,
			ArrayList<Map<String, String>> airportMapList, CurrencyConversionMap currencyConversionMap,FlightTempAirSegmentDAO flightTempAirSegmentDAO,TboFlightConfig tboFlightConfig,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao){
		this.flightsearch = flightsearch;
		this.markupMap = markupMap;
		this.airlineNameMap = airlineNameMap;
		this.airportMapList = airportMapList;
		this.currencyConversionMap = currencyConversionMap;
		this.flightTempAirSegmentDAO = flightTempAirSegmentDAO;
		this.tboFlightConfig = tboFlightConfig;
		this.appKeyVo = appKeyVo;
		this.companyConfigDAO = companyConfigDAO;
		this.companyDao = companyDao;
	}

	@Override
	public SearchFlightResponse call() throws Exception {
		SearchFlightResponse searchFlightResponse = null;
		try{
			logger.info("Searching TboServiceCall Flights");
			searchFlightResponse = TboServiceCall.callSearchService(flightsearch,markupMap,airlineNameMap,airportMapList,currencyConversionMap,flightTempAirSegmentDAO,tboFlightConfig,appKeyVo,companyConfigDAO,companyDao);
		}
		 catch (ClassNotFoundException  e) {
			 logger.error("ClassNotFoundException",e);
			throw new FlightException(ErrorCodeCustomerEnum.ClassNotFoundException,FlightErrorMessages.NO_FLIGHT);
		}
		catch(SOAPException e){
			logger.error("SOAPException",e);
			throw new FlightException(ErrorCodeCustomerEnum.SOAPException,FlightErrorMessages.NO_FLIGHT);
		}catch(JAXBException  e){
			logger.error("JAXBException",e);
			throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.NO_FLIGHT);
		}
		catch(Exception  e){
			logger.error("Exception",e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		}
		return searchFlightResponse;
	}
}