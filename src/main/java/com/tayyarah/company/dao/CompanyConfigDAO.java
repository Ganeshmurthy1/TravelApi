package com.tayyarah.company.dao;

import java.util.List;

import com.tayyarah.company.entity.CompanyConfig;


public interface CompanyConfigDAO {
	public List<CompanyConfig> getCompanyConfigconfig_id(int config_id) throws Exception;
	public CompanyConfig getCompanyConfigByConfigId(int configId) throws Exception;
}
