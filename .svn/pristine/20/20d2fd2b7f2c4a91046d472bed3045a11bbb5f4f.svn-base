/**
 * 
 */
package com.tayyarah.flight.cache.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.flight.cache.entity.FlightSearchCacheDestination;


/**
 * @author      : Manish Samrat
 * @createdAt   : 17/08/2017
 * @version
 */
public class FlightSearchCacheDaoImpl implements FlightSearchCacheDao{

	@Autowired
	private SessionFactory  sessionFactory;

	@Override
	public List<FlightSearchCacheDestination> fetchAllSearchCacheList(){
		Session session = null;   
		Criteria criteria=null;
		List<FlightSearchCacheDestination> searchCacheDestinationList=new ArrayList<FlightSearchCacheDestination>();
		try{
			session = sessionFactory.openSession();
			criteria=session.createCriteria(FlightSearchCacheDestination.class);
			searchCacheDestinationList= criteria.list();
		}
		catch (Exception e) {
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return searchCacheDestinationList;
	}

}
