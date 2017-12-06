package com.tayyarah.company.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusMarkupCommissionDetails;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.company.entity.CompanyEntity;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.hotel.model.HotelMarkupCommissionDetails;
import com.tayyarah.insurance.model.InsuranceMarkUp;
import com.tayyarah.insurance.model.InsuranceMarkupCommissionDetails;
import com.tayyarah.user.entity.FrontUserDetail;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.model.UserAuthenticationRequest;


public interface CompanyDao {	
	
	public List<Company> getCompanyBycompanyid(int companyid) throws Exception;
	public Company getCompany(int companyid);
	public String getAppKey(String email , String password) throws Exception;
	public boolean checkAppkey(AppKeyVo appKeyVo) throws HibernateException, NumberFormatException,Exception;
	public List<FlightMarkUpConfig> getFlightMarkUpConfig(String app_key) throws Exception;	
	public HotelMarkupCommissionDetails getHotelMarkupCommissionDetails(AppKeyVo appKeyVo) throws Exception;		
	public Map<String,List<FlightMarkUpConfig>> getFlightMarkUpConfigMapByCompanyId(AppKeyVo appKeyVo, Map<String,List<FlightMarkUpConfig>>  markupMap)throws SQLException, Exception ;
	public Map<String,List<FlightMarkUpConfig>> getParentMarkupMap(CompanyConfig companyConfig, CompanyConfig parentCompConfig, Map<String,List<FlightMarkUpConfig>>  markupMap)throws SQLException, Exception;	
	public MarkupCommissionDetails getFlightMarkupCommissionDetailsByCompanyId(AppKeyVo appKeyVo, MarkupCommissionDetails  markupCommissionDetails)throws SQLException, Exception;	
	public boolean checkAppkey(String app_key) throws HibernateException, NumberFormatException,Exception;
	public String getCompanyName(String id) throws Exception;
	public String getCompanyCurrencyCode(int companyId) throws Exception;
	public String getBillingCoyuntry()throws Exception;
	public List<User> getParentCompanyList(List<Integer> userids)throws Exception;
	public int getParentUserIdLevel1(Integer integer) throws Exception;
	public List<Integer> getParentUserIdLevel2(Integer integer) throws Exception;
	public User getUserByCompanyId(int companyid) throws Exception;
	public CompanyConfig getcompanyconfig(String app_key) throws Exception;
	public boolean insertRMConfigTripDetails(RmConfigTripDetailsModel rmConfigTripDetailsModel)throws Exception;
	public Company getParentCompany(Company company);
	public User getUserByCompanyEmail(String email) throws Exception;
	public User getUserById(int userid);
	public Map<String,List<BusMarkUpConfig>> getBusMarkUpMapByCompanyId(String companyid, String configId, Map<String,List<BusMarkUpConfig>>  markupMap)throws SQLException, Exception ;
	public BusMarkupCommissionDetails getBusMarkupCommissionDetailsByCompanyId(String companyid, String configId, BusMarkupCommissionDetails  markupCommissionDetails)throws SQLException, Exception;
	public User getAuthentherisedUser(UserAuthenticationRequest userAuthReq);
	public String getAppKeyByCompanyId(int companyId);
	public FrontUserDetail getAuthorisedVisitor(UserAuthenticationRequest userAuthReq);
	public FrontUserDetail  insertVisiterInfo(FrontUserDetail userDetail);
	public boolean  verifyingEmailExistence(String email);
	public FrontUserDetail  fetchDirectUserByid(long id);
	public List <Integer>  getCompanyChildIds(String companyUserId,String type);
	public CompanyConfig getCompanyConfigUsingId(int config_id);
	public CompanyEntity getCompanyEntityUsingId(int id) ;
	public Map<String,List<InsuranceMarkUp>> getInsuranceMarkUpMapByCompanyId(AppKeyVo appKeyVo, Map<String,List<InsuranceMarkUp>>  markupMap);
	public Map<String,List<InsuranceMarkUp>> getParentMarkupMapForInsurance(CompanyConfig companyConfig, CompanyConfig parentCompConfig, Map<String,List<InsuranceMarkUp>>  markupMap);
	public InsuranceMarkupCommissionDetails getInsuranceMarkupCommissionDetailsByCompanyId(AppKeyVo appKeyVo, InsuranceMarkupCommissionDetails  markupCommissionDetails);
	public Company getCompanyRoleByCompanyId(Integer companyId);
	public List<User> getUserListUnderCompany(int companyId);
}
