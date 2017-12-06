/**
 * 
 */
package com.tayyarah.flight.cache.dao;

import java.util.List;

import com.tayyarah.flight.cache.entity.FlightSearchCacheDestination;


/**
 * @author MANISH
 *
 */
public interface FlightSearchCacheDao {
	public List<FlightSearchCacheDestination> fetchAllSearchCacheList();

}
