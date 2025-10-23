package com.ruoyi.task.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.vo.GoodsDetailVo;
import com.ruoyi.task.domain.FinanceDto;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.domain.ShippingTaskDetail;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2025-04-08
 */
public interface IShippingTaskService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ShippingTask selectShippingTaskById(Long id);

    public ShippingTask selectShippingTaskByRelatedId(String goodsId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ShippingTask> selectShippingTaskList(ShippingTask shippingTask);

    /**
     * 新增【请填写功能名称】
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 结果
     */
    public int insertShippingTask(ShippingTask shippingTask);

    public void generateTaskAndDetails(ShippingTask shippingTask);
    public void insertFiance(FinanceDto financeDto);

    public void clearTaskAndDetailsByGoodsId(Long goodsId);

    /**
     * 修改【请填写功能名称】
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 结果
     */
    public int updateShippingTask(ShippingTask shippingTask);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteShippingTaskByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteShippingTaskById(Long id);


    public AjaxResult checkPreviousTaskNode(ShippingTask shippingTask);
    public List<ShippingTaskDetail> selectShippingTaskNodeList(ShippingTask shippingTask);

    public GoodsDetailVo selectGoodsDetailByGoodsId(String goodsId);
}
