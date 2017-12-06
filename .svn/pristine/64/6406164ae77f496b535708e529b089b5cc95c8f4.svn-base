package com.tayyarah.hotel.util.api.concurrency;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.dao.ApiHotelMapStoreDao;
import com.tayyarah.hotel.model.APIHotelMap;



public class DBHandlerTask implements Callable<Boolean> {

	@Autowired
	ApiHotelMapStoreDao apihotelstoredao;

	public APIHotelMap getApistack() {
		return apistack;
	}
	public void setApistack(APIHotelMap apistack) {
		this.apistack = apistack;
	}
	APIHotelMap apistack;
	public DBHandlerTask(APIHotelMap apistack) {
		super();
		this.apistack = apistack;
	}
	@Override
	public Boolean call() throws Exception {
		//insertApiMapBySearchKey
		try {
			apihotelstoredao.insertApiMapBySearchKey(5, apistack);
		} catch (HibernateException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return true;
	}
}