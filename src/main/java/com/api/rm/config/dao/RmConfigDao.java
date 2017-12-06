package com.api.rm.config.dao;

import java.util.List;

import com.api.model.rm.config.Vo.HttpStatusMessage;
import com.api.model.rm.config.Vo.RmConfigVo;
import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.car.entity.CarOrderRow;
import com.tayyarah.car.ordercustomer.model.CarOrderCustomer;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.insurance.entity.InsuranceOrderCustomerDetail;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.miscellaneous.ordercustomer.model.MiscellaneousOrderCustomer;
import com.tayyarah.misellaneous.entity.MiscellaneousOrderRow;
import com.tayyarah.train.entity.TrainOrderRow;
import com.tayyarah.train.ordercustomer.model.TrainOrderCustomer;
import com.tayyarah.visa.entity.VisaOrderRow;
import com.tayyarah.visa.ordercustomer.model.VisaOrderCustomer;

public interface RmConfigDao {
	/**
	 * @author      : Shaik Basha
	 * @createdAt   : 12-20-2017
	 * @version
	 */
	

	public void updateFlightOrderCust(FlightOrderCustomer orderCustomer);
	
	public void updateHotelOrderCust(HotelOrderGuest orderCustomer);
	
	public void updateCarOrderCust(CarOrderCustomer orderCustomer);
	
	public void updateBusOrderCust(BusOrderCustomerDetail orderCustomer);
	
	public void updateTrainOrderCust(TrainOrderCustomer orderCustomer);
	
	
	public void updateInsurranceOrderCust(InsuranceOrderCustomerDetail orderCustomer);
	
	public void updateVisaOrderCust(VisaOrderCustomer orderCustomer);
	
	public void updateMiscOrderCust(MiscellaneousOrderCustomer orderCustomer);
	
	
	
	
	public List<FlightOrderCustomer> getFlightOrderCustomerData(String paxId);
	
	public HotelOrderGuest getHotelOrderCust(String paxId);

	public BusOrderCustomerDetail getBusOrderCust(String paxId);

	public CarOrderCustomer getCarOrderCust(String paxId);

	public TrainOrderCustomer getTrainOrderCust(String paxId);

	public VisaOrderCustomer getVisaOrderCust(String paxId);

	public InsuranceOrderCustomerDetail getInsuranceOrderCust(String paxId);

	public MiscellaneousOrderCustomer getMiscOrderCust(String paxId);
	
	
	
	

	public HttpStatusMessage buildRmDataWithPaxFlight(List<FlightOrderCustomer> flightorderCustList,
			RmConfigVo rmConfigVo, HttpStatusMessage httpStatusMessage);
	
	public HttpStatusMessage buildRmDataWithPaxHotel(List<HotelOrderGuest> hotelOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage);

	public HttpStatusMessage buildRmDataWithPaxBus(List<BusOrderCustomerDetail> busOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage);

	public HttpStatusMessage buildRmDataWithPaxCar(List<CarOrderRow> carOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage);

	public HttpStatusMessage buildRmDataWithPaxTrain(List<TrainOrderRow> trainOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage);

	public HttpStatusMessage buildRmDataWithPaxVisa(List<VisaOrderRow> visaOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage);

	public HttpStatusMessage buildRmDataWithPaxInsurance(List<InsuranceOrderCustomerDetail> insuranceOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage);

	public HttpStatusMessage buildRmDataWithPaxMisc(List<MiscellaneousOrderRow> miscOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage);

	

	

}
