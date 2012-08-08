package com.jeff.zhengduan;

import java.util.Random;

import android.util.Log;

public enum HandTypeEnum {
	
	JianDao, 
	ShiTou, 
	Bu;
	
	
	public static int compareTo(HandTypeEnum type1, HandTypeEnum type2){
		switch(type1){
		case JianDao:
			switch(type2){
			case JianDao:
				return 0;
			case ShiTou:
				return -1;
			case Bu:
				return 1;
			}
			break;
		case ShiTou:
			switch(type2){
			case JianDao:
				return 1;
			case ShiTou:
				return 0;
			case Bu:
				return -1;
			}
			break;
		case Bu:
			switch(type2){
			case JianDao:
				return -1;
			case ShiTou:
				return 1;
			case Bu:
				return 0;
			}
			break;
		}
		Log.e("Test", "HandTypeEnum -- compareTo: error");
		return 99;
	}
	
	public static HandTypeEnum getRandomType(){
		Random r = new Random();
		int rInt = r.nextInt(100);
		switch(rInt%3){
		case 0:
			return HandTypeEnum.ShiTou;
		case 1:
			return HandTypeEnum.JianDao;
		case 2:
			return HandTypeEnum.Bu;
		default: 
			return HandTypeEnum.JianDao;
			
		}
	}

}
