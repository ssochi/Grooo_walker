package com.example.administrator.grooo_walker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/10/24.
 */
public class loading_view extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint ;
    public int Heartrate = 0;


    public loading_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }
    public loading_view(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public void draw(){
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        draw_number(canvas);
        draw_Arc(canvas);
        draw_number(canvas);
//        draw_text(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }

    private void draw_text(Canvas canvas) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth((float) 10.0);              //线宽
        paint.setTypeface(Typeface.SANS_SERIF);
        paint.setTextSize(100);


    }

    private void draw_number(Canvas canvas) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth((float) 10.0);              //线宽
        paint.setTypeface(Typeface.SANS_SERIF);
        paint.setTextSize(100);
        canvas.drawText(String.valueOf(Heartrate), 300, 350, paint);
        paint.setTextSize(40);
        canvas.drawText("bpm" , 500, 350, paint);

    }

    private void draw_Arc(Canvas canvas) {
//        paint = new Paint();
//        Shader mShader = new LinearGradient(0, 0, 100, 100,
//                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
//                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
//        paint.setShader(mShader);
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth((float) 10.0);              //线宽
//        paint.setStyle(Paint.Style.STROKE);
//        RectF oval = new RectF();
//        oval.top = 75;
//        oval.bottom = 475;
//        oval.left = 150;
//        oval.right = 550;
//        canvas.drawArc(oval,0,Heartrate*2,false,paint);


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        float mRotate = 0;
        Matrix mMatrix = new Matrix();
        Shader mShader;
        //x,y为start x,y;
        float x = 460;
        float y = 350;

        mShader = new SweepGradient(x, y, new int[] {0xFFFF6433,
                0xFFB0F44B,
                0xFF09F68C,
                0xFFE8DD30,
                0xFFF1CA2E,
                0xFFFF902F}, null);
        paint.setShader(mShader);
        paint.setStyle(Paint.Style.STROKE);
        PathEffect effect = new DashPathEffect(new float[] { 2, 3, 2,3}, 1);
        paint.setPathEffect(effect);
        paint.setStrokeWidth(80);
//        canvas.drawColor(Color.WHITE);
        mMatrix.setRotate(mRotate, x, y);
        mShader.setLocalMatrix(mMatrix);
        mRotate += 3;
        if (mRotate >= 360) {
            mRotate = 0;
        }
//        invalidate();
//        判断心率弧度
        int heartrate = Heartrate;
        if(Heartrate<=50){
            Heartrate = (int) ( 135 + Heartrate*1.68);
        }else if(Heartrate<=100){
            Heartrate = (int) ( 135 + Heartrate*1.68);
        }else if(Heartrate <=160){
            Heartrate = (int) (135 + Heartrate*1.68);
        }else{
            Heartrate = 405;
        }
        getArc(canvas, x, y, 300, 135, Heartrate, paint);//300为圆的大小 405为最大值
        Heartrate = heartrate;

    }
    public void getArc(Canvas canvas,float o_x,float o_y,float r,
                       float startangel,float endangel,Paint paint){
        RectF rect = new RectF(o_x - r, o_y - r, o_x + r, o_y + r);
        Path path = new Path();
        path.moveTo(o_x,o_y);
        path.lineTo((float)(o_x+r* Math.cos(startangel * Math.PI / 180))
                , (float)(o_y+r* Math.sin(startangel * Math.PI / 180)));
        path.lineTo((float)(o_x+r* Math.cos(endangel * Math.PI / 180))
                , (float)(o_y+r* Math.sin(endangel * Math.PI / 180)));
        path.addArc(rect, startangel, endangel-startangel);
        canvas.clipPath(path);
        canvas.drawCircle(o_x, o_y, r, paint);
    }

    private Timer timer = null ;
    private TimerTask timerTask = null;
    public void starttimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {


                Heartrate ++;
                draw();

            }
        };
        timer.schedule(timerTask,0,100);
    }
    public void stoptimer(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        starttimer();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stoptimer();
    }
}
