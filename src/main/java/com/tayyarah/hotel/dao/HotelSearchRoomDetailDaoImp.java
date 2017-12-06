package com.tayyarah.hotel.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.HotelSearchRoomDetailTemp;

public class HotelSearchRoomDetailDaoImp implements HotelSearchRoomDetailDao {
	@Autowired
	private SessionFactory sessionFactory;	
	Logger logger = Logger.getLogger(HotelSearchRoomDetailDaoImp.class);

	@Override
	public HotelSearchRoomDetailTemp insertOrupdateHotelSearchRoomDetail(HotelSearchRoomDetailTemp hotelSearchRoomDetail) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();	
			hotelSearchRoomDetail.setCreatedAt(new Timestamp(new Date().getTime()));
			hotelSearchRoomDetail.setUpdatedAt(new Timestamp(new Date().getTime()));			
			session.saveOrUpdate(hotelSearchRoomDetail);			
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			logger.debug(ex.getMessage());
		} finally {
			if(session != null)
				session.close();  
		}
		return hotelSearchRoomDetail;
	}

	@Override
	public HotelSearchRoomDetailTemp getHotelSearchRoomDetail(HotelSearchRoomDetailTemp hotelSearchRoomDetail) 
	{
		HotelSearchRoomDetailTemp hotelSearchRoomDetailStored = hotelSearchRoomDetail;
		Session session = null;
		try{
			session = sessionFactory.openSession();			
			String sql = "from HotelSearchRoomDetailTemp hrd where hrd.search_key=:search_key";
			logger.info("hotelsearchroomdetail stored... search query.."+sql);
			hotelSearchRoomDetailStored = (HotelSearchRoomDetailTemp) session.createQuery(sql)
					.setParameter("search_key", hotelSearchRoomDetail.getSearch_key())
					.uniqueResult();
		}catch (HibernateException e) {
			logger.info(e.getMessage());
		}finally {
			if(session != null)
				session.close();  
		}		
		return hotelSearchRoomDetailStored;	
	}
}