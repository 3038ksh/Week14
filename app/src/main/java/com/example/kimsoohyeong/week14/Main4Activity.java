package com.example.kimsoohyeong.week14;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main4Activity extends AppCompatActivity {
    TextView tv1;
    EditText id, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        tv1 = (TextView)findViewById(R.id.login);
        id = (EditText)findViewById(R.id.id);
        pw = (EditText)findViewById(R.id.pw);
    }

    Handler handler = new Handler();
    private class MyThread extends Thread{
        @Override
        public void run() {
            try {
                URL url = new URL("http://jerry1004.dothome.co.kr/info/login.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                final String userId = id.getText().toString();
                final String userPw = pw.getText().toString();

                String postData = "userid=" + URLEncoder.encode(userId) + "&password=" + URLEncoder.encode(userPw);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                InputStream inputStream;

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                final String result = loginResult(inputStream);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (userId.equals("") || userPw.equals("")) {
                            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력하세요.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (result.equals("FAIL")) {
                            tv1.setText("로그인이 실패했습니다.");
                        } else {
                            tv1.setText(result + "님 로그인 성공");
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String loginResult(InputStream is) {
        String data = "";
        Scanner s = new Scanner(is);
        while (s.hasNext()) data += s.nextLine();
        s.close();
        return data;
    }

    public void onClick(View v) {
        MyThread thread = new MyThread();
        thread.start();
    }

}