package com.tayyarah.services;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
public class SchedulerService {
	public static final Logger logger = Logger.getLogger(SchedulerService.class);

	/*@Scheduled(fixedRate = 5 * 1000, initialDelay = 50 * 1000)
	public void doStartEmailsSending() {

		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getApi_url() != null && !commonConfig.getApi_url().equals("")) {
					URL url = new URL(commonConfig.getEmail_service_url());
					// URL url = new
					// URL("http://localhost:9080/LintasTravelAPI/Email/sendMails");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						logger.info("Failed : HTTP error code : " + conn.getResponseCode());
						throw new Exception("Email service connection Exception :: " + conn.getResponseCode());
						// throw new RuntimeException("Failed : HTTP error code
						// : "
						// + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;

					while ((output = br.readLine()) != null) {
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				} else {
					logger.error(new Exception("Error :: Common Config Api URL is not found..."));
				}
			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		}
	}*/

	// @Scheduled(cron="*/5 * * * * ?")
	// @Scheduled(fixedRate=60*60*1000, initialDelay=10*60*1000)
	// @Scheduled(fixedRate=2*1000, initialDelay=5*1000)
	/*@Scheduled(fixedRate = 30 * 1000, initialDelay = 60 * 1000)
	public void doStartEmailNotificationsSending() {

		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getApi_url() != null && !commonConfig.getApi_url().equals("")) {
					URL url = new URL(commonConfig.getApi_url() + "EmailNotification/sendMails");
					// URL url = new
					// URL("http://localhost:9080/LintasTravelAPI/Email/sendMails");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					// logger.info("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				} else {
					logger.error(new Exception("Error :: Common Config Api URL is not found..."));
				}
			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}
		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
			// e.printStackTrace();
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			// e.printStackTrace();
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			// e.printStackTrace();
		}
	}*/

	/*@Scheduled(fixedRate = 30 * 1000, initialDelay = 60 * 1000)
	public void doStartPaymentNotificationsSending() {
		//logger.info("doStartPaymentNotificationsSending started. Current time is :: " + new Date());
		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getEmail_service_Flight_pending_payment_url() != null
						&& !commonConfig.getEmail_service_Flight_pending_payment_url().equals("")) {
					URL url = new URL(commonConfig.getEmail_service_Flight_pending_payment_url());
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					// logger.info("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				} else {
					logger.error(new Exception(
							"Error :: Common Config getEmail_service_Flight_pending_payment_url is not found..."));
				}

			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		}
	}*/

	/*@Scheduled(fixedRate = 30 * 1000, initialDelay = 60 * 1000)
	public void doStartHotelPaymentNotificationsSending() {
		//logger.info("getEmail_service_Hotel_pending_payment_url started. Current time is :: " + new Date());
		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getEmail_service_Hotel_pending_payment_url() != null
						&& !commonConfig.getEmail_service_Hotel_pending_payment_url().equals("")) {
					URL url = new URL(commonConfig.getEmail_service_Hotel_pending_payment_url());
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					// logger.info("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				} else {
					logger.error(new Exception(
							"Error :: Common Config getEmail_service_Hotel_pending_payment_url is not found..."));
				}

			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		}
	}
*/
	/*@Scheduled(fixedRate = 30 * 1000, initialDelay = 86400 * 1000)
	public void doCityLoading() {
		//logger.info("doCityLoading started. Current time is :: " + new Date());
		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getApi_url() != null && !commonConfig.getApi_url().equals("")) {
					URL url = new URL(commonConfig.getApi_url() + "cities/preload");
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					// logger.info("Output from Server .... \n");
					// System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// System.out.println(output);
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				} else {
					logger.error(new Exception("Error :: Common Config getApi_url is not found..."));
				}

			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		}
	}*/

