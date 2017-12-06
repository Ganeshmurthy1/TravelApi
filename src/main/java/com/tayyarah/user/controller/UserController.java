/**
 * 
 */
package com.tayyarah.user.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.util.Status;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.user.entity.FrontUserDetail;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.model.UserAuthenticationRequest;
import com.tayyarah.user.model.UserAuthenticationResponse;
import com.tayyarah.user.service.db.UserAuthenticationServices;

/**
 * @author info : Manish Samrat
 * @createdAt : 24/05/2017
 * @version : 1.0
 */

@RestController
@RequestMapping("/Authentication")
public class UserController {

	@Autowired
	CompanyDao CmpDao;
	@Autowired
	UserAuthenticationServices userAuthServices;
	
	public static final Logger logger = Logger.getLogger(UserController.class);

	/**
	 * @param UserAuthenticationRequest authenticationRequest;  (i.e  userName, password, passkey)
	 * @return UserAuthenticationResponse authenticationResponse;
	 */
	@RequestMapping(value = "/User", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<Object, Object> userResponse(@RequestBody UserAuthenticationRequest userAuthReq)
			throws Exception {

		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		UserAuthenticationResponse authenticationResponse=null;
		
		if (userAuthReq.getEmailId().trim().equals("")) 
			responseMap.put("status", new Status(0,"Please enter your Mail id."));
		
		else if (userAuthReq.getPassword().trim().equals("")) 
			responseMap.put("status", new Status(0,"Please enter your Password."));
		
		else if (userAuthReq.getPasskey().trim().equals("")) 
			responseMap.put("status", new Status(0,"Please enter your Passkey."));
		
		else {
			try {
				User user = CmpDao.getAuthentherisedUser(userAuthReq);
				String appKey = CmpDao.getAppKeyByCompanyId(user.getCompanyid());
				authenticationResponse = new UserAuthenticationResponse();
				if (user != null && appKey != null) {
					authenticationResponse.setEmailId(user.getEmail());
					authenticationResponse.setAppKey(appKey);
					authenticationResponse.setCompanyId(user.getCompanyid());
					authenticationResponse.setFirstName(user.getFirstname());
					authenticationResponse.setLastName(user.getLastname());
					authenticationResponse.setWalletId(user.getAgentWallet().getWalletId());
					authenticationResponse.setUserRoleId(user.getUserrole_id().getRoleid());
					
					responseMap.put("status", new Status(1,"Success."));
					responseMap.put("response", authenticationResponse);
					return responseMap;
				}
			} catch (Exception e) {
				responseMap.put("status", new Status(0,"User Not Authorised / Invalid login."));
			}
		}
		
		responseMap.put("response", null);
		return responseMap;
	}

	/**
	 * @param UserAuthenticationRequest authenticationRequest;  (i.e  userName, password, passkey)
	 * @return UserAuthenticationResponse authenticationResponse;
	 */
	@RequestMapping(value = "/DirectUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<Object, Object> validatingVisitingUser(
			@RequestBody UserAuthenticationRequest userAuthReq) {

		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		UserAuthenticationResponse authenticationResponse=null;
		
		if (userAuthReq.getEmailId().trim().equals("")) 
			responseMap.put("status", new Status(0,"Please enter your Mail id."));
		
		else if (userAuthReq.getPassword().trim().equals("")) 
			responseMap.put("status", new Status(0,"Please enter your Password."));
		
		else {
			try {
				FrontUserDetail frontUserDetail = CmpDao.getAuthorisedVisitor(userAuthReq);
				if (frontUserDetail != null) {
					User user = CmpDao.getUserByCompanyEmail("DirectUser@intellicommsolutions.com");
					String appKey = CmpDao.getAppKeyByCompanyId(user.getCompanyid());
					authenticationResponse = new UserAuthenticationResponse();
					authenticationResponse.setEmailId(frontUserDetail.getEmail());
					authenticationResponse.setAppKey(appKey);
					authenticationResponse.setCompanyId(user.getCompanyid());
					authenticationResponse.setFirstName(frontUserDetail.getFirstName());
					authenticationResponse.setLastName(frontUserDetail.getLastName());
					
					responseMap.put("status", new Status(1,"Success."));
					responseMap.put("response", authenticationResponse);
					return responseMap;
				} 
				
				else 
					responseMap.put("status", new Status(0,"User Not Authorised / Invalid login"));
				
			} catch (Exception e) {
				responseMap.put("status", new Status(0,"User Not Authorised / Invalid login"));
			}
		}

		responseMap.put("response", null);
		return responseMap;
	}

	/**
	 * @param FrontUserDetail userDetail; (i.e firstName,lastName,email,city,country,mobile,password,phone,state,streetAddress,zipCode)
	 * @return FrontUserDetail userDetailResponse;
	 */
	@RequestMapping(value = "/Register", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<Object, Object> insertVisitorsData(@RequestBody FrontUserDetail userDetail) {

		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		if (userDetail != null) {
			if(CmpDao.verifyingEmailExistence(userDetail.getEmail())){
				responseMap.put("status", new Status(0,"User Already Exist"));
				responseMap.put("response", null);
				return responseMap;
			}
			else {
				try {
					Date date=new Date();
					long time = date.getTime();
					Timestamp newDate=new Timestamp(time);
					userDetail.setCreatedAt(newDate);
					userDetail.setUpdatedAt(newDate);
					userDetail.setUserName(userDetail.getEmail());
					FrontUserDetail userDetailResponse=CmpDao.insertVisiterInfo(userDetail);
					if(userDetailResponse!=null){
						responseMap.put("status", new Status(1,"Inserted Successfully"));
						responseMap.put("response", userDetailResponse);
						return responseMap;
					}
				} catch (Exception e) {
					responseMap.put("status", new Status(0,"Insertion Failed"));
				}
			}
		}
		else{
			responseMap.put("status", new Status(0,"Incomplete input / Please fill all required Fields."));
		}
		responseMap.put("response", null);
		return responseMap;
	}
	
	/**
	 * @param Headers (id, userType)  {ex :- userType :(anyone) direct,B2B,B2C,B2E  && id=1,2,3...  (any userid )}
	 * @return User user;
	 */
	@RequestMapping(value="/UserProfile",method=RequestMethod.GET)
	public @ResponseBody Map<Object, Object> fetchUserprofile(@RequestHeader(value = "id",defaultValue="0") String id,@RequestHeader(value = "userType",defaultValue="") String userType){
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		try{
			int userid=Integer.parseInt(id);
			if(userid<=0)
				responseMap.put("status", new Status(0,"Please enter the user Id."));
			
			else if(userType.trim().equals(""))
				responseMap.put("status", new Status(0,"User type should not be blank."));
			
			else{
				responseMap=userAuthServices.fetchUserOrFrontUserDetailById(id, responseMap, userType);
				
				if(responseMap.size()>0)
					return responseMap;
			}
		 }catch (NumberFormatException e) {
			 responseMap.put("status", new Status(0,"Id  not valid, must be a number."));
		}
		catch (Exception e) {
			responseMap.put("status", new Status(0,"Oops Server error."));
		}
		responseMap.put("response", null);
		return responseMap;
	}
}
