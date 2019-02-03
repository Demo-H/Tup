package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.StaffManagerBean;
import com.tupperware.biz.entity.StaffRequest;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.component.DaggerAddNewStaffActivityComponent;
import com.tupperware.biz.ui.contract.AddNewStaffContract;
import com.tupperware.biz.ui.fragment.DatePickerFragment;
import com.tupperware.biz.ui.module.AddNewStaffPresenterModule;
import com.tupperware.biz.ui.presenter.AddNewStaffPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.DesUtils;
import com.tupperware.biz.widget.SimpleDialog;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/22.
 */

public class AddNewStaffActivity extends BaseActivity implements AddNewStaffContract.View {

    private static final String TAG = "AddNewStaffActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;

    @BindView(R.id.staff_no_rl)
    RelativeLayout mStaffNoRl;
    @BindView(R.id.staff_no)
    TextView mStaffNo;
    @BindView(R.id.staff_name)
    EditText mStaffName;
    @BindView(R.id.staff_tel)
    EditText mStaffTel;
    @BindView(R.id.staff_sex_rl)
    RelativeLayout mSatffSexRl;
    @BindView(R.id.staff_sex)
    TextView mStaffSex;
    @BindView(R.id.staff_hiredate_rl)
    RelativeLayout mStaffHiredateRl;
    @BindView(R.id.staff_hiredate)
    TextView mStaffHiredate;
    @BindView(R.id.staff_password)
    EditText mStaffPassword;
    @BindView(R.id.staff_status_rl)
    RelativeLayout mStaffStatusRl;
    @BindView(R.id.staff_status)
    TextView mStaffStatus;

    private String flag;  // 判断是新增还是修改
    private StaffManagerBean.StaffContentBean staffInfo;
    public static final int SEX_CODE = 1;
    public static final int STATUS_CODE = 2;
    private int currentselect;
    @Inject
    AddNewStaffPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_staff;
    }

    @Override
    protected void initLayout() {
        DaggerAddNewStaffActivityComponent.builder()
                .appComponent(getAppComponent())
                .addNewStaffPresenterModule(new AddNewStaffPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        flag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        mRightText.setText(getResources().getString(R.string.submit));
        //给editText设置filter
        mStaffName.setFilters(new InputFilter[]{inputFilter/*,new InputFilter.LengthFilter(10)*/});
        if(Constant.NEW_ADD.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.new_add_staff));
            mStaffNoRl.setVisibility(View.GONE);
        } else if(Constant.MODIFIED.equals(flag)) {
            mTitle.setText(getResources().getString(R.string.staff_info_modified));
            staffInfo = (StaffManagerBean.StaffContentBean) getIntent().getSerializableExtra(Constant.STAFF_INFO);
            if(staffInfo != null) {
                mStaffNo.setText(staffInfo.getStoreEmployeeCode());
                mStaffName.setText(staffInfo.getStoreEmployeeName());
                mStaffTel.setText(staffInfo.getStoreEmployeeMobile());
                if(staffInfo.getStoreEmployeeGender() == 0) {
                    mStaffSex.setText(getResources().getString(R.string.male_));
                } else if(staffInfo.getStoreEmployeeGender() == 1) {
                    mStaffSex.setText(getResources().getString(R.string.female_));
                } else {
                    mStaffSex.setText(getResources().getString(R.string.unkonw));
                }
                mStaffHiredate.setText(DateFormatter.timeSecondToDateDay(staffInfo.getStoreEmployeeEntryTime()));
                try {
                    String psw = DesUtils.decrypt(staffInfo.getStoreEmployeePassword());
                    mStaffPassword.setText(psw);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(staffInfo.getStoreEmployeeStatus() == 0) {
                    mStaffStatus.setText(getResources().getString(R.string.start_using));
                } else {
                    mStaffStatus.setText(getResources().getString(R.string.stop_using));
                }
            }
        }
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.staff_sex_rl, R.id.staff_hiredate_rl, R.id.staff_status_rl, R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.staff_sex_rl:
                String gender = mStaffSex.getText().toString().trim();
                if(gender.equals(getResources().getString(R.string.female_))) {
                    currentselect = 2;
                } else {
                    currentselect = 1;
                }
                Intent intent = new Intent(this, StaffInfoSelectActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.STAFF_SEX_SELECTED);
                intent.putExtra("SEX", currentselect);
                startActivityForResult(intent, SEX_CODE);
                break;
            case R.id.staff_hiredate_rl:
                chooseDate();
                break;
            case R.id.staff_status_rl:
                String status = mStaffStatus.getText().toString().trim();
                if(status.equals(getResources().getString(R.string.stop_using))) {
                    currentselect = 2;
                } else {
                    currentselect = 1;
                }
                Intent i = new Intent(this, StaffInfoSelectActivity.class);
                i.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.STAFF_STATUS_SELECTED);
                i.putExtra("STATUS", currentselect);
                startActivityForResult(i, STATUS_CODE);
                break;
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                submit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int selected = data.getIntExtra(Constant.SELECTED_RESULT, 0);
        switch (requestCode) {
            case SEX_CODE:
                if(selected == 1) {
                    mStaffSex.setText(getResources().getString(R.string.male_));
                } else if(selected == 2){
                    mStaffSex.setText(getResources().getString(R.string.female_));
                }
                break;
            case STATUS_CODE:
                if(selected == 1) {
                    mStaffStatus.setText(getResources().getString(R.string.start_using));
                } else if(selected == 2) {
                    mStaffStatus.setText(getResources().getString(R.string.stop_using));
                }
                break;
        }
