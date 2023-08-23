package com.phucthinh.appbookingfood.model.Event;

import com.phucthinh.appbookingfood.model.FoodDetail;

public class SuaXoaEvent {
    FoodDetail foodDetail;

    public SuaXoaEvent(FoodDetail foodDetail) {
        this.foodDetail = foodDetail;
    }

    public FoodDetail getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(FoodDetail foodDetail) {
        this.foodDetail = foodDetail;
    }
}