package com.minio.utils;

import java.io.Serializable;
/**
 *   
 * 类名称：ResultMessage    
 * 类描述：   统一返回标准
 * 创建人：-- zhanjm
 * 创建时间：
 * 修改人：--     
 * 修改时间：   
 * 修改备注：       
 * @version </pre>
 */
public class ResultMessage implements Serializable {
	//操作成功code
	public static final String SUCCESS="2000";
	//操作成功msg
	public static final String DELMSG="删除成功!";//删除
	public static final String ADDMSG="添加成功!";//添加
	public static final String UPDMSG="修改成功!";//修改
	public static final String GETMSG="查询成功!";//查询
	
	
	//操作失败code
	public static final String  FAIL="5000";
	//操作失败msg
	public static final String DELMSGF="删除失败!";//删除
	public static final String ADDMSGF="添加失败!";//添加
	public static final String UPDMSGF="修改失败!";//修改
	public static final String GETMSGF="查询失败!";//查询
	
	
	//操作失败特定code   msg   
	public static final String  FAILONE="5001";
	public static final String POSTPAM="参数不能为空!";//删除
	
	
	public static final String FORBIDDEN="403";
	public static final String SYMBOLUNIQUE="代表符号必须唯一";
	private static final long serialVersionUID = 1L;

	private String  code;//200  成功 500异常
	
	private String msg;//详情
	
	private Object data;//数据
	
	private long total;//总数
	//某类下某方法抛出某异常
	public static void syso(String type,String function,String ex){
		System.out.println(type+"下"+function+"抛出"+ex);
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
}
