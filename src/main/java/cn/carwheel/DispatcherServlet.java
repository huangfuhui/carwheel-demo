package cn.carwheel;

import cn.carwheel.bean.Data;
import cn.carwheel.bean.Handler;
import cn.carwheel.bean.Param;
import cn.carwheel.bean.View;
import cn.carwheel.helper.BeanHelper;
import cn.carwheel.helper.ConfigHelper;
import cn.carwheel.helper.ControllerHelper;
import cn.carwheel.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public void init(ServletConfig servletConfig) throws ServletException {
        // 初始化相关Helper类
        HelperLoader.init();
        // 获取ServletContext对象，用于注册Servlet
        ServletContext servletContext = servletConfig.getServletContext();
        // 注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAssetPath() + "*");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求方法与路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        logger.debug("requestMethod: " + requestMethod + " " + "requestPath: " + requestPath);

        // 获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (null != handler) {
            logger.debug("controller: " + handler.getControllerClass().toString() + "  " + "method: " + handler.getActionMethod().toString());
            // 获取Controller类以及Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // 创建请求参数对象
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] params = StringUtils.split(body, "&");
                if (null != params && params.length > 0) {
                    for (String param : params) {
                        String[] array = StringUtils.split(param, "=");
                        if (null != array && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            // 调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            // 处理Action返回值
            if (result instanceof View) {
                // 返回JSP页面
                View view = (View) result;
                String path = view.getPath();
                logger.debug("viewPath: " + path);
                if (StringUtils.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        response.sendRedirect(request.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                    }
                }
            } else if (result instanceof Data) {
                // 返回JSON数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (null != model) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    logger.debug("returnDataForJson: " + json);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
