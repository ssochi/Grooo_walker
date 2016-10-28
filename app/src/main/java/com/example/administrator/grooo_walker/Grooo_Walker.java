package com.example.administrator.grooo_walker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/26.
 */
//##################标准中心坐标应该是（position.getX()-Window_width/2,position.getY()-Window_height/2）
public class Grooo_Walker extends SurfaceView implements Callback {
    float Window_height ;
    float Window_width;
    Container container;
    Walker walker;
    Timer timer ;
    TimerTask task;
    position position ;
    position point ;
    Context context;
    Word first_word;
    boolean is_start_timer = false;
    boolean is_init = true;
    boolean updateComplete = false;
//    Word first_word ;
//    Word second_word;
    position index_Area;
    int AREA_VALUE = 11;//必需是奇数  正比于地图面积的开方
    int number_of_box = 20;//宝箱的数目

    Box[] box;
//    String url = "http://xperdit.sinaapp.com/text";
    String url = "http://xperdit.sinaapp.com/text";


    public Grooo_Walker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        get_Window_height_and_Window_width();
        init();



        getHolder().addCallback(this);
    }

    private void init() {
        intiposition();

//        first_word = new Word("                  ‖\n             ‖      ‖\n      ‖                   ‖\n‖这是世界的中心‖",0,0);
//        second_word = new Word("######食用方法######\n以安卓图像为中心\n按住安卓上方则往上走\n按住下方则往下走咯\n下面对话框中输入字符\n按住提交按钮也可以\n留言哦",-400,-400);


        initCantainer();


    }

    private void intiposition() {
        point = new position();
        position = new position();
        index_Area = new position();
        position.setX(Window_width / 2);
        position.setY(Window_height / 2);
    }

    private void initCantainer() {
        container = new Container();


        walker = new Walker(getResources(),R.drawable.grooo_walker,Window_height,Window_width,context);
        container.addChildrenView(walker);
//        walker.addChildrenView(first_word);
//        walker.addChildrenView(second_word);

    }

    public void draw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.translate(position.getX(), position.getY());
        container.draw(canvas);
        getHolder().unlockCanvasAndPost(canvas);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        if(is_init){
            get_Window_height_and_Window_width();
            init_box();
            init_position();
            init_firstword();
        }
//        get_input_word("这是世界的中心哦~~~~~~");


        draw();
        //主要是出掉刷箱子的bug

        is_init = false;



    }

    private void init_firstword() {
        first_word = new Word("这是世界的中心哦~~~~~~",0,Window_height/2,true);

        walker.addChildrenView(first_word);
        walker.addlist(first_word);
    }

    private void init_box() {
        box = new Box[number_of_box];
        for(int i = 0;i<box.length;i++){
            //为什么要除3；我也不知道；
            box[i] = new Box(getResources(),Window_width/2+random_plus_minus()*(float)Math.random()*AREA_VALUE/2*Window_width,Window_height/2+random_plus_minus()*(float)Math.random()*AREA_VALUE/2*Window_height);
            walker.addChildrenView(box[i]);
            walker.addlist_box(box[i], walker.getIndex() - 1);

        }


    }

    private int random_plus_minus() {
        int i = (int) (Math.random()*2);
        if(i<1)
            return 1;
        else
            return -1;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopTimer();
    }

    public void get_Window_height_and_Window_width() {
//                TypedArray a = context.obtainStyledAttributes(attrs,
//                R.styleable.Grooo_Walker);
//                Window_height = a.getFloat(0,0);
//                Window_width = a.getFloat(0,0);
                Window_height = this.getHeight();
                Window_width = this.getWidth();
    }

    void start_timer(){
        if(timer==null){
            timer = new Timer();
            task = new TimerTask() {

                @Override
                public void run() {
                    is_start_timer = true;
                    init_position();
                    analysis();
                    draw();
                }


            };
            timer.schedule(task, 0, 10);
        }

    }

    private void analysis() {


//    判断是否越界
//        System.out.println(position.getY());
//
//        System.out.println(position.getX());
        if(position.getX()>AREA_VALUE/2*Window_width)
            position.setX( (-AREA_VALUE/2*Window_width));
        else if(position.getX()<-AREA_VALUE/2*Window_width)
            position.setX((AREA_VALUE/2*Window_width));
        if(position.getY()>AREA_VALUE/2*Window_height)
            position.setY( (-AREA_VALUE/2*Window_height));
        else if (position.getY()<-AREA_VALUE/2*Window_height)
            position.setY(AREA_VALUE/2*Window_height);
//        System.out.println("X  " + position.getX() );
//        System.out.println("Y  " + position.getX() );
//        System.out.println("Window_width   " + Window_width );
//        System.out.println("Window_height  " + Window_height );
//     求出所在的Area
//        index_Area = walker.index_Area(Window_width,Window_height,AREA_VALUE);
//        System.out.println("X```````````````````"+ index_Area.getX());
//        System.out.println("Y```````````````````"+ index_Area.getY());
        //判断是否打开宝箱
       if(walker.is_box_in_body())
            System.out.println("遇见宝箱");



    }

    private void init_position() {
        position.setX((float) (position.getX() - point.get_unit_X()));
        position.setY((float) (position.getY() - point.get_unit_Y()));
        walker.Reset(position.getX()-Window_width/2, position.getY()-Window_height/2);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            is_start_timer = false;
        }
    }
