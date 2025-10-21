package com.ruoyi.common.enums;

import com.ruoyi.common.utils.StringUtils;

/**
 * @author mzh
 */

public enum ApproveStatus {
    /**
     * 创建
     */
    YES("1", "审批通过", "审批通过"),
    NO("0", "审批不通过", "审批不通过");
    private String code;
    private String desc;
    private String remark;

    ApproveStatus(String code, String desc, String remark) {
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
        for(ApproveStatus task : ApproveStatus.values()){
            if(StringUtils.equals(code,task.getCode())){
                return task.remark;
            }
        }
        return "";
    }

    public static String getDescByCode(String code) {
        for (ApproveStatus task : ApproveStatus.values()) {
            if (StringUtils.equals(code, task.getCode())) {
                return task.desc;
            }
        }
        return "";
    }
}
