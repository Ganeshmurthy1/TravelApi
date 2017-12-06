package com.tayyarah.flight.service.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tayyarah.common.entity.Airport;
import com.tayyarah.flight.dao.AirportDAO;
import com.tayyarah.flight.dao.AirportDAOImp;

@Component("airportService")
public class AirportServiceImpl implements AirportService{

	Logger logger = Logger.getLogger(AirportServiceImpl.class);

	@Autowired
	AirportDAO airportDao;

	@Override
	public ArrayList<Map<String, String>> getAirportMap()  throws Exception
	{
		//		AirportDAO airportDao = new AirportDAOImp();
		Map<String, String> CityNameMap = new HashMap<String, String>();
		Map<String, String> AirportNameMap = new HashMap<String, String>();
		ArrayList<Map<String, String>> airportMapList = new ArrayList<Map<String, String>>();
		List<Airport> airports = airportDao.getAirportList();

		if (airports!=null && airports.size() > 0) {
			for(Airport airport :airports) 
			{
				CityNameMap.put(airport.getAirport_code(), airport.getCity());
				AirportNameMap.put(airport.getAirport_code(),
						airport.getAirport_name());
			}
			airportMapList.add(CityNameMap);
			airportMapList.add(AirportNameMap);
		}
		return airportMapList;
	}

	@Override
	public Map<String, Airport> getAllAirportMap()  throws Exception
	{
		Map<String, Airport> AirportNameMap = new HashMap<String, Airport>();
		List<Airport> airports = airportDao.getAirportList();

		if (airports!=null && airports.size() > 0) {
			for(Airport airport :airports) 
			{
				AirportNameMap.put(airport.getAirport_code(), airport);
			}
		}
		return AirportNameMap;
	}


	@Override
	public List<Airport> getAirportList()  throws Exception
	{
		//		AirportDAO airportDao = new AirportDAOImp();
		return airportDao.getAirportList();
	}



	/*public String getCurrencyCode(String country) throws Exception
	{

	}

	public String getyCountryName(String airportCode) throws Exception
	{

	}
	public List<Airport> getAirportList() throws Exception
	{

	}
	public List<Airport> getAirportByCity(String searchKey) throws Exception
	{

	}
	 */



}
