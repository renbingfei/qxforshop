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

	//������
	private ImageView back;  //����
	private TextView finish; //���
	
	//��Ҫ����
	private EditText goodNameEt; //��Ʒ����
	private ImageView addGoodIv; //�����ƷͼƬ
	private Gallery gallery; //��ʾ��ƷͼƬ
	private EditText goodInfoEt; //��Ʒ����
	private Spinner typeSpinner; //��Ʒ���
	private String typeStr;
	private ArrayAdapter<String> typeSpinnerAdatper;
	private Spinner subTypeSpinner; //����� 
	private String subTypeStr; 
	private ArrayAdapter<String> subTypeSpinnerAdatper;
	private EditText originPriceEt; //����ԭ��
	private Spinner measurementSpinner; //������λ
	private String measurementStr;
	private ArrayAdapter measurementSpinnerAdapter;
	private EditText qxPriceEt; //�����ּ�
	private EditText leftNumberEt; //�������
	private EditText qxStartTimeEt; //���ʿ�ʼʱ��
	private EditText qxStopTimeEt; //���ʽ���ʱ��
	private TextView downloadGood; //�ϼ���Ʒ
	private TextView deleteGood; //ɾ����Ʒ
	//gallery������
	LinkedList<HashMap<String, Object>> data ;
	AddGoodAdapter galleryAdapter ;
	private String basePath; //��ƷͼƬ����·��,�ϴ�����ɾ��
	private String fileName ; //��Ʒ����
	//Spinner
	private String[] type = new String[] {"ˮ��","�߲�","���","����","��ʳ","ȫ��"};
	private String[][] subType = new String[][]{
			{ "ȫ��","ƻ��","��","�㽶","����","������","����","����", "��ݮ","����","����","��֦"},
			{ "ȫ��","�ײ�","�ܲ�","�ƹ�","����","�۲�","����","����","����"},
			{ "ȫ��","�̶���","��ɳ��","�ඹ��","÷����","���","����"},
			{ "ȫ��","����","����","����","ţ��","¿��","����"},
			{ "ȫ��","��ţ��","������","������","������Ѽ"},
			{ "ȫ��","ˮ��","�߲�","���","����","��ʳ"}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_selling_good);
		initActionBar();
		initWidget();
		//��ʼ����ͨ
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
		typeStr = "ˮ��";
		subTypeStr = "ȫ��";
		measurementStr = "��";
		
	}
	public void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.global_modify_goods_navigation);
	}
	
	public void initWidget(){
		back = (ImageView)findViewById(R.id.back);
		finish = (TextView)findViewById(R.id.finish);
		//��ʼ������
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
		//��Ӽ���
		back.setOnClickListener(this);
		finish.setOnClickListener(this);
		downloadGood.setOnClickListener(this);
		deleteGood.setOnClickListener(this);
		//Ϊ������Ӽ���
//		addGoodIv.setOnClickListener(this);
//		gallery.setOnItemLongClickListener(this);
//		publishGoodIv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == back){
			//��ɾ�������ͼƬ�ļ�
			Common.RecursionDeleteFile(new File(basePath));
			finish();
			
		}else if(v == finish){
			//���
			//��ɾ�������ͼƬ�ļ�
			Common.RecursionDeleteFile(new File(basePath));
			finish();
		}else if(v == addGoodIv){
			//�����Ʒ
//			if(data.size()<6){
//				changePhoto();
//			}else{
//				Toast.makeText(ModifySelledGoodActivity.this, "������6��ͼƬ", Toast.LENGTH_SHORT).show();
//			}
			
		}else if(v == downloadGood){
			//�ϼ���Ʒ
			
		}else if( v == deleteGood){
			//ɾ����Ʒ
			
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
     * ȡ��SD���е�ͼƬ��Ҳ�ɸ�Ϊȡ��������ݿ�ͼƬ�� 
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
						.setTitle("�����ƷͼƬ")
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
								fileName = Common.getFileName()+".jpg";
		                        Intent intent = new Intent(Intent.ACTION_PICK, null); 
		                           
		                        /** 
		                         * ������仰����������ʽд��һ����Ч��������� 
		                         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
		                         * intent.setType(""image/*");������������ 
		                         * ���������Ҫ�����ϴ�����������ͼƬ����ʱ����ֱ��д�磺"image/jpeg �� image/png�ȵ�����" 
		                         * 
		                         */
		                      //�������ָ������������պ����Ƭ�洢��·�� 
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
						.setNegativeButton("����", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss(); 
								fileName = Common.getFileName()+".jpg";
		                        Intent intent = new Intent( 
		                                MediaStore.ACTION_IMAGE_CAPTURE); 
		                        //�������ָ������������պ����Ƭ�洢��·�� 
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
            File temp = new File(basePath+File.separator
                    + fileName); 
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
                .fromFile(new File(basePath, 
                        fileName));
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

            HashMap<String, Object> map = new HashMap<String, Object>(); 
            map.put("image", BitmapFactory.decodeFile(basePath+File.separator+fileName));
            map.put("imageUrl", fileName);
            data.addFirst(map);
            
            galleryAdapter.notifyDataSetChanged();
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//���¼����Ϸ��ذ�ť
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
