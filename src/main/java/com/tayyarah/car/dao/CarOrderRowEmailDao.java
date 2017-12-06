package com.tayyarah.car.dao;
import org.hibernate.HibernateException;

import com.tayyarah.car.entity.CarOrderRow;
import com.tayyarah.car.quotation.entity.CarTravelRequestQuotation;

public interface CarOrderRowEmailDao {
	public CarOrderRow getCarOrderRowDetailsByOrderId(String id) throws HibernateException;
	public CarOrderRow getCarOrderRowDetailsById(Long id) throws HibernateException;
	public CarTravelRequestQuotation getcarOrderQuotationDetailsById(long orderId) throws HibernateException;
}