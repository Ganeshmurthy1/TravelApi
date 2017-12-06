package com.tayyarah.email.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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
import com.tayyarah.bugtracker.entity.BugTrackerHistory;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;


@Component
public class BugEmailHelper {
	
	public static void sendBugHistoryMailToTechHead(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, EmailDao emailDao, EmailService emailService, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			BugTrackerHistory bugTrackerHistory = null;
			User techSupportUser = null;
			User assignedEmp = null;
			User raisedByUser = null;
			List<String> newTechheadList = new ArrayList<>();
			List<User> techHeadList = emailDao.getAllTechHeadList();
			bugTrackerHistory = emailDao.getBugTrackerHistoryDeatails(email.getOrderId());
			if (bugTrackerHistory != null && techHeadList != null && techHeadList.size() > 0) {
				if (bugTrackerHistory.getAssignTo() > 0) {
					techSupportUser = emailDao.getTechSupportDetails(bugTrackerHistory.getAssignTo());
					assignedEmp = emailDao.getTechSupportDetails(bugTrackerHistory.getAssignedBy());
					raisedByUser = emailDao.getTechSupportDetails(bugTrackerHistory.getBugTracker().getCreatedBy());
				} else {
					raisedByUser = emailDao.getTechSupportDetails(bugTrackerHistory.getBugTracker().getCreatedBy());
				}
				for (User techHeadUser : techHeadList) {					
					newTechheadList.add(techHeadUser.getEmail());
				}
				emailService.sendBugHistoryCreatedMailToTechHeads(email, newTechheadList, techSupportUser,
						assignedEmp, raisedByUser, bugTrackerHistory, MediaType.IMAGE_PNG_VALUE, locale, request,
						response, servletContext, applicationContext);
			}
			emailService.sendBugHistoryCreatedMailToTechHeads(email, newTechheadList, techSupportUser,
					assignedEmp, raisedByUser, bugTrackerHistory, MediaType.IMAGE_PNG_VALUE, locale, request,
					response, servletContext, applicationContext);
		}
	}
}