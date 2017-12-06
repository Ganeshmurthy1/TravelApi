package com.tayyarah.flight.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.company.entity.Company;
import com.tayyarah.flight.entity.FlightCreditNote;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.user.entity.User;

public interface FlightCreditNoteDao {
	public List<User>  getTicketCancelByUser(FlightOrderRow userid) throws HibernateException;
	public List<FlightCreditNote>  getCreditNoteByUser(int id) throws HibernateException;
	public FlightCreditNote  getCreditNoteById(String id) throws HibernateException;
	public FlightCreditNote  getCreditNoteByRowId(String id) throws HibernateException;
	public List<FlightCreditNote> getCreditNoteListByOrderRowID(Long id);
	public List<User> getAgentAddress(String userId);
	public List<Integer> getParentUserIdLevel2(Integer valueOf) throws Exception;
	public User getCompanyIdObjByPassingUserId(int superUserId);
	public FlightCreditNote getCreditNoteDetailsByComapnyId(int companyid,
			Long id);
	public Company findParentCompany(Company loginCompanyObj);
	public Company getCompanyAddress(String valueOf);
}
