package com.rbf.qxforshop.view.adapter;

import java.util.HashMap;
import java.util.LinkedList;

import com.rbf.qxforshop.R;
import com.rbf.qxforshop.utils.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SellingPullAdapter extends BaseAdapter {

	private LinkedList<HashMap<String,Object>> datalist;
	private LayoutInflater mInflater;
	private Context context;
	public SellingPullAdapter(LinkedList<HashMap<String,Object>> datalist, Context context) {
		mInflater = LayoutInflater.from(context);
		this.datalist = datalist;
		this.context = context;
	}
	private static class ViewHolder {
		ImageView imageView;        //������ʾ����
		TextView tvName;
		TextView tvOriginPrice;
		TextView tvQxPrice;
		TextView good_detail_left_number;
		TextView qx_time;
		String goodId;
	}
	@Override
	public int getCount() {
		return datalist.size();
	}

	@Override
	public Object getItem(int position) {
		return datalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.sell_listitem, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.goods_item_imageview);
			holder.tvName = (TextView) convertView.findViewById(R.id.goods_item_name);
			holder.tvOriginPrice = (TextView) convertView.findViewById(R.id.goods_item_origin_price);
			holder.tvQxPrice = (TextView) convertView.findViewById(R.id.goods_item_qx_price);
			holder.good_detail_left_number = (TextView)convertView.findViewById(R.id.good_detail_left_number);
			holder.qx_time = (TextView)convertView.findViewById(R.id.qx_time);
			holder.goodId = "";
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(datalist.size()>0){
			
			final HashMap<String, Object> map = datalist.get(position);
			
			Common.fb.display(holder.imageView, map.get("image").toString());
			holder.tvName.setText(map.get("name").toString());
			holder.tvOriginPrice.setText("ԭ�ۼ�  "+map.get("origin_price").toString());
			holder.tvQxPrice.setText(" "+map.get("qx_price").toString()+" ");
			holder.good_detail_left_number.setText("���"+map.get("good_detail_left_number").toString());
			holder.qx_time.setText(map.get("qx_time").toString());
			holder.goodId = map.get("goodId").toString();  //������tag���Ա��ȡ��ַ
			holder.tvName.setTag(holder.goodId);
			
		}
		return convertView;
	}

	
}
