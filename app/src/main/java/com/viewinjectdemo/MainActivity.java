package com.viewinjectdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.viewinject.annotation.BindContentView;
import com.viewinject.annotation.BindOnClick;
import com.viewinject.annotation.BindView;
import com.viewinjectdemo.base.BaseActivity;

@BindContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.tv)
    TextView tv;

    @Override
    public void bindUI() {
        tv.setText("绑定成功");
    }

    @BindOnClick(R.id.tv)
    void onClick() {
        Log.e("aaaa", "aaaaa");
        Toast.makeText(getApplicationContext(), "aaaaaa", Toast.LENGTH_LONG).show();
    }
}
