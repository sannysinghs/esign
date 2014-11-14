package com.tagit.esign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.EventLog.Event;
import android.view.MotionEvent;
import android.view.View;

public class MDrawingPadView extends View {

	
	private Paint paint;
	private Path path;

	public MDrawingPadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		path = new Path();
		
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(5f);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// one of the drawing method of Canvas
		canvas.drawPath(path,paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
				path.moveTo(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			path.lineTo(x, y);
		break;
	
		default:
			
			break;
		}
		
		invalidate();//saying android framework that to draw and call onDraw() method
		return true;
	}

	
	
	

}
