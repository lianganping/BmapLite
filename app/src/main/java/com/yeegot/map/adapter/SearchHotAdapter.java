package com.yeegot.map.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.yeegot.map.R;
import com.yeegot.map.base.BaseListAdapter;
import com.yeegot.map.utils.AppUtils;

/**
 * @author gfuil
 */

public class SearchHotAdapter extends BaseListAdapter<String> {
    private int[] resIds = {
            R.drawable.ic_restaurant_menu_18dp,
            R.drawable.ic_local_grocery_store_18dp,
            R.drawable.ic_local_hotel_18dp,
            R.drawable.ic_local_convenience_store_18dp,
            R.drawable.ic_local_see_18dp,
            R.drawable.ic_local_hospital_18dp,
            R.drawable.ic_local_mall_18dp,
            R.drawable.ic_local_movies_18dp,
            R.drawable.ic_local_parking_black_18dp,
            R.drawable.ic_local_gas_station_18dp,
            R.drawable.ic_directions_bus_18dp,
            R.drawable.ic_directions_subway_18dp
    };

    private int[] colorIds = {
            R.color.keyword1,
            R.color.keyword2,
            R.color.keyword3,
            R.color.keyword4,
            R.color.keyword5,
            R.color.keyword6,
            R.color.keyword7,
            R.color.keyword8,
            R.color.keyword9,
            R.color.keyword10,
            R.color.keyword11,
            R.color.keyword12,

    };

    public SearchHotAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = getInflater().inflate(R.layout.item_search_hot, parent, false);
        }
        TextView textHot = ViewHolder.get(convertView, R.id.text_keyword);
        textHot.setText(getList().get(position));
        Drawable drawable = getContext().getResources().getDrawable(resIds[position]);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textHot.setCompoundDrawables(drawable, null, null, null);
        textHot.setCompoundDrawablePadding(AppUtils.dip2Px(getContext(), 5));

        textHot.setTextColor(getContext().getResources().getColor(colorIds[position]));


        return convertView;
    }
}
