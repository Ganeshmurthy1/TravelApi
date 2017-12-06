package com.tayyarah.hotel.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzHotelRoomImage;

public class RzHotelRoomImageDaoImp implements RzHotelRoomImageDao {
	@Autowired
	private SessionFactory hotelsessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public RzHotelRoomImage getHotelByRoomImageId(int id)
			throws NonUniqueResultException,HibernateException {

		try{
			session = hotelsessionFactory.openSession();
			tx = session.beginTransaction();			
			String sql = "from RzHotelRoomImage rzi  where rzi.id=:id";
			RzHotelRoomImage rzRoomImage = (RzHotelRoomImage) session.createQuery(sql)
					.setParameter("id",id)
					.uniqueResult();	
			return rzRoomImage;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();	
				tx = session.beginTransaction();			
				String sql = "from RzHotelRoomImage rzi  where rzi.id=:id";
				RzHotelRoomImage rzRoomImage = (RzHotelRoomImage) session.createQuery(sql)
						.setParameter("id",id).setMaxResults(1).uniqueResult();			
				return rzRoomImage;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();	
				tx = session.beginTransaction();				
				String sql = "from RzHotelRoomImage rzi  where rzi.id=:id";
				RzHotelRoomImage rzRoomImage = (RzHotelRoomImage) session.createQuery(sql)
						.setParameter("id",id).setMaxResults(0).list();
				return rzRoomImage;
			}
		}
	}

	@Override
	public List<RzHotelRoomImage> getByVendorId(String HotelRoomDesVid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}