	// AutoReleaseHoldBookingTicket
	// cron="0 0 1 * * *" means every day 1:00 am this task will be called
	/*@Scheduled(cron = "0 0 1 * * *")
	public void doAutoReleaseHoldBookingTicketEmailNotification() {
		//	logger.info("doAutoReleaseHoldBookingTicket started. Current time is :: " + new Date());

		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getEmail_service_Flight_Autorelease_holdbooking_url() != null
						&& !commonConfig.getEmail_service_Flight_Autorelease_holdbooking_url().equals("")) {

					URL url = new URL(
							CommonConfig.GetCommonConfig().getEmail_service_Flight_Autorelease_holdbooking_url());
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					// logger.info("Output from Server .... \n");
					// System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// System.out.println(output);
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				} else {
					logger.error(new Exception("Error :: Common Config getApi_url is not found..."));
				}

			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		}

	}*/

	// HoldFlightBookingEmailNotification
	// cron="0 0 1 * * *" means every day 1:00 am this task will be called
	/*@Scheduled(cron = "0 0 1 * * *")
	public void doHoldFlightBookingEmailNotification() {
		//logger.info("doHoldFlightBookingEmailNotification started. Current time is :: " + new Date());

		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getEmail_service_Flight_Sendholdticket_url() != null
						&& !commonConfig.getEmail_service_Flight_Sendholdticket_url().equals("")) {

					URL url = new URL(CommonConfig.GetCommonConfig().getEmail_service_Flight_Sendholdticket_url());
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					// logger.info("Output from Server .... \n");
					// System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// System.out.println(output);
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				} else {
					logger.error(new Exception("Error :: Common Config getApi_url is not found..."));
				}

			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		}

	}*/

	// Auto send pending bugs mails every morning 9:00 am
	// cron="0 0 9* * *" means every day 9:00 am this task will be called

	// @Scheduled(cron="0 1 1 * * *") means every day 1:01 am this task will be
	// called
	// format is seconds,minute,hours from the right
	// cron="0 0/30 8-10 * * *"
	/*@Scheduled(cron = "0 0 9 * * *")
	public void doAutoSendPendingBugsToEmail() {

		//logger.info("doAutoSendPendingBugsToEmail started. Current time is :: " + new Date());
		HttpURLConnection conn = null;
		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getUpdate_cache_in_table() != null
						&& !commonConfig.getUpdate_cache_in_table().equals("")) {

					URL url = new URL(CommonConfig.GetCommonConfig().getUpdate_cache_in_table());
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					// logger.info("Output from Server .... \n");
					// System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// System.out.println(output);
						// logger.info("Output from Server .... --"+output);
					}
				} else {
					logger.error(new Exception("Error :: Common Config getApi_url is not found..."));
					// System.out.println(new Exception("Error :: Common Config
					// getApi_url is not found..."));
				}
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);

		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}
*/
	/*
	 * //scheduler for updating notification table,if notication is not deleted
	 * then current_notification_view status is true.then go inside
	 * 
	 * @Scheduled(cron="0 12 12 * * *") public synchronized void
	 * sendAllNotificationForToday(){
	 * System.out.println("calling doUpdateNotificationTable");
	 * //logger.info("updating city started. Current time is :: "+ new Date());
	 * //System.out.println("notification table updated");
	 * 
	 * try{ List<Notification>
	 * notifications=notificationDAO.getAllNotificationToBeSentToday();
	 * System.out.println(notifications.size()); //current_notification_view is
	 * true then that notification is active otherwise inactive //list of
	 * notification whose state is not expired List<NotificationDetail>
	 * unexpiredNotificationDetails=null; for (Notification notification :
	 * notifications) { if(notification.getCurrentNotificationView()==true){
	 * unexpiredNotificationDetails=notification.getDetails(); for
	 * (NotificationDetail notificationDetail : unexpiredNotificationDetails) {
	 * 
	 * 
	 * }
	 * 
	 * } } } catch (Exception e) { // TODO: handle exception }
	 * 
	 * 
	 * }
	 * 
	 * 
	 */

	// run the schdeuler in every one hour created for sending all notification
	// for today

