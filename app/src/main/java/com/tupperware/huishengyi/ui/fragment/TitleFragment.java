package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tupperware.huishengyi.R;

/**
 * Created by dhunter on 2018/2/22.
 */

public class TitleFragment extends BaseFragment {

    private String title;
    private TextView tvTitle;
    private View backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init(R.layout.fragment_title,inflater,container);
        initLayout();
        initLayoutData();
        return parent;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initLayout() {
        tvTitle = (TextView) parent.findViewById(R.id.title);
        tvTitle.setText(getTag());

        backBtn = parent.findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }

    @Override
    public void initLayoutData() {

    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }


    @Override
    public int getLayoutId() {
        return 0;
    }
}
