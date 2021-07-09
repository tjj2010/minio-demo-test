package com.minio.controller;

import com.github.pagehelper.PageInfo;
import com.minio.Service.FileDownloadRegisterService;
import com.minio.Service.FileInfoRegisterService;
import com.minio.dto.response.FileUploadResponse;
import com.minio.entity.FileDownloadRegisterEntity;
import com.minio.entity.FileInfoRegisterEntity;
import com.minio.utils.MinioUtil;
import com.minio.utils.ResultMessage;

import io.minio.errors.MinioException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

//@Slf4j
@RestController
@RequestMapping("/file")
public class MinioController {

    @Autowired
    private MinioUtil minioUtil;
    
	@Autowired
	private FileInfoRegisterService fileInfoRegisterService;
	
	@Autowired
	private FileDownloadRegisterService fileDownloadRegisterService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 上传文件
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public FileUploadResponse upload(@RequestParam(name = "file", required = true) MultipartFile file, @RequestParam(required = false, defaultValue = "salt") String bucketName, @RequestParam String uploadOperator, @RequestParam String moduleCode, @RequestParam String businessId) {
    	FileUploadResponse response = null;
        try {
        	/** 1-必输入参校验  **/
    		if (StringUtils.isEmpty(uploadOperator)||StringUtils.isEmpty(businessId)||StringUtils.isEmpty(moduleCode)){
    			logger.error("入参中uploadOperator、businessId、moduleCode其中之一为空，入参校验失败！");
    			throw new RuntimeException("入参中uploadOperator、businessId、moduleCode其中之一为空，入参校验失败！");
    		}
    		
    		/** 2-文件上传逻辑处理  **/
            response = minioUtil.uploadFile(file, bucketName);
            
            /** 3-在文件信息表中登记相关信息  **/
            String newFileName = response.getNewFileName();
            minioUtil.fileInfoRegister(file, uploadOperator, moduleCode, businessId, newFileName);
        } catch (Exception e) {
        	logger.error("上传失败 : [{}]", Arrays.asList(e.getStackTrace()));
        }
        return response;
    }

    /**
     * 删除文件
     */
	@ApiOperation(value = "文件删除：通过文件上传成功后生成的新文件名进行文件删除")
    @DeleteMapping("/delete/{objectName}")
    public void delete(@PathVariable("objectName") String objectName, @RequestParam(required = false, defaultValue = "salt") String bucketName, @RequestParam String deleteOperator) throws Exception {
		/** 1-必输入参校验  **/
		if (StringUtils.isEmpty(objectName)||StringUtils.isEmpty(deleteOperator)){
			logger.error("必输入参中objectName、deleteOperator其中之一为空，入参校验失败！");
			throw new RuntimeException("必输入参中objectName、deleteOperator其中之一为空，入参校验失败！");
		}
		
		/** 2-文件删除处理逻辑  **/
		minioUtil.removeObject(bucketName, objectName);
		
		/** 3-在文件删除后更新文件信息登记表：将删除人员ID、删除时间记录下来  **/
        fileInfoRegisterService.updateAftDelete(objectName, deleteOperator, "01");
        logger.info("删除成功");
    }

