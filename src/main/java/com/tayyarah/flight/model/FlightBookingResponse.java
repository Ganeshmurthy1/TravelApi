package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FlightBookingResponse implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<PassengerFareBreakUp> passengerFareBreakUps;
	private FareFlightSegment  fareFlightSegment;
	private Flightsearch  flightsearch;
	private String transactionKey;
	private String country;
	private boolean cache;	
	private String pnr;
	private String confirmationNumber;
	private String bookingComments;
	private String bokingConditions;
	private boolean bookingStatus;
	private boolean paymentStatus;	
	private int paymentCalledCount;
	private BigDecimal GSTonMarkup;
	private BigDecimal gstTotal;
	private BigDecimal gstOnMYArptTax;
	private String lastTicketingDate;
	private BigDecimal FinalPriceWithGST;
	private BigDecimal processingfee;
	private String pgID;
	private String newpgID;
	private List<PassengerFareBreakUp> passengerFareBreakUpsSpecial;
	private FareFlightSegment  fareFlightSegmentSpecial;
	private String pnrSpecial;
	private String confirmationNumberSpecial;
	private String bookingCommentsSpecial;
	private String bokingConditionsSpecial;
	private boolean bookingStatusSpecial;
	private BigDecimal GSTonMarkupSpecial;
	private String lastTicketingDateSpecial;	
	private String invoiceNumber;
	private String invoiceNumberSpecial;
	private FlightCustomerDetails flightCustomerDetails;
	
	public BigDecimal getGSTonMarkup() {
		return GSTonMarkup;
	}
	public void setGSTonMarkup(BigDecimal gSTonMarkup) {
		GSTonMarkup = gSTonMarkup;
	}
	public BigDecimal getFinalPriceWithGST() {
		return FinalPriceWithGST;
	}
	public void setFinalPriceWithGST(BigDecimal finalPriceWithGST) {
		FinalPriceWithGST = finalPriceWithGST;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public String getInvoiceNumberSpecial() {
		return invoiceNumberSpecial;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public void setInvoiceNumberSpecial(String invoiceNumberSpecial) {
		this.invoiceNumberSpecial = invoiceNumberSpecial;
	}
	public List<PassengerFareBreakUp> getPassengerFareBreakUpsSpecial() {
		return passengerFareBreakUpsSpecial;
	}
	public void setPassengerFareBreakUpsSpecial(
			List<PassengerFareBreakUp> passengerFareBreakUpsSpecial) {
		this.passengerFareBreakUpsSpecial = passengerFareBreakUpsSpecial;
	}
	public FareFlightSegment getFareFlightSegmentSpecial() {
		return fareFlightSegmentSpecial;
	}

	public void setFareFlightSegmentSpecial(FareFlightSegment fareFlightSegmentSpecial) {
		this.fareFlightSegmentSpecial = fareFlightSegmentSpecial;
	}
	public String getPnrSpecial() {
		return pnrSpecial;
	}
	public void setPnrSpecial(String pnrSpecial) {
		this.pnrSpecial = pnrSpecial;
	}
	public String getConfirmationNumberSpecial() {
		return confirmationNumberSpecial;
	}
	public void setConfirmationNumberSpecial(String confirmationNumberSpecial) {
		this.confirmationNumberSpecial = confirmationNumberSpecial;
	}
	public String getBookingCommentsSpecial() {
		return bookingCommentsSpecial;
	}
	public void setBookingCommentsSpecial(String bookingCommentsSpecial) {
		this.bookingCommentsSpecial = bookingCommentsSpecial;
	}
	public String getBokingConditionsSpecial() {
		return bokingConditionsSpecial;
	}
	public void setBokingConditionsSpecial(String bokingConditionsSpecial) {
		this.bokingConditionsSpecial = bokingConditionsSpecial;
	}
	public boolean isBookingStatusSpecial() {
		return bookingStatusSpecial;
	}
	public void setBookingStatusSpecial(boolean bookingStatusSpecial) {
		this.bookingStatusSpecial = bookingStatusSpecial;
	}
	public BigDecimal getGSTonMarkupSpecial() {
		return GSTonMarkupSpecial;
	}
	public void setGSTonMarkupSpecial(BigDecimal gSTonMarkupSpecial) {
		GSTonMarkupSpecial = gSTonMarkupSpecial;
	}
	public String getLastTicketingDateSpecial() {
		return lastTicketingDateSpecial;
	}
	public void setLastTicketingDateSpecial(String lastTicketingDateSpecial) {
		this.lastTicketingDateSpecial = lastTicketingDateSpecial;
	}
	public String getLastTicketingDate() {
		return lastTicketingDate;
	}
	public void setLastTicketingDate(String lastTicketingDate) {
		this.lastTicketingDate = lastTicketingDate;
	}
	public int getPaymentCalledCount() {
		return paymentCalledCount;
	}
	public void setPaymentCalledCount(int paymentCalledCount) {
		this.paymentCalledCount = paymentCalledCount;
	}
	public String getPgID() {
		return pgID;
	}
	public void setPgID(String pgID) {
		this.pgID = pgID;
	}
	public String getNewpgID() {
		return newpgID;
	}
	public void setNewpgID(String newpgID) {
		this.newpgID = newpgID;
	}
	public boolean isPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public FlightCustomerDetails getFlightCustomerDetails() {
		return flightCustomerDetails;
	}
	public void setFlightCustomerDetails(FlightCustomerDetails flightCustomerDetails) {
		this.flightCustomerDetails = flightCustomerDetails;
	}
	public boolean isBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(boolean bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public Flightsearch getFlightsearch() {
		return flightsearch;
	}
	public void setFlightsearch(Flightsearch flightsearch) {
		this.flightsearch = flightsearch;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean getCache ()
	{
		return cache;
	}
	public void setCache (boolean cache)
	{
		this.cache = cache;
	}
	public String getTransactionKey ()
	{
		return transactionKey;
	}
	public void setTransactionKey (String transactionKey)
	{
		this.transactionKey = transactionKey;
	}

	/**
	 * @return the passengerFareBreakUps
	 */
	public List<PassengerFareBreakUp> getPassengerFareBreakUps() {
		return passengerFareBreakUps;
	}

	/**
	 * @param passengerFareBreakUps the passengerFareBreakUps to set
	 */
	public void setPassengerFareBreakUps(
			List<PassengerFareBreakUp> passengerFareBreakUps) {
		this.passengerFareBreakUps = passengerFareBreakUps;
	}

	/**
	 * @return the fareFlightSegment
	 */
	public FareFlightSegment getFareFlightSegment() {
		return fareFlightSegment;
	}

	/**
	 * @param fareFlightSegment the fareFlightSegment to set
	 */
	public void setFareFlightSegment(FareFlightSegment fareFlightSegment) {
		this.fareFlightSegment = fareFlightSegment;
	}

	/**
	 * @return the pnr
	 */
	public String getPnr() {
		return pnr;
	}

	/**
	 * @param pnr the pnr to set
	 */
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	/**
	 * @return the confirmationNumber
	 */
	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	/**
	 * @param confirmationNumber the confirmationNumber to set
	 */
	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	/**
	 * @return the bookingComments
	 */
	public String getBookingComments() {
		return bookingComments;
	}

	/**
	 * @param bookingComments the bookingComments to set
	 */
	public void setBookingComments(String bookingComments) {
		this.bookingComments = bookingComments;
	}

	/**
	 * @return the bokingConditions
	 */
	public String getBokingConditions() {
		return bokingConditions;
	}

	/**
	 * @param bokingConditions the bokingConditions to set
	 */
	public void setBokingConditions(String bokingConditions) {
		this.bokingConditions = bokingConditions;
	}
	public BigDecimal getGstTotal() {
		return gstTotal;
	}
	public void setGstTotal(BigDecimal gstTotal) {
		this.gstTotal = gstTotal;
	}
	public BigDecimal getGstOnMYArptTax() {
		return gstOnMYArptTax;
	}
	public void setGstOnMYArptTax(BigDecimal gstOnMYArptTax) {
		this.gstOnMYArptTax = gstOnMYArptTax;
	}
	public BigDecimal getProcessingfee() {
		return processingfee;
	}
	public void setProcessingfee(BigDecimal processingfee) {
		this.processingfee = processingfee;
	}
}