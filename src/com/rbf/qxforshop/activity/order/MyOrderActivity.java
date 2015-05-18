package com.rbf.qxforshop.activity.order;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.activity.AddGoodActivity;
import com.rbf.qxforshop.activity.myshop.MyShopActivity;
import com.rbf.qxforshop.activity.myshop.SelledActivity;
import com.rbf.qxforshop.activity.myshop.SellingActivity;
import com.rbf.qxforshop.utils.Util;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MyOrderActivity extends TabActivity implements OnTabChangeListener, OnClickListener{
	private TabHost tabHost;
	public static MyOrderActivity instance;
	//部件
	private static final int THUMB_SIZE = 150;
	private ImageView share;
	private ImageView add_goods;
	//微信分享
	public static final String APP_ID = "wx50e7c1b62dd05c26";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order);
		initSpec();
		initWidget();
		instance = MyOrderActivity.this;
		MyApplication.getInstance().addActivity(this);
	}
	
	public void initWidget(){
		share = (ImageView) findViewById(R.id.share);
		add_goods = (ImageView) findViewById(R.id.add_goods);
		//设置监听
		share.setOnClickListener(this);
		add_goods.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == share){
			//分享
			final IWXAPI api = WXAPIFactory.createWXAPI(getApplicationContext(),APP_ID,true);  
	        api.registerApp(APP_ID);  
	     // 初始化一个WXTextObject对象  
//            String text = "share our application";  
//            WXTextObject textObj = new WXTextObject();  
//            textObj.text = text;  
//            
//            WXMediaMessage msg = new WXMediaMessage(textObj);  
//            msg.mediaObject = textObj;  
//            msg.description = text;  
	        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			WXImageObject imgObj = new WXImageObject(bmp);
			
			WXMediaMessage msg = new WXMediaMessage();
			msg.mediaObject = imgObj;
			msg.description="dkjfdkfdkjfk";
			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
			bmp.recycle();
			msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置缩略图  
            SendMessageToWX.Req req = new SendMessageToWX.Req();  
            req.transaction = String.valueOf(System.currentTimeMillis());  
            req.message = msg;  
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
            api.sendReq(req);  
		}else if(v == add_goods){
			//添加商品
			Intent intent = new Intent();
			intent.setClass(MyOrderActivity.this, AddGoodActivity.class);
			startActivity(intent);
		}
	}
}
