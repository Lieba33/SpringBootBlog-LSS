/**
 * Created by IntelliJ IDEA.
 * User: lss
 * DateTime: 2023/9/6 9:50
 **/
package com.wip.dao;

import com.wip.dto.cond.CourseCond;
import com.wip.model.CourseDomain;
import com.wip.model.CourseShipDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教程相关Dao接口
 */
@Mapper
public interface CourseDao {
    /**
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
     * @return
     */
    List<CourseDomain> getCourseArticleByCond(CourseCond courseCond);

    /**
     * 删除教程
     * @param couid
     */
    void deleteCourseArticleById(Integer couid);

    /**
     * 获取教程总数
     * @return
     */
    Long getCourseArticleCount();

    /**
     * 通过分类名获取教程
     * @param category
     * @return
     */
    List<CourseDomain> getCourseArticleByCategory(@Param("category") String category);

    /**
     * 通过标签获取教程
     * @param couid
     * @return
     */
    List<CourseDomain> getCourseArticleByTags(List<CourseShipDomain> couid);

}
