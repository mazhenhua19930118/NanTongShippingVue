package com.ruoyi.task.mapper;

import com.ruoyi.common.dto.ShippingContractDto;
import com.ruoyi.task.domain.FinanceDto;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.domain.ShippingTaskDetail;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2025-04-08
 */
public interface ShippingTaskDetailMapper
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ShippingTaskDetail selectShippingTaskDetailById(Long id);

    public List<ShippingTaskDetail> selectShippingTaskDetailByTaskId(Long taskId);

    public List<ShippingTaskDetail> selectShippingTaskDetailByRelatedId(String orderId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @return 【请填写功能名称】集合
     */
    public List<ShippingTaskDetail> selectShippingTaskDetailList(ShippingTaskDetail shippingTaskDetail);
    public List<ShippingTaskDetail> selectShippingTaskDetailListWithTask(ShippingTaskDetail shippingTaskDetail);

    /**
     * 新增【请填写功能名称】
     *
     * @return 结果
     */
    public int insertShippingTaskDetail(ShippingTaskDetail shippingTaskDetail);


    /**
     * 修改【请填写功能名称】
     *
     * @return 结果
     */
    public int updateShippingTaskDetail(ShippingTaskDetail shippingTaskDetail);



    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteShippingTaskDetailById(Long id);

    public int deleteShippingTaskDetailByGoodsId(Long goodsId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShippingTaskDetailByIds(Long[] ids);
}
