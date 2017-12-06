package com.tayyarah.email.dao;
import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderCustomerSSR;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.insurance.entity.InsuranceOrderRow;

public interface FlightOrderRowEmailDao {
	public List<FlightOrderRow> flightOrderRows() throws HibernateException;
	public FlightOrderRow flightOrderRowByOrderId(String orderId) throws HibernateException;
	public List<FlightOrderRow> flightOrderRowsPendingInvoices() throws HibernateException;
	public List<FlightOrderRow> updateFlightOrderRowsEmailStatus(List<FlightOrderRow> flightorderrows) throws HibernateException;
	public List<FlightOrderTripDetail> flightTrips(FlightOrderRow flightOrderRow) throws HibernateException;
	public List<FlightOrderCustomerPriceBreakup> flightPriceBreakup(FlightOrderRow priceFlightOrderRow) throws HibernateException;
	public List<FlightOrderCustomerSSR> flightCustomerSSR(Long customerid)throws HibernateException;
	public List<FlightOrderCustomer> customerDeatails(FlightOrderRow FlightOrderRow) throws HibernateException;	
	public FlightOrderRow flightOrderRowById(String id)
			throws HibernateException;
	public List<FlightOrderRow> getStatusFailedAction()throws HibernateException;
	public InsuranceOrderRow insuranceOrderRowById(long rowId);
	
}

