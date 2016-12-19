package com.lg.bean.message.material;

import java.util.List;

/**
 * 获取永久素材返回消息-其他类型
 * 
 * @author LuoYi
 *
 */
public class OtherMessage extends BaseMessage {
	private List<OtherItem> item;

	public List<OtherItem> getItem() {
		return item;
	}

	public void setItem(List<OtherItem> item) {
		this.item = item;
	}

}
