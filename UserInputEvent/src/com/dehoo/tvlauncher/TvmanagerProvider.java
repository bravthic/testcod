package com.dehoo.tvlauncher;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * 对外提供数据
 * @author houliqiang
 *
 */
public class TvmanagerProvider extends ContentProvider {
	
	public static final String TAG = "Launcher.TvProvider";

    static final String PARAMETER_NOTIFY = "notify"; // 数据发生改变时是否发消息提醒
	private DatabaseHelper mOpenHelper; 
	private SQLiteDatabase db;
//	private static final UriMatcher mUriMatcher;
	private SqlArguments mSqlArguments;
	
    private static final int mUserOprateInfo = 1;
    private static final int mUserOprateSingleInfo = 2;
	
	@Override
	public boolean onCreate() {
		Log.d(TAG, "--------TvProvider---------");
		mOpenHelper = new DatabaseHelper(getContext());
		db = mOpenHelper.getWritableDatabase(); 
		return true;
	}

	@Override
	public String getType(Uri uri) {
		mSqlArguments = new SqlArguments(uri, null, null);
        if (TextUtils.isEmpty(mSqlArguments.where)) {
            return Provider.CONTENT_TYPE + mSqlArguments.table;
        } else {
            return Provider.CONTENT_ITEM_TYPE + mSqlArguments.table;
        }
	}

	/**
	 * 插入一条数据
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		SqlArguments args = new SqlArguments(uri);
        final long rowId = db.insert(args.table, null, initialValues);
        if (rowId <= 0) return null;
        uri = ContentUris.withAppendedId(uri, rowId);
        sendNotify(uri);
        return uri;
	}
	
	/**
	 * 插入多条数据
	 */
	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		SqlArguments args = new SqlArguments(uri);
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			int numValues = values.length;
			for (int i = 0; i < numValues; i++) {
				if (db.insert(args.table, null, values[i]) < 0)
					return 0;
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		sendNotify(uri);
		return values.length;
	}
	
	/**
	 * 删除数据
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count = db.delete(args.table, args.where, args.args);
		if (count > 0)
			sendNotify(uri);
		return count;
	}

	/**
	 * 更新数据
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count = db.update(args.table, values, args.where, args.args);
		if (count > 0)
			sendNotify(uri);
		return count;
	}

	/**
	 * 查询数据
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(args.table);
        Cursor result = qb.query(db, projection, args.where, args.args, null, null, sortOrder);
        
        
        
        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
		
	}

	/**
	 * 发送数据改变广播
	 * @param uri
	 */
    private void sendNotify(Uri uri) {
        String notify = uri.getQueryParameter(PARAMETER_NOTIFY);
        if (notify == null || "true".equals(notify)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
	
    /**
     * 创建数据库
     * @author zhanmin
     *
     */
	private class DatabaseHelper extends SQLiteOpenHelper {
		
		private static final int DATABASE_VERSION = 1;
		private Context mContext;
		
		public DatabaseHelper(Context context) {
			super(context, Provider.DATABASE_NAME, null, DATABASE_VERSION);
			mContext = context;
			Log.d(TAG, "--------DatabaseHelper----------");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG, "--------DatabaseHelper.onCreate------");
			// 鍒涘缓TV琛�
			db.execSQL("CREATE TABLE tv(" 
						+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," 
						+ "ServerName TEXT," 
						+ "Freqency INTEGER ,"
						+ "ChannelNo INTEGER," 
						+ "ServiceId INTEGER," 
						+ "Ts_Id INTEGER," 
						+ "On_Id INTEGER ,"
						+ "PmtPid INTEGER," 
						+ "Video_Pid INTEGER,"  
						+ "Pcr_Pid INTEGER," 
						+ "VideoType INTEGER ,"
						+ "Is_Scramble INTEGER," 
						+ "CASysNum INTEGER,"  
						+ "CASysIndex INTEGER," 
						+ "ProgType INTEGER ,"
						+ "Volume INTEGER," 
						+ "lcn INTEGER," 
						+ "ServiceBatGroup INTEGER," 
						+ "AudioTrack INTEGER ,"
						+ "AudioPid INTEGER," 
						+ "Subtitle_Pid INTEGER," 
						+ "Teltext_Pid INTEGER" 			
						+ ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG, "-----onUpgrade------");
			db.execSQL("DROP TABLE IF EXISTS tv");  
	        onCreate(db);  
		}
	}
	
	/**
	 * 数据库操作参数
	 * @author houliqiang
	 */
	static class SqlArguments {
		public final String table;
		public final String where;
		public final String[] args;

		SqlArguments(Uri url, String where, String[] args) {
			if (url.getPathSegments().size() == 1) {
				this.table = url.getPathSegments().get(0);
				this.where = where;
				this.args = args;
			} else if (url.getPathSegments().size() != 2) {
				throw new IllegalArgumentException("Invalid URI: " + url);
			} else if (!TextUtils.isEmpty(where)) {
				throw new UnsupportedOperationException("WHERE clause not supported: " + url);
			} else {
				this.table = url.getPathSegments().get(0);
				this.where = "_id=" + ContentUris.parseId(url);
				this.args = null;
			}
		}

		SqlArguments(Uri url) {
			if (url.getPathSegments().size() == 1) {
				table = url.getPathSegments().get(0);
				where = null;
				args = null;
			} else {
				throw new IllegalArgumentException("Invalid URI: " + url);
			}
		}
	}
	
    static {

    }

}
