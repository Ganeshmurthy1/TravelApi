package com.tayyarah.misellaneous.dao;

import org.hibernate.HibernateException;

import com.tayyarah.misellaneous.entity.MiscellaneousOrderRow;
import com.tayyarah.quotation.entity.MiscellaneousTravelRequestQuotation;

public interface MiscellaneousOrderRowEmailDao {
	public MiscellaneousOrderRow getMiscellaneousOrderRowDetailsByOrderId(String id) throws HibernateException;
	public MiscellaneousOrderRow getMiscellaneousOrderRowDetailsById(Long id) throws HibernateException;
	public MiscellaneousTravelRequestQuotation getmiscellaneousOrderQuotationDetailsById(long orderId) throws HibernateException;

}
