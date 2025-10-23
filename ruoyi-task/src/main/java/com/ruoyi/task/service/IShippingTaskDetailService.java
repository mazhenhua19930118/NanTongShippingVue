package com.ruoyi.task.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.domain.ShippingTaskDetail;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2025-04-08
 */
public interface IShippingTaskDetailService
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ShippingTaskDetail selectShippingTaskDetailById(Long id);

    public  List<ShippingTaskDetail> selectShippingTaskDetailByTaskId(Long taskId);

    public List<ShippingTaskDetail> selectShippingTaskDetailByRelatedId(String goodsId);

    public List<ShippingTaskDetail> selectShippingTaskDetailList(ShippingTaskDetail shippingTaskDetail);
    public List<ShippingTaskDetail> selectShippingTaskDetailListWithTask(ShippingTaskDetail shippingTaskDetail);

    public int insertShippingTaskDetail(ShippingTaskDetail shippingTaskDetail);

    /**
     * 修改【请填写功能名称】
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 结果
     */
    public int updateShippingTaskDetail(ShippingTaskDetail shippingTask);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteShippingTaskDetailByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteShippingTaskDetailById(Long id);

    public AjaxResult checkPreviousTaskNode(ShippingTaskDetail shippingTaskDetail);
}
