package com.tayyarah.hotel.quotation.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.ApiProviderPaymentTransaction;
import com.tayyarah.common.entity.ApiProviderPaymentTransactionDetail;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.PaymentTransactionDetail;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;

public class HotelOfflineBookingDao {
	
	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(HotelOfflineBookingDao.class);

	
	public HotelOrderRow insertHotelOrderRowInfo(HotelOrderRow hotelOrderRow){
		Session session = null;	
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(hotelOrderRow);
			transaction.commit();

		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return hotelOrderRow;
	}
 
	public List<HotelOrderRoomInfo> insertHotelOrderRoomInfoInfo(List<HotelOrderRoomInfo> hotelOrderRoomInfoListNew) {
		Session session = null;
		Transaction transaction = null;
		List<HotelOrderRoomInfo> hotelOrderRoomInfoList = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if(hotelOrderRoomInfoListNew!=null && hotelOrderRoomInfoListNew.size()>0){
				for(HotelOrderRoomInfo hotelOrderRoomInfo:hotelOrderRoomInfoListNew){
					session.save(hotelOrderRoomInfo);
					hotelOrderRoomInfoList.add(hotelOrderRoomInfo);
				}
			 }
		 transaction.commit();

		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return hotelOrderRoomInfoList;
	}

	public List<HotelOrderGuest> insertHotelOrderGuestnfo(List<HotelOrderGuest> hotelOrderGuestListNew) {	
		Session session = null;
		Transaction transaction = null;
		List<HotelOrderGuest> hotelOrderGuestList = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if(hotelOrderGuestListNew != null && hotelOrderGuestListNew.size() > 0){
				for(HotelOrderGuest hotelOrderRoomInfo:hotelOrderGuestListNew){
					session.save(hotelOrderRoomInfo);
					hotelOrderGuestList.add(hotelOrderRoomInfo);
				}
			 }
		 transaction.commit();

		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return hotelOrderGuestList;
	}
	 
	public List<PaymentTransactionDetail> getPaymentTransactionDetailList(String api_transaction_id) {		
		Session session= null;
		List<PaymentTransactionDetail> list=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(PaymentTransactionDetail.class);
			criteria.add(Restrictions.eq("apiTransactionId", api_transaction_id));
			list= criteria.list();
		} catch (Exception e) {
			logger.error("HibernateException---------------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return list;
	}

	public List<ApiProviderPaymentTransactionDetail> getApiProviderPaymentTransactionDetailList(String api_transaction_id) {
		Session session= null;
		List<ApiProviderPaymentTransactionDetail> list=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(ApiProviderPaymentTransactionDetail.class);
			criteria.add(Restrictions.eq("apiTransactionId", api_transaction_id));
			list = criteria.list();
		} catch (Exception e) {
			logger.error("HibernateException---------------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	
	public PaymentTransaction  getPaymentTransactionInfo(String apiTransactionId){
		PaymentTransaction paymentTransaction= null;
		Session session= null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(PaymentTransaction.class);
			criteria.add(Restrictions.eq("api_transaction_id", apiTransactionId));
			paymentTransaction = (PaymentTransaction) criteria.uniqueResult();

		}catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			if(session!=null && session.isOpen())
				session.close(); 
		}
		return paymentTransaction;
	}
	
	public ApiProviderPaymentTransaction  getApiProviderPaymentTransactionInfo(String apiTransactionId){
		ApiProviderPaymentTransaction apiProviderPaymentTransaction= null;
		Session session= null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(ApiProviderPaymentTransaction.class);
			criteria.add(Restrictions.eq("api_transaction_id", apiTransactionId));
			apiProviderPaymentTransaction = (ApiProviderPaymentTransaction) criteria.uniqueResult();

		}catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			if(session!=null && session.isOpen())
				session.close(); 
		}
		return apiProviderPaymentTransaction;
	}
 }
