package com.phucthinh.appbookingfood.model;

import java.io.Serializable;

public class FoodDetail implements Serializable {
    int id_detail;
    String food_name;
    String image_food;
    String description;
    String price;
    int id_cate;

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getImage_food() {
        return image_food;
    }

    public void setImage_food(String image_food) {
        this.image_food = image_food;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public int getId_cate() {
        return id_cate;
    }

    public void setId_cate(int id_cate) {
        this.id_cate = id_cate;
    }
}
