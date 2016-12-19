package com.lg.bean.message.material;

import java.util.List;

/**
 * 获取永久素材返回消息-图文类型
 * 
 * @author LuoYi
 *
 */
public class NewsMessage extends BaseMessage {
	private List<NewsItem> item;

	public List<NewsItem> getItem() {
		return item;
	}

	public void setItem(List<NewsItem> item) {
		this.item = item;
	}

}
