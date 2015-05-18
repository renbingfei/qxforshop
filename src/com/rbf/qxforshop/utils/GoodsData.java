package com.rbf.qxforshop.utils;

import java.util.HashMap;

import com.rbf.qxforshop.activity.myshop.SelledActivity;
import com.rbf.qxforshop.activity.myshop.SellingActivity;
import com.rbf.qxforshop.activity.order.OrderedActivity;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class GoodsData {
	private Context context;
	public static Handler handler;
	
	public GoodsData(Context context,Handler handler){
		this.context = context;
		GoodsData.handler = handler;
	}
				
		//��Ӧ�����̼�id�Լ���Ʒid
		public static void getSellingData(){
			AjaxParams params = new AjaxParams();
			Common.fhttp.post(Common.baseUrl, params, new AjaxCallBack<Object>() {
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					// TODO Auto-generated method stub
					
					Message msg = handler.obtainMessage();
					msg.what = 0;
					//Ӧ�÷���onSuccess�У��˴�Ϊ����ʹ��
					//LinkedList<HashMap<String,Object>> data = new LinkedList<HashMap<String,Object>>();
					
					HashMap<String, Object > map;
					for(int i=0;i<9;i++){
						map = new HashMap<String, Object>();
						map.put("image", Common.imageUrl);
						map.put("name", "�츻ʿƻ��");
						map.put("goodId", "goodId: "+i);
						map.put("origin_price", "7 Ԫ/��");
						map.put("qx_price", "2.5");
						map.put("good_detail_left_number", "30��");
						map.put("qx_time", "2015��3��25��  17:00 ���� 2015��3��26�� 21:00");
						SellingActivity.mListItems.add(map);
					}
					
					handler.sendMessage(msg);
					super.onFailure(t, errorNo, strMsg);
				}

				@Override
				public void onLoading(long count, long current) {
					// TODO Auto-generated method stub
					super.onLoading(count, current);
				}

				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
				}
			});
		}
		
		//��Ӧ�����̼�id�Լ���Ʒid
				public static void getSelledData(){
					AjaxParams params = new AjaxParams();
					Common.fhttp.post(Common.baseUrl, params, new AjaxCallBack<Object>() {
						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							// TODO Auto-generated method stub
							
							Message msg = handler.obtainMessage();
							msg.what = 0;
							//Ӧ�÷���onSuccess�У��˴�Ϊ����ʹ��
							//LinkedList<HashMap<String,Object>> data = new LinkedList<HashMap<String,Object>>();
							
							HashMap<String, Object > map;
							for(int i=0;i<9;i++){
								map = new HashMap<String, Object>();
								map.put("image", Common.imageUrl);
								map.put("name", "�츻ʿƻ��");
								map.put("goodId", "goodId: "+i);
								map.put("origin_price", "7 Ԫ/��");
								map.put("qx_price", "2.5");
								map.put("good_detail_left_number", "30��");
								map.put("qx_time", "2015��3��25��  17:00 ���� 2015��3��26�� 21:00");
								SelledActivity.mListItems.add(map);
							}
							
							handler.sendMessage(msg);
							super.onFailure(t, errorNo, strMsg);
						}

						@Override
						public void onLoading(long count, long current) {
							// TODO Auto-generated method stub
							super.onLoading(count, current);
						}

						@Override
						public void onSuccess(Object t) {
							// TODO Auto-generated method stub
							super.onSuccess(t);
						}
					});
				}
				//��Ӧ�����̼�id�Լ���Ʒid
				public static void getOrderData(){
					AjaxParams params = new AjaxParams();
					Common.fhttp.post(Common.baseUrl, params, new AjaxCallBack<Object>() {
						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							// TODO Auto-generated method stub
							
							Message msg = handler.obtainMessage();
							msg.what = 0;
							//Ӧ�÷���onSuccess�У��˴�Ϊ����ʹ��
							//LinkedList<HashMap<String,Object>> data = new LinkedList<HashMap<String,Object>>();
							
							HashMap<String, Object > map;
							for(int i=0;i<9;i++){
								map = new HashMap<String, Object>();
								//map.put("image",i+1);
								map.put("name", "�츻ʿƻ��");
								map.put("goodId", "goodId: "+i);
								map.put("origin_price", "�������� "+3+" ��");
								map.put("qx_price", "2.5");
								map.put("good_detail_left_number", "30��");
								map.put("qx_time", "2015��3��25��  17:00 ���� 2015��3��26�� 21:00");
								map.put("order_money", "7.5Ԫ");
								OrderedActivity.mListItems.add(map);
							}
							
							handler.sendMessage(msg);
							super.onFailure(t, errorNo, strMsg);
						}

						@Override
						public void onLoading(long count, long current) {
							// TODO Auto-generated method stub
							super.onLoading(count, current);
						}

						@Override
						public void onSuccess(Object t) {
							// TODO Auto-generated method stub
							super.onSuccess(t);
						}
					});
				}
}
