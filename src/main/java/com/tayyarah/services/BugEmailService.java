package com.tayyarah.services;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring3.context.SpringWebContext;

import com.tayyarah.bugtracker.entity.BugTrackerHistory;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.user.entity.User;

@Service
public class BugEmailService {
	public static final Logger logger = Logger.getLogger(BugEmailService.class);
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;
	public boolean sendBugPendingStatusMailToTechHeadsandTechSupport(final  User  techHeadUser,final User techSupportUser,final User assignedEmp,BugTrackerHistory bugTrackerHistory,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());	
		variables.put("bugTrackerHistory", bugTrackerHistory);
		variables.put("techHeadUser", techHeadUser);
		variables.put("techSupportUser", techSupportUser);
		variables.put("assignedEmp", assignedEmp);
		variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,
				servletContext, locale, variables, applicationContext);
	 
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
				true /* multipart */, "UTF-8");
		message.setSubject("Bug Request Status"+":"+bugTrackerHistory.getStatus());
		CommonConfig cc=CommonConfig.GetCommonConfig();		
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		String[] recipientToEmaill={techHeadUser.getEmail(),techSupportUser.getEmail()};
		message.setTo(recipientToEmaill); 
		String htmlContent = this.templateEngine.process("BugTrackerRequestAssigned.html", ctx);	
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		return true;
	} 
}
