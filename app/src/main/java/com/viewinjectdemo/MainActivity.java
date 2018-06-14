package com.viewinjectdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.viewinject.annotation.BindContentView;
import com.viewinject.annotation.BindEvent;
import com.viewinject.annotation.BindView;
import com.viewinjectdemo.base.BaseActivity;

@BindContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_event_title)
    private TextView tvEventTitle;
    @BindView(R.id.btn_bind_click)
    private android.widget.Button btnBindClick;
    @BindView(R.id.btn_bind_touch)
    private android.widget.Button btnBindTouch;
    @BindView(R.id.btn_bind_long_click)
    private android.widget.Button btnBindLongClick;
    @BindView(R.id.tv_view_title)
    private TextView tvViewTitle;
    @BindView(R.id.tv_result)
    private TextView tvResult;

    @Override
    public void bindUI() {
        tvEventTitle.setText("绑定事件");
        tvViewTitle.setText("绑定View");
        tvResult.setText("事件输出结果：");
    }

    /**
     * 多个view公用一个事件
     *
     * @param view
     */
    @BindEvent({R.id.btn_show_dialog, R.id.btn_show_fragment, R.id.btn_show_list})
    void onClick(View view) {
        Log.e(" invoke aaaa", "aaaaa");
        Toast.makeText(getApplicationContext(), "aaaaaa", Toast.LENGTH_LONG).show();
        switch (view.getId()) {
            case R.id.btn_bind_click:
                tvResult.setText("多VIEW公用一个onClick事件");
                break;
            case R.id.btn_show_dialog:
                tvResult.setText("多VIEW公用一个onClick事件\n 显示dialog");
                View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_test, null);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setView(v)
                        .setTitle("测试")
                        .setMessage("测试绑定dialog的点击事件")
                        .create();
                dialog.show();
                break;
            case R.id.btn_show_fragment:
                break;
            case R.id.btn_show_list:
                break;
            default:
                break;
        }
    }

    /**
     * 自定义回调事件的方法名
     *
     * @param view
     */
    @BindEvent(R.id.btn_bind_click)
    void onTestEvent(View view) {

    }

    private void initView() {

    }
}
