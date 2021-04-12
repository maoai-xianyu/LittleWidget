package com.oitsme.widgetdemo.widgetnew;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.oitsme.widgetdemo.LogU;
import com.oitsme.widgetdemo.MainActivity;
import com.oitsme.widgetdemo.R;
import com.oitsme.widgetdemo.model.User;
import com.oitsme.widgetdemo.view.GlideRoundedCornersTransform.CornerType;


/**
 * @author zhangkun
 * @time 2021/3/22 3:29 PM
 * @Description 基于 AlarmManager 进行赋值，点击刷新按钮有问题
 */
public class GithubAutoRefreshAlarmWidget extends BaseAppWidgetProvider {

    private RemoteViews mRemoteViews;

    private String name = "maoai-xianyu";

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        LogU.showDLog("Alarm自动刷新 前 " + name);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        getHotMovie(name);
        name = "bennyhuo";
        LogU.showDLog("Alarm自动刷新 后赋值 为" + name);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

        long firstTime = System.currentTimeMillis();
        firstTime += 20000;
        Intent receiver = new Intent(context, AlarmManagerBroadcastReceiver.class);
        receiver.putExtra("FirstTime", firstTime);
        receiver.setAction(MOVIE_CARD);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 2006, receiver, 0);

        LogU.showILog(" firstTime " + firstTime);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            LogU.showILog(" 高版本 " + firstTime);
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP, firstTime, 20000, pendingIntent);
        }
        LogU.showDLog("onEnabled: ");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Intent intent = new Intent();
        intent.setAction(MOVIE_CARD);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 2006, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
        LogU.showDLog("onDisabled: ");
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        this.mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget_github_auto_fresh_alarm);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        this.mRemoteViews.setOnClickPendingIntent(R.id.ll_right, pi);

        Intent btIntent = new Intent(context, GithubAutoRefreshAlarmWidget.class);
        btIntent.setAction("refresh");
        PendingIntent btPendingIntent = PendingIntent
            .getBroadcast(context, appWidgetId, btIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        this.mRemoteViews.setOnClickPendingIntent(R.id.btnRefresh, btPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }

    private void onUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context, GithubAutoRefreshAlarmWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    void showHotMovie(User user) {
        LogU.showILog(" Alarm自动 user " + user);
        LogU.showILog(" Alarm自动 mRemoteViews " + mRemoteViews);
        LogU.showILog(" Alarm自动 appWidgetManager " + mAppWidgetManager);
        LogU.showILog(" Alarm自动 appWidgetIds " + mAppWidgetIds);
        LogU.showILog(" Alarm自动 mContext " + mContext);
        if (user != null) {
            mRemoteViews.setTextViewText(R.id.tvId, user.getId() + "");
            mRemoteViews.setTextViewText(R.id.tvName, user.getName());
            mRemoteViews.setViewVisibility(R.id.tvId, View.VISIBLE);
            mRemoteViews.setViewVisibility(R.id.tvName, View.VISIBLE);

            String avatar_url = user.getAvatar_url();
            if (!TextUtils.isEmpty(avatar_url)) {
                mRemoteViews.setViewVisibility(R.id.ivLeft, View.VISIBLE);
                loadImage(mRemoteViews, R.id.ivLeft, CornerType.LEFT, 16, avatar_url, 50, 100);
            } else {
                mRemoteViews.setViewVisibility(R.id.ivLeft, View.GONE);
            }

            if (!TextUtils.isEmpty(avatar_url)) {
                mRemoteViews.setViewVisibility(R.id.ivRightTop, View.VISIBLE);
                loadImage(mRemoteViews, R.id.ivRightTop, CornerType.TOP_RIGHT, 16, avatar_url, 50, 50);
            } else {
                mRemoteViews.setViewVisibility(R.id.ivRightTop, View.GONE);
            }

            if (!TextUtils.isEmpty(avatar_url)) {
                mRemoteViews.setViewVisibility(R.id.ivRightBottom, View.VISIBLE);
                loadImage(mRemoteViews, R.id.ivRightBottom, CornerType.BOTTOM_RIGHT, 16, avatar_url, 50, 50);
            } else {
                mRemoteViews.setViewVisibility(R.id.ivRightBottom, View.GONE);
            }

        } else {
            mRemoteViews.setViewVisibility(R.id.tvId, View.GONE);
            mRemoteViews.setViewVisibility(R.id.tvName, View.GONE);
            mRemoteViews.setViewVisibility(R.id.ivLeft, View.GONE);
            mRemoteViews.setViewVisibility(R.id.ivRightTop, View.GONE);
            mRemoteViews.setViewVisibility(R.id.ivRightBottom, View.GONE);
        }
        mAppWidgetManager.updateAppWidget(mAppWidgetIds, mRemoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (context != null) {
            String action = intent.getAction();
            LogU.showElog("alarm 自动  刷新  " + action);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            if (TextUtils.equals(action, "refresh")) {
                LogU.showElog(" alarm  刷新  bennyhuo");
                getHotMovie("bennyhuo");
            }
        }

    }
}
