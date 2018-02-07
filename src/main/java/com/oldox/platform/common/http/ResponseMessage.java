package com.oldox.platform.common.http;

import java.io.Serializable;

public class ResponseMessage<T> implements Serializable {

	// 响应码
	private String code;

	// 响应消息
	private String message;

	// 数据
	private T data;

	// 是否成功
	private boolean isOk;

	public ResponseMessage() {

	}

	public ResponseMessage(boolean isOk) {
		this.isOk = isOk;
	}

	public ResponseMessage(boolean isOk, String code, String message) {
		this.isOk = isOk;
		this.code = code;
		this.message = message;
	}

	public ResponseMessage(boolean isOk, String code, String message, T data) {
		this.isOk = isOk;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(boolean isOk) {
		this.isOk = isOk;
	}

}
