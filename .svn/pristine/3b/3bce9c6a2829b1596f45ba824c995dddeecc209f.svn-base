package com.tayyarah.hotel.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the islhotelmapping database table.
 * 
 */
@Entity
@Table(name = "islhotelmapping")

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/*@NamedQuery(name="Islhotelmapping.findAll", query="SELECT i FROM Islhotelmapping i")*/
public class Islhotelmapping implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ISLVendorID")
	private String ISLVendorID;
	@Column(name="LintasVendorID")
	private String lintasVendorID;
	@Column(name="ReznextVendorID")
	private long reznextVendorID;
	@Column(name="TGVendorID")
	private String TGVendorID;

	public Islhotelmapping() {
	}

	public String getISLVendorID() {
		return this.ISLVendorID;
	}

	public void setISLVendorID(String ISLVendorID) {
		this.ISLVendorID = ISLVendorID;
	}

	public String getLintasVendorID() {
		return this.lintasVendorID;
	}

	public void setLintasVendorID(String lintasVendorID) {
		this.lintasVendorID = lintasVendorID;
	}

	public long getReznextVendorID() {
		return this.reznextVendorID;
	}

	public void setReznextVendorID(long reznextVendorID) {
		this.reznextVendorID = reznextVendorID;
	}

	public String getTGVendorID() {
		return this.TGVendorID;
	}

	public void setTGVendorID(String TGVendorID) {
		this.TGVendorID = TGVendorID;
	}
	@Override
	public String toString() {
		return "Islhotelmapping [ISLVendorID=" + ISLVendorID
				+ ", lintasVendorID=" + lintasVendorID + ", reznextVendorID="
				+ reznextVendorID + ", TGVendorID=" + TGVendorID + "]";
	}
}