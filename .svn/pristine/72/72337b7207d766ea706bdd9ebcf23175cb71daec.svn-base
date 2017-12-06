package com.tayyarah.email.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import com.itextpdf.text.DocumentException;
import com.tayyarah.common.entity.Enquiry;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.FrontUserDetail;

@Component
public class FrontUserEmailHelper {


	public static void sendFrontUserRegistrationEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			String userid = email.getOrderId();
			FrontUserDetail frontUserDetail = null;			
			frontUserDetail = allEmailDao.getFrontUserDetailByUserId(userid);
			if (frontUserDetail != null) {				
				emailService.sendFrontUserDetailMail(frontUserDetail, MediaType.IMAGE_PNG_VALUE, locale, request,
						response, servletContext, applicationContext,email);

			} else {
				throw new Exception("User not found");
			}
		}
	}

	public static void sendFrontUserForgotPassswordEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			String userid = email.getOrderId();
			FrontUserDetail frontUserDetail = null;			
			frontUserDetail = allEmailDao.getFrontUserDetailByUserId(userid);			
			if (frontUserDetail != null) {
				emailService.sendFrontUserForgotPasswordMail(frontUserDetail, MediaType.IMAGE_PNG_VALUE, locale,
						request, response, servletContext, applicationContext);
			} else {
				throw new Exception("User not found");
			}
		}
	}

	public static void sendUserEnqueries(Email email, HttpServletRequest request, HttpServletResponse response, Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext)
			throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
			DocumentException, IOException, Exception {
		if (email != null) {
			String enquiryId = email.getOrderId();

			Enquiry enquiry = allEmailDao.getEnquiry(enquiryId);
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if (enquiry != null) {
				emailService.sendEnquiryEmail(enquiry, MediaType.IMAGE_PNG_VALUE, locale, request, response,
						servletContext, applicationContext);
			} else {
				throw new Exception("Enquiry not found");
			}
		}
	}
}