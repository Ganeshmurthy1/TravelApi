package com.tayyarah.hotel.dao;

import java.io.IOException;
import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.HotelTransactionTemp;


public interface HotelTransactionDao {	
	public HotelTransactionTemp getHotelTransaction(Long searchKey)
			throws HibernateException, IOException, Exception;
	
	public HotelTransactionTemp insertApiMapBySearchKey(HotelTransactionTemp ht)
			throws HibernateException, IOException, Exception;
}
