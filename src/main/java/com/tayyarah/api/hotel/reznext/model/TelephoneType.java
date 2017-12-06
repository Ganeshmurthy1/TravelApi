//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.25 at 11:12:45 AM IST 
//


package com.tayyarah.api.hotel.reznext.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for TelephoneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TelephoneType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="PhoneLocationType" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="PhoneTechType" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="PhoneNumber" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="FormattedInd" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelephoneType", propOrder = {
    "value"
})
public class TelephoneType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlValue
    protected String value;
    @XmlAttribute(name = "PhoneLocationType")
    protected Byte phoneLocationType;
    @XmlAttribute(name = "PhoneTechType")
    protected Byte phoneTechType;
    @XmlAttribute(name = "PhoneNumber")
    protected Long phoneNumber;
    @XmlAttribute(name = "FormattedInd")
    protected Byte formattedInd;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the phoneLocationType property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getPhoneLocationType() {
        return phoneLocationType;
    }

    /**
     * Sets the value of the phoneLocationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setPhoneLocationType(Byte value) {
        this.phoneLocationType = value;
    }

    /**
     * Gets the value of the phoneTechType property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getPhoneTechType() {
        return phoneTechType;
    }

    /**
     * Sets the value of the phoneTechType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setPhoneTechType(Byte value) {
        this.phoneTechType = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPhoneNumber(Long value) {
        this.phoneNumber = value;
    }

    /**
     * Gets the value of the formattedInd property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getFormattedInd() {
        return formattedInd;
    }

    /**
     * Sets the value of the formattedInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setFormattedInd(Byte value) {
        this.formattedInd = value;
    }

}