//关于触屏的一系列操作
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            point.setX(event.getX() - Window_width / 2);
            point.setY(event.getY() - Window_height / 2);
            if(!is_start_timer){
                Toast.makeText(context,"不能走的话试试点一下按钮 = =",Toast.LENGTH_SHORT).show();
            }







        }else if(event.getAction() == MotionEvent.ACTION_UP){
            point = new position();





        }
        return true;
    }
    public boolean get_input_word(String input){
//        Word newword = new Word(input,-(position.getX()-(Window_width/2)),-(position.getY()-(Window_height/2)));
        float x =-(position.getX()-(Window_width/2));
        float y = -(position.getY()-(Window_height/2));
        int i = 0;
        if(walker.Isempty(x,y,input,75)){
            Scanner sc = new Scanner(input);
            while(sc.hasNext()){
                String str = sc.nextLine();
                http_post_value(url, x, y + (85 * i), str, walker.isOpen_box_in_this_step());
                Word word = new Word(str, x, y + (85 * i),walker.isOpen_box_in_this_step());
                walker.setOpen_box_in_this_step(false);
                walker.addChildrenView(word);
//                draw();
                //实在不该这么弄。。。。但是不想再弄了
                walker.addlist(word);
                i++;
            }

            return true;
        }
            return false;
    }
    public boolean get_input_word_without_http(String input,float x,float y,boolean is_change){
//        Word newword = new Word(input,-(position.getX()-(Window_width/2)),-(position.getY()-(Window_height/2)));


        int i = 0;
        if(walker.Isempty(x,y,input,75)){
            Scanner sc = new Scanner(input);
            while(sc.hasNext()){
                String str = sc.nextLine();
                Word word = new Word(str, x, y + (85 * i),is_change);
                walker.setOpen_box_in_this_step(false);
                walker.addChildrenView(word);
//                draw();
                //实在不该这么弄。。。。但是不想再弄了
                walker.addlist(word);
                i++;
            }

            return true;
        }
        return false;
    }



    private void http_post_value(final String url, final float position_X,final float position_Y,final String inputword,final boolean is_change_color ) {
        final int is_change_color_value ;
        if(is_change_color){
            is_change_color_value = 1;
        }else {
            is_change_color_value = 0;
        }
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {

                    URL url1 = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();

                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");

                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write("positionX=" + position_X + "&positionY=" + position_Y + "&inputword=" + inputword + "&change_color=" + is_change_color_value);
                    System.out.println("positionX=" + position_X + "&positionY=" + position_Y + "&inputword=" + inputword + "&change_color=" + is_change_color_value);
                    bufferedWriter.flush();

                    InputStreamReader isr = new InputStreamReader(connection.getInputStream(),"utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    while ((line = br.readLine())!=null){
                        System.out.println(line);
                    }
                    br.close();
                    isr.close();




                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return null;
            }
        }.execute(url);
    }


    public void setUpdateComplete(boolean updateComplete) {
        this.updateComplete = updateComplete;
    }
    public boolean getUpdateComplete() {
        return this.updateComplete;
    }

}

