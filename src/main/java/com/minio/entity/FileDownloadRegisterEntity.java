package com.minio.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 文件下载信息登记实体
 * @author: tjj
 * @version: 1.0
 * @date: 2021-07-05
 */

@Data
@Table(name = "file_download_register")
@ApiModel(value = "FileDownloadRegister", description = "文件下载信息登记实体")
public class FileDownloadRegisterEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主表id
	 */
	@ApiModelProperty(value = "自增长主键ID")
	@Id
	@GeneratedValue(generator = "JDBC", strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 原文件名
	 */
	@ApiModelProperty(value = "原文件名")
	private String originalFileName;
	
	/**
	 * 新文件名：文件上传生命名之后生成的文件名
	 */
	@ApiModelProperty(value = "新文件名")
	private String newFileName;

	/**
	 * 下载人员ID
	 */
	@ApiModelProperty(value = "下载人员ID")
	private String downloadOperator;
	
	/**
	 * 文件ID：file_info_register表中此文件对应的ID值
	 */
	@ApiModelProperty(value = "文件ID")
	private String fileId;

	/**
	 * 文件下载时间
	 */
	@ApiModelProperty(value = "文件下载时间")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
	private Timestamp downloadTime;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
	private Timestamp createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
	private Timestamp updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	

	public String getDownloadOperator() {
		return downloadOperator;
	}

	public void setDownloadOperator(String downloadOperator) {
		this.downloadOperator = downloadOperator;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Timestamp getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Timestamp downloadTime) {
		this.downloadTime = downloadTime;
	}


	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}



}