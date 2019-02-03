package com.tupperware.biz.entity.product;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/10/26.
 */

public class ProductType extends BaseData {
    private List<ProductItem> models;

    public class ProductItem{
        private String salesCatalogId;
        private String salesCatalog;

        public String getSalesCatalogId() {
            return salesCatalogId;
        }

        public void setSalesCatalogId(String salesCatalogId) {
            this.salesCatalogId = salesCatalogId;
        }

        public String getSalesCatalog() {
            return salesCatalog;
        }

        public void setSalesCatalog(String salesCatalog) {
            this.salesCatalog = salesCatalog;
        }
    }

    public List<ProductItem> getModels() {
        return models;
    }

    public void setModels(List<ProductItem> models) {
        this.models = models;
    }
}
