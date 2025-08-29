package com.ruoyi.contract.controller;

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
import com.ruoyi.contract.domain.ShippingContract;
import com.ruoyi.contract.service.IShippingContractService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 合同管理Controller
 * 
 * @author ruoyi
 * @date 2025-08-19
 */
@RestController
@RequestMapping("/contract/contract")
public class ShippingContractController extends BaseController
{
    @Autowired
    private IShippingContractService shippingContractService;

    /**
     * 查询合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShippingContract shippingContract)
    {
        startPage();
        List<ShippingContract> list = shippingContractService.selectShippingContractList(shippingContract);
        return getDataTable(list);
    }

    /**
     * 导出合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:export')")
    @Log(title = "合同管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShippingContract shippingContract)
    {
        List<ShippingContract> list = shippingContractService.selectShippingContractList(shippingContract);
        ExcelUtil<ShippingContract> util = new ExcelUtil<ShippingContract>(ShippingContract.class);
        util.exportExcel(response, list, "合同管理数据");
    }

    /**
     * 获取合同管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shippingContractService.selectShippingContractById(id));
    }

    /**
     * 新增合同管理
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:add')")
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShippingContract shippingContract)
    {
        return shippingContractService.insertShippingContract(shippingContract);
    }

    /**
     * 修改合同管理
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:edit')")
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShippingContract shippingContract)
    {
        return toAjax(shippingContractService.updateShippingContract(shippingContract));
    }

    /**
     * 删除合同管理
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:remove')")
    @Log(title = "合同管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shippingContractService.deleteShippingContractByIds(ids));
    }
}
