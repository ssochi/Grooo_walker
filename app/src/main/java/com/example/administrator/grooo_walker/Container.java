package com.example.administrator.grooo_walker;

/**
 * Created by Administrator on 2015/11/26.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.Toast;

public class Container {

    private List<Container> children = null;
    float x = 0, y = 0;
    boolean should_draw = true;
    boolean open_box_in_this_step = false;
    boolean say_something = false;
    boolean say_nothing = false;
    int SAY_HAVE_NOTHING = 1;
    int SAY_HAVA_SOMETHING = 2;
    int SAY_NOTHING = 0;
    int Value_of_say = SAY_NOTHING;
    int Value_for_say_something = 5;
    int timer_for_say_something = Value_for_say_something;
       Context context ;



    public Container() {
        children = new ArrayList<Container>();
    }
    public Container(Context context) {
        children = new ArrayList<Container>();
        this.context = context;


    }

    public void draw(Canvas canvas) {
        canvas.save();
        childrenView(canvas);
        for (Container c : children) {
            c.draw(canvas);
        }
        canvas.restore();
    }

    public void childrenView(Canvas canvas) {

    }

    public void addChildrenView(Container child) {
        children.add(child);
    }

    public void removeChildrenView(int id) {
        children.remove(id);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setChildren_should_draw(int id,boolean boo) {
        if(children.get(id).should_draw == boo){

        }
            else{
            if(analysis_hava_or_not()){
                open_box_in_this_step = true;//返回给Grooo——walker.java
                say_something = true;
                Value_of_say = SAY_HAVA_SOMETHING;
            }else {
                open_box_in_this_step = false;//返回给Grooo——walker.java
                Value_of_say = SAY_HAVE_NOTHING;
            }

            children.get(id).should_draw = boo;
            timer_for_say_something = Value_for_say_something;
        }

    }

    private boolean analysis_hava_or_not() {
        int Value = (int) (Math.random()*2);
        if(Value < 1)
            return  true;
        else
            return  false;

    }

    public boolean isOpen_box_in_this_step() {
        return open_box_in_this_step;
    }

    public void setOpen_box_in_this_step(boolean open_box_in_this_step) {
        this.open_box_in_this_step = open_box_in_this_step;
    }


}