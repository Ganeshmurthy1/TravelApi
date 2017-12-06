package com.tayyarah.hotel.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzRoomAmenity;


public class RzRoomAmenityDaoImp implements RzRoomAmenityDao {
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public RzRoomAmenity getHotelByAmenityId(int amenityid)
			throws NonUniqueResultException,HibernateException {

		try{
			session = hotelsessionFactory.openSession();			
			tx = session.beginTransaction();	
			String sql = "from RzRoomAmenity rza  where rza.amenityid=:amenityid";
			RzRoomAmenity rzAmenity = (RzRoomAmenity) session.createQuery(sql)
					.setParameter("amenityid", (byte)amenityid)
					.uniqueResult();			
			return rzAmenity;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();				
				tx = session.beginTransaction();				
				String sql = "from RzRoomAmenity rza  where rza.amenityid=:amenityid";
				RzRoomAmenity rzAmenity = (RzRoomAmenity) session.createQuery(sql)
						.setParameter("amenityid", (byte)amenityid).setMaxResults(1).uniqueResult();
				return rzAmenity;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();	
				tx = session.beginTransaction();			
				String sql = "from RzRoomAmenity rza  where rza.amenityid=:amenityid";
				RzRoomAmenity rzAmenity = (RzRoomAmenity) session.createQuery(sql)
						.setParameter("amenityid", (byte)amenityid).setMaxResults(0).list();			
				return rzAmenity;
			}
		}
	}

	@Override
	public List<RzRoomAmenity> getByVendorId(String HotelRoomDesVid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
