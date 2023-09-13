package com.wip.service.article;


import com.github.pagehelper.PageInfo;
import com.wip.dto.cond.CourseCond;
import com.wip.model.CourseDomain;
import com.wip.model.MetaDomain;

import java.util.List;

/**
 * 教程相关Service接口
 */
public interface CourseService {

    /***
     * 添加教程
     * @param courseDomain
     */
    void addCourseArticle(CourseDomain courseDomain);

    /**
     * 根据编号获取教程
     * @param couid
     * @return
     */
    CourseDomain getCourseArticleById(Integer couid);

    /**
     * 更新教程
     * @param courseDomain
     */
    void updateCourseArticleById(CourseDomain courseDomain);

    /**
     * 根据条件获取教程列表
     * @param courseCond
     * @param page
     * @param limit
     * @return
     */
    PageInfo<CourseDomain> getCourseArticlesByCond(CourseCond courseCond, int page, int limit);

    /**
     * 删除教程
     * @param couid
     */
    void deleteCourseArticleById(Integer couid);

    /**
     * 添加教程点击量
     * @param course
     */
    void updateCourseByCouid(CourseDomain course);

    /**
     * 通过分类获取教程
     * @param category
     * @return
     */
    List<CourseDomain> getCourseArticleByCategory(String category);

    /**
     * 通过标签获取教程
     * @param tags
     * @return
     */
    List<CourseDomain> getCourseArticleByTags(MetaDomain tags);
}


