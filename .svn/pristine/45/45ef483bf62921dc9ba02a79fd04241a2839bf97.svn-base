package com.tayyarah.email.entity.model;


import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Proxy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;



/**
 * The persistent class for the email database table.
 * 
 */
@Entity
@Table(name="email")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy = false)

public class Email implements Serializable {


	private static final long serialVersionUID = 1L;

	// ###################  EMAIL ACKNOWLEDGEMENTS TYPES ###################

	public static final int STATUS_PENDING = 0;                             // email is just inserted in email table
	public static final int STATUS_PROCESSING = -1;                         // email is being processed for sending
	public static final int STATUS_SENT_SUCCESS = 1;                        // email is being sent successfully
	public static final int STATUS_SENT_ERROR_WRONG_EMAIL = 2;              // email sent error
	public static final int STATUS_SENT_ERROR_SERVER_ISSUE = 3;             // email sent error
	public static final int STATUS_EMAIL_BLOCKED = 4;                       // email sent error for firewall issue
	public static final int STATUS_VERIFIED = 5;                            // email has been verified successfully

	// ###################  VOUCHER EMAIL TYPES ###################

	public static final int EMAIL_TYPE_HOTEL_VOUCHER = 1;                    
	public static final int EMAIL_TYPE_FLIGHT_VOUCHER = 2;
	public static final int EMAIL_TYPE_HOTEL_OFFLINE_VOUCHER = 48;
	public static final int EMAIL_TYPE_FLIGHT_OFFLINE_VOUCHER = 51;
	public static final int EMAIL_TYPE_BUS_VOUCHER = 88;

	// ###################  INVOICE EMAIL TYPES ###################

	public static final int EMAIL_TYPE_FLIGHT_OFFLINE_ONLINE_INVOICE_SEND = 61;
	public static final int EMAIL_TYPE_HOTEL_OFFLINE_ONLINE_INVOICE_SEND = 63;
	public static final int EMAIL_TYPE_CAR_INVOICE = 64; 
	public static final int EMAIL_TYPE_BUS_INVOICE = 65;
	public static final int EMAIL_TYPE_VISA_INVOICE = 66;
	public static final int EMAIL_TYPE_TRAIN_INVOICE = 67;
	public static final int EMAIL_TYPE_INSURANCE_INVOICE = 68;
	public static final int EMAIL_TYPE_MISC_INVOICE = 69;
	public static final int EMAIL_TYPE_MEAL_INVOICE = 70;
	public static final int EMAIL_TYPE_LABOR_INVOICE = 71;

	public static final int EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS = 16;
	public static final int EMAIL_TYPE_DIS_INVOICE_TO_OTHERS = 17;
	public static final int EMAIL_TYPE_HOTEL_INVOICE_SUPER_TO_OTHERS = 18;
	public static final int EMAIL_TYPE_HOTEL_INVOICE_DISTRIBUTOR_TO_OTHERS = 19;
	
	public static final int EMAIL_TYPE_CUSTOMER_INVOICE_FLIGHT = 40;
	public static final int EMAIL_TYPE_CUSTOMER_INVOICE_HOTEL = 41;

	// ###################  B2C USER EMAIL TYPES ###################

	public static final int EMAIL_TYPE_FRONT_USER_REGISTRATION = 5;
	public static final int EMAIL_TYPE_FRONT_USER_FORGOT_PWD = 8;
	public static final int EMAIL_TYPE_FRONT_USER_UPDATION = 34;
	
	public static final int EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH = 93;
	public static final int EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH_UMRAH = 94;

	// ###################  USER EMAIL TYPES ###################

	public static final int EMAIL_TYPE_USER_REGISTRATION = 3;
	public static final int EMAIL_TYPE_USER_FORGOT_PWD_REGISTRATION = 6;
	public static final int EMAIL_TYPE_BLOCKED_USER = 12;
	public static final int EMAIL_TYPE_USER_RESET_PASSWORD = 14;	
	public static final int EMAIL_TYPE_VERIFICATION_USER = 20;
	public static final int EMAIL_TYPE_USER_CREDENTIALS = 28;
	public static final int EMAIL_TYPE_USER_ENQUERIES = 29;	

