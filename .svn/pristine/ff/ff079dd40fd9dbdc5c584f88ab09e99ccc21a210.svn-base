package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.entity.TboCity;
import com.tayyarah.hotel.model.City;

public class CityDaoImp implements HotelCityDao{
	@Autowired
	private SessionFactory hotelsessionFactory;

	Session session = null;
	Transaction tx = null;
	Logger logger = Logger.getLogger(CityDaoImp.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<City> getCityList() throws Exception {
		session = hotelsessionFactory.openSession();
		tx = session.beginTransaction();	
		List<City> cities = session.createCriteria(City.class)
				.list();				

		return cities;
	}

	
	@Override
	public List<City> getByCity(String searchCityKey) throws Exception {
		logger.info("city:"+ searchCityKey);
		session = hotelsessionFactory.openSession();		
		tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(City.class);		
		criteria.add(
				Restrictions.disjunction()
				.add(Restrictions.like("city", searchCityKey, MatchMode.ANYWHERE))
				);

		return criteria.list();
	}

	@Override
	public HotelSearchCity getHotelSearchCity(String cityName, String countryCode)	
			throws HibernateException, IOException, Exception {
		Session session = null;
		Transaction tx = null;		
		HotelSearchCity hsc = null;
		try{
			session = hotelsessionFactory.openSession();
			tx = session.beginTransaction();			
			Criteria cityCriteria=session.createCriteria(HotelSearchCity.class);
			Conjunction objConjunction = Restrictions.conjunction();			
			objConjunction.add(Restrictions.eq("city", cityName));
			objConjunction.add(Restrictions.eq("countryCode", countryCode));			
			cityCriteria.add(objConjunction);
			List<HotelSearchCity> list = cityCriteria.list();		
			tx.commit();
			if(list!=null && list.size()>0)
				hsc = list.get(0);

		}catch (HibernateException e) {
			logger.info("-------search city common pojo-----HibernateException--"+e.getMessage());
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			logger.info("-------search city common pojo-----finally--");
			session.close(); 
		}
		return hsc;
	}

	@Override
	public HotelSearchCity getHotelSearchCity(Integer cityId)	
			throws HibernateException, IOException, Exception {
		Session session = null;		
		HotelSearchCity hsc = null;		
		try{
			session = hotelsessionFactory.openSession();					
			Criteria cityCriteria=session.createCriteria(HotelSearchCity.class);
			Conjunction objConjunction = Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("id", cityId));			
			cityCriteria.add(objConjunction);
			hsc = (HotelSearchCity) cityCriteria.uniqueResult();	
		}catch (HibernateException e) {
			logger.info("-------search city common pojo-----HibernateException--"+e.getMessage());
			throw e; 
		}finally {
			logger.info("-------search city common pojo-----finally--");
			session.close();
		}
		return hsc;
	}
	
