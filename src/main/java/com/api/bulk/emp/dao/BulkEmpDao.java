package com.api.bulk.emp.dao;

import com.api.bulk.emp.response.BulkEmpResponse;
import com.tayyarah.user.entity.User;

public interface BulkEmpDao {
	/**
	 * @author Basha
	 * created Date- 22-09-2017
	 */
	public BulkEmpResponse builtDatabulkEmployeeAutoCompleter(User userIndividualData);
	

}
