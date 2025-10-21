package com.ruoyi.contract.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.SysStatus;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtil;
import com.ruoyi.generator.util.GenerateCodeUtils;
import com.ruoyi.goods.domain.ShippingGoods;
import com.ruoyi.goods.mapper.ShippingGoodsMapper;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.domain.ShippingTaskDetail;
import com.ruoyi.task.mapper.ShippingTaskDetailMapper;
import com.ruoyi.task.service.impl.ShippingTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contract.mapper.ShippingContractMapper;
import com.ruoyi.contract.domain.ShippingContract;
import com.ruoyi.contract.service.IShippingContractService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import static com.ruoyi.common.utils.SecurityUtils.getUserId;
import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 合同管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-19
 */
@Service
public class ShippingContractServiceImpl implements IShippingContractService
{
    @Autowired
    private ShippingContractMapper shippingContractMapper;
    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    @Autowired
    private ShippingTaskServiceImpl shippingTaskService;
    @Autowired
    private ShippingGoodsMapper shippingGoodsMapper;
    @Autowired
    private ShippingTaskDetailMapper shippingTaskDetailMapper;


    public static final String INVOICE_XQ = "XQ";
    /**
     * 查询合同管理
     * 
     * @param id 合同管理主键
     * @return 合同管理
     */
    @Override
    public ShippingContract selectShippingContractById(Long id)
    {
        return shippingContractMapper.selectShippingContractById(id);
    }

    /**
     * 查询合同管理列表
     * 
     * @param shippingContract 合同管理
     * @return 合同管理
     */
    @Override
    public List<ShippingContract> selectShippingContractList(ShippingContract shippingContract)
    {
        return shippingContractMapper.selectShippingContractList(shippingContract);
    }

