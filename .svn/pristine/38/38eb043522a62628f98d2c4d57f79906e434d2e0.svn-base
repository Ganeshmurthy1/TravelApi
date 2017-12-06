package com.api.bulk.emp.dao;

import java.util.List;

import org.hibernate.criterion.Conjunction;

import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.car.entity.CarOrderRow;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.misellaneous.entity.MiscellaneousOrderRow;
import com.tayyarah.train.entity.TrainOrderRow;
import com.tayyarah.visa.entity.VisaOrderRow;

public interface BulkInvoicePdfDao {
	public List<FlightOrderRow> getFlightOrderRowList(String fromInvoiceDate,String toInvoiceDate);
	public List<HotelOrderRow> getHotelOrderRowList(String fromInvoiceDate,String toInvoiceDate);
	public List<CarOrderRow> getCarOrderRowList(String fromInvoiceDate,String toInvoiceDate);
	public List<BusOrderRow> getBusOrderRowList(String fromInvoiceDate,String toInvoiceDate);
	public List<TrainOrderRow> getTrainOrderRowList(String fromInvoiceDate,String toInvoiceDate);
	public List<VisaOrderRow> getVisaOrderRowList(String fromInvoiceDate,String toInvoiceDate);
	public List<InsuranceOrderRow> getInsuranceOrderRowList(String fromInvoiceDate,String toInvoiceDate);
	public List<MiscellaneousOrderRow> getMiscellaneousOrderRowList(String fromInvoiceDate,String toInvoiceDate);
	public Conjunction  getConunctionWithDbFormatDates(String fromInvoiceDate,String toInvoiceDate);
	
	 
}
