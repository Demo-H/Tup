package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dhunter.common.widget.flowlayout.FlowLayout;
import com.android.dhunter.common.widget.flowlayout.TagAdapter;
import com.android.dhunter.common.widget.flowlayout.TagFlowLayout;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/28.
 */

public class DevInfoResultActivity extends BaseActivity {

    private static final String TAG = "DevInfoResultActivity";

    @BindView(R.id.image_cancel)
    ImageView mCancel;
    @BindView(R.id.sex_result_image)
    ImageView mSexImage;
    @BindView(R.id.age_result)
    TextView mAge;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mTagFlowLayout;
    @BindView(R.id.follow_fans)
    TextView mFans;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info_result);
        ActivityManager.getInstance().addActivity(this);
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mInflater = getLayoutInflater();
        mTagFlowLayout.setAdapter(new TagAdapter<String>(getStringArray(R.array.interest)) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.textview_tag_select,
                        mTagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

    }

    @Override
    protected void initLayoutData() {

    }

    @OnClick({R.id.image_cancel, R.id.follow_fans})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_cancel:
                ActivityManager.getInstance().exit();
                break;
            case R.id.follow_fans:
                Intent intent = new Intent(this, MemberDetialActivity.class);
                startActivity(intent);
                ActivityManager.getInstance().exit();
                break;
        }
    }


    @Override
    public void setStateBarColor() {
        setStateBarColor(R.color.white);
    }
}
