/*
 * Copyright(C) 2012 SS soft
 * 
 * when       		who     	what, where, why
 *----------   		-----     	----------------------------------------------------------
 *07/11/2012		lzj			1) fix null pointer exception issue
 *07/07/2012		lzj			1) fix null pointer exception issue
 *07/04/2012		lzj			1) add Event handler
 *								2) rearrange event procedure 
 *06/27/2012		lzj			1) format source code style
 *								2) add setHandsColor() to reduce redundancy codes
 * */

package com.jeff.zhengduan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class BattleActivity extends Activity {
	
	final static String LOG_TAG = "ZDJJJ";
	final static String LOG_ITEM = " BATTLE";
	
	/*********************************************************************
	 * 		Event list
	**********************************************************************/
	final static int EVT_RST_GAME = 0;
	final static int EVT_SHOW_SELECT = 1;
	final static int EVT_DO_FIRE = 2;
	final static int EVT_SHOW_WARNING_TOAST = 3;
	
	/*********************************************************************
	 * 		Timer define
	 *********************************************************************/
	final static int TIMER_DELAY_SEC = 1000;
	
	private ImageView imgJiqiAll;
	private LinearLayout linearJiqiHalf;
	private ImageView imgJiqiLeft;
	private ImageView imgJiqiRight;
	private LinearLayout linearBattleField;
	private ImageView imgEnemyHand;
	private ImageView imgMyHand;
	
	private LinearLayout linearSelector;
	private ImageView imgSelectShitou;
	private ImageView imgSelectJiandao;
	private ImageView imgSelectBu;
	private Button btnSelectorOk;
	
	private HandTypeEnum myHand;
	private HandTypeEnum enemyHand;
	
	private Button btnMySelect;
	private Button btnMyFire;
	
	private Handler mBattleHandler = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();
        setContentView(R.layout.battle);
        initView();        
	}
	
	@Override
	public void onResume(){
		super.onResume();
		mBattleHandler.sendEmptyMessage(EVT_SHOW_SELECT);
	}
	
	
	private void initView() {
		this.imgJiqiAll = (ImageView)findViewById(R.id.img_jiqi_all);
		this.linearJiqiHalf = (LinearLayout)findViewById(R.id.linear_jiqi_half);
		this.imgJiqiLeft = (ImageView)findViewById(R.id.img_jiqi_left);
		this.imgJiqiRight = (ImageView)findViewById(R.id.img_jiqi_right);
		this.linearBattleField = (LinearLayout)findViewById(R.id.linear_battle_field);
		this.imgEnemyHand = (ImageView)findViewById(R.id.img_enemy_hand);
		this.imgMyHand = (ImageView)findViewById(R.id.img_my_hand);
		
		this.linearSelector = (LinearLayout)findViewById(R.id.linear_selector);
		this.btnSelectorOk = (Button)findViewById(R.id.btn_selector_ok);
		this.btnSelectorOk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//结束选择
				linearSelector.setVisibility(View.GONE);
				//start fire //add by lzj
				mBattleHandler.sendEmptyMessageDelayed(EVT_DO_FIRE, TIMER_DELAY_SEC);
			}
		});
		ImageSelectClickListener selectorListener = new ImageSelectClickListener();
		this.imgSelectShitou = (ImageView)findViewById(R.id.img_select_shitou);
		this.imgSelectShitou.setOnClickListener(selectorListener);
		this.imgSelectJiandao = (ImageView)findViewById(R.id.img_select_jiandao);
		this.imgSelectJiandao.setOnClickListener(selectorListener);
		this.imgSelectBu = (ImageView)findViewById(R.id.img_select_bu);
		this.imgSelectBu.setOnClickListener(selectorListener);
		
		this.btnMyFire=(Button)findViewById(R.id.btn_my_fire);
		this.btnMyFire.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(myHand == null){
					/*
					Toast msg = Toast.makeText(getApplicationContext(), "你还没有出招！", Toast.LENGTH_LONG);
					msg.setGravity(Gravity.CENTER, 0, 0);
					msg.show();
					*/
					Message msg = new Message();
					msg.what = EVT_SHOW_WARNING_TOAST;
					msg.obj = "您还没有出招！";
					mBattleHandler.sendMessage(msg);
					return;
				}
				doFire();
			}
		});
		
		
		this.btnMySelect=(Button)findViewById(R.id.btn_my_select);
		this.btnMySelect.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setHandsColor(myHand);				
				linearSelector.setVisibility(View.VISIBLE);
			}
		});
		
		
	}
	/*********************************************************************************
	 * 	Warning toast
	**********************************************************************************/
	private void showWarningToast(String str){
		if(null == str) str = "__NULL__";
		Toast msg = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
		msg.setGravity(Gravity.CENTER, 0, 0);
		msg.show();
	}
	
	/**********************************************************************************
	*							change Hand's color when selected
	*	setHandsColor(h)
	*	h is HandTypeEnum, if h is null, it will reset all hands' background color to default.
	**********************************************************************************/
	private void setHandsColor(HandTypeEnum h){
		imgSelectShitou.setBackgroundResource(R.color.touming);
		imgSelectJiandao.setBackgroundResource(R.color.touming);
		imgSelectBu.setBackgroundResource(R.color.touming);
		
		if(null == h) return;
		
		switch(h){
			case ShiTou:imgSelectShitou.setBackgroundResource(R.color.selected_bg);break;
			case JianDao:imgSelectJiandao.setBackgroundResource(R.color.selected_bg);break;
			case Bu:imgSelectBu.setBackgroundResource(R.color.selected_bg);break;
			default:break;
		}
	}
	
	private class ImageSelectClickListener implements View.OnClickListener{

		public void onClick(View v) {
			switch(v.getId()){
			case R.id.img_select_shitou:
				Log.d("Test", "BattleActivity -- shitou onClick");
				myHand = HandTypeEnum.ShiTou;
				break;
			case R.id.img_select_jiandao:
				myHand = HandTypeEnum.JianDao;
				Log.d("Test", "BattleActivity -- jiandao onClick");
				break;
			case R.id.img_select_bu:
				myHand = HandTypeEnum.Bu;
				Log.d("Test", "BattleActivity -- bu onClick");
				break;
			}
			setHandsColor(myHand);
			//after selected the hand type, show "OK" button
			linearSelector.findViewById(R.id.btn_selector_ok).setVisibility(View.VISIBLE);
		}
		
	}
	
	private void resetGame(){
		this.imgJiqiAll.setVisibility(View.VISIBLE);
		this.linearJiqiHalf.setVisibility(View.GONE);
		this.linearBattleField.setVisibility(View.GONE);
		this.linearSelector.setVisibility(View.GONE);
		linearSelector.findViewById(R.id.btn_selector_ok).setVisibility(View.GONE);

		setHandsColor(null);		
		this.myHand = null;
		this.enemyHand = null;
	}
	
	
	private void doFire(){
		
		this.enemyHand = HandTypeEnum.getRandomType();
		setHandImage(this.myHand, this.enemyHand);
		
		this.imgJiqiAll.setVisibility(View.GONE);
		this.linearJiqiHalf.setVisibility(View.VISIBLE);
		this.linearBattleField.setVisibility(View.VISIBLE);
		this.linearSelector.setVisibility(View.GONE);
		
		final int result = HandTypeEnum.compareTo(myHand, enemyHand);
		
		new Thread(){
			@Override
			public void run(){
				try{
					Thread.sleep(1000);
				}catch(Exception ex){}
				runOnUiThread(new Runnable() {
					public void run() {
						switch(result){
						case 0:	//draw
							showResultDialog("平局");
							break;
						case 1:		//win
							showResultDialog("你赢了！");
							break;
						case -1:	//lose
							showResultDialog("你输了！");
							break;
						}
					}
				});
			}
		}.start();
		
		
	}
	
	private void showResultDialog(String result){
		AlertDialog dialog = new AlertDialog.Builder(this)
					.setMessage(result)
					.setNegativeButton("确定", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					}).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				//resetGame();//modified by lzj
				mBattleHandler.sendEmptyMessage(EVT_RST_GAME);
				mBattleHandler.sendEmptyMessageDelayed(EVT_SHOW_SELECT, TIMER_DELAY_SEC);
			}
		});
		dialog.show();
	}
	
	private void setHandImage(HandTypeEnum myHand, HandTypeEnum enemyHand){
		if(null == myHand){
			Message msg = new Message();
			msg.what = EVT_SHOW_WARNING_TOAST;
			msg.obj = "您还没有出招！";
			mBattleHandler.sendMessage(msg);
			return;
		}
		
		switch(myHand){
		case ShiTou:
			this.imgMyHand.setImageResource(R.drawable.shitou_up);
			break;
		case JianDao:
			this.imgMyHand.setImageResource(R.drawable.jiandao_up);
			break;
		case Bu:
			this.imgMyHand.setImageResource(R.drawable.bu_up);
			break;
		}
		
		switch(enemyHand){
		case ShiTou:
			this.imgEnemyHand.setImageResource(R.drawable.shitou_down);
			break;
		case JianDao:
			this.imgEnemyHand.setImageResource(R.drawable.jiandao_down);
			break;
		case Bu:
			this.imgEnemyHand.setImageResource(R.drawable.bu_down);
			break;
		}
	}
	
	/******************************************************************************
	 * 				void	initHandler(void)
	 * 	This will init activity's handler to process all events				
	 *****************************************************************************/
	private void initHandler(){
		mBattleHandler = new Handler(){
			public void handleMessage(Message m){ 
				logi("m.what is "+m.what);
				switch(m.what){
				case EVT_RST_GAME:
					resetGame();
					break;
				case EVT_SHOW_SELECT:
					linearSelector.setVisibility(View.VISIBLE);
					break;
				case EVT_DO_FIRE:
					doFire();
					break;
				case EVT_SHOW_WARNING_TOAST:
					showWarningToast(m.obj.toString());
					break;
				default:break;
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
