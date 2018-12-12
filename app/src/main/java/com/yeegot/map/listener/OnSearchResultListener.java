package com.yeegot.map.listener;

import com.amap.api.services.core.SuggestionCity;

import java.util.List;

import com.yeegot.map.base.OnBaseListener;
import com.yeegot.map.model.MyPoiModel;

/**
 * @author gfuil
 */

public interface OnSearchResultListener extends OnBaseListener {
    void setSearchResult(List<MyPoiModel> list);
    void setSuggestCityList(List<SuggestionCity> cities);
}
