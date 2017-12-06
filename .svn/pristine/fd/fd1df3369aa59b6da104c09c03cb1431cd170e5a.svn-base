package com.tayyarah.flight.service.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tayyarah.common.entity.Airlinelist;
import com.tayyarah.flight.dao.AirlineDAO;

@Component("airlineService")
public class AirlineServiceImpl implements AirlineService{

	Logger logger = Logger.getLogger(AirlineServiceImpl.class);
	
	@Autowired
	AirlineDAO airlineDAO;

	@Override
	public Map<String, String> getAirlineNameMap() throws Exception {
		Map<String, String> airlineNameMap = new HashMap<String, String>();
		try {
			List<Airlinelist> list = airlineDAO.getAirlineList();
			if (list!=null && list.size() > 0) {
				Iterator<Airlinelist> airportlist = list.iterator();
				while (airportlist.hasNext()) {
					Airlinelist APL = airportlist.next();
					airlineNameMap.put(APL.getAirlinecode(),
							APL.getAirlinename());
				}
			}

		}
		catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		} 
		return airlineNameMap;
	}

	@Override
	public List<Airlinelist> getAirlineList() throws Exception {
		return airlineDAO.getAirlineList();
	}

	@Override
	public Map<String, Airlinelist> getAirlineMap() throws Exception {
		Map<String, Airlinelist> airlineNameMap = new HashMap<String, Airlinelist>();
		try {
			List<Airlinelist> list = airlineDAO.getAirlineList();
			if (list!=null && list.size() > 0) {
				Iterator<Airlinelist> airportlist = list.iterator();
				while (airportlist.hasNext()) {
					Airlinelist APL = airportlist.next();
					airlineNameMap.put(APL.getAirlinecode(),APL);
				}
			}
		}
		catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		} 
		return airlineNameMap;
	}

}
