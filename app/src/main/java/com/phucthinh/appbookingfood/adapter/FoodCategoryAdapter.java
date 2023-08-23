package com.phucthinh.appbookingfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.FoodCategory;

import java.util.List;

public class FoodCategoryAdapter extends BaseAdapter {

    Context context;
    List<FoodCategory> categoryList;

    public FoodCategoryAdapter(Context context, List<FoodCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView mfoodName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_food, null);
            viewHolder.mfoodName = view.findViewById(R.id.item_foodName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mfoodName.setText(categoryList.get(i).getCategory_name());
        return view;

    }
}
