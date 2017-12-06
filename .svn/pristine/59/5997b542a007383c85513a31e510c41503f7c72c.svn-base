package com.tayyarah.quotation.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tayyarah.common.util.Timestampable;


@Entity
@Table(name = "miscellaneous_travel_request")
public class MiscellaneousTravelRequest  extends Timestampable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(targetEntity = MiscellaneousTravelRequestQuotation.class,   mappedBy = "miscellaneousTravelRequest" , cascade = CascadeType.ALL)
	private List<MiscellaneousTravelRequestQuotation> miscellaneousTravelRequestQuotations; 


	
	@Column(name = "company_id" ,columnDefinition="INT(11) default 0")
	private int companyId;
	@Column(name = "user_id",columnDefinition="INT(11) default 0")
	private int userId;
	@Column(name="title")
	private String title;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name = "status_id")
	 private int statusId;
	@Column(name="tr_no")
	private String TRNo;
	
	
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getTRNo() {
		return TRNo;
	}
	public void setTRNo(String tRNo) {
		TRNo = tRNo;
	}
	public List<MiscellaneousTravelRequestQuotation> getMiscellaneousTravelRequestQuotations() {
		return miscellaneousTravelRequestQuotations;
	}
	public void setMiscellaneousTravelRequestQuotations(
			List<MiscellaneousTravelRequestQuotation> miscellaneousTravelRequestQuotations) {
		this.miscellaneousTravelRequestQuotations = miscellaneousTravelRequestQuotations;
	}
}