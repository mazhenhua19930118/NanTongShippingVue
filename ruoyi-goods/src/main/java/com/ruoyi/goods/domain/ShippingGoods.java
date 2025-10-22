package com.ruoyi.goods.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 货源信息对象 shipping_goods
 * 
 * @author ruoyi
 * @date 2025-08-19
 */
public class ShippingGoods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** 托运单位 */
    @Excel(name = "托运单位")
    private String shippingUnit;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 海伦号 */
    @Excel(name = "海伦号")
    private String helenNumber;

    /** 船号 */
    @Excel(name = "船号")
    private String shipNumber;

    /** 中转港 */
    @Excel(name = "中转港")
    private String transitPort;

    /** 目的港 */
    @Excel(name = "目的港")
    private String destinationPort;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;

    /** 1吨 2千克 */
    @Excel(name = "1吨 2千克")
    private String weightUnit;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 货物 */
    @Excel(name = "货物")
    private String goods;

    /** 发布者 */
    @Excel(name = "发布者")
    private String publishBy;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 到港时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到港时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arrivalTime;

    @Excel(name = "特殊场景类型")
    private String specialCases;

    @Excel(name = "特殊原因")
    private String specialCaseReason;

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getSpecialCases() {
        return specialCases;
    }

    public void setSpecialCases(String specialCases) {
        this.specialCases = specialCases;
    }

    public String getSpecialCaseReason() {
        return specialCaseReason;
    }

    public void setSpecialCaseReason(String specialCaseReason) {
        this.specialCaseReason = specialCaseReason;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setShippingUnit(String shippingUnit) 
    {
        this.shippingUnit = shippingUnit;
    }

    public String getShippingUnit() 
    {
        return shippingUnit;
    }

    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }

    public void setHelenNumber(String helenNumber) 
    {
        this.helenNumber = helenNumber;
    }

    public String getHelenNumber() 
    {
        return helenNumber;
    }

    public void setShipNumber(String shipNumber) 
    {
        this.shipNumber = shipNumber;
    }

    public String getShipNumber() 
    {
        return shipNumber;
    }

    public void setTransitPort(String transitPort) 
    {
        this.transitPort = transitPort;
    }

    public String getTransitPort() 
    {
        return transitPort;
    }

    public void setDestinationPort(String destinationPort) 
    {
        this.destinationPort = destinationPort;
    }

    public String getDestinationPort() 
    {
        return destinationPort;
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

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setGoods(String goods) 
    {
        this.goods = goods;
    }

    public String getGoods() 
    {
        return goods;
    }

    public void setPublishBy(String publishBy) 
    {
        this.publishBy = publishBy;
    }

    public String getPublishBy() 
    {
        return publishBy;
    }

    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shippingUnit", getShippingUnit())
            .append("orderId", getOrderId())
            .append("helenNumber", getHelenNumber())
            .append("shipNumber", getShipNumber())
            .append("transitPort", getTransitPort())
            .append("destinationPort", getDestinationPort())
            .append("weight", getWeight())
            .append("weightUnit", getWeightUnit())
            .append("status", getStatus())
            .append("goods", getGoods())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("publishBy", getPublishBy())
            .append("publishTime", getPublishTime())
            .toString();
    }
}
