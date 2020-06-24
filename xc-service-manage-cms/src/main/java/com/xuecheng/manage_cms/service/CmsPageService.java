package com.xuecheng.manage_cms.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Daniel Liu 2019/11/20 18:23
 */
@Service
public class CmsPageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsPageService.class);
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsConfigRepository cmsConfigRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    public Page<CmsPage> findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        Pageable pageable = PageRequest.of(page, size);
        CmsPage cmsPage = new CmsPage();
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        return cmsPageRepository.findAll(example, pageable);
    }

    /**
     * 页面添加
     * 是先判断是否有异常
     *
     * @param cmsPage
     */
    /*public CmsPageResult add(CmsPage cmsPage) { //添加根据页面名称、站点Id、页面webpath查询页面方法，此方法用于校验页面是否存在
        CmsPage page = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (page == null) {
            //出于安全考虑，添加页面的主键由spring data自动生成（防止接口被调用来篡改主Id？）
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }*/
    public CmsPageResult add(CmsPage cmsPage) {
        //添加根据页面名称、站点Id、页面webpath查询页面方法，此方法用于校验页面是否存在
        CmsPage page = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (page != null) {
            throw new CustomException(CmsCode.CMS_PAGE_NAME_EXITS);
        }
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    /**
     * 根据页面Id查询页面
     *
     * @param id
     * @return
     */
    public CmsPage findById(String id) {
        //若查到返回CmsPage，未查到返回null
        return cmsPageRepository.findById(id).orElse(null);
//        Optional<CmsPage> optional = cmsPageRepository.findById(id);
//        return optional.orElse(null);
    }

    /**
     * 更新页面内容
     *
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage one = this.findById(id);
        if (one != null) {
            //更新模板Id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            one.setDataUrl(cmsPage.getDataUrl());
            return new CmsPageResult(CommonCode.SUCCESS, cmsPageRepository.save(one));
        }
        return null;
    }

    /**
     * 页面删除
     *
     * @param id
     * @return
     */
    public ResponseResult del(String id) {
        //先查询一下CmsPage，有则删除
        Optional<CmsPage> cmsPage = cmsPageRepository.findById(id);
//        cmsPage.ifPresent(page -> {
//            cmsPageRepository.delete(page);
//            return new ResponseResult(CommonCode.SUCCESS);
//        });
        if (cmsPage.isPresent()) {
            cmsPageRepository.delete(cmsPage.get());
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 根据id查cms_config
     *
     * @param id
     * @return
     */
    public CmsConfig getConfigById(String id) {
        return cmsConfigRepository.findById(id).orElse(null);
    }

    /**
     * 获取页面静态化后的HTML内容
     *
     * @param pageId
     * @return
     */
    public String getPageHtml(String pageId) {
        Map model = getModelByPageId(pageId);

        String templateContent = getTemplateContent(pageId);

        return generateHtml(model, templateContent);
    }

    /**
     * 执行静态化返回HTML内容
     *
     * @param model
     * @param templateContent
     * @return
     * @throws IOException
     */
    private String generateHtml(Map model, String templateContent) {
        try {
            //配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", templateContent);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取数据
            Template template = configuration.getTemplate("template");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据页面id拿到girdFs中的文件数据
     *
     * @param pageId
     * @return
     * @throws IOException
     */
    private String getTemplateContent(String pageId) {
        CmsPage cmsPage = this.findById(pageId);
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            throw new CustomException(CmsCode.CMS_PAGE_TEMPLATE_IS_EMPTY);
        }
        Optional<CmsTemplate> cmsTemplate = cmsTemplateRepository.findById(templateId);
        if (cmsTemplate.isPresent()) {
            CmsTemplate cmsTemplate1 = cmsTemplate.get();
            String templateFileId = cmsTemplate1.getTemplateFileId();
            //根据id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建gridFsResource，用于获取流对象
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            //获取流中的数据
            try {
                return IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据页面id获取模型数据
     *
     * @param pageId
     * @return
     */
    private Map getModelByPageId(String pageId) {
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            throw new CustomException(CmsCode.CMS_PAGE_DOES_NOT_EXIST);
        }
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            throw new CustomException(CmsCode.CMS_PAGE_DATAURL_NO_EXITS);
        }
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        return forEntity.getBody();
    }

    /**
     * 发布页面接口
     *
     * @param pageId
     * @return
     */
    public ResponseResult postPage(String pageId) {
        //获取页面静态化后的HTML内容
        String pageHtml = this.getPageHtml(pageId);
        //Save Html to gridFS
        CmsPage cmsPage = saveHtml(pageId, pageHtml);
        //send msg
        this.sendMsg(pageId, cmsPage);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 发送消息
     *
     * @param pageId
     * @param cmsPage
     */
    private void sendMsg(String pageId, CmsPage cmsPage) {
        Map<String, String> map = new HashMap<>(16);
        map.put("pageId", pageId);
        String msg = JSON.toJSONString(map);
        LOGGER.info("send msg,pageId is {}", pageId);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, cmsPage.getSiteId(), msg);
    }

    /**
     * Save Html to gridFS
     *
     * @param pageId
     * @param pageHtml
     * @return
     */
    private CmsPage saveHtml(String pageId, String pageHtml) {
        if (StrUtil.isEmpty(pageHtml)) {
            throw new CustomException(CmsCode.CMS_PAGE_HTML_ISNULL);
        }
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        if (!optionalCmsPage.isPresent()) {
            throw new CustomException(CmsCode.CMS_PAGE_DOES_NOT_EXIST);
        }
        CmsPage cmsPage = optionalCmsPage.get();
        ByteArrayInputStream byteArrayInputStream = IoUtil.toStream(pageHtml, StandardCharsets.UTF_8);
        //先删再存
        String htmlFileId = cmsPage.getHtmlFileId();
        if (!StrUtil.isEmpty(htmlFileId)) {
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
        }
        ObjectId objectId = gridFsTemplate.store(byteArrayInputStream, cmsPage.getPageName());
        cmsPage.setHtmlFileId(String.valueOf(objectId));
        return cmsPageRepository.save(cmsPage);
    }

    /**
     * 保存页面，无则添加，有则更新
     *
     * @param cmsPage
     * @return
     */
    public CmsPageResult save(CmsPage cmsPage) {
        //添加根据页面名称、站点Id、页面webpath查询页面方法，此方法用于校验页面是否存在
        CmsPage one = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        System.out.println("page = " + one);
        if (one != null) {
//            throw new CustomException(CmsCode.CMS_PAGE_NAME_EXITS);
            return this.update(one.getPageId(), cmsPage);
        } else {
            return this.add(cmsPage);
        }
    }

    /**
     * 保存之后页面之后返回id，根据id发布页面
     *
     * @param cmsPage
     * @return
     */
    public CmsPostPageResult oneClickPostPage(CmsPage cmsPage) {
        //添加页面
        CmsPageResult cmsPageResult = this.save(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            throw new CustomException(CommonCode.FAIL);
//            return new CmsPostPageResult(CommonCode.FAIL, null);
        }
        CmsPage one = cmsPageResult.getCmsPage();
        //要发布页面的id
        String pageId = one.getPageId();
        //发布页面
        ResponseResult responseResult = this.postPage(pageId);
        if (!responseResult.isSuccess()) {
//            return new CmsPostPageResult(CommonCode.FAIL, null);
            throw new CustomException(CommonCode.FAIL);
        }
        //得到页面的url
        //页面url=站点域名+站点webpath+页面webpath+页面名称
        //站点id
        String siteId = one.getSiteId();
        //查询站点信息
        CmsSite cmsSite = findCmsSiteById(siteId);
        //站点域名
        String siteDomain = cmsSite.getSiteDomain();
        //站点web路径
        String siteWebPath = cmsSite.getSiteWebPath();
        //页面web路径
        String pageWebPath = one.getPageWebPath();
        //页面名称
        String pageName = one.getPageName();
        //页面的web访问地址
        String pageUrl = siteDomain + siteWebPath + pageWebPath + pageName;
        LOGGER.info(pageUrl);
        return new CmsPostPageResult(CommonCode.SUCCESS, pageUrl);
    }

    private CmsSite findCmsSiteById(String siteId) {
        Optional<CmsSite> cmsSite = cmsSiteRepository.findById(siteId);
        if (cmsSite.isPresent()) {
            return cmsSite.get();
        }
        return null;
    }
}