package com.tayyarah.hotel.dao;

import java.util.List;
import org.hibernate.HibernateException;

import com.tayyarah.company.entity.Company;
import com.tayyarah.hotel.entity.HotelCreditNote;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.user.entity.User;

public interface HotelCreditNoteDao {
	public HotelCreditNote  getHotelCreditNoteByRowId(String id) throws HibernateException;
	public List<HotelCreditNote> getCreditNoteListByOrderRowID(Long id);
	public List<HotelOrderGuest> getHotelGuestInfoByRoomId(
			List<HotelOrderRoomInfo> hotelOrderRoomInfos);
	public List<User> getAgentAddress(String userId);
	public List<Integer> getParentUserIdLevel2(Integer valueOf) throws Exception;
	public User getCompanyIdObjByPassingUserId(int superUserId);
	public HotelCreditNote getCreditNoteDetailsByComapnyId(int companyid,
			Long id);
	public Company findParentCompany(Company loginCompanyObj);
	public Company getCompanyAddress(String valueOf);
}
