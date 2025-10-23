package com.ruoyi.task.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.dto.ShippingContractDto;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtil;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.domain.ShippingTaskDetail;
import com.ruoyi.task.mapper.ShippingTaskDetailMapper;
import com.ruoyi.task.mapper.ShippingTaskMapper;
import com.ruoyi.task.service.IShippingTaskDetailService;
import com.ruoyi.task.service.IShippingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-04-08
 */
@Service
public class ShippingTaskDetailServiceImpl implements IShippingTaskDetailService
{
    @Autowired
    private ShippingTaskDetailMapper shippingTaskDetailMapper;

    @Override
    public ShippingTaskDetail selectShippingTaskDetailById(Long id) {
        return shippingTaskDetailMapper.selectShippingTaskDetailById(id);
    }

    @Override
    public  List<ShippingTaskDetail> selectShippingTaskDetailByTaskId(Long taskId) {
        return shippingTaskDetailMapper.selectShippingTaskDetailByTaskId(taskId);
    }

    @Override
    public List<ShippingTaskDetail> selectShippingTaskDetailByRelatedId(String goodsId) {
        return shippingTaskDetailMapper.selectShippingTaskDetailByRelatedId(goodsId);
    }

    @Override
    public List<ShippingTaskDetail> selectShippingTaskDetailList(ShippingTaskDetail shippingTaskDetail) {
        return shippingTaskDetailMapper.selectShippingTaskDetailList(shippingTaskDetail);
    }

    @Override
    public List<ShippingTaskDetail> selectShippingTaskDetailListWithTask(ShippingTaskDetail shippingTaskDetail) {
        return shippingTaskDetailMapper.selectShippingTaskDetailListWithTask(shippingTaskDetail);
    }

    @Override
    public int insertShippingTaskDetail(ShippingTaskDetail shippingTaskDetail) {
        return shippingTaskDetailMapper.insertShippingTaskDetail(shippingTaskDetail);
    }

    @Override
    public int updateShippingTaskDetail(ShippingTaskDetail shippingTaskDetail) {
        return shippingTaskDetailMapper.updateShippingTaskDetail(shippingTaskDetail);
    }

    @Override
    public int deleteShippingTaskDetailByIds(Long[] ids) {
        return shippingTaskDetailMapper.deleteShippingTaskDetailByIds(ids);
    }

    @Override
    public int deleteShippingTaskDetailById(Long id) {
        return shippingTaskDetailMapper.deleteShippingTaskDetailById(id);
    }

    @Override
    public AjaxResult checkPreviousTaskNode(ShippingTaskDetail shippingTaskDetail) {
        if (StringUtil.isNullOrEmpty(shippingTaskDetail.getTaskStatus())) {
            return AjaxResult.error("任务类型不存在");
        }
        List<ShippingTaskDetail> shippingTaskDetailList = shippingTaskDetailMapper.selectShippingTaskDetailByRelatedId(shippingTaskDetail.getOrderId());
        if (CollectionUtils.isEmpty(shippingTaskDetailList)) {
            return AjaxResult.error("暂无任务");
        }
        if(StringUtil.isEquals(TaskStatus.create.getCode(),shippingTaskDetail.getTaskStatus())){
            return AjaxResult.success("");
        }
        if(StringUtil.isEquals(TaskStatus.termination.getCode(),shippingTaskDetail.getTaskStatus())){
            return AjaxResult.success("");
        }
        List<Long> taskList = shippingTaskDetailList.stream().map(item -> StringUtil.convertStringToLong(item.getTaskStatus())).collect(Collectors.toList());
        Long current = StringUtil.convertStringToLong(shippingTaskDetail.getTaskStatus());
        Long previous = StringUtil.convertStringToLong(shippingTaskDetail.getTaskStatus()) - 1;
        Long latter = StringUtil.convertStringToLong(shippingTaskDetail.getTaskStatus()) + 1;
        //如果当前节点是已装货 则再减1 去掉终止状态
        if(StringUtil.isEquals(TaskStatus.loading.getCode(),shippingTaskDetail.getTaskStatus())){
            previous = previous -1;
        }
        if (!taskList.contains(previous)) {
            String previousTaskStatus = StringUtil.convertLongToString(previous);
            String taskName = TaskStatus.getDescByCode(previousTaskStatus);
            return AjaxResult.error("上一个流程【" + taskName + "】未结束，请先走完【" + taskName + "】流程！");
        }

        if (taskList.contains(latter)) {
            String latterTaskStatus = StringUtil.convertLongToString(latter);
            String currentStatus = StringUtil.convertLongToString(current);
            String taskName = TaskStatus.getDescByCode(latterTaskStatus);
            String currentTaskName = TaskStatus.getDescByCode(currentStatus);
            return AjaxResult.error("下一个流程【" + taskName + "】已结束，不能修改当前【"+currentTaskName+"】流程！");
        }
        return AjaxResult.success("");
    }
}
