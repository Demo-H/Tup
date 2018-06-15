package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.android.dhunter.common.utils.ScreenUtil;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.DevMemberRequest;
import com.tupperware.huishengyi.entity.member.DevMemberSelect;
import com.tupperware.huishengyi.entity.member.TasteBean;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.tupperware.huishengyi.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/1.
 */

public class DevTasteSelectActivity extends BaseActivity {

    private static final String TAG = "DevTasteSelectActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private TasteAdapter mAdapter;

    public static final int []icon_RES = new int[]{R.mipmap.food_1,R.mipmap.food_2,R.mipmap.food_3,R.mipmap.food_4,
            R.mipmap.food_5,R.mipmap.food_6,R.mipmap.food_7,R.mipmap.food_8};
    public static final String []icon_RES_des = new String[]{"无辣不欢","清淡","酸甜",
            "中餐胃", "西餐粉", "肉食动物", "素食主义", "甜品控"};
    private DevMemberSelect mDevMember;
    private List<DevMemberRequest.TagsCode> tasteTagsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_taste_select);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtil.dip2px(this,3)));
        mAdapter = new TasteAdapter(R.layout.item_dev_taste_recycleview, getDatas());
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setAdapter(new BaseQuickAdapter<TasteBean, BaseViewHolder>(R.layout.item_dev_taste_recycleview, getDatas()) {
//            @Override
//            protected void convert(BaseViewHolder helper, final TasteBean item, int position) {
////                helper.setText(R.id.name, item.name);
//                final TextView textView = helper.getView(R.id.name);
//                textView.setText(item.name);
//                final SimpleDraweeView simpleDraweeView = helper.getView(R.id.image);
//                simpleDraweeView.setActualImageResource(item.image);
//
//                simpleDraweeView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        RoundingParams roundingParams = simpleDraweeView.getHierarchy().getRoundingParams();
//                        int color = roundingParams.getBorderColor();
//                        if(item.isSelected) {
//                            item.setSelected(false);
//                            roundingParams.setBorderColor(0x00000000);
//                            simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
//                            textView.setSelected(false);
//                        } else {
//                            item.setSelected(true);
//                            roundingParams.setBorderColor(0xffff7000);
//                            simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
//                            textView.setSelected(true);
//                        }
//                    }
//                });
//            }
//        });
    }

    @Override
    protected void initLayoutData() {
        Bundle bundle = this.getIntent().getExtras();
        mDevMember = (DevMemberSelect)bundle.getSerializable(Constant.DEV_MEMBER_DATA);
    }

    private List<TasteBean> getDatas() {
        List<TasteBean> datas = new ArrayList<TasteBean>();
        TasteBean tasteBean = null;
        for(int i = 0; i < icon_RES.length; i++) {
            tasteBean = new TasteBean();
            tasteBean.image = icon_RES[i];
            tasteBean.name = icon_RES_des[i];
            tasteBean.isSelected = false;
            datas.add(tasteBean);
        }
        return datas;
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                if(getSelectItem()) {
                    Intent intent = new Intent(this, DevPurifierSelectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DEV_MEMBER_DATA, mDevMember);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    toast("请选择后再进行下一步");
                }
                break;
        }
    }

    private boolean getSelectItem() {
        if(tasteTagsList != null) {
            tasteTagsList.clear();
        } else {
            tasteTagsList = new ArrayList<>();
        }
        if(mAdapter.getData() == null) {
            return false;
        }
        int size = mAdapter.getData().size();
        DevMemberRequest.TagsCode tags;
        if (mAdapter.getData().get(0).isSelected) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TASTE_1);
            tasteTagsList.add(tags);
        }
        if (mAdapter.getData().get(1).isSelected) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TASTE_2);
            tasteTagsList.add(tags);
        }
        if (mAdapter.getData().get(2).isSelected) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TASTE_3);
            tasteTagsList.add(tags);
        }
        if (mAdapter.getData().get(3).isSelected) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TASTE_4);
            tasteTagsList.add(tags);
        }
        if (mAdapter.getData().get(4).isSelected) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TASTE_5);
            tasteTagsList.add(tags);
        }
        if (mAdapter.getData().get(5).isSelected) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TASTE_6);
            tasteTagsList.add(tags);
        }
        if (mAdapter.getData().get(6).isSelected) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TASTE_7);
            tasteTagsList.add(tags);
        }
        if (mAdapter.getData().get(7).isSelected) {
            tags = new DevMemberRequest.TagsCode();
            tags.setTagCode(Constant.TAG_CODE_TASTE_8);
            tasteTagsList.add(tags);
        }
        if(tasteTagsList.size() == 0) {
            return false;
        } else {
            mDevMember.setTasteTags(tasteTagsList);
            return true;
        }
    }


    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }

    public class TasteAdapter extends BaseQuickAdapter<TasteBean, BaseViewHolder> {

        public TasteAdapter(int layoutResId, List<TasteBean> mData) {
            super(layoutResId, mData);
        }

        @Override
        protected void convert(BaseViewHolder helper, final TasteBean item, int position) {
            final TextView textView = helper.getView(R.id.name);
            textView.setText(item.name);
            final SimpleDraweeView simpleDraweeView = helper.getView(R.id.image);
            simpleDraweeView.setActualImageResource(item.image);

            simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoundingParams roundingParams = simpleDraweeView.getHierarchy().getRoundingParams();
                    int color = roundingParams.getBorderColor();
                    if(item.isSelected) {
                        item.setSelected(false);
                        roundingParams.setBorderColor(0x00000000);
                        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
                        textView.setSelected(false);
                    } else {
                        item.setSelected(true);
                        roundingParams.setBorderColor(0xffff7000);
                        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
                        textView.setSelected(true);
                    }
                }
            });
        }
    }

}
