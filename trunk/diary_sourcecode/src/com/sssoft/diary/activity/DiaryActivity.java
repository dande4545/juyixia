package com.sssoft.diary.activity;

import com.sssoft.diary.R;
import com.sssoft.diary.R.layout;
import com.sssoft.diary.R.menu;
import com.sssoft.diary.service.DiaryService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

/***
 * Ö÷Ò³ÃæµÄActivity
 * @author ShanTao
 * 
 */
public class DiaryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        startService(new Intent(this, DiaryService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_diary, menu);
        return true;
    }
}
