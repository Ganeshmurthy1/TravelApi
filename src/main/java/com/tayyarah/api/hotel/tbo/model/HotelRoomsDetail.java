
package com.tayyarah.api.hotel.tbo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"ChildCount",
	"RequireAllPaxDetails",
	"RoomIndex",
	"RoomTypeCode",
	"RoomTypeName",
	"RatePlanCode",
	"RatePlanName",
	"InfoSource",
	"SequenceNo",
	"DayRates",
	"SupplierPrice",
	"Price",
	"RoomPromotion",
	"Amenities",
	"SmokingPreference",
	"BedTypes",
	"BedTypeCode", 
	"Supplements",
	"LastCancellationDate",
	"CancellationPolicies",
	"CancellationPolicy",
	"SupplierSpecificData"	
})
public class HotelRoomsDetail {

	@JsonProperty("SupplierSpecificData")
	private String SupplierSpecificData;
	public String getSupplierSpecificData() {
		return SupplierSpecificData;
	}
	public void setSupplierSpecificData(String supplierSpecificData) {
		this.SupplierSpecificData = supplierSpecificData;
	}



   


	@JsonProperty("ChildCount")
	private Integer ChildCount;
	@JsonProperty("RequireAllPaxDetails")
	private Boolean RequireAllPaxDetails;
	@JsonProperty("RoomIndex")
	private Integer RoomIndex;
	@JsonProperty("RoomTypeCode")
	private String RoomTypeCode;
	@JsonProperty("RoomTypeName")
	private String RoomTypeName;
	@JsonProperty("RatePlanCode")
	private String RatePlanCode;
	@JsonProperty("RatePlanName")
	private String RatePlanName;
	@JsonProperty("InfoSource")
	private String InfoSource;
	@JsonProperty("SequenceNo")
	private String SequenceNo;
	@JsonProperty("DayRates")
	@Valid
	private List<DayRate> DayRates = new ArrayList<DayRate>();
	@JsonProperty("SupplierPrice")
	private Double SupplierPrice;
	@JsonProperty("Price")
	@Valid
	private com.tayyarah.api.hotel.tbo.model.Price Price;
	@JsonProperty("RoomPromotion")
	private String RoomPromotion;
	@JsonProperty("Amenities")
	@Valid
	private List<String> Amenities = new ArrayList<String>();
	@JsonProperty("SmokingPreference")
	private String SmokingPreference;
	@JsonProperty("BedTypes")
	@Valid
	private List<BedType> BedTypes = new ArrayList<BedType>();
	
	
	@JsonProperty("BedTypeCode")
	@Valid
	private String BedTypeCode;

	public String getBedTypeCode() {
		return this.BedTypeCode;
	}

	public void setBedTypeCode(String BedTypeCode) {
		this.BedTypeCode = BedTypeCode;
	}

	@JsonProperty("Supplements")
	@Valid
	private List<String> Supplements = new ArrayList<String>();
	@JsonProperty("LastCancellationDate")
	private String LastCancellationDate;
	@JsonProperty("CancellationPolicies")
	@Valid
	private List<com.tayyarah.api.hotel.tbo.model.CancellationPolicy> CancellationPolicies = new ArrayList<com.tayyarah.api.hotel.tbo.model.CancellationPolicy>();
	@JsonProperty("CancellationPolicy")
	private String CancellationPolicy;
	@JsonProperty("additionalProperties")
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public HotelRoomsDetail() {
	}

