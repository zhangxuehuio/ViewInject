package com.viewinject;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by zhangxuehui on 2018/6/13.
 */

public class ViewHelper {
    private View view;
    private Activity activity;

    public ViewHelper(Activity activity) {
        this.activity = activity;
        this.view = activity.getWindow().getDecorView();
    }

    public ViewHelper(View view) {
        this.view = view;
    }

    public View findViewById(@IdRes int resId) {
        if (view != null) {
            return view.findViewById(resId);
        } else {
            return null;
        }
    }

}
