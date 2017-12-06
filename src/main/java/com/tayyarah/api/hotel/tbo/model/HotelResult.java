
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
    "ResultIndex",
    "HotelCode",
    "HotelName",
    "HotelCategory",
    "StarRating",
    "HotelDescription",
    "HotelPromotion",
    "HotelPolicy",
    "Price",
    "HotelPicture",
    "HotelAddress",
    "HotelContactNo",
    "HotelMap",
    "Latitude",
    "Longitude",
    "HotelLocation",
    "SupplierPrice",
    "RoomDetails"
})
public class HotelResult {

    @JsonProperty("ResultIndex")
    private Integer ResultIndex;
    @JsonProperty("HotelCode")
    private String HotelCode;
    @JsonProperty("HotelName")
    private String HotelName;
    @JsonProperty("HotelCategory")
    private String HotelCategory;
    @JsonProperty("StarRating")
    private Integer StarRating;
    @JsonProperty("HotelDescription")
    private String HotelDescription;
    @JsonProperty("HotelPromotion")
    private String HotelPromotion;
    @JsonProperty("HotelPolicy")
    private String HotelPolicy;
    @JsonProperty("Price")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.Price Price;
    @JsonProperty("HotelPicture")
    private String HotelPicture;
    @JsonProperty("HotelAddress")
    private String HotelAddress;
    @JsonProperty("HotelContactNo")
    private String HotelContactNo;
    @JsonProperty("HotelMap")
    private String HotelMap;
    @JsonProperty("Latitude")
    private String Latitude;
    @JsonProperty("Longitude")
    private String Longitude;
    @JsonProperty("HotelLocation")
    private String HotelLocation;
    @JsonProperty("SupplierPrice")
    private Double SupplierPrice;
    @JsonProperty("RoomDetails")
    @Valid
    private List<Object> RoomDetails = new ArrayList<Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public HotelResult() {
    }

    /**
     * 
     * @param HotelName
     * @param ResultIndex
     * @param HotelDescription
     * @param SupplierPrice
     * @param HotelContactNo
     * @param StarRating
     * @param HotelPromotion
     * @param HotelCode
     * @param RoomDetails
     * @param Price
     * @param Latitude
     * @param Longitude
     * @param HotelMap
     * @param HotelCategory
     * @param HotelPolicy
     * @param HotelPicture
     * @param HotelAddress
     * @param HotelLocation
     */
    public HotelResult(Integer ResultIndex, String HotelCode, String HotelName, String HotelCategory, Integer StarRating, String HotelDescription, String HotelPromotion, String HotelPolicy, com.tayyarah.api.hotel.tbo.model.Price Price, String HotelPicture, String HotelAddress, String HotelContactNo, String HotelMap, String Latitude, String Longitude, String HotelLocation, Double SupplierPrice, List<Object> RoomDetails) {
        this.ResultIndex = ResultIndex;
        this.HotelCode = HotelCode;
        this.HotelName = HotelName;
        this.HotelCategory = HotelCategory;
        this.StarRating = StarRating;
        this.HotelDescription = HotelDescription;
        this.HotelPromotion = HotelPromotion;
        this.HotelPolicy = HotelPolicy;
        this.Price = Price;
        this.HotelPicture = HotelPicture;
        this.HotelAddress = HotelAddress;
        this.HotelContactNo = HotelContactNo;
        this.HotelMap = HotelMap;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.HotelLocation = HotelLocation;
        this.SupplierPrice = SupplierPrice;
        this.RoomDetails = RoomDetails;
    }

    /**
     * 
     * @return
     *     The ResultIndex
     */
    @JsonProperty("ResultIndex")
    public Integer getResultIndex() {
        return ResultIndex;
    }

    /**
     * 
     * @param ResultIndex
     *     The ResultIndex
     */
    @JsonProperty("ResultIndex")
    public void setResultIndex(Integer ResultIndex) {
        this.ResultIndex = ResultIndex;
    }

    /**
     * 
     * @return
     *     The HotelCode
     */
    @JsonProperty("HotelCode")
    public String getHotelCode() {
        return HotelCode;
    }

    /**
     * 
     * @param HotelCode
     *     The HotelCode
     */
    @JsonProperty("HotelCode")
    public void setHotelCode(String HotelCode) {
        this.HotelCode = HotelCode;
    }

    /**
     * 
     * @return
     *     The HotelName
     */
    @JsonProperty("HotelName")
    public String getHotelName() {
        return HotelName;
    }

    /**
     * 
     * @param HotelName
     *     The HotelName
     */
    @JsonProperty("HotelName")
    public void setHotelName(String HotelName) {
        this.HotelName = HotelName;
    }

    /**
     * 
     * @return
     *     The HotelCategory
     */
    @JsonProperty("HotelCategory")
    public String getHotelCategory() {
        return HotelCategory;
    }

