package com.tayyarah.umrah.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.umrah.dao.TayyarahumrahUserDao;
import com.tayyarah.umrah.entity.TayyarahUmrahContactDetails;




public class TayyarahUmrahUserServiceImp implements TayyarahUmrahUserService{
	@Autowired  
	private TayyarahumrahUserDao tayyarahUmrahDao;  

	public TayyarahUmrahContactDetails saveUser(TayyarahUmrahContactDetails user){  
		try {
			tayyarahUmrahDao.addUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}
}