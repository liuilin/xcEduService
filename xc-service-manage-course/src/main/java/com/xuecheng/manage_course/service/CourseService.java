package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.domain.cms.response.CourseResult;
import com.xuecheng.framework.domain.course.*;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.dao.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 课程服务层
 *
 * @author Daniel Liu 2019/12/18 8:22
 */
@Service
public class CourseService {
    @Value( "${course-publish.dataUrlPre}" )
    private String publish_dataUrlPre;
    @Value( "${course-publish.pagePhysicalPath}" )
    private String publish_page_physicalpath;
    @Value( "${course-publish.pageWebPath}" )
    private String publish_page_webpath;
    @Value( "${course-publish.siteId}" )
    private String publish_siteId;
    @Value( "${course-publish.templateId}" )
    private String publish_templateId;
    @Value( "${course-publish.previewUrl}" )
    private String previewUrl;
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeachPlanRepository teachPlanRepository;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseMarketRepository courseMarketRepository;
    @Autowired
    private CoursePicRepository coursePicRepository;
    @Autowired
    private CmsPageClient cmsPageClient;

    public void print(String s) {
        System.out.print(s);
    }

    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.findTeachPlanById(courseId);
    }


    @Transactional
    public ResponseResult addTeachPlan(Teachplan teachplan) {
        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid()) || StringUtils.isEmpty(teachplan.getPname())) {
            throw new CustomException(CommonCode.INVALID_PARAMETER);
        }
        String courseid = teachplan.getCourseid();
        //页面传入的父id，可能没有
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)) {
            parentid = this.getTeachPlanRootNode(courseid);
        }
        Optional<Teachplan> teachplanOptional = teachPlanRepository.findById(parentid);
        Teachplan parentNode = teachplanOptional.get();
        //父节点级别
        String grade = parentNode.getGrade();
        //保存课程
        Teachplan teachplanNew = new Teachplan();
        BeanUtils.copyProperties(teachplan, teachplanNew);
        teachplanNew.setParentid(parentid);
        teachplanNew.setCourseid(courseid);
        //grade级别，根据父节点的级别来设置
        if (grade.equals("1")) {
            teachplanNew.setGrade("2");
        } else {
            teachplanNew.setGrade("3");
        }
        teachPlanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取根节点，如果没有则添加根节点
     *
     * @param courseid
     * @return
     */
    private String getTeachPlanRootNode(String courseid) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        if (!optional.isPresent()) {
            return null;
        }
        CourseBase courseBase = optional.get();
        List<Teachplan> teachplans = teachPlanRepository.findAllByCourseidAndParentid(courseid, "0");
//        Optional.ofNullable(teachplans).orElseThrow(() -> new ServiceException("不存在或是已被删除"));
        if (CollectionUtils.isEmpty(teachplans)) {
            //查询不到根节点，自动添加根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setPname(courseBase.getName());
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setCourseid(courseid);
            teachPlanRepository.save(teachplan);
            return teachplan.getId();
        }
        //返回根节点id
        return teachplans.get(0).getId();
    }

    /**
     * 分页查询课程列表
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest) {
        PageHelper.startPage(page, size);
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<>();
        courseInfoQueryResult.setList(courseListPage.getResult());
        courseInfoQueryResult.setTotal(courseListPage.getTotal());
        return new QueryResponseResult<>(CommonCode.SUCCESS, courseInfoQueryResult);
    }

    /**
     * 新增课程
     *
     * @param courseBase
     * @return
     */
    @Transactional
    public CourseResult save(CourseBase courseBase) {
        CourseBase save = courseBaseRepository.save(courseBase);
        return new CourseResult(CommonCode.SUCCESS, save);
    }

    /**
     * 根据课程id获取课程信息
     *
     * @param courseId
     * @return CourseBase
     */
    public CourseBase getCourseBaseById(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        return optional.orElse(null);
    }

    /**
     * 更新课程信息
     *
     * @param courseId
     * @param courseBase entity must not be null
     * @return
     */
    @Transactional
    public ResponseResult updateCourseBase(String courseId, CourseBase courseBase) {
        CourseBase one = this.getCourseBaseById(courseId);
        if (one == null) {
            throw new CustomException(CmsCode.COURSE_BASE_DOES_NOT_EXIT);
        }
        one.setName(courseBase.getName());
        one.setUsers(courseBase.getUsers());
        one.setMt(courseBase.getMt());
        one.setSt(courseBase.getSt());
        one.setGrade(courseBase.getGrade());
        one.setStudymodel(courseBase.getStudymodel());
        one.setDescription(courseBase.getDescription());
        courseBaseRepository.save(one);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 课程视图查询
     *
     * @param id
     * @return
     */
    public CourseView getCourseViewById(String id) {
        CourseView courseView = new CourseView();
        //查询课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        if (courseBaseOptional.isPresent()) {
            CourseBase courseBase = courseBaseOptional.get();
            courseView.setCourseBase(courseBase);
        }
        //查询课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(id);
        if (courseMarketOptional.isPresent()) {
            CourseMarket courseMarket = courseMarketOptional.get();
            courseView.setCourseMarket(courseMarket);
        }
        //查询课程图片信息
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(id);
        if (coursePicOptional.isPresent()) {
            CoursePic coursePic = coursePicOptional.get();
            courseView.setCoursePic(coursePic);
        }
        //查询课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.findTeachPlanById(id);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    /**
     * 课程预览
     *
     * @param courseId 页面Id
     * @return
     */
    public CoursePublishResult preview(String courseId) {
        CourseBase one = this.getCourseBaseById(courseId);

        //准备发布课程预览页面
        CmsPage cmsPage = new CmsPage();
        //站点
        cmsPage.setSiteId(publish_siteId);//课程预览站点
        //模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(courseId + ".html");
        //页面别名
        cmsPage.setPageAliase(one.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);
        //远程请求cms保存页面信息
        CmsPageResult cmsPageResult = cmsPageClient.save(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }
        //页面id
        String pageId = cmsPageResult.getCmsPage().getPageId();
        //页面url
        String pageUrl = previewUrl + pageId;
        return new CoursePublishResult(CommonCode.SUCCESS, pageUrl);
    }

    @Transactional
    public CoursePublishResult publish(String courseId) {
        CourseBase one = this.getCourseBaseById(courseId);

        //准备cmsPage信息
        //准备发布课程预览页面
        CmsPage cmsPage = new CmsPage();
        //站点
        cmsPage.setSiteId(publish_siteId);//课程预览站点
        //模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(courseId + ".html");
        //页面别名
        cmsPage.setPageAliase(one.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);

        //调用cms一键发布接口将课程详情页面发布到服务器
        CmsPostPageResult cmsPostPageResult = cmsPageClient.oneClickPostPage(cmsPage);
        if (!cmsPostPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }
        //保存课程的发布状态为“已发布”
        CourseBase courseBase = this.setStatus(courseId);
        //保存课程索引信息
        //...
        //缓存课程的信息
        //...
        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }

    private CourseBase setStatus(String courseId) {
        CourseBase one = this.getCourseBaseById(courseId);
        //用的是数据字典
        one.setStatus("202002");
        return courseBaseRepository.save(one);
    }
}