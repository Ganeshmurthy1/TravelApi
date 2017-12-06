package com.tayyarah.common.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "state_info")
public class StateInfo implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	
	@Id 
	@GeneratedValue
	private	Long id;
	@Column(name="state_name" )
	private String stateName;
	@Column(name="state_code")
	private String stateCode;
	@Column(name="is_Union_Territory")
	private boolean isUnionTerritory;	
	
	public boolean isUnionTerritory() {
		return isUnionTerritory;
	}
	public void setUnionTerritory(boolean isUnionTerritory) {
		this.isUnionTerritory = isUnionTerritory;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStateName() {
		return stateName;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
}