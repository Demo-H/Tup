package com.tupperware.biz.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.MemberPopupAdapter;

import java.util.ArrayList;

/**
 * Created by dhunter on 2018/10/26.
 */

public class MemberSortSelectPopup extends PopupWindow {

    private Context mContext;
    private ListView myLv;
//    private AdapterView.OnItemClickListener myOnItemClickListener;
    private ArrayList<String> myItems;
    private int myWidth;
    private int myHeight;
    private int setWidth;
    private String selectString;
    private OnSortSelectedClickListener mOnSortSelectedListener;

    private MemberPopupAdapter adapter;
    // 判断是否需要添加或更新列表子类项
    private boolean mIsDirty = true;
    private LayoutInflater inflater = null;
    private View myMenuView;

    private LinearLayout popupLL;
    public MemberSortSelectPopup(Context context) {
        // TODO Auto-generated constructor stub
    }

    public MemberSortSelectPopup(Context context,
                                 OnSortSelectedClickListener onItemClickListener, ArrayList<String> items, String selectString) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.layout_full_pop, null);

        this.mContext = context;
        this.myItems = items;
        this.mOnSortSelectedListener = onItemClickListener;
        this.selectString = selectString;

        initWidget();
        setPopup();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        myLv = (ListView) myMenuView.findViewById(R.id.popup_lv);
        popupLL = (LinearLayout) myMenuView.findViewById(R.id.popup_layout);
        myLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setItemChecked(position);
                adapter.notifyDataSetChanged();
                mOnSortSelectedListener.onSortSelectedClick(position);
            }
        });

    }

    /**
     * 设置popup的样式
     */
    private void setPopup() {
        // 设置AccessoryPopup的view
        this.setContentView(myMenuView);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(true);
        // 设置AccessoryPopup弹出窗体的动画效果
        this.setAnimationStyle(R.style.AnimTopMiddle);
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x33000000);
//        // 设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);

        myMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(popupLL != null) {
                    int height = popupLL.getBottom();
                    int left = popupLL.getLeft();
                    int right = popupLL.getRight();
                    int y = (int) event.getY();
                    int x = (int) event.getX();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y > height || x < left || x > right) {
                            dismiss();
                        }
                    }
                }
                return true;
            }
        });
    }

    /**
     * 显示弹窗界面
     *
     * @param view
     */
    public void show(View view) {
        if (mIsDirty) {
            mIsDirty = false;
            adapter = new MemberPopupAdapter(mContext, myItems, selectString, mOnSortSelectedListener);
            myLv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            myLv.setAdapter(adapter);
        }

        showAsDropDown(view, 0, 0);
    }

    public interface OnSortSelectedClickListener {
        void onSortSelectedClick(int position);
    }

//    public OnSortSelectedClickListener getmOnSortSelectedListener() {
//        return mOnSortSelectedListener;
//    }
//
//    public void setmOnSortSelectedListener(OnSortSelectedClickListener mOnSortSelectedListener) {
//        this.mOnSortSelectedListener = mOnSortSelectedListener;
//    }
}