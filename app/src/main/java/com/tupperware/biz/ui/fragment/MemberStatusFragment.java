package com.tupperware.biz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.TagAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.TagBean;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.StringUtils;
import com.tupperware.biz.view.SpacesItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tupperware.biz.config.Constant.REQUEST_DATE;

/**
 * Created by dhunter on 2018/4/3.
 */

public class MemberStatusFragment extends BaseFragment {

    @BindView(R.id.month)
    TextView mDateMonth;
    @BindView(R.id.status_recyclerview)
    RecyclerView mRecyclerView;

    private View rootview;
    private LinearLayout mRightll;
    private List<TagBean> tagBeanList = new ArrayList<>();
    private TagAdapter tagAdapter;


    public void setRightIconText(LinearLayout mRightIcon) {
        this.mRightll = mRightIcon;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
//        requestData();
        setRecyclerView();
        return rootview;
    }

    @Override
    public void initLayout() {
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        mDateMonth.setText(formatter.format(curDate));
        mRightll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

    }

    @Override
    public void requestData() {
        if(tagBeanList.size() == 0) {
            tagBeanList.add(new TagBean("1", getResources().getString(R.string.current_active_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("2", getResources().getString(R.string.active_in_three_month), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("3", getResources().getString(R.string.active_in_six_month), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("4", getResources().getString(R.string.active_in_twelve_month), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("5", getResources().getString(R.string.half_sleep_member), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("6", getResources().getString(R.string.sleep_member), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("7", getResources().getString(R.string.deep_sleep_member), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("8", getResources().getString(R.string.miss_member), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("9", getResources().getString(R.string.current_active_member_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("10", getResources().getString(R.string.current_active_vip_count), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("11", getResources().getString(R.string.member_unit), StringUtils.getTestCount()));
            tagBeanList.add(new TagBean("12", getResources().getString(R.string.member_purchase_money), StringUtils.getTestCount()));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_member_status;
    }

    private void setRecyclerView() {
        GridLayoutManager layoutManage = new GridLayoutManager(getActivity(), 2);
        layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(layoutManage);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_STATUS));
        tagAdapter = new TagAdapter(tagBeanList);
        mRecyclerView.setAdapter(tagAdapter);
    }

    private void chooseDate() {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog =  DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
        dialog.setTargetFragment(MemberStatusFragment.this, REQUEST_DATE);
        dialog.show(manager, Constant.DIALOG_DATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(Constant.EXTRA_DATE);

            mDateMonth.setText(DateFormatter.MonthFormat(date));
        }
    }
}