	/**
	 * 
	 * @param RoomPromotion
	 * @param RatePlanName
	 * @param RoomTypeName
	 * @param DayRates
	 * @param SupplierPrice
	 * @param Supplements
	 * @param RequireAllPaxDetails
	 * @param CancellationPolicies
	 * @param RoomTypeCode
	 * @param BedTypes
	 * @param BedTypeCode
	 * @param ChildCount
	 * @param CancellationPolicy
	 * @param SmokingPreference
	 * @param Amenities
	 * @param Price
	 * @param LastCancellationDate
	 * @param InfoSource
	 * @param RatePlanCode
	 * @param RoomIndex
	 * @param SequenceNo
	 */
	public HotelRoomsDetail(Integer ChildCount, Boolean RequireAllPaxDetails, Integer RoomIndex, String RoomTypeCode, String RoomTypeName, String RatePlanCode, String RatePlanName, String InfoSource, String SequenceNo, List<DayRate> DayRates, Double SupplierPrice, com.tayyarah.api.hotel.tbo.model.Price Price, String RoomPromotion, List<String> Amenities, String SmokingPreference, List<BedType> BedTypes, String BedTypeCode, List<String> Supplements, String LastCancellationDate, List<com.tayyarah.api.hotel.tbo.model.CancellationPolicy> CancellationPolicies, String CancellationPolicy) {
		this.ChildCount = ChildCount;
		this.RequireAllPaxDetails = RequireAllPaxDetails;
		this.RoomIndex = RoomIndex;
		this.RoomTypeCode = RoomTypeCode;
		this.RoomTypeName = RoomTypeName;
		this.RatePlanCode = RatePlanCode;
		this.RatePlanName = RatePlanName;
		this.InfoSource = InfoSource;
		this.SequenceNo = SequenceNo;
		this.DayRates = DayRates;
		this.SupplierPrice = SupplierPrice;
		this.Price = Price;
		this.RoomPromotion = RoomPromotion;
		this.Amenities = Amenities;
		this.SmokingPreference = SmokingPreference;
		this.BedTypes = BedTypes;
		this.BedTypeCode = BedTypeCode;
		this.Supplements = Supplements;
		this.LastCancellationDate = LastCancellationDate;
		this.CancellationPolicies = CancellationPolicies;
		this.CancellationPolicy = CancellationPolicy;
	}
	public HotelRoomsDetail(Integer ChildCount, Boolean RequireAllPaxDetails, Integer RoomIndex, String RoomTypeCode, String RoomTypeName, String RatePlanCode, String RatePlanName, String InfoSource, String SequenceNo, List<DayRate> DayRates, Double SupplierPrice, com.tayyarah.api.hotel.tbo.model.Price Price, String RoomPromotion, List<String> Amenities, String SmokingPreference, List<BedType> BedTypes, String BedTypeCode, List<String> Supplements, String LastCancellationDate, List<com.tayyarah.api.hotel.tbo.model.CancellationPolicy> CancellationPolicies, String CancellationPolicy, String SupplierSpecificData) {
		this.ChildCount = ChildCount;
		this.RequireAllPaxDetails = RequireAllPaxDetails;
		this.RoomIndex = RoomIndex;
		this.RoomTypeCode = RoomTypeCode;
		this.RoomTypeName = RoomTypeName;
		this.RatePlanCode = RatePlanCode;
		this.RatePlanName = RatePlanName;
		this.InfoSource = InfoSource;
		this.SequenceNo = SequenceNo;
		this.DayRates = DayRates;
		this.SupplierPrice = SupplierPrice;
		this.Price = Price;
		this.RoomPromotion = RoomPromotion;
		this.Amenities = Amenities;
		this.SmokingPreference = SmokingPreference;
		this.BedTypes = BedTypes;
		this.BedTypeCode = BedTypeCode;
		this.Supplements = Supplements;
		this.LastCancellationDate = LastCancellationDate;
		this.CancellationPolicies = CancellationPolicies;
		this.CancellationPolicy = CancellationPolicy;
		this.SupplierSpecificData = SupplierSpecificData;
	}

	
	/**
	 * 
	 * @return
	 *     The ChildCount
	 */
	@JsonProperty("ChildCount")
	public Integer getChildCount() {
		return ChildCount;
	}

	/**
	 * 
	 * @param ChildCount
	 *     The ChildCount
	 */
	@JsonProperty("ChildCount")
	public void setChildCount(Integer ChildCount) {
		this.ChildCount = ChildCount;
	}

	/**
	 * 
	 * @return
	 *     The RequireAllPaxDetails
	 */
	@JsonProperty("RequireAllPaxDetails")
	public Boolean getRequireAllPaxDetails() {
		return RequireAllPaxDetails;
	}

