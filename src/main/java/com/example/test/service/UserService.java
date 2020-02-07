package com.example.test.service;

import com.example.test.dto.User;

public interface UserService {
	User search(String userId);
	boolean insert(User user);
}