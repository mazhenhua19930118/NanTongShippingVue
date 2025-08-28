package com.ruoyi.dispatch.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.dispatch.mapper.ShippingDispatchMapper;
import com.ruoyi.dispatch.domain.ShippingDispatch;
import com.ruoyi.dispatch.service.IShippingDispatchService;

/**
 * 调度管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
@Service
public class ShippingDispatchServiceImpl implements IShippingDispatchService 
{
    @Autowired
    private ShippingDispatchMapper shippingDispatchMapper;

    /**
     * 查询调度管理
     * 
     * @param id 调度管理主键
     * @return 调度管理
     */
    @Override
    public ShippingDispatch selectShippingDispatchById(Long id)
    {
        return shippingDispatchMapper.selectShippingDispatchById(id);
    }

    /**
     * 查询调度管理列表
     * 
     * @param shippingDispatch 调度管理
     * @return 调度管理
     */
    @Override
    public List<ShippingDispatch> selectShippingDispatchList(ShippingDispatch shippingDispatch)
    {
        return shippingDispatchMapper.selectShippingDispatchList(shippingDispatch);
    }

    /**
     * 新增调度管理
     * 
     * @param shippingDispatch 调度管理
     * @return 结果
     */
    @Override
    public int insertShippingDispatch(ShippingDispatch shippingDispatch)
    {
        return shippingDispatchMapper.insertShippingDispatch(shippingDispatch);
    }

    /**
     * 修改调度管理
     * 
     * @param shippingDispatch 调度管理
     * @return 结果
     */
    @Override
    public int updateShippingDispatch(ShippingDispatch shippingDispatch)
    {
        return shippingDispatchMapper.updateShippingDispatch(shippingDispatch);
    }

    /**
     * 批量删除调度管理
     * 
     * @param ids 需要删除的调度管理主键
     * @return 结果
     */
    @Override
    public int deleteShippingDispatchByIds(Long[] ids)
    {
        return shippingDispatchMapper.deleteShippingDispatchByIds(ids);
    }

    /**
     * 删除调度管理信息
     * 
     * @param id 调度管理主键
     * @return 结果
     */
    @Override
    public int deleteShippingDispatchById(Long id)
    {
        return shippingDispatchMapper.deleteShippingDispatchById(id);
    }
}
