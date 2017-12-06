package com.tayyarah.common.util.enums;

public enum TravelRequestStatusEnum {
	 DEFAULT(0,"Default"),CREATED(1,"Created"),QSENT(2,"QuotationSent"),APPROVED(3,"Approved"),SHORTLISTED(4,"ShortListed"),BOOKED(5,"Booked"),CANCELLED(6,"Cancelled") ;
	 private int value; 
	 private String status; 
	 TravelRequestStatusEnum(int id, String status) {
	  this.value = id;
	  this.setStatus(status);

	 }
	 public static String getTravelRequestStatusEnumStatus(int statusId) {
	  String defaultStatus = "";
	   
	  for (TravelRequestStatusEnum bd : TravelRequestStatusEnum.values())
	  {
	   if(bd.getValue() == statusId)
	   {
	    defaultStatus=bd.getStatus();
	    break;
	   }  
	  }

	  return defaultStatus; 

	 }
	 
	 public static int getTravelRequestStatusEnumStatusId(String status) {
	  int defaultStatusid = 0 ;
	   
	  for (TravelRequestStatusEnum bd : TravelRequestStatusEnum.values())
	  {
	   if(bd.getStatus().equalsIgnoreCase(status))
	   {
	    defaultStatusid =bd.getValue();
	    break;
	   }  
	  }

	  return defaultStatusid; 

	 }

	 public int getValue() {
	  return value;
	 }
	 public void setValue(int value) {
	  this.value = value;
	 }
	 public String getStatus() {
	  return status;
	 }
	 public void setStatus(String status) {
	  this.status = status;
	 }


	}