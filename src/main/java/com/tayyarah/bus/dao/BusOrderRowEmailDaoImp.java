package com.tayyarah.bus.dao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.quotation.entity.BusTravelRequestQuotation;




public class BusOrderRowEmailDaoImp implements BusOrderRowEmailDao, Serializable {
	/**
	 * 
	 */
	public static final org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(BusOrderRowEmailDaoImp.class);
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory  sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public BusOrderRow getBusOrderRowDetailsByOrderId(String orderId)
			throws HibernateException {
		Session session = null;
		BusOrderRow busOrderRow = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(BusOrderRow.class);
			criteria.add(Restrictions.eq("orderId",orderId));
			busOrderRow=(BusOrderRow) criteria.uniqueResult();
			busOrderRow.setBasePrice(busOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setOtherTaxes(busOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setManagementFee(busOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setConvenienceFee(busOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setServiceTax(busOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setTotalAmount(busOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return busOrderRow;
	}


	@Override
	public BusOrderRow getBusOrderRowDetailsById(Long id)
			throws HibernateException {
		Session session = null;
		BusOrderRow busOrderRow = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(BusOrderRow.class);
			criteria.add(Restrictions.eq("id",id));
			busOrderRow=(BusOrderRow) criteria.uniqueResult();
			busOrderRow.setBasePrice(busOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setOtherTaxes(busOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setManagementFee(busOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setConvenienceFee(busOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setServiceTax(busOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			busOrderRow.setTotalAmount(busOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return busOrderRow;
	}

	public BusTravelRequestQuotation getBusOrderQuotationDetailsById(long orderId) {
		Session session = null;
		BusTravelRequestQuotation  busTravelRequestQuotation = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(BusTravelRequestQuotation.class);
			criteria.add(Restrictions.eq("orderRowId",orderId));
			busTravelRequestQuotation=(BusTravelRequestQuotation) criteria.uniqueResult(); 
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return busTravelRequestQuotation;
	}

	public List<BusOrderCustomerDetail> geBusOrderCustomerDetailList(long orderId) {
		Session session = null;
		List<BusOrderCustomerDetail> busOrderCustomerDetailList = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(BusOrderCustomerDetail.class);
			criteria.add(Restrictions.eq("busOrderRow.id",orderId));
			busOrderCustomerDetailList = criteria.list();
		} catch (Exception e) {
			logger.error("geBusOrderCustomerDetailList Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return busOrderCustomerDetailList;
	}
}
