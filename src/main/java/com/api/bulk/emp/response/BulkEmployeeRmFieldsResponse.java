package com.api.bulk.emp.response;

import com.api.bulk.emp.vo.BulkEmpDetails;

public class BulkEmployeeRmFieldsResponse {
	/**
	 * @author Basha
	 * created Date- 10-10-2017
	 */

	private boolean isRmDetailsSet;
	private BulkEmpDetails rmDataListDetails;
	private String message;
	public boolean isRmDetailsSet() {
		return isRmDetailsSet;
	}
	public BulkEmpDetails getRmDataListDetails() {
		return rmDataListDetails;
	}
	public String getMessage() {
		return message;
	}
	public void setRmDetailsSet(boolean isRmDetailsSet) {
		this.isRmDetailsSet = isRmDetailsSet;
	}
	public void setRmDataListDetails(BulkEmpDetails rmDataListDetails) {
		this.rmDataListDetails = rmDataListDetails;
	}
	public void setMessage(String message) {
		this.message = message;
	}
 
 
 
	
	
	
}
