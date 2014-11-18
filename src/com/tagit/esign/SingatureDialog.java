package com.tagit.esign;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.tagit.esign.utils.MUtils;

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
		
		AlertDialog dialog = new AlertDialog.Builder(getActivity())
			   .setTitle("Signature")
			   .setView(v)
			   .setPositiveButton("Use This", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					 //create underlying bitmap
					 Bitmap bitmap = Bitmap.createBitmap(drawPadView.getWidth(),drawPadView.getHeight(), Bitmap.Config.ARGB_8888);
					 //Define bitmap in Canvas
					 Canvas c = new Canvas(bitmap);
					 //use Bitmap's draw method with parameter of canvas
					 drawPadView.draw(c);
					 mListener.onSave(MUtils.ScaleDownMyBitmap(bitmap , 100 , 60));
				}
			   })
			   .setNeutralButton("Re-sign", new OnClickListener() {
				   	@Override
					public void onClick(DialogInterface dialog, int which) {}
				})
			   .setNegativeButton("Cancel", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).create();
		
			dialog.setOnShowListener(new OnShowListener() {
				
				@Override
				public void onShow(DialogInterface d) {
					// TODO Auto-generated method stub
					((AlertDialog)d).getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							drawPadView.clearCanvas();
						}
					});
				}
			});
		return dialog;
	}
}
