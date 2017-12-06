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
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.dao.WalletTransferHistoryDAO;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;

@Component
public class CompanyEmailHelper {	

	public static void sendCompanyDetailsEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			Company company = null;
			User user = null;
			String userid = email.getOrderId();			
			company = allEmailDao.getCompanyByUserId(userid);
			user = allEmailDao.getUserByUserId(userid);
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if (user != null && company != null && conf != null) {
				String path = user.getImagepath();

				if (path != null) {
					String imagePth = conf.getImage_path() + path;					
					emailService.sendCompanyDetailsMail(email, company, MediaType.IMAGE_PNG_VALUE, locale, request,
							response, servletContext, applicationContext, imagePth, user);
				} else {
					String imagePth = conf.getDefult_image_path();
					emailService.sendCompanyDetailsMail(email, company, MediaType.IMAGE_PNG_VALUE, locale, request,
							response, servletContext, applicationContext, imagePth, user);
				}

			} else {
				throw new Exception("Company not found");
			}

		}
	}

	public static void sendCompanyRegistrationEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			Company company = null;
			String userid = email.getOrderId();			
			company = allEmailDao.getCompanyByUserId(userid);
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if (company != null && conf != null) {				
				String path = company.getImagepath();
				if (path != null) {
					String imagePth = conf.getImage_path() + path;
					emailService.sendCompanyRegistrationMail(company, MediaType.IMAGE_PNG_VALUE, locale, request,
							response, servletContext, applicationContext, imagePth);
				} else {
					String imagePth = conf.getDefult_image_path();					
					emailService.sendCompanyRegistrationMail(company, MediaType.IMAGE_PNG_VALUE, locale, request,
							response, servletContext, applicationContext, imagePth);
				}

			} else {
				throw new Exception("Company not found");
			}

		}
	}

	public static void sendComapnyResetPassword(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			Company company = null;
			String companyId = email.getOrderId();			
			company = allEmailDao.getCompanyByCompanyId(companyId);
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if (company != null && conf != null) {
				company.initLogoDisplayable();
				emailService.sendCompanyUpdateMail(company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
						servletContext, applicationContext);

			} else {
				throw new Exception("Company not found");
			}

		}
	}

	public static void sendCompanyForgotePasswordEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		Company company = null;
		if (email != null) {
			String userid = email.getOrderId();		
			company = allEmailDao.getCompanyByUserId(userid);
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if (company != null && conf != null) {
				String path = company.getImagepath();
				if (path != null) {
					String imagePth = conf.getImage_path() + path;					
					emailService.sendCompanyRegistrationMail(company, MediaType.IMAGE_PNG_VALUE, locale, request,
							response, servletContext, applicationContext, imagePth);
				} else {
					String imagePth = conf.getDefult_image_path();
					emailService.sendCompanyRegistrationMail(company, MediaType.IMAGE_PNG_VALUE, locale, request,
							response, servletContext, applicationContext, imagePth);
				}

			}

		}

	}

	public static void sendWalletNoticationEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, WalletTransferHistoryDAO walletTransferHistoryDAO) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			Company parentcompany = null;
			String walletid = email.getOrderId();

			WalletAmountTranferHistory walletHistory = walletTransferHistoryDAO.getWalletById(walletid);
			User user = allEmailDao.getUserByUserId(String.valueOf(walletHistory.getUserId()));
			parentcompany = emailDao.getParentCompany(user);
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if (parentcompany != null && conf != null) {
				String path = user.getImagepath();
				String parentpath = parentcompany.getImagepath();
				String defPth = conf.getDefult_image_path();
				String imagestore = conf.getImage_path();
				user.setImagepath(defPth);
				parentcompany.setImagepath(defPth);
				if (path != null) {
					user.setImagepath(imagestore + path);
				}
				if (parentpath != null) {
					parentcompany.setImagepath(imagestore + parentpath);
				}
				if (!((walletHistory.getUserId() == walletHistory.getParentUserId()) && (walletHistory.getParentUserId() == walletHistory.getWalletId())))// super
					// user
					// alots
					// vertual
					// money
					// himself...
				{
					User parentUserAlotting = allEmailDao
							.getUserByUserId(String.valueOf(walletHistory.getParentUserId()));
					Company parentCompanyAlotting = emailDao.getParentCompany(parentUserAlotting);

					emailService.sendWalletTransferNotification(email, user, walletHistory, parentcompany,
							MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext, applicationContext);
					/*
					 * if(email.getType() ==
					 * Email.EMAIL_TYPE_WALLET_CREDIT_PARENT_NOTIFICATION ||
					 * email.getType() ==
					 * Email.EMAIL_TYPE_WALLET_DEBIT_PARENT_NOTIFICATION) {
					 * emailNotificationDao.insertEmailNotification(
					 * parentCompanyAlotting, parentCompanyAlotting,
					 * parentcompany, user,
					 * EmailNotification.ACTION_PARENT_TO_CHILD, email); } else
					 * { emailNotificationDao.insertEmailNotification(
					 * parentCompanyAlotting, parentCompanyAlotting,
					 * parentcompany, user,
					 * EmailNotification.ACTION_PARENT_TO_CHILD, email);
					 *
					 * }
					 */

				}
				/*
				 * if((walletHistory.getUserId() ==
				 * walletHistory.getParentUserId()) &&
				 * (walletHistory.getParentUserId() ==
				 * walletHistory.getWalletId()))// super user alots vertual
				 * money himself... {
				 * emailService.sendWalletTransferNotificationSuperUser(
				 * true, user, walletHistory, parentcompany,
				 * MediaType.IMAGE_PNG_VALUE, locale, request, response,
				 * servletContext, applicationContext);
				 *
				 * } else {
				 * emailService.sendWalletTransferNotification(true, user,
				 * walletHistory, parentcompany, MediaType.IMAGE_PNG_VALUE,
				 * locale, request, response, servletContext,
				 * applicationContext);
				 *
				 * if(user.getId() != 1)/////if not super user
				 * emailService.sendWalletTransferNotification(false, user,
				 * walletHistory, parentcompany, MediaType.IMAGE_PNG_VALUE,
				 * locale, request, response, servletContext,
				 * applicationContext);
				 *
				 * emailNotificationDao.insertEmailNotification(parentcompany,
				 * parentcompany, parentcompany, user,
				 * EmailNotification.ACTION_PARENT_TO_CHILD, email); }
				 */
			} else {
				throw new Exception("Parent Company not found");
			}

		}
	}

	public void companyConfigApprovalEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			String userid = email.getOrderId();
			CompanyConfig companyConfig = null;
			Company company = null;			
			companyConfig = allEmailDao.getCompanyConfigByUserId(userid);
			company = allEmailDao.getCompanyByUserId(userid);
			if (companyConfig != null && company != null) {
				emailService.sendCompanyConfigApprovalMail(company, companyConfig, MediaType.IMAGE_PNG_VALUE,
						locale, request, response, servletContext, applicationContext);

			} else {
				throw new Exception("User not found");
			}

		}
	}

	public static void sendWhiteLabelCompanyEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			/* List<Email> whiteLabelMailList= null; */
			List<CompanyConfig> companyConfigs = null;
			List<Company> companies = null;
			HttpSession session = request.getSession();
			CommonConfig conf = CommonConfig.GetCommonConfig();
			Integer mailId = Integer.valueOf(email.getOrderId());
			companyConfigs = allEmailDao.getWhiteLabelUserByConfigId(mailId);
			if (companyConfigs != null && companyConfigs.size() > 0) {
				session.setAttribute("companyConfig", companyConfigs);
				for (CompanyConfig companyconf : companyConfigs) {
					companies = allEmailDao.getWhiteLabelCompanyById(companyconf);
					if (companies != null && companies.size() > 0) {
						session.setAttribute("company", companies);
						for (Company companyobj : companies) {
							String path = companyobj.getImagepath();
							if (path != null) {
								String imagePth = conf.getImage_path() + path;								

								emailService.sendWhiteLabelCompanyMail(companyobj, companyconf,
										MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
										applicationContext, imagePth);
							} else {
								String imagePth = conf.getDefult_image_path();							

								emailService.sendWhiteLabelCompanyMail(companyobj, companyconf,
										MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
										applicationContext, imagePth);
							}

						}
					} else {
						throw new Exception("Company not found");
					}
				}
			} else {
				throw new Exception("Company config not found");
			}

		}
	}



}
