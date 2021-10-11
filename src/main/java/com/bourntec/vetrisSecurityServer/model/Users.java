package com.bourntec.vetrisSecurityServer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Donepudi Suresh
 *
 */

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
	
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
		
		@Column(name = "created_by",length = 200,nullable=false,unique = true)
		private String createdBy;

		@Column(name = "date_created",nullable=false)
		private Date dateCreated;
		
		@Column(name = "update_by",nullable=true, unique = true)
		private String updateBy;
		
		@Column(name = "date_updated",nullable=true)
		private Date dateUpdated;

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
}
