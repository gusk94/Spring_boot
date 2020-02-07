package com.example.test.service;

import com.example.test.dto.User;

public interface JwtService {
	String makeJwt(User user) throws Exception;
	boolean checkJwt(String jwt) throws Exception;
}