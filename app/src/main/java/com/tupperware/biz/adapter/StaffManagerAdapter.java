package com.tupperware.biz.adapter;

import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.StaffManagerBean;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerAdapter extends BaseQuickAdapter<StaffManagerBean.StaffContentBean,BaseViewHolder> {


    public StaffManagerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffManagerBean.StaffContentBean item, int position) {
        helper.setText(R.id.staff_name, item.getStoreEmployeeName());
        helper.setText(R.id.staff_no, "编号：" + item.getStoreEmployeeCode());
        TextView mStatus = helper.getView(R.id.staff_status);
        if(item.getStoreEmployeeStatus() == 0) {
            mStatus.setSelected(true);
            mStatus.setText("启用中");
        } else {
            mStatus.setSelected(false);
            mStatus.setText("已停用");
        }
        helper.addOnClickListener(R.id.staff_item);
//        setOnItemChildClickListener(new OnItemChildClickListener() {
//            @Override
//            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
////                mlister.onItemClick(getData().get(position));
//                Intent intent = new Intent(view.getContext(), AddNewStaffActivity.class);
//                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.MODIFIED);
//                intent.putExtra(Constant.STAFF_INFO, getData().get(position));
//                view.getContext().startActivity(intent);
//                return false;
//            }
//        });
    }

}
