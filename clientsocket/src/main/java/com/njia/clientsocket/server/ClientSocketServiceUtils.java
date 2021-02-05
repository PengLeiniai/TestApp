package com.njia.clientsocket.server;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocketServiceUtils {
    public String TAG = this.getClass().getSimpleName();
    public Socket socket;
    public OutputStream ots;
    public PrintWriter pw;
    public String ipAddress = "192.168.98.176";
    public int port = 8181;
    public static ClientSocketServiceUtils instance;

    public static ClientSocketServiceUtils getGetInstance() {
        if (instance == null){
            instance = new ClientSocketServiceUtils();
        }
        return instance;
    }

    public void launcherClientSocket(String ...ipAndProt){
        try {
            if (ipAndProt != null && ipAndProt.length > 1){
                this.ipAddress = ipAndProt[0];
                try{
                    this.port = Integer.parseInt(ipAndProt[1]);
                }catch (ClassCastException e){
                    Log.d(TAG,"端口有误");
                }
            }
            socket = new Socket();
            connectSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否断开连接，断开返回true,没有返回false
     *
     * @param socket Socket
     * @return Boolean
     */
    private Boolean isServerClose(Socket socket) {
        try {
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        } catch (Exception se) {
            return true;
        }
    }

    public void sendSocket(String text) {
        try {
            connectService();
            //向服务器端传递信息
            ots = socket.getOutputStream();
            pw = new PrintWriter(ots);
            pw.write(text);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接socketService
     */
    private void connectService() throws IOException {
        if (isServerClose(socket)) {
            socket = new Socket();
            connectSocket();
        }
        if (!socket.isConnected()) {
            connectSocket();
        }

    }
    private void connectSocket() throws IOException {
        socket.connect(new InetSocketAddress(ipAddress, port), 1000);
    }
}
