package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;


import com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.hotel.entity.HotelOrderCancellationPolicy;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderHotelData;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRowCancellation;
import com.tayyarah.hotel.entity.HotelOrderRowCancellationAPIResponse;
import com.tayyarah.hotel.entity.HotelOrderRowCommission;
import com.tayyarah.hotel.entity.HotelOrderRowMarkup;
import com.tayyarah.hotel.model.HotelReport;

public interface HotelOrderDao {
	
	public void insertCommission(HotelOrderRowCommission hotelOrderRowCommission) throws Exception;
	public void insertMarkupDetails(HotelOrderRowMarkup hotelOrderRowMarkup) throws Exception;
	public HotelOrderRowCancellation getUpdateHotelOrderRowCancellation(String orderId) throws HibernateException, Exception;
	public HotelOrderRowCancellation insertOrUpdateHotelOrderRowCancellation(HotelOrderRowCancellation hotelOrderRowCancellation) throws HibernateException, Exception;
	public OrderCustomer insertOrderCustomerDetails(OrderCustomer oc)throws HibernateException, Exception;
	public HotelOrderCancellationPolicy insertHotelOrderCancellationPolicyDetails(HotelOrderCancellationPolicy hocp)throws HibernateException, Exception;
	public HotelOrderGuest insertHotelOrderGuestDetails(HotelOrderGuest hog)throws HibernateException, Exception;
	public HotelOrderHotelData insertHotelOrderHotelDataDetails(HotelOrderHotelData hohd)throws HibernateException, Exception;  
	public HotelOrderRoomInfo insertHotelOrderRoomInfoDetails(HotelOrderRoomInfo hori) throws HibernateException, Exception; 
	public HotelOrderRow insertHotelOrderRowDetails(HotelOrderRow hor) throws HibernateException, Exception;  
	public List<HotelReport> getHotelOrderCancellationPolicyDetails() throws HibernateException, Exception; 
	public HotelOrderRow getHotelOrderRow(String orderid) throws HibernateException, Exception;  	
	public HotelOrderRow updateHotelBookStatus(String orderReference, HotelOrderRow hor);	
	public void updateRoomsBookStatus(String orderReference, String status);
	public  void insertHotelOrderRowCancellationApiResposnse(
			HotelOrderRowCancellationAPIResponse hotelOrderRowCancellationAPIResponse)throws HibernateException, Exception;
	List<HotelOrderRow> getHotelBookingList(List<String> companyIdList, Integer pageNo, Integer pageSize);
	HotelOrderRow getHotelBookingDetail(Long flightId);	
}
