package cn.carwheel;

import cn.carwheel.helper.BeanHelper;
import cn.carwheel.helper.ClassHelper;
import cn.carwheel.helper.ControllerHelper;
import cn.carwheel.helper.IocHelper;
import cn.carwheel.util.ClassUtil;

/**
 * 加载对应的Helper类
 */
public class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), false);
        }
    }
}
