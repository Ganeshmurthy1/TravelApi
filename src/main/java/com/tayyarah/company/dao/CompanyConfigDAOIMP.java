package com.tayyarah.company.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.company.entity.CompanyConfig;


@SuppressWarnings("deprecation")
public class CompanyConfigDAOIMP implements CompanyConfigDAO {
	
	@Autowired
	private SessionFactory sessionFactory;	
	
	Logger logger = Logger.getLogger(CompanyConfigDAOIMP.class);
	
	@SuppressWarnings({ "unchecked" })
	public List<CompanyConfig> getCompanyConfigconfig_id(int config_id)
	 {
		Session session = null;	
		List<CompanyConfig> list = null;
		try{
		session = sessionFactory.openSession();			
		Criteria criteria = session.createCriteria(CompanyConfig.class);
		criteria = criteria.add(Restrictions.eq("config_id", config_id));
		list = criteria.list();			
		session.close();
		}catch(Exception e){
			logger.info("getCompanyConfigconfig_id Exception:" + e);
		}
		return list;
	}	 
	public CompanyConfig getCompanyConfigByConfigId(int configId)
	 {
		CompanyConfig companyConfig = null;
		Session session = null;
		try{				
		session = sessionFactory.openSession();	
		Criteria criteria = session.createCriteria(CompanyConfig.class);
		criteria = criteria.add(Restrictions.eq("config_id", configId));
		companyConfig = (CompanyConfig) criteria.uniqueResult();	
		session.close();
		}catch(Exception e){
			logger.info("getCompanyConfigByConfigId Exception:" + e);
		}
		return companyConfig;
	}

}