    /**
     * 下载文件到本地
     */
	@ApiOperation(value = "文件下载：通过文件上传成功后生成的新文件名进行文件下载（GET请求方式）")
    @GetMapping("/download/{objectName}")
    public ResponseEntity<byte[]> downloadToLocal(@PathVariable("objectName") String objectName, HttpServletResponse response, @RequestParam String downloadOperator) throws Exception {
        ResponseEntity<byte[]> responseEntity = null;
        InputStream stream = null;
        ByteArrayOutputStream output = null;
        try {
        	/** 1-必输入参校验  **/
    		if (StringUtils.isEmpty(objectName)||StringUtils.isEmpty(downloadOperator)){
    			logger.error("必输入参中objectName、downloadOperator其中之一为空，入参校验失败！");
    			throw new RuntimeException("必输入参中objectName、downloadOperator其中之一为空，入参校验失败！");
    		}
    		
    		/** 2-文件下载逻辑处理  **/
            // 获取"myobject"的输入流。
            stream = minioUtil.getObject("salt", objectName);
            if (stream == null) {
            	logger.error("文件不存在");
                throw new RuntimeException("文件不存在");
            }
            //用于转换byte
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = stream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();

            //设置header
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Accept-Ranges", "bytes");
            httpHeaders.add("Content-Length", bytes.length + "");
//            objectName = new String(objectName.getBytes("UTF-8"), "ISO8859-1");
            //把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
            httpHeaders.add("Content-disposition", "attachment; filename=" + objectName);
            httpHeaders.add("Content-Type", "text/plain;charset=utf-8");
//            httpHeaders.add("Content-Type", "image/jpeg");
            responseEntity = new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.CREATED);
            
            /** 3-在文件下载信息表中登记相关信息  **/
            minioUtil.fileDownloadRegister(objectName, downloadOperator);
        } catch (MinioException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (output != null) {
                output.close();
            }
        }
        return responseEntity;
    }

    /**
     * 在浏览器预览图片
     */
    @GetMapping("/preViewPicture/{objectName}")
    public void preViewPicture(@PathVariable("objectName") String objectName, HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        try (ServletOutputStream out = response.getOutputStream()) {
            InputStream stream = minioUtil.getObject("salt", objectName);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = stream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();
            out.write(bytes);
            out.flush();
        }
    }
    
    /**
     * @description: 通过业务ID、模块编码分页查询file_info_register表中文件上传信息（GET请求方式）
     */
	@ApiOperation(value = "通过业务ID、模块编码分页查询file_info_register表中文件上传信息（GET请求方式）")
	@GetMapping(value = "queryByBusinessIdModuleCode")
	public ResultMessage queryByBusinessIdModuleCode(@RequestParam(required = true, defaultValue = "1") int page, @RequestParam(required = true, defaultValue = "10")  int size, @RequestParam String businessId, @RequestParam  String moduleCode) {
		ResultMessage result = new ResultMessage();
		/** 1-必输入参校验  **/
		if (StringUtils.isEmpty(businessId)||StringUtils.isEmpty(moduleCode)){
			result.setCode(ResultMessage.FAIL);
			result.setMsg("入参中businessId为空或moduleCode为空，入参校验失败！");
			logger.error("入参中businessId为空或moduleCode为空，入参校验失败！");
			return result;
		}
		
		/** 2-调用查询处理逻辑  **/
		try {
			PageInfo<FileInfoRegisterEntity> pageInfo = fileInfoRegisterService.selectByBusinessIdModuleCode(page, size, businessId, moduleCode);
			result.setData(pageInfo.getList());
			result.setTotal(pageInfo.getTotal());
			result.setCode(ResultMessage.SUCCESS);
			result.setMsg(ResultMessage.GETMSG);
		} catch (Exception e) {
			result.setCode(ResultMessage.FAIL);
			result.setMsg(ResultMessage.GETMSGF);
			e.printStackTrace();
		}
		return result;
	}
	
    /**
     * @description: 通过原文件名、业务ID、模块编码、文件上传者ID、上传起止日期格式：yyyy-MM-dd等组合条件分页查询file_info_register表中文件上传信息（GET请求方式）
     * 
     */
	@ApiOperation(value = "通过原文件名、业务ID、模块编码、文件上传者ID、上传起止日期（入参String型，格式：yyyy-MM-dd）组合条件分页查询file_info_register表中文件上传信息（GET请求方式）")
	@GetMapping(value = "queryfileInfoByComposeCondition")
	public ResultMessage queryfileInfoByComposeCondition(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10")  int size, @RequestParam(required = false) String originalFileName, @RequestParam(required = false)  String businessId, @RequestParam(required = false)  String moduleCode, @RequestParam(required = false)  String uploadOperator, @RequestParam(required = false)  String startUploadDate, @RequestParam(required = false)  String endUploadDate) {
		ResultMessage result = new ResultMessage();
		try {
			PageInfo<FileInfoRegisterEntity> pageInfo = fileInfoRegisterService.selectFileInfoByComposeCondition(page, size, originalFileName, businessId, moduleCode, uploadOperator, startUploadDate, endUploadDate);
			result.setData(pageInfo.getList());
			result.setTotal(pageInfo.getTotal());
			result.setCode(ResultMessage.SUCCESS);
			result.setMsg(ResultMessage.GETMSG);
		} catch (Exception e) {
			result.setCode(ResultMessage.FAIL);
			result.setMsg(ResultMessage.GETMSGF);
			e.printStackTrace();
		}
		return result;
	}
	
	
    /**
     * @description: 通过原文件名、新文件名、文件下载人员ID、下载起止日期（入参String型，格式：yyyy-MM-dd）等组合条件分页查询file_download_register表中文件下载信息（GET请求方式）
     * 下载起止日期格式：yyyy-MM-dd
     */
	@ApiOperation(value = "通过原文件名、新文件名、文件下载人员ID、下载起止日期（入参String型，格式：yyyy-MM-dd）等组合条件分页查询file_download_register表中文件下载信息（GET请求方式）")
	@GetMapping(value = "queryfileDownloadByComposeCondition")
	public ResultMessage queryfileDownloadByComposeCondition(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10")  int size, @RequestParam(required = false)  String originalFileName, @RequestParam(required = false)  String newFileName, @RequestParam(required = false)  String downloadOperator, @RequestParam(required = false)  String startDownloadDate, @RequestParam(required = false)  String endDownloadDate) {
		ResultMessage result = new ResultMessage();
		try {
			PageInfo<FileDownloadRegisterEntity> pageInfo = fileDownloadRegisterService.selectFileDownloadByComposeCondition(page, size, originalFileName, newFileName, downloadOperator, startDownloadDate, endDownloadDate);
			result.setData(pageInfo.getList());
			result.setTotal(pageInfo.getTotal());
			result.setCode(ResultMessage.SUCCESS);
			result.setMsg(ResultMessage.GETMSG);
		} catch (Exception e) {
			result.setCode(ResultMessage.FAIL);
			result.setMsg(ResultMessage.GETMSGF);
			e.printStackTrace();
		}
		return result;
	}
	
}
