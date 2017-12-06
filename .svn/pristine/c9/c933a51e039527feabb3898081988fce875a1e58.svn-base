package com.tayyarah.car.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.car.entity.CarCreditNote;
import com.tayyarah.company.entity.Company;
import com.tayyarah.user.entity.User;

public interface CarCreditNoteDao {
	public List<CarCreditNote>  getCreditNoteByUser(int id) throws HibernateException;
	public CarCreditNote  getCreditNoteById(String id) throws HibernateException;
	public CarCreditNote  getCreditNoteByRowId(String id) throws HibernateException;
	public List<CarCreditNote> getCreditNoteListByOrderRowID(Long id);
	public List<User> getAgentAddress(String userId);
	public List<Integer> getParentUserIdLevel2(Integer valueOf) throws Exception;
	public User getCompanyIdObjByPassingUserId(int superUserId);
	public CarCreditNote getCreditNoteDetailsByComapnyId(int companyid,
			Long id);
	public Company findParentCompany(Company loginCompanyObj);
	public Company getCompanyAddress(String valueOf);

}