	// ###################  COMPANY EMAIL TYPES ###################

	public static final int EMAIL_TYPE_COMPANY_REGISTRATION = 4;
	public static final int EMAIL_TYPE_COMPANY_FORGOT_PWD = 7;
	public static final int EMAIL_TYPE_COMPANY_APPROVAL = 9;
	public static final int EMAIL_TYPE_COMPANY_CONFIG_APPROVAL = 10;
	public static final int EMAIL_TYPE_COMPANY_RESET_PASSWORD = 15;
	public static final int EMAIL_TYPE_VERIFICATION_COMPANY = 21;
	public static final int EMAIL_TYPE_COMPANY_UPDATION = 32;
	public static final int EMAIL_TYPE_EMPLOYEE_UPDATION = 33;	
	public static final int EMAIL_TYPE_WHITE_LABEL = 13;
	public static final int EMAIL_TYPE_VERIFICATION_ACK = 27;                // sending email verfication ack back to parent company..

	// ###################  CREDITNOTE EMAIL TYPES ###################
	
	public static final int EMAIL_TYPE_CREDITNOTE_REQUEST_HOTEL = 23;  
	public static final int EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL = 24;
	public static final int EMAIL_TYPE_CREDITNOTE_REQUEST_FLIGHT = 25; 
	public static final int EMAIL_TYPE_CREDITNOTE_CONFIRM_FLIGHT = 26;
	public static final int EMAIL_TYPE_CREDITNOTE_REQUEST_CAR = 72; 
	public static final int EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR = 73;
	public static final int EMAIL_TYPE_CREDITNOTE_REQUEST_BUS =74; 
	public static final int EMAIL_TYPE_CREDITNOTE_CONFIRM_BUS = 75;
	public static final int EMAIL_TYPE_CREDITNOTE_REQUEST_TRAIN =76; 
	public static final int EMAIL_TYPE_CREDITNOTE_CONFIRM_TRAIN = 77;
	public static final int EMAIL_TYPE_CREDITNOTE_REQUEST_VISA =78; 
	public static final int EMAIL_TYPE_CREDITNOTE_CONFIRM_VISA = 79;
	public static final int EMAIL_TYPE_CREDITNOTE_REQUEST_INSURANCE = 80; 
	public static final int EMAIL_TYPE_CREDITNOTE_CONFIRM_INSURANCE = 81;
	public static final int EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL_Customer = 82;
	public static final int EMAIL_TYPE_CREDITNOTE_REQUEST_MISCELLANEOUS = 89;
	public static final int EMAIL_TYPE_CREDITNOTE_CONFIRM_MISCELLANEOUS = 90;
	
	// ###################  QUOTATION EMAIL TYPES ###################
	
	public static final int EMAIL_TYPE_HOTEL_QUOTATION_ALTERNATIVE_EMAIL_SEND = 59;
	public static final int EMAIL_TYPE_FLIGHT_QUOTATION_ALTERNATIVE_EMAIL_SEND = 60;
	public static final int EMAIL_TYPE_HOTEL_OFFLINE_REQUEST = 46;
	public static final int EMAIL_TYPE_HOTEL_OFFLINE_REQUEST_QUOTATION = 47;
	public static final int EMAIL_TYPE_FLIGHT_OFFLINE_REQUEST = 49;
	public static final int EMAIL_TYPE_FLIGHT_OFFLINE_REQUEST_QUOTATION = 50;
	
	// ###################  FAILED BOOKING EMAIL TYPES ###################
	
	public static final int EMAIL_TYPE_FAILS_HOTEL_BOOK = 30;
	public static final int EMAIL_TYPE_FAILS_FLIGHT_BOOK = 31;
	
	// ###################  WALLET NOTIFICATION EMAIL TYPES ###################
	
