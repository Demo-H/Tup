package com.tupperware.huishengyi.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dhunter.common.baserecycleview.BaseMultiItemQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.android.dhunter.common.widget.mzBannerView.MZBannerView;
import com.android.dhunter.common.widget.mzBannerView.holder.MZHolderCreator;
import com.android.dhunter.common.widget.popupWindow.Config;
import com.android.dhunter.common.widget.popupWindow.EasyTopPopup;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseApplication;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.home.HomeIndexBean;
import com.tupperware.huishengyi.listener.PositionChangedListener;
import com.tupperware.huishengyi.ui.activities.BrowserActivity;
import com.tupperware.huishengyi.ui.activities.PersonalQrActivity;
import com.tupperware.huishengyi.ui.activities.RegisterActivity;
import com.tupperware.huishengyi.ui.activities.SaleEnterActivity;
import com.tupperware.huishengyi.ui.activities.ScanCouponActivity;
import com.tupperware.huishengyi.ui.activities.WebViewActivity;
import com.tupperware.huishengyi.view.BannerPaddingViewHolder;
import com.tupperware.huishengyi.view.GridMenu;
import com.tupperware.huishengyi.view.TargetMeViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dhunter on 2018/3/11.
 */

public class HomeMultipleRecycleAdapter extends BaseMultiItemQuickAdapter<HomeIndexBean.ItemInfoListBean, BaseViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener{


    private int maxHasLoadPosition;
    private Activity mActivity;
//    public static final int []RES = new int[]{R.mipmap.banner_1_img,R.mipmap.banner_2_img,R.mipmap.banner_3_img};
//    public static final String []RES_Title = new String[]{"新品上市，新年的钟声随雪花飘荡，和草莓雪人...",
//            "新品上市，雪花飘荡...","新年的钟声随雪花飘荡，和草莓雪人..."};
//    public static final int []rcmd_RES = new int[]{R.mipmap.rcmd_1_img,R.mipmap.rcmd_2_img,R.mipmap.rcmd_3_img};
//    public static final String []rcmd_RES_des = new String[]{"家庭水质检测","给冰箱做个“SPA”","帅锅鉴定宝典"};
//    public static final int []icon_RES = new int[]{R.mipmap.vistor_ic,R.mipmap.sale_ic,R.mipmap.qrcode_ic,R.mipmap.wemall_ic,
//            R.mipmap.drink_ic,R.mipmap.water_ic,R.mipmap.customize_ic,R.mipmap.more_ic};
//    public static final String []icon_RES_des = new String[]{"发展新会员","销售录入","券码核销",
//            "我的微店", "饮水安全\n调查", "好水嘉年华报名", "私人订制\n预约", "更多"};
public static final int []icon_RES = new int[]{R.mipmap.vistor_ic,R.mipmap.sale_ic,R.mipmap.qrcode_ic,R.mipmap.wemall_ic,
        R.mipmap.prod_ic};
    public static final String []icon_RES_des = new String[]{"发展新会员","销售录入","券码核销",
            "我的微店", "重点热卖"};

    /**
     * 当前position监听
     */
    private PositionChangedListener listener;

    private EasyTopPopup mPopup;
    private LinearLayout mChooseNameLL; //选择门店
    private TextView mStoreName; //选择门店名
    private String mSelectStoreName;

    public void setListener(PositionChangedListener listener) {
        this.listener = listener;
    }

    public void resetMaxHasLoadPosition() {
        maxHasLoadPosition = 0;
    }

    public HomeMultipleRecycleAdapter(Activity activity) {
        setSpanSizeLookup(this);
        this.mActivity = activity;
        addItemType(Constant.TYPE_TOP_BANNER, R.layout.homerecycle_item_top_banner);
        addItemType(Constant.TYPE_PRECISE_RECOMMENDATION, R.layout.homerecycle_item_precise_recommendation);
        addItemType(Constant.TYPE_ICON_LIST, R.layout.homerecycle_item_icon_list);
//        addItemType(Constant.TYPE_TARGET_ME, R.layout.homerecycle_item_target_me);
        addItemType(Constant.TYPE_MARKET_INFO, R.layout.homerecycle_item_market_info);
    }


