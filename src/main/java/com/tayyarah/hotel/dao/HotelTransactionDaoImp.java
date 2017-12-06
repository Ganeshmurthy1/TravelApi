package com.tayyarah.hotel.dao;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.HotelTransactionTemp;




public class HotelTransactionDaoImp implements HotelTransactionDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	Transaction tx = null;
	public static final Logger logger = Logger.getLogger(HotelSearchDaoImp.class);
	@Override
	public HotelTransactionTemp insertApiMapBySearchKey(HotelTransactionTemp ht) throws HibernateException, IOException, Exception {		
		Session session = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			ht.setCreatedAt(new Timestamp(new Date().getTime()));
			ht.setUpdatedAt(new Timestamp(new Date().getTime()));		
			session.save(ht);    //Save the data
			if (!tx.wasCommitted())
				tx.commit();
			
			return ht;

		} catch (HibernateException e) {
			throw e;
		} catch (Exception e) {
			throw e;			
		}
		finally{	
			if (!tx.wasCommitted())
				tx.commit();
			session.close();
		}
	}
	
	@Override
	public HotelTransactionTemp getHotelTransaction(Long searchKey) throws HibernateException, IOException, Exception {
		HotelTransactionTemp ht = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(HotelTransactionTemp.class);
			criteria.add(Restrictions.eq("search_key", searchKey));
			List<HotelTransactionTemp> hotelTransactionTempList = criteria.list();			
			if(hotelTransactionTempList != null && !hotelTransactionTempList.isEmpty())
				ht = hotelTransactionTempList.get(0);
			
			return ht;

		} catch (HibernateException e) {
			throw e;
		} catch (Exception e) {
			throw e;			
		}
		finally{	
			if (!tx.wasCommitted())
				tx.commit();
			session.close();
		}			
	}
}