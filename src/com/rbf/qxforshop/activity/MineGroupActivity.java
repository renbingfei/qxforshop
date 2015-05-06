package com.rbf.qxforshop.activity;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.utils.Common;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

public class MineGroupActivity extends ActivityGroup{
	/** 
     * 一个静态的ActivityGroup变量，用于管理本Group中的Activity 
     */  
    public static ActivityGroup group;  
      
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
         
        group = this;
        
        MyApplication.getInstance().addActivity(this);
          
    }  
  
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
    	System.out.println("(((((((((((((((((((((((((((((");
    	return group.getLocalActivityManager()  
        .getCurrentActivity().onKeyDown(keyCode, event);  
	}

  
    @Override  
    protected void onResume() {  
        // TODO Auto-generated method stub  
        super.onResume();  
        //把界面切换放到onResume方法中是因为，从其他选项卡切换回来时，  
        //调用搞得是onResume方法  
        Intent intent = new Intent();
        Window w = null;
        //要跳转的界面
        if(!Common.isLogin){
	        if(Common.loginOrfindPassword){
	        	intent.setClass(this, RegisterActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	w = group.getLocalActivityManager().startActivity("RegisterActivity",intent);
	        }else{
	        	intent.setClass(this, MineActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	w = group.getLocalActivityManager().startActivity("MineActivity",intent);
	        }
        }else{
        	//已登录
        	intent.setClass(this, MineActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	w = group.getLocalActivityManager().startActivity("MineActivity",intent);
        }
        //把一个Activity转换成一个View  
        View view = w.getDecorView();  
        //把View添加大ActivityGroup中  
        group.setContentView(view);  
    }  
}
