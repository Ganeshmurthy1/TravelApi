package com.tayyarah.company.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.bus.entity.BusMarkup;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusMarkupCommissionDetails;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CommissionDetails;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.company.entity.CompanyEntity;
import com.tayyarah.flight.entity.FlightMarkup;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.hotel.model.HotelMarkupCommissionDetails;
import com.tayyarah.insurance.entity.InsuranceMarkUpConfig;
import com.tayyarah.insurance.model.InsuranceMarkUp;
import com.tayyarah.insurance.model.InsuranceMarkupCommissionDetails;
import com.tayyarah.user.entity.FrontUserDetail;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.model.UserAuthenticationRequest;



public class CompanyDaoIMP implements CompanyDao {

	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	Logger logger = Logger.getLogger(CompanyDaoIMP.class);
	public List<Company> getCompanyBycompanyid(int companyid) {
		List<Company> list = null;		
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Company.class);
			criteria.add(Restrictions.eq("companyid", companyid));
			list = criteria.list();
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}
		return list;
	}

	public Company getCompany(int companyid) {
		Company company = null;	
		Session session=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Company.class);
			criteria.add(Restrictions.eq("companyid", companyid));
			company = (Company) criteria.uniqueResult();
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}
		return company;
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public String getAppKey(String email, String password) {
		String companyid = "invalid";
		String AppKey = "invalid";	
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Company.class);
			LogicalExpression logicalExpression  = Restrictions.and(Restrictions.eq("Email", email), 
					Restrictions.eq("Password", password));   
			criteria.add(logicalExpression);
			List<Company> list = criteria.list();
			for (Company company:list) {
				companyid = String.valueOf(company.getCompanyid());
			}
			int cid = Integer.parseInt(companyid);
			Criteria configCriteria = session.createCriteria(CompanyConfig.class);
			configCriteria.add(Restrictions.eq("company_id", cid));
			List<CompanyConfig> list2 = configCriteria.list();
			for (CompanyConfig CC:list2 ) {
				String configid = String.valueOf(CC.getConfig_id());
				String compnyid = String.valueOf(CC.getCompany_id());
				AppKey = configid + "-" + compnyid;

			}
		} catch (NumberFormatException ne) {
			logger.error("NumberFormatException", ne);
		} 
		catch (HibernateException e) {
			logger.error("Exception", e);
		}catch (Exception e) {
			logger.error("Exception", e);

		} finally {
			session.close();
			return AppKey;
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public String getCompanyName(String id) {
		String Company_userid = "invalid";
		try {
			session = sessionFactory.openSession();
			int cid = Integer.parseInt(id);
			Criteria configCriteria = session.createCriteria(Company.class);
			configCriteria.add(Restrictions.eq("companyid", cid));
			List<Company> list = configCriteria.list();
			if (list != null && list.size() > 0) {
				Company company = list.get(0);
				Company_userid = company.getCompany_userid();
			}
		} catch (HibernateException e) {
			logger.error("Exception", e);
		}   
		catch(NumberFormatException ne) {
			logger.error("NumberFormatException", ne);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			session.close();
			return Company_userid;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List<FlightMarkUpConfig> getFlightMarkUpConfig(String app_key) throws SQLException, Exception {
		FlightMarkup companyMarkup = null;
		String companyId = "invalid";
		String configId = "invalid";
		configId = app_key.substring(0, app_key.indexOf("-"));
		companyId = app_key.substring(app_key.indexOf("-") + 1);
		List<FlightMarkUpConfig> MArkUplist = new ArrayList<FlightMarkUpConfig>();
		try {
			session = sessionFactory.openSession();
			Criteria markupCriteria = session.createCriteria(FlightMarkup.class);
			LogicalExpression logicalExpression = Restrictions.and(
					Restrictions.eq("configId", Integer.parseInt(configId)),
					Restrictions.eq("companyId", Integer.parseInt(companyId)));
			markupCriteria.add(logicalExpression);
			markupCriteria.addOrder(Order.desc("positionOfMarkup"));
			markupCriteria.addOrder(Order.desc("createdDate"));
			List<FlightMarkup> list = markupCriteria.list();
			if (list != null && list.size() > 0) {
				Iterator<FlightMarkup> iterator = list.iterator();
				while (iterator.hasNext()) {
					companyMarkup = iterator.next();
					FlightMarkUpConfig FlightMarkUpConfig = new FlightMarkUpConfig();
					FlightMarkUpConfig.setName(companyMarkup.getName());
					FlightMarkUpConfig.setAccumulative(Boolean
							.parseBoolean(companyMarkup.getAccumulative()));
					FlightMarkUpConfig.setAirline(companyMarkup.getAirline());
					FlightMarkUpConfig.setFixedAmount(Boolean
							.parseBoolean(companyMarkup.getFixedAmount()));

					FlightMarkUpConfig.setClassOfService(companyMarkup
							.getClassOfService());
					FlightMarkUpConfig.setCompanyId(String
							.valueOf(companyMarkup.getCompanyId()));
					FlightMarkUpConfig.setConfigId(String.valueOf(companyMarkup
							.getConfigId()));
					FlightMarkUpConfig.setMarkup(String.valueOf(companyMarkup
							.getMarkup()));
					FlightMarkUpConfig.setMarkupId(String.valueOf(companyMarkup
							.getMarkupId()));
					FlightMarkUpConfig.setPositionOfMarkup(String
							.valueOf(companyMarkup.getPositionOfMarkup()));
					FlightMarkUpConfig.setOrigin(companyMarkup.getOrigin());
					FlightMarkUpConfig.setDestination(companyMarkup
							.getDestination());
					FlightMarkUpConfig.setArrvDate(companyMarkup.getArrvDate());
					FlightMarkUpConfig.setDeptDate(companyMarkup.getDeptDate());
					FlightMarkUpConfig.setCountry(companyMarkup.getCountry());
					FlightMarkUpConfig.setFareBasisCode(companyMarkup.getFareBaseCode());
					FlightMarkUpConfig.setDestinationType(companyMarkup.getDestinationType());
					FlightMarkUpConfig.setPromofareStartDate(companyMarkup.getPromofareStartDate());
					FlightMarkUpConfig.setPromofareEndDate(companyMarkup.getPromofareEndDate());
					MArkUplist.add(FlightMarkUpConfig);
				}
			}
		} 
		catch (HibernateException e) {
			logger.error("HibernateException", e);
		}
		catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			session.close();
			return MArkUplist;
		}

	}

	public List<FlightMarkUpConfig> getFlightMarkUpConfigFIrst(String companyId,String configId)
			throws SQLException, Exception {
		FlightMarkup flightMarkup = null;
		List<FlightMarkUpConfig> markupList = new ArrayList<FlightMarkUpConfig>();
		try {
			session = sessionFactory.openSession();			
			Criteria markupCriteria=session.createCriteria(FlightMarkup.class);			
			Conjunction objConjunction = Restrictions.conjunction();		
			objConjunction.add(Restrictions.eq("configId", Integer.parseInt(configId)));
			objConjunction.add(Restrictions.eq("companyId", Integer.parseInt(companyId)));
			objConjunction.add(Restrictions.eq("createdbyCompanyId", Integer.parseInt(companyId)));		
			markupCriteria.add(objConjunction);
			markupCriteria.addOrder(Order.desc("positionOfMarkup"));
			markupCriteria.addOrder(Order.desc("createdDate"));
			List<FlightMarkup> list = markupCriteria.list();
			if (list!=null && list.size() > 0) {
				Iterator<FlightMarkup> iterator = list.iterator();
				while (iterator.hasNext()) {
					flightMarkup = iterator.next();
					FlightMarkUpConfig FlightMarkUpConfig = new FlightMarkUpConfig();
					FlightMarkUpConfig.setName(flightMarkup.getName());
					FlightMarkUpConfig.setAccumulative(Boolean
							.parseBoolean(flightMarkup.getAccumulative()));
					FlightMarkUpConfig.setAirline(flightMarkup.getAirline());
					FlightMarkUpConfig.setFixedAmount(Boolean
							.parseBoolean(flightMarkup.getFixedAmount()));

					FlightMarkUpConfig.setClassOfService(flightMarkup
							.getClassOfService());
					FlightMarkUpConfig.setCompanyId(String
							.valueOf(flightMarkup.getCompanyId()));
					FlightMarkUpConfig.setConfigId(String.valueOf(flightMarkup
							.getConfigId()));
					FlightMarkUpConfig.setMarkup(String.valueOf(flightMarkup
							.getMarkup()));
					FlightMarkUpConfig.setMarkupId(String.valueOf(flightMarkup
							.getMarkupId()));
					FlightMarkUpConfig.setPositionOfMarkup(String
							.valueOf(flightMarkup.getPositionOfMarkup()));
					FlightMarkUpConfig.setOrigin(flightMarkup.getOrigin());
					FlightMarkUpConfig.setDestination(flightMarkup
							.getDestination());
					FlightMarkUpConfig.setArrvDate(flightMarkup.getArrvDate());
					FlightMarkUpConfig.setDeptDate(flightMarkup.getDeptDate());
					FlightMarkUpConfig.setCountry(flightMarkup.getCountry());
					FlightMarkUpConfig.setFareBasisCode(flightMarkup.getFareBaseCode());
					FlightMarkUpConfig.setDestinationType(flightMarkup.getDestinationType());
					FlightMarkUpConfig.setPromofareStartDate(flightMarkup.getPromofareStartDate());
					FlightMarkUpConfig.setPromofareEndDate(flightMarkup.getPromofareEndDate());
					markupList.add(FlightMarkUpConfig);
				}
			}
		}
		catch (HibernateException e) {
			logger.error("HibernateException", e);
		} 
		catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			session.close();			
		}
		return markupList;
	}

	public List<FlightMarkUpConfig> getFlightMarkUpConfigRest(String companyId,String newCompanyId,int confID)
			throws SQLException, Exception {
		FlightMarkup flightMarkup = null;
		List<FlightMarkUpConfig> markupList = new ArrayList<FlightMarkUpConfig>();
		try {
			session = sessionFactory.openSession();
			Criteria markupCriteria=session.createCriteria(FlightMarkup.class);			
			Conjunction objConjunction = Restrictions.conjunction();			
			objConjunction.add(Restrictions.eq("configId", confID));
			objConjunction.add(Restrictions.eq("companyId", Integer.parseInt(companyId)));
			objConjunction.add(Restrictions.eq("createdbyCompanyId", Integer.parseInt(newCompanyId)));			
			markupCriteria.add(objConjunction);
			markupCriteria.addOrder(Order.desc("positionOfMarkup"));
			markupCriteria.addOrder(Order.desc("createdDate"));
			List<FlightMarkup> list = markupCriteria.list();
			if (list !=null && list.size() > 0) {
				Iterator<FlightMarkup> iterator = list.iterator();
				while (iterator.hasNext()) {
					flightMarkup = iterator.next();
					FlightMarkUpConfig FlightMarkUpConfig = new FlightMarkUpConfig();
					FlightMarkUpConfig.setName(flightMarkup.getName());
					FlightMarkUpConfig.setAccumulative(Boolean
							.parseBoolean(flightMarkup.getAccumulative()));
					FlightMarkUpConfig.setAirline(flightMarkup.getAirline());
					FlightMarkUpConfig.setFixedAmount(Boolean
							.parseBoolean(flightMarkup.getFixedAmount()));
					FlightMarkUpConfig.setClassOfService(flightMarkup
							.getClassOfService());
					FlightMarkUpConfig.setCompanyId(String
							.valueOf(flightMarkup.getCompanyId()));
					FlightMarkUpConfig.setConfigId(String.valueOf(flightMarkup
							.getConfigId()));
					FlightMarkUpConfig.setMarkup(String.valueOf(flightMarkup
							.getMarkup()));
					FlightMarkUpConfig.setMarkupId(String.valueOf(flightMarkup
							.getMarkupId()));
					FlightMarkUpConfig.setPositionOfMarkup(String
							.valueOf(flightMarkup.getPositionOfMarkup()));

					FlightMarkUpConfig.setOrigin(flightMarkup.getOrigin());
					FlightMarkUpConfig.setDestination(flightMarkup
							.getDestination());
					FlightMarkUpConfig.setArrvDate(flightMarkup.getArrvDate());
					FlightMarkUpConfig.setDeptDate(flightMarkup.getDeptDate());
					FlightMarkUpConfig.setCountry(flightMarkup.getCountry());
					FlightMarkUpConfig.setFareBasisCode(flightMarkup.getFareBaseCode());
					FlightMarkUpConfig.setDestinationType(flightMarkup.getDestinationType());
					FlightMarkUpConfig.setPromofareStartDate(flightMarkup.getPromofareStartDate());
					FlightMarkUpConfig.setPromofareEndDate(flightMarkup.getPromofareEndDate());
					markupList.add(FlightMarkUpConfig);
				}
			}
		}
		catch (HibernateException e) {
			logger.error("############## getFlightMarkUpConfigRest company id-"+companyId+" HibernateException", e);
		} catch (Exception e) {
			logger.error("############## getFlightMarkUpConfigRest company id-"+companyId+" Exception", e);			
		} finally {
			session.close();			
		}
		return markupList;
	}

	public HotelMarkupCommissionDetails getHotelMarkupCommissionDetails(AppKeyVo appKeyVo ){
		HotelMarkupCommissionDetails hotelMarkupCommissionDetails=new HotelMarkupCommissionDetails();
		CompanyConfig companyConfig = null;
		try {
			session = sessionFactory.openSession();
			companyConfig = appKeyVo.getCompanyConfig();
			if(companyConfig != null)
			{
				hotelMarkupCommissionDetails.setRateType(companyConfig.getRateTypeHotel());
				hotelMarkupCommissionDetails.setCompanyId(String.valueOf(appKeyVo.getCompanyId()));
			}
			List<CommissionDetails> commissionDetailslist=new ArrayList<CommissionDetails>();
			getcommissionDetailsHotel(commissionDetailslist, String.valueOf(appKeyVo.getConfigId()));
			hotelMarkupCommissionDetails.initCommissionDetailsMap(commissionDetailslist);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			session.close();			
		}
		return hotelMarkupCommissionDetails;
	}
	public void getcommissionDetailsHotel(List<CommissionDetails> commissionDetailslist,String configId){
		CommissionDetails commissionDetails = new CommissionDetails();
		CompanyConfig companyConfig = null;
		int createdbyComapnyId=-1;
		int ComapnyId=-1;
		int parentConfigId=-1;
		try {
			Criteria configCriteria = session.createCriteria(CompanyConfig.class);
			configCriteria.add(Restrictions.eq("config_id", Integer.parseInt(configId)));
			companyConfig = (CompanyConfig) configCriteria.uniqueResult();
			ComapnyId=companyConfig.getCompany_id();
			createdbyComapnyId=companyConfig.getCreatedbyComapnyId();
			parentConfigId=companyConfig.getParent_config_id();
			commissionDetails.setCompanyId(String.valueOf(companyConfig.getCompany_id()));
			commissionDetails.setCommissionType(companyConfig.getCommissionTypeHotel());
			commissionDetails.setRateType(companyConfig.getRateTypeHotel());
			if(companyConfig.getRateTypeHotel().equalsIgnoreCase("COMMISSION")){
				commissionDetails.setCommissionAmount(companyConfig.getCommissionAmountHotel());
			}else{
				commissionDetails.setCommissionAmount(new BigDecimal(0));
			}
		}
		catch (HibernateException e) {
			logger.error("HibernateException", e);
		}  
		catch (Exception e) {
			//System.out.println("catch");
			logger.error("Exception", e);
		} finally {
			commissionDetailslist.add(commissionDetails);
			if(createdbyComapnyId != ComapnyId && createdbyComapnyId!=-1){
				getcommissionDetailsHotel(commissionDetailslist, String.valueOf(parentConfigId));	
			}
		}
	}

	public CompanyConfig getcompanyconfig(String app_key){
		CompanyConfig companyConfig = null;
		String companyId = "invalid";
		String configId = "invalid";
		configId = app_key.substring(0, app_key.indexOf("-"));
		companyId = app_key.substring(app_key.indexOf("-") + 1);		
		try {
			session = sessionFactory.openSession();
			Criteria configCriteria = session.createCriteria(CompanyConfig.class);
			Conjunction objConjunction=Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("config_id", Integer.parseInt(configId)));
			objConjunction.add(Restrictions.eq("company_id", Integer.parseInt(companyId)));
			configCriteria.add(objConjunction);
			companyConfig = (CompanyConfig) configCriteria.uniqueResult();	

		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			session.close();			
		}
		return companyConfig;
	}


	@SuppressWarnings({ "finally", "unchecked" })
	public MarkupCommissionDetails getFlightMarkupCommissionDetailsByCompanyId(AppKeyVo appKeyVo, MarkupCommissionDetails  markupCommissionDetails)throws SQLException, Exception {	
		Session sess = null;		
		String sql = null;
		SQLQuery query = null;	
		CompanyConfig companyConfig = null;
		List<CommissionDetails> commissionDetailslist = new ArrayList<CommissionDetails>();

		try{
			sess = sessionFactory.openSession();		

			companyConfig = appKeyVo.getCompanyConfig();
			if(companyConfig !=null)
			{
				markupCommissionDetails.setRateType(companyConfig.getRateTypeFlight());
				markupCommissionDetails.setCompanyId(String.valueOf(appKeyVo.getCompanyId()));
				CommissionDetails commissionDetails = new CommissionDetails();
				commissionDetails.setCompanyId(String.valueOf(companyConfig.getCompany_id()));
				commissionDetails.setCommissionType(companyConfig.getCommissionTypeFlight());
				commissionDetails.setRateType(companyConfig.getRateTypeFlight());
				if(companyConfig.getRateTypeFlight().equalsIgnoreCase("COMMISSION")){
					commissionDetails.setCommissionAmount(companyConfig.getCommissionAmountFlight());
				}else{
					commissionDetails.setCommissionAmount(new BigDecimal(0));
				}
				commissionDetailslist.add(commissionDetails);
				int level = 0;
				while (companyConfig != null && companyConfig.getCompany_id()>=1) {
					int parentCompanyid = companyConfig.getCreatedbyComapnyId();
					int parentConfigId = companyConfig.getParent_config_id();					
					Criteria configCriteria1 = sess.createCriteria(CompanyConfig.class);
					Conjunction objConjunction1=Restrictions.conjunction();
					objConjunction1.add(Restrictions.eq("config_id", parentConfigId));
					objConjunction1.add(Restrictions.eq("company_id", parentCompanyid));
					configCriteria1.add(objConjunction1);
					companyConfig = (CompanyConfig) configCriteria1.uniqueResult();						
					if(companyConfig != null)
					{
						CommissionDetails parentcommissionDetails = new CommissionDetails();
						parentcommissionDetails.setCompanyId(String.valueOf(companyConfig.getCompany_id()));
						parentcommissionDetails.setCommissionType(companyConfig.getCommissionTypeFlight());
						parentcommissionDetails.setRateType(companyConfig.getRateTypeFlight());
						if(companyConfig.getRateTypeFlight().equalsIgnoreCase("COMMISSION")){
							parentcommissionDetails.setCommissionAmount(companyConfig.getCommissionAmountFlight());
						}else{
							parentcommissionDetails.setCommissionAmount(new BigDecimal(0));
						}
						commissionDetailslist.add(parentcommissionDetails);

						level ++;
						if(companyConfig.getCompany_id()==1)
						{
							logger.info("#####################FlightMarkupCommissionDetails super parent break");							
							logger.info("#####################FlightMarkupCommissionDetails for companyid"+companyConfig.getCompany_id());
							break;
						}
					}
					else
					{
						logger.info("#####################FlightMarkupCommissionDetails companyConfig null break");						
						logger.info("#####################FlightMarkupCommissionDetails for companyid"+parentCompanyid);
						break;
					}					
				}						
				markupCommissionDetails.setCommissionDetailslist(commissionDetailslist);	
			}			
		}catch (HibernateException e) {	
			logger.error("#####################FlightMarkupCommissionDetails HibernateException for companyid"+appKeyVo.getCompanyId(), e);			
			return markupCommissionDetails;

		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return markupCommissionDetails;
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Map<String,List<FlightMarkUpConfig>> getFlightMarkUpConfigMapByCompanyId(AppKeyVo appKeyVo , Map<String,List<FlightMarkUpConfig>>  markupMap)throws SQLException, Exception {	
		Session sess = null;		
		Company company = null;
		Company parentcompany = null;
		CompanyConfig companyConfig = null;
		CompanyConfig parentCompanyConfig = null;
		String sql = null;
		SQLQuery query = null;			
		try{
			sess = sessionFactory.openSession();
			companyConfig = appKeyVo.getCompanyConfig();				
			company = appKeyVo.getCompany();				
			if(company != null && companyConfig != null)
			{				
				Criteria markupCriteria=sess.createCriteria(FlightMarkup.class);
				Conjunction markupConjunction=Restrictions.conjunction();
				markupConjunction.add(Restrictions.eq("configId", companyConfig.getConfig_id()));
				markupConjunction.add(Restrictions.eq("companyId",  company.getCompanyid()));
				markupConjunction.add(Restrictions.eq("createdbyCompanyId",  company.getCompanyid()));
				markupCriteria.add(markupConjunction);
				markupCriteria.addOrder(Order.desc("positionOfMarkup"));
				markupCriteria.addOrder(Order.desc("createdDate"));
				List<FlightMarkup> flightMarkupList = markupCriteria.list();
				List<FlightMarkUpConfig> flightMarkUpConfigList = markupMap.get(String.valueOf(company.getCompanyid()));
				if(flightMarkUpConfigList == null)
					flightMarkUpConfigList = new ArrayList<FlightMarkUpConfig>();
				if (flightMarkupList!=null && flightMarkupList.size() > 0) {
					Iterator<FlightMarkup> iterator = flightMarkupList.iterator();
					while (iterator.hasNext()) {
						FlightMarkup flightMarkup = iterator.next();
						FlightMarkUpConfig FlightMarkUpConfig = new FlightMarkUpConfig();
						FlightMarkUpConfig.setName(flightMarkup.getName());
						FlightMarkUpConfig.setAccumulative(Boolean
								.parseBoolean(flightMarkup.getAccumulative()));
						FlightMarkUpConfig.setAirline(flightMarkup.getAirline());
						FlightMarkUpConfig.setFixedAmount(Boolean
								.parseBoolean(flightMarkup.getFixedAmount()));

						FlightMarkUpConfig.setClassOfService(flightMarkup
								.getClassOfService());
						FlightMarkUpConfig.setCompanyId(String
								.valueOf(flightMarkup.getCompanyId()));
						FlightMarkUpConfig.setConfigId(String.valueOf(flightMarkup
								.getConfigId()));
						FlightMarkUpConfig.setMarkup(String.valueOf(flightMarkup
								.getMarkup()));
						FlightMarkUpConfig.setMarkupId(String.valueOf(flightMarkup
								.getMarkupId()));
						FlightMarkUpConfig.setPositionOfMarkup(String
								.valueOf(flightMarkup.getPositionOfMarkup()));

						FlightMarkUpConfig.setOrigin(flightMarkup.getOrigin());
						FlightMarkUpConfig.setDestination(flightMarkup
								.getDestination());
						FlightMarkUpConfig.setArrvDate(flightMarkup.getArrvDate());
						FlightMarkUpConfig.setDeptDate(flightMarkup.getDeptDate());
						FlightMarkUpConfig.setCountry(flightMarkup.getCountry());
						FlightMarkUpConfig.setFareBasisCode(flightMarkup.getFareBaseCode());
						FlightMarkUpConfig.setDestinationType(flightMarkup.getDestinationType());
						FlightMarkUpConfig.setPromofareStartDate(flightMarkup.getPromofareStartDate());
						FlightMarkUpConfig.setPromofareEndDate(flightMarkup.getPromofareEndDate());							
						flightMarkUpConfigList.add(FlightMarkUpConfig);
					}
				}					
				markupMap.put(String.valueOf(company.getCompanyid()), flightMarkUpConfigList);
				if(company.getCompanyid() == 1)
				{
					logger.info("####################  company is super user-"+company);					
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
						logger.info("########################## DB Markup map termination--"+markupMap);
						return markupMap;
					}
					else if(parentcompany != null && parentCompanyConfig != null)
					{
						logger.info("########################## DB Markup map recursion call--for companyid "+parentcompany.getCompanyid());
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
	public Map<String,List<FlightMarkUpConfig>> getParentMarkupMap(CompanyConfig companyConfig, CompanyConfig parentCompConfig, Map<String,List<FlightMarkUpConfig>>  markupMap)throws SQLException, Exception {	
		Session sess = null;		
		Company company = null;	
		Company parentcompany = null;
		Company superparentcompany = null;	
		CompanyConfig superParentCompConfig = null;
		String sql = null;
		SQLQuery query = null;				
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
				Criteria markupCriteria=sess.createCriteria(FlightMarkup.class);
				Conjunction markupConjunction=Restrictions.conjunction();
				markupConjunction.add(Restrictions.eq("configId", companyConfig.getConfig_id()));
				markupConjunction.add(Restrictions.eq("companyId",  company.getCompanyid()));
				markupConjunction.add(Restrictions.eq("createdbyCompanyId",  parentcompany.getCompanyid()));
				markupCriteria.add(markupConjunction);
				markupCriteria.addOrder(Order.desc("positionOfMarkup"));
				markupCriteria.addOrder(Order.desc("createdDate"));
				List<FlightMarkup> flightMarkupList = markupCriteria.list();
				List<FlightMarkUpConfig> flightMarkUpConfigList = new ArrayList<FlightMarkUpConfig>();
				if (flightMarkupList.size() > 0) {
					Iterator<FlightMarkup> iterator = flightMarkupList.iterator();
					while (iterator.hasNext()) {
						FlightMarkup flightMarkup = iterator.next();
						FlightMarkUpConfig FlightMarkUpConfig = new FlightMarkUpConfig();
						FlightMarkUpConfig.setName(flightMarkup.getName());
						FlightMarkUpConfig.setAccumulative(Boolean
								.parseBoolean(flightMarkup.getAccumulative()));
						FlightMarkUpConfig.setAirline(flightMarkup.getAirline());
						FlightMarkUpConfig.setFixedAmount(Boolean
								.parseBoolean(flightMarkup.getFixedAmount()));

						FlightMarkUpConfig.setClassOfService(flightMarkup
								.getClassOfService());
						FlightMarkUpConfig.setCompanyId(String
								.valueOf(flightMarkup.getCompanyId()));
						FlightMarkUpConfig.setConfigId(String.valueOf(flightMarkup
								.getConfigId()));
						FlightMarkUpConfig.setMarkup(String.valueOf(flightMarkup
								.getMarkup()));
						FlightMarkUpConfig.setMarkupId(String.valueOf(flightMarkup
								.getMarkupId()));
						FlightMarkUpConfig.setPositionOfMarkup(String
								.valueOf(flightMarkup.getPositionOfMarkup()));
						FlightMarkUpConfig.setOrigin(flightMarkup.getOrigin());
						FlightMarkUpConfig.setDestination(flightMarkup
								.getDestination());
						FlightMarkUpConfig.setArrvDate(flightMarkup.getArrvDate());
						FlightMarkUpConfig.setDeptDate(flightMarkup.getDeptDate());
						FlightMarkUpConfig.setCountry(flightMarkup.getCountry());
						FlightMarkUpConfig.setFareBasisCode(flightMarkup.getFareBaseCode());
						FlightMarkUpConfig.setDestinationType(flightMarkup.getDestinationType());
						FlightMarkUpConfig.setPromofareStartDate(flightMarkup.getPromofareStartDate());
						FlightMarkUpConfig.setPromofareEndDate(flightMarkup.getPromofareEndDate());							
						flightMarkUpConfigList.add(FlightMarkUpConfig);
					}
				}					
				markupMap.put(String.valueOf(parentCompConfig.getCompany_id()), flightMarkUpConfigList);			

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
	public void getParentCompanyIds(String id,List<String> compnayIdList) {
		Company company = null;
		String parent_company_userid = "invalid";
		try {
			int cid = Integer.parseInt(id);
			session = sessionFactory.openSession();
			Criteria companyCriteria=session.createCriteria(Company.class);
			companyCriteria.add(Restrictions.eq("companyid",cid));
			List<Company> list = companyCriteria.list();		
			if (list!=null && list.size() > 0) {
				company = list.get(0);
				parent_company_userid = company.getParent_company_userid();
			}
		}
		catch (HibernateException e) {
			logger.error("Exception", e);
		} 
		catch (NumberFormatException ne) {
			logger.error("NumberFormatException", ne);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally{  
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
			Criteria companyCriteria=session.createCriteria(Company.class);
			companyCriteria.add(Restrictions.eq("company_userid",id));
			List<Company> list = companyCriteria.list();
			if (list!=null && list.size() > 0) {
				company = list.get(0);
				company_id = String.valueOf(company.getCompanyid());
				parent_company_userid=company.getParent_company_userid();
			}
		} 
		catch (HibernateException e) {
			logger.error("Exception", e);
		}catch (NumberFormatException ne) {
			logger.error("NumberFormatException", ne);
		} catch (Exception e) {
			logger.error("Exception", e);
		} 
		finally{ 
			session.close();
			compnayIdList.add(company_id);
			if(!id.equals(parent_company_userid.trim())){
				getParentCompanyIds(company_id,compnayIdList);
			}
		}
	}

	public User getUserByCompanyId(int companyid) throws Exception
	{
		User user = null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();	
			Criteria companycriteria = session.createCriteria(Company.class);
			Conjunction companyConjunction = Restrictions.conjunction();		
			companyConjunction.add(Restrictions.eq("id",companyid));
			companycriteria.add(companyConjunction);
			Company company = (Company) companycriteria.uniqueResult();

			Criteria criteria = session.createCriteria(User.class);
			Conjunction userConjunction = Restrictions.conjunction();		
			userConjunction.add(Restrictions.eq("Email",company.getEmail()));
			criteria.add(userConjunction);
			user = (User) criteria.uniqueResult();
		}
		catch (HibernateException e) {
			System.out.println("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}		
		return user;
	}

	public List<User> getParentCompanyList(List<Integer> userids) throws Exception
	{
		List<User> users = new ArrayList<User>();
		Session session = null;		
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(User.class);
			Conjunction userConjunction = Restrictions.conjunction();		
			userConjunction.add(Restrictions.in("id",userids));
			criteria.add(userConjunction);
			users = criteria.list();
		}
		catch (HibernateException e) {
			System.out.println("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}		
		return users;
	}

	public int getParentUserIdLevel1(Integer integer) throws Exception
	{
		Session session = null;
		int parentUserid=0;
		try{
			session = sessionFactory.openSession();	
			Criteria criteria = session.createCriteria(User.class);
			Conjunction userConjunction = Restrictions.conjunction();		
			userConjunction.add(Restrictions.eq("id",integer));			
			User user = (User) criteria.uniqueResult();
			if (user!=null) {
				parentUserid=user.getCreatedbyCompanyUserId();
			}
		}
		catch (HibernateException e) {
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}
		return parentUserid;
	}

	public List<Integer> getParentUserIdLevel2(Integer integer) throws Exception
	{
		List<Integer> userIds=new LinkedList<>();
		Session session = null;
		try{
			int parentUseridLevel1=getParentUserIdLevel1(integer);
			if(integer!=parentUseridLevel1){
				userIds.add(integer);
				userIds.add(parentUseridLevel1);
			}
			else{
				userIds.add(integer);
			}
			session = sessionFactory.openSession();	
			Criteria criteria = session.createCriteria(User.class);
			Conjunction userConjunction = Restrictions.conjunction();		
			userConjunction.add(Restrictions.eq("id",parentUseridLevel1));			
			User user = (User) criteria.uniqueResult();	
			if (user!=null) {
				int parentUseridLevel2=user.getCreatedbyCompanyUserId();
				if(parentUseridLevel2!=parentUseridLevel1){
					userIds.add(parentUseridLevel2);
				}
			}
		}
		catch (HibernateException e) {
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}
		Collections.reverse(userIds);
		return  userIds;
	}

	public  boolean checkAppkey(String app_key) 
	{
		Session session = null;
		String companyId = "invalid";
		String configId = "invalid";
		boolean result = false;
		try{
			configId = app_key.substring(0, app_key.indexOf("-"));
			companyId = app_key.substring(app_key.indexOf("-") + 1);
			session = sessionFactory.openSession();			
			Criteria configCriteria = session.createCriteria(CompanyConfig.class);
			Conjunction objConjunction=Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("config_id", Integer.parseInt(configId)));
			objConjunction.add(Restrictions.eq("company_id", Integer.parseInt(companyId)));
			configCriteria.add(objConjunction);
			if(configCriteria.uniqueResult()!=null)
				result = true;
		}
		catch (HibernateException  | NumberFormatException  e) {
			logger.error("#####################appkey not parsed correctly "+ e);
			result=false;
		}
		finally {
			session.close();
		}
		return result;

	}



	public HotelMarkupCommissionDetails getHotelMarkupCommissionDetailsOld(String app_key){
		CompanyConfig companyConfig = null;
		List<CompanyConfig> listCommission = null;
		String companyId = "-1";
		String configId = "-1";

		configId = app_key.substring(0, app_key.indexOf("-"));
		companyId = app_key.substring(app_key.indexOf("-") + 1);
		//logger.info("compId ;" + companyId + "  ,ConfigId  :" + configId);
		List<String> compnayIdList=new ArrayList<String>();//get by writing separate method
		getParentCompanyIds(companyId,compnayIdList);
		if(!compnayIdList.contains(companyId)){
			compnayIdList.add(companyId);
		}
		//List<CommissionDetails> commissionDetailslist = new ArrayList<CommissionDetails>();	
		Map<String,CommissionDetails> commissionmap = new HashMap<String,CommissionDetails>();

		HotelMarkupCommissionDetails hotelmarkupCommissionDetails = new HotelMarkupCommissionDetails("NET", companyId, commissionmap);

		//logger.info("compnayIdList  :" + compnayIdList + "  ,compnayIdList size :" +compnayIdList.size());
		StringBuilder commissioncompanyqueryin = new StringBuilder();
		List<Integer> compnayIdListInt=new ArrayList<Integer>();
		for (int i = 0; i < compnayIdList.size(); i++) {
			if(i == compnayIdList.size()-1)
				commissioncompanyqueryin.append(""+compnayIdList.get(i)+"");
			else
				commissioncompanyqueryin.append(""+compnayIdList.get(i)+",");
			compnayIdListInt.add(Integer.parseInt(compnayIdList.get(i)));
		}	


		Session session = null;
		try{
			session = sessionFactory.openSession();	
			Criteria crit = session.createCriteria(CompanyConfig.class);
			Criterion criterionconfig = Restrictions.eq("config_id", Integer.parseInt(configId));
			Criterion criterioncompany = Restrictions.eq("company_id", Integer.parseInt(companyId));
			LogicalExpression orExp = Restrictions.and(criterionconfig, criterioncompany);
			crit.add(orExp);
			companyConfig = (CompanyConfig) crit.uniqueResult();	
			if(companyConfig != null && companyConfig.getRateTypeHotel() != null)
			{
				Criteria critcommissions = session.createCriteria(CompanyConfig.class);			
				critcommissions.add(Restrictions.in("company_id", compnayIdListInt));
				listCommission = critcommissions.list();
				//logger.info("listCommission  :" + listCommission +",listCommission size :" +listCommission.size());
				if(companyConfig.getRateTypeHotel().equalsIgnoreCase("Commission")){
					for (CompanyConfig commissionitemconfig : listCommission) {
						CommissionDetails companycommissiondetails = new CommissionDetails();					
						companycommissiondetails.setCompanyId(String.valueOf(commissionitemconfig.getCompany_id()));
						companycommissiondetails.setCommissionType(commissionitemconfig.getCommissionTypeHotel());
						companycommissiondetails.setRateType(commissionitemconfig.getRateTypeHotel());
						if(commissionitemconfig.getRateTypeHotel().equalsIgnoreCase("Commission")){
							companycommissiondetails.setCommissionAmount(commissionitemconfig.getCommissionAmountHotel());
						}else{
							companycommissiondetails.setCommissionAmount(new BigDecimal(0));
						}
						commissionmap.put(companycommissiondetails.getCompanyId(),companycommissiondetails);
					}														
					hotelmarkupCommissionDetails = new HotelMarkupCommissionDetails(companyConfig.getRateTypeHotel(), companyId, commissionmap);

				}
				else
				{
					hotelmarkupCommissionDetails = new HotelMarkupCommissionDetails(companyConfig.getRateTypeHotel(), companyId, commissionmap);

				}
			}


		}catch (HibernateException e) {
			logger.error("HibernateException", e);
			throw e; 
		}
		catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();  
		}
		return hotelmarkupCommissionDetails;		
	}
	@SuppressWarnings({ "unchecked", "finally" })
	public String getCompanyCurrencyCode(int companyId) {
		String CurrencyCode = "invalid";
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(Company.class);
			criteria.add(Restrictions.eq("companyid", companyId));
			List<Company> list = criteria.list();
			if(list!=null && list.size() > 0) {
				Company company = list.get(0);
				CurrencyCode = company.getCurrencyCode();
			}

		} 
		catch (HibernateException e) {
			logger.error("Exception", e);
		}
		catch (NumberFormatException ne) {
			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);

		} finally {
			session.close();
			return CurrencyCode;
		}
	}


	public int getParentConfId(String companyId,String configId) throws HibernateException,
	NumberFormatException, Exception {
		session = sessionFactory.openSession();
		int result = -1;
		Criteria crit = session.createCriteria(CompanyConfig.class);
		Criterion criterionconfig = Restrictions.eq("config_id", Integer.parseInt(configId));
		Criterion criterioncompany = Restrictions.eq("company_id", Integer.parseInt(companyId));
		LogicalExpression orExp = Restrictions.and(criterionconfig, criterioncompany);
		crit.add(orExp);
		List<CompanyConfig> list = crit.list();
		if (list!=null && list.size() > 0) {
			result = list.get(0).getParent_config_id();
		}
		session.close();
		return result;
	}


	@SuppressWarnings({ "unchecked", "finally" })
	public String getBillingCoyuntry() {
		String biiling_country = "invalid";
		try {
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(Company.class);
			crit.add(Restrictions.eq("companyid",1));
			List<Company> list = crit.list();
			if (list!=null && list.size() > 0) {
				Company company = list.get(0);
				biiling_country = company.getBillingcountry();
			}

		} 
		catch (HibernateException e) {
			logger.error("Exception", e);
		}
		catch (NumberFormatException ne) {
			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);

		} finally {			
			session.close();
			return biiling_country;
		}
	}


	public boolean insertRMConfigTripDetails(RmConfigTripDetailsModel rmConfigTripDetailsModel){
		boolean isInserted = false;
		Session session = null;	
		Transaction transaction = null;
		try{			
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(rmConfigTripDetailsModel);
			transaction.commit();
			isInserted = true;
		}catch(Exception e){
			logger.error("insertRMConfigTripDetails Exception", e);
		}		
		finally {			
			session.close();
		}
		return isInserted;
	}

	public Company getParentCompany(Company company)
			throws HibernateException {
		Session session = null;		
		Criteria crit = null;
		Company parentcompany = null;		
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(Company.class);			
			crit.add(Restrictions.eq("company_userid", company.getParent_company_userid()));			
			parentcompany = (Company) crit.uniqueResult();				
		}catch (HibernateException e) {	
			logger.error("getParentCompany Exception", e);
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return parentcompany;
	}

	@Override
	public User getUserByCompanyEmail(String email) throws Exception {
		User user = null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();	
			Criteria criteria = session.createCriteria(User.class);
			Conjunction userConjunction = Restrictions.conjunction();		
			userConjunction.add(Restrictions.eq("Email",email));
			criteria.add(userConjunction);
			user = (User) criteria.uniqueResult();
		}
		catch (HibernateException e) {
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}		
		return user;
	}

	@Override
	public User getUserById(int id) {
		User user = null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();	
			Criteria criteria = session.createCriteria(User.class);
			Conjunction userConjunction = Restrictions.conjunction();		
			userConjunction.add(Restrictions.eq("id",id));
			criteria.add(userConjunction);
			user = (User) criteria.uniqueResult();
		}
		catch (HibernateException e) {
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}		
		return user;
	}

	public Map<String,List<BusMarkUpConfig>> getBusMarkUpMapByCompanyId(String companyid, String configId, Map<String,List<BusMarkUpConfig>>  markupMap)throws SQLException, Exception {	
		Session sess = null;		
		Company company = null;
		Company parentcompany = null;
		CompanyConfig companyConfig = null;
		CompanyConfig parentCompanyConfig = null;
		String sql = null;
		SQLQuery query = null;		
		try{
			sess = sessionFactory.openSession();
			Criteria configCriteria = sess.createCriteria(CompanyConfig.class);
			Conjunction objConjunction=Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("config_id", Integer.parseInt(configId)));
			objConjunction.add(Restrictions.eq("company_id", Integer.parseInt(companyid)));
			configCriteria.add(objConjunction);
			companyConfig = (CompanyConfig) configCriteria.uniqueResult();				

			Criteria configCriteria1 = sess.createCriteria(Company.class);
			configCriteria1.add(Restrictions.eq("companyid", Integer.parseInt(companyid)));
			company = (Company) configCriteria1.uniqueResult();

			if(company != null && companyConfig != null)
			{				
				Criteria markupCriteria=sess.createCriteria(BusMarkup.class);
				Conjunction markupConjunction=Restrictions.conjunction();
				markupConjunction.add(Restrictions.eq("configId", companyConfig.getConfig_id()));
				markupConjunction.add(Restrictions.eq("companyId",  company.getCompanyid()));
				markupConjunction.add(Restrictions.eq("createdbyCompanyId",  company.getCompanyid()));
				markupCriteria.add(markupConjunction);
				markupCriteria.addOrder(Order.desc("positionOfMarkup"));
				markupCriteria.addOrder(Order.desc("createdDate"));
				List<BusMarkup> busMarkupList = markupCriteria.list();
				List<BusMarkUpConfig> busMarkUpConfigList = markupMap.get(String.valueOf(company.getCompanyid()));
				if(busMarkUpConfigList == null)
					busMarkUpConfigList = new ArrayList<BusMarkUpConfig>();
				if (busMarkupList!=null && busMarkupList.size() > 0) {
					Iterator<BusMarkup> iterator = busMarkupList.iterator();
					while (iterator.hasNext()) {
						BusMarkup busMarkup = iterator.next();
						BusMarkUpConfig busMarkUpConfig = new BusMarkUpConfig();
						busMarkUpConfig.setAccumulative(busMarkup.isAccumulative());
						busMarkUpConfig.setCompanyId(busMarkup.getCompanyId());
						busMarkUpConfig.setConfigId(busMarkup.getConfigId());
						busMarkUpConfig.setDestination(busMarkup.getDestination());
						busMarkUpConfig.setFixedAmount(busMarkup.isFixedAmount());
						busMarkUpConfig.setMarkupAmt(busMarkup.getMarkupAmount());
						busMarkUpConfig.setMarkupId(busMarkup.getMarkupId());
						busMarkUpConfig.setName(busMarkup.getMarkupName());
						busMarkUpConfig.setOnwardDate(busMarkup.getDepDate());
						busMarkUpConfig.setOrigin(busMarkup.getOrigin());
						busMarkUpConfig.setPositionOfMarkup(busMarkup.getPositionOfMarkup());
						busMarkUpConfig.setPromofareEndDate(busMarkup.getPromofareEndDate());
						busMarkUpConfig.setPromofareStartDate(busMarkup.getPromofareStartDate());
						busMarkUpConfig.setReturnDate(busMarkup.getArrDate());
						busMarkUpConfig.setBusOperators(busMarkup.getBusOperators());	
						busMarkUpConfig.setMarkUpPerPassenger(busMarkup.isMarkUpPerPassenger());
						busMarkUpConfig.setMarkUpOnTotal(busMarkup.isMarkUpOnTotal());
						busMarkUpConfigList.add(busMarkUpConfig);
					}
				}					
				markupMap.put(String.valueOf(company.getCompanyid()), busMarkUpConfigList);
				if(company.getCompanyid() == 1)
				{
					logger.info("####################  company is super user-"+company);					
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
						logger.info("########################## DB Markup map termination--"+markupMap);
						return markupMap;
					}
					else if(parentcompany != null && parentCompanyConfig != null)
					{
						logger.info("########################## DB Markup map recursion call--for companyid "+parentcompany.getCompanyid());
						markupMap = getParentMarkupMapForBus(companyConfig, parentCompanyConfig, markupMap);
					}
					else
					{
						return markupMap;
					}
				}
			}

		}catch (HibernateException e) {	
			logger.error("#####################3 marup call HibernateException for companyid"+companyid, e);			
			return markupMap;

		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return markupMap;
	}

	public Map<String,List<BusMarkUpConfig>> getParentMarkupMapForBus(CompanyConfig companyConfig, CompanyConfig parentCompConfig, Map<String,List<BusMarkUpConfig>>  markupMap)throws SQLException, Exception {	
		Session sess = null;		
		Company company = null;	
		Company parentcompany = null;
		Company superparentcompany = null;	
		CompanyConfig superParentCompConfig = null;
		String sql = null;
		SQLQuery query = null;			
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
				Criteria markupCriteria=sess.createCriteria(BusMarkup.class);
				Conjunction markupConjunction=Restrictions.conjunction();
				markupConjunction.add(Restrictions.eq("configId", companyConfig.getConfig_id()));
				markupConjunction.add(Restrictions.eq("companyId",  company.getCompanyid()));
				markupConjunction.add(Restrictions.eq("createdbyCompanyId",  parentcompany.getCompanyid()));
				markupCriteria.add(markupConjunction);
				markupCriteria.addOrder(Order.desc("positionOfMarkup"));
				markupCriteria.addOrder(Order.desc("createdDate"));
				List<BusMarkup> busMarkupList = markupCriteria.list();
				List<BusMarkUpConfig> busMarkUpConfigList = new ArrayList<BusMarkUpConfig>();
				if (busMarkupList.size() > 0) {
					Iterator<BusMarkup> iterator = busMarkupList.iterator();
					while (iterator.hasNext()) {
						BusMarkup busMarkup = iterator.next();
						BusMarkUpConfig busMarkUpConfig = new BusMarkUpConfig();
						busMarkUpConfig.setAccumulative(busMarkup.isAccumulative());
						busMarkUpConfig.setCompanyId(busMarkup.getCompanyId());
						busMarkUpConfig.setConfigId(busMarkup.getConfigId());
						busMarkUpConfig.setDestination(busMarkup.getDestination());
						busMarkUpConfig.setFixedAmount(busMarkup.isFixedAmount());
						busMarkUpConfig.setMarkupAmt(busMarkup.getMarkupAmount());
						busMarkUpConfig.setMarkupId(busMarkup.getMarkupId());
						busMarkUpConfig.setName(busMarkup.getMarkupName());
						busMarkUpConfig.setOnwardDate(busMarkup.getDepDate());
						busMarkUpConfig.setOrigin(busMarkup.getOrigin());
						busMarkUpConfig.setPositionOfMarkup(busMarkup.getPositionOfMarkup());
						busMarkUpConfig.setPromofareEndDate(busMarkup.getPromofareEndDate());
						busMarkUpConfig.setPromofareStartDate(busMarkup.getPromofareStartDate());
						busMarkUpConfig.setReturnDate(busMarkup.getArrDate());
						busMarkUpConfig.setBusOperators(busMarkup.getBusOperators());	
						busMarkUpConfig.setMarkUpPerPassenger(busMarkup.isMarkUpPerPassenger());
						busMarkUpConfig.setMarkUpOnTotal(busMarkup.isMarkUpOnTotal());
						busMarkUpConfigList.add(busMarkUpConfig);
					}
				}					
				markupMap.put(String.valueOf(parentCompConfig.getCompany_id()), busMarkUpConfigList);					

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
						markupMap = getParentMarkupMapForBus(parentCompConfig, superParentCompConfig,  markupMap);
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

	public BusMarkupCommissionDetails getBusMarkupCommissionDetailsByCompanyId(String companyid, String configId, BusMarkupCommissionDetails  markupCommissionDetails)throws SQLException, Exception {	
		Session sess = null;		
		String sql = null;
		SQLQuery query = null;	
		CompanyConfig companyConfig = null;
		List<CommissionDetails> commissionDetailslist = new ArrayList<CommissionDetails>();		
		try{
			sess = sessionFactory.openSession();		
			Criteria configCriteria = sess.createCriteria(CompanyConfig.class);
			Conjunction objConjunction=Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("config_id", Integer.parseInt(configId)));
			objConjunction.add(Restrictions.eq("company_id", Integer.parseInt(companyid)));
			configCriteria.add(objConjunction);
			companyConfig = (CompanyConfig) configCriteria.uniqueResult();
			if(companyConfig !=null)
			{
				markupCommissionDetails.setRateType(companyConfig.getRateTypeFlight());
				markupCommissionDetails.setCompanyId(companyid);
				CommissionDetails commissionDetails = new CommissionDetails();
				commissionDetails.setCompanyId(String.valueOf(companyConfig.getCompany_id()));
				commissionDetails.setCommissionType(companyConfig.getCommissionTypeFlight());
				commissionDetails.setRateType(companyConfig.getRateTypeFlight());
				if(companyConfig.getRateTypeFlight().equalsIgnoreCase("COMMISSION")){
					commissionDetails.setCommissionAmount(companyConfig.getCommissionAmountFlight());
				}else{
					commissionDetails.setCommissionAmount(new BigDecimal(0));
				}
				commissionDetailslist.add(commissionDetails);
				int level = 0;
				while (companyConfig != null && companyConfig.getCompany_id()>=1) {
					int parentCompanyid = companyConfig.getCreatedbyComapnyId();
					int parentConfigId = companyConfig.getParent_config_id();		

					Criteria configCriteria1 = sess.createCriteria(CompanyConfig.class);
					Conjunction objConjunction1=Restrictions.conjunction();
					objConjunction1.add(Restrictions.eq("config_id", parentConfigId));
					objConjunction1.add(Restrictions.eq("company_id", parentCompanyid));
					configCriteria1.add(objConjunction1);
					companyConfig = (CompanyConfig) configCriteria1.uniqueResult();					

					if(companyConfig != null)
					{
						CommissionDetails parentcommissionDetails = new CommissionDetails();
						parentcommissionDetails.setCompanyId(String.valueOf(companyConfig.getCompany_id()));
						parentcommissionDetails.setCommissionType(companyConfig.getCommissionTypeFlight());
						parentcommissionDetails.setRateType(companyConfig.getRateTypeFlight());
						if(companyConfig.getRateTypeFlight().equalsIgnoreCase("COMMISSION")){
							parentcommissionDetails.setCommissionAmount(companyConfig.getCommissionAmountFlight());
						}else{
							parentcommissionDetails.setCommissionAmount(new BigDecimal(0));
						}
						commissionDetailslist.add(parentcommissionDetails);

						level ++;
						if(companyConfig.getCompany_id()==1)
						{							
							logger.info("#####################BusMarkupCommissionDetailsByCompanyId super parent break");
							logger.info("#####################BusMarkupCommissionDetailsByCompanyId for companyid"+companyConfig.getCompany_id());
							break;
						}
					}
					else
					{						
						logger.info("#####################BusMarkupCommissionDetails  companyConfig null break");
						logger.info("#####################BusMarkupCommissionDetails for companyid"+parentCompanyid);
						break;
					}					
				}						
				markupCommissionDetails.setCommissionDetailslist(commissionDetailslist);	
			}			
		}catch (HibernateException e) {	
			logger.error("#####################BusMarkupCommissionDetails HibernateException for companyid"+companyid, e);			
			return markupCommissionDetails;

		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return markupCommissionDetails;
	}

	@Override
	public User getAuthentherisedUser(UserAuthenticationRequest userAuthReq) {
		User user = new User();
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(User.class);			
			Conjunction objConjunction = Restrictions.conjunction();			
			objConjunction.add(Restrictions.eq("Email", userAuthReq.getEmailId()));
			objConjunction.add(Restrictions.eq("Password", userAuthReq.getPassword()));
			objConjunction.add(Restrictions.eq("company_userid", userAuthReq.getPasskey()));
			criteria.add(objConjunction);
			user = (User) criteria.uniqueResult();
		}catch (Exception e) {
			logger.error("getAuthentherisedUser Exception "+ e);			
		}
		finally{
			session.close();
		}
		return user;
	}

	@Override
	public String getAppKeyByCompanyId(int companyId) {
		String appKey = "invalid";
		try {
			session = sessionFactory.openSession();
			Conjunction conj = Restrictions.conjunction();
			Criteria criteria = session.createCriteria(Company.class);
			criteria.add(Restrictions.eq("companyid", companyId));
			Company companyObj = (Company) criteria.uniqueResult();
			companyObj = companyObj != null ? companyObj : new Company();
			if (companyObj.getCompanyRole() != null) {
				Criteria configCriteria = session.createCriteria(CompanyConfig.class);
				conj.add(Restrictions.eq("company_id", companyObj.getCompanyid()));
				conj.add(Restrictions.eq("isActive", true));
				configCriteria.add(conj);
				List<CompanyConfig> companyConfigList = configCriteria.list();
				if (companyObj.getCompanyRole().isSuperUser() || companyObj.getCompanyRole().isAgent()
						|| companyObj.getCompanyRole().isDistributor()) {
					for (CompanyConfig companyConfig : companyConfigList) {
						if (companyConfig.getCompanyConfigType().isB2B()) {
							appKey = companyConfig.getAppKey();
							break;
						}
						else if (companyConfig.getCompanyConfigType().isB2C()) {
							appKey = companyConfig.getAppKey();
							break;
						}
					}
				}
				if (companyObj.getCompanyRole().isCorporate()) {
					for (CompanyConfig companyConfig : companyConfigList) {
						if (companyConfig.getCompanyConfigType().isB2E()) {
							appKey = companyConfig.getAppKey();
						}
					}
				}
			}
		} catch (NumberFormatException ne) {
			logger.error("NumberFormatException", ne);

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);

		} finally {
			session.close();
		}
		return appKey;
	}

	public FrontUserDetail getAuthorisedVisitor(UserAuthenticationRequest userAuthReq){		
		FrontUserDetail userDetail = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria=session.createCriteria(FrontUserDetail.class);
			Conjunction conjunction=Restrictions.conjunction();			
			conjunction.add(Restrictions.eq("email", userAuthReq.getEmailId()));
			conjunction.add(Restrictions.eq("password", userAuthReq.getPassword()));			
			criteria.add(conjunction);
			userDetail=(FrontUserDetail) criteria.uniqueResult();
		}catch (Exception e) {
			logger.error("Exception", e);
		}
		finally{
			session.close();
		}
		return userDetail;
	}

	public FrontUserDetail insertVisiterInfo(FrontUserDetail userDetail){		
		Transaction transaction=null;
		try{
			session=sessionFactory.openSession();
			transaction=session.beginTransaction();
			session.saveOrUpdate(userDetail);
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			logger.error("Exception", e);
			return null;
		}
		finally{
			session.close();
		}
		return userDetail;
	}
	public boolean  verifyingEmailExistence(String email){		
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(FrontUserDetail.class);
			criteria.add(Restrictions.eq("email", email));
			FrontUserDetail userDetail=(FrontUserDetail) criteria.uniqueResult();
			if(userDetail != null){
				return true;
			}
		}catch (Exception e) {
			logger.error("Exception", e);
		}		
		finally{
			session.close();
		}
		return false;
	}
	public FrontUserDetail  fetchDirectUserByid(long id){
		FrontUserDetail userDetail = null;
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FrontUserDetail.class);	
			criteria.add(Restrictions.eq("id", id));
			userDetail = (FrontUserDetail) criteria.uniqueResult();			
		}catch (Exception e) {
			logger.error("Exception", e);
		}
		finally{
			session.close();
		}
		return userDetail;
	}
	public List <Integer>  getCompanyChildIds(String companyUserId,String type){		
		List <Integer> companyIdList=new ArrayList <Integer>();
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(Company.class);
			if(type.equals("superCompanyUserid"))
				criteria.add(Restrictions.eq("super_company_userid", companyUserId));
			else
				criteria.add(Restrictions.eq("parent_company_userid", companyUserId));

			criteria.setProjection(Projections.property("companyid"));
			companyIdList = criteria.list();
		}catch (Exception e) {
			logger.error("Exception", e);
		}
		finally{
			session.close();
		}
		return companyIdList;
	}



	@Override
	public CompanyConfig getCompanyConfigUsingId(int config_id) {
		CompanyConfig companyConfig = null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();	
			Criteria criteria = session.createCriteria(CompanyConfig.class);
			criteria.add(Restrictions.eq("config_id",config_id));
			companyConfig = (CompanyConfig) criteria.uniqueResult();
		}
		catch (HibernateException e) {
			System.out.println("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}		
		return companyConfig;
	}
	public CompanyEntity getCompanyEntityUsingId(int id) {
		CompanyEntity companyEntity = null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();	
			Criteria criteria = session.createCriteria(CompanyEntity.class);
			criteria.add(Restrictions.eq("companyEntityId",id));
			companyEntity = (CompanyEntity) criteria.uniqueResult();
		}
		catch (HibernateException e) {
			System.out.println("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}		
		return companyEntity;
	}

	public Map<String,List<InsuranceMarkUp>> getInsuranceMarkUpMapByCompanyId(AppKeyVo appKeyVo, Map<String,List<InsuranceMarkUp>>  markupMap){	
		Session sess = null;		
		Company company = null;
		Company parentcompany = null;
		CompanyConfig companyConfig = null;
		CompanyConfig parentCompanyConfig = null;
		String sql = null;
		SQLQuery query = null;		
		try{
			sess = sessionFactory.openSession();

			companyConfig = appKeyVo.getCompanyConfig();				
			logger.info("####################  companyConfig-"+companyConfig);

			company = appKeyVo.getCompany();	
			logger.info("####################  company-"+company);
			if(company != null && companyConfig != null)
			{				
				Criteria markupCriteria=sess.createCriteria(InsuranceMarkUpConfig.class);
				Conjunction markupConjunction=Restrictions.conjunction();
				markupConjunction.add(Restrictions.eq("configId", companyConfig.getConfig_id()));
				markupConjunction.add(Restrictions.eq("companyId",  company.getCompanyid()));
				markupConjunction.add(Restrictions.eq("createdbyCompanyId",  company.getCompanyid()));
				markupCriteria.add(markupConjunction);
				markupCriteria.addOrder(Order.desc("positionOfMarkup"));
				markupCriteria.addOrder(Order.desc("createdDate"));
				List<InsuranceMarkUpConfig> insuranceMarkupList = markupCriteria.list();
				List<InsuranceMarkUp> InsuranceMarkUpList = markupMap.get(String.valueOf(company.getCompanyid()));
				if(InsuranceMarkUpList == null)
					InsuranceMarkUpList = new ArrayList<InsuranceMarkUp>();
				if (insuranceMarkupList!=null && insuranceMarkupList.size() > 0) {
					Iterator<InsuranceMarkUpConfig> iterator = insuranceMarkupList.iterator();
					while (iterator.hasNext()) {
						InsuranceMarkUpConfig insuranceMarkUpConfig = iterator.next();
						InsuranceMarkUp insuranceMarkUp = new InsuranceMarkUp();
						insuranceMarkUp.setAccumulative(insuranceMarkUpConfig.isAccumulative());
						insuranceMarkUp.setCompanyId(insuranceMarkUpConfig.getCompanyId());
						insuranceMarkUp.setConfigId(insuranceMarkUpConfig.getConfigId());					
						insuranceMarkUp.setFixedAmount(insuranceMarkUpConfig.isFixedAmount());
						insuranceMarkUp.setMarkupAmt(insuranceMarkUpConfig.getMarkupAmount());
						insuranceMarkUp.setMarkupId(insuranceMarkUpConfig.getMarkupId());
						insuranceMarkUp.setName(insuranceMarkUpConfig.getMarkupName());						
						insuranceMarkUp.setPositionOfMarkup(insuranceMarkUpConfig.getPositionOfMarkup());						
						insuranceMarkUp.setMarkUpPerPassenger(insuranceMarkUpConfig.isMarkUpPerPassenger());
						insuranceMarkUp.setMarkUpOnTotal(insuranceMarkUpConfig.isMarkUpOnTotal());
						InsuranceMarkUpList.add(insuranceMarkUp);
					}
				}					
				markupMap.put(String.valueOf(company.getCompanyid()), InsuranceMarkUpList);
				if(company.getCompanyid() == 1)
				{
					logger.info("####################  company is super user-"+company);					
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
						logger.info("########################## DB Markup map termination--"+markupMap);
						return markupMap;
					}
					else if(parentcompany != null && parentCompanyConfig != null)
					{
						logger.info("########################## DB Markup map recursion call--for companyid "+parentcompany.getCompanyid());
						markupMap = getParentMarkupMapForInsurance(companyConfig, parentCompanyConfig, markupMap);
					}
					else
					{
						return markupMap;
					}
				}
			}

		}catch (HibernateException e) {	
			logger.error("#####################3 marup call HibernateException for companyid", e);
			//throw e; 
			return markupMap;

		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return markupMap;
	}

	public Map<String,List<InsuranceMarkUp>> getParentMarkupMapForInsurance(CompanyConfig companyConfig, CompanyConfig parentCompConfig, Map<String,List<InsuranceMarkUp>>  markupMap) {	
		Session sess = null;		
		Company company = null;	
		Company parentcompany = null;
		Company superparentcompany = null;	
		CompanyConfig superParentCompConfig = null;
		String sql = null;
		SQLQuery query = null;			
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
				Criteria markupCriteria=sess.createCriteria(InsuranceMarkUpConfig.class);
				Conjunction markupConjunction=Restrictions.conjunction();
				markupConjunction.add(Restrictions.eq("configId", companyConfig.getConfig_id()));
				markupConjunction.add(Restrictions.eq("companyId",  company.getCompanyid()));
				markupConjunction.add(Restrictions.eq("createdbyCompanyId",  parentcompany.getCompanyid()));
				markupCriteria.add(markupConjunction);
				markupCriteria.addOrder(Order.desc("positionOfMarkup"));
				markupCriteria.addOrder(Order.desc("createdDate"));
				List<InsuranceMarkUpConfig> insuranceMarkUpConfigList = markupCriteria.list();
				List<InsuranceMarkUp> insuranceMarkUpList = new ArrayList<InsuranceMarkUp>();
				if (insuranceMarkUpConfigList.size() > 0) {
					Iterator<InsuranceMarkUpConfig> iterator = insuranceMarkUpConfigList.iterator();
					while (iterator.hasNext()) {
						InsuranceMarkUpConfig insuranceMarkUpConfig = iterator.next();
						InsuranceMarkUp insuranceMarkUp = new InsuranceMarkUp();
						insuranceMarkUp.setAccumulative(insuranceMarkUpConfig.isAccumulative());
						insuranceMarkUp.setCompanyId(insuranceMarkUpConfig.getCompanyId());
						insuranceMarkUp.setConfigId(insuranceMarkUpConfig.getConfigId());					
						insuranceMarkUp.setFixedAmount(insuranceMarkUpConfig.isFixedAmount());
						insuranceMarkUp.setMarkupAmt(insuranceMarkUpConfig.getMarkupAmount());
						insuranceMarkUp.setMarkupId(insuranceMarkUpConfig.getMarkupId());
						insuranceMarkUp.setName(insuranceMarkUpConfig.getMarkupName());						
						insuranceMarkUp.setPositionOfMarkup(insuranceMarkUpConfig.getPositionOfMarkup());						
						insuranceMarkUp.setMarkUpPerPassenger(insuranceMarkUpConfig.isMarkUpPerPassenger());
						insuranceMarkUp.setMarkUpOnTotal(insuranceMarkUpConfig.isMarkUpOnTotal());
						insuranceMarkUpList.add(insuranceMarkUp);
					}
				}					
				markupMap.put(String.valueOf(parentCompConfig.getCompany_id()), insuranceMarkUpList);				

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
						markupMap = getParentMarkupMapForInsurance(parentCompConfig, superParentCompConfig,  markupMap);
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

	public InsuranceMarkupCommissionDetails getInsuranceMarkupCommissionDetailsByCompanyId(AppKeyVo appKeyVo, InsuranceMarkupCommissionDetails  markupCommissionDetails){	
		Session sess = null;		
		String sql = null;
		SQLQuery query = null;	
		CompanyConfig companyConfig = null;
		List<CommissionDetails> commissionDetailslist = new ArrayList<CommissionDetails>();		
		try{
			sess = sessionFactory.openSession();		
			companyConfig = appKeyVo.getCompanyConfig();
			if(companyConfig !=null)
			{
				markupCommissionDetails.setRateType(companyConfig.getRateTypeFlight());
				markupCommissionDetails.setCompanyId(String.valueOf(appKeyVo.getCompanyId()));
				CommissionDetails commissionDetails = new CommissionDetails();
				commissionDetails.setCompanyId(String.valueOf(companyConfig.getCompany_id()));
				commissionDetails.setCommissionType(companyConfig.getCommissionTypeFlight());
				commissionDetails.setRateType(companyConfig.getRateTypeFlight());
				if(companyConfig.getRateTypeFlight().equalsIgnoreCase("COMMISSION")){
					commissionDetails.setCommissionAmount(companyConfig.getCommissionAmountFlight());
				}else{
					commissionDetails.setCommissionAmount(new BigDecimal(0));
				}
				commissionDetailslist.add(commissionDetails);
				int level = 0;
				while (companyConfig != null && companyConfig.getCompany_id()>=1) {
					int parentCompanyid = companyConfig.getCreatedbyComapnyId();
					int parentConfigId = companyConfig.getParent_config_id();		

					Criteria configCriteria1 = sess.createCriteria(CompanyConfig.class);
					Conjunction objConjunction1=Restrictions.conjunction();
					objConjunction1.add(Restrictions.eq("config_id", parentConfigId));
					objConjunction1.add(Restrictions.eq("company_id", parentCompanyid));
					configCriteria1.add(objConjunction1);
					companyConfig = (CompanyConfig) configCriteria1.uniqueResult();						

					if(companyConfig != null)
					{
						CommissionDetails parentcommissionDetails = new CommissionDetails();
						parentcommissionDetails.setCompanyId(String.valueOf(companyConfig.getCompany_id()));
						parentcommissionDetails.setCommissionType(companyConfig.getCommissionTypeFlight());
						parentcommissionDetails.setRateType(companyConfig.getRateTypeFlight());
						if(companyConfig.getRateTypeFlight().equalsIgnoreCase("COMMISSION")){
							parentcommissionDetails.setCommissionAmount(companyConfig.getCommissionAmountFlight());
						}else{
							parentcommissionDetails.setCommissionAmount(new BigDecimal(0));
						}
						commissionDetailslist.add(parentcommissionDetails);

						level ++;
						if(companyConfig.getCompany_id()==1)
						{						
							logger.info("#####################InsuranceMarkupCommissionDetails super parent break");
							logger.info("#####################InsuranceMarkupCommissionDetails for companyid"+companyConfig.getCompany_id());
							break;
						}
					}
					else
					{						
						logger.info("#####################InsuranceMarkupCommissionDetails  companyConfig null break");
						logger.info("#####################InsuranceMarkupCommissionDetails for companyid"+parentCompanyid);
						break;
					}					
				}						
				markupCommissionDetails.setCommissionDetailslist(commissionDetailslist);	
			}		
		}catch (HibernateException e) {	
			logger.error("#####################InsuranceMarkupCommissionDetails HibernateException for companyid"+appKeyVo.getCompanyId(), e);			
			return markupCommissionDetails;

		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return markupCommissionDetails;
	}
	@Override
	public Company getCompanyRoleByCompanyId(Integer companyId) {
		Company company = null;
		logger.info("companyid:" + companyId);
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Company.class);
			criteria.add(Restrictions.eq("companyid", companyId));
			company = (Company) criteria.uniqueResult();
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception : ", e);
		} finally {
			session.close();
		}
		return company;
	}

	@Override
	public  boolean checkAppkey(AppKeyVo appKeyVo)  
	{
		Session session = null;
		boolean result = false;
		try{
			session = sessionFactory.openSession();
			Criteria configCriteria = session.createCriteria(CompanyConfig.class);
			Conjunction objConjunction=Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("config_id", appKeyVo.getConfigId()));
			objConjunction.add(Restrictions.eq("company_id", appKeyVo.getCompanyId()));
			configCriteria.add(objConjunction);
			if(configCriteria.uniqueResult()!=null)
				result = true;
		}
		catch (HibernateException  | NumberFormatException  e) {
			logger.error("#####################appkey not parsed correctly "+ e);
			result=false;
		}
		finally {
			session.close();
		}
		return result;

	}
	
	public List<User> getUserListUnderCompany(int companyid) {
		//added by BASHA  for get the employees under the company 
					List<User> userList = new ArrayList<>();
					Session session = null;
					try{
						session = sessionFactory.openSession();
						Criteria criteria = session.createCriteria(User.class);
						criteria.add(Restrictions.eq("Companyid", companyid));
						userList = (List<User>)criteria.list();
					}catch (Exception e) {
						logger.error("--------------getUserEmailListByCompanyId Exception-----------------"+e.getMessage());
					}
					return userList;
				
}
}
