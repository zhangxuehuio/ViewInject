package com.viewinjectdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.ToastUtils;
import com.viewinject.EventType;
import com.viewinject.annotation.BindContentView;
import com.viewinject.annotation.BindEvent;
import com.viewinject.annotation.BindView;
import com.viewinjectdemo.base.BaseActivity;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

@BindContentView(R.layout.activity_list)
public class ListActivity extends BaseActivity {
    @BindView(R.id.lv_content)
    private ListView lvContent;

    @Override
    public void bindUI() {
        List<String> list = new ArrayList<>();
        list.add("数据：你点击了");
        list.add("数据：你点击了");
        list.add("数据：你点击了");
        list.add("数据：你点击了");
        list.add("数据：你点击了");
        list.add("数据：你点击了");
        list.add("数据：你点击了");

        list.add("数据：你点击了");
        list.add("数据：你点击了");
        list.add("数据：你点击了");
        list.add("数据：你点击了");
        list.add("数据：你点击了");
        lvContent.setAdapter(new ListAdapter(list, R.layout.item_name));
    }

    @BindEvent(value = R.id.lv_content, eventType = EventType.CLICK_ITEM)
    void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtils.showShort(parent.getAdapter().getItem(position).toString() + position + "项");
    }
}
