package com.tayyarah.flight.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.Airport;
import com.tayyarah.common.entity.Country;


public class AirportDAOImp implements AirportDAO {
	Logger logger = Logger.getLogger(AirportDAOImp.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	@SuppressWarnings("unchecked")
	/*@Cacheable(value="airportListCache")*/
	public synchronized List<Airport> getAirportList() throws Exception {
		session = sessionFactory.openSession();
		List<Airport> airports = null ;
		try{
			airports = session.createCriteria(Airport.class).list();
		}
		catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		} finally {
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		return airports;
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized List<Airport> getAirportByCity(String searchKey) throws Exception {			
		List<Airport> airportList = null;
		try
		{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Airport.class);			
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.like("airport_code", "%" + searchKey + "%")));
			airportList = criteria.list();
		}catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		}
		try
		{
			Criteria criteria = session.createCriteria(Airport.class);			
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.like("city", "%" + searchKey + "%"))
					.add(Restrictions.like("country", "%" + searchKey + "%"))
					.add(Restrictions.like("airport_name", "%" + searchKey + "%")));		
			airportList.addAll(criteria.list());
		}
		catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		} finally {
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		return airportList;
	}

	@Override
	public ArrayList<Map<String, String>> getAirportMap() throws Exception {
		Map<String, String> CityNameMap = new HashMap<String, String>();
		Map<String, String> AirportNameMap = new HashMap<String, String>();
		ArrayList<Map<String, String>> airportMapList = new ArrayList<Map<String, String>>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(Airport.class);
			List<Airport> list = criteria.list();
			if (list!=null && list.size() > 0) {
				Iterator<Airport> airport = list.iterator();
				while (airport.hasNext()) {
					Airport AP = airport.next();
					CityNameMap.put(AP.getAirport_code(), AP.getCity());
					AirportNameMap.put(AP.getAirport_code(),
							AP.getAirport_name());
				}
				airportMapList.add(CityNameMap);
				airportMapList.add(AirportNameMap);
			}
		}
		catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		} finally {
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		return airportMapList;
	}
	
	@Override
	public String getCurrencyCode(String con) throws Exception {
		String currencyCode = null;	
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Country.class);
			criteria.add(Restrictions.eq("country_name", con));
			List<Country> list = criteria.list();			
			if (list!=null && list.size() > 0) {
				currencyCode = list.get(0).getCurrrency_code();			
			}
		}
		catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		} finally {
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		return currencyCode;
	}

	@Override
	public String getyCountryName(String airportCode) throws Exception {
		String country = "INDIA";
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Airport.class);
			criteria.add(Restrictions.eq("airport_code", airportCode));
			List<Airport> list = criteria.list();
			if (list!=null && list.size() > 0) {
				country = list.get(0).getCountry();
			}
		}
		catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		} finally {
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		return country.trim();
	}
}