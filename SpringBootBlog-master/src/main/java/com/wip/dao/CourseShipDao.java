/**
 * Created by IntelliJ IDEA.
 * User: lss
 * DateTime: 2023/9/6 10:40
 **/
package com.wip.dao;

import com.wip.model.CourseShipDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教程和项目关联表
 */
@Mapper
public interface CourseShipDao {

    /**
     * 根据meta编号获取关联
     * @param mid
     * @return
     */
    List<CourseShipDomain> getCourseShipByMid(Integer mid);

    /**
     * 根据meta编号删除关联
     * @param mid
     */
    void deleteCourseShipByMid(Integer mid);

    /**
     * 获取数量
     * @param coid
     * @param mid
     * @return
     */
    Long getCourseCountById(@Param("coid") Integer coid, @Param("mid") Integer mid);

    /**
     * 添加
     * @param courseShip
     * @return
     */
    int addCourseShip(CourseShipDomain courseShip);

    /**
     * 根据教程编号删除关联
     * @param coid
     */
    void deleteCourseShipByCoid(int coid);

    /**
     * 根据教程ID获取关联
     * @param coid
     */
    List<CourseShipDomain> getCourseShipByCoid(Integer coid);
}
