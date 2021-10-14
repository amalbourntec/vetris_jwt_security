/**
 * 
 */
package com.bourntec.vetrisSecurityServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bourntec.vetrisSecurityServer.model.Users;

/**
 * @author Donepudi Suresh
 *
 */
@Repository
public interface VetrisSecurityServerRepository extends JpaRepository<Users, String> {
	@Query(value="select * from users u where u.login_id=?1",nativeQuery=true)
	public Users findloginId(String loginId);
	
}
