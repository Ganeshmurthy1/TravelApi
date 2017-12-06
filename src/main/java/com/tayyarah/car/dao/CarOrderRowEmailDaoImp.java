package com.tayyarah.car.dao;
import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.car.entity.CarOrderRow;
import com.tayyarah.car.quotation.entity.CarTravelRequestQuotation;


public class CarOrderRowEmailDaoImp implements CarOrderRowEmailDao, Serializable {
	/**
	 * 
	 */
	public static final org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(CarOrderRowEmailDaoImp.class);
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory  sessionFactory;
	Session session = null;
	Transaction tx = null;

	public CarOrderRow getCarOrderRowDetailsById(Long id) {
		Session session= null;
		CarOrderRow  carOrderRow=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(CarOrderRow.class);
			criteria.add(Restrictions.eq("id",id));
			carOrderRow=(CarOrderRow) criteria.uniqueResult();
			carOrderRow.setBasePrice(carOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setTollOrParkingCharges(carOrderRow.getTollOrParkingCharges().setScale(0, BigDecimal.ROUND_HALF_UP));                  
			carOrderRow.setDriverAllowanceDay(carOrderRow.getDriverAllowanceDay().setScale(0, BigDecimal.ROUND_HALF_UP)); 
			carOrderRow.setDriverAllowanceNight(carOrderRow.getDriverAllowanceNight().setScale(0, BigDecimal.ROUND_HALF_UP)); 
			carOrderRow.setOtherTaxes(carOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setManagementFee(carOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setConvenienceFee(carOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setServiceTax(carOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setTotalAmount(carOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return carOrderRow;
	}	
	
	public CarOrderRow getCarOrderRowDetailsByOrderId(String orderId) {
		Session session= null;
		CarOrderRow  carOrderRow=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(CarOrderRow.class);
			criteria.add(Restrictions.eq("orderId",orderId));
			carOrderRow=(CarOrderRow) criteria.uniqueResult();
			carOrderRow.setBasePrice(carOrderRow.getBasePrice().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setTollOrParkingCharges(carOrderRow.getTollOrParkingCharges().setScale(0, BigDecimal.ROUND_HALF_UP));                  
			carOrderRow.setDriverAllowanceDay(carOrderRow.getDriverAllowanceDay().setScale(0, BigDecimal.ROUND_HALF_UP)); 
			carOrderRow.setDriverAllowanceNight(carOrderRow.getDriverAllowanceNight().setScale(0, BigDecimal.ROUND_HALF_UP)); 
			carOrderRow.setOtherTaxes(carOrderRow.getOtherTaxes().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setManagementFee(carOrderRow.getManagementFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setConvenienceFee(carOrderRow.getConvenienceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setServiceTax(carOrderRow.getServiceTax().setScale(0, BigDecimal.ROUND_HALF_UP));
			carOrderRow.setTotalAmount(carOrderRow.getTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return carOrderRow;
	}
	
	public CarTravelRequestQuotation getcarOrderQuotationDetailsById(long orderId) {
		Session session= null;
		CarTravelRequestQuotation  carTravelRequestQuotation=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(CarTravelRequestQuotation.class);
			criteria.add(Restrictions.eq("orderRowId",orderId));
			carTravelRequestQuotation=(CarTravelRequestQuotation) criteria.uniqueResult(); 
		} catch (Exception e) {
			logger.error("Exception---------"+e.getMessage());
		}
		finally{
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return carTravelRequestQuotation;
	}	 
}