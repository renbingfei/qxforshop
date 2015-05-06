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
	//��¼�˺����
	private EditText accountEt;
	private EditText passwordEt;
	private TextView login_btn; //��¼��ť
	private TextView forget_password_btn;//��������
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
		//��Ӽ�����
		login_btn.setOnClickListener(this);
		forget_password_btn.setOnClickListener(this);
	}
	//�ڶ���view
	public void setContentView2(){
		setContentView(contentView2);
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//���¼����Ϸ��ذ�ť
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
				new AlertDialog.Builder(MineGroupActivity.group)
					.setIcon(R.drawable.ic_prompt)
					.setTitle("����")
					.setMessage("�˳�Ӧ��")
					.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
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
			//��¼
			String account = accountEt.getText().toString().trim();
			String password = passwordEt.getText().toString().trim();
			if("".equals(account) || "".equals(password)){
				Toast.makeText(this, "�˺Ż����벻��Ϊ��", 3000).show();
			}
			else if(!Validate.isAccountValid(account)){
				Toast.makeText(this, "�ֻ��Ų��Ϸ�", 3000).show();
			}else{
				//�ύ��¼����
				progressDialog = ProgressDialog.show(this, "��ʾ","���ڵ�½����ȴ�...");
				//progressDialog.setCanceledOnTouchOutside(true);
				
			}
			//���õ�¼״̬
			Common.isLogin = true;
			setContentView2();
			
		}else if(v == forget_password_btn){
			//��������
			Intent intent = new Intent(MineActivity.this, RegisterActivity.class);
			Window w = MineGroupActivity.group.getLocalActivityManager()  
                    .startActivity("RegisterActivity",intent);  
            View view = w.getDecorView();  
            //��View��Ӵ�ActivityGroup��  
            MineGroupActivity.group.setContentView(view); 
		}
	}
}
