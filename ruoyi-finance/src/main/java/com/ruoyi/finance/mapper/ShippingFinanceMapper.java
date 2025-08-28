package com.ruoyi.finance.mapper;

import java.util.List;
import com.ruoyi.finance.domain.ShippingFinance;

/**
 * 财务管理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
public interface ShippingFinanceMapper 
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
    public int updateShippingFinance(ShippingFinance shippingFinance);

    /**
     * 删除财务管理
     * 
     * @param id 财务管理主键
     * @return 结果
     */
    public int deleteShippingFinanceById(Long id);

    /**
     * 批量删除财务管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShippingFinanceByIds(Long[] ids);
}
