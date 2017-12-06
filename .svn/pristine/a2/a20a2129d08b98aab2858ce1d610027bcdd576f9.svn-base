/**
 * 
 */
package com.tayyarah.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.util.Status;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.user.model.UserAuthenticationRequest;
import com.tayyarah.user.model.UserWalletResponse;
import com.tayyarah.user.service.db.UserUtilityServices;

/**
 * @author info : Manish Samrat
 * @createdAt : 29/05/2017
 * @version : 1.0
 */

@RestController
@RequestMapping(value="/Wallet")
public class UserWalletController {

	@Autowired
	CompanyDao CmpDao;
	
	@Autowired
	UserUtilityServices utilServices;
	
	public static final Logger logger = Logger.getLogger(UserWalletController.class);
	/**
	 * @param UserAuthenticationRequest authenticationRequest;  (i.e  userName, password, passkey)
	 * @return UserWalletResponse walletResponse;
	 */
	@RequestMapping(value="Details",method=RequestMethod.POST)
	public @ResponseBody Map<Object,Object> getwalletInfo(@RequestBody UserAuthenticationRequest authenticationRequest){
		
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		
		if (authenticationRequest.getEmailId().trim().equals("")) 
			responseMap.put("status", new Status(0,"Please enter your Mail id."));
		
		else if (authenticationRequest.getPassword().trim().equals("")) 
			responseMap.put("status", new Status(0,"Please enter your Password."));
		
		else if (authenticationRequest.getPasskey().trim().equals("")) 
			responseMap.put("status", new Status(0,"Please enter your Passkey."));
		
		else {
			try{
				utilServices.fetchingAutorisedUserDetails(authenticationRequest, responseMap);
				if(responseMap.size()>1)
					return responseMap;
			}catch (Exception e) {
				responseMap.put("status", new Status(0,"Oop's Database/Server problem, Please try after sometime."));
			}
		}
		responseMap.put("response", null);
		return responseMap;
	}
}
