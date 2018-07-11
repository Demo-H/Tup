package com.tupperware.huishengyi.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.popupWindow.Config;
import com.android.dhunter.common.widget.popupWindow.TopMiddlePopup;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.SearchMemberFragment;
import com.tupperware.huishengyi.ui.fragment.SearchOrderFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/16.
 */

public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

    @BindView(R.id.home_search_ll)
    LinearLayout mHomeSearchTitle;
    @BindView(R.id.search)
    EditText mSearchEdit;
    @BindView(R.id.search_cancel)
    TextView mCancelView;
    @BindView(R.id.search_choose_title_ll)
    LinearLayout mChoosell;
    @BindView(R.id.search_choose_title)
    TextView mChooseTite;

    @BindView(R.id.order_search_ll)
    LinearLayout mOrderSearchTitle;
    @BindView(R.id.order_search)
    EditText mOrderSearchEdit;
    @BindView(R.id.order_search_cancel)
    TextView mOrderCancelView;

    private TopMiddlePopup mTitleMiddlePopup;
    private String activity_from;

    private SearchOrderFragment mOrderFragment;
    private SearchMemberFragment mMemberFragment;
    private FragmentManager fragmentManager;
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mOrderFragment = (SearchOrderFragment) fragmentManager.findFragmentByTag(SearchOrderFragment.class.getName());
            mMemberFragment = (SearchMemberFragment) fragmentManager.findFragmentByTag(SearchMemberFragment.class.getName());
            fragmentManager.beginTransaction().show(mOrderFragment).hide(mMemberFragment);
            //把当前显示的fragment记录下来
            currentFragment = mOrderFragment;
        } else {
            mOrderFragment = mOrderFragment.newInstance();
            mMemberFragment = mMemberFragment.newInstance();
            if(Constant.LOVE_VIP_FRAGMENT.equals(activity_from)) {
                mSearchEdit.setHint(R.string.search_vip_tip);
                mChooseTite.setText(getResources().getString(R.string.vip));
                showFragment(mMemberFragment);
            } else {
                showFragment(mOrderFragment);
            }
        }
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    private void init() {
        if(Constant.ORDER_FRAGMENT.equals(activity_from)) {
            mHomeSearchTitle.setVisibility(View.GONE);
            mOrderSearchTitle.setVisibility(View.VISIBLE);
            mOrderFragment.setEditText(mOrderSearchEdit);
        } else {
            mHomeSearchTitle.setVisibility(View.VISIBLE);
            mOrderSearchTitle.setVisibility(View.GONE);
            mOrderFragment.setEditText(mSearchEdit);
            mMemberFragment.setEditText(mSearchEdit);
        }

    }

    @Override
    protected void initLayout() {
        fragmentManager = getSupportFragmentManager();
        activity_from = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.search, R.id.order_search, R.id.search_cancel, R.id.search_choose_title_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).showSoftInput(mSearchEdit, 0);
                break;
            case R.id.order_search:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).showSoftInput(mOrderSearchEdit, 0);
                break;
            case R.id.search_cancel:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        SearchActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
//                onBackPressed();
                break;
            case R.id.search_choose_title_ll:
                mTitleMiddlePopup = new TopMiddlePopup(view.getContext(),  new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selection(position);
                        mTitleMiddlePopup.dismiss();
                    }
                }, getItemsName(), Config.POPTYPE_SEARCH);
                mTitleMiddlePopup.show(mChoosell);
                break;
        }
    }

    /**
     * 设置弹窗内容
     *
     * @return
     */
    private ArrayList<String> getItemsName() {
        ArrayList<String> items = new ArrayList<String>();
        items.add(getResources().getString(R.string.order));
        items.add(getResources().getString(R.string.vip));
        return items;
    }

    private void selection(int position) {
        switch (position) {
            case 0:
                mSearchEdit.setHint(R.string.search_order_tip);
                mChooseTite.setText(getResources().getString(R.string.order));
                showFragment(mOrderFragment);
                break;
            case 1:
                mSearchEdit.setHint(R.string.search_vip_tip);
                mChooseTite.setText(getResources().getString(R.string.vip));
                showFragment(mMemberFragment);
                break;
        }
    }


    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(BaseFragment fg){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if(!fg.isAdded()){
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .add(R.id.searchresultFrame, fg, fg.getClass().getName());  //第三个参数为添加当前的fragment时绑定一个tag，即绑定fragment的类名
            } else {
                transaction.add(R.id.searchresultFrame, fg, fg.getClass().getName());
            }
        }else{
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .show(fg);
            } else {
                transaction.show(fg);
            }
        }
        currentFragment = fg;
        transaction.commit();
    }
}
