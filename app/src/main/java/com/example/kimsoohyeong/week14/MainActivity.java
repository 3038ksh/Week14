package com.example.kimsoohyeong.week14;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    String SERVER_IP = "118.218.39.70";
    int SERVER_PORT = 200;
    EditText et1;
    String msg = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.et1);
    }

    Handler myHandler = new Handler();
    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Log.d("DEBUG", "하이욘");
                Socket aSocket = new Socket(SERVER_IP, SERVER_PORT);
                ObjectOutputStream outstream =
                        new ObjectOutputStream(aSocket.getOutputStream());

                msg = "Client>> " + et1.getText().toString();
                Log.d("DEBUG", msg);
                outstream.writeObject(msg);
                outstream.flush();

                ObjectInputStream instream =
                        new ObjectInputStream(aSocket.getInputStream());
                final Object data = instream.readObject();

                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),  "Server>> " + data,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                aSocket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Next");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            changeToNextAcitivity();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        MyThread thread = new MyThread();
        thread.start();
    }

    public void changeToNextAcitivity() {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
    }
}
