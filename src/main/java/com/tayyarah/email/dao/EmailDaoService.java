package com.tayyarah.email.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.email.entity.model.Email;

public interface EmailDaoService {
	public List<Email> getEmailStatus( int status) throws HibernateException;
}
