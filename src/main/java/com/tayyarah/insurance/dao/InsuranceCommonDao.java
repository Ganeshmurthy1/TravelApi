package com.tayyarah.insurance.dao;

import com.tayyarah.common.entity.FlightAndHotelBookApiResponse;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.insurance.entity.InsuranceOrderCustomerDetail;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.insurance.entity.InsurancePolicyTemp;
import com.tayyarah.insurance.entity.TrawellTagPremiumChart;

public interface InsuranceCommonDao {
	public String getDecryptedAppKey(CompanyDao CDAO,String app_key);
	public TrawellTagPremiumChart getTrawellTagPremiumChart(String daylimit,String agelimit,int planId);
	public long getLastInsurancePolicyTempId();
	public void saveorupdateInsuranceSearchTemp(InsurancePolicyTemp insurancePolicyTemp);
	public InsuranceOrderRow insertInsuranceOrderRowDetails(InsuranceOrderRow insuranceOrderRow);
	public void updateInsuranceOrderRowDetails(InsuranceOrderRow insuranceOrderRow);
	public void insertPaymentTransactionDetails(PaymentTransaction foc);
	public void updatePaymentTransactionDetails(PaymentTransaction paymentTransaction);
	public void insertInsuranceOrderCustomerDetails(InsuranceOrderCustomerDetail insuranceOrderCustomerDetail);
	public void SaveApiBookingStatus(FlightAndHotelBookApiResponse flightAndHotelBookApiResponse);
	public long getInsuranceOrderRowIdByOrderId(String orderId);
}
