package com.linkallcloud.um.kh.controller;

import java.io.Serializable;

import com.linkallcloud.domain.Domain;
import com.linkallcloud.dto.AppVisitor;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.query.rule.Equal;

public class GcControllers {

    /**
     * fd：把登录用户的area信息作为查询条件加入page中
     * 
     * @param page
     * @param av
     */
    public static <PK extends Serializable, T extends Domain<PK>> void addAreaCnd2Page(Page<PK, T> page,
            AppVisitor av) {
        if (av != null) {
            if (av.getAreaId() == null) {
                page.addRule(new Equal("level", 0));
                page.addRule(new Equal("areaId", 0L));
            } else {
                page.addRule(new Equal("level", av.getAreaLevel()));
                page.addRule(new Equal("areaId", Long.parseLong(av.getAreaId())));
            }
        }
    }

}