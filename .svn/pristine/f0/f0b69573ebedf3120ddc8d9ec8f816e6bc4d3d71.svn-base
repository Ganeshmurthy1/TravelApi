package com.tayyarah.umrah.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.umrah.entity.TayyarahUmrahContactDetails;


public class TayyarahUmrahUserDaoImp implements TayyarahumrahUserDao {
	
	@Autowired  
	 SessionFactory sessionFactory;  
	 Session session = null;  
	 Transaction tx = null;  
   
	 public TayyarahUmrahContactDetails addUser(TayyarahUmrahContactDetails user) throws Exception {  
	  session = sessionFactory.openSession();  
	  tx = session.beginTransaction();  
	  session.save(user);  
	  tx.commit();  
	  session.close();  
	  return user;  
	 } 
}
