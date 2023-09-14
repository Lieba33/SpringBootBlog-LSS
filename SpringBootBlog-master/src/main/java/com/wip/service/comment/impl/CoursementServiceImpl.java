/**
 * Created by IntelliJ IDEA.
 * User: lss
 * DateTime: 2023/9/31 14:30
 **/
package com.wip.service.comment.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wip.constant.ErrorConstant;
import com.wip.dao.CoursementDao;
import com.wip.dto.cond.CoursementCond;
import com.wip.exception.BusinessException;
import com.wip.model.CourseDomain;
import com.wip.model.CoursementDomain;
import com.wip.service.article.CourseService;
import com.wip.service.comment.CoursementService;
import com.wip.utils.DateKit;
import com.wip.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CoursementServiceImpl implements CoursementService {

    @Autowired
    private CoursementDao coursementDao;

    @Autowired
    private CourseService courseService;

    private static final Map<String,String> STATUS_MAP = new ConcurrentHashMap<>();

    /**
     * 评论状态：正常
     */
    private static final String STATUS_NORMAL = "approved";
    /**
     * 评论状态：不不显示
     */
    private static final String STATUS_BLANK = "not_audit";

    static {
        STATUS_MAP.put("approved",STATUS_NORMAL);
        STATUS_MAP.put("not_audit",STATUS_BLANK);
    }

    @Override
    @Transactional
    @CacheEvict(value = "coursementCache", allEntries = true)
    public void addCoursement(CoursementDomain coursements) {
        String msg = null;

        if (null == coursements) {
            msg = "评论对象为空";
        }

        if (StringUtils.isBlank(coursements.getAuthor())) {
            coursements.setAuthor("热心网友");
        }
        if (StringUtils.isNotBlank(coursements.getEmail()) && !TaleUtils.isEmail(coursements.getEmail())) {
            msg =  "请输入正确的邮箱格式";
        }
        if (StringUtils.isBlank(coursements.getContent())) {
            msg = "评论内容不能为空";
        }
        if (coursements.getContent().length() < 5 || coursements.getContent().length() > 2000) {
            msg = "评论字数在5-2000个字符";
        }
        if (null == coursements.getCouid()) {
            msg = "评论文章不能为空";
        }
        if (msg != null)
            throw BusinessException.withErrorCode(msg);

        CourseDomain article = courseService.getCourseArticleById(coursements.getCouid());
        if (null == article) {
            throw BusinessException.withErrorCode("该文章不存在");
        }

        coursements.setOwnerId(article.getAuthorId());
        coursements.setStatus(STATUS_MAP.get(STATUS_BLANK));
        coursements.setCreated(DateKit.getCurrentUnixTime());
        coursementDao.addCoursement(coursements);

        CourseDomain temp = new CourseDomain();
        temp.setCouid(article.getCouid());
        Integer count = article.getCoursementsNum();
        if (null == count) {
            count = 0;
        }
        temp.setCoursementsNum(count + 1);
        courseService.updateCourseByCouid(temp);
    }

    @Override
    @Cacheable(value = "coursementCache", key = "'coursementsByCId_' + #p0")
    public List<CoursementDomain> getCoursementByCId(Integer couid) {
        if (null == couid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return coursementDao.getCoursementByCId(couid);
    }

    @Override
    @Cacheable(value = "coursementCache", key = "'coursementsByCond_'+ #p1")
    public PageInfo<CoursementDomain> getCoursementsByCond(CoursementCond coursementCond, int pageNum, int pageSize) {
        if (null == coursementCond)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum,pageSize);
        List<CoursementDomain> coursements = coursementDao.getCoursementsByCond(coursementCond);
        PageInfo<CoursementDomain> pageInfo = new PageInfo<>(coursements);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "coursementCache",key = "'coursementById_' + #p0")
    public CoursementDomain getCoursementById(Integer coid) {
        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return coursementDao.getCoursementById(coid);
    }

    @Override
    @CacheEvict(value = "coursementCache", allEntries = true)
    public void updateCoursementStatus(Integer coid, String status) {
        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        coursementDao.updateCoursementStatus(coid, status);
    }

    @Override
    public void deleteCoursement(Integer coid) {
        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        coursementDao.deleteCoursement(coid);
    }
}
