package com.tayyarah.hotel.dao;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.hotel.model.Hotelreview;


public class HotelreviewDaoImp implements HotelreviewDao{
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;
	Logger logger = Logger.getLogger(HotelreviewDaoImp.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Hotelreview> getByVendorId(String hotelReviewVid) throws HibernateException {
		session = hotelsessionFactory.openSession();	
		String sql = "from Hotelreview hv where hv.hoteloverview.VendorID=:VendorID";
		Query query = session.createQuery(sql);
		query.setParameter("VendorID", hotelReviewVid);
		List<Hotelreview> list = query.list();
		if (list.size() > 0) {
			session.close();
		}
		return list;
	}

	@Override
	public List<Hotelreview> getHotelById(int rid) throws HibernateException {		
		session = hotelsessionFactory.openSession();		
		String sql = "from Hotelreview hv where hv.id=:id";
		Query query = session.createQuery(sql);
		query.setParameter("id", rid);
		@SuppressWarnings("unchecked")
		List<Hotelreview> list = query.list();
		if (list.size() > 0) {
			session.close();

		}
		return list;
	}
}