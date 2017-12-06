//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.09 at 04:51:51 PM IST 
//


package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * <p>Java class for RateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Rate" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}AmountType">
 *                 &lt;sequence>
 *                   &lt;element name="TPA_Extensions" type="{http://www.opentravel.org/OTA/2003/05}TPA_ExtensionsType" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="CachedIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="Duration" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *                 &lt;attribute name="RateMode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="RateSource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="RateTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="RoomPricingType" type="{http://www.opentravel.org/OTA/2003/05}pricingType" />
 *               &lt;/extension>
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RateType", propOrder = {
    "rate"
})
public class RateType
    implements Serializable
{

    /**
	 * @param rates the rates to set
	 */
	public void setRates(List<RateType.Rate> rates) {
		this.rates = rates;
	}


	private final static long serialVersionUID = -1L;
    @XmlElement(name = "Rate", required = true)
    protected List<RateType.Rate> rates;

    /**
     * Gets the value of the rates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RateType.Rate }
     * 
     * 
     */
    public List<RateType.Rate> getRates() {
        if (rates == null) {
            rates = new ArrayList<RateType.Rate>();
        }
        return this.rates;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}AmountType">
     *       &lt;sequence>
     *         &lt;element name="TPA_Extensions" type="{http://www.opentravel.org/OTA/2003/05}TPA_ExtensionsType" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="CachedIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="Duration" type="{http://www.w3.org/2001/XMLSchema}duration" />
     *       &lt;attribute name="RateMode" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="RateSource" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="RateTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="RoomPricingType" type="{http://www.opentravel.org/OTA/2003/05}pricingType" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tpaExtensions"
    })
    public static class Rate
        extends AmountType
        implements Serializable
    {

        /**
		 * @return the tpaExtensions
		 */
		public TPAExtensions getTpaExtensions() {
			return tpaExtensions;
		}

		/**
		 * @param tpaExtensions the tpaExtensions to set
		 */
		public void setTpaExtensions(TPAExtensions tpaExtensions) {
			this.tpaExtensions = tpaExtensions;
		}

		/**
		 * @return the cachedIndicator
		 */
		public Boolean getCachedIndicator() {
			return cachedIndicator;
		}

		private final static long serialVersionUID = -1L;
        @XmlElement(name = "TPA_Extensions")
        protected TPAExtensions tpaExtensions;
        @XmlAttribute(name = "CachedIndicator")
        protected Boolean cachedIndicator;
        @XmlAttribute(name = "Duration")
        protected Duration duration;
        @XmlAttribute(name = "RateMode")
        protected String rateMode;
        @XmlAttribute(name = "RateSource")
        protected String rateSource;
        @XmlAttribute(name = "RateTypeCode")
        protected String rateTypeCode;
        @XmlAttribute(name = "RoomPricingType")
        protected PricingType roomPricingType;
        
        
        
        
        protected int roomIndex;
		public int getRoomIndex() {
			return roomIndex;
		}

		public void setRoomIndex(int roomIndex) {
			this.roomIndex = roomIndex;
		}

		public int getAdults() {
			return adults;
		}

		public void setAdults(int adults) {
			this.adults = adults;
		}

		public int getChildren() {
			return children;
		}

		public void setChildren(int children) {
			this.children = children;
		}

		public String getChildrenages() {
			return childrenages;
		}

		public void setChildrenages(String childrenages) {
			this.childrenages = childrenages;
		}

		@XmlAttribute(name = "Adults")
		protected int adults;
		@XmlAttribute(name = "Children")
		protected int children;
		@XmlAttribute(name = "ChildrenAges")
		protected String childrenages;        		
		protected String name;		
        
        
        
        
        
        

        public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
         * Gets the value of the tpaExtensions property.
         * 
         * @return
         *     possible object is
         *     {@link TPAExtensions }
         *     
         */
        public TPAExtensions getTPAExtensions() {
            return tpaExtensions;
        }

        /**
         * Sets the value of the tpaExtensions property.
         * 
         * @param value
         *     allowed object is
         *     {@link TPAExtensions }
         *     
         */
        public void setTPAExtensions(TPAExtensions value) {
            this.tpaExtensions = value;
        }

        /**
         * Gets the value of the cachedIndicator property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isCachedIndicator() {
            return cachedIndicator;
        }

        /**
         * Sets the value of the cachedIndicator property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCachedIndicator(Boolean value) {
            this.cachedIndicator = value;
        }

        /**
         * Gets the value of the duration property.
         * 
         * @return
         *     possible object is
         *     {@link Duration }
         *     
         */
        public Duration getDuration() {
            return duration;
        }

        /**
         * Sets the value of the duration property.
         * 
         * @param value
         *     allowed object is
         *     {@link Duration }
         *     
         */
        public void setDuration(Duration value) {
            this.duration = value;
        }

        /**
         * Gets the value of the rateMode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRateMode() {
            return rateMode;
        }

        /**
         * Sets the value of the rateMode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRateMode(String value) {
            this.rateMode = value;
        }

        /**
         * Gets the value of the rateSource property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRateSource() {
            return rateSource;
        }

        /**
         * Sets the value of the rateSource property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRateSource(String value) {
            this.rateSource = value;
        }

        /**
         * Gets the value of the rateTypeCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRateTypeCode() {
            return rateTypeCode;
        }

        /**
         * Sets the value of the rateTypeCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRateTypeCode(String value) {
            this.rateTypeCode = value;
        }

        /**
         * Gets the value of the roomPricingType property.
         * 
         * @return
         *     possible object is
         *     {@link PricingType }
         *     
         */
        public PricingType getRoomPricingType() {
            return roomPricingType;
        }

        /**
         * Sets the value of the roomPricingType property.
         * 
         * @param value
         *     allowed object is
         *     {@link PricingType }
         *     
         */
        public void setRoomPricingType(PricingType value) {
            this.roomPricingType = value;
        }

    }

}
