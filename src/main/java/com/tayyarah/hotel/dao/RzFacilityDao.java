package com.tayyarah.hotel.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.model.RzFacility;


public interface RzFacilityDao {
	public RzFacility getHotelByVendorID(int VendorID) throws HibernateException;
	public List<RzFacility> getByVendorId(String HotelRoomDesVid) throws Exception;
}
