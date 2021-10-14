/**
 * 
 */
package com.bourntec.vetrisSecurityServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bourntec.vetrisSecurityServer.model.Users;

/**
 * @author Donepudi Suresh
 *
 */
@Repository
public interface VetrisSecurityServerRepository extends JpaRepository<Users, String> {
	public Users findByLoginId(String login_id);
	
}
