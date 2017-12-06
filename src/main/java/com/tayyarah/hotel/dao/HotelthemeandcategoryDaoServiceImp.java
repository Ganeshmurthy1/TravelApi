package com.tayyarah.hotel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.Hotelthemeandcategory;

public class HotelthemeandcategoryDaoServiceImp implements
		HotelthemeandcategoryDaoService {
	@Autowired
	HotelthemeandcategoryDao hotelthemedao;
	@Override
	public List<Hotelthemeandcategory> getEntityByVid(String hotelthemeVid)
			throws Exception {
		// TODO Auto-generated method stub
		return hotelthemedao.getByVendorId(hotelthemeVid);
	}

	@Override
	public List<Hotelthemeandcategory> getEntityById(String vendorId)
			throws Exception {
		// TODO Auto-generated method stub
		return hotelthemedao.getHotelById(vendorId);
	}

}
