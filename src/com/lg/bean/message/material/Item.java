package com.lg.bean.message.material;

/**
 * 获取永久素材返回消息item基类
 * 
 * @author LuoYi
 *
 */
public class Item {
	private String media_id;
	private long update_time;

	public String getMedia_id() {
		return media_id;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

}
