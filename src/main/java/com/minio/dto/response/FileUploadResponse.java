package com.minio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
    private String urlHttp;

    private String urlPath;

	private String newFileName;

	public FileUploadResponse(String urlHttp, String urlPath, String newFileName) {
		// TODO Auto-generated constructor stub
		this.urlHttp = urlHttp ;
		this.urlPath = urlPath ;
		this.newFileName = newFileName ;
	}

	public String getUrlHttp() {
		return urlHttp;
	}

	public void setUrlHttp(String urlHttp) {
		this.urlHttp = urlHttp;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	
    
    public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
}
