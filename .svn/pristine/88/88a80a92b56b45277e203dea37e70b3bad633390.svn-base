package com.api.rm.config.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.rm.config.Vo.HttpStatusMessage;
import com.api.model.rm.config.Vo.RmConfigFields;
import com.api.model.rm.config.Vo.RmConfigVo;
import com.api.rm.config.dao.RmConfigDao;
import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.car.entity.CarOrderRow;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.insurance.entity.InsuranceOrderCustomerDetail;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.misellaneous.entity.MiscellaneousOrderRow;
import com.tayyarah.train.entity.TrainOrderRow;
import com.tayyarah.visa.entity.VisaOrderRow;

@RestController
@RequestMapping(value = "/rmconfig")
public class RmConfigController {
	static final Logger logger = Logger.getLogger(RmConfigController.class);
	/**
	 * @author      : Shaik Basha
	 * @createdAt   : 12-20-2017
	 * @version
	 */
	@Autowired
	RmConfigDao rmConfigDao;
	
	@RequestMapping(value = "/details", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public HttpStatusMessage  produceDetails(@RequestBody RmConfigVo rmConfigVo,HttpServletRequest request, HttpServletResponse response) throws  Exception {
		HttpStatusMessage httpStatusMessage=null;
		if(rmConfigVo==null) 
			httpStatusMessage=new HttpStatusMessage(204 , "No Content");
		else{
			if(rmConfigVo.getServiceType()!=null)
				if(rmConfigVo.getRmConfigList()!=null && rmConfigVo.getRmConfigList().size()>0){
					if(rmConfigVo.getServiceType().equals("Flight")){
						List<FlightOrderCustomer> flightorderCustList=new ArrayList<>();
						List<InsuranceOrderCustomerDetail> insuredorderCustList=new ArrayList<>();
						for(RmConfigFields rmconfigfields :rmConfigVo.getRmConfigList()){
							List<FlightOrderCustomer> orderCustDatalist=new ArrayList<>();
							InsuranceOrderCustomerDetail insuredCustomerData=new InsuranceOrderCustomerDetail();
							if(rmconfigfields.getPaxId()!=null && !rmconfigfields.getPaxId().trim().equalsIgnoreCase(""))
								orderCustDatalist=rmConfigDao.getFlightOrderCustomerData(rmconfigfields.getPaxId());
								insuredCustomerData=rmConfigDao.getInsuranceOrderCust(rmconfigfields.getPaxId());
							if(orderCustDatalist!=null && orderCustDatalist.size()>0)
							flightorderCustList=orderCustDatalist;
							if(insuredCustomerData!=null)
							insuredorderCustList.add(insuredCustomerData);
						}
						if(flightorderCustList!=null && flightorderCustList.size()>0)
							httpStatusMessage= rmConfigDao.buildRmDataWithPaxFlight(flightorderCustList, rmConfigVo, httpStatusMessage);
						else
							httpStatusMessage=new HttpStatusMessage(204 , "flightorderCustList empty or null");
						if(insuredorderCustList!=null && insuredorderCustList.size()>0)
							httpStatusMessage= rmConfigDao.buildRmDataWithPaxInsurance(insuredorderCustList, rmConfigVo, httpStatusMessage);
						else 
							httpStatusMessage=new HttpStatusMessage(204 , "insuredorderCustList empty or null");
					}
					if(rmConfigVo.getServiceType().equals("Hotel")){
						List<HotelOrderGuest> hotelorderguestList=new ArrayList<>();
						for(RmConfigFields rmconfigfields :rmConfigVo.getRmConfigList()){
							HotelOrderGuest orderGuestData=new HotelOrderGuest();
							if(rmconfigfields.getPaxId()!=null && !rmconfigfields.getPaxId().trim().equalsIgnoreCase(""))
								orderGuestData=rmConfigDao.getHotelOrderCust(rmconfigfields.getPaxId());
							if(orderGuestData!=null)
							hotelorderguestList.add(orderGuestData);
						}
						if(hotelorderguestList!=null && hotelorderguestList.size()>0)
							httpStatusMessage= rmConfigDao.buildRmDataWithPaxHotel(hotelorderguestList, rmConfigVo, httpStatusMessage);
						else
							httpStatusMessage=new HttpStatusMessage(204 , "hotelorderguestList empty or null");
					}
					if(rmConfigVo.getServiceType().equals("Bus")){
						List<BusOrderCustomerDetail> busorderguestList=new ArrayList<>();
						for(RmConfigFields rmconfigfields :rmConfigVo.getRmConfigList()){
							BusOrderCustomerDetail orderGuestData=new BusOrderCustomerDetail();
							if(rmconfigfields.getPaxId()!=null && !rmconfigfields.getPaxId().trim().equalsIgnoreCase(""))
								orderGuestData=rmConfigDao.getBusOrderCust(rmconfigfields.getPaxId());
							if(orderGuestData!=null)
							busorderguestList.add(orderGuestData);
						}
						if(busorderguestList!=null && busorderguestList.size()>0)
							httpStatusMessage= rmConfigDao.buildRmDataWithPaxBus(busorderguestList, rmConfigVo, httpStatusMessage);
						else
							httpStatusMessage=new HttpStatusMessage(204 , "busorderguestList empty or null");
					}
					if(rmConfigVo.getServiceType().equals("Car")){
						//List<CarOrderRow> carOrders=rmConfigDao.getCarOrderList(rmConfigVo.getTransactionKey());
						//httpStatusMessage= rmConfigDao.buildRmDataWithPaxCar(carOrders, rmConfigVo, httpStatusMessage);
					}
					if(rmConfigVo.getServiceType().equals("Train")){
						//List<TrainOrderRow> trainOrders=rmConfigDao.getTrainOrderList(rmConfigVo.getTransactionKey());
						//httpStatusMessage= rmConfigDao.buildRmDataWithPaxTrain(trainOrders, rmConfigVo, httpStatusMessage);
					}
					if(rmConfigVo.getServiceType().equals("Visa")){
						//List<VisaOrderRow> visaOrders=rmConfigDao.getVisaOrderList(rmConfigVo.getTransactionKey());
						//httpStatusMessage= rmConfigDao.buildRmDataWithPaxVisa(visaOrders, rmConfigVo, httpStatusMessage);
					}
					if(rmConfigVo.getServiceType().equals("Insurance")){
						//List<InsuranceOrderRow> insuranceOrders=rmConfigDao.getInsuranceOrderList(rmConfigVo.getTransactionKey());
						//httpStatusMessage= rmConfigDao.buildRmDataWithPaxInsurance(insuranceOrders, rmConfigVo, httpStatusMessage);
					}
					if(rmConfigVo.getServiceType().equals("Miscellaneous")){
						//List<MiscellaneousOrderRow> miscOrders=rmConfigDao.getMiscOrderList(rmConfigVo.getTransactionKey());
						//httpStatusMessage= rmConfigDao.buildRmDataWithPaxMisc(miscOrders, rmConfigVo, httpStatusMessage);
					}
				}else
					httpStatusMessage=new HttpStatusMessage(204 , "RmConfigList is empty or null");
			
		}
		return httpStatusMessage;
	}


	
}
