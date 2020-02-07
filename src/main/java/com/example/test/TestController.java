package com.example.test;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	@RequestMapping("/")
	public String googleLogin() {
		System.out.println("success");
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println(auth.getPrincipal());
		return "index";
	}

	@RequestMapping("/callback")
	public String callback() {
		System.out.println("Reirecting to homepage");
		return "home";
	}
}
