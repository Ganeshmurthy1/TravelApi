package com.tayyarah.bus.dao;

import java.sql.SQLException;
import java.util.List;

import com.tayyarah.bus.entity.BusBlockedSeatTemp;
import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.entity.BusOrderRowCancellation;
import com.tayyarah.bus.entity.BusOrderRowMarkup;
import com.tayyarah.bus.entity.BusOrderRowServiceTax;
import com.tayyarah.bus.entity.BusSearchTemp;
import com.tayyarah.bus.entity.BusSeatAvailableTemp;
import com.tayyarah.common.entity.FlightAndHotelBookApiResponse;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.company.dao.CompanyDao;


public interface BusCommonDao {
	public String getDecryptedAppKey(CompanyDao CDAO,String app_key)throws SQLException;
	public void saveorupdateBusSearchTemp(BusSearchTemp busSearchTemp) throws SQLException;
	public BusSearchTemp getBusSearchTemp(String searchKey) throws SQLException;
	public long getLastBusSearchTempId() throws SQLException;
	public void saveorupdateBusSeatAvailableTemp(BusSeatAvailableTemp busSeatAvailableTemp) throws SQLException;
	public BusSeatAvailableTemp getBusSeatAvailableTemp(String searchKey) throws SQLException;
	public void insertOrderCustomerDetails(OrderCustomer oc) throws Exception;
	public void insertBusOrderRowDetails(BusOrderRow busOrderRow) throws Exception;
	public void updateBusOrderRowDetails(BusOrderRow busOrderRow)throws Exception;
	public void insertBusOrderRowServiceTaxDetails(BusOrderRowServiceTax busOrderRowServiceTax)throws Exception;
	public void insertBusOrderCustomerDetails(BusOrderCustomerDetail busOrderCustomerDetail)throws Exception;
	public void insertPaymentTransactionDetails(PaymentTransaction foc)throws Exception;
	public BusOrderRow getBusOrderRow(String transactionKey)throws Exception;
	public void saveorupdateBusBlockedSeatTemp(BusBlockedSeatTemp busBlockedSeatTemp)throws Exception;
	public BusBlockedSeatTemp getBusBlockedSeatTemp(String searchKey)throws Exception;
	public PaymentTransaction getPaymentTransaction(String orderid)throws Exception;
	public  void updatePaymentTransactionDetails(PaymentTransaction paymentTransaction)throws Exception;
	public List<BusOrderRowMarkup> getBusOrderRowMarkup(BusOrderRow busOrderRow)throws Exception;
	public void saveBusOrderRowCancellation(BusOrderRowCancellation busOrderRowCancellation)throws Exception;
	public BusBlockedSeatTemp getBusBlockedSeatTempUsingTransKey(String transactionKey)throws Exception;
	public  BusOrderRow getBusOrderRowUsingOrderId(String orderid)throws Exception;
	public  PaymentTransaction getPaymentTransactionUsingPgId(String pgId)throws Exception;
	public  OrderCustomer updateOrderCustomerDetails(OrderCustomer oc)throws Exception;
	public void SaveApiBookingStatus(FlightAndHotelBookApiResponse flightAndHotelBookApiResponse);	
}
