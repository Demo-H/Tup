package com.tupperware.biz.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RelativeLayout;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.android.dhunter.common.utils.FileUtils;
import com.tupperware.biz.entity.member.PersonalQrBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.PersonalQrContract;
import com.tupperware.biz.utils.BitmapUtils;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;
import com.tupperware.biz.utils.thread.HandlerThreadFactory;

import java.io.File;

import javax.inject.Inject;

import static cn.jzvd.JZUtils.TAG;

/**
 * Created by dhunter on 2018/6/4.
 */

public class PersonalQrPresenter extends BasePresenter implements PersonalQrContract.Presenter {

    private PersonalDataManager mDataManager;
    private PersonalQrContract.View mView;

    @Inject
    public PersonalQrPresenter(PersonalDataManager mDataManager, PersonalQrContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getPersonQrData(String storeCode) {
        addDisposabe(mDataManager.getPersonalQrData(new ErrorDisposableObserver<PersonalQrBean>() {
            @Override
            public void onNext(PersonalQrBean personalQrBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(personalQrBean));
                if(!personalQrBean.isSuccess()) {
                    mView.toast(personalQrBean.message);
                    if(personalQrBean.resultCode != null && (personalQrBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                            || personalQrBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mView.setNetErrorView();
                    }
                } else if(personalQrBean.getModel() == null) {
                    mView.setEmptyView();
                }else {
                    mView.setNormalView();
                    mView.setPersonQrData(personalQrBean);
                }
            }

            @Override
            public void onComplete() {

            }
        }, storeCode));
    }

    public void saveQrImage(final RelativeLayout mRl, final Context context) {

        HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.BackgroundThread).post(new Runnable() {
            @Override
            public void run() {
                FileUtils.fileIsExistsbyType(FileUtils.PathType.APP_PHOTOS);
                String fileName = "qr_" + System.currentTimeMillis() + ".png";
                String filepath = FileUtils.DIR_PHOTO_CRASH + File.separator + fileName;
                BitmapUtils.saveBitmap(mRl, filepath);
                mView.toast("二维码保存路径" + filepath);
                /** 通知图片库更新，否则手机重启才能看到 **/
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File file = new File(filepath);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                context.sendBroadcast(intent);
            }
        });
    }
}
