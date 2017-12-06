package com.tayyarah.email.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import com.itextpdf.text.DocumentException;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;

@Component
public class UserEmailHelper {
	
	public static void UserForgotPasswordEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			String userid = email.getOrderId();
			User user = null;

			user = allEmailDao.getUserByUserId(userid);			
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if (user != null && conf != null) {
				String path = user.getImagepath();
				String imagePth = conf.getDefult_image_path();
				if (path != null) {
					imagePth = conf.getImage_path() + path;
				}
				user.setImagepath(imagePth);
				Company parentcompany = emailDao.getParentCompany(user);

				emailService.sendUserForgotPasswordMail(user, parentcompany, MediaType.IMAGE_PNG_VALUE, locale,
						request, response, servletContext, applicationContext);

			} else {
				throw new Exception("Company not found");
			}

		}
	}

	public static void sendLockedUserEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			String userid = email.getOrderId();
			List<User> userlist = null;
			HttpSession session = request.getSession();

			userlist = allEmailDao.getBlockedUserByUserId(userid);			
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if (userlist != null && userlist.size() > 0 && conf != null) {
				for (User userobj : userlist) {
					String path = userobj.getImagepath();
					if (path != null) {
						String imagePth = conf.getImage_path() + path;
						emailService.sendLockedUserMail(userobj, MediaType.IMAGE_PNG_VALUE, locale, request,
								response, servletContext, applicationContext, imagePth);
					} else {
						String imagePth = conf.getDefult_image_path();						
						emailService.sendLockedUserMail(userobj, MediaType.IMAGE_PNG_VALUE, locale, request,
								response, servletContext, applicationContext, imagePth);
					}

				}
			} else {
				throw new Exception("User not found");
			}

		}
	}
	
	public static void sendUserCredentialEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			String userid = email.getOrderId();
			User user = null;			
			user = allEmailDao.getUserByUserId(userid);
			if (user != null) {
				CommonConfig conf = CommonConfig.GetCommonConfig();
				String path = user.getImagepath();

				String imagePth = conf.getDefult_image_path();
				if (path != null) {
					imagePth = conf.getImage_path() + path;
				}
				user.setImagepath(imagePth);
				Company parentcompany = emailDao.getParentCompany(user);
				if (parentcompany != null) {
					emailService.sendUserCredentialMail(user, parentcompany, MediaType.IMAGE_PNG_VALUE, locale,
							request, response, servletContext, applicationContext);
				} else {
					throw new Exception("Company not found");
				}

			}

			else {
				throw new Exception("User not found");
			}

		}
	}

	public static void sendUserResetPassword(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			String userid = email.getOrderId();
			User user = null;			
			user = allEmailDao.getUserResetPassword(userid);
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if (user != null && conf != null) {
				String path = user.getImagepath();
				if (path != null) {
					String imagePth = conf.getImage_path() + path;					
					emailService.sendUserResetPassword(user, MediaType.IMAGE_PNG_VALUE, locale, request, response,
							servletContext, applicationContext, imagePth);
				} else {
					String imagePth = conf.getDefult_image_path();					
					emailService.sendUserResetPassword(user, MediaType.IMAGE_PNG_VALUE, locale, request, response,
							servletContext, applicationContext, imagePth);
				}

			} else {
				throw new Exception("User not found");
			}

		}
	}
	public static void sendUserRegistrationEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			String userid = email.getOrderId();
			User user = null;

			user = allEmailDao.getUserByUserId(userid);
			if (user != null) {
				CommonConfig conf = CommonConfig.GetCommonConfig();
				String path = user.getImagepath();
				String imagePth = conf.getDefult_image_path();
				if (path != null) {
					imagePth = conf.getImage_path() + path;
				}
				user.setImagepath(imagePth);
				Company parentcompany = emailDao.getParentCompany(user);

				if (parentcompany != null) {
					emailService.sendUserRegistrationMail(user, parentcompany, MediaType.IMAGE_PNG_VALUE, locale,
							request, response, servletContext, applicationContext);
				} else {
					throw new Exception("Company not found");
				}

			}

			else {
				throw new Exception("User not found");
			}

		}
	}


	
}
