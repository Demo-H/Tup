package com.tupperware.biz.entity;

/**
 * Created by dhunter on 2018/9/6.
 */

public class MeBenfitCoin extends BaseData{

    private Model model;

    public class Model {
        private int storeIntegralAmount;
        private int storeCouponTotal;

        public int getStoreIntegralAmount() {
            return storeIntegralAmount;
        }

        public void setStoreIntegralAmount(int storeIntegralAmount) {
            this.storeIntegralAmount = storeIntegralAmount;
        }

        public int getStoreCouponTotal() {
            return storeCouponTotal;
        }

        public void setStoreCouponTotal(int storeCouponTotal) {
            this.storeCouponTotal = storeCouponTotal;
        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
