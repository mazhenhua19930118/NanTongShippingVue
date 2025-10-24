package com.ruoyi.common.enums;

import com.ruoyi.common.utils.StringUtils;

/**
 * @author mzh
 */

public enum TaskStatus {
    /**
     * 创建
     */
//    create("1001", "已创建","创建了货源订单"),
//    publish("1002", "已发布","发布了货源订单"),
//    dispatch("1003", "已派单","派送了货源订单"),
//    sign_main_contract("1004", "主合同已签订","签订了主合同"),
//    pre_insurance("1005", "已预保险","上了预保险"),
//    termination("1006", "已终止","终止了任务"),
//    loading("1007", "已装货","填报了装货数据"),
//    insurance("1008", "已确认保险","确认了保险"),
//    sign_sub_contract("1009", "子合同已签订","签订了子合同"),
//    advance_payment("1010", "预付款","进行了预保险"),
//    unloaded("1011", "已卸货","确认了卸货"),
//    upload_details("1012", "已上传卸货明细","上传了卸货明细"),
//    paid("1013", "已支付","支付了运费"),
//    already_settled("1014", "已结算","结算了运费");

    create("1001", "合同已创建", "创建了合同"),
    sign_main_contract("1101", "主合同已签署", "签订了主合同"),
    publish("1201", "已发布", "发布了计划"),
    dispatch("1301", "已派单", "派送了订单"),
    deposit_paid("1401", "已派单（有定金）", "定金已受领"),
    shipping_fee_paid("1501", "已派单（有航费）", "航费已受领"),
    shipping_fee_deposit_paid("1601", "已派单（有航费和定金）", "航费和定金已受领"),
    wait_sign_sub_contract("1700", "待子合同签署", "待子合同签署"),
    sign_sub_contract("1701", "子合同已签署", "签订了子合同"),
    wait_pre_insurance("1800", "待预保险", "待预保险"),
    pre_insurance("1801", "已预保险", "确认了预保险"),
    loading("1901", "已装货", "已装货"),
    insurance("2001", "已保险确认", "已保险确认"),
    arrived_at_port("2101", "已到港", "已到港"),
    special_handling_for_unloading("2201", "卸货特殊情况处理", "卸货特殊情况处理"),
    wait_unloaded("2202", "待卸货", "待卸货"),
    unloaded("2301", "已卸货", "已卸货"),
    camera("2302", "摄像头说明已补充", "摄像头说明已补充"),
    upload_details("2401", "已上传卸货数据", "已上传卸货数据"),
    wait_paid("2500", "待支付", "待支付"),
    paid("2501", "已支付", "支付了运费"),
    already_settled("2601", "已结算", "结算了运费"),
    termination("2801", "已终止", "终止了任务");
    private String code;
    private String desc;
    private String remark;

    TaskStatus(String code, String desc, String remark) {
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
        for(TaskStatus task :TaskStatus.values()){
            if(StringUtils.equals(code,task.getCode())){
                return task.remark;
            }
        }
        return "";
    }

    public static String getDescByCode(String code) {
        for (TaskStatus task : TaskStatus.values()) {
            if (StringUtils.equals(code, task.getCode())) {
                return task.desc;
            }
        }
        return "";
    }
}