//        super.onActivityResult(requestCode, resultCode, data);
    }

    private void chooseDate() {
//        Intent intent = new Intent(this, DatePickerActivity.class);
//        startActivity(intent);
        FragmentManager manager = getSupportFragmentManager();
        DatePickerFragment dialog =  DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
        dialog.show(manager, Constant.DIALOG_DATE);
        dialog.setOnDialogListener(new DatePickerFragment.OnDialogListener() {
            @Override
            public void onDialogClick(Date date) {
                mStaffHiredate.setText(new DateFormatter().DateFormat(date));
            }
        });
    }

    private void submit() {
        StaffRequest reqData = new StaffRequest();
        String name = mStaffName.getText().toString().trim();
        String mobile = mStaffTel.getText().toString().trim();
        String joindate = mStaffHiredate.getText().toString().trim();
        String psw = mStaffPassword.getText().toString().trim();
        String gender = mStaffSex.getText().toString().trim();
        String status = mStaffStatus.getText().toString().trim();
        if(name == null || name.isEmpty()) {
            toast("请输入姓名");
            return;
        } else if(name.length() > 10) {
            toast("输入姓名不可以超出10位");
            return;
        }
        if(mobile == null || mobile.isEmpty()) {
            toast("请输入手机号码");
            return;
        }
        if(psw == null || psw.isEmpty()) {
            toast("请输入密码");
            return;
        } else if(psw.length() != 6) {
            toast("输入密码必须为6位");
            return;
        }
        if(joindate.equals(getResources().getString(R.string.select))) {
            toast("请输入入职时间");
            return;
        }

        reqData.setStoreEmployeeName(name);
        reqData.setStoreEmployeeMobile(mobile);
        reqData.setStoreEmployeePassword(psw);
        reqData.setStoreEmployeeEntryTime(DateFormatter.date2TimeStamp(joindate));

        if(gender.equals(getResources().getString(R.string.male_))) {
            reqData.setStoreEmployeeGender(0);
        } else {
            reqData.setStoreEmployeeGender(1);
        }
        if(status.equals(getResources().getString(R.string.start_using))) {
            reqData.setStoreEmployeeStatus(0);
        } else {
            reqData.setStoreEmployeeStatus(1);
        }
        if(Constant.NEW_ADD.equals(flag)) {
            showDialog();
            mPresenter.addStaffData(reqData);
        } else if (Constant.MODIFIED.equals(flag)) {
            long employeeId = staffInfo.getStoreEmployeeId();
            reqData.setStoreEmployeeId(employeeId);
            showDialog();
            mPresenter.updateStaffData(reqData);
        }
    }

    public void backandFinish() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void addStaffDatasuccess() {
        backandFinish();
    }

    @Override
    public void updateStaffDatasuccess() {
        backandFinish();
    }

    InputFilter inputFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                toast("不支持输入表情");
                return "";
            }
            return null;
        }
    };

    @Override
    public void onBackPressed() {
        SimpleDialog dialog = new SimpleDialog(this);
        dialog.setmessage("该资料未保存是否确定离开？");
        dialog.setcancelText("取消");
        dialog.setsureText("确认");
        dialog.setCancelOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
        dialog.build().show();

    }
}
