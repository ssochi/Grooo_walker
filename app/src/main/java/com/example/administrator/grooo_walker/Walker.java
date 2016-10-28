package com.example.administrator.grooo_walker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/26.
 */
public class Walker extends  Container {
    Paint paint ;
    Paint mpaint;
    Bitmap bmp;
    android.graphics.Rect src = new android.graphics.Rect() ;
    android.graphics.Rect dst = new android.graphics.Rect();
    float heigth;
    float width;
    position position ;
    int Bitmap_width = 300 ;
    int Bitmap_height = 300;
    Bitmap Abmp;
    int changed_heigth =50;
    int changed_width =50;
    boolean should_change = true;
    int timer_value = 20;
    int timer = timer_value;
    List<Word> word_list ;
    List<Box> box_list;
    List<Integer> id_list;
    position real ;
    int index = 0;
    boolean say_some_joke = false;
    int Value_of_joke = 800;//1000
    int timer_for_joke = Value_of_joke;
    int Case_for_joke ;
    int Value_of_one_joke = 450;//600
    int timer_for_one_joke;
    boolean say_joke = false;
    Bitmap dialog_box ;
    int DIALOG_BOX_WIDTH ;
    int DIALOG_BOX_HEIGTH = 200;
    String str;






// ####################  Walker 的初始坐标应该为（0，0）  position 的初始是（-150，-150）
//x轴正方向朝向右  y轴正方向朝向下

    Walker(Resources res,int path,float heigth,float width,Context context){
        bmp= BitmapFactory.decodeResource(res, path);
       // inti_src_dst();


        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(6);
        paint.setTextSize(50);
        this.heigth = heigth;
        this.width = width;
        word_list = new ArrayList<>();
        box_list = new ArrayList<>();
        id_list = new ArrayList<>();
        bmp = Bitmap.createScaledBitmap(bmp,Bitmap_width,Bitmap_height,true);
        Abmp = Bitmap.createScaledBitmap(bmp,Bitmap_width+changed_width,Bitmap_height + changed_heigth,true);
        position = new position();
        position.setX(-Bitmap_width / 2);
        position.setY(-Bitmap_height / 2);
        this.context = context;
        dialog_box = BitmapFactory.decodeResource(res, R.drawable.dialog_box);


    }

//    private void inti_src_dst() {
//        src = new Rect();
//        src.left = 0;
//        src.top = 0;
//        src.right = 50;
//        src.bottom = 50;
//        dst = new Rect();
//        dst.left = 0;
//        dst.top = 0;
//        dst.right = 1000;
//        dst.bottom =1000;
//    }

