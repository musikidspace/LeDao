package com.lg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lg.bean.message.response.Article;
import com.lg.bean.message.response.ImageMessage;
import com.lg.bean.message.response.NewsMessage;
import com.lg.bean.message.response.TextMessage;
import com.lg.global.MyConfig;
import com.lg.utils.MessageUtil;

/**
 * 核心服务类
 * 
 * @author LuoYi
 *
 */
public class CoreService {

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			System.out.println(fromUserName);
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			System.out.println(toUserName);
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息内容
			String content = requestMap.get("Content");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);

			List<Article> articleList = new ArrayList<Article>();

			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				if (content != null && (content.contains("地址") || content.contains("线路") || content.contains("路线")
						|| content.contains("位置") || content.contains("方位"))) {
					// Article article1 = new Article();
					// article1.setTitle("马场位置");
					// article1.setDescription("");
					// article1.setPicUrl(
					// "https://mmbiz.qlogo.cn/mmbiz/jHWjWCTB9ZNhicT358gbqtOUicIzcODZGCPAEyqILVgVwA3QgYrez9t7pqExeZiaQoF6k07xGScwCddo83QQqaPRg/0?wx_fmt=jpeg");
					// article1.setUrl(MyConfig.ipConfig +
					// "equine/location.jsp");
					//
					// articleList.add(article1);
					// newsMessage.setArticleCount(articleList.size());
					// newsMessage.setArticles(articleList);
					// respMessage = MessageUtil.newsMessageToXml(newsMessage);

					ImageMessage imageMessage = new ImageMessage();
					imageMessage.setPicUrl(
							"https://mmbiz.qlogo.cn/mmbiz/jHWjWCTB9ZNhicT358gbqtOUicIzcODZGCPAEyqILVgVwA3QgYrez9t7pqExeZiaQoF6k07xGScwCddo83QQqaPRg/0?wx_fmt=jpeg");
					respMessage = MessageUtil.imageMessageToXml(imageMessage);
				}
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "欢迎关注乐道大叔！\n\n将乐道会员与微信<a href=\"" + MyConfig.ipConfig + "member/bind.jsp\">绑定</a>，\n随时随地管理您的会员信息。\n还没有乐道会员？\n点击<a href=\"" + MyConfig.ipConfig + "member/register.jsp\">注册</a>\n";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("12")) {
						//respContent = "调用扫一扫！";
					}else if (eventKey.equals("13")) {
						respContent = "马匹信息！";
					} else if (eventKey.equals("14")) {
						Article article1 = new Article();
						article1.setTitle("个人信息");
						article1.setDescription("您是XX会员\n总次数X次\n剩余X次");
						article1.setPicUrl(
								"https://mmbiz.qlogo.cn/mmbiz/jHWjWCTB9ZOM4niaRcPdUSkEeBZYxev95k4WBmawC9BR5bnsArCjRt27GwicZSJdFe0iaUxHp7JKlRUarYMnAAOAQ/0");
						article1.setUrl(MyConfig.ipConfig + "member/housingrecord.jsp");

						articleList.add(article1);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
					} else if (eventKey.equals("21")) {
						Article article1 = new Article();
						article1.setTitle("近期活动1");
						article1.setDescription("");
						article1.setPicUrl(
								"https://mmbiz.qlogo.cn/mmbiz/jHWjWCTB9ZOM4niaRcPdUSkEeBZYxev95k4WBmawC9BR5bnsArCjRt27GwicZSJdFe0iaUxHp7JKlRUarYMnAAOAQ/0");
						article1.setUrl("https://www.baidu.com/s?wd=%E8%BF%91%E6%9C%9F%E6%B4%BB%E5%8A%A81");

						Article article2 = new Article();
						article2.setTitle("近期活动2");
						article2.setDescription("");
						article2.setPicUrl(
								"https://mmbiz.qlogo.cn/mmbiz/jHWjWCTB9ZOM4niaRcPdUSkEeBZYxev95k4WBmawC9BR5bnsArCjRt27GwicZSJdFe0iaUxHp7JKlRUarYMnAAOAQ/0");
						article2.setUrl("https://www.baidu.com/s?wd=%E8%BF%91%E6%9C%9F%E6%B4%BB%E5%8A%A82");

						Article article3 = new Article();
						article3.setTitle("近期活动3");
						article3.setDescription("");
						article3.setPicUrl(
								"https://mmbiz.qlogo.cn/mmbiz/jHWjWCTB9ZOM4niaRcPdUSkEeBZYxev95k4WBmawC9BR5bnsArCjRt27GwicZSJdFe0iaUxHp7JKlRUarYMnAAOAQ/0");
						article3.setUrl("https://www.baidu.com/s?wd=%E8%BF%91%E6%9C%9F%E6%B4%BB%E5%8A%A83");

						articleList.add(article1);
						articleList.add(article2);
						articleList.add(article3);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
					} else if (eventKey.equals("22")) {
						respContent = "冬令营招生！";
					} else if (eventKey.equals("24")) {
						respContent = "优惠办卡！";
					} else if (eventKey.equals("31")) {
						respContent = "感悟投稿！";
					} else if (eventKey.equals("32")) {
						respContent = "合作事宜！";
					} else if (eventKey.equals("33")) {
						respContent = "人才招聘！";
					} else if (eventKey.equals("34")) {
						respContent = "关于乐道！";
					}
				}
			}
			textMessage.setContent(respContent);
			respMessage = respMessage == null ? MessageUtil.textMessageToXml(textMessage) : respMessage;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}
