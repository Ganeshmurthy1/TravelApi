package com.tayyarah.common.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalTime;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;
import com.tayyarah.bus.dao.BusCreditNoteDao;
import com.tayyarah.bus.dao.BusOrderRowEmailDao;
import com.tayyarah.car.dao.CarCreditNoteDao;
import com.tayyarah.car.dao.CarOrderRowEmailDao;
import com.tayyarah.common.dao.NotificationDAO;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.notification.Notification;
import com.tayyarah.common.notification.NotificationDetail;
import com.tayyarah.common.notification.dao.HotelAndFlightPaymentTransctionDAO;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.dao.EmailNotificationDao;
import com.tayyarah.email.dao.FlightOrderRowEmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.email.helper.BugEmailHelper;
import com.tayyarah.email.helper.BusEmailHelper;
import com.tayyarah.email.helper.CarEmailHelper;
import com.tayyarah.email.helper.CompanyEmailHelper;
import com.tayyarah.email.helper.FlightEmailHelper;
import com.tayyarah.email.helper.FlightQuotationEmailHelper;
import com.tayyarah.email.helper.FrontUserEmailHelper;
import com.tayyarah.email.helper.HotelEmailHelper;
import com.tayyarah.email.helper.HotelQuotationEmailHelper;
import com.tayyarah.email.helper.InsuranceEmailHelper;
import com.tayyarah.email.helper.MiscellaneousEmailHelper;
import com.tayyarah.email.helper.TayyarahUmrahEmailHelper;
import com.tayyarah.email.helper.TrainEmailHelper;
import com.tayyarah.email.helper.UserEmailHelper;
import com.tayyarah.email.helper.VisaEmailHelper;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightCreditNoteDao;
import com.tayyarah.flight.entity.FlightBookingKeysTemp;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.flight.model.FlightInvoiceData;
import com.tayyarah.flight.quotation.dao.FlightTravelRequestDao;
import com.tayyarah.flight.quotation.entity.FlightQuotationHistory;
import com.tayyarah.flight.util.api.tbo.TboCommonUtil;
import com.tayyarah.hotel.dao.HotelCreditNoteDao;
import com.tayyarah.hotel.dao.HotelOrderRowEmailDao;
import com.tayyarah.hotel.dao.HotelSearchRoomDetailDao;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.HotelEmailInvoiceData;
import com.tayyarah.hotel.quotation.dao.HotelOfflineBookingDao;
import com.tayyarah.hotel.quotation.dao.HotelTravelRequestDao;
import com.tayyarah.hotel.quotation.entity.HotelQuotationHistory;
import com.tayyarah.insurance.dao.InsuranceCreditNoteDao;
import com.tayyarah.insurance.dao.InsuranceOrderRowEmailDao;
import com.tayyarah.misellaneous.dao.MiscellaneousCreditNoteDao;
import com.tayyarah.misellaneous.dao.MiscellaneousOrderRowEmailDao;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.train.dao.TrainCreditNoteDao;
import com.tayyarah.train.dao.TrainOrderRowEmailDao;
import com.tayyarah.user.dao.WalletTransferHistoryDAO;
import com.tayyarah.user.entity.User;
import com.tayyarah.visa.dao.VisaCreditNoteDao;
import com.tayyarah.visa.dao.VisaOrderRowEmailDao;


@Controller
@RequestMapping(value = "/Email")
public class Emailcontroller {

	public static final Logger logger = Logger.getLogger(Emailcontroller.class);

	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailContentService emailContentService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	FlightOrderRowEmailDao flightOrderRowEmailDao;
	@Autowired
	BusOrderRowEmailDao busOrderRowEmailDao;

	@Autowired
	CarOrderRowEmailDao carOrderRowEmailDao;

	@Autowired
	VisaOrderRowEmailDao visaOrderRowEmailDao;

	@Autowired
	InsuranceOrderRowEmailDao insuranceOrderRowEmailDao;

	@Autowired
	TrainOrderRowEmailDao trainOrderRowEmailDao;

	@Autowired
	HotelOrderRowEmailDao hotelOrderRowEmailDao;

	@Autowired
	FlightInvoiceData flightInvoiceData;

	@Autowired
	HotelEmailInvoiceData hotelEmailInvoiceData;

	@Autowired
	AllEmailDao allEmailDao;
	@Autowired
	EmailDao emailDao;

	@Autowired
	EmailNotificationDao emailNotificationDao;

	@Autowired
	WalletTransferHistoryDAO walletDAO;

	@Autowired
	FlightCreditNoteDao flightCreditNoteDao;

	@Autowired
	HotelCreditNoteDao hotelCreditNoteDao;

	@Autowired
	CarCreditNoteDao carCreditNoteDao;

	@Autowired
	TrainCreditNoteDao trainCreditNoteDao;

	@Autowired
	BusCreditNoteDao busCreditNoteDao;

	@Autowired
	InsuranceCreditNoteDao insuranceCreditNoteDao;

	@Autowired
	VisaCreditNoteDao visaCreditNoteDao;



	@Autowired
	FlightBookingDao flightBookingDao;

	@Autowired
	HotelTravelRequestDao hotelTravelRequestDao;

	@Autowired
	FlightTravelRequestDao flightTravelRequestDao;

	@Autowired
	HotelOfflineBookingDao hotelOfflineBookingDao;

	@Autowired
	HotelSearchRoomDetailDao hotelSearchRoomDetailDao;

	@Autowired
	private NotificationDAO notificationDAO;

	@Autowired
	private HotelAndFlightPaymentTransctionDAO hotelAndFlightPaymentTransctionDAO;

	@Autowired
	FlightEmailHelper flightEmailHelper;

	@Autowired
	HotelEmailHelper hotelEmailHelper;

	@Autowired
	HotelQuotationEmailHelper hotelQuotationEmailHelper;

	@Autowired
	FlightQuotationEmailHelper flightQuotationEmailHelper;

	@Autowired
	CarEmailHelper carEmailHelper;

	@Autowired
	BusEmailHelper busEmailHelper;

	@Autowired
	TrainEmailHelper trainEmailHelper;

	@Autowired
	VisaEmailHelper visaEmailHelper;

	@Autowired
	InsuranceEmailHelper insuranceEmailHelper;

	@Autowired
	RmConfigDetailDAO rmConfigDetailDAO;

	@Autowired
	CompanyDao companyDAO; 

	@Autowired
	MiscellaneousEmailHelper miscellaneousEmailHelper;

	@Autowired
	MiscellaneousOrderRowEmailDao miscellaneousOrderRowEmailDao;

	@Autowired
	MiscellaneousCreditNoteDao miscellaneousCreditNoteDao;

	@Autowired
	TayyarahUmrahEmailHelper  tayyarahUmrahEmailHelper;

	LocalTime startTime = null;

