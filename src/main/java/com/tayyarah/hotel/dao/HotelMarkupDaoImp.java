package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.hotel.entity.HotelMarkup;


public class HotelMarkupDaoImp implements HotelMarkupDao {
	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	Logger logger = Logger.getLogger(HotelMarkupDaoImp.class);	
	public HotelMarkup insertHotelMarkup(HotelMarkup hm) throws HibernateException, IOException {
		List<HotelMarkup> list = new ArrayList<HotelMarkup>();
		tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from HotelMarkup";
			Query query = session.createQuery(sql);
			list = query.list();		
			tx.commit();

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			session.close(); 
		}
		return list.get(0);
	}

	public List<HotelMarkup> getHotelMarkups(int companyId) throws HibernateException, IOException {
		List<HotelMarkup> list = new ArrayList<HotelMarkup>();
		tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from HotelMarkup hm where hm.companyId= company_id";
			Query query = session.createQuery(sql);
			query.setParameter("company_id", companyId);
			list = query.list();		
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			session.close(); 
		}
		return list;
	}

	public List<HotelMarkup> getHotelMarkups(int companyId, int configId) throws HibernateException, IOException {
		List<HotelMarkup> list = new ArrayList<HotelMarkup>();
		tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from HotelMarkup hm where hm.companyId=:company_id and hm.configId=:config_id";
			Query query = session.createQuery(sql);
			query.setParameter("company_id", companyId);
			query.setParameter("config_id", configId);
			list = query.list();		
			tx.commit();

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			session.close(); 
		}
		return list;
	}

	public List<HotelMarkup> getHotelMarkups(int companyId, int configId, int isaccumulative)
			throws HibernateException, IOException {
		List<HotelMarkup> list = new ArrayList<HotelMarkup>();
		tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from HotelMarkup";
			Query query = session.createQuery(sql);
			list = query.list();		
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			session.close(); 
		}
		return list;
	}

	public List<HotelMarkup> getAllHotelMarkups() throws HibernateException, IOException {
		List<HotelMarkup> list = new ArrayList<HotelMarkup>();
		tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from HotelMarkup";
			Query query = session.createQuery(sql);
			list = query.list();		
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			session.close(); 
		}
		return list;
	}

	public List<HotelMarkup> getHotelMarkupsLevelMarkUp(int childCompanyId, int parentCompanyId)
			throws HibernateException, IOException {
		List<HotelMarkup> list = new ArrayList<HotelMarkup>();
		tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from HotelMarkup hm where hm.companyId=:company_id  and hm.createdbyUserId=:createdby_company_id";
			Query query = session.createQuery(sql);
			query.setParameter("company_id", childCompanyId);
			query.setParameter("createdby_company_id", parentCompanyId);
			list = query.list();		
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			session.close(); 
		}
		return list;
	}	

	public Map<String,List<HotelMarkup>> getHotelMarkUpConfigMapByCompanyId(AppKeyVo appKeyVo, Map<String,List<HotelMarkup>>  markupMap)throws SQLException, Exception {	
		Session sess = null;		
		Company company = null;
		Company parentcompany = null;
		CompanyConfig companyConfig = null;
		CompanyConfig parentCompanyConfig = null;
		String sql = null;
		SQLQuery query = null;		 
		logger.info("####################  Hotel markup collection call companyid-"+appKeyVo.getCompanyId());
		try{
			sess = sessionFactory.openSession();
			companyConfig = appKeyVo.getCompanyConfig();	
			company = appKeyVo.getCompany();	

			if(company != null && companyConfig != null)
			{				
				Criteria markupCriteria=sess.createCriteria(HotelMarkup.class);
				Conjunction markupConjunction=Restrictions.conjunction();
				markupConjunction.add(Restrictions.eq("configId", companyConfig.getConfig_id()));
				markupConjunction.add(Restrictions.eq("companyId",  company.getCompanyid()));
				markupConjunction.add(Restrictions.eq("createdbyCompanyId",  company.getCompanyid()));
				markupCriteria.add(markupConjunction);
				markupCriteria.addOrder(Order.desc("positionMarkup"));
				markupCriteria.addOrder(Order.desc("createdDate"));
				List<HotelMarkup> hotelMarkupList = markupCriteria.list();
				logger.info("####################  company is not super user configId-"+companyConfig.getConfig_id());	
				logger.info("####################  company is not super user companyId-"+company.getCompanyid());	
				logger.info("####################  company is not super user createdbyCompanyId-"+company.getCompanyid());	
				logger.info("####################  company is not super user hotelMarkupList-"+hotelMarkupList);

				markupMap.put(String.valueOf(company.getCompanyid()), hotelMarkupList);				
				if(company.getCompanyid() == 1)
				{									
					return markupMap;
				}
				else
				{				
					Criteria companyCriteria=sess.createCriteria(Company.class);
					companyCriteria.add(Restrictions.eq("company_userid", company.getParent_company_userid()));
					parentcompany = (Company) companyCriteria.uniqueResult();						
					Criteria companyCriteria1 = sess.createCriteria(CompanyConfig.class);
					companyCriteria1.add(Restrictions.eq("config_id",companyConfig.getParent_config_id()));
					parentCompanyConfig = (CompanyConfig) companyCriteria1.uniqueResult();

					if(company.getCompanyid() == 1)
					{
						//This parent company is super compnay.. terminate here n go back...						
						return markupMap;
					}
					else if(parentcompany != null && parentCompanyConfig != null)
					{					
						markupMap = getParentMarkupMap(companyConfig, parentCompanyConfig, markupMap);
					}
					else
					{
						return markupMap;
					}
				}
			}			
		}catch (HibernateException e) {	
			logger.error("#####################3 marup call HibernateException for companyid"+appKeyVo.getCompanyId(), e);			
			return markupMap;

		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return markupMap;
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Map<String,List<HotelMarkup>> getParentMarkupMap(CompanyConfig companyConfig, CompanyConfig parentCompConfig, Map<String,List<HotelMarkup>>  markupMap)throws SQLException, Exception {	
		Session sess = null;		
		Company company = null;	
		Company parentcompany = null;
		Company superparentcompany = null;	
		CompanyConfig superParentCompConfig = null;
		String sql = null;
		SQLQuery query = null;		 
		logger.info("#################### RECURSION  Hotel markup  call companyid-"+companyConfig.getCompany_id());
		try{
			sess = sessionFactory.openSession();

			Criteria companyCriteria=sess.createCriteria(Company.class);
			companyCriteria.add(Restrictions.eq("companyid", companyConfig.getCompany_id()));
			company = (Company) companyCriteria.uniqueResult();	

			Criteria companyCriteriaParent = sess.createCriteria(Company.class);
			companyCriteriaParent.add(Restrictions.eq("companyid", parentCompConfig.getCompany_id()));
			parentcompany = (Company) companyCriteriaParent.uniqueResult();	

			if(company != null && companyConfig != null && parentCompConfig != null)
			{
				Criteria markupCriteria=sess.createCriteria(HotelMarkup.class);
				Conjunction markupConjunction=Restrictions.conjunction();
				markupConjunction.add(Restrictions.eq("configId", companyConfig.getConfig_id()));
				markupConjunction.add(Restrictions.eq("companyId",  company.getCompanyid()));
				markupConjunction.add(Restrictions.eq("createdbyCompanyId",  parentcompany.getCompanyid()));
				markupCriteria.add(markupConjunction);
				markupCriteria.addOrder(Order.desc("positionMarkup"));
				markupCriteria.addOrder(Order.desc("createdDate"));
				List<HotelMarkup> hotelMarkupList = markupCriteria.list();						
				markupMap.put(String.valueOf(parentcompany.getCompanyid()), hotelMarkupList);				
				Criteria companyCriteria1 = sess.createCriteria(CompanyConfig.class);
				companyCriteria1.add(Restrictions.eq("config_id",parentCompConfig.getParent_config_id()));
				superParentCompConfig = (CompanyConfig) companyCriteria1.uniqueResult();

				if(superParentCompConfig != null)
				{
					Criteria companyCriteriaSuperParent = sess.createCriteria(Company.class);
					companyCriteriaSuperParent.add(Restrictions.eq("companyid", superParentCompConfig.getCompany_id()));
					superparentcompany = (Company) companyCriteriaSuperParent.uniqueResult();			

					if(superparentcompany != null && superparentcompany.getCompanyid() != parentcompany.getCompanyid())
					{
						logger.info("########################## RECURSION DB Markup map recursion call--for companyid s parent "+parentcompany.getCompanyid());
						markupMap = getParentMarkupMap(parentCompConfig, superParentCompConfig,  markupMap);
					}
					else if(superparentcompany.getCompanyid() == parentcompany.getCompanyid())
					{
						logger.info("########################## RECURSION DB parent ==== super parent "+parentcompany.getCompanyid());				
						return markupMap;
					}
				}
				else
				{
					logger.info("########################## RECURSION DB parent ==== super parent "+parentcompany.getCompanyid());				
					return markupMap;
				}			

			}	

		}catch (HibernateException e) {	
			logger.error("#####################3 marup call HibernateException for companyid "+company.getCompanyid(), e);
			return markupMap;

		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return markupMap;
	}	

	public List<HotelMarkup> getFirstLevelMarkUps(String companyId, String configId)
			throws HibernateException, IOException {
		List<HotelMarkup> list = new ArrayList<HotelMarkup>();
		tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from HotelMarkup hm  where hm.configId=:config_id and hm.companyId=:company_id and hm.createdbyUserId=:createdby_company_id order by  hm.positionMarkup desc, hm.createdDate desc";
			Query query = session.createQuery(sql);
			query.setParameter("config_id", Integer.parseInt(configId));
			query.setParameter("company_id", Integer.parseInt(companyId));
			query.setParameter("createdby_company_id", Integer.parseInt(companyId));
			list = query.list();		
			tx.commit();

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			session.close(); 
		}
		return list;
	}

	public List<HotelMarkup> getMarkUpsRest(String childCompanyId, String parentCompanyId)
			throws HibernateException, IOException {
		List<HotelMarkup> list = new ArrayList<HotelMarkup>();
		tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "from HotelMarkup hm  where  hm.companyId=:company_id and hm.createdbyUserId=:createdby_company_id order by  hm.positionMarkup desc, hm.createdDate desc";
			Query query = session.createQuery(sql);
			query.setParameter("company_id", Integer.parseInt(childCompanyId));
			query.setParameter("createdby_company_id", Integer.parseInt(parentCompanyId));
			list = query.list();		
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			session.close(); 
		}
		return list;
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Map<String,List<HotelMarkup>> getApplicableMarkUps(String app_key){	
		String companyId = "invalid";
		String configId = "invalid";
		Map<String,List<HotelMarkup>>  markupMap = new HashMap<String,List<HotelMarkup>>();
		try{
			configId = app_key.substring(0, app_key.indexOf("-"));
			companyId = app_key.substring(app_key.indexOf("-") + 1);
			List<String> compnayIdList=new ArrayList<String>();
			getParentCompanyIds(companyId,compnayIdList);
			if((compnayIdList.size()==1)&&(companyId.equals(compnayIdList.get(0)))){
				markupMap.put(companyId, getFirstLevelMarkUps( companyId, configId));
			}else{
				markupMap.put(companyId, getFirstLevelMarkUps( companyId, configId));
				for(String newCompanyId:compnayIdList){
					markupMap.put(newCompanyId, getMarkUpsRest(companyId,newCompanyId));
					companyId=newCompanyId;
				}
			}
		}
		catch (Exception e) {
			logger.info("Exception found loading markupss...."+e.getMessage());
			e.printStackTrace();
		}
		return markupMap;
	}

	public void getParentCompanyIds(String id,List<String> compnayIdList) {
		Company company = null;
		String parent_company_userid = "invalid";
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}		
		try {
			int cid = Integer.parseInt(id);
			String sql = "from Company c where c.companyid=:companyid";
			Query query = session.createQuery(sql);
			query.setParameter("companyid", cid);
			List<Company> list = query.list();			
			if (list!=null && list.size() > 0) {
				company = list.get(0);
				parent_company_userid = company.getParent_company_userid();
			}
		} catch (NumberFormatException ne) {
			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);

		}finally
		{ 
			tx.commit();
			session.close();
			getParentCompanyId(parent_company_userid,compnayIdList);
		}
	}
	public void getParentCompanyId(String id,List<String> compnayIdList) {

		Company company = null;
		String company_id = "invalid";
		String parent_company_userid = "invalid";
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}		
		try {

			String sql = "from Company c where c.company_userid =:company_userid";
			Query query = session.createQuery(sql);
			query.setParameter("company_userid",  id);
			List<Company> list = query.list();			
			if (list.size() > 0) {
				company = list.get(0);
				company_id = String.valueOf(company.getCompanyid());
				parent_company_userid=company.getParent_company_userid();
			}
		} catch (NumberFormatException ne) {
			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);

		} 
		finally{ 
			tx.commit();
			session.close();
			compnayIdList.add(company_id);		
			if(!id.equals(parent_company_userid.trim())){
				getParentCompanyIds(company_id,compnayIdList);
			}
		}
	}


	public boolean checkAppkey(String app_key) throws HibernateException,
	NumberFormatException, Exception {		
		String companyId = "invalid";
		String configId = "invalid";
		configId = app_key.substring(0, app_key.indexOf("-"));
		companyId = app_key.substring(app_key.indexOf("-") + 1);
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		boolean result = false;
		String sql = "from CompanyConfig hm  where hm.config_id=:config_id and hm.company_id=:company_id";
		Query query = session.createQuery(sql);
		query.setParameter("config_id", Integer.parseInt(configId));
		query.setParameter("company_id", Integer.parseInt(companyId));
		List<CompanyConfig> list = query.list();		
		if (list!=null && list.size() > 0) {
			result = true;
		}
		tx.commit();
		session.close();
		return result;
	}
}