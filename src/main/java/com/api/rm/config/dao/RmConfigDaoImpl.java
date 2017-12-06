package com.api.rm.config.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.api.model.rm.config.Vo.HttpStatusMessage;
import com.api.model.rm.config.Vo.RmConfigFields;
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

public class RmConfigDaoImpl implements RmConfigDao{
	static final Logger logger = Logger.getLogger(RmConfigDaoImpl.class);

	/**
	 * @author      : Shaik Basha
	 * @createdAt   : 12-20-2017
	 * @version
	 */
	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;


	@Override
	public List<FlightOrderCustomer> getFlightOrderCustomerData(String paxId) {
		List<FlightOrderCustomer> list=null;
		Criteria crit = null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(FlightOrderCustomer.class);			
			crit.add(Restrictions.eq("paxId", paxId));			
			list =crit.list();				
		}catch (HibernateException e) {	
			logger.error("getParentCompany Exception", e);
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return list;
	}




	@Override
	public BusOrderCustomerDetail getBusOrderCust(String paxId) {
		BusOrderCustomerDetail ordercustDataBus=null;
		Criteria crit = null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(BusOrderCustomerDetail.class);			
			crit.add(Restrictions.eq("paxId", paxId));			
			ordercustDataBus = (BusOrderCustomerDetail) crit.uniqueResult();				
		}catch (HibernateException e) {	
			logger.error("getParentCompany Exception", e);
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ordercustDataBus;
	}



	@Override
	public HotelOrderGuest getHotelOrderCust(String paxId) {
		HotelOrderGuest ordercustDatahotel=null;
		Criteria crit = null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(HotelOrderGuest.class);			
			crit.add(Restrictions.eq("paxId", paxId));			
			ordercustDatahotel = (HotelOrderGuest) crit.uniqueResult();				
		}catch (HibernateException e) {	
			logger.error("getParentCompany Exception", e);
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ordercustDatahotel;
	}

	@Override
	public CarOrderCustomer getCarOrderCust(String paxId) {
		CarOrderCustomer ordercustDataCar=null;
		Criteria crit = null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(CarOrderCustomer.class);			
			crit.add(Restrictions.eq("paxId", paxId));			
			ordercustDataCar = (CarOrderCustomer) crit.uniqueResult();				
		}catch (HibernateException e) {	
			logger.error("getParentCompany Exception", e);
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ordercustDataCar;
	}

	@Override
	public TrainOrderCustomer getTrainOrderCust(String paxId) {
		TrainOrderCustomer ordercustDataTrain=null;
		Criteria crit = null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(TrainOrderCustomer.class);			
			crit.add(Restrictions.eq("paxId", paxId));			
			ordercustDataTrain = (TrainOrderCustomer) crit.uniqueResult();				
		}catch (HibernateException e) {	
			logger.error("getParentCompany Exception", e);
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ordercustDataTrain;
	}



	@Override
	public VisaOrderCustomer getVisaOrderCust(String paxId) {
		VisaOrderCustomer ordercustDataVisa=null;
		Criteria crit = null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(TrainOrderCustomer.class);			
			crit.add(Restrictions.eq("paxId", paxId));			
			ordercustDataVisa = (VisaOrderCustomer) crit.uniqueResult();				
		}catch (HibernateException e) {	
			logger.error("getParentCompany Exception", e);
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ordercustDataVisa;
	}





	@Override
	public InsuranceOrderCustomerDetail getInsuranceOrderCust(String paxId) {
		InsuranceOrderCustomerDetail ordercustDataInsurance=null;
		Criteria crit = null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(InsuranceOrderCustomerDetail.class);			
			crit.add(Restrictions.eq("paxId", paxId));			
			ordercustDataInsurance = (InsuranceOrderCustomerDetail) crit.uniqueResult();				
		}catch (HibernateException e) {	
			logger.error("getParentCompany Exception", e);
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ordercustDataInsurance;
	}



