package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * 下载页面，将文件保存到制定目录
 *
 * @author Daniel Liu 2019/12/12 19:46
 */
@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    public void savePageToServerPath(String pageId) {
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()) {
            throw new CustomException(CmsCode.CMS_PAGE_DOES_NOT_EXIST);
        }
        CmsPage cmsPage = optional.get();
        String siteId = cmsPage.getSiteId();
        CmsSite site = this.getSiteById(siteId);
        assert site != null;
        String path = site.getSitePhysicalPath() + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        //获取存储页面的id
        String htmlFileId = cmsPage.getHtmlFileId();

        InputStream inputStream = this.getFileById(htmlFileId);
        try {
            assert inputStream != null;
//            IoUtil.copy(inputStream, new FileOutputStream(path));
            IOUtils.copy(inputStream, new FileOutputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CmsSite getSiteById(String siteId) {
        Optional<CmsSite> cmsSite = cmsSiteRepository.findById(siteId);
        return cmsSite.orElse(null);
    }

    /**
     * 根据id获取文件流内容
     *
     * @param htmlFileId
     * @return
     */
    private InputStream getFileById(String htmlFileId) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}