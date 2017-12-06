package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.HotelSearchTemp;

public class HotelSearchDaoImp implements HotelSearchDao {

	
	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;	
	public static final Logger logger = Logger.getLogger(HotelSearchDaoImp.class);

	public HotelSearchTemp insertOrUpdateHotelSearch(HotelSearchTemp hs) throws HibernateException, IOException, Exception {		
		try{
			session = sessionFactory.openSession();			
			Criteria cr = session.createCriteria(HotelSearchTemp.class);
			cr.add(Restrictions.eq("search_key", hs.getSearch_key()));
			HotelSearchTemp hotelSearchTempDB = (HotelSearchTemp) cr.uniqueResult();
			if(session != null && session.isOpen())
			{				
				session.close(); 
				session = null;
			}
			if(hotelSearchTempDB != null)
			{
				try{
					session = sessionFactory.openSession();			
					tx = session.beginTransaction();
					hotelSearchTempDB.setHotelres_map(hs.getHotelres_map());
					hotelSearchTempDB.setHotelsearch_cmd(hs.getHotelsearch_cmd());	
					hotelSearchTempDB.setCreatedAt(new Timestamp(new Date().getTime()));
					hotelSearchTempDB.setUpdatedAt(new Timestamp(new Date().getTime()));				
					session.update(hotelSearchTempDB);    //Save the data
					if (!tx.wasCommitted())
						tx.commit();
				}
				catch (Exception e) {
					if (tx!=null) tx.rollback();		
				}
			}
			else
			{
				session = sessionFactory.openSession();		
				session.saveOrUpdate(hs); 
			}	
			
			return hs;

		} catch (HibernateException e) {
			throw e;
		} catch (Exception e) {
			throw e;			
		}
		finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}			
		}
	}
	
	@Override
	public HotelSearchTemp getHotelSearch(Long searchKey) throws HibernateException, IOException {
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			HotelSearchTemp hs = (HotelSearchTemp)session.get(HotelSearchTemp.class, searchKey);			
			return hs;

		} catch (HibernateException e) {
			logger.info("------getApiMapBySearchKey---"+searchKey+"--HibernateException--"+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("------getApiMapBySearchKey---"+searchKey+"--Exception--"+e.getMessage());
			e.printStackTrace();
		}
		finally{		
			if (!tx.wasCommitted())
				tx.commit();						
		}
		return new HotelSearchTemp();	
	}
}