
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
    "NoOfAdults",
    "NoOfChild",
    "ChildAge"
})
public class RoomGuest {

    @JsonProperty("NoOfAdults")
    private Integer NoOfAdults;
    @JsonProperty("NoOfChild")
    private Integer NoOfChild;
    @JsonProperty("ChildAge")
    @Valid
    private List<Integer> ChildAge = new ArrayList<Integer>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public RoomGuest() {
    }

    /**
     * 
     * @param NoOfChild
     * @param NoOfAdults
     * @param ChildAge
     */
    public RoomGuest(Integer NoOfAdults, Integer NoOfChild, List<Integer> ChildAge) {
        this.NoOfAdults = NoOfAdults;
        this.NoOfChild = NoOfChild;
        this.ChildAge = ChildAge;
    }

    /**
     * 
     * @return
     *     The NoOfAdults
     */
    @JsonProperty("NoOfAdults")
    public Integer getNoOfAdults() {
        return NoOfAdults;
    }

    /**
     * 
     * @param NoOfAdults
     *     The NoOfAdults
     */
    @JsonProperty("NoOfAdults")
    public void setNoOfAdults(Integer NoOfAdults) {
        this.NoOfAdults = NoOfAdults;
    }

    /**
     * 
     * @return
     *     The NoOfChild
     */
    @JsonProperty("NoOfChild")
    public Integer getNoOfChild() {
        return NoOfChild;
    }

    /**
     * 
     * @param NoOfChild
     *     The NoOfChild
     */
    @JsonProperty("NoOfChild")
    public void setNoOfChild(Integer NoOfChild) {
        this.NoOfChild = NoOfChild;
    }

    /**
     * 
     * @return
     *     The ChildAge
     */
    @JsonProperty("ChildAge")
    public List<Integer> getChildAge() {
        return ChildAge;
    }

    /**
     * 
     * @param ChildAge
     *     The ChildAge
     */
    @JsonProperty("ChildAge")
    public void setChildAge(List<Integer> ChildAge) {
        this.ChildAge = ChildAge;
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
