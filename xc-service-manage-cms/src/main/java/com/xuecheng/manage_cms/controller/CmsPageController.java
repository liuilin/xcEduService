package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author Daniel Liu 2019/11/19 9:50
 */
@RestController
@RequestMapping( "/cms/page" )
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;

    @Override
    @GetMapping( "/list/{page}/{size}" )
    public QueryResponseResult findList(@PathVariable( "page" ) int page, @PathVariable( "size" ) int size, QueryPageRequest queryPageRequest) {
        /*//暂时采用测试数据，测试接口是否可以正常运行
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(2);
        //静态数据列表
        List list = new ArrayList();
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        list.add(cmsPage);
        queryResult.setList(list);
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;*/
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 10;
        }
        Page<CmsPage> list = cmsPageService.findList(page, size, queryPageRequest);
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(list.getContent());
        queryResult.setTotal(list.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**@RequestBody将json转为对象
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping( "/add" )
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    @Override
    @GetMapping("/findById/{id}")
    public CmsPage findById(@PathVariable String id) {
        return cmsPageService.findById(id);
    }

    /**PutMapping用来更新，@RequestBody来接收json数据
     * @param id
     * @param cmsPage
     * @return
     */
    @Override
    @PutMapping("/update/{id}")
    public CmsPageResult update(@PathVariable String id, @RequestBody CmsPage cmsPage) {
        return cmsPageService.update(id, cmsPage);
    }


}