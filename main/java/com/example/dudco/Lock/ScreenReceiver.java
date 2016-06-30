package com.example.dudco.Lock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;import java.lang.Override;


/**
 * Created by qkswk on 2015-11-24.
 */
public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
            case Intent.ACTION_SCREEN_OFF:
                LockManager.getInstance().sendAction(intent.getAction());
                break;
        }
    }

}