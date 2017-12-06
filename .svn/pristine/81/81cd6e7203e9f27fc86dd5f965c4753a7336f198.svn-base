package com.tayyarah.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.common.dao.NotificationDAO;
import com.tayyarah.common.notification.Notification;
import com.tayyarah.common.notification.NotificationDetail;
import com.tayyarah.common.notification.NotificationInventoryTypeEnum;
import com.tayyarah.common.notification.NotificationStatusEnum;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.user.entity.User;


public class NotificationServices {

	@Autowired
	private NotificationDAO notificationDAO;

	public Notification createNotification(String notificationJson) {
		ObjectMapper mapper = new ObjectMapper();
		Notification notification = null;
		try {
			notification = mapper.readValue(notificationJson, Notification.class);
		} catch (IOException ex) {
			System.out.println("###### Exception,," + ex.toString());
			ex.printStackTrace();
		}
		return notification;

	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> createPendingNotificationList(Integer companyId, Integer userId) {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> notificationMap = new HashMap<String, Object>();
		notificationMap.put("companyId", companyId);
		notificationMap.put("userId", userId);
		try {
			List<Notification> notifications = notificationDAO.getNotifications(NotificationStatusEnum.STATUS_PENDING,
					companyId, userId);

			if (notifications != null && notifications.size() > 0) {
				List<HashMap<String, Object>> notificationFeeds = new ArrayList<HashMap<String, Object>>();
				for (Notification notification : notifications) {

					JSONObject notificationJsonObj = new JSONObject();
					notificationJsonObj.put("notificationId", notification.getId());
					notificationJsonObj.put("statusId", notification.getStatusId());
					notificationJsonObj.put("createdAt", notification.getCreatedAt().getTime());
					notificationJsonObj.put("updatedAt", notification.getUpdatedAt().getTime());
					notificationJsonObj.put("userId", notification.getUserId());
					notificationJsonObj.put("userName", getUserName(notification.getUserId()));
					if (notification.getCreatedby() != null && notification.getCreatedby() > 0)
						notificationJsonObj.put("createdby", getUserName(notification.getCreatedby()));
					notificationJsonObj.put("imageid",getImageid(notification.getDetails().get(notification.getDetails().size() - 1).getType()));
					notificationJsonObj.put("companyId", notification.getCompanyId());
					notificationJsonObj.put("actionlink", getActionLink(
							notification.getDetails().get(notification.getDetails().size() - 1).getType(),
							notification.getDetails().get(notification.getDetails().size() - 1).getInventoryId()));
					JSONArray notificationDetailJsonArr = new JSONArray();

					List<NotificationDetail> notificationDetails = notification.getDetails();

					for (NotificationDetail notificationDetail : notificationDetails) {
						JSONObject notificationDetailJsonObj = new JSONObject();
						notificationDetailJsonObj.put("createdAt", notificationDetail.getCreatedAt().getTime());
						notificationDetailJsonObj.put("updatedAt", notificationDetail.getUpdatedAt().getTime());
						notificationDetailJsonObj.put("inventoryId", notificationDetail.getInventoryId());
						notificationDetailJsonObj.put("type", notificationDetail.getType());
						notificationDetailJsonObj.put("description", notificationDetail.getDescription());
						notificationDetailJsonArr.add(notificationDetailJsonObj);

					}

					notificationJsonObj.put("details", notificationDetailJsonArr);
					notificationFeeds.add(notificationJsonObj);
				}
				notificationMap.put("notifications", notificationFeeds);
				notificationMap.put("count", notificationFeeds.size());
			} else {
				notificationMap.put("notifications", new ArrayList<Notification>());
				notificationMap.put("count", 0);
			}
		} catch (Exception ex) {
			notificationMap.put("notifications", new ArrayList<Notification>());
			notificationMap.put("count", 0);
			System.out.println("###### Exception,," + ex.toString());
			ex.printStackTrace();
		}
		return notificationMap;

	}

	private static String getImageid(int notifytype) {
		return NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getImagename();
	}

	private String getActionLink(int notifytype, String inventoryId) {

		String actionlink = "";
		if (notifytype == NotificationInventoryTypeEnum.FLIGHT_ORDER.getId()) {
			FlightOrderRow flightOrderRow = notificationDAO.getFlightOrderRowDetail(inventoryId);
			if (flightOrderRow != null)
				actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
				+ "?id=" + flightOrderRow.getId() + "&orderId=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.HOTEL_ORDER.getId()) {
			HotelOrderRow hotelOrderRow = notificationDAO.getHotelOrderRowById(inventoryId);
			if (hotelOrderRow != null)
				actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
				+ "?selectedRowIndex=" + hotelOrderRow.getId();
		} else if (notifytype == NotificationInventoryTypeEnum.USER_WALLET.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?transactionid=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.COMPANY.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?companyid=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.COMPANY_CONFIG.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?config_id=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.B2CUSER.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?id=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.USER.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?id=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.CMS.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?id=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.COMPANY_APPROVAL.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?companyid=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.FLIGHT_MARKUP.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?markupId=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.HOTEL_MARKUP.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?id=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.HOTEL_QUOATATION.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?hotelQuotationRequestId=" + inventoryId;
		} else if (notifytype == NotificationInventoryTypeEnum.CUSTOM_NOTIFICATION.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?id=" + inventoryId;
		}
		else if (notifytype == NotificationInventoryTypeEnum.HOTEL_CUSTOMER_PAYMENT_NOTIFICATION_ALERT.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?id=" + inventoryId;
		}
		else if (notifytype == NotificationInventoryTypeEnum.HOTEL_SUPPLIER_PAYMENT_NOTIFICATION_ALERT.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?id=" + inventoryId;
		}
		else if (notifytype == NotificationInventoryTypeEnum.FLIGHT_CUSTOMER_PAYMENT_NOTIFICATION_ALERT.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?id=" + inventoryId;
		}
		else if (notifytype == NotificationInventoryTypeEnum.FLIGHT_SUPPLIER_PAYMENT_NOTIFICATION_ALERT.getId()) {
			actionlink = NotificationInventoryTypeEnum.getBrowsingHistoryDetailTypeEnum(notifytype).getActionname()
					+ "?id=" + inventoryId;
		}
		else {
			actionlink = "";
		}

		return actionlink;
	}

	public  String getUserName(int userId) {
		String username = "";
		if (userId != 0) {
			User user = notificationDAO.getUserDetailsByUserId(userId);
			if(user != null)
			   username = user.getUsername();
		}
		return username;
	}

	public JSONObject generateCustomNotification(int companyId,int userId){
		try {
			List<Notification> notifications = notificationDAO.getListOfCustomNotifications(companyId, userId);			
			List<JSONObject> notificationsToSent = new ArrayList<>();
			JSONObject notificationObj = new JSONObject();
			if(notifications.size() > 0){				
				for (Notification notification : notifications) {

					DateFormat defaultdateFormat = new SimpleDateFormat("hh:mm");					
					Date timeinterval = notification.getTimeInterval();					
					Date date = new Date();				
					String notifyinterval = defaultdateFormat.format(timeinterval);
					String currentTime = defaultdateFormat.format(date.getTime());						

					Date notifyintervaltime = new SimpleDateFormat("HH:mm").parse(notifyinterval);
					Date currentTimemin = new SimpleDateFormat("HH:mm").parse(currentTime);

					// System.out.println("notifyintervaltime------------------"+notifyintervaltime);
					// System.out.println("currentTimemin------------------"+currentTimemin);

					if((notifyintervaltime.compareTo(currentTimemin) == 0 || notifyintervaltime.before(currentTimemin)) && notification.getStatusId()==NotificationStatusEnum.STATUS_PENDING.getCode()){
						JSONObject matchedNotification = new JSONObject();
						matchedNotification.put("userId", notification.getUserId());
						matchedNotification.put("companyId", notification.getCompanyId());
						matchedNotification.put("userName", notificationDAO.getUserDetailsByUserId(notification.getUserId()).getUsername());
						matchedNotification.put("createdby", notificationDAO.getUserDetailsByUserId(notification.getCreatedby()).getUsername());
						matchedNotification.put("createdAt", notification.getCreatedAt());
						matchedNotification.put("notificationId", notification.getId());
						matchedNotification.put("statusId", notification.getStatusId());
						
						//action link added,add arr[0] istead of notification.getDetails().iterator().next().getInventoryId()
						String[] arr=notification.getDetails().iterator().next().getInventoryId().split(",");
						
						matchedNotification.put("actionlink", getActionLink(
								notification.getDetails().get(0).getType(),
								notification.getDetails().get(0).getInventoryId()));
								/*notification.getDetails().get(notification.getDetails().size() - 1).getType(),
								notification.getDetails().get(notification.getDetails().size() - 1).getInventoryId()));
						
*/
						JSONArray detailArray = new JSONArray();
						for (NotificationDetail notificationDetail : notification.getDetails()) {
							JSONObject matchedNotificationDetail = new JSONObject();
							matchedNotificationDetail.put("detailId", notificationDetail.getId());
							matchedNotificationDetail.put("inventoryId", notificationDetail.getInventoryId());
							matchedNotificationDetail.put("type", notificationDetail.getType());
							matchedNotificationDetail.put("title", notificationDetail.getDescription());
							matchedNotificationDetail.put("comments", notificationDetail.getComments());
							detailArray.add(matchedNotificationDetail);
						}

						matchedNotification.put("details", detailArray);					
						notificationsToSent.add(matchedNotification);
					}					
				}	

				if(notificationsToSent.size() > 0){
					notificationObj.put("status", true);
					notificationObj.put("count", notificationsToSent.size());
					notificationObj.put("notications", notificationsToSent);
				}else{
					notificationObj.put("status", false);
					notificationObj.put("count", 0);
				}


			}else{
				notificationObj.put("status", false);
				notificationObj.put("count", 0);
			}
			return notificationObj;
		} 
		catch (ParseException e) {
			e.printStackTrace();
			JSONObject notificationObj = new JSONObject();
			notificationObj.put("status", false);
			notificationObj.put("count", 0);
			return  notificationObj;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("exception occured in delete ");
			JSONObject notificationObj = new JSONObject();
			notificationObj.put("status", false);
			notificationObj.put("count", 0);
			return  notificationObj;

		}

	}

	public  Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}






	// not used
	/*
	 * public void allEmailNotification(){ List<Notification>
	 * notifications=notificationDAO.allEmailNotification();
	 * 
	 * for (Notification notification : notifications) {
	 * 
	 * notification.getCompanyId(); } }
	 */

}
