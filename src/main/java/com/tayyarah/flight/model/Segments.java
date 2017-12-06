package com.tayyarah.flight.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Segments implements Serializable
{

	private static final long serialVersionUID = 1L;
	private int tripNo;
	private int stops;
	private int group;
	private Cabin cabin;
	private String fareInfoRef;
	private Flight flight;
	private String duration;
	private String dest;
	private String depart;
	private String arrival;
	private String depDate;
	private String arrDate;
	private String depTime;
	private String arrTime;
	private Carrier carrier;
	private String ori;
	private String oriName;
	private String destName;
	private String trackno;
	private String oriAirportName;
	private String destAirportName;
	private String craft;
	private String fareClass;

	public int getTripNo() {
		return tripNo;
	}

	public void setTripNo(int tripNo) {
		this.tripNo = tripNo;
	}
	public String getFareClass() {
		return fareClass;
	}

	public void setFareClass(String fareClass) {
		this.fareClass = fareClass;
	}

	public String getCraft() {
		return craft;
	}
	public void setCraft(String craft) {
		this.craft = craft;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getFareInfoRef() {
		return fareInfoRef;
	}

	public void setFareInfoRef(String fareInfoRef) {
		this.fareInfoRef = fareInfoRef;
	}

	public int getStops() {
		return stops;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}

	public String getTrackno() {
		return trackno;
	}

	public void setTrackno(String trackno) {
		this.trackno = trackno;
	}



	public String getOriAirportName() {
		return oriAirportName;
	}

	public void setOriAirportName(String oriAirportName) {
		this.oriAirportName = oriAirportName;
	}

	public String getDestAirportName() {
		return destAirportName;
	}

	public void setDestAirportName(String destAirportName) {
		this.destAirportName = destAirportName;
	}

	public String getOriName() {
		return oriName;
	}

	public void setOriName(String oriName) {
		this.oriName = oriName;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}



	public Cabin getCabin ()
	{
		return cabin;
	}

	public void setCabin (Cabin cabin)
	{
		this.cabin = cabin;
	}

	public Flight getFlight ()
	{
		return flight;
	}

	public void setFlight (Flight flight)
	{
		this.flight = flight;
	}

	public String getDuration ()
	{
		return duration;
	}

	public void setDuration (String duration)
	{
		this.duration = duration;
	}

	public String getDest ()
	{
		return dest;
	}

	public void setDest (String dest)
	{
		this.dest = dest;
	}

	public String getDepart ()
	{
		return depart;
	}

	public void setDepart (String depart)
	{
		this.depart = depart;
	}

	public Carrier getCarrier ()
	{
		return carrier;
	}

	public void setCarrier (Carrier carrier)
	{
		this.carrier = carrier;
	}

	public String getArrival ()
	{
		return arrival;
	}

	public void setArrival (String arrival)
	{
		this.arrival = arrival;
	}

	public String getOri ()
	{
		return ori;
	}

	public void setOri (String ori)
	{
		this.ori = ori;
	}

	/**
	 * @return the depDate
	 */
	public String getDepDate() {
		return depDate;
	}

	/**
	 * @param depDate the depDate to set
	 */
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	/**
	 * @return the arrDate
	 */
	public String getArrDate() {
		return arrDate;
	}

	/**
	 * @param arrDate the arrDate to set
	 */
	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}

	/**
	 * @return the depTime
	 */
	public String getDepTime() {
		return depTime;
	}

	/**
	 * @param depTime the depTime to set
	 */
	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}

	/**
	 * @return the arrTime
	 */
	public String getArrTime() {
		return arrTime;
	}

	/**
	 * @param arrTime the arrTime to set
	 */
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}

	@Override
	public String toString() {
		return "Segments [tripNo=" + tripNo + ",stops=" + stops + ", group=" + group
				+ ", fareInfoRef=" + fareInfoRef + ", cabin=" + cabin
				+ ", flight=" + flight + ", duration=" + duration + ", dest="
				+ dest + ", depart=" + depart + ", carrier=" + carrier
				+ ", arrival=" + arrival + ", ori=" + ori + ", oriName="
				+ oriName + ", destName=" + destName + ", oriAirportName="
				+ oriAirportName + ", destAirportName=" + destAirportName + "]";
	}




	/* @Override
    public String toString()
    {
        return "ClassPojo [cabin = "+cabin+", flight = "+flight+", duration = "+duration+", dest = "+dest+", depart = "+depart+", carrier = "+carrier+", arrival = "+arrival+", ori = "+ori+"]";
    }*/
}


