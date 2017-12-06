package com.tayyarah.common.util;

import java.util.Calendar;

import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.user.dao.FrontUserDao;
import com.tayyarah.user.entity.FrontUserDetail;



public class GetFrontUserDetail {
	
	public static FrontUserDetail getFrontUserDetailDetails(OrderCustomer orderCustomer,FrontUserDao frontUserDao){
		FrontUserDetail frontUserDetail = new FrontUserDetail();
		frontUserDetail.setFirstName(orderCustomer.getFirstName());
		frontUserDetail.setLastName(orderCustomer.getLastName());
		frontUserDetail.setEmail(orderCustomer.getEmail());
		frontUserDetail.setUserName(orderCustomer.getEmail());
		frontUserDetail.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		frontUserDetail.setUpdatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		frontUserDetail.setCity(orderCustomer.getCity()!=null?orderCustomer.getCity():"");
		frontUserDetail.setCountry(orderCustomer.getCountryId()!=null?frontUserDao.getCountryName(orderCustomer.getCountryId()):"");
		frontUserDetail.setIsLocked(0);
		frontUserDetail.setState(orderCustomer.getState()!=null?orderCustomer.getState():"");
		frontUserDetail.setPassword(RandomConfigurationNumber.randomFrontUserPassword());
		frontUserDetail.setMobile(orderCustomer.getMobile()!=null?orderCustomer.getMobile():"");
		frontUserDetail.setPhone(orderCustomer.getPhone()!=null?orderCustomer.getPhone():"");
		frontUserDetail.setRole(0);
		frontUserDetail.setVersion(1);
		frontUserDetail.setZipCode(orderCustomer.getZip()!=null?orderCustomer.getZip():"");
		frontUserDetail.setStatus(0);
		frontUserDetail.setLanguageCode("ENG");	
		frontUserDetail.setCompanyId(orderCustomer.getCompanyId()!=null?orderCustomer.getCompanyId():-1);
		frontUserDetail.setConfigId(orderCustomer.getConfigId()!=null?orderCustomer.getConfigId():-1);
		return frontUserDetail;
	}

}
