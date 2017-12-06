package com.tayyarah.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tayyarah.common.dao.NotificationDAO;
import com.tayyarah.common.notification.Notification;
import com.tayyarah.common.notification.NotificationStatusEnum;
import com.tayyarah.services.NotificationServices;


@RestController
@RequestMapping("/notification")
public class NotificationDetailController {

	public static final Logger logger = Logger.getLogger(NotificationDetailController.class);

	@Autowired
	private NotificationServices notificationServices;

	@Autowired
	private NotificationDAO notificationDAO;


	@RequestMapping(value = "/getPendingNotification", method = RequestMethod.GET, headers = "Accept=application/json")
	public HashMap<String, Object> getPendingNotification(@RequestParam(value = "companyId") Integer companyId,
			@RequestParam(value = "userId") Integer userId) {

		HashMap<String, Object> notificationList = new HashMap<String, Object>();
		try {
			HashMap<String, Object> notificationInfo = notificationServices.createPendingNotificationList(companyId,userId);
			notificationList.put("notifications", notificationInfo);
			notificationList.put("pendingNotifications", true);
			return notificationList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("getPendingNotification----------NotificationDetailController-:" + e.getMessage());
			String status = "failure";
			notificationList.put("status", status);
			return notificationList;
		}

	}

	// have to update the updated time
	@RequestMapping(value = "/editNotification", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Map<String, String> updateNotification(
			@RequestParam(value = "notificationId") String notificationid,@RequestParam(value = "description") String description,
			@RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate,
			@RequestParam(value = "comments") String comments,
			@RequestParam(value = "timeInterval") String timeInterval, @RequestParam(value = "isAdmin") Boolean isAdmin,
			@RequestParam(value = "isEmail") Boolean isEmail,@RequestParam(value = "toEmail") String toEmail,@RequestParam(value = "ccEmail") String ccEmail) {
		Map<String, String> respMap = new HashMap<>();
		String status = null;

		Long notificationId = Long.parseLong(notificationid);

		try {
			status = notificationDAO.editNotification(notificationId, fromDate, toDate, comments, timeInterval, isAdmin,
					isEmail,description,toEmail,ccEmail);

			if (status != null) {
				status = "SUCESS";
				respMap.put("status", status);
			}
			return respMap;

		} catch (Exception e) {
			logger.error(
					"########################--exception occured in editNotification--#########################  ");
			e.printStackTrace();
			status = "failure";
			respMap.put("status", status);
			return respMap;
		}

	}

	/*
	 * @RequestMapping(value = "/hideNotification", method = RequestMethod.GET,
	 * headers = "Accept=application/json") public NotificationDetail
	 * hideNotification(@RequestParam(value = "notificationId") Long
	 * notificationId) { // hidenotificaion try {
	 * notificationDAO.hideNotification(notificationId);
	 * System.out.println("hided sucessfully  sucessfully"); } catch (Exception
	 * e) { e.printStackTrace();
	 * System.out.println("exception occured in delete "); }
	 * 
	 * return null;
	 * 
	 * }
	 */
	// create a new notification
	// done custom notification
	@RequestMapping(value = "/insertCustomNotification", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Map<String, String> insertCustomNotification(@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate, @RequestParam(value = "comments") String comments,
			@RequestParam(value = "description") String description, @RequestParam(value = "companyId") int companyId,
			@RequestParam(value = "userId") int userId, @RequestParam(value = "timeInterval") String timeInterval,
			@RequestParam(value = "isAdmin") Boolean isAdmin, @RequestParam(value = "isEmail") Boolean isEmail,@RequestParam(value = "toEmail") String toEmail,@RequestParam(value = "ccEmail") String ccEmail) {
		Map<String, String> respMap = new HashMap<>();
		String status = null;
		boolean statusbool=false;

		try {
			Boolean customFlag = true;
			statusbool=notificationDAO.setNewNotification(fromDate, toDate, comments, description, companyId, userId, timeInterval,
					isAdmin, isEmail, customFlag,toEmail,ccEmail);
			 
			if(statusbool==true){
				status = "SUCESS";
				respMap.put("status", status);
				return respMap;
			}
			else{
				status = "FAIL";
				respMap.put("status", status);
				return respMap;
			}
			
		}

		catch (Exception e) {
			logger.error(
					"########################--exception occured in insertNotification--#########################  ");
			e.printStackTrace();
			status = "failure";
			respMap.put("status", status);
			return respMap;
		}

	}

	// for inserting a notificaction like system generated
	@RequestMapping(value = "/insertSystemNotification", method = RequestMethod.GET, headers = "Accept=application/json")
	public boolean insertSystemNotification(@RequestParam(value = "userId") int userId,@RequestParam(value = "companyId") int companyId,
		    @RequestParam(value = "description") String description,@RequestParam(value = "type") String type,
			@RequestParam(value = "hastoupdateparent") boolean hastoupdateparent) {

/*try{
			
			List<User> userlist  = new ArrayList<User>();
			if(hastoupdateparent){
				List<Integer> useridlist = notificationDAO.getParentUserIdLevel2(userId);
				userlist = notificationDAO.getParentCompanyList(useridlist);		
			}else{				
				userlist.add(sessionUser);
			}					
			 
			if(userlist != null && userlist.size() > 0){
				for (int i = 0; i < userlist.size(); i++) {
					User user = userlist.get(i);							    	
					List<NotificationDetail> details = new ArrayList<NotificationDetail>();									
					NotificationDetail notificationDetail = new NotificationDetail();
					notificationDetail.setType(type);
					notificationDetail.setDescription(description);
					notificationDetail.setInventoryId(invertory_id);				
					details.add(notificationDetail);

					Notification notification = new Notification();
					notification.setType(type);
					notification.setCompanyId(user.getCompanyid());
					notification.setDescription(description);
					notification.setUserId(user.getId());
					notification.setDetails(details);
					notification.setCreatedby(sessionUser.getId());
					notificationDao.insert(notification);
				}
			}
		}catch(Exception e){
            logger.info("Exception " +e);
		}*/

		return false;
	}

	@RequestMapping(value = "/updateNotification", method = RequestMethod.GET, headers = "Accept=application/json")
	public boolean updateNotification(@RequestParam(value = "notificationId") Long notificationId) {
		// update the notification
		try {
			List<Notification> notifications = notificationDAO.getNotificationsList(notificationId);
			// createPendingNotificationList
			boolean result = notificationDAO.updateNotifications(notifications);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
					"########################--exception occured in updateNotification--#########################  ");
			return false;

		}

	}

	@RequestMapping(value = "/updateAllNotification", method = RequestMethod.GET, headers = "Accept=application/json")
	public boolean updateAllNotification(@RequestParam(value = "companyId") int companyId,
			@RequestParam(value = "userId") int userId) {
		try {
			List<Notification> notifications = notificationDAO.getNotifications(NotificationStatusEnum.STATUS_PENDING,
					companyId, userId);
			boolean result = notificationDAO.updateNotifications(notifications);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
					"########################--exception occured in updateAllNotification--#########################  ");
			return false;

		}

	}

