package com.utsoft.uttoastlibary;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

/**
 * Created by cj on 2016/12/16.
 * Function:
 * Desc:
 */

public class UTToastUtil {
    private static Context mContext;

    public static void initUTToastUtil(Context context) {
        mContext = context;
    }

    private static UTToast setToast(int iconResId, int location, String content) {
        return UTToast.makeUTToast(mContext, content, UTToast.LENGTH_SHORT)
                .setToastGravity(Gravity.BOTTOM, 0, 80)
                .setTextImage(iconResId)
                .setToastTextColor(Color.WHITE)
                .setTextImageSize(50, 50)
                .setTextImageLocation(location)
                .setImagePadding(20);
    }

    public static void success(String content, int location) {
        setToast(R.drawable.success, location, content).show();
    }

    public static void success(String content) {
        setToast(R.drawable.success, UTToast.LEFT, content).show();
    }

    public static void fail(String content, int location) {
        setToast(R.drawable.fail, location, content).show();
    }

    public static void fail(String content) {
        setToast(R.drawable.fail, UTToast.LEFT, content).show();
    }

    public static void remind(String content, int location) {
        setToast(R.drawable.remind, location, content).show();
    }

    public static void remind(String content) {
        setToast(R.drawable.remind, UTToast.LEFT, content).show();
    }


    public static void warn(String content, int location) {
        setToast(R.drawable.warn, location, content).show();
    }

    public static void warn(String content) {
        setToast(R.drawable.warn, UTToast.LEFT, content).show();
    }

    public static void common(String content, int location) {
        setToast(R.drawable.common, location, content).show();
    }

    public static void common(String content) {
        setToast(R.drawable.common, UTToast.LEFT, content).show();
    }
}
