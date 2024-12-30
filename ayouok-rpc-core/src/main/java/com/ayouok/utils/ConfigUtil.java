package com.ayouok.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import org.yaml.snakeyaml.Yaml;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
        //三种配置文件
        String ymlConfig = configFileBuilder + ".yml";
        String yamlConfig = configFileBuilder + ".yaml";
        String propertiesConfig = configFileBuilder + ".properties";
        //判断三种配置文件是否存在
        boolean existYml = FileUtil.exist(ymlConfig);
        boolean existYaml = FileUtil.exist(yamlConfig);
        boolean existProperties = FileUtil.exist(propertiesConfig);

        if (existYml) {
            //yml文件
            return loadYamlConfig(ymlConfig, tClass, prefix);
        } else if (existYaml) {
            //yaml文件
            return loadYamlConfig(yamlConfig, tClass, prefix);
        } else if (existProperties) {
            //properties文件
            return loadPropertiesConfig(propertiesConfig, tClass, prefix);
        } else {
            throw new ConfigurationNotFoundException("No configuration file found for environment: " + environment);
        }
    }

    private static <T> T loadYamlConfig(String configPath, Class<?> tClass, String prefix) {
        try {
            Yaml yaml = new Yaml();
            //获取配置文件中的map集合
            HashMap<String, Object> yamlMap = yaml.loadAs(FileUtil.readUtf8String(configPath), HashMap.class);
            return mapToBean(yamlMap, tClass, prefix);
        } catch (Exception e) {
            throw new ConfigurationLoadException("Failed to load YAML configuration from " + configPath, e);
        }
    }

    private static <T> T mapToBean(Map<String, Object> yamlMap, Class<?> tClass, String prefix) {
        try {
            Constructor<?> constructor = tClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = (T) constructor.newInstance();

            for (Field field : tClass.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = prefix.isEmpty() ? field.getName() : prefix + "." + field.getName();
                Object value = yamlMap.get(fieldName);
                if (value != null) {
                    field.set(instance, value);
                }
            }
            return instance;
        } catch (Exception e) {
            throw new ConfigurationLoadException("Failed to map YAML to bean", e);
        }
    }

    private static <T> T loadPropertiesConfig(String configPath, Class<?> tClass, String prefix) {
        try {
            Props props = new Props(configPath);
            return (T) props.toBean(tClass, prefix);
        } catch (Exception e) {
            throw new ConfigurationLoadException("Failed to load properties configuration", e);
        }
    }

    // 自定义异常类
    public static class ConfigurationNotFoundException extends RuntimeException {
        public ConfigurationNotFoundException(String message) {
            super(message);
        }
    }

    public static class ConfigurationLoadException extends RuntimeException {
        public ConfigurationLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