	@RequestMapping(value = "/getCustomNotifications", method = RequestMethod.GET, headers = "Accept=application/json")
	public JSONObject getCustomNotifications(@RequestParam(value = "companyId") int companyId,
			@RequestParam(value = "userId") int userId) {
		JSONObject jsonObject = new JSONObject();
		try {
			 jsonObject = notificationServices.generateCustomNotification(companyId, userId);	            
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("generateCustomNotification----------NotificationDetailController-:" + e.getMessage());
			jsonObject.put("status", false);
			jsonObject.put("count", 0);		
		}
		return jsonObject;
	}
	
	/*@RequestMapping(value = "/getSystemNotifications", method = RequestMethod.GET, headers = "Accept=application/json")
	public JSONObject getSystemNotifications(@RequestParam(value = "companyId") int companyId,
			@RequestParam(value = "userId") int userId) {
		JSONObject jsonObject = new JSONObject();
		try {
			 jsonObject = notificationServices.generateCustomNotification(companyId, userId);	            
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("generateCustomNotification----------NotificationDetailController-:" + e.getMessage());
			jsonObject.put("status", false);
			jsonObject.put("count", 0);		
		}
		return jsonObject;
	}*/

	@RequestMapping(value = "/updateCustomNotification", method = RequestMethod.GET, headers = "Accept=application/json")
	public boolean updateCustomNotification(@RequestParam(value = "notificationId") Long notificationId) {
		try {

			List<Notification> notifications = notificationDAO.getCustomNotificationsList(notificationId);
			// createPendingNotificationList
			boolean result = notificationDAO.updateNotifications(notifications);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
					"########################--exception occured in updateCustomNotification--#########################  ");
			return false;

		}
	}

	@RequestMapping(value = "/updateAllCustomNotification", method = RequestMethod.GET, headers = "Accept=application/json")
	public boolean updateAllCustomNotification(@RequestParam(value = "companyId") int companyId,
			@RequestParam(value = "userId") int userId) {
		try {

			List<Notification> notifications = notificationDAO.getListOfCustomNotifications(companyId, userId);
			// createPendingNotificationList
			boolean result = notificationDAO.updateNotifications(notifications);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
					"########################--exception occured in updateAllCustomNotification--#########################  ");
			return false;

		}
	}

	
	
}
