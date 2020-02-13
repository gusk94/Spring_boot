package com.example.test.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="file")
public class FileUpload {
	private String uploadDir;
	
	public String getUploadDir() {
        return uploadDir;
    }
 
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}