/**
 * 
 */
package com.tayyarah.flight.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.flight.entity.FlightOrderRow;


/**
 * @author info : Manish Samrat
 * @createdAt : 30/05/2017
 * @version : 1.0
 */
public class FlightOrderDaoImpl implements FlightOrderDao{

	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	Logger logger = Logger.getLogger(FlightOrderDaoImpl.class);
	
	@Override
	public List<FlightOrderRow> getFlightBookingList(List<String> companyIdList,Integer pageNo,Integer pageSize) {
		
		List<FlightOrderRow> flightOrderRowList=new ArrayList<FlightOrderRow>();
		try{
			session=sessionFactory.openSession();
			String hql="select FlightOrderRow for ";
			Criteria criteria=session.createCriteria(FlightOrderRow.class);
			criteria.add(Restrictions.in("companyId", companyIdList));
			
			/*criteria.setProjection(Projections.projectionList()
					.add(Projections.property("id"), "id")
					.add(Projections.property("origin"), "origin")
					.add(Projections.property("destination"), "destination")
					.add(Projections.property("paymentStatus"), "paymentStatus")
					.add(Projections.property("statusAction"), "statusAction")	
					.add(Projections.property("orderId"), "orderId")
					.add(Projections.property("pnr"), "pnr")
					.add(Projections.property("finalPrice"), "finalPrice")
					.add(Projections.property("bookingDate"), "bookingDate")
					.add(Projections.property("booking_currency"), "booking_currency")
					.add(Projections.property("flightOrderTripDetails"), "flightOrderTripDetails")
//					.add(Projections.property("flightOrder.flightOrderTripDetails"), "flightOrderTripDetails")
					)
					.setResultTransformer(Transformers.aliasToBean(FlightOrderRow.class));	*/
			
			criteria.addOrder(Order.desc("bookingDate"));
			criteria.setFirstResult((pageNo-1)*pageSize + 1);
			criteria.setMaxResults(pageSize);
			
			flightOrderRowList= (List<FlightOrderRow>)criteria.list();
		}catch (Exception e) {
		}
		finally {
			session.close();
		}
		return flightOrderRowList;
	}

	@Override
	public FlightOrderRow getFlightBookingDetail(Long flightId) {
		
		
		FlightOrderRow flightOrderRow=new FlightOrderRow();
		
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FlightOrderRow.class);
			criteria.add(Restrictions.eq("id", flightId));
			flightOrderRow=(FlightOrderRow) criteria.uniqueResult();
		}catch (Exception e) {
		}
		finally {
			session.close();
		}
		return flightOrderRow;
	}


/*@Override
public List<FlightOrderTripDetail> getFlightTriDetails(Long flightOrderId) {
	
	
	List<FlightOrderTripDetail> flightOrderTripDetailsList=new ArrayList<FlightOrderTripDetail>();
	
	try{
		session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(FlightOrderTripDetail.class);
		criteria.add(Restrictions.eq("order_row_id", flightOrderId));
		flightOrderTripDetailsList= criteria.list();
	}catch (Exception e) {
	}
	finally {
		session.close();
	}
	return flightOrderTripDetailsList;
}*/
}
