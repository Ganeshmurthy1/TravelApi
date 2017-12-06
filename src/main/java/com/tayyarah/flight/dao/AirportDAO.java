package com.tayyarah.flight.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tayyarah.common.entity.Airport;

public interface AirportDAO {
	public List<Airport> getAirportList() throws Exception;
	public List<Airport> getAirportByCity(String searchKey) throws Exception;
	public ArrayList<Map<String,String>> getAirportMap()  throws Exception;
	public String getCurrencyCode(String country) throws Exception;
	public String getyCountryName(String airportCode) throws Exception;
}
