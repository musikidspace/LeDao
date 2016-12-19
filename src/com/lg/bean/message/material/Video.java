package com.lg.bean.message.material;

/**
 * 获取永久素材返回视频消息item
 * 
 * @author LuoYi
 *
 */
public class Video{
	private String title;
	private String description;
	private String down_url;
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getDown_url() {
		return down_url;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDown_url(String down_url) {
		this.down_url = down_url;
	}

}
