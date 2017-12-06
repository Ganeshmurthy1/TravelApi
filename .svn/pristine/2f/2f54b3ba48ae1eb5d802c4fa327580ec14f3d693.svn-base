package com.tayyarah.hotel.dao;
import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.HotelOrderCancellationPolicy;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.HotelReport;




public interface HotelOrderRowEmailDao {
	public HotelOrderRow hotelOrderRowByOrderId(String orderId) throws HibernateException;
	public HotelOrderRow hotelOrderRowByRowId(String id) throws HibernateException;
	public List<HotelOrderRoomInfo> roomInfoDeatails(HotelOrderRow hotelOrderRow) throws HibernateException;
	public  List<HotelOrderGuest> guesInformationDeatails(List<HotelOrderRoomInfo> roomInfo) throws HibernateException;
	public List<HotelOrderCancellationPolicy> getCancelPolicies(HotelOrderRow hotelOrderRow)throws HibernateException;
	public HotelReport hotelRoomandGuestandHotelOrderRowInfo(String orderId);
	public List<HotelOrderGuest> hotelOrderGuestInfo(Long id);
	
	
}
