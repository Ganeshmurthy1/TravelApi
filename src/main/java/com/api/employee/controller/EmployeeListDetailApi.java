package com.api.employee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.bulk.emp.dao.BulkEmpDao;
import com.api.bulk.emp.response.BulkEmpResponse;
import com.api.bulk.emp.response.BulkEmployeeRmFieldsResponse;
import com.api.bulk.emp.vo.BulkEmpDetails;
import com.api.bulk.emp.vo.RmManualFields;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.RmConfigModel;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.user.entity.User;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeListDetailApi {
	/**
	 * @author      : Shaik Basha
	 * @createdAt   : 07-20-2017
	 * @version
	 */
	@Autowired
	BulkEmpDao bulkEmpdao;
	
	
	@Autowired
	CompanyDao companyDAO;	
	

	@Autowired
	RmConfigDetailDAO rmConfigDetailDAO;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody  List<BulkEmpResponse> fetchAllEmpListUnderCorp(@RequestParam(value = "companyid") String companyid, HttpServletRequest request, HttpServletResponse response) throws  Exception {
		List<BulkEmpResponse> searchCacheDestinationList=new ArrayList<BulkEmpResponse>();
		List<User> userUnderCorporate=new ArrayList<>();
		try {
			if(companyid!=null & !companyid.trim().equalsIgnoreCase("")){
				int companyId=Integer.parseInt(companyid);
				userUnderCorporate=companyDAO.getUserListUnderCompany(companyId);
				List<User> listOfEmployees=new ArrayList<>();
				if(userUnderCorporate!=null && userUnderCorporate.size()>0){
				for(User userList:userUnderCorporate){
					if(userList.getUserrole_id().isCorporateemployee())
						listOfEmployees.add(userList);
				}
				}
				if(listOfEmployees!=null && listOfEmployees.size()>0){
					for(User userListnew:listOfEmployees){
						BulkEmpResponse autocompleterObject=new BulkEmpResponse();
						autocompleterObject.setId(Integer.toString(userListnew.getId()));
						autocompleterObject.setFirstName(userListnew.getFirstname());
						autocompleterObject.setLastName(userListnew.getLastname());
						autocompleterObject.setEmail(userListnew.getEmail());
						autocompleterObject.setEmpCode(userListnew.getCompany_userid());
						searchCacheDestinationList.add(autocompleterObject);
				}
				}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchCacheDestinationList;
	}
	
	@RequestMapping(value = "/empdetailsById", method = RequestMethod.GET, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody BulkEmployeeRmFieldsResponse fetchEmpDetailsById(
			@RequestParam(value = "id") String id, HttpServletRequest request, HttpServletResponse response) throws  Exception {
		BulkEmployeeRmFieldsResponse employeeRmFieldsJsonVo=new BulkEmployeeRmFieldsResponse();
		BulkEmpDetails rmDataListDetails=new BulkEmpDetails();
		List<String> fieldNameArray = new ArrayList<String>();
		List<RmManualFields> rmList = new ArrayList<>();
		RmManualFields manualFields=null;
		try{
			if(id!=null && !id.trim().equalsIgnoreCase("")){
				
				User user=companyDAO.getUserById(Integer.parseInt(id));
				RmConfigModel rmConfigModel=rmConfigDetailDAO.getRmConfigModel(user.getCompanyid());
				if(rmConfigModel!=null && rmConfigModel.getId()>0){
					employeeRmFieldsJsonVo.setRmDetailsSet(true);
					rmDataListDetails.setId(id);
					if(rmConfigModel.isApproverName())
						rmDataListDetails.setApproverName("");
					if(rmConfigModel.isBillNonBill())
						rmDataListDetails.setBillNonBill("");
					if(rmConfigModel.isBussinessUnit())
						rmDataListDetails.setBussinessUnit("");
					if(rmConfigModel.isCostCenter())
						rmDataListDetails.setCostCenter("");
					if(rmConfigModel.isDepartment())
						rmDataListDetails.setDepartment("");
					if(rmConfigModel.isEmpCode())
						rmDataListDetails.setEmpCode("");
					if(rmConfigModel.isLocation())
						rmDataListDetails.setLocation("");
					if(rmConfigModel.isProjectCode())
						rmDataListDetails.setProjectCode("");
					if(rmConfigModel.isReasonForTravel())
						rmDataListDetails.setReasonForTravel("");
					if(rmConfigModel.isTrNumber())
						rmDataListDetails.setTrNumber("");
					
					String manualStringFields[] = rmConfigModel.getDynamicFieldsData()!=null && !rmConfigModel.getDynamicFieldsData().trim().equalsIgnoreCase("") ?rmConfigModel.getDynamicFieldsData().split(","):null;
					if(manualStringFields!=null && manualStringFields.length>0){
						for(String oneField:manualStringFields){
							if(!oneField.trim().equalsIgnoreCase("")){
								String fieldsName[]=oneField.split(":");
								fieldNameArray.add(fieldsName[0]);
							}
							
						}
					}
					
					if(fieldNameArray!=null && fieldNameArray.size()>0){
						if(fieldNameArray.get(0)!=null && !fieldNameArray.get(0).trim().equalsIgnoreCase("")){
							manualFields=new RmManualFields();
							manualFields.setManualField(fieldNameArray.get(0));
							manualFields.setType("text");
							rmList.add(manualFields);
						}
						if(fieldNameArray.size()>1 && fieldNameArray.get(1)!=null && !fieldNameArray.get(1).trim().equalsIgnoreCase("")){
							manualFields=new RmManualFields();
							manualFields.setManualField(fieldNameArray.get(1));
							manualFields.setType("text");
							rmList.add(manualFields);
						}
						if(fieldNameArray.size()>2 && fieldNameArray.get(2)!=null && !fieldNameArray.get(2).trim().equalsIgnoreCase("")){
							manualFields=new RmManualFields();
							manualFields.setManualField(fieldNameArray.get(2));
							manualFields.setType("text");
							rmList.add(manualFields);
						}
						if(fieldNameArray.size()>3 && fieldNameArray.get(3)!=null && !fieldNameArray.get(3).trim().equalsIgnoreCase("")){
							manualFields=new RmManualFields();
							manualFields.setManualField(fieldNameArray.get(3));
							manualFields.setType("text");
							rmList.add(manualFields);
						}
						if(fieldNameArray.size()>4 && fieldNameArray.get(4)!=null && !fieldNameArray.get(4).trim().equalsIgnoreCase("")){
							manualFields=new RmManualFields();
							manualFields.setManualField(fieldNameArray.get(4));
							manualFields.setType("text");
							rmList.add(manualFields);
						}
					}
					rmDataListDetails.setManualFields(rmList);
					
					employeeRmFieldsJsonVo.setRmDataListDetails(rmDataListDetails);
					employeeRmFieldsJsonVo.setMessage("Success");
					
				}else{
					employeeRmFieldsJsonVo.setMessage("This User's Company  Does't have any Rm config");
				}
				
				
			}else{
				employeeRmFieldsJsonVo.setMessage("id is empty please send an id");
			}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return employeeRmFieldsJsonVo;
	}
}
