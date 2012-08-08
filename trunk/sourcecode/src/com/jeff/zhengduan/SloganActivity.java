/*
 * Copyright(C) 2012 SS soft
 * 
 * when       	who     what, where, why
 *------   		---     ----------------------------------------------------------
 *06/29/2012	lzj		Add OpenGL Cube model for Slogan effect
 *06/26/2012	lzj		Init version
 * */
package com.jeff.zhengduan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import android.opengl.GLSurfaceView;
import com.jeff.zhengduan.SloganEffectModel.CubeRenderer;

public class SloganActivity extends Activity {
	private Handler mHandy = null;
	private GLSurfaceView mGLSurfaceView;
	private boolean evtSent = false;
	private LinearLayout mLl = null;
	final static String LOG_TAG = "ZDJJJ";
	final static String LOG_ITEM = " SLOGAN ";
	final static int TIME_DELAY = 4000;
	final static int EVT_SLOGAN_END = 1;
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //mGLSurfaceView = new GLSurfaceView(this);
        //mGLSurfaceView.setRenderer(new CubeRenderer(false));
        //setContentView(mGLSurfaceView);
        setContentView(R.layout.slogan);
        
        initAll();
        if(null != mHandy){
        	mHandy.sendEmptyMessageDelayed(EVT_SLOGAN_END, TIME_DELAY);
        	evtSent = true;
        }else{
        	loge("mHandy is null");
        }
    }
    @Override
    public void onResume(){
    	logi("resume");
    	super.onResume();
    	//mGLSurfaceView.onResume();
    	if(evtSent){/*Avoid to send more than one time*/
    		logi("evt sent ok");
    	}else{
    		mHandy.sendEmptyMessageDelayed(EVT_SLOGAN_END, TIME_DELAY);
    	}
    }
    public void stopExcMessage(int what){
    	if(mHandy.hasMessages(what)){
    		mHandy.removeMessages(what);
    		logi("message "+what+" removed");
    		evtSent = false;
    	};
    }
    
    public void jumpToEntry(){
    	Intent i = new Intent(Intent.ACTION_MAIN);
		i.setClass(getApplication(), MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
    }
    @Override
    public void onPause(){
    	logi("pause");
    	super.onPause();
    	//mGLSurfaceView.onPause();
    	stopExcMessage(EVT_SLOGAN_END);
    }
    @Override
    public void onDestroy(){
    	logi("destroy");
    	stopExcMessage(EVT_SLOGAN_END);
    	super.onDestroy();
    }
    @Override
    public void onStop(){
    	logi("stop");
    	super.onStop();
    	stopExcMessage(EVT_SLOGAN_END);
    }
    
    public void initAll(){
    	mHandy = new Handler(){
    		public void handleMessage(Message m){
    			logd("handle event is "+m.what);
    			switch (m.what){
    			case EVT_SLOGAN_END:
    				jumpToEntry();
    				break;
    			default:
    				logi("default message handle, m.what is "+m.what);
    			}
    		}
    	};
    }
    
    /*******************************************
    Log utils
    ********************************************/
    void loge(String string){
    	Log.e(LOG_TAG,LOG_ITEM + string);
    }
    void logw(String string){
    	Log.w(LOG_TAG,LOG_ITEM + string);
    }
    void logd(String string){
    	Log.d(LOG_TAG,LOG_ITEM + string);
    }
    void logv(String string){
    	Log.v(LOG_TAG,LOG_ITEM + string);
    }
    void logi(String string){
    	Log.i(LOG_TAG,LOG_ITEM + string);
    }
}
