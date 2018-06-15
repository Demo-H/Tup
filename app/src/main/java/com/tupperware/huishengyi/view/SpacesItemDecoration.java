package com.tupperware.huishengyi.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tupperware.huishengyi.config.Constant;

/**
 * Created by dhunter on 2018/3/9.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int item;
    private int position;

    public SpacesItemDecoration(int space, int item) {
        this.space = space;
        this.item = item;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        position = parent.getChildLayoutPosition(view);
        if(item == Constant.TYPE_ZHAOMU) { //第一行顶部需要分割线
            if(position < 9) {
                if (position % 2 == 0) {
                    outRect.left = 0;
                    outRect.right = space * 2;
                } else {
                    outRect.left = space * 2;
                    outRect.right = 0;
                }
            } else {
                if (position % 2 == 0) {
                    outRect.right = 0;
                    outRect.left = space * 2;
                } else {
                    outRect.right = space * 2;
                    outRect.left = 0;
                }
            }

            if(position == 0 || position == 1) {
                outRect.top = space * 4;
            } else if(position == 6 || position == 7 ||
                    position == 9 || position == 10){
                outRect.top = space * 3;
            } else {
                outRect.top = 0;
            }

        } else if(item == Constant.TYPE_STATUS) {  //第一行顶部不需要分割线
            if (position % 2 == 0) {
                outRect.left = 0;
                outRect.right = space * 2;
            } else {
                outRect.left = space * 2;
                outRect.right = 0;
            }
            outRect.top = 0;

        } else if(item == Constant.TYPE_ORDER) {
            if (position == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
            outRect.left = 0;
            outRect.right = 0;
        } else if(item == Constant.TYPE_SWITCH_STORE) {
            if (position == 0) {
                outRect.top = space * 4;
            } else {
                outRect.top = 0;
            }
            outRect.left = 0;
            outRect.right = 0;
        } else if(item == Constant.TYPE_NO_TOP) {
            outRect.top = 0;
            outRect.left = 0;
            outRect.right = 0;
        }

        outRect.bottom = space;


    }
}
