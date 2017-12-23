package com.study.daynode;

import android.app.Application;
import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.study.daynode.common.GreenDaoManager;

/**
 */
public class DayNodeApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        GreenDaoManager.getInstance();
        Fresco.initialize(this);
    }

    public static Context getContext() {
        return mContext;
    }


}
