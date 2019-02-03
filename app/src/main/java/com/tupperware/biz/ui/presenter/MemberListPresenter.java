package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;
import com.tupperware.biz.entity.product.ProductType;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.MemberListContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/10/22.
 */

public class MemberListPresenter extends BasePresenter implements MemberListContract.Presenter {

    private static final String TAG = "MemberListPresenter";

    private MemberDataManager mDataManager;
    private MemberListContract.View mView;

    @Inject
    public MemberListPresenter(MemberDataManager mDataManager, MemberListContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getMemberListData(MemberSearchConditionDTO searchCondition) {
        addDisposabe(mDataManager.getMemberList(new ErrorDisposableObserver<MemberBean>() {
            @Override
            public void onNext(MemberBean memberBean) {
                mView.hideDialog();
                if(memberBean != null) {
                    if(!memberBean.isSuccess()) {
                        mView.toast(memberBean.getMessage());
                        if(memberBean.resultCode != null && (memberBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                                || memberBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                            mView.reLogin();
                            mDataManager.deleteSPData();
                        } else {
                            mView.setNetErrorView();
                        }
                    } else if(memberBean.getPageInfo() == null || memberBean.getPageInfo().getList() == null
                            || memberBean.getPageInfo().getList().size() == 0) {
                        mView.setEmptyView();
                    } else {
                        mView.setNormalView();
                        mView.setMemberListData(memberBean);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        }, searchCondition));
    }

    @Override
    public void getMoreMemberListData(MemberSearchConditionDTO searchCondition) {
        addDisposabe(mDataManager.getMemberList(new ErrorDisposableObserver<MemberBean>() {
            @Override
            public void onNext(MemberBean memberBean) {
                if(memberBean != null) {
                    mView.setMoreMemberListData(memberBean);
                }
            }

            @Override
            public void onComplete() {

            }
        }, searchCondition));
    }

    @Override
    public void getMemberFilterProductType() {
        addDisposabe(mDataManager.getMemberFilterProductType(new ErrorDisposableObserver<ProductType>() {
            @Override
            public void onNext(ProductType productType) {
                if(productType != null) {
                    if(!productType.isSuccess()) {
                        mView.toast(productType.getMessage());
                        if(productType.resultCode != null && (productType.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                                || productType.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                            mView.reLogin();
                            mDataManager.deleteSPData();
                        }
                    } else {
                        mView.setMemberFilterProductType(productType);
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
