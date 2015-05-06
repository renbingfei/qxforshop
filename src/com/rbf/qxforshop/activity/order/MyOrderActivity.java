package com.rbf.qxforshop.activity.order;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.activity.myshop.MyShopActivity;
import com.rbf.qxforshop.activity.myshop.SelledActivity;
import com.rbf.qxforshop.activity.myshop.SellingActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MyOrderActivity extends TabActivity implements OnTabChangeListener{
	private TabHost tabHost;
	public static MyOrderActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order);
		initSpec();
		instance = MyOrderActivity.this;
		MyApplication.getInstance().addActivity(this);
	}
	
	public void initSpec(){
		
		tabHost = getTabHost();
		tabHost.addTab(buildTabSpec("main1","已完成订单",new Intent(MyOrderActivity.this,OrderedActivity.class)));
		tabHost.addTab(buildTabSpec("main2","我的收入",new Intent(MyOrderActivity.this,IncomeActivity.class)));
		for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++){  
            //获取tabview项   
            View view = tabHost.getTabWidget().getChildTabViewAt(i);  
            //设置tab背景颜色,对应配置文件的tab_bg.xml,可变化的背景,选中时为白色,未选中为黑色  
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_bg));  
		}
		Intent intent = getIntent();
		if(intent == null){
			tabHost.setCurrentTab(2);
		}else{
			tabHost.setCurrentTab(intent.getFlags());
		}
		updateTab(tabHost);
		tabHost.setOnTabChangedListener(this);
	}
	
	public TabHost.TabSpec buildTabSpec(String tag, String label,Intent intent){
		
		return tabHost.newTabSpec(tag)
				.setIndicator(label)
				.setContent(intent);
	}
	
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		updateTab(tabHost);
	}
	
	@SuppressLint("ResourceAsColor")
	public void updateTab(final TabHost tabHost){
		
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
          
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            
            if (tabHost.getCurrentTab() == i) {
                //选中后的背景
            	System.out.println("choose current:"+tabHost.getCurrentTab()+">>>i: "+i);
            	tv.setTextColor(this.getResources().getColorStateList(
          	          android.R.color.holo_orange_dark));
            	//vvv.setBackgroundColor(R.color.red);
                
            } else {
                //非选择的背景
            	System.out.println("noneChoose current:"+tabHost.getCurrentTab()+">>>i: "+i);
            	tv.setTextColor(this.getResources().getColorStateList(
            	          android.R.color.black));
                //vvv.setBackgroundColor(R.color.white);
            }
        }
	}
}
