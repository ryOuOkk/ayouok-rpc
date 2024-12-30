package com.ayouok.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @author dxn
 * @date 2024年12月20日 15:29
 * @apiNote 读取配置工具类
 */
public class ConfigUtil {

    /**
     * 加载配置对象
     *
     * @param tClass class类
     * @param prefix 前缀
     * @param <T>    返回
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，支持区分环境
     *
     * @param tClass      class类
     * @param prefix      前缀
     * @param environment 环境
     * @param <T>         类型
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        return props.toBean(tClass, prefix);
    }
}
