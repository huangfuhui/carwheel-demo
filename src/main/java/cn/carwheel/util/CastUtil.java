package cn.carwheel.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 转型操作工具类
 */
public class CastUtil {

    /**
     * 转为String类型，默认返回""
     *
     * @param obj
     * @return
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * 转为String类型
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String castString(Object obj, String defaultValue) {
        return null != obj ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为double类型，默认返回0.0
     *
     * @param obj
     * @return
     */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0.0);
    }

    /**
     * 转为double类型
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static double castDouble(Object obj, Double defaultValue) {
        double doubleValue = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    defaultValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转为long类型，默认返回0
     *
     * @param obj
     * @return
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /**
     * 转为long类型
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 转为int类型，默认返回0
     *
     * @param obj
     * @return
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /**
     * 转为int类型
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (null != obj) {
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        Integer.valueOf("");
        return intValue;
    }

    /**
     * 转为boolean类型，默认返回false
     *
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /**
     * 转为boolean类型
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        return null != obj ? Boolean.parseBoolean(castString(obj)) : defaultValue;
    }
}
