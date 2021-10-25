package com.vetris.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vetris.security.model.UserRoles;


/**
 * Repository for UserRoles
 * @author Jose Eldhose
 *
 */
@Repository
public interface UserRolesRepostitory  extends JpaRepository<UserRoles ,Integer> {

}
