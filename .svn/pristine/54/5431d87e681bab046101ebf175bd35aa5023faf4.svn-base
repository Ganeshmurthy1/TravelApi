package com.tayyarah.email.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.flight.entity.FlightOrderRow;



public class FlightOrderRowEmailDaoServiceImp implements
		FlightOrderRowEmailDaoService {
	@Autowired
	FlightOrderRowEmailDao flightOrderId;

	@Override
	public List<FlightOrderRow> getFlightOrderRowByOrderRef(String orderId)
			throws HibernateException {
		// TODO Auto-generated method stub
		return flightOrderId.flightOrderRows();
	}

}
