package com.viewinjectdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    @BindOnClick(value = R.id.tv, method = "onClick")
    void onClick(View view) {
        Log.e(" invoke aaaa", "aaaaa");
        Toast.makeText(getApplicationContext(), "aaaaaa", Toast.LENGTH_LONG).show();
    }
}
