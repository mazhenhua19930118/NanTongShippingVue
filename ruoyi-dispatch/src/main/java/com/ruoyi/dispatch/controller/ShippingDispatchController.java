package com.ruoyi.dispatch.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
}
