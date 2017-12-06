//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.07 at 05:57:04 PM IST 
//


package com.tayyarah.api.hotel.travelguru.book.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CancelInfoRSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CancelInfoRSType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CancelRules" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CancelRule" type="{http://www.opentravel.org/OTA/2003/05}CancelRuleType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelInfoRSType", propOrder = {
    "cancelRules",
    "uniqueID"
})
public class CancelInfoRSType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "CancelRules")
    protected CancelInfoRSType.CancelRules cancelRules;
    @XmlElement(name = "UniqueID")
    protected UniqueIDType uniqueID;

    /**
     * Gets the value of the cancelRules property.
     * 
     * @return
     *     possible object is
     *     {@link CancelInfoRSType.CancelRules }
     *     
     */
    public CancelInfoRSType.CancelRules getCancelRules() {
        return cancelRules;
    }

    /**
     * Sets the value of the cancelRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link CancelInfoRSType.CancelRules }
     *     
     */
    public void setCancelRules(CancelInfoRSType.CancelRules value) {
        this.cancelRules = value;
    }

    /**
     * Gets the value of the uniqueID property.
     * 
     * @return
     *     possible object is
     *     {@link UniqueIDType }
     *     
     */
    public UniqueIDType getUniqueID() {
        return uniqueID;
    }

    /**
     * Sets the value of the uniqueID property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniqueIDType }
     *     
     */
    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="CancelRule" type="{http://www.opentravel.org/OTA/2003/05}CancelRuleType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "cancelRules"
    })
    public static class CancelRules
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "CancelRule", required = true)
        protected List<CancelRuleType> cancelRules;

        /**
         * Gets the value of the cancelRules property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cancelRules property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCancelRules().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CancelRuleType }
         * 
         * 
         */
        public List<CancelRuleType> getCancelRules() {
            if (cancelRules == null) {
                cancelRules = new ArrayList<CancelRuleType>();
            }
            return this.cancelRules;
        }

    }

}
