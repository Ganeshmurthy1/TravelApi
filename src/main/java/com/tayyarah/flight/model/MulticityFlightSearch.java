package com.tayyarah.flight.model;

import java.io.Serializable;

public class MulticityFlightSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dest1;
	private String dest2;
	private String dest3;
	private String dest4;
	private String origin1;
	private String origin2;
	private String origin3;
	private String origin4;
	private String departDate1;
	private String departDate2;
	private String departDate3;
	private String departDate4;

	@Override
	public String toString() {
		return "MulticityFlightSearch [dest1=" + dest1 + ", dest2=" + dest2
				+ ", dest3=" + dest3 + ", dest4=" + dest4 + ", origin1="
				+ origin1 + ", origin2=" + origin2 + ", origin3=" + origin3
				+ ", origin4=" + origin4 + ", departDate1=" + departDate1
				+ ", departDate2=" + departDate2 + ", departDate3="
				+ departDate3 + ", departDate4=" + departDate4 + "]";
	}

	public String getDest1() {
		return dest1;
	}

	public void setDest1(String dest1) {
		this.dest1 = dest1;
	}

	public String getDest2() {
		return dest2;
	}

	public void setDest2(String dest2) {
		this.dest2 = dest2;
	}

	public String getDest3() {
		return dest3;
	}

	public void setDest3(String dest3) {
		this.dest3 = dest3;
	}

	public String getDest4() {
		return dest4;
	}

	public void setDest4(String dest4) {
		this.dest4 = dest4;
	}

	public String getOrigin1() {
		return origin1;
	}

	public void setOrigin1(String origin1) {
		this.origin1 = origin1;
	}

	public String getOrigin2() {
		return origin2;
	}

	public void setOrigin2(String origin2) {
		this.origin2 = origin2;
	}

	public String getOrigin3() {
		return origin3;
	}

	public void setOrigin3(String origin3) {
		this.origin3 = origin3;
	}

	public String getOrigin4() {
		return origin4;
	}

	public void setOrigin4(String origin4) {
		this.origin4 = origin4;
	}

	public String getDepartDate1() {
		return departDate1;
	}

	public void setDepartDate1(String departDate1) {
		this.departDate1 = departDate1;
	}

	public String getDepartDate2() {
		return departDate2;
	}

	public void setDepartDate2(String departDate2) {
		this.departDate2 = departDate2;
	}

	public String getDepartDate3() {
		return departDate3;
	}

	public void setDepartDate3(String departDate3) {
		this.departDate3 = departDate3;
	}

	public String getDepartDate4() {
		return departDate4;
	}

	public void setDepartDate4(String departDate4) {
		this.departDate4 = departDate4;
	}

}
