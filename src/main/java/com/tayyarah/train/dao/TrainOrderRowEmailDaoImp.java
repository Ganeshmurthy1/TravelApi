package com.tayyarah.train.dao;
import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.quotation.entity.TrainTravelRequestQuotation;
import com.tayyarah.train.entity.TrainOrderRow;



public class TrainOrderRowEmailDaoImp implements TrainOrderRowEmailDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(TrainOrderRowEmailDaoImp.class);
	@Autowired
	private SessionFactory  sessionFactory;
	Session session = null;
	Transaction tx = null;

	public TrainOrderRow getTrainOrderRowDetailsByOrderId(String orderId) {
		Session session = null;
		TrainOrderRow  trainOrderRow = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(TrainOrderRow.class);
			criteria.add(Restrictions.eq("orderId", orderId));
			trainOrderRow = (TrainOrderRow) criteria.uniqueResult();
			trainOrderRow.setBasePrice(trainOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setOtherTaxes(trainOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setManagementFee(trainOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setConvenienceFee(trainOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setServiceTax(trainOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setTotalAmount(trainOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));			

		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return trainOrderRow;
	}

	public TrainTravelRequestQuotation getTrainOrderQuotationDetailsById(long orderId) {
		Session session = null;
		TrainTravelRequestQuotation trainTravelRequestQuotation = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(TrainTravelRequestQuotation.class);
			criteria.add(Restrictions.eq("orderRowId", orderId));
			trainTravelRequestQuotation = (TrainTravelRequestQuotation) criteria.uniqueResult();					

		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return trainTravelRequestQuotation;
	}
	
	public TrainOrderRow getTrainOrderRowDetailsById(Long id) {
		Session session= null;
		TrainOrderRow  trainOrderRow=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(TrainOrderRow.class);
			criteria.add(Restrictions.eq("id",id));
			trainOrderRow=(TrainOrderRow) criteria.uniqueResult();
			trainOrderRow.setBasePrice(trainOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setOtherTaxes(trainOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setManagementFee(trainOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setConvenienceFee(trainOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setServiceTax(trainOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			trainOrderRow.setTotalAmount(trainOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return trainOrderRow;
	}
}