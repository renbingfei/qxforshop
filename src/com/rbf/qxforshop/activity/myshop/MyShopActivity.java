package com.rbf.qxforshop.activity.myshop;

import java.io.File;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.activity.MineActivity;
import com.rbf.qxforshop.activity.MineGroupActivity;
import com.rbf.qxforshop.activity.RealMainActivity;
import com.rbf.qxforshop.activity.TempActivity;
import com.rbf.qxforshop.utils.Common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class MyShopActivity extends TabActivity implements OnTabChangeListener, OnClickListener{
	private TabHost tabHost;
	private ImageView shopLogo; 
	private TextView shopName;
	public static MyShopActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_shop);
		instance = MyShopActivity.this;
		initSpec();
		initWidget();
		
		MyApplication.getInstance().addActivity(this);
	}
	
	public void initWidget(){
		shopLogo = (ImageView) findViewById(R.id.shopLogo);
		shopName = (TextView) findViewById(R.id.shopName);
		//监听
		shopLogo.setOnClickListener(this);
		shopName.setText("金凤呈祥");
	}
	
	public void initSpec(){
		
		tabHost = getTabHost();
		tabHost.addTab(buildTabSpec("main1","出售中",new Intent(MyShopActivity.this,SellingActivity.class)));
		tabHost.addTab(buildTabSpec("main2","已下架",new Intent(MyShopActivity.this,SelledActivity.class)));
		for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++){  
            //获取tabview项   
            View view = tabHost.getTabWidget().getChildTabViewAt(i);  
            //设置tab背景颜色,对应配置文件的tab_bg.xml,可变化的背景,选中时为白色,未选中为黑色  
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_bg));  
		}
		Intent intent = getIntent();
		if(intent == null){
			tabHost.setCurrentTab(2);
		}else{
			tabHost.setCurrentTab(intent.getFlags());
		}
		updateTab(tabHost);
		tabHost.setOnTabChangedListener(this);
	}
	
	public TabHost.TabSpec buildTabSpec(String tag, String label,Intent intent){
		
		return tabHost.newTabSpec(tag)
				.setIndicator(label)
				.setContent(intent);
	}
	
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		updateTab(tabHost);
	}
	
	@SuppressLint("ResourceAsColor")
	public void updateTab(final TabHost tabHost){
		
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
          
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            
            if (tabHost.getCurrentTab() == i) {
                //选中后的背景
            	System.out.println("choose current:"+tabHost.getCurrentTab()+">>>i: "+i);
            	tv.setTextColor(this.getResources().getColorStateList(
          	          android.R.color.holo_orange_dark));
            	//vvv.setBackgroundColor(R.color.red);
                
            } else {
                //非选择的背景
            	System.out.println("noneChoose current:"+tabHost.getCurrentTab()+">>>i: "+i);
            	tv.setTextColor(this.getResources().getColorStateList(
            	          android.R.color.black));
                //vvv.setBackgroundColor(R.color.white);
            }
        }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == shopLogo){
			//更换图片
			changePhoto();
		}
	}

	//更换图片
	private void changePhoto(){
		new AlertDialog.Builder(MyShopActivity.this)
						.setTitle("修改头像")
						.setIcon(R.drawable.ic_prompt)
						.setMessage("选择获取照片方式")
						.setPositiveButton("相册", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss(); 
		                        /** 
		                         * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码， 
		                         * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下 
		                         */ 
		                        Intent intent = new Intent(Intent.ACTION_PICK, null); 
		                           
		                        /** 
		                         * 下面这句话，与其它方式写是一样的效果，如果： 
		                         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
		                         * intent.setType(""image/*");设置数据类型 
		                         * 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 
		                         * 
		                         */
		                      //下面这句指定调用相机拍照后的照片存储的路径 
		                        File dir = new File(Environment 
                                        .getExternalStorageDirectory()+File.separator+"qxforshop");
		                        if(!dir.exists()){
		                        	dir.mkdir();
		                        }
		                        intent.setDataAndType( 
		                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
		                                "image/*"); 
		                        startActivityForResult(intent, 1); 
							}
						})
						.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss(); 
		                        Intent intent = new Intent( 
		                                MediaStore.ACTION_IMAGE_CAPTURE); 
		                        //下面这句指定调用相机拍照后的照片存储的路径 
		                        File dir = new File(Environment 
                                        .getExternalStorageDirectory()+File.separator+"qxforshop");
		                        if(!dir.exists()){
		                        	dir.mkdir();
		                        }
		                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri 
		                                .fromFile(new File(Environment 
		                                        .getExternalStorageDirectory()+File.separator+"qxforshop"+File.separator
		                	                    + "temp.jpg"))); 
		                        startActivityForResult(intent, 2); 
							}
						})
						.show();
						
	}
	@Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		//Toast.makeText(this, "resultCode: "+resultCode, 3000).show();
		if(resultCode == RESULT_CANCELED && requestCode!=3){
			//Toast.makeText(this, "取消", 3000).show();
			return ;
		}
        switch (requestCode) { 
        // 如果是直接从相册获取 
        case 1: 
            startPhotoZoom(data.getData()); 
            break; 
        // 如果是调用相机拍照时 
        case 2: 
            File temp = new File(Environment.getExternalStorageDirectory()+File.separator+"qxforshop"+File.separator
                    + "temp.jpg"); 
            startPhotoZoom(Uri.fromFile(temp)); 
            break; 
        // 取得裁剪后的图片 
        case 3: 
        	//Toast.makeText(this, "requestCode: "+requestCode, 3000).show();
            /** 
             * 非空判断大家一定要验证，如果不验证的话， 
             * 在剪裁之后如果发现不满意，要重新裁剪，丢弃 
                
             */ 
            if(data != null){ 
                setPicToView(); 
            } 
            break; 
        default: 
            break; 
   
        } 
        super.onActivityResult(requestCode, resultCode, data); 
    } 
       
    /** 
     * 裁剪图片方法实现 
     * @param uri 
     */ 
    public void startPhotoZoom(Uri uri) { 
        /* 
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页 
         * yourself_sdk_path/docs/reference/android/content/Intent.html 
            
         */ 
        Intent intent = new Intent("com.android.camera.action.CROP"); 
        intent.setDataAndType(uri, "image/*"); 
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪 
        intent.putExtra("crop", "true"); 
        Uri imageUri = Uri 
                .fromFile(new File(Environment 
                        .getExternalStorageDirectory()+File.separator+"qxforshop", 
                        "temp.jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//图像输出  
        intent.putExtra("outputFormat",  
                Bitmap.CompressFormat.JPEG.toString()); 
        // aspectX aspectY 是宽高的比例 
        intent.putExtra("aspectX", 1); 
        intent.putExtra("aspectY", 1); 
//        // outputX outputY 是裁剪图片宽高 
        intent.putExtra("outputX", 150);    
        intent.putExtra("outputY", 150);  
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true); 
        intent.putExtra("return-data", false); 
        startActivityForResult(intent, 3); 
    } 
       
    /** 
     * 保存裁剪之后的图片数据 
     * @param picdata 
     */ 
    private void setPicToView() { 
     
        	Bitmap bm = BitmapFactory.decodeFile(Environment 
                    .getExternalStorageDirectory()+File.separator+"qxforshop/temp.jpg");
            Drawable drawable = new BitmapDrawable(bm); 
            shopLogo.setImageDrawable(drawable);
       
    }
}
