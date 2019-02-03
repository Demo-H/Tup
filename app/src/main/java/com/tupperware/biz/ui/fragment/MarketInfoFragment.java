package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;

/**
 * Created by dhunter on 2018/2/2.
 * 弃用，需求更改
 */

public class MarketInfoFragment extends BaseFragment implements View.OnClickListener {


    private ZixunFragment mZixunFragment;
    private VideoFragment mVideoFragment;
    private View mZixunView;
    private View mVideoView;
    private RelativeLayout mZixunRL;
    private RelativeLayout mVideoRL;
    private TextView mZixunText;
    private TextView mVideoText;

    private FragmentManager fragmentManager;


    public static MarketInfoFragment newInstance() {
        MarketInfoFragment fragment = new MarketInfoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mZixunView = view.findViewById(R.id.zixun_line);
        mVideoView = view.findViewById(R.id.video_line);
        mZixunRL = (RelativeLayout) view.findViewById(R.id.rl_zixun);
        mVideoRL = (RelativeLayout) view.findViewById(R.id.rl_video);
        mZixunRL.setOnClickListener(this);
        mVideoRL.setOnClickListener(this);
        mZixunText = (TextView) view.findViewById(R.id.zixun);
        mVideoText = (TextView) view.findViewById(R.id.video);
        initLayout();
//        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        fragmentManager = getActivity().getSupportFragmentManager();
        mZixunFragment = new ZixunFragment();
        mVideoFragment = new VideoFragment();
        fragmentManager.beginTransaction().add(R.id.contentFrame, mZixunFragment, "1").commit();
        fragmentManager.beginTransaction().add(R.id.contentFrame, mVideoFragment, "2").commit();
        selection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_zixun:
                selection(0);
                break;
            case R.id.rl_video:
                selection(1);
                break;
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_market_info;
    }

    private void selection(int position) {
        mZixunView.setVisibility(View.INVISIBLE);
        mVideoView.setVisibility(View.INVISIBLE);
        mZixunText.setSelected(false);
        mVideoText.setSelected(false);
        switch (position) {
            case 0:
                fragmentManager.beginTransaction().hide(mVideoFragment)
                        .show(mZixunFragment).commit();
                mZixunView.setVisibility(View.VISIBLE);
                mZixunText.setSelected(true);
                break;
            case 1:
                fragmentManager.beginTransaction().hide(mZixunFragment)
                        .show(mVideoFragment).commit();
                mVideoView.setVisibility(View.VISIBLE);
                mVideoText.setSelected(true);
                break;
        }
    }


}
