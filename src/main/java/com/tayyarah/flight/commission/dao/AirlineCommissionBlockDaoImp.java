package com.tayyarah.flight.commission.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.flight.commission.entity.AirlineCommissionBlock;
import com.tayyarah.flight.commission.entity.AirlineCommissionCompanyBlock;
import com.tayyarah.flight.commission.entity.AirlineCommissionSheet;


public class AirlineCommissionBlockDaoImp implements AirlineCommissionBlockDao {
	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(AirlineCommissionBlockDaoImp.class);

	@Override
	public List<AirlineCommissionBlock> getAirlineCommissionBlock(
			Integer parentCompanyId, Integer childCompanyId)
					throws HibernateException {
		List<AirlineCommissionBlock> list  = new ArrayList<AirlineCommissionBlock>(); 
		Session sess = null;   
		System.out.println("########################## DB AirlineCommissionBlockRow retriving call" +parentCompanyId + ""+childCompanyId);
		try{
			sess = sessionFactory.openSession();
			Criteria crit = sess.createCriteria(AirlineCommissionBlock.class);
			crit.add(Restrictions.eq("parentCompanyId", parentCompanyId));
			crit.add(Restrictions.eq("childCompanyId", childCompanyId));

			LogicalExpression logicalExpression  = Restrictions.and(Restrictions.eq("parentCompanyId", parentCompanyId), 
					Restrictions.eq("childCompanyId", childCompanyId));
			crit.add(logicalExpression);   
			crit.setProjection(Projections.projectionList()
					.add(Projections.property("id"), "id")
					.add(Projections.property("iataCode"), "iataCode")
					.add(Projections.property("sheetId"), "sheetId")
					.add(Projections.property("parentCompanyId"), "parentCompanyId")
					.add(Projections.property("childCompanyId"), "childCompanyId")							
					.add(Projections.property("plbCommissionRetain"), "plbCommissionRetain")
					.add(Projections.property("iataCommissionRetain"), "iataCommissionRetain")

					)
					.setResultTransformer(Transformers.aliasToBean(AirlineCommissionBlock.class));	
			list = (List<AirlineCommissionBlock>) crit.list();   
			
		}catch (HibernateException e) {     
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
	public List<AirlineCommissionBlock> createAirlineCommissionBlock(
			Integer parentCompanyId,  Integer childCompanyId, Long sheetId)
					throws HibernateException {
		Session sess = null;
		Transaction tx = null;
		logger.info("insertAirlineCommissionBlock called " + parentCompanyId + childCompanyId + sheetId);
		List<AirlineCommissionBlock> listnew  = new ArrayList<AirlineCommissionBlock>();	
		try{
			sess = sessionFactory.openSession();	
			Criteria cr = sess.createCriteria(AirlineCommissionSheet.class);
			cr.add(Restrictions.eq("sheetId", sheetId));
			cr.setProjection(Projections.projectionList()
					.add(Projections.property("id"), "id")
					.add(Projections.property("iataCode"), "iataCode")
					.add(Projections.property("sheetId"), "sheetId")					
					)
					.setResultTransformer(Transformers.aliasToBean(AirlineCommissionSheet.class));		

			List<AirlineCommissionSheet> list = cr.list();
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			sess = null;
			sess = sessionFactory.openSession();			
			int count = 0;
			if(list!=null && list.size()>0)
			{
				int sheetsize = list.size() + 1;
				for (AirlineCommissionSheet airlineCommissionSheetRow : list) {
					AirlineCommissionBlock airlineCommissionBlockRow = new AirlineCommissionBlock();
					String tempid = String.valueOf(sheetsize); 		
					Long id = new Long(tempid);	
					airlineCommissionBlockRow.setId(id);
					airlineCommissionBlockRow.setIataCode(airlineCommissionSheetRow.getIataCode());
					airlineCommissionBlockRow.setIataCommissionRetain(new BigDecimal(0));
					airlineCommissionBlockRow.setPlbCommissionRetain(new BigDecimal(0));
					tx = sess.beginTransaction();
					sess.save(airlineCommissionBlockRow);

					if ( ++count % 100 == 0 ) {
						sess.flush();
						sess.clear();
					}					
					tx.commit();
					listnew.add(airlineCommissionBlockRow);
					sheetsize++;
				}
			}
		}catch (HibernateException e) {
			System.out.println("########################## DB insertAirlineCommissionBlock call error");
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
	public Map<Integer, AirlineCommissionBlock> getAirlineCommissionBlockMap(
			Integer childCompanyConfigId, String iataCode)
					throws HibernateException {
		Map<Integer, AirlineCommissionBlock> airlineCommissionBlockMap  = new HashMap<Integer, AirlineCommissionBlock>(); 
		Session sess = null;   
		try{
			sess = sessionFactory.openSession();
			AirlineCommissionBlock airlineCommissionBlockRow = null;
			while(childCompanyConfigId!=null)
			{			
				if(airlineCommissionBlockRow != null)
				{
					//logger.info("##########################  deal block for CompanyId -"+childCompanyId + "deal block-"+airlineCommissionBlockRow.toString() );
					//logger.info("##########################  deal block parent company id -"+airlineCommissionBlockRow.getParentCompanyId());

					//airlineCommissionBlockMap.put(airlineCommissionBlockRow.getParentCompanyId(), airlineCommissionBlockRow);
					//childCompanyId = airlineCommissionBlockRow.getParentCompanyId();
				}
				else
				{
					//childCompanyId = null;
					//logger.info("########################## unable to find deal block for CompanyId -"+childCompanyId);
					break;
				}
			}

		}catch (HibernateException e) {     
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{    
				sess.close(); 
			}

		}
		return airlineCommissionBlockMap;
	}


	@Override
	public List<AirlineCommissionBlock> insertAirlineCommissionBlock(
			List<AirlineCommissionBlock> airlineCommissionBlockRows)
					throws HibernateException {
		Session sess = null;
		Transaction tx = null;
		logger.info("insertAirlineCommissionBlock called ");
		List<AirlineCommissionBlock> listnew  = new ArrayList<AirlineCommissionBlock>();	
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			int count=0;
			for (AirlineCommissionBlock airlineCommissionBlockRow : airlineCommissionBlockRows) {

				airlineCommissionBlockRow = (AirlineCommissionBlock) sess.save(airlineCommissionBlockRow);
				if ( ++count % 100 == 0 ) {
					sess.flush();
					sess.clear();
				}
				listnew.add(airlineCommissionBlockRow);
			}

		}catch (HibernateException e) {
			logger.info("########################## DB insertAirlineCommissionBlock call error");
			logger.error(e);
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
	public List<AirlineCommissionBlock> updateAirlineCommissionBlock(
			List<AirlineCommissionBlock> airlineCommissionBlockRows)
					throws HibernateException {
		Session sess = null;
		Transaction tx = null;
		logger.info("updateAirlineCommissionBlock called ");
		List<AirlineCommissionBlock> listnew  = new ArrayList<AirlineCommissionBlock>();	
		try{
			sess = sessionFactory.openSession();

			int count=0;
			for (AirlineCommissionBlock airlineCommissionBlockRow : airlineCommissionBlockRows) {


				Criteria crit = sess.createCriteria(AirlineCommissionBlock.class);			
				crit.add(Restrictions.eq("id", airlineCommissionBlockRow.getId()));			
				AirlineCommissionBlock airlineCommissionBlockRowDb = (AirlineCommissionBlock) crit.uniqueResult();
				airlineCommissionBlockRowDb.setIataCommissionRetain(airlineCommissionBlockRow.getIataCommissionRetain());
				airlineCommissionBlockRowDb.setPlbCommissionRetain(airlineCommissionBlockRow.getPlbCommissionRetain());
				tx = sess.beginTransaction();	
				sess.update(airlineCommissionBlockRowDb);
				if ( ++count % 100 == 0 ) {
					sess.flush();
					sess.clear();
				}
				tx.commit();
				listnew.add(airlineCommissionBlockRowDb);
			}

		}catch (HibernateException e) {
			logger.info("########################## DB updateAirlineCommissionBlock update call error");
			logger.error(e);
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
	public AirlineCommissionCompanyBlock createCompanyDealSheetBlock(AirlineCommissionCompanyBlock commissionBlockSheet) throws HibernateException {
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();	
			sess.save(commissionBlockSheet);
			tx.commit();
		}catch (HibernateException e) {
			logger.info("########################## DB updateAirlineCommissionBlock update call error");
			logger.error(e);
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return commissionBlockSheet;

	}

	@Override
	public AirlineCommissionCompanyBlock createCompanyCommissionBlock(AirlineCommissionCompanyBlock airlineCommissionCompanyBlock)
			throws HibernateException, Exception{
		Session sess = null;
		Transaction tx = null;
		logger.info("createCompanyCommissionBlock called ");
		List<AirlineCommissionBlock> listnew  = new ArrayList<AirlineCommissionBlock>();	
		try{
			sess =  sessionFactory.openSession();
			if(airlineCommissionCompanyBlock.getCreatedByCompanyID()!=0 && airlineCommissionCompanyBlock.getCreatedByUserID()!=0 && airlineCommissionCompanyBlock.getAppliedSheetId() != null)
			{
				if(airlineCommissionCompanyBlock.getAppliedSheetId()==null)
				{
					airlineCommissionCompanyBlock.setAppliedSheetId(0L);
				}
				airlineCommissionCompanyBlock.setMasterBlock(false);
				airlineCommissionCompanyBlock.setName("Wholesaler Block - "+airlineCommissionCompanyBlock.getName());
				tx = sess.beginTransaction();
				sess.save(airlineCommissionCompanyBlock);			
				tx.commit();
				logger.info("company block created -- block id--"+airlineCommissionCompanyBlock.getId());

				Criteria cr = sess.createCriteria(AirlineCommissionSheet.class);
				cr.add(Restrictions.eq("sheetId", airlineCommissionCompanyBlock.getAppliedSheetId()));
				cr.setProjection(Projections.projectionList()							
						.add(Projections.property("sheetId"), "sheetId")						
						.add(Projections.property("iataCode"), "iataCode")					
						)
						.setResultTransformer(Transformers.aliasToBean(AirlineCommissionSheet.class));
				List<AirlineCommissionSheet> list  = cr.list();	
				if(list != null && list.size()>0)
				{				
					int count=0;
					for (AirlineCommissionSheet airlineCommissionSheetRow : list) {

						AirlineCommissionBlock airlineCommissionBlockRow = new AirlineCommissionBlock();
						airlineCommissionBlockRow.setAirlineCommissionCompanyBlock(airlineCommissionCompanyBlock);
						airlineCommissionBlockRow.setIataCode(airlineCommissionSheetRow.getIataCode());
						airlineCommissionBlockRow.setIataCommissionRetain(new BigDecimal(0));
						airlineCommissionBlockRow.setPlbCommissionRetain(new BigDecimal(0));					
						Timestamp updated_at = new Timestamp(new Date().getTime());	
						airlineCommissionBlockRow.setLastModifiedAt(updated_at);

						tx = sess.beginTransaction();
						sess.save(airlineCommissionBlockRow);
						if (++count % 100 == 0 ) {
							sess.flush();
							sess.clear();
						} 
						listnew.add(airlineCommissionBlockRow);
						tx.commit();
					}
					//airlineCommissionCompanyBlock.setAirlineCommissionBlockRowList(listnew);
					logger.info("##### AirlineCommissionBlockRow items created.. ");
				}			

			}
			else
			{
				throw new Exception("Comp id, user id or sheet id null"); 
			}
		}catch (HibernateException e) {
			logger.info("########################## DB insertAirlineCommissionSheet call error");
			logger.error(e);
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return airlineCommissionCompanyBlock;
	}


	@Override
	public List<AirlineCommissionCompanyBlock> getChildrenCompanyCommissionBlocks(boolean isMaster, int companyId)
			throws HibernateException {
		Session sess = null;
		logger.info("getChildrenCompanyCommissionBlocks called ");
		List<AirlineCommissionCompanyBlock> listnew =null;
		try{			
			logger.info("children block search for companyId--"+companyId);
			sess =  sessionFactory.openSession();
			Criteria cr = sess.createCriteria(AirlineCommissionCompanyBlock.class);
			LogicalExpression logicalExpression  = Restrictions.and(Restrictions.eq("createdByCompanyID", companyId), 
					Restrictions.eq("isMasterBlock", isMaster));   
			cr.add(logicalExpression);	
			listnew  = cr.list();
			System.out.println("List<AirlineCommissionCompanyBlock> list--------"+listnew.size());

		}catch (HibernateException e) {
			logger.info("########################## DB insertAirlineCommissionSheet call error");
			logger.error(e);
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
	public AirlineCommissionCompanyBlock getAirlineCommissionCompanyBlock(Long blockId) throws HibernateException {
		// TODO Auto-generated method stub
		Session session=null;
		 
		AirlineCommissionCompanyBlock companyBlock=null;
		try{
			session= sessionFactory.openSession();
			Criteria cr = session.createCriteria(AirlineCommissionCompanyBlock.class);
			cr.add(Restrictions.eq("id", blockId));
			companyBlock=(AirlineCommissionCompanyBlock) cr.uniqueResult();
		 }
		catch (HibernateException e) {
			companyBlock = null;
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return companyBlock;
		 
	}

	
	@Override
	public AirlineCommissionBlock getAirlineCommissionBlockRow(String iataCode, Long blockId)
			throws HibernateException {
		Session sess = null;
		logger.info("getAirlineCommissionBlockRow called ");
		AirlineCommissionBlock airlineCommissionBlockRow =null;
		try{			
			logger.info("############### get block row...blockId-"+blockId);
			sess =  sessionFactory.openSession();
			Criteria cr = sess.createCriteria(AirlineCommissionBlock.class);
			LogicalExpression logicalExpression  = Restrictions.and(Restrictions.eq("airlineCommissionCompanyBlock.id", blockId), 
					Restrictions.eq("iataCode", iataCode));   
			cr.add(logicalExpression);	
			airlineCommissionBlockRow  = (AirlineCommissionBlock) cr.uniqueResult();
			System.out.println("airlineCommissionBlockRow--------"+airlineCommissionBlockRow);

		}catch (HibernateException e) {
			logger.info("########################## DB getAirlineCommissionBlockRow call error");
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return airlineCommissionBlockRow;
	}

	
	@Override
	public List<AirlineCommissionBlock> getAirlineCommissionBlockRow (Long blockId) throws HibernateException {
		// TODO Auto-generated method stub
		Session session=null;		 
		List<AirlineCommissionBlock> airlineCommissionBlockRows = null;
		System.out.println("blockId" +blockId);
		try{
			session= sessionFactory.openSession();			
			Criteria cr = session.createCriteria(AirlineCommissionBlock.class);
			cr.add(Restrictions.eq("airlineCommissionCompanyBlock.id", blockId)); 
			airlineCommissionBlockRows =  cr.list();
			System.out.println("airlineCommissionBlockRows" +airlineCommissionBlockRows.size());
		 }
		catch (HibernateException e) {
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return airlineCommissionBlockRows;
		 
	}
	
	@Override
	public AirlineCommissionCompanyBlock updateAirlineCommissionCompanyBlock(AirlineCommissionCompanyBlock airlineCommissionCompanyBlock) {
		Session session=null;	
		Transaction  tx=null;	
		AirlineCommissionCompanyBlock airlineCommissionCompanyBlockDB = null;
		try{
			session=sessionFactory.openSession();
			tx = session.beginTransaction();
			airlineCommissionCompanyBlockDB = (AirlineCommissionCompanyBlock)session.get(AirlineCommissionCompanyBlock.class, airlineCommissionCompanyBlock.getId()); 
			airlineCommissionCompanyBlockDB.setActive(airlineCommissionCompanyBlock.isActive());
			airlineCommissionCompanyBlockDB.setName(airlineCommissionCompanyBlock.getName());
			airlineCommissionCompanyBlockDB.setDescription(airlineCommissionCompanyBlock.getDescription());
			airlineCommissionCompanyBlockDB.setAppliedSheetId(airlineCommissionCompanyBlock.getAppliedSheetId());
			airlineCommissionCompanyBlock.setSheetInfo("");
			session.saveOrUpdate(airlineCommissionCompanyBlockDB); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return airlineCommissionCompanyBlockDB;
	}
}