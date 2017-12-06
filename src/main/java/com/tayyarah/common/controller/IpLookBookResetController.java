/**
 * 
 */
package com.tayyarah.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.admin.analytics.lookbook.dao.LookBookDao;


/**
 * @author      : Manish Samrat
 * @createdAt   : 25/07/2017
 * @version
 * @updateaBy   :  
 */
@RestController
@RequestMapping(value = "/reset")
public class IpLookBookResetController {
	@SuppressWarnings("rawtypes")
	@Autowired
	LookBookDao lookBookDao;

	@RequestMapping(value = "/ipStatusForB2C", method = RequestMethod.GET, headers = "Accept=application/json") 
	public @ResponseBody synchronized String ipStatusForB2C(HttpServletRequest request, HttpServletResponse response) throws  Exception {
		try {
			lookBookDao.resetAllB2C();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";
	}

}
