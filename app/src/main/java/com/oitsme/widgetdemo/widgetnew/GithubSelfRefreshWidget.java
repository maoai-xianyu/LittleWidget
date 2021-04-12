package com.oitsme.widgetdemo.widgetnew;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
 * @Description
 */
public class GithubSelfRefreshWidget extends BaseAppWidgetProvider {

    private RemoteViews mRemoteViews;

    private String name = "maoai-xianyu";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        LogU.showDLog("手动 onUpdate 前" + name);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        getHotMovie(context, name);
        LogU.showDLog("手动 onUpdate 后赋值" + name);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        this.mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget_github_self_fresh);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        this.mRemoteViews.setOnClickPendingIntent(R.id.ll_right, pi);

        Intent btIntent = new Intent(context, GithubSelfRefreshWidget.class);
        btIntent.setAction("refresh");
        PendingIntent btPendingIntent = PendingIntent
            .getBroadcast(context, appWidgetId, btIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        this.mRemoteViews.setOnClickPendingIntent(R.id.btnRefresh, btPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }


    @Override
    void showHotMovie(User user, Context context) {
        LogU.showILog(" 手动  user " + user.toString());
        LogU.showILog(" 手动 mRemoteViews " + mRemoteViews);
        LogU.showILog(" 手动 appWidgetManager " + mAppWidgetManager);
        LogU.showILog(" 手动 appWidgetIds " + mAppWidgetIds);
        LogU.showILog(" 手动 mContext " + mContext);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context, GithubSelfRefreshWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        if (user != null) {
            mRemoteViews.setTextViewText(R.id.tvId, user.getId() + "");
            mRemoteViews.setTextViewText(R.id.tvName, user.getName());
            mRemoteViews.setViewVisibility(R.id.tvId, View.VISIBLE);
            mRemoteViews.setViewVisibility(R.id.tvName, View.VISIBLE);

            String avatar_url = user.getAvatar_url();
            if (!TextUtils.isEmpty(avatar_url)) {
                mRemoteViews.setViewVisibility(R.id.ivLeft, View.VISIBLE);
                loadImage(context, mRemoteViews, appWidgetManager, appWidgetIds,

                    R.id.ivLeft, CornerType.LEFT, 16, avatar_url, 50, 100);
            } else {
                mRemoteViews.setViewVisibility(R.id.ivLeft, View.GONE);
            }

            if (!TextUtils.isEmpty(avatar_url)) {
                mRemoteViews.setViewVisibility(R.id.ivRightTop, View.VISIBLE);
                loadImage(context, mRemoteViews, appWidgetManager, appWidgetIds,
                    R.id.ivRightTop, CornerType.TOP_RIGHT, 16,
                    avatar_url, 50, 50);
            } else {
                mRemoteViews.setViewVisibility(R.id.ivRightTop, View.GONE);
            }

            if (!TextUtils.isEmpty(avatar_url)) {
                mRemoteViews.setViewVisibility(R.id.ivRightBottom, View.VISIBLE);
                loadImage(context, mRemoteViews, appWidgetManager, appWidgetIds,
                    R.id.ivRightBottom, CornerType.BOTTOM_RIGHT, 16,
                    avatar_url, 50, 50);
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
        appWidgetManager.updateAppWidget(appWidgetIds, mRemoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (context != null) {
            LogU.showElog("手动  刷新  ");
            String action = intent.getAction();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            if (TextUtils.equals(action, "refresh")) {
                LogU.showElog("  刷新  bennyhuo");
                getHotMovie(context, "bennyhuo");

            }
        }
    }
}
