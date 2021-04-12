package com.oitsme.widgetdemo.uitls;

import android.content.Context;

public class DimenUtils {
    /**
     * dpתpx 根据手机的分辨率从dip的单位转成为 px
     */
    public static int dp2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * pxתdp 根据手机的分辨率从px 的单位转成为dp
     */
    public static int px2dip(Context ctx, float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
