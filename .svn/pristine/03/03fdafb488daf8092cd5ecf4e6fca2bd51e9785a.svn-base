package com.tayyarah.hotel.dao;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.model.Hotelroomdescription;

public interface HotelroomdescriptionDao {
	public Hotelroomdescription getHotelByRoomType(int RoomTypeID) throws HibernateException;
	public List<Hotelroomdescription> getByVendorId(String HotelRoomDesVid) throws Exception;
	public Map<String, Map<Integer, Hotelroomdescription>> getHotelroomdescriptionByVendorID(List<String> vendorIdList) throws HibernateException;
	public Map<String, Map<Integer, Hotelroomdescription>> getHotelroomdescriptionApiMap(Map<String, Integer> hotelidmap) throws HibernateException;
}
