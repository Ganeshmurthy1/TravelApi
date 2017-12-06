package com.tayyarah.flight.commission.dao;

import java.util.List;
import org.hibernate.HibernateException;

import com.tayyarah.flight.commission.entity.AirlineCommissionMasterSheet;
import com.tayyarah.flight.commission.entity.AirlineCommissionSheet;
import com.tayyarah.flight.commission.model.CommissionActionStatus;


public interface AirlineCommissionSheetDao {
	public AirlineCommissionMasterSheet saveSuperUserSheetDetails(AirlineCommissionMasterSheet sheet); 
	public List<AirlineCommissionMasterSheet> getSuperUserSheetList();
	public List<AirlineCommissionSheet> getAirlineCommissionSheet(Long sheetId) throws HibernateException;
	public CommissionActionStatus duplicateMasterSheetItems(Long sheetIdBackUp, int superUserCompanyId, AirlineCommissionMasterSheet superUserDealSheet)
					throws HibernateException;
	public AirlineCommissionSheet getAirlineCommissionSheetCommons(
			Long sheetId) throws HibernateException;
	public AirlineCommissionSheet getAirlineCommissionSheetRemark(
			Long sheetId, String iataCode, boolean isPlbRemark) throws HibernateException;
	public AirlineCommissionSheet getAirlineCommissionSheetComplete(
			Long sheetId, String iataCode) throws HibernateException;
	public List<AirlineCommissionSheet> updateAirlineCommissionSheet(List<AirlineCommissionSheet> airlineCommissionSheetRows) throws HibernateException;
	List<AirlineCommissionSheet> updateAirlineCommissionSheetDates(String dtValidFromStr, String dtValidTillStr,Long sheetId) throws HibernateException;	

	public AirlineCommissionMasterSheet getSuperUserAirlineCommissionSheet(Long sheetId);

	public List<AirlineCommissionSheet> getAirlineCommissionSheetRows(
			Long sheetId) throws HibernateException ;
	public AirlineCommissionMasterSheet getDealSheetDetails(Long sheetId);
	public AirlineCommissionMasterSheet updateDealSheet(AirlineCommissionMasterSheet commissionSheet);
}
