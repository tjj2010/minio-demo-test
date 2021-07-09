package com.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProp {
    /**
     * 连接地址
     */
    private String endpoint;
    
	/**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 域名
     */
    private String filHost;
    
    public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getFilHost() {
		return filHost;
	}
	public void setFilHost(String filHost) {
		this.filHost = filHost;
	}
}
