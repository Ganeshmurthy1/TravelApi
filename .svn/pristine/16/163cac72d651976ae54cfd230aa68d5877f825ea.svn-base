package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import com.tayyarah.hotel.model.AddressesType.Address;
import com.tayyarah.hotel.model.BasicPropertyInfoType.Position;


public class AgencyInfo
implements Serializable
{

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	private final static long serialVersionUID = -1L;
	@XmlElement(name = "Name", required = true)
	protected String name;
	@XmlElement(name = "Position", required = true)
	protected Position position;
	@XmlElement(name = "Address", required = true)
	protected Address address;

	/**
	 * Gets the value of the name property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setName(String value) {
		this.name = value;
	}

}