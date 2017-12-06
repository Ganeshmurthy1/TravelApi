package com.tayyarah.common.dao;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.MoneyExchange;


public class MoneyExchangeDaoImp implements MoneyExchangeDao {

	static final Logger logger = Logger.getLogger(MoneyExchangeDaoImp.class);
	@Autowired
	private SessionFactory sessionFactory;
		
	@Override
	public MoneyExchange insertOrUpdateCurrency(MoneyExchange MoneyExchange)
			throws HibernateException, IOException, Exception {
		Session session = null;
		Transaction tx = null;	
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(MoneyExchange);    //Save the data
			if (!tx.wasCommitted())
				tx.commit();
			
			return MoneyExchange;
		} catch (HibernateException e) {
			throw e;
		} catch (Exception e) {
			throw e;			
		}
		finally{	
			if (!tx.wasCommitted())
				tx.commit();
			
			session.close();
		}
	}

	@Override
	public List<MoneyExchange> getEntityList()  {
		Session session = null;		
		List<MoneyExchange> ratelist = new ArrayList<>();
		try{
		session = sessionFactory.openSession(); 		
		Criteria criteria = session.createCriteria(MoneyExchange.class);
		ratelist = criteria.list(); 
		session.close();  
		}catch(Exception e){
			logger.info("Exception " +e);
		}
		return ratelist;
	}

	@Override
	public Map<String,Double> getCurrencyRate(String baseCur, String convertedCur)
			throws HibernateException {
		MoneyExchange moneyExchange= new MoneyExchange();
		Map<String,Double> resultMap=new HashMap<String, Double>();
		Session session = null;
		try{
			session = sessionFactory.openSession();		
			Criteria criteria = session.createCriteria(MoneyExchange.class);
			criteria.add(Restrictions.eq("currency", baseCur));
			moneyExchange = (MoneyExchange) criteria.uniqueResult();	

			Criteria criteriaConverted = session.createCriteria(MoneyExchange.class);
			criteriaConverted.add(Restrictions.eq("currency", convertedCur));
			MoneyExchange convertedCurrency  = (MoneyExchange) criteriaConverted.uniqueResult();
			
			String baseStrCur = moneyExchange.getExchangeRate().toString();
			String reqStrCur=convertedCurrency.getExchangeRate().toString();
			double reqDouble=Double.parseDouble(baseStrCur)/Double.parseDouble(reqStrCur);			
			resultMap.put("value", Double.valueOf(reqDouble));
			session.close();
		} catch (HibernateException e) {
			//throw e;
			e.printStackTrace();
		} catch (Exception e) {
			//throw e;		
			e.printStackTrace();	
		}
		return resultMap;

	}
}