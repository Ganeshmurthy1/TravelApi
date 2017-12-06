package com.tayyarah.email.dao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.Enquiry;
import com.tayyarah.common.entity.StateInfo;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.company.entity.CompanyEntity;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.umrah.entity.TayyarahUmrahContactDetails;
import com.tayyarah.user.entity.FrontUserDetail;
import com.tayyarah.user.entity.User;


public class AllEmailDaoImp implements AllEmailDao,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AllEmailDaoImp.class);
	@Autowired
	private SessionFactory  sessionFactory;

	@Override
	public Company getCompanyByUserId(String userId) throws HibernateException {
		Session sess = null;
		Company companyDb = null;
		try{
			Integer userid = Integer.valueOf(userId);
			sess = sessionFactory.openSession();
			Criteria crit1 = sess.createCriteria(User.class);			
			crit1.add(Restrictions.eq("id", userid));
			User userDb = (User) crit1.uniqueResult();		
			if(userDb != null)
			{
				Criteria crit2 = sess.createCriteria(Company.class);			
				crit2.add(Restrictions.eq("companyid", userDb.getCompanyid()));			
				companyDb = (Company) crit2.uniqueResult();	
			}			
		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return companyDb;
	}

	@Override
	public Company getCompanyByCompanyId(String companyId) throws HibernateException {
		Session sess = null;
		Company companyDb = null;
		try{	
			Integer company_id = Integer.valueOf(companyId);
			sess = sessionFactory.openSession();
			Criteria crit2 = sess.createCriteria(Company.class);			
			crit2.add(Restrictions.eq("companyid", company_id));			
			companyDb = (Company) crit2.uniqueResult();	

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return companyDb;
	}	

	@Override
	public Company getSuperCompanyByCompanyUserId(String superCompanyUserId) throws HibernateException {
		Session sess = null;
		Company companyDb = null;
		try{	
			sess = sessionFactory.openSession();
			Criteria crit2 = sess.createCriteria(Company.class);			
			crit2.add(Restrictions.eq("company_userid", superCompanyUserId));
			companyDb = (Company) crit2.uniqueResult();	

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return companyDb;
	}


	@Override
	public CompanyEntity getCompanyEntityByCompanyId(Long companyEntityId) throws HibernateException {
		Session sess = null;
		CompanyEntity companyEntity = null;
		try{	
			sess = sessionFactory.openSession();
			Criteria crit2 = sess.createCriteria(CompanyEntity.class);			
			crit2.add(Restrictions.eq("companyEntityId", companyEntityId.intValue()));			
			companyEntity = (CompanyEntity) crit2.uniqueResult();	

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		}
		return companyEntity;
	}

	@Override
	public Company getCompanyByCompanyId(int companyId) throws HibernateException {
		Session sess = null;
		Company companyDb = null;
		try{	
			sess = sessionFactory.openSession();
			Criteria crit2 = sess.createCriteria(Company.class);			
			crit2.add(Restrictions.eq("companyid", companyId));			
			companyDb = (Company) crit2.uniqueResult();	

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return companyDb;
	}

	@Override
	public User getUserByUserId(String userId) throws HibernateException {
		Session sess = null;
		User userDb = null;
		try{
			Integer userid = Integer.valueOf(userId);
			sess = sessionFactory.openSession();
			Criteria crit1 = sess.createCriteria(User.class);			
			crit1.add(Restrictions.eq("id", userid));
			userDb = (User) crit1.uniqueResult();	

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return userDb;
	}

	@Override
	public User getUserByEmail(String email) throws HibernateException {
		Session sess = null;
		User userDb = null;
		try{			
			sess = sessionFactory.openSession();
			Criteria crit1 = sess.createCriteria(User.class);			
			crit1.add(Restrictions.eq("Email", email));
			userDb = (User) crit1.uniqueResult();	

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return userDb;
	}

	@Override
	public FrontUserDetail getFrontUserDetailByUserId(String userId) throws HibernateException {
		Session sess = null;
		FrontUserDetail frontUserDetailDb = null;
		try{
			Long userid = Long.valueOf(userId);
			sess = sessionFactory.openSession();
			Criteria crit1 = sess.createCriteria(FrontUserDetail.class);			
			crit1.add(Restrictions.eq("id", userid));
			frontUserDetailDb = (FrontUserDetail) crit1.uniqueResult();	

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return frontUserDetailDb;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CompanyConfig getCompanyConfigByUserId(String userId) throws HibernateException {
		Session sess = null;
		CompanyConfig companyConfigDb = null;
		try{
			Integer userid = Integer.valueOf(userId);
			sess = sessionFactory.openSession();
			Criteria crit1 = sess.createCriteria(User.class);	
			List<User> userList =  (List<User>)crit1.list();
			if(userList != null && userList.size() > 0){

				crit1.add(Restrictions.eq("id", userid));
				User userDb = (User) crit1.uniqueResult();		

				Criteria crit2 = sess.createCriteria(CompanyConfig.class);			
				if(userDb!=null)
					crit2.add(Restrictions.eq("company_id", userDb.getCompanyid()));			
				List<CompanyConfig> companyConfiglist =  crit2.list();
				for (int i = 0; i < companyConfiglist.size(); i++) {
					CompanyConfig companyConfig = companyConfiglist.get(i);
					if(companyConfig.getConfig_id() != 1){
						companyConfigDb = companyConfig;
					}
				}
			}
		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return companyConfigDb;
	}

	@Override
	public  List<User> getPostPaidWalletUser() throws HibernateException {
		Session sess = null;
		List<User> userDb = null;
		List<User> newPostPaidUsers = new ArrayList<User>();
		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(User.class);	
			userDb= criteria.list();
			if(userDb!=null && userDb.size()>0)
			{
				for(User user: userDb)
				{
					if(user.getAgentWallet().getWalletType().equalsIgnoreCase("Postpaid")&& user.getAgentWallet().getWalletbalance().compareTo(new BigDecimal("0.00"))==-1 ) {
						newPostPaidUsers.add(user);
					}
				}
			}
		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return newPostPaidUsers;
	}

	@Override
	public List<FlightOrderRow>  getLastBookingDateForFlight(int userid)
			throws HibernateException {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = cal.getTime();		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");		
		String format = formatter.format(tomorrow);
		format = format+"T00:00:00";
		Session sess = null;
		List<FlightOrderRow> lastdateRowList = null;
		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(FlightOrderRow.class);			
			Conjunction objConjunction = Restrictions.conjunction();			
			objConjunction.add(Restrictions.eq("statusAction", "confirmed"));
			objConjunction.add(Restrictions.eq("userId", String.valueOf(userid)));
			objConjunction.add(Restrictions.like("lastTicketingDate", format));
			criteria.add(objConjunction);
			lastdateRowList = criteria.list();

		} catch (HibernateException e) {			
			System.out.println("HibernateException"+e);
		} catch (Exception e) {			
			System.out.println("Exception"+e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return lastdateRowList;
	}

	@Override
	public List<FlightOrderRow>  getLastBookingDateForHoldFlightTickets()
			throws HibernateException {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, 2); 
		Date dayaftertomorrow = cal.getTime();		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
		String format = formatter.format(dayaftertomorrow);
		format = format+"T00:00:00";
		Session sess = null;
		List<FlightOrderRow> lastdateRowList = null;
		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(FlightOrderRow.class);			
			Conjunction objConjunction = Restrictions.conjunction();		
			objConjunction.add(Restrictions.eq("statusAction", "Hold"));			
			objConjunction.add(Restrictions.like("lastTicketingDate", format));
			criteria.add(objConjunction);
			lastdateRowList = criteria.list();
			
		} catch (HibernateException e) {			
			System.out.println("HibernateException"+e);
		} catch (Exception e) {			
			System.out.println("Exception"+e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return lastdateRowList;
	}


	@Override
	public List<FlightOrderRow>  getExpiredHoldFlightTickets()
			throws HibernateException {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date yesterday = cal.getTime();		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
		String format = formatter.format(yesterday);
		format = format+"T00:00:00";
		Session sess = null;
		List<FlightOrderRow> lastdateRowList = null;
		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(FlightOrderRow.class);			
			Conjunction objConjunction = Restrictions.conjunction();		
			objConjunction.add(Restrictions.eq("statusAction", "Hold"));			
			objConjunction.add(Restrictions.like("lastTicketingDate", format));
			criteria.add(objConjunction);
			lastdateRowList = criteria.list();

		} catch (HibernateException e) {			
			System.out.println("HibernateException"+e);
		} catch (Exception e) {			
			System.out.println("Exception"+e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return lastdateRowList;
	}
	
	@Override
	public List<CompanyConfig> getWhiteLabelUserByConfigId(int companyId)
			throws HibernateException {
		Session sess = null;
		List<CompanyConfig> configList = null;
		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(CompanyConfig.class);
			criteria.add(Restrictions.eq("company_id", companyId));
			configList= criteria.list();
			if(configList!=null && configList.size()>0)
			{
				for (CompanyConfig companyConfig : configList) {
					if(companyConfig.isWhitelable())
					{						
						getWhiteLabelCompanyById(companyConfig);
					}
				}
			}
		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return configList;
	}

	@Override
	public List<Company> getWhiteLabelCompanyById(CompanyConfig configid)
			throws HibernateException {
		Session sess = null;
		List<Company> companyList = null;
		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(Company.class);
			criteria.add(Restrictions.eq("companyid", configid.getCompany_id()));
			companyList = criteria.list();
			
		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return companyList;
	}

	@Override
	public List<User> getBlockedUserByUserId(String userId) throws HibernateException {		
		Session sess = null;
		List<User> userlist = null;
		List<User> lockedUserList = new ArrayList<User>();
		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", Integer.parseInt(userId)));
			userlist= criteria.list();
			if(userlist!=null && userlist.size()>0)
			{
				for(User user :userlist)
				{					
					if(user.isLocked() ) {
						lockedUserList.add(user);						
					}
				}
			}
		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return lockedUserList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HotelOrderRow> getLastBookingDateForHotel(int userId)
			throws HibernateException {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(tomorrow);
		DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date minDate = null;
		try {
			minDate = originalFormat.parse(format);
		} catch (ParseException e1) {		
			e1.printStackTrace();
		}
		Date maxDate = new Date(minDate.getTime() + TimeUnit.DAYS.toMillis(1));
		Session sess = null;
		List<HotelOrderRow> lastdateRowList = null;
		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(HotelOrderRow.class);			
			Conjunction objConjunction = Restrictions.conjunction();			
			objConjunction.add(Restrictions.eq("statusAction", "confirmed"));
			objConjunction.add(Restrictions.eq("userId", String.valueOf(userId)));
			objConjunction.add(Restrictions.ge("checkInDate", minDate));
			objConjunction.add(Restrictions.lt("checkInDate", maxDate));
			criteria.add(objConjunction);
			lastdateRowList = criteria.list();

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return lastdateRowList;
	}

	@Override
	public Company getCompanyResetPassword(String companyId)
			throws HibernateException {
		Session sess = null;
		Company companyDb = null;
		try{	
			Integer company_Id = Integer.valueOf(companyId);
			sess = sessionFactory.openSession();
			Criteria crit2 = sess.createCriteria(Company.class);			
			crit2.add(Restrictions.eq("companyid", company_Id));			
			companyDb = (Company) crit2.uniqueResult();	

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return companyDb;		
	}

	@Override
	public User getUserResetPassword(String userId) throws HibernateException {
		Session sess = null;
		User userDb = null;
		try{
			Integer userid = Integer.valueOf(userId);
			sess = sessionFactory.openSession();
			Criteria crit1 = sess.createCriteria(User.class);			
			crit1.add(Restrictions.eq("id", userid));
			userDb = (User) crit1.uniqueResult();	
			
		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return userDb;
	}

	@Override
	public Enquiry getEnquiry(String orderId) throws HibernateException {
		Session sess = null;
		Enquiry enquiryDB = null;
		try{
			Long  id = Long.valueOf(orderId);
			sess = sessionFactory.openSession();
			Criteria crit1 = sess.createCriteria(Enquiry.class);			
			crit1.add(Restrictions.eq("id", id));
			enquiryDB = (Enquiry) crit1.uniqueResult();	

		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return enquiryDB;
	}

	@Override
	public List<Company> getAllCorporates() throws Exception {
		List<Company> list=null;
		Session session = null;
		Date tomorrowDate = new Date();
		Date yesterdayDate = new Date();
		Calendar c = Calendar.getInstance(); 
		Calendar v = Calendar.getInstance(); 
		c.setTime(tomorrowDate); 
		v.setTime(yesterdayDate); 
		c.add(Calendar.DATE, 1);
		v.add(Calendar.DATE, -1);
		tomorrowDate = c.getTime(); 
		yesterdayDate = v.getTime(); 
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(Company.class);
			Conjunction conjunction=Restrictions.conjunction();
			criteria.createCriteria("companyRole").add(Restrictions.eq("isCorporate", true));			
			conjunction.add(Restrictions.lt("agreementExpiryDate",tomorrowDate));
			conjunction.add(Restrictions.ge("agreementExpiryDate",yesterdayDate));		
			criteria.add(conjunction);
			list = criteria.list();
			
		}catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return list;
	}

	@Override
	public  List<User> getAllUserList() throws HibernateException {
		Session sess = null;
		List<User> userDb = null;

		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(User.class);		
			userDb = criteria.list(); 
			
		} catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return userDb;
	}


	@Override
	public List<FlightOrderRow> getFlightDepartureLastDay() {	
		Session session = null;		
		Date todayDate = new Date(); 
		Calendar c = Calendar.getInstance();  
		c.setTime(todayDate);  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		String format = formatter.format(todayDate); 
		List<FlightOrderRow> flightOrderRowList = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FlightOrderRow.class);
			Conjunction conjunction=Restrictions.conjunction(); 
			conjunction.add(Restrictions.eq("statusAction", "confirmed"));		
			conjunction.add(Restrictions.eq("departureDate",format)); 	
			criteria.add(conjunction);
			flightOrderRowList = criteria.list();

		}catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return flightOrderRowList;
	}

	@Override
	public List<FlightOrderRow> getFlightMailBeforeOneDay() {	
		Session session = null;	 
		Date yesterdayDate = new Date(); 
		Calendar y = Calendar.getInstance(); 
		y.setTime(yesterdayDate);
		y.add(Calendar.DATE, 1); 
		yesterdayDate = y.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		String formaty = formatter.format(yesterdayDate);		
		List<FlightOrderRow> flightOrderRowList = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FlightOrderRow.class);
			Conjunction conjunction=Restrictions.conjunction(); 
			conjunction.add(Restrictions.eq("statusAction", "confirmed")); 
			conjunction.add(Restrictions.eq("departureDate",formaty)); 	
			criteria.add(conjunction);
			flightOrderRowList = criteria.list();

		}catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return flightOrderRowList;
	}

	public  StateInfo getStateInfo(String statename){
		Session session = null;	 
		StateInfo stateInfo = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(StateInfo.class);
			criteria.add(Restrictions.eq("stateName", statename));			
			stateInfo =  (StateInfo) criteria.uniqueResult();
		}catch(Exception e){
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}
		finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return stateInfo;
	}

	public Email check24HoursEmailIsExists(String orderid){
		Session session = null;	
		Email email = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(Email.class);
			criteria.add(Restrictions.eq("type", Email.EMAIL_TYPE_FLIGHT_24HOURS_NOTIFICATION));
			criteria.add(Restrictions.eq("orderId", orderid));			
			List<Email> emaillist =  criteria.list();
			if(emaillist.size() > 0)
				email = emaillist.get(0);
			
		}catch(Exception e){
			logger.error("--------------HibernateException-----------------"+e.getMessage());
			email = new Email();
		}
		finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return email;
	}
	
	public Email check4HoursEmailIsExists(String orderid){
		Session session = null;	
		Email email = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(Email.class);
			criteria.add(Restrictions.eq("type", Email.EMAIL_TYPE_FLIGHT_4HOURS_NOTIFICATION));
			criteria.add(Restrictions.eq("orderId", orderid));			
			List<Email> emaillist =  criteria.list();
			if(emaillist.size() > 0)
				email = emaillist.get(0);
			
		}catch(Exception e){
			logger.error("--------------HibernateException-----------------"+e.getMessage());
			email = new Email();
		}
		finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return email;
	}	

	public  TayyarahUmrahContactDetails getTayyarahUmrahEnquiryDetails(String orderId){
		Session session = null;	 
		TayyarahUmrahContactDetails tayyarahUmrahContactDetails = new TayyarahUmrahContactDetails();
		try{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(TayyarahUmrahContactDetails.class);
			criteria.add(Restrictions.eq("id", Integer.parseInt(orderId)));			
			tayyarahUmrahContactDetails =  (TayyarahUmrahContactDetails) criteria.uniqueResult();
		}catch(Exception e){
			logger.error("----HibernateException------"+e.getMessage());
		}
		finally {
			if(session != null && session.isOpen())
				session.close(); 
		}
		return tayyarahUmrahContactDetails;
	}
}