package com.ruoyi.approve.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.approve.mapper.ShippingApproveMapper;
import com.ruoyi.approve.domain.ShippingApprove;
import com.ruoyi.approve.service.IShippingApproveService;

/**
 * 审批Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-21
 */
@Service
public class ShippingApproveServiceImpl implements IShippingApproveService 
{
    @Autowired
    private ShippingApproveMapper shippingApproveMapper;

    /**
     * 查询审批
     * 
     * @param id 审批主键
     * @return 审批
     */
    @Override
    public ShippingApprove selectShippingApproveById(Long id)
    {
        return shippingApproveMapper.selectShippingApproveById(id);
    }

    /**
     * 查询审批列表
     * 
     * @param shippingApprove 审批
     * @return 审批
     */
    @Override
    public List<ShippingApprove> selectShippingApproveList(ShippingApprove shippingApprove)
    {
        return shippingApproveMapper.selectShippingApproveList(shippingApprove);
    }

    /**
     * 新增审批
     * 
     * @param shippingApprove 审批
     * @return 结果
     */
    @Override
    public int insertShippingApprove(ShippingApprove shippingApprove)
    {
        return shippingApproveMapper.insertShippingApprove(shippingApprove);
    }

    /**
     * 修改审批
     * 
     * @param shippingApprove 审批
     * @return 结果
     */
    @Override
    public int updateShippingApprove(ShippingApprove shippingApprove)
    {
        return shippingApproveMapper.updateShippingApprove(shippingApprove);
    }

    /**
     * 批量删除审批
     * 
     * @param ids 需要删除的审批主键
     * @return 结果
     */
    @Override
    public int deleteShippingApproveByIds(Long[] ids)
    {
        return shippingApproveMapper.deleteShippingApproveByIds(ids);
    }

    /**
     * 删除审批信息
     * 
     * @param id 审批主键
     * @return 结果
     */
    @Override
    public int deleteShippingApproveById(Long id)
    {
        return shippingApproveMapper.deleteShippingApproveById(id);
    }
}