	@Override
	public List<HotelSearchCity> getHotelSearchCityDuplicates(Integer cityId)	
			throws HibernateException, IOException, Exception {
		Session session = null;
		List<HotelSearchCity> hscList = new ArrayList<HotelSearchCity>();
		try{
			session = hotelsessionFactory.openSession();					
			Criteria cityCriteria=session.createCriteria(HotelSearchCity.class);
			Conjunction objConjunction = Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("id", cityId));	
			
			cityCriteria.add(objConjunction);
			HotelSearchCity hsc = (HotelSearchCity) cityCriteria.uniqueResult();
			if(hsc !=null && hsc.getDuplicateIdData() != null)
			{				
				String[] duplicateIdDataArray = hsc.getDuplicateIdData().split(",");
				if(duplicateIdDataArray != null && duplicateIdDataArray.length >0)
				{
					List<Integer> duplicateIdDataList = new ArrayList<Integer>();
					for (String duplicateId : duplicateIdDataArray) {
						try{
							duplicateIdDataList.add(Integer.valueOf(duplicateId));
						}
						catch(Exception e)
						{
							logger.info("------search city common pojo-----HotelSearchCity mapping error-");
						}				
					}
					cityCriteria=session.createCriteria(HotelSearchCity.class);
					objConjunction = Restrictions.conjunction();
					objConjunction.add(Restrictions.in("id", duplicateIdDataList));			
					cityCriteria.add(objConjunction);
					List<HotelSearchCity> hscListTemp = cityCriteria.list();
					if(hscListTemp != null)
						hscList.addAll(hscListTemp);
				}
			}			
		}catch (HibernateException e) {
			logger.info("-------search city common pojo-----HibernateException--"+e.getMessage());
			throw e; 
		}finally {
			logger.info("-------search city common pojo-----finally--");
			session.close();
		}
		return hscList;
	}

	@Override
	public TboCity getTboCity(Integer cityId)	
			throws HibernateException, IOException, Exception {
		Session session = null;
		Transaction tx = null;		
		TboCity hsc = null;
		try{
			session = hotelsessionFactory.openSession();					
			Criteria cityCriteria=session.createCriteria(TboCity.class);
			Conjunction objConjunction = Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("Cityid", cityId));			
			cityCriteria.add(objConjunction);
			hsc = (TboCity) cityCriteria.uniqueResult();		

		}catch (HibernateException e) {
			logger.info("-------search city common pojo-----HibernateException--"+e.getMessage());

			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			logger.info("-------search city common pojo-----finally--");
			session.close(); 

		}
		return hsc;
	}

	@Override
	public List<HotelSearchCity> getHotelSearchCity(String key)
			throws HibernateException, IOException, Exception {
		Session sess = null;	
		List<HotelSearchCity> list = null;
		Criteria  cityCriteria = null;
		Criteria  criteriaTBO = null;
		try{
			sess = hotelsessionFactory.openSession();
			if(key != null && !key.equalsIgnoreCase("all")) 
			{	
				cityCriteria = sess.createCriteria(HotelSearchCity.class);				
				Conjunction cityConjunction = Restrictions.conjunction();
				Disjunction supplierDisjunction = Restrictions.disjunction();						
				supplierDisjunction.add(Restrictions.isNotNull("tboCity"));
				supplierDisjunction.add(Restrictions.isNotNull("tgCity"));
				supplierDisjunction.add(Restrictions.eq("autocompleterFlag", true));
				
				Disjunction cityDisjunction = Restrictions.disjunction();				
				cityDisjunction.add(Restrictions.like("city",key, MatchMode.ANYWHERE));
				cityDisjunction.add(Restrictions.like("countryCode",key, MatchMode.ANYWHERE));
				cityDisjunction.add(Restrictions.like("aliasname",key, MatchMode.ANYWHERE));
				cityDisjunction.add(Restrictions.like("aliasname2",key, MatchMode.ANYWHERE));
				cityDisjunction.add(Restrictions.like("state",key, MatchMode.ANYWHERE));
				
				cityConjunction.add(supplierDisjunction);
				cityConjunction.add(cityDisjunction);
				cityCriteria.add(cityConjunction);
			}
			else 
			{
				cityCriteria=sess.createCriteria(HotelSearchCity.class);				
				cityCriteria.add(Restrictions.eq("autocompleterFlag", true));
			}
			list = cityCriteria.list();	
			logger.info("------search HotelSearchCity-----items found--"+list.size());

		}catch (HibernateException e) {
			logger.info("-------search HotelSearchCity-----HibernateException--"+e.getMessage());
			throw e; 
		}finally {
			logger.info("-------search HotelSearchCity-----finally--");
			if(sess!=null && sess.isOpen())
			{
				sess.close(); 
			}
		}
		return list;
	}

	@Override
	public List<TboCity> getTBOCities(String key)
			throws HibernateException, IOException, Exception {
		Session sess = null;	
		List<TboCity> list = null;
		Criteria  cityCriteria =null;
		Criteria  criteriaTBO =null;
		try{
			sess = hotelsessionFactory.openSession();
			criteriaTBO = sess.createCriteria(TboCity.class);	
			if(key != null && !key.equalsIgnoreCase("all")) 
			{
				Conjunction tboConjunction = Restrictions.conjunction();
				Disjunction tboDisjunction = Restrictions.disjunction();

				tboDisjunction.add(Restrictions.like("Destination",key, MatchMode.ANYWHERE));
				tboDisjunction.add(Restrictions.like("Stateprovince",key, MatchMode.ANYWHERE));
				tboDisjunction.add(Restrictions.like("Country",key, MatchMode.ANYWHERE));
				tboDisjunction.add(Restrictions.like("Countrycode",key, MatchMode.ANYWHERE));

				ArrayList<String> activatedStatusList = new ArrayList<String>();
				activatedStatusList.add("yes");
				activatedStatusList.add("Yes");
				activatedStatusList.add("YES");

				tboConjunction.add(Restrictions.in("Isactivated",activatedStatusList));
				tboConjunction.add(tboDisjunction);
				criteriaTBO.add(tboConjunction);	

			}
			else 
			{
				ArrayList<String> activatedStatusList = new ArrayList<String>();
				activatedStatusList.add("yes");
				activatedStatusList.add("Yes");
				activatedStatusList.add("YES");
				criteriaTBO.add(Restrictions.in("Isactivated",activatedStatusList));					
			}

			list = criteriaTBO.list();
			logger.info("------TboCity-----items found--"+list.size());

		}catch (HibernateException e) {
			logger.info("-------TboCity-----HibernateException--"+e.getMessage());
			throw e; 
		}finally {
			logger.info("-------TboCity-----finally--");
			if(sess!=null && sess.isOpen())
			{
				sess.close(); 
			}
		}
		return list;
	}
}