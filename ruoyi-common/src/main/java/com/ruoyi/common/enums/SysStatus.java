package com.ruoyi.common.enums;

import com.ruoyi.common.utils.StringUtils;

/**
 * @author mzh
 */

public enum SysStatus {


    Yes("1", "是", "是"),
    No("0", "否", "否");


    private String code;
    private String desc;
    private String remark;

    SysStatus(String code, String desc, String remark) {
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
        for(SysStatus task : SysStatus.values()){
            if(StringUtils.equals(code,task.getCode())){
                return task.remark;
            }
        }
        return "";
    }

    public static String getDescByCode(String code) {
        for (SysStatus task : SysStatus.values()) {
            if (StringUtils.equals(code, task.getCode())) {
                return task.desc;
            }
        }
        return "";
    }
}
