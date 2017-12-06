package com.tayyarah.user.service.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tayyarah.common.util.Status;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.model.UserAuthenticationRequest;
import com.tayyarah.user.model.UserWalletResponse;

/**
 * @author info : Manish Samrat
 * @createdAt : 06/06/2017
 * @version : 1.0
 */

@Service("utilServices")
public class UserUtilityServices {

	@Autowired
	CompanyDao CmpDao;
	
	public Map<Object, Object> fetchingAutorisedUserDetails(UserAuthenticationRequest authenticationRequest,Map<Object, Object> responseMap){
		try{
			User user = CmpDao.getAuthentherisedUser(authenticationRequest);
			if(user!=null){
				UserWalletResponse userWalletResponse=new UserWalletResponse();
				userWalletResponse.setCurrencyCode(user.getAgentWallet().getCurrencyCode());
				userWalletResponse.setTransactionType(user.getAgentWallet().getTransactionType());
				userWalletResponse.setWalletBalance(user.getAgentWallet().getWalletbalance());
				userWalletResponse.setWalletType(user.getAgentWallet().getWalletType());
				responseMap.put("status", new Status(1,"Success"));
				responseMap.put("response", userWalletResponse);
			}
			else {
				responseMap.put("status", new Status(0,"User Not Found/Exist."));
				responseMap.put("response", null);
			}
		}catch (Exception e) {
			responseMap.put("status", new Status(0,"User Not Found/Exist."));
			responseMap.put("response", null);
		}
			
		return responseMap;
	}
}
