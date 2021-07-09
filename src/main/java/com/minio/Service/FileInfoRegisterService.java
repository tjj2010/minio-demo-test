package com.minio.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minio.dao.FileInfoRegisterDao;
import com.minio.entity.FileInfoRegisterEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 消息登记服务类
 * @author: tjj
 * @version: 1.0
 * @date: 2021-06-07
 */
@Service
public class FileInfoRegisterService {

    @Autowired
    private FileInfoRegisterDao fileInfoRegisterDao;

    public Object insert(FileInfoRegisterEntity fileInfoRegister){
        fileInfoRegisterDao.insert(fileInfoRegister);
        return fileInfoRegister.getId();
    }
    
    /**
     * @description: 通过业务ID、模块编码分页查询上传文件信息
     */
    public PageInfo<FileInfoRegisterEntity> selectByBusinessIdModuleCode(int page, int size, String  businessId, String moduleCode) {
		String orderBy = "id desc";
		PageHelper.startPage(page, size, orderBy);
		return new PageInfo<FileInfoRegisterEntity>(fileInfoRegisterDao.selectByBusinessIdModuleCode(businessId,moduleCode));
    }
    
    /**
     * @description: 通过原文件名、业务ID、模块编码、文件创建者ID等组合条件分页查询file_info_register表中文件上传信息
     */
    public PageInfo<FileInfoRegisterEntity> selectFileInfoByComposeCondition(int page, int size, String  originalFileName, String  businessId, String moduleCode, String uploadOperator, String startUploadDate, String endUploadDate) {
		String orderBy = "id desc";
		PageHelper.startPage(page, size, orderBy);
		return new PageInfo<FileInfoRegisterEntity>(fileInfoRegisterDao.selectFileInfoByComposeCondition(originalFileName,businessId,moduleCode,uploadOperator,startUploadDate,endUploadDate));
    }
    
    
    /**
     * @description: 在文件删除后更新文件信息登记表
     */
    public void updateAftDelete(String newFileName,String deleteOperator,String state) {
        fileInfoRegisterDao.updateAftDelete(newFileName,deleteOperator,state);
    }
    
    public FileInfoRegisterEntity selectByNewFileName(String  newFileName) {
    	return  fileInfoRegisterDao.selectByNewFileName(newFileName);
    }
    
}