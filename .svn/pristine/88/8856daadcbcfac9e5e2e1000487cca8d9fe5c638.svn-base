package com.tayyarah.common.notification.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.notification.Notification;
import com.tayyarah.common.notification.NotificationDetail;
import com.tayyarah.common.notification.NotificationStatusEnum;
import com.tayyarah.user.entity.User;

public class NotificationDaoIMP implements NotificationDao{

	@Autowired
	private SessionFactory sessionFactory;

	Transaction tx = null;
	Logger logger = Logger.getLogger(NotificationDao.class);
	public Notification insert(Notification notification){
		Session session = null;
		try{
			if(notification!=null){
				session = sessionFactory.openSession();			
				if(notification.getDetails() != null)
				{
					tx = session.beginTransaction();		
					for (NotificationDetail notificationDetail : notification.getDetails()) {
						notificationDetail.setNotification(notification);
						notificationDetail.setCreatedAt(new Timestamp(new Date().getTime()));
						notificationDetail.setUpdatedAt(new Timestamp(new Date().getTime()));

						session.save(notificationDetail);
					}					
					System.out.println("############# browsingHistoryDetail saved----------------------------");
					tx.commit();
				}//session.save(browsingHistory);
				tx = session.beginTransaction();
				notification.setStatusId(NotificationStatusEnum.STATUS_PENDING.getCode());
				notification.setCreatedAt(new Timestamp(new Date().getTime()));
				notification.setUpdatedAt(new Timestamp(new Date().getTime()));
				session.save(notification); 
				System.out.println("############# browsingHistory saved----------------------------");
				tx.commit();
			}
			System.out.println("committed------------------------");
		}
		catch(HibernateException he){
			System.out.println("HibernateException "+he);
			he.printStackTrace();
			if(tx!=null) 
				tx.rollback();
			//throw new RuntimeException("DB is not connecting ,please make connections properly.");
		}
		finally {
			if(session!=null)
				session.close();
		}
		return notification;		
	}

	public User getUserByCompanyId(int companyid) {		
		User user = new User();		
		try {
			Session session = sessionFactory.openSession();		
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("Companyid", companyid));
			user = (User) criteria.uniqueResult();
			session.close();
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return user;
	}


}
