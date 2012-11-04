package com.sss.diary.utils;

/***
 * 对于Log的封装
 * @author ShanTao
 *
 */
public class Log {
	
	/***
	 * 测试时设为True， 发布后设为False
	 */
	private final static boolean isLog = true;
	
    public static void e(String tag, String log){
    	if(isLog){
    		android.util.Log.e(tag, log);
    	}
    }
    
    public static void w(String tag, String log){
    	if(isLog){
    		android.util.Log.w(tag, log);
    	}
    }
    
    public static void d(String tag, String log){
    	if(isLog){
    		android.util.Log.d(tag, log);
    	}
    }
    
    public static void v(String tag, String log){
    	if(isLog){
    		android.util.Log.v(tag, log);
    	}
    }
    
    public static void i(String tag, String log){
    	if(isLog){
    		android.util.Log.i(tag, log);
    	}
    }
    
    public static void e(String tag, String log, Throwable ex){
    	if(isLog){
    		android.util.Log.e(tag, log, ex);
    	}
    }
    
    public static void w(String tag, String log, Throwable ex){
    	if(isLog){
    		android.util.Log.w(tag, log, ex);
    	}
    }
    
    public static void d(String tag, String log, Throwable ex){
    	if(isLog){
    		android.util.Log.d(tag, log, ex);
    	}
    }
    

}
