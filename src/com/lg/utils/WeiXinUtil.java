package com.lg.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.lg.bean.message.material.Article;
import com.lg.bean.message.material.BaseMessage;
import com.lg.bean.message.material.Item;
import com.lg.bean.message.material.NewsItem;
import com.lg.bean.message.material.NewsItemContent;
import com.lg.bean.message.material.NewsMessage;
import com.lg.bean.message.material.OtherMessage;
import com.lg.bean.message.material.Video;
import com.lg.bean.pojo.AccessToken;
import com.lg.bean.pojo.Menu;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 公众平台通用接口工具类
 * 
 * @author LuoYi
 *
 */
public class WeiXinUtil {
	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 菜单创建（POST） 限100（次/天）
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 获取素材总数
	public final static String GET_MATERIALCOUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	// 获取素材列表
	public final static String BATCHGET_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	// 获取永久素材
	public final static String GET_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			if (buffer.toString().startsWith("{")) {
				jsonObject = JSONObject.fromObject(buffer.toString());
			}
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	/**
	 * 获取素材总数
	 * 
	 * @param accessToken
	 *            有效的access_token
	 * @return
	 */
	public static int[] getMaterialCount(String accessToken) {
		// 拼装url
		String url = GET_MATERIALCOUNT_URL.replace("ACCESS_TOKEN", accessToken);

		// 调用接口获取素材总数
		JSONObject countObject = httpRequest(url, "GET", null);
		int[] counts = new int[4];
		counts[0] = (int) countObject.get("voice_count");
		counts[1] = (int) countObject.get("video_count");
		counts[2] = (int) countObject.get("image_count");
		counts[3] = (int) countObject.get("news_count");
		return counts;
	}

	/**
	 * 获取素材列表
	 * 
	 * @param type
	 *            素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset
	 *            从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count
	 *            返回素材的数量，取值在1到20之间
	 * @param accessToken
	 *            有效的access_token
	 * @return
	 */
	public static List<BaseMessage> batchGetMaterial(String type, int offset, int count, String accessToken) {
		// 拼装url
		String url = BATCHGET_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);

		// 将参数转换成json字符串
		String json = "{\"type\":\"" + type + "\",\"offset\":" + offset + ",\"count\":" + count + "}";
		// 调用接口获取素材列表
		JSONObject materialObject = httpRequest(url, "POST", json);
		if (materialObject == null) {
			return null;
		}
		if (materialObject.containsKey("errcode")) {
			log.error("获取素材列表失败 errcode:{} errmsg:{}", materialObject.getInt("errcode"),
					materialObject.getString("errmsg"));
		} else {
			JSONArray itemArray = materialObject.getJSONArray("item");// 消息条目
			List<BaseMessage> baseMessages = new ArrayList<>();
			for (int i = 0; i < itemArray.size(); i++) {
				JSONObject itemObject = itemArray.getJSONObject(i);
				if (itemObject != null && "news".equals(type)) {// 图文消息
					NewsMessage newsMessage = new Gson().fromJson(materialObject.toString(), NewsMessage.class);
					baseMessages.add(newsMessage);
				} else if (itemObject != null) {// 其他消息
					OtherMessage otherMessage = new Gson().fromJson(materialObject.toString(), OtherMessage.class);
					baseMessages.add(otherMessage);
				}
			}
			return baseMessages;
		}
		return null;
	}

	/**
	 * 获取永久素材
	 * 
	 * @param media_id
	 *            素材id
	 * @param accessToken
	 *            有效的access_token
	 * @return 图文消息返回文章列表，视频消息返回视频，其他返回1（自行保存为文件）
	 */
	public static Object getMaterial(String media_id, String accessToken) {
		// 拼装url
		String url = GET_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);

		// 将参数转换成json字符串
		String json = "{\"media_id\":\"" + media_id + "\"}";
		// 调用接口获取素材
		JSONObject materialObject = httpRequest(url, "POST", json);
		if (materialObject == null) {// 其他类型的素材消息，则响应的直接为素材的内容，开发者可以自行保存为文件
			return 1;
		}
		if (materialObject.containsKey("errcode")) {
			log.error("获取素材失败 errcode:{} errmsg:{}", materialObject.getInt("errcode"),
					materialObject.getString("errmsg"));
		} else {
			log.info(materialObject.get("news_item").toString());
			NewsMessage newsMessage = new NewsMessage();
			if (materialObject.containsKey("news_item")) {// 图文消息
				JSONArray news_item = materialObject.getJSONArray("news_item");
				List<Article> articles = new ArrayList<>();
				for (int i = 0; i < news_item.size(); i++) {
					JSONObject articleObject = news_item.getJSONObject(i);
					Article article = new Gson().fromJson(articleObject.toString(), Article.class);
					articles.add(article);
				}
				return articles;
			} else if (materialObject.containsKey("down_url")) {// 视频消息
				Video video = new Gson().fromJson(materialObject.toString(), Video.class);
				return video;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wx271aed988aecd65e";
		// 第三方用户唯一凭证密钥
		String appSecret = "49a237baff015a83e1018c8a90427dc5";

		// 调用接口获取access_token
		AccessToken at = WeiXinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单
			 List<BaseMessage> baseMessages =
			 WeiXinUtil.batchGetMaterial("news", 0, 3, at.getToken());
			 log.info(((NewsMessage)baseMessages.get(0)).getItem().get(0).getContent().getNews_item().get(0).getUrl());
			 log.info(WeiXinUtil.getMaterial("SyhUOBYL9ru7VipdMhpv7EpU6lr33nAK25XRfnXKkTo", at.getToken()) + "");
		}
	}
}
