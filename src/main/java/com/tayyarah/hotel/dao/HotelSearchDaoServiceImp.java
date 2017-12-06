package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.math.BigInteger;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.HotelSearchTemp;


public class HotelSearchDaoServiceImp implements HotelSearchDaoService {
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Override
	public HotelSearchTemp insertHotelSearch(HotelSearchTemp hs) throws HibernateException, IOException, Exception {
		// TODO Auto-generated method stub
		return hotelSearchDao.insertOrUpdateHotelSearch(hs);
	}
	@Override
	public HotelSearchTemp getHotelSearch(Long searchKey) throws HibernateException, IOException {
		// TODO Auto-generated method stub
		return hotelSearchDao.getHotelSearch(searchKey);
	}

}
