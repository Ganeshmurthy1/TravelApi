
package com.tayyarah.hotel.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "CurrencyCode",
    "RoomPrice",
    "Tax",
    "ExtraGuestCharge",
    "ChildCharge",
    "OtherCharges",
    "Discount",
    "PublishedPrice",
    "PublishedPriceRoundedOff",
    "OfferedPrice",
    "OfferedPriceRoundedOff",
    "AgentCommission",
    "AgentMarkUp",
    "ServiceTax",
    "TDS"
})
public class Price {

    @JsonProperty("CurrencyCode")
    private String CurrencyCode;
    @JsonProperty("RoomPrice")
    private BigDecimal RoomPrice;
    @JsonProperty("Tax")
    private BigDecimal Tax;
    @JsonProperty("ExtraGuestCharge")
    private BigDecimal ExtraGuestCharge;
    @JsonProperty("ChildCharge")
    private BigDecimal ChildCharge;
    @JsonProperty("OtherCharges")
    private BigDecimal OtherCharges;
    @JsonProperty("Discount")
    private BigDecimal Discount;
    @JsonProperty("PublishedPrice")
    private BigDecimal PublishedPrice;
    @JsonProperty("PublishedPriceRoundedOff")
    private BigDecimal PublishedPriceRoundedOff;
    @JsonProperty("OfferedPrice")
    private BigDecimal OfferedPrice;
    @JsonProperty("OfferedPriceRoundedOff")
    private BigDecimal OfferedPriceRoundedOff;
    @JsonProperty("AgentCommission")
    private BigDecimal AgentCommission;
    @JsonProperty("AgentMarkUp")
    private BigDecimal AgentMarkUp;
    @JsonProperty("ServiceTax")
    private BigDecimal ServiceTax;
    @JsonProperty("TDS")
    private BigDecimal TDS;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Price() {
    }

   

    /**
     * 
     * @return
     *     The CurrencyCode
     */
    @JsonProperty("CurrencyCode")
    public String getCurrencyCode() {
        return CurrencyCode;
    }

    /**
     * 
     * @param CurrencyCode
     *     The CurrencyCode
     */
    @JsonProperty("CurrencyCode")
    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    /**
     * 
     * @return
     *     The RoomPrice
     */
    @JsonProperty("RoomPrice")
    public BigDecimal getRoomPrice() {
        return RoomPrice;
    }

    /**
     * 
     * @param RoomPrice
     *     The RoomPrice
     */
    @JsonProperty("RoomPrice")
    public void setRoomPrice(BigDecimal RoomPrice) {
        this.RoomPrice = RoomPrice;
    }

    /**
     * 
     * @return
     *     The Tax
     */
    @JsonProperty("Tax")
    public BigDecimal getTax() {
        return Tax;
    }

    /**
     * 
     * @param Tax
     *     The Tax
     */
    @JsonProperty("Tax")
    public void setTax(BigDecimal Tax) {
        this.Tax = Tax;
    }

    /**
     * 
     * @return
     *     The ExtraGuestCharge
     */
    @JsonProperty("ExtraGuestCharge")
    public BigDecimal getExtraGuestCharge() {
        return ExtraGuestCharge;
    }

    /**
     * 
     * @param ExtraGuestCharge
     *     The ExtraGuestCharge
     */
    @JsonProperty("ExtraGuestCharge")
    public void setExtraGuestCharge(BigDecimal ExtraGuestCharge) {
        this.ExtraGuestCharge = ExtraGuestCharge;
    }

    /**
     * 
     * @return
     *     The ChildCharge
     */
    @JsonProperty("ChildCharge")
    public BigDecimal getChildCharge() {
        return ChildCharge;
    }

    /**
     * 
     * @param ChildCharge
     *     The ChildCharge
     */
    @JsonProperty("ChildCharge")
    public void setChildCharge(BigDecimal ChildCharge) {
        this.ChildCharge = ChildCharge;
    }

    /**
     * 
     * @return
     *     The OtherCharges
     */
    @JsonProperty("OtherCharges")
    public BigDecimal getOtherCharges() {
        return OtherCharges;
    }

    /**
     * 
     * @param OtherCharges
     *     The OtherCharges
     */
    @JsonProperty("OtherCharges")
    public void setOtherCharges(BigDecimal OtherCharges) {
        this.OtherCharges = OtherCharges;
    }

