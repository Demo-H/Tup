package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.WaterSafeCheckAdapter;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.DataEntry;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.android.dhunter.common.utils.SharePreferenceData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/30.
 * 饮水机安全调查
 */

public class BackVisitDetialActivity extends BaseActivity {

    private static final String TAG = "BackVisitDetialActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;

    @BindView(R.id.member_name)
    TextView mName;
    @BindView(R.id.tele_number)
    TextView mTelNum;
    @BindView(R.id.addr)
    TextView mAddr;
    @BindView(R.id.install_date)
    TextView mInstallDate;
    @BindView(R.id.base_code)
    TextView mBaseCode;
    @BindView(R.id.full_choose)
    CheckBox mFullChoose;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private String fromTag;
    private WaterSafeCheckAdapter mAdapter;  //复用
    private ArrayList<DataEntry> itemBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_visit_detial);
        //将相关activity暂时保存，等到扫描完成后再一次性finish
        ActivityManager.getInstance().addActivity(this);
        mSharePreDate = new SharePreferenceData(this);
        token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
        fromTag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        mContext = this;
        initLayoutData();
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.water_safe_check));
        mRightText.setText(getResources().getString(R.string.submit));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new WaterSafeCheckAdapter(R.layout.item_water_safe_check_recycleview, itemBeanList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initLayoutData() {
        DataEntry itemBean = null;
        String[] arrayString = getStringArray(R.array.server_follow_after_sale);
        for(int i = 0, p = arrayString.length; i < p; i++) {
            itemBean = new DataEntry();
            itemBean.title = arrayString[i];
            itemBeanList.add(itemBean);
        }

    }

    @OnClick({R.id.left_back, R.id.next, R.id.full_choose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                toast("提交完成");
                ActivityManager.getInstance().exit();
                break;
            case R.id.full_choose:
                if(mFullChoose.isChecked()) {
                    mAdapter.setAll(true);
                } else {
                    mAdapter.setAll(false);
                }
                break;
        }
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }
}
