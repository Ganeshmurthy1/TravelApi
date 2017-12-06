package com.tayyarah.common.notification;

import java.util.ArrayList;
import java.util.List;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.notification.dao.NotificationDao;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.user.entity.User;




public class NotificationUtil {	

	public void insertNotification(AppKeyVo appKeyVo,String invertory_id,String description,Integer type,boolean hastoupdateparent,NotificationDao NFDAO,CompanyDao companyDao){
		try{			
			User currentuser = companyDao.getUserByCompanyId(appKeyVo.getCompanyId());	
			List<User> userlist  = new ArrayList<User>();
			if(hastoupdateparent){
				List<Integer> useridlist = companyDao.getParentUserIdLevel2(currentuser.getId());				
				userlist = companyDao.getParentCompanyList(useridlist);		
			}else{				
				userlist.add(currentuser);
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
					notification.setCreatedby(currentuser.getId());
					NFDAO.insert(notification);
				}
			}
		}catch(Exception e){
			System.out.println("insertNotification Exception " + e);
		}
	}
}