    /**
     * 数据绑定未进行详细的数据验证，咱使用本地数据
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     * @param position
     */
    @Override
    protected void convert(BaseViewHolder helper, HomeIndexBean.ItemInfoListBean item, int position) {
        if (listener != null) {
            listener.currentPosition(position);
        }
        if (maxHasLoadPosition < position) {
            maxHasLoadPosition = position;
        }

//        bindTopBannerData(helper, item, position);
//        bindPreciseRecommendationData(helper, item, position);
//        bindIconListData(helper, item, position);
//        bindTargetMeData(helper, item, position);
//        bindMarketInfoData(helper, item, position);
        if ("topBanner".equals(item.itemType) && maxHasLoadPosition <= position) {
            bindTopBannerData(helper, item, position);
        } else if ("preciseRecommendation".equals(item.itemType)) {
            bindPreciseRecommendationData(helper, item, position);
        } else if ("iconList".equals(item.itemType) && maxHasLoadPosition <= position) {
            bindIconListData(helper, item, position);
//        } else if ("targetMe".equals(item.itemType) && maxHasLoadPosition <= position) {
//            bindTargetMeData(helper, item, position);
        } else if ("marketInfo".equals(item.itemType) && maxHasLoadPosition <= position) {
            bindMarketInfoData(helper, item, position);
        } else if ("type_Title".equals(item.itemType)) {
            bindTypeTitleData(helper, item, position);
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return mData.get(position).getSpanSize();
    }


    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.list_1:
                Toast.makeText(view.getContext(),"444",Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    /**
     * 绑定banner数据
     *
     * @param helper
     * @param item
     * @param position
     */
    private void bindTopBannerData(BaseViewHolder helper, final HomeIndexBean.ItemInfoListBean item, int position) {

        MZBannerView banner = helper.getView(R.id.banner);
        banner.setIndicatorVisible(false);
//        banner.setIndicatorRes(R.color.colorAccent,R.color.colorPrimary);
        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), BrowserActivity.class);
                String url = item.itemContentList.get(position).link;
                intent.putExtra(Constant.OPEN_URL,url);
                intent.putExtra(Constant.URL_TITLE,"今日头条");
                view.getContext().startActivity(intent);
//                Toast.makeText(view.getContext(), item.itemContentList.get(position).link, Toast.LENGTH_SHORT).show();
            }
        });
        if(item.itemContentList.size() > 0) {
            banner.setPages(item.itemContentList, new MZHolderCreator<BannerPaddingViewHolder>() {
                @Override
                public BannerPaddingViewHolder createViewHolder() {
                    return new BannerPaddingViewHolder();
                }
            });
        }
    }

    /**
     * 绑定今日推荐数据
     */
    private void bindPreciseRecommendationData(final BaseViewHolder helper, HomeIndexBean.ItemInfoListBean item, int position) {
        if (item.itemContentList == null || item.itemContentList.size() <= 0) return;
        /**  添加使用本地数据 **/
//        item.article.clear();
//        HomeIndex.ItemInfoListBean.ItemContentListBean itemBean = null;
//        for(int i=0; i<rcmd_RES.length; i++) {
//            itemBean = new HomeIndex.ItemInfoListBean.ItemContentListBean();
//            itemBean.image = rcmd_RES[i];
//            itemBean.itemTitle = rcmd_RES_des[i];
//            item.itemContentList.add(itemBean);
//        }
        /**  添加使用本地数据结束 **/
        RecyclerView recyclerView = helper.getView(R.id.precise_recommendation_content_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        PreciseRecommendationAdapter adapter = new PreciseRecommendationAdapter(R.layout.homerecycle_item_precise_recommendation, item.itemContentList);
//        PreciseRecommendationAdapter adapter = new PreciseRecommendationAdapter(R.layout.homerecycle_item_precise_recommendation, initPreciseRcmData());
        recyclerView.setAdapter(adapter);
    }

    /**
     * 绑定icon数据
     * @param helper
     * @param item
     * @param position
     */

    private void bindIconListData(BaseViewHolder helper, HomeIndexBean.ItemInfoListBean item, int position) {
//        if (item.itemContentList.size() == 10) {
        /**  添加使用本地数据 **/
        item.itemContentList.clear();
        HomeIndexBean.ItemInfoListBean.ItemContentListBean itemBean = null;
        for(int i=0; i<icon_RES.length; i++) {
            itemBean = new HomeIndexBean.ItemInfoListBean.ItemContentListBean();
            itemBean.image = icon_RES[i];
            itemBean.title = icon_RES_des[i];
            item.itemContentList.add(itemBean);
        }
        /**  添加使用本地数据结束 **/
        List<GridMenu> gridMenus=new ArrayList<>();
        gridMenus.add((GridMenu)helper.getView(R.id.list_1));
        gridMenus.add((GridMenu)helper.getView(R.id.list_2));
        gridMenus.add((GridMenu)helper.getView(R.id.list_3));
        gridMenus.add((GridMenu)helper.getView(R.id.list_4));
        gridMenus.add((GridMenu)helper.getView(R.id.list_5));
//        gridMenus.add((GridMenu)helper.getView(R.id.list_6));
//        gridMenus.add((GridMenu)helper.getView(R.id.list_7));
//        gridMenus.add((GridMenu)helper.getView(R.id.list_8));

        /** 图片文字初始化 **/
        for(int i = 0; i < gridMenus.size(); i++) {
            gridMenus.get(i).setAttr(icon_RES[i],icon_RES_des[i]);
            gridMenus.get(i).setOnClickListener(this);
        }

    }

    private void bindTargetMeData(BaseViewHolder helper, HomeIndexBean.ItemInfoListBean item, int position) {

        mChooseNameLL = helper.getView(R.id.choose_dian_name_ll);
        mStoreName = helper.getView(R.id.dian_name);

        mChooseNameLL.setOnClickListener(this);

        List<Integer> list = new ArrayList<>();
        for(int i=0; i<3; i++){
            list.add(i);
        }
        MZBannerView banner = helper.getView(R.id.target_banner);
        banner.setIndicatorVisible(true);
        banner.setIndicatorRes(R.drawable.indicator_normal_item,R.drawable.indicator_selected_item);
        banner.setPages(list, new MZHolderCreator<TargetMeViewHolder>() {
            @Override
            public TargetMeViewHolder createViewHolder() {
                return new TargetMeViewHolder();
            }
        });

    }

    private void  bindMarketInfoData(BaseViewHolder helper, HomeIndexBean.ItemInfoListBean item, int position) {

        /**  添加使用本地数据 **/
//        item.itemContentList.clear();
//        HomeIndexBean.ItemInfoListBean.ItemContentListBean itemBean = null;
//        String []market_info_title = new String[]{"怎么才能让客户快速交定金，快速下单？","如何更好的管理询盘，拿到更多的下单？"};
//
//        for(int i=0; i<2; i++) {
//            itemBean = new HomeIndexBean.ItemInfoListBean.ItemContentListBean();
//            itemBean.image = R.mipmap.info_img;
//            itemBean.title = market_info_title[i];
//            itemBean.authorName = "子墨麻麻の";
//            itemBean.pageView = 4374;
//            item.itemContentList.add(itemBean);
//        }
        /**  添加使用本地数据结束 **/

        RecyclerView recyclerView = helper.getView(R.id.market_info_content_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//        ZixunHomeAdapter adapter = new ZixunHomeAdapter(R.layout.homerecycle_item_market_info, item.itemContentList);
        ZixunHomeAdapter adapter = new ZixunHomeAdapter(R.layout.homerecycle_item_market_info, item.itemContentList);
        recyclerView.setAdapter(adapter);
    }


    private void  bindTypeTitleData(BaseViewHolder helper, HomeIndexBean.ItemInfoListBean item, int position) {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_1:
                jumptoActivity(v.getContext(), RegisterActivity.class); //发展新会员
                break;
            case R.id.list_2:
//                jumptoActivity(v.getContext(), ProductEnterActivity.class);
                 //销售录入
                try{
                    jumptoActivity(v.getContext(), SaleEnterActivity.class);
                } catch (Exception e) {
                    checkCameraPermission(v.getContext());
                }
                break;
            case R.id.list_3:
                //券码核销
                try{
                    Intent intent = new Intent(v.getContext(), ScanCouponActivity.class);
                    intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.COUPON_SCAN);
                    v.getContext().startActivity(intent);
                } catch (Exception e) {
                    checkCameraPermission(v.getContext());
                }
                break;
            case R.id.list_4:
                // 我的微店
                jumptoActivity(v.getContext(), PersonalQrActivity.class);
                break;
            case R.id.list_5:
                //重点热卖
                Intent browserintent = new Intent(v.getContext(), WebViewActivity.class);
                browserintent.putExtra(Constant.BROWSER_JUMP_NAME, Constant.IMPORT_SALE);
                v.getContext().startActivity(browserintent);
                break;
//            case R.id.list_6:
//                break;
//            case R.id.list_7:
//                break;
//            case R.id.list_8:
//                jumptoActivity(v.getContext(), MoreFunctionActivity.class);
//                break;
            case R.id.choose_dian_name_ll:
                mPopup = new EasyTopPopup(mActivity, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        selection(position);
                        mSelectStoreName = (String) parent.getItemAtPosition(position);
                        mStoreName.setText(mSelectStoreName);
                    }
                }, getItemsName(), Config.RIGHT);
                mPopup.setCurrentSelected(mStoreName.getText().toString().trim());
                mPopup.show(mChooseNameLL);

                break;
        }
    }

    private void jumptoActivity(Context context, Class<?> _cls) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ACTIVITY_CREATE_FROM,Constant.HOME);
        context.startActivity(intent);
    }


    /**
     * 设置弹窗内容
     *
     * @return
     */
    private ArrayList<String> getItemsName() {
        ArrayList<String> items = new ArrayList<String>();
        items.add("广州东圃店");
        items.add("广州越秀店");
        items.add("广州天河店");
        return items;
    }

    /*    private List<DataEntry> initPreciseRcmData() {
        List<DataEntry> list = new ArrayList<>();
        DataEntry dataEntry = null;
        for(int i=0; i<RES.length; i++){
            dataEntry = new DataEntry();
            dataEntry.resId = rcmd_RES[i];
            dataEntry.title = rcmd_RES_des[i];
            list.add(dataEntry);
        }
        return list;
    }*/

    private void checkCameraPermission(Context context) {
        if(Build.VERSION.SDK_INT>=23) {
            if (ContextCompat.checkSelfPermission(BaseApplication.getInstance(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                Toast.makeText(context,"请打开使用摄像头权限",Toast.LENGTH_SHORT).show();
                mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
                return;
            }
        }
    }

}
