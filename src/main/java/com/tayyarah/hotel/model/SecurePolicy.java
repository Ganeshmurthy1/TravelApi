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
		"policyProvider",
		"policyNo",
		"policyDescription",
		"policyBenifits"
})
public class SecurePolicy
implements Serializable
{

	private final static long serialVersionUID = -1L;
	@XmlElement(name = "PolicyProvider", required = true)
	protected String policyProvider;
	@XmlElement(name = "PolicyNo")
	protected int policyNo;
	@XmlElement(name = "PolicyDescription", required = true)
	protected String policyDescription;
	@XmlElement(name = "PolicyBenifits", required = true)
	protected String policyBenifits;

	/**
	 * Gets the value of the policyProvider property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getPolicyProvider() {
		return policyProvider;
	}

	/**
	 * Sets the value of the policyProvider property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setPolicyProvider(String value) {
		this.policyProvider = value;
	}

	/**
	 * Gets the value of the policyNo property.
	 * 
	 */
	public int getPolicyNo() {
		return policyNo;
	}

	/**
	 * Sets the value of the policyNo property.
	 * 
	 */
	public void setPolicyNo(int value) {
		this.policyNo = value;
	}

	/**
	 * Gets the value of the policyDescription property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getPolicyDescription() {
		return policyDescription;
	}

	/**
	 * Sets the value of the policyDescription property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setPolicyDescription(String value) {
		this.policyDescription = value;
	}

	/**
	 * Gets the value of the policyBenifits property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getPolicyBenifits() {
		return policyBenifits;
	}

	/**
	 * Sets the value of the policyBenifits property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setPolicyBenifits(String value) {
		this.policyBenifits = value;
	}

}