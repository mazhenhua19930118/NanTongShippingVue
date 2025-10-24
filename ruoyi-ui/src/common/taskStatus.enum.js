// 定义全局枚举（通常放在 src/constants/ 或 src/enums/ 目录下）
export const StatusEnum = {
  loading: { value: "1901", label: "已装货" },
  camera: { value: "2302", label: "摄像头说明已补充" },
  upload_details: { value: "2401", label: "已上传卸货数据" },
  paid: { value: "2501", label: "已支付" },
  already_settled: { value: "2601", label: "已结算" }
};

// 使用示例
console.log(StatusEnum.camera.value); // 输出: "待处理"
