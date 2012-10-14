package com.sssoft.diary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver  extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
//			Log.d(Constant.TAG, "BootReceiver -- onReceive");
			
//			Intent startBoot = new Intent(context, GridMenuActivity.class);
//			String action="android.intent.action.MAIN";   
//	        String category="android.intent.category.LAUNCHER";   
//	        startBoot.setAction(action);
//	        startBoot.addCategory(category);
//
//			startBoot.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败
//			context.startActivity(startBoot);
//			Toast.makeText(context, "随开机启动的程序", 2000).show();
//			Intent s = new Intent(context, TestService.class);
//			context.startService(s);
			
		}
	}
}
