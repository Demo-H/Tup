package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.TagAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.TagBean;
import com.tupperware.biz.utils.StringUtils;
import com.tupperware.biz.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/3.
 */

public class MemberZhaomuListFragment extends BaseFragment {

    @BindView(R.id.record_recyclerview)
    RecyclerView mRecyclerView;

    private int mTabPosition;
    private List<TagBean> tagBeanList = new ArrayList<>();
    private TagAdapter tagAdapter;

    public static MemberZhaomuListFragment newInstance(Bundle bundle) {
        MemberZhaomuListFragment fragment = new MemberZhaomuListFragment();
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
//        requestData();
        setRecyclerView();
        return view;
    }

    @Override
    public void initLayout() {

    }

    @Override
    public void requestData() {
        if(tagBeanList.size() == 0) {
            tagBeanList.add(new TagBean("1", getResources().getString(R.string.new_member_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("2", getResources().getString(R.string.new_very_member_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("3", getResources().getString(R.string.effective_member_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("4", getResources().getString(R.string.effective_very_member_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("5", getResources().getString(R.string.new_come_member_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("6", getResources().getString(R.string.new_come_very_member_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("7", getResources().getString(R.string.effective_all_member_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("8", getResources().getString(R.string.new_come_all_member_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("9", getResources().getString(R.string.online_member_count), StringUtils.getTestCount()));

            tagBeanList.add(new TagBean("10", getResources().getString(R.string.member_out), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("11", getResources().getString(R.string.member_in), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("12", getResources().getString(R.string.very_member_out), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("13", getResources().getString(R.string.very_member_in), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("14", getResources().getString(R.string.member_exit), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("15", getResources().getString(R.string.very_member_exit), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("16", getResources().getString(R.string.member_update_out), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("17", getResources().getString(R.string.member_update_in), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("18", getResources().getString(R.string.market_update_count), StringUtils.getTestCount()));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_key_product_record;
    }

    private void setRecyclerView() {
        GridLayoutManager layoutManage = new GridLayoutManager(getActivity(), 2);
        layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (tagBeanList.get(position).getTag_id().equals("9")) {
                    return 2;
                }
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(layoutManage);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ZHAOMU));
        tagAdapter = new TagAdapter(tagBeanList);
        mRecyclerView.setAdapter(tagAdapter);
    }

}
