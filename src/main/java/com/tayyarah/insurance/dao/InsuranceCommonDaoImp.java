package com.tayyarah.insurance.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.FlightAndHotelBookApiResponse;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.util.encryptions;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.insurance.entity.InsuranceOrderCustomerDetail;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.insurance.entity.InsurancePolicyTemp;
import com.tayyarah.insurance.entity.TrawellTagPremiumChart;
import com.tayyarah.insurance.exception.InsuranceErrorMessages;
import com.tayyarah.insurance.exception.InsuranceException;
import com.tayyarah.insurance.util.InsuranceCommonUtil;




public class InsuranceCommonDaoImp implements InsuranceCommonDao{
	public static final Logger logger = Logger.getLogger(InsuranceCommonDaoImp.class);
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
				throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.NOTFOUND_APPKEY.getErrorMessage());
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new InsuranceException(ErrorCodeCustomerEnum.HibernateException,InsuranceErrorMessages.DB_ERROR.getErrorMessage());
		}catch (NumberFormatException e) {
			e.printStackTrace();
			throw new InsuranceException(ErrorCodeCustomerEnum.NumberFormatException,InsuranceErrorMessages.INVALID_APPKEY.getErrorMessage());
		}catch (Exception e) {
			e.printStackTrace();

			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.INVALID_APPKEY.getErrorMessage());
		}
		return decAppkey;
	}
	public TrawellTagPremiumChart getTrawellTagPremiumChart(String dayLimit,String ageLimit,int planId){
		Session session = null;	
		TrawellTagPremiumChart trawellTagPremiumChart = null;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(TrawellTagPremiumChart.class);			
			cr.add(Restrictions.eq("trawellTagPlan.id", InsuranceCommonUtil.getPlanId(planId)));			
			List<TrawellTagPremiumChart> trawellTagPremiumChartList = cr.list();
		
			// Remove Duplicate days and Sort 
			SortedSet<Integer> daysarray = new TreeSet<Integer>();
			for (TrawellTagPremiumChart trawellTagPremiumChart2 : trawellTagPremiumChartList) {
				daysarray.add(trawellTagPremiumChart2.getDayLimit());
			}			
			// Find Closest Premium Day
			int closestday = InsuranceCommonUtil.findClosestPremiumDay(daysarray, Integer.parseInt(dayLimit));			
			for (TrawellTagPremiumChart trawellTagPremiumChart2 : trawellTagPremiumChartList) {
				if(Integer.parseInt(ageLimit) <= trawellTagPremiumChart2.getAgeLimit()){
					if(closestday == trawellTagPremiumChart2.getDayLimit()){
						trawellTagPremiumChart = trawellTagPremiumChart2;
						break;
					}
					
				}
			}
			
		}catch(Exception e){

		}
		return trawellTagPremiumChart;
	}
	
	
	public long getLastInsurancePolicyTempId(){
		Session session = null;	
		long lastInsertedId = 0l;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(InsurancePolicyTemp.class);
			cr.addOrder(Order.desc("id"));
			cr.setMaxResults(1);
			InsurancePolicyTemp insurancePolicyTemp = (InsurancePolicyTemp) cr.uniqueResult(); 
			if(insurancePolicyTemp != null)
				lastInsertedId = insurancePolicyTemp.getId();
		}catch(Exception e){
			logger.error("getLastInsurancePolicyTempId Exception", e);
		}finally{	
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return lastInsertedId;
	}
	public void saveorupdateInsuranceSearchTemp(InsurancePolicyTemp insurancePolicyTemp){
		Session session = null;
		Transaction tx = null;	
		try{
			session = sessionFactory.openSession();			
			Criteria cr = session.createCriteria(InsurancePolicyTemp.class);
			cr.add(Restrictions.eq("transactionKey", insurancePolicyTemp.getTransactionKey()));
			InsurancePolicyTemp insurancePolicyTempDB = (InsurancePolicyTemp) cr.uniqueResult();
			if(session != null && session.isOpen())
			{				
				session.close(); 
				session = null;
			}
			if(insurancePolicyTempDB != null){
				try{
					session = sessionFactory.openSession();			
					tx = session.beginTransaction();
					insurancePolicyTempDB.setInsurancePolicyData(insurancePolicyTemp.getInsurancePolicyData());
					session.update(insurancePolicyTempDB);   
					if (!tx.wasCommitted())
						tx.commit();

				}catch (Exception e) {
					if (tx!=null) tx.rollback();		
				}

			}else{
				session = sessionFactory.openSession();		
				session.saveOrUpdate(insurancePolicyTemp); 
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
	public synchronized InsuranceOrderRow insertInsuranceOrderRowDetails(InsuranceOrderRow insuranceOrderRow)
	{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(insuranceOrderRow);
			transaction.commit();
		}catch(Exception e){
			logger.error("insertInsuranceOrderRowDetails Exception", e);
		}
		finally {
			session.close();
		}
		return insuranceOrderRow;
	}
	public synchronized  void updateInsuranceOrderRowDetails(InsuranceOrderRow insuranceOrderRow){
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(insuranceOrderRow);
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
			logger.error("insert Insurance PaymentTransactionDetails Exception", e);
		}finally {
			session.close();
		}

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
	public synchronized void insertInsuranceOrderCustomerDetails(InsuranceOrderCustomerDetail insuranceOrderCustomerDetail) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(insuranceOrderCustomerDetail);
			transaction.commit();
		}catch(Exception e){
			logger.error("insertInsuranceOrderCustomerDetails Exception", e);
		}finally {
			session.close();
		}		
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
	public synchronized long getInsuranceOrderRowIdByOrderId(String orderId)
	{
		Session session = null;
		long rowId = -1;
		try{
			session = sessionFactory.openSession();		
			Criteria cr = session.createCriteria(InsuranceOrderRow.class);
			cr.add(Restrictions.eq("orderId",orderId));	
			InsuranceOrderRow insuranceOrderRow = (InsuranceOrderRow) cr.uniqueResult();
			if(insuranceOrderRow != null){
				rowId = insuranceOrderRow.getId();
			}
			
		}catch(Exception e){
			logger.error("insertInsuranceOrderRowDetails Exception", e);
		}
		finally {
			session.close();
		}
		return rowId;
	}
}
