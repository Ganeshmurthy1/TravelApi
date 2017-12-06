package com.tayyarah.common.notification.dao;


import com.tayyarah.common.notification.Notification;
import com.tayyarah.user.entity.User;

public interface NotificationDao {
	
	public Notification insert(Notification notification) throws Exception;
	public User getUserByCompanyId(int companyid)  throws Exception;
}
