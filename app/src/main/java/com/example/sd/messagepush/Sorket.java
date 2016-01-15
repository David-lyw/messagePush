package com.example.sd.messagepush;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 暂时没有用到
 * Created by sd on 2015/10/13.
 */
public class Sorket extends Thread {
    Socket socket = null;
    String buffer = "";

    public static Sorket getInstance()
    {
        return  new Sorket();
    }
    public void Send(String address, int port) {
        //定义消息
        Message msg = new Message();
        msg.what = 0x11;
        Bundle bundle = new Bundle();
        bundle.clear();
        try {
            //连接服务器 并设置连接超时为5秒
            socket = new Socket();
            socket.connect(new InetSocketAddress(address, port), 5000);
            //获取输入输出流
            OutputStream ou = socket.getOutputStream();
            BufferedReader bff = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            //读取发来服务器信息
            String line = null;
            buffer = "";
            while ((line = bff.readLine()) != null) {
                buffer = line + buffer;
            }

            //向服务器发送信息
            ou.write("请求推送".getBytes("utf-8"));
            ou.flush();
            bundle.putString("msg", buffer.toString());
            msg.setData(bundle);
            //关闭各种输入输出流
            bff.close();
            ou.close();
            socket.close();
        } catch (SocketTimeoutException aa) {
            //连接超时 在UI界面显示消息
            bundle.putString("msg", "服务器连接失败！请检查网络是否打开");
            msg.setData(bundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
