package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/3.
 */

public class DataWindowSelectFragment extends BaseFragment {

    @BindView(R.id.point_1)
    ImageView mPoint1;
    @BindView(R.id.point_2)
    ImageView mPoint2;
    @BindView(R.id.point_3)
    ImageView mPoint3;


    private View rootview;
    private int position;

    public static DataWindowSelectFragment newInstance(Bundle bundle) {
        DataWindowSelectFragment fragment = new DataWindowSelectFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt(Constant.POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
//        requestData();
        return rootview;
    }

    @Override
    public void initLayout() {
        if(position == 0) {
            mPoint1.setImageResource(R.mipmap.selected_ic);
            mPoint2.setImageResource(R.mipmap.normal_ic);
            mPoint3.setImageResource(R.mipmap.normal_ic);
        } else if(position == 1) {
            mPoint1.setImageResource(R.mipmap.normal_ic);
            mPoint2.setImageResource(R.mipmap.selected_ic);
            mPoint3.setImageResource(R.mipmap.normal_ic);
        } else if(position == 2) {
            mPoint1.setImageResource(R.mipmap.normal_ic);
            mPoint2.setImageResource(R.mipmap.normal_ic);
            mPoint3.setImageResource(R.mipmap.selected_ic);
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_window_select_content;
    }
}
