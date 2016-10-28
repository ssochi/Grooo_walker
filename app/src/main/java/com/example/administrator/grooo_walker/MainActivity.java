package com.example.administrator.grooo_walker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
        Grooo_Walker grooo_walker;
        EditText editText ;
        Button btn;
        boolean should_start = true;
        View soft_input;
        String url = "http://xperdit.sinaapp.com/json" ;
        boolean firstTimerEnd = false;
        boolean BTN_ABLE = true;
        boolean isConnectStart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new loading_view(this));
        setContentView(R.layout.activity_main);
        soft_input = getWindow().peekDecorView();

        grooo_walker = (Grooo_Walker) findViewById(R.id.Grooo_Walker);
        editText = (EditText) findViewById(R.id.editText);
        btn = (Button) findViewById(R.id.button);
        btn.setText("start");

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grooo_walker.stopTimer();
                System.out.println("stopTimer");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(BTN_ABLE){
                    if(should_start){
                        if (isNetworkAvailable(MainActivity.this)){
                            update(url);
                            btn.setText("加载中...");
                        }else {
                            btn.setText("没有网络啊");
                        }
                        should_start = false;
                        BTN_ABLE = false;
                        grooo_walker.stopTimer();
                        if (soft_input != null) {
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputmanger.hideSoftInputFromWindow(soft_input.getWindowToken(), 0);
                        }
                        final Timer timer = new Timer();
                        TimerTask Task = new TimerTask() {
                            @Override
                            public void run() {
                                if(grooo_walker.getUpdateComplete()){
                                    grooo_walker.start_timer();
                                    System.out.println("startTimer");
                                    BTN_ABLE = true;
                                    MainActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            btn.setText("提交");

                                        }
                                    });
                                    firstTimerEnd = true;
                                    timer.cancel();}

                            }
                        };
                        timer.schedule(Task, 500, 1000000);

                    }else{
                        if(editText.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
                        }
                        else if( grooo_walker.get_input_word(editText.getText().toString())){

                        }else{
                            Toast.makeText(MainActivity.this, "真的没有位置了=___=", Toast.LENGTH_SHORT).show();
                        }

                        editText.setText("");

                        if (soft_input != null) {
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputmanger.hideSoftInputFromWindow(soft_input.getWindowToken(), 0);
                        }
                        final Timer timer = new Timer();
                        TimerTask Task = new TimerTask() {
                            @Override
                            public void run() {
                                grooo_walker.start_timer();
                                System.out.println("startTimer");
                                timer.cancel();
                            }
                        };
                        timer.schedule(Task,500,1000000);

                    }
                }
                else {
                    if(isNetworkAvailable(MainActivity.this)){
                        if(isConnectStart) {
                            Toast.makeText(MainActivity.this, "正在努力加载中", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            btn.setText("加载中...");
                            update(url);

                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this,"请检查你的网络",Toast.LENGTH_SHORT).show();
                    }

                }





            }
        });




    }

    private void update( String url) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                try {
                    isConnectStart = true;
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");

                    OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
                    BufferedWriter bf = new BufferedWriter(osw);
                    bf.write("positionX=1273.0159&positionY=1273.0159&inputword=dsd&change_color=1");
                    bf.flush();

                    InputStreamReader isr = new InputStreamReader(connection.getInputStream(),"utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    String out="";
                    while ((line = br.readLine())!=null){
                        out+=line;
                    }
//                            System.out.println(out);
                    br.close();
                    isr.close();
                    return out;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Internet erorr",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();

                    Toast.makeText(MainActivity.this,"Internet erorr",Toast.LENGTH_SHORT).show();
                }



                return null;
            }
            @Override
            protected void onPostExecute(String result){
                if(result!=""){
                    try {
                        JSONObject root = new JSONObject(result);
                        JSONArray array = root.getJSONArray("position");
                        for (int i =0;i<array.length();i++){
                            JSONObject row = array.getJSONObject(i);
                            boolean change_value = false;
                            if(row.getInt("change_value")==1){
                                change_value = true;
                            }
                            grooo_walker.get_input_word_without_http(row.getString("inputword"),(float)row.getDouble("positionX"),(float)row.getDouble("positionY"),change_value);
                        }
                        grooo_walker.setUpdateComplete(true);

                            grooo_walker.start_timer();
                        System.out.println("startTimer");
                            BTN_ABLE = true;
                            btn.setText("提交");


                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("erorr with json_deal");
                    }
                }


            }

        }.execute(url);
    }


    @Override
    protected void onPause() {
        grooo_walker.stopTimer();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 检查当前网络是否可用
     *
     * @param
     * @return
     */

    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
