package com.sss.diary.service;


import java.util.Date;

import com.sss.diary.Constant;
import com.sss.diary.utils.AppUtils;
import com.sss.diary.utils.dbutils.DiaryDbUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.sss.diary.domain.DiaryContentInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class DiaryService extends Service {
	
	//GPS
	private LocationManager locationManager;
	private Criteria criteria;	//位置提供器的标准
	private String strLocationProvider = null;
	
	private final static String TAG = Constant.TAG;

	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "DiaryService:onCreate");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		if(locationManager!=null){
			locationManager.removeUpdates(locationListener);
		}
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "DiaryService:onStartComand");
		//GPS
		String serviceName = Context.LOCATION_SERVICE;
	    locationManager = (LocationManager)getSystemService(serviceName);
	    criteria = new Criteria();    
	    criteria.setAccuracy(Criteria.ACCURACY_FINE);    
	    criteria.setAltitudeRequired(true);    
	    criteria.setBearingRequired(true);    
	    criteria.setCostAllowed(true);
	    criteria.setSpeedRequired(true);
	    criteria.setPowerRequirement(Criteria.POWER_LOW);

	    //GPS是否可用
		if(isOpenGPS()& strLocationProvider==null){
			Log.d(TAG, "GPS -- start get location provider");
			strLocationProvider = locationManager.getBestProvider(criteria, true);
			if(strLocationProvider==null){
				strLocationProvider = LocationManager.NETWORK_PROVIDER;
			}
			Log.d(TAG, "get location provider is: "+ strLocationProvider);
			
//			Location location = locationManager.getLastKnownLocation(strLocationProvider);
//			if(location!=null){
//	//	  				App.setCurrentLocation(location);
//				Log.d(TAG, "last location,"+AppUtil.getLocationString(location));
//			}
			locationManager.requestLocationUpdates(strLocationProvider, 10*60*1000, 600, locationListener);
		}
		setForeground(true);
		Log.d(TAG, "DiaryService -- server is started...");
		return Service.START_STICKY; 
	}
	
	//位置监听器
	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {	// 位置发生改变后调用
			String strLoc = AppUtils.getLocationString(location);
			DiaryContentInfo diary = new DiaryContentInfo();
			Date now = new Date();
			diary.setCreateTime(now);
			diary.setTitle(AppUtils.date2String(now, "yyyy-MM-dd"));
			diary.setContent(strLoc);
			diary.setUpdateTime(now);
			DiaryDbUtil dbUtil = new DiaryDbUtil(getApplicationContext(), false);
			dbUtil.addNewDiary(diary);
			dbUtil.closeDB();
		}

		@Override
		public void onProviderDisabled(String provider) {	//provider 被用户关闭后调用
			strLocationProvider = null;
			locationManager.removeUpdates(this);
		}

		@Override
		public void onProviderEnabled(String provider) {	 // provider 被用户开启后调用
			locationManager.requestLocationUpdates(strLocationProvider, 3000, 2, this);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) { 	// provider 状态变化时调用
			
		}
		
	};
		
	/***
	 * 判断GPS是否可用
	 * @return
	 */
	private boolean isOpenGPS(){
		 if (locationManager .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
				 || locationManager .isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
			 Log.i(TAG, "RemoteService -- gps is enable");
			 return true;
		 }else{
			 Log.i(TAG, "RemoteService -- gps is not enable");
			 return false;
		 }
	}

}
