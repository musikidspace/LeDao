package com.lg.bean.message.response;

/**
 * 音乐消息
 * 
 * @author LuoYi
 *
 */
public class MusicMessage extends BaseMessage {
	// 音乐  
    private Music Music;  
  
    public Music getMusic() {  
        return Music;  
    }  
  
    public void setMusic(Music music) {  
        Music = music;  
    }
}
