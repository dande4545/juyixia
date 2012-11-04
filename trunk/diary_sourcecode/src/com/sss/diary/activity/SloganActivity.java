/*
 * Copyright(C) 2012 SS soft
 * 
 * when       	who     	what, where, why
 *------   		-----     	----------------------------------------------------------
 *10/26/2012	shant		Init version
 * */
package com.sss.diary.activity;

import com.sss.diary.Constant;
import com.sss.diary.R;
import com.sss.diary.service.DiaryService;
import com.sss.diary.utils.AppUtils;
import com.sss.diary.utils.dbutils.DatabaseUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import static com.sss.diary.utils.Log.*;



public class SloganActivity extends Activity {
	
	private Handler mHandy = null;
	private boolean evtSent = false;
	private final static int TIME_DELAY = 4000;
	private final static int EVT_SLOGAN_END = 1;
    
	private static final String TAG = Constant.TAG;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //mGLSurfaceView = new GLSurfaceView(this);
        //mGLSurfaceView.setRenderer(new CubeRenderer(false));
        //setContentView(mGLSurfaceView);
        setContentView(R.layout.activity_slogan);
        
        initAll();
        if(null != mHandy){
        	mHandy.sendEmptyMessageDelayed(EVT_SLOGAN_END, TIME_DELAY);
        	evtSent = true;
        }else{
        	e(TAG, "SloganActivity -- mHandy is null");
        }
    }
    @Override
    public void onResume(){
    	i(TAG, "SloganActivity -- resume");
    	super.onResume();
    	//mGLSurfaceView.onResume();
    	if(evtSent){/*Avoid to send more than one time*/
    		i(TAG, "SloganActivity -- evt sent ok");
    	}else{
    		mHandy.sendEmptyMessageDelayed(EVT_SLOGAN_END, TIME_DELAY);
    	}
    }
    public void stopExcMessage(int what){
    	if(mHandy.hasMessages(what)){
    		mHandy.removeMessages(what);
    		i(TAG, "SloganActivity -- message "+what+" removed");
    		evtSent = false;
    	};
    }
    
    public void jumpToEntry(){
    	Intent i = new Intent(Intent.ACTION_MAIN);
		i.setClass(getApplication(), DiaryActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
    }
    @Override
    public void onPause(){
    	super.onPause();
    	//mGLSurfaceView.onPause();
    	stopExcMessage(EVT_SLOGAN_END);
    }
    @Override
    public void onDestroy(){
    	stopExcMessage(EVT_SLOGAN_END);
    	super.onDestroy();
    }
    @Override
    public void onStop(){
    	super.onStop();
    	stopExcMessage(EVT_SLOGAN_END);
    }
    
    public void initAll(){
    	mHandy = new Handler(){
    		public void handleMessage(Message m){
    			d(TAG, "SloganActivity -- handle event is "+m.what);
    			switch (m.what){
    			case EVT_SLOGAN_END:
    				jumpToEntry();
    				break;
    			default:
    				i(TAG, "SloganActivity -- default message handle, m.what is "+m.what);
    			}
    		}
    	};
    	//新建数据库实例
		DatabaseUtil dbUtil = new DatabaseUtil(getApplicationContext(),true);
		dbUtil.closeDB();
		//启动后台服务
		if(!AppUtils.isServiceRunning(getApplicationContext(), DiaryService.class.getName())){
			startService(new Intent(getApplicationContext(), DiaryService.class));
		}
		
    }
    
   
}
