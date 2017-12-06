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
import com.tayyarah.hotel.dao.HotelOrderRowEmailDao;
import com.tayyarah.hotel.quotation.dao.HotelTravelRequestDao;
import com.tayyarah.hotel.quotation.entity.HotelQuotationHistory;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequest;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequestQuotation;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;

@Component
public class HotelQuotationEmailHelper {

	public static final Logger logger = Logger.getLogger(HotelQuotationEmailHelper.class);

	public void sendHotelQuotations(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, HotelTravelRequestDao hotelTravelRequestDao, HotelOrderRowEmailDao hotelOrderRowEmailDao,  EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
			DocumentException, IOException, Exception {
		if (email != null) {
			Company company = null;
			User user = null;
			String userId = TravelRequestHelper.getQuotationUserId(email.getOrderId());
			logger.info("'=userId-------------" + userId);

			String requestId = TravelRequestHelper.getTravelRequestId(email.getOrderId());
			logger.info("'=requestId-------------" + requestId);

			List<Long> quotationIdList = TravelRequestHelper.getQuotationIdList(email.getOrderId());
			logger.info("'=quotationIdList-------------" + quotationIdList);

			logger.info("## sendHotelQuotations : " + userId);
			if (userId != null && requestId != null && quotationIdList != null && quotationIdList.size() > 0) {
				company = allEmailDao.getCompanyByUserId(userId);
				user = allEmailDao.getUserByUserId(userId);
				CommonConfig conf = CommonConfig.GetCommonConfig();
				if (user != null && company != null && conf != null) {

					user.initLogoDisplayable();
					company.initLogoDisplayable();

					List<HotelTravelRequestQuotation> quotations = hotelTravelRequestDao
							.getHotelRequestTravelQuotationList(quotationIdList);
					if (quotations.size() > 0) {
						HotelTravelRequest HotelTravelRequest = hotelTravelRequestDao
								.getHotelTravelRequest(Long.valueOf(requestId));
						if (HotelTravelRequest != null) {
							user.tranformDisplayable();
							company.tranformDisplayable();
							emailService.sendHotelQuotationEmail(user, company, HotelTravelRequest, quotations,
									locale, request, response, servletContext, applicationContext);
						} else {
							throw new Exception("######HotelTravelRequest not found");
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
	public void sendToHotelAlternativeEmails(Email email, HotelQuotationHistory hotelQuotationHistoryEmail,
			HttpServletRequest request, HttpServletResponse response, Locale locale, HotelTravelRequestDao hotelTravelRequestDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
					DocumentException, IOException, Exception {

		if (hotelQuotationHistoryEmail != null) {
			Company company = null;
			User user = null;
			String userId = TravelRequestHelper.getQuotationUserId(hotelQuotationHistoryEmail.getHotelQuotationSchema());
			String requestId = TravelRequestHelper.getTravelRequestId(hotelQuotationHistoryEmail.getHotelQuotationSchema());
			List<Long> quotationIdList = TravelRequestHelper.getQuotationIdList(hotelQuotationHistoryEmail.getHotelQuotationSchema());
			if (userId != null && requestId != null && quotationIdList != null && quotationIdList.size() > 0) {
				company = allEmailDao.getCompanyByUserId(userId);
				user = allEmailDao.getUserByUserId(userId);
				CommonConfig conf = CommonConfig.GetCommonConfig();
				if (user != null && company != null && conf != null) {
					user.initLogoDisplayable();
					company.initLogoDisplayable();
					List<HotelTravelRequestQuotation> quotations = hotelTravelRequestDao
							.getHotelRequestTravelQuotationList(quotationIdList);			
					if (quotations.size() > 0) {
						HotelTravelRequest HotelTravelRequest = hotelTravelRequestDao
								.getHotelTravelRequest(Long.valueOf(requestId));
						if (HotelTravelRequest != null) {
							user.tranformDisplayable();
							company.tranformDisplayable();
							emailService.sendHotelQuotationAlternativeEmail(user, company, HotelTravelRequest,
									quotations, hotelQuotationHistoryEmail, locale, request, response, servletContext,
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