package com.njia.testsocket.server;

import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class ISocketServiceUtils {

   static String info;
    ServerSocket serverSocket;
    InputStream inp;
    InputStreamReader isr;
    BufferedReader bfr;
    Socket socket;
    public void startServerSocket(final Handler handler) {
        try {
            //创建一个服务器端socket，指定绑定的端口号，并监听此端口
            serverSocket = new ServerSocket(8181);
            socket = serverSocket.accept();
            //调用accept()方法开始监听，等待客户端的连接
            System.out.println("**********服务器即将启动，等待客户端的连接*************");
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    while (true) {
                        try {
                            //获取输入流，并读取客户端信息
                            inp = socket.getInputStream();
//                          把字节流转换成字符流
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(inp);
                            if (bufferedInputStream.read() == -1){
                                serverSocket.close();
                                inp.close();
                                bufferedInputStream.close();
                                startServerSocket(handler);
                                return;
                            }

                            //为字符流增加缓冲区
                            byte[] bytes = new byte[1024];
                            inp.read(bytes);
                            if (info == null || !info.equals(new String(bytes, StandardCharsets.UTF_8))){
                                info = new String(bytes, StandardCharsets.UTF_8);
                                System.out.println(info);
                                Message message = new Message();
                                message.obj = socket.getLocalAddress();
                                handler.sendMessage(message);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }//循环读取数据

                    }
                }
            }).start();

//            socket.shutdownInput();//关闭输入流
            //向客户端传递的信息
//            OutputStream ots = socket.getOutputStream();
//            PrintWriter pw = new PrintWriter(ots);
//            pw.write("欢迎登陆");
//            pw.flush();
            //关闭资源
//            pw.close();
//            ots.close();
//            bfr.close();
//            isr.close();
//            inp.close();
//            socket.close();
//            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
