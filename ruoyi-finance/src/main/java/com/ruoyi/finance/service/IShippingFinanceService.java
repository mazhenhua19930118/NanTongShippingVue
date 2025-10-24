package com.ruoyi.finance.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.finance.domain.ShippingFinance;

/**
 * 财务管理Service接口
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
public interface IShippingFinanceService 
{
    /**
     * 查询财务管理
     * 
     * @param id 财务管理主键
     * @return 财务管理
     */
    public ShippingFinance selectShippingFinanceById(Long id);

    /**
     * 查询财务管理列表
     * 
     * @param shippingFinance 财务管理
     * @return 财务管理集合
     */
    public List<ShippingFinance> selectShippingFinanceList(ShippingFinance shippingFinance);

    /**
     * 新增财务管理
     * 
     * @param shippingFinance 财务管理
     * @return 结果
     */
    public int insertShippingFinance(ShippingFinance shippingFinance);

    /**
     * 修改财务管理
     * 
     * @param shippingFinance 财务管理
     * @return 结果
     */
    public AjaxResult updateShippingFinance(ShippingFinance shippingFinance);
    public AjaxResult reportUnloadingData(ShippingFinance shippingFinance);
    public AjaxResult settlement(ShippingFinance shippingFinance);

    /**
     * 批量删除财务管理
     * 
     * @param ids 需要删除的财务管理主键集合
     * @return 结果
     */
    public int deleteShippingFinanceByIds(Long[] ids);

    /**
     * 删除财务管理信息
     * 
     * @param id 财务管理主键
     * @return 结果
     */
    public int deleteShippingFinanceById(Long id);
}
