package com.phucthinh.appbookingfood.model;

import java.util.List;

public class FoodCategoryModel {
    boolean success;
    String message;
    List<FoodCategory> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FoodCategory> getResult() {
        return result;
    }

    public void setResult(List<FoodCategory> result) {
        this.result = result;
    }
}
