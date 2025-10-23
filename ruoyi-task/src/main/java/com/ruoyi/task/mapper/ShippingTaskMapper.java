package com.ruoyi.task.mapper;

import com.ruoyi.common.dto.ShippingContractDto;
import com.ruoyi.common.vo.GoodsDetailVo;
import com.ruoyi.task.domain.FinanceDto;
import com.ruoyi.task.domain.ShippingTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2025-04-08
 */
public interface ShippingTaskMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ShippingTask selectShippingTaskById(Long id);

    public ShippingTask selectShippingTaskByRelatedId(String orderId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ShippingTask> selectShippingTaskList(ShippingTask shippingTask);

    /**
     * 新增【请填写功能名称】
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 结果
     */
    public int insertShippingTask(ShippingTask shippingTask);

    public int insertShippingContract(ShippingContractDto shippingContractDto);

    public int selectShippingContractCount(String goodsId);

    /**
     * 修改【请填写功能名称】
     * 
     * @param shippingTask 【请填写功能名称】
     * @return 结果
     */
    public int updateShippingTask(ShippingTask shippingTask);

    public int updateShippingTaskByGoodsId(ShippingTask shippingTask);

    public int updateShippingGoodsTask(ShippingTask shippingTask);


    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteShippingTaskById(Long id);

    public int deleteShippingTaskByGoodsId(Long goodsId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShippingTaskByIds(Long[] ids);

    /**
     * 综合查询明细查询
     */
    public GoodsDetailVo selectGoodsDetailByGoodsId(String goodsId);
    public int insertFiance(FinanceDto financeDto);
}
