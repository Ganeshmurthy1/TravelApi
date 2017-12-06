package com.tayyarah.visa.dao;
import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.quotation.entity.VisaTravelRequestQuotation;
import com.tayyarah.visa.entity.VisaOrderRow;


public class VisaOrderRowEmailDaoImp implements VisaOrderRowEmailDao, Serializable {
	/**
	 * 
	 */
	public static final org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(VisaOrderRowEmailDaoImp.class);
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory  sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public VisaOrderRow getVisaOrderRowDetailsByOrderId(String orderId)
			throws HibernateException {
		Session session= null;
		VisaOrderRow  visaOrderRow=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(VisaOrderRow.class);
			criteria.add(Restrictions.eq("orderId", orderId));
			visaOrderRow=(VisaOrderRow) criteria.uniqueResult();
			visaOrderRow.setBasePrice(visaOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setVfsCharges(visaOrderRow.getVfsCharges().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setCourierCharges(visaOrderRow.getCourierCharges().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setOtherTaxes(visaOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setManagementFee(visaOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setConvenienceFee(visaOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setServiceTax(visaOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setTotalAmount(visaOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return visaOrderRow;
	}


	@Override
	public VisaOrderRow getVisaOrderRowDetailsById(Long id)
			throws HibernateException {
		Session session = null;
		VisaOrderRow  visaOrderRow = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(VisaOrderRow.class);
			criteria.add(Restrictions.eq("id", id));
			visaOrderRow=(VisaOrderRow) criteria.uniqueResult();
			visaOrderRow.setBasePrice(visaOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setVfsCharges(visaOrderRow.getVfsCharges().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setCourierCharges(visaOrderRow.getCourierCharges().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setOtherTaxes(visaOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setManagementFee(visaOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setConvenienceFee(visaOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setServiceTax(visaOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			visaOrderRow.setTotalAmount(visaOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return visaOrderRow;
	}

	public VisaTravelRequestQuotation getVisaOrderQuotationDetailsById(long orderId) {
		Session session = null;
		VisaTravelRequestQuotation visaTravelRequestQuotation = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(VisaTravelRequestQuotation.class);
			criteria.add(Restrictions.eq("orderRowId", orderId));
			visaTravelRequestQuotation=(VisaTravelRequestQuotation) criteria.uniqueResult();

		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return visaTravelRequestQuotation;
	}
}