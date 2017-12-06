package com.tayyarah.user.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.Country;
import com.tayyarah.user.entity.FrontUserDetail;


public class FrontUserDaoImp implements FrontUserDao{
	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(FrontUserDaoImp.class);
	public String getCountryName(String countryid){
		Session session = null;	
		String countryName = "";
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(Country.class);
			criteria.add(Restrictions.eq("country_code", countryid));
			Country Country =   (Country) criteria.uniqueResult();
			if(Country!=null)
				countryName = Country.getCountry_name();

		}catch(Exception e){
			logger.info("getCountryName Exception" +e);
		}finally {
			session.close();
		}
		return countryName;
	}
	public FrontUserDetail insertFrontUserDetail(FrontUserDetail frontUserDetail){
		Session session = null;	
		try{
			session = sessionFactory.openSession();	
			Criteria criteria = session.createCriteria(FrontUserDetail.class);
			criteria.add(Restrictions.eq("email", frontUserDetail.getEmail()));
			FrontUserDetail frontUserDetailObj =  (FrontUserDetail) criteria.uniqueResult();
			if(frontUserDetailObj == null)
				session.save(frontUserDetail);		

		}catch(Exception e){
			logger.info("insertFrontUserDetail Exception" +e);
		}finally {
			session.close();
		}
		return frontUserDetail;
	}

}
