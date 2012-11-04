package com.sss.diary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static com.sss.diary.utils.Log.*;

import com.sss.diary.Constant;

import android.app.ActivityManager;
import android.content.Context;
import android.location.Location;


/***
 * ������
 * @author Shantao
 *
 */
public class AppUtils {

	/**  
	* �����жϷ����Ƿ�����.  
	* @param context  
	* @param className �жϵķ������֣�ȫ�޶���
	* @return true ������ false ��������  
	*/ 
	public static boolean isServiceRunning(Context mContext,String className) {  
		boolean isRunning = false;  
		ActivityManager activityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
	    List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(100);
	     for (int i=0; i<serviceList.size(); i++) {
//	    	 System.out.println(serviceList.get(i).service.getClassName());
	         if (serviceList.get(i).service.getClassName().equals(className)) {
	        	 isRunning = serviceList.get(i).started;
//	             isRunning = true;
	             break;  
	         }
	     }  
	     return isRunning;  
	}
	
	/***
	 * ���ڵ��ַ�������ʽ yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String date2String(Date date){
		if(date==null){
			return "";
		}
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat1.format(date);
	}

	public static String date2StringWithoutSecond(Date date){
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateformat1.format(date);
	}
	
	public static String date2String(Calendar date){
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat1.format(date.getTime());
	}
	
	public static String date2String(Date date, String format){
		SimpleDateFormat dateformat1=new SimpleDateFormat(format);
		return dateformat1.format(date);
	}
	
	/***
	 * �ַ��������ڣ� ��ʽyyyy-MM-dd HH:mm:ss
	 * @param source
	 * @return
	 */
	public static Date string2Date(String source){
		if(source==null || "".equals(source)){
			return null;
		}
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateformat1.parse(source);
		} catch (ParseException e) {
			e(Constant.TAG, "AppUtil -- �ַ������������ǳ���");
			return null;
		}
	}
	
	/***
	 * ���location���ַ���
	 * @param location
	 * @return
	 */
	public static String getLocationString(Location location){
		Date gpsTime = new Date(location.getTime());
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		String latitudeType;
		String longitudeType;
		if(latitude>=0){	//��γ
			latitudeType = "N";
		}else{	//��γ
			latitudeType = "S";
		}
		if(longitude>=0){	//����
			longitudeType = "E";
		}else{			//����
			longitudeType = "W";
		}
		float speed = location.getSpeed();
		int height = (int) location.getAltitude();
		int direction = (int)location.getBearing();
		StringBuilder sb = new StringBuilder();
		sb.append("Time:"+AppUtils.date2String(gpsTime)+",");
		sb.append("latitude:"+latitude+latitudeType+",");
		sb.append("longitude:"+latitude+longitudeType+",");
		sb.append("speed:"+(int)(speed*100)+",");
		sb.append("height:"+height+",");
		sb.append("direction:"+direction);
		return sb.toString();
		
	}
	
}
