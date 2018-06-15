package com.android.dhunter.common.widget.popupWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.android.dhunter.common.R;
import com.android.dhunter.common.utils.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by dhunter on 2018/3/13.
 * 该公共组件已弃用，请使用EasyTopPopup, 目前只有搜索页面在使用该控件
 */

public class TopMiddlePopup extends PopupWindow {
    private Context myContext;
    private ListView myLv;
    private AdapterView.OnItemClickListener myOnItemClickListener;
    private ArrayList<String> myItems;
    private int myWidth;
    private int myHeight;
    private int myType;
    private int setWidth;

    // 判断是否需要添加或更新列表子类项
    private boolean myIsDirty = true;

    private LayoutInflater inflater = null;
    private View myMenuView;

    private LinearLayout popupLL;

    private PopupAdapter adapter;

    public TopMiddlePopup(Context context) {
        // TODO Auto-generated constructor stub
    }

    public TopMiddlePopup(Context context,
                          AdapterView.OnItemClickListener onItemClickListener, ArrayList<String> items,
                          int type) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.top_popup, null);

        this.myContext = context;
        this.myItems = items;
        this.myOnItemClickListener = onItemClickListener;
        this.myType = type;

        this.myWidth = ScreenUtil.getWidth(context);
        this.myHeight = ScreenUtil.getHeight(context);

        initWidget();
        setPopup();
    }




    public TopMiddlePopup(Context context, int width, int height,
                          AdapterView.OnItemClickListener onItemClickListener, ArrayList<String> items,
                          int type) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.top_popup, null);

        this.myContext = context;
        this.myItems = items;
        this.myOnItemClickListener = onItemClickListener;
        this.myType = type;

        this.myWidth = width;
        this.myHeight = height;

        initWidget();
        setPopup();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        myLv = (ListView) myMenuView.findViewById(R.id.popup_lv);
        popupLL = (LinearLayout) myMenuView.findViewById(R.id.popup_layout);
        myLv.setOnItemClickListener(myOnItemClickListener);
        if(myType == 0) {
            android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 2);
            lpPopup.setMargins(0, 0, (int) (myWidth * 1.0 / 4), 0);
            popupLL.setLayoutParams(lpPopup);
        } else if (myType == 1) {
            android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 4);
            lpPopup.setMargins(0, 0, (int) (myWidth * 3.0 / 4), 0);
            popupLL.setLayoutParams(lpPopup);
        } else if (myType == 2) {
            android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 4);
            lpPopup.setMargins(0, 0, (int) (myWidth * 1.0 / 22), 0);
            popupLL.setLayoutParams(lpPopup);
        } else if(myType == Config.POPTYPE_SEARCH) {
            android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 7);
            lpPopup.setMargins(0, 0, (int) (myWidth * 3.0 / 4), 0);
            popupLL.setLayoutParams(lpPopup);
        } else if(myType == Config.POPTYPE_FILTER) {
            android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 4);
            lpPopup.setMargins(0, 0, (int) (myWidth * 1.0 / 22), 0);
            popupLL.setLayoutParams(lpPopup);
        } else if(myType == Config.POPTYPE_PRODUCT_SEARCH) {
            android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 5);
            lpPopup.setMargins(0, 0, (int) (myWidth * 1.0 / 6), 0); // left, top, right, bottom
            popupLL.setLayoutParams(lpPopup);
        }
    }

    /**
     * 设置popup的样式
     */
    private void setPopup() {
        // 设置AccessoryPopup的view
        this.setContentView(myMenuView);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(true);
        // 设置AccessoryPopup弹出窗体的动画效果
        if (myType == 1 || myType == Config.POPTYPE_SEARCH || myType == Config.POPTYPE_PRODUCT_SEARCH) {
            this.setAnimationStyle(R.style.AnimTopLeft);
        } else if (myType == 2 || myType == Config.POPTYPE_FILTER) {
            this.setAnimationStyle(R.style.AnimTopRight);
        } else {
            //this.setAnimationStyle(R.style.AnimTop);
            this.setAnimationStyle(R.style.AnimTopMiddle);
        }
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x33000000);
//        // 设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);

        myMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

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
        if (myIsDirty) {
            myIsDirty = false;
            adapter = new PopupAdapter(myContext, myItems, Config.MIDDLE);
            myLv.setAdapter(adapter);
        }

        showAsDropDown(view, 0, 0);
    }
    public void setSameWidth(View view) {
        this.setWidth = view.getWidth();
    }
}
