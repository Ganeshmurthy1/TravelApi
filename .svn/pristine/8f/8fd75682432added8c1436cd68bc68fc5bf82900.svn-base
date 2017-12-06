/**
 * 
 */
package com.tayyarah.hotel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.util.Status;
import com.tayyarah.company.entity.Company;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.HotelOrderListResponse;
import com.tayyarah.hotel.model.HotelOrderRowDetailResponse;
import com.tayyarah.hotel.service.db.HotelOrderService;

/**
 * @author info : Manish Samrat
 * @createdAt : 01/06/2017
 * @version : 1.0
 */

@RestController
@RequestMapping("/Hotel")
public class HotelOrderController {

	@Autowired
	HotelOrderService hotelOrderService;

	Logger logger = Logger.getLogger(HotelOrderController.class);

	/**
	 * @param companyId,pageNo,pageSize;
	 *            (In Headers)
	 * @return HotelOrderListResponse orderListResponse;
	 */
	@RequestMapping("/GetBookingList")
	public @ResponseBody Map<Object, Object> getHotelBookingListById(
			@RequestHeader(value = "companyId", defaultValue = "0", required = true) int companyId,
			@RequestHeader(value = "pageNo", defaultValue = "0", required = true) int pageNo,
			@RequestHeader(value = "pageSize", defaultValue = "0", required = true) int pageSize) {

		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		List<HotelOrderListResponse> listResponse = new ArrayList<HotelOrderListResponse>();
		List<HotelOrderRow> orderRowlist = new ArrayList<HotelOrderRow>();

		Company company = new Company();
		if (companyId > 0) {
			company = hotelOrderService.getCompanyRoleTypeByCompanyId(companyId, company);

			if (company != null && company.getCompanyRole() != null) {
				orderRowlist = hotelOrderService.getFlightOrderRowList(company, orderRowlist, companyId, pageNo,
						pageSize);

				if (orderRowlist.size() > 0)
					hotelOrderService.getHotelOrderListResponse(orderRowlist, listResponse);

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
	 * @param hotelId;
	 *            (In Headers)
	 * @return HotelOrderRowDetailResponse detailResponse;
	 */
	@RequestMapping(value = "/GetBookingDetails", method = RequestMethod.GET)
	public @ResponseBody Map<Object, Object> getHotelBookingDetail(
			@RequestHeader(value = "hotelId", defaultValue = "0", required = true) long hotelId) {
		Map<Object, Object> responseMap = new HashMap<Object, Object>();

		if (hotelId > 0) {
			responseMap=hotelOrderService.getHotelOrderDetailsByHotelId(responseMap, hotelId);
			if (responseMap.size() > 1)
				return responseMap;
			else
				responseMap.put("status", new Status(0, "Booking Not Found"));
		} else
			responseMap.put("status", new Status(0, "Hotel Id is required."));

		responseMap.put("response", null);
		return responseMap;
	}
}
