package com.tayyarah.flight.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.common.entity.FlightAndHotelBookApiResponse;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.flight.entity.FlightBookingDetailsTemp;
import com.tayyarah.flight.entity.FlightBookingKeysTemp;
import com.tayyarah.flight.entity.FlightFareAlertConnectingFlight;
import com.tayyarah.flight.entity.FlightFareAlertDetail;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderCustomerSSR;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderRowCancellation;
import com.tayyarah.flight.entity.FlightOrderRowCommission;
import com.tayyarah.flight.entity.FlightOrderRowMarkup;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.user.entity.WalletAmountTranferHistory;


public interface FlightBookingDao {

	public void insertCommission(FlightOrderRowCommission flightOrderRowCommission) throws Exception;
	public void insertMarkupDetails(FlightOrderRowMarkup flightOrderRowMarkup) throws Exception;	
	public void insertOrderCustomerDetails(OrderCustomer oc) throws Exception;	
	public void insertFlightOrderCustomerDetails(FlightOrderCustomer foc) throws Exception;
	public void insertFlightOrderCustomerSSRDetails(FlightOrderCustomerSSR focs)throws Exception;
	public void insertFlightOrderCustomerPriceBreakupDetails(FlightOrderCustomerPriceBreakup foc) throws Exception;
	public void insertFlightOrderRowDetails(FlightOrderRow foc) throws Exception;
	public void updateFlightOrderRowDetails(FlightOrderRow foc) throws Exception;
	public void insertFlightOrderTripDetailDetails(FlightOrderTripDetail foc) throws Exception;
	public void insertPaymentTransactionDetails(PaymentTransaction foc) throws Exception;
	public PaymentTransaction getPaymentTransactionDetail(String paymentref) throws Exception;
	public PaymentTransaction insertPaymentTransactionDetail(PaymentTransaction pt) throws Exception;
	public int getPaymentTransactionsCount(String orderid) throws Exception;
	public List<PaymentTransaction> getPaymentTransactionDetails(String orderid) throws Exception;
	public void updatePNR(String pnr,String orderID)throws HibernateException,
	NumberFormatException,Exception;
	public void updatePaymentStatus(PaymentTransaction paymentTransaction)throws HibernateException,
	Exception;
	public void insertwalletamount_tranfer_history(WalletAmountTranferHistory AWTH) throws Exception ;
	public void insertBookingDetails(FlightBookingDetailsTemp bookingDetailsToDb)throws HibernateException,
	Exception;
	public String getorderID(String paymentGatewayId)throws HibernateException,
	Exception;
	public FlightBookingDetailsTemp getBookingDetailsToDb(String orderId)throws HibernateException,
	Exception;
	public void updateKeyStatus(String transactionkey)throws HibernateException,
	NumberFormatException,Exception;
	public boolean getTransStatus(String transId)throws HibernateException,
	NumberFormatException,Exception;
	public void updateWalletBalanceIfFailed(BigDecimal amount,int walletId,WalletAmountTranferHistory AWTH, int travelType)
			throws HibernateException, NumberFormatException, Exception;
	public void updatePNRandWallet(String pnr,String orderID,String refNo)
			throws HibernateException, NumberFormatException, Exception;
	public FlightOrderRow updatePNRandWalletTBO(String pnr,String orderID,String refNo,BigDecimal suppierdiscount,BigDecimal systemdiscount,BigDecimal publisheddiscount,String apitraceid)
			throws HibernateException, NumberFormatException, Exception;
	public void updateHoldPNRTBO(String pnr,String orderID,String refNo,BigDecimal suppierdiscount,BigDecimal systemdiscount,BigDecimal publisheddiscount,String apitraceid,String statusaction)
			throws HibernateException, NumberFormatException, Exception;
	public FlightOrderRow GetFlightBookingDetails(String orderId)throws HibernateException,
	Exception;
	public void updateFlightOrderCustomerDetails(List<FlightOrderCustomer>  foc,long orderrowid) throws Exception;
	public FlightOrderRow getflightorderrow(String orderID) throws HibernateException, NumberFormatException, Exception;
	public FlightOrderRow getflightorderrowbypnr(String orderID) throws HibernateException, NumberFormatException, Exception;
	public FlightBookingKeysTemp getpricekey(String transactionkey) throws HibernateException, NumberFormatException, Exception;
	public void updateFlightOrderCustomerPriceBreakupDetails(List<FlightOrderCustomerPriceBreakup> focp, long orderrowid)
			throws Exception;
	public  void SaveApiBookingStatus(FlightAndHotelBookApiResponse flightAndHotelBookApiResponse)throws Exception;	
	public List<FlightOrderRowCancellation> getAllFailedCancellations();
	public void updateOrderCustomerDetails(OrderCustomer oc);
	public FareFlightSegment getLowPriceFlightFareSegment(String transactionkey, String flightIndex,FlightTempAirSegmentDAO flightTempAirSegmentDAO);
	public FlightFareAlertDetail insertLowFareFlightDetail(FlightFareAlertDetail flightFareAlertDetail);
	public void updateLowFareFlightDetail(FlightFareAlertDetail flightFareAlertDetail);
	public FlightFareAlertConnectingFlight insertFlightFareAlertConnectingFlight(FlightFareAlertConnectingFlight flightFareAlertConnectingFlight);
	//public FlightOrderRowCancellation updateFlightOrderRowCancellation(FlightOrderRowCancellation flightOrderRowCancellation);
	//public FlightOrderRowCancellationAPIResponse saveFlightOrderRowCancellationApiResponse(FlightOrderRowCancellationAPIResponse flightOrderRowCancellationAPIResponse);
}
