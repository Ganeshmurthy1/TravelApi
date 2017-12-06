package com.tayyarah.common.util;

import java.sql.Timestamp;
import javax.persistence.*;

@MappedSuperclass
public class Timestampable {

	@Id 
	@GeneratedValue
	Long id;
	@Column(name = "created_at")
	Timestamp createdAt;
	@Column(name = "updated_at",insertable = false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL")
	Timestamp updatedAt;
	@Version
	@Column(name = "version",columnDefinition = "integer DEFAULT 0", nullable = false)
	int version;	

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Timestamp getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt)
	{
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt)
	{
		this.updatedAt = updatedAt;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

}
