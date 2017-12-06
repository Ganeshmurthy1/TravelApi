package com.tayyarah.insurance.dao;
import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.quotation.entity.InsuranceTravelRequestQuotation;




public class InsuranceOrderRowEmailDaoImp implements InsuranceOrderRowEmailDao, Serializable {
	/**
	 * 
	 */
	public static final org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(InsuranceOrderRowEmailDaoImp.class);
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory  sessionFactory;
	Session session = null;
	Transaction tx = null;
	
	@Override
	public InsuranceOrderRow getInsuranceOrderRowDetailsByOrderId(String orderId)
			throws HibernateException {
		Session session = null;
		InsuranceOrderRow  insuranceOrderRow=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(InsuranceOrderRow.class);
			criteria.add(Restrictions.eq("orderId", orderId));
			insuranceOrderRow=(InsuranceOrderRow) criteria.uniqueResult();
			insuranceOrderRow.setBasePrice(insuranceOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setOtherTaxes(insuranceOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setManagementFee(insuranceOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setConvenienceFee(insuranceOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setServiceTax(insuranceOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setTotalAmount(insuranceOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return insuranceOrderRow;
	}

	@Override
	public InsuranceOrderRow getInsuranceOrderRowDetailsById(Long id)
			throws HibernateException {
		Session session = null;
		InsuranceOrderRow insuranceOrderRow = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(InsuranceOrderRow.class);
			criteria.add(Restrictions.eq("id", id));
			insuranceOrderRow=(InsuranceOrderRow) criteria.uniqueResult();
			insuranceOrderRow.setBasePrice(insuranceOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setOtherTaxes(insuranceOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setManagementFee(insuranceOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setConvenienceFee(insuranceOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setServiceTax(insuranceOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			insuranceOrderRow.setTotalAmount(insuranceOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return insuranceOrderRow;
	}

	@Override
	public InsuranceTravelRequestQuotation getInsuranceOrderQuotationDetailsById(
			Long orderId) {
		Session session = null;
		InsuranceTravelRequestQuotation  insuranceTravelRequestQuotation = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(InsuranceTravelRequestQuotation.class);
			criteria.add(Restrictions.eq("orderRowId",orderId));
			insuranceTravelRequestQuotation = (InsuranceTravelRequestQuotation) criteria.uniqueResult(); 
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return insuranceTravelRequestQuotation;
	}
}