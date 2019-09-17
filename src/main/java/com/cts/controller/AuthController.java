/**
 * This class is used to validate the authenticated user.
 * 
 * @author 764432
 *
 */
package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.model.User;
import com.cts.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	/**
	 * This constructor is used to initialize the AuthService variable.
	 * 
	 * @param authService
	 */
	public AuthController(final AuthService authService) {
		super();
		this.authService = authService;
	}

	/**
	 * This method is used for return the authenticated user Id.
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody final User user) {

		/*
		 * if(authService.login(user).equals("User Looged in sucessfully")){
		 * return new ResponseEntity(user + authService.login(user)+
		 * " with user Id: "+user.getUserId(), HttpStatus.OK); }else{ return new
		 * ResponseEntity(user + authService.login(user)+ " with user Id: "
		 * +user.getUserId(), HttpStatus.INTERNAL_SERVER_ERROR); }
		 */
		return authService.login(user) + " with user Id:" + user.getUserId();
	}

	/**
	 * This method is used for creating the user.
	 * 
	 * @param user
	 * @return String
	 */
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	public String createUser(@RequestBody final User user) {

		return authService.createUser(user);
	}
}
