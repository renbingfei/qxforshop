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
	public static boolean isLogin = false;  //�Ƿ��¼
	public static LocationClient locationClient = null;  //�ٶȶ�λ
	public static final int UPDATE_TIME = 5000;   //��λˢ��ʱ��
	public static int LOCATION_COUTNS = 0;        //��λ����
	public static BDLocationListener myListener = null;
	public static double lan = 0.0d;
	public static double lng = 0.0d; //��γ��
	public static String addr = "";//����λ��
    
	public static FinalBitmap fb = null;
	public static FinalHttp fhttp = null;
	public static String baseUrl = "http://10.108.167.26:8081/";
	public static String imageUrl = baseUrl + "ic_launcher.png";
	
	//ע���������
	public static void registerLocation(){
		locationClient.registerLocationListener( myListener );    //ע���������
	}
	
	//ȡ��ע���������
	public static void unregisterLocation(){
		locationClient.unRegisterLocationListener( myListener );    //ע���������
	}
	
	
}
