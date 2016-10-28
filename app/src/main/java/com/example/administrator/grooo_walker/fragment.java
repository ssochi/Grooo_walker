package com.example.administrator.grooo_walker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by Administrator on 2015/12/21.
 */
public class fragment extends Fragment {
    Grooo_Walker grooo_walker;
    EditText editText ;
    Button btn;
    boolean should_start = true;
    View soft_input;
    String url = "http://xperdit.sinaapp.com/json" ;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        soft_input = getActivity().getWindow().peekDecorView();
        grooo_walker = (Grooo_Walker) view.findViewById(R.id.Grooo_Walker);
        editText = (EditText) view.findViewById(R.id.editText);
        btn = (Button) view.findViewById(R.id.button);
        btn.setText("start");

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grooo_walker.stopTimer();

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(should_start){
                    update(url);
                    should_start = false;
                    btn.setText("提交");
                    grooo_walker.stopTimer();
                    if (soft_input != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(soft_input.getWindowToken(), 0);
                    }
                    final Timer timer = new Timer();
                    TimerTask Task = new TimerTask() {
                        @Override
                        public void run() {
                            grooo_walker.start_timer();
                            timer.cancel();
                        }
                    };
                    timer.schedule(Task, 500, 1000000);

                }else{
                    if(editText.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "输入为空", Toast.LENGTH_SHORT).show();
                    }
                    else if( grooo_walker.get_input_word(editText.getText().toString())){
//                            http_post_value(url,grooo_walker.getx(),grooo_walker.gety(),editText.getText().toString(),grooo_walker.get_is_change_color());
                    }else{
                        Toast.makeText(getActivity(), "真的没有位置了=___=", Toast.LENGTH_SHORT).show();
                    }

                    editText.setText("");

                    if (soft_input != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputmanger.hideSoftInputFromWindow(soft_input.getWindowToken(), 0);
                    }
                    final Timer timer = new Timer();
                    TimerTask Task = new TimerTask() {
                        @Override
                        public void run() {
                            grooo_walker.start_timer();
                            timer.cancel();
                        }
                    };
                    timer.schedule(Task,500,1000000);

                }




            }
        });


        return view;

    }
    private void update( String url) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                try {
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
                } catch (IOException e) {
                    e.printStackTrace();
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("erorr with json_deal");
                    }
                }


            }

        }.execute(url);
    }

    @Override
    public void onPause() {
        grooo_walker.stopTimer();
        super.onPause();
    }
}
