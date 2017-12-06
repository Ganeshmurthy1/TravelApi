package com.tayyarah.train.dao;
import org.hibernate.HibernateException;

import com.tayyarah.quotation.entity.TrainTravelRequestQuotation;
import com.tayyarah.train.entity.TrainOrderRow;

public interface TrainOrderRowEmailDao {
	public TrainOrderRow getTrainOrderRowDetailsByOrderId(String id) throws HibernateException;
	public TrainOrderRow getTrainOrderRowDetailsById(Long id) throws HibernateException;
	public TrainTravelRequestQuotation getTrainOrderQuotationDetailsById(long orderId)  throws HibernateException;
}

