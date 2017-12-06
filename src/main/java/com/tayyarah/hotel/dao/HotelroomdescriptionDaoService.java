package com.tayyarah.hotel.dao;

import java.util.List;

import com.tayyarah.hotel.model.Hotelroomdescription;



public interface HotelroomdescriptionDaoService {
	public Hotelroomdescription getEntityRoomType(int RoomTypeID) throws Exception;
	public  List<Hotelroomdescription> getEntityByVid(String HotelRoomDesVid ) throws Exception;
}
