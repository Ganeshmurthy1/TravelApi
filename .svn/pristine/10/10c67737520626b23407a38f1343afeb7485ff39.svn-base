package com.tayyarah.email.dao;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;

import com.tayyarah.bugtracker.entity.BugTrackerHistory;
import com.tayyarah.company.entity.Company;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.user.entity.User;

public interface EmailDao {
	
	public List<Email> getPendingMails() throws HibernateException;
	public List<Email> getFailedMails(int status, int maxAttemptCount, int maxQueueLimit, int type,String orderid) throws HibernateException;	
	public List<Email> getMailsById(Long id) throws HibernateException;
	public List<Email> getMails(String orderid) throws HibernateException;
	public Email emailUpdateStatus(Email updateEmail) throws HibernateException;	
	public Email insertEmail(String orderId,int status,int emailType);	
	public Email getEmail(Long id) throws HibernateException;
	public void getOrderDetails(HttpSession ses,String orderId,int type) throws HibernateException;
	public void getHotelOrderInvoiceDetails(HttpSession session, String orderId, int type, int childUserId, int parentUserId)
			throws HibernateException;
	public Company verifyCompanyEmail(String code, int verifyEmailType)
			throws HibernateException;
	public User verifyUserEmail(String code, int verifyEmailType)
			throws HibernateException;
	public Company getParentCompany(Company company)
			throws HibernateException;
	public User getCompanyWalletUser(Company company)
			throws HibernateException;
	public Company getCompanyByCompanyUserId(String companyUserId)
			throws HibernateException;
	public Company getParentCompany(User user)
			throws HibernateException;	
	public Company getUserCompany(User user)
			throws HibernateException;
	public Company getImmediateChildCompanyBooked(Company company, Integer orderUserId)
			throws HibernateException;
	public  BugTrackerHistory  getBugTrackerHistoryDeatails(String bugHistoryId);
	public  List<User>  getAllTechHeadList();
	public  User  getTechSupportDetails(int userId);
	 
	
}
