package com.sss.diary.utils.dbutils;

import java.util.ArrayList;

import com.sss.diary.Constant;
import com.sss.diary.domain.DiaryContentInfo;
import com.sss.diary.utils.AppUtils;
import com.sss.diary.utils.Log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DiaryDbUtil extends DatabaseUtil {

	private static final String TAG = Constant.TAG;
	
	public DiaryDbUtil(Context mContext, boolean readOnly) {
		super(mContext, readOnly);		
	}
	
	public boolean addNewDiary(DiaryContentInfo diary){
		try{
			ContentValues values = getDiaryContentValue(diary);
			int id = (int)insert("T_DiaryContent", null, values);
			diary.setId(id);
			return true;
		}catch(Exception ex){
			Log.e(TAG, "DiaryDbUtil -- addNewDiary error: " + ex.getMessage(), ex);
			return false;
		}
	}
	
	public boolean deleteDiary(int diaryId){
		try{
			int i = delete("T_DiaryContent", "ID=?", new String[]{String.valueOf(diaryId)});
			Log.d(Constant.TAG, "DiaryDbUtil -- deleteDiary:success,id="+diaryId+",result="+i);
			return true;
		}catch(Exception ex){
			Log.e(TAG, "DiaryDbUtil -- deleteDiary error:" + ex.getMessage(), ex);
			return false;
		}
		
	}
	
	public boolean updateDiary(int diaryId, DiaryContentInfo newInfo){
		try{
			int i = update("T_DiaryContent", getDiaryContentValue(newInfo), "ID=?", new String[]{String.valueOf(diaryId)});
			Log.d(Constant.TAG, "DiaryContentInfo -- updateDiary: success,id="+diaryId+",result="+i);
			newInfo.setId(diaryId);
			return true;
		}catch(Exception ex){
			Log.e(TAG, "DiaryDbUtil --updateDiary error:" + ex.getMessage(), ex);
			return false;
		}
		
	}
	
	
	public ArrayList<DiaryContentInfo> getDiaryContentList(){
		ArrayList<DiaryContentInfo> lstDiary = null; 
		try{
			String where = null;
			Cursor cursor = db.query("T_DiaryContent", null, where, null, null, null, "CreateTime DESC");
			if(cursor.getCount()<=0){
				Log.w(Constant.TAG, "DiaryDbUtil -- getDiaryContentList size=0");
				cursor.close();
			}else{
				lstDiary = new ArrayList<DiaryContentInfo>(cursor.getCount());
				while(cursor.moveToNext()){
					DiaryContentInfo diary = new DiaryContentInfo();
					diary.setId(cursor.getInt(cursor.getColumnIndex("ID")));
					diary.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
					diary.setContent(cursor.getString(cursor.getColumnIndex("Content")));
					diary.setCreateTime(AppUtils.string2Date(cursor.getString(cursor.getColumnIndex("CreateTime"))));
					diary.setUpdateTime(AppUtils.string2Date(cursor.getString(cursor.getColumnIndex("UpdateTime"))));
					diary.setPicPath(cursor.getString(cursor.getColumnIndex("PicPath")));
					diary.setLocation(cursor.getString(cursor.getColumnIndex("Location")));
					lstDiary.add(diary);
				}
			}		
			return lstDiary;	
		}catch(Exception ex){
			Log.e(TAG, "DiaryDbUtil -- getDiaryContent error:" + ex.getMessage(), ex);
		}
		
		return lstDiary;
	}
	
	private ContentValues getDiaryContentValue(DiaryContentInfo info){
		
		ContentValues values = new ContentValues();

		if(info.getTitle()!=null){
			values.put("Title", info.getTitle());
		}
		if(info.getContent()!=null){
			values.put("Content", info.getContent());
		}
		if(info.getCreateTime()!=null){
			values.put("CreateTime", AppUtils.date2String(info.getCreateTime()));
		}
		if(info.getUpdateTime()!=null){
			values.put("UpdateTime", AppUtils.date2String(info.getUpdateTime()));
		}
		if(info.getPicPath()!=null){
			values.put("PicPath", info.getPicPath());
		}
		if(info.isCanModify()){
			values.put("CanModify", 1);
		}else{
			values.put("CanModify", 0);
		}
		if(info.getLocation()!=null){
			values.put("Location", info.getLocation());
		}
		
		return values;
	}

}
