/**
 * $Id: AdvertiseCategory.java,v 1.4 2012/04/09 06:47:10 lili.meng Exp $
 */
package com.gamephone.admin.common.type;

import org.apache.commons.lang.StringUtils;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-31 广告分类
 */
public enum AdvertiseCategory {

    SMART_NG_PLATFORM_INDEX_ADV(1, "首页广告"), SMART_NG_PLATFORM_MALL_ADV(2, "积分商城广告"), SMART_NG_PLATFORM_GAME_ADV(3, "最新网游广告");

    private Integer id;

    private String title;

    private AdvertiseCategory(Integer id, String title) {
        this.id=id;
        this.title=title;
    }

    public static AdvertiseCategory getAdvertiseCategory(String category) {
        if(StringUtils.isBlank(category)) return null;
        for(AdvertiseCategory type: AdvertiseCategory.values()) {
            if(type.name().equals(category)){
                return type;
            }
        }
        return null;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title=title;
    }
}
