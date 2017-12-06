package com.tayyarah.hotel.quotation.dao;
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
import com.tayyarah.common.util.enums.TravelRequestStatusEnum;
import com.tayyarah.hotel.quotation.entity.HotelQuotationHistory;
import com.tayyarah.hotel.quotation.entity.HotelQuotationPerTravelRequestSchema;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequest;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequestQuotation;
import com.tayyarah.hotel.quotation.model.HotelQuotationSchema;


public class HotelTravelRequestDao {
	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(HotelTravelRequestDao.class);

	public HotelTravelRequest insertHotelQuotationRowDetails(HotelTravelRequest hotelQuotationRow){
		Session session = null;
		Transaction transaction=null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(hotelQuotationRow);
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
		return hotelQuotationRow;
	}

	public List<HotelTravelRequest>  loadHotelQuotationRowList(){
		Session session = null;
		List<HotelTravelRequest> list = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(HotelTravelRequest.class);
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

	public HotelTravelRequest getHotelQuotationRequestDetails(Long id){
		HotelTravelRequest HotelTravelRequest = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(HotelTravelRequest.class);
			criteria.add(Restrictions.eq("id", id));
			HotelTravelRequest= (HotelTravelRequest) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return HotelTravelRequest;
	}

	public boolean insertHotelQuotationList(List<HotelTravelRequestQuotation> hotelTravelRequestQuotation,HotelTravelRequest HotelTravelRequest){
		boolean isInserted = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if(hotelTravelRequestQuotation!=null && hotelTravelRequestQuotation.size()>0){
				for(HotelTravelRequestQuotation hotelQuotation:hotelTravelRequestQuotation){
					hotelQuotation.setCreatedAt(new Timestamp(new Date().getTime()));
					hotelQuotation.setHotelTravelRequest(HotelTravelRequest);
					session.save(hotelQuotation);
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
	
	public List<HotelTravelRequestQuotation> getHotelRequestTravelQuotationList(Long hotelQuotationRequestId) {
		List<HotelTravelRequestQuotation> list = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();
			String sql = "from HotelTravelRequestQuotation com where com.hotelTravelRequest.id=:id";
			Query query = session.createQuery(sql);
			query.setParameter("id", hotelQuotationRequestId);
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

	public HotelTravelRequestQuotation getHotelRequestTravelQuotation(Long hotelQuotationId) {
		HotelTravelRequestQuotation hotelTravelRequestQuotation = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();
			String sql = "from HotelTravelRequestQuotation quot where quot.id=:id";
			Query query = session.createQuery(sql);
			query.setParameter("id", hotelQuotationId);
			hotelTravelRequestQuotation = (HotelTravelRequestQuotation) query.uniqueResult();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return hotelTravelRequestQuotation;
	}

	public List<HotelTravelRequestQuotation> getHotelRequestTravelQuotationList(List<Long> quotationIdList) {
		HotelTravelRequestQuotation hotelTravelRequestQuotation = null;
		List<HotelTravelRequestQuotation> list  = new ArrayList<HotelTravelRequestQuotation>();		
		Session sess = null;	
		try{			
			sess = sessionFactory.openSession();
			Criteria criteria=sess.createCriteria(HotelTravelRequestQuotation.class);
			criteria.add(Restrictions.in("id", quotationIdList));				
			list = criteria.list();
			
		}catch (HibernateException e) {			
			logger.info("########################## DB Email verification update call error" +e);			
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
				sess.close(); 
		}
		return list;
	}	

	public List<HotelQuotationHistory> getHoteQuotationHistoryList() {
		List<HotelQuotationHistory> list = null;
		Session sess = null;		
		try{			
			sess = sessionFactory.openSession();
			Criteria criteria=sess.createCriteria(HotelQuotationHistory.class);
			criteria.add(Restrictions.eq("emailStatusId",0));				
			list = criteria.list();	

		}catch (HibernateException e) {			
			logger.info("########################## DB Email verification update call error " +e);			
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
				sess.close(); 
		}
		return list;
	}
	

	public HotelTravelRequest getHotelTravelRequest(Long hotelRequestId) {	
		HotelTravelRequest HotelTravelRequest = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();
			String sql = "from HotelTravelRequest hr where hr.id=:id";
			Query query = session.createQuery(sql);
			query.setParameter("id", hotelRequestId);
			HotelTravelRequest = (HotelTravelRequest) query.uniqueResult();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return HotelTravelRequest;
	}

	public HotelTravelRequestQuotation hotelRequestQuotationUpdate(HotelTravelRequestQuotation hotelTravelRequestQuotation) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			HotelTravelRequestQuotation  hotelTravelRequestQuotationUpdate=(HotelTravelRequestQuotation) session.get(HotelTravelRequestQuotation.class,hotelTravelRequestQuotation.getId());
			hotelTravelRequestQuotationUpdate.setHotelName(hotelTravelRequestQuotation.getHotelName());
			hotelTravelRequestQuotationUpdate.setSellRate(hotelTravelRequestQuotation.getSellRate());
			session.saveOrUpdate(hotelTravelRequestQuotationUpdate);
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
		return hotelTravelRequestQuotation;
	}

	public HotelTravelRequestQuotation updateHotelRequestQuotationWithOrderId(Long hotelOrderRowId, Long hotelRequestQuotationId) {
		Session session= null;
		Transaction transaction=null;
		HotelTravelRequestQuotation  hotelTravelRequestQuotationUpdate=null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			hotelTravelRequestQuotationUpdate=(HotelTravelRequestQuotation) session.get(HotelTravelRequestQuotation.class,hotelRequestQuotationId);
			hotelTravelRequestQuotationUpdate.setBooked(true);
			hotelTravelRequestQuotationUpdate.setHide(true);
			hotelTravelRequestQuotationUpdate.setOrderRowId(hotelOrderRowId);
			hotelTravelRequestQuotationUpdate.setStatusId(TravelRequestStatusEnum.getTravelRequestStatusEnumStatusId("BOOKED"));			
			session.saveOrUpdate(hotelTravelRequestQuotationUpdate);
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
		return hotelTravelRequestQuotationUpdate;

	}
	public  List<HotelQuotationSchema> getHotelQuotationSchemaList(Long companyId){
		Session session= null;
		List<HotelQuotationSchema> hotelQuotationSchemalist=new LinkedList<>(); 
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(HotelQuotationSchema.class);
			criteria.add(Restrictions.eq("companyId", companyId));
			HotelQuotationSchema newObj= (HotelQuotationSchema) criteria.uniqueResult(); 
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
					hotelQuotationSchemalist.add(new HotelQuotationSchema(partsTest[0], partsTest[1],Integer.parseInt(partsTest[2]), partsTest[3]));
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
		return hotelQuotationSchemalist;
	}
	public  HotelQuotationPerTravelRequestSchema geHotelQuotationPerTravelRequestSchema(Long travelRequestId){
		Session session= null;
		HotelQuotationPerTravelRequestSchema hotelQuotationPerTravelRequestSchema = null; 
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(HotelQuotationPerTravelRequestSchema.class);
			criteria.add(Restrictions.eq("travelRequestId", travelRequestId));
			hotelQuotationPerTravelRequestSchema = (HotelQuotationPerTravelRequestSchema) criteria.uniqueResult(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return hotelQuotationPerTravelRequestSchema;
	}


	public void updateHotelQuotationHistoryEmailStatus(HotelQuotationHistory hotelQuotationHistory) {	
		Session session= null;
		Transaction transaction=null;
		HotelQuotationHistory hotelQuotationHistoryUpdate=null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			hotelQuotationHistoryUpdate=(HotelQuotationHistory) session.get(HotelQuotationHistory.class,hotelQuotationHistory.getId());
			hotelQuotationHistoryUpdate.setEmailStatusId(hotelQuotationHistory.getEmailStatusId());
			hotelQuotationHistoryUpdate.setMessage(hotelQuotationHistory.getMessage());
			session.saveOrUpdate(hotelQuotationHistoryUpdate);
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
	}

	public  HotelQuotationHistory getHotelQuotationHistory(Long id) {	
		Session session= null;
		HotelQuotationHistory hotelQuotationHistory=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(HotelQuotationHistory.class);
			criteria.add(Restrictions.eq("id", id));
			hotelQuotationHistory=(HotelQuotationHistory) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return hotelQuotationHistory;

	}
	public HotelTravelRequestQuotation getHotelQuotationDetails(Long hotelRowId) {
		Session  session = null;
		HotelTravelRequestQuotation hotelQuotation=null;
		try
		{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(HotelTravelRequestQuotation.class);
			criteria.add(Restrictions.eq("orderRowId",hotelRowId));
			hotelQuotation=(HotelTravelRequestQuotation) criteria.uniqueResult();
		}
		catch (HibernateException e) {
			logger.error("HibernateException---------"+e.getMessage());
		}finally {
			if(session!=null && session.isOpen())
				session.close(); 
		}
		return hotelQuotation;

	}
	public ApiProviderPaymentTransaction insertSupplierPaymentTransactionInfo(ApiProviderPaymentTransaction apiProviderPaymentTransaction) {
		Session session = null;
		Transaction transaction = null;
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
