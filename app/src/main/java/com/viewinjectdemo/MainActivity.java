package com.viewinjectdemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.viewinject.EventType;
import com.viewinject.annotation.BindContentView;
import com.viewinject.annotation.BindEvent;
import com.viewinject.annotation.BindView;
import com.viewinjectdemo.base.BaseActivity;

@BindContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_event_title)
    private TextView tvEventTitle;
    @BindView(R.id.btn_bind_click)
    private Button btnBindClick;
    @BindView(R.id.btn_bind_item_click)
    private Button btnBindItemClick;
    @BindView(R.id.btn_bind_long_click)
    private Button btnBindLongClick;
    @BindView(R.id.btn_bind_touch)
    private TextView btnBindTouch;
    @BindView(R.id.btn_bind_layout_change)
    private TextView btnBindLayoutChange;
    @BindView(R.id.btn_bind_text_watcher)
    private TextView btnBindTextWatcher;
    @BindView(R.id.tv_view_title)
    private TextView tvViewTitle;
    @BindView(R.id.tv_result)
    private TextView tvResult;
    @BindView(R.id.fl_content)
    private FrameLayout flContent;

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
    @BindEvent({R.id.btn_bind_click, R.id.btn_show_dialog, R.id.btn_show_fragment, R.id.btn_show_list})
    void onClick(View view) {
        tvResult.setText("");
        switch (view.getId()) {
            case R.id.btn_bind_click:
                tvResult.setText("多VIEW公用一个onClick事件");
                break;
            case R.id.btn_show_dialog:
                tvResult.setText("多VIEW公用一个onClick事件\n 显示dialog");
                View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_test, null);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setView(v)
                        .create();
                dialog.show();
                break;
            case R.id.btn_show_fragment:
                FragmentManager fm = MainActivity.this.getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fl_content, BindFragment.newInstance(), "listFragment").commit();
                break;
            case R.id.btn_show_list:
                ActivityUtils.startActivity(ListActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 自定义回调事件的方法名
     */
    @BindEvent(value = R.id.btn_bind_touch, eventType = EventType.TOUCH)
    public boolean onTouch(View v, MotionEvent event) {
        tvResult.setText("");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            tvResult.append("你触摸了屏幕[" + event.getX() + "," + event.getY() + "]\n");
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            tvResult.append("你从[" + event.getX() + "," + event.getY() + "]抬起了手指\n");
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            tvResult.append("手指移动到[" + event.getX() + "," + event.getY() + "]\n");
        }
        return true;
    }


    /**
     * 自定义回调事件的方法名
     */
    @BindEvent(value = R.id.btn_bind_long_click, eventType = EventType.CLICK_LONG)
    public boolean onLongClick(View v) {
        tvResult.setText("");
        tvResult.append("你触发了长按事件\n");
        return false;
    }


}
