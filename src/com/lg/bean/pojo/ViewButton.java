package com.lg.bean.pojo;

/**
 * 网页按钮（子按钮） 直接跳转网页
 * 
 * @author LuoYi
 *
 */
public class ViewButton extends Button {
	private String type;
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
