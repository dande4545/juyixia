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
	 * 同步的插入方法
	 * @param table
	 * @param nullColumnHack
	 * @param values 
	 * @return 插入的行数（ID）
	 */
	protected long insert(String table, String nullColumnHack, ContentValues values){
		synchronized(dbLock){
			return db.insert(table, nullColumnHack, values);
		}
	}
	
	/***
	 * 同步的更新方法
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
	 * 同步的删除方法
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
	 * 同步的执行Sql方法
	 * @param sql
	 */
	protected void execSQL(String sql){
		synchronized(dbLock){
			db.execSQL(sql);
		}
	} 
	

}
