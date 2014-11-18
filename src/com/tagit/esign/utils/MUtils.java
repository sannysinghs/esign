package com.tagit.esign.utils;

import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class MUtils {

	public static Bitmap ScaleDownMyBitmap(Bitmap bitmap , int reqWidth , int reqHeight){
		 
		 ByteArrayOutputStream os = new ByteArrayOutputStream();
		 //Compress the bitmap with JPEG format and output it as BAOS
		 bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
		 //convert stream to array
		 byte[] mBitmapByteArray = os.toByteArray();
		 
		 Options options = new BitmapFactory.Options();
		 options.inJustDecodeBounds = true;
		 
		 BitmapFactory.decodeByteArray(mBitmapByteArray, 0, mBitmapByteArray.length, options);
		 int inSampleSize = 1;
		 //to get more accurate width and height of desired dimension
		 int outHeight = options.outHeight/2;
		 int outWidth = options.outWidth/2;
		 //calculate the inSampleSize , power of 2 
		 while ( (outWidth / inSampleSize ) > reqWidth && (outHeight/inSampleSize) > reqHeight ) {
			inSampleSize *= 2;
		 }
		 options.inSampleSize = inSampleSize;
		 
		 options.inJustDecodeBounds = false;
		 
		 return BitmapFactory.decodeByteArray(mBitmapByteArray, 0, mBitmapByteArray.length, options);
		 
		
	}
}
