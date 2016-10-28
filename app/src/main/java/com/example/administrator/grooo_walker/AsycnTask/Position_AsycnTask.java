package com.example.administrator.grooo_walker.AsycnTask;

import android.os.AsyncTask;
import android.content.Context;
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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/12/18.
 */
public class Position_AsycnTask extends AsyncTask {
    String URL ;
    Position_AsycnTask(String URL){
        this.URL = URL;
    }
    @Override
    protected Object doInBackground(Object[] params) {

        try {
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("username=123456&password=123456");
            bw.flush();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
