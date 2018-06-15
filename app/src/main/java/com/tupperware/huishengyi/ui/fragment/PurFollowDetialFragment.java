package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.PurFollowDetialAdapter;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.PurFollowDetialBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.component.DaggerPurFollowDetialFragmentComponent;
import com.tupperware.huishengyi.module.PurFollowDetialPresenterModule;
import com.tupperware.huishengyi.ui.contract.PurFollowDetialContract;
import com.tupperware.huishengyi.ui.presenter.PurFollowDetialPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/30.
 * 意向客户跟进
 */

public class PurFollowDetialFragment extends BaseFragment implements PurFollowDetialContract.View {

    @BindView(R.id.record_recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    PurFollowDetialPresenter mPresenter;

    private PurFollowDetialAdapter mAdapter;
    private int mTabPosition;
    private ArrayList<PurFollowDetialBean> list = new ArrayList<>();


    public static PurFollowDetialFragment newInstance(Bundle bundle) {
        PurFollowDetialFragment fragment = new PurFollowDetialFragment();
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
        DaggerPurFollowDetialFragmentComponent.builder()
                .appComponent(getAppComponent())
                .purFollowDetialPresenterModule(new PurFollowDetialPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_SWITCH_STORE));
        if(Constant.DemoTest) {
            PurFollowDetialBean purFollowDetialBean = null;
            list.clear();
            for(int i=0; i<2; i++) {
                purFollowDetialBean = new PurFollowDetialBean();
                purFollowDetialBean.userName = "朱小姐";
                list.add(purFollowDetialBean);
            }
        }
        mAdapter = new PurFollowDetialAdapter(R.layout.item_purpose_follow_recyclerview, list);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initLayoutData() {
        mPresenter.getPurFollowDetialData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_key_product_record; //复用
    }

    @Override
    public void setPurFollowDetialData(PurFollowDetialBean purFollowDetialBean) {

        mAdapter.addData(purFollowDetialBean);
    }
}
