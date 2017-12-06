
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
    "ResponseStatus",
    "Error",
    "TraceId",
    "IsUnderCancellationAllowed",
    "IsPolicyPerStay",
    "HotelRoomsDetails",
    "RoomCombinations"
})
public class GetHotelRoomResult {

    @JsonProperty("ResponseStatus")
    private Integer ResponseStatus;
    @JsonProperty("Error")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.Error Error;
    @JsonProperty("TraceId")
    private String TraceId;
    @JsonProperty("IsUnderCancellationAllowed")
    private Boolean IsUnderCancellationAllowed;
    @JsonProperty("IsPolicyPerStay")
    private Boolean IsPolicyPerStay;
    @JsonProperty("HotelRoomsDetails")
    @Valid
    private List<HotelRoomsDetail> HotelRoomsDetails = new ArrayList<HotelRoomsDetail>();
    @JsonProperty("RoomCombinations")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.RoomCombinations RoomCombinations;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetHotelRoomResult() {
    }

    /**
     * 
     * @param RoomCombinations
     * @param HotelRoomsDetails
     * @param IsPolicyPerStay
     * @param ResponseStatus
     * @param TraceId
     * @param IsUnderCancellationAllowed
     * @param Error
     */
    public GetHotelRoomResult(Integer ResponseStatus, com.tayyarah.api.hotel.tbo.model.Error Error, String TraceId, Boolean IsUnderCancellationAllowed, Boolean IsPolicyPerStay, List<HotelRoomsDetail> HotelRoomsDetails, com.tayyarah.api.hotel.tbo.model.RoomCombinations RoomCombinations) {
        this.ResponseStatus = ResponseStatus;
        this.Error = Error;
        this.TraceId = TraceId;
        this.IsUnderCancellationAllowed = IsUnderCancellationAllowed;
        this.IsPolicyPerStay = IsPolicyPerStay;
        this.HotelRoomsDetails = HotelRoomsDetails;
        this.RoomCombinations = RoomCombinations;
    }

    /**
     * 
     * @return
     *     The ResponseStatus
     */
    @JsonProperty("ResponseStatus")
    public Integer getResponseStatus() {
        return ResponseStatus;
    }

    /**
     * 
     * @param ResponseStatus
     *     The ResponseStatus
     */
    @JsonProperty("ResponseStatus")
    public void setResponseStatus(Integer ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    /**
     * 
     * @return
     *     The Error
     */
    @JsonProperty("Error")
    public com.tayyarah.api.hotel.tbo.model.Error getError() {
        return Error;
    }

    /**
     * 
     * @param Error
     *     The Error
     */
    @JsonProperty("Error")
    public void setError(com.tayyarah.api.hotel.tbo.model.Error Error) {
        this.Error = Error;
    }

    /**
     * 
     * @return
     *     The TraceId
     */
    @JsonProperty("TraceId")
    public String getTraceId() {
        return TraceId;
    }

    /**
     * 
     * @param TraceId
     *     The TraceId
     */
    @JsonProperty("TraceId")
    public void setTraceId(String TraceId) {
        this.TraceId = TraceId;
    }

    /**
     * 
     * @return
     *     The IsUnderCancellationAllowed
     */
    @JsonProperty("IsUnderCancellationAllowed")
    public Boolean getIsUnderCancellationAllowed() {
        return IsUnderCancellationAllowed;
    }

    /**
     * 
     * @param IsUnderCancellationAllowed
     *     The IsUnderCancellationAllowed
     */
    @JsonProperty("IsUnderCancellationAllowed")
    public void setIsUnderCancellationAllowed(Boolean IsUnderCancellationAllowed) {
        this.IsUnderCancellationAllowed = IsUnderCancellationAllowed;
    }

    /**
     * 
     * @return
     *     The IsPolicyPerStay
     */
    @JsonProperty("IsPolicyPerStay")
    public Boolean getIsPolicyPerStay() {
        return IsPolicyPerStay;
    }

    /**
     * 
     * @param IsPolicyPerStay
     *     The IsPolicyPerStay
     */
    @JsonProperty("IsPolicyPerStay")
    public void setIsPolicyPerStay(Boolean IsPolicyPerStay) {
        this.IsPolicyPerStay = IsPolicyPerStay;
    }

    /**
     * 
     * @return
     *     The HotelRoomsDetails
     */
    @JsonProperty("HotelRoomsDetails")
    public List<HotelRoomsDetail> getHotelRoomsDetails() {
        return HotelRoomsDetails;
    }

    /**
     * 
     * @param HotelRoomsDetails
     *     The HotelRoomsDetails
     */
    @JsonProperty("HotelRoomsDetails")
    public void setHotelRoomsDetails(List<HotelRoomsDetail> HotelRoomsDetails) {
        this.HotelRoomsDetails = HotelRoomsDetails;
    }

    /**
     * 
     * @return
     *     The RoomCombinations
     */
    @JsonProperty("RoomCombinations")
    public com.tayyarah.api.hotel.tbo.model.RoomCombinations getRoomCombinations() {
        return RoomCombinations;
    }

    /**
     * 
     * @param RoomCombinations
     *     The RoomCombinations
     */
    @JsonProperty("RoomCombinations")
    public void setRoomCombinations(com.tayyarah.api.hotel.tbo.model.RoomCombinations RoomCombinations) {
        this.RoomCombinations = RoomCombinations;
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
