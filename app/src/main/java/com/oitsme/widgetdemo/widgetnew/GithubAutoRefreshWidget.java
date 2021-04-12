package com.oitsme.widgetdemo.widgetnew;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
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
 * @Description 使用handler 进行自动刷新
 */
public class GithubAutoRefreshWidget extends BaseAppWidgetProvider {

    private RemoteViews mRemoteViews;

    private String name = "maoai-xianyu";

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        LogU.showDLog("自动刷新 前 " + name);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        getHotMovie(name);
        name = "bennyhuo";
        LogU.showDLog("自动刷新 后赋值 为" + name);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisAppWidgetComponentName = new ComponentName(context, GithubAutoRefreshWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
                LogU.showILog("handler  延时 name " + name);
                //name = "bennyhuo";
                //getHotMovie(name);
                onUpdate(context, appWidgetManager, appWidgetIds);
            }
        }, 10000);

    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        this.mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget_github_auto_fresh);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        this.mRemoteViews.setOnClickPendingIntent(R.id.ll_right, pi);

        Intent btIntent = new Intent(context, GithubAutoRefreshWidget.class);
        //btIntent.setAction("refresh");
        btIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent btPendingIntent = PendingIntent
            .getBroadcast(context, appWidgetId, btIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        this.mRemoteViews.setOnClickPendingIntent(R.id.btnRefresh, btPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }

    private void onUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context, GithubAutoRefreshWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    void showHotMovie(User user) {
        LogU.showILog(" 自动 user " + user);
        LogU.showILog(" 自动 mRemoteViews " + mRemoteViews);
        LogU.showILog(" 自动 appWidgetManager " + mAppWidgetManager);
        LogU.showILog(" 自动 appWidgetIds " + mAppWidgetIds);
        LogU.showILog(" 自动 mContext " + mContext);

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
            LogU.showElog("handler 自动  刷新  " + action);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            if (TextUtils.equals(action, "refresh")) {
                LogU.showElog("handler 自动  刷新  bennyhuo");
                getHotMovie("bennyhuo");
            }
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        handler.removeCallbacksAndMessages(null);
    }
}
