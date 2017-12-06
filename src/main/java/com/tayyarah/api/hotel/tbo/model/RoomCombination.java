
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
    "RoomIndex"
})
public class RoomCombination {

    @JsonProperty("RoomIndex")
    @Valid
    private List<Integer> RoomIndex = new ArrayList<Integer>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public RoomCombination() {
    }

    /**
     * 
     * @param RoomIndex
     */
    public RoomCombination(List<Integer> RoomIndex) {
        this.RoomIndex = RoomIndex;
    }

    /**
     * 
     * @return
     *     The RoomIndex
     */
    @JsonProperty("RoomIndex")
    public List<Integer> getRoomIndex() {
        return RoomIndex;
    }

    /**
     * 
     * @param RoomIndex
     *     The RoomIndex
     */
    @JsonProperty("RoomIndex")
    public void setRoomIndex(List<Integer> RoomIndex) {
        this.RoomIndex = RoomIndex;
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
