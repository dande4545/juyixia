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
        this.lstContents.add("���ǵ�һ�仰���ǵ�һ�仰");
        this.lstContents.add("���ǵڶ��仰���ǵ�һ�仰");
        this.lstContents.add("���ǵ����仰���ǵ�һ�仰");
        this.lstContents.add("���ǵ��ľ仰���ǵ�һ�仰");
        this.lstContents.add("���ǵ���仰���ǵ�һ�仰");
        
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
