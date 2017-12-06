package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.HotelBookingTemp;




public class HotelBookingDaoImp implements HotelBookingDao {


	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;	
	@Override
	public HotelBookingTemp insertHotelBooking(HotelBookingTemp hb) throws HibernateException, IOException, Exception {
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			hb.setCreatedAt(new Timestamp(new Date().getTime()));
			hb.setUpdatedAt(new Timestamp(new Date().getTime()));			
			session.save(hb);    //Save the data
			if (!tx.wasCommitted())
				tx.commit();

			return hb;
		} catch (HibernateException e) {
			throw e;
		} catch (Exception e) {
			throw e;			
		}
		finally{	
			if (!tx.wasCommitted())
				tx.commit();			
		}
	}

	@Override
	public HotelBookingTemp getHotelBooking(String orderId) throws HibernateException, IOException, Exception {		
		try{			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from HotelBookingTemp hb where hb.orderId=:order_id";
			Query query = session.createQuery(sql);
			query.setParameter("order_id", orderId);
			List<HotelBookingTemp> list = query.list();		
			tx.commit();			
			return list.get(0);				
		}
		catch (HibernateException e) {
			throw e;
		} catch (Exception e) {
			throw e;			
		}
		finally{		
			if (!tx.wasCommitted())
				tx.commit();		
							
		}		
	}

	@Override
	public HotelBookingTemp getHotelBooking(Long id) throws HibernateException, IOException, Exception {
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			HotelBookingTemp hb = (HotelBookingTemp)session.get(HotelBookingTemp.class, id);	
			return hb;
		}
		catch (HibernateException e) {
			throw e;
		} catch (Exception e) {
			throw e;			
		}
		finally{		
			if (!tx.wasCommitted())
				tx.commit();	
							
		}
	}
}