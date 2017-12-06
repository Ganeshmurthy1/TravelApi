package com.tayyarah.hotel.dao;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.HotelOverview;


public class HoteloverviewDaoServiceImp implements HoteloverviewDaoService {
@Autowired
HoteloverviewDao hoteloverviewdao;

@Override
public HotelOverview gettEntityByVendorID(String VendorID)
		throws HibernateException {
	// TODO Auto-generated method stub
	return hoteloverviewdao.getHotelOverviewByVendorID(VendorID);
}
}
