package com.utsoft.toasttest.App;

import android.app.Application;

import com.utsoft.uttoastlibary.UTToastUtil;

/**
 * Created by cj on 2016/12/16.
 * Function:
 * Desc:
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UTToastUtil.initUTToastUtil(this);
    }
}
