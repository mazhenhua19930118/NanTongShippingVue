package com.ruoyi.goods.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.approve.domain.ShippingApprove;
import com.ruoyi.approve.mapper.ShippingApproveMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtil;
import com.ruoyi.dispatch.domain.ShippingDispatch;
import com.ruoyi.dispatch.mapper.ShippingDispatchMapper;
import com.ruoyi.insurance.domain.ShippingInsurance;
import com.ruoyi.insurance.mapper.ShippingInsuranceMapper;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.service.IShippingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.goods.mapper.ShippingGoodsMapper;
import com.ruoyi.goods.domain.ShippingGoods;
import com.ruoyi.goods.service.IShippingGoodsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 货源信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-19
 */
@Service
public class ShippingGoodsServiceImpl implements IShippingGoodsService
{
    @Autowired
    private ShippingGoodsMapper shippingGoodsMapper;
    @Autowired
    private IShippingTaskService iShippingTaskService;
    @Autowired
    private ShippingDispatchMapper shippingDispatchMapper;
    @Autowired
    private ShippingInsuranceMapper insuranceMapper;
    @Autowired
    private ShippingApproveMapper shippingApproveMapper;

    /**
     * 查询货源信息
     * 
     * @param id 货源信息主键
     * @return 货源信息
     */
    @Override
    public ShippingGoods selectShippingGoodsById(Long id)
    {
        return shippingGoodsMapper.selectShippingGoodsById(id);
    }

    /**
     * 查询货源信息列表
     * 
     * @param shippingGoods 货源信息
     * @return 货源信息
     */
    @Override
    public List<ShippingGoods> selectShippingGoodsList(ShippingGoods shippingGoods)
    {
        return shippingGoodsMapper.selectShippingGoodsList(shippingGoods);
    }

    /**
     * 新增货源信息
     * 
     * @param shippingGoods 货源信息
     * @return 结果
     */
    @Override
    public int insertShippingGoods(ShippingGoods shippingGoods)
    {
        shippingGoods.setCreateTime(DateUtils.getNowDate());
        return shippingGoodsMapper.insertShippingGoods(shippingGoods);
    }

    /**
     * 修改货源信息
     * 
     * @param shippingGoods 货源信息
     * @return 结果
     */
    @Override
    public int updateShippingGoods(ShippingGoods shippingGoods)
    {
        return shippingGoodsMapper.updateShippingGoods(shippingGoods);
    }

