//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.09 at 04:51:51 PM IST 
//


package com.tayyarah.api.hotel.travelguru.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}HotelResResponseType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "OTA_HotelResRS")
public class OTAHotelResRS
    extends HotelResResponseType
    implements Serializable
{

    @Override
	public String toString() {
		return "OTAHotelResRS [pos=" + pos + ", success=" + success + ", warnings=" + warnings + ", hotelReservations="
				+ hotelReservations + ", errors=" + errors + ", resResponseType=" + resResponseType + ", correlationID="
				+ correlationID + ", echoToken=" + echoToken + ", retransmissionIndicator=" + retransmissionIndicator
				+ ", sequenceNmbr=" + sequenceNmbr + ", target=" + target + ", targetName=" + targetName
				+ ", timeStamp=" + timeStamp + ", transactionIdentifier=" + transactionIdentifier
				+ ", transactionStatusCode=" + transactionStatusCode + ", version=" + version + ", altLangID="
				+ altLangID + ", primaryLangID=" + primaryLangID + "]";
	}

	private final static long serialVersionUID = -1L;

}
