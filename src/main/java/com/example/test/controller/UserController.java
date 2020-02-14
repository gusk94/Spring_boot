package com.example.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.dto.User;
import com.example.test.service.JwtService;
import com.example.test.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge=6000)
@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
    public JavaMailSender emailSender;

	@ExceptionHandler
	public ResponseEntity<Map<String, Object>>handler(Exception e){ 
		return handleFail(e.getMessage(), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	@ApiOperation("id로 user를 조회하는 기능") 
	public ResponseEntity<Map<String, Object>> searchUser(@PathVariable String userId){ 
		return handleSuccess(userService.search(userId));
	}

	@PostMapping("/user/login")
	@ApiOperation("login") 
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user, HttpServletResponse response) { 
		try {
			User findedUser = userService.search(user.getUserId());

			// 회원 비밀번호 == 인코딩된 비밀번호
			if(passwordEncoder.matches(user.getPassword(), findedUser.getPassword())) {
				response.setStatus(HttpServletResponse.SC_OK);

//				Map<String, Object> userLogin = new HashMap<>();
//				userLogin.put("ID", user.getUserId());
//				userLogin.put("Nickname", user.getNickname());

				Map<String, Object> result = new HashMap<>();
				result.put("token", jwtService.makeJwt(findedUser));
//				result.put("user", userLogin);
				return handleSuccess(result);		 
			} else{
				// 로그인 실패
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
		} catch(Exception e) {
			// 아이디 존재 안함
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
	}
	
	@PostMapping("/user/email")
	@ApiOperation("user 이메일 인증")
	public ResponseEntity<Map<String, Object>> checkEmail(@RequestBody String email){ 
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("name@gmail.com");
        message.setTo(email);
        message.setSubject("hi");
        message.setText("success sending email!");
        emailSender.send(message);
		
		return handleSuccess("등록 완료");
	}

	@PostMapping("/user")
	@ApiOperation("user 정보 등록")
	public ResponseEntity<Map<String, Object>> insertUser(@RequestBody User user){ 
		boolean result = userService.insert(user); 
		
		if(result) return handleSuccess("등록 완료");
		else return handleFail("등록 실패", HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<Map<String, Object>> handleSuccess(Object data){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok"); resultMap.put("data", data); 
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK); 
	}

	public ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status){ 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail"); resultMap.put("data", data); 
		return new ResponseEntity<Map<String,Object>>(resultMap, status); 
	}
}