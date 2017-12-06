package com.api.bulk.emp.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.car.entity.CarOrderRow;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.misellaneous.entity.MiscellaneousOrderRow;
import com.tayyarah.train.entity.TrainOrderRow;
import com.tayyarah.visa.entity.VisaOrderRow;

public class BulkInvoicePdfDaoImpl implements BulkInvoicePdfDao{
	public static final org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(BulkInvoicePdfDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<FlightOrderRow> getFlightOrderRowList(String fromInvoiceDate, String toInvoiceDate) {
		// TODO Auto-generated method stub
		List<FlightOrderRow> list=null;
		Criteria crit = null;
		Session session=null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(FlightOrderRow.class);
			Disjunction disjunction=Restrictions.disjunction();
			disjunction.add(Restrictions.eq("statusAction","Confirmed"));
			disjunction.add(Restrictions.eq("statusAction","Cancelled"));
			crit.add(getConunctionWithDbFormatDates(fromInvoiceDate, toInvoiceDate));	
			crit.add(disjunction);	
			list =crit.list();				
		}catch (HibernateException e) {	
			logger.error("HibernateException ----", e);
			throw e; 
		} finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HotelOrderRow> getHotelOrderRowList(String fromInvoiceDate, String toInvoiceDate) {
		// TODO Auto-generated method stub
		List<HotelOrderRow> list=null;
		Criteria crit = null;
		Session session=null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(HotelOrderRow.class);
			Disjunction disjunction=Restrictions.disjunction();
			disjunction.add(Restrictions.eq("statusAction","Confirmed"));
			disjunction.add(Restrictions.eq("statusAction","Cancelled"));
			crit.add(getConunctionWithDbFormatDates(fromInvoiceDate, toInvoiceDate));	
			crit.add(disjunction);	
			list =crit.list();				
		}catch (HibernateException e) {	
			logger.error("HibernateException ----", e);
			throw e; 
		} finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarOrderRow> getCarOrderRowList(String fromInvoiceDate, String toInvoiceDate) {
		// TODO Auto-generated method stub
		List<CarOrderRow> list=null;
		Criteria crit = null;
		Session session=null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(CarOrderRow.class);
			Disjunction disjunction=Restrictions.disjunction();
			disjunction.add(Restrictions.eq("statusAction","Confirmed"));
			disjunction.add(Restrictions.eq("statusAction","Cancelled"));
			crit.add(getConunctionWithDbFormatDates(fromInvoiceDate, toInvoiceDate));	
			crit.add(disjunction);	
			list =crit.list();
		}catch (HibernateException e) {	
			logger.error("HibernateException ----", e);
			throw e; 
		} finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusOrderRow> getBusOrderRowList(String fromInvoiceDate, String toInvoiceDate) {
		// TODO Auto-generated method stub
		List<BusOrderRow> list=null;
		Criteria crit = null;
		Session session=null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(BusOrderRow.class);
			Disjunction disjunction=Restrictions.disjunction();
			disjunction.add(Restrictions.eq("statusAction","Confirmed"));
			disjunction.add(Restrictions.eq("statusAction","Cancelled"));
			crit.add(getConunctionWithDbFormatDates(fromInvoiceDate, toInvoiceDate));	
			crit.add(disjunction);	
			list =crit.list();				
		}catch (HibernateException e) {	
			logger.error("HibernateException ----", e);
			throw e; 
		} finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrainOrderRow> getTrainOrderRowList(String fromInvoiceDate, String toInvoiceDate) {
		// TODO Auto-generated method stub
		List<TrainOrderRow> list=null;
		Criteria crit = null;
		Session session=null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(TrainOrderRow.class);
			Disjunction disjunction=Restrictions.disjunction();
			disjunction.add(Restrictions.eq("statusAction","Confirmed"));
			disjunction.add(Restrictions.eq("statusAction","Cancelled"));
			crit.add(getConunctionWithDbFormatDates(fromInvoiceDate, toInvoiceDate));	
			crit.add(disjunction);	
			list =crit.list();				
		}catch (HibernateException e) {	
			logger.error("HibernateException ----", e);
			throw e; 
		} finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisaOrderRow> getVisaOrderRowList(String fromInvoiceDate, String toInvoiceDate) {
		// TODO Auto-generated method stub
		List<VisaOrderRow> list=null;
		Criteria crit = null;
		Session session=null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(VisaOrderRow.class);
			Disjunction disjunction=Restrictions.disjunction();
			disjunction.add(Restrictions.eq("statusAction","Confirmed"));
			disjunction.add(Restrictions.eq("statusAction","Cancelled"));
			crit.add(getConunctionWithDbFormatDates(fromInvoiceDate, toInvoiceDate));	
			crit.add(disjunction);	
			list =crit.list();				
		}catch (HibernateException e) {	
			logger.error("HibernateException ----", e);
			throw e; 
		} finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InsuranceOrderRow> getInsuranceOrderRowList(String fromInvoiceDate, String toInvoiceDate) {
		// TODO Auto-generated method stub
		List<InsuranceOrderRow> list=null;
		Criteria crit = null;
		Session session=null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(InsuranceOrderRow.class);
			Disjunction disjunction=Restrictions.disjunction();
			disjunction.add(Restrictions.eq("statusAction","Confirmed"));
			disjunction.add(Restrictions.eq("statusAction","Cancelled"));
			crit.add(getConunctionWithDbFormatDates(fromInvoiceDate, toInvoiceDate));	
			crit.add(disjunction);	
			list =crit.list();				
		}catch (HibernateException e) {	
			logger.error("HibernateException ----", e);
			throw e; 
		} finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MiscellaneousOrderRow> getMiscellaneousOrderRowList(String fromInvoiceDate, String toInvoiceDate) {
		// TODO Auto-generated method stub
		List<MiscellaneousOrderRow> list=null;
		Criteria crit = null;
		Session session=null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(MiscellaneousOrderRow.class);
			Disjunction disjunction=Restrictions.disjunction();
			disjunction.add(Restrictions.eq("statusAction","Confirmed"));
			disjunction.add(Restrictions.eq("statusAction","Cancelled"));
			crit.add(getConunctionWithDbFormatDates(fromInvoiceDate, toInvoiceDate));	
			crit.add(disjunction);	
			list =crit.list();				
		}catch (HibernateException e) {	
			logger.error("HibernateException ----", e);
			throw e; 
		} finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return list;
	}

	@Override
	public Conjunction  getConunctionWithDbFormatDates(String fromInvoiceDate,String toInvoiceDate) {
		// TODO Auto-generated method stub
		Conjunction  conjunction=Restrictions.conjunction();
		SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");	
		DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate=null;
		Date toDate=null;
		try{
			fromDate = originalFormat.parse(fromInvoiceDate);
			String formattedFromDate = targetFormat.format(fromDate); 
			fromDate = targetFormat.parse(formattedFromDate);
			toDate = originalFormat.parse(toInvoiceDate);
			String formattedToDate = targetFormat.format(toDate); 
			toDate = targetFormat.parse(formattedToDate);
			fromDate = new Date(fromDate.getTime()+ TimeUnit.SECONDS.toMillis(1));
			toDate = new Date(toDate.getTime()+ TimeUnit.DAYS.toMillis(1));
			conjunction.add(Restrictions.ge("createdAt", fromDate));
			conjunction.add(Restrictions.lt("createdAt", toDate));
			
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conjunction;
	}

}
