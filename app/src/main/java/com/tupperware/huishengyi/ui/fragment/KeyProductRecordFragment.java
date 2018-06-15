package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.KeyProductRecordInventoryAdapter;
import com.tupperware.huishengyi.adapter.KeyProductRecordSaleAdapter;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.KeyProductRecordBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.component.DaggerKeyProductRecordFragmentComponent;
import com.tupperware.huishengyi.module.KeyProductRecordPresenterModule;
import com.tupperware.huishengyi.ui.contract.KeyProductRecordContract;
import com.tupperware.huishengyi.ui.presenter.KeyProductRecordPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/22.
 */

public class KeyProductRecordFragment extends BaseFragment implements KeyProductRecordContract.View {

    @BindView(R.id.record_recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    KeyProductRecordPresenter mPresenter;

    private KeyProductRecordSaleAdapter mSaleAdapter;
    private KeyProductRecordInventoryAdapter mInventoryAdapter;
    private int mTabPosition;


    public static KeyProductRecordFragment newInstance(Bundle bundle) {
        KeyProductRecordFragment fragment = new KeyProductRecordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
        initLayoutData();
        return view;
    }


    @Override
    public void initLayout() {
        DaggerKeyProductRecordFragmentComponent.builder()
                .appComponent(getAppComponent())
                .keyProductRecordPresenterModule(new KeyProductRecordPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_SWITCH_STORE));

        if(mTabPosition == 0) { //销售记录
            mSaleAdapter = new KeyProductRecordSaleAdapter(R.layout.item_key_product_record_sale_recyclerview);
            mRecyclerView.setAdapter(mSaleAdapter);
        } else if(mTabPosition == 1) {//库存记录
            mInventoryAdapter = new KeyProductRecordInventoryAdapter(R.layout.item_key_product_record_sale_recyclerview);
            mRecyclerView.setAdapter(mInventoryAdapter);
        }

    }

    @Override
    public void initLayoutData() {
        mPresenter.getKeyProductRecordData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_key_product_record;
    }

    @Override
    public void setKeyProductRecordData(KeyProductRecordBean keyProductRecordBean) {
        if(mTabPosition == 0) {
            mSaleAdapter.addData(keyProductRecordBean.content);
        } else  if(mTabPosition == 1) {
            mInventoryAdapter.addData(keyProductRecordBean.content);
        }
    }
}
