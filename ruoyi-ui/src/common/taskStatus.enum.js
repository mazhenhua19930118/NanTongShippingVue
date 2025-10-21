// 定义全局枚举（通常放在 src/constants/ 或 src/enums/ 目录下）
export const StatusEnum = {
  loading: { value: "1901", label: "已装货" },
  unloaded: { value: "2301", label: "已卸货" }
};

// 使用示例
console.log(StatusEnum.loading.value); // 输出: "待处理"
