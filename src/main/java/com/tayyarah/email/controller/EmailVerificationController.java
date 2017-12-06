package com.tayyarah.email.controller;

import java.io.IOException;

import java.net.URLDecoder;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;

@Controller
@RequestMapping(value = "/verifyemail")
public class EmailVerificationController {
	static final Logger logger = Logger.getLogger(EmailVerificationController.class);
	@Autowired
	private EmailService emailService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext applicationContext;	
	@Autowired
	EmailDao emailDao;		

	@RequestMapping(value="/user",method = RequestMethod.GET)
	public @ResponseBody	
	String verifyUserEmail(@RequestParam(value="code") String code, HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		try{
			String decodedEmailCode = URLDecoder.decode(code, "UTF-8");		
			decodedEmailCode = decodedEmailCode.replaceAll(" ", "+");
			User user = emailDao.verifyUserEmail(decodedEmailCode, Email.VERIFY_EMAIL_USER);
			logger.info("###########COMPANY EMAIL VERFICATION user-"+user+" email code "+decodedEmailCode);
			if(user != null)
			{
				if(user.getEmailCode() != null && user.getEmailCode().length() > 0)
				{
					logger.info("###########COMPANY EMAIL VERFICATION user name-"+user.getUsername());
					Company parentcompany = emailDao.getParentCompany(user);
					logger.info("###########COMPANY EMAIL VERFICATION user name-"+user.getUsername()+"'s  parent user");				
					if(parentcompany != null)	
					{
						logger.info("###########COMPANY EMAIL VERFICATION user name-"+user.getUsername()+"'s  parent user name"+parentcompany.getCompanyname());
						response.sendRedirect(CommonConfig.GetCommonConfig().getAdmin_url()+"emailConfirmation?pname="+parentcompany.getCompanyname()+"&pphone="+parentcompany.getPhone()+"&pemail="+parentcompany.getEmail());
						final Locale locale = LocaleContextHolder.getLocale();
						sendUserDetails(user, parentcompany, request, response, locale);
					}	
				}
			}
			else
			{
				return "Your Email has been Already verfied.";
			}

		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return "Oops..! Something went wrong";
	}

	@RequestMapping(value="/company",method = RequestMethod.GET)
	public @ResponseBody	
	String verifyCompanyEmail(@RequestParam(value="code") String code, HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		try{
			String decodedEmailCode = URLDecoder.decode(code, "UTF-8");
			decodedEmailCode = decodedEmailCode.replaceAll(" ", "+");
			Company company = emailDao.verifyCompanyEmail(decodedEmailCode, Email.VERIFY_EMAIL_COMPANY);
			logger.info("###########COMPANY EMAIL VERFICATION company-"+company+" email code "+decodedEmailCode);
			if(company != null)
			{
				if(company.getEmailCode() != null && company.getEmailCode().length() > 0)
				{
					logger.info("###########COMPANY EMAIL VERFICATION company name-"+company.getCompanyname());
					Company parentcompany = emailDao.getParentCompany(company);
					logger.info("###########COMPANY EMAIL VERFICATION company name-"+company.getCompanyname()+"'s  parent company");
					if(parentcompany != null)	
					{
						logger.info("###########COMPANY EMAIL VERFICATION company name-"+company.getCompanyname()+"'s  parent company name"+parentcompany.getCompanyname());
						response.sendRedirect(CommonConfig.GetCommonConfig().getAdmin_url()+"emailConfirmation?pname="+parentcompany.getCompanyname()+"&pphone="+parentcompany.getPhone()+"&pemail="+parentcompany.getEmail());
						final Locale locale = LocaleContextHolder.getLocale();
						//sendVerificationAcknowledgementCompany(company, parentcompany, request, response, locale);				
					}	
				}
			}
			else
			{
				return "Your Email has been Already verfied.";
			}

		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return "Oops..! Something went wrong";
	}


	public void sendVerificationAcknowledgementCompany(Company company, Company parentcompany,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale) {				
		try {				
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if(company!=null && conf!=null)
			{
				logger.info("##### ACK mail -- Parent company email id----- : " + parentcompany.getEmail());
				String path = company.getImagepath();
				String imagePth=conf.getDefult_image_path();
				if(path!=null){
					imagePth = conf.getImage_path()+path;					
				}					
				company.setImagepath(imagePth);
				String parentpath = parentcompany.getImagepath();
				String parentImagePth=conf.getDefult_image_path();
				if(path!=null){
					parentImagePth = conf.getImage_path()+parentpath;					
				}					
				parentcompany.setImagepath(parentImagePth);

				this.emailService.sendEmailVerficationACKCompany(company, parentcompany, 
						MediaType.IMAGE_PNG_VALUE, locale, request, response,
						servletContext, applicationContext);
				logger.info("EmailController send email call after emailService.sendSimpleMail----  ");					
			}
		} catch (Exception e) {
			logger.info("##################ACK email failed----  ");	
			logger.error(e);	
		}		
	}
	
	public void sendVerificationAcknowledgementUser(User user, Company parentcompany,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale) {				
		try {				
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if(user!=null && conf!=null)
			{
				logger.info("##### ACK mail -- Parent company email id----- : " + parentcompany.getEmail());
				String path = user.getImagepath();
				String imagePth=conf.getDefult_image_path();
				if(path!=null){
					imagePth = conf.getImage_path()+path;					
				}					
				user.setImagepath(imagePth);
				String parentpath = parentcompany.getImagepath();
				String parentImagePth=conf.getDefult_image_path();
				if(path!=null){
					parentImagePth = conf.getImage_path()+parentpath;					
				}					
				parentcompany.setImagepath(parentImagePth);

				this.emailService.sendEmailVerficationACKUser(user, parentcompany, 
						MediaType.IMAGE_PNG_VALUE, locale, request, response,
						servletContext, applicationContext);
				logger.info("EmailController send email call after emailService.sendSimpleMail----  ");					
			}
		} catch (Exception e) {
			logger.info("##################ACK email failed----  ");	
			logger.error(e);	
		}		
	}

	public void sendUserDetails(User user, Company parentcompany,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale) {				
		try {				
			CommonConfig conf = CommonConfig.GetCommonConfig();
			if(user!=null && conf!=null)
			{
				logger.info("##### -- Parent company email id----- : " + parentcompany.getEmail());
				String path = user.getImagepath();
				String imagePth=conf.getDefult_image_path();
				if(path!=null){
					imagePth = conf.getImage_path()+path;					
				}					
				user.setImagepath(imagePth);
				String parentpath = parentcompany.getImagepath();
				String parentImagePth=conf.getDefult_image_path();
				if(path!=null){
					parentImagePth = conf.getImage_path()+parentpath;					
				}					
				parentcompany.setImagepath(parentImagePth); 
				this.emailService.sendEmailUserDetails(user, parentcompany,MediaType.IMAGE_PNG_VALUE, locale, request, response,
						servletContext, applicationContext);  
			}
		} catch (Exception e) {
			logger.info("##################User Approvel email failed----");	
			logger.error(e);	

		}		
	}
}