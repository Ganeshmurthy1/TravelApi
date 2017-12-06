package com.tayyarah.email.dao;
import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.common.entity.Enquiry;
import com.tayyarah.common.entity.StateInfo;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.company.entity.CompanyEntity;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.umrah.entity.TayyarahUmrahContactDetails;
import com.tayyarah.user.entity.FrontUserDetail;
import com.tayyarah.user.entity.User;


public interface AllEmailDao {
	public Company getCompanyByUserId(String userId) throws HibernateException;	
	public User getUserByUserId(String userId) throws HibernateException;	
	public FrontUserDetail getFrontUserDetailByUserId(String userId) throws HibernateException;	
	public CompanyConfig getCompanyConfigByUserId(String userId) throws HibernateException;
	public Company getCompanyByCompanyId(String companyId) throws HibernateException ;
	public CompanyEntity getCompanyEntityByCompanyId(Long companyEntityId) throws HibernateException ;
	public List<User>  getPostPaidWalletUser() throws HibernateException;
	public  List<FlightOrderRow> getLastBookingDateForFlight(int userId ) throws HibernateException;
	public  List<FlightOrderRow> getLastBookingDateForHoldFlightTickets() throws HibernateException;
	public  List<FlightOrderRow> getExpiredHoldFlightTickets() throws HibernateException;
	public  List<HotelOrderRow> getLastBookingDateForHotel(int userId ) throws HibernateException;
	public List<User> getBlockedUserByUserId(String userId) throws HibernateException;	
	public List<CompanyConfig>  getWhiteLabelUserByConfigId(int companyId) throws HibernateException;
	public List<Company>  getWhiteLabelCompanyById(CompanyConfig  configid) throws HibernateException;
	public Company getCompanyResetPassword(String userId) throws HibernateException;	
	public User getUserResetPassword(String userId) throws HibernateException;
	public Enquiry getEnquiry(String orderId) throws HibernateException;
	public User getUserByEmail(String email) throws HibernateException;
	public List<Company> getAllCorporates() throws Exception;
	public List<User> getAllUserList()throws HibernateException;
	public List<FlightOrderRow> getFlightDepartureLastDay() throws HibernateException;
	public List<FlightOrderRow> getFlightMailBeforeOneDay() throws HibernateException;
	public Company getCompanyByCompanyId(int companyId) throws HibernateException;
	public  StateInfo getStateInfo(String statename)throws HibernateException;
	public Company getSuperCompanyByCompanyUserId(String superCompanyUserId) throws HibernateException; 
	public Email check24HoursEmailIsExists(String orderid)throws HibernateException; 
	public Email check4HoursEmailIsExists(String orderid)throws HibernateException;
	public TayyarahUmrahContactDetails  getTayyarahUmrahEnquiryDetails(String orderId)throws HibernateException;; 
}
