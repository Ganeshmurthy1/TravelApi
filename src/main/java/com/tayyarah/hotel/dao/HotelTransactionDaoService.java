package com.tayyarah.hotel.dao;

import java.io.IOException;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.HotelTransactionTemp;


public interface HotelTransactionDaoService {
	public HotelTransactionTemp insertApiMapBySearchKey(HotelTransactionTemp ht)
			throws HibernateException, IOException, Exception;
}
