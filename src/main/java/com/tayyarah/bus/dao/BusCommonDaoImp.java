package com.tayyarah.bus.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.bus.entity.BusBlockedSeatTemp;
import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.entity.BusOrderRowCancellation;
import com.tayyarah.bus.entity.BusOrderRowCommission;
import com.tayyarah.bus.entity.BusOrderRowMarkup;
import com.tayyarah.bus.entity.BusOrderRowServiceTax;
import com.tayyarah.bus.entity.BusSearchTemp;
import com.tayyarah.bus.entity.BusSeatAvailableTemp;
import com.tayyarah.bus.exception.BusErrorMessages;
import com.tayyarah.bus.exception.BusException;
import com.tayyarah.common.entity.FlightAndHotelBookApiResponse;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.util.encryptions;
import com.tayyarah.company.dao.CompanyDao;

public class BusCommonDaoImp implements BusCommonDao{
	public static final Logger logger = Logger.getLogger(BusCommonDaoImp.class);
	@Autowired
	private SessionFactory sessionFactory;


	public String getDecryptedAppKey(CompanyDao CDAO,String app_key)
	{		
		encryptions enc = new encryptions();
		app_key = app_key.replaceAll(" ", "+");
		String decAppkey = enc.decrypt(app_key);	
		try {			
			boolean isFound = CDAO.checkAppkey(decAppkey);			
			if(!isFound){				
				throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NOTFOUND_APPKEY.getErrorMessage());
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new BusException(ErrorCodeCustomerEnum.HibernateException,BusErrorMessages.DB_ERROR.getErrorMessage());
		}catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusException(ErrorCodeCustomerEnum.NumberFormatException,BusErrorMessages.INVALID_APPKEY.getErrorMessage());
		}catch (Exception e) {
			e.printStackTrace();

			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.INVALID_APPKEY.getErrorMessage());
		}
		return decAppkey;
	}

	public void saveorupdateBusSearchTemp(BusSearchTemp busSearchTemp){
		Session session = null;
		Transaction tx = null;	
		try{
			session = sessionFactory.openSession();			
			Criteria cr = session.createCriteria(BusSearchTemp.class);
			cr.add(Restrictions.eq("searchKey", busSearchTemp.getSearchKey()));
			BusSearchTemp busSearchTempDB = (BusSearchTemp) cr.uniqueResult();
			if(session != null && session.isOpen())
			{				
				session.close(); 
				session = null;
			}
			if(busSearchTempDB != null){
				try{
					session = sessionFactory.openSession();			
					tx = session.beginTransaction();
					busSearchTempDB.setBusSearchData(busSearchTemp.getBusSearchData());
					session.update(busSearchTempDB);   
					if (!tx.wasCommitted())
						tx.commit();

				}catch (Exception e) {
					if (tx!=null) tx.rollback();		
				}

			}else{
				session = sessionFactory.openSession();		
				session.saveOrUpdate(busSearchTemp); 
			}
		}catch(Exception e){
			logger.error("saveBusSearchTemp Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
	}
	public BusSearchTemp getBusSearchTemp(String searchKey){
		Session session = null;	
		BusSearchTemp busSearchTemp = null;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(BusSearchTemp.class);
			cr.add(Restrictions.eq("searchKey", searchKey));
			busSearchTemp = (BusSearchTemp) cr.uniqueResult();
		}catch(Exception e){
			logger.error("getBusSearchTemp Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return busSearchTemp;
	}
	public long getLastBusSearchTempId(){
		Session session = null;	
		long lastInsertedId = 0l;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(BusSearchTemp.class);
			cr.addOrder(Order.desc("id"));
			cr.setMaxResults(1);
			BusSearchTemp busSearchTemp = (BusSearchTemp) cr.uniqueResult(); 
			if(busSearchTemp != null)
				lastInsertedId = busSearchTemp.getId();
		}catch(Exception e){
			logger.error("getLastBusSearchTempId Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return lastInsertedId;
	}
	public void saveorupdateBusSeatAvailableTemp(BusSeatAvailableTemp busSeatAvailableTemp){
		Session session = null;
		Transaction tx = null;	
		try{
			session = sessionFactory.openSession();			
			Criteria cr = session.createCriteria(BusSeatAvailableTemp.class);
			cr.add(Restrictions.eq("searchKey", busSeatAvailableTemp.getSearchKey()));
			BusSeatAvailableTemp busSeatAvailableTempDB = (BusSeatAvailableTemp) cr.uniqueResult();
			if(session != null && session.isOpen())
			{				
				session.close(); 
				session = null;
			}
			if(busSeatAvailableTempDB != null){
				try{
					session = sessionFactory.openSession();			
					tx = session.beginTransaction();
					busSeatAvailableTempDB.setBusSeatData(busSeatAvailableTemp.getBusSeatData());
					session.update(busSeatAvailableTempDB);   
					if (!tx.wasCommitted())
						tx.commit();

				}catch (Exception e) {
					if (tx!=null) tx.rollback();		
				}

			}else{
				session = sessionFactory.openSession();		
				session.saveOrUpdate(busSeatAvailableTemp); 
			}
		}catch(Exception e){
			logger.error("saveBusSearchTemp Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
	}
	public BusSeatAvailableTemp getBusSeatAvailableTemp(String searchKey){
		Session session = null;	
		BusSeatAvailableTemp busSeatAvailableTemp = null;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(BusSeatAvailableTemp.class);
			cr.add(Restrictions.eq("searchKey", searchKey));
			busSeatAvailableTemp = (BusSeatAvailableTemp) cr.uniqueResult();
		}catch(Exception e){
			logger.error("getBusSeatAvailableTemp Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return busSeatAvailableTemp;
	}

	public synchronized void saveorupdateBusBlockedSeatTemp(BusBlockedSeatTemp busBlockedSeatTemp){
		Session session = null;
		Transaction tx = null;	
		try{
			session = sessionFactory.openSession();			
			Criteria cr = session.createCriteria(BusBlockedSeatTemp.class);
			cr.add(Restrictions.eq("searchKey", busBlockedSeatTemp.getSearchKey()));
			BusBlockedSeatTemp BusBlockedSeatTempDB = (BusBlockedSeatTemp) cr.uniqueResult();
			if(session != null && session.isOpen())
			{				
				session.close(); 
				session = null;
			}
			if(BusBlockedSeatTempDB != null){
				try{
					session = sessionFactory.openSession();			
					tx = session.beginTransaction();
					BusBlockedSeatTempDB.setBusBlockedData(busBlockedSeatTemp.getBusBlockedData());
					session.update(BusBlockedSeatTempDB);   
					if (!tx.wasCommitted())
						tx.commit();

				}catch (Exception e) {
					if (tx!=null) tx.rollback();		
				}

			}else{
				session = sessionFactory.openSession();		
				session.saveOrUpdate(busBlockedSeatTemp); 
			}
		}catch(Exception e){
			logger.error("saveorupdateBusBlockedSeatTemp Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
	}

	public BusBlockedSeatTemp getBusBlockedSeatTemp(String searchKey){
		Session session = null;	
		BusBlockedSeatTemp busBlockedSeatTemp = null;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(BusBlockedSeatTemp.class);
			cr.add(Restrictions.eq("searchKey", searchKey));
			busBlockedSeatTemp = (BusBlockedSeatTemp) cr.uniqueResult();
		}catch(Exception e){
			logger.error("getBusBlockedSeatTemp Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return busBlockedSeatTemp;
	}

	public synchronized void insertOrderCustomerDetails(OrderCustomer oc) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(oc);
			transaction.commit();
		}catch(Exception e){
			logger.error("insertOrderCustomerDetails Exception", e);
		}finally {
			session.close();
		}		
	}
	public synchronized OrderCustomer updateOrderCustomerDetails(OrderCustomer oc) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(oc);
			transaction.commit();
		}catch(Exception e){
			logger.error("insertOrderCustomerDetails Exception", e);
		}finally {
			session.close();
		}	
		return oc;
	}
	public synchronized void insertBusOrderRowServiceTaxDetails(BusOrderRowServiceTax busOrderRowServiceTax)
	{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(busOrderRowServiceTax);
			transaction.commit();
		}catch(Exception e){
			logger.error("insertBusOrderRowServiceTaxDetails Exception", e);
		}
		finally {
			session.close();
		}
	}
	public synchronized void insertBusOrderRowDetails(BusOrderRow busOrderRow)
	{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(busOrderRow);
			transaction.commit();
		}catch(Exception e){
			logger.error("insertBusOrderRowDetails Exception", e);
		}
		finally {
			session.close();
		}
	}
	public synchronized  void updateBusOrderRowDetails(BusOrderRow busOrderRow){
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(busOrderRow);
			if (!transaction.wasCommitted()){
				transaction.commit();
			}

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}
	}
	public synchronized  void insertBusCommission(BusOrderRowCommission busOrderRowCommission) {		
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(busOrderRowCommission);
			transaction.commit();
		} catch (HibernateException e) {
			logger.error("insertBusCommission Exception", e);
		} catch (Exception e) {
			logger.error("insertBusCommission Exception", e);
		}
		finally {
			session.close();
		}

	}
	public synchronized void insertBusOrderCustomerDetails(BusOrderCustomerDetail busOrderCustomerDetail) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(busOrderCustomerDetail);
			transaction.commit();
		}catch(Exception e){
			logger.error("insertBusOrderCustomerDetails Exception", e);
		}finally {
			session.close();
		}		
	}
	public synchronized void insertPaymentTransactionDetails(PaymentTransaction foc)
	{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(foc);
			transaction.commit();
		}catch(Exception e){
			logger.error("insertBusOrderCustomerDetails Exception", e);
		}finally {
			session.close();
		}

	}
	public synchronized BusOrderRow getBusOrderRow(String transactionKey){
		Session session = null;	
		BusOrderRow busOrderRow = null;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(BusOrderRow.class);
			cr.add(Restrictions.eq("transactionKey", transactionKey));
			List<BusOrderRow> busOrderRowList = cr.list();
			if(busOrderRowList.size() > 0){
				busOrderRow = busOrderRowList.get(busOrderRowList.size() - 1);
			}
		}catch(Exception e){
			logger.error("getBusOrderRow Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return busOrderRow;
	}
	public synchronized BusOrderRow getBusOrderRowUsingOrderId(String orderid){
		Session session = null;	
		BusOrderRow busOrderRow = null;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(BusOrderRow.class);
			cr.add(Restrictions.eq("orderId", orderid));
			busOrderRow = (BusOrderRow) cr.uniqueResult();
		}catch(Exception e){
			logger.error("getBusOrderRow Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return busOrderRow;
	}
	public synchronized PaymentTransaction getPaymentTransactionUsingPgId(String pgId){
		Session session = null;	
		PaymentTransaction paymentTransaction = null;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(PaymentTransaction.class);
			cr.add(Restrictions.eq("refno", pgId));
			paymentTransaction = (PaymentTransaction) cr.uniqueResult();
		}catch(Exception e){
			logger.error("getBusOrderRow Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return paymentTransaction;
	}
	public synchronized PaymentTransaction getPaymentTransaction(String orderid){
		Session session = null;	
		PaymentTransaction paymentTransaction = null;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(PaymentTransaction.class);
			cr.add(Restrictions.eq("api_transaction_id", orderid));
			paymentTransaction = (PaymentTransaction) cr.uniqueResult();
		}catch(Exception e){
			logger.error("getBusOrderRow Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return paymentTransaction;
	}
	public synchronized  void updatePaymentTransactionDetails(PaymentTransaction paymentTransaction){
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(paymentTransaction);
			if (!transaction.wasCommitted()){
				transaction.commit();
			}

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}
	}
	public List<BusOrderRowMarkup> getBusOrderRowMarkup(BusOrderRow busOrderRow){
		List<BusOrderRowMarkup> busOrderRowMarkupList = new ArrayList<>();
		Session session = null;
		try{
			session = sessionFactory.openSession();
			Criteria cr = session.createCriteria(BusOrderRowMarkup.class);
			cr.add(Restrictions.eq("busOrderRow.id", busOrderRow.getId()));
			busOrderRowMarkupList = cr.list();
		}catch(Exception e){
			logger.error("getBusOrderRowMarkup", e);
		}
		return busOrderRowMarkupList;
	}

	public synchronized void saveBusOrderRowCancellation(BusOrderRowCancellation busOrderRowCancellation){
		Session session = null;
		Transaction tx = null;	
		try{			
			session = sessionFactory.openSession();		
			tx = session.beginTransaction();
			session.saveOrUpdate(busOrderRowCancellation); 
			if (!tx.wasCommitted())
				tx.commit();

		}catch(Exception e){
			logger.error("saveBusOrderRowCancellation Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
	}

	public BusBlockedSeatTemp getBusBlockedSeatTempUsingTransKey(String transactionKey){
		Session session = null;	
		BusBlockedSeatTemp busBlockedSeatTemp = null;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(BusBlockedSeatTemp.class);
			cr.add(Restrictions.eq("transactionKey", transactionKey));
			busBlockedSeatTemp = (BusBlockedSeatTemp) cr.uniqueResult();
		}catch(Exception e){
			logger.error("getBusBlockedSeatTemp Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return busBlockedSeatTemp;
	}

	public synchronized void SaveApiBookingStatus(FlightAndHotelBookApiResponse flightAndHotelBookApiResponse){
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(flightAndHotelBookApiResponse);
			transaction.commit();
			session.close();

		}catch (Exception e) {
			logger.error("Exception", e);
		}

	}
	/*public synchronized void saveBusLowFareDetail(BusFareAlertDetail busFareAlertDetail){
		Session session = null;
		Transaction tx = null;	
		try{			
			session = sessionFactory.openSession();		
			tx = session.beginTransaction();
			session.saveOrUpdate(busFareAlertDetail); 
			if (!tx.wasCommitted())
				tx.commit();

		}catch(Exception e){
			logger.error("saveBusLowFareDetail Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
	}*/

}
