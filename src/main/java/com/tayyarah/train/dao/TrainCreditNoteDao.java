package com.tayyarah.train.dao;

import java.util.List;
import org.hibernate.HibernateException;

import com.tayyarah.company.entity.Company;
import com.tayyarah.train.entity.TrainCreditNote;
import com.tayyarah.user.entity.User;

public interface TrainCreditNoteDao {
	public List<TrainCreditNote>  getCreditNoteByUser(int id) throws HibernateException;
	public TrainCreditNote  getCreditNoteById(String id) throws HibernateException;
	public TrainCreditNote  getCreditNoteByRowId(String id) throws HibernateException;
	public List<TrainCreditNote> getCreditNoteListByOrderRowID(Long id);
	public List<User> getAgentAddress(String userId);
	public List<Integer> getParentUserIdLevel2(Integer valueOf) throws Exception;
	public User getCompanyIdObjByPassingUserId(int superUserId);
	public TrainCreditNote getCreditNoteDetailsByComapnyId(int companyid,
			Long id);
	public Company findParentCompany(Company loginCompanyObj);
	public Company getCompanyAddress(String valueOf);
}
