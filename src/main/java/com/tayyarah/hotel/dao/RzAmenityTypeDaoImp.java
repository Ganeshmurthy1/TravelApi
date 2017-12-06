package com.tayyarah.hotel.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.hotel.model.RzAmenityType;

public class RzAmenityTypeDaoImp implements RzAmenityTypeDao {
	@Autowired
	private SessionFactory hotelsessionFactory;

	Session session = null;
	Transaction tx = null;
	@Override
	public RzAmenityType getHotelByAmenityId(int amenityid)
			throws NonUniqueResultException,HibernateException {
		try{
			session = hotelsessionFactory.openSession();		
			tx = session.beginTransaction();
			String sql = "from RzAmenityType rat where rat.amenityid=:amenityid";
			RzAmenityType rzAmenityType = (RzAmenityType) session.createQuery(sql)
					.setParameter("amenityid", amenityid)
					.uniqueResult();
			return rzAmenityType;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();		
				tx = session.beginTransaction();
				String sql = "from RzAmenityType rat where rat.amenityid=:amenityid";
				RzAmenityType rzAmenityType = (RzAmenityType) session.createQuery(sql)
						.setParameter("amenityid", amenityid).setMaxResults(1).uniqueResult();
				return rzAmenityType;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();		
				tx = session.beginTransaction();
				String sql = "from RzAmenityType rat where rat.amenityid=:amenityid";
				RzAmenityType rzAmenityType = (RzAmenityType) session.createQuery(sql)
						.setParameter("amenityid", amenityid).setMaxResults(0).list();
				return rzAmenityType;
			}
		}
	}
	@Override
	public List<RzAmenityType> getByVendorId(String HotelRoomDesVid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