    /**
     * 
     * @param HotelCategory
     *     The HotelCategory
     */
    @JsonProperty("HotelCategory")
    public void setHotelCategory(String HotelCategory) {
        this.HotelCategory = HotelCategory;
    }

    /**
     * 
     * @return
     *     The StarRating
     */
    @JsonProperty("StarRating")
    public Integer getStarRating() {
        return StarRating;
    }

    /**
     * 
     * @param StarRating
     *     The StarRating
     */
    @JsonProperty("StarRating")
    public void setStarRating(Integer StarRating) {
        this.StarRating = StarRating;
    }

    /**
     * 
     * @return
     *     The HotelDescription
     */
    @JsonProperty("HotelDescription")
    public String getHotelDescription() {
        return HotelDescription;
    }

    /**
     * 
     * @param HotelDescription
     *     The HotelDescription
     */
    @JsonProperty("HotelDescription")
    public void setHotelDescription(String HotelDescription) {
        this.HotelDescription = HotelDescription;
    }

    /**
     * 
     * @return
     *     The HotelPromotion
     */
    @JsonProperty("HotelPromotion")
    public String getHotelPromotion() {
        return HotelPromotion;
    }

    /**
     * 
     * @param HotelPromotion
     *     The HotelPromotion
     */
    @JsonProperty("HotelPromotion")
    public void setHotelPromotion(String HotelPromotion) {
        this.HotelPromotion = HotelPromotion;
    }

    /**
     * 
     * @return
     *     The HotelPolicy
     */
    @JsonProperty("HotelPolicy")
    public String getHotelPolicy() {
        return HotelPolicy;
    }

    /**
     * 
     * @param HotelPolicy
     *     The HotelPolicy
     */
    @JsonProperty("HotelPolicy")
    public void setHotelPolicy(String HotelPolicy) {
        this.HotelPolicy = HotelPolicy;
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
     *     The HotelPicture
     */
    @JsonProperty("HotelPicture")
    public String getHotelPicture() {
        return HotelPicture;
    }

    /**
     * 
     * @param HotelPicture
     *     The HotelPicture
     */
    @JsonProperty("HotelPicture")
    public void setHotelPicture(String HotelPicture) {
        this.HotelPicture = HotelPicture;
    }

    /**
     * 
     * @return
     *     The HotelAddress
     */
    @JsonProperty("HotelAddress")
    public String getHotelAddress() {
        return HotelAddress;
    }

    /**
     * 
     * @param HotelAddress
     *     The HotelAddress
     */
    @JsonProperty("HotelAddress")
    public void setHotelAddress(String HotelAddress) {
        this.HotelAddress = HotelAddress;
    }

    /**
     * 
     * @return
     *     The HotelContactNo
     */
    @JsonProperty("HotelContactNo")
    public String getHotelContactNo() {
        return HotelContactNo;
    }

    /**
     * 
     * @param HotelContactNo
     *     The HotelContactNo
     */
    @JsonProperty("HotelContactNo")
    public void setHotelContactNo(String HotelContactNo) {
        this.HotelContactNo = HotelContactNo;
    }

    /**
     * 
     * @return
     *     The HotelMap
     */
    @JsonProperty("HotelMap")
    public String getHotelMap() {
        return HotelMap;
    }

    /**
     * 
     * @param HotelMap
     *     The HotelMap
     */
    @JsonProperty("HotelMap")
    public void setHotelMap(String HotelMap) {
        this.HotelMap = HotelMap;
    }

    /**
     * 
     * @return
     *     The Latitude
     */
    @JsonProperty("Latitude")
    public String getLatitude() {
        return Latitude;
    }

    /**
     * 
     * @param Latitude
     *     The Latitude
     */
    @JsonProperty("Latitude")
    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    /**
     * 
     * @return
     *     The Longitude
     */
    @JsonProperty("Longitude")
    public String getLongitude() {
        return Longitude;
    }

    /**
     * 
     * @param Longitude
     *     The Longitude
     */
    @JsonProperty("Longitude")
    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    /**
     * 
     * @return
     *     The HotelLocation
     */
    @JsonProperty("HotelLocation")
    public String getHotelLocation() {
        return HotelLocation;
    }

    /**
     * 
     * @param HotelLocation
     *     The HotelLocation
     */
    @JsonProperty("HotelLocation")
    public void setHotelLocation(String HotelLocation) {
        this.HotelLocation = HotelLocation;
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
     *     The RoomDetails
     */
    @JsonProperty("RoomDetails")
    public List<Object> getRoomDetails() {
        return RoomDetails;
    }

    /**
     * 
     * @param RoomDetails
     *     The RoomDetails
     */
    @JsonProperty("RoomDetails")
    public void setRoomDetails(List<Object> RoomDetails) {
        this.RoomDetails = RoomDetails;
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
