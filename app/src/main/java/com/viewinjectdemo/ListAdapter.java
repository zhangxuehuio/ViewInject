package com.viewinjectdemo;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.viewinject.ViewHelper;
import com.viewinject.ViewInject;
import com.viewinject.annotation.BindEvent;
import com.viewinject.annotation.BindView;

import java.util.List;

/**
 * Created by zhangxuehui on 2018/6/15.
 */

public class ListAdapter extends BaseAdapter {
    private List<String> list;
    private int layoutId;

    public ListAdapter(List<String> list, @LayoutRes int layoutId) {
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setName("绑定ViewHolder");
        holder.setPos(position + "");
        return convertView;
    }
    private class ViewHolder {
        @BindView(R.id.tv_name)
        private TextView tvName;
        @BindView(R.id.tv_pos)
        private TextView tvPos;

        public ViewHolder(View view) {
            ViewInject.getInstance().inject(ViewHolder.this, view);
        }

        public void setName(String msg) {
            tvName.setText(msg);
        }

        public void setPos(String pos) {
            tvPos.setText(pos);
        }

        @BindEvent(value = R.id.tv_name)
        private void onClick(View view) {
            ToastUtils.showShort("绑定ViewHolder点击事件成功");
        }
    }
}
