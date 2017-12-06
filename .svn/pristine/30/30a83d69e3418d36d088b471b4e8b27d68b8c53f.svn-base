
package com.tayyarah.hotel.model;

import java.io.Serializable;
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
	"ApiProvider",
    "InfoSource",
    "RoomCombination"
})
public class RoomCombinations implements Serializable{

	@JsonProperty("ApiProvider")
	private Integer apiProvider;
    public Integer getApiProvider() {
		return apiProvider;
	}

	public void setApiProvider(Integer apiProvider) {
		this.apiProvider = apiProvider;
	}

	@JsonProperty("InfoSource")
    private String InfoSource;
    @JsonProperty("RoomCombination")
    @Valid
    private List<RoomCombination> RoomCombination = new ArrayList<RoomCombination>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public RoomCombinations() {
    }

    /**
     * 
     * @param RoomCombination
     * @param InfoSource
     */
    public RoomCombinations(String InfoSource, List<RoomCombination> RoomCombination) {
        this.InfoSource = InfoSource;
        this.RoomCombination = RoomCombination;
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
     *     The RoomCombination
     */
    @JsonProperty("RoomCombination")
    public List<RoomCombination> getRoomCombination() {
        return RoomCombination;
    }

    /**
     * 
     * @param RoomCombination
     *     The RoomCombination
     */
    @JsonProperty("RoomCombination")
    public void setRoomCombination(List<RoomCombination> RoomCombination) {
        this.RoomCombination = RoomCombination;
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
