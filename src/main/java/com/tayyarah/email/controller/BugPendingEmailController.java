package com.tayyarah.email.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.bugtracker.dao.BugDaoImpl;
import com.tayyarah.bugtracker.entity.BugTrackerHistory;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.services.BugEmailService;
import com.tayyarah.user.entity.User;


@RestController
@RequestMapping("/bug")
public class BugPendingEmailController {
	public static final Logger logger = Logger.getLogger(BugPendingEmailController.class);	
	@Autowired
	BugDaoImpl bugDao;
	@Autowired
	BugEmailService bugEmailService;
	@Autowired
	EmailDao emailDao;
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext applicationContext;
 
	
	
	@RequestMapping(value="/sendBugPendingMails" , method = RequestMethod.GET, headers = "Accept=application/json")
	public String sendBugPendingStatusEmail(HttpServletRequest request,HttpServletResponse response) throws MailException, NullPointerException, UnsupportedEncodingException, MessagingException, Exception{
		//ResponseHeader.setResponse(response);// Setting response header
		final Locale locale = LocaleContextHolder.getLocale();
		List<User> techHeadList = emailDao.getAllTechHeadList();
		System.out.println("techHeadList------------"+techHeadList.size());
		User techSupportUser=null;
		User assignedEmp=null;
		int techSupportCount=0;
		int techHeadCount=0;
		List<BugTrackerHistory> bugTrackerHistoryList = bugDao.bugPendingStatusList();
		System.out.println("bugTrackerHistoryList------------"+bugTrackerHistoryList.size());
		if(bugTrackerHistoryList !=null && techHeadList!=null ){
			for(BugTrackerHistory bugTrackerHistory:bugTrackerHistoryList){
				if(bugTrackerHistory.getAssignTo()>0){
					techSupportCount++;
					 techSupportUser=emailDao.getTechSupportDetails(bugTrackerHistory.getAssignTo());
					 System.out.println("techSupportUser----"+techSupportCount+"--------"+techSupportUser.getEmail());
					 assignedEmp=emailDao.getTechSupportDetails(bugTrackerHistory.getAssignedBy());
					 System.out.println("assignedEmp----"+techSupportCount+"--------"+assignedEmp.getEmail());
					 bugEmailService.sendBugPendingStatusMailToTechHeadsandTechSupport(assignedEmp, techSupportUser, assignedEmp, bugTrackerHistory, MediaType.IMAGE_PNG_VALUE, locale,  request, response, servletContext, applicationContext);
				
				}
			/*for(User techHeadUser:techHeadList){
				techHeadCount++;
				
				 System.out.println("techHeadUser----"+techHeadCount+"--------"+assignedEmp.getEmail());
			}*/
		 }
		}
		String msg="No of techHeads"+techHeadList.size()+"\n"+
		"No of techHead Mails:"+techHeadCount+"\n"+
		"No of techSupports"+bugTrackerHistoryList.size()+"\n"+	
		"No of techSupports Mails:"+techSupportCount+"\n";
		
		System.out.println("Mail send confirmation MEssage------------"+msg);
		
		
		
		 return msg;
	}
	 
}
