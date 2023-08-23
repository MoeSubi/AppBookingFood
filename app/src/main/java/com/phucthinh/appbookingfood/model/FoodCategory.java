package com.phucthinh.appbookingfood.model;

import java.io.Serializable;

public class FoodCategory implements Serializable {
    int id_cate;
    String category_name;

    public FoodCategory(String id_cate, String category_name) {
        this.id_cate = Integer.parseInt(id_cate);
        this.category_name = category_name;
    }



    public int getId_cate() {
        return id_cate;
    }

    public void setId_cate(int id_cate) {
        this.id_cate = id_cate;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
