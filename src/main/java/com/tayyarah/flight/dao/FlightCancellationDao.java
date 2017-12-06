package com.tayyarah.flight.dao;

import java.io.Serializable;

import com.tayyarah.flight.entity.FlightOrderRowCancellation;
import com.tayyarah.flight.entity.FlightOrderRowCancellationAPIResponse;

public interface FlightCancellationDao {
	public void insertFlightOrderRowCancellation(FlightOrderRowCancellation forc);
    public Serializable saveSupplierResponse(FlightOrderRowCancellationAPIResponse forc);
}
