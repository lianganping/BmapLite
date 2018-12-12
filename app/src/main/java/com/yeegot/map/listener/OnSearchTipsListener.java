package com.yeegot.map.listener;

import java.util.List;

import com.yeegot.map.base.OnBaseListener;


/**
 * @author gfuil
 */

public interface OnSearchTipsListener extends OnBaseListener {
    void setSearchTipsAdatper(List<String> list);
}
