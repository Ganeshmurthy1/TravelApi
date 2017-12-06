
package com.tayyarah.api.hotel.tbo.block.model;

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

@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "RoomIndex",
    "RoomTypeCode",
    "RoomTypeName",
    "RatePlanCode",
    "BedTypeCode",
    "SmokingPreference",
    "Supplements",
    "Price",
    "HotelPassenger"
})
public class HotelRoomsDetail {

    @JsonProperty("RoomIndex")
    private Integer RoomIndex;
    @JsonProperty("RoomTypeCode")
    private String RoomTypeCode;
    @JsonProperty("RoomTypeName")
    private String RoomTypeName;
    @JsonProperty("RatePlanCode")
    private String RatePlanCode;
    @JsonProperty("BedTypeCode")
    private Object BedTypeCode;
    @JsonProperty("SmokingPreference")
    private String SmokingPreference;
    @JsonProperty("Supplements")
    private Object Supplements;
    @JsonProperty("Price")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.Price Price;
    @JsonProperty("HotelPassenger")
    @Valid
    private List<com.tayyarah.api.hotel.tbo.block.model.HotelPassenger> HotelPassenger = new ArrayList<com.tayyarah.api.hotel.tbo.block.model.HotelPassenger>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The BedTypeCode
     */
    @JsonProperty("BedTypeCode")
    public Object getBedTypeCode() {
        return BedTypeCode;
    }

    /**
     * 
     * @param BedTypeCode
     *     The BedTypeCode
     */
    @JsonProperty("BedTypeCode")
    public void setBedTypeCode(Object BedTypeCode) {
        this.BedTypeCode = BedTypeCode;
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
     *     The Supplements
     */
    @JsonProperty("Supplements")
    public Object getSupplements() {
        return Supplements;
    }

    /**
     * 
     * @param Supplements
     *     The Supplements
     */
    @JsonProperty("Supplements")
    public void setSupplements(Object Supplements) {
        this.Supplements = Supplements;
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
     *     The HotelPassenger
     */
    @JsonProperty("HotelPassenger")
    public List<com.tayyarah.api.hotel.tbo.block.model.HotelPassenger> getHotelPassenger() {
        return HotelPassenger;
    }

    /**
     * 
     * @param HotelPassenger
     *     The HotelPassenger
     */
    @JsonProperty("HotelPassenger")
    public void setHotelPassenger(List<com.tayyarah.api.hotel.tbo.block.model.HotelPassenger> HotelPassenger) {
        this.HotelPassenger = HotelPassenger;
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
