package com.tayyarah.hotel.dao;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzHotelOverview;




public class RzHotelOverviewDaoImp implements RzHotelOverviewDao{
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;
	@Override
	public RzHotelOverview getHotelByVendoriD(int VendorID)
			throws HibernateException,NonUniqueResultException {
		try{
			session = hotelsessionFactory.openSession();		
			tx = session.beginTransaction();				
			String sql = "from RzHotelOverview rzo where rzo.id.vendorID=:VendorID";
			RzHotelOverview rzoHotelOverview = (RzHotelOverview) session.createQuery(sql)
					.setParameter("VendorID", VendorID)
					.uniqueResult();				
			return rzoHotelOverview;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();		
				tx = session.beginTransaction();				
				String sql = "from RzHotelOverview rzo where rzo.id.vendorID=:VendorID";
				RzHotelOverview rzoHotelOverview = (RzHotelOverview) session.createQuery(sql)
						.setParameter("VendorID", VendorID).setMaxResults(1).uniqueResult();					
				return rzoHotelOverview;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();		
				tx = session.beginTransaction();				
				String sql = "from RzHotelOverview rzo where rzo.id.vendorID=:VendorID";
				RzHotelOverview rzoHotelOverview = (RzHotelOverview) session.createQuery(sql)
						.setParameter("VendorID", VendorID).setMaxResults(0).list();					
				return rzoHotelOverview;
			}
		}
	}
	@Override
	public List<RzHotelOverview> getByVendorId(String HotelRoomDesVid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
