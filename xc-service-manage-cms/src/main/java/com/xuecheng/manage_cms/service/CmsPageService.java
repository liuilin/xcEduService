package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Daniel Liu 2019/11/20 18:23
 */
@Service
public class CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

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
    /*public CmsPageResult add(CmsPage cmsPage) {
        //添加根据页面名称、站点Id、页面webpath查询页面方法，此方法用于校验页面是否存在
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
}