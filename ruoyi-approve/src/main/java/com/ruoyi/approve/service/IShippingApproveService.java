package com.ruoyi.approve.service;

import java.util.List;
import com.ruoyi.approve.domain.ShippingApprove;

/**
 * 审批Service接口
 * 
 * @author ruoyi
 * @date 2025-10-21
 */
public interface IShippingApproveService 
{
    /**
     * 查询审批
     * 
     * @param id 审批主键
     * @return 审批
     */
    public ShippingApprove selectShippingApproveById(Long id);

    /**
     * 查询审批列表
     * 
     * @param shippingApprove 审批
     * @return 审批集合
     */
    public List<ShippingApprove> selectShippingApproveList(ShippingApprove shippingApprove);

    /**
     * 新增审批
     * 
     * @param shippingApprove 审批
     * @return 结果
     */
    public int insertShippingApprove(ShippingApprove shippingApprove);

    /**
     * 修改审批
     * 
     * @param shippingApprove 审批
     * @return 结果
     */
    public int updateShippingApprove(ShippingApprove shippingApprove);

    /**
     * 批量删除审批
     * 
     * @param ids 需要删除的审批主键集合
     * @return 结果
     */
    public int deleteShippingApproveByIds(Long[] ids);

    /**
     * 删除审批信息
     * 
     * @param id 审批主键
     * @return 结果
     */
    public int deleteShippingApproveById(Long id);
}
