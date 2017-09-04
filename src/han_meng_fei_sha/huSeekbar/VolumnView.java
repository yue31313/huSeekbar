package han_meng_fei_sha.huSeekbar;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;

//自定义的转盘View
public class VolumnView extends View 
{
	private float screenHight, screenWidth;// 屏幕的宽和高
	private float radius;// 绘制圆的半径
	private float circleRadius; // 半径
	private float startAngle;// 开始角度
	private float sweepAngle; // 扫过的角度
	private float currentAngle;//当前的角度
	private int[] itemColor;
	private Paint mPaint;
	private float mPointX;  //中心点
	private float mPointY;
	private float startPointX;
	private float startPointY;
	private boolean isPressed = false;
	private ProgressBar progressbar ;
	
	public VolumnView(Context context, AttributeSet attr)
	{
		super(context, attr);
		initial();
	}
	public void setProgressBar(ProgressBar progressbar)
	{
		this.progressbar = progressbar;
	}
	
	public void initial()
	{
		
		itemColor = new int[] { 0xFFFFFFFF,// 白色
				0xFFB0E0E6,// 粉蓝色　
				0xFF444444,// 深灰色
				0xFF008B8B,// 暗青色
				0xFFFFA500,// 橙色
				0xFF7FFF00,// 黄绿色
				0xFFF08080,// 亮珊瑚色
				0xFFff0000 // 亮钢兰色
		};
		screenHight = getHeight();
		screenWidth = getWidth();
		// 半径
		radius = 50;
		circleRadius = 20;
		startAngle = 0;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch (e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
//			Log.d("lc", "e.getX() :" + e.getX() + "e.getY() :" + e.getY());
			isPressed  = true;
			startPointX = e.getX();
			startPointY = e.getY();
			progressbar.setVisibility(View.VISIBLE);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
//			Log.d("lc", "e.getX() :" + e.getX() + "e.getY() :" + e.getY());
			sweepAngle = computeCurrentAngle(e.getX(), e.getY());
			Log.d("lc", "sweepAngle:"+sweepAngle +"startAngle :"+startAngle);
			if(startAngle >= 0 && startAngle <= 180)
			startAngle += sweepAngle;
			if(startAngle < 0)
			startAngle = 0;
			if(startAngle > 180)
		    startAngle = 180;
			startPointX = e.getX();
			startPointY = e.getY();
			progressbar.setProgress((int) (startAngle*100/180));
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			isPressed  = false;
			sweepAngle = 0;
			invalidate();
			progressbar.setVisibility(View.INVISIBLE);
		}
		return true;
	}
	
