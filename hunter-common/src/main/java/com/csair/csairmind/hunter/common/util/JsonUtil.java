package com.csair.csairmind.hunter.common.util;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by zhengcheng
 */
public class JsonUtil {
    public static String fromObject(Object object) {
        JSONObject jsonObject = JSONObject.fromObject(object);//将java对象转换为json对象
        String str = jsonObject.toString();//将json对象转换为字符串
        return str;
    }

    public static Object toBean(String str, Class beanClass) {
        JSONObject obj = new JSONObject().fromObject(str);//将json字符串转换为json对象
        return JSONObject.toBean(obj, beanClass);
    }

    /**
     * 如果bean里面有List自定义对象,需要使用此方法
     *
     * @param str
     * @param beanClass
     * @param classesMap 为自定义类的类型,如users-User.getClass()
     * @return
     */
    public static Object toBean(String str, Class beanClass, Map<String, Class> classesMap) {
        JSONObject obj = new JSONObject().fromObject(str);//将json字符串转换为json对象
        Object object = JSONObject.toBean(obj, beanClass, classesMap);
        return object;
    }

}
