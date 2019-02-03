package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.dhunter.common.widget.flowlayout.FlowLayout;
import com.android.dhunter.common.widget.flowlayout.TagAdapter;
import com.android.dhunter.common.widget.flowlayout.TagFlowLayout;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/28.
 */

public class DevInfoSelectFragment extends BaseFragment {

    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;


    private View rootview;
    private int position;
    private LayoutInflater mInflater;

    public static DevInfoSelectFragment newInstance(Bundle bundle) {
        DevInfoSelectFragment fragment = new DevInfoSelectFragment();
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
        mInflater = LayoutInflater.from(getActivity());

        if(position == 0) {
            initAdapter(R.array.fertility);
        } else if(position ==1) {
            initAdapter(R.array.marriage);
        } else if(position ==2) {
            initAdapter(R.array.family);
        } else if(position ==3) {
            initAdapter(R.array.job);
        } else if(position ==4) {
            initAdapter(R.array.interest);
        }

    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dev_info_select;
    }

    @OnClick({R.id.id_flowlayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_flowlayout:
                break;
        }
    }

    private void initAdapter(int id) {
        mFlowLayout.setAdapter(new TagAdapter<String>(getStringArray(id)) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.textview_tag_select,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public boolean setSelected(int position, String s) {
                return false;
            }
        });
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                toast("choose:" + selectPosSet.toString());
            }
        });
    }

    private String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }
}
