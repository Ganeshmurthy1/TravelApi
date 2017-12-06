package com.tayyarah.company.service.db;

import java.util.List;

import com.tayyarah.company.entity.Company;



public interface CompanyDAOService {
	public  List<Company> getCompanyEntityByCompanyid(int companyid ) throws Exception;
}
