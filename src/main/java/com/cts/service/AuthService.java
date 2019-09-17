package com.cts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.model.User;
import com.cts.repository.AuthRepository;

@Service
public class AuthService {

	@Autowired
	private AuthRepository authFileRepo;

	public String login(User user) {

		return authFileRepo.login(user);

	}

	public String createUser(User user) {

		return authFileRepo.createUser(user);
	}
}
