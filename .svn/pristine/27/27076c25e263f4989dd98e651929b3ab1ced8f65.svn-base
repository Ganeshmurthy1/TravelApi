package com.tayyarah.flight.tempairsegment.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.flight.cache.entity.FlightSearchCache;
import com.tayyarah.flight.entity.FlightAirPriceDetailsTemp;
import com.tayyarah.flight.entity.FlightApiSearchResponseTemp;
import com.tayyarah.flight.entity.FlightBookingKeysTemp;
import com.tayyarah.flight.entity.FlightSearchDetailsTemp;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.TempAirSegment;


public class TempAirSegmentDAOIMP implements TempAirSegmentDAO {

	@Autowired
	private SessionFactory sessionFactory;



	Logger logger = Logger.getLogger(TempAirSegmentDAOIMP.class);

	@Override
	public void InsertTAS(TempAirSegment TAS) throws HibernateException,
	NumberFormatException, Exception {
		Session session = null;
		Transaction tx = null;
		try {	
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.save(TAS);
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation", "finally" })
	public TempAirSegment getTAS(String flight_index) {
		List<TempAirSegment> list = null;
		TempAirSegment tempAirSegment = new TempAirSegment();	
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		try {
			Criteria criteria = session.createCriteria(TempAirSegment.class);
			criteria = criteria
					.add(Restrictions.eq("flight_index", flight_index));
			list = criteria.list();
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
			if (list.size() > 0) {
				tempAirSegment = list.get(0);
			}
			return tempAirSegment;
		}

	}

	@Override
	public void InsertAll(FlightSearchDetailsTemp searchDetails)
			throws HibernateException, NumberFormatException, Exception {
		Session session = null;
		Transaction tx = null;
		try {

			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.save(searchDetails);	
			tx.commit();

		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
		}
	}

	@Override
	public void UpdateFlightSearchDetailsTempAll(FlightSearchDetailsTemp searchDetails)
			throws HibernateException, NumberFormatException, Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(FlightSearchDetailsTemp.class);
			criteria = criteria.add(Restrictions.eq("searchkey", searchDetails.getSearchkey()));
			List<FlightSearchDetailsTemp> list = criteria.list();
			session.close();
			if(!session.isOpen()){				
				session = sessionFactory.openSession();
				System.out.println("list size "+list.size());
				if(list.size() > 0){
					for (FlightSearchDetailsTemp detail : list) {					
						searchDetails.setId(detail.getId());
						tx = session.beginTransaction();
						session.saveOrUpdate(searchDetails);
						System.out.println("updated ");
					}

				}
			}

		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
		}
	}

	@Override
	public void InsertApiResponse(FlightApiSearchResponseTemp flightApiSearchResponseTemp)
			throws HibernateException, NumberFormatException, Exception {
		Session localSession = null;
		Transaction localtx = null;
		try {
			localSession = sessionFactory.openSession();
			localtx = localSession.beginTransaction();
			localSession.save(flightApiSearchResponseTemp);
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			if (!localtx.wasCommitted()){  localtx.commit();}
			localSession.close();
		}
	}
	@Override
	public boolean CheckSearchKeyExists(String searchKey,String apiProvider)
			throws HibernateException, NumberFormatException, Exception {
		boolean searchKeyExists = false;
		List<FlightApiSearchResponseTemp> list = new ArrayList<>();
		Session session = null;
		Transaction tx = null;
		try {			
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(FlightApiSearchResponseTemp.class);
			criteria = criteria.add(Restrictions.eq("searchKey", searchKey));
			criteria = criteria.add(Restrictions.eq("apiprovider", apiProvider));
			list =  criteria.list();
			System.out.println("list "+list.size());
			System.out.println("searchKeyExists "+searchKeyExists);
			if(list.size() > 0){
				searchKeyExists = true;
			}
			System.out.println("searchKeyExists "+searchKeyExists);
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}
		return searchKeyExists;
	}

	@Override
	public List<FlightApiSearchResponseTemp> GetApiSearchResponse(String searchKey)
			throws HibernateException, NumberFormatException, Exception {
		FlightApiSearchResponseTemp flightApiSearchResponseTemp = new FlightApiSearchResponseTemp();
		List<FlightApiSearchResponseTemp> list = new ArrayList<FlightApiSearchResponseTemp>();
		Session session = null;
		Transaction tx = null;
		try {

			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(FlightApiSearchResponseTemp.class);
			criteria = criteria.add(Restrictions.eq("searchKey", searchKey));
			list=  criteria.list();

		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}
		return list;
	}

	@Override
	public void insertKeys(FlightBookingKeysTemp flightBookingKeys)
			throws HibernateException, NumberFormatException, Exception {
		Session session = null;
		Transaction tx = null;
		try {			
			session = sessionFactory.openSession();		
			tx = session.beginTransaction();
			session.saveOrUpdate(flightBookingKeys);

		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
		}
	}

	@Override
	public void updateKeys(FlightBookingKeysTemp flightBookingKeys)
			throws HibernateException, NumberFormatException, Exception {
		Session session = null;
		Transaction tx = null;
		try {

			session = sessionFactory.openSession();			
			Criteria criteria = session
					.createCriteria(FlightBookingKeysTemp.class);
			criteria = criteria.add(Restrictions.eq("search_key",
					flightBookingKeys.getSearch_key()));
			List<FlightBookingKeysTemp> list = criteria.list();
			session.close();
			if(!session.isOpen()){
				session = sessionFactory.openSession();
				if(list.size() > 0){
					for (FlightBookingKeysTemp flightBookingKeysTemp : list) {
						flightBookingKeys.setId(flightBookingKeysTemp.getId());
						tx = session.beginTransaction();
						session.saveOrUpdate(flightBookingKeys);
					}

				}				
			}
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
		}
	}

