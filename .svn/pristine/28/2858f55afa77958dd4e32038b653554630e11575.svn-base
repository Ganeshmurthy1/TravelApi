package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the hotelinandaround database table.
 * 
 */
@Entity
@Table(name="hotelinandaround")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/*@NamedQuery(name="Hotelinandaround.findAll", query="SELECT h FROM Hotelinandaround h")*/
public class Hotelinandaround implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HotelinandaroundPK pid;

	private Integer id;
	//bi-directional many-to-one association to Hoteloverview
		@ManyToOne
		@JoinColumn(name="VendorID")
		private HotelOverview hoteloverview;

	public HotelOverview getHoteloverview() {
			return hoteloverview;
		}

		public void setHoteloverview(HotelOverview hoteloverview) {
			this.hoteloverview = hoteloverview;
		}

	public Hotelinandaround() {
	}

	public HotelinandaroundPK getpid() {
		return this.pid;
	}

	public void setId(HotelinandaroundPK pid) {
		this.pid = pid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}