package com.api.bulk.emp.dao;

import com.api.bulk.emp.response.BulkEmpResponse;
import com.tayyarah.user.entity.User;

public class BulkEmpDaoImpl implements BulkEmpDao{
	public static final org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(BulkEmpDaoImpl.class);
	/**
	 * @author Basha
	 * created Date- 22-09-2017
	 */

	@Override
	public BulkEmpResponse builtDatabulkEmployeeAutoCompleter(User userIndividualData) {
		BulkEmpResponse bulkEmployeeAutoCompleter=new BulkEmpResponse();
		bulkEmployeeAutoCompleter.setFirstName(userIndividualData.getFirstname()!=null?userIndividualData.getFirstname():"N/A");
		bulkEmployeeAutoCompleter.setLastName(userIndividualData.getLastname()!=null?userIndividualData.getLastname():"N/A");
		bulkEmployeeAutoCompleter.setEmail(userIndividualData.getEmail()!=null?userIndividualData.getEmail():"N/A");
		// TODO Auto-generated method stub
		return bulkEmployeeAutoCompleter;
	}
	
	

}
