package com.viewinjectdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewinject.ViewInject;
import com.viewinject.annotation.BindContentView;
import com.viewinject.annotation.BindView;

@BindContentView(R.layout.fragment_bind)
public class BindFragment extends Fragment {
    @BindView(R.id.tv_bind)
    TextView tvBind;

    public BindFragment() {
        // Required empty public constructor
    }

    public static BindFragment newInstance() {
        BindFragment fragment = new BindFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = ViewInject.getInstance().inject(this, inflater, container);
        tvBind.setText("fragment绑定成功");
        return view;
    }

}
