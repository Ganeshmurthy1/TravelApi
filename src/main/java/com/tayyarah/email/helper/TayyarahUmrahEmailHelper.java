package com.tayyarah.email.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import com.itextpdf.text.DocumentException;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.services.EmailService;
import com.tayyarah.umrah.entity.TayyarahUmrahContactDetails;

@Component
public class TayyarahUmrahEmailHelper {

	public static final Logger logger = Logger.getLogger(TayyarahUmrahEmailHelper.class);	

	public void sendTayyarahUmrahEnquiryEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailService emailService,AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {

		if (email != null) {
			if(email.getOrderId()!=null){ 
				try{ 
					String orderId = email.getOrderId();
					HttpSession session = request.getSession();		
					TayyarahUmrahContactDetails tayyarahUmrahContactDetails = allEmailDao.getTayyarahUmrahEnquiryDetails(orderId);
					if(tayyarahUmrahContactDetails != null){
						session.setAttribute("tayyarahUmrahContactDetails", tayyarahUmrahContactDetails);
						CommonConfig conf = CommonConfig.GetCommonConfig();
						String imagePth = conf.getDefult_image_path();
						emailService.sendEnquiryUmrah(tayyarahUmrahContactDetails,  
								MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
								applicationContext, imagePth);
					}			
				}catch(Exception e){
					e.printStackTrace();

				}			 
			}			 

		}
	}
}