package com.njia.clientsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.njia.clientsocket.server.ClientSocketServiceUtils;

public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClientSocketServiceUtils.getGetInstance().launcherClientSocket();
            }
        }).start();
    }
    int i = 0;
    public void clicka(View view) {
        i++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClientSocketServiceUtils.getGetInstance().sendSocket("我是  "+i);
            }
        }).start();
    }

}
