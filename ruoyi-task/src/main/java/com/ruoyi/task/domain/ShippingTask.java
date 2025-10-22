package com.ruoyi.task.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ShippingTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 货源编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 1001 创建 1002 发布 1003 派单 */
    @Excel(name = "1001 创建 1002 发布 1003 派单")
    private String taskStatus;

    /** 分配人员id */
    @Excel(name = "分配人员id")
    private Long assignUserId;

    /** 分配人员姓名 */
    private String assignUserName;

    /** 分配人员昵称 */
    private String assignUserNickName;

    /** 图片信息 */
    private List<ShippingUpload> shippingUploadList;

    /** 任务是否终止  显式控制 JSON名*/
    @JsonProperty("isTermination")
    private Boolean isTermination ;

    private String cameraInstructions;

    public String getCameraInstructions() {
        return cameraInstructions;
    }

    public void setCameraInstructions(String cameraInstructions) {
        this.cameraInstructions = cameraInstructions;
    }

    public Boolean getTermination() {
        return isTermination;
    }

    public void setTermination(Boolean termination) {
        isTermination = termination;
    }

    public List<ShippingUpload> getShippingUploadList() {
        return shippingUploadList;
    }

    public void setShippingUploadList(List<ShippingUpload> shippingUploadList) {
        this.shippingUploadList = shippingUploadList;
    }

    public String getAssignUserName() {
        return assignUserName;
    }

    public void setAssignUserName(String assignUserName) {
        this.assignUserName = assignUserName;
    }

    public String getAssignUserNickName() {
        return assignUserNickName;
    }

    public void setAssignUserNickName(String assignUserNickName) {
        this.assignUserNickName = assignUserNickName;
    }


    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    @Override
    public void setTaskStatus(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    @Override
    public String getTaskStatus()
    {
        return taskStatus;
    }
    public void setAssignUserId(Long assignUserId) 
    {
        this.assignUserId = assignUserId;
    }

    public Long getAssignUserId() 
    {
        return assignUserId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("taskStatus", getTaskStatus())
            .append("assignUserId", getAssignUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
