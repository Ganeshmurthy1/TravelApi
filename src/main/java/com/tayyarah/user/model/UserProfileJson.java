package com.tayyarah.user.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author info : Manish Samrat
 * @createdAt : 06/06/2017
 * @version : 1.0
 */

public class UserProfileJson implements Serializable {
 
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int walletId;
	public int companyId;
	private int roleid;
	public int mailStatus;
	private int attempt;
	
	public String type;
	public String UserName;
	public String firstName;
	public String lastName;
	public String role;
	public String password;
	public String walletType;
	public BigDecimal walletBalance;
	public String transactionType;
	public BigDecimal depositBalance;
	public String logoDisplayable;
	public String address;
	public String imagepath;
	public String email;
	public String city;
	public String phone;
	public String companyUserId;
	
	public Boolean isLocked;
	public Boolean isStatus;
	public int getId() {
		return id;
	}
	public int getWalletId() {
		return walletId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public int getRoleid() {
		return roleid;
	}
	public int getMailStatus() {
		return mailStatus;
	}
	public int getAttempt() {
		return attempt;
	}
	public String getType() {
		return type;
	}
	public String getUserName() {
		return UserName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getRole() {
		return role;
	}
	public String getPassword() {
		return password;
	}
	public String getWalletType() {
		return walletType;
	}
	public BigDecimal getWalletBalance() {
		return walletBalance;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public BigDecimal getDepositBalance() {
		return depositBalance;
	}
	public String getLogoDisplayable() {
		return logoDisplayable;
	}
	public String getAddress() {
		return address;
	}
	public String getImagepath() {
		return imagepath;
	}
	public String getEmail() {
		return email;
	}
	public String getCity() {
		return city;
	}
	public String getPhone() {
		return phone;
	}
	public String getCompanyUserId() {
		return companyUserId;
	}
	public Boolean getIsLocked() {
		return isLocked;
	}
	public Boolean getIsStatus() {
		return isStatus;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public void setMailStatus(int mailStatus) {
		this.mailStatus = mailStatus;
	}
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
	public void setWalletBalance(BigDecimal walletBalance) {
		this.walletBalance = walletBalance;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setDepositBalance(BigDecimal depositBalance) {
		this.depositBalance = depositBalance;
	}
	public void setLogoDisplayable(String logoDisplayable) {
		this.logoDisplayable = logoDisplayable;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setCompanyUserId(String companyUserId) {
		this.companyUserId = companyUserId;
	}
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public void setIsStatus(Boolean isStatus) {
		this.isStatus = isStatus;
	}
}
