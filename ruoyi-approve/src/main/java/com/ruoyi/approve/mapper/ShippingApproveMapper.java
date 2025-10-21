package com.ruoyi.approve.mapper;

import java.util.List;
import com.ruoyi.approve.domain.ShippingApprove;

/**
 * 审批Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-21
 */
public interface ShippingApproveMapper 
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
     * 删除审批
     * 
     * @param id 审批主键
     * @return 结果
     */
    public int deleteShippingApproveById(Long id);

    /**
     * 批量删除审批
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShippingApproveByIds(Long[] ids);
}
