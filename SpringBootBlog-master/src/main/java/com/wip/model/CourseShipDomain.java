/**
 * Created by IntelliJ IDEA.
 * User: lss
 * DateTime: 2023/9/6 10:30
 **/
package com.wip.model;

/**
 * 教程关联信息表
 */
public class CourseShipDomain {
    /**
     * 教程主键
     */
    private Integer coid;

    /**
     * 项目编号
     */
    private Integer mid;

    public Integer getCoid() {
        return coid;
    }

    public void setCoid(Integer coid) {
        this.coid = coid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }
}
