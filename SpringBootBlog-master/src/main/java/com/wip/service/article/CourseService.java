package com.wip.service.article;


import com.github.pagehelper.PageInfo;
import com.wip.dto.cond.ContentCond;
import com.wip.model.ContentDomain;
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
     * @param coid
     * @return
     */
    CourseDomain getCourseArticleById(Integer coid);

    /**
     * 更新教程
     * @param courseDomain
     */
    void updateCourseArticleById(CourseDomain courseDomain);

    /**
     * 根据条件获取教程列表
     * @param contentCond
     * @param page
     * @param limit
     * @return
     */
    PageInfo<CourseDomain> getCourseArticlesByCond(ContentCond contentCond, int page, int limit);

    /**
     * 删除教程
     * @param cid
     */
    void deleteArticleById(Integer cid);

    /**
     * 添加教程点击量
     * @param content
     */
    void updateContentByCid(ContentDomain content);

    /**
     * 通过分类获取教程
     * @param category
     * @return
     */
    List<ContentDomain> getArticleByCategory(String category);

    /**
     * 通过标签获取教程
     * @param tags
     * @return
     */
    List<ContentDomain> getArticleByTags(MetaDomain tags);
}


