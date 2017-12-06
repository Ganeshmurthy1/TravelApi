package com.tayyarah.email.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import com.itextpdf.text.DocumentException;
import com.tayyarah.common.util.TravelRequestHelper;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.dao.EmailNotificationDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.flight.model.FlightInvoiceData;
import com.tayyarah.flight.quotation.dao.FlightTravelRequestDao;
import com.tayyarah.flight.quotation.entity.FlightQuotationHistory;
import com.tayyarah.flight.quotation.entity.FlightTravelRequest;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestQuotation;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;

@Component
public class FlightQuotationEmailHelper {

	public static final Logger logger = Logger.getLogger(FlightQuotationEmailHelper.class);
	public void sendFlightQuotations(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, FlightTravelRequestDao flightTravelRequestDao, FlightInvoiceData flightInvoiceData, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			Company company = null;
			User user = null;			
			String userId = TravelRequestHelper.getQuotationUserId(email.getOrderId());			
			String requestId = TravelRequestHelper.getTravelRequestId(email.getOrderId());		
			List<Long> quotationIdList = TravelRequestHelper.getQuotationIdList(email.getOrderId());
			if (userId != null && requestId != null && quotationIdList != null && quotationIdList.size() > 0) {
				company = allEmailDao.getCompanyByUserId(userId);
				user = allEmailDao.getUserByUserId(userId);
				CommonConfig conf = CommonConfig.GetCommonConfig();
				if (user != null && company != null && conf != null) {
					user.initLogoDisplayable();
					company.initLogoDisplayable();
					List<FlightTravelRequestQuotation> quotations = flightTravelRequestDao
							.getFlightRequestTravelQuotationList(quotationIdList);
					if (quotations != null && quotations.size() > 0) {
						FlightTravelRequest flightTravelRequest = flightTravelRequestDao
								.getFlightTravelRequest(Long.valueOf(requestId));			

						if (flightTravelRequest != null) {
							user.tranformDisplayable();
							company.tranformDisplayable();
							emailService.sendFlightQuotationEmail(user, company, flightTravelRequest, quotations,
									locale, request, response, servletContext, applicationContext);
						} else {
							throw new Exception("######FlightTravelRequest not found");
						}

					} else {
						throw new Exception("######Quotations not found");
					}
				} else {
					throw new Exception("######User or Company not found");
				}
			} else {
				throw new Exception("######Invalid id pattern");
			}

		} else {
			throw new Exception("Email not found");
		}
	}

	/* Flight alternative Emails */
	public void sendToFlightAlternativeEmails(Email email, FlightQuotationHistory flightQuotationHistoryEmail,
			HttpServletRequest request, HttpServletResponse response, Locale locale, FlightTravelRequestDao flightTravelRequestDao, FlightInvoiceData flightInvoiceData, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
					DocumentException, IOException, Exception {

		if (flightQuotationHistoryEmail != null) {
			Company company = null;
			User user = null;
			String userId = TravelRequestHelper.getQuotationUserId(flightQuotationHistoryEmail.getFlightQuotationSchema());
			String requestId = TravelRequestHelper.getTravelRequestId(flightQuotationHistoryEmail.getFlightQuotationSchema());
			List<Long> quotationIdList = TravelRequestHelper.getQuotationIdList(flightQuotationHistoryEmail.getFlightQuotationSchema());
			if (userId != null && requestId != null && quotationIdList != null && quotationIdList.size() > 0) {
				company = allEmailDao.getCompanyByUserId(userId);
				user = allEmailDao.getUserByUserId(userId);
				CommonConfig conf = CommonConfig.GetCommonConfig();
				if (user != null && company != null && conf != null) {
					user.initLogoDisplayable();
					company.initLogoDisplayable();
					List<FlightTravelRequestQuotation> quotations = flightTravelRequestDao
							.getFlightRequestTravelQuotationList(quotationIdList);		
					if (quotations.size() > 0) {
						FlightTravelRequest flightTravelRequest = flightTravelRequestDao
								.getFlightTravelRequest(Long.valueOf(requestId));
						if (flightTravelRequest != null) {
							user.tranformDisplayable();
							company.tranformDisplayable();
							emailService.sendFlightQuotationAlternativeEmail(user, company, flightTravelRequest,
									quotations, flightQuotationHistoryEmail, locale, request, response, servletContext,
									applicationContext);
						}

					} else {
						throw new Exception("######Quotations not found");
					}
				} else {
					throw new Exception("######User or Company not found");
				}
			} else {
				throw new Exception("######Invalid id pattern");
			}

		} else {
			throw new Exception("Email not found");
		}
	}
}