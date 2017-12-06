package com.tayyarah.flight.model;

import java.io.Serializable;
import java.util.List;

import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomer;



public class BookingDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderCustomer orderCustomer;
	private FlightPriceResponse flightPriceResponse;	
	private FlightCustomerDetails flightCustomerDetails;
	private String  countrycode;
	private String  transactionkey;
	private List<FlightOrderCustomer> flightOrderCustomers;
	
	public OrderCustomer getOrderCustomer() {
		return orderCustomer;
	}
	public void setOrderCustomer(OrderCustomer orderCustomer) {
		this.orderCustomer = orderCustomer;
	}
	public FlightPriceResponse getFlightPriceResponse() {
		return flightPriceResponse;
	}
	public void setFlightPriceResponse(FlightPriceResponse flightPriceResponse) {
		this.flightPriceResponse = flightPriceResponse;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public String getTransactionkey() {
		return transactionkey;
	}
	public void setTransactionkey(String transactionkey) {
		this.transactionkey = transactionkey;
	}	
	public FlightCustomerDetails getFlightCustomerDetails() {
		return flightCustomerDetails;
	}
	public void setFlightCustomerDetails(FlightCustomerDetails flightCustomerDetails) {
		this.flightCustomerDetails = flightCustomerDetails;
	}	
	public List<FlightOrderCustomer> getFlightOrderCustomers() {
		return flightOrderCustomers;
	}
	public void setFlightOrderCustomers(
			List<FlightOrderCustomer> flightOrderCustomers) {
		this.flightOrderCustomers = flightOrderCustomers;
	}
}