    /**
     * 发布货源信息
     * @param shippingGoods
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult publishShippingGoods(ShippingGoods shippingGoods)
    {
        shippingGoods.setUpdateTime(DateUtils.getNowDate());
        shippingGoods.setUpdateBy(getUsername());
        shippingGoods.setStatus(TaskStatus.publish.getCode());
        shippingGoodsMapper.updateShippingGoods(shippingGoods);
        ShippingDispatch shippingDispatch = new ShippingDispatch();
        shippingDispatch.setOrderId(shippingGoods.getOrderId());
        List<ShippingDispatch> shippingDispatches = shippingDispatchMapper.selectShippingDispatchList(shippingDispatch);
        if(!CollectionUtils.isEmpty(shippingDispatches)){
            List<Long> ids = shippingDispatches.stream().map(ShippingDispatch::getId).collect(Collectors.toList());
            Long[] idsArray = new Long[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                idsArray[i] = ids.get(i);
            }
            shippingDispatchMapper.deleteShippingDispatchByIds(idsArray);
        }
        shippingDispatchMapper.insertShippingDispatch(shippingDispatch);
        //生成保险管理
//        ShippingInsurance shippingInsurance = new ShippingInsurance();
//        shippingInsurance.setOrderId(shippingGoods.getOrderId());
//        shippingInsurance.setInsuranceStatus("0");//待预保险
//        List<ShippingInsurance> shippingInsurances = insuranceMapper.selectShippingInsuranceList(shippingInsurance);
//        if(CollectionUtils.isEmpty(shippingInsurances)){
//            shippingInsurance.setCreateTime(DateUtils.getNowDate());
//            shippingInsurance.setCreateBy(getUsername());
//            insuranceMapper.insertShippingInsurance(shippingInsurance);
//        }
        ShippingTask shippingTask = new ShippingTask();
        shippingTask.setOrderId(shippingGoods.getOrderId());
        shippingTask.setTaskStatus(TaskStatus.publish.getCode());
        iShippingTaskService.generateTaskAndDetails(shippingTask);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult arrivalEdit(ShippingGoods shippingGoods)
    {
        if (shippingGoods.getArrivalTime() == null) {
            return AjaxResult.error("请选择到港时间");
        }
       if(StringUtil.isEquals(shippingGoods.getSpecialCases(),"01")){
           //卸货特殊情况处理
           ShippingTask shippingTask = new ShippingTask();
           shippingTask.setOrderId(shippingGoods.getOrderId());
           shippingTask.setTaskStatus(TaskStatus.arrived_at_port.getCode());
           iShippingTaskService.generateTaskAndDetails(shippingTask);
           shippingTask.setTaskStatus(TaskStatus.special_handling_for_unloading.getCode());
           iShippingTaskService.generateTaskAndDetails(shippingTask);
           ShippingGoods good = new ShippingGoods();
           good.setId(shippingGoods.getId());
           good.setStatus(TaskStatus.special_handling_for_unloading.getCode());
           good.setSpecialCases(shippingGoods.getSpecialCases());
           shippingGoodsMapper.updateShippingGoods(good);
       }else{
           ShippingTask shippingTask = new ShippingTask();
           shippingTask.setOrderId(shippingGoods.getOrderId());
           shippingTask.setTaskStatus(TaskStatus.arrived_at_port.getCode());
           iShippingTaskService.generateTaskAndDetails(shippingTask);
           ShippingGoods good = new ShippingGoods();
           good.setId(shippingGoods.getId());
           good.setStatus(TaskStatus.arrived_at_port.getCode());
           good.setSpecialCases(shippingGoods.getSpecialCases());
           shippingGoodsMapper.updateShippingGoods(good);
       }
       return AjaxResult.success();
    }

    public AjaxResult specialEdit(ShippingGoods shippingGoods)
    {
        if (StringUtil.isEmpty(shippingGoods.getSpecialCaseReason())) {
            return AjaxResult.error("请说明原因！");
        }
        ShippingApprove shippingApprove = new ShippingApprove();
        shippingApprove.setOrderId(shippingGoods.getOrderId());
        shippingApprove.setTaskStatus(TaskStatus.special_handling_for_unloading.getCode());
        List<ShippingApprove> shippingApproves = shippingApproveMapper.selectShippingApproveList(shippingApprove);
        if (CollectionUtils.isEmpty(shippingApproves)) {
            shippingApprove.setApplicantUser(getUsername());
            shippingApprove.setApplicantReason(shippingGoods.getSpecialCaseReason());
            shippingApprove.setApplicantTime(DateUtils.getNowDate());
            shippingApproveMapper.insertShippingApprove(shippingApprove);
            ShippingTask shippingTask = new ShippingTask();
            shippingTask.setOrderId(shippingGoods.getOrderId());
            shippingTask.setTaskStatus(TaskStatus.special_handling_for_unloading.getCode());
            iShippingTaskService.generateTaskAndDetails(shippingTask);
        } else {
            ShippingApprove old = shippingApproves.get(0);
            if (StringUtil.isEmpty(old.getApproveStatus())) {
                return AjaxResult.error("正在审批中，请耐心等待！");
            }
            if (StringUtil.isEquals(old.getApproveStatus(), "0")) {
                return AjaxResult.error("审批不通过！");
            }
        }
        return AjaxResult.success();
    }

    /**
     * 批量删除货源信息
     * 
     * @param ids 需要删除的货源信息主键
     * @return 结果
     */
    @Override
    public int deleteShippingGoodsByIds(Long[] ids)
    {
        return shippingGoodsMapper.deleteShippingGoodsByIds(ids);
    }

    /**
     * 删除货源信息信息
     * 
     * @param id 货源信息主键
     * @return 结果
     */
    @Override
    public int deleteShippingGoodsById(Long id)
    {
        return shippingGoodsMapper.deleteShippingGoodsById(id);
    }
}
