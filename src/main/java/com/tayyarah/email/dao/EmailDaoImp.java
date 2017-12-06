package com.tayyarah.email.dao;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.bugtracker.entity.BugTrackerHistory;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;





public class EmailDaoImp implements EmailDao {
	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(EmailDaoImp.class);		

	@SuppressWarnings("finally")
	@Override
	public List<Email> getPendingMails() throws HibernateException {
		List<Email> list  = new ArrayList<Email>();
		List<Email> listEmails  = new ArrayList<Email>();
		Session sess = null;
		Transaction tx = null;
		List<Email> emailVerificationListUser = new ArrayList<Email>();	
		List<Email> emailVerificationListCompany = new ArrayList<Email>();	
	
		try{
			List<Integer> statusList=new ArrayList<>();
			statusList.add(Email.STATUS_PENDING);
			statusList.add(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
			sess = sessionFactory.openSession();
			Criteria criteria=sess.createCriteria(Email.class);
			Conjunction conjuction=Restrictions.conjunction();
			conjuction.add(Restrictions.in("mailStatus", statusList));
			conjuction.add(Restrictions.lt("attemptedCount", CommonConfig.GetCommonConfig().getMax_email_attempts()));
			criteria.add(conjuction);			
			criteria.setMaxResults(CommonConfig.GetCommonConfig().getMax_email_queue_size());		
			list = criteria.list();
			int count=0;
			if(list!=null && list.size()>0)
			{
				for (Email email : list) {				
					int atemptcount = email.getAttempedCount();
					email.setAttempedCount(++atemptcount);
					email.setStatus(Email.STATUS_PROCESSING);							
					tx = sess.beginTransaction();	
					sess.update(email);
					tx.commit();
					if((email.getType() == Email.EMAIL_TYPE_USER_REGISTRATION))
					{
						emailVerificationListUser.add(email);
						
					}
					else if((email.getType() == Email.EMAIL_TYPE_COMPANY_REGISTRATION))
					{
						emailVerificationListCompany.add(email);
						
					}

					listEmails.add(email);
				}
				if(emailVerificationListCompany!=null && emailVerificationListCompany.size()>0)
				{

					List<Integer> userIds=new ArrayList<>();					
					for (int i = 0; i < emailVerificationListCompany.size(); i++) {
						Email e = emailVerificationListCompany.get(i);
						Integer id=Integer.parseInt(e.getOrderId());
						userIds.add(id);						
					}						
					tx = sess.beginTransaction();				
					DetachedCriteria detachAssignmentPrd=DetachedCriteria.forClass(User.class, "assignment");
					detachAssignmentPrd.add(Restrictions.in("id", userIds)).setProjection(Projections.property("assignment.Companyid"));
					Criteria companyCriteriaObj=sess.createCriteria(Company.class,"position");
					companyCriteriaObj.add(Subqueries.propertyIn("position.companyid", detachAssignmentPrd));
					@SuppressWarnings("unchecked")
					List<Company> companylist = (List<Company>) companyCriteriaObj.list();			
					for (Company c : companylist) {
						c.setMailStatus(Email.STATUS_PROCESSING);			
						sess.merge(c);				
						if (++count % 100 == 0 ) {
							sess.flush();
							sess.clear();
						}	
					}
					tx.commit();
				}
				if(emailVerificationListUser!=null && emailVerificationListUser.size()>0)
				{					
					List<Integer> userIds=new ArrayList<>();
					StringBuilder useridquerysection = new StringBuilder();
					for (int i = 0; i < emailVerificationListUser.size(); i++) {
						Email e = emailVerificationListUser.get(i);
						Integer id=Integer.parseInt(e.getOrderId());
						userIds.add(id);					
					}				
					tx = sess.beginTransaction();					
					Criteria userCriteriaObj=sess.createCriteria(User.class);
					userCriteriaObj.add(Restrictions.in("id", userIds));
					@SuppressWarnings("unchecked")
					List<User> userlist = (List<User>) userCriteriaObj.list();			
					for (User u : userlist) {
						u.setMailStatus(Email.STATUS_PROCESSING);			
						sess.merge(u);				
						if ( ++count % 100 == 0 ) {
							sess.flush();
							sess.clear();
						}	
					}
					tx.commit();
				}
			}
			

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("########################## DB Email verification update call error");
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			return listEmails;
		}
	}

	public List<Email> getFailedMails(int status, int maxAttemptCount, int maxQueueLimit, int type,String orderid) throws HibernateException {

		List<Email> list  = new ArrayList<Email>();
		List<Email> listEmails  = new ArrayList<Email>();
		Session sess = null;
		Transaction tx = null;
		List<Email> emailVerificationListUser = new ArrayList<Email>();	
		List<Email> emailVerificationListCompany = new ArrayList<Email>();		
		try{
			sess = sessionFactory.openSession();	
			Criteria criteria=sess.createCriteria(Email.class);
			Conjunction conjuction=Restrictions.conjunction();
			conjuction.add(Restrictions.eq("orderId", orderid));
			conjuction.add(Restrictions.eq("type", type));
			conjuction.add(Restrictions.eq("mailStatus", status));
			conjuction.add(Restrictions.lt("attemptedCount", maxAttemptCount));
			criteria.add(conjuction);
			criteria.setMaxResults(maxQueueLimit);
			list = criteria.list();
			int count=0;
			if(list!=null && list.size()>0)
			{
				for (Email email : list) {				
					int atemptcount = email.getAttempedCount();
					email.setAttempedCount(++atemptcount);
					email.setStatus(Email.STATUS_PROCESSING);						
					tx = sess.beginTransaction();	
					sess.update(email);
					tx.commit();
					if((email.getType() == Email.EMAIL_TYPE_USER_REGISTRATION))
					{
						emailVerificationListUser.add(email);
						
					}
					else if((email.getType() == Email.EMAIL_TYPE_COMPANY_REGISTRATION))
					{
						emailVerificationListCompany.add(email);
						
					}

					listEmails.add(email);
				}
				if(emailVerificationListCompany!=null && emailVerificationListCompany.size()>0)
				{						
					List<Integer> userIds=new ArrayList<>();					
					for (int i = 0; i < emailVerificationListCompany.size(); i++) {
						Email e = emailVerificationListCompany.get(i);
						Integer id=Integer.parseInt(e.getOrderId());
						userIds.add(id);						
					}							
					tx = sess.beginTransaction();					
					DetachedCriteria detachAssignmentPrd=DetachedCriteria.forClass(User.class, "assignment");
					detachAssignmentPrd.add(Restrictions.in("id", userIds)).setProjection(Projections.property("assignment.Companyid"));
					Criteria companyCriteriaObj=sess.createCriteria(Company.class,"position");
					companyCriteriaObj.add(Subqueries.propertyIn("position.companyid", detachAssignmentPrd));					
					@SuppressWarnings("unchecked")
					List<Company> companylist =companyCriteriaObj.list();			
					for (Company c : companylist) {
						c.setMailStatus(Email.STATUS_PROCESSING);			
						sess.merge(c);				
						if ( ++count % 100 == 0 ) {
							sess.flush();
							sess.clear();
						}	
					}
					tx.commit();
				}
				if(emailVerificationListUser!=null && emailVerificationListUser.size()>0)
				{		

					List<Integer> userIds=new ArrayList<>();				
					for (int i = 0; i < emailVerificationListUser.size(); i++) {
						Email e = emailVerificationListUser.get(i);
						Integer id=Integer.parseInt(e.getOrderId());
						userIds.add(id);						
					}					
					tx = sess.beginTransaction();					
					Criteria userCriteriaObj=sess.createCriteria(User.class);
					userCriteriaObj.add(Restrictions.in("id", userIds));					
					@SuppressWarnings("unchecked")
					List<User> userlist = userCriteriaObj.list();			
					for (User u : userlist) {
						u.setMailStatus(Email.STATUS_PROCESSING);			
						sess.merge(u);				
						if ( ++count % 100 == 0 ) {
							sess.flush();
							sess.clear();
						}	
					}
					tx.commit();
				}
			}
			

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("########################## DB Email verification update call error");
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
	public List<Email> getMailsById(Long id) throws HibernateException {

		List<Email> list  = new ArrayList<Email>();
		List<Email> listEmails  = new ArrayList<Email>();
		Session sess = null;
		Transaction tx = null;
		List<Email> emailVerificationListUser = new ArrayList<Email>();	
		List<Email> emailVerificationListCompany = new ArrayList<Email>();	
		
		try{
			sess = sessionFactory.openSession();
			Criteria criteria=sess.createCriteria(Email.class);
			Conjunction conjuction=Restrictions.conjunction();
			conjuction.add(Restrictions.eq("id", id));			
			criteria.add(conjuction);		
			list = criteria.list();
			if(list!=null && list.size()>0)
			{
				for (Email email : list) {
					int atemptcount = email.getAttempedCount();
					email.setAttempedCount(++atemptcount);
					email.setStatus(Email.STATUS_PROCESSING);				
					tx = sess.beginTransaction();	
					sess.update(email);
					tx.commit();

					if((email.getType() == Email.EMAIL_TYPE_USER_REGISTRATION))
					{
						emailVerificationListUser.add(email);
						
					}
					else if((email.getType() == Email.EMAIL_TYPE_COMPANY_REGISTRATION))
					{
						emailVerificationListCompany.add(email);
						
					}

					listEmails.add(email);
				}
			}
			logger.info("########################## User Email list is set processing....");

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("########################## DB Email verification update call error");
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
	public List<Email> getMails(String orderid) throws HibernateException {

		List<Email> list  = new ArrayList<Email>();
		List<Email> listEmails  = new ArrayList<Email>();
		Session sess = null;
		Transaction tx = null;
		List<Email> emailVerificationListUser = new ArrayList<Email>();	
		List<Email> emailVerificationListCompany = new ArrayList<Email>();	
		
		try{
			sess = sessionFactory.openSession();

			//String sql = "select * from email where type in("+type+") and mail_status in("+status+") and attempted_count < "+maxAttemptCount+" order by created_at limit "+maxQueueLimit+"";
			Criteria criteria=sess.createCriteria(Email.class);
			Conjunction conjuction=Restrictions.conjunction();
			conjuction.add(Restrictions.eq("orderId", orderid));			
			criteria.add(conjuction);		

			list = criteria.list();
			int count=0;
			if(list!=null && list.size()>0)
			{
				for (Email email : list) {					
					int atemptcount = email.getAttempedCount();
					email.setAttempedCount(++atemptcount);
					email.setStatus(Email.STATUS_PROCESSING);						
					tx = sess.beginTransaction();	
					sess.update(email);
					tx.commit();
					if((email.getType() == Email.EMAIL_TYPE_USER_REGISTRATION))
					{
						emailVerificationListUser.add(email);
						
					}
					else if((email.getType() == Email.EMAIL_TYPE_COMPANY_REGISTRATION))
					{
						emailVerificationListCompany.add(email);
						
					}

					listEmails.add(email);
				}
				if(emailVerificationListCompany!=null && emailVerificationListCompany.size()>0)
				{				
					List<Integer> userIds=new ArrayList<>();					
					for (int i = 0; i < emailVerificationListCompany.size(); i++) {
						Email e = emailVerificationListCompany.get(i);
						Integer id=Integer.parseInt(e.getOrderId());
						userIds.add(id);					
					}						
					tx = sess.beginTransaction();					
					DetachedCriteria detachAssignmentPrd=DetachedCriteria.forClass(User.class, "assignment");
					detachAssignmentPrd.add(Restrictions.in("id", userIds)).setProjection(Projections.property("assignment.Companyid"));
					Criteria companyCriteriaObj=sess.createCriteria(Company.class,"position");
					companyCriteriaObj.add(Subqueries.propertyIn("position.companyid", detachAssignmentPrd));					
					@SuppressWarnings("unchecked")
					List<Company> companylist =companyCriteriaObj.list();			
					for (Company c : companylist) {
						c.setMailStatus(Email.STATUS_PROCESSING);			
						sess.merge(c);				
						if ( ++count % 100 == 0 ) {
							sess.flush();
							sess.clear();
						}	
					}
					tx.commit();
				}
				if(emailVerificationListUser!=null && emailVerificationListUser.size()>0)
				{		

					List<Integer> userIds=new ArrayList<>();					
					for (int i = 0; i < emailVerificationListUser.size(); i++) {
						Email e = emailVerificationListUser.get(i);
						Integer id=Integer.parseInt(e.getOrderId());
						userIds.add(id);						
					}					
					tx = sess.beginTransaction();					
					Criteria userCriteriaObj=sess.createCriteria(User.class);
					userCriteriaObj.add(Restrictions.in("id", userIds));				
					@SuppressWarnings("unchecked")
					List<User> userlist = userCriteriaObj.list();			
					for (User u : userlist) {
						u.setMailStatus(Email.STATUS_PROCESSING);			
						sess.merge(u);				
						if ( ++count % 100 == 0 ) {
							sess.flush();
							sess.clear();
						}	
					}
					tx.commit();
				}
			}
			

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("########################## DB Email verification update call error");
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
	public Email emailUpdateStatus(Email updateEmail)
			throws HibernateException {
		Session sess = null;
		Transaction tx = null;
		
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			Criteria crit = sess.createCriteria(Email.class);			
			crit.add(Restrictions.eq("id", updateEmail.getId()));			
			Email emaildb = (Email) crit.uniqueResult();
			if(emaildb!=null)
			{
				emaildb.setStatus(updateEmail.getStatus());
			
				emaildb.setStatusMsg(updateEmail.getStatusMsg());
				sess.update(emaildb);
				tx.commit();
				String sql = null;			
				SQLQuery query = null;				
				if(updateEmail.getType() == Email.EMAIL_TYPE_COMPANY_REGISTRATION)
				{						
					sql = "select * from company where companyid in (select companyid from user where id in( "+updateEmail.getOrderId()+"))";
					DetachedCriteria detachAssignmentPrd=DetachedCriteria.forClass(User.class, "assignment");
					detachAssignmentPrd.add(Restrictions.eq("id", Integer.parseInt(updateEmail.getOrderId()))).setProjection(Projections.property("assignment.Companyid"));
					Criteria companyCriteriaObj=sess.createCriteria(Company.class,"position");
					companyCriteriaObj.add(Subqueries.propertyIn("position.companyid", detachAssignmentPrd));					
					Company company = (Company) companyCriteriaObj.uniqueResult();
					if(company!=null)
					{
						tx = sess.beginTransaction();						
						company.setMailStatus(updateEmail.getStatus());		
					
						sess.update(company);
						tx.commit();
						
					}
				}
				else if(updateEmail.getType() == Email.EMAIL_TYPE_USER_REGISTRATION)
				{

					Criteria userCriteria=sess.createCriteria(User.class);
					userCriteria.add(Restrictions.eq("id", Integer.parseInt(updateEmail.getOrderId())));					
					User user = (User) userCriteria.uniqueResult();
					if(user!=null)
					{
						tx = sess.beginTransaction();						
						user.setMailStatus(updateEmail.getStatus());		
						logger.info("########################## DB Email verification call  query--"+sql+" status changed");
						sess.update(user);
						tx.commit();
						logger.info("########################## User Email status updated....");
					}
				}
			}

			return emaildb;
		}catch (HibernateException e) {
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
	public Email insertEmail(String orderId,int status,int emailType)
	{
		Session sess = null;
		Transaction tx = null;
		Email email = new Email();
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			email.setOrderId(orderId);
			email.setStatus(status);
			email.setType(emailType);
			Timestamp updated_at = new Timestamp(new Date().getTime());		
			email.setUpdatedAt(updated_at);
			email.setCreatedAt(updated_at);
			sess.saveOrUpdate(email);		
			tx.commit();
			sess.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return email;	}

	public Email getEmail(Long id)
			throws HibernateException {
		Session sess = null;	
		Email email = null;
		try{		
			sess = sessionFactory.openSession();			
			Criteria emailCriteria=sess.createCriteria(Email.class);
			emailCriteria.add(Restrictions.eq("id", id));			
			email = (Email) emailCriteria.uniqueResult();			
		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return email;
	}
	@Override
	public void getOrderDetails(HttpSession session, String orderId,int type)
			throws HibernateException {
		Session sess = null;	

		List<WalletAmountTranferHistory> historyList=null;
		try{
			sess = sessionFactory.openSession();			
			Criteria walletHistoryCriteria=sess.createCriteria(WalletAmountTranferHistory.class);
			walletHistoryCriteria.add(Restrictions.eq("actionId", orderId));			
			historyList = walletHistoryCriteria.list();
		}
		catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			sess.close(); 
		}		
		LinkedHashSet<Integer> userIdHashSet=new LinkedHashSet<Integer>();
		if(historyList!=null && historyList.size()>0)
		{
			for(WalletAmountTranferHistory walletAmountTranferHistory:historyList){
				userIdHashSet.add(walletAmountTranferHistory.getUserId());
			}
			ArrayList<Integer> userIdList=new ArrayList<Integer>();
			userIdList.addAll(userIdHashSet);
			BigDecimal payAmount=new BigDecimal("0.0");;
			BigDecimal urProfit=new BigDecimal("0.0");
			BigDecimal urMarkup=new BigDecimal("0.0");
			BigDecimal urCommission=new BigDecimal("0.0");
			BigDecimal commissionShared=new BigDecimal("0.0");			
			BigDecimal chlidMarkup=new BigDecimal("0.0");
			BigDecimal TDS=new BigDecimal("0.0");
			if(type==Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS){
				for(WalletAmountTranferHistory walletAmountTranferHistory:historyList){
					if(walletAmountTranferHistory.getAction().equalsIgnoreCase("FlightBooking Initiated")){
						payAmount=walletAmountTranferHistory.getAmount();					  
					}

					if(userIdList!=null && userIdList.size()>=2)
					{
						if(walletAmountTranferHistory.getAction().equalsIgnoreCase("Flight Markup")&& walletAmountTranferHistory.getUserId()==userIdList.get(1)){
							urMarkup=walletAmountTranferHistory.getAmount();

						}
						if(walletAmountTranferHistory.getAction().equalsIgnoreCase("Flight Commission Added")&& walletAmountTranferHistory.getUserId()==userIdList.get(1)){
							urCommission=walletAmountTranferHistory.getAmount();

						}
						if(walletAmountTranferHistory.getAction().equalsIgnoreCase("Flight Commission Shared")&& walletAmountTranferHistory.getUserId()==userIdList.get(1)){
							commissionShared=walletAmountTranferHistory.getAmount();

						}
						if(walletAmountTranferHistory.getAction().equalsIgnoreCase("TDS on commission")&& walletAmountTranferHistory.getUserId()==userIdList.get(1)){
							TDS=walletAmountTranferHistory.getAmount();

						}
						session.setAttribute("userid", userIdList.get(1));
					}
					if(userIdList!=null && userIdList.size()>2){
						if(walletAmountTranferHistory.getAction().equalsIgnoreCase("Flight Markup")&&walletAmountTranferHistory.getUserId()==userIdList.get(2)){
							chlidMarkup=walletAmountTranferHistory.getAmount();

						}
					}

					session.setAttribute("parentUserid", userIdList.get(0));
				}
			}else if(type==Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS){

				for(WalletAmountTranferHistory walletAmountTranferHistory:historyList){
					if(walletAmountTranferHistory.getAction().equalsIgnoreCase("FlightBooking Initiated")){
						payAmount=walletAmountTranferHistory.getAmount();					  
					}					
					if(userIdList!=null && userIdList.size()>=3)
					{
						if(walletAmountTranferHistory.getAction().equalsIgnoreCase("Flight Markup")&&walletAmountTranferHistory.getUserId()==userIdList.get(2)){
							urMarkup=walletAmountTranferHistory.getAmount();

						}
						if(walletAmountTranferHistory.getAction().equalsIgnoreCase("Flight Commission Added")&&walletAmountTranferHistory.getUserId()==userIdList.get(2)){
							urCommission=walletAmountTranferHistory.getAmount();

						}
						if(walletAmountTranferHistory.getAction().equalsIgnoreCase("TDS on commission")&&walletAmountTranferHistory.getUserId()==userIdList.get(2)){
							TDS=walletAmountTranferHistory.getAmount();
						}
					}

				}
				session.setAttribute("userid", userIdList.get(2));
				session.setAttribute("parentUserid", userIdList.get(1));    
			}
			payAmount=payAmount.subtract(chlidMarkup.add(urMarkup)).add(TDS);
			urProfit= (urMarkup.add(urCommission)).subtract(commissionShared).subtract(TDS);
			try{
				commissionShared = commissionShared.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				urCommission = urCommission.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				urMarkup = urMarkup.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				payAmount = payAmount.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				urProfit = urProfit.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				TDS = TDS.setScale(2,
						BigDecimal.ROUND_HALF_UP);

			} catch (Exception e) {
				commissionShared = new BigDecimal(0).setScale(0);
				urCommission = new BigDecimal(0).setScale(0);
				urMarkup = new BigDecimal(0).setScale(0);
				payAmount = new BigDecimal(0).setScale(0);
				urProfit = new BigDecimal(0).setScale(0);
				TDS = new BigDecimal(0).setScale(0);			
			}
			session.setAttribute("commissionShared", commissionShared);	
			session.setAttribute("urCommission", urCommission);	
			session.setAttribute("urMarkup", urMarkup);	
			session.setAttribute("paidAmount",payAmount);
			session.setAttribute("urProfit", urProfit);
			session.setAttribute("TDS", TDS);
		}
	}

	public void getHotelOrderInvoiceDetails(HttpSession session, String orderId, int type, int childUserId, int parentUserId)
			throws HibernateException {
		Session sess = null;
		List<WalletAmountTranferHistory> historyList=null;
		try{
			sess = sessionFactory.openSession();			
			List<Integer> userIds=new ArrayList<>();
			userIds.add(childUserId);
			userIds.add(parentUserId);
			Criteria walletHistoryCriteria=sess.createCriteria(WalletAmountTranferHistory.class);
			Conjunction conjuction=Restrictions.conjunction();
			conjuction.add(Restrictions.eq("actionId", orderId));
			conjuction.add(Restrictions.in("userId", userIds));
			walletHistoryCriteria.add(conjuction);			
			historyList = walletHistoryCriteria.list();
		}
		catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			sess.close(); 
		}
		BigDecimal payAmount=new BigDecimal("0.0");;
		BigDecimal urProfit=new BigDecimal("0.0");
		BigDecimal urMarkup=new BigDecimal("0.0");
		BigDecimal urCommission=new BigDecimal("0.0");
		BigDecimal commissionShared=new BigDecimal("0.0");		
		BigDecimal chlidMarkup=new BigDecimal("0.0");
		BigDecimal TDS=new BigDecimal("0.0");

		for(WalletAmountTranferHistory walletAmountTranferHistory:historyList){
			if(walletAmountTranferHistory.getAction().equalsIgnoreCase("HotelBookingInitiated")&&walletAmountTranferHistory.getUserId()==childUserId){
				payAmount=walletAmountTranferHistory.getAmount();					  
			}
			if(walletAmountTranferHistory.getAction().equalsIgnoreCase("Hotel Markup")&&walletAmountTranferHistory.getUserId()==childUserId){
				urMarkup=walletAmountTranferHistory.getAmount();
			}
			if(walletAmountTranferHistory.getAction().equalsIgnoreCase("Hotel Commission Added")&&walletAmountTranferHistory.getUserId()==childUserId){
				urCommission=walletAmountTranferHistory.getAmount();
			}
			if(walletAmountTranferHistory.getAction().equalsIgnoreCase("Hotel Commission Shared")&&walletAmountTranferHistory.getUserId()==childUserId){
				commissionShared=walletAmountTranferHistory.getAmount();
			}
			if(walletAmountTranferHistory.getAction().equalsIgnoreCase("TDS on commission")&&walletAmountTranferHistory.getUserId()==childUserId){
				TDS=walletAmountTranferHistory.getAmount();
			}			
		}		
		payAmount = payAmount.subtract(chlidMarkup.add(urMarkup)).add(TDS);
		urProfit= (urMarkup.add(urCommission)).subtract(commissionShared).subtract(TDS);

		try{
			commissionShared = commissionShared.setScale(2,
					BigDecimal.ROUND_HALF_UP);
			urCommission = urCommission.setScale(2,
					BigDecimal.ROUND_HALF_UP);
			urMarkup = urMarkup.setScale(2,
					BigDecimal.ROUND_HALF_UP);
			payAmount = payAmount.setScale(2,
					BigDecimal.ROUND_HALF_UP);
			urProfit = urProfit.setScale(2,
					BigDecimal.ROUND_HALF_UP);
			TDS = TDS.setScale(2,
					BigDecimal.ROUND_HALF_UP);

		} catch (Exception e) {
			commissionShared = new BigDecimal(0).setScale(0);
			urCommission = new BigDecimal(0).setScale(0);
			urMarkup = new BigDecimal(0).setScale(0);
			payAmount = new BigDecimal(0).setScale(0);
			urProfit = new BigDecimal(0).setScale(0);
			TDS = new BigDecimal(0).setScale(0);			
		}
		session.setAttribute("commissionShared", commissionShared);	
		session.setAttribute("urCommission", urCommission);	
		session.setAttribute("urMarkup", urMarkup);	
		session.setAttribute("paidAmount",payAmount);
		session.setAttribute("urProfit", urProfit);
		session.setAttribute("TDS", TDS);
	}

	@SuppressWarnings("finally")
	@Override
	public Company verifyCompanyEmail(String code, int verifyEmailType)
			throws HibernateException {
		Session sess = null;
		Transaction tx = null;
		Criteria crit = null;
		Company company = new Company();
		logger.info("########################## DB Email verification call");
		try{
			sess = sessionFactory.openSession();				
			Criteria criteria = sess.createCriteria(Company.class);
			Conjunction verifyConjunction = Restrictions.conjunction();
			verifyConjunction.add(Restrictions.eq("emailCode",code));
			criteria.add(verifyConjunction);
			company = (Company) criteria.uniqueResult();			
			if(company!=null && company.getMailStatus() != Email.STATUS_VERIFIED)
			{
				tx = sess.beginTransaction();
				company.setMailStatus(Email.STATUS_VERIFIED);	
			
				sess.update(company);
				tx.commit();
			}
			else if(company!=null && company.getMailStatus() == Email.STATUS_VERIFIED)
			{
				logger.info("########################## DB Company email is already verified");
				company = null;
			}
			else if(company == null)
			{
				logger.info("########################## DB Company is not found ");
				company = null;
			}
			sess.close();			
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("########################## DB Company Email verification update call error");
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			return company;
		}
	}
	@SuppressWarnings("finally")
	@Override
	public User verifyUserEmail(String code, int verifyEmailType)
			throws HibernateException {
		Session sess = null;
		Transaction tx = null;
		Criteria crit = null;
		User user = new User();
		logger.info("########################## DB User Email verification call");
		try{
			sess = sessionFactory.openSession();			
			Criteria userCriteria=sess.createCriteria(User.class);
			userCriteria.add(Restrictions.eq("emailCode", code));
			user = (User) userCriteria.uniqueResult();
			if(user!=null && user.getMailStatus() != Email.STATUS_VERIFIED)
			{
				tx = sess.beginTransaction();	
				user.setMailStatus(Email.STATUS_VERIFIED);				
				sess.update(user);
				tx.commit();
			}
			else
			{
				logger.info("########################## DB UserEmail verification call  Duplication-- Or user = null");
				user = null;
			}
			sess.close();			
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("########################## DB User Email verification update call error");
			logger.error(e);
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			return user;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Company getParentCompany(Company company)
			throws HibernateException {
		Session sess = null;		
		Criteria crit = null;
		Company parentcompany = null;
		logger.info("Email verification call");
		try{
			sess = sessionFactory.openSession();			
			crit = sess.createCriteria(Company.class);			
			crit.add(Restrictions.eq("company_userid", company.getParent_company_userid()));			
			parentcompany = (Company) crit.uniqueResult();				
			sess.close();	

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			return parentcompany;
		}
	}


	@SuppressWarnings("finally")
	@Override
	public Company getCompanyByCompanyUserId(String companyUserId)
			throws HibernateException {
		Session sess = null;		
		Criteria crit = null;
		Company parentcompany = null;		
		try{
			sess = sessionFactory.openSession();			
			crit = sess.createCriteria(Company.class);			
			crit.add(Restrictions.eq("company_userid", companyUserId));			
			parentcompany = (Company) crit.uniqueResult();				
			sess.close();	

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			return parentcompany;
		}
	}	

	@SuppressWarnings("finally")
	@Override
	public Company getParentCompany(User user)
			throws HibernateException {
		Session sess = null;		
		Criteria crit = null;
		Company company = null;
		Company parentcompany = null;
		logger.info("### get parent company call");
		try{
			sess = sessionFactory.openSession();
			DetachedCriteria detachAssignmentPrd=DetachedCriteria.forClass(User.class, "assignment");
			detachAssignmentPrd.add(Restrictions.eq("id",user.getId())).setProjection(Projections.property("assignment.Companyid"));
			Criteria criteria=sess.createCriteria(Company.class,"position");
			criteria.add(Subqueries.propertyIn("position.companyid", detachAssignmentPrd));		
			company = (Company) criteria.uniqueResult();
			if(!user.getUserrole_id().isSuperuser() && !user.getUserrole_id().isUsermode())// employee
			{
				parentcompany =  company;
			}
			else
			{
				crit = sess.createCriteria(Company.class);			
				crit.add(Restrictions.eq("company_userid", company.getParent_company_userid()));			
				parentcompany = (Company) crit.uniqueResult();				
			}

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			return parentcompany;
		}
	}


	@SuppressWarnings("finally")
	@Override
	public Company getUserCompany(User user)
			throws HibernateException {
		Session sess = null;		
		Criteria crit = null;
		Company company = null;

		
		try{
			sess = sessionFactory.openSession();			
			DetachedCriteria detachAssignmentPrd=DetachedCriteria.forClass(User.class, "assignment");
			detachAssignmentPrd.add(Restrictions.eq("id",user.getId())).setProjection(Projections.property("assignment.Companyid"));
			Criteria criteria=sess.createCriteria(Company.class,"position");
			criteria.add(Subqueries.propertyIn("position.companyid", detachAssignmentPrd));		
			company = (Company) criteria.uniqueResult();

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			return company;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public User getCompanyWalletUser(Company company)
			throws HibernateException {
		Session sess = null;		
		Criteria crit = null;
		User user = null;		
		
		try{
			sess = sessionFactory.openSession();			
			Criteria criteria = sess.createCriteria(User.class);
			Conjunction verifyConjunction = Restrictions.conjunction();
			verifyConjunction.add(Restrictions.eq("Companyid",company.getCompanyid()));
			verifyConjunction.add(Restrictions.eq("Email",company.getEmail()));
			criteria.add(verifyConjunction);		
			user = (User) criteria.uniqueResult();			

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			return user;
		}
	}


	@SuppressWarnings("finally")
	@Override
	public Company getImmediateChildCompanyBooked(Company company, Integer orderUserId)
			throws HibernateException {
		Session sess = null;		
		Criteria crit = null;
		Company parentCompany = null;
		Company bookchildCompany = null;		
		
		try{
			sess = sessionFactory.openSession();
			DetachedCriteria detachAssignmentPrd=DetachedCriteria.forClass(User.class, "assignment");
			detachAssignmentPrd.add(Restrictions.eq("id",orderUserId)).setProjection(Projections.property("assignment.Companyid"));
			Criteria criteria=sess.createCriteria(Company.class,"position");
			criteria.add(Subqueries.propertyIn("position.companyid", detachAssignmentPrd));		
			bookchildCompany = (Company) criteria.uniqueResult();
			if(bookchildCompany !=null)
			{
				if((company.getCompanyid()!=bookchildCompany.getCompanyid()))
				{
					logger.info("########################## DB get immediate book company call--Booker is not a super distributor");
					crit = sess.createCriteria(Company.class);			
					crit.add(Restrictions.eq("company_userid", bookchildCompany.getParent_company_userid()));			
					parentCompany = (Company) crit.uniqueResult();		
					if(parentCompany!=null && (parentCompany.getCompanyid()==company.getCompanyid()))
					{
						logger.info("########################## DB get immediate book company call--Distributor level book--parent is caller ");
						return bookchildCompany;
					}
					else if(parentCompany!=null && (parentCompany.getCompanyid()!=company.getCompanyid()))
					{
						logger.info("########################## DB get immediate book company call--Agency level book--parent is Distributor ");
						return parentCompany;
					}
				}
				else
				{
					logger.info("########################## DB get immediate book company call--Booker is a super distributor ");
					return parentCompany;
				}
			}
			sess.close();	

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
			return company;
		}
	}

	@Override
	public BugTrackerHistory getBugTrackerHistoryDeatails(String bugHistoryId) {
		// TODO Auto-generated method stub
		Session session=null;
		BugTrackerHistory  history=null;
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(BugTrackerHistory.class);
			criteria.add(Restrictions.eq("id",Long.parseLong(bugHistoryId)));
			history=(BugTrackerHistory) criteria.uniqueResult();
			if(history==null) 
				history=new BugTrackerHistory();
		}catch(HibernateException he){
			logger.info("------HibernateException------"+he.getMessage());	
		}
		catch(Exception e){
			logger.info("------Exception------"+e.getMessage());	
		}
		return history;
	}

	@Override
	public List<User> getAllTechHeadList() {
		// TODO Auto-generated method stub
		Session session=null;
		List<User>  techHeadList=null;
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(User.class);
			criteria.createCriteria("userrole_id").add(Restrictions.eq("isTechHead", true));
			techHeadList= criteria.list();
			if(techHeadList==null){
				techHeadList=new ArrayList<>();
			} 
		}catch(HibernateException he){
			logger.info("------HibernateException------"+he.getMessage());	
		}
		catch(Exception e){
			logger.info("------Exception------"+e.getMessage());	
		}
		return techHeadList;
	}

	@Override
	public User getTechSupportDetails(int userId) {
		// TODO Auto-generated method stub
		Session session=null;
		User user  =null;
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id",userId));
			user= (User) criteria.uniqueResult();
			if(user==null){
				user=new User();
			} 
		}catch(HibernateException he){
			logger.info("------HibernateException------"+he.getMessage());	
		}
		catch(Exception e){
			logger.info("------Exception------"+e.getMessage());	
		}
		return user;
	}


}