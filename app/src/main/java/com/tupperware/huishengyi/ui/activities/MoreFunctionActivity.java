package com.tupperware.huishengyi.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.view.GridMenu;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/11.
 */

public class MoreFunctionActivity extends BaseActivity /*implements Tupperware.TupperwareErrorListener, Tupperware.TupperwareListener
        TupVolley.TupVolleyListener, TupVolley.TupVolleyErrorListener */ {
    private static final String TAG = "MoreFunctionActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.menu_1)
    GridMenu mGridMenu1;
    @BindView(R.id.menu_2)
    GridMenu mGridMenu2;
    @BindView(R.id.menu_3)
    GridMenu mGridMenu3;
    @BindView(R.id.menu_4)
    GridMenu mGridMenu4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_function;
    }

    @Override
    protected void initLayout() {
        initToolBar();
        mGridMenu1.setAttr(R.mipmap.prod_ic, getResources().getString(R.string.key_product_upload));
        mGridMenu2.setAttr(R.mipmap.week_ic, getResources().getString(R.string.week_update));
        mGridMenu3.setAttr(R.mipmap.feedback_ic, getResources().getString(R.string.sale_feedback));
        mGridMenu4.setAttr(R.mipmap.pure_ic, getResources().getString(R.string.pure_after_sale_follow));
    }

    @Override
    protected void requestData() {

    }

    private void initToolBar() {
        mRightNext.setVisibility(View.GONE);
        mTitle.setText(getResources().getString(R.string.more));
    }

    @OnClick({R.id.left_back, R.id.menu_1, R.id.menu_2, R.id.menu_3, R.id.menu_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.menu_1:
//                jumptoActivity(this, KeyProductActivity.class); //重点产品上报
//                asyncetuGUrl();
                break;
            case R.id.menu_2:
                Toast.makeText(view.getContext(),"周报",Toast.LENGTH_SHORT).show(); //周报上报
                break;
            case R.id.menu_3:
                jumptoActivity(this, FeedBackActivity.class);   //售后反馈
                break;
            case R.id.menu_4:
                //净水器售后跟进
                break;
        }
    }

    private void jumptoActivity(Context context, Class<?> _cls ) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ACTIVITY_CREATE_FROM,Constant.HOME);
        context.startActivity(intent);
    }

    /**
     * 获取重点产品销售的url
     */
    /*private void asyncetuGUrl() {
//        LoginInfo.Extra login_extra = new LoginInfo.Extra();
        showDialog();
//        String json = (String) mSharePreDate.getParam(GlobalConfig.KEY_DATA_LOGIN_INFO, "");
        String json = mDataManager.getSPData(GlobalConfig.KEY_DATA_LOGIN_INFO);
        if(json == null) {
            return;
        }
        try {
            JSONObject login_info = new JSONObject(json);
            JSONObject login_extra = login_info.getJSONObject("extra");
            String storeid = "", mStoreInfo = "", mStoreFromOrgan = "", url = "";
            if(login_extra != null) {
                Iterator<String> iterator = login_extra.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if(key.equals("storeId")) {
                        storeid = login_extra.getString(key);
                    } else if(key.equals("store_info")) {
                        mStoreInfo = login_extra.getString(key);
                    } else if(key.equals("store_from_organ")) {
                        mStoreFromOrgan = login_extra.getString(key);
                    }
                }
            }
            long timestamp = System.currentTimeMillis();
            String token = StringUtils.MD5(StringUtils.MD5(storeid + timestamp) + "tupperware");
            Uri uri = Uri.parse(ServerURL.IMPORTANT_PRODUCT_SALE_TEST);
            uri.buildUpon();
            Uri.Builder builder = uri.buildUpon();
            builder.appendQueryParameter("storeid", storeid);
            builder.appendQueryParameter("timestamp", timestamp + "");
            builder.appendQueryParameter("token", token);
            url = builder.build().toString();

            Map<String, String> params = new HashMap<>();
            params.put("platform_referer", "Android");
            params.put("store_info", mStoreInfo);
            params.put("store_from_organ", mStoreFromOrgan);
            *//**
             * 添加other属性来将store_info和store_from_organ进行转码为数组类型
             * 1.0版本留下的坑，适配服务器必须加入该参数属性
             *//*
            params.put("other", "other");
            mTupVolley.post(Constants.REQUEST_CODE_IMPORT_SALE, url, params, this, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

}
