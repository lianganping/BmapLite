package com.yeegot.map.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yeegot.map.BApp;
import com.yeegot.map.R;
import com.yeegot.map.activity.WebActivity;
import com.yeegot.map.base.BaseListAdapter;
import com.yeegot.map.model.MyPoiModel;
import com.yeegot.map.model.TypeMap;
import com.yeegot.map.model.TypePoi;
import com.yeegot.map.utils.StringUtils;

/**
 * @author gfuil
 */

public class SearchPoiResultAdapter extends BaseListAdapter<MyPoiModel> {
    private OnSelectPoiListener onSelectPoiListener;
    private boolean mIsShowOption;
    private boolean mIsDetails = true;
    private MyPoiModel mNearby;

    public SearchPoiResultAdapter(Context context, List<MyPoiModel> list, boolean isShowOption, boolean isShowDetails, MyPoiModel nearby) {
        super(context, list);
        this.mIsShowOption = isShowOption;
        this.mIsDetails =isShowDetails;
        this.mNearby = nearby;
    }

    public void setOnSelectPoiListener(OnSelectPoiListener onSelectPoiListener) {
        this.onSelectPoiListener = onSelectPoiListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = getInflater().inflate(R.layout.item_search_result, parent, false);
        }
        TextView textName = ViewHolder.get(convertView, R.id.text_name);
        TextView textAddress = ViewHolder.get(convertView, R.id.text_address);
        TextView textInfo = ViewHolder.get(convertView, R.id.text_info);
        ImageView btnGoHere = ViewHolder.get(convertView, R.id.btn_go_here);
        ImageView btnCall = ViewHolder.get(convertView, R.id.btn_call);

        final MyPoiModel poi = getList().get(position);
        textName.setText(poi.getName());
        if (null != poi.getAddress() && !poi.getAddress().isEmpty()) {
            textAddress.setVisibility(View.VISIBLE);
            textAddress.setText(poi.getAddress());
        } else {
            textAddress.setVisibility(View.GONE);
        }

        if (null != mNearby && (poi.getTypePoi() != TypePoi.BUS_LINE || poi.getTypePoi() != TypePoi.SUBWAY_LINE)) {
            int distance = (int) DistanceUtil.getDistance(new LatLng(mNearby.getLatitude(), mNearby.getLongitude()), new LatLng(poi.getLatitude(), poi.getLongitude()));
            if (1000 > distance && 0 < distance) {
                textInfo.setText("[" + distance + "米]");
            } else if (1000 <= distance) {
                textInfo.setText("[" + distance / 1000 + "公里]");
            } else {
                textInfo.setVisibility(View.GONE);
            }

            textInfo.setVisibility(View.VISIBLE);
        } else if (null != BApp.MY_LOCATION && (poi.getTypePoi() != TypePoi.BUS_LINE || poi.getTypePoi() != TypePoi.SUBWAY_LINE)) {

            int distance = (int) DistanceUtil.getDistance(new LatLng(BApp.MY_LOCATION.getLatitude(), BApp.MY_LOCATION.getLongitude()), new LatLng(poi.getLatitude(), poi.getLongitude()));
            if (1000 > distance && 0 < distance) {
                textInfo.setText("[" + distance + "米]");
            } else if (1000 <= distance) {
                textInfo.setText("[" + distance / 1000 + "公里]");
            } else {
                textInfo.setVisibility(View.GONE);
            }

            textInfo.setVisibility(View.VISIBLE);


        } else {
            textInfo.setVisibility(View.GONE);
        }


        if (mIsDetails && null != poi.getUid() && !poi.getUid().isEmpty()) {
            btnCall.setVisibility(View.VISIBLE);

            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    callPhone(poi.getInfo());
                    gotoDetails(poi);
                }
            });
            if (mIsShowOption) {
                btnCall.setVisibility(View.VISIBLE);
            } else {
                btnCall.setVisibility(View.GONE);
            }
        } else {
            btnCall.setVisibility(View.GONE);
        }


        if (poi.getTypePoi() == TypePoi.BUS_LINE || poi.getTypePoi() == TypePoi.SUBWAY_LINE) {
            btnGoHere.setVisibility(View.GONE);
        } else {

            btnGoHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onSelectPoiListener) {
                        onSelectPoiListener.setPoiEnd(poi);
                    }

                }
            });
            if (mIsShowOption) {
                btnGoHere.setVisibility(View.VISIBLE);
            } else {
                btnGoHere.setVisibility(View.GONE);
            }
        }


        return convertView;
    }

    private void gotoDetails(MyPoiModel poi) {
        if (null == poi.getUid() || poi.getUid().isEmpty()) {
            Toast.makeText(getContext(), "没有详情信息", Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = new Bundle();
        if (BApp.TYPE_MAP == TypeMap.TYPE_BAIDU) {
            bundle.putString("uid", poi.getUid());
            bundle.putString("url", "https://com.yeegot.com.yeegot.com.yeegot.com.yeegot.map.baidu.com/mobile/webapp/place/detail/qt=inf&uid=" + poi.getUid());
        } else if (BApp.TYPE_MAP == TypeMap.TYPE_AMAP) {
            bundle.putString("url", "http://m.amap.com/detail/index/poiid=" + poi.getUid());
        }
        bundle.putParcelable("poi", poi);
        openActivity(WebActivity.class, bundle);
    }

    private void callPhone(final String info) {
        try {
            List<String> pStringList = new ArrayList<>();
            if (BApp.TYPE_MAP == TypeMap.TYPE_AMAP) {
                pStringList = Arrays.asList(StringUtils.convertStrToArray(info, ";"));
            } else if (BApp.TYPE_MAP == TypeMap.TYPE_BAIDU) {
                pStringList = Arrays.asList(StringUtils.convertStrToArray(info, ","));
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("拨打电话");
            final String[] finalPhone = (String[]) pStringList.toArray();
            builder.setItems(finalPhone, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + finalPhone[which]);
                    intent.setData(data);
                    getContext().startActivity(intent);
                }
            });
            builder.create().show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnSelectPoiListener {
        void setPoiStart(MyPoiModel poi);

        void setPoiEnd(MyPoiModel poi);
    }
}
