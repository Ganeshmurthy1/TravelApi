package com.tayyarah.company.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.entity.CompanyConfig;


public class CompanyConfigDAOServiceIMP implements CompanyConfigDAOService {
	@Autowired
	CompanyConfigDAO companyconfig;
	public List<CompanyConfig> getCompanyConfigEntityByconfig_id(int config_id)
			throws Exception {
		
		return companyconfig.getCompanyConfigconfig_id(config_id);
	}

}