	/**
	 * 
	 * @param RequireAllPaxDetails
	 *     The RequireAllPaxDetails
	 */
	@JsonProperty("RequireAllPaxDetails")
	public void setRequireAllPaxDetails(Boolean RequireAllPaxDetails) {
		this.RequireAllPaxDetails = RequireAllPaxDetails;
	}

	/**
	 * 
	 * @return
	 *     The RoomIndex
	 */
	@JsonProperty("RoomIndex")
	public Integer getRoomIndex() {
		return RoomIndex;
	}

	/**
	 * 
	 * @param RoomIndex
	 *     The RoomIndex
	 */
	@JsonProperty("RoomIndex")
	public void setRoomIndex(Integer RoomIndex) {
		this.RoomIndex = RoomIndex;
	}

	/**
	 * 
	 * @return
	 *     The RoomTypeCode
	 */
	@JsonProperty("RoomTypeCode")
	public String getRoomTypeCode() {
		return RoomTypeCode;
	}

	/**
	 * 
	 * @param RoomTypeCode
	 *     The RoomTypeCode
	 */
	@JsonProperty("RoomTypeCode")
	public void setRoomTypeCode(String RoomTypeCode) {
		this.RoomTypeCode = RoomTypeCode;
	}

	/**
	 * 
	 * @return
	 *     The RoomTypeName
	 */
	@JsonProperty("RoomTypeName")
	public String getRoomTypeName() {
		return RoomTypeName;
	}

	/**
	 * 
	 * @param RoomTypeName
	 *     The RoomTypeName
	 */
	@JsonProperty("RoomTypeName")
	public void setRoomTypeName(String RoomTypeName) {
		this.RoomTypeName = RoomTypeName;
	}

	/**
	 * 
	 * @return
	 *     The RatePlanCode
	 */
	@JsonProperty("RatePlanCode")
	public String getRatePlanCode() {
		return RatePlanCode;
	}

	/**
	 * 
	 * @param RatePlanCode
	 *     The RatePlanCode
	 */
	@JsonProperty("RatePlanCode")
	public void setRatePlanCode(String RatePlanCode) {
		this.RatePlanCode = RatePlanCode;
	}

	/**
	 * 
	 * @return
	 *     The RatePlanName
	 */
	@JsonProperty("RatePlanName")
	public String getRatePlanName() {
		return RatePlanName;
	}

	/**
	 * 
	 * @param RatePlanName
	 *     The RatePlanName
	 */
	@JsonProperty("RatePlanName")
	public void setRatePlanName(String RatePlanName) {
		this.RatePlanName = RatePlanName;
	}

	/**
	 * 
	 * @return
	 *     The InfoSource
	 */
	@JsonProperty("InfoSource")
	public String getInfoSource() {
		return InfoSource;
	}

	/**
	 * 
	 * @param InfoSource
	 *     The InfoSource
	 */
	@JsonProperty("InfoSource")
	public void setInfoSource(String InfoSource) {
		this.InfoSource = InfoSource;
	}

	/**
	 * 
	 * @return
	 *     The SequenceNo
	 */
	@JsonProperty("SequenceNo")
	public String getSequenceNo() {
		return SequenceNo;
	}

	/**
	 * 
	 * @param SequenceNo
	 *     The SequenceNo
	 */
	@JsonProperty("SequenceNo")
	public void setSequenceNo(String SequenceNo) {
		this.SequenceNo = SequenceNo;
	}

	/**
	 * 
	 * @return
	 *     The DayRates
	 */
	@JsonProperty("DayRates")
	public List<DayRate> getDayRates() {
		return DayRates;
	}

	/**
	 * 
	 * @param DayRates
	 *     The DayRates
	 */
	@JsonProperty("DayRates")
	public void setDayRates(List<DayRate> DayRates) {
		this.DayRates = DayRates;
	}

	/**
	 * 
	 * @return
	 *     The SupplierPrice
	 */
	@JsonProperty("SupplierPrice")
	public Double getSupplierPrice() {
		return SupplierPrice;
	}

	/**
	 * 
	 * @param SupplierPrice
	 *     The SupplierPrice
	 */
	@JsonProperty("SupplierPrice")
	public void setSupplierPrice(Double SupplierPrice) {
		this.SupplierPrice = SupplierPrice;
	}

