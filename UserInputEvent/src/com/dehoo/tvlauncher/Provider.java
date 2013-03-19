package com.dehoo.tvlauncher;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 
 * @author houliqiang
 *
 */
public class Provider {
	
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/";
    //数据库名
 	public static final String DATABASE_NAME = "tvmanager.db";
 	// 数据库访问域名
 	public static final String AUTHORITY = "com.dehoo.launcher.tvmanager";
	
	//public static final String TABLENAME = "tvmanager";

    /**
     * tv数据库数据结构
     * @author houliqiang
     */
	public static final class tvmanagerColumns implements BaseColumns {
		
		public static String TABLE_NAME = "/tv";

		public static final Uri CONTENT_URI = Uri.parse("content://"+ AUTHORITY + TABLE_NAME);
		public static final String SERVERNAME = "ServerName";         //节目名
		public static final String FREQENCY = "Freqency";             //频点
		public static final String CHANNELNO = "ChannelNo";	         //频道号
		public static final String SERVICEID = "ServiceId";           //节目ID
		public static final String TS_ID = "Ts_Id";           //TS流ID
		public static final String ON_ID = "On_Id";           //网络ID
		public static final String PMTID = "PmtPid";           //PMT表ID
		public static final String VIDEO_PID = "Video_Pid";           //视频ID
		public static final String PCR_PID = "Pcr_Pid";           //音视频同步ID
		public static final String VIDEOTYPE = "VideoType";           //视频类型，MEPG3，MEPG4，H264,H263 等。
		public static final String  IS_SCRAMBLE = "Is_Scramble";           //是否加密节目	
		public static final String CASYSNUM = "CASysNum";           //CA类型
		public static final String CASYSINDEX = "CASysIndex";           //CA索引
		public static final String PROGTYPE = "ProgType";           //节目类型，音频或者视频
		public static final String VOLUME = "Volume";           //音量
		public static final String LCN = "lcn";           //LCN号
		public static final String SERVICEBATGROUP = "ServiceBatGroup";           //节目所在BAT分组
		public static final String AUDIOTRACK = "AudioTrack";           //声道
		public static final String AUDIODATA = "AudioData"  ;         //音频PID,是一个数组
		public static final String SUBTITLEDATA = "SubtitleData";           //字幕PID，是一个数组，
		public static final String TELTEXTDATA = "TeltextData";           //图文电视PID，是一个数组
		
		
	}
	
}
