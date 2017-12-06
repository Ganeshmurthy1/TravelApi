package com.tayyarah.common.notification.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.PaymentTransactionDetail;
import com.tayyarah.flight.entity.FlightPaymentTxDetailHistory;
import com.tayyarah.hotel.entity.HotelPaymentTxDetailHistory;




public class HotelAndFlightPaymentTransctionDAO {

	@Autowired
	private SessionFactory sessionFactory;	
	
	public HotelPaymentTxDetailHistory getHotelPaymentTxDetailHistory(Long id){
		Session session = null;
		HotelPaymentTxDetailHistory hotelPaymentTxDetailHistory = new HotelPaymentTxDetailHistory();		
		try{
			session= sessionFactory.openSession();
			Criteria criteria = session.createCriteria(HotelPaymentTxDetailHistory.class);	
			Conjunction filters = Restrictions.conjunction();
			filters.add(Restrictions.eq("id", id));					
			criteria.add(filters);
			hotelPaymentTxDetailHistory = (HotelPaymentTxDetailHistory) criteria.uniqueResult();
		}
		catch(HibernateException he){
			System.out.println("HibernateException "+he);
			he.printStackTrace();			
		}
		finally {
			if(session!=null)
				session.close();
		}		
		return hotelPaymentTxDetailHistory;		
	}	
	
	public List<PaymentTransactionDetail> getPaymentTransactionDetail(PaymentTransaction paymentTransaction){
		List<PaymentTransactionDetail> paymentTransactionDetails=null;
		try {
			Session session = sessionFactory.openSession();		
			Criteria criteria = session.createCriteria(PaymentTransactionDetail.class);
			criteria.add(Restrictions.eq("paymentTransaction", paymentTransaction));
			paymentTransactionDetails = (List<PaymentTransactionDetail>) criteria.list();
			session.close();
			
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return paymentTransactionDetails;	
	}	

	public FlightPaymentTxDetailHistory getFlightPaymentTxDetailHistory(Long id){
		Session session = null;
		FlightPaymentTxDetailHistory flightPaymentTxDetailHistory = null; 		
		try{
			session= sessionFactory.openSession();
			Criteria criteria = session.createCriteria(FlightPaymentTxDetailHistory.class);	
			Conjunction filters = Restrictions.conjunction();
			filters.add(Restrictions.eq("id", id));					
			criteria.add(filters);
			flightPaymentTxDetailHistory = (FlightPaymentTxDetailHistory) criteria.uniqueResult();
		}
		catch(HibernateException he){
			System.out.println("HibernateException "+he);
			he.printStackTrace();			
		}
		finally {
			if(session!=null)
				session.close();
		}		
		return flightPaymentTxDetailHistory;		
	}
}
