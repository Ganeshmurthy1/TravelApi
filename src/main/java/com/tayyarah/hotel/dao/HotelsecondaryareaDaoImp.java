package com.tayyarah.hotel.dao;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.Hotelsecondaryarea;

public class HotelsecondaryareaDaoImp implements HotelsecondaryareaDao{
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;	


	@SuppressWarnings("unchecked")
	@Override
	public List<Hotelsecondaryarea> getByVendorId(String hotelsecondaryVid) throws HibernateException {		
		session = hotelsessionFactory.openSession();
		tx = session.beginTransaction();
		String sql = "from Hotelsecondaryarea hsr  where hsr.hoteloverview.VendorId=:VendorID";
		Query query = session.createQuery(sql);
		query.setParameter("VendorID", hotelsecondaryVid);
		List<Hotelsecondaryarea> list = query.list();
		if (list.size() > 0) {
			session.close();
		}
		return list;
	}

	@Override
	public List<Hotelsecondaryarea> getHotelById(int vendorId) throws HibernateException {		
		session = hotelsessionFactory.openSession();		
		tx = session.beginTransaction();
		String sql = "from Hotelsecondaryarea hsr  where hsr.hoteloverview.VendorId=:VendorID";
		Query query = session.createQuery(sql);
		query.setParameter("VendorID", vendorId);
		@SuppressWarnings("unchecked")
		List<Hotelsecondaryarea> list = query.list();
		if (list.size() > 0) {
			session.close();
		}
		return list;
	}
}