	public static final int EMAIL_TYPE_WALLET_CREDIT_CHILD_NOTIFICATION = 22;   
	public static final int EMAIL_TYPE_WALLET_CREDIT_PARENT_NOTIFICATION = 36; 
	public static final int EMAIL_TYPE_WALLET_DEBIT_PARENT_NOTIFICATION = 38; 
	public static final int EMAIL_TYPE_WALLET_DEBIT_CHILD_NOTIFICATION = 39;
	public static final int EMAIL_TYPE_WALLET_DEPOSIT_PARENT_NOTIFICATION = 83;
	public static final int EMAIL_TYPE_WALLET_DEPOSIT_CHILD_NOTIFICATION = 84; 	
	public static final int EMAIL_TYPE_WALLET_WITHDRAW_PARENT_NOTIFICATION = 85;
	public static final int EMAIL_TYPE_WALLET_WITHDRAW_CHILD_NOTIFICATION = 86; 	
	
	// ###################  BUG NOTIFICATION EMAIL TYPES ###################
	
	public static final int EMAIL_TYPE_BUG_TRACKER_HISTORY_CREATED = 52;
	public static final int EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED = 53;
	public static final int EMAIL_TYPE_BUG_TRACKER_HISTORY_PENDING = 54;
	public static final int EMAIL_TYPE_BUG_TRACKER_HISTORY_WORK_IN_PROGRESS = 55;
	public static final int EMAIL_TYPE_BUG_TRACKER_HISTORY_STILL_IN_PROGRESS = 56;
	public static final int EMAIL_TYPE_BUG_TRACKER_HISTORY_REVIEW = 57;
	public static final int EMAIL_TYPE_BUG_TRACKER_HISTORY_TEST_REVIEW = 87;
	public static final int EMAIL_TYPE_BUG_TRACKER_HISTORY_CLOSED = 58;
	
	
	// ###################  COMMON NOTIFICATION EMAIL TYPES ###################
	
	public static final int EMAIL_TYPE_FLIGHT_24HOURS_NOTIFICATION = 91;
	public static final int EMAIL_TYPE_FLIGHT_4HOURS_NOTIFICATION = 92;
    public static final int EMAIL_TYPE_COMMISSION_NOTIFICATION = 35;
	public static final int EMAIL_TYPE_ENQUIRY = 42;
	public static final int EMAIL_TYPE_SEND_NOTIFICATION = 62; 	
	public static final int EMAIL_TYPE_POSTPAID_BILL = 11;

	// ###################  VERIFY NOTIFICATION EMAIL TYPES ###################

	public static final int VERIFY_EMAIL_USER = 1;
	public static final int VERIFY_EMAIL_COMPANY = 2;
	public static final int VERIFY_EMAIL_COMPANY_CONFIRMATION = 3;	



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", columnDefinition="BIGINT(20) default 0")
	private Long id;

	@Column(name="order_id")
	private String orderId;

	@Column(name="status_msg" , columnDefinition="TEXT" )
	private String statusMsg;

	@Transient
	private boolean onlyHtmlContent=false;

	@Column(name="mail_status")
	private int mailStatus;

	@Column(name="type")
	private int type;

	@Column(name="attempted_count")
	private int attemptedCount;	

	@Column(name = "created_at")
	Timestamp createdAt;

	@Column(name = "updated_at",
			insertable = false,
			columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL")
	Timestamp updatedAt;

	@Column(name = "to_emailaddress")
	private String toEmailAddress;

	@Column(name = "cc_emailaddress")
	private String ccEmailAddress;

	public String getToEmailAddress() {
		return toEmailAddress;
	}
	public void setToEmailAddress(String toEmailAddress) {
		this.toEmailAddress = toEmailAddress;
	}
	public String getCcEmailAddress() {
		return ccEmailAddress;
	}
	public void setCcEmailAddress(String ccEmailAddress) {
		this.ccEmailAddress = ccEmailAddress;
	}
	public int getAttempedCount() {
		return attemptedCount;
	}
	public void setAttempedCount(int attempedCount) {
		this.attemptedCount = attempedCount;
	}	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public int getStatus() {
		return mailStatus;
	}
	public void setStatus(int status) {
		this.mailStatus = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isOnlyHtmlContent() {
		return onlyHtmlContent;
	}
	public void setOnlyHtmlContent(boolean onlyHtmlContent) {
		this.onlyHtmlContent = onlyHtmlContent;
	}	
	public int getAttemptedCount() {
		return attemptedCount;
	}	
	public void setAttemptedCount(int attemptedCount) {
		this.attemptedCount = attemptedCount;
	}
}