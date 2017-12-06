package com.tayyarah.common.entity;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tayyarah.bus.quotation.entity.BusTravelRequest;
import com.tayyarah.car.quotation.entity.CarTravelRequest;
import com.tayyarah.common.util.Timestampable;
import com.tayyarah.flight.quotation.entity.FlightTravelRequest;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequest;
import com.tayyarah.quotation.entity.InsuranceTravelRequest;
import com.tayyarah.quotation.entity.TrainTravelRequest;
import com.tayyarah.quotation.entity.VisaTravelRequest;


@Entity
@Table(name="trip_request")
public class TripRequest extends Timestampable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="trip_id")
	private long tripId;
	@Column(name="company_id")
	private int companyId;
	@Column(name="user_id")
	private int userId;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_travel_request_id", referencedColumnName = "id")
	private HotelTravelRequest hotelTravelRequest;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "flight_travel_request_id", referencedColumnName = "id")
	private FlightTravelRequest flightTravelRequest;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "car_travel_request_id", referencedColumnName = "id")
	private CarTravelRequest carTravelRequest;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "train_travel_request_id", referencedColumnName = "id")
	private TrainTravelRequest trainTravelRequest;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "visa_travel_request_id", referencedColumnName = "id")
	private VisaTravelRequest visaTravelRequest;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bus_travel_request_id", referencedColumnName = "id")
	private BusTravelRequest busTravelRequest;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "insurance_travel_request_id", referencedColumnName = "id")
	private InsuranceTravelRequest insuranceTravelRequest;

	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public HotelTravelRequest getHotelTravelRequest() {
		return hotelTravelRequest;
	}

	public void setHotelTravelRequest(HotelTravelRequest hotelTravelRequest) {
		this.hotelTravelRequest = hotelTravelRequest;
	}

	public FlightTravelRequest getFlightTravelRequest() {
		return flightTravelRequest;
	}

	public void setFlightTravelRequest(FlightTravelRequest flightTravelRequest) {
		this.flightTravelRequest = flightTravelRequest;
	}

	public CarTravelRequest getCarTravelRequest() {
		return carTravelRequest;
	}

	public void setCarTravelRequest(CarTravelRequest carTravelRequest) {
		this.carTravelRequest = carTravelRequest;
	}

	public TrainTravelRequest getTrainTravelRequest() {
		return trainTravelRequest;
	}

	public void setTrainTravelRequest(TrainTravelRequest trainTravelRequest) {
		this.trainTravelRequest = trainTravelRequest;
	}

	public VisaTravelRequest getVisaTravelRequest() {
		return visaTravelRequest;
	}

	public void setVisaTravelRequest(VisaTravelRequest visaTravelRequest) {
		this.visaTravelRequest = visaTravelRequest;
	}

	public BusTravelRequest getBusTravelRequest() {
		return busTravelRequest;
	}

	public void setBusTravelRequest(BusTravelRequest busTravelRequest) {
		this.busTravelRequest = busTravelRequest;
	}

	public InsuranceTravelRequest getInsuranceTravelRequest() {
		return insuranceTravelRequest;
	}

	public void setInsuranceTravelRequest(InsuranceTravelRequest insuranceTravelRequest) {
		this.insuranceTravelRequest = insuranceTravelRequest;
	} 
 }
