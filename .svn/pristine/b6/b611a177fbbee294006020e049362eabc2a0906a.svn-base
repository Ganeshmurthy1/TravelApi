package com.tayyarah.hotel.reposit.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.reposit.entity.Facility;
import com.tayyarah.hotel.reposit.entity.HotelBookingComLink;

public class BookingComDAOImp implements BookingComDAO {
	@Autowired
	private SessionFactory lintashotelsessionFactory;	
	Logger logger = Logger.getLogger(BookingComDAOImp.class);

	@Override
	public HotelBookingComLink getHotelBookingComLink(String vendorName) throws HibernateException {
		Session session = null;
		List<HotelBookingComLink> list=null;
		try{
			session = lintashotelsessionFactory.openSession();				
			//String sql = "select * from hotelbookinglink  where VendorName= '" + vendorName + "';";
			String sql = "from HotelBookingComLink hb where hb.vendorName=:VendorName"; 
			logger.info("hotel."+sql);
			Query query = session.createQuery(sql);	
			query.setParameter("VendorName", vendorName);
			list = query.list();
			if(list != null && list.size()>0)
				return list.get(0);
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return null;
	}

}
