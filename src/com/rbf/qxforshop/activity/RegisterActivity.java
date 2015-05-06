package com.rbf.qxforshop.activity;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.utils.Common;
import com.rbf.qxforshop.utils.Instance;
import com.rbf.qxforshop.utils.Validate;
import com.tencent.bugly.crashreport.CrashReport;

public class RegisterActivity extends Activity implements OnClickListener {
	String account ;
	private View contentView1,contentView2;
	private boolean isNextView = false;
	private int changeViewCount = 0;

	//��¼�˺����
	private EditText accountEt;
	private TextView nextStep; //��һ����ť
	//�ڶ���view��
	private EditText identify_code; //��֤��
	private TextView get_identify_code;//�򿪾ͷ���
	private EditText passwordEt; //����
	private EditText ensure_passwordEt;//ȷ������
	private TextView finish_and_login; //���ע�Ტ��½
	
	private ProgressDialog progressDialog;//������
	private TimeCount timeCount;                //��ʱ������֤���ط�
	private Long millisInFuture;                //��ʱ����ʱ��
	private Long countDownInterval;             //��ʱ�����ʱ��
	private Handler handler = new MyHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		contentView1 = inflater.inflate(R.layout.register_main, null);
		contentView2 = inflater.inflate(R.layout.register_main_next_step, null);
		setContentView1();
		init();
		MyApplication.getInstance().addActivity(this);
	}
	
	private void setContentView1(){
		setContentView(contentView1);
	}
	private void setContentView2(){
		setContentView(contentView2);
		//���ÿؼ�
		if(changeViewCount==0){
			identify_code = (EditText) findViewById(R.id.identify_code);
			get_identify_code = (TextView) findViewById(R.id.get_identify_code);
			passwordEt = (EditText) findViewById(R.id.password);
			ensure_passwordEt = (EditText) findViewById(R.id.ensure_password);
			finish_and_login = (TextView) findViewById(R.id.finish_and_login);
			//ע�������
			get_identify_code.setOnClickListener(this);
			finish_and_login.setOnClickListener(this);
		}
		isNextView = true;
		changeViewCount++;
	}

	public void init(){
		//
		accountEt = (EditText)findViewById(R.id.account);
		nextStep = (TextView)findViewById(R.id.nextStep);
		//��Ӽ�����
		nextStep.setOnClickListener(this);
		
		////��ʼ������
		millisInFuture = (long) 60*1000;
		countDownInterval = (long) 1*1000;
		timeCount = new TimeCount(millisInFuture, countDownInterval);
		//
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			if(v == nextStep){
			//��¼
			account = accountEt.getText().toString().trim();
			if("".equals(account)){
				Toast.makeText(this, "�ֻ��Ų���Ϊ��", 3000).show();
			}
			else if(!Validate.isAccountValid(account)){
				Toast.makeText(this, "�ֻ��Ų��Ϸ�", 3000).show();
			}else{
				//������һ�����滻view
				setContentView2();
				//������֤��
				timeCount.start();
				get_identify_code.setBackgroundResource(R.drawable.ic_forget_button);//������ɫ
				getIdentifyCode(account);
				
			}
		}else if(v == get_identify_code){
			//��ȡ��֤��
			timeCount.start();
			get_identify_code.setBackgroundResource(R.drawable.ic_forget_button);//������ɫ
			getIdentifyCode(account);
			
		}else if(v == finish_and_login){
			//��ɲ���½
			//account = accountEt.getText().toString().trim();
			String identifyCode = identify_code.getText().toString().trim();
			String password = passwordEt.getText().toString().trim();
			String confirmPassword = ensure_passwordEt.getText().toString().trim();
			if("".equals(password) || "".equals(confirmPassword)||"".equals(identifyCode)){
				Toast.makeText(this, "����������", 3000).show();
			}else if(!password.equals(confirmPassword)){
				Toast.makeText(this, "�����������벻һ��", 3000).show();
			}else{
					//�һ�����
			}
		}
	}
	//���ؼ�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//���¼����Ϸ��ذ�ť
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(isNextView){
				setContentView(contentView1);
				isNextView = false;
				changeViewCount++;
			}else{
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, MineActivity.class);
				
				//��һ��Activityת����һ��View  
                Window w = MineGroupActivity.group.getLocalActivityManager()  
                        .startActivity("MineActivity",intent);  
                View view = w.getDecorView();  
                //��View��Ӵ�ActivityGroup��  
                MineGroupActivity.group.setContentView(view); 
                
				return false;
			}
			return false;
		}else{		
			return super.onKeyDown(keyCode, event);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//System.exit(0);
		//�����������ַ�ʽ
		//android.os.Process.killProcess(android.os.Process.myPid()); 
	}
	
	//��ʱ����������֤��������
	class TimeCount extends CountDownTimer{

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			get_identify_code.setClickable(false);
			get_identify_code.setText("���·���("+(millisUntilFinished/1000) +")" );
					
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			get_identify_code.setText("��ȡ��֤��");
			get_identify_code.setClickable(true);
			get_identify_code.setBackgroundResource(R.drawable.ic_register);
		}
				
	}
	
	class MyHandler extends Handler{
		private boolean isFirst = true;
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
				case 0:
					
					break;
				case 1:
					break;
			}
			
		}
		
	}
	
	private void getIdentifyCode(String account){
		AjaxParams params = new AjaxParams();
		JSONObject data = new JSONObject();
		JSONObject header = new JSONObject();
		JSONObject body = new JSONObject();
		body.put("account",account);
		data.put("body",body);
		String finalData = Instance.EncryptGzipJsonData(data);
		params.put("data", finalData);
		System.out.println("finalData: "+finalData);
		//http.addHeader("Content-Encoding", "gzip");
		//http.addHeader("Accept-Encoding", "gzip");
		Common.fhttp.post(Common.baseUrl+"user/getIdentifyCode", params, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
//				System.out.println("****");
//				Toast.makeText(RegisterActivity.this, "****"+errorNo+":"+strMsg, 3000).show();
				super.onFailure(t, errorNo, strMsg);
			}
			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
			}
	
			@SuppressLint("NewApi")
			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				System.out.println("****12");
				System.out.println(t);
				//Toast.makeText(RegisterActivity.this, t, 3000).show();
				Message msg = handler.obtainMessage();
				msg.what = 0;
				//Ӧ�÷���onSuccess�У��˴�Ϊ����ʹ��
				//LinkedList<HashMap<String,Object>> data = new LinkedList<HashMap<String,Object>>();
				JSONObject data = Instance.DencryptUnGzipJsonData(t);
				System.out.println(data.toJSONString());
				//Toast.makeText(RegisterActivity.this, data.toJSONString()+"**", 50000).show();
				handler.sendMessage(msg);
			}
		});
	}
}
