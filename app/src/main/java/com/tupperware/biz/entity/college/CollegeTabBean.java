package com.tupperware.biz.entity.college;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/5/2.
 */

public class CollegeTabBean extends BaseData {

    public List<TabModel> models;

    public static class TabModel{
        public String name;
        public int id;
    }

}
