package com.rbf.qxforshop.activity;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.utils.Common;

import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private ImageView forgetPasswordIv; 
	private ImageView loginIv; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initWidget();
		
		//添加activity
		MyApplication.getInstance().addActivity(this);
	}
	
	public void initWidget(){
		forgetPasswordIv = (ImageView)findViewById(R.id.forgetPasswordIv);
		loginIv = (ImageView)findViewById(R.id.loginIv);
		
		//listener
		forgetPasswordIv.setOnClickListener(this);
		loginIv.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//按下键盘上返回按钮
				if(keyCode == KeyEvent.KEYCODE_BACK){
					new AlertDialog.Builder(this)
						.setIcon(R.drawable.ic_prompt)
						.setTitle("提醒")
						.setMessage("退出应用")
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						})
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int whichButton) {
								MyApplication.getInstance().exit();
							}
						}).show();
					
					return true;
				}else{		
					return super.onKeyDown(keyCode, event);
				}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setFlags(2);
		if(v == loginIv){
			//登录
			Common.loginOrfindPassword = false;
			intent.setClass(MainActivity.this, RealMainActivity.class);
			startActivity(intent);
		}else if(v == forgetPasswordIv){
			//忘记密码
			Common.loginOrfindPassword = true;
			intent.setClass(MainActivity.this, RealMainActivity.class);
			
			startActivity(intent);
		}
	}


}
