package com.example.administrator.grooo_walker;

/**
 * Created by Administrator on 2015/12/2.
 */
public class Rect {
    float x;
    float y;
    int length ;
    int heigth ;
    Rect(float x,float y,int length,int heigth){
        this.x = x;
        this.y = y;
        this.length = length;
        this.heigth = heigth;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getLength() {
        return length;
    }
}
