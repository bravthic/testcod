package com.dehoo.tvlauncher;

import com.dehoo.tvlauncher.Provider;
import com.dehoo.tvlauncher.TvInfo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends Activity {

	private static final String TAG = "Launcher.TvProvider";

	private ContentResolver mProvider;
	private Cursor cur;
	private Uri mUri = Uri.parse("content://com.dehoo.launcher.tvmanager");

	@SuppressWarnings("null")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "START LOG-------------");
		inSertTvInfo(null, null);
		queryTVInfo();
	}

	private int inSertTvInfo(TvInfo info, String tableName) {

		TvInfo tvInfo = new TvInfo();
		tvInfo.ServerName = "cctv1";
		tvInfo.Freqency = 474;
		tvInfo.ChannelNo = 1;

		ContentValues values = new ContentValues();
		values.put(Provider.tvmanagerColumns.SERVERNAME, tvInfo.ServerName);
		values.put(Provider.tvmanagerColumns.FREQENCY, tvInfo.Freqency);
		values.put(Provider.tvmanagerColumns.CHANNELNO, tvInfo.ChannelNo);

		Log.d(TAG, "Provider.tvmanagerColumns.CONTENT_URI = "
				+ Provider.tvmanagerColumns.CONTENT_URI);

		Uri uri = getContentResolver().insert(
				Uri.parse("content://" + Provider.AUTHORITY + "/tv"), values);
		Log.i(TAG, "insert uri=" + uri);
		String lastPath = uri.getPathSegments().get(1);
		if (TextUtils.isEmpty(lastPath)) {
			Log.i(TAG, "insert failure!");
		} else {
			Log.i(TAG, "insert success! the id is " + lastPath);
		}

		return Integer.parseInt(lastPath);
	}

	private void queryTVInfo() {
		Cursor cur;
		cur = getContentResolver().query(Uri.parse("content://" + Provider.AUTHORITY + "/tv"), null,
				null, null, null);
		if(cur==null){
			Log.d("dehoo","null");
			return;
		}
		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext())

		{

			int mCurTimeColumn = cur.getColumnIndex("ServerName");

			int mTimeColumn = cur.getColumnIndex("Freqency");

			int keyID = cur.getColumnIndex("ChannelNo");



			String key = cur.getString(mCurTimeColumn);
			
			Log.d("dehoo", "ServerName=" + key);
			
			String CurTimeColumn = cur.getString(mCurTimeColumn);

			int TimeColum = cur.getInt(mTimeColumn);
			Log.d("dehoo", "Freqency=" + TimeColum);
		}
	}
	
	private void deleteInfo () {
		
		
	}

}

