package com.ruoyi.finance.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.finance.mapper.ShippingFinanceMapper;
import com.ruoyi.finance.domain.ShippingFinance;
import com.ruoyi.finance.service.IShippingFinanceService;

/**
 * 财务管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-28
 */
@Service
public class ShippingFinanceServiceImpl implements IShippingFinanceService 
{
    @Autowired
    private ShippingFinanceMapper shippingFinanceMapper;

    /**
     * 查询财务管理
     * 
     * @param id 财务管理主键
     * @return 财务管理
     */
    @Override
    public ShippingFinance selectShippingFinanceById(Long id)
    {
        return shippingFinanceMapper.selectShippingFinanceById(id);
    }

    /**
     * 查询财务管理列表
     * 
     * @param shippingFinance 财务管理
     * @return 财务管理
     */
    @Override
    public List<ShippingFinance> selectShippingFinanceList(ShippingFinance shippingFinance)
    {
        return shippingFinanceMapper.selectShippingFinanceList(shippingFinance);
    }

    /**
     * 新增财务管理
     * 
     * @param shippingFinance 财务管理
     * @return 结果
     */
    @Override
    public int insertShippingFinance(ShippingFinance shippingFinance)
    {
        shippingFinance.setCreateTime(DateUtils.getNowDate());
        return shippingFinanceMapper.insertShippingFinance(shippingFinance);
    }

    /**
     * 修改财务管理
     * 
     * @param shippingFinance 财务管理
     * @return 结果
     */
    @Override
    public int updateShippingFinance(ShippingFinance shippingFinance)
    {
        shippingFinance.setUpdateTime(DateUtils.getNowDate());
        return shippingFinanceMapper.updateShippingFinance(shippingFinance);
    }

    /**
     * 批量删除财务管理
     * 
     * @param ids 需要删除的财务管理主键
     * @return 结果
     */
    @Override
    public int deleteShippingFinanceByIds(Long[] ids)
    {
        return shippingFinanceMapper.deleteShippingFinanceByIds(ids);
    }

    /**
     * 删除财务管理信息
     * 
     * @param id 财务管理主键
     * @return 结果
     */
    @Override
    public int deleteShippingFinanceById(Long id)
    {
        return shippingFinanceMapper.deleteShippingFinanceById(id);
    }
}
