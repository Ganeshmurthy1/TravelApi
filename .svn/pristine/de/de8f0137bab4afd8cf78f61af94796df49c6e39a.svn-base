package com.tayyarah.misellaneous.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.misellaneous.entity.MiscellaneousOrderRow;
import com.tayyarah.quotation.entity.MiscellaneousTravelRequestQuotation;

public class MiscellaneousOrderRowEmailDaoImp implements MiscellaneousOrderRowEmailDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(MiscellaneousOrderRowEmailDaoImp.class);	
	@Autowired
	private SessionFactory  sessionFactory;
	Session session = null;
	Transaction tx = null;

	public MiscellaneousOrderRow getMiscellaneousOrderRowDetailsById(Long id) {
		Session session= null;
		MiscellaneousOrderRow  miscellaneousOrderRow=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(MiscellaneousOrderRow.class);
			criteria.add(Restrictions.eq("id",id));
			miscellaneousOrderRow=(MiscellaneousOrderRow) criteria.uniqueResult();
			miscellaneousOrderRow.setBasePrice(miscellaneousOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			miscellaneousOrderRow.setOtherTaxes(miscellaneousOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			miscellaneousOrderRow.setManagementFee(miscellaneousOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			miscellaneousOrderRow.setConvenienceFee(miscellaneousOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));		
			miscellaneousOrderRow.setTotalAmount(miscellaneousOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return miscellaneousOrderRow;
	}
	
	
	public MiscellaneousOrderRow getMiscellaneousOrderRowDetailsByOrderId(String orderId) {
		Session session= null;
		MiscellaneousOrderRow  miscellaneousOrderRow = null;		
		try {
			session = sessionFactory.openSession();			
			Criteria criteria=session.createCriteria(MiscellaneousOrderRow.class);
			criteria.add(Restrictions.eq("orderId",orderId));
			miscellaneousOrderRow=(MiscellaneousOrderRow) criteria.uniqueResult();
			miscellaneousOrderRow.setBasePrice(miscellaneousOrderRow.getBasePrice().setScale(2,BigDecimal.ROUND_UP));			 
			miscellaneousOrderRow.setOtherTaxes(miscellaneousOrderRow.getOtherTaxes().setScale(2,BigDecimal.ROUND_UP));
			miscellaneousOrderRow.setManagementFee(miscellaneousOrderRow.getManagementFee().setScale(2,BigDecimal.ROUND_UP));
			miscellaneousOrderRow.setConvenienceFee(miscellaneousOrderRow.getConvenienceFee().setScale(2,BigDecimal.ROUND_UP));			
			miscellaneousOrderRow.setTotalAmount(miscellaneousOrderRow.getTotalAmount().setScale(2,BigDecimal.ROUND_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return miscellaneousOrderRow;
	}
	
	public MiscellaneousTravelRequestQuotation getmiscellaneousOrderQuotationDetailsById(long orderId) {
		Session session= null;
		MiscellaneousTravelRequestQuotation  miscellaneousTravelRequestQuotation=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(MiscellaneousTravelRequestQuotation.class);
			criteria.add(Restrictions.eq("orderRowId",orderId));
			miscellaneousTravelRequestQuotation=(MiscellaneousTravelRequestQuotation) criteria.uniqueResult(); 
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return miscellaneousTravelRequestQuotation;
	}	 
}