	// @Scheduled(fixedRate=3600*1000, initialDelay=60*1000)
	/*	@Scheduled(cron = "0 0/1 * * * *")
	public void allMailNotificationForToday() {


		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getSend_all_email_notification() != null
						&& !commonConfig.getSend_all_email_notification().equals("")) {
					URL url = new URL(commonConfig.getSend_all_email_notification());
					// http://localhost:8080/LintasTravelAPI/Email/notificationEmail/
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					// logger.info("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				} else {
					logger.error(new Exception("Error :: Common Config getApi_url is not found..."));
				}

			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		}

	}*/

	/*@Scheduled(cron = "0 32 21 * * *")
	public void sendAllHotelFlightCustomerPartialPaymentEmail() {
		//logger.info("getAllHotelFlightCustomerPartialPayment started. Current time is :: " + new Date());

		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getAllHotelFlightCustomerPartialPayment() != null
						&& !commonConfig.getAllHotelFlightCustomerPartialPayment().equals("")) {
					URL url = new URL(commonConfig.getAllHotelFlightCustomerPartialPayment());
					// URL url = new
					// URL("http://localhost:8080/LintasTravelAPI/Email/getAllHotelFlightCustomerPayment");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;

					while ((output = br.readLine()) != null) {
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				} else {
					logger.error(new Exception(
							"Error :: Common Config getEmail_service_Hotel_pending_payment_url is not found..."));
				}

			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		}
	}
*/
	// Created by harsha---------- for sending mail to corporate companies one
	// day before based on corporate agreement expiry date
	// cron="0 1 12 * * *" means every day 12:00 pm this task will be called
	//@Scheduled(cron = "0 1 12 * * *")
	/*@Scheduled(cron = "0 0 12 * * *")
	public void doAutoSendCorporateExpiryToEmail() {
		///logger.info("doAutoSendCorporateExpiryToEmail started. Current time is :: " + new Date());
		HttpURLConnection conn = null;
		try {

			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getCorporateAgreementExpiryEmailUrl() != null
						&& !commonConfig.getCorporateAgreementExpiryEmailUrl().equals("")) {

					URL url = new URL(CommonConfig.GetCommonConfig().getCorporateAgreementExpiryEmailUrl());
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;

					// System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// System.out.println(output);
						logger.info("Output from Server .... --" + output);
					}
				} else {
					logger.error(new Exception("Error :: Common Config getApi_url is not found..."));
				}
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);

		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}
*/
	/*
	 * //scheduler for updating hotels in the database after a particular
	 * instance of time //uncomment when updating cache
	 * 
	 * @Scheduled(cron="0 0 1 * * *") public synchronized void
	 * doUpdateHotelsForParticularCity() {
	 * logger.info("updating city started. Current time is :: "+ new Date());
	 * System.out.println("corn schdeuler is called"); try { CommonConfig
	 * commonConfig = CommonConfig.GetCommonConfig(); if(commonConfig != null )
	 * { if(commonConfig.getApi_url()!= null &&
	 * !commonConfig.getUpdate_cache_in_table().equals("")) { URL url = new
	 * URL(commonConfig.getUpdate_cache_in_table()); HttpURLConnection conn =
	 * (HttpURLConnection) url.openConnection(); conn.setDoOutput(true);
	 * conn.setRequestMethod("GET"); conn.setRequestProperty("Access",
	 * "application/json"); if (conn.getResponseCode() != 200) { throw new
	 * RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
	 * } BufferedReader br = new BufferedReader(new InputStreamReader(
	 * (conn.getInputStream()))); String output;
	 * logger.info("Output from Server .... \n"); while ((output =
	 * br.readLine()) != null) { //System.out.println(output);
	 * logger.info("Output from Server .... --"+output); } conn.disconnect(); }
	 * else { logger.error(new
	 * Exception("Error :: Common Config getApi_url is not found...")); }
	 * 
	 * } else { logger.error(new
	 * Exception("Error :: Common Config is not loaded...")); }
	 * 
	 * } catch (MalformedURLException e) {
	 * logger.info("--MalformedURLException"+e.getMessage()); logger.error(e); }
	 * catch (IOException e) { logger.info("--IOException"+e.getMessage());
	 * logger.error(e); } catch (Exception e) {
	 * logger.info("--IOException"+e.getMessage()); logger.error(e); } }
	 */


//COMMENTED BY BASHA
	//created by saumya to check Flight cancellation after a certain period of time
	/*@Scheduled(cron = "0 0/3 * * * *")
	public void doCheckFlightCancellationStatus() {

		//logger.info("checking hotel cancellation happened or not " + new Date());

		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getApi_url() != null && !commonConfig.getApi_url().equals("")) {
					URL url = new URL(commonConfig.getApi_url() + "cancelticket/periodicCheck");					
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
						logger.info("doCheckFlightCancellationStatus---------------"+conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					// logger.info("Output from Server .... \n");
					// System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						// System.out.println(output);
						// logger.info("Output from Server .... --"+output);
					}
					conn.disconnect();
				}
			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		}

	}*/


	



