package com.vetris.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import lombok.Getter;
import lombok.Setter;


/**
 * Entity for UserManagement
 * @author Anandu
 *
 */


@Getter
@Setter
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET is_active = 'N' WHERE id=?")
public class User extends AuditEntityModel{
	
	@Id
	@Column(name = "id",length=200)
	private String id;
	
	
	@Column(name = "code",length = 40,nullable=true)
	private String code;
	
	@Column(name = "name",length = 200,nullable=true)
	private String name;
	
	@Column(name = "password",length = 200,nullable=false)
	private String password;

	@Column(name = "email_id",length = 100,nullable=true)
	private String emailId;

	@Column(name = "contact_no",length = 20,nullable=true)
	private String contactNo;
	
	@Column(name = "user_role_id",nullable=false)
	private Integer userRoleId;
	
	@Column(name = "first_login",length = 1,nullable=true)
	private String firstLogin;
	
	@Column(name = "pacs_user_id",length = 50,nullable=true)
	private String pacsUserId;

	@Column(name = "pacs_password",length = 200,nullable=true)
	private String pacsPassword;

	@Column(name = "is_active",length = 1,nullable=true)
	private String isActive;
	
	@Column(name = "is_visible",length = 1,nullable=true)
	private String isVisible;

	@Column(name = "login_id",length = 50,nullable=true, unique = true)
	private String loginId;
	
	@Column(name = "notification_pref",length = 1,nullable=true)
	private String notificationPref;

	@Column(name = "allow_manual_submission",length = 1,nullable=true)
	private String allowManualSubmission;
	
	@Column(name = "allow_dashboard_view",length = 1,nullable=true)
	private String allowDashboardView;
	
	@Column(name = "enable_mfa",length = 1,nullable=true)
	private String enableMfa;
	
	@Column(name = "secret_key",length = 200,nullable=true)
	private String secretKey;
	
	@Column(name = "theme_pref",length = 10,nullable=true)
	private String themePref;
	
}
