package com.smht.service.core;

import java.io.Serializable;

/**
 * 方法调用返回结果
 *
 * @author fanhaoyu
 * @since 2015年7月15日
 */
public class InvokeResult<T> implements Serializable {

	private static final long serialVersionUID = 7009804612635235417L;
	// 响应结果码，默认为0-成功
	private int returnCode = 0;
	// 消息
	private String message;
	// 返回的数据
	private T data;

	public InvokeResult() {
	}

	public InvokeResult(int returnCode, String message) {
		this.returnCode = returnCode;
		this.message = message;
	}

	public InvokeResult(int returnCode, String message, T data) {
		this.returnCode = returnCode;
		this.message = message;
		this.data = data;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
