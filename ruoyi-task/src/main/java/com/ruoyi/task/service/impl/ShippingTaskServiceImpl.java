package com.ruoyi.task.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.dto.ShippingContractDto;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.CopyUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtil;
import com.ruoyi.common.vo.GoodsDetailVo;
import com.ruoyi.common.vo.ShippingUploadVo;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.task.domain.FinanceDto;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.domain.ShippingTaskDetail;
import com.ruoyi.task.mapper.ShippingTaskDetailMapper;
import com.ruoyi.task.mapper.ShippingTaskMapper;
import com.ruoyi.task.service.IShippingTaskDetailService;
import com.ruoyi.task.service.IShippingTaskService;
import com.ruoyi.upload.domain.ShippingUpload;
import com.ruoyi.upload.service.IShippingUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-04-08
 */
@Service
public class ShippingTaskServiceImpl implements IShippingTaskService
{
    @Autowired
    private ShippingTaskMapper shippingTaskMapper;
    @Autowired
    private ShippingTaskDetailMapper shippingTaskDetailMapper;
    @Autowired
    private IShippingTaskDetailService shippingTaskDetailService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IShippingUploadService shippingUploadService;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ShippingTask selectShippingTaskById(Long id)
    {
        return shippingTaskMapper.selectShippingTaskById(id);
    }

