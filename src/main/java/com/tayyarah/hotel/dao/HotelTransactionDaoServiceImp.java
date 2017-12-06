package com.tayyarah.hotel.dao;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.HotelTransactionTemp;


public class HotelTransactionDaoServiceImp implements
		HotelTransactionDaoService {
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Override
	public HotelTransactionTemp insertApiMapBySearchKey(HotelTransactionTemp ht)
			throws HibernateException, IOException, Exception {
		// TODO Auto-generated method stub
		return hotelTransactionDao.insertApiMapBySearchKey(ht);
	}

}
