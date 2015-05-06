package com.rbf.qxforshop.activity.myshop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.utils.GoodsData;
import com.rbf.qxforshop.view.PullToRefreshBase.OnRefreshListener;
import com.rbf.qxforshop.view.PullToRefreshListView;
import com.rbf.qxforshop.view.adapter.SelledPullAdapter;
import com.rbf.qxforshop.view.adapter.SellingPullAdapter;
import com.rbf.qxforshop.view.task.SelledPullTask;
import com.rbf.qxforshop.view.task.SellingPullTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelledActivity extends Activity{
	public static LinkedList<HashMap<String,Object>> mListItems;
	private PullToRefreshListView mPullRefreshListView;
	private ListView mListView;
	private SelledPullAdapter pullAdapter;
	private Handler handler = new MyHandler();
	private GoodsData goodsData = new GoodsData(this,handler);
	public ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selling_main);
		initViews();
		//此处添加网络获取数据代码
		MyApplication.getInstance().addActivity(this);
	}

	private void initViews() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pullrefresh);
		mPullRefreshListView.setOnRefreshListener(mOnrefreshListener);
		mListView = mPullRefreshListView.getRefreshableView();
		mListItems = new LinkedList<HashMap<String,Object>>();
		//mListItems.addAll(Arrays.asList(mStrings));
		GoodsData.getSelledData();
		pullAdapter = new SelledPullAdapter(mListItems, SelledActivity.this);
		mListView.setAdapter(pullAdapter);
	}
	
	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
		SelledPullTask pullTask =	new SelledPullTask(mPullRefreshListView, mPullRefreshListView.getRefreshType(), pullAdapter, mListItems);
		pullTask.execute();
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//按下键盘上返回按钮
		if(keyCode == KeyEvent.KEYCODE_BACK){
				new AlertDialog.Builder(MyShopActivity.instance)
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
	
	class MyHandler extends Handler{
		private boolean isFirst = true;
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
				case 0:
					
					pullAdapter.notifyDataSetChanged();
					break;
				case 1:
					break;
			}
			
		}
	}
}
