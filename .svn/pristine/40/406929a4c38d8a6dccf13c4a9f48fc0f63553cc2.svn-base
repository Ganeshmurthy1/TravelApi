package com.tayyarah.umrah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.tayyarah.common.util.DateConversion;
import com.tayyarah.common.util.Status;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.umrah.entity.TayyarahUmrahContactDetails;
import com.tayyarah.umrah.service.TayyarahUmrahUserService;



/**
 * @author      : Harsha M R
 * @createdAt   : 29/08/2017
 * @version
 * @updateaBy   :  
 */
@RestController
@RequestMapping(value = "/user")
public class TayyarahUmrahController { 
	
	@Autowired
	private TayyarahUmrahUserService userService; 
	@Autowired
	private EmailDao emailDao;
 
	@RequestMapping(value = "/createUser", method = RequestMethod.POST,headers="Accept=application/json")
	public Status insertUser(@RequestBody TayyarahUmrahContactDetails user) { 
		 Email email=new Email();
		  //countryService.addCountry(country);  
		  try {  
			  if(user!=null){
				  if(user.getStartDateTemp()!=null && !user.getStartDateTemp().trim().equalsIgnoreCase(""))
				  user.setStartDate(DateConversion.StringToDate(user.getStartDateTemp()));
				  if(user.getReturnDateTemp()!=null && !user.getReturnDateTemp().trim().equalsIgnoreCase(""))
				  user.setReturnDate(DateConversion.StringToDate(user.getReturnDateTemp()));
				  TayyarahUmrahContactDetails tayyarahUmrahContactDetails=userService.saveUser(user);
				  if(tayyarahUmrahContactDetails.getId()>0){
					  email=emailDao.insertEmail(Integer.toString(tayyarahUmrahContactDetails.getId()), 0, email.EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH_UMRAH);
					  if(email!=null && email.getId()!=null)
						  System.out.println("Email iS iNSERTED SUCESSFULLY ...........fOR UMRAH  "+email.getId());
					  else
						  System.out.println("Email iS iNSERTED Failed ...........fOR UMRAH  ");
					  return new Status(1, "Enquiry send Successfully !");
				  }
				  else
					  return new Status(0, "Enquiry send Unsucessfull !");  
			  }else{
				  return new Status(0,"User Object Is Empty"); 
			  }
			  } catch (Exception e) {  
			   // e.printStackTrace();  
			   return new Status(0, e.toString());  
			  }  
		  
		 }
	
}
