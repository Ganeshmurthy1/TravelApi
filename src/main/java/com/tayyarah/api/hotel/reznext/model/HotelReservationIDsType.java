//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.24 at 06:17:09 PM IST 
//


package com.tayyarah.api.hotel.reznext.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;




/**
 * <p>Java class for HotelReservationIDsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HotelReservationIDsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HotelReservationID" type="{}HotelReservationIDType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelReservationIDsType", propOrder = {
    "hotelReservationID",
    "hotelReservationIDs"
})
public class HotelReservationIDsType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "HotelReservationID", required = true)
    protected HotelReservationIDType hotelReservationID;
    @XmlElement(name = "HotelReservationID")
    protected List<HotelReservationIDType> hotelReservationIDs;

    public void setHotelReservationIDs(
			List<HotelReservationIDType> hotelReservationIDs) {
		this.hotelReservationIDs = hotelReservationIDs;
	}

	/**
     * Gets the value of the hotelReservationID property.
     * 
     * @return
     *     possible object is
     *     {@link HotelReservationIDType }
     *     
     */
    public HotelReservationIDType getHotelReservationID() {
        return hotelReservationID;
    }

    /**
     * Sets the value of the hotelReservationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelReservationIDType }
     *     
     */
    public void setHotelReservationID(HotelReservationIDType value) {
        this.hotelReservationID = value;
    }
    public List<HotelReservationIDType> getHotelReservationIDs() {
        if (hotelReservationIDs == null) {
            hotelReservationIDs = new ArrayList<HotelReservationIDType>();
        }
        return this.hotelReservationIDs;
    }
}
