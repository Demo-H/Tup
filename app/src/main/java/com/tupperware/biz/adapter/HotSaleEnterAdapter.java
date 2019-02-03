package com.tupperware.biz.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.hotsale.HotInventoryInfo;
import com.tupperware.biz.entity.hotsale.HotSaleEnterInfo;
import com.tupperware.biz.utils.StringUtils;

import java.util.List;

/**
 * Created by dhunter on 2018/12/3.
 */

public class HotSaleEnterAdapter extends BaseQuickAdapter<HotInventoryInfo, BaseViewHolder> {

    private Animation leftAlphaAnimation = null;
    private Animation rightAlphaAnimation = null;
    private TranslateAnimation expandAlphaAnimation = null;
    private SparseArray<HotSaleEnterInfo> sparselist;
    private EditText mEditText;
    private String mCount;
    private int value;
    private HotSaleEnterInfo infoItem;
    private final static int IN = 0;
    private final static int OUT = 1;
//    private Integer storeId;
//    private String currentDate;


    public HotSaleEnterAdapter(Context mContext, int layoutResId) {
        super(layoutResId);
//        this.storeId = storeId;
//        currentDate = DateFormatter.timestampToDateToSecond(System.currentTimeMillis());
        leftAlphaAnimation = AnimationUtils.loadAnimation(mContext, R.anim.left_rotate);
        rightAlphaAnimation = AnimationUtils.loadAnimation(mContext, R.anim.right_rotate);
        expandAlphaAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        expandAlphaAnimation.setDuration(300);

        sparselist = new SparseArray<>();

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.parent_layout:
                        switchHideAndExpandChild(view);
                        break;
                    case R.id.inventory_in_count_add:
                        addOne(view, R.id.inventory_in_count_text);
                        break;
                    case R.id.inventory_in_count_reduce:
                        reduceOne(view, R.id.inventory_in_count_text);
                        break;
                    case R.id.goods_out_count_add:
                        addOne(view, R.id.goods_out_count_text);
                        break;
                    case R.id.goods_out_count_reduce:
                        reduceOne(view, R.id.goods_out_count_text);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void convert(final BaseViewHolder helper, HotInventoryInfo item, int position) {
        helper.setText(R.id.parent_title, item.getGoodsName());
        helper.setText(R.id.inventory_in_count_text, item.getDayJhl() + "");
        helper.setText(R.id.goods_out_count_text, item.getDayXsl() + "");

        helper.addOnClickListener(R.id.parent_layout);
        helper.addOnClickListener(R.id.inventory_in_count_add);
        helper.addOnClickListener(R.id.inventory_in_count_reduce);
        helper.addOnClickListener(R.id.goods_out_count_add);
        helper.addOnClickListener(R.id.goods_out_count_reduce);

        ((EditText)helper.getView(R.id.inventory_in_count_text)).addTextChangedListener(new TextWatcher() {
            int position = helper.getAdapterPosition();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                value = StringUtils.StringChangeToInt(s.toString());
                updateSpareList(position, IN, value);
            }
        });

        ((EditText)helper.getView(R.id.goods_out_count_text)).addTextChangedListener(new TextWatcher() {
            int position = helper.getAdapterPosition();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                value = StringUtils.StringChangeToInt(s.toString());
                updateSpareList(position, OUT, value);
            }
        });

    }

    @Override
    public void setNewData(List<HotInventoryInfo> data) {
        super.setNewData(data);
        if(data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                HotSaleEnterInfo info = new HotSaleEnterInfo();
                info.setGoodsId(data.get(i).getId());
                info.setDayJhl(0);
                info.setDayXsl(0);
                sparselist.put(i, info);
            }
        }
    }

    private void switchHideAndExpandChild(View view) {
        if(view.getParent() != null) {
            LinearLayout childView = (LinearLayout) ((View) view.getParent()).findViewById(R.id.child_enter_layout);
            ImageView switchArrow = (ImageView) view.findViewById(R.id.switch_arrow);
            if (childView.getVisibility() == View.GONE) {
                childView.startAnimation(expandAlphaAnimation);
                childView.setVisibility(View.VISIBLE);
                switchArrow.startAnimation(leftAlphaAnimation);
            } else if(childView.getVisibility() == View.VISIBLE){
//                childView.startAnimation(hideAlphaAnimation);
                childView.setVisibility(View.GONE);
                switchArrow.startAnimation(rightAlphaAnimation);
            }
        }
    }

    private void addOne(View view, int id) {
        ((InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if(view.getParent() != null) {
            mEditText = (EditText) ((View) view.getParent()).findViewById(id);
            mCount = mEditText.getText().toString().trim();
            value = StringUtils.StringChangeToInt(mCount) + 1;
            mEditText.setText( value + "");
//            updateSpareList(position, type, value);
        }
    }

    private void reduceOne(View view, int id) {
        ((InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if(view.getParent() != null) {
            mEditText = (EditText) ((View) view.getParent()).findViewById(id);
            mCount = mEditText.getText().toString().trim();
            if(StringUtils.StringChangeToInt(mCount) > 0) {
                value = StringUtils.StringChangeToInt(mCount) - 1;
            } else {
                value = 0;
            }
            mEditText.setText(value + "");
//            updateSpareList(position, type, value);
        }
    }

    private void updateSpareList(int position, int type, int count) {
        infoItem = sparselist.get(position);
        if(type == IN) {
            infoItem.setDayJhl(count);
        } else {
            infoItem.setDayXsl(count);
        }
        sparselist.put(position, infoItem);
    }

    public SparseArray<HotSaleEnterInfo> getSparselist() {
        return sparselist;
    }

}
