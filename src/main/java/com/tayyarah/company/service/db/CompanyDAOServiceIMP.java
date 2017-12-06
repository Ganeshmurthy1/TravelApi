package com.tayyarah.company.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;


public class CompanyDAOServiceIMP implements CompanyDAOService {

	@Autowired
	CompanyDao companyDao;
	public List<Company> getCompanyEntityByCompanyid(int companyid)
			throws Exception {
		
		return companyDao.getCompanyBycompanyid(companyid);
	}

}
