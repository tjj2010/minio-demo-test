package com.minio.dao;

import com.minio.entity.FileDownloadRegisterEntity;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

/**
 * @description: 文件信息登记DAO
 * @author: tjj
 * @version: 1.0
 * @date: 2021-07-05
 */

@Repository
public interface FileDownloadRegisterDao extends Mapper<FileDownloadRegisterEntity>,
		IdsMapper<FileDownloadRegisterEntity>, ConditionMapper<FileDownloadRegisterEntity> {
	
	@Select({ "<script>",
		"SELECT id as id, original_file_name as originalFileName, new_file_name as newFileName, download_operator as downloadOperator,download_time as  downloadTime ,file_id as fileId , create_time as createTime,update_time as updateTime FROM file_download_register",
		" WHERE 1=1", "<if test=\"originalFileName !=null and  originalFileName !='' \">",
		"and original_file_name like CONCAT(CONCAT('%',#{originalFileName},'%')) ", "</if>",
		"<if test=\"newFileName !=null and  newFileName !='' \">",
		"and new_file_name like CONCAT(CONCAT('%',#{newFileName},'%')) ", "</if>",
		"<if test=\"downloadOperator !=null and  downloadOperator !='' \">",
		"and download_operator=#{downloadOperator}", "</if>", 
		
		"<if test=\"startDownloadTime !=null and  startDownloadTime !='' \">",
		"and download_time >= CONCAT(CONCAT(#{startDownloadTime},' 00:00:00')) ", "</if>",
		"<if test=\"endDownloadTime !=null and  endDownloadTime !='' \">",
//		"and download_time > CONCAT(CONCAT(#{endDownloadTime},' 23:59:59')) ", "</if>",
		"and CONCAT(CONCAT(#{endDownloadTime},' 23:59:59')) >= download_time", "</if>",
		"</script>" })
	List<FileDownloadRegisterEntity> selectFileInfoByComposeCondition(
			@Param("originalFileName") String originalFileName, @Param("newFileName") String newFileName,
			@Param("downloadOperator") String downloadOperator, @Param("startDownloadTime") String startDownloadTime,
			@Param("endDownloadTime") String endDownloadTime);
}
