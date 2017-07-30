package cn.carwheel.helper;

import cn.carwheel.annotation.Inject;
import cn.carwheel.util.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class IocHelper {

    static {
        // 获取所有的Bean类与Bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (null != beanMap && !beanMap.isEmpty()) {
            // 遍历Bean Map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // 从Bean中获取Bean类与Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 从Bean中获取所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (null != beanFields) {
                    // 遍历Bean Field
                    for (Field beanField : beanFields) {
                        // 判断当前Bean Field是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 在Bean Map中获取Bean Field对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (null != beanFieldInstance) {
                                // 通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
