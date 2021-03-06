package com.tagit.esign;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tagit.esign.SingatureDialog.OnSaveDialogListener;
import com.tagit.esign.utils.MUtils;

public class MainActivity extends Activity implements OnClickListener {
	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_CHOOSE_LIBRARY = 2;
	static final CharSequence[] items = {"Take a photo","Choose from library","Cancel"};
	static final String format = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	
	ImageView img_nric;
	ImageView img_nric_back;
	ImageView img_sign;
	ImageView nric_delete;
	ImageView nric_back_delete;
	ImageView sign_delete;
	TextView nric_desc;
	TextView nric_back_desc;
	TextView sign_desc;
	int selectedViewID = 0;
	TextView nric_error;
	TextView nric_back_error;
	TextView sign_error;
	Button btn_next;
	boolean status_nric;
	boolean status_nric_back;
	boolean status_sign;
	String[] ENStr = new String[3];
	String[] DEStr = new String[3];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		img_nric = (ImageView) findViewById(R.id.img_nric);
		img_nric_back = (ImageView) findViewById(R.id.img_nric_back);
		img_sign = (ImageView) findViewById(R.id.img_sign);
		
		nric_delete = (ImageView) findViewById(R.id.img_main_delete);
		nric_back_delete = (ImageView) findViewById(R.id.img_main_delete2);
		sign_delete = (ImageView) findViewById(R.id.img_main_delete3);
		
		nric_desc = (TextView) findViewById(R.id.txt_main_nric_desc);
		nric_back_desc = (TextView) findViewById(R.id.txt_main_nric_back_desc);
		sign_desc = (TextView) findViewById(R.id.txt_main_sign_desc);
		
		nric_error = (TextView) findViewById(R.id.txt_main_error_nric_view);
		nric_back_error = (TextView) findViewById(R.id.txt_main_error_nric_back_view);
		sign_error = (TextView) findViewById(R.id.txt_main_error_sign);
		
		btn_next = (Button) findViewById(R.id.btn_submit);
		
		img_nric.setOnClickListener(this);
		img_nric_back.setOnClickListener(this);
		img_sign.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		
		nric_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				img_nric.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera));
				nric_desc.setText(R.string.txt_static_main_click_title);
				nric_delete.setVisibility(View.INVISIBLE);
				nric_error.setVisibility(View.VISIBLE);
				nric_error.setText("deleted on "+format);
				status_nric = false;
			}
		});
		
		nric_back_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				img_nric_back.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera));
				nric_back_desc.setText(R.string.txt_static_main_click_title);
				nric_back_delete.setVisibility(View.INVISIBLE);
				nric_back_error.setVisibility(View.VISIBLE);
				nric_back_error.setText("deleted on "+format);
				status_nric_back = false;
			}
		});
		
		sign_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				img_sign.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera));
				sign_desc.setText(R.string.txt_static_main_click_title);
				sign_delete.setVisibility(View.INVISIBLE);
				sign_error.setVisibility(View.VISIBLE);
				sign_error.setText("deleted on "+format);
				status_sign = false;
			}
		});			
	}
	
	private String encode(Bitmap b) {
		// TODO Auto-generated method stub
		String encode = null; 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        boolean compress = b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	        if (compress) {
	        	 encode = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
			}
	     return encode;    
	}
	
	private byte[] decode(String encode) {
		// TODO Auto-generated method stub
		return Base64.decode(encode, Base64.DEFAULT);
//		imgDecode.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		selectedViewID = v.getId();
		
		if (v.getId() == R.id.img_sign) {
			new SingatureDialog(new OnSaveDialogListener() {
				
				@Override
				public void onSave(Bitmap bitmap) {
					ENStr[0] = encode(bitmap);
					img_sign.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 150, 150, true));
					sign_desc.setText(format);
					sign_delete.setVisibility(View.VISIBLE);
					sign_error.setVisibility(View.GONE);
					status_sign = true;
					
				}
			}).show(getFragmentManager(), "Signature");
		}else if(v.getId() == R.id.btn_submit){
			
			if ( status_nric && status_nric_back && status_sign ) {
				Toast.makeText(MainActivity.this, ENStr[0].toString(), Toast.LENGTH_LONG).show();
			}
		}else{
			Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setTitle("Choose");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					// TODO Auto-generated method stub
					switch (item) {
					case 0:
						Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						if (i.resolveActivity(getPackageManager()) != null) {
						        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
						}
						break;
					case 1:
						Intent intent = new Intent(Intent.ACTION_PICK);
						intent.setType("image/*");
						startActivityForResult(Intent.createChooser(intent, "Select Pic"),REQUEST_CHOOSE_LIBRARY);
						break;
						
					case 2 : 
						 
						break;
					default:
						break;
					}
				}
			});
			
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        fillImage(imageBitmap);
	    }
		
		if (requestCode == REQUEST_CHOOSE_LIBRARY && resultCode == RESULT_OK) {
			
			InputStream imageStream = null;
			try {
				imageStream = getContentResolver().openInputStream(data.getData());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
	        
	        fillImage(imageBitmap);
		}
	}
	
	private void fillImage(Bitmap bitmap) {
		
		if (selectedViewID != 0) {
			bitmap =  MUtils.ScaleDownMyBitmap(bitmap, 100, 100);
			Bitmap ScaleBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
			
			switch (selectedViewID) {
			case R.id.img_nric:
					img_nric.setImageBitmap(ScaleBitmap);
					nric_desc.setText("added on "+format);
					nric_delete.setVisibility(View.VISIBLE);
					nric_error.setVisibility(View.GONE);
					status_nric = true;
				break;
			case R.id.img_nric_back:
					img_nric_back.setImageBitmap(ScaleBitmap);
					nric_back_desc.setText("added on " +format);
					nric_back_delete.setVisibility(View.VISIBLE);
					nric_back_error.setVisibility(View.GONE);
					status_nric_back = true;
				break;
			default:
				selectedViewID = 0;
				break;
				
			}
		}
	}

}
