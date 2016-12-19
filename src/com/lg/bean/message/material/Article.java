package com.lg.bean.message.material;

/**
 * 获取永久素材返回消息图文item里的文章
 * 
 * @author LuoYi
 *
 */
public class Article {
	private String title;
	private String thumb_media_id;
	private int show_cover_pic;// 是否显示封面，0为false，即不显示，1为true，即显示
	private String author;
	private String digest;
	private String content;
	private String url;
	private String content_source_url;

	public String getTitle() {
		return title;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public int getShow_cover_pic() {
		return show_cover_pic;
	}

	public String getAuthor() {
		return author;
	}

	public String getDigest() {
		return digest;
	}

	public String getContent() {
		return content;
	}

	public String getUrl() {
		return url;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	public void setShow_cover_pic(int show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

}
