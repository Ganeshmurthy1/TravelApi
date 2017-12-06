package com.tayyarah.misellaneous.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.company.entity.Company;
import com.tayyarah.misellaneous.entity.MiscellaneousCreditNote;
import com.tayyarah.user.entity.User;

public interface MiscellaneousCreditNoteDao {
	public List<MiscellaneousCreditNote>  getCreditNoteByUser(int id) throws HibernateException;
	public MiscellaneousCreditNote  getCreditNoteById(String id) throws HibernateException;
	public MiscellaneousCreditNote  getCreditNoteByRowId(String id) throws HibernateException;
	public List<MiscellaneousCreditNote> getCreditNoteListByOrderRowID(Long id);
	public List<User> getAgentAddress(String userId);
	public List<Integer> getParentUserIdLevel2(Integer valueOf) throws Exception;
	public User getCompanyIdObjByPassingUserId(int superUserId);
	public MiscellaneousCreditNote getCreditNoteDetailsByComapnyId(int companyid,
			Long id);
	public Company findParentCompany(Company loginCompanyObj);
	public Company getCompanyAddress(String valueOf);
}
