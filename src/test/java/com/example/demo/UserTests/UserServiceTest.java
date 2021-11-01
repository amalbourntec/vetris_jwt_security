package com.example.demo.UserTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetris.security.enums.ExceptionCodes;
import com.vetris.security.exception.InvalidUserException;
import com.vetris.security.exception.TokenExpiredException;
import com.vetris.security.exception.UnauthorizedException;
import com.vetris.security.model.User;
import com.vetris.security.repository.SecurityRepository;
import com.vetris.security.repository.UserRolesRepostitory;
import com.vetris.security.service.SecurittyService;
import com.vetris.security.service.impl.SecurityServiceImpl;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = SecurityServiceImpl.class)
class UserServiceTest {

	@Autowired
	private SecurittyService securittyService;

	@MockBean
	private SecurityRepository securityRepository;

	@MockBean
	UserRolesRepostitory userRolesRepostitory;

	@MockBean
	ObjectMapper objectMapper;

	public static User userModel;

	public static User userModelDifferentPassword;

	@BeforeAll
	public static void setUp() {

		userModel = new User();
		userModel.setId("278b6c57-673d-4605-8766-3f0600f8074f");
		userModel.setCode("Admin");
		userModel.setContactNo("9999999999");
		userModel.setEmailId("admin@test.com");
		userModel.setFirstLogin("N");
		userModel.setIsVisible("Y");
		userModel.setIsActive("Y");
		userModel.setLoginId("admin");
		userModel.setName("admin");
		userModel.setNotificationPref("B");
		userModel.setPassword("cc03e747a6afbbcbf8be7668acfebee5");
		userModel.setPacsUserId("admin");
		userModel.setPacsPassword("cc03e747a6afbbcbf8be7668acfebee5");
		userModel.setThemePref("DEFAULT");
		userModel.setUserRoleId(1);

		userModelDifferentPassword = new User();
		userModelDifferentPassword.setId("278b6c57-673d-4605-8766-3f0600f8074f");
		userModelDifferentPassword.setCode("testuser");
		userModelDifferentPassword.setContactNo("9999999999");
		userModelDifferentPassword.setEmailId("testuser@test.com");
		userModelDifferentPassword.setFirstLogin("N");
		userModelDifferentPassword.setIsVisible("Y");
		userModelDifferentPassword.setIsActive("Y");
		userModelDifferentPassword.setLoginId("testuser");
		userModelDifferentPassword.setName("testuser");
		userModelDifferentPassword.setNotificationPref("B");
		userModelDifferentPassword.setPassword("cc03e747a6afbbcbf8be7668acfebee51");
		userModelDifferentPassword.setPacsUserId("testuser");
		userModelDifferentPassword.setPacsPassword("cc03e747a6afbbcbf8be7668acfebee5");
		userModelDifferentPassword.setThemePref("DEFAULT");
		userModelDifferentPassword.setUserRoleId(1);

	}

	@Test
	void testSignon() throws UnauthorizedException, InvalidUserException, TokenExpiredException {

		Mockito.when(securityRepository.findByLoginId(Mockito.any(String.class))).thenReturn(userModel);

		String auth = Base64.getEncoder().encodeToString("admin:test123".getBytes());
		ResponseEntity<?> response = null;

		response = securittyService.signon(auth);

		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void testUnauthorizedExceptionInSignon() {

		String auth = Base64.getEncoder().encodeToString("testuser:test123".getBytes());

		Mockito.when(securityRepository.findByLoginId(Mockito.any(String.class)))
				.thenReturn(userModelDifferentPassword);

		UnauthorizedException exception = assertThrows(UnauthorizedException.class,
				() -> securittyService.signon(auth));

		assertTrue(exception.getMessage().equalsIgnoreCase(ExceptionCodes.UNAUTHORIZED_USER.getMessage()));
	}

	@Test
	void testInvalidUserExceptionInSignon() {

		String auth = Base64.getEncoder().encodeToString("nousser:test123".getBytes());

		InvalidUserException exception = assertThrows(InvalidUserException.class, () -> securittyService.signon(auth));

		assertTrue(exception.getMessage().equalsIgnoreCase(ExceptionCodes.USER_NOT_FOUND.getMessage()));
	}

}
