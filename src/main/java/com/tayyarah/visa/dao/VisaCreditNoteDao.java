package com.tayyarah.visa.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.company.entity.Company;
import com.tayyarah.user.entity.User;
import com.tayyarah.visa.entity.VisaCreditNote;

public interface VisaCreditNoteDao {
	public List<VisaCreditNote>  getCreditNoteByUser(int id) throws HibernateException;
	public VisaCreditNote  getCreditNoteById(String id) throws HibernateException;
	public VisaCreditNote  getCreditNoteByRowId(String id) throws HibernateException;
	public List<VisaCreditNote> getCreditNoteListByOrderRowID(Long id);
	public List<User> getAgentAddress(String userId);
	public List<Integer> getParentUserIdLevel2(Integer valueOf) throws Exception;
	public User getCompanyIdObjByPassingUserId(int superUserId);
	public VisaCreditNote getCreditNoteDetailsByComapnyId(int companyid,
			Long id);
	public Company findParentCompany(Company loginCompanyObj);

	public Company getCompanyAddress(String valueOf);

}
