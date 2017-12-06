//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.09 at 04:51:51 PM IST 
//


package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;


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
 *       &lt;attribute name="available" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="deals" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="maxPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="minPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="total" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "HotelsInfo")
public class HotelsInfo
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlAttribute(name = "available", required = true)
    protected int available;
    @XmlAttribute(name = "deals")
    protected Integer deals;
    @XmlAttribute(name = "maxPrice")
    protected BigDecimal maxPrice;
    @XmlAttribute(name = "minPrice")
    protected BigDecimal minPrice;
    @XmlAttribute(name = "total", required = true)
    protected int total;

    @XmlAttribute(name = "ApiProviderMap")
	protected HashMap<Integer, Integer> apiProviderMap;
    
    public HashMap<Integer, Integer> getApiProviderMap() {
		return apiProviderMap;
	}   
    public void setApiProviderMap(HashMap<Integer, Integer> apiProviderMap) {
		this.apiProviderMap = apiProviderMap;
	}
    
    @XmlAttribute(name = "ApiProviderCityMap")
   	protected HashMap<Integer, HashMap<Integer, Integer>> apiProviderCityMap;
    public HashMap<Integer, HashMap<Integer, Integer>> getApiProviderCityMap() {
		return apiProviderCityMap;
	}

	public void setApiProviderCityMap(HashMap<Integer, HashMap<Integer, Integer>> apiProviderCityMap) {
		this.apiProviderCityMap = apiProviderCityMap;
	}
   

	

    //HashMap<Integer, HashMap<Integer, Integer>> apiProviderMap = new HashMap<Integer, HashMap<Integer, Integer>>();	
	

	public Integer getDuplicates() {
		return duplicates;
	}

	public void setDuplicates(Integer duplicates) {
		this.duplicates = duplicates;
	}

	@XmlAttribute(name = "duplicates")
    protected Integer duplicates;

    
    
    /**
     * Gets the value of the available property.
     * 
     */
    public int getAvailable() {
        return available;
    }

    /**
     * Sets the value of the available property.
     * 
     */
    public void setAvailable(int value) {
        this.available = value;
    }

    /**
     * Gets the value of the deals property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeals() {
        return deals;
    }

    /**
     * Sets the value of the deals property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeals(Integer value) {
        this.deals = value;
    }

    /**
     * Gets the value of the maxPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    /**
     * Sets the value of the maxPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxPrice(BigDecimal value) {
        this.maxPrice = value;
    }

    /**
     * Gets the value of the minPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    /**
     * Sets the value of the minPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMinPrice(BigDecimal value) {
        this.minPrice = value;
    }

    /**
     * Gets the value of the total property.
     * 
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     */
    public void setTotal(int value) {
        this.total = value;
    }

}
