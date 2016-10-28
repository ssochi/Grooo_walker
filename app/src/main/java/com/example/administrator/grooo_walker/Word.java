package com.example.administrator.grooo_walker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Administrator on 2015/11/26.
 */
public class Word extends  Container {
    Paint paint;
    Paint Cpaint;
    String str ;
    float x;
    float y;
    int length ;
    boolean should_change_color;
    int timer_length = 10;
    int timer =timer_length;
    int index_length = 11;
    int index = 1;
    int color_index = 0;
    Word(String input,float x,float y,boolean change_color){

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        color_index = (int) (Math.random()*11);
        setPaint(color_index);
        paint.setStrokeWidth(3);
        paint.setTextSize(80);
        str = input;
        this.x = x;
        this.y = y;
        length = input.length();
        should_change_color = change_color;
        init_cpaint();

    }
    Word(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setTextSize(80);
    }



    @Override
    public void childrenView(Canvas canvas) {
        timer--;
        if(timer<0){
            timer = timer_length;
            index ++;
        }
        if(index > index_length )
            index = 1;

        change_color(index);



//      x-((length)/2)*75 为了让字从walke的中心输出,取消原因换行就对不齐了
//        canvas.drawText(str,x-((length)/2)*75,y,paint);

        if(should_change_color){
            canvas.drawText(str,x,y,Cpaint);
        }else
            canvas.drawText(str,x,y,paint);



    }

    private void setPaint(int index) {
        switch (index){
            case 1:
                paint.setColor(Color.BLACK);
                break;
            case 2:
                paint.setColor(Color.DKGRAY);
                break;
            case 3:
                paint.setColor(Color.GRAY);
                break;
            case 4:
                paint.setColor(Color.LTGRAY);
                break;
            case 5:
                paint.setColor(Color.RED);
                break;
            case 6:
                paint.setColor(Color.RED);
                break;
            case 7:
                paint.setColor(Color.GREEN);
                break;
            case 8:
                paint.setColor(Color.BLUE);
                break;
            case 9:
                paint.setColor(Color.YELLOW);
                break;
            case 10:
                paint.setColor(Color.CYAN);
                break;
            case 11:
                paint.setColor(Color.MAGENTA);
                break;

        }
    }


    private void change_color(int index) {
        switch (index){
            case 1:
                Cpaint.setColor(Color.BLACK);
                break;
            case 2:
                Cpaint.setColor(Color.DKGRAY);
                break;
            case 3:
                Cpaint.setColor(Color.GRAY);
                break;
            case 4:
                Cpaint.setColor(Color.LTGRAY);
                break;
            case 5:
                Cpaint.setColor(Color.WHITE);
                break;
            case 6:
                Cpaint.setColor(Color.RED);
                break;
            case 7:
                Cpaint.setColor(Color.GREEN);
                break;
            case 8:
                Cpaint.setColor(Color.BLUE);
                break;
            case 9:
                Cpaint.setColor(Color.YELLOW);
                break;
            case 10:
                Cpaint.setColor(Color.CYAN);
                break;
            case 11:
                Cpaint.setColor(Color.MAGENTA);
                break;

        }
    }

    private void init_cpaint() {
        Cpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Cpaint.setColor(Color.RED);
        Cpaint.setStrokeWidth(3);
        Cpaint.setTextSize(80);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
