package com.tayyarah.flight.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;

@RestController
@RequestMapping("/SearchBycid")
public class FlightSearchControllerOld {
	static final Logger logger = Logger.getLogger(FlightSearchControllerOld.class);
	@Autowired
	CompanyDao companyDao;		
	
	@RequestMapping(value = "/byCid/{cid}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<Company> getCompanyBycompanyid(@PathVariable("cid") int companyid) {
		List<Company> companiesid = null;
		try {			
			companiesid = companyDao.getCompanyBycompanyid(companyid);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return companiesid;
	}
	
	@RequestMapping(value = "/getcompid/{email}/{password}",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	String getcompid(@PathVariable("email") String email,@PathVariable("password") String password) {
		String companiesid = "invalid";
		try {
			logger.info("CompanyBycompanyid controller : "+email);
			logger.info("CompanyBycompanyid controller : "+password);
			
			companiesid = companyDao.getAppKey(email, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return companiesid;
	}	
}