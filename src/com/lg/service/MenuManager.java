package com.lg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lg.bean.pojo.AccessToken;
import com.lg.bean.pojo.Button;
import com.lg.bean.pojo.CommonButton;
import com.lg.bean.pojo.ComplexButton;
import com.lg.bean.pojo.Menu;
import com.lg.bean.pojo.ViewButton;
import com.lg.global.MyConfig;
import com.lg.utils.WeiXinUtil;

public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wx271aed988aecd65e";
		// 第三方用户唯一凭证密钥
		String appSecret = "49a237baff015a83e1018c8a90427dc5";

		// 调用接口获取access_token
		AccessToken at = WeiXinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeiXinUtil.createMenu(getMenu(), at.getToken());
			// 判断菜单创建结果
			if (0 == result) {
				log.info("菜单创建成功！");
			} else {
				log.info("菜单创建失败，错误码：" + result);
			}
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {

		ViewButton btn11 = new ViewButton();
		btn11.setName("骑马预约");
		btn11.setType("view");
		btn11.setUrl(MyConfig.ipConfig + "member/order.jsp");

		CommonButton btn12 = new CommonButton();
		btn12.setName("扫码验证");
		btn12.setType("scancode_push");
		btn12.setKey("12");

		CommonButton btn13 = new CommonButton();
		btn13.setName("马匹信息");
		btn13.setType("click");
		btn13.setKey("13");

		CommonButton btn14 = new CommonButton();
		btn14.setName("个人信息");
		btn14.setType("click");
		btn14.setKey("14");

		CommonButton btn21 = new CommonButton();
		btn21.setName("近期活动");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn22 = new CommonButton();
		btn22.setName("冬令营招生");
		btn22.setType("click");
		btn22.setKey("22");

		ViewButton btn23 = new ViewButton();
		btn23.setName("往期精彩");
		btn23.setType("view");
		btn23.setUrl(
				"http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MjM5MjIzODEzNA==#wechat_webview_type=1&wechat_redirect");

		CommonButton btn24 = new CommonButton();
		btn24.setName("优惠办卡");
		btn24.setType("click");
		btn24.setKey("24");

		CommonButton btn31 = new CommonButton();
		btn31.setName("感悟投稿");
		btn31.setType("click");
		btn31.setKey("31");

		CommonButton btn32 = new CommonButton();
		btn32.setName("合作事宜");
		btn32.setType("click");
		btn32.setKey("32");

		CommonButton btn33 = new CommonButton();
		btn33.setName("人才招聘");
		btn33.setType("click");
		btn33.setKey("33");

		CommonButton btn34 = new CommonButton();
		btn34.setName("关于乐道");
		btn34.setType("click");
		btn34.setKey("34");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("会员服务");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("精彩活动");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("联系大叔");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34 });

		/**
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“联系大叔”，而直接是“关于我们”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
