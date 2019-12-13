package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * 监听消息，通知页面发布
 *
 * @author Daniel Liu 2019/12/13 8:48
 */
@Component
public class ConsumerPostPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);
    @Autowired
    private PageService pageService;
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @RabbitListener( queues = "${xuecheng.mq.queue}" )
    public void postPage(String msg) {
        Map map = JSON.parseObject(msg, Map.class);
        LOGGER.info("receive msg:{}", msg);
        String pageId = (String) map.get("pageId");
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        if (!optionalCmsPage.isPresent()) {
            LOGGER.info("receive cms post page,page is null:{}", msg);
            return;
        }
        pageService.savePageToServerPath(pageId);
    }
}