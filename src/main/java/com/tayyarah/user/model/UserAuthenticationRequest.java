/**
 * 
 */
package com.tayyarah.user.model;

/**
 * @author info : Manish Samrat
 * @createdAt   : 24/05/2017
 * @version     : 1.0
 */

public class UserAuthenticationRequest {

	private String emailId;
	private String password;
	private String passkey;
	private String userType;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasskey() {
		return passkey;
	}
	public void setPasskey(String passkey) {
		this.passkey = passkey;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
