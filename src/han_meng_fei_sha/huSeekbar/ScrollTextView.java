package han_meng_fei_sha.huSeekbar;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class ScrollTextView extends View
{
	private Paint mPaint = new Paint();
	private Context context;
	
	public ScrollTextView(Context context)
	{
		super(context);
		mPaint.setAntiAlias(true);
		this.context = context;
	}
	
	public ScrollTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mPaint.setAntiAlias(true);
		this.context = context;
	}
	
	float x =200,y=300;
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch (e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			Log.d("lc", "e.getX() :" + e.getX() + "e.getY() :" + e.getY());
			break;
		case MotionEvent.ACTION_MOVE:
			x=e.getX();y=e.getY(); 
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
		}
		return true;
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(30);
		canvas.drawText("测试文字", x, y, mPaint);
	}
	
	 
}
