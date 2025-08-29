package com.ruoyi.contract.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.contract.domain.ShippingContract;

/**
 * 合同管理Service接口
 * 
 * @author ruoyi
 * @date 2025-08-19
 */
public interface IShippingContractService 
{
    /**
     * 查询合同管理
     * 
     * @param id 合同管理主键
     * @return 合同管理
     */
    public ShippingContract selectShippingContractById(Long id);

    /**
     * 查询合同管理列表
     * 
     * @param shippingContract 合同管理
     * @return 合同管理集合
     */
    public List<ShippingContract> selectShippingContractList(ShippingContract shippingContract);

    /**
     * 新增合同管理
     * 
     * @param shippingContract 合同管理
     * @return 结果
     */
    public AjaxResult insertShippingContract(ShippingContract shippingContract);

    /**
     * 修改合同管理
     * 
     * @param shippingContract 合同管理
     * @return 结果
     */
    public int updateShippingContract(ShippingContract shippingContract);

    /**
     * 批量删除合同管理
     * 
     * @param ids 需要删除的合同管理主键集合
     * @return 结果
     */
    public int deleteShippingContractByIds(Long[] ids);

    /**
     * 删除合同管理信息
     * 
     * @param id 合同管理主键
     * @return 结果
     */
    public int deleteShippingContractById(Long id);
}
