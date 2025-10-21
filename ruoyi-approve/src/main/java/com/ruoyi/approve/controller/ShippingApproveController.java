package com.ruoyi.approve.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.approve.domain.ShippingApprove;
import com.ruoyi.approve.service.IShippingApproveService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 审批Controller
 * 
 * @author ruoyi
 * @date 2025-10-21
 */
@RestController
@RequestMapping("/approve/approve")
public class ShippingApproveController extends BaseController
{
    @Autowired
    private IShippingApproveService shippingApproveService;

    /**
     * 查询审批列表
     */
    @PreAuthorize("@ss.hasPermi('approve:approve:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShippingApprove shippingApprove)
    {
        startPage();
        List<ShippingApprove> list = shippingApproveService.selectShippingApproveList(shippingApprove);
        return getDataTable(list);
    }

    /**
     * 导出审批列表
     */
    @PreAuthorize("@ss.hasPermi('approve:approve:export')")
    @Log(title = "审批", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShippingApprove shippingApprove)
    {
        List<ShippingApprove> list = shippingApproveService.selectShippingApproveList(shippingApprove);
        ExcelUtil<ShippingApprove> util = new ExcelUtil<ShippingApprove>(ShippingApprove.class);
        util.exportExcel(response, list, "审批数据");
    }

    /**
     * 获取审批详细信息
     */
    @PreAuthorize("@ss.hasPermi('approve:approve:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shippingApproveService.selectShippingApproveById(id));
    }

    /**
     * 新增审批
     */
    @PreAuthorize("@ss.hasPermi('approve:approve:add')")
    @Log(title = "审批", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShippingApprove shippingApprove)
    {
        return toAjax(shippingApproveService.insertShippingApprove(shippingApprove));
    }

    /**
     * 修改审批
     */
    @PreAuthorize("@ss.hasPermi('approve:approve:edit')")
    @Log(title = "审批", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShippingApprove shippingApprove)
    {
        return toAjax(shippingApproveService.updateShippingApprove(shippingApprove));
    }

    /**
     * 删除审批
     */
    @PreAuthorize("@ss.hasPermi('approve:approve:remove')")
    @Log(title = "审批", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shippingApproveService.deleteShippingApproveByIds(ids));
    }
}
