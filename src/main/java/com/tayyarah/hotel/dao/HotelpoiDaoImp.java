package com.tayyarah.hotel.dao;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.HotelPoi;


public class HotelpoiDaoImp implements HotelpoiDao {
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;
	Logger logger = Logger.getLogger(HotelpoiDaoImp.class);
	
	@Override
	public HotelPoi getHotelByPoiId(int PoiId) throws HibernateException {
		logger.info("PoiId:"+ PoiId);
		session = hotelsessionFactory.openSession();		
		HotelPoi hotelPoi=(HotelPoi)session.get(HotelPoi.class,PoiId);
		tx = session.getTransaction();
		session.beginTransaction();
		tx.commit();
		return hotelPoi;
	}
	
	@Override
	public List<HotelPoi> getHotelByPoiId() throws HibernateException {		
		session = hotelsessionFactory.openSession();
		tx = session.beginTransaction();	
		@SuppressWarnings("unchecked")
		List<HotelPoi> cities = session.createCriteria(HotelPoi.class)
				.list();		
		return cities;
	}
}