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

        String appId = "900002911";   //��Bugly(bugly.qq.com)ע���Ʒ��ȡ��AppId
        
        boolean isDebug = true ;  //true����App���ڵ��Խ׶Σ�false����App�����׶�
        
        CrashReport. initCrashReport(appContext , appId ,isDebug);  //��ʼ��SDK  
        CrashReport. setUserId( "rbf");  //�����û���Ψһ��ʶ
        //��ʼ����λ����
      //��ʼ��fb �� fhttp
      		Common.fb = FinalBitmap.create(this);
      		Common.fb.configBitmapLoadThreadSize(4);
      		Common.fb.configDiskCachePath(this.getBaseContext().getFilesDir().toString());//���û���Ŀ¼��   
      		Common.fb.configDiskCacheSize(1024 * 1024 * 10);//���û����С 
      		Common.fb.configLoadingImage(R.drawable.ic_load_fail);
      		Common.fb.configLoadfailImage(R.drawable.ic_load_fail);
      		
      		Common.fhttp = new FinalHttp();
      		Common.fhttp.configTimeout(1000);//��ʱʱ��  ����ʹ��,ʵ��ʹ��ʱ��Ҫ����
      		//��ʼ���ٶȶ�λclient
      		Common.locationClient = new LocationClient(this);
      		//���ö�λ����
      		LocationClientOption option = new LocationClientOption();
      		option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ
      		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
      		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
      		option.setIsNeedAddress(true);//���صĶ�λ���������ַ��Ϣ
      		option.setNeedDeviceDirect(true);//���صĶ�λ��������ֻ���ͷ�ķ���
      		option.setOpenGps(true);
      		option.setScanSpan(Common.UPDATE_TIME); 
      		option.setProdName("����");
      		Common.locationClient.setLocOption(option);
      		Common.myListener = new MyLocationListener(this);
      		//ע���������
      		Common.registerLocation();
      		if(NetWorkProvider.isNetworkAvailable(this)){
      			Common.locationClient.start(); //��ʼ��λ
      			
      		}else{
      			Toast.makeText(this, "��λʧ�ܣ��������", Toast.LENGTH_SHORT).show();
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