package com.example.sd.messagepush;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Notification.Builder;
import android.widget.Toast;

import java.util.Locale;

import engineering.R;

public class MainActivity extends Activity {
    private Notification notification;
    private NotificationManager nm;
    public static int notificationId = 1;
    Intent intent;
    PendingIntent pendingIntent;


    private ServiceConnection serviceConnection;

    PushMsgServices pushMsgServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        serviceConnection = new ServiceConnection() {
            /**
             * 只有在PushMsgService中的onBind方法中返回一个IBinder实例才会在Bind的时候
             * 调用onServiceConnection回调方法
             * 第二个参数service就是MyService中onBind方法return的那个IBinder实例，可以利用这个来传递数据
             */
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                pushMsgServices = ((PushMsgServices.LocalBinder) iBinder).getService();
                String recStr = ((PushMsgServices.LocalBinder) iBinder).stringToSend;
                //利用IBinder对象传递过来的字符串数据（其他数据也可以啦，哪怕是一个对象也OK~~）
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //只有在service因异常而断开连接的时候，这个方法才会用到
                serviceConnection = null;
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nm != null) {
            nm.cancelAll();
        } else {
            return;
        }

    }

    //布局中绑定的方法----开启服务
    public void beginServices(View v) {

        intent = new Intent(this, PushMsgServices.class);
        startService(intent);

    }

    //布局中绑定的方法----停止服务
    public void stopServices(View v) {

        intent = new Intent();
        intent.setAction("com.test.SERVICE_TEST");
        stopService(intent);
    }

    //布局中绑定的方法----绑定服务
    public void bindService(View v) {
        intent = new Intent();
        intent.setAction("com.test.SERVICE_TEST");
        //bind多次也只会调用一次onBind方法
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    //布局中绑定的方法----解绑服务
    public void unBindService(View v) {

        unbindService(serviceConnection);
    }


    //布局中绑定的方法----本activity消息推送
    //Notification.Builder require minAPI 11,current API 8,so add-->@SuppressLint("NewApi")
    @SuppressLint("NewApi")
    public void pushMsg(View v) {
        // 第一步：获取NotificationManager
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 第二步：定义Notification
        // notification = new Notification(R.mipmap.ic_launcher, "woshiyigeNotification", System.currentTimeMillis());
        intent = new Intent(getApplicationContext(), MainActivity.class);
        //FLAG_CANCEL_CURRENT作用:notificationId对应的pendingIntent，如果当前系统中已经存在一个相同的PendingIntent对象，那么就将先将已有的PendingIntent取消，然后重新生成一个PendingIntent对象
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Builder(this);
        builder.setContentTitle("标题：爱你" + notificationId)
                .setContentText("想你 +" + notificationId)
                .setSmallIcon(R.mipmap.timomengzhu)
                //设置可以清除
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.animal_duck))
                .setContentIntent(pendingIntent);
        notification = builder.build();

        //使用默认设置，如铃声，震动等
        //notification.defaults = Notification.DEFAULT_ALL;
        //用户点击消息后，消息自动在通知栏自动消失
        notification.flags = Notification.FLAG_AUTO_CANCEL;

//        Intent intent = new Intent(getApplicationContext(), activity_touchimageview2.class);
//        intent.putExtra("content", "content");
//        intent.putExtra("content2", "content2");
//        intent.putExtra("data", "data");
//        // PendingIntent是待执行的Intent
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
//        notification.flags = Notification.FLAG_NO_CLEAR;
//        notification.setLatestEventInfo(this, "biaoti", "wenben", pendingIntent);//?????????


        // 第三步：启动通知栏，第一个参数是一个通知的唯一标识
        nm.notify(notificationId, notification);
        notificationId++;
        // 关闭通知
        // nm.cancel(0);
    }


    //清除所有通知
    public void cleanAllNotification(View v) {
        if (nm != null) {
            nm.cancelAll();
        } else {
            return;
        }
    }


    //单独向服务器发送消息
    public void sendMessage(View v) {
        // Sorket.getInstance().Send("192.168.1.109",9999);
        new MyThread("192.168.1.109", 9999).start();
        // System.out.print("输出的内容你在哪里，我找不到你，好心急！！！！！！！！！！！！！！！");
        Log.i("view", "输出的内容你在哪里，我找不到你，好心急！！！！！！！！！！！！！！！");
        // Toast.makeText(this,"输出的内容你在哪里",Toast.LENGTH_SHORT).show();
    }
}
