package com.vetris.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity for UserRoles
 * @author Jose
 *
 */

@Getter
@Setter
@Entity
@Table(name="user_roles")
@SQLDelete(sql = "UPDATE user_roles SET is_active = 'N' WHERE id=?")
@Where(clause = "is_active='Y'")
public class UserRoles extends AuditEntityModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", length=200)
	private Integer id;
	
	@Column(name="code", length=10,nullable = false, unique=true)
	private String code;
	
	@Column(name="name", length=30, nullable=false)
	private String name;
	
	@Column(name="is_visible", length=1,nullable = true)
	private String isVisible;
	
	@Column(name="is_active", length=1,nullable = true)
	private String isActive;
	
	@Column(name="sys_defined", length=1,nullable = true)
	private String sysDefined;
}
