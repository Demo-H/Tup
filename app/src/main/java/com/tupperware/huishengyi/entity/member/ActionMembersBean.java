package com.tupperware.huishengyi.entity.member;

import com.tupperware.huishengyi.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/6/14.
 * 根据活动查询参与活动的人
 * 和参与活动人详情
 */

public class ActionMembersBean extends BaseData{
    private ReservationItem model;
    private List<ReservationItem> models;


    public ReservationItem getModel() {
        return model;
    }

    public void setModel(ReservationItem model) {
        this.model = model;
    }

    public List<ReservationItem> getModels() {
        return models;
    }

    public void setModels(List<ReservationItem> models) {
        this.models = models;
    }
}
