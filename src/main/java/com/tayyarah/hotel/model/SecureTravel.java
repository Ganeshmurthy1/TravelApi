package com.tayyarah.hotel.model;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"securePolicies"
})
public class SecureTravel
implements Serializable
{

	/**
	 * @return the securePolicies
	 */
	public List<SecurePolicy> getSecurePolicies() {
		return securePolicies;
	}
	/**
	 * @param securePolicies the securePolicies to set
	 */
	public void setSecurePolicies(List<SecurePolicy> securePolicies) {
		this.securePolicies = securePolicies;
	}
	private final static long serialVersionUID = -1L;
	@XmlElement(name = "SecurePolicies", required = true)
	protected List<SecurePolicy> securePolicies;



}