package com.tayyarah.hotel.service.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tayyarah.common.util.Status;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.HotelOrderListResponse;
import com.tayyarah.hotel.model.HotelOrderRowDetailResponse;

/**
 * @author info : Manish Samrat
 * @createdAt : 05/06/2017
 * @version : 1.0
 */

@Service("hotelOrderService")
public class HotelOrderService {

	@Autowired
	CompanyDao companyDAO;

	@Autowired
	HotelOrderDao hotelOrderDao;

	Logger logger = Logger.getLogger(HotelOrderService.class);

	public Company getCompanyRoleTypeByCompanyId(Integer companyId, Company company) {
		company = companyDAO.getCompanyRoleByCompanyId(companyId);
		return company;
	}

	public List<HotelOrderRow> getFlightOrderRowList(Company company, List<HotelOrderRow> orderRowlist,
			Integer companyId, Integer pageNo, Integer pageSize) {

		List<Integer> companyIdList = new ArrayList<Integer>();
		List<String> newIdList = new ArrayList<String>();
		if (company.getCompanyRole().isAgent() || company.getCompanyRole().isCorporate()
				|| company.getCompanyRole().isDistributor()) {

			companyIdList = companyDAO.getCompanyChildIds(company.getCompany_userid(), "parentCompanyUserid");

		} else {
			companyIdList = companyDAO.getCompanyChildIds(company.getCompany_userid(), "superCompanyUserid");
		}
		companyIdList.add(companyId);
		for (Integer myInt : companyIdList) {
			newIdList.add(String.valueOf(myInt));
		}
		orderRowlist = hotelOrderDao.getHotelBookingList(newIdList, pageNo, pageSize);

		return orderRowlist;

	}

	public List<HotelOrderListResponse> getHotelOrderListResponse(List<HotelOrderRow> orderRowlist,
			List<HotelOrderListResponse> listResponse) {
		HotelOrderListResponse orderListResponse = null;
		for (HotelOrderRow orderRow : orderRowlist) {
			orderListResponse = new HotelOrderListResponse();
			orderListResponse.setId(orderRow.getId());
			orderListResponse.setBookingDate(orderRow.getBookingDate());
			orderListResponse.setCurrency(orderRow.getBooking_currency());
			orderListResponse.setOrderId(orderRow.getOrderReference());
			orderListResponse.setTotalBookingAmount(orderRow.getFinalPrice());
			orderListResponse.setStatusAction(orderRow.getStatusAction());
			orderListResponse.setPaymentStatus(orderRow.getPaymentStatus());
			orderListResponse.setLocation(orderRow.getHotelOrderHotelData().getRegionName());
			orderListResponse.setHotelName(orderRow.getHotelOrderHotelData().getName());
			listResponse.add(orderListResponse);
		}
		return listResponse;
	}

	public Map<Object, Object> getHotelOrderDetailsByHotelId(Map<Object, Object> responseMap, Long hotelId) {
		HotelOrderRowDetailResponse detailResponse = null;
		HotelOrderRow hotelOrderRow = hotelOrderDao.getHotelBookingDetail(hotelId);
		if (hotelOrderRow != null && hotelOrderRow.getId() > 0) {
			detailResponse = new HotelOrderRowDetailResponse(hotelOrderRow);
			responseMap.put("status", new Status(1, "Success"));
			responseMap.put("response", detailResponse);
		}
		return responseMap;
	}
}
