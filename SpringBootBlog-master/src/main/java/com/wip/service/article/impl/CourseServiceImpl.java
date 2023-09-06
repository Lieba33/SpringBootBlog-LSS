/**
 * Created by IntelliJ IDEA.
 * User: lss
 * DateTime: 2023/9/6 10:50
 **/
package com.wip.service.article.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wip.constant.ErrorConstant;
import com.wip.constant.Types;
import com.wip.constant.WebConst;
import com.wip.dao.CommentDao;
import com.wip.dao.CourseDao;
import com.wip.dao.CourseShipDao;
import com.wip.dto.cond.CourseCond;
import com.wip.exception.BusinessException;
import com.wip.model.*;
import com.wip.service.article.CourseService;
import com.wip.service.meta.MetaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private MetaService metaService;

    @Autowired
    private CourseShipDao courseShipDao;

    @Autowired
    private CommentDao commentDao;

    @Transactional
    @Override
    @CacheEvict(value = {"articleCache", "articleCaches"}, allEntries = true, beforeInvocation = true)
    public void addCourseArticle(CourseDomain courseDomain) {
        if (null == courseDomain)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        if (StringUtils.isBlank(courseDomain.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);

        if (courseDomain.getTitle().length() > WebConst.MAX_TITLE_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_IS_TOO_LONG);

        if (StringUtils.isBlank(courseDomain.getCourse()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);

        if (courseDomain.getCourse().length() > WebConst.MAX_CONTENT_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_IS_TOO_LONG);

        // 取到标签和分类
        String tags = courseDomain.getTags();
        String categories = courseDomain.getCategories();

        // 添加文章
        courseDao.addCourseArticle(courseDomain);

        // 添加分类和标签
        int coid = courseDomain.getCoid();
        metaService.addMetas(coid, tags, Types.TAG.getType());
        metaService.addMetas(coid, categories, Types.CATEGORY.getType());
    }

    @Override
    @Cacheable(value = "courseArticleCache", key = "'courseArticleById_' + #p0")
    public CourseDomain getCourseArticleById(Integer coid) {
        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return courseDao.getCourseArticleById(coid);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"courseArticleCache", "courseArticleCaches"}, allEntries = true, beforeInvocation = true)
    public void updateCourseArticleById(CourseDomain courseDomain) {

        // 标签和分类
        String tags = courseDomain.getTags();
        String categories = courseDomain.getCategories();

        // 更新教程
        courseDao.updateCourseArticleById(courseDomain);
        int coid = courseDomain.getCoid();
        courseShipDao.deleteCourseShipByCoid(coid);
        metaService.addMetas(coid,tags,Types.TAG.getType());
        metaService.addMetas(coid,categories,Types.CATEGORY.getType());
    }

    @Override
    @Cacheable(value = "courseArticleCaches", key = "'courseArticlesByCond_' + #p1 + 'type_' + #p0.type")
    public PageInfo<CourseDomain> getCourseArticlesByCond(CourseCond courseCond, int pageNum, int pageSize) {
        if (null == courseCond)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum,pageSize);
        List<CourseDomain> courses = courseDao.getCourseArticleByCond(courseCond);
        PageInfo<CourseDomain> pageInfo = new PageInfo<>(courses);
        return pageInfo;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"courseArticleCache","courseArticleCaches"},allEntries = true, beforeInvocation = true)
    public void deleteCourseArticleById(Integer coid) {

        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        // 删除教程
        courseDao.deleteCourseArticleById(coid);

        // 同时要删除该 教程下的所有评论
        List<CommentDomain> comments = commentDao.getCommentByCId(coid);
        if (null != comments && comments.size() > 0) {
            comments.forEach(comment -> {
                commentDao.deleteComment(comment.getCoid());
            });
        }

        // 删除标签和分类关联
        List<CourseShipDomain> courseShips = courseShipDao.getCourseShipByCoid(coid);
        if (null != courseShips && courseShips.size() > 0) {
            courseShipDao.deleteCourseShipByCoid(coid);
        }
    }

    @Override
    @CacheEvict(value = {"courseArticleCache","courseArticleCaches"}, allEntries = true, beforeInvocation = true)
    public void updateCourseByCoid(CourseDomain course) {
        if (null != course && null != course.getCoid()) {
            courseDao.updateCourseArticleById(course);
        }
    }

    @Override
    @Cacheable(value = "courseArticleCache", key = "'courseArticleByCategory_' + #p0")
    public List<CourseDomain> getCourseArticleByCategory(String category) {
        if (null == category)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return courseDao.getCourseArticleByCategory(category);
    }

    @Override
    @Cacheable(value = "courseArticleCache", key = "'courseArticleByTags_'+ #p0")
    public List<CourseDomain> getCourseArticleByTags(MetaDomain tags) {
        if (null == tags)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        List<CourseShipDomain> courseShip = courseShipDao.getCourseShipByMid(tags.getMid());
        if (null != courseShip && courseShip.size() > 0) {
            return courseDao.getCourseArticleByTags(courseShip);
        }
        return null;
    }
}
