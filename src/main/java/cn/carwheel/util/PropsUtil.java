package cn.carwheel.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropsUtil {

    /**
     * 加载属性文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        Properties properties = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (null == inputStream) {
                throw new FileNotFoundException(fileName + " is not found.");
            } else {
                properties = new Properties();
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    /**
     * 获取字符串类型属性
     *
     * @param properties
     * @param key
     * @return
     */
    public static String getString(Properties properties, String key) {
        return properties.getProperty(key, "");
    }

    /**
     * 获取字符串类型属性，有默认返回值
     *
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties properties, String key, String defaultValue) {
        if (properties.contains(key)) {
            return properties.getProperty(key);
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取整型类型属性
     *
     * @param properties
     * @param key
     * @return
     */
    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    /**
     * 获取整型类型属性，有默认值返回
     *
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Properties properties, String key, int defaultValue) {
        if (properties.contains(key)) {
            return Integer.valueOf(properties.getProperty(key));
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取布尔类型属性
     *
     * @param properties
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, false);
    }

    /**
     * 获取布尔类型属性，有默认值返回
     *
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Properties properties, String key, boolean defaultValue) {
        if (properties.contains(key)) {
            return Boolean.valueOf(properties.getProperty(key));
        } else {
            return defaultValue;
        }
    }
}
