package com.minio.dao;

import com.minio.entity.FileInfoRegisterEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

/**
 * @description: 文件信息登记DAO
 * @author: tjj
 * @version: 1.0
 * @date: 2021-07-05
 */

@Repository
public interface FileInfoRegisterDao extends Mapper<FileInfoRegisterEntity>, IdsMapper<FileInfoRegisterEntity>,
		ConditionMapper<FileInfoRegisterEntity> {

	@Update("UPDATE file_info_register SET  delete_operator= #{deleteOperator}  , state= #{state}, delete_time= NOW() , update_time= NOW()  WHERE new_file_name = #{newFileName}")
	void updateAftDelete(@Param("newFileName") String newFileName, @Param("deleteOperator") String deleteOperator,
			@Param("state") String state);

	@Select("SELECT id as id, original_file_name as originalFileName, new_file_name as newFileName, module_code as moduleCode,business_id as  businessId ,upload_operator as uploadOperator , delete_operator as deleteOperator ,file_size as fileSize,file_type as fileType,upload_time as uploadTime,delete_time as deleteTime,state as state,create_time as createTime,update_time as updateTime FROM file_info_register  WHERE business_id = #{businessId} and module_code = #{moduleCode}")
	List<FileInfoRegisterEntity> selectByBusinessIdModuleCode(@Param("businessId") String businessId,
			@Param("moduleCode") String moduleCode);

	@Select("SELECT id as id, original_file_name as originalFileName, new_file_name as newFileName, module_code as moduleCode,business_id as  businessId ,upload_operator as uploadOperator , delete_operator as deleteOperator ,file_size as fileSize,file_type as fileType,upload_time as uploadTime,delete_time as deleteTime,state as state,create_time as createTime,update_time as updateTime FROM file_info_register  WHERE new_file_name = #{newFileName}")
	FileInfoRegisterEntity selectByNewFileName(@Param("newFileName") String newFileName);


	@Select({ "<script>",
			"SELECT id as id, original_file_name as originalFileName, new_file_name as newFileName, module_code as moduleCode,business_id as  businessId ,upload_operator as uploadOperator , delete_operator as deleteOperator ,file_size as fileSize,file_type as fileType,upload_time as uploadTime,delete_time as deleteTime,state as state,create_time as createTime,update_time as updateTime FROM file_info_register",
			" WHERE 1=1", "<if test=\"originalFileName !=null and  originalFileName !='' \">",
			"and original_file_name like CONCAT(CONCAT('%',#{originalFileName},'%')) ", "</if>",
			"<if test=\"businessId !=null and  businessId !='' \">", "and business_id=#{businessId}", "</if>",
			"<if test=\"moduleCode !=null and  moduleCode !='' \">", "and module_code=#{moduleCode}", "</if>",
			"<if test=\"uploadOperator !=null and  uploadOperator !='' \">", "and upload_operator=#{uploadOperator}",
			"</if>", "<if test=\"startUploadDate !=null and  startUploadDate !='' \">",
			"and upload_time >= CONCAT(CONCAT(#{startUploadDate},' 00:00:00')) ", "</if>",
			"<if test=\"endUploadDate !=null and  endUploadDate !='' \">",
			"and CONCAT(CONCAT(#{endUploadDate},' 23:59:59')) >= upload_time", "</if>", "</script>" })
	List<FileInfoRegisterEntity> selectFileInfoByComposeCondition(@Param("originalFileName") String originalFileName,
			@Param("businessId") String businessId, @Param("moduleCode") String moduleCode,
			@Param("uploadOperator") String uploadOperator, @Param("startUploadDate") String startUploadDate,
			@Param("endUploadDate") String endUploadDate);

}
