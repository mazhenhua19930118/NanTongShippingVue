package com.ruoyi.dispatch.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.approve.domain.ShippingApprove;
import com.ruoyi.approve.mapper.ShippingApproveMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.dispatch.mapper.ShippingDispatchMapper;
import com.ruoyi.dispatch.domain.ShippingDispatch;
import com.ruoyi.dispatch.service.IShippingDispatchService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    @Autowired
    private ShippingApproveMapper shippingApproveMapper;

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
    public AjaxResult updateShippingDispatch(ShippingDispatch shippingDispatch)
    {
        ShippingApprove shippingApprove = new ShippingApprove();
        shippingApprove.setOrderId(shippingDispatch.getOrderId());
        List<String> taskList = Arrays.asList(TaskStatus.deposit_paid.getCode(), TaskStatus.shipping_fee_paid.getCode(), TaskStatus.shipping_fee_deposit_paid.getCode());
        shippingApprove.setTaskStatusList(taskList);
        List<ShippingApprove> shippingApproves = shippingApproveMapper.selectShippingApproveList(shippingApprove);
        if(!CollectionUtils.isEmpty(shippingApproves)){
            List<ShippingApprove> approvingList = shippingApproves.stream().filter(v -> v.getApproveStatus() == null).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(approvingList)){
                return AjaxResult.error("该订单正在审批中！暂时无法修改！");
            }
        }
        shippingDispatchMapper.updateShippingDispatch(shippingDispatch);
        return AjaxResult.success();
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
