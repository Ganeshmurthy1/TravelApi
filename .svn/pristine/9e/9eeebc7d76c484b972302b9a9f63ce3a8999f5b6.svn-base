package com.tayyarah.hotel.util;

import java.util.HashMap;
import java.util.List;
import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.model.HotelMarkupCommissionDetails;
import com.tayyarah.hotel.model.HotelSearchCommand;

public interface HotelMarkUpApplier {
	public void applyMarkUpAdd(String hotelcode,
			com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)
			throws HibernateException, Exception;
	public void applyMarkUpAdd(
			HashMap<String, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> rsmap)
			throws HibernateException, Exception;
	public void applyMarkUpInit(
			HashMap<String, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> rsmap)
			throws HibernateException, Exception;
	public void applyMarkUpInitHotelSearch(HotelSearchCommand hs,
			List<HotelMarkup> markups) throws HibernateException, Exception;	
	public void applyAllLevelMarkUpInitHotelSearch(HotelSearchCommand hs, HotelMarkupCommissionDetails hotelmarkupCommissionDetails) throws HibernateException, Exception;
}
