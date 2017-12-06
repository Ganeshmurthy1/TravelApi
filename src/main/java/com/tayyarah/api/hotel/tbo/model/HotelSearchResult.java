
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
    "CityId",
    "CheckInDate",
    "CheckOutDate",
    "PreferredCurrency",
    "NoOfRooms",
    "RoomGuests",
    "HotelResults"
})
public class HotelSearchResult {

    @JsonProperty("ResponseStatus")
    private Integer ResponseStatus;
    @JsonProperty("Error")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.Error Error;
    @JsonProperty("TraceId")
    private String TraceId;
    @JsonProperty("CityId")
    private String CityId;
    @JsonProperty("CheckInDate")
    private String CheckInDate;
    @JsonProperty("CheckOutDate")
    private String CheckOutDate;
    @JsonProperty("PreferredCurrency")
    private String PreferredCurrency;
    @JsonProperty("NoOfRooms")
    private Integer NoOfRooms;
    @JsonProperty("RoomGuests")
    @Valid
    private List<RoomGuest> RoomGuests = new ArrayList<RoomGuest>();
    @JsonProperty("HotelResults")
    @Valid
    private List<HotelResult> HotelResults = new ArrayList<HotelResult>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public HotelSearchResult() {
    }

    /**
     * 
     * @param HotelResults
     * @param NoOfRooms
     * @param PreferredCurrency
     * @param CityId
     * @param CheckInDate
     * @param RoomGuests
     * @param ResponseStatus
     * @param CheckOutDate
     * @param TraceId
     * @param Error
     */
    public HotelSearchResult(Integer ResponseStatus, com.tayyarah.api.hotel.tbo.model.Error Error, String TraceId, String CityId, String CheckInDate, String CheckOutDate, String PreferredCurrency, Integer NoOfRooms, List<RoomGuest> RoomGuests, List<HotelResult> HotelResults) {
        this.ResponseStatus = ResponseStatus;
        this.Error = Error;
        this.TraceId = TraceId;
        this.CityId = CityId;
        this.CheckInDate = CheckInDate;
        this.CheckOutDate = CheckOutDate;
        this.PreferredCurrency = PreferredCurrency;
        this.NoOfRooms = NoOfRooms;
        this.RoomGuests = RoomGuests;
        this.HotelResults = HotelResults;
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
     *     The CityId
     */
    @JsonProperty("CityId")
    public String getCityId() {
        return CityId;
    }

    /**
     * 
     * @param CityId
     *     The CityId
     */
    @JsonProperty("CityId")
    public void setCityId(String CityId) {
        this.CityId = CityId;
    }

    /**
     * 
     * @return
     *     The CheckInDate
     */
    @JsonProperty("CheckInDate")
    public String getCheckInDate() {
        return CheckInDate;
    }

    /**
     * 
     * @param CheckInDate
     *     The CheckInDate
     */
    @JsonProperty("CheckInDate")
    public void setCheckInDate(String CheckInDate) {
        this.CheckInDate = CheckInDate;
    }

    /**
     * 
     * @return
     *     The CheckOutDate
     */
    @JsonProperty("CheckOutDate")
    public String getCheckOutDate() {
        return CheckOutDate;
    }

    /**
     * 
     * @param CheckOutDate
     *     The CheckOutDate
     */
    @JsonProperty("CheckOutDate")
    public void setCheckOutDate(String CheckOutDate) {
        this.CheckOutDate = CheckOutDate;
    }

    /**
     * 
     * @return
     *     The PreferredCurrency
     */
    @JsonProperty("PreferredCurrency")
    public String getPreferredCurrency() {
        return PreferredCurrency;
    }

    /**
     * 
     * @param PreferredCurrency
     *     The PreferredCurrency
     */
    @JsonProperty("PreferredCurrency")
    public void setPreferredCurrency(String PreferredCurrency) {
        this.PreferredCurrency = PreferredCurrency;
    }

    /**
     * 
     * @return
     *     The NoOfRooms
     */
    @JsonProperty("NoOfRooms")
    public Integer getNoOfRooms() {
        return NoOfRooms;
    }

    /**
     * 
     * @param NoOfRooms
     *     The NoOfRooms
     */
    @JsonProperty("NoOfRooms")
    public void setNoOfRooms(Integer NoOfRooms) {
        this.NoOfRooms = NoOfRooms;
    }

    /**
     * 
     * @return
     *     The RoomGuests
     */
    @JsonProperty("RoomGuests")
    public List<RoomGuest> getRoomGuests() {
        return RoomGuests;
    }

    /**
     * 
     * @param RoomGuests
     *     The RoomGuests
     */
    @JsonProperty("RoomGuests")
    public void setRoomGuests(List<RoomGuest> RoomGuests) {
        this.RoomGuests = RoomGuests;
    }

    /**
     * 
     * @return
     *     The HotelResults
     */
    @JsonProperty("HotelResults")
    public List<HotelResult> getHotelResults() {
        return HotelResults;
    }

    /**
     * 
     * @param HotelResults
     *     The HotelResults
     */
    @JsonProperty("HotelResults")
    public void setHotelResults(List<HotelResult> HotelResults) {
        this.HotelResults = HotelResults;
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
