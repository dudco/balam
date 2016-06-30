package com.example.dudco.Lock;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;

import com.example.dudco.appzam.MainActivity;
import com.example.dudco.appzam.MyReceiver;

import java.lang.Override;


/**
 * Created by qkswk on 2015-11-24.
 */
public class LockService extends Service {

    private ScreenReceiver sReceiver = null;
    private MyReceiver mWifiMonitor = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        //"MainActivity.class" 대신에, 상단바 알림창 눌렀을 때 "시작할 액티비티.class" 넣기
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT
        );

        //icon_splash 대신 상단바 알림창 아이콘 설정
        Notification notification = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.icon_splash)
                .setTicker("잠금 서비스가 실행되었습니다.")
                .setContentTitle("일해라! 개발자의 화면 잠금이 실행중입니다.")
                .setContentText("터치해서 설정합니다.")
                .setContentIntent(pendingIntent)
                .build();


        startForeground(1, notification);

//        sReceiver = new ScreenReceiver();
//        IntentFilter sFilter = new IntentFilter();
//        sFilter.addAction(Intent.ACTION_SCREEN_ON);
//        sFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        registerReceiver(sReceiver, sFilter);


        mWifiMonitor = new MyReceiver(this);
        IntentFilter receiverFilter = new IntentFilter();
        receiverFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        receiverFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mWifiMonitor, receiverFilter);

        //
        LockManager.getInstance().init(this);

        return START_REDELIVER_INTENT;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWifiMonitor != null) {
            unregisterReceiver(mWifiMonitor);
        }
    }

}