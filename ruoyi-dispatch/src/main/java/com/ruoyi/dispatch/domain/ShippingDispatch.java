package com.ruoyi.dispatch.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 调度管理对象 shipping_dispatch
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
public class ShippingDispatch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 定金 */
    @Excel(name = "定金")
    private BigDecimal deposit;

    /** 航费 */
    @Excel(name = "航费")
    private BigDecimal shippingCost;

    /** 是否预保险 */
    @Excel(name = "是否需要签订子合同")
    private String isNeedSubSign;

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

    public void setDeposit(BigDecimal deposit) 
    {
        this.deposit = deposit;
    }

    public BigDecimal getDeposit() 
    {
        return deposit;
    }

    public void setShippingCost(BigDecimal shippingCost) 
    {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getShippingCost() 
    {
        return shippingCost;
    }

    public String getIsNeedSubSign() {
        return isNeedSubSign;
    }

    public void setIsNeedSubSign(String isNeedSubSign) {
        this.isNeedSubSign = isNeedSubSign;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("deposit", getDeposit())
            .append("shippingCost", getShippingCost())
            .append("isNeedSubSign", getIsNeedSubSign())
            .toString();
    }
}
