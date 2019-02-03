package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/28.
 * 6万店养成
 */

public class CollStoreFragment extends BaseFragment {

    public static CollStoreFragment newInstance(Bundle bundle) {
        CollStoreFragment fragment = new CollStoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
//        requestData();
        return view;
    }

    @Override
    public void initLayout() {

    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_store_coll;
    }
}
