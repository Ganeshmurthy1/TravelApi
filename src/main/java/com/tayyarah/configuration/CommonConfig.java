
package com.tayyarah.configuration;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class CommonConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	@Autowired
	@Qualifier("commonConfig")
	private static CommonConfig commonConfig;

	//public static CommonConfig commonConfig ;


	public static CommonConfig GetCommonConfig() {
		if(commonConfig == null || commonConfig.api_url == null)
		{
			String confFile = "CommonConfig.xml";
			ConfigurableApplicationContext context
			= new ClassPathXmlApplicationContext(confFile);
			commonConfig = (CommonConfig) context.getBean("commonConfig");
			context.close();
		}
		return commonConfig;
	}

	private String server_ip_address ;
	public String getServer_ip_address() {
		return server_ip_address;
	}


	public void setServer_ip_address(String server_ip_address) {
		this.server_ip_address = server_ip_address;
	}

	//for cache update
	private String update_cache_in_table;
	//for sending all mail for today
	private String send_all_email_notification;
	
	private String allHotelFlightCustomerPartialPayment;
	
	private String bug_service_url;
	private String email_service_url ;
	private String email_booking ;
	private String email_support ;
	private String email_support_bcc ;
	private String email_admin ;
	private String image_path;
	private String defult_image_path;
	private String invoice_hotel_prefix;
	private String invoice_flight_prefix; 
	private String email_service_Flight_pending_payment_url;
	private String email_service_Hotel_pending_payment_url;
	private boolean is_lintas_enabled;
	private boolean is_tayyarah_enabled;
	private String log_location_linux;
	private String log_location;
	private String log_location_windows;
	private String care_mail_id;
	private String admin_url;
	private String ibe_url;
	private String api_url;
	private String email_verify_url; 
	private int max_email_attempts;
	private int	max_email_queue_size;
	private boolean	is_dev_mode;
	private boolean	flightCacheEnable;
	private String email_service_Flight_Sendholdticket_url;
	private String email_service_Flight_Autorelease_holdbooking_url;
	private String flight_rounding_mode;
	private String hotel_rounding_mode;	
	private String system_model;
	private String corporateAgreementExpiryEmailUrl;
	private String bus_rounding_mode;
	private String sendFlightMailBeforeDeparture;  
	private String sendFlightMailBeforeOneDay;
	private String reset_all_b2c_ip;
	
	public String getBus_rounding_mode() {
		return bus_rounding_mode;
	}


	public void setBus_rounding_mode(String bus_rounding_mode) {
		this.bus_rounding_mode = bus_rounding_mode;
	}


	public String getSystem_model() {
		return system_model;
	} 
	public void setSystem_model(String system_model) {
		this.system_model = system_model;
	}


	public String getFlight_rounding_mode() {
		return flight_rounding_mode;
	}


	public void setFlight_rounding_mode(String flight_rounding_mode) {
		this.flight_rounding_mode = flight_rounding_mode;
	}


	public String getHotel_rounding_mode() {
		return hotel_rounding_mode;
	}


	public void setHotel_rounding_mode(String hotel_rounding_mode) {
		this.hotel_rounding_mode = hotel_rounding_mode;
	}


	public String getEmail_service_Flight_Sendholdticket_url() {
		return email_service_Flight_Sendholdticket_url;
	}


	public String getEmail_service_Flight_Autorelease_holdbooking_url() {
		return email_service_Flight_Autorelease_holdbooking_url;
	}


	public void setEmail_service_Flight_Sendholdticket_url(String email_service_Flight_Sendholdticket_url) {
		this.email_service_Flight_Sendholdticket_url = email_service_Flight_Sendholdticket_url;
	}


	public void setEmail_service_Flight_Autorelease_holdbooking_url(
			String email_service_Flight_Autorelease_holdbooking_url) {
		this.email_service_Flight_Autorelease_holdbooking_url = email_service_Flight_Autorelease_holdbooking_url;
	}


	public boolean isIs_dev_mode() {
		return is_dev_mode;
	}


	public void setIs_dev_mode(boolean is_dev_mode) {
		this.is_dev_mode = is_dev_mode;
	}


	


	public String getEmail_booking() {
		return email_booking;
	}

	public void setEmail_booking(String email_booking) {
		this.email_booking = email_booking;
	}

	public String getEmail_support() {
		return email_support;
	}

	public void setEmail_support(String email_support) {
		this.email_support = email_support;
	}

	public String getEmail_admin() {
		return email_admin;
	}

	public void setEmail_admin(String email_admin) {
		this.email_admin = email_admin;
	}


	public boolean isIs_lintas_enabled() {
		return is_lintas_enabled;
	}

	public void setIs_lintas_enabled(boolean is_lintas_enabled) {
		this.is_lintas_enabled = is_lintas_enabled;
	}

	public boolean isIs_tayyarah_enabled() {
		return is_tayyarah_enabled;
	}

	public void setIs_tayyarah_enabled(boolean is_tayyarah_enabled) {
		this.is_tayyarah_enabled = is_tayyarah_enabled;
	}


	public String getEmail_service_Flight_pending_payment_url() {
		return email_service_Flight_pending_payment_url;
	}

	public void setEmail_service_Flight_pending_payment_url(
			String email_service_Flight_pending_payment_url) {
		this.email_service_Flight_pending_payment_url = email_service_Flight_pending_payment_url;
	}

	public String getEmail_service_Hotel_pending_payment_url() {
		return email_service_Hotel_pending_payment_url;
	}

	public void setEmail_service_Hotel_pending_payment_url(
			String email_service_Hotel_pending_payment_url) {
		this.email_service_Hotel_pending_payment_url = email_service_Hotel_pending_payment_url;
	}

	public String getEmail_service_url() {
		return email_service_url;
	}

	public void setEmail_service_url(String email_service_url) {
		this.email_service_url = email_service_url;
	}

	public String getInvoice_hotel_prefix() {
		return invoice_hotel_prefix;
	}

	public void setInvoice_hotel_prefix(String invoice_hotel_prefix) {
		this.invoice_hotel_prefix = invoice_hotel_prefix;
	}

	public String getInvoice_flight_prefix() {
		return invoice_flight_prefix;
	}

	public void setInvoice_flight_prefix(String invoice_flight_prefix) {
		this.invoice_flight_prefix = invoice_flight_prefix;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getDefult_image_path() {
		return defult_image_path;
	}

	public void setDefult_image_path(String defult_image_path) {
		this.defult_image_path = defult_image_path;
	}

	public String getLog_location_linux() {
		return log_location_linux;
	}

	public void setLog_location_linux(String log_location_linux) {
		this.log_location_linux = log_location_linux;
	}

	public String getLog_location_windows() {
		return log_location_windows;
	}

	public void setLog_location_windows(String log_location_windows) {
		this.log_location_windows = log_location_windows;
	}

	public String getCare_mail_id() {
		return care_mail_id;
	}

	public void setCare_mail_id(String care_mail_id) {
		this.care_mail_id = care_mail_id;
	}

	public String getEmail_support_bcc() {
		return email_support_bcc;
	}

	public void setEmail_support_bcc(String email_support_bcc) {
		this.email_support_bcc = email_support_bcc;
	}


	public String getLog_location() {
		return log_location;
	}


	public void setLog_location(String log_location) {
		this.log_location = log_location;
	}

	public String getAdmin_url() {
		return admin_url;
	}


	public void setAdmin_url(String admin_url) {
		this.admin_url = admin_url;
	}


	public String getIbe_url() {
		return ibe_url;
	}


	public void setIbe_url(String ibe_url) {
		this.ibe_url = ibe_url;
	}
	public String getEmail_verify_url() {
		return email_verify_url;
	}


	public void setEmail_verify_url(String email_verify_url) {
		this.email_verify_url = email_verify_url;
	}
	public int getMax_email_attempts() {
		return max_email_attempts;
	}


	public void setMax_email_attempts(int max_email_attempts) {
		this.max_email_attempts = max_email_attempts;
	}


	public int getMax_email_queue_size() {
		return max_email_queue_size;
	}


	public void setMax_email_queue_size(int max_email_queue_size) {
		this.max_email_queue_size = max_email_queue_size;
	}

	public String getApi_url() {
		return api_url;
	}


	public void setApi_url(String api_url) {
		this.api_url = api_url;
	}


	public String getBug_service_url() {
		return bug_service_url;
	}


	public void setBug_service_url(String bug_service_url) {
		this.bug_service_url = bug_service_url;
	}

	public String getUpdate_cache_in_table() {
		return update_cache_in_table;
	}


	public void setUpdate_cache_in_table(String update_cache_in_table) {
		this.update_cache_in_table = update_cache_in_table;
	}


	public String getSend_all_email_notification() {
		return send_all_email_notification;
	}


	public void setSend_all_email_notification(String send_all_email_notification) {
		this.send_all_email_notification = send_all_email_notification;
	}


	public String getAllHotelFlightCustomerPartialPayment() {
		return allHotelFlightCustomerPartialPayment;
	}


	public void setAllHotelFlightCustomerPartialPayment(String allHotelFlightCustomerPartialPayment) {
		this.allHotelFlightCustomerPartialPayment = allHotelFlightCustomerPartialPayment;
	}


	public String getCorporateAgreementExpiryEmailUrl() {
		return corporateAgreementExpiryEmailUrl;
	}


	public void setCorporateAgreementExpiryEmailUrl(String corporateAgreementExpiryEmailUrl) {
		this.corporateAgreementExpiryEmailUrl = corporateAgreementExpiryEmailUrl;
	}


	public String getSendFlightMailBeforeDeparture() {
		return sendFlightMailBeforeDeparture;
	}


	public void setSendFlightMailBeforeDeparture(String sendFlightMailBeforeDeparture) {
		this.sendFlightMailBeforeDeparture = sendFlightMailBeforeDeparture;
	}


	public String getSendFlightMailBeforeOneDay() {
		return sendFlightMailBeforeOneDay;
	}


	public void setSendFlightMailBeforeOneDay(String sendFlightMailBeforeOneDay) {
		this.sendFlightMailBeforeOneDay = sendFlightMailBeforeOneDay;
	}


	public String getReset_all_b2c_ip() {
		return reset_all_b2c_ip;
	}


	public void setReset_all_b2c_ip(String reset_all_b2c_ip) {
		this.reset_all_b2c_ip = reset_all_b2c_ip;
	}


	public boolean isFlightCacheEnable() {
		return flightCacheEnable;
	}


	public void setFlightCacheEnable(boolean flightCacheEnable) {
		this.flightCacheEnable = flightCacheEnable;
	}
	 
}
