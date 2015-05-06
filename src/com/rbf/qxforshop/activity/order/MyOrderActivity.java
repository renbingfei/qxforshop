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
		tabHost.addTab(buildTabSpec("main1","����ɶ���",new Intent(MyOrderActivity.this,OrderedActivity.class)));
		tabHost.addTab(buildTabSpec("main2","�ҵ�����",new Intent(MyOrderActivity.this,IncomeActivity.class)));
		for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++){  
            //��ȡtabview��   
            View view = tabHost.getTabWidget().getChildTabViewAt(i);  
            //����tab������ɫ,��Ӧ�����ļ���tab_bg.xml,�ɱ仯�ı���,ѡ��ʱΪ��ɫ,δѡ��Ϊ��ɫ  
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
                //ѡ�к�ı���
            	System.out.println("choose current:"+tabHost.getCurrentTab()+">>>i: "+i);
            	tv.setTextColor(this.getResources().getColorStateList(
          	          android.R.color.holo_orange_dark));
            	//vvv.setBackgroundColor(R.color.red);
                
            } else {
                //��ѡ��ı���
            	System.out.println("noneChoose current:"+tabHost.getCurrentTab()+">>>i: "+i);
            	tv.setTextColor(this.getResources().getColorStateList(
            	          android.R.color.black));
                //vvv.setBackgroundColor(R.color.white);
            }
        }
	}
}
