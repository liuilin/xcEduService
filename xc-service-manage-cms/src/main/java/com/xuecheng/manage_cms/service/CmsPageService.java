package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Daniel Liu 2019/11/20 18:23
 */
@Service
public class CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    /**分页查询
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    public Page<CmsPage> findList(int page, int size, QueryPageRequest queryPageRequest) {
        Pageable pageable = PageRequest.of(page, size);
        return cmsPageRepository.findAll(pageable);
    }
}