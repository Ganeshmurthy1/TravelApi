//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.09 at 04:51:51 PM IST 
//


package com.tayyarah.api.hotel.travelguru.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for officeLocationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="officeLocationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Main"/>
 *     &lt;enumeration value="Field"/>
 *     &lt;enumeration value="Division"/>
 *     &lt;enumeration value="Regional"/>
 *     &lt;enumeration value="Remote"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "officeLocationType")
@XmlEnum
public enum OfficeLocationType {

    @XmlEnumValue("Main")
    MAIN("Main"),
    @XmlEnumValue("Field")
    FIELD("Field"),
    @XmlEnumValue("Division")
    DIVISION("Division"),
    @XmlEnumValue("Regional")
    REGIONAL("Regional"),
    @XmlEnumValue("Remote")
    REMOTE("Remote");
    private final String value;

    OfficeLocationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OfficeLocationType fromValue(String v) {
        for (OfficeLocationType c: OfficeLocationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
