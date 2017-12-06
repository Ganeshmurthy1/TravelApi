package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.util.HashMap;

import org.hibernate.HibernateException;

import com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;


public interface ApiHotelMapStoreDaoService  {	
	public HashMap<String, RoomStay> getRoomStaysMap(int searchKey) throws HibernateException, IOException;
	public HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay> getTGRoomStaysMap(int searchKey) throws HibernateException, IOException;	
	public int insertApiMapBySearchKey(int searchKey, APIHotelMap  apiHotelMap) throws HibernateException, IOException;
}
