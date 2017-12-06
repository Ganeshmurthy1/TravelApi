package com.tayyarah.company.service.db;

import java.util.List;

import com.tayyarah.company.entity.CompanyConfig;


public interface CompanyConfigDAOService {
	public  List<CompanyConfig> getCompanyConfigEntityByconfig_id(int config_id ) throws Exception;

}
