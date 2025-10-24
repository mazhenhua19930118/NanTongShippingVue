package com.ruoyi.finance.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.ruoyi.approve.domain.ShippingApprove;
import com.ruoyi.approve.mapper.ShippingApproveMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.CommonUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtil;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.service.impl.ShippingTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.finance.mapper.ShippingFinanceMapper;
import com.ruoyi.finance.domain.ShippingFinance;
import com.ruoyi.finance.service.IShippingFinanceService;
import org.springframework.util.CollectionUtils;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 财务管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
@Service
public class ShippingFinanceServiceImpl implements IShippingFinanceService 
{
    @Autowired
    private ShippingFinanceMapper shippingFinanceMapper;
    @Autowired
    private ShippingTaskServiceImpl shippingTaskService;
    @Autowired
    private ShippingApproveMapper shippingApproveMapper;

    /**
     * 查询财务管理
     * 
     * @param id 财务管理主键
     * @return 财务管理
     */
    @Override
    public ShippingFinance selectShippingFinanceById(Long id)
    {
        return shippingFinanceMapper.selectShippingFinanceById(id);
    }

    /**
     * 查询财务管理列表
     * 
     * @param shippingFinance 财务管理
     * @return 财务管理
     */
    @Override
    public List<ShippingFinance> selectShippingFinanceList(ShippingFinance shippingFinance)
    {
        return shippingFinanceMapper.selectShippingFinanceList(shippingFinance);
    }

    /**
     * 新增财务管理
     * 
     * @param shippingFinance 财务管理
     * @return 结果
     */
    @Override
    public int insertShippingFinance(ShippingFinance shippingFinance)
    {
        shippingFinance.setCreateTime(DateUtils.getNowDate());
        return shippingFinanceMapper.insertShippingFinance(shippingFinance);
    }

    /**
     * 修改财务管理
     * 
     * @param shippingFinance 财务管理
     * @return 结果
     */
    @Override
    public AjaxResult updateShippingFinance(ShippingFinance shippingFinance)
    {
        ShippingFinance finance = shippingFinanceMapper.selectShippingFinanceById(shippingFinance.getId());
        if (finance.getFloatRate() == null) {
            return AjaxResult.error("请先填报装载重量和实际重量！");
        }
        if (shippingFinance.getPaymentAmount() == null) {
            return AjaxResult.error("请先填写支付金额！");
        }
        ShippingApprove shippingApprove = new ShippingApprove();
        shippingApprove.setOrderId(shippingFinance.getOrderId());
        shippingApprove.setTaskStatus(TaskStatus.wait_upload_details.getCode());
        List<ShippingApprove> shippingApproves = shippingApproveMapper.selectShippingApproveList(shippingApprove);
        if (CollectionUtils.isEmpty(shippingApproves)) {
            shippingApprove.setApplicantTime(DateUtils.getNowDate());
            shippingApprove.setApplicantReason("财务支付申请");
            shippingApprove.setApplicantUser(getUsername());
            shippingApproveMapper.insertShippingApprove(shippingApprove);
        } else {
            shippingApprove = shippingApproves.get(0);
            if (StringUtil.isEmpty(shippingApprove.getApproveStatus())) {
                return AjaxResult.error("正在审批中，请耐心等待！");
            }
            if (StringUtil.isEquals(shippingApprove.getApproveStatus(), "0")) {
                return AjaxResult.error("审批不通过！");
            }
        }
        shippingFinance.setUpdateTime(DateUtils.getNowDate());
        shippingFinanceMapper.updateShippingFinance(shippingFinance);
        ShippingTask shippingTask = new ShippingTask();
        shippingTask.setOrderId(shippingFinance.getOrderId());
        shippingTask.setTaskStatus(TaskStatus.paid.getCode());
        shippingTaskService.generateTaskAndDetails(shippingTask);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult reportUnloadingData(ShippingFinance shippingFinance)
    {
        if(shippingFinance.getAboveLimitWeight()==null || shippingFinance.getWeight()==null){
            return AjaxResult.error("重量必填");
        }
        if(StringUtil.isEmpty(shippingFinance.getAboveLimitWeightUnit()) || StringUtil.isEmpty(shippingFinance.getWeightUnit())){
            return AjaxResult.error("重量单位必填");
        }
        BigDecimal load = CommonUtils.convertWeight(shippingFinance.getWeight(), shippingFinance.getWeightUnit());
        BigDecimal actual = CommonUtils.convertWeight(shippingFinance.getAboveLimitWeight(), shippingFinance.getAboveLimitWeightUnit());
        if (actual.compareTo(load) > 0) {
            return AjaxResult.error("实际重量超过装载重量，请重新调整");
        }
        BigDecimal ff = load.subtract(actual).divide(load, 2, RoundingMode.HALF_UP);
        shippingFinance.setFloatRate(ff);
        shippingFinance.setUpdateTime(DateUtils.getNowDate());
        shippingFinanceMapper.updateShippingFinance(shippingFinance);
        ShippingTask shippingTask = new ShippingTask();
        shippingTask.setOrderId(shippingFinance.getOrderId());
        shippingTask.setTaskStatus(TaskStatus.upload_details.getCode());
        shippingTaskService.generateTaskAndDetails(shippingTask);
        return AjaxResult.success();
    }



    /**
     * 批量删除财务管理
     * 
     * @param ids 需要删除的财务管理主键
     * @return 结果
     */
    @Override
    public int deleteShippingFinanceByIds(Long[] ids)
    {
        return shippingFinanceMapper.deleteShippingFinanceByIds(ids);
    }

    /**
     * 删除财务管理信息
     * 
     * @param id 财务管理主键
     * @return 结果
     */
    @Override
    public int deleteShippingFinanceById(Long id)
    {
        return shippingFinanceMapper.deleteShippingFinanceById(id);
    }
}
