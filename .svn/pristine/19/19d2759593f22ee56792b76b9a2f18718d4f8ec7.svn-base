package com.tayyarah.flight.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.common.entity.Airlinelist;

public class AirlineDAOImp implements AirlineDAO {
	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	Logger logger = Logger.getLogger(AirlineDAOImp.class);


	@Override
	/*@Cacheable(value="airlineListCache")*/
	public List<Airlinelist> getAirlineList() throws Exception {
		List<Airlinelist> list = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(Airlinelist.class);
			list = criteria.list();
			if(list!=null && list.size()>0)
			{
				Collections.sort(list, new AirlineComparator());
			}
		}
		catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		} finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return list;
	}

	class AirlineComparator implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			Airlinelist A1 = (Airlinelist) o1;
			Airlinelist A2 = (Airlinelist) o2;
			return A1.getAirlinename().compareTo(A2.getAirlinename());
		}
	}
}