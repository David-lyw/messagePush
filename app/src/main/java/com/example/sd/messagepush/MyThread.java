package com.example.sd.messagepush;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 测试使用
 * Created by sd on 2015/10/13.
 */
class MyThread extends Thread {
    Socket socket = null;
    String buffer = "";
    public String address;
    public int port;

    public MyThread(String str, int port1) {
        address = str;
        port = port1;

    }

    @Override
    public void run() {
        try {
            //连接服务器 并设置连接超时为5秒
            socket = new Socket();
            socket.connect(new InetSocketAddress(address, port), 5000);
            //获取输入输出流
            OutputStream ou = socket.getOutputStream();
            BufferedReader bff = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            //读取发来服务器信息
            buffer =bff.readLine();
            System.out.print(buffer);
//            PushMsgServices.
            Log.i("sun",buffer);
           // Toast.makeText(MainActivity.class,"buffer",0).show();

            //向服务器发送信息
            ou.write("android 客户端".getBytes("utf-8"));
            ou.flush();


            //关闭各种输入输出流
            bff.close();
            ou.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}