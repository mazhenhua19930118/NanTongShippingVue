// 定义全局枚举（通常放在 src/constants/ 或 src/enums/ 目录下）
export const StatusEnum = {
  create: { value: "1001", label: "已创建" },
  publish: { value: "1002", label: "已发布" },
  dispatch: { value: "1003", label: "已派单" },
  sign_main_contract: { value: "1004", label: "主合同已签订" },
  pre_insurance: { value: "1005", label: "已预保险" },
  termination: { value: "1006", label: "已终止" },
  loading: { value: "1007", label: "已装货" },
  insurance: { value: "1008", label: "已确认保险" },
  sign_sub_contract: { value: "1009", label: "子合同已签订" },
  advance_payment: { value: "1010", label: "预付款" },
  unloaded: { value: "1011", label: "已卸货" },
  upload_details: { value: "1012", label: "已上传卸货明细" },
  paid: { value: "1013", label: "已支付" },
  already_settled: { value: "1014", label: "已结算" }
};

// 使用示例
console.log(StatusEnum.create.value); // 输出: "待处理"