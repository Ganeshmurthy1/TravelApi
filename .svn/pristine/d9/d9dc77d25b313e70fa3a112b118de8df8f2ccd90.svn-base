package com.tayyarah.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.apiproviderconfig.entity.ApiProviderBluestarConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderCommonConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderDesiyaConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderEtravelSmartConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderTayyarahConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderTboConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderTrawellTagConfig;
import com.tayyarah.common.model.AppKeyVo;



public class ApiProviderCommonConfigActiveDaoImpl implements ApiProviderCommonConfigActiveDao {
	static final Logger logger = Logger.getLogger(ApiProviderCommonConfigActiveDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;

	/**
	 *	This method calls the common config method
	 *	@param 	     apiStatus (for checking main switch status)
	 *	@Tablename   api_provider_common_config
	 *	@return common config
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<ApiProviderCommonConfig> commonConfigActiveList(String apiStatus,String apiFlightStatus,AppKeyVo appKeyVo) {
		Session session = null;
		List<ApiProviderCommonConfig> commonConfigList = new ArrayList<ApiProviderCommonConfig>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(ApiProviderCommonConfig.class);
			Conjunction objConjunction = Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("companyId", appKeyVo.getCompanyId()));
			objConjunction.add(Restrictions.eq("configId", appKeyVo.getConfigId()));
			objConjunction.add(Restrictions.eq("active", true));
			objConjunction.add(Restrictions.eq(apiStatus, true));
			objConjunction.add(Restrictions.eq(apiFlightStatus, true));
			criteria.add(objConjunction);
			commonConfigList = criteria.list();
		} catch (HibernateException e) {
			logger.error("HibernateException in commonConfigActiveList " + e);
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return commonConfigList;
	}

	/**
	 *  This method calls the tbo config table
	 *  @param    tboFlightEnvironment 
	 *  @Tablename   api_provider_tbo_Config
	 *  @return      apiProviderTboFlightConfigList 
	 */
	@Override
	public List<ApiProviderTboConfig> getApiProviderTboConfigList(String tboFlightEnvironment) {	
		Session session = null;
		List<ApiProviderTboConfig> apiProviderTboFlightConfigList = new ArrayList<ApiProviderTboConfig>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(ApiProviderTboConfig.class);
			criteria.add(Restrictions.eq("environment", tboFlightEnvironment));
			apiProviderTboFlightConfigList = criteria.list();
			
		} catch (HibernateException e) {
			logger.error("HibernateException in getApiProviderTboConfigList " + e);
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return apiProviderTboFlightConfigList;
	}

	/**
	 *  This method calls the tayyarah config table
	 *  @param    tboFlightEnvironment 
	 *  @Tablename   api_provider_tayyarah_Config
	 *  @return      apiProviderTayyarahHotelConfigList 
	 */
	@Override
	public List<ApiProviderTayyarahConfig> getApiProviderTayyarahConfigList(String tboFlightEnvironment) {		
		Session session = null;
		List<ApiProviderTayyarahConfig> apiProviderTayyarahHotelConfigList = new ArrayList<ApiProviderTayyarahConfig>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(ApiProviderTayyarahConfig.class);
			criteria.add(Restrictions.eq("environment", tboFlightEnvironment));
			apiProviderTayyarahHotelConfigList = criteria.list();

		} catch (HibernateException e) {
			logger.error("HibernateException in getApiProviderTayyarahConfigList " + e);
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return apiProviderTayyarahHotelConfigList;
	}
	/**
	 *  This method calls the Bluestar config table
	 *  @param    tboFlightEnvironment 
	 *  @Tablename   api_provider_bluestar_config
	 *  @return      apiProviderBluestarFlightConfigList
	 */
	@Override
	public List<ApiProviderBluestarConfig> getApiProviderBluestarConfigList(String tboFlightEnvironment) {		
		Session session = null;
		List<ApiProviderBluestarConfig> apiProviderBluestarFlightConfigList = new ArrayList<ApiProviderBluestarConfig>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(ApiProviderBluestarConfig.class);
			criteria.add(Restrictions.eq("environment", tboFlightEnvironment));
			apiProviderBluestarFlightConfigList = criteria.list();

		} catch (HibernateException e) {
			logger.error("HibernateException in getApiProviderBluestarConfigList " + e);
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return apiProviderBluestarFlightConfigList;
	}

	@Override
	public List<ApiProviderDesiyaConfig> getApiProviderDesiyaConfigList(String tboFlightEnvironment) {// TODO Auto-generated method stub
		Session session = null;
		List<ApiProviderDesiyaConfig> apiProviderDesiyaFlightConfigList = new ArrayList<ApiProviderDesiyaConfig>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(ApiProviderDesiyaConfig.class);
			criteria.add(Restrictions.eq("environment", tboFlightEnvironment));
			apiProviderDesiyaFlightConfigList = criteria.list();

		} catch (HibernateException e) {
			logger.error("HibernateException in getApiProviderDesiyaConfigList " + e);
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return apiProviderDesiyaFlightConfigList;
	}
	@Override
	public List<ApiProviderEtravelSmartConfig> getApiProviderEtravelSmartConfigList(String esmartBusEnvironment) {		
		Session session = null;
		List<ApiProviderEtravelSmartConfig> ApiProviderEtravelSmartConfigList = new ArrayList<ApiProviderEtravelSmartConfig>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(ApiProviderEtravelSmartConfig.class);
			criteria.add(Restrictions.eq("environment", esmartBusEnvironment));
			ApiProviderEtravelSmartConfigList = criteria.list();

		} catch (HibernateException e) {
			logger.error("HibernateException in getApiProviderEtravelSmartConfigList " + e);
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return ApiProviderEtravelSmartConfigList;
	} 
	@Override
	public List<ApiProviderTrawellTagConfig> getApiProviderTrawellTagConfigList(String trawelltagInsuranceEnvironment) {		
		Session session = null;
		List<ApiProviderTrawellTagConfig> apiProviderTrawellTagConfigList = new ArrayList<ApiProviderTrawellTagConfig>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(ApiProviderTrawellTagConfig.class);
			criteria.add(Restrictions.eq("environment", trawelltagInsuranceEnvironment));
			apiProviderTrawellTagConfigList = criteria.list();

		} catch (HibernateException e) {
			logger.error("HibernateException in getApiProviderTrawellTagConfigList " + e);
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return apiProviderTrawellTagConfigList;
	} 
}
