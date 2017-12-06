package com.tayyarah.email.dao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderCustomerSSR;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.insurance.entity.InsuranceOrderRow;




public class FlightOrderRowEmailDaoImp implements FlightOrderRowEmailDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory  sessionFactory;

	@Override
	public List<FlightOrderRow> flightOrderRows() throws HibernateException {
		List<FlightOrderRow> list=null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();			
			Criteria criteria=session.createCriteria(FlightOrderRow.class);
			list = criteria.list();
		}catch (HibernateException e) {		

		}finally {			
			session.close();
		}
		return list;
	}
	@Override
	public FlightOrderRow flightOrderRowByOrderId(String orderId)
			throws HibernateException {
		FlightOrderRow flightOrderRowObj=null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();		
			Criteria criteria=session.createCriteria(FlightOrderRow.class);
			criteria.add(Restrictions.eq("orderId", orderId));
			flightOrderRowObj = (FlightOrderRow)criteria.uniqueResult();
		}catch (HibernateException e) {
			throw e; 
		}finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return flightOrderRowObj;	
	}
	@Override
	public List<FlightOrderRow> flightOrderRowsPendingInvoices()
			throws HibernateException {
		List<FlightOrderRow> list = null;
		Session session = null;	
		try{
			session = sessionFactory.openSession();		
			Criteria criteria=session.createCriteria(FlightOrderRow.class);
			list = criteria.list();
		}catch (HibernateException e) {		

		}finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<FlightOrderRow> updateFlightOrderRowsEmailStatus(
			List<FlightOrderRow> flightorderrows) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<FlightOrderTripDetail> flightTrips(FlightOrderRow flightOrderRow)
			throws HibernateException {
		// TODO Auto-generated method stub
		List<FlightOrderTripDetail> list = null;
		Session session = null;	
		try{
			session = sessionFactory.openSession();			
			Criteria criteria=session.createCriteria(FlightOrderTripDetail.class);
			criteria.add(Restrictions.eq("flightOrderRow.id", flightOrderRow.getId()));
			list = criteria.list();
		}catch (HibernateException e) {

		}finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}
	@Override
	public List<FlightOrderCustomerPriceBreakup> flightPriceBreakup(FlightOrderRow priceFlightOrderRow) throws HibernateException {
		// TODO Auto-generated method stub
		List<FlightOrderCustomerPriceBreakup> pricelist = null;
		Session session = null;	
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(FlightOrderCustomerPriceBreakup.class);
			criteria.add(Restrictions.eq("flightOrderRow.id", priceFlightOrderRow.getId()));
			pricelist = criteria.list();
			return pricelist;
		}catch (HibernateException e) {
			throw e; 
		}finally {
			if(session != null && session.isOpen())
				session.close();
		}
	}
	@Override
	public List<FlightOrderCustomerSSR> flightCustomerSSR(Long customerid) throws HibernateException {
		// TODO Auto-generated method stub
		List<FlightOrderCustomerSSR> ssrdetails = null;
		Session session = null;	
		try{
			session = sessionFactory.openSession();    		
			Criteria criteria=session.createCriteria(FlightOrderCustomerSSR.class);
			criteria.add(Restrictions.eq("flightCustomer.id", customerid));
			ssrdetails = criteria.list();			 

		}catch (HibernateException e) {			

		}finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return ssrdetails;
	}
	@Override
	public List<FlightOrderCustomer> customerDeatails(
			FlightOrderRow FlightOrderRow) throws HibernateException {
		// TODO Auto-generated method stub
		List<FlightOrderCustomer> cusList = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria=session.createCriteria(FlightOrderCustomer.class);
			criteria.add(Restrictions.eq("flightOrderRow.id", FlightOrderRow.getId()));
			cusList = criteria.list();
		}catch (HibernateException e) {			

		}finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return cusList;
	}

	@Override
	public FlightOrderRow flightOrderRowById(String id)
			throws HibernateException {
		FlightOrderRow flightOrderRowObj=null;
		Session session = null;	
		try{
			session = sessionFactory.openSession();			
			Criteria criteria=session.createCriteria(FlightOrderRow.class);
			criteria.add(Restrictions.eq("id",Long.parseLong(id)));
			flightOrderRowObj = (FlightOrderRow)criteria.uniqueResult();
		}catch (HibernateException e) {			
		}finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return flightOrderRowObj;	

	}
 
	@Override
	public List<FlightOrderRow> getStatusFailedAction( ) throws HibernateException {
		List<FlightOrderRow> list=null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();			
			Criteria criteria=session.createCriteria(FlightOrderRow.class);
			Conjunction conjuction= Restrictions.conjunction();
			conjuction.add(Restrictions.eq("statusAction","Failed"));
			conjuction.add(Restrictions.eq("mailSendStatus",false));
			criteria.add(conjuction);
			list = criteria.list(); 
		}catch (HibernateException e) {		

		}finally {			
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
		
	}
	@Override
	public InsuranceOrderRow insuranceOrderRowById(long rowId)
			throws HibernateException {
		InsuranceOrderRow insuranceOrderRow = null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();		
			Criteria criteria=session.createCriteria(InsuranceOrderRow.class);
			criteria.add(Restrictions.eq("id", rowId));
			insuranceOrderRow = (InsuranceOrderRow)criteria.uniqueResult();
		}catch (HibernateException e) {
			throw e; 
		}finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return insuranceOrderRow;	
	}
}
