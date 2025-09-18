package com.ruoyi.goods.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.dispatch.domain.ShippingDispatch;
import com.ruoyi.dispatch.mapper.ShippingDispatchMapper;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.service.IShippingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.goods.mapper.ShippingGoodsMapper;
import com.ruoyi.goods.domain.ShippingGoods;
import com.ruoyi.goods.service.IShippingGoodsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 货源信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-19
 */
@Service
public class ShippingGoodsServiceImpl implements IShippingGoodsService
{
    @Autowired
    private ShippingGoodsMapper shippingGoodsMapper;
    @Autowired
    private IShippingTaskService iShippingTaskService;

    /**
     * 查询货源信息
     * 
     * @param id 货源信息主键
     * @return 货源信息
     */
    @Override
    public ShippingGoods selectShippingGoodsById(Long id)
    {
        return shippingGoodsMapper.selectShippingGoodsById(id);
    }

    /**
     * 查询货源信息列表
     * 
     * @param shippingGoods 货源信息
     * @return 货源信息
     */
    @Override
    public List<ShippingGoods> selectShippingGoodsList(ShippingGoods shippingGoods)
    {
        return shippingGoodsMapper.selectShippingGoodsList(shippingGoods);
    }

    /**
     * 新增货源信息
     * 
     * @param shippingGoods 货源信息
     * @return 结果
     */
    @Override
    public int insertShippingGoods(ShippingGoods shippingGoods)
    {
        shippingGoods.setCreateTime(DateUtils.getNowDate());
        return shippingGoodsMapper.insertShippingGoods(shippingGoods);
    }

    /**
     * 修改货源信息
     * 
     * @param shippingGoods 货源信息
     * @return 结果
     */
    @Override
    public int updateShippingGoods(ShippingGoods shippingGoods)
    {
        shippingGoods.setUpdateTime(DateUtils.getNowDate());
        return shippingGoodsMapper.updateShippingGoods(shippingGoods);
    }

    /**
     * 发布货源信息
     * @param shippingGoods
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult publishShippingGoods(ShippingGoods shippingGoods)
    {
        shippingGoods.setUpdateTime(DateUtils.getNowDate());
        shippingGoods.setUpdateBy(getUsername());
        shippingGoods.setStatus(TaskStatus.publish.getCode());
        shippingGoodsMapper.updateShippingGoods(shippingGoods);
        ShippingTask shippingTask = new ShippingTask();
        shippingTask.setOrderId(shippingGoods.getOrderId());
        shippingTask.setTaskStatus(TaskStatus.publish.getCode());
        iShippingTaskService.generateTaskAndDetails(shippingTask);
        return AjaxResult.success();
    }


    /**
     * 批量删除货源信息
     * 
     * @param ids 需要删除的货源信息主键
     * @return 结果
     */
    @Override
    public int deleteShippingGoodsByIds(Long[] ids)
    {
        return shippingGoodsMapper.deleteShippingGoodsByIds(ids);
    }

    /**
     * 删除货源信息信息
     * 
     * @param id 货源信息主键
     * @return 结果
     */
    @Override
    public int deleteShippingGoodsById(Long id)
    {
        return shippingGoodsMapper.deleteShippingGoodsById(id);
    }
}
