package com.lg.bean.message.material;

import java.util.List;

/**
 * 获取永久素材返回消息-图文类型-newsitem-content
 * 
 * @author LuoYi
 *
 */
public class NewsItemContent {
	private List<Article> news_item;

	public List<Article> getNews_item() {
		return news_item;
	}

	public void setNews_item(List<Article> news_item) {
		this.news_item = news_item;
	}

}
