package com.xuecheng.framework.domain.cms.request;

import lombok.Data;

/**
 * @author Daniel Liu 2019/11/18 9:08
 */
@Data
public class QueryPageRequest {
    //站点id
    private String siteId;
    //页面ID
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //模版id
    private String templateId;
}