package com.tayyarah.flight.util;

import java.rmi.server.UID;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.Passenger;
import com.tayyarah.flight.model.TempAirSegment;
import com.travelport.api_v33.Air.TypeBaseAirSegment;

public class FlightSearchUtil {
	static final Logger logger = Logger.getLogger(FlightSearchUtil.class);

	public static void addDynamicMarkup(AppKeyVo appKeyVo,List<FlightMarkUpConfig> MArkUplist,String markupAmount){

		String companyId = "invalid";
		String configId = "invalid";
		configId = String.valueOf(appKeyVo.getConfigId());
		companyId = String.valueOf(appKeyVo.getCompanyId());
		FlightMarkUpConfig FlightMarkUpConfig = new FlightMarkUpConfig();
		FlightMarkUpConfig.setName("Dynamic markup");
		FlightMarkUpConfig.setAccumulative(Boolean
				.parseBoolean("false"));
		FlightMarkUpConfig.setAirline("ALL");
		FlightMarkUpConfig.setFixedAmount(Boolean
				.parseBoolean("true"));
		FlightMarkUpConfig.setClassOfService("ALL");
		FlightMarkUpConfig.setCompanyId(companyId);
		FlightMarkUpConfig.setConfigId(configId);
		FlightMarkUpConfig.setMarkup(markupAmount);
		FlightMarkUpConfig.setMarkupId("0");
		FlightMarkUpConfig.setPositionOfMarkup("1");
		FlightMarkUpConfig.setOrigin("ALL");
		FlightMarkUpConfig.setDestination("ALL");
		FlightMarkUpConfig.setArrvDate("ALL");
		FlightMarkUpConfig.setDeptDate("ALL");
		FlightMarkUpConfig.setCountry("ALL");
		FlightMarkUpConfig.setFareBasisCode("ALL");
		FlightMarkUpConfig.setDestinationType("ALL");
		FlightMarkUpConfig.setPromofareStartDate("ALL");
		FlightMarkUpConfig.setPromofareEndDate("ALL");
		MArkUplist.add(FlightMarkUpConfig);
	}

	public void StoreinDB(String ID,TypeBaseAirSegment typeBaseAirSegment,FlightTempAirSegmentDAO flightTempAirSegmentDAO) {
		TempAirSegment TAS = new TempAirSegment();
		TAS.setGroup(typeBaseAirSegment.getGroup());
		TAS.setFlightindex(ID);
		TAS.setCarrier(typeBaseAirSegment.getCarrier());
		TAS.setKey(typeBaseAirSegment.getKey());
		TAS.setFlightNumber(typeBaseAirSegment.getFlightNumber());
		TAS.setAvailabilitySource(typeBaseAirSegment.getAvailabilitySource());
		TAS.setAvailabilityDisplayType("dfdfd");
		TAS.setOrigin(typeBaseAirSegment.getOrigin());
		TAS.setDestination(typeBaseAirSegment.getDestination());
		TAS.setDepartureTime(typeBaseAirSegment.getDepartureTime());
		TAS.setArrivalTime(typeBaseAirSegment.getArrivalTime());
		TAS.setDistance(typeBaseAirSegment.getDistance());
		TAS.setFlightTime(typeBaseAirSegment.getFlightTime());
		try {
			flightTempAirSegmentDAO.InsertTAS(TAS);
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,
					FlightErrorMessages.NO_FLIGHT);
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException", e);
			throw new FlightException(
					ErrorCodeCustomerEnum.NumberFormatException,
					ErrorMessages.INVALID_APPKEY);
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,
					FlightErrorMessages.NO_FLIGHT);
		}
	}	
	public static void buildPassengerList(Flightsearch flightsearch,
			List<Passenger> passengers) {
		for (int i = 0; i < flightsearch.getAdult(); i++) {
			Passenger passenger = new Passenger();
			passenger.setId((new UID()).toString());
			passenger.setType("ADT");
			passengers.add(passenger);
		}
		for (int i = 0; i < flightsearch.getKid(); i++) {
			Passenger passenger = new Passenger();
			passenger.setId((new UID()).toString());
			passenger.setType("CHD");
			passengers.add(passenger);
		}
		for (int i = 0; i < flightsearch.getInfant(); i++) {
			Passenger passenger = new Passenger();
			passenger.setId((new UID()).toString());
			passenger.setType("INF");
			passengers.add(passenger);
		}
	}
}
