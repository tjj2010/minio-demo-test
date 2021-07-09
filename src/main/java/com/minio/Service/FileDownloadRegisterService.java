package com.minio.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minio.dao.FileDownloadRegisterDao;
import com.minio.entity.FileDownloadRegisterEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 消息登记服务类
 * @author: tjj
 * @version: 1.0
 * @date: 2021-06-07
 */
@Service
public class FileDownloadRegisterService {

    @Autowired
    private FileDownloadRegisterDao fileDownloadRegisterDao;

    public Object insert(FileDownloadRegisterEntity fileDownloadRegister){
        fileDownloadRegisterDao.insert(fileDownloadRegister);
        return fileDownloadRegister.getId();
    }
    
    /**
     * @description: 通过新文件名、文件下载人员ID、下载日期等组合条件分页查询file_download_register表中文件下载信息
     */
    public PageInfo<FileDownloadRegisterEntity> selectFileDownloadByComposeCondition(int page, int size, String  originalFileName, String  newFileName, String  downloadOperator, String startDownloadTime, String endDownloadTime) {
		String orderBy = "id desc";
		PageHelper.startPage(page, size, orderBy);
		return new PageInfo<FileDownloadRegisterEntity>(fileDownloadRegisterDao.selectFileInfoByComposeCondition(originalFileName,newFileName,downloadOperator,startDownloadTime,endDownloadTime));
    }
}