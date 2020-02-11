package com.example.test.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
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
import org.springframework.ui.Model;
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
	public String login(Model model) {
	    String clientId = "";//애플리케이션 클라이언트 아이디값";
	    String redirectURI;
		try {
			redirectURI = URLEncoder.encode("http://localhost:8080/naver", "UTF-8");
		    SecureRandom random = new SecureRandom();
		    String state = new BigInteger(130, random).toString();
		    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		    apiURL += "&client_id=" + clientId;
		    apiURL += "&redirect_uri=" + redirectURI;
		    apiURL += "&state=" + state;
		    model.addAttribute("apiURL", apiURL);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	@RequestMapping("/naver")
	public String navercallback(HttpServletRequest request) {
	    String clientId = "";//애플리케이션 클라이언트 아이디값";
	    String clientSecret = "";//애플리케이션 클라이언트 시크릿값";
	    String code = request.getParameter("code");
	    String state = request.getParameter("state");
	    String redirectURI;
		try {
			redirectURI = URLEncoder.encode("http://localhost:8080/naver", "UTF-8");
		    String apiURL;
		    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		    apiURL += "client_id=" + clientId;
		    apiURL += "&client_secret=" + clientSecret;
		    apiURL += "&redirect_uri=" + redirectURI;
		    apiURL += "&code=" + code;
		    apiURL += "&state=" + state;
		    String access_token = "";
		    String refresh_token = "";
		    try {
		      URL url = new URL(apiURL);
		      HttpURLConnection con = (HttpURLConnection)url.openConnection();
		      con.setRequestMethod("GET");
		      int responseCode = con.getResponseCode();
		      BufferedReader br;
		      if(responseCode==200) { // 정상 호출
		        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		      } else {  // 에러 발생
		        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		      }
		      String inputLine;
		      StringBuffer res = new StringBuffer();
		      while ((inputLine = br.readLine()) != null) {
		        res.append(inputLine);
		      }
		      br.close();
	        
	        // ----
	        String temp = res.toString();
	        String[] str = temp.split(":|,");
	        
	        StringBuilder sb = new StringBuilder();
	        for(int i=1;i<str[1].length()-1;i++) {
	        	sb.append(str[1].charAt(i));
	        }
	        String token = sb.toString();
	        // ----
	        
	        
	        // profile
	        String header = "Bearer " + token; // Bearer 다음에 공백 추가
            String profileURL = "https://openapi.naver.com/v1/nid/me";
            URL profileurl = new URL(profileURL);
            HttpURLConnection profilecon = (HttpURLConnection)profileurl.openConnection();
            profilecon.setRequestMethod("GET");
            profilecon.setRequestProperty("Authorization", header);
            int profileCode = profilecon.getResponseCode();
            BufferedReader profilebr;
            if(profileCode==200) { // 정상 호출
                profilebr = new BufferedReader(new InputStreamReader(profilecon.getInputStream()));
            } else {  // 에러 발생
                profilebr = new BufferedReader(new InputStreamReader(profilecon.getErrorStream()));
            }
            String profileinputLine;
            StringBuffer response = new StringBuffer();
            while ((profileinputLine = profilebr.readLine()) != null) {
                response.append(profileinputLine);
            }
            profilebr.close();
            System.out.println(response.toString());
		    } catch (Exception e) {
		      System.out.println(e);
		    }
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "/home";
	}

}
