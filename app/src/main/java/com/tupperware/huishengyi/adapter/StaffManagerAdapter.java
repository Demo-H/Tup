package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.StaffManagerBean;
import com.tupperware.huishengyi.ui.AddNewStaffActivity;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerAdapter extends BaseQuickAdapter<StaffManagerBean.StaffContentBean,BaseViewHolder> {

    private StaffManagerBean.StaffContentBean itemBean;

    public StaffManagerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffManagerBean.StaffContentBean item, int position) {
        helper.setText(R.id.staff_name, item.name);
        helper.setText(R.id.staff_no, "编号：" + "0801234");
        TextView mStatus = helper.getView(R.id.staff_status);
        if(item.status == 0) {
            mStatus.setSelected(true);
            mStatus.setText("启用中");
        } else if(item.status == 1) {
            mStatus.setSelected(false);
            mStatus.setText("已停用");
        }
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), AddNewStaffActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.ACTIVITY_CREATE_FROM, Constant.MODIFIED);
//                bundle.putParcelable("Data", itemBean));
//                intent.putExtras(bundle);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.MODIFIED);
                view.getContext().startActivity(intent);
            }
        });
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                return false;
            }
        });
    }
}
