package com.tayyarah.bus.dao;
import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.quotation.entity.BusTravelRequestQuotation;


public interface BusOrderRowEmailDao {
	public BusOrderRow getBusOrderRowDetailsByOrderId(String id) throws HibernateException;
	public BusOrderRow getBusOrderRowDetailsById(Long id) throws HibernateException;
	public BusTravelRequestQuotation getBusOrderQuotationDetailsById(long orderId) throws HibernateException;
	public List<BusOrderCustomerDetail> geBusOrderCustomerDetailList(long orderId) throws Exception;
}

