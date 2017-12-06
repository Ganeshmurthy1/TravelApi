package com.tayyarah.user.dao;

import com.tayyarah.user.entity.FrontUserDetail;

public interface FrontUserDao {
	public String getCountryName(String countryid);
	public FrontUserDetail insertFrontUserDetail(FrontUserDetail frontUserDetail);
}
