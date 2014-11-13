package com.tagit.esign;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class SingatureDialog extends DialogFragment {

	
	private OnSaveDialogListener mListener;
	public SingatureDialog(OnSaveDialogListener listner) {
		
		this.mListener = listner;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	public static interface OnSaveDialogListener{
		public abstract void onSave(Bitmap bitmap);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (getActivity()).getLayoutInflater();
		
		View v = inflater.inflate(R.layout.activity_signature_dialog, null);
		final MDrawingPadView drawPadView = (MDrawingPadView) v.findViewById(R.id.view_pad);
		
		Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Signature")
			   .setView(v)
			   .setPositiveButton("Okay", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 Bitmap bitmap = Bitmap.createBitmap(drawPadView.getWidth(),drawPadView.getHeight(), Bitmap.Config.ARGB_8888);
					 Canvas c = new Canvas(bitmap);
					 
					 drawPadView.layout(drawPadView.getLeft(), drawPadView.getTop(), drawPadView.getRight(), drawPadView.getBottom());
					 drawPadView.draw(c);
					 mListener.onSave(bitmap);
				}
			   })
			   .setNegativeButton("Cancel", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
		
		
		return builder.create();
	}
}
