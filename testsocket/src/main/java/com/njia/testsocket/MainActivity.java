package com.njia.testsocket;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.njia.testsocket.server.ISocketServiceUtils;
import com.njia.testsocket.server.PLServiceSocket;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements PLServiceSocket.ClientSocketCallBackListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ISocketServiceUtils socketServiceUtils = new ISocketServiceUtils();
//                socketServiceUtils.startServerSocket(handler);
//            }
//        }).start();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startService(View view) {
        PLServiceSocket.getInstance().startServiceSocket(this);
    }

    @Override
    public void clientCallBack(final String s) {
        Handler deliver = new Handler(Looper.getMainLooper());
        deliver.post(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"客户端发来消息："+s,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    Handler handler = new Handler();
}