	//created by harsha to send Flight departure mail before  4 hours
	/*@Scheduled(cron = "0 0/1 * * * *")
	public void doCheckFlightMailBeforeDeparture() {
		///logger.info("doAutoSendCorporateExpiryToEmail started. Current time is :: " + new Date());
		HttpURLConnection conn = null;
		try {

			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getSendFlightMailBeforeDeparture() != null
						&& !commonConfig.getSendFlightMailBeforeDeparture().equals("")) {

					URL url = new URL(CommonConfig.GetCommonConfig().getSendFlightMailBeforeDeparture());
					//System.out.println("url----------4  hours  "+url);
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;				
					while ((output = br.readLine()) != null) {					
						//logger.info("Output from Server .... --" + output);
					}
				} else {
					logger.error(new Exception("Error :: Common Config getApi_url is not found..."));
				}
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);

		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}*/




	//created by harsha to send Flight departure mail before 24 hours
	/*@Scheduled(cron = "0 0/1 * * * *")
	public void doCheckFlightMailBeforeOneDay() {
		///logger.info("doAutoSendCorporateExpiryToEmail started. Current time is :: " + new Date());
		HttpURLConnection conn = null;
		try {

			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getSendFlightMailBeforeOneDay() != null
						&& !commonConfig.getSendFlightMailBeforeOneDay().equals("")) {

					URL url = new URL(CommonConfig.GetCommonConfig().getSendFlightMailBeforeOneDay());
					//System.out.println("url----------24  hours  "+url);
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;					
					while ((output = br.readLine()) != null) {
						// System.out.println(output);
						//logger.info("Output from Server .... --" + output);
					}
				} else {
					logger.error(new Exception("Error :: Common Config getApi_url is not found..."));
				}
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);

		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}
	*/
	/*@Scheduled(cron = "0 10 16 ? * FRI")
	public void resetIpStatusForB2C() {
		try {
			CommonConfig commonConfig = CommonConfig.GetCommonConfig();
			if (commonConfig != null) {
				if (commonConfig.getReset_all_b2c_ip() != null
						&& !commonConfig.getReset_all_b2c_ip().equals("")) {
					URL url = new URL(commonConfig.getReset_all_b2c_ip());
					// URL url = new
					// URL("http://localhost:8282/LintasTravelAPI/Email/sendMails");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Access", "application/json");
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					}
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					String output;
					while ((output = br.readLine()) != null) {
					}
					conn.disconnect();
				} else {
					logger.error(new Exception(
							"Error :: Common Config getReset_all_B2C_ip is not found..."));
				}

			} else {
				logger.error(new Exception("Error :: Common Config is not loaded..."));
			}

		} catch (MalformedURLException e) {
			logger.info("--MalformedURLException" + e.getMessage());
			logger.error(e);
		} catch (IOException e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		} catch (Exception e) {
			logger.info("--IOException" + e.getMessage());
			logger.error(e);
		}
	}
*/


}
