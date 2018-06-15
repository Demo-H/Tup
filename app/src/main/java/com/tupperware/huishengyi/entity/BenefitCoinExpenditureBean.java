package com.tupperware.huishengyi.entity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinExpenditureBean {

    public int typeId;
    public String typeName;
    public String code;
    public List<ExpenditureContentBean> content;

    public static class ExpenditureContentBean {

        public String goodsName;
        public String date;
        public String money;
        public String userName;
        public int image;
        public String userTel;
        public String imageUrl;
    }
}
