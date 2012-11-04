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
 * DBHelper��ΪSQLite��һ�������࣬�ṩ������Ĺ��ܣ�
 * ��һ��getReadableDatabase,getWritableDatabase�����������Ի��SQLiteDatebase����ͨ���ö���������ݿ����
 * �ڶ����ṩonCreate,onUpgrade�����ص��������������Ǵ������������ݿ�ʱ��������ش���
 *
 */

public class DBHelper extends SQLiteOpenHelper {
	
	private Context mContext;
	//��SQLiteOpenHelper�������б�����������캯��
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
	
	//��һ�������ݿ�ʱִ�иú�����ʵ�����ǵ�һ�εõ�SQLiteDatabase����ʱ,�Ż���ø÷���
	@Override
	public void onCreate(SQLiteDatabase db) {
		//ִ��Sql��䣬����
		
		//�û���
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
