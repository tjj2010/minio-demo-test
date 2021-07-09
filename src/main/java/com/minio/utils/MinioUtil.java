package com.minio.utils;

import com.minio.Service.FileDownloadRegisterService;
import com.minio.Service.FileInfoRegisterService;
import com.minio.config.MinioProp;
import com.minio.dto.response.FileUploadResponse;
import com.minio.entity.FileDownloadRegisterEntity;
import com.minio.entity.FileInfoRegisterEntity;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

//@Slf4j
@Component
public class MinioUtil {

    @Autowired
    private MinioProp minioProp;

    @Autowired
    private MinioClient client;
    
	@Autowired
	private FileInfoRegisterService fileInfoRegisterService;
	
    
	@Autowired
	private FileDownloadRegisterService fileDownloadRegisterService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 创建bucket
     */
    public void createBucket(String bucketName) throws Exception {
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 上传文件
     */
    public FileUploadResponse uploadFile(MultipartFile file, String bucketName) throws Exception {
        //判断文件是否为空
//        if (null == file || 0 == file.getSize()) {
//            return null;
//        }
        if (null == file) {
            return null;
        }
        //判断存储桶是否存在  不存在则创建
        createBucket(bucketName);
        //文件名
        String originalFilename = file.getOriginalFilename();
        //新的文件名 = 存储桶文件名_时间戳.后缀名
        assert originalFilename != null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = bucketName + "_" +
                System.currentTimeMillis() + "_" + format.format(new Date()) + "_" + new Random().nextInt(1000) +"_" + originalFilename;
//                originalFilename.substring(originalFilename.lastIndexOf("."));
//        String fileName = originalFilename ;
        //开始上传
        client.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                        file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
        String url = minioProp.getEndpoint() + "/" + bucketName + "/" + fileName;
        String urlHost = minioProp.getFilHost() + "/" + bucketName + "/" + fileName;
        logger.info("上传文件成功url ：[{}], urlHost ：[{}]", url, urlHost);
        
        return new FileUploadResponse(url, urlHost, fileName);
    }

    /**
     * 获取全部bucket
     *
     * @return
     */
    public List<Bucket> getAllBuckets() throws Exception {
        return client.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    public Optional<Bucket> getBucket(String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, ErrorResponseException, ServerException, XmlParserException {
        return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    public void removeBucket(String bucketName) throws Exception {
        client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取⽂件外链
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    public String getObjectURL(String bucketName, String objectName, Integer expires) throws Exception {
        return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).expiry(expires).build());
    }

    /**
     * 获取⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @return ⼆进制流
     */
    public InputStream getObject(String bucketName, String objectName) throws Exception {
        return client.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 上传⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @param stream     ⽂件流
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws
            Exception {
        client.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, stream.available(), -1).contentType(objectName.substring(objectName.lastIndexOf("."))).build());
    }

    /**
     * 上传⽂件
     *
     * @param bucketName  bucket名称
     * @param objectName  ⽂件名称
     * @param stream      ⽂件流
     * @param size        ⼤⼩
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream, long
            size, String contextType) throws Exception {
        client.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, -1).contentType(contextType).build());
    }

    /**
     * 获取⽂件信息
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
     */
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        return client.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 删除⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @throws Exception https://docs.minio.io/cn/java-client-apireference.html#removeObject
     */
    public void removeObject(String bucketName, String objectName) throws Exception {
        client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }
    
    /**
     * 文件信息登记
     */
    public void fileInfoRegister(MultipartFile file, String uploadOperator, String moduleCode, String businessId, String newFileName) {
    	
        String originalFileName = file.getOriginalFilename();//文件名
        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
//        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());//文件名类型
        String fileSize = file.getSize()+"";//文件大小
        FileInfoRegisterEntity fileInfoRegisterEntity = new FileInfoRegisterEntity();
        fileInfoRegisterEntity.setOriginalFileName(originalFileName);
        fileInfoRegisterEntity.setNewFileName(newFileName);
//        fileInfoRegisterEntity.setFilePath(originalFileName);
        fileInfoRegisterEntity.setModuleCode(moduleCode);
        fileInfoRegisterEntity.setBusinessId(businessId);
        fileInfoRegisterEntity.setUploadOperator(uploadOperator);
        fileInfoRegisterEntity.setFileSize(fileSize);
        fileInfoRegisterEntity.setFileType(fileType);
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Timestamp timeStamp =Timestamp.valueOf(nowTime);
		fileInfoRegisterEntity.setUploadTime(timeStamp);
		fileInfoRegisterEntity.setState("00");//文件状态：00--正常，01--已删除
		fileInfoRegisterEntity.setCreateTime(timeStamp);
		fileInfoRegisterEntity.setUpdateTime(timeStamp);
		fileInfoRegisterService.insert(fileInfoRegisterEntity);
    	
    	
    }
    
    
    /**
     * 文件下载信息登记
     */
    public void fileDownloadRegister(String newFileName, String downloadOperator) {
    	
    	FileDownloadRegisterEntity fileDownloadRegisterEntity = new FileDownloadRegisterEntity();
    	fileDownloadRegisterEntity.setNewFileName(newFileName);
    	FileInfoRegisterEntity fileInfo = fileInfoRegisterService.selectByNewFileName(newFileName);
    	String fileId = fileInfo.getId()+"";
    	String originalFileName = fileInfo.getOriginalFileName();
    	fileDownloadRegisterEntity.setFileId(fileId);
    	fileDownloadRegisterEntity.setOriginalFileName(originalFileName);
    	fileDownloadRegisterEntity.setDownloadOperator(downloadOperator);
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Timestamp timeStamp =Timestamp.valueOf(nowTime);
		fileDownloadRegisterEntity.setDownloadTime(timeStamp);
		fileDownloadRegisterEntity.setCreateTime(timeStamp);
		fileDownloadRegisterEntity.setUpdateTime(timeStamp);
		fileDownloadRegisterService.insert(fileDownloadRegisterEntity);
    }
    
	/**
	 * @description: 判断输入的字符串是否为空：为空则返回true，非空返回false
	 */
	public static Boolean isEmpty(String str) {
		if (str == null || str.equals("")) {
			return true;
		}else {
			return false;
		}
	}
}
