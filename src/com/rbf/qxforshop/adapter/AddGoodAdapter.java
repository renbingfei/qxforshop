package com.rbf.qxforshop.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Gallery.LayoutParams;

public class AddGoodAdapter extends SimpleAdapter{
	private int selectItem = -1;
	private Context context;
	private LayoutParams mParams;
	
	private Context mContext;  
  
	@SuppressLint("NewApi")
  
	public AddGoodAdapter(Context context,
			List<? extends Map<String, ?>> data) {
		super(context, data, 0, null, null);
		// TODO Auto-generated constructor stub
		this.context = context;
		mParams = new LayoutParams(250,android.view.ViewGroup.LayoutParams.FILL_PARENT);
	}

	 public void setSelectItem(int selectItem) {
		  if (this.selectItem != selectItem) {               
			  this.selectItem = selectItem;
			  //notifyDataSetChanged();        
		  }
	}
	public int getSelectItem(){
		return this.selectItem;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Bitmap> map = (HashMap<String, Bitmap>) getItem(position);
//		String uri = (String) map.get("uri");
//		String infoText = (String)map.get("info");
		
		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(mParams);
		imageView.setImageBitmap(map.get("image")); 
		
		return imageView;  
	}
	

}