	private float computeCurrentAngle(float x, float y)
	{
		float ac = (float) Math
				.sqrt(((startPointX - mPointX) * (startPointX - mPointX) + (startPointY - mPointY)
						* (startPointY - mPointY)));
		float bc = (float) Math
				.sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY)
						* (y - mPointY)));
		float ab = (float) Math
				.sqrt(((x - startPointX) * (x - startPointX) + (y - startPointY)
						* (y - startPointY)));
		float degree = (float)(Math.acos((ac*ac+bc*bc-ab*ab) / (2*ac*bc)) * 180 / Math.PI);
		
		if(!isClosewise(startPointX, startPointY, x, y, mPointX, mPointY))
		{
			degree = -degree;
		}
		return  (float) (degree/1.2) ;
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		updateView(canvas);
	}
	
	public void updateView(Canvas canvas)
	{
		mPointX = 75;
		mPointY = 75;
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		canvas.drawColor(0x00000000);// 背景色
		// *********************************画边上渐变的圆环出来*********************************
		Paint localPaint = new Paint();
		localPaint.setAntiAlias(true);
		localPaint.setStyle(Paint.Style.STROKE);// 风格为圆环
		localPaint.setStrokeWidth(circleRadius); // 圆环宽度
		if(isPressed)
		{
			RadialGradient radialGradient = new RadialGradient(mPointX, mPointY,
					radius + 30, new int[] {0xFF00ccFF, 0xcc00ccFF,
					0xaa00ccFF, 0x6600ccFF, 0x0000ccFF}, null,
					TileMode.MIRROR);// 环形渐变
			localPaint.setShader(radialGradient);// 设置渐变
			canvas.drawCircle(mPointX, mPointY, radius, localPaint);
		}
		else{
			RadialGradient radialGradient = new RadialGradient(mPointX, mPointY,
					radius + circleRadius, new int[] {0xFF00ccFF, 0xaa00ccFF,
					0x7700ccFF, 0x3300ccFF, 0x0000ccFF}, null,
					TileMode.MIRROR);// 环形渐变
			localPaint.setShader(radialGradient);// 设置渐变
			canvas.drawCircle(mPointX, mPointY, radius, localPaint);
		}
		float f3 = mPointX - radius;
		float f4 = mPointY - radius;
		float f5 = mPointX + radius;
		float f6 = mPointY + radius;
		RectF rectF = new RectF(f3,  f4, f5, f6);
		mPaint.setColor(itemColor[2]);
//		canvas.drawArc(rectF, startAngle, 30, true, mPaint);
//		mPaint.setShader(shader);
		canvas.drawOval(rectF , mPaint);
		mPaint.setColor(Color.RED);
		float x = mPointX
				+ (float) (40 * Math.cos((startAngle+ 180) * Math.PI / 180));
		float y = mPointY
				+ (float) (40 * Math.sin((startAngle+ 180)* Math.PI / 180));
		canvas.drawCircle(x, y, 6, mPaint);
//		mPaint.setColor(itemColor[2]);
//		canvas.drawArc(rectF, startAngle + 30, 330, true, mPaint);
		// *********************************画中间的黑色*********************************
//		Paint localPaint2 = new Paint();
//		localPaint2.setAntiAlias(true);
//		localPaint2.setStrokeWidth(5);
//		localPaint2.setColor(Color.BLACK);
//		canvas.drawCircle(mPointX, mPointY, 6, localPaint2);
	}
	// 画上各个Item的名称
	// public void drawText(RectF localRectf, float localStartAngle,
	// float localSweepAngle, String str) {
	// path = new Path();
	// float pading = (sweepAngle-str.length())/2;
	// 在这里注意了，这里path中的sweepAngle为正值，所以逆时针转的时候应该判断一下
	// if (localSweepAngle > 0) {
	// path.addArc(localRectf, localStartAngle, localSweepAngle);
	// }
	// path.addArc(localRectf, localStartAngle - localSweepAngle,
	// -localSweepAngle);
	// mCanvas.drawTextOnPath(str, path, 5, -10, mPaint);
	// mCanvas.save();
	// }
	// @Override
	// public void run()
	// {
	// // TODO Auto-generated method stub
	// super.run();
	// // 公共在这里处理
	// mPaint.setAntiAlias(true);
	// while (!done)
	// {
	// updateView();
	// }
	/**
	 * 判断顺时针
	 * 
	 * @param x1
	 *            第一个点的X坐标
	 * @param y1
	 *            第一个点的Y坐标
	 * @param x2
	 *            第二个点的X坐标
	 * @param y2
	 *            第二个点的Y坐标
	 * @param x
	 *            中心点的x坐标
	 * @param y
	 *            中心点的y坐标
	 * @return 是否
	 */
	public static boolean isClosewise(float x1, float y1, float x2, float y2,
			float x, float y)
	{
		float dx = x2 - x1;
		float dy = y2 - y1;
		if ((dx == 0 & dy > 0 & x1 > x) || (dy == 0 & dx < 0 & y1 > y) ||(dx == 0 & dy < 0 & x1 < x) || (dy == 0 & dx > 0 & y1 < y)
				|| (dx > 0 & dy < 0 & y1 < y) || (dx > 0 & dy > 0 & y1 < y)
				|| (dx < 0 & dy > 0 & y1 > y) || (dx < 0 & dy < 0 & y1 > y))
		{
			return true;
		}
		return false;
	}
	// }
}