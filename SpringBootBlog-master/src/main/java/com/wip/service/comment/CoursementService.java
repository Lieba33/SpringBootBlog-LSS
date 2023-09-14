/**
 * Created by IntelliJ IDEA.
 * User: lss
 * DateTime: 2023/9/14 13:40
 **/
package com.wip.service.comment;

import com.github.pagehelper.PageInfo;
import com.wip.dto.cond.CoursementCond;
import com.wip.model.CoursementDomain;

import java.util.List;

/**
 * 教程评论相关Service接口
 */
public interface CoursementService {
    /**
     * 添加评论
     * @param coursements
     */
    void addCoursement(CoursementDomain coursements);

    /**
     * 通过教程ID获取评论
     * @param couid
     * @return
     */
    List<CoursementDomain> getCoursementByCId(Integer couid);

    /**
     * 根据条件获取评论列表
     * @param coursementCond   查询条件
     * @param pageNum       分页参数 第几页
     * @param pageSize      分页参数 每页条数
     * @return
     */
    PageInfo<CoursementDomain> getCoursementsByCond(CoursementCond coursementCond, int pageNum, int pageSize);

    /**
     * 通过ID获取评论
     * @param coid
     * @return
     */
    CoursementDomain getCoursementById(Integer coid);

    /**
     * 更新评论状态
     * @param coid
     * @param status
     */
    void updateCoursementStatus(Integer coid, String status);

    /**
     * 删除评论
     * @param id
     */
    void deleteCoursement(Integer id);
}
