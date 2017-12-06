package com.tayyarah.api.insurance.trawelltag.model;

public class Traveldetails {

	private String arrivaldate;

    private String days;

    private String departuredate;

    public String getArrivaldate ()
    {
        return arrivaldate;
    }

    public void setArrivaldate (String arrivaldate)
    {
        this.arrivaldate = arrivaldate;
    }

    public String getDays ()
    {
        return days;
    }

    public void setDays (String days)
    {
        this.days = days;
    }

    public String getDeparturedate ()
    {
        return departuredate;
    }

    public void setDeparturedate (String departuredate)
    {
        this.departuredate = departuredate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [arrivaldate = "+arrivaldate+", days = "+days+", departuredate = "+departuredate+"]";
    }
}
