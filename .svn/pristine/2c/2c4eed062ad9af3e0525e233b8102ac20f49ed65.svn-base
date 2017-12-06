package com.tayyarah.email.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.email.entity.model.Email;

public class EmailDaoServiceImp implements EmailDaoService{
	
	@Autowired
	EmailDao emailDao;

	@Override
	public List<Email> getEmailStatus(int status) throws HibernateException {
		// TODO Auto-generated method stub
		return emailDao.getPendingMails();
	}

}
