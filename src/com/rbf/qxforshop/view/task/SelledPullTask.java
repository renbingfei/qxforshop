package com.rbf.qxforshop.view.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

import com.rbf.qxforshop.utils.Common;
import com.rbf.qxforshop.view.PullToRefreshListView;

import android.os.AsyncTask;
import android.widget.BaseAdapter;

public class SelledPullTask extends AsyncTask<Void, Void, String>{

	private PullToRefreshListView pullToRefreshListView;  //ʵ������ˢ�����������ص�ListView
	private int pullState;               //��¼�жϣ���������������
	private BaseAdapter baseAdapter;     //ListView����������������ListView�����Ѿ�����
	private LinkedList<HashMap<String,Object>> linkedList;
	
	public SelledPullTask(PullToRefreshListView pullToRefreshListView, int pullState,
			BaseAdapter baseAdapter, LinkedList<HashMap<String,Object>> linkedList) {
		this.pullToRefreshListView = pullToRefreshListView;
		this.pullState = pullState;
		this.baseAdapter = baseAdapter;
		this.linkedList = linkedList;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		String result = "";
		try {
			Thread.sleep(1000);
			if(pullState == 1){
				//����ˢ��
				System.out.println("��ʼ����");
				result = download(Common.baseUrl);
			}else if(pullState == 2){
				//��������
				result = download(Common.baseUrl);
			}
		} catch (InterruptedException e) {
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		System.out.println("�������");
		if(pullState == 1) {//name="pullDownFromTop" value="0x1" ����
			//linkedList.addFirst("��������");
			HashMap<String, Object > map;
			for(int i=0;i<4;i++){
				map = new HashMap<String, Object>();
				map.put("image", Common.imageUrl);
				map.put("name", "�츻ʿƻ��");
				map.put("goodId", "goodId: "+i);
				map.put("origin_price", "7 Ԫ/��");
				map.put("qx_price", "2.5");
				map.put("good_detail_left_number", "30��");
				map.put("qx_time", "2015��3��25��  17:00 ���� 2015��3��26�� 21:00");
				linkedList.addFirst(map);
			}
		}
		else if(pullState == 2) {//name="pullUpFromBottom" value="0x2" ����
			//linkedList.addLast("�ײ�����");
			HashMap<String, Object > map;
			for(int i=0;i<4;i++){
				map = new HashMap<String, Object>();
				map.put("image", Common.imageUrl);
				map.put("name", "�츻ʿƻ��");
				map.put("goodId", "goodId: "+i);
				map.put("origin_price", "7 Ԫ/��");
				map.put("qx_price", "2.5");
				map.put("good_detail_left_number", "30��");
				map.put("qx_time", "2015��3��25��  17:00 ���� 2015��3��26�� 21:00");
				linkedList.addLast(map);
			}
		}
		baseAdapter.notifyDataSetChanged();
		pullToRefreshListView.onRefreshComplete();
		
		super.onPostExecute(result);
	}
	
	//��ȡ����
		private String download(String uri){
			InputStream in=null;
			HttpURLConnection con=null;
			String result = "";
			try{
				URL url=new URL(uri);
				con=(HttpURLConnection)url.openConnection();
				con.setConnectTimeout(5*1000);
				con.setReadTimeout(10 * 1000);
				con.setDoInput(true);
				con.setDoOutput(true);
				in = con.getInputStream();
				InputStreamReader inr = new InputStreamReader(in);
				BufferedReader buffer = new BufferedReader(inr);
				String inputLine  = ""; 
				while((inputLine = buffer.readLine()) != null){  
		             result += inputLine + "\n";  
		        }  
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(in != null){  
	                try {  
	                	in.close();  
	                } catch (IOException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                }  
	            }  
				if(con!=null){
					con.disconnect();
				}
			}
			return result;
		}
}
