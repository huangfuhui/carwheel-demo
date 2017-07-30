package cn.carwheel.util;

/**
 * 系统配置常量
 */
public interface ConfigConstant {
    String CONFIG_FILE = "carwheel.properties";

    /**
     * 数据库配置信息
     */
    String JDBC_DRIVER = "carwheel.jdbc.driver";
    String JDBC_URL = "carwheel.jdbc.url";
    String JDBC_USERNAME = "carwheel.jdbc.username";
    String JDBC_PASSWORD = "carwheel.jdbc.password";

    /**
     * APP基本配置
     */
    String APP_BASE_PATH = "carwheel.app.base_package";
    String APP_JSP_PATH = "carwheel.app.jsp_path";
    String APP_ASSET_PATH = "carwheel.app.asset_path";
}
