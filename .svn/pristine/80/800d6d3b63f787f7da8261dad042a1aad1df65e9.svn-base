package com.tayyarah.bus.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.bus.entity.BusCreditNote;
import com.tayyarah.company.entity.Company;
import com.tayyarah.user.entity.User;

public interface BusCreditNoteDao {
	public List<BusCreditNote>  getCreditNoteByUser(int id) throws HibernateException;
	public BusCreditNote  getCreditNoteById(String id) throws HibernateException;
	public BusCreditNote  getCreditNoteByRowId(String id) throws HibernateException;
	public List<BusCreditNote> getCreditNoteListByOrderRowID(Long id);
	public List<User> getAgentAddress(String userId);
	public List<Integer> getParentUserIdLevel2(Integer valueOf) throws Exception;
	public User getCompanyIdObjByPassingUserId(int superUserId);
	public BusCreditNote getCreditNoteDetailsByComapnyId(int companyid,Long id);
	public Company findParentCompany(Company loginCompanyObj);
	public Company getCompanyAddress(String valueOf);

}
