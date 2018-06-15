package com.android.dhunter.common.widget.popupWindow;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.dhunter.common.R;

import java.util.ArrayList;

/**
 * Created by dhunter on 2018/4/4.
 */

public class EasyTopPopup extends EasyPopup {

    private EasyPopup mEasyPopup;
    private Context mContext;
    private ListView myLv;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private ArrayList<String> mItems;
    private String mType;
    private String mSelectItemStr;

    private PopupAdapter adapter;
    // 判断是否需要添加或更新列表子类项
    private boolean mIsDirty = true;

    public EasyTopPopup(Context context) {
        super(context);
        mContext = context;
    }

    public EasyTopPopup(Context context, AdapterView.OnItemClickListener onItemClickListener, ArrayList<String> items,
                        String type) {
        super(context);

        this.mItems = items;
        this.mOnItemClickListener = onItemClickListener;
        this.mType = type;
        this.mContext = context;

        initLayout();

    }

    private void initLayout() {

        if(mEasyPopup == null) {
            mEasyPopup = new EasyPopup(mContext);
        }
        if(mType.equals(Config.RIGHT)) {
            mEasyPopup.setContentView(R.layout.layout_right_pop);
            mEasyPopup.setAnimationStyle(R.style.AnimTopRight);
        } else if(mType.equals(Config.MIDDLE)) {
            mEasyPopup.setContentView(R.layout.layout_center_pop);
            mEasyPopup.setAnimationStyle(R.style.AnimTopMiddle);
        }
        mEasyPopup.setFocusAndOutsideEnable(true);
        mEasyPopup.setBackgroundDimEnable(true);
        mEasyPopup.setDimValue(0.5f);
        mEasyPopup.createPopup();
        myLv = mEasyPopup.getView(R.id.popup_lv);
//        myLv.setOnItemClickListener(mOnItemClickListener);

    }

    public void show(View view) {
        if (mIsDirty) {
            mIsDirty = false;
            adapter = new PopupAdapter(mContext, mItems, Config.MIDDLE, mSelectItemStr);
            myLv.setAdapter(adapter);
        }
        if(mType.equals(Config.RIGHT)) {
            mEasyPopup.showAtAnchorView(view, VerticalGravity.BELOW, HorizontalGravity.RIGHT, 0, 0);
        } else if(mType.equals(Config.MIDDLE)) {
            mEasyPopup.showAtAnchorView(view, VerticalGravity.BELOW, HorizontalGravity.CENTER, 0, 0);
        }
        myLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mEasyPopup!=null && mEasyPopup.isShowing()){
                    mOnItemClickListener.onItemClick(parent, view, position, id);
                    mEasyPopup.dismiss();
                }
            }
        });
    }

    public void setCurrentSelected(String string) {
        this.mSelectItemStr = string;
    }


}
