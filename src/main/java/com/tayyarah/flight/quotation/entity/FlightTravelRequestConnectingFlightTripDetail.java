package com.tayyarah.flight.quotation.entity;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import com.tayyarah.common.util.Timestampable;

@Entity
@Table(name = "flight_travel_request_connecting_flight_trip_details")
public class FlightTravelRequestConnectingFlightTripDetail extends Timestampable{

	@Transient
	private String depTime;	
	@Transient
	private String depDate;
	@Transient
	private String arrTime;	
	@Transient
	private String arrDate;

	@Column(name = "origin_name")
	private String originName;
	@Column(name = "origin_code", length = 3)
	private String originCode;
	@Column(name = "destination_name")
	private String destinationName;
	@Column(name = "destination_code", length = 3)
	private String destinationCode;
	@Column(name = "departure_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTimestamp;	
	@Column(name = "departure_date")
	@Temporal(TemporalType.DATE)
	private Date departureDate;	
	@Column(name = "departure_time")
	@Temporal(TemporalType.TIME)
	private Date departureTime;
	@Column(name = "arrival_timestamp")
	@Temporal(TemporalType.TIMESTAMP)	
	private Date arrivalTimestamp;	
	@Column(name = "arrival_time")
	@Temporal(TemporalType.TIME)
	private Date arrivalTime;	
	@Column(name = "arrival_date")
	@Temporal(TemporalType.DATE)
	private Date arrivalDate;	
	@Column(name = "operated_by_name")
	private String operatedByName;
	@Column(name = "operated_by_code", length = 3)
	protected String operatedByCode;	
	@Column(name = "class_of_service", length = 20)
	protected String classOfService;
	@Column(name = "trips")
	private int trips;	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "flight_travel_trip_id", referencedColumnName = "id")
	private FlightTravelRequestTripDetail flightTravelRequestTripDetail;
	@Column(name = "flight_number")
	private String flightNumber;
	@Column(name = "destination_terminal", length = 30)
	private String destinationTerminal;
	@Column(name = "origin_terminal", length = 30)
	private String originTerminal;
	@Column(name = "flight_duration")
	private String flightDuration;

	public FlightTravelRequestTripDetail getFlightTravelRequestTripDetail() {
		return flightTravelRequestTripDetail;
	}
	public void setFlightTravelRequestTripDetail(FlightTravelRequestTripDetail flightTravelRequestTripDetail) {
		this.flightTravelRequestTripDetail = flightTravelRequestTripDetail;
	}
	public String getFlightNumber() {
		return flightNumber;
	}	
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getDestinationTerminal() {
		return destinationTerminal;
	}
	public void setDestinationTerminal(String destinationTerminal) {
		this.destinationTerminal = destinationTerminal;
	}
	public String getOriginTerminal() {
		return originTerminal;
	}
	public void setOriginTerminal(String originTerminal) {
		this.originTerminal = originTerminal;
	}
	public String getFlightDuration() {
		return flightDuration;
	}
	public void setFlightDuration(String flightDuration) {
		this.flightDuration = flightDuration;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationCode() {
		return destinationCode;
	}
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	public Date getDepartureTimestamp() {
		return departureTimestamp;
	}
	public void setDepartureTimestamp(Date departureTimestamp) {
		this.departureTimestamp = departureTimestamp;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public String getDepTime() {
		return depTime;
	}
	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}
	public String getDepDate() {
		return depDate;
	}
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}
	public Date getArrivalTimestamp() {
		return arrivalTimestamp;
	}
	public void setArrivalTimestamp(Date arrivalTimestamp) {
		this.arrivalTimestamp = arrivalTimestamp;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getArrTime() {
		return arrTime;
	}
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	public String getArrDate() {
		return arrDate;
	}
	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}
	public String getOperatedByName() {
		return operatedByName;
	}
	public void setOperatedByName(String operatedByName) {
		this.operatedByName = operatedByName;
	}
	public String getOperatedByCode() {
		return operatedByCode;
	}
	public void setOperatedByCode(String operatedByCode) {
		this.operatedByCode = operatedByCode;
	}
	public String getClassOfService() {
		return classOfService;
	}
	public void setClassOfService(String classOfService) {
		this.classOfService = classOfService;
	}
	public int getTrips() {
		return trips;
	}
	public void setTrips(int trips) {
		this.trips = trips;
	}	
}