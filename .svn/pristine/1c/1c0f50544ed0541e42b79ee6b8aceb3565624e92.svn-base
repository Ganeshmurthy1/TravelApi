package com.tayyarah.hotel.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzHotelRoomDescription;


public class RzHotelRoomDescriptionDaoImp implements RzHotelRoomDescriptionDao {
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public RzHotelRoomDescription getHotelByRoomId(int roomid)
			throws NonUniqueResultException,HibernateException {

		try{
			session = hotelsessionFactory.openSession();			
			tx = session.beginTransaction();		
			String sql = "from RzHotelRoomDescription rzd where rzd.roomid=:roomid";
			RzHotelRoomDescription rzDescription = (RzHotelRoomDescription) session.createQuery(sql)
					.setParameter("roomid", roomid)
					.uniqueResult();		
			return rzDescription;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();		
				tx = session.beginTransaction();				
				String sql = "from RzHotelRoomDescription rzd where rzd.roomid=:roomid";
				RzHotelRoomDescription rzDescription = (RzHotelRoomDescription) session.createQuery(sql)
						.setParameter("roomid", roomid).setMaxResults(1).uniqueResult();
				return rzDescription;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();				
				tx = session.beginTransaction();				
				String sql = "from RzHotelRoomDescription rzd where rzd.roomid=:roomid";
				RzHotelRoomDescription rzDescription = (RzHotelRoomDescription) session.createQuery(sql)
						.setParameter("roomid", roomid).setMaxResults(0).list();				
				return rzDescription;
			}
		}		
	}

	@Override
	public List<RzHotelRoomDescription> getByVendorId(String HotelRoomDesVid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}		
}
