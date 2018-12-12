package com.yeegot.map.interacter;

import android.content.Context;

import com.baidu.mapapi.favorite.FavoriteManager;
import com.baidu.mapapi.favorite.FavoritePoiInfo;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import com.yeegot.map.model.MyPoiModel;
import com.yeegot.map.model.TypeMap;
import com.yeegot.map.model.TypePoi;

/**
 * @author gfuil
 */

public class FavoriteInteracter {
    private Context mContext;
    private FavoriteManager mFavoriteManagerBaidu;

    public FavoriteInteracter(Context context) {
        this.mContext = context;

        mFavoriteManagerBaidu = FavoriteManager.getInstance();
        mFavoriteManagerBaidu.init();
    }

    public void destroy() {
        if (null != mFavoriteManagerBaidu) {
            mFavoriteManagerBaidu.destroy();
        }
    }

    public int addFavorite(MyPoiModel poi) {
        //构造一个点信息，pt和poiName是必填项
        FavoritePoiInfo info = new FavoritePoiInfo()
                .poiName(poi.getName())
                .pt(new LatLng(poi.getLatitude(), poi.getLongitude()))
                .addr(poi.getAddress())
                .cityName(poi.getCity())
                .uid(poi.getUid());

        return mFavoriteManagerBaidu.add(info);

    }

    public boolean deleteFavorite(String id) {
        return mFavoriteManagerBaidu.deleteFavPoi(id);
    }

    public boolean clearFavorite() {
        return mFavoriteManagerBaidu.clearAllFavPois();
    }

    public List<MyPoiModel> getFavoriteList() {
        List<MyPoiModel> list = null;

        List<FavoritePoiInfo> favoritePoiInfos = mFavoriteManagerBaidu.getAllFavPois();
        if (null != favoritePoiInfos && !favoritePoiInfos.isEmpty()) {
            list = new ArrayList<>();
            for (FavoritePoiInfo favoritePoiInfo : favoritePoiInfos) {
                MyPoiModel poi = new MyPoiModel(TypeMap.TYPE_BAIDU);
                poi.setTypePoi(TypePoi.POINT);
                poi.setName(favoritePoiInfo.getPoiName());
                poi.setLatitude(favoritePoiInfo.getPt().latitude);
                poi.setLongitude(favoritePoiInfo.getPt().longitude);
                poi.setAddress(favoritePoiInfo.getAddr());
                poi.setCity(favoritePoiInfo.getCityName());
                poi.setUid(favoritePoiInfo.getID());
                list.add(poi);
            }
        }
        return list;

    }
}