	@Override
	public MiscellaneousOrderCustomer getMiscOrderCust(String paxId) {
		MiscellaneousOrderCustomer ordercustDataMisc=null;
		Criteria crit = null;
		try{
			session = sessionFactory.openSession();			
			crit = session.createCriteria(MiscellaneousOrderCustomer.class);			
			crit.add(Restrictions.eq("paxId", paxId));			
			ordercustDataMisc = (MiscellaneousOrderCustomer) crit.uniqueResult();				
		}catch (HibernateException e) {	
			logger.error("getParentCompany Exception", e);
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ordercustDataMisc;
	}







	public void updateFlightOrderCust(FlightOrderCustomer orderCustomer) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		FlightOrderCustomer orderCustomerNew=null;
		try{	
			logger.info("orderCustomer================="+orderCustomer.getId());
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			orderCustomerNew=(FlightOrderCustomer)session.get(FlightOrderCustomer.class,orderCustomer.getId());
			orderCustomerNew.setRmConfigTripDetailsModel(orderCustomer.getRmConfigTripDetailsModel());
			session.saveOrUpdate(orderCustomerNew);
			tx.commit();
		}catch(Exception e){
			logger.error("insertRMConfigTripDetails Exception  orderCustomer", e);
		}		
		finally {			
			session.close();
		}

	}

	@Override
	public void updateHotelOrderCust(HotelOrderGuest orderCustomer) {
		HotelOrderGuest orderCustomerNew=null;
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try{	
			logger.info("orderCustomer================="+orderCustomer.getId());
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			orderCustomerNew=(HotelOrderGuest)session.get(HotelOrderGuest.class,orderCustomer.getId());
			orderCustomerNew.setRmConfigTripDetailsModel(orderCustomer.getRmConfigTripDetailsModel());
			session.saveOrUpdate(orderCustomerNew);
			tx.commit();
		}catch(Exception e){
			logger.error("insertRMConfigTripDetails Exception  orderCustomer", e);
		}		
		finally {			
			session.close();
		}

	}





