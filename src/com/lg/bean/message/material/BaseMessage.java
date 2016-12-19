package com.lg.bean.message.material;

/**
 * 获取永久素材返回消息基类
 * @author LuoYi
 *
 */
public class BaseMessage {
	private int total_count;
	private int item_count;
	public int getTotal_count() {
		return total_count;
	}
	public int getItem_count() {
		return item_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}

}
