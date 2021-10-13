package com.bourntec.vetrisSecurityServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Users extends AuditEntityModel{
	
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

	@Column(name = "pacs_password",length = 200,nullable=false)
	private String pacsPassword;

	@Column(name = "is_active",length = 1,nullable=true)
	private String isActive;
	
	@Column(name = "is_visible",length = 1,nullable=true)
	private String isVisible;

	@Column(name = "login_id",length = 50,nullable=true)
	private String loginId;
	
	@Column(name = "notification_pref",length = 1,nullable=true)
	private String notificationPref;

	@Column(name = "allow_manual_submission",length = 1,nullable=true)
	private String allowManualSubmission;
	
	@Column(name = "allow_dashboard_view",length = 1,nullable=true)
	private String allowDashboardView;
	
	@Column(name = "theme_pref",length = 10,nullable=true)
	private String themePref;
	
	@Column(name="deleted")
	private boolean deleted = Boolean.FALSE;

	//Default constructor
	public Users() {
		
	}

	/**
	 * @param id
	 * @param code
	 * @param name
	 * @param password
	 * @param emailId
	 * @param contactNo
	 * @param userRoleId
	 * @param firstLogin
	 * @param pacsUserId
	 * @param pacsPassword
	 * @param isActive
	 * @param isVisible
	 * @param loginId
	 * @param notificationPref
	 * @param allowManualSubmission
	 * @param allowDashboardView
	 * @param themePref
	 * @param deleted
	 */
	public Users(String id, String code, String name, String password, String emailId, String contactNo,
			Integer userRoleId, String firstLogin, String pacsUserId, String pacsPassword, String isActive,
			String isVisible, String loginId, String notificationPref, String allowManualSubmission,
			String allowDashboardView, String themePref, boolean deleted) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.password = password;
		this.emailId = emailId;
		this.contactNo = contactNo;
		this.userRoleId = userRoleId;
		this.firstLogin = firstLogin;
		this.pacsUserId = pacsUserId;
		this.pacsPassword = pacsPassword;
		this.isActive = isActive;
		this.isVisible = isVisible;
		this.loginId = loginId;
		this.notificationPref = notificationPref;
		this.allowManualSubmission = allowManualSubmission;
		this.allowDashboardView = allowDashboardView;
		this.themePref = themePref;
		this.deleted = deleted;
	}
	
	
}
