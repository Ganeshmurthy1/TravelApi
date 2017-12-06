package com.tayyarah.email.dao;

import java.util.List;
import org.hibernate.HibernateException;

import com.tayyarah.company.entity.Company;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.email.entity.model.EmailNotification;
import com.tayyarah.user.entity.User;

public interface EmailNotificationDao {
	public List<EmailNotification> getPendingMailNotifications() throws HibernateException;
	public List<EmailNotification> getFailedMailNotifications() throws HibernateException;
	public EmailNotification emailUpdateStatus(EmailNotification updateEmail) throws HibernateException;	
	public EmailNotification insertEmailNotification(EmailNotification emailNotification, Email email) throws HibernateException;
	public EmailNotification insertEmailNotification(Email email) throws HibernateException;
	public EmailNotification insertEmailNotification(Company receipientCompany, Company performingCompany, Company receivingCompany, User performingUser, int actionType, Email email) throws HibernateException;
		
}