    @Override
    public void childrenView(Canvas canvas) {
        if (should_change){
            canvas.drawBitmap(Abmp, position.getX(), position.getY(), null);
        }
        else
            canvas.drawBitmap(bmp, position.getX(), position.getY(), null);
        if(Value_of_say != 0){
            say_joke = false;
            say_some_joke = false;
            if(timer == timer_value){
                timer_for_say_something --;
            }
            if(timer_for_say_something<0){
                timer_for_say_something = Value_for_say_something;
                Value_of_say = SAY_NOTHING;
            }
            switch (Value_of_say){

                case  1:
                     str = "哎，你找到了一团空气";

                    dialog_box = Bitmap.createScaledBitmap(dialog_box,str.length()*100,DIALOG_BOX_HEIGTH,true);
                    canvas.drawBitmap(dialog_box,-Bitmap_width+position.getX(),position.getY()-Bitmap_height/2,null);
                    canvas.drawText(str,-Bitmap_width/2+position.getX()+100,position.getY()-Bitmap_height/6,paint);
                    break;
                case  2:
                     str = "恭喜哦下一条留言会炫彩";
                    dialog_box = Bitmap.createScaledBitmap(dialog_box,str.length()*100,DIALOG_BOX_HEIGTH,true);
                    canvas.drawBitmap(dialog_box,-Bitmap_width+position.getX(),position.getY()-Bitmap_height/2,null);
                    canvas.drawText(str,-Bitmap_width/2+position.getX()+100,position.getY()-Bitmap_height/6,paint);
                    break;

            }



        }

        if (Value_of_say == 0){
            timer_for_joke --;
            if(timer_for_joke < 0)
                timer_for_joke = Value_of_joke;
            if(timer_for_joke == Value_of_joke -1){
                Case_for_joke = (int) (Math.random()*6);
                say_joke = true;
            }
            if (say_joke){
                timer_for_one_joke --;
                if(timer_for_one_joke < 0){
                    say_joke = false;
                    timer_for_one_joke = Value_of_one_joke;
                }
                switch (Case_for_joke){
                    case  0:
                        str = "给你讲个笑话鹏远的暖气";
                        dialog_box = Bitmap.createScaledBitmap(dialog_box,str.length()*100,DIALOG_BOX_HEIGTH,true);
                        canvas.drawBitmap(dialog_box,-Bitmap_width+position.getX(),position.getY()-Bitmap_height/2,null);
                        canvas.drawText(str,-Bitmap_width/2+position.getX()+100,position.getY()-Bitmap_height/6,paint);
                        break;
                    case  1:
                        str = "创造我的人是个傻子哈哈";
                        dialog_box = Bitmap.createScaledBitmap(dialog_box,str.length()*100,DIALOG_BOX_HEIGTH,true);
                        canvas.drawBitmap(dialog_box,-Bitmap_width+position.getX(),position.getY()-Bitmap_height/2,null);
                        canvas.drawText(str,-Bitmap_width/2+position.getX()+100,position.getY()-Bitmap_height/6,paint);
                        break;
                    case  2:
                        str = "+快在这里留个言吧+";
                        dialog_box = Bitmap.createScaledBitmap(dialog_box,str.length()*100,DIALOG_BOX_HEIGTH,true);
                        canvas.drawBitmap(dialog_box,-Bitmap_width+position.getX(),position.getY()-Bitmap_height/2,null);
                        canvas.drawText(str,-Bitmap_width/2+position.getX()+100,position.getY()-Bitmap_height/6,paint);
                        break;
                    case  3:
                        str = "请叫我弹幕世界的步行者咕噜";
                        dialog_box = Bitmap.createScaledBitmap(dialog_box,str.length()*100,DIALOG_BOX_HEIGTH,true);
                        canvas.drawBitmap(dialog_box,-Bitmap_width+position.getX(),position.getY()-Bitmap_height/2,null);
                        canvas.drawText(str,-Bitmap_width/2+position.getX()+100,position.getY()-Bitmap_height/6,paint);
                        break;
                    case  4:
                        str = "听说宝箱里藏着神秘的力量";
                        dialog_box = Bitmap.createScaledBitmap(dialog_box,str.length()*100,DIALOG_BOX_HEIGTH,true);
                        canvas.drawBitmap(dialog_box,-Bitmap_width+position.getX(),position.getY()-Bitmap_height/2,null);
                        canvas.drawText(str,-Bitmap_width/2+position.getX()+100,position.getY()-Bitmap_height/6,paint);
                        break;
                    case  5:
                        str = "找一块空地给心爱的人表白吧";
                        dialog_box = Bitmap.createScaledBitmap(dialog_box,str.length()*100,DIALOG_BOX_HEIGTH,true);
                        canvas.drawBitmap(dialog_box,-Bitmap_width+position.getX(),position.getY()-Bitmap_height/2,null);
                        canvas.drawText(str,-Bitmap_width/2+position.getX()+100,position.getY()-Bitmap_height/6,paint);
                        break;

                }

            }


        }
    }

