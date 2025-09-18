/**
 * 从对象数组中提取指定字段生成新数组
 * @param {Array} list - 原始对象数组
 * @param {string|Function} [field='id'] - 要提取的字段名或自定义处理函数
 * @returns {Array} 包含指定字段值的新数组
 */
export function getFieldsFromList (list, field = 'id') {
  // 边界处理：非数组或空数组直接返回空数组
  if (!Array.isArray(list) || list.length === 0) return [];

  // 处理嵌套字段（支持 'a.b.c' 格式）
  const getNestedValue = (obj, path) => {
    return path.split('.').reduce((acc, key) => acc?.[key], obj);
  };

  return list.reduce((acc, current) => {
    try {
      let value;

      if (typeof field === 'function') {
        // 使用自定义处理函数
        value = field(current);
      } else if (field.includes('.')) {
        // 处理嵌套字段
        value = getNestedValue(current, field);
      } else {
        // 直接获取字段
        value = current[field];
      }

      // 过滤无效值（可选）
      if (value !== undefined && value !== null) {
        acc.push(value);
      }
    } catch (e) {
      console.warn('字段提取失败:', e);
    }
    return acc;
  }, []);
}

export function arrLength (list) {
  if (list) {
    return list.length;
  } else {
    return 0;
  }
}

export function formatTaskStatus (taskStatus) {
  if (taskStatus === '1001') {
    return '已创建';
  }
  if (taskStatus === '1002') {
    return '已发布';
  }
  if (taskStatus === '1003') {
    return '已派单';
  }
  if (taskStatus === '1004') {
    return '主合同已签订';
  }
  if (taskStatus === '1005') {
    return '已预保险';
  }
  if (taskStatus === '1006') {
    return '已终止';
  }
  if (taskStatus === '1007') {
    return '已装货';
  }
  if (taskStatus === '1008') {
    return '已确认保险';
  }
  if (taskStatus === '1009') {
    return '子合同已签订';
  }
  if (taskStatus === '1010') {
    return '预付款';
  }
  if (taskStatus === '1011') {
    return '已卸货';
  }
  if (taskStatus === '1012') {
    return '已上传卸货明细';
  }
  if (taskStatus === '1013') {
    return '已支付';
  }
  if (taskStatus === '1014') {
    return '已结算';
  }
  return '未知';
}

export function formatTaskNode (taskStatus) {
  if (taskStatus === '1001') {
    return 1;
  }
  if (taskStatus === '1002') {
    return 2;
  }
  if (taskStatus === '1003') {
    return 3;
  }
  if (taskStatus === '1004') {
    return 4;
  }
  if (taskStatus === '1005') {
    return 5;
  }
  if (taskStatus === '1006') {
    return 6;
  }
  if (taskStatus === '1007') {
    return 7;
  }
  if (taskStatus === '1008') {
    return 8;
  }
  if (taskStatus === '1009') {
    return 9;
  }
  if (taskStatus === '1010') {
    return 10;
  }
  if (taskStatus === '1011') {
    return 11;
  }
  if (taskStatus === '1012') {
    return 12;
  }
  if (taskStatus === '1013') {
    return 13;
  }
  if (taskStatus === '1014') {
    return 14;
  }
  return '未知';
}

export function testNumber (number) {
  if (number) {
    const regex = /^\d+(\.\d+)?$/;
    return regex.test(number);
  }
  return true;
}


export function formatWeightUnit (weightUnit) {
  if (weightUnit === '1') {
    return '吨';
  }
  if (weightUnit === '2') {
    return '千克';
  }

  return '';
}

export function formatYesorNo (str) {
  if (str === '1') {
    return '是';
  }
  if (str === '0') {
    return '否';
  }

  return '--';
}

export function formatInsuranceStatus (status) {
  if (status === '0') {
    return '待预保险';
  }
  if (status === '1') {
    return '预保险';
  }
  if (status === '2') {
    return '已确认保险';
  }

  return '--';
}


// function getFieldsFromList(list, field = 'id') {
//   // 边界处理：非数组或空数组直接返回空数组
//   if (!Array.isArray(list) || list.length === 0) return [];
//
//   // 处理嵌套字段（支持 'a.b.c' 格式）
//   const getNestedValue = (obj, path) => {
//     return path.split('.').reduce((acc, key) => acc?.[key], obj);
//   };
//
//   return list.reduce((acc, current) => {
//     try {
//       let value;
//
//       if (typeof field === 'function') {
//         // 使用自定义处理函数
//         value = field(current);
//       } else if (field.includes('.')) {
//         // 处理嵌套字段
//         value = getNestedValue(current, field);
//       } else {
//         // 直接获取字段
//         value = current[field];
//       }
//
//       // 过滤无效值（可选）
//       if (value !== undefined && value !== null) {
//         acc.push(value);
//       }
//     } catch (e) {
//       console.warn('字段提取失败:', e);
//     }
//     return acc;
//   }, []);
// }
//
// // 使用示例 --------------------------
//
// // 基础用法
// const users = [
//   { id: 1, name: 'Alice' },
//   { id: 2, name: 'Bob' },
//   { id: null, name: 'Charlie' },
//   { info: { uid: 3 } }
// ];
//
// console.log(getFieldsFromList(users));
// // 输出: [1, 2]
//
// // 指定字段名
// console.log(getFieldsFromList(users, 'name'));
// // 输出: ["Alice", "Bob", "Charlie"]
//
// // 处理嵌套字段
// console.log(getFieldsFromList(users, 'info.uid'));
// // 输出: [3]
//
// // 使用自定义函数
// console.log(getFieldsFromList(users, item => item.id || item.info?.uid));
// // 输出: [1, 2, 3]
//
// // 处理异常情况
// console.log(getFieldsFromList(null));
// // 输出: []
// console.log(getFieldsFromList({}));
// // 输出: []
