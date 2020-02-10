package com.example.test.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class TestController {
	
	@Value("${security.oauth2.client.clientId}")
	String clientId;
	
	@Value("${security.oauth2.client.clientSecret}")
	String clientSecret;
	
	@Value("${security.oauth2.client.preEstablishedRedirectUri}")
	String redirectURI;
	
	@RequestMapping("/")
	public String login() {
		return "/index";
	}
	
	@RequestMapping("/google")
	public String callback(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("code", code);
		parameters.add("client_id", clientId);
		parameters.add("client_secret", clientSecret);
		parameters.add("redirect_uri", redirectURI);
		parameters.add("grant_type", "authorization_code");
		
		HttpEntity<MultiValueMap<String, String>> rest_request = new HttpEntity<>(parameters, headers);
		URI uri = URI.create("https://www.googleapis.com/oauth2/v4/token");
		
		ResponseEntity<Map> rest_response;
		rest_response = restTemplate.postForEntity(uri, rest_request, Map.class);
		Map bodys = rest_response.getBody();
		String[] tokens = ((String)bodys.get("id_token")).split("\\.");

        try {
        	// 디코딩
        	String tmp = (new String(Base64.decodeBase64(tokens[1]), "utf-8"));
	        
	        // 구분자로 찢기
			String[] str = tmp.split(",|:");
			
			// 이메일 값 저장 및 다듬기
			String value = str[10];
			StringBuilder sb = new StringBuilder();
			for(int i=1;i<value.length()-1;i++)
				sb.append(value.charAt(i));
			
			String email = sb.toString();
			System.out.println("이메일: " + email );
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/home";
	}

}
