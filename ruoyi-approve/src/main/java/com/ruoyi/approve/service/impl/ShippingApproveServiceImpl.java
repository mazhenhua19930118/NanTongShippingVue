package com.ruoyi.approve.service.impl;

import java.util.List;

import com.ruoyi.approve.domain.Dispatch;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.ApproveStatus;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtil;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.mapper.ShippingTaskMapper;
import com.ruoyi.task.service.impl.ShippingTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.approve.mapper.ShippingApproveMapper;
import com.ruoyi.approve.domain.ShippingApprove;
import com.ruoyi.approve.service.IShippingApproveService;
import org.springframework.util.CollectionUtils;

import static org.apache.commons.lang3.SystemUtils.getUserName;

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
    @Autowired
    private ShippingTaskMapper shippingTaskMapper;
    @Autowired
    private ShippingTaskServiceImpl shippingTaskService;

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
    public AjaxResult updateShippingApprove(ShippingApprove shippingApprove) {
        ShippingTask shippingTask = shippingTaskMapper.selectShippingTaskByRelatedId(shippingApprove.getOrderId());
        if (shippingTask == null) {
            return AjaxResult.error("任务不存在！");
        }
        //审批通过
        if (StringUtil.isEquals(shippingApprove.getApproveStatus(), ApproveStatus.YES.getCode())) {
            if (StringUtil.isEquals(shippingTask.getTaskStatus(), TaskStatus.publish.getCode())) {
                Dispatch shippingDispatch = shippingApproveMapper.selectShippingDispatch(shippingApprove.getOrderId());
                if (shippingDispatch == null) {
                    return AjaxResult.error("任务不存在！");
                }
                //需要签订子合同
                if (StringUtil.isEquals(shippingDispatch.getIsNeedSubSign(), "1")) {
                    //有航费和定金
                    if (shippingDispatch.getDeposit() != null && shippingDispatch.getShippingCost() != null) {
                        shippingTask.setTaskStatus(TaskStatus.shipping_fee_deposit_paid.getCode());
                        shippingTaskService.generateTaskAndDetails(shippingTask);
                    }else if(shippingDispatch.getDeposit() != null){
                        shippingTask.setTaskStatus(TaskStatus.deposit_paid.getCode());
                        shippingTaskService.generateTaskAndDetails(shippingTask);
                    }else if(shippingDispatch.getShippingCost() != null){
                        shippingTask.setTaskStatus(TaskStatus.shipping_fee_paid.getCode());
                        shippingTaskService.generateTaskAndDetails(shippingTask);
                    }
                }
                shippingTaskService.generateTaskAndDetails(shippingTask);
                shippingTask.setTaskStatus(TaskStatus.wait_pre_insurance.getCode());
                shippingTaskService.updateShippingTask(shippingTask);
            }else if(StringUtil.isEquals(shippingTask.getTaskStatus(), TaskStatus.special_handling_for_unloading.getCode())){
                shippingTaskService.generateTaskAndDetails(shippingTask);
                shippingTask.setTaskStatus(TaskStatus.wait_unloaded.getCode());
                shippingTaskService.updateShippingTask(shippingTask);
            }else if(StringUtil.isEquals(shippingTask.getTaskStatus(), TaskStatus.wait_paid.getCode())){
                shippingTask.setTaskStatus(TaskStatus.paid.getCode());
                shippingTaskService.generateTaskAndDetails(shippingTask);
            }

        }
        shippingApprove.setApproveTime(DateUtils.getNowDate());
        shippingApprove.setApproveUser(SecurityUtils.getUsername());
        shippingApproveMapper.updateShippingApprove(shippingApprove);
        return AjaxResult.success();
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
