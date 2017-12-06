package com.tayyarah.common.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.ApiProvider;
import com.tayyarah.common.util.ApiSupplierBalance;



public class ApiProviderDAOIMP implements ApiProviderDAO{
	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(ApiProviderDAOIMP.class);
	
	public void updateApiProviderBalance(ApiSupplierBalance supplierBalance){
		Session session = null;	
		ApiProvider apiProvider = null;
		try{
		session = sessionFactory.openSession();			
		Criteria criteria = session.createCriteria(ApiProvider.class);
		criteria.add(Restrictions.eq("vendorName", supplierBalance.getApiProvider()));
		apiProvider = (ApiProvider) criteria.uniqueResult();
		session.close();
		if(apiProvider != null){
			if(!session.isOpen()){
				session = sessionFactory.openSession();	
				apiProvider.setCashBalance(supplierBalance.getCashBalance());
				apiProvider.setCreditBalance(supplierBalance.getCreditBalance());
				session.saveOrUpdate(apiProvider);
				
			}
		}
	}catch(Exception e){
		logger.info("updateApiProviderBalance Exception" +e);
	}
	
	}
}
