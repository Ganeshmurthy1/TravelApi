
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "ResultIndex",
    "HotelCode",
    "HotelName",
    "GuestNationality",
    "NoOfRooms",
    "ClientReferenceNo",
    "IsVoucherBooking",
    "CategoryId",
    "EndUserIp",
    "TokenId",
    "TraceId",
    "HotelRoomsDetails"
})
public class HotelBlockRequest {

    @JsonProperty("ResultIndex")
    private Integer ResultIndex;
    @JsonProperty("HotelCode")
    private String HotelCode;
    @JsonProperty("HotelName")
    private String HotelName;
    @JsonProperty("GuestNationality")
    private String GuestNationality;
    @JsonProperty("NoOfRooms")
    private Integer NoOfRooms;
    @JsonProperty("ClientReferenceNo")
    private Integer ClientReferenceNo;
    @JsonProperty("IsVoucherBooking")
    private Boolean IsVoucherBooking;
    @JsonProperty("CategoryId")
    private String CategoryId;
    @JsonProperty("EndUserIp")
    private String EndUserIp;
    @JsonProperty("TokenId")
    private String TokenId;
    @JsonProperty("TraceId")
    private String TraceId;
    @JsonProperty("HotelRoomsDetails")
    @Valid
    private List<HotelRoomsDetail> HotelRoomsDetails = new ArrayList<HotelRoomsDetail>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The GuestNationality
     */
    @JsonProperty("GuestNationality")
    public String getGuestNationality() {
        return GuestNationality;
    }

    /**
     * 
     * @param GuestNationality
     *     The GuestNationality
     */
    @JsonProperty("GuestNationality")
    public void setGuestNationality(String GuestNationality) {
        this.GuestNationality = GuestNationality;
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
     *     The ClientReferenceNo
     */
    @JsonProperty("ClientReferenceNo")
    public Integer getClientReferenceNo() {
        return ClientReferenceNo;
    }

    /**
     * 
     * @param ClientReferenceNo
     *     The ClientReferenceNo
     */
    @JsonProperty("ClientReferenceNo")
    public void setClientReferenceNo(Integer ClientReferenceNo) {
        this.ClientReferenceNo = ClientReferenceNo;
    }

    /**
     * 
     * @return
     *     The IsVoucherBooking
     */
    @JsonProperty("IsVoucherBooking")
    public Boolean getIsVoucherBooking() {
        return IsVoucherBooking;
    }

    /**
     * 
     * @param IsVoucherBooking
     *     The IsVoucherBooking
     */
    @JsonProperty("IsVoucherBooking")
    public void setIsVoucherBooking(Boolean IsVoucherBooking) {
        this.IsVoucherBooking = IsVoucherBooking;
    }

    /**
     * 
     * @return
     *     The CategoryId
     */
    @JsonProperty("CategoryId")
    public String getCategoryId() {
        return CategoryId;
    }

    /**
     * 
     * @param CategoryId
     *     The CategoryId
     */
    @JsonProperty("CategoryId")
    public void setCategoryId(String CategoryId) {
        this.CategoryId = CategoryId;
    }

    /**
     * 
     * @return
     *     The EndUserIp
     */
    @JsonProperty("EndUserIp")
    public String getEndUserIp() {
        return EndUserIp;
    }

    /**
     * 
     * @param EndUserIp
     *     The EndUserIp
     */
    @JsonProperty("EndUserIp")
    public void setEndUserIp(String EndUserIp) {
        this.EndUserIp = EndUserIp;
    }

    /**
     * 
     * @return
     *     The TokenId
     */
    @JsonProperty("TokenId")
    public String getTokenId() {
        return TokenId;
    }

    /**
     * 
     * @param TokenId
     *     The TokenId
     */
    @JsonProperty("TokenId")
    public void setTokenId(String TokenId) {
        this.TokenId = TokenId;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
