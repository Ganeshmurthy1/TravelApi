package com.tayyarah.flight.service.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tayyarah.common.util.Status;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.flight.dao.FlightOrderDao;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.flight.model.FlightOrderListResponse;
import com.tayyarah.flight.model.FlightOrderRowDetailResponse;


/**
 * @author info : Manish Samrat
 * @createdAt : 05/06/2017
 * @version : 1.0
 */

@Component("flightOrderService")
public class FlightOrderService {

	@Autowired
	CompanyDao CmpDao;
	@Autowired
	FlightOrderDao flightOrderDAO;
	
	Logger logger = Logger.getLogger(FlightOrderService.class);
	
	public Company getCompanyRoleTypeByCompanyId(Integer companyId,Company company){		
		company = CmpDao.getCompanyRoleByCompanyId(companyId);
		return company;
	}
	
	public List<FlightOrderRow> getFlightOrderRowList(Company company, 
			List<FlightOrderRow> orderRowlist, Integer companyId, Integer pageNo, Integer pageSize) {

		List<Integer> companyIdList = new ArrayList<Integer>();
		List<String> newIdList = new ArrayList<String>();
		if (company.getCompanyRole().isAgent() || company.getCompanyRole().isCorporate()
				|| company.getCompanyRole().isDistributor()) {

			companyIdList = CmpDao.getCompanyChildIds(company.getCompany_userid(), "parentCompanyUserid");

		} else {
			companyIdList = CmpDao.getCompanyChildIds(company.getCompany_userid(), "superCompanyUserid");
		}
		companyIdList.add(companyId);
		for (Integer myInt : companyIdList) {
			newIdList.add(String.valueOf(myInt));
		}
		orderRowlist = flightOrderDAO.getFlightBookingList(newIdList, pageNo, pageSize);
		return orderRowlist;
	}

	public List<FlightOrderListResponse> getFlightOrderListResponse(List<FlightOrderRow> orderRowlist,
			List<FlightOrderListResponse> listResponse) {
		FlightOrderListResponse orderListResponse = null;
		for (FlightOrderRow orderRow : orderRowlist) {
			orderListResponse = new FlightOrderListResponse();
			if (orderRow.getFlightOrderTripDetails().size() > 0) {
				String travelTime = "";
				String travelDate = "";
				for (FlightOrderTripDetail tripDetail : orderRow.getFlightOrderTripDetails()) {
					travelTime = tripDetail.getOriginName() + "-" + tripDetail.getDestinationName() + " "
							+ tripDetail.getDepartureTime() + "-" + tripDetail.getArrivalTime() + ", ";
					travelDate = tripDetail.getDepartureDate() + " : " + tripDetail.getArrivalDate() + ", ";
				}
				orderListResponse.setTravelPlace(travelTime);
				orderListResponse.setTravelDate(travelDate);
			}

			orderListResponse.setId(orderRow.getId());
			orderListResponse.setStatusAction(orderRow.getStatusAction());
			orderListResponse.setPaymentStatus(orderRow.getPaymentStatus());

			orderListResponse.setCurrency(orderRow.getBooking_currency());
			orderListResponse.setOrderId(orderRow.getOrderId());
			orderListResponse.setPnr(orderRow.getPnr());
			orderListResponse.setTotalBookingAmount(orderRow.getFinalPrice());
			orderListResponse.setBookingDate(orderRow.getBookingDate());

			listResponse.add(orderListResponse);
		}
		return listResponse;
	}
	public Map<Object, Object> getFlightOrderDetailsByFlightId(Map<Object, Object> responseMap,
			Long flightId) {		
		FlightOrderRowDetailResponse rowDetailResponse = null;
		FlightOrderRow orderRow = flightOrderDAO.getFlightBookingDetail(flightId);
		if (orderRow != null && orderRow.getId() > 0) {
			rowDetailResponse = new FlightOrderRowDetailResponse(orderRow);
			responseMap.put("status", new Status(1, "Success"));
			responseMap.put("response", rowDetailResponse);
			return responseMap;
		}
		return responseMap;
	}
}
