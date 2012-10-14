package com.jeff.zhengduan;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.view.View;

public class StoryActivity extends Activity {
	
	private TextView tvContent;
	private ImageView imgPerson;
	static final String LOG_TAG = "StoryMode";
	
	private ArrayList<String> lstContents;
    private int count = 0;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG," Enter Story Mode UI");
		setContentView(R.layout.story);
        this.lstContents = new ArrayList<String>();
        this.lstContents.add("这是第一句话这是第一句话");
        this.lstContents.add("这是第二句话这是第一句话");
        this.lstContents.add("这是第三句话这是第一句话");
        this.lstContents.add("这是第四句话这是第一句话");
        this.lstContents.add("这是第五句话这是第一句话");
        
        this.tvContent = (TextView)findViewById(R.id.tv_dialog_content);
        this.imgPerson = (ImageView)findViewById(R.id.img_person);
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Log.d(LOG_TAG, dm.widthPixels+"*"+dm.heightPixels);
    }


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_UP){
			Log.i(LOG_TAG, "OnTouchEvent -- ACTION_UP");
			
			this.tvContent.setText(lstContents.get(count));
			if(count<=3 && count>=1){
				this.imgPerson.setVisibility(View.VISIBLE);
				this.imgPerson.setImageResource(R.drawable.person_1);
			}else{
				this.imgPerson.setVisibility(View.INVISIBLE);
			}
			count++;
			if(count>=lstContents.size()){
				count = 0;
			}
			return true;
		}
		
		return super.onTouchEvent(event);
	}

}
