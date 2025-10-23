package com.ruoyi.task.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 财务管理对象 shipping_finance
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
public class FinanceDto extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 是否预付款 0否 1是 */
    @Excel(name = "是否预付款 0否 1是")
    private String isAdvancePayment;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;

    /** 重量单位 1吨 2千克 */
    @Excel(name = "重量单位 1吨 2千克")
    private String weightUnit;

    /** 子订单价格 */
    @Excel(name = "子订单价格")
    private BigDecimal subOrderPrice;

    /** 主订单价格 */
    @Excel(name = "主订单价格")
    private BigDecimal mainOrderPrice;

    /** 超耗重量 */
    @Excel(name = "超耗重量")
    private BigDecimal aboveLimitWeight;

    /** 超耗重量单位 */
    @Excel(name = "超耗重量单位")
    private String aboveLimitWeightUnit;

    /** 货物价格 */
    @Excel(name = "货物价格")
    private BigDecimal goodsPrice;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal paymentAmount;

    /** 结算金额 */
    @Excel(name = "结算金额")
    private BigDecimal settlementAmount;

    /** 延期费支付金额 */
    @Excel(name = "延期费支付金额")
    private BigDecimal deferredPayment;

    /** 延期费结算金额 */
    @Excel(name = "延期费结算金额")
    private BigDecimal deferredSettlement;

    /** 是否上传卸货管理明细 */
    @Excel(name = "是否上传卸货管理明细")
    private String isUploadDetailsManagement;

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

    public void setIsAdvancePayment(String isAdvancePayment) 
    {
        this.isAdvancePayment = isAdvancePayment;
    }

    public String getIsAdvancePayment() 
    {
        return isAdvancePayment;
    }

    public void setWeight(BigDecimal weight) 
    {
        this.weight = weight;
    }

    public BigDecimal getWeight() 
    {
        return weight;
    }

    public void setWeightUnit(String weightUnit) 
    {
        this.weightUnit = weightUnit;
    }

    public String getWeightUnit() 
    {
        return weightUnit;
    }

    public void setSubOrderPrice(BigDecimal subOrderPrice) 
    {
        this.subOrderPrice = subOrderPrice;
    }

    public BigDecimal getSubOrderPrice() 
    {
        return subOrderPrice;
    }

    public void setMainOrderPrice(BigDecimal mainOrderPrice) 
    {
        this.mainOrderPrice = mainOrderPrice;
    }

    public BigDecimal getMainOrderPrice() 
    {
        return mainOrderPrice;
    }

    public void setAboveLimitWeight(BigDecimal aboveLimitWeight) 
    {
        this.aboveLimitWeight = aboveLimitWeight;
    }

    public BigDecimal getAboveLimitWeight() 
    {
        return aboveLimitWeight;
    }

    public void setAboveLimitWeightUnit(String aboveLimitWeightUnit) 
    {
        this.aboveLimitWeightUnit = aboveLimitWeightUnit;
    }

    public String getAboveLimitWeightUnit() 
    {
        return aboveLimitWeightUnit;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) 
    {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsPrice() 
    {
        return goodsPrice;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) 
    {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() 
    {
        return paymentAmount;
    }

    public void setSettlementAmount(BigDecimal settlementAmount) 
    {
        this.settlementAmount = settlementAmount;
    }

    public BigDecimal getSettlementAmount() 
    {
        return settlementAmount;
    }

    public void setDeferredPayment(BigDecimal deferredPayment) 
    {
        this.deferredPayment = deferredPayment;
    }

    public BigDecimal getDeferredPayment() 
    {
        return deferredPayment;
    }

    public void setDeferredSettlement(BigDecimal deferredSettlement) 
    {
        this.deferredSettlement = deferredSettlement;
    }

    public BigDecimal getDeferredSettlement() 
    {
        return deferredSettlement;
    }

    public void setIsUploadDetailsManagement(String isUploadDetailsManagement) 
    {
        this.isUploadDetailsManagement = isUploadDetailsManagement;
    }

    public String getIsUploadDetailsManagement() 
    {
        return isUploadDetailsManagement;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("isAdvancePayment", getIsAdvancePayment())
            .append("weight", getWeight())
            .append("weightUnit", getWeightUnit())
            .append("subOrderPrice", getSubOrderPrice())
            .append("mainOrderPrice", getMainOrderPrice())
            .append("aboveLimitWeight", getAboveLimitWeight())
            .append("aboveLimitWeightUnit", getAboveLimitWeightUnit())
            .append("goodsPrice", getGoodsPrice())
            .append("paymentAmount", getPaymentAmount())
            .append("settlementAmount", getSettlementAmount())
            .append("deferredPayment", getDeferredPayment())
            .append("deferredSettlement", getDeferredSettlement())
            .append("isUploadDetailsManagement", getIsUploadDetailsManagement())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
