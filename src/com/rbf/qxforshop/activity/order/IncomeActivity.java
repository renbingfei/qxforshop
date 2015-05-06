package com.rbf.qxforshop.activity.order;

import java.util.Arrays;
import java.util.LinkedList;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.view.PullToRefreshBase.OnRefreshListener;
import com.rbf.qxforshop.view.PullToRefreshListView;
import com.rbf.qxforshop.view.task.SellingPullTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class IncomeActivity extends Activity{
	
	private TextView today_income;
	private TextView week_income;
	private TextView month_income;
	private TextView total_income;
	//
	private TextView shop_already_orders;
	private TextView shop_exist_days;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_main);
		initViews();
	}

	private void initViews() {
		today_income = (TextView) findViewById(R.id.today_income);
		week_income = (TextView) findViewById(R.id.week_income);
		month_income = (TextView) findViewById(R.id.month_income);
		total_income = (TextView) findViewById(R.id.today_income);
		//
		shop_exist_days = (TextView)findViewById(R.id.shop_exist_days);
		shop_already_orders = (TextView)findViewById(R.id.shop_already_orders);
		//设置收入
//		today_income.setText("");
//		week_income.setText("");
//		month_income.setText("");
//		total_income.setText("");
		//设置存在天数和订单数
//		shop_exist_days.setText("");
//		shop_already_orders.setText("");
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//按下键盘上返回按钮
		if(keyCode == KeyEvent.KEYCODE_BACK){
				new AlertDialog.Builder(MyOrderActivity.instance)
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
}
