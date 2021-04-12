package com.oitsme.widgetdemo.widgetnew;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.oitsme.widgetdemo.LogU;
import com.oitsme.widgetdemo.R;

/**
 * @author zhangkun
 * @time 2021/4/12 11:12 AM
 * @Description
 */
public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent receiver = new Intent(context, AlarmManagerBroadcastReceiver.class);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 2006, receiver, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 20000, pendingIntent);
        }

        long currentTimeMillis = System.currentTimeMillis();
        long firstTime = intent.getLongExtra("FirstTime", currentTimeMillis);

        LogU.showILog("firstTime  " + firstTime);

        LogU.showILog("System.currentTimeMillis()   " + currentTimeMillis);
        LogU.showILog("System.currentTimeMillis() 差  " + (currentTimeMillis - firstTime));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
            R.layout.layout_widget_github_auto_fresh_alarm);

        remoteViews.setTextViewText(R.id.tvId, firstTime + " 时间");
        ComponentName thiswidget = new ComponentName(context, GithubAutoRefreshAlarmWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thiswidget, remoteViews);
    }
}
