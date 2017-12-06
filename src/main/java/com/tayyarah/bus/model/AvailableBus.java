package com.tayyarah.bus.model;
import java.io.Serializable;
/*Created By Vimal Susai Raj S 
 * Date : 25-5-2017*/
import java.math.BigDecimal;
import java.util.List;

public class AvailableBus implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String operatorName;
	private String departureTime;
	private String arrivalTime;
	private Boolean mTicketAllowed;
	private Fare fare;
	private String serviceId;
	private Boolean idProofRequired;
	private BigDecimal commPCT;
	private String busType;
	private String routeScheduleId;
	private Integer availableSeats;
	private Boolean partialCancellationAllowed;
	private String cancellationPolicy;
	private Integer operatorId;	
	private List<BoardingPoint> boardingPoints;
	private List<DroppingPoint> droppingPoints;
	private Boolean isChildConcession;
	private Integer inventoryType;
	private Boolean isGetLayoutByBPDP;
	private Boolean isFareUpdateRequired;
	private Boolean isRTC;
	private Boolean isOpTicketTemplateRequired;
	private Boolean isOpLogoRequired;	
	
	
	public String getOperatorName() {
		return operatorName;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public Boolean getmTicketAllowed() {
		return mTicketAllowed;
	}
	public Fare getFare() {
		return fare;
	}
	public String getServiceId() {
		return serviceId;
	}
	public Boolean getIdProofRequired() {
		return idProofRequired;
	}
	public BigDecimal getCommPCT() {
		return commPCT;
	}
	public String getBusType() {
		return busType;
	}
	public String getRouteScheduleId() {
		return routeScheduleId;
	}
	public Integer getAvailableSeats() {
		return availableSeats;
	}
	public Boolean getPartialCancellationAllowed() {
		return partialCancellationAllowed;
	}
	public String getCancellationPolicy() {
		return cancellationPolicy;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public List<BoardingPoint> getBoardingPoints() {
		return boardingPoints;
	}
	public List<DroppingPoint> getDroppingPoints() {
		return droppingPoints;
	}
	public Boolean getIsChildConcession() {
		return isChildConcession;
	}
	public Integer getInventoryType() {
		return inventoryType;
	}
	public Boolean getIsGetLayoutByBPDP() {
		return isGetLayoutByBPDP;
	}
	public Boolean getIsFareUpdateRequired() {
		return isFareUpdateRequired;
	}
	public Boolean getIsRTC() {
		return isRTC;
	}
	public Boolean getIsOpTicketTemplateRequired() {
		return isOpTicketTemplateRequired;
	}
	public Boolean getIsOpLogoRequired() {
		return isOpLogoRequired;
	}	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public void setmTicketAllowed(Boolean mTicketAllowed) {
		this.mTicketAllowed = mTicketAllowed;
	}
	public void setFare(Fare fare) {
		this.fare = fare;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public void setIdProofRequired(Boolean idProofRequired) {
		this.idProofRequired = idProofRequired;
	}
	public void setCommPCT(BigDecimal commPCT) {
		this.commPCT = commPCT;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public void setRouteScheduleId(String routeScheduleId) {
		this.routeScheduleId = routeScheduleId;
	}
	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}
	public void setPartialCancellationAllowed(Boolean partialCancellationAllowed) {
		this.partialCancellationAllowed = partialCancellationAllowed;
	}
	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancellationPolicy = cancellationPolicy;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public void setBoardingPoints(List<BoardingPoint> boardingPoints) {
		this.boardingPoints = boardingPoints;
	}
	public void setDroppingPoints(List<DroppingPoint> droppingPoints) {
		this.droppingPoints = droppingPoints;
	}
	public void setIsChildConcession(Boolean isChildConcession) {
		this.isChildConcession = isChildConcession;
	}
	public void setInventoryType(Integer inventoryType) {
		this.inventoryType = inventoryType;
	}
	public void setIsGetLayoutByBPDP(Boolean isGetLayoutByBPDP) {
		this.isGetLayoutByBPDP = isGetLayoutByBPDP;
	}
	public void setIsFareUpdateRequired(Boolean isFareUpdateRequired) {
		this.isFareUpdateRequired = isFareUpdateRequired;
	}
	public void setIsRTC(Boolean isRTC) {
		this.isRTC = isRTC;
	}
	public void setIsOpTicketTemplateRequired(Boolean isOpTicketTemplateRequired) {
		this.isOpTicketTemplateRequired = isOpTicketTemplateRequired;
	}
	public void setIsOpLogoRequired(Boolean isOpLogoRequired) {
		this.isOpLogoRequired = isOpLogoRequired;
	}
	
}
