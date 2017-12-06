package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.math.BigInteger;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.HotelSearchRoomDetailTemp;


public interface HotelSearchRoomDetailDaoService {
	public HotelSearchRoomDetailTemp getHotelSearchRoomDetail(HotelSearchRoomDetailTemp hotelSearchRoomDetail);
	public HotelSearchRoomDetailTemp insertOrupdateHotelSearchRoomDetail(HotelSearchRoomDetailTemp hotelSearchRoomDetail);
}
