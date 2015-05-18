package com.rbf.qxforshop.activity.myshop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.activity.ModifySelledGoodActivity;
import com.rbf.qxforshop.activity.ModifySellingGoodActivity;
import com.rbf.qxforshop.utils.GoodsData;
import com.rbf.qxforshop.view.PullToRefreshBase.OnRefreshListener;
import com.rbf.qxforshop.view.PullToRefreshListView;
import com.rbf.qxforshop.view.adapter.SellingPullAdapter;
import com.rbf.qxforshop.view.task.SellingPullTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SellingActivity extends Activity implements OnItemClickListener{
	public static LinkedList<HashMap<String,Object>> mListItems;
	private PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<String> mAdapter;
	private ListView mListView;
	private SellingPullAdapter pullAdapter;
	private Handler handler = new MyHandler();
	private GoodsData goodsData = new GoodsData(this,handler);
	public ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selling_main);
		initViews();
		//�˴���������ȡ���ݴ���

		MyApplication.getInstance().addActivity(this);
	}

	private void initViews() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pullrefresh);
		mPullRefreshListView.setOnRefreshListener(mOnrefreshListener);
		mListView = mPullRefreshListView.getRefreshableView();
		mListItems = new LinkedList<HashMap<String,Object>>();
		//mListItems.addAll(Arrays.asList(mStrings));
		GoodsData.getSellingData();
		pullAdapter = new SellingPullAdapter(mListItems, SellingActivity.this);
		mListView.setAdapter(pullAdapter);
		mListView.setOnItemClickListener(this);
	}
	
	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
		SellingPullTask pullTask =	new SellingPullTask(mPullRefreshListView, mPullRefreshListView.getRefreshType(), pullAdapter, mListItems);
		pullTask.execute();
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//���¼����Ϸ��ذ�ť
		if(keyCode == KeyEvent.KEYCODE_BACK){
				new AlertDialog.Builder(MyShopActivity.instance)
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TextView goods_item_name = (TextView) view.findViewById(R.id.goods_item_name);
		Intent intent = new Intent(SellingActivity.this, ModifySellingGoodActivity.class);
		//���������Ʒid
		
		startActivity(intent);
	}
}
