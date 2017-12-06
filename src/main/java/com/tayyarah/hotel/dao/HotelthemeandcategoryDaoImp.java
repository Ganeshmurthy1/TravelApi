package com.tayyarah.hotel.dao;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.hotel.model.Hotelthemeandcategory;


public class HotelthemeandcategoryDaoImp implements HotelthemeandcategoryDao{
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;	

	@SuppressWarnings("unchecked")
	@Override
	public List<Hotelthemeandcategory> getByVendorId(String hotelthemeVid) throws HibernateException {
		session = hotelsessionFactory.openSession();		
		tx = session.beginTransaction();		
		String sql = "from Hotelthemeandcategory hc  where hc.hoteloverview.VendorId=:VendorID";
		Query query = session.createQuery(sql);
		query.setParameter("VendorID", hotelthemeVid);
		List< Hotelthemeandcategory> list = query.list();
		if (list.size() > 0) {
			session.close();			
		}
		return list;	
	}

	@Override
	public List< Hotelthemeandcategory> getHotelById(String vendorId) throws HibernateException {		
		session = hotelsessionFactory.openSession();		
		tx = session.beginTransaction();
		String sql = "from Hotelthemeandcategory hc  where hc.hoteloverview.VendorId=:VendorID";
		Query query = session.createQuery(sql);
		query.setParameter("VendorID", vendorId);	
		@SuppressWarnings("unchecked")
		List< Hotelthemeandcategory> list = query.list();
		if (list.size() > 0) {
			session.close();

		}
		return list;
	}
}