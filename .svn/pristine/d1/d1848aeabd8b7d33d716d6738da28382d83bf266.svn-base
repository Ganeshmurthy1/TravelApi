/**
 * 
 */
package com.tayyarah.flight.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.log.SysoCounter;
import com.tayyarah.common.util.Status;
import com.tayyarah.company.entity.Company;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.model.FlightOrderListResponse;
import com.tayyarah.flight.service.db.FlightOrderService;


/**
 * @author info : Manish Samrat
 * @createdAt : 30/05/2017
 * @version : 1.0
 */

@RestController
@RequestMapping(value = "/Flights")
public class FlightOrderController {

	@Autowired
	FlightOrderService flightOrderService;

	/**
	 * @param companyId,pageNo,pageSize;
	 *            (In Headers)
	 * @return List<FlightOrderListResponse> listResponse;
	 */
	@RequestMapping(value = "/GetBookingList", method = RequestMethod.GET)
	public @ResponseBody Map<Object, Object> getFlightBookingListById(
			@RequestHeader(value = "companyId", defaultValue = "0", required = true) int companyId,
			@RequestHeader(value = "pageNo", defaultValue = "0", required = true) int pageNo,
			@RequestHeader(value = "pageSize", defaultValue = "0", required = true) int pageSize)

	{

		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		List<FlightOrderListResponse> listResponse = new ArrayList<FlightOrderListResponse>();
		List<FlightOrderRow> orderRowlist= new ArrayList<FlightOrderRow>();
		Company company=new Company();
		if (companyId > 0) {
//			Company company = CmpDao.getCompany(companyId);
			company=flightOrderService.getCompanyRoleTypeByCompanyId(companyId,company);
			
			if (company != null && company.getCompanyRole() != null) {
				orderRowlist = flightOrderService.getFlightOrderRowList(company,orderRowlist, companyId, pageNo, pageSize);

				if (orderRowlist.size() > 0)
					listResponse = flightOrderService.getFlightOrderListResponse(orderRowlist, listResponse);

				if (listResponse.size() > 0) {
					responseMap.put("status", new Status(1, "Success."));
					responseMap.put("response", listResponse != null ? listResponse : null);
					return responseMap;
				}
				responseMap.put("status", new Status(0, "No booking of that person."));
			} else
				responseMap.put("status", new Status(0, "Company not exist"));
		} else
			responseMap.put("status", new Status(0, "Company Id is required."));

		responseMap.put("response", null);
		return responseMap;
	}

	/**
	 * @param flightId;
	 *            (In Headers)
	 * @return FlightOrderRowDetailResponse detailResponse;
	 */
	@RequestMapping(value = "/GetBookingDetails", method = RequestMethod.GET)
	public @ResponseBody Map<Object, Object> getFlightBookingDetail(
			@RequestHeader(value = "flightId", defaultValue = "0", required = true) long flightId) {
		
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		
		if (flightId > 0) {
			responseMap=flightOrderService.getFlightOrderDetailsByFlightId(responseMap, flightId);
			if (responseMap.size()>1)
				return responseMap;
			 else
				responseMap.put("status", new Status(0, "Booking Not Found"));
		} else
			responseMap.put("status", new Status(0, "Flight Id is required."));

		responseMap.put("response", null);
		return responseMap;
	}
}
