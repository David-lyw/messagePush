package com.example.sd.messagepush;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.app.Notification.Builder;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;


import java.io.FileDescriptor;

import engineering.R;

/**
 * 长期运行在后台的服务
 * Created by sd on 2015/10/13.
 */
public class PushMsgServices extends Service {

    private NotificationManager manager;
    private Notification notification;
    private PendingIntent pendingIntent;
    String title, content;
    Intent intent;
    //通知栏上显示的通知，标志不同通知的显示。
    private int messageId = 1;

    Socket socket = null;
    String buffer;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TelephonyManager telephonyManager;

    String token;


    public class LocalBinder extends Binder {
        String stringToSend = "I'm the test String";

        PushMsgServices getService() {
            Log.i("lyw", "getService ---> " + PushMsgServices.this);
            return PushMsgServices.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();




    @Override
    public IBinder onBind(Intent intent) {
        IBinder myIBinder = null;
        if (null == myIBinder) {
            myIBinder = new LocalBinder();
            return myIBinder;
        }

        return mBinder;

    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("lyw", "onCreate");
        initData();

        //getNotification(title, content);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i("lyw", "onStart");
        super.onStart(intent, startId);
    }


    @Override //通过start方式启动service时回调的方法
    public int onStartCommand(Intent intent, int flags, int startId) {

        token = sharedPreferences.getString("token", "");

        Log.i("lyw", token + "      在onStartCommand中。。。。。。。。。。。。。。。");

//        messageThread.isrunning = true;
//        messageThread.start();
//        final boolean isrunning = true;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (isrunning) {
//                    try {
//                        Thread.sleep(1000 * 30);
//                        //  getNotification(content1,content2,content3);
        new MyThread("192.168.1.109", 9999).start();
//
//                       // buffer = "";
//                    } catch (Exception e) {
//                    }
//                }
//
//            }
//        }).start();

        return super.onStartCommand(intent, flags, startId);
    }




    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("lyw", "onUnbind");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        Log.i("lyw", "onDestroy");
        super.onDestroy();

        //服务销毁（即当服务销毁时调用此方法），作用：取消所有通知。
        manager.cancelAll();
        Log.i("lyw", "pushMessage服务..............onDestory");
        stopSelf();
    }


    /**
     * 初始化用到的数据：设备iemi.并保存到sharePreference中。
     */
    public void initData() {
        sharedPreferences = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //手机设备的唯一标识符
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String uuid = telephonyManager.getDeviceId();

        editor.putString("token", uuid);
        editor.commit();

    }

    @SuppressLint("NewApi") //构建notification
    public void getNotification(String title, String content) {
        //获取系统的通知管理器
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //notification = new Notification(R.mipmap.ic_launcher, content, System.currentTimeMillis());
        intent = new Intent(getApplicationContext(), MainActivity.class);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), messageId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Builder(this);
        builder.setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.animal_duck))
                .setSmallIcon(R.mipmap.ic_launcher)
                //0:使用默认设置，如铃声，震动等
                .setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent);
        notification = builder.build();
//        //使用默认设置，如铃声，震动等
//        notification.defaults = Notification.DEFAULT_ALL;
//        //用户点击消息后，消息自动在通知栏自动消失
//        notification.flags = Notification.FLAG_AUTO_CANCEL;
//        // notification.contentIntent=


      /*  Intent intent = new Intent(getApplicationContext(), activity_touchimageview2.class);
        intent.putExtra("content", content);
        intent.putExtra("content2", content1);
        intent.putExtra("data", date);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        notification.setLatestEventInfo(getApplicationContext(), content1, contetn, pendingIntent);*/


        manager.notify(messageId, notification);

        //每次通知完，通知messageId递增一下，避免消息覆盖掉
        messageId++;

    }

    class MyThread extends Thread {
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
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ou)), true);
                BufferedReader bff = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                //读取发来服务器信息
                //  String line = null;
                //  buffer = "";
                //  while ((line = bff.readLine()) != null) {
                //  buffer = line + buffer;
                // }

                //向服务器发送信息
                out.println(token);

                buffer = bff.readLine();
                Log.i("lyw", sharedPreferences.getString("token", "") + "        在线程中....." + buffer);
                ou.flush();
                //关闭各种输入输出流
                bff.close();
                ou.close();
                socket.close();
                //显示通知在通知栏
                getNotification("有新文件", buffer);
                //buffer="";
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
