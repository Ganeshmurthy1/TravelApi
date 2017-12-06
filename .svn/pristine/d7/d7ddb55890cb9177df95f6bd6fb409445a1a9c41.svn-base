package com.tayyarah.user.service.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tayyarah.common.util.Status;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.user.entity.FrontUserDetail;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.model.UserProfileJson;

/**
 * @author info : Manish Samrat
 * @createdAt : 06/06/2017
 * @version : 1.0
 */
@Service("userAuthServices")
public class UserAuthenticationServices {

	@Autowired
	CompanyDao CmpDao;

	public Map<Object, Object> fetchUserOrFrontUserDetailById(String id, Map<Object, Object> responseMap,
			String userType) {

		UserProfileJson userProfileJson = new UserProfileJson();
		
		if (userType.equalsIgnoreCase("direct")) {
			FrontUserDetail frontUserDetail = CmpDao.fetchDirectUserByid(Long.parseLong(id));
			if (frontUserDetail != null)
				setFrontUserProfileJsonDetails(userProfileJson, frontUserDetail);
		} 
		
		else if (userType.equalsIgnoreCase("B2B") || userType.equalsIgnoreCase("B2C")
				|| userType.equalsIgnoreCase("B2E")) {
			User user = CmpDao.getUserById(Integer.parseInt(id));
			if (user != null)
				setUserProfileJsonDetails(userProfileJson, user);
		} 
		
		else
			responseMap.put("status", new Status(0, "Please enter the valid user type."));

		/*if Json created succesfully*/
		if (userProfileJson != null && userProfileJson.getId() > 0) {
			userProfileJson.setType(userType);
			responseMap.put("status", new Status(1, "Success."));
			responseMap.put("response", userProfileJson);
			return responseMap;
		} 
		else
			responseMap.put("status", new Status(0, "Not Found"));

		responseMap.put("response", null);
		return responseMap;
	}

	public UserProfileJson setUserProfileJsonDetails(UserProfileJson userProfileJson, User user) {
		try {
			userProfileJson.setWalletId(user.getAgentWallet().getWalletId());
			userProfileJson.setCompanyId(user.getCompanyid());
			userProfileJson.setRoleid(user.getUserrole_id().getRoleid());
			userProfileJson.setMailStatus(user.getMailStatus());
			userProfileJson.setAttempt(user.getAttemt());

			userProfileJson.setUserName(user.getUsername());
			userProfileJson.setFirstName(user.getFirstname());
			userProfileJson.setLastName(user.getLastname());
			// userProfileJson.setRole(user.getrol);
			userProfileJson.setPassword(user.getPassword());
			userProfileJson.setWalletType(user.getAgentWallet().getWalletType());
			userProfileJson.setWalletBalance(user.getAgentWallet().getWalletbalance());
			userProfileJson.setTransactionType(user.getAgentWallet().getTransactionType());
			userProfileJson.setDepositBalance(user.getAgentWallet().getDepositBalance());
			userProfileJson.setLogoDisplayable(user.getLogoDisplayable());
			userProfileJson.setAddress(user.getAddress());
			userProfileJson.setImagepath(user.getImagepath());
			userProfileJson.setEmail(user.getEmail());
			userProfileJson.setCity(user.getCity());
			userProfileJson.setPhone(user.getPhone());
			userProfileJson.setCompanyUserId(user.getCompany_userid());

			userProfileJson.setIsLocked(user.isLocked());
			userProfileJson.setIsStatus(user.isStatus());

			userProfileJson.setId(user.getId());
		} catch (Exception e) {
		}

		return userProfileJson;
	}

	public UserProfileJson setFrontUserProfileJsonDetails(UserProfileJson userProfileJson, FrontUserDetail userDetail) {
		try {
//			userProfileJson.setWalletId(userDetail.getAgentWallet().getWalletId());
//			userProfileJson.setCompanyId(userDetail.getCompanyid());
//			userProfileJson.setRoleid(userDetail.getUserrole_id().getRoleid());
//			userProfileJson.setMailStatus(userDetail.getMailStatus());
//			userProfileJson.setAttempt(userDetail.getAttemt());

			userProfileJson.setUserName(userDetail.getUserName());
			userProfileJson.setFirstName(userDetail.getFirstName());
			userProfileJson.setLastName(userDetail.getLastName());
			// userProfileJson.setRole(userDetail.getrol);
			userProfileJson.setPassword(userDetail.getPassword());
//			userProfileJson.setWalletType(userDetail.getAgentWallet().getWalletType());
//			userProfileJson.setWalletBalance(userDetail.getAgentWallet().getWalletbalance());
//			userProfileJson.setTransactionType(userDetail.getAgentWallet().getTransactionType());
//			userProfileJson.setDepositBalance(userDetail.getAgentWallet().getDepositBalance());
//			userProfileJson.setLogoDisplayable(userDetail.getLogoDisplayable());
//			userProfileJson.setAddress(userDetail.getAddress());
//			userProfileJson.setImagepath(userDetail.getImagepath());
			userProfileJson.setEmail(userDetail.getEmail());
			userProfileJson.setCity(userDetail.getCity());
//			userProfileJson.setPhone(userDetail.getPhone());
//			userProfileJson.setCompanyUserId(userDetail.getCompany_userid());
//
//			userProfileJson.setIsLocked(userDetail.isLocked());
//			userProfileJson.setIsStatus(userDetail.isStatus());
//
			userProfileJson.setId((int) (long)userDetail.getId());
		} catch (Exception e) {
		}

		return userProfileJson;
	}
}
