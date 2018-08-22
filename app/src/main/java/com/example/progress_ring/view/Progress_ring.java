package com.example.progress_ring.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.example.progress_ring.R;

public class Progress_ring extends View {

    private Paint mPaint= new Paint(); //画笔;
    private int mWidth = 0;
    private int mHeight = 0;
    private float mPadding = 0f;
    private float raduis; //自定义view的最大半径
    private float doughnutWidth;//圆环宽度
    private float currentAngle = 0f; //当前旋转角度

    private static final int RED = 69, GREEN = 139, BLUE = 0; //基础颜色
    private static final int MIN_ALPHA = 20; //最小不透明度
    private static final int MAX_ALPHA = 255; //最大不透明度

    //圆环默认颜色
    private static int[] doughnutColors = new int[]{
            Color.argb(MIN_ALPHA, RED, GREEN, BLUE),
            Color.argb(MIN_ALPHA, RED, GREEN, BLUE),
            Color.argb(MAX_ALPHA, RED, GREEN, BLUE)};


    private Thread thread = new Thread(){
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }
    };

    public Progress_ring(Context context) {
        this(context,null);

    }

    public Progress_ring(Context context, AttributeSet attrs) {
        this(context,attrs,0);

    }

    public Progress_ring(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Progress_ring);
        //取到自定义圆环宽度
        doughnutWidth = ta.getFloat(R.styleable.Progress_ring_doughnutWidth,10);
        ta.recycle();
        init();

    }
    //起动旋转线程
    private void init(){
        thread.start();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        raduis=mWidth/2;
        mPadding = 5;

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将画布中心设为原点(0,0), 方便后面计算坐标
        canvas.translate(mWidth / 2, mHeight / 2);

        //动起来
        canvas.rotate(currentAngle, 0, 0);
        if (currentAngle >= 360f){
            currentAngle = currentAngle - 360f;
        } else{
            currentAngle = currentAngle + 8f;
        }

        //圆环外接矩形
        RectF rectF = new RectF(raduis-mPadding, raduis-mPadding, -(raduis-mPadding), -(raduis-mPadding));
        initPaint();
        mPaint.setStrokeWidth(doughnutWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(new SweepGradient(0, 0, doughnutColors, null));
        canvas.drawArc(rectF, 0, 360, false, mPaint);

        //画旋转头部圆
        initPaint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.argb(MAX_ALPHA, RED, GREEN, BLUE));
        canvas.drawCircle(raduis-mPadding, 0, doughnutWidth / 2, mPaint);


    }


    private void initPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
    }

}