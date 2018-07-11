package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.StoreSwitchAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.StoreBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.component.DaggerStoreSwitchFragmentComponent;
import com.tupperware.huishengyi.ui.module.StoreSwitchPresenterModule;
import com.tupperware.huishengyi.ui.contract.StoreSwitchContract;
import com.tupperware.huishengyi.ui.presenter.StoreSwitchPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/19.
 */

public class StoreSwitchFragment extends BaseFragment implements StoreSwitchContract.View{

    private static final String TAG = "StoreSwitchFragment";

    @Inject
    StoreSwitchPresenter mPresenter;
    private RecyclerView mStoreSwitchRecyclerview;
//    private PullHeaderView storeSwitchPullRefreshHeader;
    private StoreSwitchAdapter mAdapter;


    public static StoreSwitchFragment newInstance() {
        StoreSwitchFragment fragment = new StoreSwitchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mStoreSwitchRecyclerview = (RecyclerView) view.findViewById(R.id.switch_store_recyclerview);
//        storeSwitchPullRefreshHeader = (PullHeaderView) view.findViewById(R.id.find_pull_refresh_header);
        initLayout();
        requestData();
        return view;
    }


    @Override
    public void initLayout() {
        DaggerStoreSwitchFragmentComponent.builder()
                .appComponent(getAppComponent())
                .storeSwitchPresenterModule(new StoreSwitchPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
//        storeSwitchPullRefreshHeader.setEnabled(false);
//        storeSwitchPullRefreshHeader.setPtrHandler(this);
        mStoreSwitchRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mStoreSwitchRecyclerview.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_SWITCH_STORE));
        mAdapter = new StoreSwitchAdapter(R.layout.item_store_switch_recyclerview, mStoreSwitchRecyclerview);
//        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setEnableLoadMore(true);
        mStoreSwitchRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {
        mPresenter.getStoreData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_store_switch;
    }


    @Override
    public void setStoreData(StoreBean store) {
        //add for test
        if(Constant.DemoTest) {
            if(store.content != null) {
                store.content.clear();
                String []storeName = new String[]{"华景专卖店","建设专卖店", "西关专卖店"};
                StoreBean.ContentBean contentItem = null;
                for(int i = 0; i < storeName.length; i++) {
                    contentItem = new StoreBean.ContentBean();
                    contentItem.image = R.mipmap.coupon_prd_pic;
                    contentItem.storeName = storeName[i];
                    contentItem.isSelectedimage = R.mipmap.selected_ic;
                    contentItem.isNotSelectedimage = R.mipmap.normal_ic;
                    contentItem.isSelected = i < 1? true:false;
                    store.content.add(contentItem);
                }
            }
        }
        mAdapter.addData(store.content);
    }

    @Override
    public void setRefreshStoreData(StoreBean store) {

    }

/*    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.refreshComplete();
            }
        }, 2000);
    }*/

}