    /**
     * 新增合同管理
     * 
     * @param shippingContract 合同管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertShippingContract(ShippingContract shippingContract) {
        shippingContract.setCreateTime(DateUtils.getNowDate());
        //生成订单号
        String orderCode = generateCodeUtils.generateOrderCode(INVOICE_XQ);
        shippingContract.setOrderId(orderCode);
        ShippingContract query = new ShippingContract();
        query.setMainContractNo(shippingContract.getMainContractNo());
        List<ShippingContract> shippingContracts = shippingContractMapper.selectShippingContractList(query);
        if (!CollectionUtils.isEmpty(shippingContracts)) {
            return AjaxResult.error("主合同编号已存在");
        }
        shippingContractMapper.insertShippingContract(shippingContract);
        ShippingGoods shippingGoods = new ShippingGoods();
        shippingGoods.setOrderId(orderCode);
        List<ShippingGoods> goodsList = shippingGoodsMapper.selectShippingGoodsList(shippingGoods);
        if(StringUtil.isEquals(shippingContract.getMainContractSign(), SysStatus.Yes.getCode())){
            shippingGoods.setStatus(TaskStatus.sign_main_contract.getCode());
        }else if(StringUtil.isEquals(shippingContract.getSubContractSign(), SysStatus.Yes.getCode())){
            shippingGoods.setStatus(TaskStatus.sign_sub_contract.getCode());
        }else{
            shippingGoods.setStatus(TaskStatus.create.getCode());
        }
        if(CollectionUtils.isEmpty(goodsList)){
            shippingGoods.setCreateBy(getUsername());
            shippingGoods.setUpdateBy(getUsername());
            shippingGoods.setCreateTime(DateUtils.getNowDate());
            shippingGoods.setUpdateTime(DateUtils.getNowDate());
            shippingGoodsMapper.insertShippingGoods(shippingGoods);
        }else{
            shippingGoods.setUpdateBy(getUsername());
            shippingGoods.setUpdateTime(DateUtils.getNowDate());
            shippingGoodsMapper.updateShippingGoods(shippingGoods);
        }
        ShippingTask shippingTask = new ShippingTask();
        shippingTask.setOrderId(orderCode);
        shippingTask.setCreateBy(getUsername());
        shippingTask.setAssignUserId(getUserId());
        List<ShippingTaskDetail> shippingTaskDetails = shippingTaskDetailMapper.selectShippingTaskDetailByRelatedId(shippingTask.getOrderId());
        if(!CollectionUtils.isEmpty(shippingTaskDetails)){
            List<ShippingTaskDetail> shippingTaskDetailList = shippingTaskDetails.stream().filter(v -> StringUtil.isEquals(v.getTaskStatus(), TaskStatus.create.getCode())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(shippingTaskDetailList)){
                shippingTask.setTaskStatus(TaskStatus.create.getCode());
                shippingTaskService.generateTaskAndDetails(shippingTask);
            }
        }
        shippingTask.setTaskStatus(shippingGoods.getStatus());
        shippingTaskService.generateTaskAndDetails(shippingTask);
        return AjaxResult.success("操作成功");
    }

    /**
     * 修改合同管理
     * 
     * @param shippingContract 合同管理
     * @return 结果
     */
    @Override
    public int updateShippingContract(ShippingContract shippingContract)
    {
        shippingContract.setUpdateTime(DateUtils.getNowDate());
        shippingContract.setUpdateBy(getUsername());
        ShippingGoods shippingGoods = new ShippingGoods();
        shippingGoods.setOrderId(shippingContract.getOrderId());
        List<ShippingGoods> goodsList = shippingGoodsMapper.selectShippingGoodsList(shippingGoods);
        if(StringUtil.isEquals(shippingContract.getMainContractSign(), SysStatus.Yes.getCode())){
            shippingGoods.setStatus(TaskStatus.sign_main_contract.getCode());
        }else if(StringUtil.isEquals(shippingContract.getSubContractSign(), SysStatus.Yes.getCode())){
            shippingGoods.setStatus(TaskStatus.sign_sub_contract.getCode());
        }else{
            shippingGoods.setStatus(TaskStatus.create.getCode());
        }
        if(CollectionUtils.isEmpty(goodsList)){
            shippingGoods.setCreateBy(getUsername());
            shippingGoods.setUpdateBy(getUsername());
            shippingGoods.setCreateTime(DateUtils.getNowDate());
            shippingGoods.setUpdateTime(DateUtils.getNowDate());
            shippingGoodsMapper.insertShippingGoods(shippingGoods);
        }else{
            shippingGoods.setUpdateBy(getUsername());
            shippingGoods.setUpdateTime(DateUtils.getNowDate());
            shippingGoodsMapper.updateShippingGoods(shippingGoods);
        }
        ShippingTask shippingTask = new ShippingTask();
        shippingTask.setOrderId(shippingContract.getOrderId());
        shippingTask.setCreateBy(getUsername());
        shippingTask.setAssignUserId(getUserId());
        List<ShippingTaskDetail> shippingTaskDetails = shippingTaskDetailMapper.selectShippingTaskDetailByRelatedId(shippingTask.getOrderId());
        if(!CollectionUtils.isEmpty(shippingTaskDetails)){
            List<ShippingTaskDetail> shippingTaskDetailList = shippingTaskDetails.stream().filter(v -> StringUtil.isEquals(v.getTaskStatus(), TaskStatus.create.getCode())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(shippingTaskDetailList)){
                shippingTask.setTaskStatus(TaskStatus.create.getCode());
                shippingTaskService.generateTaskAndDetails(shippingTask);
            }
        }
        shippingTask.setTaskStatus(shippingGoods.getStatus());
        shippingTaskService.generateTaskAndDetails(shippingTask);
        return shippingContractMapper.updateShippingContract(shippingContract);
    }

    /**
     * 批量删除合同管理
     * 
     * @param ids 需要删除的合同管理主键
     * @return 结果
     */
    @Override
    public int deleteShippingContractByIds(Long[] ids)
    {
        return shippingContractMapper.deleteShippingContractByIds(ids);
    }