    /**
     * 
     * @return
     *     The Discount
     */
    @JsonProperty("Discount")
    public BigDecimal getDiscount() {
        return Discount;
    }

    /**
     * 
     * @param Discount
     *     The Discount
     */
    @JsonProperty("Discount")
    public void setDiscount(BigDecimal Discount) {
        this.Discount = Discount;
    }

    /**
     * 
     * @return
     *     The PublishedPrice
     */
    @JsonProperty("PublishedPrice")
    public BigDecimal getPublishedPrice() {
        return PublishedPrice;
    }

    /**
     * 
     * @param PublishedPrice
     *     The PublishedPrice
     */
    @JsonProperty("PublishedPrice")
    public void setPublishedPrice(BigDecimal PublishedPrice) {
        this.PublishedPrice = PublishedPrice;
    }

    /**
     * 
     * @return
     *     The PublishedPriceRoundedOff
     */
    @JsonProperty("PublishedPriceRoundedOff")
    public BigDecimal getPublishedPriceRoundedOff() {
        return PublishedPriceRoundedOff;
    }

    /**
     * 
     * @param PublishedPriceRoundedOff
     *     The PublishedPriceRoundedOff
     */
    @JsonProperty("PublishedPriceRoundedOff")
    public void setPublishedPriceRoundedOff(BigDecimal PublishedPriceRoundedOff) {
        this.PublishedPriceRoundedOff = PublishedPriceRoundedOff;
    }

    /**
     * 
     * @return
     *     The OfferedPrice
     */
    @JsonProperty("OfferedPrice")
    public BigDecimal getOfferedPrice() {
        return OfferedPrice;
    }

    /**
     * 
     * @param OfferedPrice
     *     The OfferedPrice
     */
    @JsonProperty("OfferedPrice")
    public void setOfferedPrice(BigDecimal OfferedPrice) {
        this.OfferedPrice = OfferedPrice;
    }

    /**
     * 
     * @return
     *     The OfferedPriceRoundedOff
     */
    @JsonProperty("OfferedPriceRoundedOff")
    public BigDecimal getOfferedPriceRoundedOff() {
        return OfferedPriceRoundedOff;
    }

    /**
     * 
     * @param OfferedPriceRoundedOff
     *     The OfferedPriceRoundedOff
     */
    @JsonProperty("OfferedPriceRoundedOff")
    public void setOfferedPriceRoundedOff(BigDecimal OfferedPriceRoundedOff) {
        this.OfferedPriceRoundedOff = OfferedPriceRoundedOff;
    }

    /**
     * 
     * @return
     *     The AgentCommission
     */
    @JsonProperty("AgentCommission")
    public BigDecimal getAgentCommission() {
        return AgentCommission;
    }

    /**
     * 
     * @param AgentCommission
     *     The AgentCommission
     */
    @JsonProperty("AgentCommission")
    public void setAgentCommission(BigDecimal AgentCommission) {
        this.AgentCommission = AgentCommission;
    }

    /**
     * 
     * @return
     *     The AgentMarkUp
     */
    @JsonProperty("AgentMarkUp")
    public BigDecimal getAgentMarkUp() {
        return AgentMarkUp;
    }

    /**
     * 
     * @param AgentMarkUp
     *     The AgentMarkUp
     */
    @JsonProperty("AgentMarkUp")
    public void setAgentMarkUp(BigDecimal AgentMarkUp) {
        this.AgentMarkUp = AgentMarkUp;
    }

    /**
     * 
     * @return
     *     The ServiceTax
     */
    @JsonProperty("ServiceTax")
    public BigDecimal getServiceTax() {
        return ServiceTax;
    }

    /**
     * 
     * @param ServiceTax
     *     The ServiceTax
     */
    @JsonProperty("ServiceTax")
    public void setServiceTax(BigDecimal ServiceTax) {
        this.ServiceTax = ServiceTax;
    }

    /**
     * 
     * @return
     *     The TDS
     */
    @JsonProperty("TDS")
    public BigDecimal getTDS() {
        return TDS;
    }

    /**
     * 
     * @param TDS
     *     The TDS
     */
    @JsonProperty("TDS")
    public void setTDS(BigDecimal TDS) {
        this.TDS = TDS;
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
