//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.07 at 05:57:04 PM IST 
//


package com.tayyarah.api.hotel.travelguru.book.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for preferLevelType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="preferLevelType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Only"/>
 *     &lt;enumeration value="Unacceptable"/>
 *     &lt;enumeration value="Preferred"/>
 *     &lt;enumeration value="Required"/>
 *     &lt;enumeration value="NoPreference"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "preferLevelType")
@XmlEnum
public enum PreferLevelType {

    @XmlEnumValue("Only")
    ONLY("Only"),
    @XmlEnumValue("Unacceptable")
    UNACCEPTABLE("Unacceptable"),
    @XmlEnumValue("Preferred")
    PREFERRED("Preferred"),
    @XmlEnumValue("Required")
    REQUIRED("Required"),
    @XmlEnumValue("NoPreference")
    NO_PREFERENCE("NoPreference");
    private final String value;

    PreferLevelType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PreferLevelType fromValue(String v) {
        for (PreferLevelType c: PreferLevelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
