/**
 * Created by IntelliJ IDEA.
 * User: lss
 * DateTime: 2023/9/13 16:00
 **/
package com.wip.dao;

import com.wip.dto.cond.CoursementCond;
import com.wip.model.CoursementDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教程评论相关Dao接口
 */
@Mapper
public interface CoursementDao {

    /**
     * 添加评论
     * @param coursements
     */
    void addCoursement(CoursementDomain coursements);

    /**
     * 根据教程ID获取评论
     * @param couid
     * @return
     */
    List<CoursementDomain> getCoursementByCId(@Param("couid") Integer couid);


    /**
     * 删除教程评论
     * @param coid
     */
    void deleteCoursement(@Param("coid") Integer coid);

    /**
     * 获取评论总数
     * @return
     */
    Long getCoursementCount();

    /**
     * 根据条件获取评论列表
     * @param coursementCond
     * @return
     */
    List<CoursementDomain> getCoursementsByCond(CoursementCond coursementCond);

    /**
     * 通过ID获取评论
     * @param coid
     * @return
     */
    CoursementDomain getCoursementById(@Param("coid") Integer coid);

    /**
     * 更新评论状态
     * @param coid
     * @param status
     */
    void updateCoursementStatus(@Param("coid") Integer coid, @Param("status") String status);
}
