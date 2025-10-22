package com.ruoyi.task.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.upload.domain.ShippingUpload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 【请填写功能名称】对象 shipping_task
 * 
 * @author ruoyi
 * @date 2025-04-08
 */
public class ShippingTaskDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 货源编号 */
    @Excel(name = "订单编号")
    private String orderId;

    @Excel(name = "任务编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long taskId;

    /** 负责人id */
    @Excel(name = "负责人id")
    private Long director;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long creator;

    private String userName;

    /** 描述 */
    private String desc;
    private String cameraInstructions;//摄像头说明

    public String getCameraInstructions() {
        return cameraInstructions;
    }

    public void setCameraInstructions(String cameraInstructions) {
        this.cameraInstructions = cameraInstructions;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getDirector() {
        return director;
    }

    public void setDirector(Long director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "ShippingTaskDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", taskId=" + taskId +
                ", director=" + director +
                '}';
    }
}
