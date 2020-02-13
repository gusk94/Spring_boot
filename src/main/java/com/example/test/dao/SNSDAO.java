package com.example.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.test.dto.SNS;
@Mapper
public interface SNSDAO {
	public List<SNS> allsns();
	public SNS search(int articleId);
	public int countallsns();
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