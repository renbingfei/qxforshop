package com.rbf.qxforshop.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.rbf.qxforshop.listener.MyLocationListener;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

public class Common {
	public static boolean loginOrfindPassword = false;
	public static boolean isLogin = false;  //是否登录
	public static LocationClient locationClient = null;  //百度定位
	public static final int UPDATE_TIME = 5000;   //定位刷新时间
	public static int LOCATION_COUTNS = 0;        //定位次数
	public static BDLocationListener myListener = null;
	public static double lan = 0.0d;
	public static double lng = 0.0d; //经纬度
	public static String addr = "";//地理位置
    
	public static FinalBitmap fb = null;
	public static FinalHttp fhttp = null;
	public static String baseUrl = "http://10.108.167.26:8081/";
	public static String imageUrl = baseUrl + "ic_launcher.png";
	
	//注册监听函数
	public static void registerLocation(){
		locationClient.registerLocationListener( myListener );    //注册监听函数
	}
	
	//取消注册监听函数
	public static void unregisterLocation(){
		locationClient.unRegisterLocationListener( myListener );    //注册监听函数
	}
	
	
}
