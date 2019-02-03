package com.tupperware.biz.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.popupWindow.Config;
import com.android.dhunter.common.widget.popupWindow.EasyTopPopup;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.ui.activities.SearchActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/2/2.
 */

public class CloudOrderFragment extends BaseFragment {

    private OnLineOrderFragment mOnLineOrderFragment;
    private ReservationOrderFragment mReservationOrderFragment;

    @BindView(R.id.cloud_order_title_ll)
    LinearLayout mChooseTile;
    @BindView(R.id.cloud_order_title)
    TextView mTitle;
    @BindView(R.id.right_search_icon)
    ImageView mRightSearch;
    @BindView(R.id.right_filter_icon)
    ImageView mRightFilter;

    private View rootView;
    private FragmentManager fragmentManager;
    private EasyTopPopup mPopup;
    private String mFilterString;

    public static CloudOrderFragment newInstance() {
        CloudOrderFragment fragment = new CloudOrderFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mActivity = getActivity();
        initLayout();
//        requestData();
        return rootView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cloud_order;
    }

    @Override
    public void initLayout() {
        fragmentManager = getActivity().getSupportFragmentManager();
        mOnLineOrderFragment = new OnLineOrderFragment();
        mReservationOrderFragment = new ReservationOrderFragment();

        fragmentManager.beginTransaction().add(R.id.orderFrame, mOnLineOrderFragment, "1").commitAllowingStateLoss();
        fragmentManager.beginTransaction().add(R.id.orderFrame, mReservationOrderFragment, "2").commitAllowingStateLoss();
        selection(0);
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.cloud_order_title_ll, R.id.right_search_icon, R.id.right_filter_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cloud_order_title_ll:
                mPopup = new EasyTopPopup(mActivity, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selection(position);
                    }
                }, getItemsName(), Config.MIDDLE);
                mPopup.setCurrentSelected(mTitle.getText().toString().trim());
                mPopup.show(view);
                break;
            case R.id.right_search_icon:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.ORDER_FRAGMENT);
                startActivity(intent);
                break;
            case R.id.right_filter_icon:
                mPopup = new EasyTopPopup(mActivity, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        selection(position);
                        mFilterString = (String) parent.getItemAtPosition(position);
                    }
                }, getFilterItemsName(), Config.RIGHT);
                if(mFilterString == null) {
                    mFilterString = getResources().getString(R.string.refrigerator_clean);
                }
                mPopup.setCurrentSelected(mFilterString);
                mPopup.show(view);
                break;
        }
    }

    /**
     * 设置主弹窗内容
     *
     * @return
     */
    private ArrayList<String> getItemsName() {
        ArrayList<String> items = new ArrayList<String>();
        items.add(getResources().getString(R.string.order_on_line));
        items.add(getResources().getString(R.string.reservation_Order));
        return items;
    }

    /**
     * 设置过滤弹窗内容
     *
     * @return
     */
    private ArrayList<String> getFilterItemsName() {
        ArrayList<String> items = new ArrayList<String>();
        items.add(getResources().getString(R.string.refrigerator_clean));
        items.add(getResources().getString(R.string.change_filter));
        items.add(getResources().getString(R.string.water_purifier_try_use));
        items.add(getResources().getString(R.string.change_filter));
        items.add(getResources().getString(R.string.water_purifier_try_use));
        items.add(getResources().getString(R.string.change_filter));
        return items;
    }



    private void selection(int position) {
        switch (position) {
            case 0:
                mTitle.setText(getResources().getString(R.string.order_on_line));
                mRightSearch.setVisibility(View.VISIBLE);
                mRightFilter.setVisibility(View.GONE);
                fragmentManager.beginTransaction().hide(mReservationOrderFragment)
                        .show(mOnLineOrderFragment).commit();
                break;
            case 1:
                mTitle.setText(getResources().getString(R.string.reservation_Order));
                mRightSearch.setVisibility(View.GONE);
                mRightFilter.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().hide(mOnLineOrderFragment)
                        .show(mReservationOrderFragment).commit();
                break;
        }
    }

}
