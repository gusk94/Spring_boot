package com.example.test.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.test.dto.User;

@Mapper
public interface UserDAO {
	public User search(String userId);
	public boolean insert(User user);
}
