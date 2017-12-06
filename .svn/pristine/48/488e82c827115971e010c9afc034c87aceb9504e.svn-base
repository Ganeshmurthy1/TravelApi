package com.tayyarah.hotel.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzFacility;

public class RzFacilityDaoImp implements RzFacilityDao {
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;
	
	@Override
	public RzFacility getHotelByVendorID(int VendorID)
			throws NonUniqueResultException,HibernateException {
		try{
			session = hotelsessionFactory.openSession();
			tx = session.beginTransaction();			
			String sql ="from RzFacility rf where rf.id.vendorID = :VendorID";
			RzFacility rzFacility = (RzFacility) session.createSQLQuery(sql)
					.setParameter("VendorID", VendorID)
					.uniqueResult();
			return rzFacility;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();		
				tx = session.beginTransaction();				
				String sql ="from RzFacility rf where rf.id.vendorID = :VendorID";
				RzFacility rzFacility = (RzFacility) session.createSQLQuery(sql)
						.setParameter("VendorID", VendorID).setMaxResults(1).uniqueResult();
				return rzFacility;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();	
				tx = session.beginTransaction();			
				String sql ="from RzFacility rf where rf.id.vendorID = :VendorID";
				RzFacility rzFacility = (RzFacility) session.createSQLQuery(sql)
						.setParameter("VendorID", VendorID).setMaxResults(0).list();
				return rzFacility;
			}
		}
	}

	@Override
	public List<RzFacility> getByVendorId(String HotelRoomDesVid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}