package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.config.GlobalConfig;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.component.DaggerDevProductSelectActivityComponent;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.DevMemberRequest;
import com.tupperware.huishengyi.entity.member.DevMemberRespone;
import com.tupperware.huishengyi.entity.member.DevMemberSelect;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.module.DevProductSelectPresenterModule;
import com.tupperware.huishengyi.ui.contract.DevProductSelectContract;
import com.tupperware.huishengyi.ui.presenter.DevProductSelectPresenter;
import com.tupperware.huishengyi.utils.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/27.
 */

public class DevProductSelectActivity extends BaseActivity implements DevProductSelectContract.View{

    private static final String TAG = "DevProductSelectActivity";

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

    @BindView(R.id.set_water_cleaner_ll)
    LinearLayout mWaterCleaner;
    @BindView(R.id.set_tup_ll)
    LinearLayout mTup;
    @BindView(R.id.set_take_in_ll)
    LinearLayout mTakein;
    @BindView(R.id.set_kitchen_ll)
    LinearLayout mKitchen;

    private DevMemberSelect mDevMember;
    private List<DevMemberRequest.TagsCode> productTagsList;
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private DevMemberRequest mDevMemberRequest;
    private List<DevMemberRequest.TagsCode> tagsList;
    @Inject
    DevProductSelectPresenter mPresenter;
    private String currentStoreId;
    private String employeeCode;
    private String mobilePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_product_select);
        ActivityManager.getInstance().addActivity(this);
        initLayout();
        initLayoutData();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.member_label));
        mRightText.setText(getResources().getString(R.string.complete));
        DaggerDevProductSelectActivityComponent.builder()
                .appComponent(getAppComponent())
                .devProductSelectPresenterModule(new DevProductSelectPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
    }

    @Override
    protected void initLayoutData() {
        Bundle bundle = this.getIntent().getExtras();
        mDevMember = (DevMemberSelect)bundle.getSerializable(Constant.DEV_MEMBER_DATA);
        employeeCode = (String) mSharePreDate.getParam(GlobalConfig.EMPLOYEE_CODE, "");
        currentStoreId = (String) mSharePreDate.getParam(GlobalConfig.STORE_ID, "");
        mobilePhone = mDevMember.getMobile();
    }

    @OnClick({R.id.left_back, R.id.next, R.id.set_water_cleaner_ll, R.id.set_tup_ll, R.id.set_take_in_ll, R.id.set_kitchen_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                if(getSelectItem()) {
                    showDialog();
                    mPresenter.submitmemberLabel(getRequestData());
                } else {
                    toast("请选择后再提交");
                }
                break;
            case R.id.set_water_cleaner_ll:
                selectChange(mWaterCleaner, 0);
                break;
            case R.id.set_tup_ll:
                selectChange(mTup, 1);
                break;
            case R.id.set_take_in_ll:
                selectChange(mTakein, 2);
                break;
            case R.id.set_kitchen_ll:
                selectChange(mKitchen, 3);
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

    private void selectChange(LinearLayout mLinlayout, int position) {
        if(mLinlayout.isSelected()) {
            mLinlayout.setSelected(false);
            mSelectedPositions.put(position, false);
        } else {
            mLinlayout.setSelected(true);
            mSelectedPositions.put(position, true);
        }
    }

    private boolean getSelectItem() {
        if(productTagsList != null) {
            productTagsList.clear();
        } else {
            productTagsList = new ArrayList<>();
        }
        DevMemberRequest.TagsCode tags;
        if(mSelectedPositions.get(0)) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_WATER_CLEANER);
            productTagsList.add(tags);
        }
        if(mSelectedPositions.get(1)) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TUP);
            productTagsList.add(tags);
        }
        if(mSelectedPositions.get(2)) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TAKE_IN);
            productTagsList.add(tags);
        }
        if(mSelectedPositions.get(3)) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_KITCHNEN);
            productTagsList.add(tags);
        }
        if(productTagsList.size() == 0) {
            return false;
        } else {
            mDevMember.setProductTags(productTagsList);
            return true;
        }
    }

    private DevMemberRequest getRequestData() {
        mDevMemberRequest = new DevMemberRequest();
        mDevMemberRequest.setCurrentStoreId(currentStoreId);
        mDevMemberRequest.setEmployeeCode(employeeCode);
        mDevMemberRequest.setMobile(mDevMember.getMobile());
        tagsList = new ArrayList<>();
        DevMemberRequest.TagsCode tags;
        tags = new DevMemberRequest.TagsCode();
        tags.setTagCode(mDevMember.getIsLike());
        tagsList.add(tags);
        tags = new DevMemberRequest.TagsCode();
        tags.setTagCode(mDevMember.getIsWantStudy());
        tagsList.add(tags);
        for(int i = 0; i < mDevMember.getTasteTags().size(); i++) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(mDevMember.getTasteTags().get(i).getTagCode());
            tagsList.add(tags);
        }
        tags = new DevMemberRequest.TagsCode();
        tags.setTagCode(mDevMember.getIsHave());
        tagsList.add(tags);
        tags = new DevMemberRequest.TagsCode();
        tags.setTagCode(mDevMember.getIsClean());
        tagsList.add(tags);
        tags = new DevMemberRequest.TagsCode();
        tags.setTagCode(mDevMember.getFamilystatus());
        tagsList.add(tags);
        tags = new DevMemberRequest.TagsCode();
        tags.setTagCode(mDevMember.getMoneystatus());
        tagsList.add(tags);
        for(int i = 0; i < mDevMember.getProductTags().size(); i++) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(mDevMember.getProductTags().get(i).getTagCode());
            tagsList.add(tags);
        }
        mDevMemberRequest.setTags(tagsList);
        return mDevMemberRequest;
    }

    @Override
    public void setDevMemberData(DevMemberRespone devMemberRespone) {
        if(devMemberRespone.model.qrcode == null || devMemberRespone.model.qrcode.isEmpty()) {
            Intent intent = new Intent(this, MemberDetialActivity.class);
            intent.putExtra(Constant.DEV_MEMBER_PHONE, mobilePhone);
            startActivity(intent);
            ActivityManager.getInstance().exit();
        } else {
            Intent intent = new Intent(this, DevQrActivity.class);
            intent.putExtra(Constant.DEV_MEMBER_RQ_CODE, devMemberRespone.model.qrcode);
            intent.putExtra(Constant.DEV_MEMBER_PHONE, mobilePhone);
            startActivity(intent);
        }
    }
}
