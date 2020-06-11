package com.luojun.camel.common.utils;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BeanUtils {

    /**
     * 实例化对象
     *
     * @param clazz 对象类型
     * @param <T>   对象泛型类
     * @return 对象
     */
    public static <T> T instantiate(Class<T> clazz) {
//        if(!clazz.isInterface()){
//            throw new IllegalStateException("无法实例化接口：" + clazz.getName());
//        }
        try {
            return instantiate(clazz.getDeclaredConstructor());
        } catch (Exception e) {
            throw new IllegalStateException(clazz.getName()+"实例化对象失败", e);
        }
    }

    /**
     * 实例化对象
     *
     * @param constructor 构造器
     * @param <T>         对象泛型类
     * @return 对象
     */
    public static <T> T instantiate(Constructor<T> constructor, Object... initargs) {
        try {
            return constructor.newInstance(initargs);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("实例化%s对象失败", constructor.getDeclaringClass().getName()), e);
        }
    }

    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T target;
        try {
            target = instantiate(targetClass);
            copyProperties(source, target);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return target;
    }

    public static <T> List<T> copyProperties(List<?> source, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }
        return source.stream().map(s -> copyProperties(s, targetClass)).collect(Collectors.toList());
    }


}
