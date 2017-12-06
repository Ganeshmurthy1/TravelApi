package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.math.BigInteger;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.HotelSearchTemp;



public interface HotelSearchDaoService {
	public HotelSearchTemp getHotelSearch(Long searchKey)
			throws HibernateException, IOException;
	
	public HotelSearchTemp insertHotelSearch(HotelSearchTemp ht)
			throws HibernateException, IOException, Exception;
}
