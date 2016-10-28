package com.example.administrator.grooo_walker;

/**
 * Created by Administrator on 2015/11/26.
 */
public class position {
    float x = 0;
    float y = 0;
    float LENGHT_OF_MOVE = 5;
    position(){

    }
    position(float x,float y){
        this.x = x;
        this.y = y;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }
    public double get_unit_X(){
        if(x == 0 && y == 0){
            return  0;
        }
        return  LENGHT_OF_MOVE*x/Math.sqrt(x*x + y*y);
    }
    public double get_unit_Y(){
        if(x == 0 && y == 0){
            return  0;
        }
        return  LENGHT_OF_MOVE*y/Math.sqrt(x*x + y*y);
    }

}
