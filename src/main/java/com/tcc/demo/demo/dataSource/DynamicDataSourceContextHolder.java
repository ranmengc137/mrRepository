package com.tcc.demo.demo.dataSource;

public class DynamicDataSourceContextHolder {
    /**
     * 动态数据源名称上下文
     */
    private static final ThreadLocal<String> DATASOURCE_CONTEXT_KEY_HOLDER = new ThreadLocal<>();

    /**
     * 设置/切换数据源
     */
    public static void setContextKey(String key) {
        DATASOURCE_CONTEXT_KEY_HOLDER.set(key);
    }

    /**
     * 获取数据源名称
     */
    public static String getContextKey() {
        String key = DATASOURCE_CONTEXT_KEY_HOLDER.get();
        return key == null ? "account" : key;
    }

    /**
     * 删除当前数据源名称
     */
    public static void removeContextKey() {
        DATASOURCE_CONTEXT_KEY_HOLDER.remove();
    }
}