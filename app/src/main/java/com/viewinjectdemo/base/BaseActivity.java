package com.viewinjectdemo.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.viewinject.ViewInject;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInject.getInstance().inject(this);
        bindUI();
    }

    public abstract void bindUI();
}
