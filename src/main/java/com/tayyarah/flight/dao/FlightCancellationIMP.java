package com.tayyarah.flight.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.flight.entity.FlightOrderRowCancellation;
import com.tayyarah.flight.entity.FlightOrderRowCancellationAPIResponse;


public class FlightCancellationIMP implements FlightCancellationDao{

	@Autowired
	private SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(FlightCancellationIMP.class);
	
	@Override
	public void insertFlightOrderRowCancellation(FlightOrderRowCancellation forc) {
		Session session=null;
		Transaction transaction=null;
		
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(forc);
			transaction.commit();
		}
		catch (Exception e) {
			logger.info("------------error occured while saving to flight_order_row_cancellation table:"+e.getMessage());
			if (transaction!=null) {
				transaction.rollback();
			}			
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}			
		}
	}

	@Override
	public Serializable saveSupplierResponse(FlightOrderRowCancellationAPIResponse forc) {		
		Session session = null;
		Transaction transaction = null;
		Serializable pk=null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			pk = session.save(forc);
			transaction.commit();
		}
		catch (Exception e) {
			logger.info("------------error occured while saving to Flight_Order_Row_Cancellation_API_Response table:"+e.getMessage());
			transaction.rollback();
		}
		finally {
			session.close();
		}
		return pk;
	}
}
