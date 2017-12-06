package com.tayyarah.common.controller;


import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.entity.Airlinelist;
import com.tayyarah.common.entity.Airport;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.flight.dao.AirlineDAO;
import com.tayyarah.flight.dao.AirportDAO;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("/Search")
public class AirportController {
	static final Logger logger = Logger.getLogger(AirportController.class);
	@Autowired
	AirportDAO dataDao;
	@Autowired
	AirlineDAO airlineDAO;	
	
	@RequestMapping(value="/airports",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Airport> getAirline() {
		List<Airport> airlineList = null;
		try {
			airlineList = dataDao.getAirportList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airlineList;
	}
	
	@RequestMapping(value="/airlines",method = RequestMethod.GET,headers="Accept=application/json")	
	public @ResponseBody
	List<Airlinelist> getAirlineList() {
		List<Airlinelist> airlineList = null;
		try {
			airlineList = airlineDAO.getAirlineList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airlineList;
	}
	
	@RequestMapping(value="test/{city}/{country}",method = RequestMethod.GET,headers="Accept=application/json")	
	public @ResponseBody
	String test(@PathVariable("city") String city,@PathVariable("country") String country) {
		return "{"+city+": "+country+"}";
	}	
	
	@RequestMapping(value = "/bycity/{city}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Airport> getAirportByCity(@PathVariable("city") String searchKey,HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<Airport> airports = null;
		try {		
			airports = dataDao.getAirportByCity(searchKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airports;
	}
	
	@RequestMapping(value = "/bycity/",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Airport> getAllAirports(HttpServletResponse response) {
		ResponseHeader.setResponse(response);//Setting response header
		List<Airport> airports = null;
		try {
			airports = dataDao.getAirportByCity("");	

		} catch (Exception e) {
			e.printStackTrace();
		}
		return airports;
	}
	
	@RequestMapping(value = "/currency/{country}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	String getCurrencyCode(@PathVariable("country") String country,HttpServletResponse response) {
		ResponseHeader.setResponse(response);		
		String currency = country;
		try {			
			currency = dataDao.getCurrencyCode(country);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currency;
	}
}