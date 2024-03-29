//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.09 at 04:51:51 PM IST 
//


package com.tayyarah.api.hotel.travelguru.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for RoomSharesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RoomSharesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RoomShare" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="GuestRPHs" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="GuestRPH" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="Duration" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="End" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="Start" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomSharesType", propOrder = {
    "roomShares"
})
public class RoomSharesType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "RoomShare", required = true)
    protected List<RoomSharesType.RoomShare> roomShares;

    /**
     * Gets the value of the roomShares property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roomShares property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoomShares().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RoomSharesType.RoomShare }
     * 
     * 
     */
    public List<RoomSharesType.RoomShare> getRoomShares() {
        if (roomShares == null) {
            roomShares = new ArrayList<RoomSharesType.RoomShare>();
        }
        return this.roomShares;
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
     *         &lt;element name="GuestRPHs" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="GuestRPH" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="Duration" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="End" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="Start" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "guestRPHs"
    })
    public static class RoomShare
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "GuestRPHs")
        protected RoomSharesType.RoomShare.GuestRPHs guestRPHs;

        /**
         * Gets the value of the guestRPHs property.
         * 
         * @return
         *     possible object is
         *     {@link RoomSharesType.RoomShare.GuestRPHs }
         *     
         */
        public RoomSharesType.RoomShare.GuestRPHs getGuestRPHs() {
            return guestRPHs;
        }

        /**
         * Sets the value of the guestRPHs property.
         * 
         * @param value
         *     allowed object is
         *     {@link RoomSharesType.RoomShare.GuestRPHs }
         *     
         */
        public void setGuestRPHs(RoomSharesType.RoomShare.GuestRPHs value) {
            this.guestRPHs = value;
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
         *         &lt;element name="GuestRPH" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="Duration" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="End" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="Start" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
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
            "guestRPHs"
        })
        public static class GuestRPHs
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "GuestRPH", required = true)
            protected List<RoomSharesType.RoomShare.GuestRPHs.GuestRPH> guestRPHs;

            /**
             * Gets the value of the guestRPHs property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the guestRPHs property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getGuestRPHs().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RoomSharesType.RoomShare.GuestRPHs.GuestRPH }
             * 
             * 
             */
            public List<RoomSharesType.RoomShare.GuestRPHs.GuestRPH> getGuestRPHs() {
                if (guestRPHs == null) {
                    guestRPHs = new ArrayList<RoomSharesType.RoomShare.GuestRPHs.GuestRPH>();
                }
                return this.guestRPHs;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="Duration" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="End" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="Start" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class GuestRPH
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "Duration")
                protected String duration;
                @XmlAttribute(name = "End")
                protected String end;
                @XmlAttribute(name = "Start")
                protected String start;

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
                 * Gets the value of the duration property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDuration() {
                    return duration;
                }

                /**
                 * Sets the value of the duration property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDuration(String value) {
                    this.duration = value;
                }

                /**
                 * Gets the value of the end property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getEnd() {
                    return end;
                }

                /**
                 * Sets the value of the end property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setEnd(String value) {
                    this.end = value;
                }

                /**
                 * Gets the value of the start property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStart() {
                    return start;
                }

                /**
                 * Sets the value of the start property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStart(String value) {
                    this.start = value;
                }

            }

        }

    }

}
