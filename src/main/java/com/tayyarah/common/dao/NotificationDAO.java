package com.tayyarah.common.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.notification.Notification;
import com.tayyarah.common.notification.NotificationDetail;
import com.tayyarah.common.notification.NotificationInventoryTypeEnum;
import com.tayyarah.common.notification.NotificationStatusEnum;
import com.tayyarah.common.util.DateConversion;
import com.tayyarah.common.util.enums.InventoryTypeEnum;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.services.NotificationServices;
import com.tayyarah.user.entity.User;


public class NotificationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private NotificationServices notificationServices;

	public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NotificationDAO.class);

	public Notification insert(Notification notification) {

		Session session = null;
		Transaction tx = null;
		try {
			if (notification != null) {
				session = sessionFactory.openSession();
				if (notification.getDetails() != null) {
					tx = session.beginTransaction();
					for (NotificationDetail notificationDetail : notification.getDetails()) {
						notificationDetail.setNotification(notification);
						notificationDetail.setCreatedAt(new Timestamp(new Date().getTime()));
						notificationDetail.setUpdatedAt(new Timestamp(new Date().getTime()));

						session.save(notificationDetail);
					}
					System.out.println("############# browsingHistoryDetail saved----------------------------");
					tx.commit();
				} // session.save(browsingHistory);
				tx = session.beginTransaction();
				notification.setStatusId(NotificationStatusEnum.STATUS_PENDING.getCode());
				notification.setCreatedAt(new Timestamp(new Date().getTime()));
				notification.setUpdatedAt(new Timestamp(new Date().getTime()));

				session.save(notification);
				System.out.println("############# browsingHistory saved----------------------------");
				tx.commit();
			}
			System.out.println("committed------------------------");
		} catch (HibernateException he) {
			System.out.println("HibernateException " + he);
			he.printStackTrace();
			if (tx != null)
				tx.rollback();
			// throw new RuntimeException("DB is not connecting ,please make
			// connections properly.");
		} finally {
			if (session != null)
				session.close();
		}
		return notification;
	}

	// for updating or editing notification to be acessed that
	// http://localhost:8080/LintasTravelAPI/notification/editNotification
	public String editNotification(Long notifyId, String fromDate, String toDate, String comments,
			String timeInterval,Boolean isAdmin, Boolean isEmail,String description,String toEmail,String ccEmail) {
		// List<Notification> list = new ArrayList<Notification>();
		List<NotificationDetail> notificationDetails = null;
		String status=null;
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try {
			timeInterval="25-07-2015 ".concat(timeInterval);
			Date fromDatestart = df.parse(fromDate);
			Date uptoDate = df.parse(toDate);
			Date timeIntervaldb = df.parse(timeInterval);
			Session session = null;
			Transaction tx = null;
			try {
				session = sessionFactory.openSession();
				Criteria criteria = session.createCriteria(Notification.class);
				tx = session.beginTransaction();
				criteria.add(Restrictions.eq("id", notifyId));
				Notification notification = (Notification) criteria.uniqueResult();
				notification.setFromDate(fromDatestart);
				notification.setToDate(uptoDate);
				notification.setDescription(description);
				if(ccEmail!=null && !ccEmail.equalsIgnoreCase(""))
				   notification.setCcEmailAddress(ccEmail);
				if(toEmail!=null && !toEmail.equalsIgnoreCase(""))
				   notification.setToEmailAddress(toEmail);
				
				notification.setIsExpired(false);
				notification.setIs_admin(isAdmin);
				notification.setIs_email(isEmail);
				notification.setUpdatedAt(new Timestamp(new Date().getTime()));
				notification.setTimeInterval(timeIntervaldb);
				notificationDetails = notification.getDetails();
				for (NotificationDetail notificationDetail : notificationDetails) {
					notificationDetail.setDescription(description);
					notificationDetail.setComments(comments);
					notificationDetail.setUpdatedAt(new Timestamp(new Date().getTime()));
				}
				session.saveOrUpdate(notification);
				tx.commit();
				status="SUCESS";
				return status ;

			} catch (HibernateException he) {
				he.printStackTrace();
				status="Failure";
				return status ;
			} finally {
				if (session != null)
					session.close();
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status;
	}

	/*public void hideNotification(Long notificationid) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Notification notification = (Notification) session.createCriteria(Notification.class)
					.add(Restrictions.eq("id", notificationid)).uniqueResult();
			notification.setCurrentNotificationView(false);
			session.update(notification);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			System.out.println("exception occured .couldnot save to database");
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

	}*/

	public List<Notification> getNotifications(NotificationStatusEnum statusEnum, Integer companyId, Integer userId) {
		List<Notification> list = new ArrayList<Notification>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Notification.class);
			Conjunction filters = Restrictions.conjunction();
			filters.add(Restrictions.eq("companyId", companyId));
			filters.add(Restrictions.eq("userId", userId));
			filters.add(Restrictions.eq("statusId", statusEnum.getCode()));
			filters.add(Restrictions.eq("customFlag", false));
			filters.add(Restrictions.eq("isExpired", false));
			criteria.add(filters);
			criteria.addOrder(Order.desc("createdAt"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	public List<Notification> getListOfCustomNotifications( Integer companyId, Integer userId) {
		List<Notification> list = new ArrayList<Notification>();
		Session session = null;

		Date fromTimestamp = new Date();
		Date todayDate = notificationServices.getDateWithoutTime(fromTimestamp);



		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Notification.class);

			Conjunction filters = Restrictions.conjunction();
			filters.add(Restrictions.eq("companyId", companyId));
			filters.add(Restrictions.eq("userId", userId));
			filters.add(Restrictions.eq("customFlag", true));
			filters.add(Restrictions.eq("isExpired", false));
			filters.add(Restrictions.eq("isAdmin", true));
			//time based 
			/*filters.add(Restrictions.le("fromDate", new Timestamp(new Date().getTime())));
			filters.add(Restrictions.ge("toDate", new Timestamp(new Date().getTime())));*/
			filters.add(Restrictions.le("fromDate", todayDate));
			filters.add(Restrictions.ge("toDate", todayDate));
			criteria.add(filters);

			criteria.addOrder(Order.desc("createdAt"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	public boolean updateNotifications(List<Notification> list) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = sessionFactory.openSession();
			int count = 0;
			for (Notification notification : list) {
				Criteria crit = session.createCriteria(Notification.class);
				crit.add(Restrictions.eq("id", notification.getId()));
				Notification notificationDb = (Notification) crit.uniqueResult();

				tx = session.beginTransaction();
				for (NotificationDetail notificationDetail : notificationDb.getDetails()) {
					notificationDetail.setUpdatedAt(new Timestamp(new Date().getTime()));
					notificationDetail.setViewState(true);
					session.update(notificationDetail);
				}
				notificationDb.setStatusId(NotificationStatusEnum.STATUS_VIEWED.getCode());
				notificationDb.setIsExpired(true);
				//notificationDb.setCurrentNotificationView(true);
				notificationDb.setUpdatedAt(new Timestamp(new Date().getTime()));
				session.update(notificationDb);
				if (++count % 100 == 0) {
					session.flush();
					session.clear();
				}
				tx.commit();
				result = true;
			}
		} catch (HibernateException he) {
			result = false;
			System.out.println("HibernateException " + he);
			he.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	// helper methods

	public User getUserDetailsByUserId(int userId) {
		User userDetails = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			String sql = "from User com where com.id=:id";
			Query query = session.createQuery(sql);
			query.setParameter("id", userId);
			userDetails = (User) query.uniqueResult();
		} catch (HibernateException e) {
			logger.error("-------HibernateException-------" + e.getMessage());

		} catch (Exception e) {
			logger.error("------Exception-------" + e.getMessage());

		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return userDetails;
	}

	/* this method for get FlightOrderRow using order id */
	public FlightOrderRow getFlightOrderRowDetail(String orderid) {
		FlightOrderRow flightOrderRow = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			String sql = "from FlightOrderRow fotd where fotd.orderId=:orderId";
			Query query = session.createQuery(sql);
			query.setParameter("orderId", orderid);
			flightOrderRow = (FlightOrderRow) query.uniqueResult();
		} catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------" + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return flightOrderRow;
	}

	public HotelOrderRow getHotelOrderRowById(String orderReference) {
		// TODO Auto-generated method stub
		HotelOrderRow order = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(HotelOrderRow.class);
			criteria.add(Restrictions.eq("orderReference", orderReference));
			order = (HotelOrderRow) criteria.uniqueResult();
		} catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------" + e.getMessage());
		} catch (Exception e) {
			logger.error("--------------Exception-----------------" + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return order;
	}

	// method to be used by scheduler
	public List<Notification> getAllNotificationToBeSentToday() {
		Session session = null;
		List<Notification> notifications = null;
		try {			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date today = Calendar.getInstance().getTime();
			String reportDate = df.format(today);
			long millis = DateUtils.truncate(today, Calendar.MILLISECOND).getTime();			
			java.sql.Timestamp sq = new java.sql.Timestamp(millis);			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss.SSS'Z'");	

			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Notification.class);
			criteria.add(Restrictions.eq("currentNotificationView", true));
			criteria.add(Restrictions.like("fromDate", true));
			notifications = criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		System.out.println("size of noificcation" + notifications.size());
		return notifications;

	}

	// done inserting new notification
	public boolean setNewNotification(String fromDate, String toDate, String comments, String description,
			Integer companyId, Integer userId, String timeInterval, Boolean is_admin, Boolean is_email,Boolean customFlag,String toEmail,String ccEmail) {

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try {
			timeInterval="25-07-1992 ".concat(timeInterval);
			Date fromDatestart = df.parse(fromDate);
			Date uptoDate = df.parse(toDate);
			Date timeIntervaldb = df.parse(timeInterval);
			
			Session session = null;
			Transaction tx = null;

			try {
				session = sessionFactory.openSession();
				tx=session.beginTransaction();
				Notification notification = new Notification();
				List<NotificationDetail> notificationDetails = new ArrayList<>();
				NotificationDetail nd = new NotificationDetail();

				notification.setCompanyId(companyId);
				notification.setUserId(userId);
				notification.setDescription(description);
				notification.setFromDate(fromDatestart);
				notification.setToDate(uptoDate);
				notification.setCurrentNotificationView(false);
				notification.setCreatedby(userId);
				notification.setStatusId(NotificationStatusEnum.STATUS_PENDING.getCode());
				notification.setType(InventoryTypeEnum.NEW_NOTIFICATION.getId());
				notification.setCreatedAt(new Timestamp(System.currentTimeMillis()));
				notification.setTimeInterval(timeIntervaldb);
				notification.setIsExpired(false);
				notification.setIs_admin(is_admin);
				notification.setIs_email(is_email);
				notification.setCustomFlag(customFlag);
				notification.setToEmailAddress(toEmail);
				notification.setCcEmailAddress(ccEmail);

				nd.setComments(comments);
				nd.setDescription(description);
				nd.setViewState(false);
				nd.setInventoryId(Integer.toString(InventoryTypeEnum.NEW_NOTIFICATION.getId()));
				nd.setType(InventoryTypeEnum.NEW_NOTIFICATION.getId());
				nd.setNotification(notification);
				nd.setCreatedAt(new Timestamp(new Date().getTime()));
				notificationDetails.add(nd);
				notification.setDetails(notificationDetails);
				session.save(notification);
				tx.commit();
				return true;
				// criteria.add(Restrictions.eq("orderReference",
				// orderReference));
				// order = (HotelOrderRow) criteria.uniqueResult();
			} catch (HibernateException e) {
				logger.error("--------------HibernateException-----------------" + e.getMessage());
				return false;
			} catch (Exception e) {
				logger.error("--------------Exception-----------------" + e.getMessage());
				return false;
			} finally {
				if (session != null && session.isOpen())
					session.close();
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}


	}

	// to be used by scheduler at
	// http://localhost:8080/LintasTravelAPI/notification/sendEmailForNotification
	public List<Notification> allEmailNotification() {


		String date = DateConversion.convertDateToStringToDateWithTIMETEST(new Timestamp(new Date().getTime()));
		Date newDate= DateConversion.StringToDateTest(date);
		Session session = null;
		List<Notification> notifications = new ArrayList<Notification>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Notification.class);
			Conjunction objConjunction = Restrictions.conjunction();
			objConjunction.add(Restrictions.eq("currentNotificationView", false));
			objConjunction.add(Restrictions.eq("isEmail", true));
			objConjunction.add(Restrictions.le("fromDate", new Timestamp(newDate.getTime())));
			objConjunction.add(Restrictions.ge("toDate",new Timestamp(newDate.getTime())));
			criteria.add(objConjunction);
			notifications = criteria.list();	
			
		} catch (HibernateException e) {
			//System.out.println("--------------HibernateException-----------------" + e.getMessage());
			logger.error("--------------HibernateException-----------------" + e.getMessage());
		} catch (Exception e) {
			logger.error("--------------Exception-----------------" + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return notifications;
	}

	//saumya 07/02/2017
	public boolean makeCustomNotificatyionEmailFalse(Notification notification) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx=session.beginTransaction();
				notification.setCurrentNotificationView(true);
				session.update(notification);
				session.flush();
			    tx.commit();
				return true;
		} catch (HibernateException e) {
			System.out.println("--------------HibernateException-----------------" + e.getMessage());
			logger.error("--------------HibernateException-----------------" + e.getMessage());
			 return false;
		} catch (Exception e) {
			logger.error("--------------Exception-----------------" + e.getMessage());
			 return false;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
	

	}


	/**
	 * @author Soumya
	 * @createdAt 23-01-2017
	 * */
	public void updateNotification(Notification notification) {
		// TODO Auto-generated method stub

		Session session = null;
		Transaction transaction=null;
		try {
			session = sessionFactory.openSession();
			transaction= session.beginTransaction();
			session.saveOrUpdate(notification);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			logger.error("--------------HibernateException-----------------" + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();

		}

	}



	public List<Notification> getNotificationsList(Long notifyId){
		List<Notification> list = null;
		Session session=null;

		try{
			session= sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Notification.class);		
			Conjunction filters = Restrictions.conjunction();
			filters.add(Restrictions.eq("id", notifyId));					
			criteria.add(filters);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list=criteria.list();
		}
		catch(HibernateException he){
			System.out.println("HibernateException "+he);
			he.printStackTrace();			
		}
		finally {
			if(session!=null)
				session.close();
		}
		return list;
	}

	/**
	 * @author Soumya
	 * @createdAt 25-01-2017
	 * */

	public List<Notification> getCustomNotificationsList(Long notifyId){
		List<Notification> list = null;
		Session session=null;

		try{
			session= sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Notification.class);		
			Conjunction filters = Restrictions.conjunction();
			filters.add(Restrictions.eq("id", notifyId));
			filters.add(Restrictions.eq("customFlag", true));
			criteria.add(filters);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list=criteria.list();
		}
		catch(HibernateException he){
			System.out.println("HibernateException "+he);
			he.printStackTrace();			
		}
		finally {
			if(session!=null)
				session.close();
		}
		return list;
	}


	/*
	 * public String shiftNotification(String notificationTime, String
	 * typeOfNotification, int companyId, int userId, Long notificationId) {
	 * Session session = null; DateFormat df = new
	 * SimpleDateFormat("dd/MM/yyyy HH:mm"); Transaction tx = null;
	 * 
	 * try { Date fromDatestart = df.parse(notificationTime);
	 * 
	 * try {
	 * 
	 * Date shiftednotificationTime = df.parse(notificationTime); session =
	 * sessionFactory.openSession(); tx = session.beginTransaction();
	 * Notification notification = (Notification)
	 * session.get(Notification.class, notificationId);
	 * System.out.println(notification.getDescription()); //
	 * notification.setNotificationTime(shiftednotificationTime);
	 * session.saveOrUpdate(notification); tx.commit(); } catch
	 * (HibernateException e) {
	 * System.out.println("--------------HibernateException-----------------" +
	 * e.getMessage()); e.printStackTrace(); } catch (Exception e) {
	 * System.out.println("--------------Exception-----------------" +
	 * e.getMessage()); e.printStackTrace(); } finally { if (session != null &&
	 * session.isOpen()) session.close(); } }
	 * 
	 * catch (ParseException e) { e.printStackTrace(); }
	 * 
	 * return null; }
	 */
	public int getParentUserIdLevel1(Integer id) throws Exception
	{
		Session session = null;
		int parentUserid=0;
		try{
			session = sessionFactory.openSession();
			Criteria cr=session.createCriteria(User.class);
			cr.add(Restrictions.eq("id", id));
			User user=(User) cr.uniqueResult();
			if (user!=null) {
				parentUserid=user.getCreatedbyCompanyUserId();
			}
		}
		catch (HibernateException e) {
			//logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}
		return parentUserid;
	}

	public List<Integer> getParentUserIdLevel2(Integer level1Id) throws Exception
	{
		List<Integer> userIds=new LinkedList<>();
		Session session = null;
		try{
			int parentUseridLevel1=getParentUserIdLevel1(level1Id);
			if(level1Id!=parentUseridLevel1){
				userIds.add(level1Id);
				userIds.add(parentUseridLevel1);
			}
			else{
				userIds.add(level1Id);
			}
			session = sessionFactory.openSession();
			Criteria cr=session.createCriteria(User.class);
			cr.add(Restrictions.eq("id", parentUseridLevel1));
			//	criteria.addOrder(Order.desc("createdAt"));
			User user=(User) cr.uniqueResult();
			if (user!=null) {
				int parentUseridLevel2=user.getCreatedbyCompanyUserId();
				if(parentUseridLevel2!=parentUseridLevel1){
					userIds.add(parentUseridLevel2);
				}
			}
		}
		catch (HibernateException e) {
			//logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}
		Collections.reverse(userIds);
		return  userIds;
	}



	//to be used by schdeuler 02-02-2017 by saumya
	public List<NotificationDetail> getAllHotelFlightCustomerPayment() {
		Session session = null;
		List<NotificationDetail> notificationDetails=null;
		try {
			List<Integer> enums=new ArrayList<>();
			enums.add(NotificationInventoryTypeEnum.HOTEL_CUSTOMER_PAYMENT_NOTIFICATION_ALERT.getId());
			enums.add(NotificationInventoryTypeEnum.HOTEL_SUPPLIER_PAYMENT_NOTIFICATION_ALERT.getId());
			enums.add(NotificationInventoryTypeEnum.FLIGHT_CUSTOMER_PAYMENT_NOTIFICATION_ALERT.getId());
			enums.add(NotificationInventoryTypeEnum.FLIGHT_SUPPLIER_PAYMENT_NOTIFICATION_ALERT.getId());
			session = sessionFactory.openSession();
			Criteria cr=session.createCriteria(NotificationDetail.class);
			Conjunction conjuction=Restrictions.conjunction();
			conjuction.add(Restrictions.in("type", enums));
			conjuction.add(Restrictions.eq("viewState",false));
			cr.add(conjuction);
			notificationDetails=cr.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			System.out.println("exception occured .couldnot save to database");
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return notificationDetails;
	}





	//to make field true after sending email for partial notification by saumya
	//vewstate make true
	public void makeEmailerTrue(List<NotificationDetail> notificationDetails) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(NotificationDetail.class);


			for (NotificationDetail notificationDetail : notificationDetails) {
				notificationDetail.setViewState(true);
				notificationDetail.getNotification().setCurrentNotificationView(true);
				session.saveOrUpdate(notificationDetail);

			}
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

	}

}
