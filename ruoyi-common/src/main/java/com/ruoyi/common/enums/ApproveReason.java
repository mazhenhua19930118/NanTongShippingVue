package com.ruoyi.common.enums;

import com.ruoyi.common.utils.StringUtils;

/**
 * @author mzh
 */

public enum ApproveReason {
    /**
     * 创建
     */
    shippingCost("shippingCost", "有航费需要审批", "有航费需要审批"),
    deposit_shippingCost("deposit_shippingCost", "有定金和航费需要审批", "有定金航费需要审批"),
    deposit("deposit", "有定金需要审批", "有定金需要审批");
    private String code;
    private String desc;
    private String remark;

    ApproveReason(String code, String desc, String remark) {
        this.code = code;
        this.desc = desc;
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getRemark() {
        return remark;
    }

    public static String transferRemark(String code){
        for(ApproveReason task : ApproveReason.values()){
            if(StringUtils.equals(code,task.getCode())){
                return task.remark;
            }
        }
        return "";
    }

    public static String getDescByCode(String code) {
        for (ApproveReason task : ApproveReason.values()) {
            if (StringUtils.equals(code, task.getCode())) {
                return task.desc;
            }
        }
        return "";
    }
}
