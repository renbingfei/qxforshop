package com.rbf.qxforshop.activity;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class TempActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temp);
		
		MyApplication.getInstance().addActivity(this);
	}
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
