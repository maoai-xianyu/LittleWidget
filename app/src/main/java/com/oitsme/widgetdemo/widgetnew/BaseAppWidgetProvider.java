package com.oitsme.widgetdemo.widgetnew;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.oitsme.widgetdemo.LogU;
import com.oitsme.widgetdemo.model.User;
import com.oitsme.widgetdemo.net.HttpApi;
import com.oitsme.widgetdemo.net.RestApiAdapter;
import com.oitsme.widgetdemo.uitls.DimenUtils;
import com.oitsme.widgetdemo.view.GlideRoundedCornersTransform;
import com.oitsme.widgetdemo.view.GlideRoundedCornersTransform.CornerType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author zhangkun
 * @time 2021/3/22 3:12 PM
 * @Description
 */
public abstract class BaseAppWidgetProvider extends AppWidgetProvider {

    public static final int MOVIE_NUM = 3;

    protected AppWidgetManager mAppWidgetManager;
    protected int[] mAppWidgetIds;
    protected Context mContext;

    protected void getHotMovie(final Context context, String name) {
        HttpApi httpApi = RestApiAdapter.getHttpsRxStringInstance().create(HttpApi.class);
        Call<User> maoai_xianyu = httpApi.getMovieTop(name);
        maoai_xianyu.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                LogU.showElog("手动 网络请求 成功");

                LogU.showElog(" 手动" + response.body());

                showHotMovie(response.body(), context);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                LogU.showElog("手动 网络请求 失败");

            }
        });
    }


    protected void getHotMovie(String name) {
        LogU.showElog("自动 网络请求 " + name);
        HttpApi httpApi = RestApiAdapter.getHttpsRxStringInstance().create(HttpApi.class);
        Call<User> maoai_xianyu = httpApi.getMovieTop(name);
        maoai_xianyu.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                LogU.showElog("自动 网络请求 成功");

                LogU.showElog(" 自动" + response.body());

                showHotMovie(response.body());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                LogU.showElog("自动 网络请求 失败");

            }
        });
    }

    public static final String MOVIE_CARD = "com.coding.action.MOVIE_CARD";
    public static final String MOVIE_CARD_DETAIL = "movie_card";
    public static final String MOVIE_CARD_DETAIL_INDEX = "movie_card_index";

    // 网络请求数据
    void showHotMovie(User user, Context context) {

    }

    void showHotMovie(User user) {
    }

    /**
     * 当小部件被添加时或者每次小部件更新时都会调用一次该方法，
     * 配置文件中配置小部件的更新周期 updatePeriodMillis，每次更新都会调用。
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.mContext = context;
        this.mAppWidgetManager = appWidgetManager;
        this.mAppWidgetIds = appWidgetIds;
    }

    /**
     * 当小部件第一次被添加到桌面时回调该方法，可添加多次，但只在第一次调用
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    /**
     * 当最后一个该类型的小部件从桌面移除时调用，
     * 对应的广播的 Action 为 ACTION_APPWIDGET_DISABLED
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    /**
     * onAppWidgetOptionsChanged(): 当小部件布局发生更改的时候调用。对应广播的 Action 为 ACTION_APPWIDGET_OPTIONS_CHANGED。
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
        Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    /**
     * 当小部件从备份中还原，或者恢复设置的时候，会调用，实际用的比较少。
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    /**
     * 每删除一个小部件就调用一次。
     * 对应的广播的 Action 为： ACTION_APPWIDGET_DELETED 。
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }


    // 手动刷新
    protected void loadImage(Context context, final RemoteViews remoteViews,
        final AppWidgetManager appWidgetManager,
        final int[] appWidgetIds,
        final int viewId, CornerType cornerType, int corner,
        String imageUrl, int w, int h) {

        GlideRoundedCornersTransform build = new GlideRoundedCornersTransform(context,
            DimenUtils.dp2px(context, corner), cornerType);

        RequestOptions options = new RequestOptions()
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .centerCrop()
            .transform(build)
            .signature(new ObjectKey(System.currentTimeMillis()))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .apply(options)
            .into(new AppWidgetTarget(context, viewId, remoteViews, appWidgetIds))
            /*.into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    remoteViews.setImageViewBitmap(viewId, resource);
                    appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
                }

            })*/;
    }


    // 自动
    protected void loadImage(final RemoteViews remoteViews, final int viewId, CornerType cornerType, int corner,
        String imageUrl, int w, int h) {

        GlideRoundedCornersTransform build = new GlideRoundedCornersTransform(mContext,
            DimenUtils.dp2px(mContext, corner), cornerType);

        RequestOptions options = new RequestOptions()
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .centerCrop()
            .transform(build)
            .signature(new ObjectKey(System.currentTimeMillis()))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(mContext)
            .asBitmap()
            .load(imageUrl)
            .apply(options)
            .into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    remoteViews.setImageViewBitmap(viewId, resource);
                    mAppWidgetManager.updateAppWidget(mAppWidgetIds, remoteViews);
                }
            });

    }

}