    /**
     * 删除合同管理信息
     * 
     * @param id 合同管理主键
     * @return 结果
     */
    @Override
    public int deleteShippingContractById(Long id)
    {
        return shippingContractMapper.deleteShippingContractById(id);
    }

    public AjaxResult mainSign(ShippingContract shippingContract){
        shippingContract.setUpdateTime(DateUtils.getNowDate());
        shippingContract.setUpdateBy(getUsername());
        ShippingGoods shippingGoods = new ShippingGoods();
        shippingGoods.setOrderId(shippingContract.getOrderId());
        if(StringUtil.isEquals(shippingContract.getMainContractSign(), SysStatus.Yes.getCode())){
            shippingGoods.setStatus(TaskStatus.sign_main_contract.getCode());
        }else{
            return AjaxResult.success();
        }
        shippingGoodsMapper.updateShippingGoods(shippingGoods);
        ShippingTask shippingTask = new ShippingTask();
        shippingTask.setOrderId(shippingContract.getOrderId());
        shippingTask.setCreateBy(getUsername());
        shippingTask.setAssignUserId(getUserId());
        List<ShippingTaskDetail> shippingTaskDetails = shippingTaskDetailMapper.selectShippingTaskDetailByRelatedId(shippingTask.getOrderId());
        if(!CollectionUtils.isEmpty(shippingTaskDetails)){
            List<ShippingTaskDetail> shippingTaskDetailList = shippingTaskDetails.stream().filter(v -> StringUtil.isEquals(v.getTaskStatus(), TaskStatus.create.getCode())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(shippingTaskDetailList)){
                shippingTask.setTaskStatus(TaskStatus.create.getCode());
                shippingTaskService.generateTaskAndDetails(shippingTask);
            }
        }
        shippingTask.setTaskStatus(shippingGoods.getStatus());
        shippingTaskService.generateTaskAndDetails(shippingTask);
        shippingContractMapper.updateShippingContract(shippingContract);
        return AjaxResult.success("操作成功");
    }

    public AjaxResult subSign(ShippingContract shippingContract){
        shippingContract.setUpdateTime(DateUtils.getNowDate());
        shippingContract.setUpdateBy(getUsername());
        ShippingGoods shippingGoods = new ShippingGoods();
        shippingGoods.setOrderId(shippingContract.getOrderId());
        if(StringUtil.isEquals(shippingContract.getMainContractSign(), SysStatus.Yes.getCode())){
            shippingGoods.setStatus(TaskStatus.sign_sub_contract.getCode());
        }else{
            return AjaxResult.success();
        }
        shippingGoodsMapper.updateShippingGoods(shippingGoods);
        ShippingTask shippingTask = new ShippingTask();
        shippingTask.setOrderId(shippingContract.getOrderId());
        shippingTask.setCreateBy(getUsername());
        shippingTask.setAssignUserId(getUserId());
        List<ShippingTaskDetail> shippingTaskDetails = shippingTaskDetailMapper.selectShippingTaskDetailByRelatedId(shippingTask.getOrderId());
        if(!CollectionUtils.isEmpty(shippingTaskDetails)){
            List<ShippingTaskDetail> shippingTaskDetailList = shippingTaskDetails.stream().filter(v -> StringUtil.isEquals(v.getTaskStatus(), TaskStatus.create.getCode())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(shippingTaskDetailList)){
                shippingTask.setTaskStatus(TaskStatus.create.getCode());
                shippingTaskService.generateTaskAndDetails(shippingTask);
            }
        }
        shippingTask.setTaskStatus(shippingGoods.getStatus());
        shippingTaskService.generateTaskAndDetails(shippingTask);
        shippingContractMapper.updateShippingContract(shippingContract);
        return AjaxResult.success("操作成功");
    }
}
