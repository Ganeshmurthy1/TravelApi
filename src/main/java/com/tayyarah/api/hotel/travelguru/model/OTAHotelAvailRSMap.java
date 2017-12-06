package com.tayyarah.api.hotel.travelguru.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"pos",
		"success",
		"warnings",
		"hotelStays",
		"roomStaysMap",
		"services",
		"areas",
		"criteria",
		"currencyConversions",
		"rebatePrograms",
		"tpaExtensions",
		"errors"
})
@XmlRootElement(name = "OTAHotelAvailRSMap")
public class OTAHotelAvailRSMap implements Serializable{
	public POSType getPos() {
		return pos;
	}
	public void setPos(POSType pos) {
		this.pos = pos;
	}
	public SuccessType getSuccess() {
		return success;
	}
	public void setSuccess(SuccessType success) {
		this.success = success;
	}
	public WarningsType getWarnings() {
		return warnings;
	}
	public void setWarnings(WarningsType warnings) {
		this.warnings = warnings;
	}
	public OTAHotelAvailRS.HotelStays getHotelStays() {
		return hotelStays;
	}
	public void setHotelStays(OTAHotelAvailRS.HotelStays hotelStays) {
		this.hotelStays = hotelStays;
	}
	public ServicesType getServices() {
		return services;
	}
	public void setServices(ServicesType services) {
		this.services = services;
	}
	public AreasType getAreas() {
		return areas;
	}
	public void setAreas(AreasType areas) {
		this.areas = areas;
	}
	public OTAHotelAvailRS.Criteria getCriteria() {
		return criteria;
	}
	public void setCriteria(OTAHotelAvailRS.Criteria criteria) {
		this.criteria = criteria;
	}
	public OTAHotelAvailRS.CurrencyConversions getCurrencyConversions() {
		return currencyConversions;
	}
	public void setCurrencyConversions(OTAHotelAvailRS.CurrencyConversions currencyConversions) {
		this.currencyConversions = currencyConversions;
	}
	public OTAHotelAvailRS.RebatePrograms getRebatePrograms() {
		return rebatePrograms;
	}
	public void setRebatePrograms(OTAHotelAvailRS.RebatePrograms rebatePrograms) {
		this.rebatePrograms = rebatePrograms;
	}
	public TPAExtensions getTpaExtensions() {
		return tpaExtensions;
	}
	public void setTpaExtensions(TPAExtensions tpaExtensions) {
		this.tpaExtensions = tpaExtensions;
	}
	public ErrorsType getErrors() {
		return errors;
	}
	public void setErrors(ErrorsType errors) {
		this.errors = errors;
	}
	public String getSearchCacheLevel() {
		return searchCacheLevel;
	}
	public void setSearchCacheLevel(String searchCacheLevel) {
		this.searchCacheLevel = searchCacheLevel;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getEchoToken() {
		return echoToken;
	}
	public void setEchoToken(String echoToken) {
		this.echoToken = echoToken;
	}
	public Boolean getRetransmissionIndicator() {
		return retransmissionIndicator;
	}
	public void setRetransmissionIndicator(Boolean retransmissionIndicator) {
		this.retransmissionIndicator = retransmissionIndicator;
	}
	public BigInteger getSequenceNmbr() {
		return sequenceNmbr;
	}
	public void setSequenceNmbr(BigInteger sequenceNmbr) {
		this.sequenceNmbr = sequenceNmbr;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}
	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}
	public String getTransactionStatusCode() {
		return transactionStatusCode;
	}
	public void setTransactionStatusCode(String transactionStatusCode) {
		this.transactionStatusCode = transactionStatusCode;
	}
	public BigDecimal getVersion() {
		return version;
	}
	public void setVersion(BigDecimal version) {
		this.version = version;
	}
	public String getAltLangID() {
		return altLangID;
	}
	public void setAltLangID(String altLangID) {
		this.altLangID = altLangID;
	}
	public String getPrimaryLangID() {
		return primaryLangID;
	}
	public void setPrimaryLangID(String primaryLangID) {
		this.primaryLangID = primaryLangID;
	}
	public HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> getRoomStaysMap() {
		return roomStaysMap;
	}
	public void setRoomStaysMap(HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> roomStaysMap) {
		this.roomStaysMap = roomStaysMap;
	}
	private final static long serialVersionUID = -1L;
	@XmlElement(name = "POS")
	protected POSType pos;
	@XmlElement(name = "Success")
	protected SuccessType success;
	@XmlElement(name = "Warnings")
	protected WarningsType warnings;
	@XmlElement(name = "HotelStays")
	protected OTAHotelAvailRS.HotelStays hotelStays;	    
	@XmlElement(name = "Services")
	protected ServicesType services;
	@XmlElement(name = "Areas")
	protected AreasType areas;
	@XmlElement(name = "Criteria")
	protected OTAHotelAvailRS.Criteria criteria;
	@XmlElement(name = "CurrencyConversions")
	protected OTAHotelAvailRS.CurrencyConversions currencyConversions;
	@XmlElement(name = "RebatePrograms")
	protected OTAHotelAvailRS.RebatePrograms rebatePrograms;
	@XmlElement(name = "TPA_Extensions")
	protected TPAExtensions tpaExtensions;
	@XmlElement(name = "Errors")
	protected ErrorsType errors;
	@XmlAttribute(name = "SearchCacheLevel")
	protected String searchCacheLevel;
	@XmlAttribute(name = "CorrelationID")
	protected String correlationID;
	@XmlAttribute(name = "EchoToken")
	protected String echoToken;
	@XmlAttribute(name = "RetransmissionIndicator")
	protected Boolean retransmissionIndicator;
	@XmlAttribute(name = "SequenceNmbr")
	protected BigInteger sequenceNmbr;
	@XmlAttribute(name = "Target")
	protected String target;
	@XmlAttribute(name = "TargetName")
	protected String targetName;
	@XmlAttribute(name = "TimeStamp")
	@XmlSchemaType(name = "anySimpleType")
	protected String timeStamp;
	@XmlAttribute(name = "TransactionIdentifier")
	protected String transactionIdentifier;
	@XmlAttribute(name = "TransactionStatusCode")
	protected String transactionStatusCode;
	@XmlAttribute(name = "Version", required = true)
	protected BigDecimal version;
	@XmlAttribute(name = "AltLangID")
	protected String altLangID;
	@XmlAttribute(name = "PrimaryLangID")
	protected String primaryLangID;

	@XmlElement(name = "RoomStaysMap")
	private HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> roomStaysMap = new HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay>();	
	public HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> getTgRoomStays() {
		return roomStaysMap;
	}
}
