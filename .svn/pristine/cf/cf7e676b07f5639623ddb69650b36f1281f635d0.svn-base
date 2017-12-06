package com.tayyarah.hotel.model;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"secureTravel",
		"agencyInfo"
})

public class AdditionalAgencyInfo
implements Serializable
{

	/**
	 * @return the secureTravel
	 */
	public SecureTravel getSecureTravel() {
		return secureTravel;
	}
	/**
	 * @param secureTravel the secureTravel to set
	 */
	public void setSecureTravel(SecureTravel secureTravel) {
		this.secureTravel = secureTravel;
	}
	/**
	 * @return the agencyInfo
	 */
	public AgencyInfo getAgencyInfo() {
		return agencyInfo;
	}
	/**
	 * @param agencyInfo the agencyInfo to set
	 */
	public void setAgencyInfo(AgencyInfo agencyInfo) {
		this.agencyInfo = agencyInfo;
	}
	private final static long serialVersionUID = -1L;
	@XmlElement(name = "SecureTravel", required = true)
	protected SecureTravel secureTravel;
	@XmlElement(name = "AgencyInfo", required = true)
	protected AgencyInfo agencyInfo;

}
