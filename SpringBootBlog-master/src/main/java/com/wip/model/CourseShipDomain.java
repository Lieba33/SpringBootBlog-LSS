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
    private Integer couid;

    /**
     * 项目编号
     */
    private Integer mid;

    public Integer getCouid() {
        return couid;
    }

    public void setCouid(Integer couid) {
        this.couid = couid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }
}
