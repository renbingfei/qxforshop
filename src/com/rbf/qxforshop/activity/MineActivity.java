package com.rbf.qxforshop.activity;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.utils.Common;
import com.rbf.qxforshop.utils.Validate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MineActivity extends Activity implements OnClickListener{
	
	private View contentView1 ,contentView2;
	//登录账号相关
	private EditText accountEt;
	private EditText passwordEt;
	private TextView login_btn; //登录按钮
	private TextView forget_password_btn;//忘记密码
	private boolean isFirstNo = false;
	private boolean isFirstLogin = false;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_main);
		LayoutInflater inflater = LayoutInflater.from(MineActivity.this);
		contentView1 = inflater.inflate(R.layout.mine_main, null);
		contentView2 = inflater.inflate(R.layout.mine_login, null);
		if(!Common.isLogin){
			setContentView1();
		}else{
			setContentView2();
		}
		MyApplication.getInstance().addActivity(this);
	}
	
	public void setContentView1(){
		setContentView(contentView1);
		if(!isFirstNo){
			init1();
			isFirstNo = true;
		}
	}
	public void init1(){
		accountEt = (EditText)findViewById(R.id.account);
		passwordEt = (EditText)findViewById(R.id.password);
		login_btn = (TextView)findViewById(R.id.login_btn);
		forget_password_btn = (TextView)findViewById(R.id.forget_password_btn);
		//添加监听器
		login_btn.setOnClickListener(this);
		forget_password_btn.setOnClickListener(this);
	}
	//第二个view
	public void setContentView2(){
		setContentView(contentView2);
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//按下键盘上返回按钮
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
				new AlertDialog.Builder(MineGroupActivity.group)
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
				
				return false;
			}else{		
				return super.onKeyDown(keyCode,event);
			}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == login_btn){
			//登录
			String account = accountEt.getText().toString().trim();
			String password = passwordEt.getText().toString().trim();
			if("".equals(account) || "".equals(password)){
				Toast.makeText(this, "账号或密码不能为空", 3000).show();
			}
			else if(!Validate.isAccountValid(account)){
				Toast.makeText(this, "手机号不合法", 3000).show();
			}else{
				//提交登录代码
				progressDialog = ProgressDialog.show(this, "提示","正在登陆，请等待...");
				//progressDialog.setCanceledOnTouchOutside(true);
				
			}
			//设置登录状态
			Common.isLogin = true;
			setContentView2();
			
		}else if(v == forget_password_btn){
			//忘记密码
			Intent intent = new Intent(MineActivity.this, RegisterActivity.class);
			Window w = MineGroupActivity.group.getLocalActivityManager()  
                    .startActivity("RegisterActivity",intent);  
            View view = w.getDecorView();  
            //把View添加大ActivityGroup中  
            MineGroupActivity.group.setContentView(view); 
		}
	}
}
