//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.09 at 04:51:51 PM IST 
//


package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * <p>Java class for ProfileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProfileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Accesses" type="{http://www.opentravel.org/OTA/2003/05}AccessesType" minOccurs="0"/>
 *         &lt;element name="Customer" type="{http://www.opentravel.org/OTA/2003/05}CustomerType" minOccurs="0"/>
 *         &lt;element name="UserID" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type">
 *                 &lt;sequence>
 *                 &lt;/sequence>
 *                 &lt;attribute name="PinNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="PrefCollections" type="{http://www.opentravel.org/OTA/2003/05}PreferencesType" minOccurs="0"/>
 *         &lt;element name="CompanyInfo" type="{http://www.opentravel.org/OTA/2003/05}CompanyInfoType" minOccurs="0"/>
 *         &lt;element name="Affiliations" type="{http://www.opentravel.org/OTA/2003/05}AffiliationsType" minOccurs="0"/>
 *         &lt;element name="Agreements" type="{http://www.opentravel.org/OTA/2003/05}AgreementsType" minOccurs="0"/>
 *         &lt;element name="Comments" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Comment" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.opentravel.org/OTA/2003/05}ParagraphType">
 *                           &lt;sequence>
 *                             &lt;element name="AuthorizedViewer" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="ViewerCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="ActionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                           &lt;attribute name="AirlineVendorPrefRPH" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Category" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="CommentOriginatorCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="GuestViewable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                           &lt;attribute name="TransferAction" type="{http://www.opentravel.org/OTA/2003/05}transferActionType" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TPA_Extensions" type="{http://www.opentravel.org/OTA/2003/05}TPA_ExtensionsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="RPH" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="StatusCode">
 *         &lt;simpleType>
 *           &lt;list itemType="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ProfileType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ShareAllMarketInd" type="{http://www.opentravel.org/OTA/2003/05}yesNoType" />
 *       &lt;attribute name="ShareAllOptOutInd" type="{http://www.opentravel.org/OTA/2003/05}yesNoType" />
 *       &lt;attribute name="ShareAllSynchInd" type="{http://www.opentravel.org/OTA/2003/05}yesNoType" />
 *       &lt;attribute name="CreateDateTime" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="CreatorID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LastModifierID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LastModifyDateTime" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="PurgeDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileType", propOrder = {
    "accesses",
    "customer",
    "userIDs",
    "prefCollections",
    "companyInfo",
    "affiliations",
    "agreements",
    "comments",
    "tpaExtensions"
})
public class ProfileType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "Accesses")
    protected AccessesType accesses;
    @XmlElement(name = "Customer")
    protected CustomerType customer;
    @XmlElement(name = "UserID")
    protected List<ProfileType.UserID> userIDs;
    @XmlElement(name = "PrefCollections")
    protected PreferencesType prefCollections;
    @XmlElement(name = "CompanyInfo")
    protected CompanyInfoType companyInfo;
    @XmlElement(name = "Affiliations")
    protected AffiliationsType affiliations;
    @XmlElement(name = "Agreements")
    protected AgreementsType agreements;
    @XmlElement(name = "Comments")
    protected ProfileType.Comments comments;
    @XmlElement(name = "TPA_Extensions")
    protected TPAExtensions tpaExtensions;
    @XmlAttribute(name = "RPH")
    protected String rph;
    @XmlAttribute(name = "StatusCode")
    protected List<String> statusCodes;
    @XmlAttribute(name = "ProfileType")
    protected String profileType;
    @XmlAttribute(name = "ShareAllMarketInd")
    protected YesNoType shareAllMarketInd;
    @XmlAttribute(name = "ShareAllOptOutInd")
    protected YesNoType shareAllOptOutInd;
    @XmlAttribute(name = "ShareAllSynchInd")
    protected YesNoType shareAllSynchInd;
    @XmlAttribute(name = "CreateDateTime")
    @XmlSchemaType(name = "anySimpleType")
    protected String createDateTime;
    @XmlAttribute(name = "CreatorID")
    protected String creatorID;
    @XmlAttribute(name = "LastModifierID")
    protected String lastModifierID;
    @XmlAttribute(name = "LastModifyDateTime")
    @XmlSchemaType(name = "anySimpleType")
    protected String lastModifyDateTime;
    @XmlAttribute(name = "PurgeDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String purgeDate;

    /**
     * Gets the value of the accesses property.
     * 
     * @return
     *     possible object is
     *     {@link AccessesType }
     *     
     */
    public AccessesType getAccesses() {
        return accesses;
    }

    /**
     * Sets the value of the accesses property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessesType }
     *     
     */
    public void setAccesses(AccessesType value) {
        this.accesses = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerType }
     *     
     */
    public CustomerType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerType }
     *     
     */
    public void setCustomer(CustomerType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the userIDs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userIDs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserIDs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProfileType.UserID }
     * 
     * 
     */
    public List<ProfileType.UserID> getUserIDs() {
        if (userIDs == null) {
            userIDs = new ArrayList<ProfileType.UserID>();
        }
        return this.userIDs;
    }

    /**
     * Gets the value of the prefCollections property.
     * 
     * @return
     *     possible object is
     *     {@link PreferencesType }
     *     
     */
    public PreferencesType getPrefCollections() {
        return prefCollections;
    }

    /**
     * Sets the value of the prefCollections property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferencesType }
     *     
     */
    public void setPrefCollections(PreferencesType value) {
        this.prefCollections = value;
    }

    /**
     * Gets the value of the companyInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyInfoType }
     *     
     */
    public CompanyInfoType getCompanyInfo() {
        return companyInfo;
    }

    /**
     * Sets the value of the companyInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyInfoType }
     *     
     */
    public void setCompanyInfo(CompanyInfoType value) {
        this.companyInfo = value;
    }

    /**
     * Gets the value of the affiliations property.
     * 
     * @return
     *     possible object is
     *     {@link AffiliationsType }
     *     
     */
    public AffiliationsType getAffiliations() {
        return affiliations;
    }

    /**
     * Sets the value of the affiliations property.
     * 
     * @param value
     *     allowed object is
     *     {@link AffiliationsType }
     *     
     */
    public void setAffiliations(AffiliationsType value) {
        this.affiliations = value;
    }

    /**
     * Gets the value of the agreements property.
     * 
     * @return
     *     possible object is
     *     {@link AgreementsType }
     *     
     */
    public AgreementsType getAgreements() {
        return agreements;
    }

    /**
     * Sets the value of the agreements property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgreementsType }
     *     
     */
    public void setAgreements(AgreementsType value) {
        this.agreements = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileType.Comments }
     *     
     */
    public ProfileType.Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileType.Comments }
     *     
     */
    public void setComments(ProfileType.Comments value) {
        this.comments = value;
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
     * Gets the value of the rph property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRPH() {
        return rph;
    }

    /**
     * Sets the value of the rph property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRPH(String value) {
        this.rph = value;
    }

    /**
     * Gets the value of the statusCodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statusCodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatusCodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStatusCodes() {
        if (statusCodes == null) {
            statusCodes = new ArrayList<String>();
        }
        return this.statusCodes;
    }

    /**
     * Gets the value of the profileType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileType() {
        return profileType;
    }

    /**
     * Sets the value of the profileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileType(String value) {
        this.profileType = value;
    }

    /**
     * Gets the value of the shareAllMarketInd property.
     * 
     * @return
     *     possible object is
     *     {@link YesNoType }
     *     
     */
    public YesNoType getShareAllMarketInd() {
        return shareAllMarketInd;
    }

    /**
     * Sets the value of the shareAllMarketInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNoType }
     *     
     */
    public void setShareAllMarketInd(YesNoType value) {
        this.shareAllMarketInd = value;
    }

    /**
     * Gets the value of the shareAllOptOutInd property.
     * 
     * @return
     *     possible object is
     *     {@link YesNoType }
     *     
     */
    public YesNoType getShareAllOptOutInd() {
        return shareAllOptOutInd;
    }

    /**
     * Sets the value of the shareAllOptOutInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNoType }
     *     
     */
    public void setShareAllOptOutInd(YesNoType value) {
        this.shareAllOptOutInd = value;
    }

    /**
     * Gets the value of the shareAllSynchInd property.
     * 
     * @return
     *     possible object is
     *     {@link YesNoType }
     *     
     */
    public YesNoType getShareAllSynchInd() {
        return shareAllSynchInd;
    }

    /**
     * Sets the value of the shareAllSynchInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNoType }
     *     
     */
    public void setShareAllSynchInd(YesNoType value) {
        this.shareAllSynchInd = value;
    }

    /**
     * Gets the value of the createDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateDateTime() {
        return createDateTime;
    }

    /**
     * Sets the value of the createDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDateTime(String value) {
        this.createDateTime = value;
    }

    /**
     * Gets the value of the creatorID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatorID() {
        return creatorID;
    }

    /**
     * Sets the value of the creatorID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatorID(String value) {
        this.creatorID = value;
    }

    /**
     * Gets the value of the lastModifierID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModifierID() {
        return lastModifierID;
    }

    /**
     * Sets the value of the lastModifierID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModifierID(String value) {
        this.lastModifierID = value;
    }

    /**
     * Gets the value of the lastModifyDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModifyDateTime() {
        return lastModifyDateTime;
    }

    /**
     * Sets the value of the lastModifyDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModifyDateTime(String value) {
        this.lastModifyDateTime = value;
    }

    /**
     * Gets the value of the purgeDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurgeDate() {
        return purgeDate;
    }

    /**
     * Sets the value of the purgeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurgeDate(String value) {
        this.purgeDate = value;
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
     *         &lt;element name="Comment" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}ParagraphType">
     *                 &lt;sequence>
     *                   &lt;element name="AuthorizedViewer" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                           &lt;/sequence>
     *                           &lt;attribute name="ViewerCode" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="ActionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *                 &lt;attribute name="AirlineVendorPrefRPH" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Category" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="CommentOriginatorCode" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="GuestViewable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="TransferAction" type="{http://www.opentravel.org/OTA/2003/05}transferActionType" />
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
    @XmlType(name = "", propOrder = {
        "comments"
    })
    public static class Comments
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "Comment", required = true)
        protected List<ProfileType.Comments.Comment> comments;

        /**
         * Gets the value of the comments property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the comments property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getComments().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProfileType.Comments.Comment }
         * 
         * 
         */
        public List<ProfileType.Comments.Comment> getComments() {
            if (comments == null) {
                comments = new ArrayList<ProfileType.Comments.Comment>();
            }
            return this.comments;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}ParagraphType">
         *       &lt;sequence>
         *         &lt;element name="AuthorizedViewer" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                 &lt;/sequence>
         *                 &lt;attribute name="ViewerCode" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="ActionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
         *       &lt;attribute name="AirlineVendorPrefRPH" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Category" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="CommentOriginatorCode" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="GuestViewable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *       &lt;attribute name="TransferAction" type="{http://www.opentravel.org/OTA/2003/05}transferActionType" />
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
            "authorizedViewers"
        })
        public static class Comment
            extends ParagraphType
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "AuthorizedViewer")
            protected List<ProfileType.Comments.Comment.AuthorizedViewer> authorizedViewers;
            @XmlAttribute(name = "ActionDate")
            @XmlSchemaType(name = "anySimpleType")
            protected String actionDate;
            @XmlAttribute(name = "AirlineVendorPrefRPH")
            protected String airlineVendorPrefRPH;
            @XmlAttribute(name = "Category")
            protected String category;
            @XmlAttribute(name = "CommentOriginatorCode")
            protected String commentOriginatorCode;
            @XmlAttribute(name = "GuestViewable")
            protected Boolean guestViewable;
            @XmlAttribute(name = "TransferAction")
            protected TransferActionType transferAction;

            /**
             * Gets the value of the authorizedViewers property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the authorizedViewers property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAuthorizedViewers().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ProfileType.Comments.Comment.AuthorizedViewer }
             * 
             * 
             */
            public List<ProfileType.Comments.Comment.AuthorizedViewer> getAuthorizedViewers() {
                if (authorizedViewers == null) {
                    authorizedViewers = new ArrayList<ProfileType.Comments.Comment.AuthorizedViewer>();
                }
                return this.authorizedViewers;
            }

            /**
             * Gets the value of the actionDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getActionDate() {
                return actionDate;
            }

            /**
             * Sets the value of the actionDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setActionDate(String value) {
                this.actionDate = value;
            }

            /**
             * Gets the value of the airlineVendorPrefRPH property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAirlineVendorPrefRPH() {
                return airlineVendorPrefRPH;
            }

            /**
             * Sets the value of the airlineVendorPrefRPH property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAirlineVendorPrefRPH(String value) {
                this.airlineVendorPrefRPH = value;
            }

            /**
             * Gets the value of the category property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCategory() {
                return category;
            }

            /**
             * Sets the value of the category property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCategory(String value) {
                this.category = value;
            }

            /**
             * Gets the value of the commentOriginatorCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCommentOriginatorCode() {
                return commentOriginatorCode;
            }

            /**
             * Sets the value of the commentOriginatorCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCommentOriginatorCode(String value) {
                this.commentOriginatorCode = value;
            }

            /**
             * Gets the value of the guestViewable property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isGuestViewable() {
                return guestViewable;
            }

            /**
             * Sets the value of the guestViewable property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setGuestViewable(Boolean value) {
                this.guestViewable = value;
            }

            /**
             * Gets the value of the transferAction property.
             * 
             * @return
             *     possible object is
             *     {@link TransferActionType }
             *     
             */
            public TransferActionType getTransferAction() {
                return transferAction;
            }

            /**
             * Sets the value of the transferAction property.
             * 
             * @param value
             *     allowed object is
             *     {@link TransferActionType }
             *     
             */
            public void setTransferAction(TransferActionType value) {
                this.transferAction = value;
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
             *       &lt;attribute name="ViewerCode" type="{http://www.w3.org/2001/XMLSchema}string" />
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
            public static class AuthorizedViewer
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                @XmlAttribute(name = "ViewerCode")
                protected String viewerCode;

                /**
                 * Gets the value of the viewerCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getViewerCode() {
                    return viewerCode;
                }

                /**
                 * Sets the value of the viewerCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setViewerCode(String value) {
                    this.viewerCode = value;
                }

            }

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
     *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type">
     *       &lt;sequence>
     *       &lt;/sequence>
     *       &lt;attribute name="PinNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class UserID
        extends UniqueIDType
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlAttribute(name = "PinNumber")
        protected String pinNumber;

        /**
         * Gets the value of the pinNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPinNumber() {
            return pinNumber;
        }

        /**
         * Sets the value of the pinNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPinNumber(String value) {
            this.pinNumber = value;
        }

    }

}
