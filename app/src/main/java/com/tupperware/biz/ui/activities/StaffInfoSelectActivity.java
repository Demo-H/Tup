package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/10/24.
 */

public class StaffInfoSelectActivity extends BaseActivity {

    private static final String TAG = "StaffInfoSelectActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.select_one)
    TextView mSelectOneText;
    @BindView(R.id.selected_one_icon)
    ImageView mSelectedOneIcon;
    @BindView(R.id.select_two)
    TextView mSelectTwoText;
    @BindView(R.id.selected_two_icon)
    ImageView mSelectedTwoIcon;


    private String flag;  //
    private int currentselect = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_info_select;
    }

    @Override
    protected void initLayout() {
        mLeftBack.setVisibility(View.GONE);
        mRightNext.setVisibility(View.GONE);
        flag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        if(Constant.STAFF_SEX_SELECTED.equals(flag)) {
            currentselect = getIntent().getIntExtra("SEX", 1);
            mTitle.setText(getResources().getString(R.string.sex_selected));
            mSelectOneText.setText(getResources().getString(R.string.male_));
            mSelectTwoText.setText(getResources().getString(R.string.female_));
            if(currentselect == 1) {
                mSelectedOneIcon.setVisibility(View.VISIBLE);
                mSelectedTwoIcon.setVisibility(View.GONE);
            } else {
                mSelectedOneIcon.setVisibility(View.GONE);
                mSelectedTwoIcon.setVisibility(View.VISIBLE);
            }
        } else if(Constant.STAFF_STATUS_SELECTED.equals(flag)) {
            currentselect = getIntent().getIntExtra("STATUS", 1);
            mTitle.setText(getResources().getString(R.string.status_selected));
            mSelectOneText.setText(getResources().getString(R.string.start_using));
            mSelectTwoText.setText(getResources().getString(R.string.stop_using));
            if(currentselect == 1) {
                mSelectedOneIcon.setVisibility(View.VISIBLE);
                mSelectedTwoIcon.setVisibility(View.GONE);
            } else {
                mSelectedOneIcon.setVisibility(View.GONE);
                mSelectedTwoIcon.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.select_one_rl, R.id.select_two_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_one_rl:
                if(mSelectedOneIcon.getVisibility() == View.GONE) {
                    mSelectedOneIcon.setVisibility(View.VISIBLE);
                }
                if(mSelectedTwoIcon.getVisibility() == View.VISIBLE) {
                    mSelectedTwoIcon.setVisibility(View.GONE);
                }
                currentselect = 1;
                selectedResult(currentselect);
                break;
            case R.id.select_two_rl:
                if(mSelectedOneIcon.getVisibility() == View.VISIBLE) {
                    mSelectedOneIcon.setVisibility(View.GONE);
                }
                if(mSelectedTwoIcon.getVisibility() == View.GONE) {
                    mSelectedTwoIcon.setVisibility(View.VISIBLE);
                }
                currentselect = 2;
                selectedResult(currentselect);
                break;
        }
    }

    private void selectedResult(int select) {
        Intent intent = new Intent();
        intent.putExtra(Constant.SELECTED_RESULT, select);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        selectedResult(currentselect);
    }
}
