package com.tayyarah.common.entity;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author     Manish Samrat
 * @createdAt  10/04/2017
 *
 */

@Entity
@Table(name="rm_config_trip_details")
public class RmConfigTripDetailsModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id; 	
	/*private long tripId;	*/
	private String empCode;
	private String department;
	private String costCenter;
	private String approverName;
	private String location;
	private String trNumber;
	private String bussinessUnit;
	private String projectCode;
	private String reasonForTravel;
	private String billNonBill;		
	private String manualField1;
	private String manualField2;
	private String manualField3;
	private String manualField4;
	private String manualField5;
    private String ordertype; 
	private String orderId; 
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	} 	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/*public long getTripId() {
		return tripId;
	}
	public void setTripId(long tripId) {
		this.tripId = tripId;
	}*/
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTrNumber() {
		return trNumber;
	}
	public void setTrNumber(String trNumber) {
		this.trNumber = trNumber;
	}
	public String getBussinessUnit() {
		return bussinessUnit;
	}
	public void setBussinessUnit(String bussinessUnit) {
		this.bussinessUnit = bussinessUnit;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getReasonForTravel() {
		return reasonForTravel;
	}
	public void setReasonForTravel(String reasonForTravel) {
		this.reasonForTravel = reasonForTravel;
	}
	public String getBillNonBill() {
		return billNonBill;
	}
	public void setBillNonBill(String billNonBill) {
		this.billNonBill = billNonBill;
	}
	public String getManualField1() {
		return manualField1;
	}
	public void setManualField1(String manualField1) {
		this.manualField1 = manualField1;
	}
	public String getManualField2() {
		return manualField2;
	}
	public void setManualField2(String manualField2) {
		this.manualField2 = manualField2;
	}
	public String getManualField3() {
		return manualField3;
	}
	public void setManualField3(String manualField3) {
		this.manualField3 = manualField3;
	}
	public String getManualField4() {
		return manualField4;
	}
	public void setManualField4(String manualField4) {
		this.manualField4 = manualField4;
	}
	public String getManualField5() {
		return manualField5;
	}
	public void setManualField5(String manualField5) {
		this.manualField5 = manualField5;
	}	
}