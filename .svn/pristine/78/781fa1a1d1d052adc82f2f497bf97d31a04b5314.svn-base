package com.tayyarah.bugtracker.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.bugtracker.entity.BugTrackerHistory;


public class BugDaoImpl extends BugDao {
	public static final Logger logger = Logger.getLogger(BugDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	@Override
	public List<BugTrackerHistory> bugPendingStatusList() {
		Session session = null;		
		List<BugTrackerHistory> bugHistoryList = null;
		try{
			Disjunction disjunction=Restrictions.disjunction();
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(BugTrackerHistory.class);
			disjunction.add(Restrictions.eq("status", "Pending"));
			disjunction.add(Restrictions.eq("status", "WorkInProgress"));
			disjunction.add(Restrictions.eq("status", "StillInProgress"));
			criteria.add(disjunction);
			bugHistoryList = criteria.list();
			if(bugHistoryList == null) 
				bugHistoryList = new ArrayList<>();

		}catch(HibernateException e){
			logger.info("HibernateException-----"+e.getMessage());
		}
		finally {
			if(session!=null && session.isOpen())
				session.close();
		}
		return bugHistoryList;
	}	
}
