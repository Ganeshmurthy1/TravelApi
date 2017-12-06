/**
 * 
 */
package com.tayyarah.admin.analytics.lookbook.dao;

import com.tayyarah.admin.analytics.lookbook.entity.BusLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.FlightLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.HotelLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPHistory;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPStatus;

/**
 * @author      : Manish Samrat
 * @createdAt   : 12/07/2017
 * @version
 */
public interface LookBookDao <T>{
	public T insertIntoTable(T tableEntity);
	public T fetchFromTableById(long id);
	public FlightLookBook CheckAndFetchFlightLookBookByAppKey(FlightLookBook flightLookBook);
	public FlightLookBook updateIntoTable(FlightLookBook flightLookBook,String updateBookedOrSearchCount);
	public HotelLookBook CheckAndFetchHotelLookBookByAppKey(HotelLookBook hotelLookBook);
	public HotelLookBook updateIntoHotelTable(HotelLookBook hotelLookBook, String updateBookedOrSearchCount);
	public BusLookBook CheckAndFetchBusLookBookByAppKey(BusLookBook busLookBook);
	public BusLookBook updateIntoBusTable(BusLookBook lookBook, String updateBookedOrSearchCount);
	public LookBookCustomerIPStatus CheckAndFetchIpStatus(String ip);
	public LookBookCustomerIPStatus updateIpStatus(LookBookCustomerIPStatus ipStatus);
	public LookBookCustomerIPHistory CheckAndfetchIpHistory(String ip);
	public LookBookCustomerIPHistory updateIpHistory(LookBookCustomerIPHistory ipHistory);
	public String resetAllB2C();
}
