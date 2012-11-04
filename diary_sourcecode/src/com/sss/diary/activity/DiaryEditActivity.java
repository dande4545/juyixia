package com.sss.diary.activity;

import java.util.Date;

import com.sss.diary.R;
import com.sss.diary.domain.DiaryContentInfo;
import com.sss.diary.utils.AppUtils;
import com.sss.diary.utils.dbutils.DiaryDbUtil;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class DiaryEditActivity extends Activity implements View.OnClickListener{
	
	private Button btnSave;
	private Button btnCancel;
	private TextView tvNowDate;
	private EditText etTitle;
	private EditText etContent;
	
	private boolean isUpdate = false;
	private DiaryContentInfo modifyDiary = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);
        Intent from = getIntent();
        if(from!=null){
        	this.isUpdate = from.getBooleanExtra("is_update", false);
        	this.modifyDiary = (DiaryContentInfo)from.getSerializableExtra("modify_diary");
        }
        initView();
    }
	
	private void initView(){
		this.btnSave = (Button)findViewById(R.id.btn_diary_edit_save);
		this.btnSave.setOnClickListener(this);
		this.btnCancel = (Button)findViewById(R.id.btn_diary_edit_cancel);
		this.btnCancel.setOnClickListener(this);
		this.tvNowDate = (TextView)findViewById(R.id.tv_diary_edit_nowdate);
		this.etTitle = (EditText)findViewById(R.id.et_diary_edit_title);
		this.etContent = (EditText)findViewById(R.id.et_diary_edit_content);
		if(this.isUpdate){
			//更新
			this.tvNowDate.setText(AppUtils.date2String(this.modifyDiary.getCreateTime(), "yyyy年MM月dd日"));
			this.etTitle.setText(this.modifyDiary.getTitle());
			this.etContent.setText(this.modifyDiary.getContent());
		}else{
			//新增
			String nowDate = AppUtils.date2String(new Date(), "yyyy年MM月dd日");
			this.tvNowDate.setText(nowDate);
			this.etTitle.setText(nowDate);
		}
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_diary_edit_save:
			String title = this.etTitle.getText().toString();
			String content = this.etContent.getText().toString();
			
			if(this.isUpdate){
				//update
				this.modifyDiary.setContent(content);
				this.modifyDiary.setTitle(title);
				this.modifyDiary.setUpdateTime(new Date());
				DiaryDbUtil dbDiary = new DiaryDbUtil(getApplicationContext(), false);
				dbDiary.updateDiary(this.modifyDiary.getId(), this.modifyDiary);
				dbDiary.closeDB();
				Intent intent = new Intent();
				intent.putExtra("update_diary", this.modifyDiary);
				this.setResult(RESULT_OK, intent);
			}else{
				//new
				DiaryContentInfo tempDiary = new DiaryContentInfo();
				tempDiary.setContent(content);
				tempDiary.setTitle(title);
				Date d = new Date();
				tempDiary.setCreateTime(d);
				tempDiary.setUpdateTime(d);
				DiaryDbUtil dbDiary = new DiaryDbUtil(getApplicationContext(), false);
				dbDiary.addNewDiary(tempDiary);
				dbDiary.closeDB();
				Intent intent = new Intent();
				intent.putExtra("new_diary", tempDiary);
				this.setResult(RESULT_OK, intent);
			}
			
			this.finish();
			break;
		case R.id.btn_diary_edit_cancel:
			this.setResult(RESULT_CANCELED);
			this.finish();
			break;
		}
		
	}
	

}
