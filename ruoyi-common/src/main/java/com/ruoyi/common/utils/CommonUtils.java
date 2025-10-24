package com.ruoyi.common.utils;

import java.math.BigDecimal;

public class CommonUtils {
    //重量单位转换为千克
    public static BigDecimal convertWeight(BigDecimal weight, String unit) {
        if (StringUtil.isEmpty(unit)) {
            throw new RuntimeException("重量单位未知！");
        }
        //1 吨 2千克
        if ("1".equals(unit)) {
            return weight.multiply(new BigDecimal(1000));
        }
        if ("2".equals(unit)) {
            return weight;
        }
        return weight;
    }
}
