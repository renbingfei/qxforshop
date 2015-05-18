package com.rbf.qxforshop;

import java.util.LinkedList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.rbf.qxforshop.listener.MyLocationListener;
import com.rbf.qxforshop.utils.Common;
import com.rbf.qxforshop.utils.NetWorkProvider;
import com.tencent.bugly.crashreport.CrashReport;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class MyApplication extends Application{

	private List<Activity> activityList = new LinkedList<Activity>();
	public static MyApplication instance;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Context appContext =  this.getApplicationContext();         

        String appId = "900002911";   //上Bugly(bugly.qq.com)注册产品获取的AppId
        
        boolean isDebug = true ;  //true代表App处于调试阶段，false代表App发布阶段
        
        CrashReport. initCrashReport(appContext , appId ,isDebug);  //初始化SDK  
        CrashReport. setUserId( "rbf");  //设置用户的唯一标识
        //初始化定位服务
      //初始化fb 和 fhttp
      		Common.fb = FinalBitmap.create(this);
      		Common.fb.configBitmapLoadThreadSize(4);
      		Common.fb.configDiskCachePath(this.getBaseContext().getFilesDir().toString());//设置缓存目录；   
      		Common.fb.configDiskCacheSize(1024 * 1024 * 10);//设置缓存大小 
      		Common.fb.configLoadingImage(R.drawable.ic_load_fail);
      		Common.fb.configLoadfailImage(R.drawable.ic_load_fail);
      		
      		Common.fhttp = new FinalHttp();
      		Common.fhttp.configTimeout(1000);//超时时间  测试使用,实际使用时需要调高
      		//初始化百度定位client
      		Common.locationClient = new LocationClient(this);
      		//设置定位条件
      		LocationClientOption option = new LocationClientOption();
      		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
      		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
      		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
      		option.setIsNeedAddress(true);//返回的定位结果包含地址信息
      		option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
      		option.setOpenGps(true);
      		option.setScanSpan(Common.UPDATE_TIME); 
      		option.setProdName("抢鲜");
      		Common.locationClient.setLocOption(option);
      		Common.myListener = new MyLocationListener(this);
      		//注册监听函数
      		Common.registerLocation();
      		if(NetWorkProvider.isNetworkAvailable(this)){
      			Common.locationClient.start(); //开始定位
      			
      		}else{
      			Toast.makeText(this, "定位失败，请打开网络", Toast.LENGTH_SHORT).show();
      		}
	}

	public static MyApplication getInstance(){
		 if(null == instance){
			 instance = new MyApplication();
		 }
		return instance;
	 }

	 public void addActivity(Activity activity){
		 this.activityList.add(activity);
	 }
	 
	 public void exit(){
		 for(Activity activity:activityList){
			 if(activity!=null)
			 activity.finish();
		 }
		 
		 System.exit(0);
	 }
	 
	
}