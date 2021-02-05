package com.njia.testsocket.server;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PLServiceSocket {
    private ServerSocket serverSocket;
    public Socket socket;
    public InputStream inputStream;
    public static PLServiceSocket instance;

    public static PLServiceSocket getInstance() {
        if (instance == null) {
            instance = new PLServiceSocket();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startServiceSocket(final ClientSocketCallBackListener clientSocketCallBackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(8181);
                    socket = serverSocket.accept();
                    while (true) {
                        try {
                            //获取输入流，并读取客户端信息
                            inputStream = socket.getInputStream();
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                            //判断客户端是否断开连接
                            if (bufferedInputStream.read() == -1) {
                                if (!serverSocket.isClosed())
                                    serverSocket.close();
                                inputStream.close();
                                if (!socket.isClosed())
                                    socket.close();
                                bufferedInputStream.close();
                                startServiceSocket(clientSocketCallBackListener);
                                return;
                            }
                            if (clientSocketCallBackListener != null) {
                                byte[] bytes = new byte[1024];
                                inputStream.read(bytes);
                                String str = new String(bytes, "UTF-8");
                                clientSocketCallBackListener.clientCallBack(str);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    public interface ClientSocketCallBackListener {
        void clientCallBack(String s);
    }

}
