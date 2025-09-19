package com.ruoyi.insurance.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.StringUtil;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.service.IShippingTaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.insurance.domain.ShippingInsurance;
import com.ruoyi.insurance.service.IShippingInsuranceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 保险管理Controller
 * 
 * @author ruoyi
 * @date 2025-08-20
 */
@RestController
@RequestMapping("/insurance/insurance")
public class ShippingInsuranceController extends BaseController
{
    @Autowired
    private IShippingInsuranceService shippingInsuranceService;
    @Autowired
    private IShippingTaskService shippingTaskService ;

    /**
     * 查询保险管理列表
     */
    @PreAuthorize("@ss.hasPermi('insurance:insurance:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShippingInsurance shippingInsurance)
    {
        startPage();
        List<ShippingInsurance> list = shippingInsuranceService.selectShippingInsuranceList(shippingInsurance);
        return getDataTable(list);
    }

    /**
     * 导出保险管理列表
     */
    @PreAuthorize("@ss.hasPermi('insurance:insurance:export')")
    @Log(title = "保险管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShippingInsurance shippingInsurance)
    {
        List<ShippingInsurance> list = shippingInsuranceService.selectShippingInsuranceList(shippingInsurance);
        ExcelUtil<ShippingInsurance> util = new ExcelUtil<ShippingInsurance>(ShippingInsurance.class);
        util.exportExcel(response, list, "保险管理数据");
    }

    /**
     * 获取保险管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('insurance:insurance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shippingInsuranceService.selectShippingInsuranceById(id));
    }

    /**
     * 新增保险管理
     */
    @PreAuthorize("@ss.hasPermi('insurance:insurance:add')")
    @Log(title = "保险管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShippingInsurance shippingInsurance)
    {
        return toAjax(shippingInsuranceService.insertShippingInsurance(shippingInsurance));
    }

    /**
     * 修改保险管理
     */
    @PreAuthorize("@ss.hasPermi('insurance:insurance:edit')")
    @Log(title = "保险管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShippingInsurance shippingInsurance)
    {
        shippingInsuranceService.updateShippingInsurance(shippingInsurance);
        ShippingTask shippingTask = new ShippingTask();
        //预保险
        if(StringUtil.isEquals("1",shippingInsurance.getInsuranceStatus())){
            shippingTask.setTaskStatus(TaskStatus.pre_insurance.getCode());
        }
        shippingTaskService.generateTaskAndDetails(shippingTask);
        return AjaxResult.success();
    }

    /**
     * 删除保险管理
     */
    @PreAuthorize("@ss.hasPermi('insurance:insurance:remove')")
    @Log(title = "保险管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shippingInsuranceService.deleteShippingInsuranceByIds(ids));
    }
}
