package com.oldox.platform.entity;

public class MyProperty {
	
	private String title;
	private String type;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MyProperty [title=" + title + ", type=" + type + "]";
	}

}