	@Override
	public void updateCarOrderCust(CarOrderCustomer orderCustomer) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		CarOrderCustomer orderCustomerNew=null;
		try{	
			logger.info("orderCustomer================="+orderCustomer.getId());
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			orderCustomerNew=(CarOrderCustomer)session.get(CarOrderCustomer.class,orderCustomer.getId());
			orderCustomerNew.setRmConfigTripDetailsModel(orderCustomer.getRmConfigTripDetailsModel());
			session.saveOrUpdate(orderCustomerNew);
			tx.commit();
		}catch(Exception e){
			logger.error("insertRMConfigTripDetails Exception  orderCustomer", e);
		}		
		finally {			
			session.close();
		}

	}
	@Override
	public void updateBusOrderCust(BusOrderCustomerDetail orderCustomer) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		BusOrderCustomerDetail orderCustomerNew=null;
		try{	
			logger.info("orderCustomer================="+orderCustomer.getId());
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			orderCustomerNew=(BusOrderCustomerDetail)session.get(BusOrderCustomerDetail.class,orderCustomer.getId());
			orderCustomerNew.setRmConfigTripDetailsModel(orderCustomer.getRmConfigTripDetailsModel());
			session.saveOrUpdate(orderCustomerNew);
			tx.commit();
		}catch(Exception e){
			logger.error("insertRMConfigTripDetails Exception  orderCustomer", e);
		}		
		finally {			
			session.close();
		}

	}
	@Override
	public void updateTrainOrderCust(TrainOrderCustomer orderCustomer) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		TrainOrderCustomer orderCustomerNew=null;
		try{	
			logger.info("orderCustomer================="+orderCustomer.getId());
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			orderCustomerNew=(TrainOrderCustomer)session.get(TrainOrderCustomer.class,orderCustomer.getId());
			orderCustomerNew.setRmConfigTripDetailsModel(orderCustomer.getRmConfigTripDetailsModel());
			session.saveOrUpdate(orderCustomerNew);
			tx.commit();
		}catch(Exception e){
			logger.error("insertRMConfigTripDetails Exception  orderCustomer", e);
		}		
		finally {			
			session.close();
		}

	}

	@Override
	public void updateInsurranceOrderCust(InsuranceOrderCustomerDetail orderCustomer) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		InsuranceOrderCustomerDetail orderCustomerNew=null;
		try{	
			logger.info("orderCustomer================="+orderCustomer.getId());
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			orderCustomerNew=(InsuranceOrderCustomerDetail)session.get(InsuranceOrderCustomerDetail.class,orderCustomer.getId());
			orderCustomerNew.setRmConfigTripDetailsModel(orderCustomer.getRmConfigTripDetailsModel());
			session.saveOrUpdate(orderCustomerNew);
			tx.commit();
		}catch(Exception e){
			logger.error("insertRMConfigTripDetails Exception  orderCustomer", e);
		}		
		finally {			
			session.close();
		}

	}

	@Override
	public void updateVisaOrderCust(VisaOrderCustomer orderCustomer) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		VisaOrderCustomer orderCustomerNew=null;
		try{	
			logger.info("orderCustomer================="+orderCustomer.getId());
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			orderCustomerNew=(VisaOrderCustomer)session.get(VisaOrderCustomer.class,orderCustomer.getId());
			orderCustomerNew.setRmConfigTripDetailsModel(orderCustomer.getRmConfigTripDetailsModel());
			session.saveOrUpdate(orderCustomerNew);
			tx.commit();
		}catch(Exception e){
			logger.error("insertRMConfigTripDetails Exception  orderCustomer", e);
		}		
		finally {			
			session.close();
		}

	}

	@Override
	public void updateMiscOrderCust(MiscellaneousOrderCustomer orderCustomer) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		MiscellaneousOrderCustomer orderCustomerNew=null;
		try{	
			logger.info("orderCustomer================="+orderCustomer.getId());
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			orderCustomerNew=(MiscellaneousOrderCustomer)session.get(MiscellaneousOrderCustomer.class,orderCustomer.getId());
			orderCustomerNew.setRmConfigTripDetailsModel(orderCustomer.getRmConfigTripDetailsModel());
			session.saveOrUpdate(orderCustomerNew);
			tx.commit();
		}catch(Exception e){
			logger.error("insertRMConfigTripDetails Exception  orderCustomer", e);
		}		
		finally {			
			session.close();
		}

	}


	@Override




	public HttpStatusMessage buildRmDataWithPaxFlight(List<FlightOrderCustomer>  flightOrdercustList,RmConfigVo rmConfigVo,HttpStatusMessage httpStatusMessage){
		if(flightOrdercustList!=null && flightOrdercustList.size()>0)
			for(FlightOrderCustomer orderRowcustmer:flightOrdercustList){
				if(rmConfigVo.getRmConfigList()!=null && rmConfigVo.getRmConfigList().size()>0){
					for( RmConfigFields  rmconfigData:rmConfigVo.getRmConfigList()){
						if(orderRowcustmer.getPaxId()!=null && !orderRowcustmer.getPaxId().equalsIgnoreCase(""))
						if(rmconfigData.getPaxId()!=null && !rmconfigData.getPaxId().equalsIgnoreCase("") && rmconfigData.getPaxId().equals(orderRowcustmer.getPaxId()))
						{
							RmConfigTripDetailsModel rmConfigTripModel=new RmConfigTripDetailsModel();
							rmConfigTripModel=buildRmdata(rmConfigTripModel,rmconfigData);
							rmConfigTripModel.setOrdertype(rmConfigVo.getServiceType());
							rmConfigTripModel.setOrderId(orderRowcustmer.getFlightOrderRow().getOrderId()!=null && !orderRowcustmer.getFlightOrderRow().getOrderId().trim().equalsIgnoreCase("")?orderRowcustmer.getFlightOrderRow().getOrderId():null);
							orderRowcustmer.setRmConfigTripDetailsModel(rmConfigTripModel);
							updateFlightOrderCust(orderRowcustmer);
							/*if(orderRowcustmer.getFlightOrderRow().getIsInsuranceAdded()){
										buildRmDataWithPaxInsurance()
									}*/
							httpStatusMessage=new HttpStatusMessage(200, "Success");
						}
						else
							httpStatusMessage=new HttpStatusMessage(204 , "rmconfigData.getPaxId() is empty  or null");
						else
							httpStatusMessage=new HttpStatusMessage(204 , "orderRowcustmer.getPaxId() is empty  or null");
					}
					/*}
							else
								httpStatusMessage=new HttpStatusMessage(422, " PassangersList size must be match RmConfigList size");
					} */
				}
			}
		return httpStatusMessage;
	}






	@Override
	public HttpStatusMessage buildRmDataWithPaxHotel(List<HotelOrderGuest> hotelOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage) {
		if(hotelOrderCustList!=null && hotelOrderCustList.size()>0)
			for(HotelOrderGuest orderRowcustmer:hotelOrderCustList){
				if(rmConfigVo.getRmConfigList()!=null && rmConfigVo.getRmConfigList().size()>0){
					for( RmConfigFields  rmconfigData:rmConfigVo.getRmConfigList()){
						if(orderRowcustmer.getPaxId()!=null && !orderRowcustmer.getPaxId().equalsIgnoreCase(""))
						if(rmconfigData.getPaxId()!=null && !rmconfigData.getPaxId().equalsIgnoreCase("") && rmconfigData.getPaxId().equals(orderRowcustmer.getPaxId()))
						{
							RmConfigTripDetailsModel rmConfigTripModel=new RmConfigTripDetailsModel();
							rmConfigTripModel=buildRmdata(rmConfigTripModel,rmconfigData);
							rmConfigTripModel.setOrdertype(rmConfigVo.getServiceType());
							rmConfigTripModel.setOrderId(orderRowcustmer.getRoomInfo().getHotelOrderRow().getOrderReference());
							//rmConfigTripModel.setOrderId(orderRowcustmer.gethot.getOrderId()!=null && !orderRowcustmer.getFlightOrderRow().getOrderId().trim().equalsIgnoreCase("")?orderRowcustmer.getFlightOrderRow().getOrderId():null);
							//RmConfigTripDetailsModel rmModel=insertRmconfig(rmConfigTripModel);
							orderRowcustmer.setRmConfigTripDetailsModel(rmConfigTripModel);
							updateHotelOrderCust(orderRowcustmer);
							httpStatusMessage=new HttpStatusMessage(200, "Success");
						}
						else
							httpStatusMessage=new HttpStatusMessage(204 , "rmconfigData.getPaxId() is empty  or null");
						else
							httpStatusMessage=new HttpStatusMessage(204 , "orderRowcustmer.getPaxId() is empty  or null");
					}
					/*}
							else
								httpStatusMessage=new HttpStatusMessage(422, " PassangersList size must be match RmConfigList size");
					} */
				}
			}
		return httpStatusMessage;
	}


	@Override
	public HttpStatusMessage buildRmDataWithPaxBus(List<BusOrderCustomerDetail> busOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage) {
		if(busOrderCustList!=null && busOrderCustList.size()>0)
			for(BusOrderCustomerDetail orderRowcustmer:busOrderCustList){
				if(rmConfigVo.getRmConfigList()!=null && rmConfigVo.getRmConfigList().size()>0){
					for( RmConfigFields  rmconfigData:rmConfigVo.getRmConfigList()){
						if(orderRowcustmer.getPaxId()!=null && !orderRowcustmer.getPaxId().equalsIgnoreCase(""))
						if(rmconfigData.getPaxId()!=null && !rmconfigData.getPaxId().equalsIgnoreCase("") && rmconfigData.getPaxId().equals(orderRowcustmer.getPaxId()))
						{
							RmConfigTripDetailsModel rmConfigTripModel=new RmConfigTripDetailsModel();
							rmConfigTripModel=buildRmdata(rmConfigTripModel,rmconfigData);
							rmConfigTripModel.setOrdertype(rmConfigVo.getServiceType());
							//rmConfigTripModel.setOrderId("HotelDummy");
							rmConfigTripModel.setOrderId(orderRowcustmer.getBusOrderRow().getOrderId()!=null && !orderRowcustmer.getBusOrderRow().getOrderId().trim().equalsIgnoreCase("")?orderRowcustmer.getBusOrderRow().getOrderId():null);
							orderRowcustmer.setRmConfigTripDetailsModel(rmConfigTripModel);
							updateBusOrderCust(orderRowcustmer);
							httpStatusMessage=new HttpStatusMessage(200, "Success");
						}
						else
							httpStatusMessage=new HttpStatusMessage(204 , "rmconfigData.getPaxId() is empty  or null");
						else
							httpStatusMessage=new HttpStatusMessage(204 , "orderRowcustmer.getPaxId() is empty  or null");
					}
					/*}
							else
								httpStatusMessage=new HttpStatusMessage(422, " PassangersList size must be match RmConfigList size");
					} */
				}
			}
		return httpStatusMessage;
	}



	@Override
	public HttpStatusMessage buildRmDataWithPaxCar(List<CarOrderRow> carOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HttpStatusMessage buildRmDataWithPaxTrain(List<TrainOrderRow> trainOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HttpStatusMessage buildRmDataWithPaxVisa(List<VisaOrderRow> visaOrderCustList, RmConfigVo rmConfigVo,
			HttpStatusMessage httpStatusMessage) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HttpStatusMessage buildRmDataWithPaxInsurance(List<InsuranceOrderCustomerDetail> insuranceOrderCustList,
			RmConfigVo rmConfigVo, HttpStatusMessage httpStatusMessage) {
		if(insuranceOrderCustList!=null && insuranceOrderCustList.size()>0)
			for(InsuranceOrderCustomerDetail orderRowcustmer:insuranceOrderCustList){
				if(rmConfigVo.getRmConfigList()!=null && rmConfigVo.getRmConfigList().size()>0){
					for( RmConfigFields  rmconfigData:rmConfigVo.getRmConfigList()){
						if(orderRowcustmer.getPaxId()!=null && !orderRowcustmer.getPaxId().trim().equalsIgnoreCase(""))
						if(rmconfigData.getPaxId()!=null && !rmconfigData.getPaxId().trim().equalsIgnoreCase("") && rmconfigData.getPaxId().equals(orderRowcustmer.getPaxId()))
						{
							RmConfigTripDetailsModel rmConfigTripModel=new RmConfigTripDetailsModel();
							rmConfigTripModel=buildRmdata(rmConfigTripModel,rmconfigData);
							rmConfigTripModel.setOrdertype("Insurance");
							//rmConfigTripModel.setOrderId("HotelDummy");
							rmConfigTripModel.setOrderId(orderRowcustmer.getInsuranceOrderRow().getOrderId()!=null && !orderRowcustmer.getInsuranceOrderRow().getOrderId().trim().equalsIgnoreCase("")?orderRowcustmer.getInsuranceOrderRow().getOrderId():null);
							orderRowcustmer.setRmConfigTripDetailsModel(rmConfigTripModel);
							updateInsurranceOrderCust(orderRowcustmer);
							httpStatusMessage=new HttpStatusMessage(200, "Success");
						}else
							httpStatusMessage=new HttpStatusMessage(204 , "rmconfigData.getPaxId() is empty  or null");
						else
							httpStatusMessage=new HttpStatusMessage(204 , "orderRowcustmer.getPaxId() is empty  or null");
						
					}
					/*}
							else
								httpStatusMessage=new HttpStatusMessage(422, " PassangersList size must be match RmConfigList size");
					} */
				}
			}
		return httpStatusMessage;
	}



	@Override
	public HttpStatusMessage buildRmDataWithPaxMisc(List<MiscellaneousOrderRow> miscOrderCustList,
			RmConfigVo rmConfigVo, HttpStatusMessage httpStatusMessage) {
		// TODO Auto-generated method stub
		return null;
	}





	public RmConfigTripDetailsModel buildRmdata(RmConfigTripDetailsModel rmConfigTripModel,RmConfigFields rmconfigData){
		rmConfigTripModel.setApproverName(rmconfigData.getApproverName()!=null && !rmconfigData.getApproverName().trim().equalsIgnoreCase("")?rmconfigData.getApproverName():null);
		rmConfigTripModel.setBillNonBill(rmconfigData.getBillNonBill()!=null && !rmconfigData.getBillNonBill().trim().equalsIgnoreCase("")?rmconfigData.getBillNonBill():null);
		rmConfigTripModel.setBussinessUnit(rmconfigData.getBussinessUnit()!=null && !rmconfigData.getBussinessUnit().trim().equalsIgnoreCase("")?rmconfigData.getBussinessUnit():null);
		rmConfigTripModel.setCostCenter(rmconfigData.getCostCenter()!=null && !rmconfigData.getCostCenter().trim().equalsIgnoreCase("")?rmconfigData.getCostCenter():null);
		rmConfigTripModel.setDepartment(rmconfigData.getDepartment()!=null && !rmconfigData.getDepartment().trim().equalsIgnoreCase("")?rmconfigData.getDepartment():null);
		rmConfigTripModel.setEmpCode(rmconfigData.getEmpCode()!=null && !rmconfigData.getEmpCode().trim().equalsIgnoreCase("")?rmconfigData.getEmpCode():null);
		rmConfigTripModel.setLocation(rmconfigData.getLocation()!=null && !rmconfigData.getLocation().trim().equalsIgnoreCase("")?rmconfigData.getLocation():null);
		rmConfigTripModel.setManualField1(rmconfigData.getManualField1()!=null && !rmconfigData.getManualField1().trim().equalsIgnoreCase("")?rmconfigData.getManualField1():null);
		rmConfigTripModel.setManualField2(rmconfigData.getManualField2()!=null && !rmconfigData.getManualField2().trim().equalsIgnoreCase("")?rmconfigData.getManualField2():null);
		rmConfigTripModel.setManualField3(rmconfigData.getManualField3()!=null && !rmconfigData.getManualField3().trim().equalsIgnoreCase("")?rmconfigData.getManualField3():null);
		rmConfigTripModel.setManualField4(rmconfigData.getManualField4()!=null && !rmconfigData.getManualField4().trim().equalsIgnoreCase("")?rmconfigData.getManualField4():null);
		rmConfigTripModel.setManualField5(rmconfigData.getManualField5()!=null && !rmconfigData.getManualField5().trim().equalsIgnoreCase("")?rmconfigData.getManualField5():null);
		rmConfigTripModel.setProjectCode(rmconfigData.getProjectCode()!=null && !rmconfigData.getProjectCode().trim().equalsIgnoreCase("")?rmconfigData.getProjectCode():null);
		rmConfigTripModel.setReasonForTravel(rmconfigData.getReasonForTravel()!=null && !rmconfigData.getReasonForTravel().trim().equalsIgnoreCase("")?rmconfigData.getReasonForTravel():null);
		rmConfigTripModel.setTrNumber(rmconfigData.getTrNumber()!=null && !rmconfigData.getTrNumber().trim().equalsIgnoreCase("")?rmconfigData.getTrNumber():null);



		return rmConfigTripModel ;
	}



}
