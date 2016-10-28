package com.example.administrator.grooo_walker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Administrator on 2015/12/5.
 */
public class Box extends Container {
    //宝箱待做

    float x;
    float y;
    int bitmap_width = 200;
    int bitmap_height = 150;
    Bitmap bmp;
    Bitmap open_box;
    int path = R.drawable.box;
    int path_open = R.drawable.box_opened;
    boolean destory = false;

    int length_of_timer = 300;
    int timer = length_of_timer;
    Box(Resources res, float x, float y){
        this.x = x-bitmap_width/2;
        this.y = y-bitmap_height/2;
        bmp = BitmapFactory.decodeResource(res,path);
        bmp = Bitmap.createScaledBitmap(bmp, bitmap_width, bitmap_height, true);
        open_box = BitmapFactory.decodeResource(res, path_open);
        open_box = Bitmap.createScaledBitmap(open_box,bitmap_width,bitmap_height,true);

    }

    @Override
    public void childrenView(Canvas canvas) {

        if(should_draw)
            canvas.drawBitmap(bmp,x,y,null);
        else {
            if(destory){
            }else {
                canvas.drawBitmap(open_box, x, y, null);
                    timer --;
                    if(timer < 0){
                        destory = true;
                    }

            }


        }

    }

    @Override
    public float getX() {
        return x+bitmap_width/2;
    }

    @Override
    public float getY() {
        return y+bitmap_height/2;
    }
}
