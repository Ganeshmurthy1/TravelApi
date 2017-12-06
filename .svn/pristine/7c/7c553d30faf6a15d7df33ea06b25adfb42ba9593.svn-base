package com.tayyarah.hotel.dao;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.Islhotelmapping;

public class IslhotelmappingDaoImp implements IslhotelmappingDao{
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;
	Logger logger = Logger.getLogger(Islhotelmapping.class);
	
	@Override
	public Islhotelmapping getHotelByISLVendorID(String ISLVendorID)
			throws HibernateException, NonUniqueResultException ,NullPointerException{
		try{
			session = hotelsessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from Islhotelmapping ihm  where ihm.ISLVendorID=:ISLVendorID";
			Islhotelmapping islhotelmapping = (Islhotelmapping) session.createQuery(sql)
					.setParameter("ISLVendorID", ISLVendorID)
					.uniqueResult();

			session.getTransaction().commit();
			return islhotelmapping;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();
				tx = session.beginTransaction();
				String sql = "from Islhotelmapping ihm  where ihm.ISLVendorID=:ISLVendorID";
				Islhotelmapping islhotelmapping = (Islhotelmapping) session.createQuery(sql)
						.setParameter("ISLVendorID", ISLVendorID).setMaxResults(1).uniqueResult();
				session.getTransaction().commit();
				return islhotelmapping;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();
				tx = session.beginTransaction();
				String sql = "from Islhotelmapping ihm  where ihm.ISLVendorID=:ISLVendorID";
				Islhotelmapping islhotelmapping = (Islhotelmapping) session.createQuery(sql)
						.setParameter("ISLVendorID", ISLVendorID).setMaxResults(0).list();
				session.getTransaction().commit();
				return islhotelmapping;
			}
		}
	}

	@Override
	public Islhotelmapping getHotelByTGVendorID(String TGVendorID)
			throws HibernateException, NonUniqueResultException {
		try{
			session = hotelsessionFactory.openSession();		
			tx = session.beginTransaction();
			String sql = "from Islhotelmapping ihm  where ihm.TGVendorID=:TGVendorID";
			Islhotelmapping islhotelmapping = (Islhotelmapping) session.createQuery(sql)
					.setParameter("TGVendorID", TGVendorID)
					.uniqueResult();
			session.getTransaction().commit();
			return islhotelmapping;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();		
				tx = session.beginTransaction();
				String sql = "from Islhotelmapping ihm  where ihm.TGVendorID=:TGVendorID";
				Islhotelmapping islhotelmapping = (Islhotelmapping) session.createQuery(sql)
						.setParameter("TGVendorID", TGVendorID).setMaxResults(1).uniqueResult();
				session.getTransaction().commit();
				return islhotelmapping;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();
				tx = session.beginTransaction();
				String sql = "from Islhotelmapping ihm  where ihm.TGVendorID=:TGVendorID";
				Islhotelmapping islhotelmapping = (Islhotelmapping) session.createQuery(sql)
						.setParameter("TGVendorID", TGVendorID).setMaxResults(0).list();
				session.getTransaction().commit();
				return islhotelmapping;
			}
		}
	}

	@Override
	public Islhotelmapping getHotelByReznextVendorID(Long ReznextVendorID)
			throws HibernateException, NonUniqueResultException {
		try{
			session = hotelsessionFactory.openSession();		
			tx = session.beginTransaction();
			String sql = "from Islhotelmapping ihm  where ihm.reznextVendorID=:ReznextVendorID";
			Islhotelmapping islhotelmapping = (Islhotelmapping) session.createQuery(sql)
					.setParameter("ReznextVendorID", ReznextVendorID)
					.uniqueResult();
			session.getTransaction().commit();
			return islhotelmapping;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();
				tx = session.beginTransaction();
				String sql = "from Islhotelmapping ihm  where ihm.reznextVendorID=:ReznextVendorID";
				Islhotelmapping islhotelmapping = (Islhotelmapping) session.createQuery(sql)
						.setParameter("ReznextVendorID", ReznextVendorID).setMaxResults(1).uniqueResult();
				session.getTransaction().commit();
				return islhotelmapping;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();
				tx = session.beginTransaction();
				String sql = "from Islhotelmapping ihm  where ihm.reznextVendorID=:ReznextVendorID";
				Islhotelmapping islhotelmapping = (Islhotelmapping) session.createQuery(sql)
						.setParameter("ReznextVendorID", ReznextVendorID).setMaxResults(0).list();
				session.getTransaction().commit();
				return islhotelmapping;
			}
		}
	}	
}