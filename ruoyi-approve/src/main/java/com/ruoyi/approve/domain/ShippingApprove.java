package com.ruoyi.approve.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 审批对象 shipping_approve
 * 
 * @author ruoyi
 * @date 2025-10-21
 */
public class ShippingApprove extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applicantUser;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applicantTime;

    /** 申请原因 */
    @Excel(name = "申请原因")
    private String applicantReason;

    /** 审批原因 */
    @Excel(name = "审批原因")
    private String approveReason;

    /** 审批人 */
    @Excel(name = "审批人")
    private String approveUser;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approveTime;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private String approveStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }

    public void setApplicantUser(String applicantUser) 
    {
        this.applicantUser = applicantUser;
    }

    public String getApplicantUser() 
    {
        return applicantUser;
    }

    public void setApplicantTime(Date applicantTime) 
    {
        this.applicantTime = applicantTime;
    }

    public Date getApplicantTime() 
    {
        return applicantTime;
    }

    public void setApplicantReason(String applicantReason) 
    {
        this.applicantReason = applicantReason;
    }

    public String getApplicantReason() 
    {
        return applicantReason;
    }

    public void setApproveReason(String approveReason) 
    {
        this.approveReason = approveReason;
    }

    public String getApproveReason() 
    {
        return approveReason;
    }

    public void setApproveUser(String approveUser) 
    {
        this.approveUser = approveUser;
    }

    public String getApproveUser() 
    {
        return approveUser;
    }

    public void setApproveTime(Date approveTime) 
    {
        this.approveTime = approveTime;
    }

    public Date getApproveTime() 
    {
        return approveTime;
    }

    public void setApproveStatus(String approveStatus) 
    {
        this.approveStatus = approveStatus;
    }

    public String getApproveStatus() 
    {
        return approveStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("applicantUser", getApplicantUser())
            .append("applicantTime", getApplicantTime())
            .append("applicantReason", getApplicantReason())
            .append("approveReason", getApproveReason())
            .append("approveUser", getApproveUser())
            .append("approveTime", getApproveTime())
            .append("approveStatus", getApproveStatus())
            .toString();
    }
}
