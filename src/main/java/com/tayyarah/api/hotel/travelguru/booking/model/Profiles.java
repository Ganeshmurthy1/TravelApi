//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.01 at 02:39:45 PM IST 
//


package com.tayyarah.api.hotel.travelguru.booking.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Profiles complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Profiles">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProfileInfo" type="{http://www.opentravel.org/OTA/2003/05}ProfileInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Profiles", propOrder = {
    "profileInfo"
})
public class Profiles
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ProfileInfo", required = true)
    protected ProfileInfo profileInfo;

    /**
     * Gets the value of the profileInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileInfo }
     *     
     */
    public ProfileInfo getProfileInfo() {
        return profileInfo;
    }

    /**
     * Sets the value of the profileInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileInfo }
     *     
     */
    public void setProfileInfo(ProfileInfo value) {
        this.profileInfo = value;
    }

}