	/**
	 * 
	 * @return
	 *     The Price
	 */
	@JsonProperty("Price")
	public com.tayyarah.api.hotel.tbo.model.Price getPrice() {
		return Price;
	}

	/**
	 * 
	 * @param Price
	 *     The Price
	 */
	@JsonProperty("Price")
	public void setPrice(com.tayyarah.api.hotel.tbo.model.Price Price) {
		this.Price = Price;
	}

	/**
	 * 
	 * @return
	 *     The RoomPromotion
	 */
	@JsonProperty("RoomPromotion")
	public String getRoomPromotion() {
		return RoomPromotion;
	}

	/**
	 * 
	 * @param RoomPromotion
	 *     The RoomPromotion
	 */
	@JsonProperty("RoomPromotion")
	public void setRoomPromotion(String RoomPromotion) {
		this.RoomPromotion = RoomPromotion;
	}

	/**
	 * 
	 * @return
	 *     The Amenities
	 */
	@JsonProperty("Amenities")
	public List<String> getAmenities() {
		return Amenities;
	}

	/**
	 * 
	 * @param Amenities
	 *     The Amenities
	 */
	@JsonProperty("Amenities")
	public void setAmenities(List<String> Amenities) {
		this.Amenities = Amenities;
	}

	/**
	 * 
	 * @return
	 *     The SmokingPreference
	 */
	@JsonProperty("SmokingPreference")
	public String getSmokingPreference() {
		return SmokingPreference;
	}

	/**
	 * 
	 * @param SmokingPreference
	 *     The SmokingPreference
	 */
	@JsonProperty("SmokingPreference")
	public void setSmokingPreference(String SmokingPreference) {
		this.SmokingPreference = SmokingPreference;
	}

	/**
	 * 
	 * @return
	 *     The BedTypes
	 */
	@JsonProperty("BedTypes")
	public List<BedType> getBedTypes() {
		return BedTypes;
	}

	/**
	 * 
	 * @param BedTypes
	 *     The BedTypes
	 */
	@JsonProperty("BedTypes")
	public void setBedTypes(List<BedType> BedTypes) {
		this.BedTypes = BedTypes;
	}

	/**
	 * 
	 * @return
	 *     The Supplements
	 */
	@JsonProperty("Supplements")
	public List<String> getHotelSupplements() {
		return Supplements;
	}

	/**
	 * 
	 * @param Supplements
	 *     The Supplements
	 */
	@JsonProperty("Supplements")
	public void setHotelSupplements(List<String> Supplements) {
		this.Supplements = Supplements;
	}

	/**
	 * 
	 * @return
	 *     The LastCancellationDate
	 */
	@JsonProperty("LastCancellationDate")
	public String getLastCancellationDate() {
		return LastCancellationDate;
	}

	/**
	 * 
	 * @param LastCancellationDate
	 *     The LastCancellationDate
	 */
	@JsonProperty("LastCancellationDate")
	public void setLastCancellationDate(String LastCancellationDate) {
		this.LastCancellationDate = LastCancellationDate;
	}

	/**
	 * 
	 * @return
	 *     The CancellationPolicies
	 */
	@JsonProperty("CancellationPolicies")
	public List<com.tayyarah.api.hotel.tbo.model.CancellationPolicy> getCancellationPolicies() {
		return CancellationPolicies;
	}

	/**
	 * 
	 * @param CancellationPolicies
	 *     The CancellationPolicies
	 */
	@JsonProperty("CancellationPolicies")
	public void setCancellationPolicies(List<com.tayyarah.api.hotel.tbo.model.CancellationPolicy> CancellationPolicies) {
		this.CancellationPolicies = CancellationPolicies;
	}

	/**
	 * 
	 * @return
	 *     The CancellationPolicy
	 */
	@JsonProperty("CancellationPolicy")
	public String getCancellationPolicy() {
		return CancellationPolicy;
	}

	/**
	 * 
	 * @param CancellationPolicy
	 *     The CancellationPolicy
	 */
	@JsonProperty("CancellationPolicy")
	public void setCancellationPolicy(String CancellationPolicy) {
		this.CancellationPolicy = CancellationPolicy;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