    public void Reset(double x, double y){
        if(should_change){
            position.setX((float) (-x-(Bitmap_width + changed_width)/2));
            position.setY((float) (-y - (Bitmap_height + changed_heigth) / 2));
        }else
        {
            position.setX((float) (-x-(Bitmap_width )/2));
            position.setY((float) (-y - (Bitmap_height) / 2));
        }
        timer --;
        if(timer<0){
            should_change = !should_change;
            timer = timer_value;
        }

    }
//    public position index_Area(float Window_height,float Window_width,int Area_Value){
//        position index = new position();
//        //判断walker是否走出中心区域  及Area(Arae_Value/2， Arae_Value/2);
//         real = new position();
//         int real_X = (int) (position.getX() + Window_width / 2);
//         int real_Y = (int) (position.getY()+Window_height/2);
//         real.setX(position.getX() + Window_width / 2);
//         real.setY(position.getY()+Window_height/2);
//         if(Math.abs(real_X) < Window_width/2)
//            index.setX((Area_Value-1)/2);
//        else{
//            index.setX((Area_Value-1)/2 +(int)((Math.abs(real_X)-Window_width/2)/Window_width)*(real_X/Math.abs(real_X))+1);
//         }
//        if(Math.abs(real_Y) < Window_height / 2)
//            index.setY((Area_Value - 1) / 2);
//        else {
//            index.setY((Area_Value - 1) / 2 + (int) ((Math.abs(real_Y) - Window_height / 2) / Window_height) * (real_Y / Math.abs(real_Y)) + 1);
//        }
//
//
//        return  index;
//    }
    //判断是否有空位 待改进
public  boolean Isempty(float x,float y,String input,int width){
    Word word = new Word();
    int length  = getTextWidth(word.paint,input);
    Rect rect = new Rect(x,y,length,width);

    for (int i=0;i<word_list.size();i++){
        Rect Crect = new Rect(word_list.get(i).getX(),word_list.get(i).getY(),getTextWidth(word.paint,word_list.get(i).str),75);
        System.out.println(length);
        if(is_rect_in_it(rect,Crect))
            return false;
        else if(is_rect_in_it(Crect,rect))
            return false;

    }

    return true;
}

    public void addlist(Word word){
        word_list.add(word);
    }
    public void addlist_box(Box box,int index){
        box_list.add(box);
        id_list.add(index);

    }
//    public  boolean is_point_in_it(position point,float x,float y,int length){
//        if((point.getX()-x)>=0&&(point.getX()-x)<=length*75&&(point.getY()-y)<=75&&(point.getY()-y)>=-75)
//            return false;
//        return  true;
//    }
    //判断rect是否在另一个rect里面
    public  boolean is_rect_in_it(Rect rect,Rect Crect){
        if((rect.getX()-Crect.getX())>=0&&(rect.getX()-Crect.getX())<=Crect.getLength()&&(rect.getY()-Crect.getY())<=75&&(rect.getY()-Crect.getY())>=0)
            return  true;
        if(((rect.getX()+rect.getLength())-Crect.getX())>=0&&((rect.getX()+rect.getLength())-Crect.getX())<=Crect.getLength()&&(rect.getY()-Crect.getY())<=75&&(rect.getY()-Crect.getY())>=0)
            return  true;
        if((rect.getX()-Crect.getX())>=0&&(rect.getX()-Crect.getX())<=Crect.getLength()&&((rect.getY()+rect.getHeigth())-Crect.getY())<=75&&((rect.getY()+rect.getHeigth())-Crect.getY())>=0)
            return  true;
        if(((rect.getX()+rect.getLength())-Crect.getX())>=0&&((rect.getX()+rect.getLength())-Crect.getX())<=Crect.getLength()&&((rect.getY()+rect.getHeigth())-Crect.getY())<=75&&((rect.getY()+rect.getHeigth())-Crect.getY())>=0)
            return  true;
        return  false;
    }
    public boolean is_box_in_body(){
        Rect rec = new Rect(position.getX(),position.getY(),Bitmap_width,Bitmap_height);
        for (int i = 0;i<box_list.size();i++){
            if(is_point_in_rect(new position(box_list.get(i).getX(),box_list.get(i).getY()),rec)){
                setChildren_should_draw(id_list.get(i),false);

                return true;
            }

        }


        return false;
    }
    public boolean is_point_in_rect(position p,Rect rect){

        if((p.getX()-rect.getX())>=0&&(p.getX()-rect.getX())<=Bitmap_width&&(p.getY()-rect.getY())<=Bitmap_height&&(p.getY()-rect.getY())>=0)
            return  true;

        return  false;
    }

    @Override
    public void addChildrenView(Container child) {
        index ++ ;
        super.addChildrenView(child);
    }

    public int getIndex() {
        return index;
    }
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }


}
