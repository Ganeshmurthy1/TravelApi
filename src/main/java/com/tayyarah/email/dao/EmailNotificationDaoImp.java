package com.tayyarah.email.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.email.entity.model.EmailNotification;
import com.tayyarah.user.entity.User;



public class EmailNotificationDaoImp implements EmailNotificationDao {
	
	public static final Logger logger = Logger.getLogger(EmailNotificationDaoImp.class);	

	@Autowired
	private SessionFactory  sessionFactory;	
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	FlightOrderRowEmailDao flightOrderRowEmailDao;	
	@Autowired
	AllEmailDao allEmailDao;
	@Autowired
	EmailDao emailDao;
	


	@Override
	public List<EmailNotification> getPendingMailNotifications()
			throws HibernateException {
		List<EmailNotification> list  = new ArrayList<EmailNotification>();
		List<EmailNotification> listEmails  = new ArrayList<EmailNotification>();
		Session sess = null;
		Transaction tx = null;		
		logger.info("########################## DB EmailNotification retriving call");
		try{
			//sess = sessionFactory.openSession();						
			 //String sql = "select * from email_notification where mail_status in("+Email.STATUS_PENDING+","+Email.STATUS_SENT_ERROR_SERVER_ISSUE+") and attempted_count < "+CommonConfig.GetCommonConfig().getMax_email_attempts()+" order by created_at limit "+CommonConfig.GetCommonConfig().getMax_email_queue_size()+"";
			 List<Integer> statusList=new ArrayList<>();
				statusList.add(Email.STATUS_PENDING);
				statusList.add(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
				sess = sessionFactory.openSession();
				Criteria criteria=sess.createCriteria(EmailNotification.class);
				 Conjunction conjuction=Restrictions.conjunction();
				 conjuction.add(Restrictions.in("mailStatus", statusList));
				 conjuction.add(Restrictions.lt("attemptedCount", CommonConfig.GetCommonConfig().getMax_email_attempts()));
				 criteria.add(conjuction);
				 //criteria.addOrder(Order.desc("createdAt"));
				 criteria.setMaxResults(CommonConfig.GetCommonConfig().getMax_email_queue_size());
			 
			   //SQLQuery query = sess.createSQLQuery(sql);
			//query.addEntity(EmailNotification.class);
			list = criteria.list();
			int count=0;
			if(list!=null && list.size()>0)
			{
				for (EmailNotification email : list) {					
					int atemptcount = email.getAttemptedCount();
					email.setAttemptedCount(++atemptcount);
					email.setStatus(Email.STATUS_PROCESSING);						
					tx = sess.beginTransaction();	
					sess.update(email);
					tx.commit();
					listEmails.add(email);
				}				
			}
			logger.info("########################## User EmailNotification list is set processing....");
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("########################## DB EmailNotification verification update call error");
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			
		}
		return listEmails;
	}

	@Override
	public List<EmailNotification> getFailedMailNotifications()
			throws HibernateException {
		List<EmailNotification> list  = new ArrayList<EmailNotification>();
		Session sessionHibernate = sessionFactory.openSession();
		try {
			Criteria criteria = sessionHibernate.createCriteria(EmailNotification.class);
			logger.info("get criteria class--"+criteria);
			criteria.add(Restrictions.eq("status", Email.STATUS_SENT_ERROR_SERVER_ISSUE));
			// Here is updated code
			ScrollableResults scrollableResults = criteria.scroll();
			int count=0;
			if(scrollableResults!=null )
			{
				while ( scrollableResults.next() ) {
					EmailNotification email = (EmailNotification)scrollableResults.get(count);
					if(email!=null)
					{
						list.add(email);
						email.setStatus(Email.STATUS_PROCESSING);
						logger.info("get criteria class--"+email);
						sessionHibernate.merge(email);
						logger.info("get criteria class--"+email);
						if ( ++count % 100 == 0 ) {
							sessionHibernate.flush();
							sessionHibernate.clear();
						}		
						logger.info("get criteria class--"+email);
					}
				}
			}
		} 
		catch (HibernateException ex) {
			logger.debug(ex.getMessage());
		} finally {
			if(sessionHibernate != null && sessionHibernate.isOpen())
			{				
				sessionHibernate.close(); 
			}
		}
		return list;
	}

	@Override
	public EmailNotification emailUpdateStatus(EmailNotification updateEmail)
			throws HibernateException {
		Session sess = null;
		Transaction tx = null;
		logger.info("updste called :"+updateEmail.getStatus()+"  emailUpdateStatus :"+updateEmail.getId());
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			Criteria crit = sess.createCriteria(EmailNotification.class);			
			crit.add(Restrictions.eq("id", updateEmail.getId()));			
			EmailNotification emaildb = (EmailNotification) crit.uniqueResult();
			if(emaildb!=null)
			{
				emaildb.setStatus(updateEmail.getStatus());
				System.out.println("updateEmail.getStatusMsg()  :"+updateEmail.getStatusMsg());
				emaildb.setStatusMsg(updateEmail.getStatusMsg());
				sess.update(emaildb);
				tx.commit();
				String sql = null;			
				SQLQuery query = null;			
			}

			return emaildb;
		}catch (HibernateException e) {
			logger.error("####### End email sent  EmailNotification emailUpdateStatus...  "+e.getMessage());
			e.printStackTrace();
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
	}

	@Override
	public EmailNotification insertEmailNotification(EmailNotification emailNotification, Email email) throws HibernateException{
		Session sess = null;
		Transaction tx = null;		
		try{
			sess = sessionFactory.openSession();

			emailNotification.setAttemptedCount(0);
			emailNotification.setEmail(email);
			Timestamp updated_at = new Timestamp(new Date().getTime());		
			emailNotification.setUpdatedAt(updated_at);
			emailNotification.setCreatedAt(updated_at);
			emailNotification.setStatus(Email.STATUS_PENDING);
			tx = sess.beginTransaction();			
			sess.save(emailNotification);			
			tx.commit();
			sess.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption---insertEmailNotification -HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return emailNotification;

	}

	public EmailNotification insertEmailNotification(Email email) throws HibernateException
	{
		Session sess = null;
		Transaction tx = null;
		//Insert EmailNotification entry....
		Company receipientCompany = null;
		Company performingCompany = null;
		Company receivingCompany = null;		
		EmailNotification emailNotification = new EmailNotification();	

		User performingUser = null;
		performingCompany = emailNotification.getPerformerCompanyId()==null?null:allEmailDao.getCompanyByCompanyId(String.valueOf(emailNotification.getPerformerCompanyId()));

		if(email.getType() == Email.EMAIL_TYPE_USER_REGISTRATION || email.getType() == Email.EMAIL_TYPE_USER_CREDENTIALS || email.getType() == Email.EMAIL_TYPE_USER_FORGOT_PWD_REGISTRATION || email.getType() == Email.EMAIL_TYPE_BLOCKED_USER || email.getType() == Email.EMAIL_TYPE_USER_RESET_PASSWORD)
		{
			User user = null;
			logger.info("User register mail... user id " + email.getOrderId());
			user = allEmailDao.getUserByUserId(email.getOrderId());			
			if(user != null)
			{
				Company parentcompany = emailDao.getParentCompany(user);				
				if(parentcompany!=null )
				{
					Integer companyId = parentcompany.getCompanyid();
					Integer userId = user.getId();
					emailNotification.setActionType(EmailNotification.ACTION_PARENT_TO_CHILD);
					if(email.getType() == Email.EMAIL_TYPE_USER_RESET_PASSWORD)
						emailNotification.setActionType(EmailNotification.ACTION_CHILD_HIMSELF);
					emailNotification.setRecipientCompanyId(companyId.longValue());
					emailNotification.setReceiverCompanyId(companyId.longValue());
					emailNotification.setReceiverUserId(userId.longValue());
					emailNotification.setPerformerCompanyId(companyId.longValue());
					emailNotification.setPerformerUserId(userId.longValue());					
					insertEmailNotification(emailNotification, email);
				}
			}

		}		
		else if(email.getType() == Email.EMAIL_TYPE_COMPANY_REGISTRATION || email.getType() == Email.EMAIL_TYPE_COMPANY_FORGOT_PWD || email.getType() == Email.EMAIL_TYPE_COMPANY_APPROVAL || email.getType() == Email.EMAIL_TYPE_COMPANY_RESET_PASSWORD || email.getType() == Email.EMAIL_TYPE_WHITE_LABEL)
		{

			Company company = null;
			User user = null;
			String userid = email.getOrderId();
			logger.info("company user id : " + userid);			
			company = allEmailDao.getCompanyByUserId(userid);			
			user = allEmailDao.getUserByUserId(email.getOrderId());			
			if(user != null && company != null)
			{
				Company parentcompany = emailDao.getParentCompany(user);				
				if(parentcompany!=null )
				{
					Integer companyId = company.getCompanyid();
					Integer userId = user.getId();
					Integer parentCompanyId = parentcompany.getCompanyid();
					emailNotification.setActionType(EmailNotification.ACTION_PARENT_TO_CHILD);
					if(email.getType() == Email.EMAIL_TYPE_COMPANY_RESET_PASSWORD)
						emailNotification.setActionType(EmailNotification.ACTION_CHILD_HIMSELF);
					emailNotification.setRecipientCompanyId(parentCompanyId.longValue());
					emailNotification.setReceiverCompanyId(companyId.longValue());
					emailNotification.setReceiverUserId(userId.longValue());
					emailNotification.setPerformerCompanyId(parentCompanyId.longValue());
					emailNotification.setPerformerUserId(userId.longValue());					
					insertEmailNotification(emailNotification, email);
				}
			}

		}
		else if(email.getType() == Email.EMAIL_TYPE_FRONT_USER_REGISTRATION || email.getType() == Email.EMAIL_TYPE_FRONT_USER_FORGOT_PWD )
		{

			User user = null;
			logger.info("User register mail... user id " + email.getOrderId());
			user = allEmailDao.getUserByUserId(email.getOrderId());			
			if(user != null)
			{
				Company parentcompany = emailDao.getParentCompany(user);				
				if(parentcompany!=null )
				{
					Integer companyId = parentcompany.getCompanyid();
					Integer userId = user.getId();
					emailNotification.setActionType(EmailNotification.ACTION_CHILD_HIMSELF);
					emailNotification.setRecipientCompanyId(companyId.longValue());
					emailNotification.setReceiverCompanyId(companyId.longValue());
					emailNotification.setReceiverUserId(userId.longValue());
					emailNotification.setPerformerCompanyId(companyId.longValue());
					emailNotification.setPerformerUserId(userId.longValue());					
					insertEmailNotification(emailNotification, email);
				}
			}
		}		
		else if(email.getType() == Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS || email.getType() == Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS || 
				email.getType() == Email.EMAIL_TYPE_HOTEL_INVOICE_SUPER_TO_OTHERS || email.getType() == Email.EMAIL_TYPE_HOTEL_INVOICE_DISTRIBUTOR_TO_OTHERS
				|| email.getType() == Email.EMAIL_TYPE_CREDITNOTE_REQUEST_HOTEL 
				|| email.getType() == Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL || email.getType() == Email.EMAIL_TYPE_CREDITNOTE_REQUEST_FLIGHT
				|| email.getType() == Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_FLIGHT)
		{


			//this mail will be sent only when email has been sent...
			/*Company company = null;
			User user = null;
			String userid = email.getOrderId();
			logger.info("company user id : " + userid);			
			company = allEmailDao.getCompanyByUserId(userid);			
			user = allEmailDao.getUserByUserId(email.getOrderId());			
			if(user != null && company != null)
			{
				Company parentcompany = emailDao.getParentCompany(user);				
				if(parentcompany!=null )
				{
					Integer companyId = company.getCompanyid();
					Integer userId = user.getId();
					Integer parentCompanyId = parentcompany.getCompanyid();
					emailNotification.setActionType(EmailNotification.ACTION_PARENT_TO_CHILD);
					emailNotification.setRecipientCompanyId(parentCompanyId.longValue());
					emailNotification.setReceiverCompanyId(companyId.longValue());
					emailNotification.setReceiverUserId(userId.longValue());
					emailNotification.setPerformerCompanyId(parentCompanyId.longValue());
					emailNotification.setPerformerUserId(userId.longValue());					
					insertEmailNotification(emailNotification, email);
				}
			}*/

		}	
		return emailNotification;

	}


	public EmailNotification insertEmailNotification(Company receipientCompany, Company performingCompany, Company receivingCompany, User performingUser, int actionType, Email email) throws HibernateException
	{

		EmailNotification emailNotification = new EmailNotification();	
		if(receipientCompany != null && performingCompany != null && receivingCompany != null && performingUser != null)
		{

			Integer receipientCompanyId = receipientCompany.getCompanyid();
			Integer performingCompanyId = performingCompany.getCompanyid();
			Integer receivingCompanyId = receivingCompany.getCompanyid();
			Integer performingUserId = performingUser.getId();

			emailNotification.setActionType(actionType);
			emailNotification.setRecipientCompanyId(receipientCompanyId.longValue());
			emailNotification.setReceiverCompanyId(receivingCompanyId.longValue());
			emailNotification.setReceiverUserId(performingUserId.longValue());
			emailNotification.setPerformerCompanyId(performingCompanyId.longValue());
			emailNotification.setPerformerUserId(null);					
			insertEmailNotification(emailNotification, email);

		}

		return emailNotification;
	}		



}
