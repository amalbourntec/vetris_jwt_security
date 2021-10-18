/**
 * 
 */
package com.vetris.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vetris.security.model.User;

/**
 * @author Donepudi Suresh
 *
 */
@Repository
public interface SecurityRepository extends JpaRepository<User, String> {
	public User findByLoginId(String login_id);
	
}