	@Override
	public void updatePriceKey(String transactionkey, String price_key)
			throws HibernateException, NumberFormatException, Exception {

		List<FlightBookingKeysTemp> list = null;
		FlightBookingKeysTemp flightBookingKeys = null;
		Session session = null;
		Transaction tx = null;
		int id = 0;
		try {	
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			try {
				Criteria criteria = session
						.createCriteria(FlightBookingKeysTemp.class);
				criteria = criteria.add(Restrictions.eq("transaction_key",
						transactionkey));
				list = criteria.list();
			} catch (Exception e) {
				e.printStackTrace();
			}
			id = list.get(id).getId();

			flightBookingKeys = (FlightBookingKeysTemp) session.get(
					FlightBookingKeysTemp.class, id);
			flightBookingKeys.setPrice_key(price_key);
			session.update(flightBookingKeys);
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation", "finally" })
	public FlightSearchDetailsTemp getAll(String searchkey) {
		List<FlightSearchDetailsTemp> list = null;
		FlightSearchDetailsTemp searchDetails = new FlightSearchDetailsTemp();		
		Session session = null;
		Transaction tx = null;
		try {		
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(FlightSearchDetailsTemp.class);
			criteria = criteria.add(Restrictions.eq("searchkey", searchkey));
			list = criteria.list();
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {

			session.close();
			if (list!=null && list.size() > 0) {
				searchDetails = list.get(0);
			}

			return searchDetails;
		}

	}
	@Override
	@SuppressWarnings({ "unchecked", "deprecation", "finally" })
	public FlightSearchDetailsTemp getAllByTransactionKey(String transactionkey) {
		List<FlightSearchDetailsTemp> list = null;
		FlightSearchDetailsTemp searchDetails = new FlightSearchDetailsTemp();	
		logger.info("transactionkey:" + transactionkey);
		Session session = null;
		Transaction tx = null;
		try {

			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(FlightSearchDetailsTemp.class);
			criteria = criteria.add(Restrictions.eq("transactionkey", transactionkey));
			list = criteria.list();
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {			
			session.close();
			if (list!=null && list.size() > 0) {
				searchDetails = list.get(0);
			}
			return searchDetails;
		}

	}

	@Override
	public void InsertAirPriceDetails(FlightAirPriceDetailsTemp airPriceDetails)
			throws HibernateException, NumberFormatException, Exception {
		Session session = null;
		Transaction tx = null;
		try {

			session = sessionFactory.openSession();

			tx = session.beginTransaction();
			session.save(airPriceDetails);
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation", "finally" })
	public FlightAirPriceDetailsTemp getAirPriceDetails(String price_key) {
		List<FlightAirPriceDetailsTemp> list = null;
		FlightAirPriceDetailsTemp airPriceDetails = new FlightAirPriceDetailsTemp();
		Session session = null;
		Transaction tx = null;
		logger.info("price_key:" + price_key);
		try {		
			session = sessionFactory.openSession();		
			tx = session.beginTransaction();
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		try {
			Criteria criteria = session.createCriteria(FlightAirPriceDetailsTemp.class);
			criteria = criteria.add(Restrictions.eq("price_key", price_key));
			list = criteria.list();
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
			if (list.size() > 0) {
				airPriceDetails = list.get(0);
			}

			return airPriceDetails;
		}

	}
	@Override
	public void InsertFlightSearchCache(FlightSearchCache flightSearchCache)
			throws HibernateException, NumberFormatException, Exception {
		Session session = null;
		Transaction tx = null;
		try {

			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.saveOrUpdate(flightSearchCache);	
			tx.commit();

		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			if (!tx.wasCommitted()){  tx.commit();}
			session.close();
		}
	}
	@Override
	public FlightSearchCache getApiSearchCacheResponse(Flightsearch flightsearch){
		Session session = null;   
		Criteria criteria=null;
		FlightSearchCache flightSearchCache= new FlightSearchCache();
		try{
			session = sessionFactory.openSession();
			criteria=session.createCriteria(FlightSearchCache.class);
			criteria.add(Restrictions.eq("origin",flightsearch.getOrigin()));
			criteria.add(Restrictions.eq("destination",flightsearch.getDestination()));
			criteria.add(Restrictions.eq("tripType",flightsearch.getTripType()));
			criteria.add(Restrictions.eq("currency",flightsearch.getCurrency()));
			criteria.add(Restrictions.eq("depDate",flightsearch.getDepDate()));
			criteria.add(Restrictions.eq("domestic",flightsearch.isDomestic()));
			criteria.add(Restrictions.eq("infant",flightsearch.getInfant()));
			criteria.add(Restrictions.eq("adult",flightsearch.getAdult()));
			criteria.add(Restrictions.eq("kid",flightsearch.getKid()));
			criteria.add(Restrictions.eq("supplier","TBO"));


			flightSearchCache= (FlightSearchCache) criteria.uniqueResult();

		}
		catch (Exception e) {
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return flightSearchCache;
	}

}
