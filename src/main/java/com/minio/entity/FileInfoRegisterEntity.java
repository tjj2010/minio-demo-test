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
 * @description: 文件信息登记实体
 * @author: tjj
 * @version: 1.0
 * @date: 2021-07-05
 */

@Data
@Table(name = "file_info_register")
@ApiModel(value = "FileInfoRegister", description = "文件信息登记表")
public class FileInfoRegisterEntity implements Serializable {

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
	 * 所属模块编码
	 */
	@ApiModelProperty(value = "所属模块编码")
	private String moduleCode;
	
	/**
	 * 所属业务ID
	 */
	@ApiModelProperty(value = "所属业务ID")
	private String businessId;


	/**
	 * 上传人ID
	 */
	@ApiModelProperty(value = "上传人ID")
	private String uploadOperator;

	/**
	 * 文件大小
	 */
	@ApiModelProperty(value = "文件大小")
	private String fileSize;
	
	/**
	 * 文件类型
	 */
	@ApiModelProperty(value = "文件类型")
	private String fileType;
	
	/**
	 * 文件上传时间
	 */
	@ApiModelProperty(value = "文件上传时间")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
	private Timestamp uploadTime;

	/**
	 * 删除人员ID
	 */
	@ApiModelProperty(value = "删除人员")
	private String deleteOperator;


	/**
	 * 文件删除时间
	 */
	@ApiModelProperty(value = "文件删除时间")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
	private Timestamp deleteTime;
	
	/**
	 * 状态：00--正常，01--已删除
	 */
	@ApiModelProperty(value = "状态")
	private String state;
	
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

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getUploadOperator() {
		return uploadOperator;
	}

	public void setUploadOperator(String uploadOperator) {
		this.uploadOperator = uploadOperator;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	
	public String getDeleteOperator() {
		return deleteOperator;
	}

	public void setDeleteOperator(String deleteOperator) {
		this.deleteOperator = deleteOperator;
	}
	
	public Timestamp getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}
	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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