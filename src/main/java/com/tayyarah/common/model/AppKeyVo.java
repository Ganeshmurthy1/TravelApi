package com.tayyarah.common.model;

import java.io.Serializable;

import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;


public class AppKeyVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int companyId;
	private int configId;
	private Company company;
	private CompanyConfig companyConfig;
	private String appKey;


	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getConfigId() {
		return configId;
	}
	public void setConfigId(int configId) {
		this.configId = configId;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public CompanyConfig getCompanyConfig() {
		return companyConfig;
	}
	public void setCompanyConfig(CompanyConfig companyConfig) {
		this.companyConfig = companyConfig;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

}
