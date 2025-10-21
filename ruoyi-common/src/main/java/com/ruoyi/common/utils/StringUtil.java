package com.ruoyi.common.utils;

/**
 * @author mzh
 */
public class StringUtil {
    public static final String NULL = "null";
    /**
     * 判断两个字符串是否相等，避免空指针异常
     *
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return 如果两个字符串相等返回true，否则返回false
     */
    public static boolean isEquals(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equals(str2);
    }

    /**
     * 判断字符串是否为空
     * @param str 要判断的字符串
     * @return 如果字符串为 null 或空字符串，则返回 true；否则返回 false
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty() || isEquals(NULL,str);
    }

    public static boolean isNotEmpty(String str) {
        boolean flag = str == null || str.trim().isEmpty() || isEquals(NULL, str);
        return !flag;
    }

    public static boolean isEmpty(String str) {
        boolean flag = str == null || str.trim().isEmpty() || isEquals(NULL, str);
        return flag;
    }



    /**
     * 将 long 类型转换为字符串
     *
     * @param number 要转换的 long 值
     * @return 转换后的字符串
     */
    public static String convertLongToString(Long number) {
        if (number == null) {
            return "";
        }
        return String.valueOf(number);
    }


    /**
     * 将字符串转换为 long 类型
     * @param str 要转换的字符串
     * @return 转换后的 long 值
     * @throws NumberFormatException 如果字符串无法转换为 long
     */
    public static Long convertStringToLong(String str) {
        if (str == null || str.trim().isEmpty() || isEquals(NULL,str)) {
            return null;
        }
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("无法将字符串转换为 long 类型: " + str);
        }
    }
}
