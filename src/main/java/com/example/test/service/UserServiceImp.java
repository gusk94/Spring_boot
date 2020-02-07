package com.example.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.test.dao.UserDAO;
import com.example.test.dto.User;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserDAO dao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User search(String userId) {
		try {
			User user = dao.search(userId);
			return user;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean insert(User user) {
		try {
			User tempUser = dao.search(user.getUserId());
			// 회원가입 성공 시 암호화
			if(tempUser == null) {
				String pw = user.getPassword();
				user.setPassword(passwordEncoder.encode(pw));
//				System.out.println(user.getPassword());
				dao.insert(user);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}