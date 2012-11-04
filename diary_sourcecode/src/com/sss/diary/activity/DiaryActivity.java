/*
 * Copyright(C) 2012 SS soft
 * 
 * when       	who     	what, where, why
 *------   		-----     	----------------------------------------------------------
 *10/26/2012	shant		Init version
 * */
package com.sss.diary.activity;

import java.util.ArrayList;

import com.sss.diary.Constant;
import com.sss.diary.R;

import com.sss.diary.domain.DiaryContentInfo;
import com.sss.diary.service.DiaryService;
import com.sss.diary.utils.dbutils.DiaryDbUtil;

import static com.sss.diary.utils.Log.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/***
 * 主页面的Activity
 * @author ShanTao
 * 
 */
public class DiaryActivity extends Activity {
	
	private GridView gvDiaryList;
	private DiaryGridAdapter adapterDiary;
	private ArrayList<DiaryContentInfo> lstDiaryInfo;
	
	private int modifyIndex = -1;
	
	private static final String TAG = Constant.TAG;
	
	private static final int REQUEST_CODE_NEW = 10001;
	private static final int REQUEST_CODE_UPDATE = 10002;
	
	private static final int DIALOG_ID_PROGRESS = 2000101;
	
	private String proMessage = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initView();
        //startService(new Intent(this, DiaryService.class));
                
    }
    
    
    private void initView(){
    	this.gvDiaryList = (GridView)findViewById(R.id.gv_diary_main);
    	
    	this.lstDiaryInfo = new ArrayList<DiaryContentInfo>();
    	this.adapterDiary = new DiaryGridAdapter(getApplicationContext(), lstDiaryInfo);
    	this.gvDiaryList.setAdapter(this.adapterDiary);
    	this.gvDiaryList.setOnItemClickListener(new DiaryGridItemClickListener());
    	this.gvDiaryList.setOnItemLongClickListener(new DiaryGridItemLongClickListener());
    }
    
    @Override
   	protected void onStart() {
   		super.onStart();
   		refreshDiaryList();
   	}
    
    @Override
   	protected void onRestart() {
   		super.onRestart();
   		refreshDiaryList();
   	}
    
    
    /**
     * 刷新列表
     */
    private void refreshDiaryList(){
    	//删除已读取的内容
    	this.lstDiaryInfo.clear();
    	this.lstDiaryInfo.add(getEmptyContent());
    	DiaryDbUtil dbDiary = new DiaryDbUtil(getApplicationContext(), true);
    	ArrayList<DiaryContentInfo> temp = dbDiary.getDiaryContentList();
    	dbDiary.closeDB();
    	if(temp!=null){
    		this.lstDiaryInfo.addAll(temp);
    	}
    	this.adapterDiary.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_diary, menu);
        return true;
    }
    
    /***
     * 短按监听
     * @author ShanTao
     *
     */
    private class DiaryGridItemClickListener implements  AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
			if(pos==0){
				//新增
				Intent toNew = new Intent(getApplicationContext(), DiaryEditActivity.class);
				startActivityForResult(toNew, REQUEST_CODE_NEW);
			}else{
				//修改
				DiaryContentInfo info = lstDiaryInfo.get(pos);
				modifyIndex = pos;
				Intent toUpdate = new Intent(getApplicationContext(), DiaryEditActivity.class);
				toUpdate.putExtra("is_update", true);
				toUpdate.putExtra("modify_diary", info);
				startActivityForResult(toUpdate, REQUEST_CODE_UPDATE);
			}
		}
    	
    }
    
    /***
     * 长按监听
     * @author ShanTao
     *
     */
    private class DiaryGridItemLongClickListener implements AdapterView.OnItemLongClickListener{

    	@Override
		public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
			if(pos!=0){
				final int delIndex = pos;
				AlertDialog confirm = new AlertDialog.Builder(DiaryActivity.this)
										.setTitle("删除？")
										.setMessage("你确定删除这条日记吗?\n注意：删除后无法恢复！")
										.setPositiveButton("是", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												DiaryDbUtil dbUtil = new DiaryDbUtil(getApplicationContext(), false);
												dbUtil.deleteDiary(lstDiaryInfo.get(delIndex).getId());
												dbUtil.closeDB();
												lstDiaryInfo.remove(delIndex);
												adapterDiary.notifyDataSetChanged();
												dialog.dismiss();
												Toast.makeText(getApplicationContext(), "删除完成", Toast.LENGTH_SHORT).show();
											}
										})
										.setNegativeButton("否", null)
										.create();
				confirm.setCanceledOnTouchOutside(false);
				confirm.show();
			}
			return false;
		}
    	
    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			switch(requestCode){
			case REQUEST_CODE_NEW:
				DiaryContentInfo newDiary = (DiaryContentInfo)data.getSerializableExtra("new_diary");
				this.lstDiaryInfo.add(1, newDiary);
				this.adapterDiary.notifyDataSetChanged();
				break;
			case REQUEST_CODE_UPDATE:
				DiaryContentInfo updateDiary = (DiaryContentInfo)data.getSerializableExtra("update_diary");
				this.lstDiaryInfo.set(this.modifyIndex, updateDiary);
				this.adapterDiary.notifyDataSetChanged();
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
	private void showProgressDialog(String message){
		this.proMessage = message;
		showDialog(DIALOG_ID_PROGRESS);
	}
	
	private void cancelProgressDialog(){
		dismissDialog(DIALOG_ID_PROGRESS);
	}

	private DiaryContentInfo getEmptyContent(){
		DiaryContentInfo empty = new DiaryContentInfo();
		empty.setCanModify(false);
		empty.setTitle("");
		empty.setContent("");
		return empty;
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id){
		case DIALOG_ID_PROGRESS:
			ProgressDialog proDialog = new ProgressDialog(getApplicationContext());
			proDialog.setCanceledOnTouchOutside(false);
			proDialog.setMessage(proMessage);
			return proDialog;
		}
		return super.onCreateDialog(id);
	}


	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch(id){
		case DIALOG_ID_PROGRESS:
			ProgressDialog proDialog = (ProgressDialog)dialog;
			proDialog.setMessage(this.proMessage);
		}
		super.onPrepareDialog(id, dialog);
	}
	
	
}
