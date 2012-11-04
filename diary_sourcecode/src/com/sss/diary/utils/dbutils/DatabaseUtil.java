package com.sss.diary.utils.dbutils;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import static com.sss.diary.utils.dbutils.DBConstant.*;

public class DatabaseUtil {
	

	protected SQLiteDatabase db = null;
	protected DBHelper dbHelper = null;
	
	public static final Object dbLock = new Object();
	
	
	public void closeDB(){
		synchronized (dbLock) {
			dbHelper.close();
			this.db.close();
		}
	}
	
	public DatabaseUtil(Context mContext, boolean readOnly){
		dbHelper = new DBHelper(mContext, DB_NAME);
		if(readOnly){
//			Log.d(Constant.TAG, "DBUtil -- getReadableDatabase");
			db = dbHelper.getReadableDatabase();
		}else{
			db = dbHelper.getWritableDatabase();
		}
	}
	
	/***
	 * ͬ���Ĳ��뷽��
	 * @param table
	 * @param nullColumnHack
	 * @param values 
	 * @return �����������ID��
	 */
	protected long insert(String table, String nullColumnHack, ContentValues values){
		synchronized(dbLock){
			return db.insert(table, nullColumnHack, values);
		}
	}
	
	/***
	 * ͬ���ĸ��·���
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	protected int update(String table, ContentValues values, String whereClause, String[] whereArgs){
		synchronized(dbLock){
			return db.update(table, values, whereClause, whereArgs);			
		}
	}
	
	/***
	 * ͬ����ɾ������
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	protected int delete(String table, String whereClause, String[] whereArgs){
		synchronized(dbLock){
			return db.delete(table, whereClause, whereArgs);
		}
	}
	
	/***
	 * ͬ����ִ��Sql����
	 * @param sql
	 */
	protected void execSQL(String sql){
		synchronized(dbLock){
			db.execSQL(sql);
		}
	} 
	

}
