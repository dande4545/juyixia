package com.sss.diary.utils.dbutils;

import com.sss.diary.Constant;
import com.sss.diary.utils.Log;

import android.content.ContentValues;
import android.content.Context;
import static com.sss.diary.utils.dbutils.DBConstant.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBHelper作为SQLite的一个助手类，提供两方面的功能：
 * 第一，getReadableDatabase,getWritableDatabase两个方法可以获得SQLiteDatebase对象，通过该对象进行数据库操作
 * 第二，提供onCreate,onUpgrade两个回调函数，允许我们创建和升级数据库时，进行相关处理
 *
 */

public class DBHelper extends SQLiteOpenHelper {
	
	private Context mContext;
	//在SQLiteOpenHelper的子类中必须有这个构造函数
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.mContext = context;
	}
	
	public DBHelper(Context context, String name){
		this(context, name, VERSION);
		this.mContext = context;
	}
	
	public DBHelper(Context context, String name, int version){
		this(context, name, null, version);
		this.mContext = context;
	}
	
	//第一创建数据库时执行该函数，实际上是第一次得到SQLiteDatabase对象时,才会调用该方法
	@Override
	public void onCreate(SQLiteDatabase db) {
		//执行Sql语句，建表
		
		//用户表
		StringBuilder initDBSql = new StringBuilder();
		initDBSql.append("CREATE TABLE T_DiaryContent(");
		initDBSql.append("ID integer primary key autoincrement,");
		initDBSql.append("Title varchar(20) not null,");
		initDBSql.append("Content varchar(140) not null,");
		initDBSql.append("CreateTime datetime,");
		initDBSql.append("UpdateTime datetime,");
		initDBSql.append("PicPath varchar(100),");
		initDBSql.append("CanModify integer(1),");
		initDBSql.append("Location varchar(100))");
		db.execSQL(initDBSql.toString());
		
		
		Log.d(Constant.TAG, "DBHelper ----> database init complete");
		
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(Constant.TAG, "database upgrade");
	}
	
}
