package com.tagit.esign;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	static final int REQUEST_IMAGE_CAPTURE = 1;
	ImageView img1;
	ImageView img4;
	ImageView imgDecode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView img_nric = (ImageView) findViewById(R.id.img_nric);
		img1 = (ImageView) findViewById(R.id.imageView1);
		img4 = (ImageView) findViewById(R.id.imageView4);
		imgDecode = (ImageView) findViewById(R.id.imageView2);
		ImageView img3 = (ImageView) findViewById(R.id.imageView3);
		
		img3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				img1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera));
			}
		});
		
		img_nric.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				 if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
				        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
				 }
			}
		});
		
		img4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "Select Pic"),2);
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        boolean compress = imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	        if (compress) {
	        	String encode = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
			}
	        img1.setImageBitmap(imageBitmap);
	    }
		
		if (requestCode == 2 && resultCode == RESULT_OK) {
			Uri data2 = data.getData();
			InputStream imageStream = null;
			try {
				imageStream = getContentResolver().openInputStream(data2);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	            
	            
//			Uri data2 = data.getData();
//			Log.d("Data2", data2.toString());
//			String[] filePathColoum = {MediaStore.Images.Media.DATA};
//			
//			Cursor cursor = getContentResolver().query(
//					data2, filePathColoum, null, null, null);
//		 cursor.moveToFirst();
//		
//		 int columnIndex = cursor.getColumnIndex(filePathColoum[0]);
//		 String filePath = cursor.getString(columnIndex);
//		 cursor.close();
//
//
//		 Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
		 img4.setImageBitmap(yourSelectedImage);
		}
	}

	private void decode(String encode) {
		// TODO Auto-generated method stub
		byte[] decode = Base64.decode(encode, Base64.DEFAULT);
		imgDecode.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
