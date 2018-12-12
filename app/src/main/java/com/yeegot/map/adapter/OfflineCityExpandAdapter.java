package com.yeegot.map.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;

import java.util.List;

import com.yeegot.map.base.BaseExpandableAdapter;

/**
 * @author gfuil
 */

public class OfflineCityExpandAdapter extends BaseExpandableAdapter<MKOLSearchRecord> {
    public OfflineCityExpandAdapter(Context context, List<MKOLSearchRecord> list) {
        super(context, list);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (null != getList() && null != getList().get(groupPosition).childCities) ? getList().get(groupPosition).childCities.size() : 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return (null != getList() && null != getList().get(groupPosition).childCities) ? getList().get(groupPosition).childCities.get(childPosition) : null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = getInflater().inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }
        TextView textName = ViewHolder.get(convertView, android.R.id.text1);
        textName.setText(getList().get(groupPosition).cityName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = getInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textName = ViewHolder.get(convertView, android.R.id.text1);
        textName.setText(getList().get(groupPosition).childCities.get(childPosition).cityName);
        return convertView;
    }

}
