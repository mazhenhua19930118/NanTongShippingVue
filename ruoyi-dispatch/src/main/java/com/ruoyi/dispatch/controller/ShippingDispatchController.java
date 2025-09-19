package com.ruoyi.dispatch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtil;
import com.ruoyi.insurance.mapper.ShippingInsuranceMapper;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.domain.ShippingTaskDetail;
import com.ruoyi.task.service.IShippingTaskDetailService;
import com.ruoyi.task.service.IShippingTaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.dispatch.domain.ShippingDispatch;
import com.ruoyi.dispatch.service.IShippingDispatchService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 调度管理Controller
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
@RestController
@RequestMapping("/dispatch/dispatch")
public class ShippingDispatchController extends BaseController
{
    @Autowired
    private IShippingDispatchService shippingDispatchService;
    @Autowired
    private IShippingTaskService shippingTaskService;
    @Autowired
    private IShippingTaskDetailService shippingTaskDetailService;
    @Autowired
    private ShippingInsuranceMapper shippingInsuranceMapper;

    /**
     * 查询调度管理列表
     */
    @PreAuthorize("@ss.hasPermi('dispatch:dispatch:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShippingDispatch shippingDispatch)
    {
        startPage();
        List<ShippingDispatch> list = shippingDispatchService.selectShippingDispatchList(shippingDispatch);
        return getDataTable(list);
    }

    /**
     * 导出调度管理列表
     */
    @PreAuthorize("@ss.hasPermi('dispatch:dispatch:export')")
    @Log(title = "调度管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShippingDispatch shippingDispatch)
    {
        List<ShippingDispatch> list = shippingDispatchService.selectShippingDispatchList(shippingDispatch);
        ExcelUtil<ShippingDispatch> util = new ExcelUtil<ShippingDispatch>(ShippingDispatch.class);
        util.exportExcel(response, list, "调度管理数据");
    }

    /**
     * 获取调度管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('dispatch:dispatch:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shippingDispatchService.selectShippingDispatchById(id));
    }

    /**
     * 新增调度管理
     */
    @PreAuthorize("@ss.hasPermi('dispatch:dispatch:add')")
    @Log(title = "调度管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShippingDispatch shippingDispatch)
    {
        return toAjax(shippingDispatchService.insertShippingDispatch(shippingDispatch));
    }

    /**
     * 修改调度管理
     */
    @PreAuthorize("@ss.hasPermi('dispatch:dispatch:edit')")
    @Log(title = "调度管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShippingDispatch shippingDispatch)
    {
        return toAjax(shippingDispatchService.updateShippingDispatch(shippingDispatch));
    }

    /**
     * 删除调度管理
     */
    @PreAuthorize("@ss.hasPermi('dispatch:dispatch:remove')")
    @Log(title = "调度管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shippingDispatchService.deleteShippingDispatchByIds(ids));
    }

    @Log(title = "派单", businessType = BusinessType.INSERT)
    @PostMapping("/dispatchToUser")
    public AjaxResult dispatchToUser(@RequestBody ShippingTask shippingTask)
    {
        shippingTask.setTaskStatus(TaskStatus.dispatch.getCode());
        List<ShippingTask> shippingTasks = shippingTaskService.selectShippingTaskList(shippingTask);
        if(!CollectionUtils.isEmpty(shippingTasks)){
            return AjaxResult.error("本单已派送给该人员，不能重复派单！");
        }
        ShippingTask query = new ShippingTask();
        query.setOrderId(shippingTask.getOrderId());
        List<ShippingTask> queryList = shippingTaskService.selectShippingTaskList(query);
        if (CollectionUtils.isEmpty(queryList)) {
            return AjaxResult.error("本单还未生成任务，不能派单！");
        }
        if(StringUtil.isEquals(queryList.get(0).getTaskStatus(),TaskStatus.termination.getCode())){
            return AjaxResult.error("本单任务已经终止，不能派单！");
        }
        shippingTask.setCreateBy(getUsername());
        shippingTask.setUpdateBy(getUsername());
        //todo 生成船只使用记录
//        this.generateRecords(shippingTask.getGoodsId());
        //todo  有航费  无航费
        ShippingDispatch shippingDispatch = new ShippingDispatch();
        shippingDispatch.setOrderId(shippingTask.getOrderId());
        List<ShippingDispatch> shippingDispatches = shippingDispatchService.selectShippingDispatchList(shippingDispatch);
        if(CollectionUtils.isEmpty(shippingDispatches)){
            return AjaxResult.error("数据无效请检查或者重新刷新页面！");
        }
        ShippingDispatch dispatch = shippingDispatches.get(0);
        if (dispatch.getDeposit() != null || dispatch.getShippingCost() != null) {
            //todo 总经理审批
            return AjaxResult.success("请求成功，已提交总经理审批！");
        }
        shippingTaskService.generateTaskAndDetails(shippingTask);
        return AjaxResult.success();
    }

    /**
     * 查询本单派送人员
     */
    @Log(title = "查询本单派送人员", businessType = BusinessType.INSERT)
    @PostMapping("/getUserByDispatchGoods")
    public AjaxResult getUserByDispatchGoods(@RequestBody ShippingTaskDetail shippingTaskDetail)
    {
        shippingTaskDetail.setTaskStatus(TaskStatus.dispatch.getCode());
        List<ShippingTaskDetail> shippingTaskDetailList = shippingTaskDetailService.selectShippingTaskDetailList(shippingTaskDetail);
        if(CollectionUtils.isEmpty(shippingTaskDetailList)){
            return AjaxResult.success(new ArrayList<>());
        }
        List<Long> directorIds = shippingTaskDetailList.stream().map(ShippingTaskDetail::getDirector).collect(Collectors.toList());
        return  AjaxResult.success(directorIds);
    }

}
