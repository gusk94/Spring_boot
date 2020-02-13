package com.example.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.dao.SNSDAO;
import com.example.test.dto.SNS;

@Service
public class SNSServiceImp implements SNSService {
	
	@Autowired
	private SNSDAO snsdao;
	
	@Override
	public List<SNS> allsns() {
		try {
			List<SNS> articles = snsdao.allsns();
			
			return articles;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int countallsns() {
		try {
			int count = snsdao.countallsns();
			
			return count;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public List<SNS> snsByTime() {
		try {
			List<SNS> articles = snsdao.snsByTime();
			
			return articles;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public SNS search(int articleId) {
		try {
			SNS article = snsdao.search(articleId);
			
			return article;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	};
	
	@Override
	public List<SNS> searchByFollow(String userId) {
		try {
			List<SNS> articles = snsdao.searchByFollow(userId);
			
			return articles;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int countByFollow(String userId) {
		try {
			int count = snsdao.countByFollow(userId);
			
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<SNS> searchByNickname(String nickname){
		try {
			List<SNS> articles = snsdao.searchByNickname(nickname);
			
			return articles;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	};
	
	@Override
	public int countByNickname(String nickname) {
		try {
			int count = snsdao.countByNickname(nickname);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public List<SNS> searchByContents(String contents){
		try {
			List<SNS> articles = snsdao.searchByContents(contents);
			
			return articles;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	};
	
	@Override
	public int countByContents(String contents) {
		try {
			int count = snsdao.countByContents(contents);
			
			return count;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public boolean insert(SNS sns) {
		try {
			snsdao.insert(sns);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	};
	
	@Override
	public boolean update(SNS sns) {
		try {
			snsdao.update(sns);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}; 
	
	@Override
	public boolean delete(int articleId) {
		try {
			snsdao.delete(articleId);
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public List<SNS> searchByUserId(String userId) {
		try {
			List<SNS> list = snsdao.searchByUserId(userId);
			
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}; 
}