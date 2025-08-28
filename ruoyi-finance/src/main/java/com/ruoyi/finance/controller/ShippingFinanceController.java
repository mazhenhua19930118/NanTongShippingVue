package com.ruoyi.finance.controller;

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
import com.ruoyi.finance.domain.ShippingFinance;
import com.ruoyi.finance.service.IShippingFinanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 财务管理Controller
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
@RestController
@RequestMapping("/finance/finance")
public class ShippingFinanceController extends BaseController
{
    @Autowired
    private IShippingFinanceService shippingFinanceService;

    /**
     * 查询财务管理列表
     */
    @PreAuthorize("@ss.hasPermi('finance:finance:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShippingFinance shippingFinance)
    {
        startPage();
        List<ShippingFinance> list = shippingFinanceService.selectShippingFinanceList(shippingFinance);
        return getDataTable(list);
    }

    /**
     * 导出财务管理列表
     */
    @PreAuthorize("@ss.hasPermi('finance:finance:export')")
    @Log(title = "财务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShippingFinance shippingFinance)
    {
        List<ShippingFinance> list = shippingFinanceService.selectShippingFinanceList(shippingFinance);
        ExcelUtil<ShippingFinance> util = new ExcelUtil<ShippingFinance>(ShippingFinance.class);
        util.exportExcel(response, list, "财务管理数据");
    }

    /**
     * 获取财务管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('finance:finance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shippingFinanceService.selectShippingFinanceById(id));
    }

    /**
     * 新增财务管理
     */
    @PreAuthorize("@ss.hasPermi('finance:finance:add')")
    @Log(title = "财务管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShippingFinance shippingFinance)
    {
        return toAjax(shippingFinanceService.insertShippingFinance(shippingFinance));
    }

    /**
     * 修改财务管理
     */
    @PreAuthorize("@ss.hasPermi('finance:finance:edit')")
    @Log(title = "财务管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShippingFinance shippingFinance)
    {
        return toAjax(shippingFinanceService.updateShippingFinance(shippingFinance));
    }

    /**
     * 删除财务管理
     */
    @PreAuthorize("@ss.hasPermi('finance:finance:remove')")
    @Log(title = "财务管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shippingFinanceService.deleteShippingFinanceByIds(ids));
    }
}
