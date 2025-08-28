package com.ruoyi.dispatch.service;

import java.util.List;
import com.ruoyi.dispatch.domain.ShippingDispatch;

/**
 * 调度管理Service接口
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
public interface IShippingDispatchService 
{
    /**
     * 查询调度管理
     * 
     * @param id 调度管理主键
     * @return 调度管理
     */
    public ShippingDispatch selectShippingDispatchById(Long id);

    /**
     * 查询调度管理列表
     * 
     * @param shippingDispatch 调度管理
     * @return 调度管理集合
     */
    public List<ShippingDispatch> selectShippingDispatchList(ShippingDispatch shippingDispatch);

    /**
     * 新增调度管理
     * 
     * @param shippingDispatch 调度管理
     * @return 结果
     */
    public int insertShippingDispatch(ShippingDispatch shippingDispatch);

    /**
     * 修改调度管理
     * 
     * @param shippingDispatch 调度管理
     * @return 结果
     */
    public int updateShippingDispatch(ShippingDispatch shippingDispatch);

    /**
     * 批量删除调度管理
     * 
     * @param ids 需要删除的调度管理主键集合
     * @return 结果
     */
    public int deleteShippingDispatchByIds(Long[] ids);

    /**
     * 删除调度管理信息
     * 
     * @param id 调度管理主键
     * @return 结果
     */
    public int deleteShippingDispatchById(Long id);
}
