package com.tayyarah.hotel.dao;
import java.io.IOException;
import java.util.HashMap;
import org.hibernate.HibernateException;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;

public interface ApiHotelMapStoreDao {
	public HashMap<String, RoomStay> getRoomStaysMap(int searchKey) throws HibernateException, IOException;
	public HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> getTGRoomStaysMap(int searchKey)
			throws HibernateException, IOException;
	public int insertApiMapBySearchKey(int searchKey, APIHotelMap apiHotelMap)
			throws HibernateException, IOException;
}