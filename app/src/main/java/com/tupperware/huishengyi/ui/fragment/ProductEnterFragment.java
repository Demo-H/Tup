package com.tupperware.huishengyi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.popupWindow.Config;
import com.android.dhunter.common.widget.popupWindow.EasyTopPopup;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.ProductEnterAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.ProductEnterBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.component.DaggerProductEnterFragmentComponent;
import com.tupperware.huishengyi.ui.module.ProductEnterPresenterModule;
import com.tupperware.huishengyi.ui.activities.ScanCouponActivity;
import com.tupperware.huishengyi.ui.contract.ProductEnterContract;
import com.tupperware.huishengyi.ui.presenter.ProductEnterPresenter;
import com.tupperware.huishengyi.utils.DateFormatter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tupperware.huishengyi.config.Constant.REQUEST_DATE;

/**
 * Created by dhunter on 2018/3/23.
 * 需求变更，弃用
 */

public class ProductEnterFragment  extends BaseFragment implements ProductEnterContract.View {

    private static final String TAG = "ProductEnterFragment";

    @BindView(R.id.left_back)
    ImageView mLeftBack;
    @BindView(R.id.right_icon)
    ImageView mRightDate;
    @BindView(R.id.search_product)
    TextView mSearch;
    @BindView(R.id.search_scan)
    ImageView mScanEnter;
    @BindView(R.id.date)
    TextView mDateText;
    @BindView(R.id.inventory_sort)
    TextView mInventorySort;
    @BindView(R.id.product_filter_ll)
    LinearLayout mProdFilterll;
    @BindView(R.id.product_filter)
    TextView mProdFilter;
    @BindView(R.id.product_enter_content_view)
    RecyclerView mRecyclerView;

    @Inject
    ProductEnterPresenter mPresenter;

    private ProductEnterAdapter mAdapter;
    private View rootView;
    private int sort = 1; //1 为升，0 为降
    private EasyTopPopup mPopup;
    private String mFilterString;

    public static ProductEnterFragment newInstance() {
        ProductEnterFragment fragment = new ProductEnterFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
        requestData();
        return rootView;
    }

    @Override
    public void initLayout() {
        DaggerProductEnterFragmentComponent.builder()
                .appComponent(getAppComponent())
                .productEnterPresenterModule(new ProductEnterPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_SWITCH_STORE));
        mAdapter = new ProductEnterAdapter(R.layout.item_product_enter_recyclerview);
        mAdapter.setEnableLoadMore(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {
        mPresenter.getProductEnterData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_enter;
    }

    @Override
    public void setProductEnterData(ProductEnterBean productEnterBean) {
        mAdapter.addData(productEnterBean.content);
    }

    @OnClick({R.id.left_back, R.id.right_icon, R.id.search_product, R.id.search_scan, R.id.inventory_sort, R.id.product_filter_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                getActivity().finish();
                break;
            case R.id.right_icon:
                chooseDate();
                break;
            case R.id.search_product:
                toast("搜索");
                break;
            case R.id.search_scan:
                Intent intent = new Intent(getActivity(), ScanCouponActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.ENTER_PRODUCT);
                startActivity(intent);
                break;
            case R.id.inventory_sort:
                switchInventorySort();
                break;
            case R.id.product_filter_ll:
                showFilterDialog(mProdFilterll);
                break;

        }
    }

    private void setDefaultInventorySort() {
        sort = 1;
        mInventorySort.setText(getResources().getString(R.string.inventory_up));
        Drawable nav_up = getResources().getDrawable(R.mipmap.prd_arrow_up_ic);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        mInventorySort.setCompoundDrawables(null, null, nav_up, null);
    }

    private void switchInventorySort() {
        if(sort == 0) {
            mInventorySort.setText(getResources().getString(R.string.inventory_up));
            Drawable nav_up = getResources().getDrawable(R.mipmap.prd_arrow_up_ic);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            mInventorySort.setCompoundDrawables(null, null, nav_up, null);
            sort = 1;
        } else if(sort == 1) {
            mInventorySort.setText(getResources().getString(R.string.inventory_down));
            Drawable nav_up = getResources().getDrawable(R.mipmap.prd_arrow_down_ic);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            mInventorySort.setCompoundDrawables(null, null, nav_up, null);
            sort = 0;
        }
    }

    private void showFilterDialog(View view) {
        mPopup = new EasyTopPopup(mActivity, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        selection(position);
                mFilterString = (String) parent.getItemAtPosition(position);
//                mProdFilter.setText(getItemsName().get(position));
            }
        }, getItemsName(), Config.RIGHT);
        if(mFilterString == null) {
            mFilterString = getItemsName().get(0);
        }
        mPopup.setCurrentSelected(mFilterString);
        mPopup.show(view);
    }

    /**
     * 设置弹窗内容
     *
     * @return
     */
    private ArrayList<String> getItemsName() {
        ArrayList<String> items = new ArrayList<String>();
        items.add("TUP系列");
        items.add("净水器系列");
        items.add("收纳盒系列");
        items.add("锅具系列");
        return items;
    }

    private void chooseDate() {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog =  DatePickerFragment
                .newInstance(
                        new DateFormatter().stringToDate(mDateText.getText().toString().trim()));
        dialog.setTargetFragment(ProductEnterFragment.this, REQUEST_DATE);
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

            mDateText.setText(new DateFormatter().DateFormat(date));

        }
    }

}
