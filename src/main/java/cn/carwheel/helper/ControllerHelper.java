package cn.carwheel.helper;

import cn.carwheel.annotation.Action;
import cn.carwheel.bean.Handler;
import cn.carwheel.bean.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ControllerHelper {

    private static Logger logger = LoggerFactory.getLogger(ControllerHelper.class);

    /**
     * 用于存放请求与处理器的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        // 获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (null != controllerClassSet && !controllerClassSet.isEmpty()) {
            // 遍历这些Controller类
            for (Class<?> controllerClass : controllerClassSet) {
                // 获取Controller类中定义的方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (null != methods && methods.length > 0) {
                    // 遍历这些Methods
                    for (Method method : methods) {
                        // 判断当前方法是否带有Action注解
                        Action action = method.getAnnotation(Action.class);
                        String mapping = action.value();
                        // 验证URL映射规则
                        if (mapping.matches("\\w+:/\\w*")) {
                            String[] array = mapping.split(":");
                            if (null != array && array.length == 2) {
                                // 获取请求方法与请求路径
                                String requestMethod = array[0];
                                String requestPath = array[1];
                                logger.debug("requestMethod: {} | requestPath: {} | controllerClassName: {} | method: {}",
                                        requestMethod, requestPath, controllerClass.getName(), method);
                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(controllerClass, method);
                                // 初始化Action Map
                                ACTION_MAP.put(request, handler);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
