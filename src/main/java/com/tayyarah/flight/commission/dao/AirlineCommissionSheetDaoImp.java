package com.tayyarah.flight.commission.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tayyarah.flight.commission.entity.AirlineCommissionBlock;
import com.tayyarah.flight.commission.entity.AirlineCommissionCompanyBlock;
import com.tayyarah.flight.commission.entity.AirlineCommissionMasterSheet;
import com.tayyarah.flight.commission.entity.AirlineCommissionSheet;
import com.tayyarah.flight.commission.model.CommissionActionStatus;



public class AirlineCommissionSheetDaoImp implements AirlineCommissionSheetDao {

	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(AirlineCommissionSheetDaoImp.class);
	
	@Override
	public List<AirlineCommissionSheet> getAirlineCommissionSheet(
			Long sheetId) throws HibernateException {
		List<AirlineCommissionSheet> list  = new ArrayList<AirlineCommissionSheet>();	
		Session sess = null;			
		logger.info("########################## DB AirlineCommissionSheetRow retriving call");
		try{
			sess =  sessionFactory.openSession();					
			Criteria cr = sess.createCriteria(AirlineCommissionSheet.class);
			cr.add(Restrictions.eq("sheetId", sheetId));
			list = cr.list();
			
		}catch (HibernateException e) {		
			logger.info("########################## AirlineCommissionSheetRow....error " +e.getMessage());
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return list;
	}

	@Override
	public AirlineCommissionSheet getAirlineCommissionSheetCommons(
			Long sheetId) throws HibernateException {
		AirlineCommissionSheet airlineCommissionSheetRow  = new AirlineCommissionSheet();	
		Session sess = null;			
		try{
			sess =  sessionFactory.openSession();
			Criteria cr = sess.createCriteria(AirlineCommissionSheet.class);
			cr.add(Restrictions.eq("sheetId", sheetId));		
			cr.setProjection(Projections.projectionList()							
					.add(Projections.property("sheetId"), "sheetId")						
					.add(Projections.property("dtValidFrom"), "dtValidFrom")
					.add(Projections.property("dtValidTill"), "dtValidTill")
					.add(Projections.property("dealSheetVersion"), "dealSheetVersion")
					.add(Projections.property("createdAt"), "createdAt")
					.add(Projections.property("createdByUserId"), "createdByUserId")
					)
					.setResultTransformer(Transformers.aliasToBean(AirlineCommissionSheet.class));
			List<AirlineCommissionSheet> list  = cr.list();	
			if(list != null && list.size()>0)
				airlineCommissionSheetRow =  list.get(0);

		}catch (HibernateException e) {		
			logger.info("########################## AirlineCommissionSheetRow....error " +e.getMessage());
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return airlineCommissionSheetRow;
	}


	@Override
	public AirlineCommissionSheet getAirlineCommissionSheetRemark(
			Long sheetId, String iataCode, boolean isPlbRemark) throws HibernateException {
		AirlineCommissionSheet airlineCommissionSheetRow  = new AirlineCommissionSheet();	
		Session sess = null;
		try{
			sess =  sessionFactory.openSession();	
			Criteria cr = sess.createCriteria(AirlineCommissionSheet.class);			   
			LogicalExpression logicalExpression  = Restrictions.and(Restrictions.eq("sheetId", sheetId), 
					Restrictions.eq("iataCode", iataCode));   
			cr.add(logicalExpression);		
			if(isPlbRemark)
			{
				cr.setProjection(Projections.projectionList()							
						.add(Projections.property("plbRemark"), "plbRemark")				
						)
						.setResultTransformer(Transformers.aliasToBean(AirlineCommissionSheet.class));
			}
			else
			{
				cr.setProjection(Projections.projectionList()							
						.add(Projections.property("iataRemark"), "iataRemark")				
						)
						.setResultTransformer(Transformers.aliasToBean(AirlineCommissionSheet.class));
			}
			List<AirlineCommissionSheet> list  = cr.list();	
			if(list != null && list.size()>0)
				airlineCommissionSheetRow =  list.get(0);

		}catch (HibernateException e) {		
			logger.info("########################## AirlineCommissionSheetRow....error " +e.getMessage());
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return airlineCommissionSheetRow;
	}


	@Override
	public AirlineCommissionSheet getAirlineCommissionSheetComplete(
			Long sheetId, String iataCode) throws HibernateException {
		AirlineCommissionSheet airlineCommissionSheetRow  = null;	
		Session sess = null;	
		try{
			sess =  sessionFactory.openSession();					
			Criteria cr = sess.createCriteria(AirlineCommissionSheet.class);			   
			LogicalExpression logicalExpression  = Restrictions.and(Restrictions.eq("sheetId", sheetId), 
					Restrictions.eq("iataCode", iataCode));   
			cr.add(logicalExpression);		
			List<AirlineCommissionSheet> list  = cr.list();	
			if(list != null && list.size()>0)
				airlineCommissionSheetRow =  list.get(0);

		}catch (HibernateException e) {		
			logger.info("########################## AirlineCommissionSheetRow....error " +e.getMessage());
			logger.error(e);
			airlineCommissionSheetRow  = null;
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return airlineCommissionSheetRow;
	}


	@Override
	public CommissionActionStatus duplicateMasterSheetItems(Long sheetIdBackUp, int superUserCompanyId, AirlineCommissionMasterSheet superUserDealSheet)
			throws HibernateException {
		Session sess = null;
		Transaction tx = null;	
		CommissionActionStatus commissionActionStatus = new CommissionActionStatus(CommissionActionStatus.CODE_DEFAULT, CommissionActionStatus.MESSAGE_DEFAULT);
		int code  = CommissionActionStatus.CODE_SUCCESS;
		String message  = CommissionActionStatus.MESSAGE_SUCCESS;
		List<AirlineCommissionSheet> listnew  = new ArrayList<AirlineCommissionSheet>();	
		try{
			if(sheetIdBackUp!=null)
				sheetIdBackUp=new Long(0);

			sess =  sessionFactory.openSession();
			Criteria cr = sess.createCriteria(AirlineCommissionSheet.class);
			cr.add(Restrictions.eq("sheetId", sheetIdBackUp));
			AirlineCommissionCompanyBlock airlineCommissionCompanyBlock = new AirlineCommissionCompanyBlock();
			airlineCommissionCompanyBlock.setName("Super User Block - "+superUserDealSheet.getName());
			airlineCommissionCompanyBlock.setActive(superUserDealSheet.isActive());
			airlineCommissionCompanyBlock.setMasterBlock(true);
			airlineCommissionCompanyBlock.setCreatedByCompanyID(superUserCompanyId);
			airlineCommissionCompanyBlock.setAppliedSheetId(superUserDealSheet.getId());
			airlineCommissionCompanyBlock.setCreatedByUserID(superUserDealSheet.getCreatedByUserID());
			airlineCommissionCompanyBlock.setDescription(superUserDealSheet.getDescription());
			tx = sess.beginTransaction();
			sess.save(airlineCommissionCompanyBlock);			
			tx.commit();		

			List<AirlineCommissionSheet> list  = cr.list();	
			if(list != null && list.size()>0)
			{				
				int count=0;
				for (AirlineCommissionSheet airlineCommissionSheetRow : list) {
					++count;
					AirlineCommissionSheet airlineCommissionSheetRowDuplicate = new AirlineCommissionSheet();
					Timestamp updated_at = new Timestamp(new Date().getTime());	
					try{
						airlineCommissionSheetRowDuplicate.setApiSupplierId(airlineCommissionSheetRow.getApiSupplierId()); 
						airlineCommissionSheetRowDuplicate.setDealSheetVersion(airlineCommissionSheetRow.getDealSheetVersion());
						airlineCommissionSheetRowDuplicate.setIataCode(airlineCommissionSheetRow.getIataCode());
						airlineCommissionSheetRowDuplicate.setIataCommission(airlineCommissionSheetRow.getIataCommission());
						airlineCommissionSheetRowDuplicate.setIataRemark(airlineCommissionSheetRow.getIataRemark());
						airlineCommissionSheetRowDuplicate.setIsIataFixed(airlineCommissionSheetRow.getIsIataFixed());
						airlineCommissionSheetRowDuplicate.setPlbCommission(airlineCommissionSheetRow.getPlbCommission());
						airlineCommissionSheetRowDuplicate.setPlbRemark(airlineCommissionSheetRow.getPlbRemark());
						airlineCommissionSheetRowDuplicate.setIsPlbFixed(airlineCommissionSheetRow.getIsPlbFixed());					
						airlineCommissionSheetRowDuplicate.setDtValidFrom(airlineCommissionSheetRow.getDtValidFrom());
						airlineCommissionSheetRowDuplicate.setDtValidTill(airlineCommissionSheetRow.getDtValidTill());					
						airlineCommissionSheetRowDuplicate.setCreatedAt(updated_at);
						airlineCommissionSheetRowDuplicate.setLastModifiedAt(updated_at);
						airlineCommissionSheetRowDuplicate.setSheetId(superUserDealSheet.getId());					
						Integer createdByUserId = new Integer(superUserDealSheet.getCreatedByUserID());					
						airlineCommissionSheetRowDuplicate.setCreatedByUserId(createdByUserId.longValue());
						tx = sess.beginTransaction();
						sess.save(airlineCommissionSheetRowDuplicate);
						if (count % 100 == 0 ) {
							sess.flush();
							sess.clear();
						} 
						listnew.add(airlineCommissionSheetRowDuplicate);
						tx.commit();
					}
					catch(Exception e)
					{
						code  = CommissionActionStatus.CODE_SHEET_DUPLICATION_FALIED;
						message  = CommissionActionStatus.MESSAGE_SHEET_DUPLICATION_FALIED;
					}

					try
					{
						AirlineCommissionBlock airlineCommissionBlockRow = new AirlineCommissionBlock();
						airlineCommissionBlockRow.setAirlineCommissionCompanyBlock(airlineCommissionCompanyBlock);
						airlineCommissionBlockRow.setIataCode(airlineCommissionSheetRow.getIataCode());
						airlineCommissionBlockRow.setIataCommissionRetain(new BigDecimal(0));
						airlineCommissionBlockRow.setPlbCommissionRetain(new BigDecimal(0));				
						airlineCommissionBlockRow.setLastModifiedAt(updated_at);
						tx = sess.beginTransaction();
						sess.save(airlineCommissionBlockRow);
						if (count % 100 == 0 ) {
							sess.flush();
							sess.clear();
						} 
						tx.commit();
					}
					catch(Exception e)
					{
						code  = CommissionActionStatus.CODE_BLOCK_INSERTION_FALIED;
						message  = CommissionActionStatus.MESSAGE_BLOCK_INSERTION_FALIED;
					}
				}
			}
			else
			{
				code  = CommissionActionStatus.CODE_SHEET_INSERTION_FALIED;
				message  = CommissionActionStatus.MESSAGE_SHEET_INSERTION_FALIED;
			}

		}catch (HibernateException e) {
			logger.info("########################## DB insertAirlineCommissionSheet call error");
			logger.error(e);
			if (tx!=null) tx.rollback();

			code  = CommissionActionStatus.CODE_SHEET_INSERTION_FALIED;
			message  = CommissionActionStatus.MESSAGE_SHEET_INSERTION_FALIED;
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		commissionActionStatus.setStatus(code);
		commissionActionStatus.setMessage(message);
		return commissionActionStatus;
	}

	@Override
	public List<AirlineCommissionSheet> updateAirlineCommissionSheet(
			List<AirlineCommissionSheet> airlineCommissionSheetRows)
					throws HibernateException {
		Session sess = null;
		Transaction tx = null;	
		List<AirlineCommissionSheet> listnew  = new ArrayList<AirlineCommissionSheet>();	
		try{
			sess =  sessionFactory.openSession();			
			int count=0;
			for (AirlineCommissionSheet airlineCommissionSheetRow : airlineCommissionSheetRows) {
				Criteria crit = sess.createCriteria(AirlineCommissionSheet.class);			
				crit.add(Restrictions.eq("id", airlineCommissionSheetRow.getId()));			
				AirlineCommissionSheet airlineCommissionSheetRowdb = (AirlineCommissionSheet) crit.uniqueResult();
				airlineCommissionSheetRowdb.setApiSupplierId(airlineCommissionSheetRow.getApiSupplierId());
				airlineCommissionSheetRowdb.setDealSheetVersion(airlineCommissionSheetRow.getDealSheetVersion());
				airlineCommissionSheetRowdb.setDtValidFrom(airlineCommissionSheetRow.getDtValidFrom());
				airlineCommissionSheetRowdb.setDtValidTill(airlineCommissionSheetRow.getDtValidTill());
				airlineCommissionSheetRowdb.setIataCommission(airlineCommissionSheetRow.getIataCommission());
				airlineCommissionSheetRowdb.setIsIataFixed(airlineCommissionSheetRow.getIsIataFixed());
				airlineCommissionSheetRowdb.setIataRemark(airlineCommissionSheetRow.getIataRemark());
				airlineCommissionSheetRowdb.setPlbCommission(airlineCommissionSheetRow.getPlbCommission());
				airlineCommissionSheetRowdb.setIsPlbFixed(airlineCommissionSheetRow.getIsPlbFixed());
				airlineCommissionSheetRowdb.setPlbRemark(airlineCommissionSheetRow.getPlbRemark());
				airlineCommissionSheetRowdb.setSeverityLevel(airlineCommissionSheetRow.getSeverityLevel());
				Timestamp updated_at = new Timestamp(new Date().getTime());		
				airlineCommissionSheetRowdb.setLastModifiedAt(updated_at);	
				tx = sess.beginTransaction();
				sess.update(airlineCommissionSheetRowdb);
				if ( ++count % 100 == 0 ) {
					sess.flush();
					sess.clear();
				}
				tx.commit();
				listnew.add(airlineCommissionSheetRowdb);
			}

		}catch (HibernateException e) {
			System.out.println("########################## DB EupdateAirlineCommissionSheet update call error");
			System.out.println(e);
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return listnew;
	}

	@Override
	public AirlineCommissionMasterSheet saveSuperUserSheetDetails(AirlineCommissionMasterSheet sheet) {
		Session session=null;
		Transaction transaction=null;		
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(sheet);
			transaction.commit();

		}catch(HibernateException he){
			if (transaction!=null)
				transaction.rollback();
			logger.error(he.getMessage());

		}
		finally {
			if(session!=null && session.isOpen())
				session.close();
		}
		return sheet;
	}

	@Override
	public List<AirlineCommissionMasterSheet> getSuperUserSheetList() {
		Session session=null;
		Criteria criteria =null;
		List<AirlineCommissionMasterSheet> list = null;
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(AirlineCommissionMasterSheet.class);
			list = criteria.list();
		}catch(HibernateException he){
			logger.error(he.getMessage());
		}
		finally {
			if(session!=null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<AirlineCommissionSheet> updateAirlineCommissionSheetDates(String dtValidFromStr,
			String dtValidTillStr, Long sheetId) throws HibernateException {		
		Session sess = null;
		Transaction tx = null;
		List<AirlineCommissionSheet> listnew  = new ArrayList<AirlineCommissionSheet>();	
		try{
			Timestamp updated_at = new Timestamp(new Date().getTime());	
			Timestamp dtValidFrom = new Timestamp(getFormattedDateFromStringSQL(dtValidFromStr).getTime());	
			Timestamp dtValidTill = new Timestamp(getFormattedDateFromStringSQL(dtValidTillStr).getTime());			
			sess =  sessionFactory.openSession();				
			Criteria crit = sess.createCriteria(AirlineCommissionSheet.class);			
			crit.add(Restrictions.eq("sheetId", sheetId));	
			List<AirlineCommissionSheet> airlineCommissionSheetRows = crit.list();
			int count = 0;			
			for (AirlineCommissionSheet airlineCommissionSheetRow : airlineCommissionSheetRows) {				
				airlineCommissionSheetRow.setLastModifiedAt(updated_at);
				airlineCommissionSheetRow.setDtValidFrom(dtValidFrom);
				airlineCommissionSheetRow.setDtValidTill(dtValidTill);
				tx = sess.beginTransaction();
				sess.update(airlineCommissionSheetRow);
				if ( ++count % 100 == 0 ) {
					sess.flush();
					sess.clear();
				}
				tx.commit();
				listnew.add(airlineCommissionSheetRow);
			}
		}
		catch (java.text.ParseException e) {		
			if (tx!=null) tx.rollback();
		}
		catch (HibernateException e) {			
			if (tx!=null) tx.rollback();

		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return listnew;
	}

	@Override
	public AirlineCommissionMasterSheet getSuperUserAirlineCommissionSheet(Long sheetId) {
		Session session=null;

		AirlineCommissionMasterSheet superUserAirlineCommissionSheet = null;
		try{
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(AirlineCommissionMasterSheet.class);			
			crit.add(Restrictions.eq("id", sheetId));	
			superUserAirlineCommissionSheet = (AirlineCommissionMasterSheet) crit.uniqueResult();
		}catch(HibernateException he){
			logger.error(he.getMessage());
		}
		finally {
			if(session!=null && session.isOpen())
				session.close();
		}
		return superUserAirlineCommissionSheet;
	}

	@Override
	public List<AirlineCommissionSheet> getAirlineCommissionSheetRows(
			Long sheetId) throws HibernateException {
		List<AirlineCommissionSheet> airlineCommissionSheetRows  = new ArrayList<AirlineCommissionSheet>();	
		Session sess = null;					
		try{
			sess =  sessionFactory.openSession();
			Criteria cr = sess.createCriteria(AirlineCommissionSheet.class);
			cr.add(Restrictions.eq("sheetId", sheetId));					
			List<AirlineCommissionSheet> list  = cr.list();	
			if(list != null && list.size()>0)
				airlineCommissionSheetRows = cr.list();

		}catch (HibernateException e) {		
			logger.info("########################## AirlineCommissionSheetRows....error " +e.getMessage());
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return airlineCommissionSheetRows;
	}


	@Override
	public AirlineCommissionMasterSheet getDealSheetDetails(Long sheetId) {		
		Session session=null;	
		AirlineCommissionMasterSheet commissionSheetDetails=null;
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(AirlineCommissionMasterSheet.class);
			criteria.add(Restrictions.eq("id", sheetId));
			commissionSheetDetails=(AirlineCommissionMasterSheet) criteria.uniqueResult();
		}
		catch(HibernateException e){
			logger.error(e);
		}
		finally {
			if(session!=null && session.isOpen())
				session.close();
		}
		return commissionSheetDetails;
	}

	@Override
	public AirlineCommissionMasterSheet updateDealSheet(AirlineCommissionMasterSheet commissionSheet) {	
		Session session=null;	
		Transaction  tx=null;	
		AirlineCommissionMasterSheet commissionSheetDetails=null;
		try{
			session=sessionFactory.openSession();
			tx = session.beginTransaction();
			commissionSheetDetails= (AirlineCommissionMasterSheet)session.get(AirlineCommissionMasterSheet.class, commissionSheet.getId()); 
			commissionSheetDetails.setActive(commissionSheet.isActive());
			commissionSheetDetails.setName(commissionSheet.getName());
			commissionSheetDetails.setDescription(commissionSheet.getDescription());
			session.saveOrUpdate(commissionSheetDetails); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return commissionSheetDetails;
	}

	public static Date getFormattedDateFromStringSQL(String dateStr) throws java.text.ParseException
	{	
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = sdf.parse(dateStr); 
		return date; 

	}
}