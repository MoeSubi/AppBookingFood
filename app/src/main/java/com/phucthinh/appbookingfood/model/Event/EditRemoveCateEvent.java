package com.phucthinh.appbookingfood.model.Event;

import com.phucthinh.appbookingfood.model.FoodCategory;

public class EditRemoveCateEvent
{
    FoodCategory foodCategory;

    public EditRemoveCateEvent(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    public FoodCategory getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }
}
