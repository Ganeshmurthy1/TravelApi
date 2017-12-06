package com.tayyarah.flight.quotation.dao;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.ApiProviderPaymentTransaction;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.quotation.entity.FlightQuotationHistory;
import com.tayyarah.flight.quotation.entity.FlightQuotationPerTravelRequestSchema;
import com.tayyarah.flight.quotation.entity.FlightTravelRequest;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestConnectingFlightTripDetail;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestQuotation;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestTripDetail;
import com.tayyarah.flight.quotation.model.FlightQuotationSchema;

public class FlightTravelRequestDao {
	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(FlightTravelRequestDao.class);

	public FlightTravelRequest insertFlightQuotationRowDetails(FlightTravelRequest flightQuotationRow){
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(flightQuotationRow);
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
		return flightQuotationRow;
	}

	public List<FlightTravelRequest>  loadFlightQuotationRowList(){	
		Session session = null;
		List<FlightTravelRequest> list = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FlightTravelRequest.class);
			list= criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return list;
	}

	public FlightTravelRequest getFlightQuotationRequestDetails(Long id){
		FlightTravelRequest flightTravelRequest=null;
		Session session= null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FlightTravelRequest.class);
			criteria.add(Restrictions.eq("id", id));
			flightTravelRequest= (FlightTravelRequest) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return flightTravelRequest;
	}

	public boolean insertFlightQuotationList(List<FlightTravelRequestQuotation> flightTravelRequestQuotation,FlightTravelRequest flightTravelRequest){
		boolean isInserted = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if(flightTravelRequestQuotation!=null && flightTravelRequestQuotation.size()>0){
				for(FlightTravelRequestQuotation flightQuotation:flightTravelRequestQuotation){
					flightQuotation.setCreatedAt(new Timestamp(new Date().getTime()));
					flightQuotation.setFlightTravelRequest(flightTravelRequest);
					session.save(flightQuotation);
				}
			}
			transaction.commit();
			isInserted=true;
		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
				isInserted=false;
			}
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return isInserted;
	}

	public List<FlightTravelRequestQuotation> getFlightRequestTravelQuotationList(Long flightQuotationRequestId) {
		List<FlightTravelRequestQuotation> list=null;
		Session session=null;
		try{
			session = sessionFactory.openSession();
			String sql = "from FlightTravelRequestQuotation com where com.flightTravelRequest.id=:id";
			Query query = session.createQuery(sql);
			query.setParameter("id", flightQuotationRequestId);
			list= query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}

		return list;
	}

	public FlightTravelRequestQuotation getFlightTravelRequestQuotation(Long flightQuotationId) {
		FlightTravelRequestQuotation flightTravelRequestQuotation = null;
		Session session=null;
		try{
			session = sessionFactory.openSession();
			String sql = "from FlightTravelRequestQuotation quot where quot.id=:id";
			Query query = session.createQuery(sql);
			query.setParameter("id", flightQuotationId);
			flightTravelRequestQuotation = (FlightTravelRequestQuotation) query.uniqueResult();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return flightTravelRequestQuotation;
	}
	
	public List<FlightTravelRequestTripDetail> getFlightTravelRequestTripDetailList(Long flightQuotationId) {
		List<FlightTravelRequestTripDetail> flightTravelRequestTripDetaillist = new ArrayList<>();
		Session session=null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(FlightTravelRequestTripDetail.class);
			criteria.add(Restrictions.eq("flightTravelRequestQuotationId", flightQuotationId));
			flightTravelRequestTripDetaillist = criteria.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return flightTravelRequestTripDetaillist;
	}
	
	public List<FlightTravelRequestConnectingFlightTripDetail> getFlightTravelRequestConnectingFlightTripDetailList(Long tripId) {
		List<FlightTravelRequestConnectingFlightTripDetail> flightTravelRequestConnectingFlightTripDetaillist = new ArrayList<>();
		Session session=null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(FlightTravelRequestConnectingFlightTripDetail.class);
			criteria.add(Restrictions.eq("flightTravelRequestTripDetail.id", tripId));
			flightTravelRequestConnectingFlightTripDetaillist = criteria.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return flightTravelRequestConnectingFlightTripDetaillist;
	}

	public List<FlightTravelRequestQuotation> getFlightRequestTravelQuotationList(List<Long> quotationIdList) {
		List<FlightTravelRequestQuotation> list  = new ArrayList<FlightTravelRequestQuotation>();	
		Session sess = null;		
		try{	
			sess = sessionFactory.openSession();
			Criteria criteria=sess.createCriteria(FlightTravelRequestQuotation.class);			
			criteria.add(Restrictions.in("id", quotationIdList));		
			list = criteria.list();	
			
		}catch (HibernateException e) {			
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
				sess.close(); 
		}
		return list;
	}

	public FlightTravelRequest getFlightTravelRequest(Long flightRequestId) {	
		FlightTravelRequest flightTravelRequest = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();
			String sql = "from FlightTravelRequest hr where hr.id=:id";
			Query query = session.createQuery(sql);
			query.setParameter("id", flightRequestId);
			flightTravelRequest = (FlightTravelRequest) query.uniqueResult();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return flightTravelRequest;
	}


	public void flightRequestQuotationUpdate(long flightTravelRequestQuotationid,FlightOrderRow flightOrderRow,FlightOrderRow returnFlightOrderRow) {
		Session session = null;
		Transaction transaction = null;
		FlightTravelRequestQuotation  flightTravelRequestQuotationUpdate = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			flightTravelRequestQuotationUpdate = (FlightTravelRequestQuotation) session.get(FlightTravelRequestQuotation.class,flightTravelRequestQuotationid);
			if(returnFlightOrderRow!=null){
				flightTravelRequestQuotationUpdate.setOrderRowId(flightOrderRow.getId());
				flightTravelRequestQuotationUpdate.setReturnOrderRowId(returnFlightOrderRow.getId());	
				flightTravelRequestQuotationUpdate.setBooked(true);
				flightTravelRequestQuotationUpdate.setHide(true);
				flightTravelRequestQuotationUpdate.setTotalAmount(flightOrderRow.getFinalPrice().add(returnFlightOrderRow.getFinalPrice()));
				flightTravelRequestQuotationUpdate.setTaxAmount(flightOrderRow.getTotalTaxes().add(returnFlightOrderRow.getTotalTaxes()));
				flightTravelRequestQuotationUpdate.setBaseAmount(flightOrderRow.getPrice().add(returnFlightOrderRow.getPrice()));				
				
			}else{
				flightTravelRequestQuotationUpdate.setOrderRowId(flightOrderRow.getId());
				flightTravelRequestQuotationUpdate.setReturnOrderRowId(0);	
				flightTravelRequestQuotationUpdate.setBooked(true);
				flightTravelRequestQuotationUpdate.setHide(true);
				flightTravelRequestQuotationUpdate.setTotalAmount(flightOrderRow.getFinalPrice());
				flightTravelRequestQuotationUpdate.setTaxAmount(flightOrderRow.getTotalTaxes());
				flightTravelRequestQuotationUpdate.setBaseAmount(flightOrderRow.getPrice());
			}			
			session.saveOrUpdate(flightTravelRequestQuotationUpdate);
			transaction.commit();

		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			logger.error("flightRequestQuotationUpdate " + e.getMessage());
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}		
	}

	public FlightTravelRequestQuotation updateFlightRequestQuotationWithOrderId(Long flightOrderRowId, Long flightRequestQuotationId) {
		Session session= null;
		Transaction transaction=null;
		FlightTravelRequestQuotation  flightTravelRequestQuotationUpdate=null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			flightTravelRequestQuotationUpdate=(FlightTravelRequestQuotation) session.get(FlightTravelRequestQuotation.class,flightRequestQuotationId);
			flightTravelRequestQuotationUpdate.setBooked(true);
			flightTravelRequestQuotationUpdate.setOrderRowId(flightOrderRowId);
			session.saveOrUpdate(flightTravelRequestQuotationUpdate);
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
		return flightTravelRequestQuotationUpdate;
	}
	
	public  List<FlightQuotationSchema> getFlightQuotationSchemaList(Long companyId){
		Session session= null;
		List<FlightQuotationSchema> flightQuotationSchemalist=new LinkedList<>(); 
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FlightQuotationSchema.class);
			criteria.add(Restrictions.eq("companyId", companyId));
			FlightQuotationSchema newObj= (FlightQuotationSchema) criteria.uniqueResult(); 
			if(newObj!=null){
				String date=newObj.getSchemaData(); 
				StringTokenizer tok=new StringTokenizer(date, "[<\\>]");
				List<String> addList=  new LinkedList<>();
				while(tok.hasMoreTokens()){
					String token=tok.nextToken();
					addList.add(token.trim());
				}
				Iterator<String> itr=addList.iterator();
				String[] partsTest=null;
				while (itr.hasNext()) {
					String s=itr.next();
					partsTest=s.split(":");
					flightQuotationSchemalist.add(new FlightQuotationSchema(partsTest[0], partsTest[1],Integer.parseInt(partsTest[2]), partsTest[3]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return flightQuotationSchemalist;
	}
	
	public  FlightQuotationPerTravelRequestSchema geFlightQuotationPerTravelRequestSchema(Long travelRequestId){
		Session session= null;
		FlightQuotationPerTravelRequestSchema flightQuotationPerTravelRequestSchema = null; 
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FlightQuotationPerTravelRequestSchema.class);
			criteria.add(Restrictions.eq("travelRequestId", travelRequestId));
			flightQuotationPerTravelRequestSchema = (FlightQuotationPerTravelRequestSchema) criteria.uniqueResult(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return flightQuotationPerTravelRequestSchema;
	}
	
	public  FlightQuotationHistory getFlightQuotationHistory(Long id) {	
		Session session= null;
		FlightQuotationHistory flightQuotationHistory=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FlightQuotationHistory.class);
			criteria.add(Restrictions.eq("id", id));
			flightQuotationHistory=(FlightQuotationHistory) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return flightQuotationHistory;
	}
	
	public ApiProviderPaymentTransaction insertSupplierPaymentTransactionInfo(ApiProviderPaymentTransaction apiProviderPaymentTransaction) {
		Session session= null;
		Transaction transaction=null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(apiProviderPaymentTransaction);
			transaction.commit();

		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			logger.error("HibernateException---------------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return apiProviderPaymentTransaction;
	}
}
