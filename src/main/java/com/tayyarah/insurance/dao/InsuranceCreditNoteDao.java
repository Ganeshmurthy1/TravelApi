package com.tayyarah.insurance.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.company.entity.Company;
import com.tayyarah.insurance.entity.InsuranceCreditNote;
import com.tayyarah.user.entity.User;

public interface InsuranceCreditNoteDao {
	public List<InsuranceCreditNote>  getCreditNoteByUser(int id) throws HibernateException;
	public InsuranceCreditNote  getCreditNoteById(String id) throws HibernateException;
	public InsuranceCreditNote  getCreditNoteByRowId(String id) throws HibernateException;
	public List<InsuranceCreditNote> getCreditNoteListByOrderRowID(Long id);
	public List<User> getAgentAddress(String userId);
	public List<Integer> getParentUserIdLevel2(Integer valueOf) throws Exception;
	public User getCompanyIdObjByPassingUserId(int superUserId);
	public InsuranceCreditNote getCreditNoteDetailsByComapnyId(int companyid,
			Long id);
	public Company findParentCompany(Company loginCompanyObj);
	public Company getCompanyAddress(String valueOf);
}
