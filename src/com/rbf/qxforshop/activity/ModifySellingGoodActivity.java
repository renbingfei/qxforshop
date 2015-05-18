package com.rbf.qxforshop.activity;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import com.rbf.qxforshop.MyApplication;
import com.rbf.qxforshop.R;
import com.rbf.qxforshop.activity.myshop.MyShopActivity;
import com.rbf.qxforshop.adapter.AddGoodAdapter;
import com.rbf.qxforshop.utils.Common;

import android.app.AlertDialog;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ModifySellingGoodActivity extends ActionBarActivity implements OnClickListener, OnItemLongClickListener, OnItemSelectedListener{

	//导航条
	private ImageView back;  //返回
	private TextView finish; //完成
	
	//主要部件
	private EditText goodNameEt; //商品名称
	private ImageView addGoodIv; //添加商品图片
	private Gallery gallery; //显示商品图片
	private EditText goodInfoEt; //商品描述
	private Spinner typeSpinner; //商品类别
	private String typeStr;
	private ArrayAdapter<String> typeSpinnerAdatper;
	private Spinner subTypeSpinner; //子类别 
	private String subTypeStr; 
	private ArrayAdapter<String> subTypeSpinnerAdatper;
	private EditText originPriceEt; //销售原价
	private Spinner measurementSpinner; //计量单位
	private String measurementStr;
	private ArrayAdapter measurementSpinnerAdapter;
	private EditText qxPriceEt; //抢鲜现价
	private EditText leftNumberEt; //库存数量
	private EditText qxStartTimeEt; //抢鲜开始时间
	private EditText qxStopTimeEt; //抢鲜结束时间
	private TextView downloadGood; //上架商品
	private TextView deleteGood; //删除商品
	//gallery的数据
	LinkedList<HashMap<String, Object>> data ;
	AddGoodAdapter galleryAdapter ;
	private String basePath; //商品图片保存路径,上传完需删除
	private String fileName ; //商品名字
	//Spinner
	private String[] type = new String[] {"水果","蔬菜","糕点","生鲜","熟食","全部"};
	private String[][] subType = new String[][]{
			{ "全部","苹果","梨","香蕉","葡萄","火龙果","西瓜","柚子", "草莓","橘子","橙子","荔枝"},
			{ "全部","白菜","萝卜","黄瓜","茄子","芹菜","菠菜","番茄","大蒜"},
			{ "全部","绿豆糕","豆沙糕","赤豆糕","梅花糕","香糕","蒸糕"},
			{ "全部","鱼肉","羊肉","猪肉","牛肉","驴肉","海鲜"},
			{ "全部","熟牛肉","熟羊肉","熟猪肉","秘制老鸭"},
			{ "全部","水果","蔬菜","糕点","生鲜","熟食"}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_selling_good);
		initActionBar();
		initWidget();
		//初始化普通
		init();
		MyApplication.getInstance().addActivity(this);
	}
	
	public void init(){
		basePath = Environment 
                .getExternalStorageDirectory()+File.separator+"qxforshop/goodImages";
		data = new LinkedList<HashMap<String, Object>>();
		data = getImages();
		galleryAdapter = new AddGoodAdapter(this, data);
		
		gallery.setAdapter(galleryAdapter);
		gallery.setSelection(gallery.getCount()/2);
		//spinner
		measurementSpinnerAdapter = ArrayAdapter.createFromResource(ModifySellingGoodActivity.this, R.array.measurementSpinner, android.R.layout.simple_spinner_item);
		measurementSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		measurementSpinner.setAdapter(measurementSpinnerAdapter);
		measurementSpinner.setOnItemSelectedListener(this);
		measurementSpinner.setSelection(0, true);
		
		typeSpinnerAdatper = new ArrayAdapter<String>(ModifySellingGoodActivity.this, android.R.layout.simple_spinner_item, type);
		typeSpinner.setAdapter(typeSpinnerAdatper);
		typeSpinner.setOnItemSelectedListener(this);
		typeSpinner.setSelection(0, true);
		
		subTypeSpinnerAdatper = new ArrayAdapter<String>(ModifySellingGoodActivity.this, android.R.layout.simple_spinner_item, subType[0]);
		subTypeSpinner.setAdapter(subTypeSpinnerAdatper);
		subTypeSpinner.setOnItemSelectedListener(this);
		subTypeSpinner.setSelection(0, true);
		//
		typeStr = "水果";
		subTypeStr = "全部";
		measurementStr = "斤";
		
	}
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.global_modify_goods_navigation);
	}
	
	public void initWidget(){
		back = (ImageView)findViewById(R.id.back);
		finish = (TextView)findViewById(R.id.finish);
		//初始化部件
		goodNameEt = (EditText)findViewById(R.id.goodNameEt);
		addGoodIv = (ImageView)findViewById(R.id.addGoodIv);
		gallery= (Gallery)findViewById(R.id.gallery);
		goodInfoEt = (EditText)findViewById(R.id.goodInfoEt);
		typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
		subTypeSpinner = (Spinner)findViewById(R.id.subTypeSpinner);
		originPriceEt = (EditText)findViewById(R.id.originPriceEt);
		measurementSpinner = (Spinner)findViewById(R.id.measurementSpinner);
		qxPriceEt = (EditText)findViewById(R.id.qxPriceEt);
		leftNumberEt = (EditText)findViewById(R.id.leftNumberEt);
		qxStartTimeEt = (EditText)findViewById(R.id.qxStartTimeEt);
		qxStopTimeEt = (EditText)findViewById(R.id.qxStopTimeEt);
		downloadGood = (TextView)findViewById(R.id.downloadGood);
		deleteGood = (TextView)findViewById(R.id.deleteGood);
		//添加监听
		back.setOnClickListener(this);
		finish.setOnClickListener(this);
		downloadGood.setOnClickListener(this);
		deleteGood.setOnClickListener(this);
		//为部件添加监听
//		addGoodIv.setOnClickListener(this);
//		gallery.setOnItemLongClickListener(this);
//		publishGoodIv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == back){
			//先删除保存的图片文件
			Common.RecursionDeleteFile(new File(basePath));
			finish();
			
		}else if(v == finish){
			//完成
			//先删除保存的图片文件
			Common.RecursionDeleteFile(new File(basePath));
			finish();
		}else if(v == addGoodIv){
			//添加商品
//			if(data.size()<6){
//				changePhoto();
//			}else{
//				Toast.makeText(ModifySelledGoodActivity.this, "最多添加6张图片", Toast.LENGTH_SHORT).show();
//			}
			
		}else if(v == downloadGood){
			//上架商品
			
		}else if( v == deleteGood){
			//删除商品
			
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		data.remove(position);
		galleryAdapter.notifyDataSetChanged();
		return false;
	}
	
	/** 
     * 取出SD卡中的图片（也可改为取网络或数据库图片） 
     * @return 
     */  
    private LinkedList<HashMap<String, Object>> getImages() {  
    	LinkedList<HashMap<String, Object>> list = new LinkedList<HashMap<String, Object>>(); 
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i=1; i<=3; i++) {  
            map.put("image", BitmapFactory.decodeResource(getResources(), R.drawable.ic_prompt));
            map.put("imageUrl", "");
            list.add(map);
        }  
        return list;   
    }
    
    private void changePhoto(){
		new AlertDialog.Builder(ModifySellingGoodActivity.this)
						.setTitle("添加商品图片")
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
								fileName = Common.getFileName()+".jpg";
		                        Intent intent = new Intent(Intent.ACTION_PICK, null); 
		                           
		                        /** 
		                         * 下面这句话，与其它方式写是一样的效果，如果： 
		                         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
		                         * intent.setType(""image/*");设置数据类型 
		                         * 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 
		                         * 
		                         */
		                      //下面这句指定调用相机拍照后的照片存储的路径 
		                        File dir = new File(basePath);
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
								fileName = Common.getFileName()+".jpg";
		                        Intent intent = new Intent( 
		                                MediaStore.ACTION_IMAGE_CAPTURE); 
		                        //下面这句指定调用相机拍照后的照片存储的路径 
		                        File dir = new File(basePath);
		                        if(!dir.exists()){
		                        	dir.mkdir();
		                        }
		                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri 
		                                .fromFile(new File(basePath+File.separator
		                	                    + fileName))); 
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
            File temp = new File(basePath+File.separator
                    + fileName); 
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
                .fromFile(new File(basePath, 
                        fileName));
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

            HashMap<String, Object> map = new HashMap<String, Object>(); 
            map.put("image", BitmapFactory.decodeFile(basePath+File.separator+fileName));
            map.put("imageUrl", fileName);
            data.addFirst(map);
            
            galleryAdapter.notifyDataSetChanged();
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//按下键盘上返回按钮
		if(keyCode == KeyEvent.KEYCODE_BACK){
				Common.RecursionDeleteFile(new File(basePath));	
				finish();
				
				return false;
			}else{		
				return super.onKeyDown(keyCode,event);
			}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(parent == measurementSpinner){
			measurementStr = (String) measurementSpinnerAdapter.getItem(position);
			System.out.println("**************"+measurementStr);
		}else if(parent == typeSpinner){
			typeStr = (String) typeSpinnerAdatper.getItem(position);
			System.out.println("**************"+typeStr);
			subTypeSpinnerAdatper = new ArrayAdapter<String>(ModifySellingGoodActivity.this, android.R.layout.simple_spinner_item, subType[position]);
			subTypeSpinner.setAdapter(subTypeSpinnerAdatper);
		}else if(parent == subTypeSpinner){
			subTypeStr = (String)subTypeSpinnerAdatper.getItem(position);
			System.out.println("**************"+subTypeStr);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
