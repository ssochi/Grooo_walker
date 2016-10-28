package com.example.administrator.grooo_walker;

import android.view.View;

/**
 * Created by Administrator on 2015/12/2.
 */
public class Area extends Container {
    int X_index ;
    int Y_index ;
    float Window_height ;
    float Window_width;
    Area(View view,int x,int y){
        Window_height = view.getHeight();
        Window_width = view.getWidth();
        X_index = x;
        Y_index = y;
    }



}
