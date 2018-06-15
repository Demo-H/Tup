package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.flowlayout.FlowLayout;
import com.android.dhunter.common.widget.flowlayout.TagAdapter;
import com.android.dhunter.common.widget.flowlayout.TagFlowLayout;
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
 * Created by dhunter on 2018/3/29.
 */

public class WaterSafeCheckActivity extends BaseActivity {

    private static final String TAG = "WaterSafeCheckActivity";

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

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.tel_num)
    TextView mTelNum;
    @BindView(R.id.call_contacts)
    TextView mCallContact;
    @BindView(R.id.addr)
    TextView mAddr;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mTagFlowLayout;
    @BindView(R.id.full_choose)
    CheckBox mFullChoose;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private String fromTag;
    private WaterSafeCheckAdapter mAdapter;
    private ArrayList<DataEntry> itemBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_safe_check);
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
        mTagFlowLayout.setAdapter(new TagAdapter<String>(getStringArray(R.array.member_flag)) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.textview_tag_select,
                        mTagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
//        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
        mAdapter = new WaterSafeCheckAdapter(R.layout.item_water_safe_check_recycleview, itemBeanList);
        mRecyclerView.setAdapter(mAdapter);

        /*mFullChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
                    int count = 0;
                    if (isChecked) {
                        isChange = false;
                    }
                    for (int i = 0, p = itemBeanList.size(); i < p; i++) {
                        if (isChecked) {
                            map.put(i, true);
                            count++;
                        } else {
                            if (!isChange) {
                                map.put(i, false);
                                count = 0;
                            } else {
                                map = mAdapter.getMap();
                                count = map.size();
                            }
                        }
                    }
                    mAdapter.setMap(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

    }

    @Override
    protected void initLayoutData() {
        DataEntry itemBean = null;
        String[] arrayString = getStringArray(R.array.water_safe_check);
        for(int i = 0; i < arrayString.length; i++) {
            itemBean = new DataEntry();
            itemBean.title = arrayString[i];
            itemBeanList.add(itemBean);
        }

    }

    @OnClick({R.id.left_back, R.id.next, R.id.call_contacts, R.id.full_choose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                toast("提交完成");
                ActivityManager.getInstance().exit();
                break;
            case R.id.call_contacts:
                try {
                    String telNum = mTelNum.getText().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telNum));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {

                }
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
