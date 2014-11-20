package com.tagit.esign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
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
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setPathEffect(new CornerPathEffect(20));
		paint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// one of the drawing method of Canvas
		canvas.drawPath(path,paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
				path.moveTo(x, y);
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_MOVE:
			path.lineTo(x, y);
			invalidate();//saying android framework that to draw and call onDraw() method
		break;	 	
		default:
			
			break;
		}
		
		
		return true;
	}

	public void clearCanvas() {
		// TODO Auto-generated method stub
		path.reset();
		invalidate();
	}
	
	
}
