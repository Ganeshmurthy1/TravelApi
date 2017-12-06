package com.tayyarah.common.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.RmConfigModel;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.entity.TripRequest;

public class RmConfigDetailDAOIMP implements RmConfigDetailDAO{
	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(RmConfigDetailDAOIMP.class);
	public RmConfigTripDetailsModel getRmConfigDetail(String orderid){
		RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(RmConfigTripDetailsModel.class);
			if(orderid!=null)
				criteria.add(Restrictions.eq("orderId", orderid));

			rmConfigTripDetailsModel = (RmConfigTripDetailsModel) criteria.uniqueResult();

		}catch(Exception e){
			logger.info("getRmConfigDetail Exception" +e.getMessage());
		}finally {
			session.close();
		}
		return rmConfigTripDetailsModel;
	}
	public TripRequest getOrderidUsringTripId(long tripid){
		Session session = null;
		TripRequest tripRequest = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(TripRequest.class);
			criteria.add(Restrictions.eq("tripId", tripid));
			tripRequest = (TripRequest) criteria.uniqueResult();
		}catch(Exception e){
			logger.info("getOrderidUsringTripId Exception" +e.getMessage());
		}finally {
			session.close();
		}	
		return tripRequest;
	}
	public RmConfigModel getRmConfigModel(int companyid) {
		Session session = null;
		RmConfigModel rmConfigModel = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(RmConfigModel.class);
			criteria.add(Restrictions.eq("corporateId", companyid));
			rmConfigModel = (RmConfigModel) criteria.uniqueResult();
		}catch(Exception e){
			logger.info("getRmConfigModel Exception" +e.getMessage());
		}finally {
			session.close();
		}	
		return rmConfigModel;
	}
}