	private String filename = "/home/profilepics/";

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@RequestMapping(value = "/insertemail", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String insertemail(HttpServletRequest request, HttpServletResponse response) {
		emailDao.insertEmail("REG232323", 0, Email.EMAIL_TYPE_FRONT_USER_REGISTRATION);
		return "success";
	}

	@RequestMapping(value = "/sendMailsOld", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String sendMailsOld(HttpServletRequest request, HttpServletResponse response) {
		ResponseHeader.setResponse(response);// Setting response header
		final Locale locale = LocaleContextHolder.getLocale();
		List<Email> emails = null;
		try {

			emails = emailDao.getPendingMails();
			if (emails != null && emails.size() > 0) {
				logger.info("list pending emails count- --: " + emails.size());
				// emailsTemp = emailDao.emailStatus(-1);
				// emails.addAll(emails);
				/*
				 * for (int i = 0; i < emails.size(); i++) {
				 * logger.info(emails.get(i).getOrderId()); }
				 */
				List<Integer> emailStatusIds = sendAndUpdate(emails, request, response, locale);
				// logger.info("---------emailStatusIds
				// SIZE--------"+emailStatusIds.size());
			}
		} catch (Exception e) {
			logger.info("pending emails retrival...Exception " + e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}

	/*
	 * This method will be called by a schedular every 60 seconds this is root
	 * for email part this will retrieve all pending emails from table call
	 * corresponding email sending methods by calling sendAndUpdate
	 */
	@RequestMapping(value = "/sendMails", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String sendMails(HttpServletRequest request, HttpServletResponse response) {
		ResponseHeader.setResponse(response);// Setting response header
		final Locale locale = LocaleContextHolder.getLocale();
		List<Email> emails = null;
		try {

			emails = emailDao.getPendingMails();
			if (emails != null && emails.size() > 0) {			
				List<Integer> emailStatusIds = sendAndUpdate(emails, request, response, locale);			
			}
		} catch (Exception e) {
			logger.info("pending emails retrival...Exception " + e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping(value = "/sendFailedMails", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String sendFailedMails(@RequestParam(value = "status", defaultValue = "3") int status,
			@RequestParam(value = "maxAttempt", defaultValue = "5") int maxAttempt,
			@RequestParam(value = "maxQueueLimit", defaultValue = "5") int maxQueueLimit,
			@RequestParam(value = "type", defaultValue = "1") int type,
			@RequestParam(value = "orderid", defaultValue = "1") String orderid, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseHeader.setResponse(response);// Setting response header
		final Locale locale = LocaleContextHolder.getLocale();
		List<Email> emails = null;
		try {

			emails = emailDao.getFailedMails(status, maxAttempt, maxQueueLimit, type, orderid);
			if (emails != null && emails.size() > 0) {
				logger.info("list failed emails count- --: " + emails.size());
				List<Integer> emailStatusIds = sendAndUpdate(emails, request, response, locale);

			}
		} catch (Exception e) {
			logger.info("failed emails retrival...Exception " + e.getMessage());
			e.printStackTrace();
		}
		return "success";

	}

	/*
	 * This method is created for forcefull email sending test
	 *
	 * @orderid is needed as a getparameter which is email @id from email table
	 * call corresponding email sending methods based on their type and update
	 * the status
	 */
	@RequestMapping(value = "/forceTest", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String sendFailedMails(@RequestParam(value = "orderid", defaultValue = "1") Long orderid,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseHeader.setResponse(response);// Setting response header
		final Locale locale = LocaleContextHolder.getLocale();
		List<Email> emails = null;
		try {

			emails = emailDao.getMailsById(orderid);
			if (emails != null && emails.size() > 0) {
				logger.info("list failed emails count- --: " + emails.size());
				List<Integer> emailStatusIds = sendAndUpdate(emails, request, response, locale);

			}
		} catch (Exception e) {
			logger.info("failed emails retrival...Exception " + e.getMessage());
			e.printStackTrace();
		}
		return "success";

	}

	@RequestMapping(value = "/getHtmlTemplateById", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String getHtmlTemplateById(@RequestParam(value = "orderid", defaultValue = "1") String orderid,
			@RequestParam(value = "emailType", defaultValue = "1") int emailType, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseHeader.setResponse(response);// Setting response header
		final Locale locale = LocaleContextHolder.getLocale();
		String htmlContent = null;
		try {

			Email email = new Email();
			email.setOrderId(orderid);
			email.setType(emailType);
			email.setOnlyHtmlContent(true);
			htmlContent = checkHtmlContentType(email, request, response, locale);
		} catch (Exception e) {
			logger.info("failed emails retrival...Exception " + e.getMessage());
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(htmlContent))
			return htmlContent;
		else
			return "Data is not avaible currently, Plese try again later";

	}

	/*
	 * This method will be called by a sendMails call corresponding email
	 * sending methods based on their type and update the status
	 */
	public List<Integer> sendAndUpdate(List<Email> emails, HttpServletRequest request, HttpServletResponse response,
			Locale locale) {
		List<Integer> statusIds = new ArrayList<Integer>();
		for (Email email : emails) {
			if (email != null) {
				try {
					switch (email.getType()) {

					case Email.EMAIL_TYPE_USER_REGISTRATION:
						UserEmailHelper.sendUserRegistrationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_USER_REGISTRATION--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_USER_CREDENTIALS:
						UserEmailHelper.sendUserCredentialEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_USER_REGISTRATION--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_USER_FORGOT_PWD_REGISTRATION:
						UserEmailHelper.UserForgotPasswordEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_USER_REGISTRATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_COMPANY_REGISTRATION:
						CompanyEmailHelper.sendCompanyRegistrationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_COMPANY_REGISTRATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_COMPANY_FORGOT_PWD:
						CompanyEmailHelper.sendCompanyForgotePasswordEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_COMPANY_FORGOT_PWD--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FRONT_USER_REGISTRATION:
						FrontUserEmailHelper.sendFrontUserRegistrationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FRONT_USER_REGISTRATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FRONT_USER_FORGOT_PWD:
						FrontUserEmailHelper.sendFrontUserForgotPassswordEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FRONT_USER_FORGOT_PWD--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_COMPANY_APPROVAL:
						CompanyEmailHelper.sendCompanyDetailsEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_COMPANY_APPROVAL--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_BLOCKED_USER:
						UserEmailHelper.sendLockedUserEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_BLOCKED_USER--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_WHITE_LABEL:
						CompanyEmailHelper.sendWhiteLabelCompanyEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_WHITE_LABEL--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_USER_RESET_PASSWORD:
						UserEmailHelper.sendUserResetPassword(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_USER_RESET_PASSWORD--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_COMPANY_RESET_PASSWORD:
						CompanyEmailHelper.sendComapnyResetPassword(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_COMPANY_RESET_PASSWORD--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_WALLET_CREDIT_CHILD_NOTIFICATION:
						CompanyEmailHelper.sendWalletNoticationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext,walletDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_WALLET_CREDIT_CHILD_NOTIFICATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_WALLET_CREDIT_PARENT_NOTIFICATION:
						CompanyEmailHelper.sendWalletNoticationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext,walletDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_WALLET_CREDIT_PARENT_NOTIFICATION--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_WALLET_DEBIT_CHILD_NOTIFICATION:
						CompanyEmailHelper.sendWalletNoticationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext,walletDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_WALLET_DEBIT_CHILD_NOTIFICATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_WALLET_DEBIT_PARENT_NOTIFICATION:
						CompanyEmailHelper.sendWalletNoticationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext,walletDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_WALLET_DEBIT_PARENT_NOTIFICATION--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_WALLET_DEPOSIT_PARENT_NOTIFICATION:
						CompanyEmailHelper.sendWalletNoticationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext,walletDAO);
						statusIds.add(email.getType());
						break;
					case Email.EMAIL_TYPE_WALLET_DEPOSIT_CHILD_NOTIFICATION:
						CompanyEmailHelper.sendWalletNoticationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext,walletDAO);
						statusIds.add(email.getType());
						break;
					case Email.EMAIL_TYPE_WALLET_WITHDRAW_PARENT_NOTIFICATION:
						CompanyEmailHelper.sendWalletNoticationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext,walletDAO);
						statusIds.add(email.getType());
						break;
					case Email.EMAIL_TYPE_WALLET_WITHDRAW_CHILD_NOTIFICATION:
						CompanyEmailHelper.sendWalletNoticationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext,walletDAO);
						statusIds.add(email.getType());
						break;				
					case Email.EMAIL_TYPE_USER_ENQUERIES:
						FrontUserEmailHelper.sendUserEnqueries(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_USER_ENQUERIES--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_COMPANY_UPDATION:
						CompanyEmailHelper.sendCompanyDetailsEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_COMPANY_UPDATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS:
						flightEmailHelper.sendFlightInvoiceEmail(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FLIGHT_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS:
						flightEmailHelper.sendFlightInvoiceEmail(email, request, response, locale,Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FLIGHT_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FLIGHT_VOUCHER:
						flightEmailHelper.sendFlightVoucherEmail(false, email, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,false);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FLIGHT_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FAILS_FLIGHT_BOOK:
						flightEmailHelper.sendFlightVoucherEmail(true, email, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,false);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FAILS_FLIGHT_BOOK--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FLIGHT_OFFLINE_ONLINE_INVOICE_SEND:
						flightEmailHelper.sendFlightOffline_OnlineInvoice(false, email, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_HOTEL_FLIGHT_VOUCHER_SEND--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FLIGHT_OFFLINE_REQUEST_QUOTATION:
						logger.info("-EMAIL_TYPE_FLIGHT_OFFLINE_REQUEST_QUOTATION :::email.getOrderId()----"
								+ email.getOrderId());
						flightQuotationEmailHelper.sendFlightQuotations(email, request, response, locale,flightTravelRequestDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FLIGHT_OFFLINE_REQUEST_QUOTATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FLIGHT_QUOTATION_ALTERNATIVE_EMAIL_SEND:
						FlightQuotationHistory newFlightQuotationHistory = flightTravelRequestDao
						.getFlightQuotationHistory(Long.valueOf(email.getOrderId()));
						if (newFlightQuotationHistory != null) {
							flightQuotationEmailHelper.sendToFlightAlternativeEmails(email ,newFlightQuotationHistory, request, response, locale,flightTravelRequestDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
						}
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FLIGHT_QUOTATION_ALTERNATIVE_EMAIL_SEND--------"
								+ statusIds.size());
						break;
					case Email.EMAIL_TYPE_HOTEL_INVOICE_SUPER_TO_OTHERS:
						hotelEmailHelper.sendHotelInvoiceEmail(email, request, response, locale ,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_HOTEL_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_HOTEL_INVOICE_DISTRIBUTOR_TO_OTHERS:
						hotelEmailHelper.sendHotelInvoiceEmail(email, request, response, locale ,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_HOTEL_INVOICE--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_HOTEL_VOUCHER:
						hotelEmailHelper.sendHotelVoucherEmail(false, email, request, response, locale,hotelSearchRoomDetailDao,hotelTravelRequestDao,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_HOTEL_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FAILS_HOTEL_BOOK:
						hotelEmailHelper.sendHotelVoucherEmail(true, email, request, response, locale,hotelSearchRoomDetailDao,hotelTravelRequestDao,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FAILS_HOTEL_BOOK--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_HOTEL_OFFLINE_ONLINE_INVOICE_SEND:
						hotelEmailHelper.sendHotelOffline_OnlineInvoice(email, request, response, locale,hotelSearchRoomDetailDao,hotelTravelRequestDao,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_HOTEL_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_HOTEL_OFFLINE_REQUEST_QUOTATION:
						hotelQuotationEmailHelper.sendHotelQuotations(email, request, response, locale,hotelTravelRequestDao,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_HOTEL_OFFLINE_REQUEST_QUOTATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_HOTEL_QUOTATION_ALTERNATIVE_EMAIL_SEND:
						HotelQuotationHistory newHotelQuotationHistory = hotelTravelRequestDao
						.getHotelQuotationHistory(Long.valueOf(email.getOrderId()));
						if (newHotelQuotationHistory != null) {
							hotelQuotationEmailHelper.sendToHotelAlternativeEmails(email, newHotelQuotationHistory, request, response, locale,hotelTravelRequestDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
						}
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_HOTEL_QUOTATION_ALTERNATIVE_EMAIL_SEND--------"
								+ statusIds.size());
						break;
					case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_HOTEL:
						HotelEmailHelper.sendCreditNoteRequestMailHotel(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,hotelCreditNoteDao);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_HOTEL--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL:
						HotelEmailHelper.sendCreditNoteIssueMailHotel(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,hotelCreditNoteDao, hotelSearchRoomDetailDao, rmConfigDetailDAO,companyDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_FLIGHT:
						FlightEmailHelper.sendCreditNoteRequestMailFlight(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,flightCreditNoteDao,emailContentService);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_FLIGHT--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_FLIGHT:
						FlightEmailHelper.sendCreditNoteIssueMailFlight(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,flightCreditNoteDao,emailContentService, rmConfigDetailDAO,companyDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_FLIGHT--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_CAR:
						CarEmailHelper.sendCreditNoteRequestMailCar(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,carOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,carCreditNoteDao);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_CAR--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR:
						CarEmailHelper.sendCreditNoteIssueMailCar(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,carOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,carCreditNoteDao, rmConfigDetailDAO,companyDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_MISCELLANEOUS:
						MiscellaneousEmailHelper.sendCreditNoteRequestMailMiscellaneous(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,miscellaneousOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,miscellaneousCreditNoteDao);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_CAR--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_MISCELLANEOUS:
						MiscellaneousEmailHelper.sendCreditNoteIssueMailMiscellaneous(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,miscellaneousOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,miscellaneousCreditNoteDao, rmConfigDetailDAO,companyDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_TRAIN:
						TrainEmailHelper.sendCreditNoteRequestMailTrain(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,trainOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,trainCreditNoteDao,emailContentService);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_TRAIN--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_TRAIN:
						TrainEmailHelper.sendCreditNoteIssueMailTrain(email, request, response, locale, Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS, trainOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService ,trainCreditNoteDao, rmConfigDetailDAO,companyDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_TRAIN--------" + statusIds.size());
						break;	

					case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_BUS:
						BusEmailHelper.sendCreditNoteRequestMailBus(email, request, response, locale, Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS, busOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService, busCreditNoteDao);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_BUS--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_BUS:
						BusEmailHelper.sendCreditNoteIssueMailBus(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,busOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,busCreditNoteDao, rmConfigDetailDAO,companyDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_BUS--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_INSURANCE:
						InsuranceEmailHelper.sendCreditNoteRequestMailInsurance(email, request, response, locale, Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS, insuranceOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService, insuranceCreditNoteDao);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_INSURANCE--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_INSURANCE:
						InsuranceEmailHelper.sendCreditNoteIssueMailInsurance(email, request, response, locale, Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS, insuranceOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService, insuranceCreditNoteDao, rmConfigDetailDAO,companyDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_INSURANCE--------" + statusIds.size());
						break;		
					case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_VISA:
						VisaEmailHelper.sendCreditNoteRequestMailVisa(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,visaOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,visaCreditNoteDao);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_VISA--------" + statusIds.size());
						break;

					case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_VISA:
						VisaEmailHelper.sendCreditNoteIssueMailVisa(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,visaOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,visaCreditNoteDao, rmConfigDetailDAO,companyDAO);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_VISA--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_CREATED:
						BugEmailHelper.sendBugHistoryMailToTechHead(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("--------EEMAIL_TYPE_BUG_TRACKER_HISTORY_CREATED--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED:
						logger.info("--------Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED-----------"
								+ Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED);
						BugEmailHelper.sendBugHistoryMailToTechHead(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_PENDING:
						logger.info("--------Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED-----------"
								+ Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED);
						BugEmailHelper.sendBugHistoryMailToTechHead(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_STILL_IN_PROGRESS:
						logger.info("--------Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED-----------"
								+ Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED);
						BugEmailHelper.sendBugHistoryMailToTechHead(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_WORK_IN_PROGRESS:
						logger.info("--------Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED-----------"
								+ Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED);
						BugEmailHelper.sendBugHistoryMailToTechHead(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_TEST_REVIEW:
						logger.info("--------Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED-----------"
								+ Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED);
						BugEmailHelper.sendBugHistoryMailToTechHead(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED--------" + statusIds.size());
						break;


					case Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_REVIEW:
						logger.info("--------Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED-----------"
								+ Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED);
						BugEmailHelper.sendBugHistoryMailToTechHead(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_CLOSED:
						logger.info("--------Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED-----------"
								+ Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED);
						BugEmailHelper.sendBugHistoryMailToTechHead(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_SEND_NOTIFICATION:
						System.out.println("----------EMAIL_TYPE_SEND_NOTIFICATION------------:"
								+ Email.EMAIL_TYPE_SEND_NOTIFICATION);

					case Email.EMAIL_TYPE_CAR_INVOICE:
						logger.info("--------Email.EMAIL_TYPE_CAR_INVOICE-----------" + Email.EMAIL_TYPE_CAR_INVOICE);
						carEmailHelper.sendCarInvoiceEmail(email, request, response, locale,carOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_CAR_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_VISA_INVOICE:
						logger.info("--------Email.EMAIL_TYPE_VISA_INVOICE-----------" + Email.EMAIL_TYPE_VISA_INVOICE);
						visaEmailHelper.sendVisaInvoiceEmail(email, request, response, locale,visaOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_VISA_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_INSURANCE_INVOICE:
						logger.info("--------Email.EMAIL_TYPE_INSURANCE_INVOICE-----------"
								+ Email.EMAIL_TYPE_INSURANCE_INVOICE);
						insuranceEmailHelper.sendInsuranceInvoiceEmail(email, request, response, locale,insuranceOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_INSURANCE_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_LABOR_INVOICE:
						logger.info(
								"--------Email.EMAIL_TYPE_LABOR_INVOICE-----------" + Email.EMAIL_TYPE_LABOR_INVOICE);
						//	sendLaborInvoiceEmail(email, request, response, locale);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_LABOR_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_MISC_INVOICE:
						logger.info("--------Email.EMAIL_TYPE_MISC_INVOICE-----------" + Email.EMAIL_TYPE_MISC_INVOICE);
						miscellaneousEmailHelper.sendMiscellaneousInvoiceEmail(email, request, response, locale, miscellaneousOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService, rmConfigDetailDAO);
						//sendMiscellaneousInvoiceEmail(email, request, response, locale);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_MISC_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_TRAIN_INVOICE:
						logger.info(
								"--------Email.EMAIL_TYPE_TRAIN_INVOICE-----------" + Email.EMAIL_TYPE_TRAIN_INVOICE);
						trainEmailHelper.sendTrainInvoiceEmail(email, request, response, locale,trainOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_TRAIN_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_BUS_INVOICE:
						logger.info(
								"--------Email.EMAIL_TYPE_TRAIN_INVOICE-----------" + Email.EMAIL_TYPE_TRAIN_INVOICE);
						busEmailHelper.sendBusEmail(email, request, response, locale,busOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_TRAIN_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_MEAL_INVOICE:
						statusIds.add(email.getType());
						logger.info("--------EMAIL_TYPE_MEAL_INVOICE--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_BUS_VOUCHER:
						busEmailHelper.sendBusVoucherEmail(email, request, response, locale, busOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService);
						statusIds.add(email.getType());
						break; 
					case Email.EMAIL_TYPE_FLIGHT_24HOURS_NOTIFICATION:
						flightEmailHelper.sendFlightVoucherEmail(false, email, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,true);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FLIGHT_24HOURS_NOTIFICATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FLIGHT_4HOURS_NOTIFICATION:
						flightEmailHelper.sendFlightVoucherEmail(false, email, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,true);
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FLIGHT_4HOURS_NOTIFICATION--------" + statusIds.size());
						break;
					case Email.EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH:
						FrontUserEmailHelper.sendFrontUserRegistrationEmail(email, request, response, locale,emailDao,emailService,allEmailDao,applicationContext,servletContext);
						logger.info("---------EMAIL_TYPE_FLIGHT_4HOURS_NOTIFICATION--------" + statusIds.size());
						break;
						
					case Email.EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH_UMRAH:
						tayyarahUmrahEmailHelper.sendTayyarahUmrahEnquiryEmail (email, request, response, locale,emailService,allEmailDao,applicationContext,servletContext  );
						statusIds.add(email.getType());
						logger.info("---------EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH_UMRAH--------" + statusIds.size());
						break;
					default:
						break;

					}

					// Successfully sent
					logger.info("####### email sent sucussfully...  ");
					email.setStatusMsg("Successfully Sent");
					email.setStatus(Email.STATUS_SENT_SUCCESS);
					emailDao.emailUpdateStatus(email);

				} // every email sending should be checked for exceptions like
				// MessagingException, MailException, NullPointerException,
				// UnsupportedEncodingException , DocumentException,
				// IOException, Exception
				catch (MessagingException e) {
					// Update email failed...
					logger.info("#############Email Failed :: MessagingException");
					logger.error(e);
					email.setStatusMsg("Email Failed :: MessagingException");
					email.setStatus(Email.STATUS_EMAIL_BLOCKED);
					emailDao.emailUpdateStatus(email);
					e.printStackTrace();
				} catch (MailException e) {
					// Update email failed...
					logger.info("#############Email Failed :: MailException");
					logger.error(e);
					email.setStatusMsg("Email Failed :: MailException");
					email.setStatus(Email.STATUS_SENT_ERROR_WRONG_EMAIL);
					emailDao.emailUpdateStatus(email);
					e.printStackTrace();
				} catch (NullPointerException e) {
					// Update email failed...
					logger.info("#############Email Failed :: NullPointerException");
					logger.error(e);
					email.setStatusMsg("Email Failed :: NullPointerException");
					email.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailDao.emailUpdateStatus(email);
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// Update email failed...
					logger.info("#############Email Failed :: UnsupportedEncodingException");
					logger.error(e);
					email.setStatusMsg("Email Failed :: UnsupportedEncodingException");
					email.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailDao.emailUpdateStatus(email);
					e.printStackTrace();
				} catch (DocumentException e) {
					// Update email failed...
					logger.info("#############Email Failed :: DocumentException");
					logger.error(e);
					email.setStatusMsg("Email Failed :: DocumentException");
					email.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailDao.emailUpdateStatus(email);
					e.printStackTrace();
				} catch (IOException e) {
					// Update email failed...
					logger.info("#############Email Failed :: IOException");
					logger.error(e);
					email.setStatusMsg("Email Failed :: IOException");
					email.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailDao.emailUpdateStatus(email);
					e.printStackTrace();
				} catch (Exception e) {
					// Update email failed...
					logger.info("#############Email Failed :: IOException");
					logger.error(e);
					email.setStatusMsg("Email Failed :: IOException");
					email.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailDao.emailUpdateStatus(email);
					e.printStackTrace();
				}
			}
		}
		return statusIds;
	}

	public String checkHtmlContentType(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale) throws MailException, NullPointerException, UnsupportedEncodingException, DocumentException,
	IOException, Exception {
		List<Integer> statusIds = new ArrayList<Integer>();
		String htmlContent = null;
		if (email != null) {
			try {
				switch (email.getType()) {
				case Email.EMAIL_TYPE_FLIGHT_VOUCHER:
					htmlContent = flightEmailHelper.sendFlightVoucherEmail(false, email, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,false);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_FLIGHT_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_HOTEL_VOUCHER:
					htmlContent = hotelEmailHelper.sendHotelVoucherEmail(false, email, request, response, locale,hotelSearchRoomDetailDao,hotelTravelRequestDao,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_HOTEL_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS:
					flightEmailHelper.sendFlightInvoiceEmail(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_FLIGHT_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS:
					flightEmailHelper.sendFlightInvoiceEmail(email, request, response, locale,Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_FLIGHT_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_HOTEL_INVOICE_SUPER_TO_OTHERS:
					hotelEmailHelper.sendHotelInvoiceEmail(email, request, response, locale ,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_HOTEL_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_HOTEL_INVOICE_DISTRIBUTOR_TO_OTHERS:
					hotelEmailHelper.sendHotelInvoiceEmail(email, request, response, locale ,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_HOTEL_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_FAILS_HOTEL_BOOK:
					hotelEmailHelper.sendHotelVoucherEmail(true, email, request, response, locale,hotelSearchRoomDetailDao,hotelTravelRequestDao,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_FAILS_HOTEL_BOOK--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_FAILS_FLIGHT_BOOK:
					flightEmailHelper.sendFlightVoucherEmail(true, email, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,false);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_FAILS_FLIGHT_BOOK--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_FLIGHT_OFFLINE_ONLINE_INVOICE_SEND:
					logger.info("-EMAIL_TYPE_FLIGHT_OFFLINE_ONLINE_VOUCHER_SEND :::email.getOrderId()----"
							+ email.getOrderId());
					htmlContent = flightEmailHelper.sendFlightOffline_OnlineInvoice(false, email, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_FLIGHT_OFFLINE_ONLINE_INVOICE_SEND--------" + statusIds.size());
					break;

				case Email.EMAIL_TYPE_HOTEL_OFFLINE_ONLINE_INVOICE_SEND:
					htmlContent = hotelEmailHelper.sendHotelOffline_OnlineInvoice(email, request, response, locale,hotelSearchRoomDetailDao,hotelTravelRequestDao,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_HOTEL_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_CAR_INVOICE:
					logger.info("--------Email.EMAIL_TYPE_CAR_INVOICE-----------" + Email.EMAIL_TYPE_CAR_INVOICE);
					htmlContent=carEmailHelper.sendCarInvoiceEmail(email, request, response, locale,carOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					statusIds.add(email.getType());
					logger.info("--------EMAIL_TYPE_CAR_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_VISA_INVOICE:
					logger.info("--------Email.EMAIL_TYPE_VISA_INVOICE-----------" + Email.EMAIL_TYPE_VISA_INVOICE);
					htmlContent=visaEmailHelper.sendVisaInvoiceEmail(email, request, response, locale,visaOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					statusIds.add(email.getType());
					logger.info("--------EMAIL_TYPE_VISA_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_INSURANCE_INVOICE:
					logger.info("--------Email.EMAIL_TYPE_INSURANCE_INVOICE-----------"
							+ Email.EMAIL_TYPE_INSURANCE_INVOICE);
					htmlContent=insuranceEmailHelper.sendInsuranceInvoiceEmail(email, request, response, locale,insuranceOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					statusIds.add(email.getType());
					logger.info("--------EMAIL_TYPE_INSURANCE_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_LABOR_INVOICE:
					logger.info("--------Email.EMAIL_TYPE_LABOR_INVOICE-----------" + Email.EMAIL_TYPE_LABOR_INVOICE);
					//					sendLaborInvoiceEmail(email, request, response, locale);
					statusIds.add(email.getType());
					logger.info("--------EMAIL_TYPE_LABOR_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_MISC_INVOICE:
					logger.info("--------Email.EMAIL_TYPE_MISC_INVOICE-----------" + Email.EMAIL_TYPE_MISC_INVOICE);
					htmlContent= miscellaneousEmailHelper.sendMiscellaneousInvoiceEmail(email, request, response, locale,miscellaneousOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					statusIds.add(email.getType());
					logger.info("--------EMAIL_TYPE_MISC_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_TRAIN_INVOICE:
					logger.info("--------Email.EMAIL_TYPE_TRAIN_INVOICE-----------" + Email.EMAIL_TYPE_TRAIN_INVOICE);
					htmlContent=trainEmailHelper.sendTrainInvoiceEmail(email, request, response, locale,trainOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					statusIds.add(email.getType());
					logger.info("--------EMAIL_TYPE_TRAIN_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_BUS_INVOICE:
					logger.info("--------Email.EMAIL_TYPE_BUS_INVOICE-----------" + Email.EMAIL_TYPE_BUS_INVOICE);
					htmlContent=busEmailHelper.sendBusEmail(email, request, response, locale,busOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					statusIds.add(email.getType());
					logger.info("--------EMAIL_TYPE_BUS_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_MEAL_INVOICE:
					logger.info("--------Email.EMAIL_TYPE_MEAL_INVOICE-----------" + Email.EMAIL_TYPE_MEAL_INVOICE);
					//					sendMealEmail(email, request, response, locale);
					statusIds.add(email.getType());
					logger.info("--------EMAIL_TYPE_MEAL_INVOICE--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL:
					htmlContent=HotelEmailHelper.sendCreditNoteIssueMailHotel(email, request, response, locale,Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,hotelCreditNoteDao, hotelSearchRoomDetailDao, rmConfigDetailDAO, companyDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL--------" + statusIds.size());
					break;

				case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_FLIGHT:
					htmlContent=FlightEmailHelper.sendCreditNoteIssueMailFlight(email, request, response, locale,Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_FLIGHT,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,flightCreditNoteDao,emailContentService, rmConfigDetailDAO,companyDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_FLIGHT--------" + statusIds.size());
					break;
				case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR:
					htmlContent= CarEmailHelper.sendCreditNoteIssueMailCar(email, request, response, locale,Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR,carOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,carCreditNoteDao, rmConfigDetailDAO,companyDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR--------" + statusIds.size());
					break;	

				case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_TRAIN:
					htmlContent= TrainEmailHelper.sendCreditNoteIssueMailTrain(email, request, response, locale, Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR, trainOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService, trainCreditNoteDao, rmConfigDetailDAO,companyDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_CREDITNOTE_REQUEST_TRAIN--------" + statusIds.size());
					break;	

				case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_BUS:
					htmlContent= BusEmailHelper.sendCreditNoteIssueMailBus(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,busOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,busCreditNoteDao, rmConfigDetailDAO,companyDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_BUS--------" + statusIds.size());
					break;

				case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_INSURANCE:
					htmlContent= InsuranceEmailHelper.sendCreditNoteIssueMailInsurance(email, request, response, locale, Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS, insuranceOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService, insuranceCreditNoteDao, rmConfigDetailDAO,companyDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_INSURANCE--------" + statusIds.size());
					break;		
				case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_VISA:
					htmlContent= VisaEmailHelper.sendCreditNoteIssueMailVisa(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,visaOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,visaCreditNoteDao, rmConfigDetailDAO,companyDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_VISA--------" + statusIds.size());
					break;	
				case Email.EMAIL_TYPE_BUS_VOUCHER:
					htmlContent = busEmailHelper.sendBusVoucherEmail(email, request, response, locale, busOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService);
					statusIds.add(email.getType());
					break;
				case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_MISCELLANEOUS:
					htmlContent=MiscellaneousEmailHelper.sendCreditNoteIssueMailMiscellaneous(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,miscellaneousOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,miscellaneousCreditNoteDao, rmConfigDetailDAO,companyDAO);
					statusIds.add(email.getType());
					logger.info("---------EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR--------" + statusIds.size());
					break;



				default:
					break;
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		return htmlContent;
	}

	
	public ResponseEntity<byte[]> getImage(String Imgaepath, HttpServletRequest request, HttpServletResponse response)
			throws IOException {


		HttpSession session = request.getSession();
		byte[] imageContent = Imgaepath.getBytes(org.apache.commons.lang3.CharEncoding.UTF_8);
		// get image from DAO based on id

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);		
		session.setAttribute("imagepath", imageContent);
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}

	
	



	/*
	 * @RequestMapping(value = "/sendtest",method =
	 * RequestMethod.GET,headers="Accept=application/json") public @ResponseBody
	 * String sendmail(@RequestParam(value="recipientName",
	 * defaultValue="khalid") String recipientName,
	 *
	 * @RequestParam(value="recipientEmail",
	 * defaultValue="khalidhit2008@gmail.com") String recipientEmail,
	 * HttpServletResponse response) throws MessagingException {
	 * ResponseHeader.setResponse(response);//Setting response header final
	 * Locale locale = LocaleContextHolder.getLocale();
	 * logger.info("Emailcontroller send email call---- locale: "+locale);
	 * logger
	 * .info("Emailcontroller send email call---- recipientName: "+recipientName
	 * ); logger.info("Emailcontroller send email call---- recipientEmail: "+
	 * recipientEmail); String imagesrc =
	 * "http://dev.mylintas.com/images/lintus-header.png"; byte[] imageBytes =
	 * null; try { InputStream inputStream =
	 * this.getClass().getResourceAsStream("/mail/lintas.png"); BufferedImage
	 * bufferedImage = ImageIO.read(inputStream); ByteArrayOutputStream
	 * byteArrayOutputStream = new ByteArrayOutputStream(); ImageIO.write(
	 * bufferedImage , "png", byteArrayOutputStream); imageBytes =
	 * byteArrayOutputStream.toByteArray();
	 *
	 * } catch (IOException e) {
	 * logger.info("logo loading exception----mail/lintas.png--- : "
	 * +e.getLocalizedMessage()); //throw new RuntimeException(e); }
	 *
	 * this.emailService.sendMailWithInline(recipientName, recipientEmail,
	 * MediaType.IMAGE_PNG_VALUE, imagesrc, imageBytes , locale); logger.info(
	 * "Emailcontroller send email call after emailService.sendSimpleMail----  "
	 * );
	 *
	 * return "success";
	 *
	 * }
	 */
	/*
	 * @RequestMapping(value = "/send",method =
	 * RequestMethod.GET,headers="Accept=application/json") public @ResponseBody
	 * String sendmail(@RequestParam(value="orderId",defaultValue=
	 * "OID237f2a4e:14fd9e8a232:-6c58") String orderId, HttpServletRequest
	 * request, HttpServletResponse response) throws MessagingException {
	 * ResponseHeader
	 * .setResponse(response);//defaultValue="OID237f2a4e:14fd9e8a232:-6c58"
	 * Setting response header final Locale locale =
	 * LocaleContextHolder.getLocale(); FlightOrderRow flightorderrow = null;
	 *
	 * try { logger.info("flight order id : "+orderId);
	 *
	 * flightorderrow = flightOrderRowEmailDao.flightOrderRowByOrderId(orderId);
	 * logger.info("flight order row id----- : "+flightorderrow.getId());
	 * List<FlightOrderTripDetail> tripDeatailsList=
	 * flightOrderRowEmailDao.flightTrips(flightorderrow);
	 * logger.info("----------tripDeatailsList----- : "
	 * +tripDeatailsList.size()); FlightInvoiceData flightInvoiceData=new
	 * FlightInvoiceData();
	 * flightInvoiceData.flightOrderPriceBreakup(flightorderrow);
	 *
	 * List<FlightInvoiceData> flightInvoiceList
	 * =flightInvoiceData.getFlightOrderCustomerPriceDetails(flightorderrow);
	 * logger.info("----------flightInvoiceList For OrderCustomers----- : "+
	 * flightInvoiceList.size()); FlightInvoiceData gstTotalPrice
	 * =flightInvoiceData.gstCalcullation(flightorderrow); HttpSession session =
	 * request.getSession(); session.setAttribute("orderId" , orderId);
	 * session.setAttribute("tripDeatailsList" , tripDeatailsList);
	 * session.setAttribute("flightInvoiceList" , flightInvoiceList);
	 *
	 * logger.info(
	 * "----------flightInvoiceData.gstCalcullation(flightorderrow)----- : "
	 * +flightInvoiceData.gstCalcullation(flightorderrow));
	 *
	 * session.setAttribute("gstTotalPrice" , gstTotalPrice);
	 *
	 * logger.info("flight gst price "
	 * +flightInvoiceData.gstCalcullation(flightorderrow).getTotGst());
	 * logger.info("flight gst price "
	 * +flightInvoiceData.gstCalcullation(flightorderrow).getTotBeforeGst());
	 * logger.info("flight gst price "
	 * +flightInvoiceData.gstCalcullation(flightorderrow).getTotWithGst());
	 * session.setAttribute("flightOrderCustomers" , flightOrderCustomers);
	 *
	 * logger.info("----------tripDeatailsList of id for trips----- : "+
	 * flightorderrow.getFlightOrderTripDetails());
	 *
	 * //IWebContext context=new
	 * SpringWebContext(request,response,servletContext
	 * ,locale,variables,applicationContext);
	 * logger.info("Emailcontroller send email call---- locale: "+locale);
	 * logger .info("Emailcontroller send email call---- recipientEmail: "
	 * +flightorderrow .getCustomer().getEmail());
	 *
	 * String imagesrc = "http://dev.mylintas.com/images/lintus-header.png";
	 * byte[] imageBytes = null;
	 *
	 * InputStream inputStream =
	 * this.getClass().getResourceAsStream("/mail/lintas.png"); BufferedImage
	 * bufferedImage = ImageIO.read(inputStream); ByteArrayOutputStream
	 * byteArrayOutputStream = new ByteArrayOutputStream(); ImageIO.write(
	 * bufferedImage , "png", byteArrayOutputStream); imageBytes =
	 * byteArrayOutputStream.toByteArray();
	 *
	 * String mailContent, String baseFolder, String identifier, String id1=
	 * null
	 *
	 *
	 * this.emailService.sendFlightOrderMail(flightorderrow,company,
	 * MediaType.IMAGE_PNG_VALUE,locale, request, response, servletContext,
	 * applicationContext);
	 *
	 * logger.info(
	 * "Emailcontroller send email call after emailService.sendSimpleMail----  "
	 * );
	 *
	 *
	 * } catch (Exception e) { e.printStackTrace(); }
	 *
	 *
	 * return "success";
	 *
	 * }
	 */
	/*
	 * @RequestMapping(value = "/HotelEmail",method =
	 * RequestMethod.GET,headers="Accept=application/json") public @ResponseBody
	 * String sendHotelEmail(@RequestParam(value="id",
	 * defaultValue="HO14473346446800") String id, HttpServletRequest request,
	 * HttpServletResponse response) throws MessagingException {
	 * ResponseHeader.setResponse(response);//Setting response header final
	 * Locale locale = LocaleContextHolder.getLocale(); HotelOrderRow
	 * hotelOrderRowId= null; DateFormat formatter = new
	 * SimpleDateFormat("yyyy-MM-dd");
	 *
	 * try { logger.info("flight order id : "+id);
	 *
	 * hotelOrderRowId = hotelOrderId.hotelOrderRowByOrderId(id);
	 * logger.info("Hotel order row id----- : "+hotelOrderRowId.getId());
	 *
	 * logger.info("Hotel order row id----- : "+hotelOrderRowId.getCheckInDate()
	 * ) ;
	 *
	 * String checkIn = formatter.format(hotelOrderRowId.getCheckInDate());
	 * String checkOut = formatter.format(hotelOrderRowId.getCheckOutDate());
	 *
	 * DateTime dt1 = new DateTime(checkIn); DateTime dt2 = new
	 * DateTime(checkOut); System.out.print(Days.daysBetween(dt1, dt2).getDays()
	 * + " Nights, "); int numberOfNights=Days.daysBetween(dt1, dt2).getDays() ;
	 *
	 *
	 *
	 * List<HotelOrderRoomInfo> roomDeatailsList=
	 * hotelOrderId.roomInfoDeatails(hotelOrderRowId);
	 * logger.info("----------Room DeatailsList----- : "
	 * +roomDeatailsList.size()); for(HotelOrderRoomInfo
	 * roomlits:roomDeatailsList){
	 *
	 * logger.info("----------guest Get ID---- : "+roomlits.getId());
	 *
	 * logger.info("-----------guest Meal Type----- : "+roomlits.getMealType());
	 * }
	 *
	 * List<HotelOrderGuest> guestList=
	 * hotelOrderId.guesInformationDeatails(roomDeatailsList);
	 * logger.info("---------Guest  DeatailsList----- : "+guestList.size());
	 * for(HotelOrderGuest hotelguest:guestList){
	 *
	 * logger.info("----------guest First Name----- : "+hotelguest.getFirstName(
	 * ) );
	 * logger.info("-----------guest Last Name----- : "+hotelguest.getLastName
	 * ()); } HttpSession session = request.getSession();
	 * session.setAttribute("hotelOrderRowId" , hotelOrderRowId);
	 * session.setAttribute("roomDeatailsList" , roomDeatailsList);
	 * session.setAttribute("guestList" , guestList);
	 * session.setAttribute("numberOfNights" , numberOfNights);
	 * this.emailService.sendHotelOrderMail(hotelOrderRowId,
	 * MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
	 * applicationContext); logger.info(
	 * "Emailcontroller send email call after emailService.sendSimpleMail----  "
	 * );
	 *
	 *
	 * } catch (Exception e) { e.printStackTrace(); }
	 *
	 *
	 * return "success";
	 *
	 * }
	 */

	/*
	 * @RequestMapping(value = "/sendflightinvoice",method =
	 * RequestMethod.GET,headers="Accept=application/json") public @ResponseBody
	 * String sendFlightInvoiceMail(@RequestParam(value="orderId",
	 * defaultValue="OID237f2a4e:14fd9e8a232:-6c58") String orderId,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * MessagingException { ResponseHeader.setResponse(response);//Setting
	 * response header final Locale locale = LocaleContextHolder.getLocale();
	 * FlightOrderRow flightorderrow = null; try {
	 * logger.info("flight order id : "+orderId); flightorderrow =
	 * flightOrderRowEmailDao.flightOrderRowByOrderId(orderId);
	 *
	 * HttpSession session = request.getSession();
	 * session.setAttribute("orderId" , orderId); //IWebContext context=new
	 * SpringWebContext
	 * (request,response,servletContext,locale,variables,applicationContext);
	 * logger.info("Emailcontroller send email call---- locale: "+locale);
	 * logger .info("Emailcontroller send email call---- recipientEmail: "
	 * +flightorderrow .getCustomer().getEmail());
	 *
	 * String imagesrc = "http://dev.mylintas.com/images/lintus-header.png";
	 * byte[] imageBytes = null;
	 *
	 * InputStream inputStream =
	 * this.getClass().getResourceAsStream("/mail/lintas.png"); BufferedImage
	 * bufferedImage = ImageIO.read(inputStream); ByteArrayOutputStream
	 * byteArrayOutputStream = new ByteArrayOutputStream(); ImageIO.write(
	 * bufferedImage , "png", byteArrayOutputStream); imageBytes =
	 * byteArrayOutputStream.toByteArray();
	 *
	 *
	 *
	 * this.emailService.sendFlightOrderMail(flightorderrow,
	 * MediaType.IMAGE_PNG_VALUE, imagesrc, imageBytes , locale, request,
	 * response, servletContext, applicationContext); logger.info(
	 * "Emailcontroller send email call after emailService.sendSimpleMail----  "
	 * );
	 *
	 *
	 * } catch (IOException e) {
	 * logger.info("logo loading exception----mail/lintas.png--- : "
	 * +e.getLocalizedMessage()); //throw new RuntimeException(e); }catch
	 * (Exception e) { e.printStackTrace(); }
	 *
	 *
	 * return "success";
	 *
	 * }
	 */

	@RequestMapping(value = "/flightTicket", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String sendBookedTicketEmail(HttpServletRequest request, HttpServletResponse response)
			throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
			DocumentException, IOException, Exception {
		ResponseHeader.setResponse(response);
		final Locale locale = LocaleContextHolder.getLocale();
		List<FlightOrderRow> bookedFlight = null;
		HttpSession session = request.getSession();
		BigDecimal totalAmount = new BigDecimal(0.0);
		Map<User, List<FlightOrderRow>> userBookingMap = new HashMap<User, List<FlightOrderRow>>();
		CommonConfig conf = CommonConfig.GetCommonConfig();
		try {

			List<User> userlist = allEmailDao.getPostPaidWalletUser();
			if (userlist != null && userlist.size() > 0) {

				for (User userObj : userlist) {
					session.setAttribute("userlist", userlist);					
					session.setAttribute("Walltet", userObj.getAgentWallet().getWalletbalance());
					bookedFlight = allEmailDao.getLastBookingDateForFlight(userObj.getId());
				}
				if(bookedFlight.size() > 0){
				for (FlightOrderRow flightOrderRow : bookedFlight) {					
					for (User user : userlist) {
						if (String.valueOf(user.getId()).equals(flightOrderRow.getUserId())) {
							userBookingMap.put(user, bookedFlight);
						}
					}
				}
				Set<User> keys = userBookingMap.keySet();
				for (User userObj : keys) {
					session.setAttribute("Address", userObj.getAddress());
					List<FlightOrderRow> bookingList = userBookingMap.get(userObj);
					for (FlightOrderRow flightOrderRow : bookingList) {
						if (String.valueOf(userObj.getId()).equals(flightOrderRow.getUserId())) {
							session.setAttribute("bookedFlight", bookingList);
							session.setAttribute("userlist", userlist);
							session.setAttribute("bookingDate", flightOrderRow.getBookingDate());
							totalAmount = totalAmount.add(flightOrderRow.getFinalPrice());
							session.setAttribute("totalAmount", totalAmount);

						}
					}
					String path = userObj.getImagepath();
					if (path != null) {
						String imagePth = conf.getImage_path() + path;

						this.emailService.sendFlightBookedTicketMail(bookingList, userObj, MediaType.IMAGE_PNG_VALUE,
								locale, request, response, servletContext, applicationContext, imagePth);
					} else {
						String imagePth = conf.getDefult_image_path();

						this.emailService.sendFlightBookedTicketMail(bookingList, userObj, MediaType.IMAGE_PNG_VALUE,
								locale, request, response, servletContext, applicationContext, imagePth);
					}
				}
				}else{
					logger.info("Flight not found ");
				}

			} else {
				logger.info("User not found ");
			}
		}

		catch (NumberFormatException ex) { // handle your exception
			ex.printStackTrace();
		} catch (Exception e) {
			// Update email failed...

			e.printStackTrace();
		}
		return "Sucess";
	}

	// Send Hold Flight Booking Itinerary Before Last Booking Date
	@RequestMapping(value = "/holdflightTicket", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String sendHoldedTicketEmail(HttpServletRequest request, HttpServletResponse response)
			throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
			DocumentException, IOException, Exception {
		ResponseHeader.setResponse(response);

		List<FlightOrderRow> HoldedFlight = null;
		try {
			HoldedFlight = allEmailDao.getLastBookingDateForHoldFlightTickets();
			if (HoldedFlight != null && HoldedFlight.size() > 0) {
				for (FlightOrderRow flightOrderRow : HoldedFlight) {
					logger.info("Hold Ticket Orderid.........." + flightOrderRow.getOrderId());
					FlightBookingKeysTemp flightBookingKeysTemp = null;
					flightBookingKeysTemp = flightBookingDao.getpricekey(flightOrderRow.getTransaction_key());
					if (flightBookingKeysTemp != null) {
						if (flightBookingKeysTemp.isActive()) {
							TboCommonUtil.updateMailstatus(flightOrderRow.getOrderId(), emailDao);
						}
					}
				}
			}

			else {
				logger.info("No Hold Ticket Found.........");
			}
		} catch (NumberFormatException ex) { // handle your exception
			ex.printStackTrace();
		} catch (Exception e) {
			// Update email failed...
			logger.info("Hold Ticket Send Before Last Booking date exception.........." + e);
			e.printStackTrace();
		}
		return "Sucess";
	}

	// Release Flight Itinerary and Send Itinerary After Last Booking Date
	@RequestMapping(value = "/expireflightTicket", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String sendReleaseTicketEmailAfterExpire(HttpServletRequest request,
			HttpServletResponse response) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		ResponseHeader.setResponse(response);

		List<FlightOrderRow> HoldedFlight = null;
		try {
			HoldedFlight = allEmailDao.getExpiredHoldFlightTickets();
			for (FlightOrderRow flightOrderRow : HoldedFlight) {
				logger.info("Hold Ticket Orderid.........." + flightOrderRow.getOrderId());
				FlightBookingKeysTemp flightBookingKeysTemp = null;
				flightBookingKeysTemp = flightBookingDao.getpricekey(flightOrderRow.getTransaction_key());
				if (flightBookingKeysTemp != null) {
					if (flightBookingKeysTemp.isActive()) {
						// {"userId":"tayyarahhelp@intellicommsolutions.com","password":"tayyarah","appKey":"v+iPH5eGGaX8p50qOYkqQQ==","orderId":"FTBO160729105359678","remarks":"Test","methodtype":"1","requesttype":"1","cancellationtype":"3"}

						Company companyobj = allEmailDao.getCompanyByUserId(flightOrderRow.getUserId());
						CompanyConfig companyConfig = allEmailDao.getCompanyConfigByUserId(flightOrderRow.getUserId());

						JSONObject releaseobj = new JSONObject();
						releaseobj.put("userId", flightOrderRow.getUserId());
						releaseobj.put("password", companyobj.getPassword());
						releaseobj.put("appKey", companyConfig.getAppKey());
						releaseobj.put("orderId", flightOrderRow.getOrderId());
						releaseobj.put("remarks", "Auto Release Ticket");
						releaseobj.put("methodtype", "0");
						releaseobj.put("requesttype", "1");
						releaseobj.put("cancellationtype", "3"); 
						try {
							URL url = new URL(CommonConfig.GetCommonConfig().getApi_url() + "cancelticket/response");
							// URL url = new
							// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setDoOutput(true);
							conn.setDoInput(true);
							conn.setRequestMethod("POST");
							conn.setRequestProperty("Content-Type", "application/json");
							conn.setRequestProperty("Access", "application/json");

							OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
							wr.write(releaseobj.toString());
							wr.flush();
							if (conn.getResponseCode() != 200) {
								throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
							}
							BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
							String output;

							// logger.info("Output from Server .... \n");
							while ((output = br.readLine()) != null) {
								// logger.info(output);
								logger.info("Output from Server .... --" + output);
							}
							conn.disconnect();

							/*
							 * ObjectMapper mapper = new ObjectMapper();
							 * CancelTicketResponse cancelTicketResponse =
							 * mapper.readValue(output,
							 * CancelTicketResponse.class);
							 * if(cancelTicketResponse.getBookstatus().equals(
							 * "Released")){
							 *
							 * }
							 */

						} catch (MalformedURLException e) {
							logger.info("--MalformedURLException" + e.getMessage());
							logger.error(e);
							e.printStackTrace();
						} catch (IOException e) {
							logger.info("--IOException" + e.getMessage());
							logger.error(e);
							e.printStackTrace();
						} catch (Exception e) {
							logger.info("--IOException" + e.getMessage());
							logger.error(e);
							e.printStackTrace();
						}

					}
				}
			}
		}

		catch (NumberFormatException ex) { // handle your exception
			ex.printStackTrace();
		} catch (Exception e) {
			// Update email failed...
			logger.info("Release Ticket Send Before Last Booking date exception.........." + e);
			e.printStackTrace();
		}
		return "Sucess";
	}

	@RequestMapping(value = "/hotelTicket", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String sendHotelBookedTicket(HttpServletRequest request, HttpServletResponse response)
			throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
			DocumentException, IOException, Exception {
		ResponseHeader.setResponse(response);
		final Locale locale = LocaleContextHolder.getLocale();
		List<HotelOrderRow> bookedHotel = null;
		HttpSession session = request.getSession();
		BigDecimal totalAmount = new BigDecimal(0.0);
		Map<User, List<HotelOrderRow>> userBookingMap = new HashMap<User, List<HotelOrderRow>>();
		CommonConfig conf = CommonConfig.GetCommonConfig();

		try {
			List<User> userlist = allEmailDao.getPostPaidWalletUser();
			if (userlist != null) {

				for (User userObj : userlist) {

					session.setAttribute("Walltet", userObj.getAgentWallet().getWalletbalance());
					bookedHotel = allEmailDao.getLastBookingDateForHotel(userObj.getId());

				}
			}
			if (bookedHotel != null && bookedHotel.size() > 0) {
				for (HotelOrderRow hotelOrderRow : bookedHotel) {

					for (User user : userlist) {
						if (String.valueOf(user.getId()).equals(hotelOrderRow.getUserId())) {
							userBookingMap.put(user, bookedHotel);
						}
					}
				}

				Set<User> keys = userBookingMap.keySet();
				for (User userObj : keys) { 
					session.setAttribute("Address", userObj.getAddress());
					List<HotelOrderRow> bookingList = userBookingMap.get(userObj);
					for (HotelOrderRow hotelOrderRow : bookingList) {
						if (String.valueOf(userObj.getId()).equals(hotelOrderRow.getUserId())) {
							session.setAttribute("bookedHotel", bookingList);
							session.setAttribute("userlist", userlist);
							session.setAttribute("chekindate", hotelOrderRow.getCheckInDate());
							totalAmount = totalAmount.add(hotelOrderRow.getFinalPrice());
							session.setAttribute("totalAmount", totalAmount);


						}
					}
					String path = userObj.getImagepath();
					if (path != null) {
						String imagePth = conf.getImage_path() + path;

						this.emailService.sendHotelBookedTicketMail(bookingList, userObj, MediaType.IMAGE_PNG_VALUE,
								locale, request, response, servletContext, applicationContext, imagePth);
					} else {
						String imagePth = conf.getDefult_image_path();

						this.emailService.sendHotelBookedTicketMail(bookingList, userObj, MediaType.IMAGE_PNG_VALUE,
								locale, request, response, servletContext, applicationContext, imagePth);
					}
				}


			}

			else {
				logger.info("Hotel details not found ");
			}
		} catch (NumberFormatException ex) { // handle your exception
			ex.printStackTrace();
		} catch (Exception e) {
			// Update email failed...

			e.printStackTrace();
		}
		return "Sucess";
	}

	@RequestMapping(value = "/lastdate/{byId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<FlightOrderRow> getlastBookingDate(@PathVariable("byId") int userId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseHeader.setResponse(response);// Setting response header
		List<FlightOrderRow> imageList = null;

		try {
			imageList = allEmailDao.getLastBookingDateForFlight(userId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageList;
	}

	@RequestMapping(value = "/notificationEmail/", method = RequestMethod.GET, headers = "Accept=application/json")
	public synchronized void sendNotificationMailToUser(HttpServletRequest request, HttpServletResponse response) {

		// newly added date time validation for sending emails
		if (startTime == null) {
			startTime = new LocalTime();
		}
		LocalTime executionTime = new LocalTime();
		if (executionTime.isAfter(startTime) || executionTime.equals(startTime)) {
			LocalTime afterOneMinte = startTime.plusMinutes(1);

			final Locale locale = LocaleContextHolder.getLocale();
			Company company = null;
			User user = null;
			List<Notification> notifications = new ArrayList<>();
			try {
				notifications = notificationDAO.allEmailNotification();
				logger.info("size of notifications to send mail today is" + notifications.size());

				for (Notification notification : notifications) {

					DateFormat defaultdateFormat = new SimpleDateFormat("HH:mm");
					Date timeinterval = notification.getTimeInterval();
					if (timeinterval != null) {
						String notifyinterval = defaultdateFormat.format(timeinterval);
						String currentTime = defaultdateFormat.format(new Date().getTime());
						Date notifyintervaltime = new SimpleDateFormat("HH:mm").parse(notifyinterval);
						Date currentTimemin = new SimpleDateFormat("HH:mm").parse(currentTime);
						if ((notifyintervaltime.compareTo(currentTimemin) == 0)) {
							List<NotificationDetail> notificationDetails = notification.getDetails();
							company = allEmailDao.getCompanyByUserId(String.valueOf(notification.getUserId()));
							user = allEmailDao.getUserByUserId(String.valueOf(notification.getUserId()));
							emailService.sendNotificationMailToUser(company, user, MediaType.IMAGE_PNG_VALUE, locale,
									request, notification, notificationDetails, response, servletContext,
									applicationContext);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date dateWithoutTime = sdf.parse(sdf.format(notification.getToDate()));
							Date todaydateWithoutTime = sdf.parse(sdf.format(new Date()));

							if (dateWithoutTime.compareTo(todaydateWithoutTime) == 0) {
								notificationDAO.makeCustomNotificatyionEmailFalse(notification);
							}

						}
					}

				}
				startTime = afterOneMinte;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * @RequestMapping(value = "/cancelTicket", method = RequestMethod.GET,
	 * headers = "Accept=application/json") public @ResponseBody String
	 * sendCancelTicketEmail( HttpServletRequest request, HttpServletResponse
	 * response) { ResponseHeader.setResponse(response); final Locale locale =
	 * LocaleContextHolder.getLocale(); List<FlightOrderRow> bookedFlight =
	 * null; HttpSession session = request.getSession(); List<User> userlist =
	 * null; List<CreditNote> creditList = null; CommonConfig
	 * conf=CommonConfig.GetCommonConfig(); try { // FlightOrderRow
	 * flightOrderRowList,User postPaidUserList, bookedFlight =
	 * creditnote.flightCancelTripsDeatils();
	 *
	 * if (bookedFlight != null) { logger.info("userlist-----------" +
	 * bookedFlight.size()); for (FlightOrderRow userObj : bookedFlight) {
	 * logger.info("user Walltet----" + userObj.getCreditNote()); //
	 * session.setAttribute("Walltet" , //
	 * userObj.getAgentWallet().getWalletbalance()); if (userObj != null) {
	 * userlist = creditnote.getTicketCancelByUser(userObj); creditList =
	 * creditnote.getCreditNoteByUser(userObj .getCreditNote().getId()); }
	 *
	 * } }
	 *
	 * if (bookedFlight != null && userlist != null && creditList != null) {
	 *
	 * logger.info("bookedFlight-----" + bookedFlight.size());
	 *
	 * for (FlightOrderRow flightOrderRow : bookedFlight) {
	 * logger.info(" flightOrderRow Credite node" +
	 * flightOrderRow.getCreditNote().getId());
	 * logger.info(" flightOrderRow getUserId user" +
	 * flightOrderRow.getUserId()); for (CreditNote credit : creditList) {
	 * logger.info("Credite node" + credit.getId()); for (User userObj :
	 * userlist) { logger.info(" userObj getUserId user" + userObj.getId());
	 *
	 *
	 * if (flightOrderRow.getUserId().equals( userObj.getId())) {
	 * logger.info("correcte"); } else { logger.info("wrong"); }
	 * logger.info(userObj.getEmail() + "------------" +
	 * flightOrderRow.getUserId()); session.setAttribute("bookedFlight",
	 * bookedFlight); session.setAttribute("userlist", userlist);
	 * session.setAttribute("creditList", creditList); String
	 * path=userObj.getImagepath(); if(path!=null){ String imagePth
	 * =conf.getImage_path()+path;
	 * logger.info("Image imagePth controlerr imagePth"+ imagePth);
	 * this.emailService.sendFlightCancelTicketMail( flightOrderRow, credit,
	 * userObj, MediaType.IMAGE_PNG_VALUE, locale, request, response,
	 * servletContext, applicationContext,imagePth); } else{ String imagePth =
	 * conf.getDefult_image_path();
	 * logger.info("Image imagePth controlerr imagePth"+ imagePth);
	 * this.emailService.sendFlightCancelTicketMail( flightOrderRow, credit,
	 * userObj, MediaType.IMAGE_PNG_VALUE, locale, request, response,
	 * servletContext, applicationContext,imagePth); }
	 *
	 * } } } }
	 *
	 * }
	 *
	 * catch (NumberFormatException ex) { // handle your exception
	 * ex.printStackTrace(); } catch (Exception e) { // Update email failed...
	 *
	 * e.printStackTrace(); } return "Sucess"; }
	 */
	// to be used by schdeuler 02-02-2017 by saumya
	@RequestMapping(value = "/getAllHotelFlightCustomerPayment", method = RequestMethod.GET, headers = "Accept=application/json")
	public void getAllHotelFlightCustomerPayment(HttpServletRequest request, HttpServletResponse response) {

		List<NotificationDetail> notificationDetails = null;
		Company company = null;
		User user = null;
		try {
			notificationDetails = notificationDAO.getAllHotelFlightCustomerPayment(); 
			if (notificationDetails != null && !notificationDetails.isEmpty()) {

				for (NotificationDetail notificationDetail : notificationDetails) {

					if (String.valueOf(notificationDetail.getNotification().getUserId()) != null
							&& String.valueOf(notificationDetail.getNotification().getUserId()) != null) {
						company = allEmailDao
								.getCompanyByUserId(String.valueOf(notificationDetail.getNotification().getUserId()));
						user = allEmailDao
								.getUserByUserId(String.valueOf(notificationDetail.getNotification().getUserId()));
						switch (notificationDetail.getType()) {
						case 50:
						case 51:
							HotelEmailHelper.sendAllHotelCustomerPayment(notificationDetail.getInventoryId(), request, response,
									notificationDetails, company, user,hotelAndFlightPaymentTransctionDAO,emailService,notificationDAO, servletContext,applicationContext);
							break;
						case 52:
						case 53:
							FlightEmailHelper.sendAllFlightCustomerPayment(notificationDetail.getInventoryId(), request, response,
									notificationDetails, company, user,hotelAndFlightPaymentTransctionDAO,emailService,notificationDAO, servletContext,applicationContext);
							break;
						default:
							break;
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	

	

	/*
 //Created by harsha---------- for sending mail to customers 4 hour before departure timings
	@RequestMapping(value = "/sendFlightMailBeforeDeparture", method = RequestMethod.GET, headers = "Accept=application/json") 
	public @ResponseBody String getFlightBeforeDeparturemail(HttpServletRequest request, HttpServletResponse response) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {  
		ResponseHeader.setResponse(response);
		final Locale locale1 = LocaleContextHolder.getLocale();
		HttpSession session = request.getSession();	
		CommonConfig conf = CommonConfig.GetCommonConfig();
		try {

			List<FlightOrderRow> todayDepartureFlightOrderList = allEmailDao.getFlightDepartureLastDay();
			if(todayDepartureFlightOrderList!= null && todayDepartureFlightOrderList.size() > 0){
				for (FlightOrderRow flightOrderRow : todayDepartureFlightOrderList) {

					User user = allEmailDao.getUserByUserId(flightOrderRow.getUserId());
					Company company = allEmailDao.getCompanyByUserId(flightOrderRow.getUserId());

					FlightOrderTripDetail flightOrderTripDetails = flightOrderRow.getFlightOrderTripDetails().get(0);
					if(flightOrderTripDetails != null){
						String path = user.getImagepath();
						String imagePth = null;
						Date depTime = flightOrderTripDetails.getDepartureTime(); 
						logger.info("depTime" + depTime); 
						System.out.println("depTime" + depTime);

						Date presentDate = new Date();  
						Calendar c = Calendar.getInstance(); 
						c.add(Calendar.DATE, -1);  
						SimpleDateFormat mdyFormat = new SimpleDateFormat("HH:mm:ss");
						String presentDay = mdyFormat.format(presentDate);
						String departureDay=  mdyFormat.format(depTime);  
						String yestdate = mdyFormat.format(c.getTime()); 

						System.out.println("Timings of present and departure---"+presentDay+"------"+departureDay+"------"+yestdate);
						Date date1 = mdyFormat.parse(presentDay);
						Date date2 = mdyFormat.parse(departureDay);
						Date date3 = mdyFormat.parse(yestdate);
						System.out.println(date3);

						long difference = date2.getTime() - date1.getTime();
						long difference2 = date2.getTime() - date1.getTime();
						System.out.println("difference====="+difference);
						System.out.println("difference====="+difference2);
						session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());
						session.setAttribute("Address", user.getAddress());
						session.setAttribute("tripdetails", flightOrderRow.getFlightOrderTripDetails());

						 diffrence is comming in millisecounds so i am comparing with millisecounds  (difference<=14520000(7.58) && difference >=14340000(8.1)
						 if(difference<=14520000 && difference >=14340000 ){
							long hours = TimeUnit.MILLISECONDS.toHours(difference);
							System.out.println(hours);
							session.setAttribute("hours", hours);
							System.out.println("difrence is exactly 4 hours mail will send now");
							if (path != null) 
								imagePth = conf.getImage_path() + path;
							else  
								imagePth = conf.getDefult_image_path();
							this.emailService.sendFlightNotificationMail(flightOrderRow, user, company,MediaType.IMAGE_PNG_VALUE,
									locale1, request, response, servletContext, applicationContext, imagePth);
						}  
					}

				}
			}
		}
		catch (NumberFormatException ex) { // handle your exception
			ex.printStackTrace();
		} catch (Exception e) {
			// Update email failed..
			e.printStackTrace();
		}
		return "Success";
	} 
	 */


	//Created by harsha---------- for sending mail to corporate companies one day before based on  corporate agreement expiry date
			@RequestMapping(value = "/sendCorporateAgreementExpiryMail", method = RequestMethod.GET, headers = "Accept=application/json")
			public void getCorporateExpirymail(Email email, HttpServletRequest request, HttpServletResponse response,
					Locale locale) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
			DocumentException, IOException, Exception {
				List<Company> corporateList= allEmailDao.getAllCorporates();
				if(corporateList!=null && corporateList.size()>0){
					for(Company company:corporateList){
						Company parentCompany = emailDao.getParentCompany(company);
						emailService.sendCorporateAgreementExpiryMail(company,parentCompany,MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,applicationContext);
					}
				}

			}
	
			//Created by harsha---------- for sending mail to customers 4 hour before departure timings
			@RequestMapping(value = "/sendFlightMailBeforeDeparture", method = RequestMethod.GET, headers = "Accept=application/json") 
			public @ResponseBody synchronized String getFlightBeforeDeparturemail(HttpServletRequest request, HttpServletResponse response) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
			DocumentException, IOException, Exception { 

				try {
					List<FlightOrderRow> todayDepartureFlightOrderList = allEmailDao.getFlightDepartureLastDay();
					if(todayDepartureFlightOrderList!= null && todayDepartureFlightOrderList.size() > 0){
						for (FlightOrderRow flightOrderRow : todayDepartureFlightOrderList) {	
							Email email = allEmailDao.check4HoursEmailIsExists(flightOrderRow.getOrderId());
							if(email == null){
								if(flightOrderRow.getFlightOrderTripDetails().size() > 0){
								FlightOrderTripDetail flightOrderTripDetails = flightOrderRow.getFlightOrderTripDetails().get(0);
								if(flightOrderRow.getFlightOrderTripDetails().size() > 0){
									if(flightOrderTripDetails != null){							
										Date depTime = flightOrderTripDetails.getDepartureTime(); 
										if(depTime != null){ 
											Date presentDate = new Date();  
											Calendar c = Calendar.getInstance(); 
											c.add(Calendar.DATE, -1);  
											SimpleDateFormat mdyFormat = new SimpleDateFormat("HH:mm:ss");
											String presentDay = mdyFormat.format(presentDate);
											String departureDay=  mdyFormat.format(depTime); 							
											Date date1 = mdyFormat.parse(presentDay);
											Date date2 = mdyFormat.parse(departureDay);								
											long difference = date2.getTime() - date1.getTime();								
											if(difference<=14400000 && difference >=14040000 ){									
												emailDao.insertEmail(flightOrderRow.getOrderId(), 0, Email.EMAIL_TYPE_FLIGHT_4HOURS_NOTIFICATION);
											}
										}
									}
								}
							}
							}

						}
					}
				}
				catch (NumberFormatException ex) { // handle your exception
					ex.printStackTrace();
				} catch (Exception e) {
					// Update email failed..
					e.printStackTrace();
				}

				return "Success";
			}

			// for sending mail to customers 24 hour before departure timings
			//edited  by basha
			@RequestMapping(value = "/sendFlightMailBeforeOneDay", method = RequestMethod.GET, headers = "Accept=application/json") 
			public @ResponseBody synchronized String getFlightMailBeforeOneDaymail(HttpServletRequest request, HttpServletResponse response) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
			DocumentException, IOException, Exception {
				try {
					List<FlightOrderRow> todayDepartureFlightOrderList = allEmailDao.getFlightMailBeforeOneDay();
					if(todayDepartureFlightOrderList!= null && todayDepartureFlightOrderList.size() > 0){
						for (FlightOrderRow flightOrderRow : todayDepartureFlightOrderList) { 
							Email email = allEmailDao.check24HoursEmailIsExists(flightOrderRow.getOrderId());	
							if(email == null){
								if(flightOrderRow.getFlightOrderTripDetails().size() > 0){
									FlightOrderTripDetail flightOrderTripDetails = flightOrderRow.getFlightOrderTripDetails().get(0);
									if(flightOrderTripDetails != null){								
										Date depTime = flightOrderTripDetails.getDepartureTime(); 
										if(depTime != null){	
											Date presentDate = new Date(); 
											SimpleDateFormat mdyFormat = new SimpleDateFormat("HH:mm:ss");
											String presentDay = mdyFormat.format(presentDate);
											Date date1 = mdyFormat.parse(presentDay);
											Date  date2=depTime;
											long difference = date2.getTime() - date1.getTime();	
											if( difference>=0 && difference<=59000){
												emailDao.insertEmail(flightOrderRow.getOrderId(), 0, Email.EMAIL_TYPE_FLIGHT_24HOURS_NOTIFICATION);
											}
											
									}
								}
							}
						}
					}
				}
			}
				catch (NumberFormatException ex) { // handle your exception
					ex.printStackTrace();
				} catch (Exception e) {
					// Update email failed..
					e.printStackTrace();
				}
				return "Success";
			}


}