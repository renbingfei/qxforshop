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
		//����
		shopLogo.setOnClickListener(this);
		shopName.setText("������");
	}
	
	public void initSpec(){
		
		tabHost = getTabHost();
		tabHost.addTab(buildTabSpec("main1","������",new Intent(MyShopActivity.this,SellingActivity.class)));
		tabHost.addTab(buildTabSpec("main2","���¼�",new Intent(MyShopActivity.this,SelledActivity.class)));
		for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++){  
            //��ȡtabview��   
            View view = tabHost.getTabWidget().getChildTabViewAt(i);  
            //����tab������ɫ,��Ӧ�����ļ���tab_bg.xml,�ɱ仯�ı���,ѡ��ʱΪ��ɫ,δѡ��Ϊ��ɫ  
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
                //ѡ�к�ı���
            	System.out.println("choose current:"+tabHost.getCurrentTab()+">>>i: "+i);
            	tv.setTextColor(this.getResources().getColorStateList(
          	          android.R.color.holo_orange_dark));
            	//vvv.setBackgroundColor(R.color.red);
                
            } else {
                //��ѡ��ı���
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
			//����ͼƬ
			changePhoto();
		}
	}

	//����ͼƬ
	private void changePhoto(){
		new AlertDialog.Builder(MyShopActivity.this)
						.setTitle("�޸�ͷ��")
						.setIcon(R.drawable.ic_prompt)
						.setMessage("ѡ���ȡ��Ƭ��ʽ")
						.setPositiveButton("���", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss(); 
		                        /** 
		                         * �տ�ʼ�����Լ�Ҳ��֪��ACTION_PICK�Ǹ���ģ�����ֱ�ӿ�IntentԴ�룬 
		                         * ���Է�������ܶණ����Intent�Ǹ���ǿ��Ķ��������һ����ϸ�Ķ��� 
		                         */ 
		                        Intent intent = new Intent(Intent.ACTION_PICK, null); 
		                           
		                        /** 
		                         * ������仰����������ʽд��һ����Ч��������� 
		                         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
		                         * intent.setType(""image/*");������������ 
		                         * ���������Ҫ�����ϴ�����������ͼƬ����ʱ����ֱ��д�磺"image/jpeg �� image/png�ȵ�����" 
		                         * 
		                         */
		                      //�������ָ������������պ����Ƭ�洢��·�� 
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
						.setNegativeButton("����", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss(); 
		                        Intent intent = new Intent( 
		                                MediaStore.ACTION_IMAGE_CAPTURE); 
		                        //�������ָ������������պ����Ƭ�洢��·�� 
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
			//Toast.makeText(this, "ȡ��", 3000).show();
			return ;
		}
        switch (requestCode) { 
        // �����ֱ�Ӵ�����ȡ 
        case 1: 
            startPhotoZoom(data.getData()); 
            break; 
        // ����ǵ����������ʱ 
        case 2: 
            File temp = new File(Environment.getExternalStorageDirectory()+File.separator+"qxforshop"+File.separator
                    + "temp.jpg"); 
            startPhotoZoom(Uri.fromFile(temp)); 
            break; 
        // ȡ�òü����ͼƬ 
        case 3: 
        	//Toast.makeText(this, "requestCode: "+requestCode, 3000).show();
            /** 
             * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ��� 
             * �ڼ���֮��������ֲ����⣬Ҫ���²ü������� 
                
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
     * �ü�ͼƬ����ʵ�� 
     * @param uri 
     */ 
    public void startPhotoZoom(Uri uri) { 
        /* 
         * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ 
         * yourself_sdk_path/docs/reference/android/content/Intent.html 
            
         */ 
        Intent intent = new Intent("com.android.camera.action.CROP"); 
        intent.setDataAndType(uri, "image/*"); 
        //�������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü� 
        intent.putExtra("crop", "true"); 
        Uri imageUri = Uri 
                .fromFile(new File(Environment 
                        .getExternalStorageDirectory()+File.separator+"qxforshop", 
                        "temp.jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//ͼ�����  
        intent.putExtra("outputFormat",  
                Bitmap.CompressFormat.JPEG.toString()); 
        // aspectX aspectY �ǿ�ߵı��� 
        intent.putExtra("aspectX", 1); 
        intent.putExtra("aspectY", 1); 
//        // outputX outputY �ǲü�ͼƬ��� 
        intent.putExtra("outputX", 150);    
        intent.putExtra("outputY", 150);  
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true); 
        intent.putExtra("return-data", false); 
        startActivityForResult(intent, 3); 
    } 
       
    /** 
     * ����ü�֮���ͼƬ���� 
     * @param picdata 
     */ 
    private void setPicToView() { 
     
        	Bitmap bm = BitmapFactory.decodeFile(Environment 
                    .getExternalStorageDirectory()+File.separator+"qxforshop/temp.jpg");
            Drawable drawable = new BitmapDrawable(bm); 
            shopLogo.setImageDrawable(drawable);
       
    }
}
