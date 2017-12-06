package com.tayyarah.hotel.dao;

import java.io.IOException;
import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.HotelSearchTemp;

public interface HotelSearchDao {
	public HotelSearchTemp getHotelSearch(Long searchKey)
			throws HibernateException, IOException;	
	public HotelSearchTemp insertOrUpdateHotelSearch(HotelSearchTemp ht)
			throws HibernateException, IOException, Exception;
}
