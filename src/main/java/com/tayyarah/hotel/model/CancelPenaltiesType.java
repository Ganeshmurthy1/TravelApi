//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.09 at 04:51:51 PM IST 
//


package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * <p>Java class for CancelPenaltiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CancelPenaltiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CancelPenalty" type="{http://www.opentravel.org/OTA/2003/05}CancelPenaltyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="CancelPolicyIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelPenaltiesType", propOrder = {
    "cancelPenalties"
})
public class CancelPenaltiesType
    implements Serializable
{

    public CancelPenaltiesType() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the cancelPolicyIndicator
	 */
	public Boolean getCancelPolicyIndicator() {
		return cancelPolicyIndicator;
	}

	/**
	 * @param cancelPenalties the cancelPenalties to set
	 */
	public void setCancelPenalties(List<CancelPenaltyType> cancelPenalties) {
		this.cancelPenalties = cancelPenalties;
	}

	private final static long serialVersionUID = -1L;
    @XmlElement(name = "CancelPenalty")
    protected List<CancelPenaltyType> cancelPenalties;
    @XmlAttribute(name = "CancelPolicyIndicator")
    protected Boolean cancelPolicyIndicator;

    /**
     * Gets the value of the cancelPenalties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cancelPenalties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCancelPenalties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CancelPenaltyType }
     * 
     * 
     */
    public List<CancelPenaltyType> getCancelPenalties() {
        if (cancelPenalties == null) {
            cancelPenalties = new ArrayList<CancelPenaltyType>();
        }
        return this.cancelPenalties;
    }

    /**
     * Gets the value of the cancelPolicyIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCancelPolicyIndicator() {
        return cancelPolicyIndicator;
    }

    /**
     * Sets the value of the cancelPolicyIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCancelPolicyIndicator(Boolean value) {
        this.cancelPolicyIndicator = value;
    }

}
