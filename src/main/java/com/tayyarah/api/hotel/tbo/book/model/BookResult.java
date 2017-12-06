
package com.tayyarah.api.hotel.tbo.book.model;

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
    "TraceId",
    "ResponseStatus",
    "Error",
    "Status",
    "HotelBookingStatus",
    "Description",
    "BookingRefNo",
    "BookingId",
    "ConfirmationNo",
    "IsPriceChanged",
    "IsCancellationPolicyChanged",
    "HotelRoomsDetails"
})
public class BookResult {

    @JsonProperty("TraceId")
    private String TraceId;
    @JsonProperty("ResponseStatus")
    private Integer ResponseStatus;
    @JsonProperty("Error")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.Error Error;
    @JsonProperty("Status")
    private Integer Status;
    @JsonProperty("HotelBookingStatus")
    private String HotelBookingStatus;
    @JsonProperty("Description")
    private String Description;
    @JsonProperty("BookingRefNo")
    private String BookingRefNo;
    @JsonProperty("BookingId")
    private String BookingId;
    public String getBookingId() {
		return BookingId;
	}

	public void setBookingId(String bookingId) {
		BookingId = bookingId;
	}

	@JsonProperty("ConfirmationNo")
    private String ConfirmationNo;
    @JsonProperty("IsPriceChanged")
    private Boolean IsPriceChanged;
    @JsonProperty("IsCancellationPolicyChanged")
    private Boolean IsCancellationPolicyChanged;
    @JsonProperty("HotelRoomsDetails")
    @Valid
    private List<com.tayyarah.api.hotel.tbo.model.HotelRoomsDetail> HotelRoomsDetails = new ArrayList<com.tayyarah.api.hotel.tbo.model.HotelRoomsDetail>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookResult() {
    }

    /**
     * 
     * @param BookingRefNo
     * @param Status
     * @param HotelRoomsDetails
     * @param Description
     * @param ConfirmationNo
     * @param ResponseStatus
     * @param IsCancellationPolicyChanged
     * @param TraceId
     * @param HotelBookingStatus
     * @param IsPriceChanged
     * @param Error
     */
    public BookResult(String TraceId, Integer ResponseStatus, com.tayyarah.api.hotel.tbo.model.Error Error, Integer Status, String HotelBookingStatus, String Description, String BookingRefNo, String ConfirmationNo, Boolean IsPriceChanged, Boolean IsCancellationPolicyChanged, List<com.tayyarah.api.hotel.tbo.model.HotelRoomsDetail> HotelRoomsDetails) {
        this.TraceId = TraceId;
        this.ResponseStatus = ResponseStatus;
        this.Error = Error;
        this.Status = Status;
        this.HotelBookingStatus = HotelBookingStatus;
        this.Description = Description;
        this.BookingRefNo = BookingRefNo;
        this.ConfirmationNo = ConfirmationNo;
        this.IsPriceChanged = IsPriceChanged;
        this.IsCancellationPolicyChanged = IsCancellationPolicyChanged;
        this.HotelRoomsDetails = HotelRoomsDetails;
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
     *     The Status
     */
    @JsonProperty("Status")
    public Integer getStatus() {
        return Status;
    }

    /**
     * 
     * @param Status
     *     The Status
     */
    @JsonProperty("Status")
    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    /**
     * 
     * @return
     *     The HotelBookingStatus
     */
    @JsonProperty("HotelBookingStatus")
    public String getHotelBookingStatus() {
        return HotelBookingStatus;
    }

    /**
     * 
     * @param HotelBookingStatus
     *     The HotelBookingStatus
     */
    @JsonProperty("HotelBookingStatus")
    public void setHotelBookingStatus(String HotelBookingStatus) {
        this.HotelBookingStatus = HotelBookingStatus;
    }

    /**
     * 
     * @return
     *     The Description
     */
    @JsonProperty("Description")
    public String getDescription() {
        return Description;
    }

    /**
     * 
     * @param Description
     *     The Description
     */
    @JsonProperty("Description")
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * 
     * @return
     *     The BookingRefNo
     */
    @JsonProperty("BookingRefNo")
    public String getBookingRefNo() {
        return BookingRefNo;
    }

    /**
     * 
     * @param BookingRefNo
     *     The BookingRefNo
     */
    @JsonProperty("BookingRefNo")
    public void setBookingRefNo(String BookingRefNo) {
        this.BookingRefNo = BookingRefNo;
    }

    /**
     * 
     * @return
     *     The ConfirmationNo
     */
    @JsonProperty("ConfirmationNo")
    public String getConfirmationNo() {
        return ConfirmationNo;
    }

    /**
     * 
     * @param ConfirmationNo
     *     The ConfirmationNo
     */
    @JsonProperty("ConfirmationNo")
    public void setConfirmationNo(String ConfirmationNo) {
        this.ConfirmationNo = ConfirmationNo;
    }

    /**
     * 
     * @return
     *     The IsPriceChanged
     */
    @JsonProperty("IsPriceChanged")
    public Boolean getIsPriceChanged() {
        return IsPriceChanged;
    }

    /**
     * 
     * @param IsPriceChanged
     *     The IsPriceChanged
     */
    @JsonProperty("IsPriceChanged")
    public void setIsPriceChanged(Boolean IsPriceChanged) {
        this.IsPriceChanged = IsPriceChanged;
    }

    /**
     * 
     * @return
     *     The IsCancellationPolicyChanged
     */
    @JsonProperty("IsCancellationPolicyChanged")
    public Boolean getIsCancellationPolicyChanged() {
        return IsCancellationPolicyChanged;
    }

    /**
     * 
     * @param IsCancellationPolicyChanged
     *     The IsCancellationPolicyChanged
     */
    @JsonProperty("IsCancellationPolicyChanged")
    public void setIsCancellationPolicyChanged(Boolean IsCancellationPolicyChanged) {
        this.IsCancellationPolicyChanged = IsCancellationPolicyChanged;
    }

    /**
     * 
     * @return
     *     The HotelRoomsDetails
     */
    @JsonProperty("HotelRoomsDetails")
    public List<com.tayyarah.api.hotel.tbo.model.HotelRoomsDetail> getHotelRoomsDetails() {
        return HotelRoomsDetails;
    }

    /**
     * 
     * @param HotelRoomsDetails
     *     The HotelRoomsDetails
     */
    @JsonProperty("HotelRoomsDetails")
    public void setHotelRoomsDetails(List<com.tayyarah.api.hotel.tbo.model.HotelRoomsDetail> HotelRoomsDetails) {
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
