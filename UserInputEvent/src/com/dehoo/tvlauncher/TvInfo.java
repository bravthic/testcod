package com.dehoo.tvlauncher;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;

public class TvInfo {
	public static final int  DHDBASE_MAX_AUDIO = 8 ;
	public static final int  DHDBASE_MAX_SUB = 10 ;
	public static final int  DHDBASE_MAX_TELTEXT = 5 ;
	
	
	public String ServerName = "null"; // 节目名
	public int Freqency = 0xffff; // 频点
	public int ChannelNo = 0xffff; // 频道号
	public int ServiceId = 0xffff; // 节目ID
	public int Ts_Id = 0xffff; // TS流ID
	public int On_Id = 0xffff; // 网络ID
	public int PmtPid = 0xffff; // PMT表ID
	public int Video_Pid = 0xffff; // 视频ID
	public int Pcr_Pid = 0xffff; // 音视频同步ID
	public int VideoType = 0xffff; // 视频类型，MEPG3，MEPG4，H264,H263 等。
	public int Is_Scramble = 0xffff; // 是否加密节目
	public int CASysNum = 0xffff; // CA类型
	public int CASysIndex = 0xffff; // CA索引
	public int ProgType = 0xffff; // 节目类型，音频或者视频
	public int Volume = 0xffff; // 音量
	public int lcn = 0xffff; // LCN号
	public int ServiceBatGroup = 0xffff; // 节目所在BAT分组
	public int AudioTrack = 0xffff; // 声道
	public int Subtitle_Pid = 0xffff; // 字幕PID
	public int Teltext_Pid = 0xffff; // 图文电视PID
	public int Audio_Pid = 0xffff; // 音频PID
	
	
//
//	public class AudioData {
//		int Audiopids[] = new int[DHDBASE_MAX_AUDIO];
//		String AudioNum;
//		int SelectAudio;
//
//	}
//	public class SubtitleData {
//		int Subpids[] = new int[DHDBASE_MAX_SUB];
//
//	}
//	public class TeltextData {
//		int TeletextPid[] = new int[DHDBASE_MAX_TELTEXT];
//
//	}
//	
//
//	public TvInfo()
//	{
//		
//	}

}
