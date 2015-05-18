package com.rbf.qxforshop.activity;


import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.activity.myshop.MyShopActivity;
import com.rbf.qxforshop.activity.order.MyOrderActivity;
import com.rbf.qxforshop.utils.Common;
import com.rbf.qxforshop.utils.Util;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class RealMainActivity extends TabActivity implements OnTabChangeListener, OnClickListener{

	private TabHost tabHost;
	private int[] pressedDrawable = new int[]{R.drawable.three_big_type_shop_pressed,R.drawable.three_big_type_order_pressed,R.drawable.three_big_type_mine_pressed};
	private int[] unpressedDrawable = new int[]{R.drawable.three_big_type_shop_unpressed,R.drawable.three_big_type_order_unpressed,R.drawable.three_big_type_mine_unpressed};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_real_main);
		
		initSpec();
		initWidget();
		
		MyApplication.getInstance().addActivity(this);
	}

	public void initWidget(){
//		share = (ImageView) findViewById(R.id.share);
//		add_goods = (ImageView) findViewById(R.id.add_goods);
//		//设置监听
//		share.setOnClickListener(this);
//		add_goods.setOnClickListener(this);
	}
	public void initSpec(){
		
		tabHost = getTabHost();
		tabHost.addTab(buildTabSpec("main1","",getResources().getDrawable(unpressedDrawable[0]),new Intent(RealMainActivity.this,MyShopActivity.class)));
		tabHost.addTab(buildTabSpec("main2","",getResources().getDrawable(unpressedDrawable[1]),new Intent(RealMainActivity.this,MyOrderActivity.class)));
		tabHost.addTab(buildTabSpec("main3", "", getResources().getDrawable(unpressedDrawable[2]),new Intent(RealMainActivity.this,MineGroupActivity.class)));
		Intent intent = getIntent();
		if(intent == null){
			tabHost.setCurrentTab(2);
		}else{
			tabHost.setCurrentTab(intent.getFlags());
		}
		updateTab(tabHost);
		tabHost.setOnTabChangedListener(this);
	}
	
	public TabHost.TabSpec buildTabSpec(String tag, String label,Drawable icon,Intent intent){
		
		return tabHost.newTabSpec(tag)
				.setIndicator(label, icon)
				.setContent(intent);
	}


	@SuppressLint("ResourceAsColor")
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		updateTab(tabHost);
	}
	
	@SuppressLint("ResourceAsColor")
	public void updateTab(final TabHost tabHost){
		
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
          
            ImageView iv = (ImageView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.icon);
            
            if (tabHost.getCurrentTab() == i) {
                //选中后的背景
            	iv.setImageDrawable(getResources().getDrawable(pressedDrawable[i]));
                
            } else {
                //非选择的背景
            	iv.setImageDrawable(getResources().getDrawable(unpressedDrawable[i]));
            }
        }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//		// TODO Auto-generated method stub
//		//按下键盘上返回按钮
//		
//				if(event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
//				
//					new AlertDialog.Builder(this)
//						.setIcon(R.drawable.ic_prompt)
//						.setTitle("提醒")
//						.setMessage("退出应用")
//						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//							}
//						})
//						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int whichButton) {
//								MyApplication.getInstance().exit();
//							}
//						}).show();
//					
//					return true;
//				}else{		
//					return super.dispatchKeyEvent(event);
//				}
//	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//按下键盘上返回按钮
		if(keyCode == KeyEvent.KEYCODE_BACK){
//			if(isNextView){
//				setContentView(contentView1);
//				isNextView = false;
//				changeViewCount++;
//			}else{
//				Intent intent = new Intent();
//				intent.setClass(RegisterActivity.this, TempActivity.class);
//				
//				finish();
//				return false;
//			}
			return false;
		}else{		
			return super.onKeyDown(keyCode, event);
		}
	}
}
