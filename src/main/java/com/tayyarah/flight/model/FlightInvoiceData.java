package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.util.GstPropertiesFile;
import com.tayyarah.email.dao.FlightOrderRowEmailDao;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderCustomerSSR;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;


@Component
@Scope("session")
public class FlightInvoiceData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger=Logger.getLogger(FlightInvoiceData.class);

	@Autowired
	FlightOrderRowEmailDao flightOrderRowEmailDao;

	private String eticketnumber;	
	private String agentName;
	private String firstName;
	private String lastName;
	private String title;
	private String passengerType;
	private BigDecimal totalMYamount;
	private BigDecimal MYamount;
	private String cusAddress;
	private String invNo;
	private String ActCode;
	private String consultant;
	private String bookNo;
	private String yourRef;
	private Date date;
	private String fax;
	private String tel;
	private String mobile;
	private String attn;
	private String gstType;
	private List<String> particulars;
	private String gender;
	private int qty;
	private BigDecimal price;
	private BigDecimal totPrice;
	private String currency;
	private String GstSummary;
	private BigDecimal tax;
	private BigDecimal totalTax;
	private BigDecimal totBeforeGst;
	private BigDecimal totGst;
	private BigDecimal totWithGst;
	private BigDecimal totAgentComm;
	private BigDecimal totAmountSpent;
	private BigDecimal totAmount;
	private BigDecimal eachOrderTotComm;
	private BigDecimal orderFinalPrice;
	private BigDecimal totMarkup;
	private String userId;
	private String paymentStatus;
	private String status;
	private String bookedDate;
	private Long orderRowId;
	private String companyId;
	private String orderId;
	private boolean isLintasGstOn;
	private BigDecimal total_tax;
	private BigDecimal Base_prise;
	private String middleName;
	private List<FlightOrderTripDetail>  tripParticulars;
	private List<FlightOrderCustomerPriceBreakup>  priceBreakupParticulars;
	private List<FlightOrderCustomer> orderCustomerParticulars;
	private List<FlightInvoiceData> orderCustomerInvoiceList;
	private List<FlightOrderRow>  flightOrderRowList;
	private List<PaymentTransaction>  txDetails;
	private String mealName;
	private String returnMealName;
	private boolean isMealAvailable;
	private boolean isReturnMealAvailable;	
	private int baggageweight;
	private int returnbaggageweight;
	private BigDecimal extramealprice;
	private BigDecimal extrabaggageprice;
	private  RmConfigTripDetailsModel rmdetail;
	private GstPropertiesFile gstPropertiesFile = new GstPropertiesFile();
	private List<WalletAmountTranferHistory> agentWalletTxDetails;
	private List<User>  userDetails;

	public int getReturnbaggageweight() {
		return returnbaggageweight;
	}
	public void setReturnbaggageweight(int returnbaggageweight) {
		this.returnbaggageweight = returnbaggageweight;
	}
	public int getBaggageweight() {
		return baggageweight;
	}
	public void setBaggageweight(int baggageweight) {
		this.baggageweight = baggageweight;
	}
	public boolean isMealAvailable() {
		return isMealAvailable;
	}
	public boolean isReturnMealAvailable() {
		return isReturnMealAvailable;
	}
	public void setMealAvailable(boolean isMealAvailable) {
		this.isMealAvailable = isMealAvailable;
	}
	public void setReturnMealAvailable(boolean isReturnMealAvailable) {
		this.isReturnMealAvailable = isReturnMealAvailable;
	}
	public String getMealName() {
		return mealName;
	}
	public String getReturnMealName() {
		return returnMealName;
	}
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}
	public void setReturnMealName(String returnMealName) {
		this.returnMealName = returnMealName;
	}
	public BigDecimal getExtramealprice() {
		return extramealprice;
	}
	public BigDecimal getExtrabaggageprice() {
		return extrabaggageprice;
	}
	public void setExtramealprice(BigDecimal extramealprice) {
		this.extramealprice = extramealprice;
	}
	public void setExtrabaggageprice(BigDecimal extrabaggageprice) {
		this.extrabaggageprice = extrabaggageprice;
	}
	public String getEticketnumber() {
		return eticketnumber;
	}
	public void setEticketnumber(String eticketnumber) {
		this.eticketnumber = eticketnumber;
	}
	public String getPassengerType() {
		return passengerType;
	}
	public BigDecimal getTotal_tax() {
		return total_tax;
	}
	public void setTotal_tax(BigDecimal total_tax) {
		this.total_tax = total_tax;
	}
	public BigDecimal getBase_prise() {
		return Base_prise;
	}
	public void setBase_prise(BigDecimal base_prise) {
		Base_prise = base_prise;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public BigDecimal getTotalMYamount() {
		return totalMYamount;
	}
	public void setTotalMYamount(BigDecimal totalMYamount) {
		this.totalMYamount = totalMYamount;
	}
	public BigDecimal getMYamount() {
		return MYamount;
	}
	public void setMYamount(BigDecimal mYamount) {
		MYamount = mYamount;
	}
	public BigDecimal getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}
	public boolean isLintasGstOn() {
		return isLintasGstOn;
	}
	public void setLintasGstOn(boolean isLintasGstOn) {
		this.isLintasGstOn = isLintasGstOn;
	}
	public List<FlightInvoiceData> getOrderCustomerInvoiceList() {
		return orderCustomerInvoiceList;
	}
	public void setOrderCustomerInvoiceList(
			List<FlightInvoiceData> orderCustomerInvoiceList) {
		this.orderCustomerInvoiceList = orderCustomerInvoiceList;
	}
	public List<FlightOrderRow> getFlightOrderRowList() {
		return flightOrderRowList;
	}
	public void setFlightOrderRowList(List<FlightOrderRow> flightOrderRowList) {
		this.flightOrderRowList = flightOrderRowList;
	}
	public List<PaymentTransaction> getTxDetails() {
		return txDetails;
	}
	public void setTxDetails(List<PaymentTransaction> txDetails) {
		this.txDetails = txDetails;
	}
	public List<WalletAmountTranferHistory> getAgentWalletTxDetails() {
		return agentWalletTxDetails;
	}
	public void setAgentWalletTxDetails(
			List<WalletAmountTranferHistory> agentWalletTxDetails) {
		this.agentWalletTxDetails = agentWalletTxDetails;
	}
	public List<User> getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(List<User> userDetails) {
		this.userDetails = userDetails;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCusAddress() {
		return cusAddress;
	}
	public void setCusAddress(String cusAddress) {
		this.cusAddress = cusAddress;
	}
	public String getInvNo() {
		return invNo;
	}
	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}
	public String getActCode() {
		return ActCode;
	}
	public void setActCode(String actCode) {
		ActCode = actCode;
	}
	public String getConsultant() {
		return consultant;
	}
	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	public String getYourRef() {
		return yourRef;
	}
	public void setYourRef(String yourRef) {
		this.yourRef = yourRef;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAttn() {
		return attn;
	}
	public void setAttn(String attn) {
		this.attn = attn;
	}
	public String getGstType() {
		return gstType;
	}
	public void setGstType(String gstType) {
		this.gstType = gstType;
	}
	public List<String> getParticulars() {
		return particulars;
	}
	public void setParticulars(List<String> particulars) {
		this.particulars = particulars;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTotPrice() {
		return totPrice;
	}
	public void setTotPrice(BigDecimal totPrice) {
		this.totPrice = totPrice;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getGstSummary() {
		return GstSummary;
	}
	public void setGstSummary(String gstSummary) {
		GstSummary = gstSummary;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getTotBeforeGst() {
		return totBeforeGst;
	}
	public void setTotBeforeGst(BigDecimal totBeforeGst) {
		this.totBeforeGst = totBeforeGst;
	}
	public BigDecimal getTotGst() {
		return totGst;
	}
	public void setTotGst(BigDecimal totGst) {
		this.totGst = totGst;
	}
	public BigDecimal getTotWithGst() {
		return totWithGst;
	}
	public void setTotWithGst(BigDecimal totWithGst) {
		this.totWithGst = totWithGst;
	}
	public BigDecimal getTotAgentComm() {
		return totAgentComm;
	}
	public void setTotAgentComm(BigDecimal totAgentComm) {
		this.totAgentComm = totAgentComm;
	}
	public BigDecimal getTotAmountSpent() {
		return totAmountSpent;
	}
	public void setTotAmountSpent(BigDecimal totAmountSpent) {
		this.totAmountSpent = totAmountSpent;
	}
	public BigDecimal getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(BigDecimal totAmount) {
		this.totAmount = totAmount;
	}
	public BigDecimal getEachOrderTotComm() {
		return eachOrderTotComm;
	}
	public void setEachOrderTotComm(BigDecimal eachOrderTotComm) {
		this.eachOrderTotComm = eachOrderTotComm;
	}
	public BigDecimal getOrderFinalPrice() {
		return orderFinalPrice;
	}
	public void setOrderFinalPrice(BigDecimal orderFinalPrice) {
		this.orderFinalPrice = orderFinalPrice;
	}
	public BigDecimal getTotMarkup() {
		return totMarkup;
	}
	public void setTotMarkup(BigDecimal totMarkup) {
		this.totMarkup = totMarkup;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBookedDate() {
		return bookedDate;
	}
	public void setBookedDate(String bookedDate) {
		this.bookedDate = bookedDate;
	}
	public Long getOrderRowId() {
		return orderRowId;
	}
	public void setOrderRowId(Long orderRowId) {
		this.orderRowId = orderRowId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public List<FlightOrderTripDetail> getTripParticulars() {
		return tripParticulars;
	}
	public void setTripParticulars(List<FlightOrderTripDetail> tripParticulars) {
		this.tripParticulars = tripParticulars;
	}
	public List<FlightOrderCustomerPriceBreakup> getPriceBreakupParticulars() {
		return priceBreakupParticulars;
	}
	public void setPriceBreakupParticulars(List<FlightOrderCustomerPriceBreakup> priceBreakupParticulars) {
		this.priceBreakupParticulars = priceBreakupParticulars;
	}
	public List<FlightOrderCustomer> getOrderCustomerParticulars() {
		return orderCustomerParticulars;
	}
	public void setOrderCustomerParticulars(List<FlightOrderCustomer> orderCustomerParticulars) {
		this.orderCustomerParticulars = orderCustomerParticulars;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public  List<FlightInvoiceData> getFlightOrderCustomerPriceDetails(FlightOrderRow  flightOrderRow  ){
		List<FlightInvoiceData> invoiceDetails = new  ArrayList<FlightInvoiceData>();
		FlightInvoiceData  invObj = new FlightInvoiceData();
		if(gstPropertiesFile.getGstSwitchonValue()){			
			Map<String,Integer> passengerMap= getPassengerMap(flightOrderRow);
			Map<String,BigDecimal> myMap = new HashMap<String, BigDecimal>();
			List<String> passList=new ArrayList<String>();
			String customerNames="";
			FlightInvoiceData newObj=null;
			String D8tax="0.00";
			String Mytax="0.00";
			BigDecimal myTax=new BigDecimal("0.00");
			BigDecimal d8tax=new BigDecimal("0.00");
			List<FlightOrderCustomer> flightOrderCustomers= flightOrderRowEmailDao.customerDeatails(flightOrderRow);
			if(flightOrderCustomers!=null && flightOrderCustomers.size()>0)
			{
				for(int i=0;i<flightOrderCustomers.size();i++){				
					FlightOrderCustomer  customer=flightOrderCustomers.get(i);					
					FlightOrderCustomerPriceBreakup  priceBreakup = flightOrderRowEmailDao.flightPriceBreakup(flightOrderRow).get(i);
					List<FlightOrderCustomerSSR> ssrDetail = flightOrderRowEmailDao.flightCustomerSSR(customer.getFlightCustomer().getId());					
					newObj=new FlightInvoiceData();
					customerNames=customer.getFirstName();
					newObj.setEticketnumber(customer.getEticketnumber());					
					if(ssrDetail.get(i)!=null && ssrDetail.size() > 0){
						if(ssrDetail.get(i).getMealname()!=null && !ssrDetail.get(i).getMealname().equalsIgnoreCase("")){
							newObj.isMealAvailable = true;						
						}
						if(ssrDetail.get(i).getReturnmealname()!=null && !ssrDetail.get(i).getReturnmealname().equalsIgnoreCase("")){
							newObj.isReturnMealAvailable = true;						
						}
						newObj.setMealName(ssrDetail.get(i).getMealname());
						newObj.setReturnMealName(ssrDetail.get(i).getReturnmealname());					

						if(ssrDetail.get(i).getBaggageweight()!=null && !ssrDetail.get(i).getBaggageweight().equalsIgnoreCase(""))
							newObj.setBaggageweight(Integer.parseInt(ssrDetail.get(i).getBaggageweight()));
						else
							newObj.setBaggageweight(0);

						if(ssrDetail.get(i).getReturnbaggageweight()!=null && !ssrDetail.get(i).getReturnbaggageweight().equalsIgnoreCase(""))
							newObj.setReturnbaggageweight(Integer.parseInt(ssrDetail.get(i).getReturnbaggageweight()));
						else
							newObj.setReturnbaggageweight(0);

					}					
					newObj.setFirstName(customerNames);
					newObj.setPassengerType(customer.getPassengerTypeCode());
					newObj.setGender(customer.getGender());	
					BigDecimal basePrice= priceBreakup.getBaseFare().multiply(flightOrderRow.getApiToBaseExchangeRate());
					BigDecimal basePriceInBooking=basePrice.multiply(flightOrderRow.getBaseToBookingExchangeRate());
					BigDecimal totalBasePriceInBooking=basePrice.multiply(flightOrderRow.getBaseToBookingExchangeRate())/*.multiply(new BigDecimal(passengerMap.get(priceBreakup.getDescription())))*/;
					BigDecimal taxes= priceBreakup.getTax().multiply(flightOrderRow.getApiToBaseExchangeRate());
					BigDecimal taxesInBooking=taxes.multiply(flightOrderRow.getBaseToBookingExchangeRate());
					String taxDescription=priceBreakup.getTax_description();/*"D8:6.00;MY:7.00;F2:2.00;IN:49.00;JN:48.00;WO:16.00;YQ:401.00;YR:9.00;"*/;
					String temp=taxDescription;				
					if(temp!=null){
						while(temp.length()>2){
							String fullValue=temp.substring(0,temp.indexOf(";"));
							String taxCode=fullValue.substring(0,fullValue.indexOf(":"));
							temp=temp.substring(fullValue.length()+1);
							if(taxCode.equalsIgnoreCase("D8") ){								
								D8tax=fullValue.substring(fullValue.indexOf(":")+1);
								logger.info("D8tax:"+D8tax);
							}
							if(taxCode.equalsIgnoreCase("MY") ){
								Mytax=fullValue.substring(fullValue.indexOf(":")+1);
							}
						}
					}
					BigDecimal airportTax=new BigDecimal("0.00");
					BigDecimal markup = priceBreakup.getMarkup()!=null && !priceBreakup.getMarkup().equals("") ?new BigDecimal(priceBreakup.getMarkup()):new BigDecimal(0);
					BigDecimal gstOnMarkup =(flightOrderRow.getGst_on_markup()!=null)?flightOrderRow.getGst_on_markup():new BigDecimal(0);
					BigDecimal baseToBookingRate = (flightOrderRow.getBaseToBookingExchangeRate()!=null)?flightOrderRow.getBaseToBookingExchangeRate():new BigDecimal(0);
					airportTax=taxesInBooking;
					if(!D8tax.equals("0.00")){									
						airportTax=new BigDecimal(D8tax).add(new BigDecimal(Mytax));
						d8tax=d8tax.add(new BigDecimal(D8tax));
						myMap.put(priceBreakup.getDescription(), new BigDecimal(Mytax));
						myTax=myTax.add(new BigDecimal(Mytax));						
					}
					BigDecimal markupInBooking=markup.multiply(flightOrderRow.getBaseToBookingExchangeRate());					
					BigDecimal totalPriceInBooking = basePriceInBooking.add(taxesInBooking);
					BigDecimal basePricewithmarkupInBooking = basePriceInBooking.add(markupInBooking);
					basePricewithmarkupInBooking = basePricewithmarkupInBooking.setScale(2, BigDecimal.ROUND_UP);
					BigDecimal basewithtax = totalBasePriceInBooking.add(taxesInBooking);
					BigDecimal totalPrice = basePricewithmarkupInBooking.add(taxesInBooking).setScale(2, BigDecimal.ROUND_UP);
					newObj.setMobile(customer.getMobile());				
					newObj.setQty(1);
					newObj.setPrice(basePricewithmarkupInBooking);
					newObj.setTotPrice(totalPrice.setScale(2, BigDecimal.ROUND_UP));
					newObj.setTotWithGst(totalBasePriceInBooking.setScale(2, BigDecimal.ROUND_UP).add(gstOnMarkup.multiply(baseToBookingRate)));
					newObj.setTax(airportTax.setScale(2, BigDecimal.ROUND_UP));
					newObj.setTotalTax(taxesInBooking.setScale(2, BigDecimal.ROUND_UP));					
					invoiceDetails.add(newObj);
					passList.add(priceBreakup.getDescription());
				}
			}
			if(!D8tax.equals("0.00")){
				FlightInvoiceData intlTax=null;
				Set<String> passset=passengerMap.keySet();
				for(String key:passset){
					String passtype=key;				
					int noofpass=passengerMap.get(key);					
					BigDecimal MYamount=myMap.get(key);
					BigDecimal TotalMYamount=MYamount.multiply(new BigDecimal(noofpass));
					intlTax=new FlightInvoiceData();
					intlTax.setPassengerType(passtype);
					intlTax.setQty(noofpass);
					intlTax.setTotalMYamount(TotalMYamount);
					intlTax.setMYamount(MYamount);
				}
			}
		}
		else{
			Map<String,Integer> passengerMap = getPassengerMap(flightOrderRow);			
			Map<String,BigDecimal> myMap = new HashMap<String, BigDecimal>();
			List<String> passList=new ArrayList<String>();
			String customerNames = "";
			String customerNames1 = "";
			String customerNames2 = "";
			FlightInvoiceData newObj = null;
			List<FlightOrderCustomer> flightOrderCustomers= flightOrderRowEmailDao.customerDeatails(flightOrderRow);
			if(flightOrderCustomers != null)
			{				
				for(int i=0;i<flightOrderCustomers.size();i++){
					FlightOrderCustomer  customer=flightOrderCustomers.get(i);
					List<FlightOrderCustomerSSR> ssrDetail = flightOrderRowEmailDao.flightCustomerSSR(customer.getFlightCustomer().getId());
					List<FlightOrderCustomerPriceBreakup> priceBreakups = flightOrderRowEmailDao.flightPriceBreakup(flightOrderRow);
					if(priceBreakups != null && priceBreakups.size()>i && customer!=null)
					{						
						FlightOrderCustomerPriceBreakup  priceBreakup=flightOrderRowEmailDao.flightPriceBreakup(flightOrderRow).get(i);
						BigDecimal brakupMarkup = new BigDecimal(0);
						try
						{
							brakupMarkup = new BigDecimal(priceBreakup.getMarkup()).multiply(flightOrderRow.getBaseToBookingExchangeRate());
						}
						catch(Exception e)
						{
							logger.error("brakupMarkup conversion issue--" +e.getMessage());							
							brakupMarkup = new BigDecimal(0);
						}						
						newObj = new FlightInvoiceData();					
						newObj.setEticketnumber(customer.getEticketnumber());
						if(ssrDetail!=null && ssrDetail.size() > 0){
							if(ssrDetail.get(i).getMealname()!=null && !ssrDetail.get(i).getMealname().equalsIgnoreCase("")){
								newObj.isMealAvailable = true;						
							}
							if(ssrDetail.get(i).getReturnmealname()!=null && !ssrDetail.get(i).getReturnmealname().equalsIgnoreCase("")){
								newObj.isReturnMealAvailable = true;						
							}
							newObj.setMealName(ssrDetail.get(i).getMealname());
							newObj.setReturnMealName(ssrDetail.get(i).getReturnmealname());
							if(ssrDetail.get(i).getBaggageweight()!=null && !ssrDetail.get(i).getBaggageweight().equalsIgnoreCase(""))
								newObj.setBaggageweight(Integer.parseInt(ssrDetail.get(i).getBaggageweight()));
							else
								newObj.setBaggageweight(0);

							if(ssrDetail.get(i).getReturnbaggageweight()!=null && !ssrDetail.get(i).getReturnbaggageweight().equalsIgnoreCase(""))
								newObj.setReturnbaggageweight(Integer.parseInt(ssrDetail.get(i).getReturnbaggageweight()));
							else
								newObj.setReturnbaggageweight(0);
						}						customerNames=customer.getFirstName().replaceAll("[-+.^:,]"," ");
						customerNames1=customer.getLastName().replaceAll("[-+.^:,]"," ");
						customerNames2=customer.getTitle().replaceAll("[-+.^:,]"," ");
						newObj.setTitle(customerNames2);						
						newObj.setLastName(customerNames1);
						newObj.setFirstName(customerNames);
						newObj.setPassengerType(customer.getPassengerTypeCode());
						newObj.setGender(customer.getGender());
						newObj.setRmdetail(customer.getRmConfigTripDetailsModel());
						BigDecimal basePrice= priceBreakup.getBaseFare().multiply(flightOrderRow.getApiToBaseExchangeRate());
						BigDecimal basePriceInBooking=basePrice.multiply(flightOrderRow.getBaseToBookingExchangeRate());
						BigDecimal totalBasePriceInBooking=basePrice.multiply(flightOrderRow.getBaseToBookingExchangeRate()).multiply(new BigDecimal(passengerMap.get(priceBreakup.getDescription())));
						BigDecimal taxes= priceBreakup.getTax().multiply(flightOrderRow.getApiToBaseExchangeRate());
						BigDecimal taxesInBooking=taxes.multiply(flightOrderRow.getBaseToBookingExchangeRate());
						String taxDescription=priceBreakup.getTax_description();/*"D8:6.00;MY:7.00;F2:2.00;IN:49.00;JN:48.00;WO:16.00;YQ:401.00;YR:9.00;"*/;
						String temp=taxDescription;
						BigDecimal airportTax=new BigDecimal("0.00");
						airportTax=taxesInBooking;
						BigDecimal totalAirportTax=airportTax.multiply(new BigDecimal(passengerMap.get(priceBreakup.getDescription())));
						BigDecimal totalPriceInBooking = basePriceInBooking.add(brakupMarkup).setScale(2, BigDecimal.ROUND_UP);
						BigDecimal totalPrice = totalPriceInBooking.add(taxesInBooking).setScale(2, BigDecimal.ROUND_UP);
						newObj.setMobile(customer.getMobile());
						newObj.setQty(1);
						newObj.setPrice(totalPriceInBooking);
						newObj.setTotPrice(totalPrice.setScale(2, BigDecimal.ROUND_UP));
						newObj.setTotal_tax(brakupMarkup.setScale(2, BigDecimal.ROUND_UP));
						newObj.setTax(airportTax.setScale(2, BigDecimal.ROUND_UP));
						newObj.setTotalTax(totalAirportTax.setScale(2, BigDecimal.ROUND_UP));
						passList.add(priceBreakup.getDescription());
					}

					List<FlightOrderTripDetail> tripDeatailsList = flightOrderRowEmailDao.flightTrips(flightOrderRow);
					if(tripDeatailsList.size() > 0){
						List<FlightOrderTripDetail> tripDeatailsListnew = new ArrayList<FlightOrderTripDetail>();
						for (FlightOrderTripDetail flightOrderTripDetail : tripDeatailsList) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
							if (flightOrderTripDetail.getArrivalTimestamp() != null) {
								String arrivalDt = dateFormat.format(flightOrderTripDetail.getArrivalTimestamp())
										.toString();
								flightOrderTripDetail.setArrDate(arrivalDt);
							}
							if (flightOrderTripDetail.getDepartureTimestamp() != null) {
								String departureDt = dateFormat.format(flightOrderTripDetail.getDepartureTimestamp())
										.toString();
								flightOrderTripDetail.setDepDate(departureDt);
							}
							tripDeatailsListnew.add(flightOrderTripDetail);
						}
						newObj.setTripParticulars(tripDeatailsListnew);
					}
					invoiceDetails.add(newObj);	
				}
			}

		}
		return invoiceDetails;
	}	

	public Map<String,Integer> getPassengerMap(FlightOrderRow  flightOrderRow){
		Map<String,Integer> passengerMap = new HashMap<String, Integer>();
		List<FlightOrderCustomer> flightOrderCustomers= flightOrderRowEmailDao.customerDeatails(flightOrderRow);
		for(int i=0;i<flightOrderCustomers.size();i++){
			FlightOrderCustomer flightOrderCustomer=(FlightOrderCustomer)flightOrderCustomers.get(i);
			FlightOrderCustomerPriceBreakup  priceBreakup=flightOrderRowEmailDao.flightPriceBreakup(flightOrderRow).get(i);
			if(passengerMap.get(priceBreakup.getDescription())!=null){
				passengerMap.put(priceBreakup.getDescription(),passengerMap.get(priceBreakup.getDescription())+1);
			}else{
				passengerMap.put(priceBreakup.getDescription(), 1);
			}
		}		
		return passengerMap;
	}
	public RmConfigTripDetailsModel getRmdetail() {
		return rmdetail;
	}
	public void setRmdetail(RmConfigTripDetailsModel rmdetail) {
		this.rmdetail = rmdetail;
	}
	


}
