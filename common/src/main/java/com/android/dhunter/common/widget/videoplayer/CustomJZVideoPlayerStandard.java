package com.android.dhunter.common.widget.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.android.dhunter.common.R;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by dhunter on 2018/4/18.
 */

public class CustomJZVideoPlayerStandard extends JZVideoPlayerStandard {


    public static onClickFullScreenListener onClickFullScreenListener;

    public CustomJZVideoPlayerStandard(Context context) {
        super(context);
        initView();
    }

    public CustomJZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public static void setOnClickFullScreenListener(onClickFullScreenListener listener) {
        onClickFullScreenListener = listener;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.fullscreen) {
            if (onClickFullScreenListener != null) {
                onClickFullScreenListener.onClickFullScreen();
            }
        }
    }

    private void initView() {
        backButton.setVisibility(View.GONE);
        titleTextView.setVisibility(View.GONE);
//        tinyBackImageView.setVisibility(View.GONE);
        batteryTimeLayout.setVisibility(View.GONE);
    }

    public interface onClickFullScreenListener {
        void onClickFullScreen();
    }
}
