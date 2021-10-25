package com.vetris.security.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Donepudi Suresh
 *
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString
public abstract class AuditEntityModel {
	
	@Column(name="created_by", length=200,nullable = false, updatable=false)
	private String createdBy;
	
	@Column(name="date_created",nullable = false, updatable=false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@Column(name="update_by", length=200,nullable = true)
	private String updateBy;
	
	@Column(name="date_updated",nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date dateUpdated;

}

