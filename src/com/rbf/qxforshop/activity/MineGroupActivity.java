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
     * һ����̬��ActivityGroup���������ڹ���Group�е�Activity 
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
        //�ѽ����л��ŵ�onResume����������Ϊ��������ѡ��л�����ʱ��  
        //���ø����onResume����  
        Intent intent = new Intent();
        Window w = null;
        //Ҫ��ת�Ľ���
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
        	//�ѵ�¼
        	intent.setClass(this, MineActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	w = group.getLocalActivityManager().startActivity("MineActivity",intent);
        }
        //��һ��Activityת����һ��View  
        View view = w.getDecorView();  
        //��View��Ӵ�ActivityGroup��  
        group.setContentView(view);  
    }  
}
