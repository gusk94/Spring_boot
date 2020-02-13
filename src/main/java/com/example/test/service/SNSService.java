package com.example.test.service;

import java.util.List;

import com.example.test.dto.SNS;

public interface SNSService {
	public List<SNS> allsns();
	public int countallsns();
	public SNS search(int articleId);
	public List<SNS> snsByTime();
	public List<SNS> searchByFollow(String userId);
	public int countByFollow(String userId);
	public List<SNS> searchByNickname(String nickname);
	public int countByNickname(String nickname);
	public List<SNS> searchByContents(String contents);
	public int countByContents(String contents);
	public boolean insert(SNS sns);
	public boolean update(SNS sns); 
	public boolean delete(int articleId); 
	public List<SNS> searchByUserId(String userId);
}