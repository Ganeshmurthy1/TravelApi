package com.tayyarah.hotel.dao;

import java.util.List;

import com.tayyarah.hotel.model.Hotelthemeandcategory;

public interface HotelthemeandcategoryDaoService {
	
	public  List<Hotelthemeandcategory> getEntityByVid(String hotelthemeVid ) throws Exception;
	public List<Hotelthemeandcategory> getEntityById(String vendorId) throws Exception;
}
