/**
 * 
 */
package com.bourntec.vetrisSecurityServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bourntec.vetrisSecurityServer.model.Users;

/**
 * @author Donepudi Suresh
 *
 */
public interface VetrisSecurityServerRepository extends JpaRepository<Users, String> {
	public Users findOneByLoginId(String loginId);
}
