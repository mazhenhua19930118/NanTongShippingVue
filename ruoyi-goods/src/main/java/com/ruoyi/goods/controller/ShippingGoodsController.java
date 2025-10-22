package com.ruoyi.goods.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.enums.TaskStatus;
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
import com.ruoyi.goods.domain.ShippingGoods;
import com.ruoyi.goods.service.IShippingGoodsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 货源信息Controller
 * 
 * @author ruoyi
 * @date 2025-08-19
 */
@RestController
@RequestMapping("/goods/goods")
public class ShippingGoodsController extends BaseController
{
    @Autowired
    private IShippingGoodsService shippingGoodsService;

    /**
     * 查询货源信息列表
     */
    @PreAuthorize("@ss.hasPermi('goods:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShippingGoods shippingGoods)
    {
        startPage();
        shippingGoods.setStatus(TaskStatus.sign_main_contract.getCode());
        List<ShippingGoods> list = shippingGoodsService.selectShippingGoodsList(shippingGoods);
        return getDataTable(list);
    }

    @GetMapping("/arrival/list")
    public TableDataInfo ArrviaList(ShippingGoods shippingGoods)
    {
        startPage();
        shippingGoods.setStatus(TaskStatus.insurance.getCode());
        List<ShippingGoods> list = shippingGoodsService.selectShippingGoodsList(shippingGoods);
        return getDataTable(list);
    }

    /**
     * 导出货源信息列表
     */
    @PreAuthorize("@ss.hasPermi('goods:goods:export')")
    @Log(title = "货源信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShippingGoods shippingGoods)
    {
        List<ShippingGoods> list = shippingGoodsService.selectShippingGoodsList(shippingGoods);
        ExcelUtil<ShippingGoods> util = new ExcelUtil<ShippingGoods>(ShippingGoods.class);
        util.exportExcel(response, list, "货源信息数据");
    }

    /**
     * 获取货源信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:goods:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shippingGoodsService.selectShippingGoodsById(id));
    }

    /**
     * 新增货源信息
     */
    @PreAuthorize("@ss.hasPermi('goods:goods:add')")
    @Log(title = "货源信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShippingGoods shippingGoods)
    {
        return toAjax(shippingGoodsService.insertShippingGoods(shippingGoods));
    }

    /**
     * 修改货源信息
     */
    @PreAuthorize("@ss.hasPermi('goods:goods:edit')")
    @Log(title = "货源信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShippingGoods shippingGoods)
    {
        return toAjax(shippingGoodsService.updateShippingGoods(shippingGoods));
    }

    @Log(title = "到港信息", businessType = BusinessType.UPDATE)
    @PostMapping("arrivalEdit")
    public AjaxResult arrivalEdit(@RequestBody ShippingGoods shippingGoods)
    {
        return shippingGoodsService.arrivalEdit(shippingGoods);
    }


    /**
     * 发布
     * @param shippingGoods
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:goods:publish')")
    @Log(title = "货源信息", businessType = BusinessType.UPDATE)
    @PostMapping("publish")
    public AjaxResult publish(@RequestBody ShippingGoods shippingGoods)
    {
        return shippingGoodsService.publishShippingGoods(shippingGoods);
    }

    /**
     * 删除货源信息
     */
    @PreAuthorize("@ss.hasPermi('goods:goods:remove')")
    @Log(title = "货源信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shippingGoodsService.deleteShippingGoodsByIds(ids));
    }
}
