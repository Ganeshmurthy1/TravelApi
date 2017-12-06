package com.tayyarah.hotel.dao;

import com.tayyarah.hotel.entity.HotelSearchRoomDetailTemp;

public interface HotelSearchRoomDetailDao {
	public HotelSearchRoomDetailTemp getHotelSearchRoomDetail(HotelSearchRoomDetailTemp hotelSearchRoomDetail);
	public HotelSearchRoomDetailTemp insertOrupdateHotelSearchRoomDetail(HotelSearchRoomDetailTemp hotelSearchRoomDetail);
}
