//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.09 at 04:51:51 PM IST 
//


package com.tayyarah.api.hotel.travelguru.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WeatherInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WeatherInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Precipitation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                 &lt;/sequence>
 *                 &lt;attribute name="AveragePrecipitation" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *                 &lt;attribute name="UnitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Temperature" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                 &lt;/sequence>
 *                 &lt;attribute name="AverageHighTemp" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="AverageLowTemp" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="TempUnit" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Period" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WeatherInfoType", propOrder = {
    "precipitations",
    "temperatures"
})
public class WeatherInfoType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "Precipitation")
    protected List<WeatherInfoType.Precipitation> precipitations;
    @XmlElement(name = "Temperature")
    protected List<WeatherInfoType.Temperature> temperatures;
    @XmlAttribute(name = "Period")
    protected String period;

    /**
     * Gets the value of the precipitations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the precipitations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrecipitations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WeatherInfoType.Precipitation }
     * 
     * 
     */
    public List<WeatherInfoType.Precipitation> getPrecipitations() {
        if (precipitations == null) {
            precipitations = new ArrayList<WeatherInfoType.Precipitation>();
        }
        return this.precipitations;
    }

    /**
     * Gets the value of the temperatures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the temperatures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemperatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WeatherInfoType.Temperature }
     * 
     * 
     */
    public List<WeatherInfoType.Temperature> getTemperatures() {
        if (temperatures == null) {
            temperatures = new ArrayList<WeatherInfoType.Temperature>();
        }
        return this.temperatures;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriod(String value) {
        this.period = value;
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
     *       &lt;/sequence>
     *       &lt;attribute name="AveragePrecipitation" type="{http://www.w3.org/2001/XMLSchema}decimal" />
     *       &lt;attribute name="UnitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Precipitation
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlAttribute(name = "AveragePrecipitation")
        protected BigDecimal averagePrecipitation;
        @XmlAttribute(name = "UnitOfMeasure")
        protected String unitOfMeasure;

        /**
         * Gets the value of the averagePrecipitation property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getAveragePrecipitation() {
            return averagePrecipitation;
        }

        /**
         * Sets the value of the averagePrecipitation property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setAveragePrecipitation(BigDecimal value) {
            this.averagePrecipitation = value;
        }

        /**
         * Gets the value of the unitOfMeasure property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnitOfMeasure() {
            return unitOfMeasure;
        }

        /**
         * Sets the value of the unitOfMeasure property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnitOfMeasure(String value) {
            this.unitOfMeasure = value;
        }

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
     *       &lt;/sequence>
     *       &lt;attribute name="AverageHighTemp" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="AverageLowTemp" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="TempUnit" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Temperature
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlAttribute(name = "AverageHighTemp")
        protected BigInteger averageHighTemp;
        @XmlAttribute(name = "AverageLowTemp")
        protected BigInteger averageLowTemp;
        @XmlAttribute(name = "TempUnit")
        protected String tempUnit;

        /**
         * Gets the value of the averageHighTemp property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getAverageHighTemp() {
            return averageHighTemp;
        }

        /**
         * Sets the value of the averageHighTemp property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setAverageHighTemp(BigInteger value) {
            this.averageHighTemp = value;
        }

        /**
         * Gets the value of the averageLowTemp property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getAverageLowTemp() {
            return averageLowTemp;
        }

        /**
         * Sets the value of the averageLowTemp property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setAverageLowTemp(BigInteger value) {
            this.averageLowTemp = value;
        }

        /**
         * Gets the value of the tempUnit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTempUnit() {
            return tempUnit;
        }

        /**
         * Sets the value of the tempUnit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTempUnit(String value) {
            this.tempUnit = value;
        }

    }

}