    @Override
    public ShippingTask selectShippingTaskByRelatedId(String goodsId)
    {
        return shippingTaskMapper.selectShippingTaskByRelatedId(goodsId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ShippingTask> selectShippingTaskList(ShippingTask shippingTask)
    {
        return shippingTaskMapper.selectShippingTaskList(shippingTask);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertShippingTask(ShippingTask shippingTask)
    {
        shippingTask.setCreateTime(DateUtils.getNowDate());
        //生成货源合同
        ShippingContractDto shippingContract = new ShippingContractDto();
        shippingContract.setOrderId(shippingTask.getOrderId());
        shippingContract.setMainContractNo(" ");
        shippingContract.setCreateTime(new Date());
        shippingContract.setCreateBy(shippingTask.getCreateBy());
        int count = shippingTaskMapper.selectShippingContractCount(shippingTask.getOrderId());
        if (count == 0) {
            shippingTaskMapper.insertShippingContract(shippingContract);
        }
        shippingTaskMapper.updateShippingGoodsTask(shippingTask);
//        shippingTaskMapper.insertShippingTask(shippingTask);
        shippingTask.setCurrentUserId(SecurityUtils.getUserId());
        this.generateTaskAndDetails(shippingTask);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateTaskAndDetails(ShippingTask shippingTask)
    {
        shippingTask.setUpdateTime(DateUtils.getNowDate());
        shippingTaskMapper.updateShippingGoodsTask(shippingTask);
        Long taskId = null;
        //这里重新把任务状态取来 防止任务表插入不同状态的数据 查询的时候赋值为空
        String taskStatus = "";
        taskStatus = shippingTask.getTaskStatus();
        Long assignUserId = shippingTask.getAssignUserId();
        shippingTask.setTaskStatus(null);
        shippingTask.setAssignUserId(null);
        List<ShippingTask> shippingTasks = shippingTaskMapper.selectShippingTaskList(shippingTask);
        if(CollectionUtils.isEmpty(shippingTasks)){
            shippingTask.setCreateTime(DateUtils.getNowDate());
            shippingTask.setUpdateTime(DateUtils.getNowDate());
            shippingTask.setTaskStatus(taskStatus);
            shippingTask.setOrderId(shippingTask.getOrderId());
            shippingTask.setAssignUserId(assignUserId);
            shippingTaskMapper.insertShippingTask(shippingTask);
            taskId = shippingTask.getId();
        }else{
            shippingTask.setCreateBy(null);
            taskId = shippingTasks.get(0).getId();
            shippingTask.setTaskStatus(taskStatus);
            shippingTask.setId(taskId);
            shippingTask.setAssignUserId(assignUserId);
            shippingTaskMapper.updateShippingTask(shippingTask);
        }
        ShippingTaskDetail shippingTaskDetail = new ShippingTaskDetail();
        shippingTaskDetail.setTaskId(taskId);
        shippingTaskDetail.setOrderId(shippingTask.getOrderId());
        shippingTaskDetail.setTaskStatus(taskStatus);
        List<ShippingTaskDetail> shippingTaskDetailList = shippingTaskDetailMapper.selectShippingTaskDetailList(shippingTaskDetail);
        if(CollectionUtils.isEmpty(shippingTaskDetailList)){
            shippingTaskDetail.setCreateTime(DateUtils.getNowDate());
            shippingTaskDetail.setUpdateTime(DateUtils.getNowDate());
            shippingTaskDetail.setDirector(shippingTask.getAssignUserId());
            if(shippingTask.getCurrentUserId()==null){
                shippingTask.setCurrentUserId(SecurityUtils.getUserId());
            }
            shippingTaskDetail.setCameraInstructions(shippingTask.getCameraInstructions());
            shippingTaskDetail.setCreator(shippingTask.getCurrentUserId());
            shippingTaskDetailMapper.insertShippingTaskDetail(shippingTaskDetail);
        }else{
            shippingTaskDetail.setId(shippingTaskDetailList.get(0).getId());
            shippingTaskDetail.setUpdateTime(DateUtils.getNowDate());
            shippingTaskDetail.setDirector(shippingTask.getAssignUserId());
            shippingTaskDetail.setCameraInstructions(shippingTask.getCameraInstructions());
            shippingTaskDetailMapper.updateShippingTaskDetail(shippingTaskDetail);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertFiance(FinanceDto financeDto){
        shippingTaskMapper.insertFiance(financeDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearTaskAndDetailsByGoodsId(Long goodsId) {
        shippingTaskMapper.deleteShippingTaskByGoodsId(goodsId);
        shippingTaskDetailMapper.deleteShippingTaskDetailByGoodsId(goodsId);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateShippingTask(ShippingTask shippingTask)
    {
        shippingTask.setUpdateTime(DateUtils.getNowDate());
        shippingTaskMapper.updateShippingGoodsTask(shippingTask);
        return shippingTaskMapper.updateShippingTask(shippingTask);
    }

    public int updateShippingTaskAndGoods(ShippingTask shippingTask)
    {
        shippingTask.setUpdateTime(DateUtils.getNowDate());
        shippingTaskMapper.updateShippingGoodsTask(shippingTask);
        return shippingTaskMapper.updateShippingTaskByGoodsId(shippingTask);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteShippingTaskByIds(Long[] ids)
    {
        return shippingTaskMapper.deleteShippingTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteShippingTaskById(Long id)
    {
        return shippingTaskMapper.deleteShippingTaskById(id);
    }

    @Override
    public AjaxResult checkPreviousTaskNode(ShippingTask shippingTask) {
        ShippingTaskDetail shippingTaskDetail = new ShippingTaskDetail();
        CopyUtils.copyProperties(shippingTask, shippingTaskDetail);
        return shippingTaskDetailService.checkPreviousTaskNode(shippingTaskDetail);
    }

    /**
     * 查询【所有任务节点】
     *
     * @param shippingTask 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public List<ShippingTaskDetail> selectShippingTaskNodeList(ShippingTask shippingTask)
    {
        return shippingTaskDetailService.selectShippingTaskDetailByTaskId(shippingTask.getId());
    }

    /**
     * 综合查询明细查询
     *
     * @param goodsId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public GoodsDetailVo selectGoodsDetailByGoodsId(String goodsId){
        GoodsDetailVo goodsDetailVo = shippingTaskMapper.selectGoodsDetailByGoodsId(goodsId);
        if(ObjectUtils.isEmpty(goodsDetailVo)){
            return new GoodsDetailVo();
        }
        List<ShippingTaskDetail> shippingTaskDetails = shippingTaskDetailMapper.selectShippingTaskDetailByRelatedId(goodsId);
        if(CollectionUtils.isEmpty(shippingTaskDetails)){
            return goodsDetailVo;
        }
        List<ShippingTaskDetail> sortedList = shippingTaskDetails.stream().sorted(Comparator.comparing(v -> StringUtil.convertStringToLong(v.getTaskStatus()))).collect(Collectors.toList());
        ShippingTaskDetail first = CollectionUtils.isEmpty(sortedList) ? null : sortedList.get(0);
        goodsDetailVo.setCreateTime(first.getCreateTime());
        ShippingTaskDetail last = CollectionUtils.isEmpty(sortedList) ? null : sortedList.get(sortedList.size() - 1);
        String timeConsuming = DateUtils.timeDistance(last.getUpdateTime(), first.getCreateTime());
        goodsDetailVo.setTimeConsuming(timeConsuming);
        //转换当前负责人名字
        if(!ObjectUtils.isEmpty(last.getDirector())){
            SysUser sysUser = userService.selectUserById(last.getDirector());
            goodsDetailVo.setDirectorName(sysUser.getNickName());
        }
        //结束时间
        if(StringUtil.isEquals(last.getTaskStatus(),TaskStatus.already_settled.getCode())){
            goodsDetailVo.setUpdateTime(last.getUpdateTime());
        }
        //构建图片明细
        buildImages(goodsId,goodsDetailVo);
        return goodsDetailVo;
    }


    /**
     *  查询图片明细
     */
    public void buildImages(String goodsId, GoodsDetailVo goodsDetailVo) {
        //查询图片明细
        ShippingUpload shippingUpload = new ShippingUpload();
        shippingUpload.setRelatedId(goodsId);
        List<ShippingUpload> shippingUploads = shippingUploadService.selectShippingUploadList(shippingUpload);
        if (CollectionUtils.isEmpty(shippingUploads)) {
            return ;
        }
        // 使用groupingBy根据type拆分
        Map<String, List<ShippingUpload>> map = shippingUploads.stream()
                .collect(Collectors.groupingBy(ShippingUpload::getUploadType));
        List<ShippingUpload> loading = map.get(TaskStatus.loading.getCode()) != null ? map.get(TaskStatus.loading.getCode()) : new ArrayList<>();
        List<ShippingUpload> unloaded = map.get(TaskStatus.unloaded.getCode()) != null ? map.get(TaskStatus.unloaded.getCode()) : new ArrayList<>();
        //这里转换一下 防止相互依赖
        List<ShippingUploadVo> loadingVo = new ArrayList<>();
        List<ShippingUploadVo> unloadedVo = new ArrayList<>();
        for (ShippingUpload upload : loading) {
            ShippingUploadVo shippingUploadVo = new ShippingUploadVo();
            CopyUtils.copyProperties(upload, shippingUploadVo);
            loadingVo.add(shippingUploadVo);
        }
        for (ShippingUpload upload : unloaded) {
            ShippingUploadVo shippingUploadVo = new ShippingUploadVo();
            CopyUtils.copyProperties(upload, shippingUploadVo);
            unloadedVo.add(shippingUploadVo);
        }
        goodsDetailVo.setLoadingImages(loadingVo);
        goodsDetailVo.setUnloadedImages(unloadedVo);
    }
}
